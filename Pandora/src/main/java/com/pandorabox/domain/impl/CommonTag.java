package com.pandorabox.domain.impl;

import com.pandorabox.domain.Tag;

public class CommonTag implements Tag {

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
