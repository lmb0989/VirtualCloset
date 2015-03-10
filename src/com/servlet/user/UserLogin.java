package com.servlet.user;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserLogin extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		boolean flag = false;
		
		response.setContentType("text/html"); 
		response.setCharacterEncoding("gbk");
        PrintWriter out = response.getWriter(); 
        String userName = request.getParameter("user"); 
        String password  = request.getParameter("password");
        if(userName.equals("admin")&&password.equals("12345"))
        {
            flag = true;  
        }else{
        	flag = false;  
        }
        System.out.println("userName:"+userName+"    password:"+password);
        if(flag){
        	out.print("µÇÂ½³É¹¦");
        }else{
        	out.print("µÇÂ½Ê§°Ü");
        }
        out.flush();    
        out.close();   
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
