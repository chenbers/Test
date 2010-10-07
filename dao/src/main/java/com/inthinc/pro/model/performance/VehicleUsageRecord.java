package com.inthinc.pro.model.performance;

import java.util.Date;

public class VehicleUsageRecord {
    private String driver;
    private String vehicle;
    private Date day;
    private String zone;
    private Date timeEntered;
    private Date timeExited;
    private Integer mileage;
    private Integer jobUse;
    private Integer companyUse;
    private Integer personalUse;
    private Integer totalMiles;
    private String approvalNotes;
    
    /**
     * @return the driver
     */
    public String getDriver() {
        return this.driver;
    }
    /**
     * @return the vehicle
     */
    public String getVehicle() {
        return this.vehicle;
    }
    /**
     * @return the day
     */
    public Date getDay() {
        return this.day;
    }
    /**
     * @return the zone
     */
    public String getZone() {
        return this.zone;
    }
    /**
     * @return the timeEntered
     */
    public Date getTimeEntered() {
        return this.timeEntered;
    }
    /**
     * @return the timeExited
     */
    public Date getTimeExited() {
        return this.timeExited;
    }
    /**
     * @return the mileage
     */
    public Integer getMileage() {
        return this.mileage;
    }
    /**
     * @return the jobUse
     */
    public Integer getJobUse() {
        return this.jobUse;
    }
    /**
     * @return the companyUse
     */
    public Integer getCompanyUse() {
        return this.companyUse;
    }
    /**
     * @return the personalUse
     */
    public Integer getPersonalUse() {
        return this.personalUse;
    }
    /**
     * @return the totalMiles
     */
    public Integer getTotalMiles() {
        return this.totalMiles;
    }
    /**
     * @return the approvalNotes
     */
    public String getApprovalNotes() {
        return this.approvalNotes;
    }
    /**
     * @param driver the driver to set
     */
    public void setDriver(String driver) {
        this.driver = driver;
    }
    /**
     * @param vehicle the vehicle to set
     */
    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }
    /**
     * @param day the day to set
     */
    public void setDay(Date day) {
        this.day = day;
    }
    /**
     * @param zone the zone to set
     */
    public void setZone(String zone) {
        this.zone = zone;
    }
    /**
     * @param timeEntered the timeEntered to set
     */
    public void setTimeEntered(Date timeEntered) {
        this.timeEntered = timeEntered;
    }
    /**
     * @param timeExited the timeExited to set
     */
    public void setTimeExited(Date timeExited) {
        this.timeExited = timeExited;
    }
    /**
     * @param mileage the mileage to set
     */
    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }
    /**
     * @param jobUse the jobUse to set
     */
    public void setJobUse(Integer jobUse) {
        this.jobUse = jobUse;
    }
    /**
     * @param companyUse the companyUse to set
     */
    public void setCompanyUse(Integer companyUse) {
        this.companyUse = companyUse;
    }
    /**
     * @param personalUse the personalUse to set
     */
    public void setPersonalUse(Integer personalUse) {
        this.personalUse = personalUse;
    }
    /**
     * @param totalMiles the totalMiles to set
     */
    public void setTotalMiles(Integer totalMiles) {
        this.totalMiles = totalMiles;
    }
    /**
     * @param approvalNotes the approvalNotes to set
     */
    public void setApprovalNotes(String approvalNotes) {
        this.approvalNotes = approvalNotes;
    }    
}
