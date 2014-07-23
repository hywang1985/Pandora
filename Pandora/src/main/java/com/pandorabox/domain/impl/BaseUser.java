package com.pandorabox.domain.impl;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.pandorabox.domain.Article;
import com.pandorabox.domain.User;

public class BaseUser implements User {

	private static final long serialVersionUID = -484835266759517694L;

	private Integer userId;
	
	private Integer weiboUid;
	
	private String url;
	
	private String username;
	
	private String passwd;
	
	private String name;
	
	private String email;
	
	@JsonBackReference
	private List<Article> articles = new ArrayList<Article>();

	@JsonIgnore
	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	@JsonIgnore
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@JsonIgnore
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public List<Article> getArticles() {
		return this.articles;
	}

	@Override
	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getWeiboUid() {
		return weiboUid;
	}

	public void setWeiboUid(Integer weiboUid) {
		this.weiboUid = weiboUid;
	}

	@Override
	public String getUrl() {
		return url;
	}

	@Override
	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public int hashCode() {
		int result = 17;
		int userId = getUserId();
		int email = getEmail()==null?0:getEmail().hashCode();
		int userName = getUsername()==null?0:getUsername().hashCode();
		int url =  getUrl()==null?0:getUrl().hashCode();
		result = 31*result+userId;
		result = 31*result+email;
		result = 31*result+userName;
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
		if (!(obj instanceof BaseUser)) {
			return false;
		}

		BaseUser target = (BaseUser) obj;
		boolean result = true;
		result = (getUserId() == target.getUserId()) && result;
		result = (getEmail()==null?target.getEmail()==null: getEmail().equals(target.getEmail())) && result;
		result = (getUsername()==null?target.getUsername()==null: getUsername().equals(target.getUsername())) && result;
		result = (getUrl()==null?target.getUrl()==null: getUrl().equals(target.getUrl())) && result;
		return result;
	}

}
