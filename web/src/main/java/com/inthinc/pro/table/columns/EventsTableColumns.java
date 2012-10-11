package com.inthinc.pro.table.columns;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.model.TableType;

public abstract class EventsTableColumns extends BaseTableColumns {

	private static final long serialVersionUID = -8337686975882369706L;

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
    
	private final static String COLUMN_LABEL_PREFIX = "notes_";

	@Override
	public List<String> getAvailableColumns() {
		return AVAILABLE_COLUMNS;
	}

	@Override
	public String getColumnLabelPrefix() {
		return COLUMN_LABEL_PREFIX;
	}
	
	@Override
    public boolean getCanHideColumn(String columnName)
    {
		if (columnName.equals("clear"))
			return false;
    	return true;
    }


	public abstract TableType getTableType();

}
