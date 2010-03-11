package com.inthinc.pro.table.model.provider;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.dao.ReportDAO;
import com.inthinc.pro.model.DeviceReportItem;
import com.inthinc.pro.model.pagination.PageParams;


public class DeviceReportPaginationTableDataProvider extends GenericPaginationTableDataProvider<DeviceReportItem> {

	private static final long serialVersionUID = 6565374170287773433L;
	
	private ReportDAO                reportDAO;
	private Integer 				groupID;

	@Override
	public List<DeviceReportItem> getItemsByRange(int firstRow, int endRow) {
		if (endRow < 0) {
			return new ArrayList<DeviceReportItem>();
		}
		PageParams pageParams = new PageParams(firstRow, endRow, getSort(), getFilters());
		return reportDAO.getDeviceReportPage(groupID, pageParams);
	}

	@Override
	public int getRowCount() {
		if (groupID == null)
			return 0;

		return reportDAO.getDeviceReportCount(groupID, getFilters());
	}

	public ReportDAO getReportDAO() {
		return reportDAO;
	}

	public void setReportDAO(ReportDAO reportDAO) {
		this.reportDAO = reportDAO;
	}

	public Integer getGroupID() {
		return groupID;
	}

	public void setGroupID(Integer groupID) {
		this.groupID = groupID;
	}

}
