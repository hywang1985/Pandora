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
	public void addArticle(Article article) {
		articleDao.save(article);
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

}
