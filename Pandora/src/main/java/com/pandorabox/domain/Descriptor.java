package com.pandorabox.domain;

public interface Descriptor {

	/**
	 * 文件在服务器上的相对路径
	 */
	public String getRelativePath();

	public void setRelativePath(String relativePath);

	/**
	 * 文件在存储空间的根（Bucket）路径
	 */
	public String getBucketPath();

	public void setBucketPath(String bucketPath);

	/**
	 * 文件的名称
	 */
	public String getName();

	public void setName(String name);

	/**
	 * 文件的绝对URL
	 */
	public String getUrl();

	public void setUrl(String url);

}
