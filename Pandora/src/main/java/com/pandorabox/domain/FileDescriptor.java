package com.pandorabox.domain;

import java.io.Serializable;

public interface FileDescriptor extends Serializable{
	
	int getFileId();
	void setFileId(int fileId);
	
	/**是否被选择为当前文章的播放音乐*/
	public boolean isSelected();
	public void setSelected(boolean selected);
	
	/**文件在图片服务器上的相对路径*/
	public String getRelativePath();
	public void setRelativePath(String relativePath);
	
	/**文件存储空间的根（Bucket）路径*/
	public String getBucketPath();
	public void setBucketPath(String bucketPath);
	
	/**文件的名称*/
	public String getName();
	public void setName(String name);
	
	/**
	 * 文件的URL
	 */
	public String getUrl();
	public void setUrl(String url);
}
