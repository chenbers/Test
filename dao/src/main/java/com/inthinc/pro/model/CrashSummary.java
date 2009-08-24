package com.inthinc.pro.model;

import java.text.DecimalFormat;
import java.util.Date;

public class CrashSummary extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8590279451272849269L;
	private static DecimalFormat onePlace = new DecimalFormat("#0.0");
	
	public CrashSummary(Integer crashEvents, Integer crashOdometer,Date lastCrashDate,Integer totalCrashes) {
		super();
       	calculateAndSetCrashesPerMillionMiles(crashEvents, crashOdometer);
        calculateAndSetDaysSinceLastCrash(lastCrashDate);
        calculateAndSetMilesSinceLastCrash(crashOdometer);
        setTotalCrashes(totalCrashes);

	}
	private Double crashesPerMillionMiles;
	private Integer milesSinceLastCrash;
	private Integer daysSinceLastCrash;
	private Integer totalCrashes;
	
	public Integer getTotalCrashes() {
		return totalCrashes;
	}
	public void setTotalCrashes(Integer totalCrashes) {
		this.totalCrashes = totalCrashes;
	}
	public void setCrashesPerMillionMiles(Double crashesPerMillionMiles) {
		this.crashesPerMillionMiles = crashesPerMillionMiles;
	}
	public void setMilesSinceLastCrash(Integer milesSinceLastCrash) {
		this.milesSinceLastCrash = milesSinceLastCrash;
	}
	public void setDaysSinceLastCrash(Integer daysSinceLastCrash) {
		this.daysSinceLastCrash = daysSinceLastCrash;
	}
	
	public Double getCrashesPerMillionMiles() {
		return crashesPerMillionMiles;
	}
	public String getCrashesPerMillionMilesString(){
		
		return onePlace.format(getCrashesPerMillionMiles());
		
	}
	public Integer getMilesSinceLastCrash() {
		return milesSinceLastCrash;
	}
	public Integer getDaysSinceLastCrash() {
		return daysSinceLastCrash;
	}
	
	public void calculateAndSetCrashesPerMillionMiles(Integer crashEvents, Integer crashOdometer){
		if (crashOdometer.equals(0)){
			
			crashesPerMillionMiles = 0.0;
			return;
		}
		crashesPerMillionMiles = new Double(crashEvents)/new Double(crashOdometer)/100000000.0;
	}
	public void calculateAndSetMilesSinceLastCrash(Integer crashOdometer){
		milesSinceLastCrash = crashOdometer*100;
	}
	public void calculateAndSetDaysSinceLastCrash(Date lastCrashDate){
		if (lastCrashDate == null){
			
			daysSinceLastCrash = 365;
			return;
		}
		Date now = new Date();
		Long diffInDays = new Long((now.getTime()-lastCrashDate.getTime())/1000/60/60/24);
		
		daysSinceLastCrash = diffInDays.intValue();
	}
}
