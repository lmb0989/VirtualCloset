package com.virtualcloset.servlet;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.virtualcloset.model.UserBean;

public class UserRegister extends HttpServlet {
	
	UserBean user;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html"); 
		response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter(); 
        
        String strJson = request.getParameter("requestJson");
        
        int regResult;
        try {
        	JSONTokener jsonParser = new JSONTokener(strJson);
			JSONObject jobj = (JSONObject) jsonParser.nextValue();
        	user = new UserBean(jobj);
        	regResult = user.create();
//			if(dao.isUserNameExist(user.getUserName())){
//				regResult = UserConfig.REG_MESSAGE_USEREXIST;
//				System.out.println("ע�������û��Ѵ���");
//			}else{
//				regResult = dao.regUser(user);
//				System.out.println("ע�᷵�ؽ����regResult="+regResult);
//			}
		} catch (JSONException e) {
			System.out.println("ע�������1");
			regResult = UserBean.REG_MESSAGE_FAILED;
			System.out.println("ע�������2  regResult="+regResult);
			e.printStackTrace();
			System.out.println("ע�ᱨ��...");
		}
		
		if(regResult == UserBean.REG_MESSAGE_REGSUCCESS){
			HttpSession session = request.getSession();
        	session.setAttribute("userName", user.userName);
		}
		out.print(user.createMessage(regResult));
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
