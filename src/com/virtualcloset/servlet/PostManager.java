package com.virtualcloset.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONHander;

public class PostManager extends HttpServlet {
	
	private static final String POST_TYPE_LOGIN = "login";
	private static final String POST_TYPE_REGISTER = "register";
	private static final String POST_TYPE_FETCH_USERINFO = "fetch_userinfo";
	private static final String POST_TYPE_UPLOAD_IMAGE = "upload_image";
	private static final String POST_TYPE_UPLOAD_FILE = "upload_file";
	private static final String POST_TYPE_DOWNLOAD_IMAGE = "download_image";
	private static final String POST_TYPE_DOWNLOAD_FILE = "download_file";

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		JSONHander hander = new JSONHander();
		String json = request.getParameter("requestJson");
		String postType = hander.getPostType(json);
		String path = null;
		if(postType != null){
			if(postType.equals(POST_TYPE_LOGIN)){
				path = "/login";
			}else if(postType.equals(POST_TYPE_REGISTER)){
				path = "/reg";
			}else if(postType.equals(POST_TYPE_FETCH_USERINFO)){
				path = "/fetchUserInfo";
			}else if(postType.equals(POST_TYPE_UPLOAD_IMAGE)){
				
			}else if(postType.equals(POST_TYPE_UPLOAD_FILE)){
				path = "/uploadfile";
			}else if(postType.equals(POST_TYPE_DOWNLOAD_IMAGE)){
				
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
