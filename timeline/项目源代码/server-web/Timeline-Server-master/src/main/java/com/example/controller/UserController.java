package com.example.controller;

import model.NormalMessage;
import model.NormalMessage2;
import model.User;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

    RemoteDBUnit remoteDBUnit;

    @RequestMapping(value = "/register")
    @ResponseBody
    public JSONObject register(@RequestBody String json) {
//        RemoteDBUnit remoteDBUnit = new RemoteDBUnit();

        JSONObject object = JSONObject.fromObject(json);
        User user = new User();
        NormalMessage msg = new NormalMessage();

        //判断输入
        if(check(object,"userID")==false){
            msg.setMessage("false");
            msg.setErrorType("invalid_input");
            return JSONObject.fromObject(msg);
        }

        if(check(object,"password")==false){
            msg.setMessage("false");
            msg.setErrorType("invalid_input");
            return JSONObject.fromObject(msg);
        }

        if(check(object,"nickname")==false){
            msg.setMessage("false");
            msg.setErrorType("invalid_input");
            return JSONObject.fromObject(msg);
        }

        user.setUserID(object.getString("userID"));
        user.setPassword(object.getString("password"));
        user.setNickname(object.getString("nickname"));

        //是否重id
        if (remoteDBUnit.userIDinUser(user.getUserID()) == true) {
            msg.setMessage("false");
            msg.setErrorType("userID_repeated");
            return JSONObject.fromObject(msg);
        } else {
            //插入用户信息
            boolean flg = remoteDBUnit.insertUser(user.getUserID(), user.getNickname(), user.getPassword());
            if (flg == true) {
                msg.setMessage("true");
                msg.setErrorType("empty");
                return JSONObject.fromObject(msg);
            }
            //可能同时插入
            else {
                msg.setMessage("false");
                msg.setErrorType("userID_repeated");
                return JSONObject.fromObject(msg);
            }

        }
    }


    @RequestMapping(value = "/login")
    @ResponseBody
    public JSONObject login(@RequestBody String json) {
//        RemoteDBUnit remoteDBUnit = new RemoteDBUnit();

        JSONObject object = JSONObject.fromObject(json);
        User user = new User();
        NormalMessage2 msg = new NormalMessage2();

        //input
        if(check(object,"userID")==false){
            msg.setMessage("false");
            msg.setErrorType("invalid_input");
            return JSONObject.fromObject(msg);
        }

        if(check(object,"password")==false){
            msg.setMessage("false");
            msg.setErrorType("invalid_input");
            return JSONObject.fromObject(msg);
        }

        user.setUserID(object.getString("userID"));
        user.setPassword(object.getString("password"));

        boolean flg = remoteDBUnit.userIDinUser(user.getUserID());
        if (flg == false) {
            msg.setMessage("false");
            msg.setErrorType("invalid_userID");
            return JSONObject.fromObject(msg);
        } else {
            //密码匹配
            boolean flg2=remoteDBUnit.matchPassword(user.getUserID(), user.getPassword());
            if(flg2==true) {
                //获取昵称
                String nickname = remoteDBUnit.getNickname(user.getUserID());
                if(nickname!=null) {
                    msg.setMessage("true");
                    msg.setErrorType("empty");
                    msg.setNickname(nickname);
                    return JSONObject.fromObject(msg);
                }
                else {
                    msg.setMessage("false");
                    msg.setErrorType("query_error");
                    return JSONObject.fromObject(msg);
                }
            }
            else{
                msg.setMessage("false");
                msg.setErrorType("invalid_userID_or_password");
                return JSONObject.fromObject(msg);
            }
        }
    }


//    @RequestMapping(value = "/getnickname")
//    @ResponseBody
//    public JSONObject getNickname(@RequestBody String json) {
////        RemoteDBUnit remoteDBUnit=new RemoteDBUnit();
//
//        JSONObject object=JSONObject.fromObject(json);
//        User user=new User();
//        NormalMessage2 msg=new NormalMessage2();
//
//
//        if(object.has("userID")) {
//            user.setUserID(object.getString("userID"));
//        }
//        else  {
//            msg.setMessage("false");
//            msg.setErrorType("invalid_input");
//            return JSONObject.fromObject(msg);
//        };
//
//        String nickname = remoteDBUnit.getNickname(user.getUserID());
//        if(nickname!=null) {
//            msg.setMessage("true");
//            msg.setErrorType("empty");
//            msg.setNickname(nickname);
//            return JSONObject.fromObject(msg);
//        }
//        else {
//            msg.setMessage("false");
//            msg.setErrorType("invalid_userID");
//            return JSONObject.fromObject(msg);
//        }
//    }

    public boolean check(JSONObject json, String item){
        if(!json.has(item))return false;
        String rs=json.getString(item);
        if(rs==null||rs.equals(""))return false;
        return true;
    }

    public UserController() {
        remoteDBUnit = new RemoteDBUnit();
    }


    public UserController(RemoteDBUnit remoteDBUnit){
        this.remoteDBUnit=remoteDBUnit;
    }

}
