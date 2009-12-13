package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.ui.EventReportItem;
import com.inthinc.pro.backing.ui.TableColumn;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventCategory;
import com.inthinc.pro.model.EventMapper;
import com.inthinc.pro.model.TableType;

public abstract class BaseEventsBean extends BaseNotificationsBean<EventReportItem> implements TablePrefOptions<EventReportItem>
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -5169760158209219888L;

	static final Logger     logger                  = Logger.getLogger(EventsBean.class);

    private final static String COLUMN_LABEL_PREFIX = "notes_";

    private EventDAO                eventDAO;
    
    private EventCategory categoryFilter;
    private Event eventFilter;
    private Long eventFilterID;
    
    private TablePref<EventReportItem> tablePref;

    // package level -- so unit test can get it
    static final List<String>       AVAILABLE_COLUMNS;
    static
    {
        // available columns
        AVAILABLE_COLUMNS = new ArrayList<String>();
        AVAILABLE_COLUMNS.add("date");
        AVAILABLE_COLUMNS.add("group");
        AVAILABLE_COLUMNS.add("driver");
        AVAILABLE_COLUMNS.add("vehicle");
        AVAILABLE_COLUMNS.add("category");
        AVAILABLE_COLUMNS.add("detail");
        AVAILABLE_COLUMNS.add("clear");

    }
    
//	private EventsTableDataProvider eventsTableDataProvider;
//	private BasePaginationTable<EventReportItem> table;
    @Override
	public void clearData() {
    	
    	super.clearData();
    	setEventFilter(null);
    }
    
	@Override
	public int getDisplaySize() {

		return filteredTableData.size();
	}
    

    @Override
    public void initBean()
    {
        super.initBean();
        tablePref = new TablePref<EventReportItem>(this);
        
        
//        eventsTableDataProvider.setGroupID(this.getUser().getGroupID());
//        eventsTableDataProvider.setDaysBack(DAYS_BACK);
//        eventsTableDataProvider.setIncludeForgiven(1);
//		
//		PaginationTableDataModel<EventReportItem> model = new PaginationTableDataModel<EventReportItem>(eventsTableDataProvider);
//		table = new BasePaginationTable<EventReportItem>();
//		table.setModel(model);

    }
    
    /*
     *When the search button is actually clicked, we want to make sure we use what's in
     *the searchFor field
     */
    @Override
    public void searchAction()
    {
        super.searchAction();
        setEventFilter(null);  
    }

    public TablePref<EventReportItem> getTablePref()
    {
        return tablePref;
    }

    public void setTablePref(TablePref<EventReportItem> tablePref)
    {
        this.tablePref = tablePref;
    }

    public Map<String, TableColumn> getTableColumns()
    {
        return tablePref.getTableColumns();
    }
    public void setTableColumns(Map<String, TableColumn> tableColumns)
    {
        tablePref.setTableColumns(tableColumns);
    }

    @Override
    protected void filterTableData()
    {
        if (tableData == null)
        {
            initTableData();
        }

        setFilteredTableData(tableData); 
        if (getCategoryFilter() != null)
        {    
            List<Integer> validEventTypes = EventMapper.getEventTypesInCategory(getCategoryFilter());
            if (validEventTypes != null)
            {
                filteredTableData = new ArrayList<EventReportItem>();
        
                for (EventReportItem item : tableData)
                {
                    if (validEventTypes.contains(item.getEvent().getType()))
                    {
                        filteredTableData.add(item);
                    }
                }
            }
        }
        
        //Filter if search is based on single event
        if (getEventFilter() != null)
        {    
            filteredTableData = new ArrayList<EventReportItem>();
            boolean found = false;
            for (EventReportItem item : tableData)
            {
                if (item.getEvent().getNoteID().equals(eventFilter.getNoteID()))
                {
                    filteredTableData.add(item);
                    found = true;
                    break;
                }
            }
            if (!found) {
            	Event event = getEventFilter();
            	if (event != null) {
                    fillInDriver(event);
                    fillInVehicle(event);
                    filteredTableData.add(new EventReportItem(event, null, getGroupHierarchy(),getMeasurementType()));
	        	}
            }
        }
        
        //Filter if search is based on group.
//        if (!getEffectiveGroupId().equals(getUser().getGroupID()))
//        {
//            filteredTableData = new ArrayList<EventReportItem>();
//            
//            for (EventReportItem item : tableData)
//            {
//                if (item.getEvent().getGroupID().equals(getEffectiveGroupId()))
//                {
//                    filteredTableData.add(item);
//                }
//            }
//        }
        
        if (searchCoordinationBean.isGoodSearch())
        {
            final ArrayList<EventReportItem> searchTableDataResult = new ArrayList<EventReportItem>();
            searchTableDataResult.addAll(filteredTableData);
            tablePref.filter(searchTableDataResult, searchCoordinationBean.getSearchFor(), true);
            setFilteredTableData(searchTableDataResult);
        }
        setMaxCount(filteredTableData.size());
        setStart(filteredTableData.size() > 0 ? 1 : 0);
        setEnd(filteredTableData.size() > getNumRowsPerPg() ? getNumRowsPerPg() : filteredTableData.size());
        setPage(1);
        tableData = filteredTableData;
    }

    @Override
    protected void filterTableDataWithoutSearch()
    {
        setFilteredTableData(tableData); 
        if (getCategoryFilter() != null)
        {    
            List<Integer> validEventTypes = EventMapper.getEventTypesInCategory(getCategoryFilter());
            if (validEventTypes != null)
            {
                filteredTableData = new ArrayList<EventReportItem>();
        
                for (EventReportItem item : tableData)
                {
                    if (validEventTypes.contains(item.getEvent().getType()))
                    {
                        filteredTableData.add(item);
                    }
                }
            }
        }
        
        //Filter if search is based on single event
        if (getEventFilter() != null)
        {    
            filteredTableData = new ArrayList<EventReportItem>();
            boolean found = false;
            for (EventReportItem item : tableData)
            {
                if (item.getEvent().getNoteID().equals(eventFilter.getNoteID()))
                {
                    filteredTableData.add(item);
                    found = true;
                    break;
                }
            }
            if (!found) {
            	Event event = getEventFilter();
            	if (event != null) {
                    fillInDriver(event);
                    fillInVehicle(event);
                    filteredTableData.add(new EventReportItem(event, null, getGroupHierarchy(),getMeasurementType()));
	        	}
            }
        }
        
        //Filter if search is based on group.
//        if (!getEffectiveGroupId().equals(getUser().getGroupID()))
//        {
//            filteredTableData = new ArrayList<EventReportItem>();
//            
//            for (EventReportItem item : tableData)
//            {
//                if (item.getEvent().getGroupID().equals(getEffectiveGroupId()))
//                {
//                    filteredTableData.add(item);
//                }
//            }
//        }
        
        setMaxCount(filteredTableData.size());
        setStart(filteredTableData.size() > 0 ? 1 : 0);
        setEnd(filteredTableData.size() > getNumRowsPerPg() ? getNumRowsPerPg() : filteredTableData.size());
        setPage(1);
        tableData = filteredTableData;
    }
    private void initTableData()
    {
        setFilteredTableData(null);

        List<EventReportItem> eventReportItemList = new ArrayList<EventReportItem>();
        
        List<Event> eventList = new ArrayList<Event>();
        if (getSearchCoordinationBean().isGoodGroupId())
        {

        	eventList = getEventsForGroup(getSearchCoordinationBean().getGroup().getGroupID());
        }

//        if (getSelectedGroupID() != null) {
//            eventList = getEventsForGroup(getSelectedGroupID());
//        }
        
        for (Event event : eventList)
        {
            fillInDriver(event);
            fillInVehicle(event);
            eventReportItemList.add(new EventReportItem(event, null, getGroupHierarchy(),getMeasurementType()));
        }
        Collections.sort(eventReportItemList);
        Collections.reverse(eventReportItemList);
        setTableData(eventReportItemList);

    }

    protected abstract List<Event> getEventsForGroup(Integer groupID);

//    public void setTableData(List<EventReportItem> tableData)
//    {
//        this.tableData = tableData;
//    }
    
    public void clearItemAction()
    {
    	if(clearItem.getEvent().getForgiven().intValue()==0){
	        if (eventDAO.forgive(clearItem.getEvent().getDriverID(), clearItem.getEvent().getNoteID()) >= 1){
	    		clearData();
	        }
    	}
//        tableData.remove(clearItem);
//        filteredTableData.remove(clearItem);
//        maxCount--;
//        if (end > maxCount)
//            end = maxCount;
    }
    
    public void includeEventAction(){
    	
    	if(clearItem.getEvent().getForgiven().intValue()==1){
	    	if (eventDAO.unforgive(clearItem.getEvent().getDriverID(), clearItem.getEvent().getNoteID())>= 1){
	    		clearData();
	        }
    	}
    	
    }
    public EventCategory getCategoryFilter()
    {
        return categoryFilter;
    }

//    private void reinit()
//    {
//    	setTableData(null);
//        setFilteredTableData(null);
//        setStart(null);
//        setEnd(null);
//        setMaxCount(null);
//        init();
//    }
    public void setCategoryFilter(EventCategory categoryFilter)
    {
//        reinit();
        this.eventFilter = null;
        this.categoryFilter = categoryFilter;
    }


    public Event getEventFilter()
    {
        return eventFilter;
    }

    public void setEventFilter(Event eventFilter)
    {
        // force table data to reinit
//        reinit();
        this.categoryFilter = null;
        this.eventFilter = eventFilter;
        if (eventFilter != null) {
        	this.getSearchCoordinationBean().setSearchFor("");
        }
    }
    
    @Override
    public String fieldValue(EventReportItem item, String column)
    {
        if ("driver".equals(column))
            column = "driverName";
        else if ("vehicle".equals(column))
            column = "vehicleName";
        else if ("clear".equals(column))
            return "";
        return TablePref.fieldValue(item, column);
    }

    public void showAllAction()
    {
        setCategoryFilter(null);
        setEventFilter(null);
        
        filterTableDataWithoutSearch();
    }


    // TablePrefOptions interface
    @Override
    public List<String> getAvailableColumns()
    {
        return AVAILABLE_COLUMNS;
    }

    @Override
    public Map<String, Boolean>  getDefaultColumns()
    {
        HashMap<String, Boolean> columns = new HashMap<String, Boolean>();
        for (String col : AVAILABLE_COLUMNS)
            columns.put(col, true);
        return columns;

    }

    @Override
    public String getColumnLabelPrefix()
    {
        return COLUMN_LABEL_PREFIX;
    }

    @Override
    public TableType getTableType()
    {
        return TableType.EVENTS;
    }

    @Override
    public Integer getUserID()
    {
        return getUser().getUserID();
    }

    public EventDAO getEventDAO()
    {
        return eventDAO;
    }

    public void setEventDAO(EventDAO eventDAO)
    {
        this.eventDAO = eventDAO;
    }

    public void setEventFilterID(Long eventFilterID)
    {
        if(eventFilterID != null && eventFilterID != 0)
        {
            Event event = eventDAO.findByID(eventFilterID);
            event.setNoteID(eventFilterID);  //TODO Got to find out why noteID is not being set.
            setEventFilter(event);
            this.eventFilterID = eventFilterID;
        }else
        {
            this.eventFilterID = null;
            setEventFilter(null);
        }
        
    }

    public Long getEventFilterID()
    {
        return eventFilterID;
    }
	public void setTableData(List<EventReportItem> tableData) {
		this.tableData = tableData;
	}

    public List<EventReportItem> getTableData() {
    	if (tableData==null) fetchData();
		return tableData;
	}

    public List<EventReportItem> getFilteredTableData() {
		return filteredTableData;
	}

	public void setFilteredTableData(List<EventReportItem> filteredTableData) {
		this.filteredTableData = filteredTableData;
	}



/*
	public EventsTableDataProvider getEventsTableDataProvider() {
		return eventsTableDataProvider;
	}


	public void setEventsTableDataProvider(
			EventsTableDataProvider eventsTableDataProvider) {
		this.eventsTableDataProvider = eventsTableDataProvider;
	}


	public BasePaginationTable<EventReportItem> getTable() {
		return table;
	}


	public void setTable(BasePaginationTable<EventReportItem> table) {
		this.table = table;
	}
*/

}

