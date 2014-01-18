package com.pandorabox.domain.impl;

import com.pandorabox.domain.ImageDescriptor;

public class CommonImageDescriptor implements ImageDescriptor {

	private static final long serialVersionUID = 9184665947074587030L;

	private String relativePath;
	
	private String bucketPath;
	
	private String name;
	
	private String fileSecret;

	public String getRelativePath() {
		return relativePath;
	}

	public void setRelativePath(String relativePath) {
		this.relativePath = relativePath;
	}

	public String getBucketPath() {
		return bucketPath;
	}

	public void setBucketPath(String bucketPath) {
		this.bucketPath = bucketPath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFileSecret() {
		return fileSecret;
	}

	public void setFileSecret(String fileSecret) {
		this.fileSecret = fileSecret;
	}

}
