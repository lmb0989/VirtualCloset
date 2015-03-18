package org.json;

import com.virtualcloset.model.UserBean;

public class JSONHander {

	public UserBean getLoginUser(String request){
		try{
			JSONTokener jsonParser = new JSONTokener(request);
			JSONObject req = (JSONObject) jsonParser.nextValue();
			
			UserBean user = new UserBean();
			user.setUserName(req.getString("username"));  
			user.setPassword(req.getString("password"));  
			return user;
		}catch(JSONException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public String getUserName(String request){
		try{
			JSONTokener jsonParser = new JSONTokener(request);
			JSONObject req = (JSONObject) jsonParser.nextValue();
			return req.getString("username");
		}catch(JSONException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public String getUserInfo(UserBean user){
		try {
			if(user==null) return null;
			JSONObject json = new JSONObject();
			json.put("username", user.getUserName());
			json.put("email", user.getEmail());
			json.put("phone", user.getPhone());
			json.put("sex", user.getSex());
			System.out.println("getUserInfo  age>>>"+user.getAge());
			json.put("age", user.getAge());
			json.put("job", user.getJob());
			return json.toString();
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public UserBean getRegUser(String request){
		try{
			JSONTokener jsonParser = new JSONTokener(request);
			JSONObject req = (JSONObject) jsonParser.nextValue();

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

			return req.getString("type");  
		}catch(JSONException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public String createrLoginOrRegMessage(String userName, int message){
		try{
			JSONObject user = new JSONObject();
			user.put("username", userName);
			user.put("message", message);
			return user.toString();
		}catch(JSONException ex){
			ex.printStackTrace();
			return null;
		}
	}
}
