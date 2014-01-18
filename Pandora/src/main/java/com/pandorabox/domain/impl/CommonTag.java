package com.pandorabox.domain.impl;

import com.pandorabox.domain.Tag;

public class CommonTag implements Tag {

	private String value;

	@Override
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String getValue() {
		return this.value;
	}

}
