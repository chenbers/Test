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
            if (isInthincUser() || (getAccountID().equals(person.getAcctID()) && addressDAO.isAuthorized(person.getAddressID())) )
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

    public Integer create(Integer accountID, Person person) {
        if (isInthincUser())
            return personDAO.create(accountID, person);
        return null;
    }

    @Override
    public Person update(Person person) {
        if (isAuthorized(person) && personDAO.update(person) != 0)
            return personDAO.findByID(person.getPersonID());
        return null;
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
