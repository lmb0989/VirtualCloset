package com.virtualcloset.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.virtualcloset.config.DBConfig;
import com.virtualcloset.model.ImageBean;

public class DeleteData extends HttpServlet {
	
	private static final int DELETE_MESSAGE_SUCCESS = 0;
	private static final int DELETE_MESSAGE_FAIL = 1;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		JSONObject messageJson = null;
		try {
			String requestJson = request.getParameter("requestJson");
			JSONTokener token = new JSONTokener(requestJson);
			JSONObject jobj = (JSONObject)token.nextValue();
			
			String deleteType = (String)request.getAttribute("deletetype");
			if(deleteType.equals("deleteimage")){
				messageJson = deleteImage(jobj);
			}else{
				
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		out.print(messageJson.toString());
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}
	
	private JSONObject deleteImage(JSONObject jobj) {
		int message;
		ImageBean image = new ImageBean(jobj);
		image = image.query();
		if(image != null){
			int res = image.delete();
			if(res > 0){
				message = DELETE_MESSAGE_SUCCESS;
				String fileName = image.fileName;
				File file = new File(DBConfig.vedioLocation, fileName);
				file.deleteOnExit();
			}else{
				message = DELETE_MESSAGE_FAIL;
			}
		}else{
			message = DELETE_MESSAGE_FAIL;
		}
		return image.createMessage(message);
	}
}
