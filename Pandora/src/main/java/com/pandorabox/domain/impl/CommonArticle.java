package com.pandorabox.domain.impl;

import java.util.ArrayList;
import java.util.List;

import com.pandorabox.domain.Article;
import com.pandorabox.domain.ImageDescriptor;
import com.pandorabox.domain.LayoutBehavior;
import com.pandorabox.domain.Tag;
import com.pandorabox.domain.User;

public class CommonArticle implements Article {

	private static final long serialVersionUID = 3078586399874526136L;
	
	private int articleId;
	
	private String musicURL;

	private List<ImageDescriptor> images = new ArrayList<ImageDescriptor>(); 
	
	private String title;
	
	private String text;
	
	private List<Tag> tags = new ArrayList<Tag>();

	private User author;
	
	private LayoutBehavior layoutBehavior;

	public String getMusicURL() {
		return musicURL;
	}

	public void setMusicURL(String musicURL) {
		this.musicURL = musicURL;
	}

	public List<ImageDescriptor> getImages() {
		return images;
	}

	public void setImages(List<ImageDescriptor> images) {
		this.images = images;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public LayoutBehavior getLayoutBehavior() {
		return layoutBehavior;
	}

	public void setLayoutBehavior(LayoutBehavior layoutBehavior) {
		this.layoutBehavior = layoutBehavior;
	}

	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

}
