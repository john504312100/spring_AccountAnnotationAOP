package com.itheima.dao.impl;

import com.itheima.dao.IAccountDao;
import com.itheima.domain.Account;
import com.itheima.utils.ConnectionUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

/**
 * @author yuehan
 * @DATE 2020-09-08
 * @TIME 16:40
 */
@Component("accountDao")
public class AccountDaoImpl implements IAccountDao {

    @Autowired
    private QueryRunner runner;
    @Autowired
    private ConnectionUtils connectionUtils;

    public List<Account> findAllAccounts() {
        try {
            return runner.query(connectionUtils.getThreadConnection(),"select * from account",new BeanListHandler<Account>(Account.class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Account findAccountById(Integer accountId) {
        try {
            return runner.query(connectionUtils.getThreadConnection(),"select * from account where id = ?", new BeanHandler<Account>(Account.class),accountId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void saveAccount(Account account) {
        try {
            runner.update(connectionUtils.getThreadConnection(),"insert into account(name, money) VALUES (?,?)",account.getName(),account.getMoney());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void updateAccount(Account account) {
        try {
            runner.update(connectionUtils.getThreadConnection(),"update account set name = ?, money = ? where id = ?",account.getName(),account.getMoney(),account.getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAccount(Integer accountId) {
        try {
            runner.update(connectionUtils.getThreadConnection(),"delete from account where id = ?", accountId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Account findAccountByName(String accountName) {
        try {
            List<Account> accounts = runner.query(connectionUtils.getThreadConnection(),"select * from account where name = ?", new BeanListHandler<Account>(Account.class),accountName);
            if(accounts == null || accounts.size() == 0) return null;
            if (accounts.size()>1) throw new RuntimeException("结果集不唯一");
            return accounts.get(0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
