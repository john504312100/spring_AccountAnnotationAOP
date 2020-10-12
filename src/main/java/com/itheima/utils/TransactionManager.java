package com.itheima.utils;

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
public class TransactionManager {

    @Autowired
    private ConnectionUtils connectionUtils;

    /**
     * 开启事务
     */
    public void beginTransaction() {
        try {
            connectionUtils.getThreadConnection().setAutoCommit(false);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
