package com.inthinc.pro.backing.paging;

import org.apache.log4j.Logger;

import com.inthinc.pro.model.VehicleReportItem;
import com.inthinc.pro.model.pagination.SortOrder;
import com.inthinc.pro.model.pagination.TableSortField;
import com.inthinc.pro.reports.ReportCriteria;

public class PagingVehicleReportBean extends BasePagingReportBean<VehicleReportItem>
{
	private static final long serialVersionUID = 9116805820208771789L;

	private static final Logger logger = Logger.getLogger(PagingVehicleReportBean.class);

	@Override
	public TableSortField getDefaultSort() {
		return new TableSortField(SortOrder.ASCENDING, "vehicleName");
	}

    @Override
	protected ReportCriteria getReportCriteria()
    {
    	return getReportCriteriaService().getVehicleReportCriteria(getUser().getGroupID(), getLocale());
    }


}

