package com.virtualcloset.servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONHander;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.virtualcloset.config.UserConfig;
import com.virtualcloset.dbdao.DatabaseDao;
import com.virtualcloset.model.UserBean;
import com.virtualcloset.util.UserUtil;

public class UserLogin extends HttpServlet {
	
	UserBean user;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html"); 
		response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        
        String strJson = request.getParameter("requestJson");
//        JSONHander hander = new JSONHander();
//        DatabaseDao dao = new DatabaseDao();
//        UserBean user = hander.getLoginUser(strJson);
        int loginResult;
        try {
	        JSONTokener jsonParser = new JSONTokener(strJson);
			JSONObject jobj = (JSONObject) jsonParser.nextValue();
			String userName = jobj.getString("username");
	        
	        int userType = UserUtil.getUserType(userName);
	        user = new UserBean(jobj); 
	        System.out.println("password = "+user.passWord);
	        System.out.println("userType = "+userType);
	        
	        if(userType == UserUtil.TYPE_USEREMAIL){
	        	userName = UserBean.getUserName("email", userName);
	        	if(userName.isEmpty()){
	        		loginResult = UserBean.LOGIN_MESSAGE_USERNOTEXIST;
	        	}else{
	        		jobj.put("username", userName);
	        		user = new UserBean(jobj);
	        	}
	        }else if(userType == UserUtil.TYPE_USERPHONE){
	        	userName = UserBean.getUserName("phone", userName);
	        	if(userName == null){
	        		loginResult = UserBean.LOGIN_MESSAGE_USERNOTEXIST;
	        	}else{
	        		jobj.put("username", userName);
	        		user = new UserBean(jobj);
	        	}
	        }
	        loginResult = user.login();
        } catch (Exception e) {
        	loginResult = UserBean.LOGIN_MESSAGE_PASSWRONG;
        	e.printStackTrace();
        }
        
        if(loginResult == UserBean.LOGIN_MESSAGE_LOGINSUCCESS){
        	HttpSession session = request.getSession();
        	session.setAttribute("userName", user.userName);
        	System.out.println("UserLogin.class userName>>>"+user.userName);
//        	out.print("µÇÂ½³É¹¦");
        }
    	out.print(user.createMessage(loginResult).toString());
        
        out.flush(); 
        out.close(); 
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
