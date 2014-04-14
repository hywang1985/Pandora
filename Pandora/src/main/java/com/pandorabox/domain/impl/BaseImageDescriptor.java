package com.pandorabox.domain.impl;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.pandorabox.domain.ImageDescriptor;

public class BaseImageDescriptor implements ImageDescriptor {

	private static final long serialVersionUID = 9184665947074587030L;

	private int imageId;
	
	private String relativePath;
	
	private String bucketPath;
	
	private String name;
	
	private String fileSecret;

	@JsonIgnore
	public String getRelativePath() {
		return relativePath;
	}

	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}

	@JsonIgnore
	public String getBucketPath() {
		return bucketPath;
	}

	public void setBucketPath(String bucketPath) {
		this.bucketPath = bucketPath;
	}

	@JsonIgnore
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonIgnore
	public String getFileSecret() {
		return fileSecret;
	}

	public void setFileSecret(String fileSecret) {
		this.fileSecret = fileSecret;
	}

	public int getImageId() {
		return imageId;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	@Override
	public String getURL() {
		return bucketPath!=null?"http://" + this.bucketPath
				+ ".b0.upaiyun.com"+relativePath:null;
	}

}
