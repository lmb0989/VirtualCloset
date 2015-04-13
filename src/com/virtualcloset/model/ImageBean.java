package com.virtualcloset.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.virtualcloset.dbdao.DatabaseDao;
import com.virtualcloset.dbdao.ObjectMapper;
import com.virtualcloset.dbdao.PersistentObject;
import com.virtualcloset.util.JSONUtil;
import com.virtualcloset.util.StringUtil;

public class ImageBean implements PersistentObject, ObjectMapper {
	
    private static final String JSON_KEY_IMAGEID = "imageid";
    private static final String JSON_KEY_USERNAME = "username";
    private static final String JSON_KEY_IMAGENAME = "imagename";
    private static final String JSON_KEY_SIZE = "size";
    private static final String JSON_KEY_STYLE = "style";
    private static final String JSON_KEY_SEASON = "season";
    private static final String JSON_KEY_TYPE = "type";
    private static final String JSON_KEY_SITUATION = "situation";
    private static final String JSON_KEY_VIDEOIDS = "videoids";
	
	public int imageId;
	public String userName = null;
	public String imageName = "";
	public int size;
	public String style = "";			//风格
	public String season = "";			//适合季节
	public String type = "";			//类型
	public String situation = "";		//适合场合
	public ArrayList<Integer> videoIDS;
	public String fileName = "";
	public String afterDeal = "";
//	public String labels = "";
	
	private static DatabaseDao db= new DatabaseDao();
	
	public ImageBean(){ }
	public ImageBean(String userName, String imageName, 
			int size, String style, String season, String type, String situation, ArrayList<Integer> videoIDS){
		this.userName = userName;
		this.imageName = imageName;
		this.size = size;
		this.style = style;
		this.season = season;
		this.type = type;
		this.situation = situation;
		this.videoIDS = videoIDS;
	}
	
	public ImageBean(String strJSON) throws JSONException{
		this((JSONObject)(new JSONTokener(strJSON)).nextValue());
	}
	
	public ImageBean(JSONObject jobj){
		this.imageId = JSONUtil.getInt(jobj, JSON_KEY_IMAGEID);
		this.userName = JSONUtil.getString(jobj, JSON_KEY_USERNAME);
		this.imageName = JSONUtil.getString(jobj, JSON_KEY_IMAGENAME);
		this.size = JSONUtil.getInt(jobj, JSON_KEY_SIZE);
		this.style = JSONUtil.getString(jobj, JSON_KEY_STYLE);
		this.season = JSONUtil.getString(jobj, JSON_KEY_SEASON);
		this.type = JSONUtil.getString(jobj, JSON_KEY_TYPE);
		this.situation = JSONUtil.getString(jobj, JSON_KEY_SITUATION);
		this.videoIDS = StringUtil.string2List(JSONUtil.getString(jobj, JSON_KEY_VIDEOIDS), "v");
	}
	
	public int create() {
		StringBuilder sb = new StringBuilder();
		sb.append("insert into images values(");
		sb.append("NULL");
		sb.append(",'").append(this.userName).append("'");
		sb.append(",'").append(this.imageName).append("'");
		sb.append(",").append(this.size);
		sb.append(",'").append(this.style).append("'");
		sb.append(",'").append(this.season).append("'");
		sb.append(",'").append(this.type).append("'");
		sb.append(",'").append(this.situation).append("'");
		sb.append(",'").append(StringUtil.list2String(videoIDS, "v")).append("'");
		sb.append(",'").append(this.fileName).append("'");
		sb.append(",'").append(this.afterDeal).append("'");
		sb.append(")");
		String sql = sb.toString();
		int result = db.update(sql);
		System.out.println("sql: "+sql);
		return result;
	}
	
	public int delete() {
		String sql = "delete from images where imageid="+imageId;
		return db.update(sql);
	}
	
	public ImageBean query() {
		if(this.imageId == 0) return null;
		String sql = "select * from images where imageid="+imageId;
		return (ImageBean)db.query(sql, new ImageBean());
	}
	
	@SuppressWarnings("unchecked")
	public List<ImageBean> getUserAllImage(){
		String sql = "select * from images where username='"+userName+"'";
		return (List<ImageBean>)db.queryList(sql, new ImageBean());
	}
	
	public int update(String key, String value) {
		String sql = "update images set "+ key +"='"+ value +"' where imageid="+ imageId;
		return db.update(sql);
	}
	
	public void update() {
		StringBuilder sb = new StringBuilder();
		sb.append("update images set ");
		sb.append("username='").append(this.userName).append("'");
		sb.append(",").append("imagename='").append(this.imageName).append("'");
		sb.append(",").append("size'").append(this.size);
		sb.append(",").append("style='").append(this.style).append("'");
		sb.append(",").append("season='").append(this.season).append("'");
		sb.append(",").append("type='").append(this.type).append("'");
		sb.append(",").append("situation='").append(this.situation).append("'");
		sb.append(",").append("videoidS='").append(StringUtil.list2String(this.videoIDS, "v")).append("'");
		sb.append(" where imageid=").append(imageId);
		String sql = sb.toString();
		System.out.println("sql= "+sql);
		db.update(sql);
	}
	
	public JSONObject createMessage(int message){
		try{
			JSONObject jobj = new JSONObject();
			jobj.put("username", this.userName);
			jobj.put("message", message);
			return jobj;
		}catch(JSONException ex){
			ex.printStackTrace();
			return null;
		}
	}
	
	public JSONObject getImageInfo(){
		ImageBean image = query();
		JSONObject jsonObj = new JSONObject();
		try{
			jsonObj.put("imageid", image.imageId);
			jsonObj.put("imagename", image.imageName);
			jsonObj.put("username", image.userName);
			jsonObj.put("season", image.season);
			jsonObj.put("size", image.size);
			jsonObj.put("style", image.style);
			jsonObj.put("type", image.type);
			jsonObj.put("situation", image.situation);
			jsonObj.put("videoidS", StringUtil.list2String(image.videoIDS, "v"));
		}catch(JSONException e){ }
		return jsonObj;
	}
	
	public ObjectMapper mapping(ResultSet rs) {
		try {
			this.imageId = rs.getInt("imageid");
        	this.userName = rs.getString("username");
        	this.imageName = rs.getString("imagename");
        	this.season = rs.getString("season");
        	this.size = rs.getInt("size");
        	this.style = rs.getString("style");
        	this.type = rs.getString("type");
        	this.situation = rs.getString("situation");
        	this.videoIDS = StringUtil.string2List(rs.getString("videoidS"), "v");
        	this.fileName = rs.getString("filename");
        	this.afterDeal = rs.getString("afterdeal");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	public ImageBean clone(){
		ImageBean image = new ImageBean();
		image.imageId = this.imageId;
		image.imageName = this.imageName;
		image.userName = this.userName;
		image.season = this.season;
		image.size = this.size;
		image.style = this.style;
		image.type = this.style;
		image.situation = this.situation;
		image.videoIDS = this.videoIDS;
		image.fileName = this.fileName;
		image.afterDeal = this.afterDeal;
		return image;
	}
}
