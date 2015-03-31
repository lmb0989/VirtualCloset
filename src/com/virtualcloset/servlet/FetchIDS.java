package com.virtualcloset.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.virtualcloset.model.ImageBean;
import com.virtualcloset.model.VideoBean;

public class FetchIDS extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		JSONArray idsArray = new JSONArray();;
		try {
			String strJson = request.getParameter("requestJson");
			JSONTokener token = new JSONTokener(strJson);
			JSONObject jobj;
			jobj = (JSONObject)token.nextValue();
			
			String idsType = (String)request.getAttribute("idstype");
			if(idsType.equals("imageids")){
				ImageBean image = new ImageBean(jobj);
				for(ImageBean im : image.getUserAllImage()){
					idsArray.put(im.imageId);
				}
				jobj.put("message", 0);
				jobj.put("image_ids", idsArray);
				out.print(jobj.toString());
			}else{
				VideoBean video = new VideoBean(jobj);
				for(VideoBean vd : video.getUserAllVideo()){
					idsArray.put(vd.videoId);
				}
				jobj.put("message", 0);
				jobj.put("video_ids", idsArray);
				out.print(jobj.toString());
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
