package com.inthinc.pro.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.service.PersonService;

public class PersonServiceImpl extends BaseService implements PersonService {

    private PersonDAO personDAO;

    public List<Person> getAll() {
        return personDAO.getPeopleInGroupHierarchy(securityBean.getGroupID());
    }

    public Person get(Integer personID) {
        Person person = securityBean.getPerson(personID);
        if (securityBean.isAuthorized(person))
            return person;

        return null;
    }

    public Integer add(Person person) {
        if (securityBean.isAuthorized(person))
            return personDAO.create(getAccountID(), person);

        return -1;
    }

    public Integer update(Person person) {
        if (securityBean.isAuthorized(person))
            return personDAO.update(person);

        return -1;
    }

    public Integer delete(Integer personID) {
        if (securityBean.isAuthorizedByPersonID(personID))
            return personDAO.deleteByID(personID);

        return -1;
    }

    public List<Integer> add(List<Person> persons) {
        List<Integer> results = new ArrayList<Integer>();
        for (Person person : persons)
            results.add(add(person));
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

    public void setPersonDAO(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    public PersonDAO getPersonDAO() {
        return personDAO;
    }

}
