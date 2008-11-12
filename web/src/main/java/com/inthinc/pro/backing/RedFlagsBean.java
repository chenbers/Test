package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.richfaces.event.DataScrollerEvent;

import com.inthinc.pro.backing.ui.RedFlagReportItem;
import com.inthinc.pro.dao.RedFlagDAO;
import com.inthinc.pro.model.RedFlag;

public class RedFlagsBean extends BaseBean
{
    private static final Logger logger = Logger.getLogger(RedFlagsBean.class);
    
    Integer DEFAULT_ROWS_PER_PAGE = 25;
    
    private Integer numRowsPerPg;
    private Integer start;
    private Integer end;
    private Integer maxCount;
    private Map<String, Boolean> tableColumns;
    private List <RedFlagReportItem> tableData;
    
    private RedFlagDAO redFlagDAO;


    // package level -- so unit test can get it
    static final List<String> AVAILABLE_COLUMNS;
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
    static final Integer AVAILABLE_COLUMNS_COUNT = 7;
    
//    private List<String> availableColumns;
//    private Integer availableColumnsCount;
    
    public List<String> getAvailableColumns()
    {
        return AVAILABLE_COLUMNS;
    }

//    public void setAvailableColumns(List<String> availableColumns)
//    {
//        this.availableColumns = availableColumns;
//    }

    public Integer getAvailableColumnsCount()
    {
        return AVAILABLE_COLUMNS_COUNT;
    }

//    public void setAvailableColumnsCount(Integer availableColumnsCount)
//    {
//        this.availableColumnsCount = availableColumnsCount;
//    }

    public void scrollerListener(DataScrollerEvent event)     
    {        
        
        logger.debug("scroll event page: " + event.getPage() + 
                " old " + event.getOldScrolVal() + " new " + event.getNewScrolVal());
//                " total " + this.driverData.size());
        
        this.start = (event.getPage()-1)*this.numRowsPerPg + 1;
        this.end = (event.getPage())*this.numRowsPerPg;
        //Partial page
        if ( this.end > getTableData().size() ) {
            this.end = getTableData().size();
        }
    }

    public Integer getNumRowsPerPg()
    {
        if (numRowsPerPg == null)
        {
            // TODO: Should this be a user preference?
            numRowsPerPg = DEFAULT_ROWS_PER_PAGE;
        }
        return numRowsPerPg;
    }

    public void setNumRowsPerPg(Integer numRowsPerPg)
    {
        this.numRowsPerPg = numRowsPerPg;
    }

    public Map<String, Boolean> getTableColumns()
    {
        if (tableColumns == null)
        {
            tableColumns = new HashMap<String, Boolean>();
            // TODO: Replace this with DAO for a "load" of the preferences for this particular table        
            for ( String column : AVAILABLE_COLUMNS) 
            {
                tableColumns.put(column,true);
            }
      
            
        }
        return tableColumns;
    }

    public void setTableColumns(Map<String, Boolean> tableColumns)
    {
        this.tableColumns = tableColumns;
    }

    public List<RedFlagReportItem> getTableData()
    {
        if (tableData == null)
        {
            initTableData();
        }
        
        return tableData;
    }

    private void initTableData()
    {
        List<RedFlag> redFlagList = redFlagDAO.getRedFlags(getUser().getPerson().getGroupID());
        List<RedFlagReportItem> redFlagReportItemList = new ArrayList<RedFlagReportItem>();
        for (RedFlag redFlag : redFlagList)
        {
            redFlagReportItemList.add(new RedFlagReportItem(redFlag, getGroupHierarchy()));
        }
        setTableData(redFlagReportItemList);
        setMaxCount(redFlagReportItemList.size());
        setStart(redFlagReportItemList.size() > 0 ? 1 : 0);
        setEnd(redFlagReportItemList.size() > getNumRowsPerPg() ? getNumRowsPerPg() : redFlagReportItemList.size());
        
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
            initTableData();
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
            initTableData();
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
            initTableData();
        }
        return maxCount;
    }

    public void setMaxCount(Integer maxCount)
    {
        this.maxCount = maxCount;
    }

    
}
