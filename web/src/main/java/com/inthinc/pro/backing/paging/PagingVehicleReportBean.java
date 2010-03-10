package com.inthinc.pro.backing.paging;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.model.VehicleReportItem;
import com.inthinc.pro.model.pagination.SortOrder;
import com.inthinc.pro.model.pagination.TableSortField;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.table.BasePaginationTable;
import com.inthinc.pro.table.model.provider.VehicleReportPaginationTableDataProvider;

public class PagingVehicleReportBean extends BasePagingReportBean<VehicleReportItem>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9116805820208771789L;


	private static final Logger logger = Logger.getLogger(PagingVehicleReportBean.class);
    
    
	private VehicleReportPaginationTableDataProvider tableDataProvider;


	// TablePrefOptions info
    static final List<String> AVAILABLE_COLUMNS;
    private final static String COLUMN_LABEL_PREFIX = "vehicleReports_";
    static
    {
        // available columns
        AVAILABLE_COLUMNS = new ArrayList<String>();
        AVAILABLE_COLUMNS.add("group");
        AVAILABLE_COLUMNS.add("vehicle_name");
        AVAILABLE_COLUMNS.add("makeModelYear");
        AVAILABLE_COLUMNS.add("driver_person_fullName");
        AVAILABLE_COLUMNS.add("distanceDriven");
        AVAILABLE_COLUMNS.add("overallScore");
        AVAILABLE_COLUMNS.add("speedScore");
        AVAILABLE_COLUMNS.add("styleScore");
        AVAILABLE_COLUMNS.add("odometer");
    }
    
    @Override
    public void init()
    {
        super.init();

		logger.info("PagingVehicleReportBean - constructor");
		
        tableDataProvider.setSort(new TableSortField(SortOrder.ASCENDING, "vehicleName"));
        tableDataProvider.setGroupID(this.getProUser().getUser().getGroupID());
		getTable().initModel(tableDataProvider);
		
    }
    

    public VehicleReportPaginationTableDataProvider getTableDataProvider() {
		return tableDataProvider;
	}



	public void setTableDataProvider(VehicleReportPaginationTableDataProvider tableDataProvider) {
		this.tableDataProvider = tableDataProvider;
	}



    @Override
	protected ReportCriteria getReportCriteria()
    {
    	return getReportCriteriaService().getVehicleReportCriteria(getUser().getGroupID(), getLocale());
    }


    // TablePrefOptions overrides
    @Override
    public String fieldValue(VehicleReportItem item, String column)
    {
        if("distanceDriven".equals(column))
        {
            if(getMeasurementType().equals(MeasurementType.ENGLISH))
                return item.getMilesDriven().toString();
            else
                return MeasurementConversionUtil.fromMilesToKilometers(item.getMilesDriven().doubleValue()).toString();
        }
        
        return super.fieldValue(item, column);
    }
    
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
        return TableType.VEHICLE_REPORT;
    }
    
    // END - TablePrefOptions overrides
}

