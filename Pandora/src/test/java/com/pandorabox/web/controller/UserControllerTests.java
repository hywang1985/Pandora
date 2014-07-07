package com.pandorabox.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockSessionCookieConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.ResultActions;

import com.pandorabox.cons.CommonConstant;
import com.pandorabox.domain.User;
import com.pandorabox.domain.impl.BaseUser;

@RunWith(SpringJUnit4ClassRunner.class)

public class UserControllerTests extends AbstractContextControllerTests {

	private static User user;
	
	private static final String WEIBO_UID = "123456";
	
	private static final String SCREEN_NAME = "聒噪的hywang";
	
	private static MockHttpSession mockSession;
 	@BeforeClass
	public static void initUser(){
		user = new BaseUser();
		user.setName("hywang");
		user.setUsername("kidbone1985");
		user.setEmail("jam_0917@sina.com");
		mockSession = new MockHttpSession();
	}
	
	@Test
	public void testLogin() throws Exception{
		ResultActions result = mockMvc.perform(get("/user/login").session(mockSession)
				.param(CommonConstant.WEBO_UID_KEY, WEIBO_UID).accept(MediaType.APPLICATION_JSON)
				.param(CommonConstant.SCREEN_NAME_KEY, SCREEN_NAME));
		result.andExpect(status().isOk()).andExpect(jsonPath("$."+CommonConstant.STATUS_KEY).value(CommonConstant.STATUS_OK)).andDo(print());
		User sessionUser = (User) mockSession.getAttribute(CommonConstant.USER_CONTEXT);
		Assert.assertNotNull(sessionUser);
	}
	
	@Test
	public void testLogout() throws Exception{
		ResultActions result = mockMvc.perform(get("/user/logout").session(mockSession)
				.param(CommonConstant.WEBO_UID_KEY, WEIBO_UID).accept(MediaType.APPLICATION_JSON)
				.param(CommonConstant.SCREEN_NAME_KEY, SCREEN_NAME));
		result.andExpect(status().isOk()).andExpect(jsonPath("$."+CommonConstant.STATUS_KEY).value(CommonConstant.STATUS_OK)).andDo(print());
		User sessionUser = (User) mockSession.getAttribute(CommonConstant.USER_CONTEXT);
		Assert.assertNull(sessionUser);
	}
	
	
	/**
	 * 测试已经绑定成功的账号的登陆情况 
	 * */
	@Test
	public void testRelogin() throws Exception{
		ResultActions result = mockMvc.perform(get("/user/login").session(mockSession)
				.param(CommonConstant.WEBO_UID_KEY, WEIBO_UID).accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isOk()).andExpect(jsonPath("$."+CommonConstant.STATUS_KEY).value(CommonConstant.STATUS_OK)).andDo(print());
		User sessionUser = (User) mockSession.getAttribute(CommonConstant.USER_CONTEXT);
		Assert.assertNotNull(sessionUser);
	}
	
	@AfterClass
	public static void postClass(){
		user = null;
	}
}
