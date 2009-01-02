package com.inthinc.pro.dao;

import com.inthinc.pro.model.AlertCon;

public interface AlertContactDAO extends GenericDAO<AlertCon, Integer>
{
  Integer create(AlertCon entity);
  AlertCon findByUserID(Integer userID);
}
