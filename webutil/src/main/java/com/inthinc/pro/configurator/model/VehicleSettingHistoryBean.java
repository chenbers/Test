package com.inthinc.pro.configurator.model;

import java.util.Date;

import com.inthinc.pro.model.configurator.VehicleSettingHistory;

public class VehicleSettingHistoryBean {

	private VehicleSettingHistory vehicleSettingHistory;
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setVehicleSettingHistory(VehicleSettingHistory vehicleSettingHistory) {
		this.vehicleSettingHistory = vehicleSettingHistory;
	}

	public Integer getDeviceID() {
		return vehicleSettingHistory.getDeviceID();
	}

	public Date getModified() {
		return vehicleSettingHistory.getModified();
	}

	public String getNewValue() {
		return vehicleSettingHistory.getNewValue();
	}

	public String getOldValue() {
		return vehicleSettingHistory.getOldValue();
	}

	public String getReason() {
		return vehicleSettingHistory.getReason();
	}

	public Integer getSetHistoryID() {
		return vehicleSettingHistory.getSetHistoryID();
	}

	public Integer getSettingID() {
		return vehicleSettingHistory.getSettingID();
	}

	public Integer getUserID() {
		return vehicleSettingHistory.getUserID();
	}

	public Integer getVehicleID() {
		return vehicleSettingHistory.getVehicleID();
	}

	public String toString() {
		return vehicleSettingHistory.toString();
	}
	
}
