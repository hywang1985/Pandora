package com.pandorabox.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pandorabox.domain.User;
import com.pandorabox.domain.impl.BaseUser;

public class BaseUserServiceTest extends BasePandoraServiceTest {

	private static final String USER_NAME = "kidbone1985";
	
	private static final String CHANGED_NAME = "hywang";
	
	private static int WEIBO_UID = 123456;
	
	private static int USER_ID;
	
	@Autowired
	private UserService userService;
	
	@Test
	public void testAddUser(){
		User user = new BaseUser();
		user.setName("王浩宇");
		user.setUsername(USER_NAME);
		USER_ID = userService.addUser(user);
	}
	
	@Test
	public void testUpdateUser(){
		User user = userService.getUserByUserName(USER_NAME);
		user.setName(CHANGED_NAME);
		user.setWeiboUid(WEIBO_UID);
		userService.updateUser(user);
	}
	
	@Test
	public void testGetUserByUserName(){
		User user = userService.getUserByUserName(USER_NAME);
		Assert.assertNotNull(user);
	}
	
	@Test
	public void testGetUserByWeiboUid(){
		User user = userService.getUserByWeiboUid(WEIBO_UID);
		Assert.assertNotNull(user);
	}
	
	@Test
	public void testDeleteUser(){
		userService.removeUser(USER_ID);
	}
}
