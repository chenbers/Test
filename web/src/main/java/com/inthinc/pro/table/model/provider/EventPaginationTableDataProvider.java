package com.inthinc.pro.table.model.provider;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.util.DateUtil;
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
    private Date endDate;
    private Date startDate;

	

	public EventPaginationTableDataProvider() {
	    
		logger.info("EventPaginationTableDataProvider:constructor");
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
		return eventDAO.getEventPage(groupID, startDate, endDate, EventDAO.INCLUDE_FORGIVEN, EventMapper.getEventTypesInCategory(eventCategory), pageParams);
	}

	@Override
	public int getRowCount() {
		
		if (groupID == null)
			return 0;

		initStartEndDates();
		return eventDAO.getEventCount(groupID, startDate, endDate, EventDAO.INCLUDE_FORGIVEN, EventMapper.getEventTypesInCategory(eventCategory), getFilters());
	}

	private void initStartEndDates() {
	    endDate = new Date();
	    startDate = DateUtil.getDaysBackDate(endDate, getDaysBack());
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
			return Integer.valueOf(1);
		return daysBack;
	}

	public void setDaysBack(Integer daysBack) {
		this.daysBack = daysBack;
		this.startDate = null;
		this.endDate = null;
	}

	public EventCategory getEventCategory() {
		return eventCategory;
	}

	public void setEventCategory(EventCategory eventCategory) {
		this.eventCategory = eventCategory;
	}

}
