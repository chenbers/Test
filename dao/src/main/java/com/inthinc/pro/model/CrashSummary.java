package com.inthinc.pro.model;

import java.text.DecimalFormat;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CrashSummary extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8590279451272849269L;

	private Integer daysSinceLastCrash;
	private Integer totalCrashes;
	private Integer crashesInTimePeriod;
	private Number totalMiles;
	private Number milesSinceLastCrash;
	
	private Double crashesPerMillionMiles;
	
	private static DecimalFormat onePlace = new DecimalFormat("#0.0");


	public CrashSummary(Integer crashesInTimePeriod, Integer totalCrashes, Integer daysSinceLastCrash, Number totalMiles, Number milesSinceLastCrash) {
		super();
		this.crashesInTimePeriod = crashesInTimePeriod;
		this.daysSinceLastCrash = daysSinceLastCrash;
		this.totalCrashes = totalCrashes;
		this.totalMiles = totalMiles;
		this.milesSinceLastCrash = milesSinceLastCrash;
		calculateAndSetCrashesPerMillionMiles();
	}
	
	public Integer getTotalCrashes() {
		return totalCrashes;
	}
	public void setTotalCrashes(Integer totalCrashes) {
		this.totalCrashes = totalCrashes;
	}
	public void setMilesSinceLastCrash(Number milesSinceLastCrash) {
		this.milesSinceLastCrash = milesSinceLastCrash;
	}
	public void setDaysSinceLastCrash(Integer daysSinceLastCrash) {
		this.daysSinceLastCrash = daysSinceLastCrash;
	}
	public Number getMilesSinceLastCrash() {
		return milesSinceLastCrash;
	}
	public Integer getDaysSinceLastCrash() {
		return daysSinceLastCrash;
	}
	public Number getTotalMiles() {
		return totalMiles;
	}
	public void setTotalMiles(Number totalMiles) {
		this.totalMiles = totalMiles;
	}
	
	public Double getCrashesPerMillionMiles() {
		return crashesPerMillionMiles;
	}
	public void setCrashesPerMillionMiles(Double crashesPerMillionMiles) {
		this.crashesPerMillionMiles = crashesPerMillionMiles;
	}
	public String getCrashesPerMillionMilesString(){
		
		return onePlace.format(getCrashesPerMillionMiles());
		
	}
	public void calculateAndSetCrashesPerMillionMiles()
	{
		if (totalMiles.equals(0)){
			
			crashesPerMillionMiles = 0.0;
			return;
		}
		crashesPerMillionMiles = (new Double(crashesInTimePeriod)*1000000.0)/new Double(totalMiles.longValue());
	}

	public Integer getCrashesInTimePeriod() {
		return crashesInTimePeriod;
	}

	public void setCrashesInTimePeriod(Integer crashesInTimePeriod) {
		this.crashesInTimePeriod = crashesInTimePeriod;
	}
}
