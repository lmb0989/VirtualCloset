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
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.virtualcloset.config.DBConfig;
import com.virtualcloset.model.ImageBean;
import com.virtualcloset.model.VideoBean;

public class UploadFile extends HttpServlet {
	
	private static final int UPLOAD_MESSAGE_SUCCESS = 0;
	private static final int UPLOAD_MESSAGE_FAIL = 1;

	String strJson = "";
	String uploadFileName = "";
	String serverFileName = "";
	int size = 0;
	String path = DBConfig.imageLocation;;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		

		System.out.println("-------------上传文件------------------");
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		HttpSession seesion = request.getSession();
		seesion.setMaxInactiveInterval(1800);

        request.setCharacterEncoding("utf-8");
        DiskFileItemFactory factory = new DiskFileItemFactory();
        String temp = request.getSession().getServletContext().getRealPath("/")+"temp";   //临时目录
        factory.setRepository(new File(temp));
        factory.setSizeThreshold(1024*1024);
        ServletFileUpload upload = new ServletFileUpload(factory);  
        upload.setSizeMax(100*1024*1024);	//100M
        
        try{
            List<FileItem> list = (List<FileItem>)upload.parseRequest(request);
            for(FileItem item:list){
                if(item.isFormField()){
                    strJson = item.getString();
                    System.out.println("UploadFile strJson:: "+strJson);
                }  
                else{   
                	size = (int)item.getSize();
                    String value = item.getName();
                    int start = value.lastIndexOf("\\");  
                    uploadFileName = value.substring(start+1);
                      
                    /*第三方提供的方法直接写到文件中。 
                     * item.write(new File(path,filename));*/  
                    //收到写到接收的文件中
                    serverFileName = System.currentTimeMillis() + "-" + uploadFileName;
                    System.out.println("UploadFile serverFileName:: "+serverFileName);
                    OutputStream output = new FileOutputStream(new File(path,serverFileName));  
                    InputStream in = item.getInputStream();  
                      
                    int length = 0; 
                    byte[] buf = new byte[1024];
                    while((length = in.read(buf))!=-1){
                    	output.write(buf,0,length);
                    }
                    in.close();
                }
            }
            System.out.println("循环结束");
        }catch(Exception e){
            e.printStackTrace(); 
        }
        
        JSONObject messageJson = null;
        
        try{
        	JSONTokener token = new JSONTokener(strJson);
        	JSONObject jobj = (JSONObject)token.nextValue();
        	String uploadType = jobj.getString("post_type");
        	System.out.println("UploadFile uploadType:: "+uploadType);
        	if(uploadType.equals("upload_image")){
        		messageJson = uploadImage(jobj);
        		System.out.println("UploadFile messageJson:: "+messageJson.toString());
        	}else{
        		messageJson = uploadVideo(jobj);
        		System.out.println("UploadFile messageJson:: "+messageJson.toString());
        	}
        }catch(JSONException e){ 
        	e.printStackTrace();
        }
        
        out.print(messageJson.toString());
		out.flush();
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public JSONObject uploadImage(JSONObject jobj){
		ImageBean image = new ImageBean(jobj);
    	image.size = size;
    	image.imageName = uploadFileName;
    	image.fileName = serverFileName;
    	System.out.println("UploadFile fileName:: "+image.fileName);
    	int result = image.create();
    	boolean isFileExist = (new File(path, image.fileName)).exists();
    	System.out.println("UploadFile result::: "+result);
    	System.out.println("UploadFile isFileExist:: "+isFileExist);
    	if(result>0 && isFileExist){
    		return image.createMessage(UPLOAD_MESSAGE_SUCCESS);
    	}else{
    		return image.createMessage(UPLOAD_MESSAGE_FAIL);
    	}
	}
	
	public JSONObject uploadVideo(JSONObject jobj){
		VideoBean video = new VideoBean(jobj);
		video.videoName = uploadFileName;
		video.fileName = serverFileName;
		int result = video.create();
		boolean isFileExist = (new File(path, video.fileName)).exists();
		if(result>0 && isFileExist){
			return video.createMessage(UPLOAD_MESSAGE_SUCCESS);
		}else{
			return video.createMessage(UPLOAD_MESSAGE_FAIL);
		}
	}
	
}
