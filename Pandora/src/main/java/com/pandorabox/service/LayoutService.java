package com.pandorabox.service;

import com.pandorabox.domain.Article;
import com.pandorabox.domain.LayoutBehavior;

public interface LayoutService {

	public LayoutBehavior getLayoutByName(String name);
	
	public void setupLayout(Article article, String articleLayout);
}
