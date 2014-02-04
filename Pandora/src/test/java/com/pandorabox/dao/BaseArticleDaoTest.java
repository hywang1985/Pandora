package com.pandorabox.dao;


import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pandorabox.domain.Article;
import com.pandorabox.domain.ImageDescriptor;
import com.pandorabox.domain.LayoutBehavior;
import com.pandorabox.domain.Tag;
import com.pandorabox.domain.User;
import com.pandorabox.domain.impl.BaseArticle;
import com.pandorabox.domain.impl.BaseImageDescriptor;
import com.pandorabox.domain.impl.BaseLayoutDescriptor;
import com.pandorabox.domain.impl.BaseTag;
import com.pandorabox.domain.impl.BaseUser;

public class BaseArticleDaoTest extends BasePandoraDaoTest {
	
	@Autowired
	private ArticleDao articleDao;
	
	@Test
	public void findArticlesByTitle(){
		List<Article> articles = articleDao.getByTitle("abc");
		Assert.assertTrue(articles.isEmpty());
	}
	
	@Test
	public void addArtile(){
		System.out.println("**************存储文章****************");
		//创建用户
		User user = new BaseUser();
		 user.setName("hywang");
		 user.setEmail("jam_0917@sina.com");
		 user.setPasswd("123456");
		 //创建文章
		Article article = new BaseArticle();
		article.setAuthor(user);
		article.setMusicURL("https://sdfsfs.mp3");
		//设置一个标签
		Tag tag = new BaseTag();
		tag.setValue("历史");
		article.getTags().add(tag);
		
		article.setText("This is text");
		article.setTitle("This is title");
		
		//创建一副图片
		ImageDescriptor img = new BaseImageDescriptor();
		img.setName("pandora");
		img.setBucketPath("PandoraBox");
		img.setFileSecret("abcabc");
		img.setRelativePath("pathaaaa");
		article.getImages().add(img);
		
		//创建一个布局描述
		LayoutBehavior horizontal = new BaseLayoutDescriptor();
		article.setLayoutBehavior(horizontal);
		Integer id=articleDao.save(article);
		System.out.println("**************存储文章 "+id+" 成功！****************");
		System.out.println();
		System.out.println("**************查询已经存储的文章****************");
		Article retrieved = articleDao.get(id);
		Assert.assertNotNull("没有找到id是"+id+"的文章", retrieved);
		Assert.assertNotNull("文章没有作者", retrieved.getAuthor());
		Assert.assertNotNull("文章没有图片", retrieved.getImages().get(0));
		Assert.assertNotNull("文章没有音乐", retrieved.getMusicURL());
	}
	
}
