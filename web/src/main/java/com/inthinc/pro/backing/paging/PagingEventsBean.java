package com.inthinc.pro.backing.paging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.LocaleBean;
import com.inthinc.pro.backing.TablePref;
import com.inthinc.pro.backing.TablePrefOptions;
import com.inthinc.pro.backing.ui.TableColumn;
import com.inthinc.pro.dao.TablePreferenceDAO;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventCategory;
import com.inthinc.pro.model.EventReportItem;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.model.pagination.SortOrder;
import com.inthinc.pro.model.pagination.TableFilterField;
import com.inthinc.pro.model.pagination.TableSortField;
import com.inthinc.pro.table.BasePaginationTable;
import com.inthinc.pro.table.model.provider.EventPaginationTableDataProvider;
import com.inthinc.pro.util.MessageUtil;

public abstract class PagingEventsBean extends BasePagingNotificationsBean<Event> implements TablePrefOptions<Event> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6099930335506182237L;
	
    private static final Logger logger = Logger.getLogger(PagingEventsBean.class);
	private EventPaginationTableDataProvider tableDataProvider;
	private BasePaginationTable<Event> table;

	private Long eventFilterID;
    private final static String SINGLE_NOTE_FILTER_FIELD = "noteID";
    
	

	private final static String COLUMN_LABEL_PREFIX = "notes_";
	private TablePreferenceDAO       tablePreferenceDAO;
    private TablePref<Event> tablePref;

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
    
    @Override
    public void init()
    {
        super.init();
        tablePref = new TablePref<Event>(this);
    		
		logger.debug("PagingEventsBean - constructor");
		
		
		table = new BasePaginationTable<Event>();
		tableDataProvider.setEventCategory(getEventCategory());
        tableDataProvider.setSort(new TableSortField(SortOrder.DESCENDING, "time"));
		table.initModel(tableDataProvider);
    }
    
    public TablePref<Event> getTablePref()
    {
        return tablePref;
    }

    public void setTablePref(TablePref<Event> tablePref)
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

	public void setTablePreferenceDAO(TablePreferenceDAO tablePreferenceDAO) {
	    this.tablePreferenceDAO = tablePreferenceDAO;
	}


    // TablePrefOptions<Event> implementation
    @Override
    public String getColumnLabelPrefix()
    {
        return COLUMN_LABEL_PREFIX;
    }

    @Override
    public TablePreferenceDAO getTablePreferenceDAO() {
	    return tablePreferenceDAO;
	}

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
    public TableType getTableType()
    {
        return TableType.EVENTS;
    }

    @Override
    public Integer getUserID()
    {
        return getUser().getUserID();
    }
    
    @Override
    public String fieldValue(Event item, String column)
    {
        if ("driver".equals(column))
            column = "driverName";
        else if ("vehicle".equals(column))
            column = "vehicleName";
        else if ("clear".equals(column))
            return "";
        return TablePref.fieldValue(item, column);
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

            eventReportItemList.add(new EventReportItem(event, getMeasurementType(), dateFormatStr, detailsFormatStr, mphString));
        }
        Collections.sort(eventReportItemList);
        Collections.reverse(eventReportItemList);
        
        return eventReportItemList;
    }

    // called from links on team page
	public void allAction(){
		this.tableDataProvider.setDaysBack(MAX_DAYS_BACK);
		if (getEventFilterID() != null)
		{
		//	logger.debug("setting noteID filter " + getEventFilterID());
			tableDataProvider.addFilterField(new TableFilterField(SINGLE_NOTE_FILTER_FIELD, getEventFilterID()));
		}
		table.reset();
    }

	public void refreshAction(){
		if (tableDataProvider.getFilters() != null) {
			TableFilterField singleNoteFilter = null;
			for (TableFilterField filterField : tableDataProvider.getFilters())
				if (filterField.getField().equals(SINGLE_NOTE_FILTER_FIELD)) {
					singleNoteFilter = filterField;
					break;
				}
			
			if (singleNoteFilter != null)
				tableDataProvider.getFilters().remove(singleNoteFilter);
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

    protected abstract EventCategory getEventCategory();

    public Long getEventFilterID() {
		return eventFilterID;
	}

	public void setEventFilterID(Long eventFilterID) {
		this.eventFilterID = eventFilterID;
	}
}
