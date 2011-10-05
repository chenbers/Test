package com.inthinc.pro.domain.settings;

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
@Table(name="desiredVSet")
public class DesiredVehicleSetting {
	@Id
	@Column(name="desiredVSetID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer desiredVehicleSettingID;
	private Integer settingID;
	private Integer vehicleID;
	private Integer deviceID;
	@Temporal(TemporalType.TIMESTAMP)
	private Date modified;
	private String value;
	private String reason;
	private Integer userID;
	
	
	public Integer getDesiredVehicleSettingID() {
		return desiredVehicleSettingID;
	}
	public void setDesiredVehicleSettingID(Integer desiredVehicleSettingID) {
		this.desiredVehicleSettingID = desiredVehicleSettingID;
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
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Integer getUserID() {
		return userID;
	}
	public void setUserID(Integer userID) {
		this.userID = userID;
	}
	@Override
	public String toString() {
		return "DesiredVehicleSetting [desiredVehicleSettingID="
				+ desiredVehicleSettingID + ", settingID=" + settingID
				+ ", vehicleID=" + vehicleID + ", deviceID=" + deviceID
				+ ", modified=" + modified + ", value=" + value + ", reason="
				+ reason + ", userID=" + userID + "]";
	}
}
