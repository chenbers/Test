package com.inthinc.pro.backing;

import com.inthinc.pro.model.CrashSummary;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.wrapper.ScoreableEntityPkg;

public class TrendBeanItem {
	
	private ScoreableEntity scoreableEntity;
	private ScoreableEntityPkg scoreableEntityPkg;
	private CrashSummary crashSummary;
	
	public String getCrashesPerMillionMiles() {
		return crashSummary.getCrashesPerMillionMilesString();
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
	public Integer getGroupID(){
		
		return scoreableEntity.getEntityID();
	}
	public String getGroupName(){
		return scoreableEntity.getIdentifier();
	}
	public ScoreableEntity getScoreableEntity() {
		return scoreableEntity;
	}
	public void setScoreableEntity(ScoreableEntity scoreableEntity) {
		this.scoreableEntity = scoreableEntity;
	}
	public Integer getScore() {
		return scoreableEntity.getScore();
	}
	public Boolean getShow() {
		return scoreableEntityPkg.getShow();
	}
	public void setShow(Boolean show) {
		scoreableEntityPkg.setShow(show);
	}
}
