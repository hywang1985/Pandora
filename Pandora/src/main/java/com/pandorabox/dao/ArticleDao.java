package com.pandorabox.dao;

import java.util.List;

import com.pandorabox.domain.Article;

public interface ArticleDao extends GenericDataAccessor<Article, Integer> {

	public List<Article> getByTitle(String articleTitle);

	public List<Article> getArticles();

	public List<Article> getArticlesByPage(int start, int count);

	public List<Article> getPreviousArticles(int articleId, int count);

	public List<Article> getNextArticles(int articleId, int count);
}
