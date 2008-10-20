package com.inthinc.pro.dao.hessian.proserver;

import java.util.Map;

import com.inthinc.pro.dao.hessian.exceptions.HessianException;

public interface CentralService extends HessianService
{

  Map<String, Object> getUserByAccountID(Integer accountID) throws HessianException;

  Map<String, Object> getUserIDByName(String username) throws HessianException;

  Map<String, Object> getUserIDByEmail(String email) throws HessianException;

}
