package com.inthinc.pro.model.event;

import java.util.Date;

public class LastReportedEvent extends Event{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String deviceSerialNum;
    
    private Date lastAssignedDate;
    
    private Date lastUnassignedDate;
    
    private Integer daysSince;
    
    public String getDeviceSerialNum() {
        return deviceSerialNum;
    }

    public void setDeviceSerialNum(String deviceSerialNum) {
        this.deviceSerialNum = deviceSerialNum;
    }

    public Date getLastAssignedDate() {
        return lastAssignedDate;
    }

    public void setLastAssignedDate(Date lastAssignedDate) {
        this.lastAssignedDate = lastAssignedDate;
    }

    public Date getLastUnassignedDate() {
        return lastUnassignedDate;
    }

    public void setLastUnassignedDate(Date lastUnassignedDate) {
        this.lastUnassignedDate = lastUnassignedDate;
    }

    public Integer getDaysSince() {
        return daysSince;
    }

    public void setDaysSince(Integer daysSince) {
        this.daysSince = daysSince;
    }
    
    
    
}
