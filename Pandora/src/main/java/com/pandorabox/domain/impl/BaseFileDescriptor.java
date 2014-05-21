package com.pandorabox.domain.impl;

import com.pandorabox.domain.FileDescriptor;

public class BaseFileDescriptor implements FileDescriptor {

	private static final long serialVersionUID = 1559436616199998385L;

	private int fileId;
	
	private String relativePath;
	
	private String bucketPath;
	
	private String name;
	
	private String url;
	
	private boolean selected;

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	

}
