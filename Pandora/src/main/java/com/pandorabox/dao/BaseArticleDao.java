package com.pandorabox.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pandorabox.domain.Article;

@Repository("articleDao")
@Transactional
public class BaseArticleDao extends BaseGenericDataAccessor<Article, Integer> implements
		ArticleDao{

	private static String GET_ARTICLES_BY_TITLE = "from Article article where article.title like ?";
	
	public BaseArticleDao() {
	
	}

	@Override
	public List<Article> getByTitle(String articleTitle) {
		return find(GET_ARTICLES_BY_TITLE, articleTitle);
	}
}
