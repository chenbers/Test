package com.inthinc.pro.model.hos;

import com.inthinc.pro.model.BaseEntity;

public class HOSVehicleMileage  extends BaseEntity{
    private Integer groupID;
    private Long distance;
    private String vehicleName;
    public HOSVehicleMileage(Integer groupID, String vehicleName, Long distance) {
        this.groupID = groupID;
        this.vehicleName = vehicleName;
        this.distance = distance;
    }
    public HOSVehicleMileage() {
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
    public String getVehicleName() {
        return vehicleName;
    }
    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

}
