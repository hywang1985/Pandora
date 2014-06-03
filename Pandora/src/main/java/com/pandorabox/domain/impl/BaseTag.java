package com.pandorabox.domain.impl;

import com.pandorabox.domain.Tag;

public class BaseTag implements Tag {

	private static final long serialVersionUID = -4059212636105734659L;

	private int tagId;
	
	private String value;

	@Override
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String getValue() {
		return this.value;
	}

	public int getTagId() {
		return tagId;
	}

	public void setTagId(int tagId) {
		this.tagId = tagId;
	}

}
