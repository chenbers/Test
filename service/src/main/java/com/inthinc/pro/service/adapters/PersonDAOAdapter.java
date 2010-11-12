/**
 * 
 */
package com.inthinc.pro.service.adapters;

import java.util.List;

import com.inthinc.pro.dao.GenericDAO;
import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.model.Person;

/**
 * Adapter for the Person resources.
 *  
 * @author dcueva
 */
public class PersonDAOAdapter extends BaseDAOAdapter<Person> {

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

}
