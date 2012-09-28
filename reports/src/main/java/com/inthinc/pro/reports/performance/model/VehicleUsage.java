package com.inthinc.pro.reports.performance.model;

import java.util.Date;

public class VehicleUsage {
    private String  driver;
    private String  vehicle;
    private Date date;
    private String zoneName;
    private Date timeEntered;
    private Date timeExited;
    private Integer mileage;
    private Integer totalMiles;
    private Integer jobUse;
    private Integer companyUse;
    private Integer personalUse;

    public VehicleUsage(){}

    public VehicleUsage(String driver, String vehicle, Date date, String zoneName, Date timeEntered, Date timeExited, Integer mileage, Integer totalMiles, Integer jobUse,
            Integer companyUse, Integer personalUse) {
        this.driver = driver;
        this.vehicle = vehicle;
        this.date = date;
        this.zoneName = zoneName;
        this.timeEntered = timeEntered;
        this.timeExited = timeExited;
        this.mileage = mileage;
        this.totalMiles = totalMiles;
        this.jobUse = jobUse;
        this.companyUse = companyUse;
        this.personalUse = personalUse;
    }



    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public Date getTimeEntered() {
        return timeEntered;
    }

    public void setTimeEntered(Date timeEntered) {
        this.timeEntered = timeEntered;
    }

    public Date getTimeExited() {
        return timeExited;
    }

    public void setTimeExited(Date timeExited) {
        this.timeExited = timeExited;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public Integer getTotalMiles() {
        return totalMiles;
    }

    public void setTotalMiles(Integer totalMiles) {
        this.totalMiles = totalMiles;
    }

    public Integer getJobUse() {
        return jobUse;
    }

    public void setJobUse(Integer jobUse) {
        this.jobUse = jobUse;
    }

    public Integer getCompanyUse() {
        return companyUse;
    }

    public void setCompanyUse(Integer companyUse) {
        this.companyUse = companyUse;
    }

    public Integer getPersonalUse() {
        return personalUse;
    }

    public void setPersonalUse(Integer personalUse) {
        this.personalUse = personalUse;
    }

}
