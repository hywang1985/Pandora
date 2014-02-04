package com.pandorabox.dao;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * 测试Dao的基类，因为Service已经通过AspectJ表达式加上了事务，因此不用继承AbstractTransactionalJUnit4SpringContextTests
 * */
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring-config/applicationContext.xml")
@TransactionConfiguration(defaultRollback=false)
public class BasePandoraDaoTest extends AbstractTransactionalJUnit4SpringContextTests {
}
