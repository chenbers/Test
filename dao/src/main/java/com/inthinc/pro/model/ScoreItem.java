package com.inthinc.pro.model;


public class ScoreItem extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ScoreType scoreType;
	private Integer score;
	
	public ScoreItem() {
		super();
	}
	
	public ScoreItem(ScoreType scoreType, Integer score) {
		super();
		this.scoreType = scoreType;
		this.score = score;
	}
	
	public ScoreType getScoreType() {
		return scoreType;
	}
	public void setScoreType(ScoreType scoreType) {
		this.scoreType = scoreType;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
}
