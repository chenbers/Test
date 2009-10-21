package com.inthinc.pro.service.impl;

import javax.ws.rs.core.Response;

import com.inthinc.pro.model.Account;
import com.inthinc.pro.service.AccountService;
import com.inthinc.pro.util.SecureAccountDAO;

public class AccountServiceImpl extends GenericServiceImpl<Account, SecureAccountDAO> implements AccountService {

    @Override
    public Response get() {
        return get(getDao().getAccountID());
    }

    

}
