package com.pandorabox.domain;

import java.io.Serializable;
import java.util.List;

/**
 * User对象用来描述系统的注册用户
 * 
 * @author hywang
 * */
public interface User extends Serializable{
	
	public Integer getUserId();
	public void setUserId(Integer userId);
	
	public Integer getWeiboUid();
	public void setWeiboUid(Integer weiboUid);
	/**
	 * 用户名
	 */
	public String getUsername();
	public void setUsername(String username);
	
	/**
	 * 密码
	 */
	public String getPasswd();
	public void setPasswd(String passWd);
	
	/**
	 * 真实姓名
	 */
	public String getName();
	public void setName(String name);
	
	/**
	 * 注册邮箱
	 */
	public String getEmail();
	public void setEmail(String email);
	
	/**
	 * 该用户下创建的文章
	 * */
	public List<Article> getArticles();
	public void setArticles(List<Article> articles);
	
	/**
	 * 用户主页的URL，绝对路径
	 * */
	public String getUrl();
	public void setUrl(String url);
	
	/**
	 * 用户的播放偏好
	 * 是否播放音乐
	 * */
	public Boolean isPlayMusic();
	public void setPlayMusic(Boolean playMusic);
}
