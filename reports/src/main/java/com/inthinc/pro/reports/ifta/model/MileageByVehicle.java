package com.inthinc.pro.reports.ifta.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Bean for MileageByVehicle report.
 */
@XmlRootElement
public class MileageByVehicle {

    private String vehicleName;
    private String state;
    private Double total;
    private String month;
    private String groupName;
    /**
     * Default constructor.
     */
    public MileageByVehicle() {
    }

    /**
     * The vehicleName getter.
     * @return the vehicle
     */
    public String getVehicleName() {
        return this.vehicleName;
    }
    /**
     * The distance getter.
     * @return the distance
     */
    public Double getTotal() {
        return this.total;
    }
    /**
     * The state getter.
     * @return the state
     */
    public String getState() {
        return state;
    }
    /**
     * The groupName getter.
     * @return the groupName
     */
    public String getGroupName() {
        return this.groupName;
    }

    /**
     * The month getter.
     * @return the month
     */
    public String getMonth() {
        return month;
    }

    /**
     * The month setter.
     * @param month the month to set
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * The state setter.
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }
    /**
     * The vehicle name setter.
     * @param vehicle the vehicle to set
     */
    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }
    /**
     * The distance setter.
     * @param total the distance to set
     */
    public void setTotal(Double total) {
        this.total = total;
    }

    /**
     * The groupName setter.
     * @param groupName the groupName to set
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

}
