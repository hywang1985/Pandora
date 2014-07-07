package com.pandorabox.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pandorabox.domain.User;

@Repository("userDao")
public class BaseUserDao extends BaseGenericDataAccessor<User, Integer> implements
		UserDao {

	private static String GET_USER_BY_NAME = "from User user where user.username like ?";
	
	private static String GET_USER_BY_UID = "from User user where user.weiboUid like ?";

	@Override
	public User getUserByUserName(String userName) {
		User user = null;
		List result = find(GET_USER_BY_NAME,userName);
		if(result!=null && !result.isEmpty()){
			user = (User)result.get(0);
		}
		return user;
	}

	@Override
	public User getUserByWeiboUid(int weiboUid) {
		User user = null;
		List result = find(GET_USER_BY_UID,weiboUid);
		if(result!=null && !result.isEmpty()){
			user = (User)result.get(0);
		}
		return user;
	}

}
