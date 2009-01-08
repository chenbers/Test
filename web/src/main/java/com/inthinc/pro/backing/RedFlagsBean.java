package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.richfaces.event.DataScrollerEvent;

import com.inthinc.pro.backing.ui.EditableColumns;
import com.inthinc.pro.backing.ui.RedFlagReportItem;
import com.inthinc.pro.backing.ui.TableColumn;
import com.inthinc.pro.dao.RedFlagDAO;
import com.inthinc.pro.dao.TablePreferenceDAO;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventCategory;
import com.inthinc.pro.model.EventMapper;
import com.inthinc.pro.model.RedFlag;
import com.inthinc.pro.model.TablePreference;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.util.MessageUtil;

public class RedFlagsBean extends BaseBean implements TablePrefOptions
{
    private static final Logger     logger                  = Logger.getLogger(RedFlagsBean.class);

    private final static String COLUMN_LABEL_PREFIX = "redflags_";

    private static final Integer                 numRowsPerPg = 25;
    private Integer                 start;
    private Integer                 end;
    private Integer                 maxCount;
    private List<RedFlagReportItem> tableData;
    private List<RedFlagReportItem> filteredTableData;

    private RedFlagDAO              redFlagDAO;
    private TablePreferenceDAO tablePreferenceDAO;
    
    private RedFlagReportItem   clearItem;
    
    private EventCategory categoryFilter;
    private Event eventFilter;
    
    private String searchText;
    
    private TablePref tablePref;

    // package level -- so unit test can get it
    static final List<String>       AVAILABLE_COLUMNS;
    static
    {
        // available columns
        AVAILABLE_COLUMNS = new ArrayList<String>();
        AVAILABLE_COLUMNS.add("level");
        AVAILABLE_COLUMNS.add("alerts");
        AVAILABLE_COLUMNS.add("date");
        AVAILABLE_COLUMNS.add("group");
        AVAILABLE_COLUMNS.add("category");
        AVAILABLE_COLUMNS.add("detail");
        AVAILABLE_COLUMNS.add("clear");

    }

    public void initBean()
    {
        tablePref = new TablePref(this);
        
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


    public List<RedFlagReportItem> getTableData()
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
                filteredTableData = new ArrayList<RedFlagReportItem>();
        
                for (RedFlagReportItem item : tableData)
                {
                    if (validEventTypes.contains(item.getRedFlag().getEvent().getType()))
                    {
                        filteredTableData.add(item);
                    }
                }
            }
        }
        if (getEventFilter() != null)
        {    
            filteredTableData = new ArrayList<RedFlagReportItem>();
    
            for (RedFlagReportItem item : tableData)
            {
                if (item.getRedFlag().getEvent().getNoteID().equals(eventFilter.getNoteID()))
                {
                    filteredTableData.add(item);
                    break;
                }
            }
        }
        if (searchText != null && !searchText.trim().isEmpty())
        {
            ArrayList<RedFlagReportItem> searchTableData = new ArrayList<RedFlagReportItem>();
            
            for (RedFlagReportItem item : filteredTableData)
            {
                if (item.getCategory().startsWith(searchText) ||
                    item.getGroup().startsWith(searchText) ||
                    item.getDetail().startsWith(searchText) ||
                    item.getRedFlag().getLevel().toString().startsWith(searchText))
                {
                    searchTableData.add(item);
                }
            }
            setFilteredTableData(searchTableData);
        }
        setMaxCount(filteredTableData.size());
        setStart(filteredTableData.size() > 0 ? 1 : 0);
        setEnd(filteredTableData.size() > getNumRowsPerPg() ? getNumRowsPerPg() : filteredTableData.size());
    }

    private void initTableData()
    {
        setFilteredTableData(null);
        
        List<RedFlag> redFlagList = redFlagDAO.getRedFlags(getUser().getGroupID());
        List<RedFlagReportItem> redFlagReportItemList = new ArrayList<RedFlagReportItem>();
        for (RedFlag redFlag : redFlagList)
        {
            if (!redFlag.getCleared())
            {
                redFlagReportItemList.add(new RedFlagReportItem(redFlag, getGroupHierarchy()));
            }
        }
        setTableData(redFlagReportItemList);

    }

    public void setTableData(List<RedFlagReportItem> tableData)
    {
        this.tableData = tableData;
    }

    public RedFlagDAO getRedFlagDAO()
    {
        return redFlagDAO;
    }

    public void setRedFlagDAO(RedFlagDAO redFlagDAO)
    {
        this.redFlagDAO = redFlagDAO;
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


    public RedFlagReportItem getClearItem()
    {
        return clearItem;
    }

    public void setClearItem(RedFlagReportItem clearItem)
    {
        this.clearItem = clearItem;
    }
    
    public void clearItemAction()
    {
        clearItem.getRedFlag().setCleared(true);
        tableData.remove(clearItem);
        filteredTableData.remove(clearItem);
        // todo: persist in DAO
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

    public List<RedFlagReportItem> getFilteredTableData()
    {
        return filteredTableData;
    }

    public void setFilteredTableData(List<RedFlagReportItem> filteredTableData)
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

    public TablePref getTablePref()
    {
        return tablePref;
    }

    public void setTablePref(TablePref tablePref)
    {
        this.tablePref = tablePref;
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
        return TableType.RED_FLAG;
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

    // wrappers for tablePref
//    public String saveColumns()
//    {
//        return tablePref.saveColumns();
//    }
    public Map<String, TableColumn> getTableColumns()
    {
        return tablePref.getTableColumns();
    }
    public void setTableColumns(Map<String, TableColumn> tableColumns)
    {
        tablePref.setTableColumns(tableColumns);
    }

}
