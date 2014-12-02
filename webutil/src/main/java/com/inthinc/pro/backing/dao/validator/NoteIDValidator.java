package com.inthinc.pro.backing.dao.validator;

import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.Group;

public class NoteIDValidator extends DefaultValidator {

	EventDAO eventDAO;
	GroupDAO groupDAO;
	


	@Override
	public boolean isValid(String input) {
		
		Long id = null;
		try {
			id = Long.valueOf(input);
		}
		catch (NumberFormatException ex) {
			return false;
		}
		
		try {
			Event event = eventDAO.findByID(id);
			
			if (event == null)
				return false;
			
			Integer groupID = event.getGroupID();
			Group group = groupDAO.findByID(groupID);
			
			return (group != null && isValidAccountID(group.getAccountID()));
		}
		catch (Exception ex) {
			return false;
		}
	}

	@Override
	public String invalidMsg() {
		return "The noteID is not valid.";
	}

	public EventDAO getEventDAO() {
		return eventDAO;
	}

	public void setEventDAO(EventDAO eventDAO) {
		this.eventDAO = eventDAO;
	}
	
	public GroupDAO getGroupDAO() {
		return groupDAO;
	}

	public void setGroupDAO(GroupDAO groupDAO) {
		this.groupDAO = groupDAO;
	}


}
