package com.inthinc.pro.model.hos;

import java.util.Date;

public class HOSSummaryParams {

	private Date summaryDate;
	private Integer totalCount;
	private Date startDate;
	private Date endDate;
	private Byte checksum;

	public HOSSummaryParams() {
	}

	public Date getSummaryDate() {
		return summaryDate;
	}

	public void setSummaryDate(Date summaryDate) {
		this.summaryDate = summaryDate;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Byte getChecksum() {
		return checksum;
	}

	public void setChecksum(Byte checksum) {
		this.checksum = checksum;
	}

}
