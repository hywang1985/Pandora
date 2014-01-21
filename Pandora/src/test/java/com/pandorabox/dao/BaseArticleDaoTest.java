package com.pandorabox.dao;


import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pandorabox.AbstractPandoraTransactionalTest;
import com.pandorabox.domain.Article;

public class BaseArticleDaoTest extends AbstractPandoraTransactionalTest {
	
	@Autowired
	private ArticleDao articleDao;
	
	@Test
	public void findArticlesByTitle(){
		List<Article> articles = articleDao.getByTitle("abc");
		Assert.assertTrue(articles.isEmpty());
	}
}
