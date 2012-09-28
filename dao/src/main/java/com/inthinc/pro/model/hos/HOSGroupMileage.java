package com.inthinc.pro.model.hos;

import com.inthinc.pro.model.BaseEntity;

public class HOSGroupMileage  extends BaseEntity{
    Integer groupID;
    Long distance;
    
    
    public HOSGroupMileage(Integer groupID, Long distance) {
        super();
        this.groupID = groupID;
        this.distance = distance;
    }
    public HOSGroupMileage() {
        // TODO Auto-generated constructor stub
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
