package com.inthinc.pro.backing.paging;

import org.apache.log4j.Logger;

import com.inthinc.pro.model.DriverReportItem;
import com.inthinc.pro.model.pagination.SortOrder;
import com.inthinc.pro.model.pagination.TableSortField;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.table.model.provider.DriverReportPaginationTableDataProvider;

public class PagingDriverReportBean extends BasePagingReportBean<DriverReportItem>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5349999687948286628L;


	private static final Logger logger = Logger.getLogger(PagingDriverReportBean.class);
    
    
	private DriverReportPaginationTableDataProvider tableDataProvider;


	// TablePrefOptions info
    @Override
    public void init()
    {
        super.init();

		logger.info("PagingDriverReportBean - constructor");
		
        tableDataProvider.setSort(new TableSortField(SortOrder.ASCENDING, "driverName"));
        tableDataProvider.setGroupID(this.getProUser().getUser().getGroupID());
		getTable().initModel(tableDataProvider);
		
    }
    
    

    public DriverReportPaginationTableDataProvider getTableDataProvider() {
		return tableDataProvider;
	}



	public void setTableDataProvider(DriverReportPaginationTableDataProvider tableDataProvider) {
		this.tableDataProvider = tableDataProvider;
	}



    @Override
	protected ReportCriteria getReportCriteria()
    {
    	return getReportCriteriaService().getDriverReportCriteria(getUser().getGroupID(), getLocale());
    }


}

