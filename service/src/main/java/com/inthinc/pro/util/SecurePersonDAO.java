package com.inthinc.pro.util;

import java.util.List;

import org.jboss.resteasy.spi.NotFoundException;
import org.jboss.resteasy.spi.UnauthorizedException;
import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.model.Person;

public class SecurePersonDAO extends BaseSecureDAO {

    private PersonDAO personDAO;
    private SecureAddressDAO addressDAO;

    public boolean isAuthorized(Person person) {
        if (person != null) {
            if (!getAccountID().equals(person.getAcctID()))
                throw new NotFoundException("accountID not found: " + person.getAcctID());
           	if (!addressDAO.isAuthorized(person.getAddressID()))
           		return false;
            
            return true;

        }
        throw new UnauthorizedException("Person not found");
    }

    public boolean isAuthorized(Integer personID) {
        return isAuthorized(findByID(personID));
    }
    
    public Person findByID(Integer personID) {
        Person person = personDAO.findByID(personID);
        if (person == null || !person.getAcctID().equals(getAccountID()))
            throw new NotFoundException("personID not found: " + personID);
        return person;
    }

    public List<Person> getAll() {
        return personDAO.getPeopleInGroupHierarchy(getGroupID());
    }

    public Integer create(Person person) {
        if (isAuthorized(person))
            return personDAO.create(getAccountID(), person);

        return -1;
    }

    public Integer update(Person person) {
        if (isAuthorized(person))
            return personDAO.update(person);

        return -1;
    }

    public Integer deleteByID(Integer personID) {
        if (isAuthorized(personID))
            return personDAO.deleteByID(personID);

        return -1;
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
