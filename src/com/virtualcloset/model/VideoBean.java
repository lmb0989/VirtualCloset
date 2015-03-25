package com.virtualcloset.model;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.virtualcloset.dbdao.ObjectMapper;
import com.virtualcloset.dbdao.PersistentObject;
import com.virtualcloset.util.StringUtil;

public class VideoBean implements ObjectMapper, PersistentObject{
	
	public int videoId;
	public String userName;
	public String videoName;
	public ArrayList<Integer> imageIDS;
	public String fileName;
	
	public VideoBean(){ }
	public VideoBean(int videoId, String userName, String videoName, String imageIDS, String fileName){
		this(videoId, userName, videoName, StringUtil.String2List(imageIDS, "|" ), fileName);
	}
	
	public VideoBean(int videoId, String userName, String videoName, ArrayList<Integer> imageIDS, String fileName){
		this.videoId = videoId;
		this.userName = userName;
		this.videoName = videoName;
		this.imageIDS = imageIDS;
		this.fileName = fileName;
	}

	public int create() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public void delete() {
		// TODO Auto-generated method stub
		
	}
	
	public Object query() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	public ObjectMapper mapping(ResultSet rs) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ObjectMapper clone(){
		return null;
	}
	
}
