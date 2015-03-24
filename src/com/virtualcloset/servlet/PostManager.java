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

public class PostManager extends HttpServlet {
	
	private static final String POST_TYPE_LOGIN = "login";
	private static final String POST_TYPE_REGISTER = "register";
	private static final String POST_TYPE_FETCH_USERINFO = "fetch_userinfo"; 
    private static final String POST_TYPE_FETCH_IMAGE = "fetch_image_info";
	private static final String POST_TYPE_UPLOAD_IMAGE = "upload_image";
	private static final String POST_TYPE_UPLOAD_FILE = "upload_file";
	private static final String POST_TYPE_DOWNLOAD_IMAGE = "download_image";
	private static final String POST_TYPE_DOWNLOAD_FILE = "download_file";
	
    private static final String POST_TYPE_FETCH_VEDIO_IDS = "fetch_vedio_ids";
    private static final String POST_TYPE_FETCH_IMAGE_IDS = "fetch_image_ids";
    private static final String POST_TYPE_DOWNLOAD_VEDIO = "download_vedio";

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String postType = null;
		try{
			String strJson = request.getParameter("requestJson");
			JSONTokener token = new JSONTokener(strJson);
			JSONObject jobj = (JSONObject)token.nextValue();
			postType = jobj.getString("type");
		}catch(JSONException ex){
			ex.printStackTrace();
		}
		
		String path = null;
		if(postType != null){
			if(postType.equals(POST_TYPE_LOGIN)){
				path = "/login";
			}else if(postType.equals(POST_TYPE_REGISTER)){
				path = "/reg";
			}else if(postType.equals(POST_TYPE_FETCH_USERINFO)){
				path = "/fetchInfo";
				request.setAttribute("infotype", "userinfo");
			}else if(postType.equals(POST_TYPE_FETCH_IMAGE)){
				path = "/fetchInfo";
				request.setAttribute("infotype", "imageinfo");
			}else if(postType.equals(POST_TYPE_UPLOAD_IMAGE)){
				path = "/uploadfile";
			}else if(postType.equals(POST_TYPE_UPLOAD_FILE)){
				
			}else if(postType.equals(POST_TYPE_DOWNLOAD_IMAGE)){
				path = "/downloadfile";
			}else{
				
			}
			if(path != null){
				System.out.println("PostManager.java  path >>> "+path);
				request.getRequestDispatcher(path) .forward(request,response);
			}
		}
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
