package com.virtualcloset.servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONHander;

import com.virtualcloset.config.UserConfig;
import com.virtualcloset.dbdao.UserDao;
import com.virtualcloset.model.UserBean;

public class UserRegister extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html"); 
		response.setCharacterEncoding("gbk");
        PrintWriter out = response.getWriter(); 
        
        String json = request.getParameter("requestJson");
        JSONHander hander = new JSONHander();
        UserBean user = hander.getRegUser(json);
        
        int regResult;
        try {
			if(UserDao.isUserNameExist(user.getUserName())){
				regResult = UserConfig.REG_MESSAGE_USEREXIST;
			}else{
				regResult = UserDao.regUser(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
