package com.virtualcloset.model;

import java.sql.Date;

public class UserBean {
	
	private String userName = null;
	private String password = null;
	private String email = "";
	private String phone = "";
	private String sex = "";
	private int age;
	private String job = "";
	private String height = "";		//身高
	private String weight = "";		//体重
	private String bust = "";			//胸围
	private String waist = "";			//腰围
	private String hip = "";				//臀围
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email==null ? "" : email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone==null ? "" : phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSex() {
		return sex==null ? "" : sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getJob() {
		return job==null ? "" : job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getBust() {
		return bust;
	}
	public void setBust(String bust) {
		this.bust = bust;
	}
	public String getWaist() {
		return waist;
	}
	public void setWaist(String waist) {
		this.waist = waist;
	}
	public String getHip() {
		return hip;
	}
	public void setHip(String hip) {
		this.hip = hip;
	}
}
