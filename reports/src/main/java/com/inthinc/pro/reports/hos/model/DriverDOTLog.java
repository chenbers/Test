package com.inthinc.pro.reports.hos.model;

import com.inthinc.hos.model.HOSOrigin;
import com.inthinc.hos.model.HOSStatus;

public class DriverDOTLog {
    
    private String editUserName;
    private String timeStr;
    private String timeAddedStr;
    private String location;
    private String vehicleName;
    private String driverName;
    private String trailer;
    private String service;
    private HOSStatus status;
    private HOSOrigin origin;
    private Boolean deleted;
    private Integer changedCnt;

    public Boolean getDeleted() {
        return deleted;
    }
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
    public Integer getChangedCnt() {
        return changedCnt;
    }
    public void setChangedCnt(Integer changedCnt) {
        this.changedCnt = changedCnt;
    }
    public HOSOrigin getOrigin() {
        return origin;
    }
    public void setOrigin(HOSOrigin origin) {
        this.origin = origin;
    }
    public String getEditUserName() {
        return editUserName;
    }
    public void setEditUserName(String editUserName) {
        this.editUserName = editUserName;
    }
    public String getTimeStr() {
        return timeStr;
    }
    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }
    public String getTimeAddedStr() {
        return timeAddedStr;
    }
    public void setTimeAddedStr(String timeAddedStr) {
        this.timeAddedStr = timeAddedStr;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getVehicleName() {
        return vehicleName;
    }
    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }
    public String getDriverName() {
        return driverName;
    }
    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
    public String getTrailer() {
        return trailer;
    }
    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }
    public String getService() {
        return service;
    }
    public void setService(String service) {
        this.service = service;
    }
    public HOSStatus getStatus() {
        return status;
    }
    public void setStatus(HOSStatus status) {
        this.status = status;
    }
}
