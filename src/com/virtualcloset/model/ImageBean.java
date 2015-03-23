package com.virtualcloset.model;

import java.sql.ResultSet;

import com.virtualcloset.dbdao.ObjectMapper;

public class ImageBean implements PersistentObject, ObjectMapper {
	
	public int imageId;
	public String userName = null;
	public String imageName;
	public int size;
	public String style = "";
	public String season = "";
	public String type = "";
	
	
	
	public int create() {
		return 0;
	}
	public void delete() {
		
	}
	public Object query() {
		return null;
	}
	public void update() {
		
	}
	public ObjectMapper mapping(ResultSet rs) {
		return null;
	}
	
}
