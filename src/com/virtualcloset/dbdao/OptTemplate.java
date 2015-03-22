package com.virtualcloset.dbdao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OptTemplate {  
	
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	
	private void initConnect(){
		conn = (Connection) ConnDBC3P0.getInstance().getConn();
		stmt = ConnDBC3P0.createStmt(conn);
	}
	
	private void close(){
		ConnDBC3P0.close(conn, stmt, rs);
	}
	
//    public Object find(String sql,Object[] obj,ObjectMapper mapper){  
//        Object o=null;  
//        try{  
//        	initConnect();
//            pstmt=conn.prepareStatement(sql);
//            for(int i=0;i<obj.length;i++){  
//                pstmt.setObject(i+1, obj[i]);
//                ResultSet rs=pstmt.executeQuery();
//                if(rs.next()){  
//                    o=mapper.mapping(rs);
//                }  
//            }
//            }catch(Exception ex){  
//                ex.printStackTrace();  
//            }finally{  
//                try{  
//                    pstmt.close();  
//                    conn.close();  
//                }catch(SQLException ex){  
//                    ex.printStackTrace();  
//                }  
//            }  
//            return o;  
//        }  
  
//    public List<? extends Object> query(String sql,Object[] obj,ObjectMapper mapper){  
//        Object o=null;  
//        List<Object> list=new ArrayList<Object>();  
//        Connection conn=null;  
//        PreparedStatement pstmt=null;  
//        try{  
//            conn=DBConnection.getConn();  
//            pstmt=conn.prepareStatement(sql);  
//            for(int i=0;i<obj.length;i++){  
//                pstmt.setObject(i+1, obj[i]);  
//            }  
//                ResultSet rs=pstmt.executeQuery();  
//  
//                while(rs.next()){  
//                      
//                    o=mapper.mapping(rs);  
//                    list.add(o);  
//                }  
//              
//              
//        }catch(SQLException ex){  
//            ex.printStackTrace();  
//        }finally{  
//            try{  
//                pstmt.close();  
//                conn.close();  
//            }catch(SQLException ex){  
//                ex.printStackTrace();  
//            }  
//        }  
//        return list;  
//    }  
    
//    public boolean update(String sql,Object[] obj,boolean isGenerateKey){  
//        Connection conn=null;  
//        PreparedStatement pstmt=null;  
//        boolean bFlag=false;  
//        try{  
//            conn=DBConnection.getConn();  
//            pstmt=isGenerateKey ? conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS):conn.prepareStatement(sql);  
//            for(int i=0;i<obj.length;i++){  
//                pstmt.setObject(i+1, obj[i]);  
//            }  
//            conn.setAutoCommit(false);  
//            int i=pstmt.executeUpdate();  
//            conn.commit();  
//            if(i>0)  
//                bFlag=true;  
//        }catch(SQLException ex){  
//            ex.printStackTrace();  
//        }finally{  
//            try{  
//                conn.close();  
//                pstmt.close();  
//                  
//            }catch(SQLException ex){  
//                ex.printStackTrace();  
//            }  
//        }  
//        return bFlag;  
//    }  
}  