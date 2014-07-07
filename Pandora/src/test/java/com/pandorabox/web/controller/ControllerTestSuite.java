package com.pandorabox.web.controller;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	ArticleControllerTests.class,
	AuthorizationConrollerTests.class,
	UserControllerTests.class
})
/**控制器的测试套件*/
public class ControllerTestSuite {

}
