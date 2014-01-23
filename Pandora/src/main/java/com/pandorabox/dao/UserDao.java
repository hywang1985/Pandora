package com.pandorabox.dao;

import com.pandorabox.domain.User;

public interface UserDao extends GenericDataAccessor<User, Integer> {

	public User getUserByName(String userName);
}
