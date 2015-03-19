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

import org.json.JSONHander;

import com.virtualcloset.dbdao.DatabaseDao;
import com.virtualcloset.model.ImageBean;

public class ListNeedResource extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		String json = request.getParameter("requestJson");
        JSONHander hander = new JSONHander();
        String userName = hander.getUserName(json);
        
        List<ImageBean> result = new ArrayList<ImageBean>();
        try {
        	List<ImageBean> serverImageList = (new DatabaseDao()).getAllImages(userName);
			List<Integer>   clientImageList = hander.getClientAllImages(json);
			for(ImageBean image : serverImageList){
				if(!clientImageList.contains(image.getImageId())){
					result.add(image);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		out.print(hander.getNeedImage(result));
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
