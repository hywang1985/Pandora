package com.pandorabox.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pandorabox.dao.LayoutBehaviorDao;
import com.pandorabox.domain.LayoutBehavior;

@Service
public class BaseLayoutService implements LayoutService {

	@Autowired
	private LayoutBehaviorDao layoutDao;
	
	@Override
	public LayoutBehavior getLayoutByName(String name) {
		return layoutDao.getLayoutByName(name);
	}

}
