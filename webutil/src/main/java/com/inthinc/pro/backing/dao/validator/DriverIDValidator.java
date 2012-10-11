package com.inthinc.pro.backing.dao.validator;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.model.Driver;

public class DriverIDValidator extends DefaultValidator {

	DriverDAO driverDAO;
	

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
			Driver driver = driverDAO.findByID(id);
			
			return (driver != null && isValidAccountID(driver.getPerson().getAcctID()));
		}
		catch (Exception ex) {
			return false;
		}
	}

	@Override
	public String invalidMsg() {
		return "The driverID is not valid.";
	}

	public DriverDAO getDriverDAO() {
		return driverDAO;
	}

	public void setDriverDAO(DriverDAO driverDAO) {
		this.driverDAO = driverDAO;
	}


}
