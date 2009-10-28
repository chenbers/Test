package com.inthinc.pro.util;

import java.util.List;

import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.model.Person;

public class SecurePersonDAO extends SecureDAO<Person> {

    private PersonDAO personDAO;
    private SecureAddressDAO addressDAO;

    @Override
    public boolean isAuthorized(Person person) {
        if (person != null) {
            if(getUser().getRole().equals(inthincRole))
                return true;
            if (!getAccountID().equals(person.getAcctID()))
                return false;
            if (!addressDAO.isAuthorized(person.getAddressID()))
                return false;

            return true;
        }
        return false;
    }

    public boolean isAuthorized(Integer personID) {
        return isAuthorized(findByID(personID));
    }

    @Override
    public Person findByID(Integer personID) {
        Person person = personDAO.findByID(personID);
        if (isAuthorized(person))
            return person;
        return null;
    }

    @Override
    public List<Person> getAll() {
        return personDAO.getPeopleInGroupHierarchy(getGroupID());
    }

    @Override
    public Integer create(Person person) {
        if (isAuthorized(person))
            return personDAO.create(getAccountID(), person);

        return null;
    }

    @Override
    public Integer update(Person person) {
        if (isAuthorized(person))
            return personDAO.update(person);

        return 0;
    }

    @Override
    public Integer delete(Integer personID) {
        if (isAuthorized(personID))
            return personDAO.deleteByID(personID);

        return 0;
    }

    public PersonDAO getPersonDAO() {
        return personDAO;
    }

    public void setPersonDAO(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    public SecureAddressDAO getAddressDAO() {
        return addressDAO;
    }

    public void setAddressDAO(SecureAddressDAO addressDAO) {
        this.addressDAO = addressDAO;
    }
}
