package com.inthinc.pro.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class IdlePercentItem extends BaseScore implements Comparable<IdlePercentItem> {

	// time is in seconds
	Long drivingTime;
	Long idlingTime;
	Integer numVehicles;
	Integer numEMUVehicles;
	
	public IdlePercentItem(Long drivingTime, Long idlingTime, Date date, Integer numVehicles, Integer numEMUVehicles) {
		super(date);
		this.drivingTime = drivingTime;
		this.idlingTime = idlingTime;
		this.numVehicles = numVehicles;
		this.numEMUVehicles = numEMUVehicles;
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
	public Integer getNumVehicles() {
		return numVehicles;
	}
	public void setNumVehicles(Integer numVehicles) {
		this.numVehicles = numVehicles;
	}
	public Integer getNumEMUVehicles() {
		return numEMUVehicles;
	}
	public void setNumEMUVehicles(Integer numEMUVehicles) {
		this.numEMUVehicles = numEMUVehicles;
	}
	@Override
	public int compareTo(IdlePercentItem o) {
		
		if (getDate() == null && o.getDate() == null)
			return 0;
		if (getDate() == null)
			return -1;
		if (o.getDate() == null)
			return 1;
        return getDate().compareTo(o.getDate());

	}
}
