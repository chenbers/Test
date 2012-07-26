package com.inthinc.pro.model;

import java.util.Date;

public class TrendItem  extends ScoreItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Number distance;
	private Date date;
	
	public TrendItem() {
		super();
	}
	
	public TrendItem(ScoreType scoreType, Number distance, Integer score, Date date) {
		super(scoreType, score);
		this.distance = distance;
		this.date = date;
	}
	
	public Number getDistance() {
		return distance;
	}
	public void setDistance(Number distance) {
		this.distance = distance;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public String toString()
	{
		return "Date: " + date + " score: " + getScore();
	}
}
