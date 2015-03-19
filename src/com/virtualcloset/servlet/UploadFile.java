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

public class UploadFile extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		HttpSession seesion = request.getSession();
		seesion.setMaxInactiveInterval(1800);

        request.setCharacterEncoding("gbk");
        //获得磁盘文件条目工厂。
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //获取文件上传需要保存的路径，upload文件夹需存在。
        String path = request.getSession().getServletContext().getRealPath("/upload");
        System.out.println("UploadFile.java path>>>"+path);
        //设置暂时存放文件的存储室，这个存储室可以和最终存储文件的文件夹不同。因为当文件很大的话会占用过多内存所以设置存储室。 
        String temp = request.getSession().getServletContext().getRealPath("/")+"temp";   //临时目录
        System.out.println("UploadFile.java path>>>"+temp);
        factory.setRepository(new File(temp));
        //设置缓存的大小，当上传文件的容量超过缓存时，就放到暂时存储室。
        factory.setSizeThreshold(1024*1024);
        //上传处理工具类（高水平API上传处理？）
        ServletFileUpload upload = new ServletFileUpload(factory);  
        upload.setSizeMax(100*1024*1024);	//100M
          
        try{  
            //调用 parseRequest（request）方法  获得上传文件 FileItem 的集合list 可实现多文件上传。
            List<FileItem> list = (List<FileItem>)upload.parseRequest(request);
            for(FileItem item:list){  
                //获取表单属性名字。  
                String name = item.getFieldName(); 
                //如果获取的表单信息是普通的文本信息。即通过页面表单形式传递来的字符串。  
                if(item.isFormField()){  
                    //获取用户具体输入的字符串，  
                    String value = item.getString();  
                    System.out.println("UploadFile.java value>>>"+value);
                    out.print(value);
                    request.setAttribute(name, value);  
                }  
                //如果传入的是非简单字符串，而是图片，音频，视频等二进制文件。
                else{   
                    //获取路径名  
                    String value = item.getName();  
                    //取到最后一个反斜杠。  
                    int start = value.lastIndexOf("\\");  
                    //截取上传文件的 字符串名字。+1是去掉反斜杠。  
                    String filename = value.substring(start+1);  
                    request.setAttribute(name, filename);  
                      
                    /*第三方提供的方法直接写到文件中。 
                     * item.write(new File(path,filename));*/  
                    //收到写到接收的文件中。  
                    OutputStream output = new FileOutputStream(new File(path,filename));  
                    InputStream in = item.getInputStream();  
                      
                    int length = 0;  
                    byte[] buf = new byte[1024];
                    System.out.println("获取文件总量的容量:"+ item.getSize());

                    while((length = in.read(buf))!=-1){
                    	output.write(buf,0,length);
                    }
                    in.close();
                    out.close();
                }
            }
        }catch(Exception e){
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
