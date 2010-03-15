package com.inthinc.pro.table.model.provider;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.Interval;

import com.inthinc.pro.dao.ReportDAO;
import com.inthinc.pro.model.IdlingReportItem;
import com.inthinc.pro.model.pagination.PageParams;


public class IdlingReportPaginationTableDataProvider extends GenericPaginationTableDataProvider<IdlingReportItem> {

	private static final long serialVersionUID = 6565374170287773433L;
	
	private ReportDAO               reportDAO;
	private Integer 				groupID;
	private Interval 				interval;
	private Integer					totalDrivers;
	private Integer					totalDriversWithIdleStats;

	@Override
	public List<IdlingReportItem> getItemsByRange(int firstRow, int endRow) {
		if (endRow < 0) {
			return new ArrayList<IdlingReportItem>();
		}
		PageParams pageParams = new PageParams(firstRow, endRow, getSort(), getFilters());
		return reportDAO.getIdlingReportPage(groupID, interval, pageParams);
	}

	@Override
	public int getRowCount() {
		if (groupID == null)
			return 0;

		int rowCount =  reportDAO.getIdlingReportCount(groupID, interval, getFilters());
		setTotalDriversWithIdleStats(rowCount);
		setTotalDrivers(reportDAO.getIdlingReportTotalCount(groupID, interval, getFilters()));
		
		return rowCount;
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

	public Interval getInterval() {
		return interval;
	}

	public void setInterval(Interval interval) {
		this.interval = interval;
	}

	public Integer getTotalDrivers() {
		return totalDrivers;
	}

	public void setTotalDrivers(Integer totalDrivers) {
		this.totalDrivers = totalDrivers;
	}

	public Integer getTotalDriversWithIdleStats() {
		return totalDriversWithIdleStats;
	}

	public void setTotalDriversWithIdleStats(Integer totalDriversWithIdleStats) {
		this.totalDriversWithIdleStats = totalDriversWithIdleStats;
	}


}
