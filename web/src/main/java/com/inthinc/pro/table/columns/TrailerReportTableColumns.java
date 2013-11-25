package com.inthinc.pro.table.columns;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.model.TableType;

public class TrailerReportTableColumns extends BaseTableColumns {
    
    private static final long serialVersionUID = 447580222357141029L;
    private final static String COLUMN_LABEL_PREFIX = "trailerReports_";
    private static final List<String> AVAILABLE_COLUMNS;
    static
    {
        // available columns
        AVAILABLE_COLUMNS = new ArrayList<String>();
        AVAILABLE_COLUMNS.add("group");
        AVAILABLE_COLUMNS.add("trailer_name");
        AVAILABLE_COLUMNS.add("vehicle_name");
        AVAILABLE_COLUMNS.add("driver_person_fullName");
        AVAILABLE_COLUMNS.add("assigned_status");
        AVAILABLE_COLUMNS.add("entry_method");
    }

    @Override
    public TableType getTableType() {
        return TableType.TRAILER_REPORT;
    }
    
    @Override
    public List<String> getAvailableColumns() {
        return AVAILABLE_COLUMNS;
    }
    
    @Override
    public String getColumnLabelPrefix() {
        return COLUMN_LABEL_PREFIX;
    }
    
}
