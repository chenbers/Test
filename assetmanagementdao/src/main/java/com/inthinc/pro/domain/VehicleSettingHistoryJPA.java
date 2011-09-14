package com.inthinc.pro.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="vsetHistory")
public class VehicleSettingHistoryJPA {
    
    @Id
	@Column(name="vsetHistoryID")
    private Integer setHistoryID;   
    private Integer vehicleID;  
    private Integer deviceID;
    private Integer settingID;
	@Column(name="old")
    private String  oldValue;
	@Column(name="new")
    private String  newValue;
    private Date    modified;
    private String  reason;
    private Integer userID;
   
    public Integer getSetHistoryID() {
        return setHistoryID;
    }
    public void setSetHistoryID(Integer setHistoryID) {
        this.setHistoryID = setHistoryID;
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
    public Integer getSettingID() {
        return settingID;
    }
    public void setSettingID(Integer settingID) {
        this.settingID = settingID;
    }
    public String getOldValue() {
        return oldValue;
    }
    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }
    public String getNewValue() {
        return newValue;
    }
    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }
    public Date getModified() {
        return modified;
    }
    public void setModified(Date modified) {
        this.modified = modified;
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
		return "VehicleSettingHistory [setHistoryID=" + setHistoryID
				+ ", vehicleID=" + vehicleID + ", deviceID=" + deviceID
				+ ", settingID=" + settingID + ", oldValue=" + oldValue
				+ ", newValue=" + newValue + ", modified=" + modified
				+ ", reason=" + reason + ", userID=" + userID + "]";
	}
}
