package com.inthinc.pro.reports.performance.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DriverHours {
    private String groupName;
    private String date;
    private String driverName;
    private Double hours;

    public DriverHours(){
    }
    
    public DriverHours(String groupName, String date, String driverName, Double hours) {
        this.groupName = groupName;
        this.date = date;
        this.driverName = driverName;
        this.hours = hours;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public Double getHours() {
        return hours;
    }

    public void setHours(Double hoursThisDay) {
        this.hours = hoursThisDay;
    }

    public void dump() {
        System.out.println("new DriverHours(" +
                "\""+ groupName + "\"," +
                "\""+ date + "\"," +
                "\""+ driverName + "\"," +
                hours + ")," );
    }

}
