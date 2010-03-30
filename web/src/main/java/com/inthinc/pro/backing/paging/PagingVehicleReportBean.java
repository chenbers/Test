package com.inthinc.pro.backing.paging;

import org.apache.log4j.Logger;

import com.inthinc.pro.model.VehicleReportItem;
import com.inthinc.pro.model.pagination.SortOrder;
import com.inthinc.pro.model.pagination.TableSortField;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.table.model.provider.VehicleReportPaginationTableDataProvider;

public class PagingVehicleReportBean extends BasePagingReportBean<VehicleReportItem>
{
	private static final long serialVersionUID = 9116805820208771789L;

	private static final Logger logger = Logger.getLogger(PagingVehicleReportBean.class);
	private VehicleReportPaginationTableDataProvider tableDataProvider;


    
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


}

