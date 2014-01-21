package com.pandorabox;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;


@ContextConfiguration("file:src/main/webapp/WEB-INF/spring-config/applicationContext.xml")
public class AbstractPandoraTransactionalTest extends AbstractTransactionalJUnit4SpringContextTests {
}
