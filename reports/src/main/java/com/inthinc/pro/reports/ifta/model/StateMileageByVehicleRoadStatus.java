package com.inthinc.pro.reports.ifta.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Bean for MileageByVehicle report.
 */
@XmlRootElement
public class StateMileageByVehicleRoadStatus {

    private String vehicle;
    private String groupName;
    private String state;
    private String roadStatus;
    private Double total;
    
    /**
     * Default constructor.
     */
    public StateMileageByVehicleRoadStatus() {
    }

    /**
     * The vehicle getter.
     * @return the vehicle
     */
    public String getVehicle() {
        return this.vehicle;
    }

    /**
     * The vehicle setter.
     * @param vehicle the vehicle to set
     */
    public void setVehicle(String vehicleId) {
        this.vehicle = vehicleId;
    }

    /**
     * The groupName getter.
     * @return the groupName
     */
    public String getGroupName() {
        return this.groupName;
    }

    /**
     * The groupName setter.
     * @param groupName the groupName to set
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRoadStatus() {
        return roadStatus;
    }

    public void setRoadStatus(String roadStatus) {
        this.roadStatus = roadStatus;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

}
