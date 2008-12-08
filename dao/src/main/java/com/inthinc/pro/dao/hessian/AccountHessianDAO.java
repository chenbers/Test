package com.inthinc.pro.dao.hessian;

import java.util.Map;

import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.User;

public class AccountHessianDAO extends GenericHessianDAO<Account, Integer> implements AccountDAO
{

    @Override
    @SuppressWarnings("unchecked")
    public Integer create(Integer id, Account entity)
    {
        // if silo id is not provided -- get one from the back end
        if (id == null)
        {
            id = getReturnKey(getSiloService().getNextSilo());
        }
        return super.create(id, entity);
    }

}
