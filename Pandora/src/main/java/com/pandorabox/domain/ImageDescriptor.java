package com.pandorabox.domain;

import java.io.Serializable;

/**
 * ImageDescriptor对象用来描述文章中的图片
 * @author hywang
 * */
public interface ImageDescriptor extends Serializable{
	
	int getImageId();
	void setImageId(int imageId);
	
	/**图片在图片服务器上的相对路径*/
	public String getRelativePath();
	public void setRelativePath(String relativePath);
	
	/**图片存储空间的根（Bucket）路径*/
	public String getBucketPath();
	public void setBucketPath(String bucketPath);
	
	/**图片的名称*/
	public String getName();
	public void setName(String name);
	
	/**
	 * 设置待上传文件的"访问密钥"
		 注意：
		 仅支持图片空！，设置密钥后，无法根据原文件URL直接访问，需带URL后面加上（缩略图间隔标志符+密钥）进行访问
		 举例：
		 如果缩略图间隔标志符为"!"，密钥为"bac"，上传文件路径为"/folder/test.jpg"，
		 那么该图片的对外访问地址为：http://空间域名 /folder/test.jpg!bac
	 */
	public String getFileSecret();
	public void setFileSecret(String fileSecret);
	
	/**
	 * 图片的URL
	 */
	public String getUrl();
	public void setUrl(String url);
	
	public String getSnapshotUrl();
}
