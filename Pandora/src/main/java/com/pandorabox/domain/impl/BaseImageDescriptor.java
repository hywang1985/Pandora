package com.pandorabox.domain.impl;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import com.pandorabox.cons.CommonConstant;
import com.pandorabox.domain.AbstractDescriptor;
import com.pandorabox.domain.ImageDescriptor;

public class BaseImageDescriptor extends AbstractDescriptor implements ImageDescriptor {

	private static final long serialVersionUID = 9184665947074587030L;

	private int imageId;
	
	private String fileSecret;
	
	@JsonIgnore
	public String getRelativePath() {
		return relativePath;
	}

	@JsonIgnore
	public String getBucketPath() {
		return bucketPath;
	}

	@JsonIgnore
	public String getName() {
		return name;
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

	@JsonProperty
	public String getSnapshotUrl() {
		return url+CommonConstant.IMAGE_SNAPSHOT_SUFFIX;
	}

	@Override
	public int hashCode() {
		int result = 17;
		int imageId = getImageId();
		int name = getName()==null?0:getName().hashCode();
		int url = getUrl()==null?0:getUrl().hashCode();
		result = 31*result+imageId;
		result = 31*result+name;
		result = 31*result+url;
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
		if (!(obj instanceof BaseImageDescriptor)) {
			return false;
		}

		BaseImageDescriptor target = (BaseImageDescriptor) obj;
		boolean result = true;
		result = (getImageId() == target.getImageId()) && result;
		result = (getName()==null?target.getName()==null: getName().equals(target.getName())) && result;
		result = (getUrl()==null?target.getUrl()==null: getUrl().equals(target.getUrl())) && result;
		return result;
	}

}
