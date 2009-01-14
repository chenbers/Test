package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.ui.TableColumn;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.TableType;

public class WarningsBean extends BaseEventsBean
{
    private static final Logger     logger                  = Logger.getLogger(EventsBean.class);

    private TablePref tablePref;

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


}
