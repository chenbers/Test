package com.inthinc.pro.table.columns;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.model.TableType;

public class RedFlagsTableColumns extends BaseTableColumns {

	private static final long serialVersionUID = -8211452226005190602L;
    static final List<String> AVAILABLE_COLUMNS;
    static {
        // available columns
        AVAILABLE_COLUMNS = new ArrayList<String>();
        AVAILABLE_COLUMNS.add("level");
        AVAILABLE_COLUMNS.add("alerts");
        AVAILABLE_COLUMNS.add("date");
        AVAILABLE_COLUMNS.add("group");
        AVAILABLE_COLUMNS.add("driver");
        AVAILABLE_COLUMNS.add("vehicle");
        AVAILABLE_COLUMNS.add("category");
        AVAILABLE_COLUMNS.add("detail");
        AVAILABLE_COLUMNS.add("clear");
    }
    
    private final static String COLUMN_LABEL_PREFIX = "notes_redflags_";


	@Override
	public List<String> getAvailableColumns() {
		return AVAILABLE_COLUMNS;
	}

	@Override
	public String getColumnLabelPrefix() {
		return COLUMN_LABEL_PREFIX;
	}

	@Override
	public TableType getTableType() {
        return TableType.RED_FLAG;
	}
	
	@Override
    public boolean getCanHideColumn(String columnName)
    {
		if (columnName.equals("clear"))
			return false;
    	return true;
    }


}
