package com.inthinc.pro.dao.service;

public interface ServiceCreator<T extends DAOService>
{
  T getService();
}
