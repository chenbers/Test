package com.inthinc.pro.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="actualVSet")
public class ActualVehicleSetting {
	@Id
	@Column(name="actualVSetID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer actualVehicleSettingID;
	private Integer settingID;
	private Integer vehicleID;
	private Integer deviceID;
	@Temporal(TemporalType.TIMESTAMP)
	private Date modified;
	private String value;
	@Column(name="vsetHistoryID")
	private Integer vehicleSettingHistoryID;
	
	public Integer getActualVehicleSettingID() {
		return actualVehicleSettingID;
	}
	public void setActualVehicleSettingID(Integer actualVehicleSettingID) {
		this.actualVehicleSettingID = actualVehicleSettingID;
	}
	public Integer getSettingID() {
		return settingID;
	}
	public void setSettingID(Integer settingID) {
		this.settingID = settingID;
	}
	public Integer getVehicleID() {
		return vehicleID;
	}
	public void setVehicleID(Integer vehicleID) {
		this.vehicleID = vehicleID;
	}
	public Integer getDeviceID() {
		return deviceID;
	}
	public void setDeviceID(Integer deviceID) {
		this.deviceID = deviceID;
	}
	public Date getModified() {
		return modified;
	}
	public void setModified(Date modified) {
		this.modified = modified;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Integer getVehicleSettingHistoryID() {
		return vehicleSettingHistoryID;
	}
	public void setVehicleSettingHistoryID(Integer vehicleSettingHistoryID) {
		this.vehicleSettingHistoryID = vehicleSettingHistoryID;
	}
	@Override
	public String toString() {
		return "ActualVehicleSetting [actualVehicleSettingID="
				+ actualVehicleSettingID + ", settingID=" + settingID
				+ ", vehicleID=" + vehicleID + ", deviceID=" + deviceID
				+ ", modified=" + modified + ", value=" + value
				+ ", vehicleSettingHistoryID=" + vehicleSettingHistoryID + "]";
	}
}
