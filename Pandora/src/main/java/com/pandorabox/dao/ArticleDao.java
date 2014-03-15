package com.pandorabox.dao;

import java.util.List;

import com.pandorabox.domain.Article;


public interface ArticleDao extends GenericDataAccessor<Article, Integer> {

	public List<Article> getByTitle(String articleTitle);
	
	public List<Article> getArticles();
}
