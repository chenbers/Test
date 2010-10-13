package com.inthinc.pro.reports.performance.model;

import java.util.Date;

public class TenHoursViolation {
    private String groupName;
    private Date date;
    private String driverName;
    private String vehicleID;
    private String employeeID;
    private Double hoursThisDay;

    public TenHoursViolation(){}
    
    public TenHoursViolation(String groupName, Date date, String driverName, String vehicleId, String employeeId, Double hours) {
        this.groupName = groupName;
        this.date = date;
        this.driverName = driverName;
        this.vehicleID = vehicleId;
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

    public String getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(String vehicleID) {
        this.vehicleID = vehicleID;
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
                "\""+ vehicleID + "\"," +
                "\""+ employeeID + "\"," +
                hoursThisDay + ")," );
    }

}
