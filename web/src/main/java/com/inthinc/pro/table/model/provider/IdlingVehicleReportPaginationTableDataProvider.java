package com.inthinc.pro.table.model.provider;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.Interval;

import com.inthinc.pro.model.IdlingReportItem;
import com.inthinc.pro.model.pagination.PageParams;


public class IdlingVehicleReportPaginationTableDataProvider extends ReportPaginationTableDataProvider<IdlingReportItem> {

	
	private Interval 				interval;
	private Integer					totalDrivers;
	private Integer					totalDriversWithIdleStats;

	@Override
	public List<IdlingReportItem> getItemsByRange(int firstRow, int endRow) {
		if (endRow < 0) {
			return new ArrayList<IdlingReportItem>();
		}
		PageParams pageParams = new PageParams(firstRow, endRow, getSort(), getFilters());
		return getReportDAO().getIdlingVehicleReportPage(getGroupID(), interval, pageParams);
	}

	@Override
	public int getRowCount() {
		if (getGroupID() == null)
			return 0;

		int idleRowCount =  getReportDAO().getIdlingVehicleReportSupportsIdleStatsCount(getGroupID(), interval, getFilters());
		setTotalDriversWithIdleStats(idleRowCount);
		int rowCount =  getReportDAO().getIdlingVehicleReportCount(getGroupID(), interval, getFilters());
		setTotalDrivers(rowCount);
		
		return rowCount;
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
