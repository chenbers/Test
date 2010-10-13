package com.inthinc.pro.reports.ifta.model;

/**
 * Bean for MileageByVehicle report.
 */
public class MileageByVehicle {

    private String vehicle;
    private Double distance;
    private String groupName;
    /**
     * Default constructor.
     */
    public MileageByVehicle() {
    }

    /**
     * The vehicle getter.
     * @return the vehicle
     */
    public String getVehicle() {
        return this.vehicle;
    }
    /**
     * The distance getter.
     * @return the distance
     */
    public Double getDistance() {
        return this.distance;
    }
    /**
     * The vehicle setter.
     * @param vehicle the vehicle to set
     */
    public void setVehicle(String vehicleId) {
        this.vehicle = vehicleId;
    }
    /**
     * The distance setter.
     * @param distance the distance to set
     */
    public void setDistance(Double distance) {
        this.distance = distance;
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

}
