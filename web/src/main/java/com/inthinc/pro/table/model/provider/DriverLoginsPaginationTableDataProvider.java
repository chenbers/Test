package com.inthinc.pro.table.model.provider;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventCategory;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.TableFilterField;
import com.inthinc.pro.model.pagination.TableSortField;

public class DriverLoginsPaginationTableDataProvider  extends BaseNotificationPaginationDataProvider<Event> {

	
    private static final Logger logger = Logger.getLogger(DriverLoginsPaginationTableDataProvider.class);
    
	private EventDAO       eventDAO;
	private Integer        groupID;
	private EventCategory  eventCategory;
	
	private TableSortField sort;
	private List<TableFilterField> filter;
	private List<Event> data;
	

	public DriverLoginsPaginationTableDataProvider() {
	}
	
	public void loadData() {
	    initStartEndDates();
	    data = eventDAO.getEventsForGroupFromVehicles(groupID, eventCategory.getNoteTypesInCategory(), startDate, endDate);
	}

	@Override
	public List<Event> getItemsByRange(int firstRow, int endRow) {
		List<Event> results;
		if (endRow < 0) {
			return new ArrayList<Event>();
		}
		if (endDate == null || startDate == null) {
			initStartEndDates();
		}
		
		PageParams pageParams = new PageParams(firstRow, endRow, getSort(), getFilters());
		if(isRefreshNeeded())
		    loadData();
		results = new ArrayList<Event>(data);
		//TODO: jwimmer: sort based on getSort()
		System.out.println("getSort: "+getSort());
		//TODO: jwimmer: filter based on getFilters();
		System.out.println("getFilters: "+getFilters());
		
		return results.subList(firstRow, endRow);
	}

	@Override
	public int getRowCount() {
		
		if (groupID == null) {
			return 0;
		}
		
		//TODO: jwimmer: this should be the SIZE after filters are applied!
		if(data == null)
		    loadData();
		return data.size();
	}
	public boolean isRefreshNeeded() {
	    return true;//TODO: jwimmer: implemnt so that data is only refreshed when needed
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
