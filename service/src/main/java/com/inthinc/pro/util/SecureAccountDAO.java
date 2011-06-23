package com.inthinc.pro.util;

import java.util.List;

import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.model.Account;

public class SecureAccountDAO extends SecureDAO<Account> {

    private AccountDAO accountDAO;

    @Override
    public Integer create(Account account) {
        if (isAuthorized(account))
            return accountDAO.create(account);
        return null;
    }

    @Override
    public Integer delete(Integer id) {
        //Only INTHINC users can delete an account
        if(isInthincUser())
            return accountDAO.deleteByID(id);
        return 0;
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
        if (isInthincUser()) {
            return accountDAO.getAllAcctIDs();
        }
        return null;
    }

    @Override
    public boolean isAuthorized(Account account) {
        if (account != null) {
            if (isInthincUser() || getAccountID().equals(account.getAccountID()))
                return true;
        }
        return false;
    }

    @Override
    public Account update(Account account) {
        if(isAuthorized(account) && accountDAO.update(account) != 0) 
            return accountDAO.findByID(account.getAccountID());
        return null;
            
    }

    public AccountDAO getAccountDAO() {
        return accountDAO;
    }

    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

}
