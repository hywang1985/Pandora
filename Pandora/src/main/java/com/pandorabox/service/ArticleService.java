/**
 * 
 */
package com.pandorabox.service;

import java.util.List;

import org.hibernate.Query;

import com.pandorabox.domain.Article;

/**
 * @author hywang
 *ArticleService 用来为文章提供服务
 */
public interface ArticleService {

	public int addArticle(Article article);
	
	public void removeArticle(int articleId);
	
	public void updateArticle(Article article);
	
	public List<Article> getArticles();
	
	public Article getArticleById(int articleId);
	
	public List<Article> getArticlesByPage(int start,int count);
	
	public List<Article> getPreviousArticles(int articleId,int count);
	
	public List<Article> getNextArticles(int articleId, int count);
	
	public Article getRandomArticle(int previousId);
}
