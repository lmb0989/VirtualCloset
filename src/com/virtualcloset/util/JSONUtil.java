package com.virtualcloset.util;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtil {

	public static String getString(JSONObject jobj, String key){
		if(jobj.has(key)){
			try {
				return jobj.getString(key);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	
	public static int getInt(JSONObject jobj, String key){
		if(jobj.has(key)){
			try {
				return jobj.getInt(key);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return 0;
	}
}
