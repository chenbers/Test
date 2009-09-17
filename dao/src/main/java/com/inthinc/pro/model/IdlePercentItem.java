package com.inthinc.pro.model;

public class IdlePercentItem {

	// time is in seconds
	Long drivingTime;
	Long idlingTime;
	
	public IdlePercentItem(Long drivingTime, Long idlingTime) {
		super();
		this.drivingTime = drivingTime;
		this.idlingTime = idlingTime;
	}
	public Long getDrivingTime() {
		return drivingTime;
	}
	public void setDrivingTime(Long drivingTime) {
		this.drivingTime = drivingTime;
	}
	public Long getIdlingTime() {
		return idlingTime;
	}
	public void setIdlingTime(Long idlingTime) {
		this.idlingTime = idlingTime;
	}
}
