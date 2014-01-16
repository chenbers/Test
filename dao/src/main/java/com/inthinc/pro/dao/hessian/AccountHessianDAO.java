package com.inthinc.pro.dao.hessian;


import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.NotImplementedException;

import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Silo;


public class AccountHessianDAO extends GenericHessianDAO<Account, Integer> implements AccountDAO
{

    @Override
    public Integer create(Account entity)
    {
        // if silo id is not provided -- get one from the back end
        Silo silo = getMapper().convertToModelObject(getSiloService().getNextSilo(), Silo.class);
        return super.create(silo.getSiloID() << 24, entity);
    }
    
    @Override
    public List<Account> getAllAcctIDs()
    {
        try
        {
            List<Account> accountList = getMapper().convertToModelObject(getSiloService().getAllAcctIDs(),Account.class);
            return accountList;
        }
        catch(EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
        
    }

    @Override
    public List<Long> getAllValidAcctIDs() {
        throw new NotImplementedException();
    }
}
