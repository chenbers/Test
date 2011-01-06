package com.inthinc.pro.model;

public class StateMileage extends BaseEntity
{
    private static final long serialVersionUID = -5095660906917230045L;

    private Integer groupID;
    private String vehicleName;
    private String stateName;
    private String stateAbbrev;
    private Boolean onRoadFlag;
    private String month;
    private Long miles;
    private Float truckGallons;
    private Float trailerGallons;
    
    
    
    public Integer getGroupID() {
        return groupID;
    }
    public void setGroupID(Integer groupID) {
        this.groupID = groupID;
    }
    public String getVehicleName() {
        return vehicleName;
    }
    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }
    public String getStateName() {
        return stateName;
    }
    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getStateAbrev() {
        return stateAbbrev;
    }
    public void setStateAbbrev(String stateAbbrev) {
        this.stateAbbrev = stateAbbrev;
    }
    
    public Boolean isOnRoadFlag() {
        return onRoadFlag;
    }
    public void setOnRoadFlag(Boolean onRoadFlag) {
        this.onRoadFlag = onRoadFlag;
    }
    public String getMonth() {
        return month;
    }
    public void setMonth(String month) {
        this.month = month;
    }
    public Long getMiles() {
        return miles;
    }
    public void setMiles(Long miles) {
        this.miles = miles;
    }
    public Float getTruckGallons() {
        return truckGallons;
    }
    public void setTruckGallons(Float truckGallons) {
        this.truckGallons = truckGallons;
    }
    public Float getTrailerGallons() {
        return trailerGallons;
    }
    public void setTrailerGallons(Float trailerGallons) {
        this.trailerGallons = trailerGallons;
    }
}
