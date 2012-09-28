package com.inthinc.pro.model.hos;

import java.util.Date;

import org.joda.time.Interval;

import com.inthinc.hos.model.HOSStatus;
import com.inthinc.pro.model.BaseEntity;

public class HOSVehicleDriverSchedule extends BaseEntity {
    
    Integer     hosLogID;
    Integer     driverID;
    Integer     vehicleID;
//    private HOSStatus status;
    Interval interval;

    public HOSVehicleDriverSchedule(Integer hosLogID, Integer driverID, Integer vehicleID, Interval interval)
    {
        this.hosLogID = hosLogID;
        this.driverID = driverID;
        this.vehicleID = vehicleID;
        this.interval = interval;
    }

    public Integer getHosLogID() {
        return hosLogID;
    }

    public void setHosLogID(Integer hosLogID) {
        this.hosLogID = hosLogID;
    }

    public Integer getVehicleID() {
        return vehicleID;
    }
    
    public void setVehicleID(Integer vehicleID) {
        this.vehicleID = vehicleID;
    }

    public Integer getDriverID() {
        return driverID;
    }

    public void setDriverID(Integer driverID) {
        this.driverID = driverID;
    }
/*
    public HOSStatus getStatus() {
        return status;
    }

    public void setStatus(HOSStatus status) {
        this.status = status;
    }
*/
    public Interval getInterval() {
        return interval;
    }

    public void setInterval(Interval interval) {
        this.interval = interval;
    }

    public long getStartTime() {
        return interval.getStartMillis();
    }
    
    public long getEndTime() {
        return interval.getEndMillis();
    }
    


}
