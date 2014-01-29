package com.pandorabox.dao;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pandorabox.AbstractPandoraTransactionalTest;
import com.pandorabox.domain.User;
import com.pandorabox.domain.impl.BaseUser;


public class BaseUserDaoTest extends AbstractPandoraTransactionalTest {

	@Autowired
	private UserDao userDao;
	
	@Test
	public void addUser(){
		User user = new BaseUser();
		user.setEmail("jam_0917@sina.com");
		user.setUsername("kidbone1985");
		user.setPasswd("19850917");
		userDao.save(user);
	}
	
	@Test
	public void getUserByUserName(){
		User user = userDao.getUserByUserName("kidbone1985");
		Assert.assertNotNull(user);
	}
}
