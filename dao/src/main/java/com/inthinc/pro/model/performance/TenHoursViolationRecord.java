package com.inthinc.pro.model.performance;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import org.joda.time.DateTime;

import com.inthinc.pro.model.BaseEntity;

@XmlRootElement
public class TenHoursViolationRecord extends BaseEntity {

    private static final long serialVersionUID = 6391654979859374036L;

    private DateTime dateTime;
    private Integer driverID;
    private String vehicleName;
    private Integer vehicleID;
    private Number hoursThisDay;
    

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getDriverID() {
        return driverID;
    }

    public void setDriverID(Integer driverID) {
        this.driverID = driverID;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public Integer getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(Integer vehicleID) {
        this.vehicleID = vehicleID;
    }

    public Number getHoursThisDay() {
        return hoursThisDay;
    }

    public void setHoursThisDay(Number hoursThisDay) {
        this.hoursThisDay = hoursThisDay;
    }
}
