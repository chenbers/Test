package com.inthinc.pro.backing.paging;

import org.apache.log4j.Logger;

import com.inthinc.pro.model.DriverReportItem;
import com.inthinc.pro.model.pagination.SortOrder;
import com.inthinc.pro.model.pagination.TableSortField;
import com.inthinc.pro.reports.ReportCriteria;

public class PagingDriverReportBean extends BasePagingReportBean<DriverReportItem>
{
	private static final long serialVersionUID = 5349999687948286628L;

	private static final Logger logger = Logger.getLogger(PagingDriverReportBean.class);
    
    @Override
	public TableSortField getDefaultSort() {
		return new TableSortField(SortOrder.ASCENDING, "driverName");
	}

    @Override
	protected ReportCriteria getReportCriteria()
    {
    	return getReportCriteriaService().getDriverReportCriteria(getUser().getGroupID(), getLocale());
    }


}

