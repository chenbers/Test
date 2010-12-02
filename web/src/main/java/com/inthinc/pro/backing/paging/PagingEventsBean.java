package com.inthinc.pro.backing.paging;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.joda.time.DateTimeZone;

import com.inthinc.pro.backing.LocaleBean;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventCategory;
import com.inthinc.pro.model.event.StatusEvent;
import com.inthinc.pro.model.EventReportItem;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.pagination.SortOrder;
import com.inthinc.pro.model.pagination.TableFilterField;
import com.inthinc.pro.model.pagination.TableSortField;
import com.inthinc.pro.table.BasePaginationTable;
import com.inthinc.pro.table.model.provider.EventPaginationTableDataProvider;
import com.inthinc.pro.util.MessageUtil;

public abstract class PagingEventsBean extends BasePagingNotificationsBean<Event> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6099930335506182237L;
	
    private static final Logger logger = Logger.getLogger(PagingEventsBean.class);
	private EventPaginationTableDataProvider tableDataProvider;
	private BasePaginationTable<Event> table;

	private final static String linkFilterKeys[] = {
		"noteID",
		"driverID" 
	};
	private Map<String, Object> linkFilters;
    

    
    @Override
    public void init()
    {
        super.init();
        linkFilters = new HashMap<String, Object>();
    		
		logger.debug("PagingEventsBean - constructor");
		
		
		table = new BasePaginationTable<Event>();
		tableDataProvider.setDateTimeZone(DateTimeZone.forTimeZone(getUser().getPerson().getTimeZone()));
		tableDataProvider.setEventCategory(getEventCategory());
        tableDataProvider.setSort(new TableSortField(SortOrder.DESCENDING, "time"));
		table.initModel(tableDataProvider);
    }
    
    
    @Override
    protected List<EventReportItem> getReportTableData()
    {
        List<EventReportItem> eventReportItemList = new ArrayList<EventReportItem>();
        
        List<Event> eventList = new ArrayList<Event>();
       	eventList = getReportEvents();
        
       	MeasurementType measurementType = this.getMeasurementType();
        String mphString = MessageUtil.getMessageString(measurementType.toString()+"_mph");
        String dateFormatStr = MessageUtil.getMessageString("dateTimeFormat", LocaleBean.getCurrentLocale());

        for (Event event : eventList)
        {
            String detailsFormatStr = MessageUtil.getMessageString("redflags_details" + event.getEventType());
            if (event.getDriverName() == null || event.getDriverName().isEmpty()) {
            	event.setDriverName(MessageUtil.getMessageString("unknown_driver"));
            }
            String statusString = null;
            if (Arrays.asList(event.getClass().getInterfaces()).contains(StatusEvent.class)) {
                statusString = MessageUtil.getMessageString(((StatusEvent)event).getStatusMessageKey());
            }
            eventReportItemList.add(new EventReportItem(event, getMeasurementType(), dateFormatStr, detailsFormatStr, (statusString == null) ? mphString : statusString, LocaleBean.getCurrentLocale()));
            
        }
        
        return eventReportItemList;
    }

    // called from links on team page
	public void allAction(){
		for (String filterKey : linkFilterKeys) {
			if (linkFilters.get(filterKey) != null)
			{
				tableDataProvider.addFilterField(new TableFilterField(filterKey, linkFilters.get(filterKey)));
			}
		}
		table.reset();
	}

	public void refreshAction(){
		if (tableDataProvider.getFilters() != null) {
			for (String filterKey : linkFilterKeys) {
				TableFilterField linkFilter = null;
				for (TableFilterField filterField : tableDataProvider.getFilters())
					if (filterField.getField().equals(filterKey)) {
						linkFilter = filterField;
						break;
					}
				
				if (linkFilter != null) {
					tableDataProvider.getFilters().remove(linkFilter);
					linkFilter = null;
				}
				
			}
		}
		
		table.reset();
    }
	
	@Override
	public void refreshPage(){
		table.resetPage();
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
    
	protected List<Event> getReportEvents() {
		int totalCount = getTableDataProvider().getRowCount();
		if (totalCount == 0)
			return new ArrayList<Event>();
		
		return getTableDataProvider().getItemsByRange(0, totalCount);
	}


	public Map<String, Object> getLinkFilters() {
		return linkFilters;
	}

	public void setLinkFilters(Map<String, Object> linkFilters) {
		this.linkFilters = linkFilters;
	}

    protected abstract EventCategory getEventCategory();


}
