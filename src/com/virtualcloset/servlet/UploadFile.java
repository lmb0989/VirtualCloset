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
        //��ô����ļ���Ŀ������
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //��ȡ�ļ��ϴ���Ҫ�����·����upload�ļ�������ڡ�
        String path = request.getSession().getServletContext().getRealPath("/upload");
        System.out.println("UploadFile.java path>>>"+path);
        //������ʱ����ļ��Ĵ洢�ң�����洢�ҿ��Ժ����մ洢�ļ����ļ��в�ͬ����Ϊ���ļ��ܴ�Ļ���ռ�ù����ڴ��������ô洢�ҡ� 
        String temp = request.getSession().getServletContext().getRealPath("/")+"temp";   //��ʱĿ¼
        System.out.println("UploadFile.java path>>>"+temp);
        factory.setRepository(new File(temp));
        //���û���Ĵ�С�����ϴ��ļ���������������ʱ���ͷŵ���ʱ�洢�ҡ�
        factory.setSizeThreshold(1024*1024);
        //�ϴ��������ࣨ��ˮƽAPI�ϴ�������
        ServletFileUpload upload = new ServletFileUpload(factory);  
        upload.setSizeMax(100*1024*1024);	//100M
          
        try{  
            //���� parseRequest��request������  ����ϴ��ļ� FileItem �ļ���list ��ʵ�ֶ��ļ��ϴ���
            List<FileItem> list = (List<FileItem>)upload.parseRequest(request);
            for(FileItem item:list){  
                //��ȡ���������֡�  
                String name = item.getFieldName(); 
                //�����ȡ�ı���Ϣ����ͨ���ı���Ϣ����ͨ��ҳ�����ʽ���������ַ�����  
                if(item.isFormField()){  
                    //��ȡ�û�����������ַ�����  
                    String value = item.getString();  
                    System.out.println("UploadFile.java value>>>"+value);
                    out.print(value);
                    request.setAttribute(name, value);  
                }  
                //���������ǷǼ��ַ���������ͼƬ����Ƶ����Ƶ�ȶ������ļ���
                else{   
                    //��ȡ·����  
                    String value = item.getName();  
                    //ȡ�����һ����б�ܡ�  
                    int start = value.lastIndexOf("\\");  
                    //��ȡ�ϴ��ļ��� �ַ������֡�+1��ȥ����б�ܡ�  
                    String filename = value.substring(start+1);  
                    request.setAttribute(name, filename);  
                      
                    /*�������ṩ�ķ���ֱ��д���ļ��С� 
                     * item.write(new File(path,filename));*/  
                    //�յ�д�����յ��ļ��С�  
                    OutputStream output = new FileOutputStream(new File(path,filename));  
                    InputStream in = item.getInputStream();  
                      
                    int length = 0;  
                    byte[] buf = new byte[1024];
                    System.out.println("��ȡ�ļ�����������:"+ item.getSize());

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
