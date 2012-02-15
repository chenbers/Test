package com.inthinc.pro.table.columns;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.model.TableType;

public class IdlingReportTableColumns extends BaseTableColumns {
	private static final long serialVersionUID = 8753087702011476835L;
	private final static String COLUMN_LABEL_PREFIX = "idlingReports_";
    static final List<String> AVAILABLE_COLUMNS;
    static {
        // available columns
        AVAILABLE_COLUMNS = new ArrayList<String>();
        AVAILABLE_COLUMNS.add("group");
        AVAILABLE_COLUMNS.add("driver_person_fullName");
        AVAILABLE_COLUMNS.add("duration");
        AVAILABLE_COLUMNS.add("hasRPM");
        AVAILABLE_COLUMNS.add("lowHrs");
        AVAILABLE_COLUMNS.add("lowPercent");
        AVAILABLE_COLUMNS.add("highHrs");
        AVAILABLE_COLUMNS.add("highPercent");
        AVAILABLE_COLUMNS.add("totalHrs");
        AVAILABLE_COLUMNS.add("totalPercent");
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
        return TableType.IDLING_REPORT;
	}

}
