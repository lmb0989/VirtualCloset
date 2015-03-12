package org.json;

import com.virtualcloset.model.UserBean;

public class JSONHander {

	public UserBean getLoginUser(String request){
		try{
			JSONTokener jsonParser = new JSONTokener(request);  
			JSONObject req = (JSONObject) jsonParser.nextValue();  
			// 接下来的就是JSON对象的操作了  
			UserBean user = new UserBean();
			user.setUserName(req.getString("username"));  
			user.setPassword(req.getString("password"));  
			return user;
		}catch(JSONException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public UserBean getRegUser(String request){
		try{
			JSONTokener jsonParser = new JSONTokener(request);  
			JSONObject req = (JSONObject) jsonParser.nextValue();  
			// 接下来的就是JSON对象的操作了  
			UserBean user = new UserBean();
			user.setUserName(req.getString("username"));  
			user.setPassword(req.getString("password")); 
			if(req.getString("email") != null) user.setEmail(req.getString("email"));  
			if(req.getString("phone") != null) user.setPhone(req.getString("phone"));  
			if(req.getString("sex") != null) user.setSex(req.getString("sex"));  
			if(req.getInt("age") >0 ) user.setAge(req.getInt("age")); 
			if(req.getString("job") != null) user.setJob(req.getString("job"));  
			
			return user;
		}catch(JSONException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public String getPostType(String request){
		try{
			JSONTokener jsonParser = new JSONTokener(request);  
			JSONObject req = (JSONObject) jsonParser.nextValue();  
			// 接下来的就是JSON对象的操作了  
			return req.getString("type");  
		}catch(JSONException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public String createrLoginMessage(int message){
		try{
			JSONObject user = new JSONObject();  
			user.put("message", message);
			return user.toString();
		}catch(JSONException ex){
			ex.printStackTrace();
			return null;
		}
	}
}
