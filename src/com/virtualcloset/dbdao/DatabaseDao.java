package com.virtualcloset.dbdao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.virtualcloset.model.ImageBean;

public class DatabaseDao {
	
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;

	private void initConnect(){
		conn = ConnDBC3P0.getInstance().getConn();
		stmt = ConnDBC3P0.createStmt(conn);
	}
	
	private void close(){
		ConnDBC3P0.close(conn, stmt, rs);
	}
	
	public Object query(String sql, ObjectMapper mapper){
		Object obj = null;
		try {
			initConnect();
			rs = ConnDBC3P0.exetQuery(stmt, sql);
			while(rs.next()){
				obj = mapper.mapping(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			close();
		}
		return obj;
	}
	
	public List<? extends Object> queryList(String sql, ObjectMapper mapper){
		Object obj = null;
		List<Object> list = new ArrayList<Object>();
		try {
			initConnect();
			rs = ConnDBC3P0.exetQuery(stmt, sql);
			while(rs.next()){
				obj = mapper.mapping(rs);
				list.add(obj);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return list;
	}
	
	public int update(String sql){
		int res = 0;
		try {
			initConnect();
			res = ConnDBC3P0.exetUpdate(stmt, sql);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return res;
	}
	
	
	public ArrayList<ImageBean> getAllImages(String username) throws SQLException{
		ArrayList<ImageBean> imageList = new ArrayList<ImageBean>();
		initConnect();
		String sql = "select * from images where username='" + username +"'";
		rs = ConnDBC3P0.exetQuery(stmt, sql);
		while(rs.next()){
			ImageBean image = new ImageBean();
			image.setImageId(rs.getInt("imageid"));
			image.setImageName(rs.getString("imagename"));
			image.setImageSize(rs.getInt("size"));
			String labels[] = rs.getString("labels").split("|");
			ArrayList<String> imageLabels = new ArrayList<String>();
			for(String label : labels){
				imageLabels.add(label);
			}
			image.setImageLabels(imageLabels);
			imageList.add(image);
		}
		close();
		return imageList;
	}
	
	public String getImageName(int imageId) throws SQLException{
		String imageName = null;
		initConnect();
		String sql = "select * from images where imageid="+imageId;
		rs = ConnDBC3P0.exetQuery(stmt, sql);
		while(rs.next()){
			imageName = rs.getString("imagename");
		}
		close();
		return imageName;
	}
}
