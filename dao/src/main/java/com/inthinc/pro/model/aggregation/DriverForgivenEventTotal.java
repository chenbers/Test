package com.inthinc.pro.model.aggregation;

import com.inthinc.pro.model.event.EventType;

/**
 * 
 * @author mstrong
 * 
 * DriverForgivenEventTotal holds data which summarizes the total number of forgiven
 * events to total events by event type for a single driver.
 *
 */
public class DriverForgivenEventTotal {
    
    private Integer driverID;
    
    private Integer groupID;
    
    private String driverName;
    
    private String groupName;
    
    private EventType eventType;
    
    private Integer eventCountForgiven;
    
    private Integer eventCount;
    
    private Double percentForgiven;

    public Integer getDriverID() {
        return driverID;
    }

    public void setDriverID(Integer driverID) {
        this.driverID = driverID;
    }

    public Integer getGroupID() {
        return groupID;
    }

    public void setGroupID(Integer groupID) {
        this.groupID = groupID;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public Integer getEventCountForgiven() {
        return eventCountForgiven;
    }

    public void setEventCountForgiven(Integer eventCountForgiven) {
        this.eventCountForgiven = eventCountForgiven;
    }

    public Integer getEventCount() {
        return eventCount;
    }

    public void setEventCount(Integer eventCount) {
        this.eventCount = eventCount;
    }

    public Double getPercentForgiven() {
        return percentForgiven;
    }
    
    public void setPercentForgiven(Double percentForgiven) {
        this.percentForgiven = percentForgiven;
    }
}
