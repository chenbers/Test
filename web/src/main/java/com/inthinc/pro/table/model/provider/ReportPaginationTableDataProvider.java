package com.inthinc.pro.table.model.provider;

import java.util.List;

import com.inthinc.pro.dao.ReportDAO;

public abstract class ReportPaginationTableDataProvider<T> extends GenericPaginationTableDataProvider<T> {

	private static final long serialVersionUID = -4772526623970802091L;

	private ReportDAO                reportDAO;
	private Integer 				groupID;

	public abstract List<T> getItemsByRange(int firstRow, int endRow);
	public abstract int getRowCount();

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
