package com.virtualcloset.servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONHander;

import com.virtualcloset.config.UserConfig;
import com.virtualcloset.dbdao.DatabaseDao;
import com.virtualcloset.model.UserBean;
import com.virtualcloset.util.UserUtil;

public class UserLogin extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html"); 
		response.setCharacterEncoding("gbk");
        PrintWriter out = response.getWriter(); 
        
        String json = request.getParameter("requestJson");
        JSONHander hander = new JSONHander();
        UserBean user = hander.getLoginUser(json);
        
        String userName = user.getUserName();
        int userType = UserUtil.getUserType(userName);
        int loginResult;
        try {
	        if(userType == UserConfig.USER_TYPE_USERNAME){
				loginResult = DatabaseDao.login(user);
	        }else if(userType == UserConfig.USER_TYPE_USEREMAIL){
	        	userName = DatabaseDao.getUserName(userName, "email");
	        	if(userName == null){
	        		loginResult = UserConfig.LOGIN_MESSAGE_USERNOTEXIST;
	        	}else{
	        		user.setUserName(userName);
	        		loginResult = DatabaseDao.login(user);
	        	}
	        }else{
	        	userName = DatabaseDao.getUserName(userName, "phone");
	        	if(userName == null){
	        		loginResult = UserConfig.LOGIN_MESSAGE_USERNOTEXIST;
	        	}else{
	        		user.setUserName(userName);
	        		loginResult = DatabaseDao.login(user);
	        	}
	        }
        } catch (SQLException e) {
        	loginResult = UserConfig.LOGIN_MESSAGE_PASSWRONG;
        	e.printStackTrace();
        }
        
        if(loginResult == UserConfig.LOGIN_MESSAGE_LOGINSUCCESS){
        	HttpSession session = request.getSession();
        	session.setAttribute("userName", userName);
        	System.out.println("UserLogin.class userName>>>"+userName);
//        	out.print("µÇÂ½³É¹¦");
        }
    	out.print(hander.createrLoginOrRegMessage(userName, loginResult));
        
        out.flush(); 
        out.close(); 
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
