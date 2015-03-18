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

import com.virtualcloset.dbdao.DatabaseDao;
import com.virtualcloset.model.UserBean;

public class FetchUserInfo extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		System.out.println("=========");
		response.setContentType("text/html");
		response.setCharacterEncoding("gbk");
		PrintWriter out = response.getWriter();
		
		String json = request.getParameter("requestJson");
        JSONHander hander = new JSONHander();
        
        HttpSession session = request.getSession(false);
//        System.out.println("session>>>"+session==null);
        String userName = hander.getUserName(json);
        System.out.println("userName>>>"+userName);
        
        UserBean user = null;
        try {
			user = DatabaseDao.getUer(userName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		out.print(hander.getUserInfo(user));
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
