package com.pandorabox.domain;

/**
 * Tag对象用来描述文章标签
 * @author hywang
 * */
public interface Tag {
	
	public int getTagId();
	public void setTagId(int tagId);
	
	public void setValue(String value);
	
	public String getValue();
}
