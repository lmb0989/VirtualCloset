package com.virtualcloset.util;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {
	
	public static ArrayList<Integer> string2List(String str, String regex){
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(String s : str.split(regex)){
			System.out.println(s);
			if(!s.isEmpty()){
				list.add(Integer.parseInt(s));
			}
		}
		return list;
	}
	
	public static String list2String(ArrayList<Integer> videoIDS, String regex){
		String result = "";
		for(Integer o : videoIDS){
			result = o.toString() + regex + result;
		}
		return result;
	}
	
	public static void main(String []args){
		String str = "22";
		ArrayList<Integer> list = string2List(str, "v");
		System.out.println("======================");
		for(Integer i : list){
			System.out.println(i);
		}
	}
}
