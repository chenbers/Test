package com.inthinc.pro.dao;

import java.util.List;

import com.inthinc.pro.model.Account;


public interface AccountDAO extends GenericDAO<Account, Integer>
{
    Integer create(Account entity);
    
    List<Account> getAllAcctIDs();
    
    List<Long> getAllValidAcctIDs();

}
