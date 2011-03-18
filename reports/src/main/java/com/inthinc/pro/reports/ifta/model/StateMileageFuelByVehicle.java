package com.inthinc.pro.reports.ifta.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Data Bean for the StateMileageFuelByVehicle report.
 */
@XmlRootElement(name="fuelConsumption")
public class StateMileageFuelByVehicle {

    private String groupName;
    private String vehicleName;
    private String month;
    private String state;
    private Double totalMiles;
    private Double totalTruckGas;
    private Double totalTrailerGas;
    private Double mileage;

    /**
     * @return the groupName
     */
    public String getGroupName() {
        return groupName;
    }

    /*
     * @param groupName the groupName to set.
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * @return the vehicle name
     */
    public String getVehicleName() {
        return vehicleName;
    }

    /**
     * @param vehicleName
     *            the vehicle name to set
     */
    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    /**
     * @return the month
     */
    public String getMonth() {
        return month;
    }

    /**
     * @param month
     *            the month to set
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state
     *            the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the totalMiles
     */
    public Double getTotalMiles() {
        return totalMiles;
    }

    /**
     * @param totalMiles
     *            the totalMiles to set
     */
    public void setTotalMiles(Double totalMiles) {
        this.totalMiles = totalMiles;
    }

    /**
     * @return the totalTruckGas
     */
    public Double getTotalTruckGas() {
        return totalTruckGas;
    }

    /**
     * @param totalTruckGas
     *            the totalTruckGas to set
     */
    public void setTotalTruckGas(Double totalTruckGas) {
        this.totalTruckGas = totalTruckGas;
    }

    /**
     * @return the totalTrailerGas
     */
    public Double getTotalTrailerGas() {
        return totalTrailerGas;
    }

    /**
     * @param totalTrailerGas
     *            the totalTrailerGas to set
     */
    public void setTotalTrailerGas(Double totalTrailerGas) {
        this.totalTrailerGas = totalTrailerGas;
    }

    /**
     * @param mileage
     *            the mileage to set
     */
    public void setMileage(Double mileage) {
        this.mileage = mileage;
    }

    /**
     * @return the mileage
     */
    public Double getMileage() {
        return mileage;
    }

}
