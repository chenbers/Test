package com.inthinc.pro.model.performance;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.inthinc.pro.model.BaseEntity;

@XmlRootElement
public class VehicleUsageRecord extends BaseEntity {

    private static final long serialVersionUID = 6390658799480605750L;
    
    /** fields */
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
    
    /** Default constructor. */
    public VehicleUsageRecord(){
        
    }
    
    /** Constructor to initiate all fields. */
    public VehicleUsageRecord(String driver, String vehicle, Date date, String zoneName, Date timeEntered, Date timeExited, Integer mileage, Integer totalMiles, Integer jobUse,
            Integer companyUse, Integer personalUse) {
        super();
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

    /**
     * The driver getter.
     * @return the driver
     */
    public String getDriver() {
        return this.driver;
    }

    /**
     * The vehicle getter.
     * @return the vehicle
     */
    public String getVehicle() {
        return this.vehicle;
    }

    /**
     * The date getter.
     * @return the date
     */
    public Date getDate() {
        return this.date;
    }

    /**
     * The zoneName getter.
     * @return the zoneName
     */
    public String getZoneName() {
        return this.zoneName;
    }

    /**
     * The timeEntered getter.
     * @return the timeEntered
     */
    public Date getTimeEntered() {
        return this.timeEntered;
    }

    /**
     * The timeExited getter.
     * @return the timeExited
     */
    public Date getTimeExited() {
        return this.timeExited;
    }

    /**
     * The mileage getter.
     * @return the mileage
     */
    public Integer getMileage() {
        return this.mileage;
    }

    /**
     * The totalMiles getter.
     * @return the totalMiles
     */
    public Integer getTotalMiles() {
        return this.totalMiles;
    }

    /**
     * The jobUse getter.
     * @return the jobUse
     */
    public Integer getJobUse() {
        return this.jobUse;
    }

    /**
     * The companyUse getter.
     * @return the companyUse
     */
    public Integer getCompanyUse() {
        return this.companyUse;
    }

    /**
     * The personalUse getter.
     * @return the personalUse
     */
    public Integer getPersonalUse() {
        return this.personalUse;
    }

    /**
     * The driver setter.
     * @param driver the driver to set
     */
    public void setDriver(String driver) {
        this.driver = driver;
    }

    /**
     * The vehicle setter.
     * @param vehicle the vehicle to set
     */
    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    /**
     * The date setter.
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * The zoneName setter.
     * @param zoneName the zoneName to set
     */
    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    /**
     * The timeEntered setter.
     * @param timeEntered the timeEntered to set
     */
    public void setTimeEntered(Date timeEntered) {
        this.timeEntered = timeEntered;
    }

    /**
     * The timeExited setter.
     * @param timeExited the timeExited to set
     */
    public void setTimeExited(Date timeExited) {
        this.timeExited = timeExited;
    }

    /**
     * The mileage setter.
     * @param mileage the mileage to set
     */
    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    /**
     * The totalMiles setter.
     * @param totalMiles the totalMiles to set
     */
    public void setTotalMiles(Integer totalMiles) {
        this.totalMiles = totalMiles;
    }

    /**
     * The jobUse setter.
     * @param jobUse the jobUse to set
     */
    public void setJobUse(Integer jobUse) {
        this.jobUse = jobUse;
    }

    /**
     * The companyUse setter.
     * @param companyUse the companyUse to set
     */
    public void setCompanyUse(Integer companyUse) {
        this.companyUse = companyUse;
    }

    /**
     * The personalUse setter.
     * @param personalUse the personalUse to set
     */
    public void setPersonalUse(Integer personalUse) {
        this.personalUse = personalUse;
    }
    
}
