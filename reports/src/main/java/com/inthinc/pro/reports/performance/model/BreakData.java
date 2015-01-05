package com.inthinc.pro.reports.performance.model;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class BreakData implements Comparable<BreakData>
{
    private Integer groupID;
    private String groupName;
    private String groupAddress;
    private Integer driverId;
    private String driverName;
    private String employeeID;
    private Integer offDutyHours;
    private Integer onDutyHours;
    private Integer breaksTaken;
    private Integer breakTime;
    private DateTimeZone driverTimeZone;

    public BreakData() {
    }

    public BreakData(Integer groupID, String groupName, String groupAddress, Integer driverId, String driverName, String employeeID,
                     DateTimeZone driverTimeZone) {
        super();
        this.groupID = groupID;
        this.groupName = groupName;
        this.groupAddress = groupAddress;
        this.driverId = driverId;
        this.driverName = driverName;
        this.employeeID = employeeID;
        this.driverTimeZone = driverTimeZone;
    }

    public void dump()
    {
        System.out.println("new BreakData(" +
                "\"" + groupName + "\"," +
                "\"" + groupAddress + "\"," +
                driverId + "," + 
                "\"" + driverName + "\"," +
                "\"" + employeeID + "\"),"
        );
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
    public int compareTo(BreakData o) {
        int cmp = groupName.compareTo(o.getGroupName());
        if (cmp == 0) {
            cmp = driverName.compareTo(o.getDriverName());
            if (cmp == 0) {
            	if((this.getEmployeeID()!=null)&&(o.getEmployeeID()!=null)) {
            	    cmp=this.getEmployeeID().compareTo(o.getEmployeeID());
            	}
            }
        }
        
        return cmp;
    }

    public void setGroupID(Integer groupID) {
        this.groupID = groupID;
    }
    public Integer getGroupID() {
        return groupID;
    }
    public DateTimeZone getDriverTimeZone() {
        return driverTimeZone;
    }
    public void setDriverTimeZone(DateTimeZone driverTimeZone) {
        this.driverTimeZone = driverTimeZone;
    }

    public Integer getOffDutyHours() {
        return offDutyHours;
    }

    public void setOffDutyHours(Integer offDutyHours) {
        this.offDutyHours = offDutyHours;
    }

    public Integer getOnDutyHours() {
        return onDutyHours;
    }

    public void setOnDutyHours(Integer onDutyHours) {
        this.onDutyHours = onDutyHours;
    }

    public Integer getBreaksTaken() {
        return breaksTaken;
    }

    public void setBreaksTaken(Integer breaksTaken) {
        this.breaksTaken = breaksTaken;
    }

    public Integer getBreakTime() {
        return breakTime;
    }

    public void setBreakTime(Integer breakTime) {
        this.breakTime = breakTime;
    }
}
