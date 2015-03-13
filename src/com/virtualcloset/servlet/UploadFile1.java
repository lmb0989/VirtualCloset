package com.virtualcloset.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;

public class UploadFile1 extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

        String temp=request.getSession().getServletContext().getRealPath("/")+"temp";   //��ʱĿ¼
        System.out.println("temp="+temp);
        String loadpath=request.getSession().getServletContext().getRealPath("/")+"Image"; //�ϴ��ļ����Ŀ¼
        System.out.println("loadpath="+loadpath);
        DiskFileUpload fu =new DiskFileUpload();
        fu.setSizeMax(1*1024*1024);   // ���������û��ϴ��ļ���С,��λ:�ֽ�
        fu.setSizeThreshold(4096);   // �������ֻ�������ڴ��д洢������,��λ:�ֽ�
        fu.setRepositoryPath(temp); // ����һ���ļ���С����getSizeThreshold()��ֵʱ���ݴ����Ӳ�̵�Ŀ¼
       
        //��ʼ��ȡ�ϴ���Ϣ
		int index=0;
        List fileItems =null;
         try {
			 fileItems = fu.parseRequest(request);
			 System.out.println("fileItems="+fileItems);
         } catch (Exception e) {
        	 e.printStackTrace();
         }
             
        Iterator iter = fileItems.iterator(); // ���δ���ÿ���ϴ����ļ�
		while (iter.hasNext()) {
            FileItem item = (FileItem)iter.next();// �������������ļ�������б���Ϣ
			if (!item.isFormField()) {
                String name = item.getName();//��ȡ�ϴ��ļ���,����·��
                name=name.substring(name.lastIndexOf("\\")+1);//��ȫ·������ȡ�ļ���
				long size = item.getSize();
                if((name==null||name.equals("")) && size==0)
                      continue;
                int point = name.indexOf(".");
                name=(new Date()).getTime()+name.substring(point,name.length())+index;
                index++;
                File fNew=new File(loadpath, name);
                try {
                	item.write(fNew);
                	} catch (Exception e) {
                		e.printStackTrace();
                	}
				}
			else//ȡ�������ļ�������б���Ϣ
            {
                String fieldvalue = item.getString();
                //�����������ӦдΪ��(תΪUTF-8����)
                //String fieldvalue = new String(item.getString().getBytes(),"UTF-8");
            }
        }
        String text1="11";
        response.sendRedirect("result.jsp?text1="+ text1);
		

		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
