//package com.example.controller;
//
//import model.User;
//import net.sf.json.JSONObject;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import static org.mockito.Matchers.anyString;
//import static org.mockito.Mockito.*;
//
//public class UserControllerTest {
//
//    RemoteDBUnit mockDB;
//    UserController userController;
//    UserController spyUC;
//    User user;
//    String input;
//
//    @Before
//    public void setUp() throws Exception {
//        mockDB=mock(RemoteDBUnit.class);
//        userController=new UserController(mockDB);
//        spyUC=spy(new UserController());
//        user=new User();
//        input="{\"userID\":\"vickiii@qq.com\",\"nickname\":\"vicki\",\"password\":\"123456\"}";
//    }
//
//    //tests for input
//    @Test
//    public void input_need_userID_and_should_not_be_null(){
//        doReturn(false).when(spyUC).check(JSONObject.fromObject(input),"userID");
//
//        JSONObject res1 = new JSONObject();
//        res1 = spyUC.login(input);
//        Assert.assertEquals("false", res1.getString("message"));
//        Assert.assertEquals("invalid_input", res1.getString("errorType"));
//
//        JSONObject res2 = new JSONObject();
//        res2 = spyUC.register(input);
//        Assert.assertEquals("false", res2.getString("message"));
//        Assert.assertEquals("invalid_input", res2.getString("errorType"));
//    }
//
//    @Test
//    public void input_need_password_and_should_not_be_null(){
//        doReturn(false).when(spyUC).check(JSONObject.fromObject(input),"password");
//
//        JSONObject res1 = new JSONObject();
//        res1 = spyUC.login(input);
//        Assert.assertEquals("false", res1.getString("message"));
//        Assert.assertEquals("invalid_input", res1.getString("errorType"));
//
//        JSONObject res2 = new JSONObject();
//        res2 = spyUC.register(input);
//        Assert.assertEquals("false", res2.getString("message"));
//        Assert.assertEquals("invalid_input", res2.getString("errorType"));
//    }
//
//    @Test
//    public void input_need_nickname_and_should_not_be_null(){
//        doReturn(false).when(spyUC).check(JSONObject.fromObject(input),"nickname");
//
//        JSONObject res1 = new JSONObject();
//        res1 = spyUC.register(input);
//        Assert.assertEquals("false", res1.getString("message"));
//        Assert.assertEquals("invalid_input", res1.getString("errorType"));
//    }
//
//
//    //tests for register
//    @Test
//    public void userID_cant_be_repeated_when_register() {
//        when(mockDB.userIDinUser(anyString())).thenReturn(true);
//
//        JSONObject res= new JSONObject();
//        res = userController.register(input);
//
//        //(expect,actual)
//        Assert.assertEquals("false",res.getString("message"));
//        Assert.assertEquals("userID_repeated",res.getString("errorType"));
//    }
//
//    @Test
//    public void when_insert_error_return_false() {
//        when(mockDB.userIDinUser(anyString())).thenReturn(false);
//        when(mockDB.insertUser(anyString(),anyString(),anyString())).thenReturn(false);
//
//        JSONObject res= new JSONObject();
//        res = userController.register(input);
//
//        Assert.assertEquals("false",res.getString("message"));
//        Assert.assertEquals("userID_repeated",res.getString("errorType"));
//
//    }
//
//    @Test
//    public void register_return_true_if_everything_is_right() {
//        when(mockDB.userIDinUser(anyString())).thenReturn(false);
//        when(mockDB.insertUser(anyString(),anyString(),anyString())).thenReturn(true);
//
//        JSONObject res= new JSONObject();
//        res = userController.register(input);
//
//        Assert.assertEquals("true",res.getString("message"));
//        Assert.assertEquals("empty",res.getString("errorType"));
//
//    }
//
//
//    //tests for login
//    @Test
//    public void userId_should_in_db_when_login(){
//        when(mockDB.userIDinUser(anyString())).thenReturn(false);
//
//        JSONObject res= new JSONObject();
//        res = userController.login(input);
//
//        Assert.assertEquals("false",res.getString("message"));
//        Assert.assertEquals("invalid_userID_or_password",res.getString("errorType"));
//    }
//
//    @Test
//    public void password_should_match_userID(){
//        when(mockDB.userIDinUser(anyString())).thenReturn(true);
//        when(mockDB.matchPassword(anyString(),anyString())).thenReturn(false);
//
//        JSONObject res= new JSONObject();
//        res = userController.login(input);
//
//        Assert.assertEquals("false",res.getString("message"));
//        Assert.assertEquals("invalid_userID_or_password",res.getString("errorType"));
//    }
//
//    @Test
//    public void get_nickname_failed_will_return_false(){
//        when(mockDB.userIDinUser(anyString())).thenReturn(true);
//        when(mockDB.matchPassword(anyString(),anyString())).thenReturn(true);
//        when(mockDB.getNickname(anyString())).thenReturn(null);
//
//        JSONObject res= new JSONObject();
//        res = userController.login(input);
//
//        Assert.assertEquals("false",res.getString("message"));
//        Assert.assertEquals("query_error",res.getString("errorType"));
//    }
//
//    @Test
//    public void login_return_true_if_everything_is_right(){
//        when(mockDB.userIDinUser(anyString())).thenReturn(true);
//        when(mockDB.matchPassword(anyString(),anyString())).thenReturn(true);
//        when(mockDB.getNickname(anyString())).thenReturn("right_nickname");
//
//        JSONObject res= new JSONObject();
//        res = userController.login(input);
//
//        Assert.assertEquals("true",res.getString("message"));
//        Assert.assertEquals("empty",res.getString("errorType"));
//        Assert.assertEquals("right_nickname",res.getString("nickname"));
//    }
//
//
//    //test for check
//    @Test
//    public void checkTest(){
//        JSONObject jsonInput=new JSONObject();
//        String item;
//
//        jsonInput=JSONObject.fromObject("{\"userID\":\"1\"}");
//        item="nickname";
//        Assert.assertEquals(false,spyUC.check(jsonInput,item));
//
//        jsonInput=JSONObject.fromObject("{\"userID\":\"\"}");
//        item="userID";
//        Assert.assertEquals(false,spyUC.check(jsonInput,item));
//
//        jsonInput=jsonInput=JSONObject.fromObject("{\"userID\":\"1\"}");
//        item="userID";
//        Assert.assertEquals(true,spyUC.check(jsonInput,item));
//    }
//
//
//}