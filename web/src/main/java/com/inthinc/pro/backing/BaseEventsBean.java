package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.richfaces.event.DataScrollerEvent;

import com.inthinc.pro.backing.ui.EventReportItem;
import com.inthinc.pro.backing.ui.RedFlagReportItem;
import com.inthinc.pro.backing.ui.TableColumn;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.TablePreferenceDAO;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventCategory;
import com.inthinc.pro.model.EventMapper;
import com.inthinc.pro.model.TableType;

public class BaseEventsBean extends BaseBean implements TablePrefOptions
{
    private static final Logger     logger                  = Logger.getLogger(EventsBean.class);

    private final static String COLUMN_LABEL_PREFIX = "events_";
    protected final static Integer DAYS_BACK = 7;

    private static final Integer                 numRowsPerPg = 25;
    private Integer                 start;
    private Integer                 end;
    private Integer                 maxCount;
    private List<EventReportItem> tableData;
    private List<EventReportItem> filteredTableData;

    private EventDAO                eventDAO;
    private TablePreferenceDAO tablePreferenceDAO;
    
    private EventReportItem   clearItem;
    
    private EventCategory categoryFilter;
    private Event eventFilter;
    
    private String searchText;
    
//    private TablePref tablePref;

    // package level -- so unit test can get it
    static final List<String>       AVAILABLE_COLUMNS;
    static
    {
        // available columns
        AVAILABLE_COLUMNS = new ArrayList<String>();
        AVAILABLE_COLUMNS.add("date");
        AVAILABLE_COLUMNS.add("group");
        AVAILABLE_COLUMNS.add("category");
        AVAILABLE_COLUMNS.add("detail");
        AVAILABLE_COLUMNS.add("clear");

    }

    public void initBean()
    {
        
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
    private void filterTableData()
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
        if (searchText != null && !searchText.trim().isEmpty())
        {
            ArrayList<EventReportItem> searchTableDataResult = searchTableData(filteredTableData, searchText.toLowerCase());
            
            setFilteredTableData(searchTableDataResult);
        }
        setMaxCount(filteredTableData.size());
        setStart(filteredTableData.size() > 0 ? 1 : 0);
        setEnd(filteredTableData.size() > getNumRowsPerPg() ? getNumRowsPerPg() : filteredTableData.size());
    }

    protected ArrayList<EventReportItem> searchTableData(List<EventReportItem> filteredTableData, String lowerCase)
    {
        return null;
    }

    private void initTableData()
    {
        setFilteredTableData(null);

        List<Event> eventList = getEventsForGroup(getUser().getGroupID());
//        List<RedFlag> redFlagList = redFlagDAO.getRedFlags(getUser().getGroupID());
        List<EventReportItem> EventReportItemList = new ArrayList<EventReportItem>();
        for (Event event : eventList)
        {
            EventReportItemList.add(new EventReportItem(event, null, getGroupHierarchy()));
        }
        setTableData(EventReportItemList);

    }

    protected List<Event> getEventsForGroup(Integer groupID)
    {
//        return eventDAO.getMostRecentEvents(groupID, EVENT_CNT);
        return null;
    }

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
//        clearItem.getRedFlag().setCleared(true);
        eventDAO.forgive(clearItem.getEvent().getDriverID(), clearItem.getEvent().getNoteID());
        tableData.remove(clearItem);
        filteredTableData.remove(clearItem);
    }

    public EventCategory getCategoryFilter()
    {
        return categoryFilter;
    }

    private void reinit()
    {
        //setTableData(null);
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

    public String getSearchText()
    {
        return searchText;
    }

    public void setSearchText(String searchText)
    {
        if (searchText == null || searchText.isEmpty())
        {
            setCategoryFilter(null);
            setEventFilter(null);
        }
        this.searchText = searchText;
    }
    
    public void searchAction()
    {
        filterTableData();
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

}

