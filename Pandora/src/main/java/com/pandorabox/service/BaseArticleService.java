package com.pandorabox.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pandorabox.dao.ArticleDao;
import com.pandorabox.domain.Article;

@Service
public class BaseArticleService implements ArticleService {

	@Autowired
	private ArticleDao articleDao;
	
	@Override
	public int addArticle(Article article) {
		return articleDao.save(article);
	}

	@Override
	public void removeArticle(int articleId) {
		Article article = articleDao.get(articleId);
		articleDao.remove(article);
	}

	@Override
	public List<Article> getArticles() {
		return articleDao.getArticles();
	}

	@Override
	public List<Article> getArticlesByPage(int start, int count) {
		return articleDao.getArticlesByPage(start, count);
	}

	@Override
	public void updateArticle(Article article) {
		articleDao.update(article);
	}

	@Override
	public Article getArticleById(int articleId) {
		return articleDao.get(articleId);
	}

	@Override
	public List<Article> getPreviousArticles(int articleId, int count) {
		return articleDao.getPreviousArticles(articleId, count);
	}

	@Override
	public List<Article> getNextArticles(int articleId, int count) {
		return articleDao.getNextArticles(articleId, count);
	}

	@Override
	public Article getRandomArticle(int previousId) {
		return articleDao.getRandomArticle(previousId,null);
	}

}
