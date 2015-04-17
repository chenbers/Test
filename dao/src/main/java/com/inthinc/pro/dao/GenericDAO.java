package com.inthinc.pro.dao;

public interface GenericDAO<T, ID> {

  T findByID(ID id);
  ID create(ID id, T entity);
  Integer update(T entity);
  Integer deleteByID(ID id);
}
