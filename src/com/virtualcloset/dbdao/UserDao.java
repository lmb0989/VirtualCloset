package com.virtualcloset.dbdao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.virtualcloset.config.UserConfig;
import com.virtualcloset.model.ImageBean;
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
			return UserConfig.LOGIN_MESSAGE_USERNOTEXIST;
		}
		initConnect();
		String sql = "select * from user where username='"+user.getUserName()+"'";
		rs = ConnDBC3P0.exetQuery(stmt, sql);
		while(rs.next()){
			if(!rs.getString("password").equals(user.getPassword())){
				return UserConfig.LOGIN_MESSAGE_PASSWRONG;
			}
		}
		close();
		return UserConfig.LOGIN_MESSAGE_LOGINSUCCESS;
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
	
	public static boolean isUserPhoneExist(String userPhone) throws SQLException {
		ResultSet rs = getAllUser();
		while(rs.next()){
			if(rs.getString("phone").equals(userPhone)){
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
	
	public static int regUser(UserBean user) throws SQLException{
		StringBuilder sb = new StringBuilder();
		sb.append("insert into user values ('").append(user.getUserName()).append("','").append(user.getPassword()).append("'");
		
		String email = user.getEmail();
		if(email==null || email.isEmpty()) email = "";
		if(isUserEmailExist(email)) return UserConfig.REG_MESSAGE_EMAILEXIST;
		sb.append(",'").append(email).append("'");
		
		String phone = user.getPhone();
		if(phone==null || phone.isEmpty()) phone = "";
		if(isUserPhoneExist(phone)) return UserConfig.REG_MESSAGE_PHONEEXIST;
		sb.append(",'").append(phone).append("'");
		
		String sex = user.getSex();
		if(sex==null || sex.isEmpty()) sex = "";
		sb.append(",'").append(sex).append("'");
		
		int age = user.getAge();
		if(age <= 0) age = 0;
		sb.append(",").append(age);
		
		String job = user.getJob();
		if(job==null || job.isEmpty()) job = "";
		sb.append(",'").append(job).append("'");
		
		sb.append(")");
		String sql = sb.toString();
		System.out.println("register sql>>>" +sql);
		
		initConnect();
		int res = ConnDBC3P0.exetUpdate(stmt, sql);
		close();
		return res==0 ? UserConfig.REG_MESSAGE_REGSUCCESS : UserConfig.REG_MESSAGE_FAILED;
	}
	
	public static UserBean getUer(String userName) throws SQLException{
		UserBean user = new UserBean();
		initConnect();
		String sql = "select * from user where username='"+userName+"'";
		rs = ConnDBC3P0.exetQuery(stmt, sql);
		while(rs.next()){
			user.setUserName(userName);
			user.setEmail(rs.getString("email"));
			user.setPhone(rs.getString("phone"));
			user.setSex(rs.getString("sex"));
			System.out.println("age>>>"+rs.getInt("age"));
			user.setAge(rs.getInt("age"));
			user.setJob(rs.getString("job"));
		}
		close();
		return user;
	}
	
	public static ArrayList getAllImages(String username) throws SQLException{
		ArrayList imageList = new ArrayList<ImageBean>();
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
	
	public static void close(){
		ConnDBC3P0.close(conn, stmt, rs);
	}
}
