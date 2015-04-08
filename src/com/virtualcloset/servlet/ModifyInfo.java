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

import com.virtualcloset.model.ImageBean;
import com.virtualcloset.model.UserBean;
import com.virtualcloset.model.VideoBean;

public class ModifyInfo extends HttpServlet {
	
	private static final int MODIFY_MESSAGE_SUCCESS = 0;
	private static final int MODIFY_MESSAGE_FAIL = 1;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String modifyType = (String) request.getAttribute("modifytype");
		String strJson = request.getParameter("requestJson");
		int result;
		try {
        	JSONTokener jsonParser = new JSONTokener(strJson);
        	JSONObject jobj = (JSONObject) jsonParser.nextValue();
        	if(modifyType.equals("modifyimage")){
        		ImageBean image = new ImageBean(jobj);
        		result = image.update("season", image.season);
        		result = image.update("situation", image.situation);
        		result = image.update("style", image.style);
        		result = image.update("type", image.type);
        		out.print(image.createMessage(result>0 ? MODIFY_MESSAGE_SUCCESS : MODIFY_MESSAGE_FAIL));
        	}else{
        		
        	}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
