package com.virtualcloset.dbdao;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.virtualcloset.config.DBConfig;

public class ConnDBC3P0 {
	
	private ComboPooledDataSource cpds = null;
	
	private ConnDBC3P0(){
		cpds = new ComboPooledDataSource();
		cpds.setUser(DBConfig.username);
		cpds.setPassword(DBConfig.password);
		cpds.setJdbcUrl(DBConfig.url);
		try {
			cpds.setDriverClass(DBConfig.classname);
		} catch (PropertyVetoException e) {
			e.printStackTrace();
		}
		cpds.setInitialPoolSize(30);
		cpds.setMaxPoolSize(100);
		cpds.setMinPoolSize(10);
		
	}
	private static ConnDBC3P0 instance = null;
	public synchronized static ConnDBC3P0 getInstance(){
		if(instance == null){
			instance = new ConnDBC3P0();
		}
		return instance;
	}
	
	public Connection getConn(){
		Connection conn = null;
		try {
			conn = cpds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static Statement createStmt(Connection conn) {
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stmt;
	}
	
	public static ResultSet exetQuery(Statement stmt,String sql) {
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public static int exetUpdate(Statement stmt,String sql) {
		int res = 0;
		try {
			res = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public static void close(Connection conn) {
		try {
			if(conn != null) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(Statement stmt) {
		try {
			if(stmt != null) {
				stmt.close();
				stmt = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(ResultSet rs) {
		try {
			if(rs != null) {
				rs.close();
				rs = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(Connection conn,Statement stmt,ResultSet rs) {
			close(conn);
			close(stmt);
			close(rs);
	}
	
	public static void close(Connection conn,Statement stmt) {
			close(conn);
			close(stmt);
	}
	/*
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		for(int i=0;i<1000000;i++)
		{
			Connection conn = ConnDBC3P0.getInstance().getConn();
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		long end = System.currentTimeMillis();
		
		System.out.println("time used : " + (end - start));
	}*/
}
