package com.pandorabox.dao;


import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pandorabox.AbstractPandoraTransactionalTest;
import com.pandorabox.domain.Article;
import com.pandorabox.domain.ImageDescriptor;
import com.pandorabox.domain.LayoutBehavior;
import com.pandorabox.domain.Tag;
import com.pandorabox.domain.User;
import com.pandorabox.domain.impl.CommonArticle;
import com.pandorabox.domain.impl.CommonImageDescriptor;
import com.pandorabox.domain.impl.CommonLayoutDescriptor;
import com.pandorabox.domain.impl.CommonTag;
import com.pandorabox.domain.impl.CommonUser;

public class BaseArticleDaoTest extends AbstractPandoraTransactionalTest {
	
	@Autowired
	private ArticleDao articleDao;
	
	@Test
	public void findArticlesByTitle(){
		List<Article> articles = articleDao.getByTitle("abc");
		Assert.assertTrue(articles.isEmpty());
	}
	
	@Test
	public void addArtile(){
		//创建用户
		User user = new CommonUser();
		 user.setName("hywang");
		 user.setEmail("jam_0917@sina.com");
		 user.setPasswd("123456");
		 //创建文章
		Article article = new CommonArticle();
		article.setAuthor(user);
		article.setMusicURL("https://sdfsfs.mp3");
		//设置一个标签
		Tag tag = new CommonTag();
		tag.setValue("历史");
		article.getTags().add(tag);
		
		article.setText("This is text");
		article.setTitle("This is title");
		
		//创建一副图片
		ImageDescriptor img = new CommonImageDescriptor();
		img.setName("pandora");
		img.setBucketPath("PandoraBox");
		img.setFileSecret("abcabc");
		img.setRelativePath("pathaaaa");
		article.getImages().add(img);
		
		//创建一个布局描述
		LayoutBehavior horizontal = new CommonLayoutDescriptor();
		article.setLayoutBehavior(horizontal);
		Integer id=articleDao.save(article);
		System.out.println(id);
	}
	
}
