package com.inthinc.pro.dao.service;

import java.util.Map;

import com.inthinc.pro.ProDAOException;

public interface CentralService extends DAOService
{

  Map<String, Object> getUserByAccountID(Integer accountID) throws ProDAOException;

  Map<String, Object> getUserIDByName(String username) throws ProDAOException;

  Map<String, Object> getUserIDByEmail(String email) throws ProDAOException;
  
  Map<String, Object> getOverallScore(Integer userID, Integer levelID, Integer startDate, Integer endDate ) throws ProDAOException;

}
