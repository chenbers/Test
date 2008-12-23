package com.inthinc.pro.dao;

import com.inthinc.pro.model.AlertContact;

public interface AlertContactDAO extends GenericDAO<AlertContact, Integer>
{
  Integer create(AlertContact entity);
  AlertContact findByUserID(Integer userID);
}
