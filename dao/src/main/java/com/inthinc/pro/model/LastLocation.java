package com.inthinc.pro.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LastLocation {
	private Integer driverID;
	private Integer vehicleID;
	private Date time;
	private LatLng loc;
	private Integer speed;
	private Integer head;

	@XmlElement(name = "location")
	public LatLng getLoc() {
		return loc;
	}

	public void setLoc(LatLng loc) {
		this.loc = loc;
	}

	public LastLocation() {

	}

	public Integer getDriverID() {
		return driverID;
	}

	public void setDriverID(Integer driverID) {
		this.driverID = driverID;
	}

	public Integer getVehicleID() {
		return vehicleID;
	}

	public void setVehicleID(Integer vehicleID) {
		this.vehicleID = vehicleID;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Integer getSpeed() {
		return speed;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}

	public Integer getHead() {
		return head;
	}

	public void setHead(Integer head) {
		this.head = head;
	}
}
