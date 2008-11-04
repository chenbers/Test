package com.inthinc.pro.dao.hessian.proserver;

import java.util.List;
import java.util.Map;

import com.inthinc.pro.ProDAOException;

public interface CentralService extends HessianService
{
	
  Map<String, Object> getUserByAccountID(Integer accountID) throws ProDAOException;

  Map<String, Object> getUserIDByName(String username) throws ProDAOException;

  Map<String, Object> getUserIDByEmail(String email) throws ProDAOException;
  
}
