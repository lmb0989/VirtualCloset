package com.virtualcloset.util;

import java.util.ArrayList;

public class StringUtil {
	
	public static ArrayList<Integer> String2List(String str, String regex){
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(String s : str.split(regex)){
			list.add(Integer.parseInt(s));
		}
		return list;
	}
}
