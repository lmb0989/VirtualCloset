package com.virtualcloset.util;

import com.virtualcloset.config.UserConfig;

public class UserUtil {
	
	public static final int TYPE_USERNAME  = 1;
	public static final int TYPE_USEREMAIL = 2;
	public static final int TYPE_USERPHONE = 3;
	
	public static int getUserType(String user){
//		String emailRegex = "[a-zA-Z0-9_]{6,12}+@[a-zA-Z]+(\\.[a-zA-Z]+){1,3}";
		String emailRegex = "[\\p{Alnum},_,.]+@[\\w+\\.]+\\p{Alpha}{2,3}";
		String phoneRegex = "1((3[0-9])||(5[0-3])||(5[5-9])||(8[0,5-9]))\\d{8}";
		if(user.matches(emailRegex)){
			return TYPE_USEREMAIL;
		} else if (user.matches(phoneRegex)){
			return TYPE_USERPHONE;
		}
		return TYPE_USERNAME;
	}
}
