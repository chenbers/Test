package com.inthinc.pro.backing.paging;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventCategory;
import com.inthinc.pro.model.EventMapper;
import com.inthinc.pro.table.BasePaginationTable;
import com.inthinc.pro.table.model.provider.EventPaginationTableDataProvider;

public abstract class PagingEventsBean extends BasePagingEventsBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6099930335506182237L;
	
    private static final Logger logger = Logger.getLogger(PagingEventsBean.class);
    private EventDAO                eventDAO;
	private EventPaginationTableDataProvider tableDataProvider;
	private BasePaginationTable<Event> table;
	
	public void init()
	{
		super.init();
		
		logger.debug("PagingEventsBean - constructor");
		
		
		table = new BasePaginationTable<Event>();
		tableDataProvider.setEventCategory(getEventCategory());
		table.initModel(tableDataProvider);
	}
    protected abstract EventCategory getEventCategory();

	public void refreshAction(){
		table.reset();
    }
	
    public void clearItemAction() {
    	
    	if(clearItem != null && clearItem.getForgiven().intValue()==0){
	        if (eventDAO.forgive(clearItem.getDriverID(), clearItem.getNoteID()) >= 1){
	        	table.resetPage();
	        }
    	}
    }
    
    public void includeEventAction() {
    	
    	if(clearItem != null && clearItem.getForgiven().intValue()==1){
	    	if (eventDAO.unforgive(clearItem.getDriverID(), clearItem.getNoteID())>= 1){
	    		table.resetPage();
	        }
    	}
    }

	public BasePaginationTable<Event> getTable() {
		return table;
	}

	public void setTable(BasePaginationTable<Event> table) {
		this.table = table;
	}

	public EventPaginationTableDataProvider getTableDataProvider() {
		return tableDataProvider;
	}

	public void setTableDataProvider(
			EventPaginationTableDataProvider eventPaginationTableDataProvider) {
		this.tableDataProvider = eventPaginationTableDataProvider;
	}
	
    public EventDAO getEventDAO()
    {
        return eventDAO;
    }

    public void setEventDAO(EventDAO eventDAO)
    {
        this.eventDAO = eventDAO;
    }
    
	@Override
	protected List<Event> getReportEvents() {
		int totalCount = getTableDataProvider().getRowCount();
		if (totalCount == 0)
			return new ArrayList<Event>();
		
		return getTableDataProvider().getItemsByRange(0, totalCount);
	}

}
