package com.pandorabox.dao;

import com.pandorabox.domain.LayoutBehavior;

public interface LayoutBehaviorDao extends
		GenericDataAccessor<LayoutBehavior, Integer> {

	public LayoutBehavior getLayoutByName(String name);
}
