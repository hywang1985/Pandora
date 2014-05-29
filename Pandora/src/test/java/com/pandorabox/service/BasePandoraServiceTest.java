package com.pandorabox.service;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试service层的基类，已经配置了service层的事务，因此在测试的时候只需要装在所有配置文件即可
 * @author hywang
 * */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class BasePandoraServiceTest {

}
