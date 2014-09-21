package com.pandorabox.domain.assist;

import java.util.List;

import com.pandorabox.domain.Tag;

public interface TagContainer {

	public boolean containsTag(String tagValue);

	public List<Tag> getTags();

	public void setTags(List<Tag> tags);
	
	public void setupTag(String tagText);
}
