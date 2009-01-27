package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.ui.EventReportItem;
import com.inthinc.pro.backing.ui.TableColumn;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.TableType;

public class WarningsBean extends BaseEventsBean
{
    private static final Logger     logger                  = Logger.getLogger(EventsBean.class);

    private TablePref tablePref;

    public void initBean()
    {
        super.initBean();
        tablePref = new TablePref(this);
        
    }
    
    @Override
    protected List<Event> getEventsForGroup(Integer groupID)
    {
        return getEventDAO().getWarningEventsForGroup(groupID, 7);

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
    public TableType getTableType()
    {
        return TableType.WARNINGS;
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
    protected ArrayList<EventReportItem> searchTableData(List<EventReportItem> filteredTableData, String caseInsensitiveSearchText)
    {
        ArrayList<EventReportItem> searchTableData = new ArrayList<EventReportItem>();
        Map<String, TableColumn> columnMap = getTableColumns();
        
        for (EventReportItem item : filteredTableData)
        {
            StringBuilder itemStr = new StringBuilder();
            itemStr.append(columnMap.get("category").getVisible() ? item.getCategory().toLowerCase() : "");
            itemStr.append(columnMap.get("group").getVisible() ? item.getGroup().toLowerCase() : "");
            itemStr.append(columnMap.get("driver").getVisible() ? item.getDriverName().toLowerCase() : "");
            itemStr.append(columnMap.get("vehicle").getVisible() ? item.getVehicleName().toLowerCase() : "");
            itemStr.append(columnMap.get("detail").getVisible() ? item.getDetail().toLowerCase() : "");
            itemStr.append(columnMap.get("date").getVisible() ? item.getDate().toLowerCase() : "");
            
            if (itemStr.indexOf(caseInsensitiveSearchText) != -1)
            {
                searchTableData.add(item);
            }
        }
        
        return searchTableData;
    }

    public String showAllFromRecentAction()
    {
        setSearchText(null);
        setCategoryFilter(null);
        setEventFilter(null);
        
        refreshAction();
        return "go_warnings";
    }
}
