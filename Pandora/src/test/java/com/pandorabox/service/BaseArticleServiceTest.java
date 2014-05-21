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
		//创建第一篇文章
		Article article1 = new BaseArticle();
		//创建Tag
		Tag tag1 = new BaseTag();
		tag1.setValue("历史");
		//创建ImageDescriptor
		ImageDescriptor image = new BaseImageDescriptor();
		image.setBucketPath("pandorabox");
		image.setFileSecret("bac");
		image.setName("sample.jpg");
		image.setRelativePath("/sample.jpg");
		//创建水平布局
		LayoutBehavior layout = new BaseLayoutDescriptor();
		//设置article的属性
		article1.setCurrentMusic("http://douban.fm/");
		article1.setTitle("梵高和他的向日葵");
		article1.setText("梵高是一个有才的，神经质的画家。");
		article1.setAuthor(user);
		article1.getTags().add(tag1);
		article1.getImages().add(image);
		article1.setLayoutBehavior(layout);
		user.getArticles().add(article1);
		articleService.addArticle(article1);
		
		//创建第二篇文章
		Article article2 = new BaseArticle();
		//创建第二个Tag
		Tag tag2 = new BaseTag();
		tag2.setValue("体育");
		//创建ImageDescriptor
		ImageDescriptor image2 = new BaseImageDescriptor();
		image2.setBucketPath("pandorabox");
		image2.setFileSecret("bac");
		image2.setName("sample.jpg");
		image2.setRelativePath("/sample.jpg");
		//创建居中布局
		LayoutBehavior layout2 = new BaseLayoutDescriptor();
		layout2.setName(LayoutBehavior.CENTER_LAYOUT_NAME);
		layout2.setRelativeCSSPath(LayoutBehavior.DEFAULT_CENTER_RELATIVE_CSS_PATH);
		//设置article的属性
		article2.setCurrentMusic("http://douban.fm/");
		article2.setTitle("詹姆斯有多牛逼");
		article2.setText("詹姆斯单场61分达成，全力詹出现");
		article2.setAuthor(user);
		article2.getTags().add(tag2);
		article2.getImages().add(image2);
		article2.setLayoutBehavior(layout2);
		user.getArticles().add(article2);
		articleService.addArticle(article2);
	}
	
	@Test
	public void testRemoveArticle(){
		articleService.removeArticle(1);
		articleService.removeArticle(2);
	}
}
