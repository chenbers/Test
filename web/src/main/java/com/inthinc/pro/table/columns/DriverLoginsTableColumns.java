package com.inthinc.pro.table.columns;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.backing.ui.TableColumn;
import com.inthinc.pro.model.TableType;

public class DriverLoginsTableColumns extends EventsTableColumns {

    @Override
    public List<String> getAvailableColumns() {
        List<String> results = new ArrayList<String>(AVAILABLE_COLUMNS);
        results.remove("driver");
        return results;
    }
    
    public Map<String, TableColumn> getTableColumns() {
        return super.getTableColumns();
    }

    @Override
	public TableType getTableType() {
		return TableType.DRIVER_LOGINS;
	}

}
