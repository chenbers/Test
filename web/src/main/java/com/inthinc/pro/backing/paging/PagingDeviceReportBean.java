package com.inthinc.pro.backing.paging;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;

import com.inthinc.pro.model.DeviceReportItem;
import com.inthinc.pro.model.DeviceStatus;
import com.inthinc.pro.model.RedFlagLevel;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.model.pagination.SortOrder;
import com.inthinc.pro.model.pagination.TableSortField;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.table.model.provider.DeviceReportPaginationTableDataProvider;
import com.inthinc.pro.util.MessageUtil;

public class PagingDeviceReportBean extends BasePagingReportBean<DeviceReportItem>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9116805820208771789L;


	private static final Logger logger = Logger.getLogger(PagingDeviceReportBean.class);
    
    
	private DeviceReportPaginationTableDataProvider tableDataProvider;


	// TablePrefOptions info
    static final List<String> AVAILABLE_COLUMNS;
    private final static String COLUMN_LABEL_PREFIX = "deviceReports_";
    static {
        // available columns
        AVAILABLE_COLUMNS = new ArrayList<String>();
        AVAILABLE_COLUMNS.add("device_name");
        AVAILABLE_COLUMNS.add("vehicle_name");
        AVAILABLE_COLUMNS.add("device_imei");
        AVAILABLE_COLUMNS.add("device_phone");
        AVAILABLE_COLUMNS.add("device_status");
        AVAILABLE_COLUMNS.add("device_ephone");
    }


    private String filterStatus;
	private List<SelectItem> deviceStatuses;
	public List<SelectItem>  getDeviceStatuses() {
		if (deviceStatuses == null) {
			deviceStatuses = new ArrayList<SelectItem> ();
			SelectItem blankItem = new SelectItem("", BLANK_SELECTION);
			blankItem.setEscape(false);
			deviceStatuses.add(blankItem);
	        for (DeviceStatus deviceStatus : EnumSet.allOf(DeviceStatus.class)) {
	        	deviceStatuses.add(new SelectItem(deviceStatus.getCode().toString(), MessageUtil.getMessageString("status"+deviceStatus.getCode(), getLocale())));
	    	}
		}
	    
	    return deviceStatuses;
    }

    public String getFilterStatus() {
		return filterStatus;
	}


	public void setFilterStatus(String filterStatus) {
		this.filterStatus = filterStatus;
	}


    @Override
    public void init()
    {
        super.init();

		logger.info("PagingDeviceReportBean - constructor");
		
        tableDataProvider.setSort(new TableSortField(SortOrder.ASCENDING, "deviceName"));
        tableDataProvider.setGroupID(this.getProUser().getUser().getGroupID());
		getTable().initModel(tableDataProvider);
		
    }
    
    
    public DeviceReportPaginationTableDataProvider getTableDataProvider() {
		return tableDataProvider;
	}



	public void setTableDataProvider(DeviceReportPaginationTableDataProvider tableDataProvider) {
		this.tableDataProvider = tableDataProvider;
	}



    @Override
	protected ReportCriteria getReportCriteria()
    {
    	return getReportCriteriaService().getDevicesReportCriteria(getUser().getGroupID(), getLocale());
    }


    // TablePrefOptions overrides
  
    @Override
    public List<String> getAvailableColumns()
    {
        return AVAILABLE_COLUMNS;
    }

    @Override
    public String getColumnLabelPrefix()
    {
        return COLUMN_LABEL_PREFIX;
    }

    @Override
    public TableType getTableType()
    {
        return TableType.DEVICE_REPORT;
    }
    
    // END - TablePrefOptions overrides
}

