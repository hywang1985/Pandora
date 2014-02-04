package com.pandorabox.service;

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

public class BaseArticleServiceTest extends BasePandoraServiceTest {

	@Autowired
	private ArticleService articleService;
	
	@Test
	public void testAddArticle(){
		//创建用户，文章作者
		User user = new BaseUser();
		user.setName("hywang");
		user.setUsername("kidbone1985");
		user.setPasswd("19850917");
		user.setEmail("jam_0917@sina.com");
		//创建文章
		Article article = new BaseArticle();
		//创建Tag
		Tag tag = new BaseTag();
		tag.setValue("历史");
		//创建ImageDescriptor
		ImageDescriptor image = new BaseImageDescriptor();
		image.setBucketPath("pandorabox");
		image.setFileSecret("bac");
		image.setName("sample.jpg");
		image.setRelativePath("/sample.jpg");
		//创建布局
		LayoutBehavior layout = new BaseLayoutDescriptor();
		//设置article的属性
		article.setMusicURL("http://douban.fm/");
		article.setTitle("梵高和他的向日葵");
		article.setText("梵高是一个有才的，神经质的画家。");
		article.setAuthor(user);
		article.getTags().add(tag);
		article.getImages().add(image);
		article.setLayoutBehavior(layout);
		user.getArticles().add(article);
		articleService.addArticle(article);
	}
	
	@Test
	public void testRemoveArticle(){
		articleService.removeArticle(1);
	}
}
