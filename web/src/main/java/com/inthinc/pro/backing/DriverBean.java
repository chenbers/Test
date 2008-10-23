package com.inthinc.pro.backing;

import org.apache.log4j.Logger;

public class DriverBean extends BaseBean
{
	private static final Logger logger = Logger.getLogger(BreakdownBean.class);
	
	private String driverOverallScore;
	private String overallScoreHistory;
	public String getDriverOverallScore() {
		return driverOverallScore;
	}
	public void setDriverOverallScore(String driverOverallScore) {
		this.driverOverallScore = driverOverallScore;
	}
	public String getOverallScoreHistory() {
		return overallScoreHistory;
	}
	public void setOverallScoreHistory(String overallScoreHistory) {
		this.overallScoreHistory = overallScoreHistory;
	}
}
