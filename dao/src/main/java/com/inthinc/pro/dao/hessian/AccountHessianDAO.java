package com.inthinc.pro.dao.hessian;


import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Silo;


public class AccountHessianDAO extends GenericHessianDAO<Account, Integer> implements AccountDAO
{

    @Override
    @SuppressWarnings("unchecked")
    public Integer create(Account entity)
    {
        // if silo id is not provided -- get one from the back end
        Silo silo = getMapper().convertToModelObject(getSiloService().getNextSilo(), Silo.class);
        return super.create(silo.getSiloID(), entity);
    }

}
