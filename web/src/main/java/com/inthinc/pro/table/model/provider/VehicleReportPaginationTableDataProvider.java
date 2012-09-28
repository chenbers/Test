package com.inthinc.pro.table.model.provider;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.model.VehicleReportItem;
import com.inthinc.pro.model.pagination.PageParams;


public class VehicleReportPaginationTableDataProvider extends ReportPaginationTableDataProvider<VehicleReportItem> {

	private static final long serialVersionUID = 6565374170287773433L;
	

	@Override
	public List<VehicleReportItem> getItemsByRange(int firstRow, int endRow) {
		if (endRow < 0) {
			return new ArrayList<VehicleReportItem>();
		}
		PageParams pageParams = new PageParams(firstRow, endRow, getSort(), getFilters());
		return getReportDAO().getVehicleReportPage(getGroupID(), pageParams);
	}

	@Override
	public int getRowCount() {
		if (getGroupID() == null)
			return 0;

		return getReportDAO().getVehicleReportCount(getGroupID(), getFilters());
	}
}
