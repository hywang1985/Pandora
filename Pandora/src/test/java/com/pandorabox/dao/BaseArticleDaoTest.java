package com.pandorabox.dao;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pandorabox.domain.Article;
import com.pandorabox.domain.FileDescriptor;
import com.pandorabox.domain.ImageDescriptor;
import com.pandorabox.domain.LayoutBehavior;
import com.pandorabox.domain.Tag;
import com.pandorabox.domain.User;
import com.pandorabox.domain.impl.BaseArticle;
import com.pandorabox.domain.impl.BaseFileDescriptor;
import com.pandorabox.domain.impl.BaseImageDescriptor;
import com.pandorabox.domain.impl.BaseLayoutDescriptor;
import com.pandorabox.domain.impl.BaseTag;
import com.pandorabox.domain.impl.BaseUser;

public class BaseArticleDaoTest extends BasePandoraDaoTest {
	
	@Autowired
	private ArticleDao articleDao;
	
	@Test
	public void testFindArticlesByTitle(){
		List<Article> articles = articleDao.getByTitle("abc");
		Assert.assertTrue(articles.isEmpty());
	}
	
	@Test
	public void testAddArtile(){
		System.out.println("**************存储文章****************");
		//创建用户
		User user = new BaseUser();
		 user.setName("hywang");
		 user.setEmail("jam_0917@sina.com");
		 user.setPasswd("123456");
		 //创建第一篇文章
		Article article1 = new BaseArticle();
		article1.setAuthor(user);
		article1.setPickedMusicIndex(1);
		//设置一个标签
		Tag tag1 = new BaseTag();
		tag1.setValue("历史");
		article1.getTags().add(tag1);
		
		article1.setText("This is text");
		article1.setTitle("This is title");
		
		//创建一副图片
		ImageDescriptor img1 = new BaseImageDescriptor();
		img1.setName("pandora");
		img1.setBucketPath("PandoraBox");
		img1.setFileSecret("abcabc");
		img1.setRelativePath("pathaaaa");
		article1.getImages().add(img1);
		
		// 创建第一首歌，作为选中歌曲
		FileDescriptor fd1 = new BaseFileDescriptor();
		fd1.setName("lalaaa");
		fd1.setBucketPath("PandoraBox");
		fd1.setRelativePath("pathaaaa");
		fd1.setUrl("https://sdfsfs.mp3");
		fd1.setSelected(true);
		article1.getFiles().add(fd1);

		// 创建第二首歌
		FileDescriptor fd2 = new BaseFileDescriptor();
		fd2.setName("lalaaa");
		fd2.setBucketPath("PandoraBox");
		fd2.setRelativePath("pathaaaa");
		fd2.setUrl("https://aaaa.mp3");
		article1.getFiles().add(fd2);
		
		//创建一个布局描述
		LayoutBehavior horizontal1 = new BaseLayoutDescriptor();
		article1.setLayoutBehavior(horizontal1);
		
		 //创建第二篇文章
		Article article2 = new BaseArticle();
		article2.setAuthor(user);
		article2.setPickedMusicIndex(1);
		//设置一个标签
		Tag tag2 = new BaseTag();
		tag2.setValue("文艺");
		article2.getTags().add(tag2);
		
		article2.setText("This is text");
		article2.setTitle("This is title");
		
		//创建一副图片
		ImageDescriptor img2 = new BaseImageDescriptor();
		img2.setName("pandora");
		img2.setBucketPath("PandoraBox");
		img2.setFileSecret("abcabc");
		img2.setRelativePath("pathaaaa");
		article2.getImages().add(img2);
		
		//创建一个布局描述
		LayoutBehavior horizontal2 = new BaseLayoutDescriptor();
		article2.setLayoutBehavior(horizontal2);
		
		 //创建第三篇文章
		Article article3 = new BaseArticle();
		article3.setAuthor(user);
		article3.setPickedMusicIndex(1);
		//设置一个标签
		Tag tag3 = new BaseTag();
		tag3.setValue("体育");
		article3.getTags().add(tag3);
		
		article3.setText("This is text");
		article3.setTitle("This is title");
		
		//创建一副图片
		ImageDescriptor img3 = new BaseImageDescriptor();
		img3.setName("pandora");
		img3.setBucketPath("PandoraBox");
		img3.setFileSecret("abcabc");
		img3.setRelativePath("pathaaaa");
		article3.getImages().add(img3);
		
		//创建一个布局描述
		LayoutBehavior horizontal3 = new BaseLayoutDescriptor();
		article3.setLayoutBehavior(horizontal3);
		
		 //创建第四篇文章
		Article article4 = new BaseArticle();
		article4.setAuthor(user);
		article4.setPickedMusicIndex(1);
		//设置一个标签
		Tag tag4 = new BaseTag();
		tag4.setValue("电影");
		article4.getTags().add(tag4);
		
		article4.setText("This is text");
		article4.setTitle("This is title");
		
		//创建一副图片
		ImageDescriptor img4 = new BaseImageDescriptor();
		img4.setName("pandora");
		img4.setBucketPath("PandoraBox");
		img4.setFileSecret("abcabc");
		img4.setRelativePath("pathaaaa");
		article4.getImages().add(img4);
		
		//创建一个布局描述
		LayoutBehavior horizontal4 = new BaseLayoutDescriptor();
		article4.setLayoutBehavior(horizontal4);
		user.getArticles().add(article1);
		user.getArticles().add(article2);
		user.getArticles().add(article3);
		user.getArticles().add(article4);
		Integer id1=articleDao.save(article1);
		Integer id2=articleDao.save(article2);
		Integer id3=articleDao.save(article3);
		Integer id4=articleDao.save(article4);
		System.out.println("**************查询已经存储的文章****************");
		
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(id1);
		ids.add(id2);
		ids.add(id3);
		ids.add(id4);
		for(int id:ids){
			Article retrieved = articleDao.get(id);
			Assert.assertNotNull("没有找到id是"+id+"的文章", retrieved);
			Assert.assertNotNull("文章没有作者", retrieved.getAuthor());
			Assert.assertNotNull("文章没有图片", retrieved.getImages().get(0));
			Assert.assertNotNull("文章没有音乐", retrieved.getPickedMusicIndex());
		}
		
	}
	
	@Test
	public void testGetArticlesByPage(){
		List<Article> articles = articleDao.getArticlesByPage(1, 3); //从第二条记录开始，加载三条记录
		for(int i=0;i<articles.size();i++){
			Article article = articles.get(i);
			switch(i){
			case 0:
				Assert.assertEquals("文艺", article.getTags().get(0).getValue());
				break;
			case 1:
				Assert.assertEquals("体育", article.getTags().get(0).getValue());
				break;
			case 2:
				Assert.assertEquals("电影", article.getTags().get(0).getValue());
				break;
			}
		}
	}
	
	@Test
	public void testGetNextArticles(){
		List<Article> articles = articleDao.getNextArticles(1, 2);
		for(int i=0;i<articles.size();i++){
			Article article = articles.get(i);
			int articleId = article.getArticleId();
			switch(i){
			case 0:
				Assert.assertEquals(2, articleId);
				break;
			case 1:
				Assert.assertEquals(3,articleId);
				break;
			}
		}
	}
	
	@Test
	public void testGetPreviousArticles(){
		List<Article> articles = articleDao.getPreviousArticles(4, 3);
		for(int i=0;i<articles.size();i++){
			Article article = articles.get(i);
			int articleId = article.getArticleId();
			switch(i){
			case 0:
				Assert.assertEquals(3, articleId);
				break;
			case 1:
				Assert.assertEquals(2,articleId);
				break;
			case 2:
				Assert.assertEquals(1,articleId);
				break;
			}
		}
	}
	
	@Test
	public void testGetRandomArticle(){
		Article article = articleDao.getRandomArticle(-1,null);
		System.out.println("Get the random article, id is: "+article.getArticleId());
		Assert.assertNotNull(article);
	}
	
}
