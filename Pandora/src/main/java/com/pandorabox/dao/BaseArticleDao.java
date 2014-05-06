package com.pandorabox.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.pandorabox.domain.Article;

@Repository("articleDao")
public class BaseArticleDao extends BaseGenericDataAccessor<Article, Integer> implements
		ArticleDao{
	
	private static String GET_ARTICLES = "from Article";

	private static String GET_ARTICLES_BY_TITLE = "from Article article where article.title like ?";
	
	public BaseArticleDao() {
	
	}

	@Override
	public List<Article> getByTitle(String articleTitle) {
		return find(GET_ARTICLES_BY_TITLE, articleTitle);
	}
	
	/**
	 * 分页效果
	 * */
	public List<Article> getArticlesByPage(int start,int count){
		 Query query = createQuery(GET_ARTICLES); 
		 query.setFirstResult(start);
		 query.setMaxResults(count);
		 return query.list();
	}

	@Override
	public List<Article> getArticles() {
		return loadAll();
	}
}
