package com.inthinc.pro.table.columns;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ajax4jsf.model.KeepAlive;

import com.inthinc.pro.model.TableType;

@KeepAlive
public class DriverReportTableColumns extends BaseTableColumns {

	private static final long serialVersionUID = 4988590781244679436L;
	private final static String COLUMN_LABEL_PREFIX = "driverReports_";
    private static final List<String> AVAILABLE_COLUMNS;
    private static final int[] DEFAULT_COLUMN_INDICES = new int[] { 0, 2, 3, 4, 5, 7, 8 };
    static
    {
        // available columns
        AVAILABLE_COLUMNS = new ArrayList<String>();
        AVAILABLE_COLUMNS.add("group");
        AVAILABLE_COLUMNS.add("driver_person_empid");
        AVAILABLE_COLUMNS.add("driver_person_fullName");
        AVAILABLE_COLUMNS.add("vehicle_name");
        AVAILABLE_COLUMNS.add("distanceDriven");
        AVAILABLE_COLUMNS.add("overallScore");
        AVAILABLE_COLUMNS.add("speedScore");
        AVAILABLE_COLUMNS.add("styleScore");
        AVAILABLE_COLUMNS.add("seatBeltScore");
        AVAILABLE_COLUMNS.add("status");
    }
    
	

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
		return TableType.DRIVER_REPORT;
	}

    @Override
    public Map<String, Boolean> getDefaultColumns()
    {
        final HashMap<String, Boolean> columns = new HashMap<String, Boolean>();
        final List<String> availableColumns = getAvailableColumns();
        for (int i : DEFAULT_COLUMN_INDICES)
            columns.put(availableColumns.get(i), true);
        return columns;
    }

}
