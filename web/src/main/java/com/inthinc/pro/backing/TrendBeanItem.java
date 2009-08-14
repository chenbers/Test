package com.inthinc.pro.backing;

import com.inthinc.pro.model.CrashSummary;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.wrapper.ScoreableEntityPkg;

public class TrendBeanItem {
	
	private ScoreableEntityPkg scoreableEntityPkg;
	private CrashSummary crashSummary;
	
	public Integer getCrashesPerMillionMiles() {
		return crashSummary.getCrashesPerMillionMiles();
	}
	public ScoreableEntity getSe() {
		return scoreableEntityPkg.getSe();
	}
	public ScoreableEntityPkg getScoreableEntityPkg() {
		return scoreableEntityPkg;
	}
	public void setScoreableEntityPkg(ScoreableEntityPkg scoreableEntityPkg) {
		this.scoreableEntityPkg = scoreableEntityPkg;
	}
	public CrashSummary getCrashSummary() {
		return crashSummary;
	}
	public void setCrashSummary(CrashSummary crashSummary) {
		this.crashSummary = crashSummary;
	}
}
