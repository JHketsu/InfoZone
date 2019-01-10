package com.example.controller;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.example.controller.RemoteDBUnit;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@CrossOrigin
@Controller
@RequestMapping("/hello")
public class HelloController {
    @RequestMapping("/cwj")
    @ResponseBody
    public String hello()
    {
        WebApplicationContext webApplicationContext = ContextLoader
                .getCurrentWebApplicationContext();
        ServletContext servletContext = webApplicationContext
                .getServletContext();
        // 得到文件绝对路径
        String realPath = servletContext.getRealPath("/img");
        return realPath;
    }

//    @RequestMapping(value="/jsonGET",method = RequestMethod.GET)
//    @ResponseBody
//    public JSONObject jsonGET(String str1,String str2)
//    {
//
//        JSONObject show=new JSONObject();
//        show.put("username",str1);
//        show.put("password6",str2);
//        return show;
//    }
//
//    @RequestMapping(value="/jsonPOST")
//    @ResponseBody
//    public JSONObject jsonPOST(@RequestBody String json)
//    {
//        JSONObject to=new JSONObject();
//        JSONObject from=JSONObject.fromObject(json);
//        to.put("username",from.get("nm"));
//        to.put("password1",from.get("pw"));
//        return to;
//    }
//
//    @RequestMapping(value="/cntest")
//
//    @ResponseBody
//    public JSONObject testChinese(@RequestBody String json)
//    {
//        JSONObject to=new JSONObject();
//        JSONObject from=JSONObject.fromObject(json);
//        to.put("Cn",from.get("Cn"));
//        String str=to.getString("Cn");
//        System.out.println(from.get("Cn"));
//
//        return to;
//    }
//
//    @RequestMapping(value="/dbGETtest",method = RequestMethod.GET)
//    @ResponseBody
//    public JSONObject dbtest(String id)
//    {
//        JSONObject to=new JSONObject();
//        String sql="select nickname from user where userID=?";
//        String nickname=new String();
//
//        PreparedStatement pstm=null;
//        ResultSet rs=null;
//        Connection con= RemoteDBUnit.getDBUnit().getConnection();
//        if(con!=null)
//        {
//            try {
//                pstm = con.prepareStatement(sql);
//                pstm.setString(1, id);
//                rs=pstm.executeQuery();
//
//                while(rs.next())
//                {
//                    nickname=rs.getString(1);
//                }
//                con.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            to.put("username",nickname);
//        }
//        return to;
//    }
//
//
//
//    @RequestMapping(value="/dbPOSTtest")
//    @ResponseBody
//    public JSONObject dbPostTest(@RequestBody String id) {
//        JSONObject to = new JSONObject();
//        JSONObject from = JSONObject.fromObject(id);
//        String sql = "select nickname from user where userID=?";
//        String nickname = new String();
//
//        PreparedStatement pstm = null;
//        ResultSet rs = null;
//        Connection con = RemoteDBUnit.getDBUnit().getConnection();
//        if (con != null) {
//            try {
//                pstm = con.prepareStatement(sql);
//                String str = from.getString("id");
//                pstm.setString(1, str);
//
//                rs = pstm.executeQuery();
//
//
//                while (rs.next()) {
//                    nickname = rs.getString(1);
//                }
//
//                con.close();
//
//                to.put("username", nickname);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return to;
//    }
//
//
//    @RequestMapping(value="/dbInsertTest")
//    @ResponseBody
//    public JSONObject dbInsertTest(@RequestBody String json)
//    {
//        JSONObject to=new JSONObject();
//        JSONObject from=JSONObject.fromObject(json);
//        JSONObject msg=new JSONObject();
//        String userID=from.getString("userID");
//        String nickname=from.getString("nickname");
//        String password=from.getString("password");
//
//        PreparedStatement pstm2=null;
//        ResultSet rs=null;
//        Connection con= RemoteDBUnit.getDBUnit().getConnection();
//        if(con!=null)
//        {
//            try {
//                    String sql2 = "insert into user(userID,nickname,password) values (?,?,?)";
//                    pstm2 = con.prepareStatement(sql2);
//                    pstm2.setString(1, "003");
//                    pstm2.setString(2, "inserttest");
//                    pstm2.setString(3, "111");
//                    pstm2.executeUpdate();
//                    msg.put("message1", "true");
//                    msg.put("ifrepeated", "false");
//                } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//
//            try {
//                con.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//
//        return msg;
    }



//    @RequestMapping(value="/posttest", method = RequestMethod.POST)
//    @ResponseBody
//    public JSONObject posttest(HttpServletResponse response, String json) {
//        JSONObject to = new JSONObject();
//        JSONObject from=JSONObject.fromObject(json);
//        to.put("post",from.get("post"));
//
//        response.addHeader("Access-Control-Allow-Origin", "*"); //用于ajax post跨域（*，最好指定确定的http等协议+ip+端口号）
//        response.setCharacterEncoding("utf-8");
//
//    return to;
//
//    }
//
//    @RequestMapping(value="/gettest1",method = RequestMethod.GET)
//    @ResponseBody
//    public JSONObject gettest1(String str)
//    {
//        JSONObject show=new JSONObject();
//        show.put("get1",str);
//        return show;
//    }
//
//    @RequestMapping(value="/gettest1",method = RequestMethod.GET)
//    @ResponseBody
//    public JSONObject gettest1( HttpServletResponse response,String str)
//    {
//        JSONObject show=new JSONObject();
//        show.put("get2",str);
//        response.addHeader("Access-Control-Allow-Origin", "*"); //用于ajax post跨域（*，最好指定确定的http等协议+ip+端口号）
//        response.setCharacterEncoding("utf-8");
//        return show;
//    }
//}
