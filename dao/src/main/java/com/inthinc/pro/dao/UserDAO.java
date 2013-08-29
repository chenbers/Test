package com.inthinc.pro.dao;

import java.util.List;

import com.inthinc.pro.model.User;

public interface UserDAO extends GenericDAO<User, Integer>
{
  User findByUserName(String username);
  
  List<User> getUsersInGroupHierarchy(Integer groupID);

  List<User> getUsersByGroupId(Integer groupID);

  User getUserByPersonID(Integer personID);
}
