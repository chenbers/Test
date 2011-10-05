package com.inthinc.pro.domain.settings;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="vsetHistory")
public class VehicleSettingHistory {
    
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
//    @ManyToOne(fetch=FetchType.LAZY)
//    @JoinColumn(name="userID")
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
	@Override
	public String toString() {
		return "VehicleSettingHistoryJPA [setHistoryID=" + setHistoryID
				+ ", vehicleID=" + vehicleID + ", deviceID=" + deviceID
				+ ", settingID=" + settingID + ", oldValue=" + oldValue
				+ ", newValue=" + newValue + ", modified=" + modified
				+ ", reason=" + reason + ", userID=" + userID + "]";
	}
//	public User getUser() {
//		return user;
//	}
//	public void setUser(User user) {
//		this.user = user;
//	}
	public Integer getUserID() {
		return userID;
	}
	public void setUserID(Integer userID) {
		this.userID = userID;
	}
}
