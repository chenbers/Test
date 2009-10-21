package com.inthinc.pro.util;

import java.util.List;

import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.model.Account;

public class SecureAccountDAO extends SecureDAO<Account> {

    private AccountDAO accountDAO;

    @Override
    public Integer create(Account object) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Integer delete(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Account findByID(Integer id) {
        Account account = accountDAO.findByID(id);
        if (isAuthorized(account))
            return account;
        return null;
    }

    @Override
    public List<Account> getAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isAuthorized(Account account) {
        if (account != null) {
            if (getAccountID().equals(account.getAcctID()))
                return true;
        }
        return false;
    }

    @Override
    public Integer update(Account object) {
        // TODO Auto-generated method stub
        return null;
    }

    public AccountDAO getAccountDAO() {
        return accountDAO;
    }

    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

}
