package com.pandorabox.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Article用来表示一篇文章
 * @author hywang
 */
public interface Article extends Serializable{
	
	public int getArticleId();
	public void setArticleId(int articleId);
	
	/**
	 * 文章当前选中的音乐URL
	 */
	public String getCurrentMusic(); 
	public void setCurrentMusic(String musicURL);
	
	/**
	 * 文章的所有图片
	 * @see ImageDescriptor
	 */
	public List<ImageDescriptor> getImages(); 
	public void setImages(List<ImageDescriptor> images); 
	
	/**
	 * 文章的所有文件(音乐)
	 * @see FileDescriptor
	 */
	public List<FileDescriptor> getFiles(); 
	public void setFiles(List<FileDescriptor> files); 
	
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
