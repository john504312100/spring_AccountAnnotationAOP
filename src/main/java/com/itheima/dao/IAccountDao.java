package com.itheima.dao;

import com.itheima.domain.Account;

import java.util.List;

/**
 * @author yuehan
 * @DATE 2020-09-08
 * @TIME 16:36
 * 账户的持久层接口
 */
public interface IAccountDao {
    /**
     * 查询所有
     * @return
     */
    List<Account> findAllAccounts();

    /**
     * 查询一个
     * @return
     */
    Account findAccountById(Integer accountId);

    /**
     * 保存
     */
    void saveAccount(Account account);

    /**
     * 更新
     */
    void updateAccount(Account account);

    /**
     * 删除
     */
    void deleteAccount(Integer accountId);

    /**
     * 根据名称查询账户
     * @param accountName 账户名
     * @return 如果有唯一的一个结果就返回，如果没有结果就返回null
     * 如果结果集超过1个，就抛异常
     */
    Account findAccountByName(String accountName);
}
