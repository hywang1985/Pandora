package com.pandorabox.dao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**数据访问层的测试套件*/
@RunWith(Suite.class)
@SuiteClasses({
	BaseImageDescriptorDaoTest.class,
	BaseArticleDaoTest.class,
	BaseUserDaoTest.class
})
public class DaoTestSuite {

}
