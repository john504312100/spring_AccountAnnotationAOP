package com.itheima.factory;

import com.itheima.domain.Account;
import com.itheima.service.IAccountService;
import com.itheima.utils.TransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @author yuehan
 * @DATE 2020-09-25
 * @TIME 17:32
 * 用于创建Service的 代理对象的工厂
 */
@Component
public class BeanFactory {
    @Autowired
    @Qualifier("proxyAccountService")
    private IAccountService accountService;

    @Autowired
    private TransactionManager txManager;

    /**
     * 获取service的代理对象
     * @return
     */
    public IAccountService getAccountService() {
        Proxy.newProxyInstance(accountService.getClass().getClassLoader(), accountService.getClass().getInterfaces(),
                new InvocationHandler() {
                    /**
                     * 添加事务的支持
                     * @param proxy
                     * @param method
                     * @param args
                     * @return
                     * @throws Throwable
                     */
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Object rtValue = null;
                        try {
                            //1.开启事务
                            txManager.beginTransaction();
                            //2.执行操作
                            rtValue = method.invoke(accountService, args);
                            //3.提交事务
                            txManager.submit();
                            //4.返回结果集
                            return rtValue;
                        } catch (Exception e) {
                            //5.回滚操作
                            txManager.rollback();
                            throw new RuntimeException(e);
                        } finally {
                            //6.释放资源
                            txManager.release();
                        }
                    }
                });
        return accountService;
    }
}
