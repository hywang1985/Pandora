package com.pandorabox;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;


@ContextConfiguration("file:src/main/webapp/WEB-INF/spring-config/applicationContext.xml")
@TransactionConfiguration(defaultRollback=false)
public class AbstractPandoraTransactionalTest extends AbstractTransactionalJUnit4SpringContextTests {
}
