package com.inthinc.pro.table.columns;

import java.util.ArrayList;
import java.util.List;

public class IdlingVehicleReportTableColumns extends IdlingReportTableColumns {

	private static final long serialVersionUID = 1L;


	@Override
	public List<String> getAvailableColumns() {
        List<String> results = new ArrayList<String>(AVAILABLE_COLUMNS);
        results.add("vehicle_name");
        return results;
	}
}
