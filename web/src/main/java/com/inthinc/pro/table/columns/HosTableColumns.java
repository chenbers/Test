package com.inthinc.pro.table.columns;

import java.util.ArrayList;
import java.util.List;

import org.ajax4jsf.model.KeepAlive;

import com.inthinc.pro.model.TableType;

@KeepAlive
public class HosTableColumns extends BaseTableColumns {

	private static final long serialVersionUID = -8211452226005190602L;
    private static final List<String> AVAILABLE_COLUMNS;
    static
    {
        // available columns
        AVAILABLE_COLUMNS = new ArrayList<String>();
        AVAILABLE_COLUMNS.add("datetime");
        AVAILABLE_COLUMNS.add("driver");
        AVAILABLE_COLUMNS.add("vehicle");
        AVAILABLE_COLUMNS.add("trailer");
        AVAILABLE_COLUMNS.add("service");
        AVAILABLE_COLUMNS.add("location");
        AVAILABLE_COLUMNS.add("edited");
        AVAILABLE_COLUMNS.add("status");
        
    }

    private final static String COLUMN_LABEL_PREFIX = "hosLogs_";


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
        return TableType.HOS_LOGS;
	}
	
	@Override
    public boolean getCanHideColumn(String columnName)
    {
		if (columnName.equals("edit"))
			return false;
    	return true;
    }


}
