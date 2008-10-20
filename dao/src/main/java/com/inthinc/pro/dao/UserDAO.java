package com.inthinc.pro.dao;

import com.inthinc.pro.model.User;

public interface UserDAO extends GenericDAO<User, Integer>
{
  User findByEmail(String email);

  User findByUserName(String username);
}
