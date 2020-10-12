package com.itheima.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author yuehan
 * @DATE 2020-09-23
 * @TIME 17:36
 * 连接工具类 用于从数据源中获取一个连接并且实现和线程绑定
 */
@Component
public class ConnectionUtils {

    private ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();

    @Autowired
    @Qualifier("ds1")
    private DataSource dataSource;


    /**
     *获取当前线程上的连接
     * @return
     */
    public Connection getThreadConnection() {
        //1.先从ThreadLocal获取
        Connection connection = threadLocal.get();
        try {
            //2.判断当前线程上是否有链接
            if (connection == null) {
                //3.从数据源中获取一个连接，并且存入threadLocal中
                connection = dataSource.getConnection();
                threadLocal.set(connection);
            }
            //4.返回当前线程上的连接
            return connection;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 连接和线程解绑
     */
    public void removeConnection(){
        threadLocal.remove();
    }
}
