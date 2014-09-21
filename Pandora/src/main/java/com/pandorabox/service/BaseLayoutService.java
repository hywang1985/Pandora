package com.pandorabox.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pandorabox.cons.CommonConstant;
import com.pandorabox.dao.LayoutBehaviorDao;
import com.pandorabox.domain.Article;
import com.pandorabox.domain.LayoutBehavior;
import com.pandorabox.domain.impl.BaseLayoutDescriptor;

@Service
public class BaseLayoutService implements LayoutService {

	@Autowired
	private LayoutBehaviorDao layoutDao;
	
	@Override
	public LayoutBehavior getLayoutByName(String name) {
		return layoutDao.getLayoutByName(name);
	}
	
	public void setupLayout(Article article,String articleLayout) {
		LayoutBehavior layoutBehavior;
		if (articleLayout != null && !CommonConstant.EMPTY_STRING.equals(articleLayout)) {
			layoutBehavior = getLayoutByName(articleLayout);
			if (layoutBehavior == null) {
				layoutBehavior = new BaseLayoutDescriptor();
				layoutBehavior.setName(articleLayout);
			}
			article.setLayoutBehavior(layoutBehavior);
		}
	}

}
