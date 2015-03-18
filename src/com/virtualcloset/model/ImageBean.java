package com.virtualcloset.model;

import java.util.ArrayList;

public class ImageBean {
	private int imageId;
	private String imageName;
	private int imageSize;
	private ArrayList<String> imageLabels;
	
	public int getImageId() {
		return imageId;
	}
	public void setImageId(int imageId) {
		this.imageId = imageId;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public int getImageSize() {
		return imageSize;
	}
	public void setImageSize(int imageSize) {
		this.imageSize = imageSize;
	}
	public ArrayList<String> getImageLabels() {
		return imageLabels;
	}
	public void setImageLabels(ArrayList<String> imageLabels) {
		this.imageLabels = imageLabels;
	}
}
