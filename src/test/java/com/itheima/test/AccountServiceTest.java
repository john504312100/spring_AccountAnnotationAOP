package com.itheima.test;

import com.itheima.config.SpringConfiguration;
import com.itheima.service.IAccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author yuehan
 * @DATE 2020-09-08
 * @TIME 17:41
 * 使用Junit 测试配置
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfiguration.class)
public class AccountServiceTest {


    @Autowired
    @Qualifier("proxyAccountService")
    private IAccountService as;

    @Test
    public void transfer() {
        as.transfer("aaa","bbb",100f);
    }
}
