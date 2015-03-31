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

public class VideoBean implements ObjectMapper, PersistentObject{
	
    private static final String JSON_KEY_VIDEOID = "videoid";
    private static final String JSON_KEY_USERNAME = "username";
    private static final String JSON_KEY_VIDEONAME = "videoname";
    private static final String JSON_KEY_STYLE = "style";
    private static final String JSON_KEY_SEASON = "season";
    private static final String JSON_KEY_TYPE = "type";
    private static final String JSON_KEY_IMAGEIDS = "imageids";
	
	public int videoId;
	public String userName = null;
	public String videoName = "";
	public String style = "";			//风格
	public String season = "";		//适合季节
	public String type = "";			//类型
	public ArrayList<Integer> imageIDS;
	public String fileName = "";
	
	private static DatabaseDao db= new DatabaseDao();
	
	public VideoBean(){ }
	public VideoBean(int videoId, String userName, String videoName, String style, String season, String type, String imageIDS){
		this(videoId, userName, videoName, style, season, type, StringUtil.string2List(imageIDS, "v" ));
	}
	
	public VideoBean(int videoId, String userName, String videoName, String style, String season, String type, ArrayList<Integer> imageIDS){
		this.videoId = videoId;
		this.userName = userName;
		this.videoName = videoName;
		this.style = style;
		this.season = season;
		this.type = type;
		this.imageIDS = imageIDS;
	}
	
	public VideoBean(String strJSON) throws JSONException{
		this((JSONObject)(new JSONTokener(strJSON)).nextValue());
	}
	
	public VideoBean(JSONObject jobj){
		this.videoId = JSONUtil.getInt(jobj, JSON_KEY_VIDEOID);
		this.userName = JSONUtil.getString(jobj, JSON_KEY_USERNAME);
		this.videoName = JSONUtil.getString(jobj, JSON_KEY_VIDEONAME);
		this.style = JSONUtil.getString(jobj, JSON_KEY_STYLE);
		this.season = JSONUtil.getString(jobj, JSON_KEY_SEASON);
		this.type = JSONUtil.getString(jobj, JSON_KEY_TYPE);
		this.imageIDS = StringUtil.string2List(JSONUtil.getString(jobj, JSON_KEY_IMAGEIDS), "v");
	}

	public int create() {
		StringBuilder sb = new StringBuilder();
		sb.append("insert into videos values(");
		int videoSize = getAllVideo().size();
		sb.append(videoSize + 1);
		sb.append(",'").append(this.userName).append("'");
		sb.append(",'").append(this.videoName).append("'");
		sb.append(",'").append(this.style).append("'");
		sb.append(",'").append(this.season).append("'");
		sb.append(",'").append(this.type).append("'");
		sb.append(",'").append(StringUtil.list2String(this.imageIDS, "v")).append("'");
		sb.append(",'").append(this.fileName).append("'");
		sb.append(")");
		String sql = sb.toString();
		int result = db.update(sql);
		System.out.println("sql: "+sql);
		return result;
	}
	
	public int delete() {
		String sql = "delete from videos where videoid="+this.videoId;
		return db.update(sql);
	}
	
	public VideoBean query() {
		String sql = "select * from videos where videoid="+this.videoId;
		return (VideoBean)db.query(sql, new VideoBean());
	}
	
	@SuppressWarnings("unchecked")
	public List<VideoBean> getUserAllVideo(){
		String sql = "select * from videos where username='"+this.userName+"'";
		return (List<VideoBean>)db.queryList(sql, new VideoBean());
	}
	
	@SuppressWarnings("unchecked")
	public static List<VideoBean> getAllVideo(){
		String sql = "select * from videos ";
		return (List<VideoBean>)db.queryList(sql, new VideoBean());
	}
	
	public void update(String key, String value) {
		String sql = "update videos set "+ key +"='"+ value +"' where videoid="+ videoId;
		db.update(sql);
	}
	
	public void update() {
		StringBuilder sb = new StringBuilder();
		sb.append("update videos set ");
		sb.append("username='").append(this.userName).append("'");
		sb.append(",").append("videoname='").append(this.videoName).append("'");
		sb.append(",").append("style='").append(this.style).append("'");
		sb.append(",").append("season='").append(this.season).append("'");
		sb.append(",").append("type='").append(this.type).append("'");
		sb.append(",").append("imageids='").append(StringUtil.list2String(this.imageIDS, "v")).append("'");
		sb.append(" where videoid=").append(videoId);
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
	
	public JSONObject getVideoInfo(){
		VideoBean video = query();
		JSONObject jsonObj = new JSONObject();
		try{
			jsonObj.put("videoid", video.videoId);
			jsonObj.put("videoname", video.videoName);
			jsonObj.put("username", video.userName);
			jsonObj.put("style", video.style);
			jsonObj.put("season", video.season);
			jsonObj.put("type", video.type);
			jsonObj.put("imageids", StringUtil.list2String(video.imageIDS, "v"));
		}catch(JSONException e){ }
		return jsonObj;
	}
	
	public ObjectMapper mapping(ResultSet rs) {
		try {
			this.videoId = rs.getInt("videoid");
        	this.userName = rs.getString("username");
        	this.videoName = rs.getString("videoname");
        	this.style = rs.getString("style");
        	this.season = rs.getString("season");
        	this.type = rs.getString("type");
        	this.imageIDS = StringUtil.string2List(rs.getString("imageids"), "v");
        	this.fileName = rs.getString("filename");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	public ObjectMapper clone(){
		VideoBean video = new VideoBean();
		video.videoId = this.videoId;
		video.videoName = this.videoName;
		video.userName = this.userName;
		video.style = this.style;
		video.season = this.season;
		video.type = this.type;
		video.imageIDS = this.imageIDS;
		video.fileName = this.fileName;
		return video;
	}
	
}
