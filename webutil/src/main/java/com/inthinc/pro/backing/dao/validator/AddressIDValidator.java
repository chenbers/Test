package com.inthinc.pro.backing.dao.validator;

import com.inthinc.pro.dao.AddressDAO;
import com.inthinc.pro.model.Address;

public class AddressIDValidator extends DefaultValidator {

	AddressDAO addressDAO;
	

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
			Address address = addressDAO.findByID(id);
			
			return (address != null && isValidAccountID(address.getAccountID()));
		}
		catch (Exception ex) {
			return false;
		}
	}

	@Override
	public String invalidMsg() {
		return "The addressID is not valid.";
	}


	public AddressDAO getAddressDAO() {
		return addressDAO;
	}

	public void setAddressDAO(AddressDAO addressDAO) {
		this.addressDAO = addressDAO;
	}
}
