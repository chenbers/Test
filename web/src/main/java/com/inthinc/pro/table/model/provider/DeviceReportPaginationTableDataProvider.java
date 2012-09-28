package com.inthinc.pro.table.model.provider;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.model.DeviceReportItem;
import com.inthinc.pro.model.pagination.PageParams;


public class DeviceReportPaginationTableDataProvider extends ReportPaginationTableDataProvider<DeviceReportItem> {

	private static final long serialVersionUID = 6565374170287773433L;
	
	@Override
	public List<DeviceReportItem> getItemsByRange(int firstRow, int endRow) {
		if (endRow < 0) {
			return new ArrayList<DeviceReportItem>();
		}
		PageParams pageParams = new PageParams(firstRow, endRow, getSort(), getFilters());
		return getReportDAO().getDeviceReportPage(getGroupID(), pageParams);
	}

	@Override
	public int getRowCount() {
		if (getGroupID() == null)
			return 0;

		return getReportDAO().getDeviceReportCount(getGroupID(), getFilters());
	}


}
