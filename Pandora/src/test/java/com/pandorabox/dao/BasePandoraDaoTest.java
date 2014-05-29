package com.pandorabox.dao;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * 测试Dao的基类，因为Service已经通过AspectJ表达式加上了事务，因此不用继承AbstractTransactionalJUnit4SpringContextTests
 * 但需要注意，在测试类对应的pandorabox-dao.xml里，需要显示添加事务transactionManager,因为测试类使用了AbstractTransactionalJUnit4SpringContextTests
 * */
@ContextConfiguration("classpath:pandorabox-dao.xml")
@TransactionConfiguration(defaultRollback=false)
public class BasePandoraDaoTest extends AbstractTransactionalJUnit4SpringContextTests {
}
