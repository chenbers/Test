package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.richfaces.event.DataScrollerEvent;

import com.inthinc.pro.backing.listener.SearchChangeListener;
import com.inthinc.pro.backing.ui.EventReportItem;
import com.inthinc.pro.backing.ui.TableColumn;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.TablePreferenceDAO;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventCategory;
import com.inthinc.pro.model.EventMapper;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.reports.ReportRenderer;
import com.inthinc.pro.reports.service.ReportCriteriaService;

public abstract class BaseEventsBean extends BaseRedFlagsBean implements TablePrefOptions<EventReportItem>, PersonChangeListener, SearchChangeListener
{
    private static final Logger     logger                  = Logger.getLogger(EventsBean.class);

    private final static String COLUMN_LABEL_PREFIX = "notes_";
    protected final static Integer DAYS_BACK = 7;

    private static final Integer                 numRowsPerPg = 25;
    private Integer                 start;
    private Integer                 end;
    private Integer                 maxCount;
    private List<EventReportItem>   tableData;
    private List<EventReportItem>   filteredTableData;
    private String emailAddress;

    private EventDAO                eventDAO;
    private TablePreferenceDAO tablePreferenceDAO;
    
    private EventReportItem   clearItem;
    
    private EventCategory categoryFilter;
    private Event eventFilter;
    
    private ReportRenderer      reportRenderer;
    private ReportCriteriaService reportCriteriaService;
    
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
    
    

    @Override
    public void initBean()
    {
        super.initBean();
        tablePref = new TablePref<EventReportItem>(this);
        searchCoordinationBean.addSearchChangeListener(this);
   }
    
    /*
     *When the search button is actually clicked, we want to make sure we use what's in
     *the searchFor field
     */
    @Override
    public void searchAction()
    {
        setEventFilter(null);  
        super.searchAction();
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

    public void scrollerListener(DataScrollerEvent event)
    {

        logger.debug("scroll event page: " + event.getPage() + " old " + event.getOldScrolVal() + " new " + event.getNewScrolVal());

        this.start = (event.getPage() - 1) * numRowsPerPg + 1;
        this.end = (event.getPage()) * numRowsPerPg;
        // Partial page
        if (this.end > getFilteredTableData().size())
        {
            this.end = getFilteredTableData().size();
        }
    }

    public Integer getNumRowsPerPg()
    {
        return numRowsPerPg;
    }


    public List<EventReportItem> getTableData()
    {
        init();
        return getFilteredTableData();
    }

    @Override
    public void personListChanged()
    {
        refreshAction();
    }

    public void refreshAction()
    {
        setTableData(null);
        init();
    }
    private void init()
    {
        if (tableData == null)
        {
            initTableData();
        }
        if (filteredTableData == null)
        {
            filterTableData();
        }

    }
    @Override
    protected void filterTableData()
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
    
            for (EventReportItem item : tableData)
            {
                if (item.getEvent().getNoteID().equals(eventFilter.getNoteID()))
                {
                    filteredTableData.add(item);
                    break;
                }
            }
        }
        
        //Filter if search is based on group.
        if (!getEffectiveGroupId().equals(getUser().getGroupID()))
        {
            filteredTableData = new ArrayList<EventReportItem>();
            
            for (EventReportItem item : tableData)
            {
                if (item.getEvent().getGroupID().equals(getEffectiveGroupId()))
                {
                    filteredTableData.add(item);
                }
            }
        }
        
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
    }

    private void initTableData()
    {
        setFilteredTableData(null);

        List<Event> eventList = getEventsForGroup(getUser().getGroupID());
        List<EventReportItem> eventReportItemList = new ArrayList<EventReportItem>();
        for (Event event : eventList)
        {
            fillInDriver(event);
            fillInVehicle(event);
            eventReportItemList.add(new EventReportItem(event, null, getGroupHierarchy()));
        }
        Collections.sort(eventReportItemList);
        Collections.reverse(eventReportItemList);
        setTableData(eventReportItemList);

    }

    protected abstract List<Event> getEventsForGroup(Integer groupID);

    public void setTableData(List<EventReportItem> tableData)
    {
        this.tableData = tableData;
    }

    public Integer getStart()
    {
        if (start == null)
        {
            init();
        }
        return start;
    }

    public void setStart(Integer start)
    {
        this.start = start;
    }

    public Integer getEnd()
    {
        if (end == null)
        {
            init();
        }
        return end;
    }

    public void setEnd(Integer end)
    {
        this.end = end;
    }

    public Integer getMaxCount()
    {
        if (maxCount == null) 
        {
            init();
        }
        return maxCount;
    }

    public void setMaxCount(Integer maxCount)
    {
        this.maxCount = maxCount;
    }


    public EventReportItem getClearItem()
    {
        return clearItem;
    }

    public void setClearItem(EventReportItem clearItem)
    {
        this.clearItem = clearItem;
    }
    
    public void clearItemAction()
    {
        eventDAO.forgive(clearItem.getEvent().getDriverID(), clearItem.getEvent().getNoteID());
        tableData.remove(clearItem);
        filteredTableData.remove(clearItem);
        maxCount--;
        if (end > maxCount)
            end = maxCount;
    }

    public EventCategory getCategoryFilter()
    {
        return categoryFilter;
    }

    private void reinit()
    {
        setFilteredTableData(null);
        setStart(null);
        setEnd(null);
        setMaxCount(null);
    }
    public void setCategoryFilter(EventCategory categoryFilter)
    {
        reinit();
        this.eventFilter = null;
        this.categoryFilter = categoryFilter;
    }

    public List<EventReportItem> getFilteredTableData()
    {
        return filteredTableData;
    }

    public void setFilteredTableData(List<EventReportItem> filteredTableData)
    {
        this.filteredTableData = filteredTableData;
    }

    public Event getEventFilter()
    {
        return eventFilter;
    }

    public void setEventFilter(Event eventFilter)
    {
        // force table data to reinit
        reinit();
        this.categoryFilter = null;
        this.eventFilter = eventFilter;
    }
    
    @Override
    public String fieldValue(EventReportItem item, String column)
    {
        if ("driver".equals(column))
            column = "driverName";
        else if ("vehicle".equals(column))
            column = "vehicleName";
        return TablePref.fieldValue(item, column);
    }

    public void showAllAction()
    {
        setCategoryFilter(null);
        setEventFilter(null);
        
        filterTableData();
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
    public TablePreferenceDAO getTablePreferenceDAO()
    {
        return tablePreferenceDAO;
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

    public void setTablePreferenceDAO(TablePreferenceDAO tablePreferenceDAO)
    {
        this.tablePreferenceDAO = tablePreferenceDAO;
    }
    public EventDAO getEventDAO()
    {
        return eventDAO;
    }

    public void setEventDAO(EventDAO eventDAO)
    {
        this.eventDAO = eventDAO;
    }

    public void setReportRenderer(ReportRenderer reportRenderer)
    {
        this.reportRenderer = reportRenderer;
    }

    public ReportRenderer getReportRenderer()
    {
        return reportRenderer;
    }

    public void setReportCriteriaService(ReportCriteriaService reportCriteriaService)
    {
        this.reportCriteriaService = reportCriteriaService;
    }

    public ReportCriteriaService getReportCriteriaService()
    {
        return reportCriteriaService;
    }

    public void setEmailAddress(String emailAddress)
    {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress()
    {
        return emailAddress;
    }
    
    @Override
	public void searchChanged() {
    	
    	reinit();
	}

}

