package com.pandorabox.web.controller;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@WebAppConfiguration
@ContextConfiguration({
		"file:src/main/webapp/WEB-INF/spring-config/pandorabox-servlet.xml",
		"classpath:applicationContext.xml" })
public class AbstractContextControllerTests {

	protected MockMvc mockMvc;
	
	@Before
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(this.wac).build();
	}
	
	@Autowired
	protected WebApplicationContext wac;

}
