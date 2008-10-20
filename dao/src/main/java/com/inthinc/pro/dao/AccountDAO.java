package com.inthinc.pro.dao;

import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.User;

public interface AccountDAO extends GenericDAO<Account, Integer>
{
  Account getAccountByUser(User user);
}
