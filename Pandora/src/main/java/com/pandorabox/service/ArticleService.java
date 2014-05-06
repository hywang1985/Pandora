/**
 * 
 */
package com.pandorabox.service;

import java.util.List;

import com.pandorabox.domain.Article;

/**
 * @author hywang
 *ArticleService 用来为文章提供服务
 */
public interface ArticleService {

	public void addArticle(Article article);
	
	public void removeArticle(int articleId);
	
	public List<Article> getArticles();
	
	public List<Article> getArticlesByPage(int start,int count);
}
