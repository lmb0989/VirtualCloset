package com.virtualcloset.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.virtualcloset.model.UserBean;

public class FetchUserInfo extends HttpServlet {

	UserBean user;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String strJson = request.getParameter("requestJson");
        try {
        	JSONTokener jsonParser = new JSONTokener(strJson);
        	JSONObject jobj = (JSONObject) jsonParser.nextValue();
			user = new UserBean(jobj);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		out.print(user.getUserInfo().toString());
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
