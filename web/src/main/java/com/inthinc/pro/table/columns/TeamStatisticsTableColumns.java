package com.inthinc.pro.table.columns;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.model.TableType;

public class TeamStatisticsTableColumns extends BaseTableColumns {

	private static final long serialVersionUID = -6025858375318753376L;
    static final List<String> COLUMNS;
    static
    {
        // available columns
    	COLUMNS = new ArrayList<String>();
    	COLUMNS.add("driver_team");
    	COLUMNS.add("score");
    	COLUMNS.add("vehicle");
    	COLUMNS.add("trips");
    	COLUMNS.add("stops");
    	COLUMNS.add("dist_driven");
    	COLUMNS.add("drive_time");
    	COLUMNS.add("idle_time");
    	COLUMNS.add("low_idle");
    	COLUMNS.add("high_idle");
    	COLUMNS.add("idle_percentage");
    	COLUMNS.add("mpg");
    	COLUMNS.add("crashes");
    	COLUMNS.add("seatbelt_clicks");
    	COLUMNS.add("safety");
        COLUMNS.add("backing_time");
        COLUMNS.add("backing");
    }
    
	@Override
	public String getColumnLabelPrefix() {
		return "team_";
	}

	@Override
	public List<String> getAvailableColumns() {
		return COLUMNS;
	}


	@Override
	public TableType getTableType() {
		return TableType.TEAM_STATS;
	}
}
