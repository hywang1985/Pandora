package com.pandorabox.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.File;
import java.io.FileInputStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.pandorabox.cons.CommonConstant;
import com.pandorabox.domain.User;
import com.pandorabox.domain.impl.BaseUser;

@RunWith(SpringJUnit4ClassRunner.class)
public class ArticleControllerTests extends AbstractContextControllerTests {

	/** 上传到upyun的图片名 */
	private static final String PIC_NAME = "sample.jpeg";

	/** 本地待上传的测试文件 */
	private static final java.net.URL SAMPLE_PIC_FILE = ArticleControllerTests.class
			.getResource(PIC_NAME);

	private MockMvc mockMvc;

	@Before
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(this.wac).build();
	}

	@Test
	public void addArticle() throws Exception {

		File image = new File(SAMPLE_PIC_FILE.toURI().getPath());
		FileInputStream fis = new FileInputStream(image);
	    byte[] bytes = new byte[fis.available()];
	    fis.read(bytes);
		MockMultipartFile file = new MockMultipartFile(PIC_NAME, PIC_NAME,
				null, bytes);

		User user = new BaseUser();
		user.setName("hywang");
		user.setUsername("kidbone1985");
		user.setEmail("jam_0917@sina.com");

		mockMvc.perform(fileUpload("/article").file(file)
				.param(CommonConstant.ARTICLE_TITLE_KEY, "title")
				.param(CommonConstant.ARTICLE_CONTENT_KEY, "content")
				.sessionAttr(CommonConstant.USER_CONTEXT, user));
	}

}
