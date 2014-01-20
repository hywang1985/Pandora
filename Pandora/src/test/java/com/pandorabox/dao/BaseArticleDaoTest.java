package com.pandorabox.dao;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pandorabox.domain.Article;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring-config/pandorabox-dao.xml")

public class BaseArticleDaoTest {

	@Autowired
	private ArticleDao articleDao;
	
	@Test
	public void findArticlesByTitle(){
		List<Article> articles = articleDao.getByTitle("abc");
		Assert.assertNull(articles);
	}
}
