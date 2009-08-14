package com.inthinc.pro.backing;

import com.inthinc.pro.dao.ScoreDAO;
import com.inthinc.pro.model.CrashSummary;

public class CrashSummaryBean extends BaseBean {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5274498413521235203L;
	

	private ScoreDAO scoreDAO;
	private CrashSummary crashSummary;

	public CrashSummary getCrashSummary() {
//		crashSummary = scoreDAO.getCrashSummaryData(this.getUser().getGroupID());
		crashSummary = new CrashSummary();
		crashSummary.setCrashesPerMillionMiles(6);
		crashSummary.setDaysSinceLastCrash(100);
		crashSummary.setMilesSinceLastCrash(2000000);
		
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
	
	public Integer getCrashesPerMillionMiles() {
		if (crashSummary == null) getCrashSummary();
		return crashSummary.getCrashesPerMillionMiles();
	}

	public Integer getDaysSinceLastCrash() {
		if (crashSummary == null) getCrashSummary();
		return crashSummary.getDaysSinceLastCrash();
	}

	public Integer getMilesSinceLastCrash() {
		if (crashSummary == null) getCrashSummary();
		return crashSummary.getMilesSinceLastCrash();
	}
	
	

}
