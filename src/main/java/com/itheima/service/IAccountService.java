package com.itheima.service;

import com.itheima.domain.Account;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yuehan
 * @DATE 2020-09-08
 * @TIME 16:14
 * 账户的业务层接口
 */
public interface IAccountService {

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
     * 转账
     * @param sourceName 转出账户名称
     * @param targetName 转入账户名称
     * @param money 转账金额
     */
    void transfer(String sourceName, String targetName, Float money);
}
