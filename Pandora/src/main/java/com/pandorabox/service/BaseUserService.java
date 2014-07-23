package com.pandorabox.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pandorabox.dao.UserDao;
import com.pandorabox.domain.User;
import com.pandorabox.domain.impl.BaseUser;

@Service
public class BaseUserService implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public User bindWeiboUser(int weiboUid,String screenName,String profileUrl) {
		User bindedUser = null;
		bindedUser = getUserByWeiboUid(weiboUid);
		if(bindedUser==null){
			bindedUser = new BaseUser();
			bindedUser.setWeiboUid(weiboUid);
			bindedUser.setUsername(screenName);
			bindedUser.setUrl(profileUrl);
			userDao.save(bindedUser);
		}
		return bindedUser;
	}

	@Override
	public User getUserByUserName(String userName) {
		return userDao.getUserByUserName(userName);
	}

	@Override
	public User getUserByWeiboUid(int weiboUid) {
		return userDao.getUserByWeiboUid(weiboUid);
	}

	@Override
	public int addUser(User user) {
		return userDao.save(user);
	}

	@Override
	public void removeUser(int userId) {
		User user = userDao.get(userId);
		userDao.remove(user);
	}

	@Override
	public void updateUser(User user) {
		userDao.update(user);
	}

	@Override
	public User getUserById(int uid) {
		return userDao.get(uid);
	}

}
