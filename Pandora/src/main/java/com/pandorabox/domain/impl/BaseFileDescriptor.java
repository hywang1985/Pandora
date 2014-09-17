package com.pandorabox.domain.impl;

import com.pandorabox.domain.FileDescriptor;

public class BaseFileDescriptor implements FileDescriptor {

	private static final long serialVersionUID = 1559436616199998385L;

	private int fileId;
	
	private String relativePath;
	
	private String bucketPath;
	
	private String name;
	
	private String url;
	
	private Boolean selected = Boolean.FALSE;

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
	
	@Override
	public int hashCode() {
		int result = 17;
		int fileId = getFileId();
		int name = getName()==null?0:getName().hashCode();
		int url = getUrl()==null?0:getUrl().hashCode();
		int selected = Boolean.valueOf(isSelected()).hashCode();
		result = 31*result+fileId;
		result = 31*result+name;
		result = 31*result+url;
		result = 31*result+selected;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof BaseFileDescriptor)) {
			return false;
		}

		BaseFileDescriptor target = (BaseFileDescriptor) obj;
		boolean result = true;
		result = (getFileId() == target.getFileId()) && result;
		result = (getName()==null?target.getName()==null: getName().equals(target.getName())) && result;
		result = (getUrl()==null?target.getUrl()==null: getUrl().equals(target.getUrl())) && result;
		result = (isSelected()==target.isSelected()) && result;
		return result;
	}
	

}
