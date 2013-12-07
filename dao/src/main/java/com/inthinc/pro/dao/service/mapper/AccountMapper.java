package com.inthinc.pro.dao.service.mapper;

import com.inthinc.pro.dao.service.dto.Account;

public class AccountMapper implements GenericMapper <com.inthinc.pro.dao.service.dto.Account, com.inthinc.pro.model.Account>  {

    @Override
    public Account mapToDTO(com.inthinc.pro.model.Account account) {
        Account dtoAccount = new Account();
        dtoAccount.setAccountID(account.getAccountID());
        return dtoAccount;
    }

    @Override
    public com.inthinc.pro.model.Account mapFromDTO(Account dtoAccount) {
        
        com.inthinc.pro.model.Account account = new com.inthinc.pro.model.Account();
        account.setAccountID(dtoAccount.getAccountID());
        return account;
    }

}
