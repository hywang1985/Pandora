package com.pandorabox.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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
		user.setUsername("fakeUser");
		user.setEmail("jam_0917@sina.com");
	}
	
	
	@Test
	public void testAddArticles() throws Exception {
		JSONArray addedImages = new JSONArray();
		JSONObject img1 = new JSONObject();
		JSONObject img2 = new JSONObject();
		img1.put("url", "/fakeUser/castle.jpg");
		img2.put("url", "/fakeUser/angle.jpg");
		img2.put("time", "1398511699");
		img2.put("message", "OK");
		img2.put("sign", "b0caf896238ec85bf3e3e14de80299bf");
		img2.put("code", "200");
		img2.put("image-width", "1024");
		img2.put("image-height", "768");
		img2.put("image-frames", "1");
		img2.put("image-type", "JPEG");
		addedImages.add(img1);
		addedImages.add(img2);
		JSONArray addedMusics = new JSONArray();
		JSONObject music1 = JSONObject.fromObject("{\"code\":200,\"message\":\"ok\",\"url\":\"\\/fakeUser/SPITZ.mp3\",\"time\":1398511699,\"sign\":\"b0caf896238ec85bf3e3e14de80299bf\"}");
		JSONObject music2 = JSONObject.fromObject("{\"code\":200,\"message\":\"ok\",\"url\":\"\\/fakeUser/SPITZ.mp3\",\"time\":1398511699,\"sign\":\"b0caf896238ec85bf3e3e14de80299bf\"}");
		addedMusics.add(music1);
		addedMusics.add(music2);
		mockMvc.perform(
				post("/article").param(CommonConstant.ARTICLE_TAGS_KEY, "文艺")
						.param(CommonConstant.ARTICLE_TITLE_KEY, "title1")
						.param(CommonConstant.ARTICLE_CONTENT_KEY, "content")
						.param(CommonConstant.ARTICLE_LAYOUT_KEY,
								LayoutBehavior.HORIZONTAL_LAYOUT_NAME)
						.param(CommonConstant.ARTICLE_COMMITTED_IMAGES_KEY,
								addedImages.toString())
						.param(CommonConstant.ARTICLE_COMMITTED_MUSIC_KEY,
								addedMusics.toString())
						.param(CommonConstant.MUSIC_SELECTED_INDEX_KEY,"1").accept(MediaType.APPLICATION_JSON)
						.sessionAttr(CommonConstant.USER_CONTEXT, user))
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$."+CommonConstant.STATUS_KEY).value(CommonConstant.STATUS_OK))
				.andDo(print());
		
		mockMvc.perform(
				post("/article").param(CommonConstant.ARTICLE_TAGS_KEY, "体育")
						.param(CommonConstant.ARTICLE_TITLE_KEY, "title2")
						.param(CommonConstant.ARTICLE_CONTENT_KEY, "content")
						.param(CommonConstant.ARTICLE_LAYOUT_KEY,
								LayoutBehavior.HORIZONTAL_LAYOUT_NAME)
						.param(CommonConstant.ARTICLE_COMMITTED_IMAGES_KEY,
								addedImages.toString())
						.param(CommonConstant.ARTICLE_COMMITTED_MUSIC_KEY,
								addedMusics.toString())
						.param(CommonConstant.MUSIC_SELECTED_INDEX_KEY,"1").accept(MediaType.APPLICATION_JSON)
						.sessionAttr(CommonConstant.USER_CONTEXT, user))
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$."+CommonConstant.STATUS_KEY).value(CommonConstant.STATUS_OK))
				.andDo(print());
		
		mockMvc.perform(
				post("/article").param(CommonConstant.ARTICLE_TAGS_KEY, "影视")
						.param(CommonConstant.ARTICLE_TITLE_KEY, "title3")
						.param(CommonConstant.ARTICLE_CONTENT_KEY, "content")
						.param(CommonConstant.ARTICLE_LAYOUT_KEY,
								LayoutBehavior.HORIZONTAL_LAYOUT_NAME)
						.param(CommonConstant.ARTICLE_COMMITTED_IMAGES_KEY,
								addedImages.toString())
						.param(CommonConstant.ARTICLE_COMMITTED_MUSIC_KEY,
								addedMusics.toString())
						.param(CommonConstant.MUSIC_SELECTED_INDEX_KEY,"1").accept(MediaType.APPLICATION_JSON)
						.sessionAttr(CommonConstant.USER_CONTEXT, user))
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$."+CommonConstant.STATUS_KEY).value(CommonConstant.STATUS_OK))
				.andDo(print());
		
		//第四篇文章要在测试中删除，相应的删除图片，为了不影响前三个数据，这理图片设置成别的，需要真的上传一下DeathGod和Griffin两张图片
		img1.put("url", "/fakeUser/DeathGod.jpg");
		img2.put("url", "/fakeUser/Griffin_wallpaper.jpg");
		mockMvc.perform(
				post("/article").param(CommonConstant.ARTICLE_TAGS_KEY, "企业,篮球")
						.param(CommonConstant.ARTICLE_TITLE_KEY, "title4")
						.param(CommonConstant.ARTICLE_CONTENT_KEY, "content")
						.param(CommonConstant.ARTICLE_LAYOUT_KEY,
								LayoutBehavior.HORIZONTAL_LAYOUT_NAME)
						.param(CommonConstant.ARTICLE_COMMITTED_IMAGES_KEY,
								addedImages.toString())
						.param(CommonConstant.ARTICLE_COMMITTED_MUSIC_KEY,
								addedMusics.toString())
						.param(CommonConstant.MUSIC_SELECTED_INDEX_KEY,"1").accept(MediaType.APPLICATION_JSON)
						.sessionAttr(CommonConstant.USER_CONTEXT, user))
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$."+CommonConstant.STATUS_KEY).value(CommonConstant.STATUS_OK))
				.andDo(print());
	}
	
	@Test
	public void testUpdateArticle() throws Exception {
		JSONArray addedImages = new JSONArray();
		JSONObject img1 = new JSONObject();
		JSONObject img2 = new JSONObject();
		img1.put("url", "/fakeUser/angle.jpg");
		img2.put("url", "/fakeUser/castle.jpg");
		addedImages.add(img1);
		addedImages.add(img2);
		JSONArray delImages = new JSONArray();
		//不需要真的删除已经上传好的这两张图片
		//		delImages.add("1");
		//		delImages.add("2");
		JSONArray addedMusics = new JSONArray();
		JSONObject music1 = JSONObject.fromObject("{\"code\":200,\"message\":\"ok\",\"url\":\"\\/fakeUser/aa.mp3\",\"time\":1398511699,\"sign\":\"b0caf896238ec85bf3e3e14de80299bf\"}");
		JSONObject music2 = JSONObject.fromObject("{\"code\":200,\"message\":\"ok\",\"url\":\"\\/fakeUser/bb.mp3\",\"time\":1398511699,\"sign\":\"b0caf896238ec85bf3e3e14de80299bf\"}");
		addedMusics.add(music1);
		addedMusics.add(music2);
		JSONArray deletedMusics = new JSONArray();
		deletedMusics.add("1");
		mockMvc.perform(
				put("/article/1")
						.param(CommonConstant.ARTICLE_TAGS_KEY, "文艺,法克")
						.param(CommonConstant.ARTICLE_TITLE_KEY, "changedTitle")
						.param(CommonConstant.ARTICLE_CONTENT_KEY,
								"changedContent")
						.param(CommonConstant.ARTICLE_LAYOUT_KEY,
								LayoutBehavior.CENTER_LAYOUT_NAME)
						.param(CommonConstant.ARTICLE_COMMITTED_IMAGES_KEY,
								addedImages.toString())
						.param(CommonConstant.ARTICLE_DELETED_IMAGES_KEY,
								delImages.toString())
						.param(CommonConstant.ARTICLE_COMMITTED_MUSIC_KEY,
								addedMusics.toString())
						.param(CommonConstant.ARTICLE_DELETED_MUSIC_KEY,
								deletedMusics.toString())
						.param(CommonConstant.MUSIC_SELECTED_INDEX_KEY,"1")
						.sessionAttr(CommonConstant.USER_CONTEXT, user))
				.andExpect(jsonPath("$."+CommonConstant.STATUS_KEY).value(CommonConstant.STATUS_OK))
				.andDo(print());
	}
	
	
	@Test
	public void testLoadArticlesByPage() throws Exception{
		mockMvc.perform(
				get("/article/dyload").header(CommonConstant.ARTICLE_START_INDEX_HEADER_NAME, 1).accept(MediaType.APPLICATION_JSON)
						.sessionAttr(CommonConstant.USER_CONTEXT, user))
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$."+CommonConstant.STATUS_KEY).value(CommonConstant.STATUS_OK))
				.andDo(print());
	}
	
	@Test
	public void testDeleteArticle() throws Exception {

		mockMvc.perform(
				delete("/article/4")
						.sessionAttr(CommonConstant.USER_CONTEXT, user))
				.andExpect(
						content().string(
								"{\"status\":\"OK\",\"deleted\":4}"))
				.andDo(print());
	}
	
	@Test
	public void testReAddArticle() throws Exception {
		JSONArray addedImages = new JSONArray();
		JSONObject img1 = new JSONObject();
		JSONObject img2 = new JSONObject();
		img1.put("url", "/fakeUser/castle.jpg");
		img2.put("url", "/fakeUser/angle.jpg");
		img2.put("time", "1398511699");
		img2.put("message", "OK");
		img2.put("sign", "b0caf896238ec85bf3e3e14de80299bf");
		img2.put("code", "200");
		img2.put("image-width", "1024");
		img2.put("image-height", "768");
		img2.put("image-frames", "1");
		img2.put("image-type", "JPEG");
		addedImages.add(img1);
		addedImages.add(img2);
		JSONArray addedMusics = new JSONArray();
		JSONObject music1 = JSONObject.fromObject("{\"code\":200,\"message\":\"ok\",\"url\":\"\\/fakeUser/SPITZ.mp3\",\"time\":1398511699,\"sign\":\"b0caf896238ec85bf3e3e14de80299bf\"}");
		JSONObject music2 = JSONObject.fromObject("{\"code\":200,\"message\":\"ok\",\"url\":\"\\/fakeUser/SPITZ.mp3\",\"time\":1398511699,\"sign\":\"b0caf896238ec85bf3e3e14de80299bf\"}");
		addedMusics.add(music1);
		addedMusics.add(music2);
		mockMvc.perform(
				post("/article").param(CommonConstant.ARTICLE_TAGS_KEY, "文艺")
						.param(CommonConstant.ARTICLE_TITLE_KEY, "title1")
						.param(CommonConstant.ARTICLE_CONTENT_KEY, "content")
						.param(CommonConstant.ARTICLE_LAYOUT_KEY,
								LayoutBehavior.HORIZONTAL_LAYOUT_NAME)
						.param(CommonConstant.ARTICLE_COMMITTED_IMAGES_KEY,
								addedImages.toString())
						.param(CommonConstant.ARTICLE_COMMITTED_MUSIC_KEY,
								addedMusics.toString())
						.param(CommonConstant.MUSIC_SELECTED_INDEX_KEY,"1").accept(MediaType.APPLICATION_JSON)
						.sessionAttr(CommonConstant.USER_CONTEXT, user))
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$."+CommonConstant.STATUS_KEY).value(CommonConstant.STATUS_OK))
				.andDo(print());
	}
	
	@AfterClass
	public static void postClass(){
		user = null;
	}
}
