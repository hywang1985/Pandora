package com.pandorabox.domain;

import java.io.Serializable;
import java.util.List;

/**
 * User对象用来描述系统的注册用户
 * 
 * @author hywang
 * */
public interface User extends Serializable{
	
	public int getUserId();
	public void setUserId(int userId);
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
}
