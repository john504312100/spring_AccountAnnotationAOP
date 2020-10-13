package com.itheima.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

/**
 * @author yuehan
 * @DATE 2020-09-23
 * @TIME 17:47
 * 和事务管理相关的工具了，它包含了 开启事务，提交事务，回滚事务和释放连接
 */
@Component
@Aspect
public class TransactionManager {

    @Pointcut("execution( * com.itheima.service.impl.*.*(..))")
    private void pt1(){}

    @Autowired
    private ConnectionUtils connectionUtils;

    /**
     * 开启事务
     */
    public void beginTransaction() {
        try {
            connectionUtils.getThreadConnection().setAutoCommit(false);
            System.out.println("beginTransaction");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 提交事务
     */
    public void submit() {
        try {
            connectionUtils.getThreadConnection().commit();
            System.out.println("submit");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 回滚事务
     */
    public void rollback() {
        try {
            connectionUtils.getThreadConnection().rollback();
            System.out.println("rollback");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 释放连接
     */
    public void release() {
        try {
            connectionUtils.getThreadConnection().close(); //还回了连接池中
            connectionUtils.removeConnection();
            System.out.println("release");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Around("pt1()")
    public Object aroundTransaction(ProceedingJoinPoint pjp){
        Object reValue = "";
        try {
            Object[] args = pjp.getArgs();
            beginTransaction();
            reValue = pjp.proceed(args);
            submit();
            return reValue;
        } catch (Throwable t) {
            rollback();
            t.printStackTrace();
            throw new RuntimeException(t);
        }finally {
            release();
        }
    }
}
