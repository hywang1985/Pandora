package com.pandorabox.domain.impl;

import java.util.ArrayList;
import java.util.List;

import com.pandorabox.domain.Article;
import com.pandorabox.domain.User;

public class CommonUser implements User {

	private static final long serialVersionUID = -484835266759517694L;

	private int userId;
	
	private String username;
	
	private String passwd;
	
	private String name;
	
	private String email;
	
	private List<Article> articles = new ArrayList<Article>();

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
