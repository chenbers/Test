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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected GenericDAO<Person, Integer> getDAO() {
		return personDAO;
	}

	@Override
	protected Integer getResourceID(Person person) {
		return person.getPersonID();
	}

}
