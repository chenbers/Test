package com.inthinc.pro.table.model.provider;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.model.DriverReportItem;
import com.inthinc.pro.model.pagination.PageParams;

public class DriverReportPaginationTableDataProvider extends ReportPaginationTableDataProvider<DriverReportItem> {

	private static final long serialVersionUID = 6565374170287773433L;
	

	@Override
	public List<DriverReportItem> getItemsByRange(int firstRow, int endRow) {
		if (endRow < 0) {
			return new ArrayList<DriverReportItem>();
		}
		PageParams pageParams = new PageParams(firstRow, endRow, getSort(), getFilters());
		return getReportDAO().getDriverReportPage(getGroupID(), pageParams);
	}

	@Override
	public int getRowCount() {
		if (getGroupID() == null)
			return 0;

		return getReportDAO().getDriverReportCount(getGroupID(), getFilters());
	}

}
