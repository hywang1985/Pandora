package com.pandorabox.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.pandorabox.domain.User;

@Repository("userDao")
public class BaseUserDao extends BaseGenericDataAccessor<User, Integer> implements
		UserDao {

	private static String GET_USER_BY_NAME = "from User user where user.username like ?";

	@Override
	public User getUserByUserName(String userName) {
		User user = null;
		List result = find(GET_USER_BY_NAME,userName);
		if(result!=null && !result.isEmpty()){
			user = (User)result.get(0);
		}
		return user;
	}

}
