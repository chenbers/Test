package com.inthinc.pro.table.model.provider;

import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventCategory;
import com.inthinc.pro.model.EventMapper;
import com.inthinc.pro.model.pagination.PageParams;

public class EventPaginationTableDataProvider  extends GenericPaginationTableDataProvider<Event> {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -2324384773462992656L;
	
    private static final Logger logger = Logger.getLogger(EventPaginationTableDataProvider.class);
    
	private EventDAO                eventDAO;
	private Integer 				groupID;
	private Integer					daysBack;
	private EventCategory			eventCategory;
	

	public EventPaginationTableDataProvider() {
	    
		logger.info("EventPaginationTableDataProvider:constructor");
	}

	@Override
	public List<Event> getItemsByRange(int firstRow, int endRow) {
		
		PageParams pageParams = new PageParams(firstRow, endRow, getSort(), getFilters());
		return eventDAO.getEventPage(groupID, getDaysBack(), EventDAO.INCLUDE_FORGIVEN, EventMapper.getEventTypesInCategory(eventCategory), pageParams);
	}

	@Override
	public int getRowCount() {

		return eventDAO.getEventCount(groupID, getDaysBack(), EventDAO.INCLUDE_FORGIVEN, EventMapper.getEventTypesInCategory(eventCategory), getFilters());
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

	public Integer getDaysBack() {
		if (daysBack == null)
			return Integer.valueOf(0);
		return daysBack;
	}

	public void setDaysBack(Integer daysBack) {
		this.daysBack = daysBack;
	}

	public EventCategory getEventCategory() {
		return eventCategory;
	}

	public void setEventCategory(EventCategory eventCategory) {
		this.eventCategory = eventCategory;
	}

}
