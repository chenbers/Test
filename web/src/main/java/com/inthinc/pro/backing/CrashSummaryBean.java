package com.inthinc.pro.backing;

import java.text.DecimalFormat;

import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.model.CrashSummary;

public class CrashSummaryBean extends BaseBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5274498413521235203L;

	private ScoreDAO scoreDAO;
	private CrashSummary crashSummary;
	private NavigationBean navigation; 

	public void initBean(){
		
		getCrashSummaryForGroup(navigation.getGroupID());
		
	}
	public CrashSummary getCrashSummaryForGroup(Integer groupID) {
		
		crashSummary = scoreDAO.getGroupCrashSummaryData(groupID);
//		crashSummary = new CrashSummary(100,2345,new Date(),204);
		
		return crashSummary;
	}

	public CrashSummary getCrashSummaryForDriver(Integer driverID) {
		
		crashSummary = scoreDAO.getDriverCrashSummaryData(driverID);
//		crashSummary = new CrashSummary(100,2345,new Date(),204);
		
		return crashSummary;
	}
	public CrashSummary getCrashSummaryForVehicle(Integer vehicleID) {
		
		crashSummary = scoreDAO.getVehicleCrashSummaryData(vehicleID);
//		crashSummary = new CrashSummary(100,2345,new Date(),204);
		
		return crashSummary;
	}
	public ScoreDAO getScoreDAO() {
		return scoreDAO;
	}

	public void setScoreDAO(ScoreDAO scoreDAO) {
		this.scoreDAO = scoreDAO;
	}

	public void setCrashSummary(CrashSummary crashSummary) {
		this.crashSummary = crashSummary;
	}
	
	public String getCrashesPerMillionMiles() {
		return crashSummary.getCrashesPerMillionMilesString();
	}
	public Integer getDaysSinceLastCrash() {
		return crashSummary.getDaysSinceLastCrash();
	}

	public Integer getMilesSinceLastCrash() {
		return crashSummary.getMilesSinceLastCrash();
	}
	
	public NavigationBean getNavigation() {
		return navigation;
	}
	public void setNavigation(NavigationBean navigation) {
		this.navigation = navigation;
	}
}
