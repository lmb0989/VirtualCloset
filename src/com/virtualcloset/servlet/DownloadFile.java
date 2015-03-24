package com.virtualcloset.servlet;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;

import com.virtualcloset.dbdao.DatabaseDao;
import com.virtualcloset.model.ImageBean;

public class DownloadFile extends HttpServlet {

	ImageBean image;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String fileName = null;
		String requestJson = request.getParameter("requestJson");
		try {
			image = new ImageBean(requestJson);
			image = image.query();
			if(image != null){
				fileName = image.fileName;
			}
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		
		try {
//			String fileName ="Screenshot_2015-03-04-10-46-08.png";
		  	String filepath = request.getSession().getServletContext().getRealPath("/upload");
			response.setContentType("text/html");
			response.setHeader("Location",fileName);
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
			OutputStream outputStream = response.getOutputStream();
			InputStream inputStream = new FileInputStream(filepath+"/"+fileName);
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

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
