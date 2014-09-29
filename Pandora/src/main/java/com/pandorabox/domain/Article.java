package com.pandorabox.domain;

import java.io.Serializable;

import com.pandorabox.domain.assist.ImageContainer;
import com.pandorabox.domain.assist.LayoutContainer;
import com.pandorabox.domain.assist.MusicContainer;
import com.pandorabox.domain.assist.TagContainer;

/**
 * Article用来表示一篇文章
 * 
 * @author hywang
 */
public interface Article extends Serializable, TagContainer, ImageContainer, MusicContainer, LayoutContainer {

    public int getArticleId();

    public void setArticleId(int articleId);

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
     * 文章作者
     */
    public User getAuthor();

    public void setAuthor(User author);
}
