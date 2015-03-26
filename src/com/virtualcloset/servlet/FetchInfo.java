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

public class FetchInfo extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String strJson = request.getParameter("requestJson");
		String infoType = (String)request.getAttribute("infotype");
        try {
        	JSONTokener jsonParser = new JSONTokener(strJson);
        	JSONObject jobj = (JSONObject) jsonParser.nextValue();
        	if(infoType.equals("userinfo")){
        		UserBean user = new UserBean(jobj);
        		out.print(user.getUserInfo().toString());
        	}else if(infoType.equals("imageinfo")){
        		ImageBean image = new ImageBean(jobj);
        		out.print(image.getImageInfo().toString());
        	}else{
        		VideoBean video = new VideoBean(jobj);
        		out.print(video.getVideoInfo().toString());
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
