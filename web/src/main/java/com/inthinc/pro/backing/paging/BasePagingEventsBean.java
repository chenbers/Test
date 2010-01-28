package com.inthinc.pro.backing.paging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.TablePref;
import com.inthinc.pro.backing.TablePrefOptions;
import com.inthinc.pro.backing.ui.EventReportItem;
import com.inthinc.pro.backing.ui.TableColumn;
import com.inthinc.pro.dao.TablePreferenceDAO;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.TableType;

public abstract class BasePagingEventsBean extends BasePagingNotificationsBean<Event> implements TablePrefOptions<Event>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1924831319206502048L;

	static final Logger     logger                  = Logger.getLogger(BasePagingEventsBean.class);

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
        
        for (Event event : eventList)
        {
            eventReportItemList.add(new EventReportItem(event, getMeasurementType()));
        }
        Collections.sort(eventReportItemList);
        Collections.reverse(eventReportItemList);
        
        return eventReportItemList;
    }

    protected abstract List<Event> getReportEvents();

}

