package com.inthinc.pro.repository.jpa;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.inthinc.pro.repository.GenericRepository;


public class GenericRepositoryImpl<T,ID> implements GenericRepository<T,ID>{


    @PersistenceContext
    EntityManager entityManager;
    
    private Class<T> domainClass; //The Class of the domain model object
    
    @SuppressWarnings("unchecked")
    public GenericRepositoryImpl() {
        this.domainClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
    
	public T save(T t) {
		entityManager.persist(t);	
		entityManager.flush();
		return t;
	}

	public T findByID(ID id) {
		return entityManager.find(domainClass, id);
	}

	@SuppressWarnings("unchecked")
    public List<T> findAll() {
        return entityManager.createQuery("FROM " + domainClass.getName()).getResultList();
	}
	
	//Temp
	@SuppressWarnings("unchecked")
    public List<T> findAll(int page, int pageSize) {
	    Query query = entityManager.createQuery("SELECT t FROM " + domainClass.getName() + " t");
	    query.setFirstResult(page * pageSize);
	    query.setMaxResults(pageSize);
	    return query.getResultList();
	}
	
	
	public T update(T t) {
		T updatedt =  entityManager.merge(t);
	    return updatedt;
	}

	public void delete(T t) {
		entityManager.remove(t);
	}

	public void deleteAll() {

		List<T> all = findAll();
		
		for(T t : all){
			delete(t);
		}
	}

	public Number count() {
		Query query = entityManager.createQuery("select count(*) from " + domainClass.getName());
		return (Number) query.getSingleResult();
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
