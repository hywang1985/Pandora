package com.pandorabox.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pandorabox.domain.LayoutBehavior;

@Repository("layoutDao")
public class BaseLayoutBehaviorDao extends BaseGenericDataAccessor<LayoutBehavior, Integer>
		implements LayoutBehaviorDao {

	private static String GET_LAYOUT_BY_NAME = "from LayoutBehavior layout where layout.name like ?";
	@Override
	public LayoutBehavior getLayoutByName(String name) {
		LayoutBehavior layout = null;
		List result = find(GET_LAYOUT_BY_NAME, name);
		if(result!=null && !result.isEmpty()){
			layout = (LayoutBehavior)result.get(0);
		}
		return layout;
	}


}
