package com.itheima.service.impl;

import com.itheima.dao.IAccountDao;
import com.itheima.domain.Account;
import com.itheima.service.IAccountService;
import com.itheima.utils.TransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yuehan
 * @DATE 2020-09-08
 * @TIME 16:35
 * 账户的业务层实现类
 * 事务的控制都在业务层
 */
@Service("proxyAccountService")
public class AccountSereviceImpl implements IAccountService {

    @Autowired
    private IAccountDao accountDao;

    public List<Account> findAllAccounts() {
        return accountDao.findAllAccounts();
    }

    public Account findAccountById(Integer accountId) {
        return accountDao.findAccountById(accountId);
    }

    public void saveAccount(Account account) {
        accountDao.saveAccount(account);
    }

    public void updateAccount(Account account) {
        accountDao.updateAccount(account);
    }

    public void deleteAccount(Integer accountId) {
        accountDao.deleteAccount(accountId);
    }

    @Override
    public void transfer(String sourceName, String targetName, Float money) {
        //1.根据名称查询转出账户
        Account sourceAccount = accountDao.findAccountByName(sourceName);
        //2.根据名称查询转入账户
        Account targetAccount = accountDao.findAccountByName(targetName);
        //3.转出账户减钱
        sourceAccount.setMoney(sourceAccount.getMoney() - money);
        //4.转入账户加钱
        targetAccount.setMoney(targetAccount.getMoney() + money);
        //5.更新转出账户
        accountDao.updateAccount(sourceAccount);
        //6.更新转入账户
        accountDao.updateAccount(targetAccount);
    }
}
