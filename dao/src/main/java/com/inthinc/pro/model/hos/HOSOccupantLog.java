package com.inthinc.pro.model.hos;

import java.util.Date;

import com.inthinc.hos.model.HOSStatus;
import com.inthinc.pro.model.BaseEntity;

public class HOSOccupantLog extends BaseEntity {
    
    private Integer     driverID;
    private String      driverName;
    private Integer     vehicleID;
    private Date        time;
    private Date        endTime;
    private HOSStatus   status;
    
    public Integer getDriverID() {
        return driverID;
    }
    public void setDriverID(Integer driverID) {
        this.driverID = driverID;
    }
    public String getDriverName() {
        return driverName;
    }
    public void setDriverName(String driverName) {
        this.driverName = driverName;
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
    public Date getEndTime() {
        return endTime;
    }
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    public HOSStatus getStatus() {
        return status;
    }
    public void setStatus(HOSStatus status) {
        this.status = status;
    }
        
}
