package com.virtualcloset.model;

import java.sql.Date;

public class UserBean {
	
	private String userName;
	private String password;
	private String email;
	private String phone;
	private String sex;
	private int age;
	private String job;
	
	private int height;		//身高
	private int weight;		//体重
	private int bust;		//胸围
	private int waist;		//腰围
	private int hip;		//臀围
	
	
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
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getBust() {
		return bust;
	}
	public void setBust(int bust) {
		this.bust = bust;
	}
	public int getWaist() {
		return waist;
	}
	public void setWaist(int waist) {
		this.waist = waist;
	}
	public int getHip() {
		return hip;
	}
	public void setHip(int hip) {
		this.hip = hip;
	}
}
