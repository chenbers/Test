package com.inthinc.pro.reports.hos.model;

import java.util.Date;

import com.inthinc.hos.model.HOSStatus;

public class HosEdit {
    private String groupName;
    private String driverName;
    private String employeeID;
    private HOSStatus status;
    private String logTime;
    private String adjustedTime;
    private String editUserName;
    private String dateAdded;
    private String dateLastUpdated;
    
    private Date logDate;
    public HosEdit() {
        
    }
    
    public HosEdit(String groupName, String driverName, String employeeID, HOSStatus status, String logTime, String adjustedTime, String editUserName, Date logDate,
            String dateAdded, String dateLastUpdated) {
        super();
        this.groupName = groupName;
        this.driverName = driverName;
        this.employeeID = employeeID;
        this.status = status;
        this.logTime = logTime;
        this.adjustedTime = adjustedTime;
        this.editUserName = editUserName;
        this.logDate = logDate;
        this.dateAdded = dateAdded;
        this.dateLastUpdated = dateLastUpdated;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public HOSStatus getStatus() {
        return status;
    }

    public void setStatus(HOSStatus status) {
        this.status = status;
    }

    public String getLogTime() {
        return logTime;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }

    public String getAdjustedTime() {
        return adjustedTime;
    }

    public void setAdjustedTime(String adjustedTime) {
        this.adjustedTime = adjustedTime;
    }

    public String getEditUserName() {
        return editUserName;
    }

    public void setEditUserName(String editUserName) {
        this.editUserName = editUserName;
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getDateLastUpdated() {
        return dateLastUpdated;
    }

    public void setDateLastUpdated(String dateLastUpdated) {
        this.dateLastUpdated = dateLastUpdated;
    }


    public void dump() {
        
        System.out.println("new HosEdit(" +
                "\"" + groupName + "\"," +
                "\"" + driverName + "\"," +
                "\"" + employeeID + "\"," +
                "HOSStatus." + status.getName() + "," +
                "\"" + logTime + "\"," +
                "\"" + adjustedTime + "\"," +
                "\"" + editUserName + "\"," +
                "new Date(" + logDate.getTime() + "l))," +
                "\"" + dateAdded + "\"," +
                "\"" + dateLastUpdated + "\"" 
                );
    }
    
}
