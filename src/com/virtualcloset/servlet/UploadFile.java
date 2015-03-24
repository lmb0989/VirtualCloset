package com.virtualcloset.servlet;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.virtualcloset.model.ImageBean;

public class UploadFile extends HttpServlet {
	
	ImageBean image = new ImageBean();
	
	private static final int UPLOAD_MESSAGE_SUCCESS = 0;
	private static final int UPLOAD_MESSAGE_FAIL = 1;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		HttpSession seesion = request.getSession();
		seesion.setMaxInactiveInterval(1800);

        request.setCharacterEncoding("utf-8");
        DiskFileItemFactory factory = new DiskFileItemFactory();
        String path = request.getSession().getServletContext().getRealPath("/upload");
        String temp = request.getSession().getServletContext().getRealPath("/")+"temp";   //临时目录
        factory.setRepository(new File(temp));
        factory.setSizeThreshold(1024*1024);
        ServletFileUpload upload = new ServletFileUpload(factory);  
        upload.setSizeMax(100*1024*1024);	//100M
        
        try{
            List<FileItem> list = (List<FileItem>)upload.parseRequest(request);
            for(FileItem item:list){
                if(item.isFormField()){
                    String strJson = item.getString();
                    image.setImageBean(strJson);
                    System.out.println("UploadFile.java strJson>>>"+strJson);
                    System.out.println("UploadFile.java image.imageName>>>"+image.imageName);
                    System.out.println("UploadFile.java image.userName>>>"+image.userName);
                    System.out.println("UploadFile.java image.type>>>"+image.type);
                    System.out.println("UploadFile.java image.style>>>"+image.style);
                    System.out.println("UploadFile.java image.season>>>"+image.season);
                }  
                else{   
                	image.size = (int)item.getSize();
                    String value = item.getName();
                    int start = value.lastIndexOf("\\");  
                    String uploadFileName = value.substring(start+1);
                    image.imageName = uploadFileName;
                      
                    /*第三方提供的方法直接写到文件中。 
                     * item.write(new File(path,filename));*/  
                    //收到写到接收的文件中
                    String fileName = System.currentTimeMillis() + "-" + uploadFileName;
                    image.fileName = fileName;
                    OutputStream output = new FileOutputStream(new File(path,fileName));  
                    InputStream in = item.getInputStream();  
                      
                    int length = 0;  
                    byte[] buf = new byte[1024];
                    while((length = in.read(buf))!=-1){
                    	output.write(buf,0,length);
                    }
                    in.close();
                }
            }
        }catch(Exception e){
            e.printStackTrace(); 
        }
        int result = image.create();
        boolean isFileExist = (new File(path, image.fileName)).exists();
        if(result>0 && isFileExist){
        	out.print(image.createMessage(UPLOAD_MESSAGE_SUCCESS));
        }else{
        	out.print(image.createMessage(UPLOAD_MESSAGE_FAIL));
        }
        
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
