package com.inthinc.pro.backing.dao.validator;

import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.model.Person;

public class PersonIDValidator extends DefaultValidator {

	PersonDAO personDAO;
	

	@Override
	public boolean isValid(String input) {
		
		Integer id = null;
		try {
			id = Integer.valueOf(input);
		}
		catch (NumberFormatException ex) {
			return false;
		}
		
		try {
			Person person = personDAO.findByID(id);
			
			return (person != null && isValidAccountID(person.getAcctID()));
		}
		catch (Exception ex) {
			return false;
		}
	}

	@Override
	public String invalidMsg() {
		return "The personID is not valid.";
	}

	public PersonDAO getPersonDAO() {
		return personDAO;
	}

	public void setPersonDAO(PersonDAO personDAO) {
		this.personDAO = personDAO;
	}


}
