package com.inthinc.pro.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.model.Person;
import com.inthinc.pro.service.PersonService;
import com.inthinc.pro.util.SecurePersonDAO;

public class PersonServiceImpl implements PersonService {

    private SecurePersonDAO personDAO;

    public List<Person> getAll() {
    	return personDAO.getAll();
    }

    public Person get(Integer personID) {
        return personDAO.findByID(personID);
    }

    public Integer create(Person person) {
        return personDAO.create(person);
    }

    public Integer update(Person person) {
        return personDAO.update(person);
    }

    public Integer delete(Integer personID) {
        return personDAO.deleteByID(personID);
    }

    public List<Integer> create(List<Person> persons) {
        List<Integer> results = new ArrayList<Integer>();
        for (Person person : persons)
            results.add(create(person));
        return results;
    }

    public List<Integer> update(List<Person> persons) {
        List<Integer> results = new ArrayList<Integer>();
        for (Person person : persons)
            results.add(update(person));
        return results;
    }

    public List<Integer> delete(List<Integer> personIDs) {
        List<Integer> results = new ArrayList<Integer>();
        for (Integer id : personIDs) {
            results.add(delete(id));
        }
        return results;
    }

    public void setPersonDAO(SecurePersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    public SecurePersonDAO getPersonDAO() {
        return personDAO;
    }

}
