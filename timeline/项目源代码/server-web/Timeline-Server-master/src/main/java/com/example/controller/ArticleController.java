package com.example.controller;

import model.*;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;

import java.util.*;
import java.util.Date;

@Controller
@RequestMapping("/article")

public class ArticleController {
    RemoteDBUnit remoteDBUnit;
    FileOperator fileOperator;

    @RequestMapping(value = "/push")
    @ResponseBody
    public JSONObject push(@RequestBody String json ) {
//        RemoteDBUnit remoteDBUnit = new RemoteDBUnit();

        JSONObject object = JSONObject.fromObject(json);
        Article article = new Article();
        NormalMessage msg = new NormalMessage();

        //input
        if(check(object,"userID")==false){
            msg.setMessage("false");
            msg.setErrorType("invalid_input");
            return JSONObject.fromObject(msg);
        }
        if(check(object,"content")==false){
            msg.setMessage("false");
            msg.setErrorType("invalid_input");
            return JSONObject.fromObject(msg);
        }

        article.setUserID(object.getString("userID"));
        article.setContent(object.getString("content"));


        //userID是否有效
        if (remoteDBUnit.userIDinUser(article.getUserID()) == false) {
            msg.setMessage("false");
            msg.setErrorType("invalid_userID");
            return JSONObject.fromObject(msg);
        }


        //deal with image
        if(check(object,"image")==false)article.setImageURL("empty");
        else  {
            String base=object.getString("image");
            //生成图片名
            SimpleDateFormat simpleDateFormat;
            simpleDateFormat = new SimpleDateFormat("ddHHssSSS");
            Date date = new Date();
            String str = simpleDateFormat.format(date);
            Random random = new Random();
            int rannum = random.nextInt() * (99999 - 10000 + 1) + 10000;// 获取5位随机数
            String imgname = rannum + "" + str;
            String url = RemoteDBUnit.hostUrl + imgname;
            //String imgPath = new String();
            String type= fileOperator.getImage(base, imgname);
            if (type != "false") article.setImageURL(url+type);
            else {
                msg.setErrorType("image_upload_error");
                msg.setMessage("false");
                return JSONObject.fromObject(msg);
            }
        }

        //插入文章
        boolean flg=remoteDBUnit.insertArticle(article.getUserID(),article.getContent(),article.getImageURL());
        if(flg==false) {
            msg.setMessage("false");
            msg.setErrorType("insert_error");
            return JSONObject.fromObject(msg);
        }

        msg.setMessage("true");
        msg.setErrorType("empty");
        return JSONObject.fromObject(msg);
    }


    @RequestMapping(value = "/refresh")
    @ResponseBody
    public JSONObject refresh(@RequestBody String json) {
//        RemoteDBUnit remoteDBUnit = new RemoteDBUnit();

        JSONObject object = JSONObject.fromObject(json);
        ArticleMessage msg = new ArticleMessage();
        ArrayList<Article> articles;

        if(check(object,"show")==false){
            msg.setMessage("false");
            msg.setErrorType("invalid_input");
            return JSONObject.fromObject(msg);
        }

        if(check(object,"articleID")==false){
            msg.setMessage("false");
            msg.setErrorType("invalid_input");
            return JSONObject.fromObject(msg);
        }

        int num = object.getInt("show");
        int front = object.getInt("articleID");


        //search for recent articles
        articles=remoteDBUnit.currentArticles(front,num);
        if(articles == null){
            msg.setMessage("false");
            msg.setErrorType("query_error");
            return JSONObject.fromObject(msg);
        }

        msg.setArticles(articles);
        msg.setNum(articles.size()+"");
        msg.setMessage("true");
        msg.setErrorType("empty");

        return JSONObject.fromObject(msg);
    }


    @RequestMapping(value = "/more")
    @ResponseBody
    public JSONObject more(@RequestBody String json){
//        RemoteDBUnit remoteDBUnit = new RemoteDBUnit();

        JSONObject object = JSONObject.fromObject(json);
        ArticleMessage msg = new ArticleMessage();
        ArrayList<Article> articles;

        if(check(object,"show")==false){
            msg.setMessage("false");
            msg.setErrorType("invalid_input");
            return JSONObject.fromObject(msg);
        }

        if(check(object,"articleID")==false){
            msg.setMessage("false");
            msg.setErrorType("invalid_input");
            return JSONObject.fromObject(msg);
        }

        int num = object.getInt("show");
        int tail = object.getInt("articleID");

        //search for previous articles
        articles=remoteDBUnit.previousArticles(tail,num);
        if(articles == null){
            msg.setMessage("false");
            msg.setErrorType("query_error");
            return JSONObject.fromObject(msg);
        }

        msg.setArticles(articles);
        msg.setNum(articles.size()+"");
        msg.setMessage("true");
        msg.setErrorType("empty");

        return JSONObject.fromObject(msg);
    }


    @RequestMapping(value = "/delete")
    @ResponseBody
    public JSONObject delete(@RequestBody String json) {
//        RemoteDBUnit remoteDBUnit = new RemoteDBUnit();

        JSONObject object = JSONObject.fromObject(json);
        NormalMessage msg = new NormalMessage();

        //check input
        if(check(object,"articleID")==false){
            msg.setMessage("false");
            msg.setErrorType("invalid_input");
            return JSONObject.fromObject(msg);
        }

        int del = object.getInt("articleID");

        //articleID should in db
        boolean flag= remoteDBUnit.articleIDinArticle(del);
        if(flag==false) {
            msg.setMessage("false");
            msg.setErrorType("invalid_articleID");
            return JSONObject.fromObject(msg);
        }

        String imageUrl=remoteDBUnit.getImageUrl(del);
        if(imageUrl==null){
            msg.setMessage("false");
            msg.setErrorType("query_error");
            return JSONObject.fromObject(msg);
        }

        boolean flg2 = remoteDBUnit.deleteArticle(del);
        if(flg2==false){
            msg.setMessage("false");
            msg.setErrorType("query_error");
            return JSONObject.fromObject(msg);
        }

        if(imageUrl.equals("empty")) {
            msg.setMessage("true");
            msg.setErrorType("empty");
            return JSONObject.fromObject(msg);
        }

        boolean flg3= fileOperator.deleteImage(imageUrl);
        if(flg3==false) {
            msg.setMessage("true");
            msg.setErrorType("delete_error");
            return JSONObject.fromObject(msg);
        }

        msg.setMessage("true");
        msg.setErrorType("empty");
        return JSONObject.fromObject(msg);
    }


    public ArticleController() {
        remoteDBUnit = new RemoteDBUnit();
        fileOperator =new FileOperator();
    }


    public ArticleController(RemoteDBUnit remoteDBUnit,FileOperator fileOperator){
        this.remoteDBUnit=remoteDBUnit;
        this.fileOperator=fileOperator;
    }



    public boolean check(JSONObject json, String item){
        if(!json.has(item))return false;
        String rs=json.getString(item);
        if(rs==null||rs.equals(""))return false;
        return true;
    }


}
