package com.inthinc.pro.backing.dao.validator;

import com.inthinc.pro.dao.ZoneDAO;
import com.inthinc.pro.model.Zone;

public class ZoneIDValidator extends DefaultValidator {

	ZoneDAO zoneDAO;
	

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
			Zone zone = zoneDAO.findByID(id);
			
			return (zone != null && isValidAccountID(zone.getAccountID()));
		}
		catch (Exception ex) {
			return false;
		}
	}

	@Override
	public String invalidMsg() {
		return "The zoneID is not valid.";
	}

	public ZoneDAO getZoneDAO() {
		return zoneDAO;
	}

	public void setZoneDAO(ZoneDAO zoneDAO) {
		this.zoneDAO = zoneDAO;
	}



}
