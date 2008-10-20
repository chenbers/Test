package com.inthinc.pro.dao.hessian.proserver;

import java.util.Map;

import com.inthinc.pro.dao.hessian.exceptions.HessianException;

public interface SiloService extends HessianService
{

  // Methods related to the User type
  Map<String, Object> getUser(Integer userID) throws HessianException;

  Map<String, Object> updateUser(Integer userID, Map<String, Object> userMap) throws HessianException;

  Map<String, Object> createUser(Integer acctID, Map<String, Object> userMap) throws HessianException;

}
