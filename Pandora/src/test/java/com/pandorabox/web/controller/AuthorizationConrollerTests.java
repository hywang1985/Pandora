package com.pandorabox.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.ResultActions;

import com.pandorabox.cons.CommonConstant;
import com.pandorabox.domain.User;
import com.pandorabox.domain.impl.BaseUser;

@RunWith(SpringJUnit4ClassRunner.class)
public class AuthorizationConrollerTests extends AbstractContextControllerTests {

	private static User user;
	@BeforeClass
	public static void initUser(){
		user = new BaseUser();
		user.setName("hywang");
		user.setUsername("kidbone1985");
		user.setEmail("jam_0917@sina.com");
	}
	
	@Test
	public void getHttpRestAuthorization() throws Exception {
		ResultActions result = mockMvc.perform(post("/authorization/rest")
				.header(CommonConstant.HTTP_METHOD_HEADER_NAME, "PUT")
				.header(CommonConstant.HTTP_CONTENT_LENGTH_HEADER_NAME, 26)
				.header(CommonConstant.HTTP_BUCKET_TYPE_HEADER_NAME, "image")
				.header(CommonConstant.HTTP_URI_HEADER_NAME,
						"/pandora001/pic.jpg"));
		result.andExpect(status().isOk()).andDo(print());
	}
	
	@Test
	public void getFormApiAuthorization() throws Exception {
		
		
		ResultActions result = mockMvc.perform(post("/authorization/form")
				.param(CommonConstant.HTTP_BUCKET_TYPE_HEADER_NAME, "image").sessionAttr(CommonConstant.USER_CONTEXT, user)
				);
		result.andExpect(status().isOk()).andDo(print());
	}
	
	@AfterClass
	public static void postClass(){
		user = null;
	}
}
