package com.inthinc.pro.backing.paging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.backing.TablePref;
import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.DriverReportItem;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.model.pagination.SortOrder;
import com.inthinc.pro.model.pagination.TableFilterField;
import com.inthinc.pro.model.pagination.TableSortField;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.table.BasePaginationTable;
import com.inthinc.pro.table.model.provider.DriverReportPaginationTableDataProvider;

public class PagingDriverReportBean extends BasePagingReportBean<DriverReportItem>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5349999687948286628L;


	private static final Logger logger = Logger.getLogger(PagingDriverReportBean.class);
    
    
	private DriverReportPaginationTableDataProvider tableDataProvider;
	private BasePaginationTable<DriverReportItem> table;


	// TablePrefOptions info
    static final List<String> AVAILABLE_COLUMNS;
    private static final int[] DEFAULT_COLUMN_INDICES = new int[] { 0, 2, 3, 4, 5, 7, 8 };
    private final static String COLUMN_LABEL_PREFIX = "driverReports_";
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
    }
    
    @Override
    public void init()
    {
        super.init();

		logger.info("PagingDriverReportBean - constructor");
		
        tableDataProvider.setSort(new TableSortField(SortOrder.ASCENDING, "driverName"));
		
		table = new BasePaginationTable<DriverReportItem>();
        tableDataProvider.setGroupID(this.getProUser().getUser().getGroupID());
		table.initModel(tableDataProvider);
		
    }
    
    public void allAction()
    {
		table.reset();
    }
    
    public void searchAction()
    {
		table.reset();
		getSearchCoordinationBean().setSearchFor(null);
    }
    
    

    public DriverReportPaginationTableDataProvider getTableDataProvider() {
		return tableDataProvider;
	}



	public void setTableDataProvider(DriverReportPaginationTableDataProvider tableDataProvider) {
		this.tableDataProvider = tableDataProvider;
	}



	public BasePaginationTable<DriverReportItem> getTable() {
		return table;
	}



	public void setTable(BasePaginationTable<DriverReportItem> table) {
		this.table = table;
	}

    @Override
	protected List<DriverReportItem> getReportTableData()
	{
		int totalCount = getTableDataProvider().getRowCount();
		if (totalCount == 0)
			return new ArrayList<DriverReportItem>();
		
		return getTableDataProvider().getItemsByRange(0, totalCount);

	}

    @Override
	protected ReportCriteria getReportCriteria()
    {
    	return getReportCriteriaService().getDriverReportCriteria(getUser().getGroupID(), getLocale());
    }


/*
    
    @Override
    public String getMappingId()
    {
      
        return "pretty:driversReport";
    }
    
    @Override
    public String getMappingIdWithCriteria()
    {
        return "pretty:driversReportWithCriteria";
    }
*/

    // TablePrefOptions overrides
    @Override
    public Map<String, Boolean> getDefaultColumns()
    {
        final HashMap<String, Boolean> columns = new HashMap<String, Boolean>();
        final List<String> availableColumns = getAvailableColumns();
        for (int i : DEFAULT_COLUMN_INDICES)
            columns.put(availableColumns.get(i), true);
        return columns;
    }

    @Override
    public String fieldValue(DriverReportItem item, String column)
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
        return TableType.DRIVER_REPORT;
    }
    
    // END - TablePrefOptions overrides
}

