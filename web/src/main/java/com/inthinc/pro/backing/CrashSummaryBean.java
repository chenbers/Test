package com.inthinc.pro.backing;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.model.CrashSummary;

public class CrashSummaryBean extends BaseBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5274498413521235203L;
	private static final Logger logger = Logger.getLogger(CrashSummaryBean.class);
	
	private ScoreDAO scoreDAO;
	private CrashSummary crashSummary;
	private NavigationBean navigation; 

	public void initBean(){

		if (navigation.getGroupID() != null)
			setCrashSummary(getCrashSummaryForGroup(navigation.getGroupID()));
//		else if (navigation.getDriverID() != null)
//			setCrashSummary(getCrashSummaryForDriver(navigation.getDriverID()));
		
	}
	private CrashSummary getCrashSummaryForGroup(Integer groupID) {
		logger.info("getCrashSummaryForGroup:  " + groupID);
		return scoreDAO.getGroupCrashSummaryData(groupID, getGroupHierarchy());
		
	}

//	public CrashSummary getCrashSummaryForDriver(Integer driverID) {
//		
//		logger.info("getCrashSummaryForDriver:  " + driverID);
//		return scoreDAO.getDriverCrashSummaryData(driverID);
//		
//	}
	public void setCrashSummary(CrashSummary crashSummary) {
		this.crashSummary = crashSummary;
	}
	
	public Double getCrashesPerMillionMiles() {
		return crashSummary.getCrashesPerMillionMiles();
	}
	public CrashSummary getCrashSummary() {
		return crashSummary;
	}
	public Integer getDaysSinceLastCrash() {

		return crashSummary.getDaysSinceLastCrash();
	}

	public Long getMilesSinceLastCrash() {
		return crashSummary.getMilesSinceLastCrash().longValue();
	}
	
	public Integer getTotalCrashes(){
		return crashSummary.getTotalCrashes();
	}

	public NavigationBean getNavigation() {
		return navigation;
	}
	public void setNavigation(NavigationBean navigation) {
		this.navigation = navigation;
	}
	public ScoreDAO getScoreDAO() {
		return scoreDAO;
	}
	public void setScoreDAO(ScoreDAO scoreDAO) {
		this.scoreDAO = scoreDAO;
	}

}
