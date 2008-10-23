package com.inthinc.pro.dao.service;

import java.util.Map;

import com.inthinc.pro.ProDAOException;

public interface SiloService extends DAOService
{

  //Methods related to the User type
  Map<String, Object> deleteUser(Integer userID) throws ProDAOException;
  
  Map<String, Object> getUser(Integer userID) throws ProDAOException;

  Map<String, Object> updateUser(Integer userID, Map<String, Object> userMap) throws ProDAOException;

  Map<String, Object> createUser(Integer acctID, Map<String, Object> userMap) throws ProDAOException;

}
