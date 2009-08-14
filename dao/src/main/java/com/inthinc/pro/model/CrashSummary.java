package com.inthinc.pro.model;

import java.util.Date;

public class CrashSummary extends BaseEntity {

	private Integer crashesPerMillionMiles;
	private Integer milesSinceLastCrash;
	private Integer daysSinceLastCrash;
	
	
	public void setCrashesPerMillionMiles(Integer crashesPerMillionMiles) {
		this.crashesPerMillionMiles = crashesPerMillionMiles;
	}
	public void setMilesSinceLastCrash(Integer milesSinceLastCrash) {
		this.milesSinceLastCrash = milesSinceLastCrash;
	}
	public void setDaysSinceLastCrash(Integer daysSinceLastCrash) {
		this.daysSinceLastCrash = daysSinceLastCrash;
	}
	
	public Integer getCrashesPerMillionMiles() {
		return crashesPerMillionMiles;
	}
	public Integer getMilesSinceLastCrash() {
		return milesSinceLastCrash;
	}
	public Integer getDaysSinceLastCrash() {
		return daysSinceLastCrash;
	}
	
	public void calculateAndSetCrashesPerMillionMiles(Integer crashEvents, Integer crashOdometer){
		crashesPerMillionMiles = crashEvents/crashOdometer/100000000;
	}
	public void calculateAndSetMilesSinceLastCrash(Integer crashOdometer){
		milesSinceLastCrash = crashOdometer*100;
	}
	public void calculateAndSetDaysSinceLastCrash(Date lastCrashDate){
		Date now = new Date();
		Long diffInDays = new Long((now.getTime()-lastCrashDate.getTime())/1000/60/60/24);
		
		daysSinceLastCrash = diffInDays.intValue();
	}
}
