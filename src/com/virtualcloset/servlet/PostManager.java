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
    private static final String POST_TYPE_FETCH_IMAGEINFO = "fetch_image_info";
    private static final String POST_TYPE_FETCH_VIDEOINFO = "fetch_video_info";
    
    private static final String POST_TYPE_MODIFY_IMAGE = "modify_image";
    
    private static final String POST_TYPE_DELETE_IMAGE = "delete_image";
    
	private static final String POST_TYPE_UPLOAD_IMAGE = "upload_image";
	private static final String POST_TYPE_UPLOAD_FILE = "upload_file";
	
    private static final String POST_TYPE_FETCH_VEDIO_IDS = "fetch_video_ids";
    private static final String POST_TYPE_FETCH_IMAGE_IDS = "fetch_image_ids";
    
    private static final String POST_TYPE_DOWNLOAD_IMAGE = "download_image";
    private static final String POST_TYPE_DOWNLOAD_VIDEO = "download_video";
    private static final String POST_TYPE_DOWNLOAD_FILE = "download_file";

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String postType = null;
		try{
			String strJson = request.getParameter("requestJson");
			JSONTokener token = new JSONTokener(strJson);
			JSONObject jobj = (JSONObject)token.nextValue();
			postType = jobj.getString("post_type");
		}catch(JSONException ex){
			ex.printStackTrace();
		}
		
		String path = null;
		if(postType != null){
			if(postType.equals(POST_TYPE_LOGIN)){	//��½
				path = "/login";
			}else if(postType.equals(POST_TYPE_REGISTER)){	//ע��
				path = "/reg";
				
			}else if(postType.equals(POST_TYPE_FETCH_USERINFO)){	//��ȡ�û���Ϣ
				path = "/fetchInfo";
				request.setAttribute("infotype", "userinfo");
			}else if(postType.equals(POST_TYPE_FETCH_IMAGEINFO)){	//��ȡͼ����Ϣ
				path = "/fetchInfo";
				request.setAttribute("infotype", "imageinfo");
			}else if(postType.equals(POST_TYPE_FETCH_VIDEOINFO)){		//��ȡ��Ƶ��Ϣ
				path = "/fetchInfo";
				request.setAttribute("infotype", "videoinfo");
				
			}else if(postType.equals(POST_TYPE_MODIFY_IMAGE)){		//�޸�ͼ����Ϣ
				path = "/modifyInfo";
				request.setAttribute("modifytype", "modifyimage");
				
			}else if(postType.equals(POST_TYPE_FETCH_IMAGE_IDS)){		//��ȡͼ��ID
				path = "/fetchids";
				request.setAttribute("idstype", "imageids");
			}else if(postType.equals(POST_TYPE_FETCH_VEDIO_IDS)){		//��ȡ��ƵID
				path = "/fetchids";
				request.setAttribute("idstype", "videoids");
				
			}else if(postType.equals(POST_TYPE_DELETE_IMAGE)){			//ɾ��ͼ��
				path = "/deletedata";
				request.setAttribute("deletetype", "deleteimage");
			}else if(postType.equals(POST_TYPE_UPLOAD_IMAGE)){		//�ϴ�ͼ��
				path = "/uploadfile";
				request.setAttribute("uploadtype", "uploadimage");
			}else if(postType.equals(POST_TYPE_UPLOAD_FILE)){			//�ϴ��ļ�
				
				
			}else if(postType.equals(POST_TYPE_DOWNLOAD_IMAGE)){		//����ͼ��
				path = "/downloadfile";
				request.setAttribute("downloadtype", "downloadimage");
			}else{																						//������Ƶ
				path = "/downloadfile";
				request.setAttribute("downloadtype", "downloadvideo");
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
