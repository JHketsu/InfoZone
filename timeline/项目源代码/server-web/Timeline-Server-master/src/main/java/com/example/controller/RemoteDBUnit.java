package com.example.controller;

import model.Article;

import java.sql.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class RemoteDBUnit{
    public static final String user = "root";
    public static final String password = "tmt980122";

//        //local
//    public final static String url = "jdbc:mysql://129.204.74.128:3306/timeline?useUnicode=true&amp&characterEncoding=UTF-8&useSSL=false";
//    public static String hostPath="/img/";
//    public final static String hostUrl="http://localhost:8080/timeline/img/";
////
////    web
    public final static String url = "jdbc:mysql://localhost:3306/timeline?useUnicode=true&amp&characterEncoding=UTF-8&useSSL=false&";
    public static String hostPath="/home/ubuntu/apache-tomcat-8.5.35/webapps/timeline/img/";
    public static String hostUrl="http://129.204.74.128:8080/timeline/img/";


    //SingleTon
//    private static RemoteDBUnit dbUnit = null;

//    public static RemoteDBUnit getDBUnit() {
//        if (dbUnit == null) dbUnit = new RemoteDBUnit();
//        return dbUnit;
//    }

    //默认构造函数
//    private RemoteDBUnit() {
//        try {
//            //加载驱动
//            Class.forName("com.mysql.cj.jdbc.Driver");
//        } catch (Exception e) {
//            // TODO: handle exception
//            e.printStackTrace();
//        }
//    }

//    public Connection getConnection() {
//        try {
//            return DriverManager.getConnection(url, user, password);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    public  Connection createConnection() {
        try {
            //加载驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e2) {
            e2.printStackTrace();
            return null;
        }
    }


    public static void closeDBResource(Connection con,PreparedStatement pstm,ResultSet rs)
    {
        if(rs!=null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(pstm!=null) {
            try {
                pstm.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(con!=null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean userIDinUser(String userID) {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Connection con = createConnection();

        String sql = "select userID from user where userID=?";

        boolean flg = false;
        try {
            pstm = con.prepareStatement(sql);
            pstm.setString(1, userID);
            rs = pstm.executeQuery();

            while (rs.next()) {
                flg = true;
                break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            RemoteDBUnit.closeDBResource(con,pstm,rs);
        }
        return flg;
    }


    public boolean articleIDinArticle(int articleID){
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Connection con = createConnection();

        String sql = "select articleID from article where articleID=?";

        boolean flg = false;
        try {
            pstm = con.prepareStatement(sql);
            pstm.setInt(1, articleID);
            rs = pstm.executeQuery();

            while (rs.next()) {
                flg = true;
                break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            RemoteDBUnit.closeDBResource(con,pstm,rs);
        }
        return flg;
    }


    public boolean insertUser(String userID,String nickname,String password) {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Connection con = createConnection();

        boolean flg=userIDinUser(userID);
        if(flg==true)return false;

        try {
            String sql = "insert into user(userID,nickname,password) values (?,?,?)";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, userID);
            pstm.setString(2, nickname);
            pstm.setString(3, password);
            pstm.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            RemoteDBUnit.closeDBResource(con, pstm, rs);
        }
    }

    public boolean matchPassword(String userID,String password) {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Connection con = createConnection();

        try {
            String sql = "select password,nickname from user where userID=?";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, userID);
            rs = pstm.executeQuery();
            while (rs.next()) {
                if (password.equals(rs.getString("password"))) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            RemoteDBUnit.closeDBResource(con, pstm, rs);
        }
        return false;
    }

    public String getNickname(String userID) {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Connection con = createConnection();

        try {
            String sql = "select nickname from user where userID=?";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, userID);
            rs = pstm.executeQuery();
            while (rs.next()) {
                return rs.getString("nickname");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            RemoteDBUnit.closeDBResource(con, pstm, rs);
        }
        return null;
    }

    public boolean insertArticle(String userID,String content,String imageURL) {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Connection con = createConnection();

        String nickname=getNickname(userID);
        if(nickname==null)return false;

        try {
            String sql = "insert into article(userID,content,imageURL,nickname) values (?,?,?,?)";
            pstm = con.prepareStatement(sql);
            pstm.setString(1, userID);
            pstm.setString(2, content);
            pstm.setString(3, imageURL);
            pstm.setString(4, nickname);
            pstm.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            RemoteDBUnit.closeDBResource(con, pstm, rs);
        }
    }

    public ArrayList<Article> currentArticles(int front, int num){
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Connection con = createConnection();
        ArrayList<Article> articles=new ArrayList<Article>();

        try {
            String sql = "select * from article where articleID > ? order by articleID DESC";
            pstm = con.prepareStatement(sql);
            pstm.setInt(1, front);
            rs = pstm.executeQuery();
            int count = 0;
            while (rs.next() && (num-- != 0)) {
                Article article = new Article();
                count++;
                article.setArticleID(rs.getString("articleID"));
                article.setUserID(rs.getString("userID"));
                article.setContent(rs.getString("content"));
                article.setImageURL(rs.getString("imageURL"));
                article.setNickname(rs.getString("nickname"));
                article.setTimeStamp(getTimeDif(rs.getTimestamp("time").toLocalDateTime()));
                articles.add(article);
            }
            return articles;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            RemoteDBUnit.closeDBResource(con, pstm, rs);
        }
    }

    public ArrayList<Article> previousArticles(int tail,int num){
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Connection con = createConnection();
        ArrayList<Article> articles=new ArrayList<Article>();

        try {
            String sql = "select * from article where articleID < ? order by articleID DESC";
            pstm = con.prepareStatement(sql);
            pstm.setInt(1, tail);
            rs = pstm.executeQuery();
            int count = 0;
            while (rs.next() && (num-- != 0)) {
                Article article = new Article();
                count++;
                article.setArticleID(rs.getString("articleID"));
                article.setUserID(rs.getString("userID"));
                article.setContent(rs.getString("content"));
                article.setImageURL(rs.getString("imageURL"));
                article.setNickname(rs.getString("nickname"));
                article.setTimeStamp(getTimeDif(rs.getTimestamp("time").toLocalDateTime()));
                articles.add(article);
            }
            return articles;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            RemoteDBUnit.closeDBResource(con, pstm, rs);
        }
    }

    public String getImageUrl(int articleID) {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Connection con = createConnection();

        boolean flag = articleIDinArticle(articleID);
        if (flag == false) return null;

        try {
            String sql = "select * from article where articleID = ? ";
            pstm = con.prepareStatement(sql);
            pstm.setInt(1, articleID);
            rs = pstm.executeQuery();
            while (rs.next()) {
                return rs.getString("imageURL");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            RemoteDBUnit.closeDBResource(con, pstm, rs);
        }
        return null;
    }

    public boolean deleteArticle(int articleID){
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Connection con = createConnection();

        boolean flag=articleIDinArticle(articleID);
        if(flag==false)return false;

        try {
            String sql = "delete from article where articleID = ? ";
            pstm = con.prepareStatement(sql);
            pstm.setInt(1, articleID);
            pstm.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            RemoteDBUnit.closeDBResource(con, pstm, rs);
        }
    }




    public static String getTimeDif(LocalDateTime past) {
        LocalDateTime now = LocalDateTime.now();
        past = past.minusHours(14);
        Duration duration = Duration.between(past, now);
        long min = duration.toMinutes();
        long hour = duration.toHours();
        long day = duration.toDays();

        DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyy年M月d日");
        DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("M月d日H时");
        DateTimeFormatter dtf3 = DateTimeFormatter.ofPattern("HH:mm");

        if (past.getYear() != now.getYear()) {
            return dtf1.format(past);
        }
        else if (past.getMonth() != now.getMonth()||day>1) {
            return dtf2.format(past);
        }
        else if (day == 1) {
            return "昨天 " + dtf3.format(past);
        }
        else if (hour >= 1) {
            return hour + "" + "小时前";
        }
        else if (min > 0) {
            return min + "" + "分钟前";
        }
        else return "刚刚";
    }


}





