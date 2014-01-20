package com.pandorabox.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Article用来表示一篇文章，根据网站的原型设计，一篇文章应该包含如下的属性
 * 一条音乐
 * 若干图片
 * 一个题目
 * 一个正文
 * 所属的标签
 * 文章的作者
 * 文章的布局参数
 * @author hywang
 */
public interface Article extends Serializable{
	
	public int getArticleId();
	public void setArticleId(int articleId);
	
	/**
	 * 文章的音乐URL
	 */
	public String getMusicURL(); 
	public void setMusicURL(String musicURL);
	
	/**
	 * 文章的所有图片
	 * @see ImageDescriptor
	 */
	public List<ImageDescriptor> getImages(); 
	public void setImages(List<ImageDescriptor> images); 
	
	/**
	 * 文章的标题 
	 */
	public String getTitle();
	public void setTitle(String title);
	
	/**
	 * 文章的正文 
	 */
	public String getText();
	public void setText(String text);
	
	/**
	 * 文章所属的标签
	 */
	public List<Tag> getTags();
	public void setTags(List<Tag> tags);
	
	/**
	 * 文章作者
	 */
	public User getAuthor();
	public void setAuthor(User author);
	
	/**
	 * 文章布局，默认为横向布局
	 * @see LayoutBehavior
	 */
	public LayoutBehavior getLayoutBehavior();
	public void setLayoutBehavior(LayoutBehavior LayoutBehavior);
}
