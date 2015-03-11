package com.virtualcloset.dbdao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.virtualcloset.config.UserConfig;
import com.virtualcloset.model.UserBean;

public class UserDao {
	
	private static Connection conn;
	private static Statement stmt;
	private static ResultSet rs;

	public static void initConnect(){
		conn = ConnDBC3P0.getInstance().getConn();
		stmt = ConnDBC3P0.createStmt(conn);
	}
	
	public static int login(UserBean user) throws SQLException{
		if(!isUserNameExist(user.getUserName())){
			return UserConfig.MESSAGE_USERNOTEXIST;
		}
		initConnect();
		String sql = "select * from user where username='"+user.getUserName()+"'";
		rs = ConnDBC3P0.exetQuery(stmt, sql);
		while(rs.next()){
			if(!rs.getString("password").equals(user.getPassword())){
				return UserConfig.MESSAGE_PASSWRONG;
			}
		}
		close();
		return UserConfig.MESSAGE_LOGINSUCCESS;
	}
	
	public static boolean isUserNameExist(String userName) throws SQLException{
		boolean isExist = false;
		initConnect();
		String sql = "select * from user where username='"+userName+"'";
		rs = ConnDBC3P0.exetQuery(stmt, sql);
		while(rs.next()){
			isExist = true;
		}
		close();
		return isExist;
	}
	
	public static boolean isUserEmailExist(String userEmail) throws SQLException {
		ResultSet rs = getAllUser();
		while(rs.next()){
			if(rs.getString("email").equals(userEmail)){
				return true;
			}
		}
		close();
		return false;
	}
	
	public static boolean isUserPhoneExist(int userPhone) throws SQLException {
		ResultSet rs = getAllUser();
		while(rs.next()){
			if(rs.getInt("phone") == userPhone){
				return true;
			}
		}
		close();
		return false;
	}
	
	public static String getUserName(String userEmailORPhone, String type) throws SQLException{
		ResultSet rs = getAllUser();
		while(rs.next()){
			if(rs.getString(type).equals(userEmailORPhone)){
				return rs.getString("username");
			}
		}
		close();
		return null;
	}
	
	public static ResultSet getAllUser(){
		initConnect();
		String sql = "select * from user";
		rs = ConnDBC3P0.exetQuery(stmt, sql);
		return rs;
	}
	
	public static void close(){
		ConnDBC3P0.close(conn, stmt, rs);
	}
}
