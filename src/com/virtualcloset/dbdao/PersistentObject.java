package com.virtualcloset.dbdao;

public interface PersistentObject {
	
	public static final int REG_MESSAGE_REGSUCCESS 	= 0;
	public static final int REG_MESSAGE_FAILED     	= 3;
	public static final int REG_MESSAGE_USEREXIST   = 5;
	public static final int REG_MESSAGE_EMAILEXIST  = 6;
	public static final int REG_MESSAGE_PHONEEXIST 	= 7;
	
	public static final int LOGIN_MESSAGE_LOGINSUCCESS = 0;
	public static final int LOGIN_MESSAGE_USERNOTEXIST  = 1;
	public static final int LOGIN_MESSAGE_PASSWRONG     = 2;
	
	public int create();
	
	public void update();
	
	public int delete();
	
	public Object query();
}
