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


public class DownloadFile extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		  try {
			  	String downFilename ="Screenshot_2015-03-04-10-46-08.png";
			  	String filepath = request.getSession().getServletContext().getRealPath("/upload");
				response.setContentType("text/html");
				response.setHeader("Location",downFilename);
				response.setHeader("Content-Disposition", "attachment; filename=" + downFilename);
				OutputStream outputStream = response.getOutputStream();
				InputStream inputStream = new FileInputStream(filepath+"/"+downFilename);
				System.out.println("11");
				byte[] buffer = new byte[1024];
				int i = -1;
				System.out.println("22");
				while ((i = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, i);
				}
				System.out.println("33");
				outputStream.flush();
				outputStream.close();
				System.out.println("44");
		  } catch(FileNotFoundException e) {
			  e.printStackTrace();
			  System.out.println("没有找到您要的文件");
		  } catch(Exception e) {
			  e.printStackTrace();
			  System.out.println("系统错误，请及时与管理员联系");
		  }
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
