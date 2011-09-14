package com.inthinc.pro.repository;

import java.util.List;

public interface GenericRepository<T extends Object, ID> {
	T save(T t);
	T findByID(ID id);
	List<T> findAll();
	//temp
	List<T> findAll(int page, int pageSize);
	T update(T t);
	void delete(T t);
	void deleteAll();
	Number count();
}
