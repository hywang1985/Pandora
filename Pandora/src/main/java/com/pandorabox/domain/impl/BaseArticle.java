package com.pandorabox.domain.impl;

import java.util.ArrayList;
import java.util.List;

import com.pandorabox.cons.CommonConstant;
import com.pandorabox.domain.Article;
import com.pandorabox.domain.FileDescriptor;
import com.pandorabox.domain.ImageDescriptor;
import com.pandorabox.domain.LayoutBehavior;
import com.pandorabox.domain.Tag;
import com.pandorabox.domain.User;

public class BaseArticle implements Article {

    private static final long serialVersionUID = 3078586399874526136L;

    private int articleId;

    private int pickedMusicIndex;

    private List<ImageDescriptor> images = new ArrayList<ImageDescriptor>();

    private List<FileDescriptor> files = new ArrayList<FileDescriptor>();

    private String title;

    private String text;

    private List<Tag> tags = new ArrayList<Tag>();

    private User author;

    private LayoutBehavior layoutBehavior;

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

    @Override
    public List<FileDescriptor> getFiles() {
        return files;
    }

    @Override
    public void setFiles(List<FileDescriptor> files) {
        this.files = files;
    }

    public int getPickedMusicIndex() {
        return pickedMusicIndex;
    }

    public void setPickedMusicIndex(int pickedMusicIndex) {
        this.pickedMusicIndex = pickedMusicIndex;
    }

    @Override
    public int hashCode() {
        int result = 17;
        int articleId = getArticleId();
        int author = getAuthor().hashCode();
        result = 31 * result + articleId;
        result = 31 * result + author;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof BaseArticle)) {
            return false;
        }
        BaseArticle target = (BaseArticle) obj;
        boolean result = true;
        result = (getArticleId() == target.getArticleId()) && result;
        result = (getAuthor() == null ? target.getAuthor() == null : getAuthor().equals(target.getAuthor())) && result;
        return result;
    }

    @Override
    public boolean containsTag(String tagValue) {
        boolean found = false;
        for (Tag tag : getTags()) {
            if (tagValue.equals(tag.getValue())) {
                found = true;
                break;
            }
        }
        return found;
    }

    public void setupTag(String tagText) {
        if (tagText != null && !CommonConstant.EMPTY_STRING.equals(tagText)) {
            if (!tagText.contains(CommonConstant.COMMA)) {
                Tag t = new BaseTag();
                t.setValue(tagText);
                getTags().add(t);
            } else {
                String[] tags = tagText.split(CommonConstant.COMMA);
                for (String tag : tags) {
                    Tag t = new BaseTag();
                    t.setValue(tag);
                    getTags().add(t);
                }
            }
        }
    }

    public void setMusicSelected(List<FileDescriptor> uploadedMusics, String musicSelectedIndex) {
        if (musicSelectedIndex != null && !CommonConstant.EMPTY_STRING.equals(musicSelectedIndex)) {
            for (int i = 0; i < uploadedMusics.size(); i++) {
                if (Integer.parseInt(musicSelectedIndex) == i) {
                    FileDescriptor music = getFiles().get(i);
                    music.setSelected(true);
                    setPickedMusicIndex(i);
                    break;
                }
            }
        }
    }
}
