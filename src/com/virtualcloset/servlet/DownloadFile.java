package com.virtualcloset.servlet;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;

import com.virtualcloset.config.DBConfig;
import com.virtualcloset.model.ImageBean;
import com.virtualcloset.model.VideoBean;

public class DownloadFile extends HttpServlet {

	String requestJson = "";
	String fileName = "";
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String downloadType = (String)request.getAttribute("downloadtype");
		requestJson = request.getParameter("requestJson");
		System.out.println("DownloadFile  downloadType>>>"+downloadType);
		if(downloadType.equals("downloadimage")){
			setImageFileName();
		}else{
			setVideoFileName();
		}
		sendFile(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}
	
	public void setImageFileName(){
		try {
			ImageBean image = new ImageBean(requestJson);
			System.out.println("DownloadFile  imageid:::"+image.imageId);
			System.out.println("DownloadFile  userName:::"+image.userName);
			
			image = image.query();
			if(image != null){
				fileName = DBConfig.imageLocation + "/" + image.fileName;
				System.out.println("DownloadFile  filename:::"+fileName);
			}
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
	}
	
	public void setVideoFileName(){
		try {
			VideoBean video = new VideoBean(requestJson);
			System.out.println("DownloadFile  videoid:::"+video.videoId);
			System.out.println("DownloadFile  userName:::"+video.userName);
			
			video = video.query();
			if(video != null){
				fileName = DBConfig.vedioLocation + "/" + video.fileName;
				System.out.println("DownloadFile  filename:::"+fileName);
			}
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
	}

	public void sendFile(HttpServletRequest request, HttpServletResponse response){
		try {
//			String fileName ="Screenshot_2015-03-04-10-46-08.png";
			response.setContentType("text/html");
			response.setHeader("Location",fileName);
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
			OutputStream outputStream = response.getOutputStream();
			InputStream inputStream = new FileInputStream(fileName);
			byte[] buffer = new byte[1024];
			int i = -1;
			while ((i = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, i);
			}
			outputStream.flush();
			outputStream.close();
		  } catch(FileNotFoundException e){
			  e.printStackTrace();
			  System.out.println("没有找到您要的文件");
		  } catch(Exception e){
			  e.printStackTrace();
			  System.out.println("系统错误，请及时与管理员联系");
		  }
	}
}
