package com.inthinc.pro.service.adapters;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inthinc.pro.dao.GenericDAO;
import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.model.Person;

/**
 * Adapter for the Person resources.
 *  
 * @author dcueva
 */
@Component
public class PersonDAOAdapter extends BaseDAOAdapter<Person> {

    @Autowired
    private PersonDAO personDAO;
    
	@Override
	public List<Person> getAll() {
        return personDAO.getPeopleInGroupHierarchy(getGroupID());
	}

	@Override
	protected GenericDAO<Person, Integer> getDAO() {
		return personDAO;
	}

	@Override
	protected Integer getResourceID(Person person) {
		return person.getPersonID();
	}
	
	// Specialized methods ----------------------------------------------------
	
    public Integer create(Integer accountID, Person person) {
        return personDAO.create(accountID, person);
    }

	// Getters and setters -----------------------------------------------------
    
	/**
	 * @return the personDAO
	 */
	public PersonDAO getPersonDAO() {
		return personDAO;
	}

	/**
	 * @param personDAO the personDAO to set
	 */
	public void setPersonDAO(PersonDAO personDAO) {
		this.personDAO = personDAO;
	}	

    
    

}
