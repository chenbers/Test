package com.inthinc.pro.table.columns;

import java.util.ArrayList;
import java.util.List;

import org.ajax4jsf.model.KeepAlive;

import com.inthinc.pro.model.TableType;
@KeepAlive
public class DeviceReportTableColumns extends BaseTableColumns {

	private static final long serialVersionUID = 2205061343607962607L;
	
	private final static String COLUMN_LABEL_PREFIX = "deviceReports_";
	private static final List<String> AVAILABLE_COLUMNS;
    static {
        // available columns
        AVAILABLE_COLUMNS = new ArrayList<String>();
        AVAILABLE_COLUMNS.add("device_name");
        AVAILABLE_COLUMNS.add("vehicle_name");
        AVAILABLE_COLUMNS.add("device_imei");
        AVAILABLE_COLUMNS.add("device_phone");
        AVAILABLE_COLUMNS.add("device_status");
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
        return TableType.DEVICE_REPORT;
	}

}
