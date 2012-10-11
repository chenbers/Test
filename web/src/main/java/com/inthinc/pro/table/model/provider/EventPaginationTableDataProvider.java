package com.inthinc.pro.table.model.provider;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventCategory;
import com.inthinc.pro.model.pagination.PageParams;

public class EventPaginationTableDataProvider  extends BaseNotificationPaginationDataProvider<Event> {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -2324384773462992656L;
	
    private static final Logger logger = Logger.getLogger(EventPaginationTableDataProvider.class);
    
	private EventDAO                eventDAO;
	private Integer 				groupID;
	private EventCategory			eventCategory;

	public EventPaginationTableDataProvider() {
	    
//		logger.info("EventPaginationTableDataProvider:constructor");
	}

	@Override
	public List<Event> getItemsByRange(int firstRow, int endRow) {
		
		if (endRow < 0) {
			return new ArrayList<Event>();
		}
		if (endDate == null || startDate == null) {
			initStartEndDates();
		}
		
		PageParams pageParams = new PageParams(firstRow, endRow, getSort(), getFilters());
		return eventDAO.getEventPage(groupID, startDate, endDate, EventDAO.INCLUDE_FORGIVEN, eventCategory.getNoteTypesInCategory(), pageParams);
	}

	@Override
	public int getRowCount() {
		
		if (groupID == null) {
			return 0;
		}
		initStartEndDates();
		return eventDAO.getEventCount(groupID, startDate, endDate, EventDAO.INCLUDE_FORGIVEN, eventCategory.getNoteTypesInCategory(), getFilters());
	}

	
	public EventDAO getEventDAO() {
		return eventDAO;
	}

	public void setEventDAO(EventDAO eventDAO) {
		this.eventDAO = eventDAO;
	}

	public Integer getGroupID() {
		return groupID;
	}

	public void setGroupID(Integer groupID) {
		this.groupID = groupID;
	}

	public EventCategory getEventCategory() {
		return eventCategory;
	}

	public void setEventCategory(EventCategory eventCategory) {
		this.eventCategory = eventCategory;
	}

}
