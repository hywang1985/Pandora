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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.ResultActions;

import com.pandorabox.cons.CommonConstant;
import com.pandorabox.domain.User;
import com.pandorabox.domain.impl.BaseUser;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerTests extends AbstractContextControllerTests {

	private static User user;
	
	private static final String WEIBO_USER_STRING = "{\"id\":1144243765,\"idstr\":\"1144243765\",\"class\":1,\"screen_name\":\"聒噪的hywang\",\"profile_url\":\"abc\"}";
	
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
				.param(CommonConstant.WEIBO_USER_KEY, WEIBO_USER_STRING)
				.param(CommonConstant.WEIBO_PROFILE_URL_KEY, "")
				.accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isOk()).andExpect(jsonPath("$."+CommonConstant.STATUS_KEY).value(CommonConstant.STATUS_OK)).andDo(print());
		User sessionUser = (User) mockSession.getAttribute(CommonConstant.USER_CONTEXT);
		Assert.assertNotNull(sessionUser);
	}
	
	@Test
	public void testLogout() throws Exception{
		ResultActions result = mockMvc.perform(get("/user/logout").session(mockSession)
				.accept(MediaType.APPLICATION_JSON));
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
				.param(CommonConstant.WEIBO_USER_KEY, WEIBO_USER_STRING).accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isOk()).andExpect(jsonPath("$."+CommonConstant.STATUS_KEY).value(CommonConstant.STATUS_OK)).andDo(print());
		User sessionUser = (User) mockSession.getAttribute(CommonConstant.USER_CONTEXT);
		Assert.assertNotNull(sessionUser);
	}
	
	@Test
	public void testUpdateUserPreferences() throws Exception{
		ResultActions result = mockMvc.perform(get("/user/updatePreference").session(mockSession)
				.header(CommonConstant.PLAY_MUSIC_KEY, "false").accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isOk()).andExpect(jsonPath("$."+CommonConstant.STATUS_KEY).value(CommonConstant.STATUS_OK))
		.andExpect(jsonPath("$."+CommonConstant.PLAY_MUSIC_KEY).value(false)).andDo(print());
		User sessionUser = (User) mockSession.getAttribute(CommonConstant.USER_CONTEXT);
		Assert.assertNotNull(sessionUser);
		Assert.assertFalse(sessionUser.isPlayMusic());
	}
	
	@AfterClass
	public static void postClass(){
		user = null;
	}
}
