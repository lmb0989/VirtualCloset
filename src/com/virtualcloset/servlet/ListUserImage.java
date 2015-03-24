package com.virtualcloset.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.virtualcloset.dbdao.DatabaseDao;
import com.virtualcloset.model.ImageBean;

public class ListUserImage extends HttpServlet {

	ImageBean image;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String strJson = request.getParameter("requestJson");
		try{
			JSONTokener token = new JSONTokener(strJson);
			JSONObject jobj = (JSONObject) token.nextValue();
			image = new ImageBean(jobj);
			String postType = jobj.getString("posttype");
			
			if(postType.equals("getAllImageId")){
				JSONArray array = new JSONArray();
				for(ImageBean im : image.getUserAllImage()){
					array.put(im.imageId);
				}
				out.print(array.toString());
				out.flush();
				out.close();
			}else{
				out.print(image.getImageInfo().toString());
				out.flush();
				out.close();
			}
		}catch(JSONException e){ }
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
