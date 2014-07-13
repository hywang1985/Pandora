package com.pandorabox.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.pandorabox.domain.Article;

@Repository("articleDao")
public class BaseArticleDao extends BaseGenericDataAccessor<Article, Integer>
		implements ArticleDao {

	private static final String GET_ARTICLES = "from Article";

	private static final String GET_ARTICLES_BY_TITLE = "from Article article where article.title like ?";

	private static final String GET_PREVIOUS_ARTICLES = "from Article article where article.articleId < ? order by article.articleId desc";

	private static final String GET_NEXT_ARTICLES = "from Article article where article.articleId > ? order by article.articleId asc";

	public BaseArticleDao() {

	}

	@Override
	public List<Article> getByTitle(String articleTitle) {
		return find(GET_ARTICLES_BY_TITLE, articleTitle);
	}

	/**
	 * 分页效果
	 * */
	public List<Article> getArticlesByPage(int start, int count) {
		Query query = createQuery(GET_ARTICLES);
		query.setFirstResult(start);
		query.setMaxResults(count);
		return query.list();
	}

	@Override
	public List<Article> getArticles() {
		return loadAll();
	}

	@Override
	public List<Article> getPreviousArticles(int articleId, int count) {
		Query query = createQuery(GET_PREVIOUS_ARTICLES, articleId);
		query.setMaxResults(count);
		return query.list();
	}

	@Override
	public List<Article> getNextArticles(int articleId, int count) {
		Query query = createQuery(GET_NEXT_ARTICLES, articleId);
		query.setMaxResults(count);
		return query.list();
	}
}
