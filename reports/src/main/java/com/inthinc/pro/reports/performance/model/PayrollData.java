package com.inthinc.pro.reports.performance.model;

import java.util.Date;

import com.inthinc.hos.model.HOSStatus;

public class PayrollData implements Comparable<PayrollData>
{
    private String groupName;
    private String groupAddress;
    private Integer driverId;
    private String driverName;
    private String employeeID;
    
    private Date day;
    private HOSStatus status;
    private int totalAdjustedMinutes;


    public PayrollData(String groupName, String groupAddress, Integer driverId, String driverName, String employeeID,
            Date day, HOSStatus status, int totalAdjustedMinutes)
    {
        super();
        this.groupName = groupName;
        this.groupAddress = groupAddress;
        this.driverId = driverId;
        this.driverName = driverName;
        this.employeeID = employeeID;
        this.day = day;
        this.status = status;
        this.totalAdjustedMinutes = totalAdjustedMinutes;
    }

    public void dump()
    {
        System.out.println("new PayrollData(" +
                "\"" + groupName + "\"," +
                "\"" + groupAddress + "\"," +
                driverId + "," + 
                "\"" + driverName + "\"," +
                "\"" + employeeID + "\"," +
                "new Date(" + day.getTime() + "l)," +
                "HOSStatus." + status.getName() + "," +
                totalAdjustedMinutes + ")," 
        );
    }
    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public HOSStatus getStatus() {
        return status;
    }

    public void setStatus(HOSStatus status) {
        this.status = status;
    }

    public int getTotalAdjustedMinutes() {
        return totalAdjustedMinutes;
    }

    public void setTotalAdjustedMinutes(int totalAdjustedMinutes) {
        this.totalAdjustedMinutes = totalAdjustedMinutes;
    }

    public String getGroupName()
    {
        return groupName;
    }

    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
    }

    public String getGroupAddress()
    {
        return groupAddress;
    }

    public void setGroupAddress(String groupAddress)
    {
        this.groupAddress = groupAddress;
    }

    public Integer getDriverId()
    {
        return driverId;
    }

    public void setDriverId(Integer driverId)
    {
        this.driverId = driverId;
    }

    public String getDriverName()
    {
        return driverName;
    }

    public void setDriverName(String driverName)
    {
        this.driverName = driverName;
    }

    public String getEmployeeID()
    {
        return employeeID;
    }

    public void setEmployeeID(String employeeID)
    {
        this.employeeID = employeeID;
    }

    @Override
    public int compareTo(PayrollData o) {
        int cmp = groupName.compareTo(o.getGroupName());
        if (cmp == 0) {
            cmp = driverName.compareTo(o.getDriverName());
            if (cmp == 0) 
                cmp = day.compareTo(o.getDay());
        }
        
        return cmp;
    }

}
