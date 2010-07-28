package com.inthinc.pro.reports.hos.model;

public class HosGroupMileage {
    Integer groupID;
    Long distance;
    
    
    public HosGroupMileage(Integer groupID, Long distance) {
        super();
        this.groupID = groupID;
        this.distance = distance;
    }
    public Integer getGroupID() {
        return groupID;
    }
    public void setGroupID(Integer groupID) {
        this.groupID = groupID;
    }
    public Long getDistance() {
        return distance;
    }
    public void setDistance(Long distance) {
        this.distance = distance;
    }
    
    
}
