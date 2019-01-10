package com.example.controller;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import sun.misc.BASE64Decoder;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileOperator {
    public boolean deleteImage(String url) {
        String str = url;
        String[] buff = str.split("/");
        String fileName = buff[buff.length - 1];


//        //local
//        WebApplicationContext webApplicationContext = ContextLoader
//                .getCurrentWebApplicationContext();
//        ServletContext servletContext = webApplicationContext
//                .getServletContext();
//        // 得到文件绝对路径
//        String realPath = servletContext.getRealPath("/img/");

        //web
        String realPath="/home/ubuntu/apache-tomcat-8.5.35/webapps/timeline/img/";


        String path = realPath+ fileName;


        File file = new File(path);
        if (!file.exists()) return false;
        if (file.delete()) {
            return true;
        } else {
            return false;
        }
    }

    public String getImage(String imgStr, String imgname) {
        if (imgStr == null)
            return "false";
        //判断图片类型，处理base64前缀
        String type=".jpg";
        String suffix[]={".jpg",".gif",".png",".jpg"};
        String prefix[]={"data:image/jpg;base64,","data:image/gif;base64,","data:image/png;base64,","data:image/jpeg;base64,"};
        for(int i=0;i<prefix.length;i++)
        {
            if(imgStr.indexOf(prefix[i])>=0) {
                imgStr=imgStr.replace(prefix[i], "");
                type=suffix[i];
            }
        }

        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
//            //生成jpeg图片

//            //local
//            WebApplicationContext webApplicationContext = ContextLoader
//                    .getCurrentWebApplicationContext();
//            ServletContext servletContext = webApplicationContext
//                    .getServletContext();
//            // 得到文件绝对路径
//            String realPath = servletContext.getRealPath("/img/");
////            //web
            String realPath="/home/ubuntu/apache-tomcat-8.5.35/webapps/timeline/img/";
            String imgFilePath = realPath+ imgname + type;

            OutputStream out = new FileOutputStream(imgFilePath);
            try {
                out.write(b);
                out.flush();
            }finally {
                out.close();
            }
            return type;
        } catch (IOException e) {
            e.printStackTrace();
            return "false";

        }
    }
}
