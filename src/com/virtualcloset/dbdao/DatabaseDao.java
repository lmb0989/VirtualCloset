package com.virtualcloset.dbdao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
			System.out.println("rs==null ? "+rs==null);
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
				obj = mapper.mapping(rs).clone();
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
		initConnect();
		res = ConnDBC3P0.exetUpdate(stmt, sql);
		close();
		return res;
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
