//package com.example.controller;
//
//import model.Article;
//import model.User;
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.util.ArrayList;
//
//import static org.mockito.Matchers.anyInt;
//import static org.mockito.Matchers.anyString;
//import static org.mockito.Mockito.*;
//
//public class ArticleControllerTest {
//
//    RemoteDBUnit mockDB;
//    FileOperator mockFO;
//    ArticleController spyAC;
//    ArticleController articleController;
//    User user;
//    Article article;
//    String input;
//    ArrayList<Article> articles;
//
//    @Before
//    public void setUp() throws Exception {
//        mockDB=mock(RemoteDBUnit.class);
//        mockFO=mock(FileOperator.class);
//        spyAC=spy(new ArticleController(mockDB,mockFO));
//        articleController=new ArticleController();
//        user=new User();
//        article=new Article();
//        input="{\"userID\":\"1\",\"content\":\"1\",\"image\":\"1\",\"show\":\"1\",\"articleID\":\"1\"}";
//
//        articles=new ArrayList<Article>();
//        article.setUserID("vickiii@qq.com");
//        article.setNickname("vicki");
//        article.setImageURL("sample_url");
//        article.setContent("sample_content");
//        articles.add(article);
//    }
//
//    //tests for input
//    @Test
//    public void input_need_articleID_and_should_not_be_null(){
//        doReturn(false).when(spyAC).check(JSONObject.fromObject(input),"articleID");
//
//        JSONObject res1 = new JSONObject();
//        res1 = spyAC.refresh(input);
//        Assert.assertEquals("false", res1.getString("message"));
//        Assert.assertEquals("invalid_input", res1.getString("errorType"));
//
//        JSONObject res2 = new JSONObject();
//        res2 = spyAC.more(input);
//        Assert.assertEquals("false", res2.getString("message"));
//        Assert.assertEquals("invalid_input", res2.getString("errorType"));
//
//        JSONObject res3 = new JSONObject();
//        res3 = spyAC.delete(input);
//        Assert.assertEquals("false", res3.getString("message"));
//        Assert.assertEquals("invalid_input", res3.getString("errorType"));
//    }
//
//    @Test
//    public void input_need_show_and_should_not_be_null(){
//        doReturn(false).when(spyAC).check(JSONObject.fromObject(input),"show");
//
//        JSONObject res1 = new JSONObject();
//        res1 = spyAC.refresh(input);
//        Assert.assertEquals("false", res1.getString("message"));
//        Assert.assertEquals("invalid_input", res1.getString("errorType"));
//
//        JSONObject res2 = new JSONObject();
//        res2 = spyAC.more(input);
//        Assert.assertEquals("false", res2.getString("message"));
//        Assert.assertEquals("invalid_input", res2.getString("errorType"));
//    }
//
//    @Test
//    public void input_need_userID_and_should_not_be_null(){
//        doReturn(false).when(spyAC).check(JSONObject.fromObject(input),"userID");
//
//        JSONObject res1 = new JSONObject();
//        res1 = spyAC.push(input);
//        Assert.assertEquals("false", res1.getString("message"));
//        Assert.assertEquals("invalid_input", res1.getString("errorType"));
//    }
//
//    @Test
//    public void input_need_content_and_should_not_be_null(){
//        doReturn(false).when(spyAC).check(JSONObject.fromObject(input),"content");
//
//        JSONObject res1 = new JSONObject();
//        res1 = spyAC.push(input);
//        Assert.assertEquals("false", res1.getString("message"));
//        Assert.assertEquals("invalid_input", res1.getString("errorType"));
//    }
//
//
//    //tests for push
//    @Test
//    public void userID_should_in_db() {
//        doReturn(false).when(mockDB).userIDinUser(anyString());
//
//        JSONObject res = new JSONObject();
//        res = spyAC.push(input);
//
//        Assert.assertEquals("false", res.getString("message"));
//        Assert.assertEquals("invalid_userID", res.getString("errorType"));
//    }
//
//    @Test
//    public void when_image_is_null_or_not_exist_imageUrl_is_empty() {
//        doReturn(false).when(spyAC).check(JSONObject.fromObject(input),"image");
//    }
//
//    @Test
//    public void when_get_image_failed_return_false() {
//        doReturn(true).when(mockDB).userIDinUser(anyString());
//        doReturn("false").when(mockFO).getImage(anyString(),anyString());
//
//        JSONObject res = new JSONObject();
//        res = spyAC.push(input);
//
//        Assert.assertEquals("false", res.getString("message"));
//        Assert.assertEquals("image_upload_error", res.getString("errorType"));
//    }
//
//    @Test
//    public void when_insert_article_failed_return_false() {
//        doReturn(true).when(mockDB).userIDinUser(anyString());
//        doReturn(".jpg").when(mockFO).getImage(anyString(),anyString());
//        doReturn(false).when(mockDB).insertArticle(any(),any(),any());
//
//        JSONObject res = new JSONObject();
//        res = spyAC.push(input);
//
//        Assert.assertEquals("false", res.getString("message"));
//        Assert.assertEquals("insert_error", res.getString("errorType"));
//    }
//
//    @Test
//    public void push_return_true_if_everything_is_right(){
//        doReturn(true).when(mockDB).userIDinUser(anyString());
//        doReturn(".jpg").when(mockFO).getImage(anyString(),anyString());
//        doReturn(true).when(mockDB).insertArticle(any(),any(),any());
//
//        JSONObject res = new JSONObject();
//        res = spyAC.push(input);
//
//        Assert.assertEquals("true", res.getString("message"));
//        Assert.assertEquals("empty", res.getString("errorType"));
//    }
//
//
//
//
//
//    //tests for refresh
//    @Test
//    public void when_query_current_articles_failed_return_false() {
//        when(mockDB.currentArticles(anyInt(), anyInt())).thenReturn(null);
//
//        JSONObject res = new JSONObject();
//        res = spyAC.refresh(input);
//
//        Assert.assertEquals("false", res.getString("message"));
//        Assert.assertEquals("query_error", res.getString("errorType"));
//    }
//
//    @Test
//    public void refresh_return_true_if_everything_is_right(){
//        when(mockDB.currentArticles(anyInt(), anyInt())).thenReturn(articles);
//
//        JSONObject res = new JSONObject();
//        res = spyAC.refresh(input);
//
//        Assert.assertEquals("true", res.getString("message"));
//        Assert.assertEquals("empty", res.getString("errorType"));
//        Assert.assertEquals(JSONArray.fromObject(articles).toString(), res.getString("articles"));
//        Assert.assertEquals("1", res.getString("num"));
//    }
//
//
//    //tests for more
//    @Test
//    public void when_query_previous_articles_failed_return_false() {
//        when(mockDB.previousArticles(anyInt(), anyInt())).thenReturn(null);
//
//        JSONObject res = new JSONObject();
//        res = spyAC.more(input);
//
//        Assert.assertEquals("false", res.getString("message"));
//        Assert.assertEquals("query_error", res.getString("errorType"));
//    }
//
//    @Test
//    public void more_return_true_if_everything_is_right(){
//        when(mockDB.previousArticles(anyInt(), anyInt())).thenReturn(articles);
//
//        JSONObject res = new JSONObject();
//        res = spyAC.more(input);
//
//        Assert.assertEquals("true", res.getString("message"));
//        Assert.assertEquals("empty", res.getString("errorType"));
//        Assert.assertEquals(JSONArray.fromObject(articles).toString(), res.getString("articles"));
//        Assert.assertEquals("1", res.getString("num"));
//    }
//
//
//    //tests for delete
//    @Test
//    public void articleID_should_in_db(){
//        when(mockDB.articleIDinArticle(anyInt())).thenReturn(false);
//
//        JSONObject res = new JSONObject();
//        res = spyAC.delete(input);
//
//        Assert.assertEquals("false", res.getString("message"));
//        Assert.assertEquals("invalid_articleID", res.getString("errorType"));
//    }
//
//    @Test
//    public void when_imageUrl_query_failed_return_false(){
//        when(mockDB.articleIDinArticle(anyInt())).thenReturn(true);
//        when(mockDB.getImageUrl(anyInt())).thenReturn(null);
//
//        JSONObject res = new JSONObject();
//        res = spyAC.delete(input);
//
//        Assert.assertEquals("false", res.getString("message"));
//        Assert.assertEquals("query_error", res.getString("errorType"));
//    }
//
//    @Test
//    public void when_delete_article_failed_return_false(){
//        when(mockDB.articleIDinArticle(anyInt())).thenReturn(true);
//        when(mockDB.getImageUrl(anyInt())).thenReturn("sample_url");
//        when(mockDB.deleteArticle(anyInt())).thenReturn(false);
//
//        JSONObject res = new JSONObject();
//        res = spyAC.delete(input);
//
//        Assert.assertEquals("false", res.getString("message"));
//        Assert.assertEquals("query_error", res.getString("errorType"));
//    }
//
//    @Test
//    public void when_imageUrl_is_empty_wont_call_deleteImage_and_return_true(){
//
//        when(mockDB.articleIDinArticle(anyInt())).thenReturn(true);
//        when(mockDB.getImageUrl(anyInt())).thenReturn("empty");
//        when(mockDB.deleteArticle(anyInt())).thenReturn(true);
//
//        JSONObject res = new JSONObject();
//        res = spyAC.delete(input);
//
//        Assert.assertEquals("true", res.getString("message"));
//        Assert.assertEquals("empty", res.getString("errorType"));
//        verify(mockFO,times(0)).deleteImage(anyString());
//    }
//
//    @Test
//    public void when_delete_image_file_failed_return_false(){
//        when(mockDB.articleIDinArticle(anyInt())).thenReturn(true);
//        when(mockDB.getImageUrl(anyInt())).thenReturn("sample_url");
//        when(mockDB.deleteArticle(anyInt())).thenReturn(true);
//        //spy object should return first
//        doReturn(false).when(mockFO).deleteImage(anyString());
//
//        JSONObject res = new JSONObject();
//        res = spyAC.delete(input);
//
//        Assert.assertEquals("false", res.getString("message"));
//        Assert.assertEquals("delete_error", res.getString("errorType"));
//    }
//
//    @Test
//    public void delete_return_true_if_everything_is_right(){
//        when(mockDB.articleIDinArticle(anyInt())).thenReturn(true);
//        when(mockDB.getImageUrl(anyInt())).thenReturn("sample_url");
//        when(mockDB.deleteArticle(anyInt())).thenReturn(true);
//        //spy object should return first
//        doReturn(true).when(mockFO).deleteImage(anyString());
//
//        JSONObject res = new JSONObject();
//        res = spyAC.delete(input);
//
//        Assert.assertEquals("true", res.getString("message"));
//        Assert.assertEquals("empty", res.getString("errorType"));
//    }
//
//    //test for check
//    @Test
//    public void checkTest(){
//        JSONObject jsonInput=new JSONObject();
//        String item;
//
//        jsonInput=JSONObject.fromObject("{\"userID\":\"1\"}");
//        item="nickname";
//        Assert.assertEquals(false,spyAC.check(jsonInput,item));
//
//        jsonInput=JSONObject.fromObject("{\"userID\":\"\"}");
//        item="userID";
//        Assert.assertEquals(false,spyAC.check(jsonInput,item));
//
//        jsonInput=jsonInput=JSONObject.fromObject("{\"userID\":\"1\"}");
//        item="userID";
//        Assert.assertEquals(true,spyAC.check(jsonInput,item));
//    }
//
//}