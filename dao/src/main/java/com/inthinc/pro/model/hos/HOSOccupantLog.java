package com.inthinc.pro.model.hos;

import java.util.Date;

import com.inthinc.hos.model.HOSStatus;
import com.inthinc.pro.model.BaseEntity;

public class HOSOccupantLog extends BaseEntity {
    
    private Integer     driverID;
    private String      driverName;
    private Integer     vehicleID;
    private Date        logTime;
    private Date        endTime;
    private HOSStatus   status;
    private String      trailerID;
    private String      serviceID;
    
    public String getTrailerID() {
        return trailerID;
    }
    public void setTrailerID(String trailerID) {
        this.trailerID = trailerID;
    }
    public String getServiceID() {
        return serviceID;
    }
    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }
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
    public Date getLogTime() {
        return logTime;
    }
    public void setLogTime(Date logTime) {
        this.logTime = logTime;
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
