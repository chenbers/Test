package com.inthinc.pro.dao.hessian;

import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.dao.hessian.proserver.CentralService;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.User;

public class AccountHessianDAO extends GenericHessianDAO<Account, Integer, CentralService> implements AccountDAO
{


  @Override
  public Account getAccountByUser(User user)
  {
    // TODO Auto-generated method stub
    return null;
  }
}
