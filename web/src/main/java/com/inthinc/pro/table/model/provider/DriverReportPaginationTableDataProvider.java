package com.inthinc.pro.table.model.provider;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.dao.ReportDAO;
import com.inthinc.pro.model.DriverReportItem;
import com.inthinc.pro.model.pagination.PageParams;

public class DriverReportPaginationTableDataProvider extends GenericPaginationTableDataProvider<DriverReportItem> {

	private static final long serialVersionUID = 6565374170287773433L;
	
	private ReportDAO                reportDAO;
	private Integer 				groupID;

	@Override
	public List<DriverReportItem> getItemsByRange(int firstRow, int endRow) {
		if (endRow < 0) {
			return new ArrayList<DriverReportItem>();
		}
		PageParams pageParams = new PageParams(firstRow, endRow, getSort(), getFilters());
		return reportDAO.getDriverReportPage(groupID, pageParams);
	}

	@Override
	public int getRowCount() {
		if (groupID == null)
			return 0;

		return reportDAO.getDriverReportCount(groupID, getFilters());
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
