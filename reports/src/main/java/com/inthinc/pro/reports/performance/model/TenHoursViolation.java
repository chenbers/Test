package com.inthinc.pro.reports.performance.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TenHoursViolation {
    private String groupName;
    private Date date;
    private String driverName;
    private String vehicleName;
    private String employeeID;
    private Double hoursThisDay;

    public TenHoursViolation(){}
    
    public TenHoursViolation(String groupName, Date date, String driverName, String vehicleName, String employeeId, Double hours) {
        this.groupName = groupName;
        this.date = date;
        this.driverName = driverName;
        this.vehicleName = vehicleName;
        this.employeeID = employeeId;
        this.hoursThisDay = hours;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public Double getHoursThisDay() {
        return hoursThisDay;
    }

    public void setHoursThisDay(Double hoursThisDay) {
        this.hoursThisDay = hoursThisDay;
    }

    public void dump() {
        System.out.println("new TenHoursViolation(" +
                "\""+ groupName + "\"," +
                "new Date("+ date.getTime() + ")," +
                "\""+ driverName + "\"," +
                "\""+ vehicleName + "\"," +
                "\""+ employeeID + "\"," +
                hoursThisDay + ")," );
    }

}
