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

public class UserRegister extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html"); 
		response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter(); 
        
        String json = request.getParameter("requestJson");
        JSONHander hander = new JSONHander();
        DatabaseDao dao = new DatabaseDao();
        UserBean user = hander.getRegUser(json);
        
        int regResult;
        try {
			if(dao.isUserNameExist(user.getUserName())){
				regResult = UserConfig.REG_MESSAGE_USEREXIST;
				System.out.println("ע�������û��Ѵ���");
			}else{
				regResult = dao.regUser(user);
				System.out.println("ע�᷵�ؽ����regResult="+regResult);
			}
		} catch (SQLException e) {
			System.out.println("ע�������1");
			regResult = UserConfig.REG_MESSAGE_FAILED;
			System.out.println("ע�������2  regResult="+regResult);
			e.printStackTrace();
			System.out.println("ע�ᱨ��...");
		}
		
		if(regResult == UserConfig.REG_MESSAGE_REGSUCCESS){
			HttpSession session = request.getSession();
        	session.setAttribute("userName", user.getUserName());
		}
		out.print(hander.createrLoginOrRegMessage(user.getUserName(), regResult));
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
