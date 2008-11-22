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

public class RedFlagsBean extends BaseBean implements EditableColumns
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
    private Map<String, TableColumn>    tableColumns;
    private TablePreferenceDAO      tablePreferenceDAO;
    private TablePreference redFlagTablePref;
    
    private RedFlagReportItem   clearItem;
    
    private EventCategory categoryFilter;
    private Event eventFilter;
    
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


    public void scrollerListener(DataScrollerEvent event)
    {

        logger.debug("scroll event page: " + event.getPage() + " old " + event.getOldScrolVal() + " new " + event.getNewScrolVal());

        this.start = (event.getPage() - 1) * this.numRowsPerPg + 1;
        this.end = (event.getPage()) * this.numRowsPerPg;
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
        else if (getEventFilter() != null)
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
        
        setMaxCount(filteredTableData.size());
        setStart(filteredTableData.size() > 0 ? 1 : 0);
        setEnd(filteredTableData.size() > getNumRowsPerPg() ? getNumRowsPerPg() : filteredTableData.size());
    }

    private void initTableData()
    {
        setFilteredTableData(null);
        
        List<RedFlag> redFlagList = redFlagDAO.getRedFlags(getUser().getPerson().getGroupID());
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

    // ---   Editable Columns Interface implementation  -- 
    public List<String> getAvailableColumns()
    {
        return AVAILABLE_COLUMNS;
    }
    public Map<String, TableColumn> getTableColumns()
    {
        if (tableColumns == null)
        {
            List<Boolean> visibleList = getRedFlagTablePref().getVisible();
            tableColumns = new HashMap<String, TableColumn>();
            int cnt = 0;
            for (String column : AVAILABLE_COLUMNS)
            {
                TableColumn tableColumn = new TableColumn(visibleList.get(cnt++), MessageUtil.getMessageString(COLUMN_LABEL_PREFIX+column));
                if (column.equals("clear"))
                    tableColumn.setCanHide(false);
                    
                tableColumns.put(column, tableColumn);
                        
            }

        }
        return tableColumns;
    }

    public void setTableColumns(Map<String, TableColumn> tableColumns)
    {
        this.tableColumns = tableColumns;
    }
    
    public String saveColumns()
    {
        logger.debug("saveColumns RedFlagsBean");
        TablePreference pref = getRedFlagTablePref();
        int cnt = 0;
        for (String column : AVAILABLE_COLUMNS)
        {
            pref.getVisible().set(cnt, tableColumns.get(column).getVisible());
        }
        setRedFlagTablePref(pref);
        tablePreferenceDAO.update(pref);
        return null;
    
    }

    public TablePreference getRedFlagTablePref()
    {
        if (redFlagTablePref == null)
        {
            // TODO: refactor -- could probably keep in a session bean
            List<TablePreference> tablePreferenceList = tablePreferenceDAO.getTablePreferencesByUserID(getUser().getUserID());
            for (TablePreference pref : tablePreferenceList)
            {
                if (pref.getTableType().equals(TableType.RED_FLAG))
                {
                    setRedFlagTablePref(pref);
                    return redFlagTablePref;
                }
            }
            redFlagTablePref = new TablePreference();
            redFlagTablePref.setUserID(getUser().getUserID());
            redFlagTablePref.setTableType(TableType.RED_FLAG);
            List<Boolean>visibleList = new ArrayList<Boolean>();
            for (String column : AVAILABLE_COLUMNS)
            {
                visibleList.add(new Boolean(true));
            }
            redFlagTablePref.setVisible(visibleList);
            
        }
        
        
        return redFlagTablePref;
    }

    public void setRedFlagTablePref(TablePreference redFlagTablePref)
    {
        this.redFlagTablePref = redFlagTablePref;
    }

    public TablePreferenceDAO getTablePreferenceDAO()
    {
        return tablePreferenceDAO;
    }

    public void setTablePreferenceDAO(TablePreferenceDAO tablePreferenceDAO)
    {
        this.tablePreferenceDAO = tablePreferenceDAO;
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
        setTableData(null);
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
    
}
