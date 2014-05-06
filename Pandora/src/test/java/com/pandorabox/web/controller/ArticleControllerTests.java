package com.pandorabox.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pandorabox.cons.CommonConstant;
import com.pandorabox.domain.LayoutBehavior;
import com.pandorabox.domain.User;
import com.pandorabox.domain.impl.BaseUser;

@RunWith(SpringJUnit4ClassRunner.class)
public class ArticleControllerTests extends AbstractContextControllerTests {

	private static User user;
	
	@BeforeClass
	public static void initUser(){
		user = new BaseUser();
		user.setName("hywang");
		user.setUsername("kidbone1985");
		user.setEmail("jam_0917@sina.com");
	}
	
	
	@Test
	public void addArticles() throws Exception {

		mockMvc.perform(
				post("/article").accept(MediaType.APPLICATION_JSON).param(CommonConstant.ARTICLE_TAGS_KEY, "文艺")
						.param(CommonConstant.ARTICLE_TITLE_KEY, "title1")
						.param(CommonConstant.ARTICLE_CONTENT_KEY, "content")
						.param(CommonConstant.ARTICLE_LAYOUT_KEY,
								LayoutBehavior.HORIZONTAL_LAYOUT_NAME)
						.param(CommonConstant.ARTICLE_COMMITTED_IMAGES_KEY,
								"{\"code\":200,\"message\":\"ok\",\"url\":\"\\/angle.jpg\",\"time\":1398511677,\"image-width\":1024,\"image-height\":768,\"image-frames\":1,\"image-type\":\"JPEG\"}")
						.param(CommonConstant.ARTICLE_COMMITTED_MUSIC_KEY,
								"{\"code\":200,\"message\":\"ok\",\"url\":\"\\/SPITZ.mp3\",\"time\":1398511699,\"sign\":\"b0caf896238ec85bf3e3e14de80299bf\"}")
						.sessionAttr(CommonConstant.USER_CONTEXT, user))
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andDo(print());
		
		mockMvc.perform(
				post("/article").accept(MediaType.APPLICATION_JSON).param(CommonConstant.ARTICLE_TAGS_KEY, "体育")
						.param(CommonConstant.ARTICLE_TITLE_KEY, "title2")
						.param(CommonConstant.ARTICLE_CONTENT_KEY, "content")
						.param(CommonConstant.ARTICLE_LAYOUT_KEY,
								LayoutBehavior.HORIZONTAL_LAYOUT_NAME)
						.param(CommonConstant.ARTICLE_COMMITTED_IMAGES_KEY,
								"{\"code\":200,\"message\":\"ok\",\"url\":\"\\/angle.jpg\",\"time\":1398511677,\"image-width\":1024,\"image-height\":768,\"image-frames\":1,\"image-type\":\"JPEG\"}")
						.param(CommonConstant.ARTICLE_COMMITTED_MUSIC_KEY,
								"{\"code\":200,\"message\":\"ok\",\"url\":\"\\/SPITZ.mp3\",\"time\":1398511699,\"sign\":\"b0caf896238ec85bf3e3e14de80299bf\"}")
						.sessionAttr(CommonConstant.USER_CONTEXT, user))
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andDo(print());
		
		mockMvc.perform(
				post("/article").accept(MediaType.APPLICATION_JSON).param(CommonConstant.ARTICLE_TAGS_KEY, "影视")
						.param(CommonConstant.ARTICLE_TITLE_KEY, "title3")
						.param(CommonConstant.ARTICLE_CONTENT_KEY, "content")
						.param(CommonConstant.ARTICLE_LAYOUT_KEY,
								LayoutBehavior.HORIZONTAL_LAYOUT_NAME)
						.param(CommonConstant.ARTICLE_COMMITTED_IMAGES_KEY,
								"{\"code\":200,\"message\":\"ok\",\"url\":\"\\/angle.jpg\",\"time\":1398511677,\"image-width\":1024,\"image-height\":768,\"image-frames\":1,\"image-type\":\"JPEG\"}")
						.param(CommonConstant.ARTICLE_COMMITTED_MUSIC_KEY,
								"{\"code\":200,\"message\":\"ok\",\"url\":\"\\/SPITZ.mp3\",\"time\":1398511699,\"sign\":\"b0caf896238ec85bf3e3e14de80299bf\"}")
						.sessionAttr(CommonConstant.USER_CONTEXT, user))
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andDo(print());
		
		mockMvc.perform(
				post("/article").accept(MediaType.APPLICATION_JSON).param(CommonConstant.ARTICLE_TAGS_KEY, "企业,篮球")
						.param(CommonConstant.ARTICLE_TITLE_KEY, "title4")
						.param(CommonConstant.ARTICLE_CONTENT_KEY, "content")
						.param(CommonConstant.ARTICLE_LAYOUT_KEY,
								LayoutBehavior.HORIZONTAL_LAYOUT_NAME)
						.param(CommonConstant.ARTICLE_COMMITTED_IMAGES_KEY,
								"{\"code\":200,\"message\":\"ok\",\"url\":\"\\/angle.jpg\",\"time\":1398511677,\"image-width\":1024,\"image-height\":768,\"image-frames\":1,\"image-type\":\"JPEG\"}")
						.param(CommonConstant.ARTICLE_COMMITTED_MUSIC_KEY,
								"{\"code\":200,\"message\":\"ok\",\"url\":\"\\/SPITZ.mp3\",\"time\":1398511699,\"sign\":\"b0caf896238ec85bf3e3e14de80299bf\"}")
						.sessionAttr(CommonConstant.USER_CONTEXT, user))
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andDo(print());
	}
	
	@Test
	public void loadArticlesByPage() throws Exception{
		mockMvc.perform(
				get("/article/dyload").header(CommonConstant.ARTICLE_START_INDEX_HEADER_NAME, 1).accept(MediaType.APPLICATION_JSON)
						.sessionAttr(CommonConstant.USER_CONTEXT, user))
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andDo(print());
	}
	
	@AfterClass
	public static void postClass(){
		user = null;
	}
}
