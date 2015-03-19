package org.json;

import java.util.ArrayList;
import java.util.List;

import com.virtualcloset.model.ImageBean;
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
			json.put("bust", user.getBust());
			json.put("waist", user.getWaist());
			json.put("hips", user.getHip());
			json.put("height", user.getHeight());
			json.put("weight", user.getWeight());
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
			if(req.getString("bust") != null) user.setBust(req.getString("bust"));
			if(req.getString("waist") != null) user.setWaist(req.getString("waist"));
			if(req.getString("hips") != null) user.setHip(req.getString("hips"));
			if(req.getString("height") != null) user.setHeight(req.getString("height"));
			if(req.getString("weight") != null) user.setWeight(req.getString("weight"));
			
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
	
	public List<Integer> getClientAllImages(String request){
		try{
			JSONTokener jsonParser = new JSONTokener(request);
			JSONObject req = (JSONObject) jsonParser.nextValue();
			
			ArrayList<Integer> imageList = new ArrayList<Integer>();
			JSONArray imageArray = req.getJSONArray("imageIdList");
			for(int i=0; i<imageArray.length(); i++){
				imageList.add(imageArray.getInt(i));
			}
			return imageList;
		}catch(JSONException ex){
			ex.printStackTrace();
			return null;
		}
	}
	
	public String getNeedImage(List<ImageBean> imageList){
		try{
			JSONArray imageArray = new JSONArray();
			for(ImageBean image : imageList){
				JSONObject imageObject = new JSONObject();
				imageObject.put("imageId", image.getImageId());
				imageObject.put("imageName", image.getImageName());
				imageObject.put("imageSize", image.getImageSize());
				JSONArray imageLabels = new JSONArray();
				for(String label : image.getImageLabels()){
					imageLabels.put(label);
				}
				imageObject.put("imageLabels", imageLabels);
				imageArray.put(imageObject);
			}
			return imageArray.toString();
		}catch(JSONException ex){
			ex.printStackTrace();
			return null;
		}
	}
	
	public int getImageID(String request){
		try{
			JSONTokener jsonParser = new JSONTokener(request);
			JSONObject req = (JSONObject) jsonParser.nextValue();
			
			return req.getInt("imageId");
		}catch(JSONException ex){
			ex.printStackTrace();
			return -1;
		}
	}
}
