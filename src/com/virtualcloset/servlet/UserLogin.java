package com.virtualcloset.servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.virtualcloset.config.UserConfig;
import com.virtualcloset.dbdao.UserDao;
import com.virtualcloset.model.UserBean;
import com.virtualcloset.util.UserUtil;

public class UserLogin extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		boolean flag = false;
		UserBean user = new UserBean();
		
		response.setContentType("text/html"); 
		response.setCharacterEncoding("gbk");
        PrintWriter out = response.getWriter(); 
        String userName = request.getParameter("username");
        String passWord  = request.getParameter("password");
        
        user.setUserName(userName);
        user.setPassword(passWord);
        int userType = UserUtil.getUserType(userName);
        int loginResult;
        try {
	        if(userType == UserConfig.USER_TYPE_USERNAME){
				loginResult = UserDao.login(user);
	        }else if(userType == UserConfig.USER_TYPE_USEREMAIL){
	        	userName = UserDao.getUserName(userName, "email");
	        	if(userName == null){
	        		loginResult = UserConfig.MESSAGE_USERNOTEXIST;
	        	}else{
	        		user.setUserName(userName);
	        		loginResult = UserDao.login(user);
	        	}
	        }else{
	        	userName = UserDao.getUserName(userName, "phone");
	        	if(userName == null){
	        		loginResult = UserConfig.MESSAGE_USERNOTEXIST;
	        	}else{
	        		user.setUserName(userName);
	        		loginResult = UserDao.login(user);
	        	}
	        }
        } catch (SQLException e) {
        	loginResult = UserConfig.MESSAGE_PASSWRONG;
        	e.printStackTrace();
        }
        
        if(loginResult == UserConfig.MESSAGE_LOGINSUCCESS){
        	HttpSession session = request.getSession();
        	session.setAttribute("userName", userName);
        	out.print("登陆成功");
        }else if(loginResult == UserConfig.MESSAGE_USERNOTEXIST){
        	out.print("用户不存在");
        }else{
        	out.print("密码错误");
        }
        out.flush();    
        out.close();   
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
