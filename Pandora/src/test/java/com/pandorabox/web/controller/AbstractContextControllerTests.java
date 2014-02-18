package com.pandorabox.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

@WebAppConfiguration
@ContextConfiguration({
		"file:src/main/webapp/WEB-INF/spring-config/pandorabox-servlet.xml",
		"file:src/main/webapp/WEB-INF/spring-config/applicationContext.xml" })
public class AbstractContextControllerTests {

	@Autowired
	protected WebApplicationContext wac;

}
