package com.inthinc.pro.reports.performance.model;

import com.inthinc.hos.model.HOSStatus;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.Date;

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
    private Integer twoHoursBreak;
    private Integer breakTime;
    private Date day;
    private DateTime dateTime;
    private String dayStr;
    private HOSStatus status;
    private int totalAdjustedMinutes;

    private DateTimeZone driverTimeZone;


    public BreakData(String groupName, String groupAddress, Integer driverId, String driverName, String employeeID,
                     Date day, HOSStatus status, int totalAdjustedMinutes) {
        this(null, groupName, groupAddress, driverId, driverName, employeeID, day, status, totalAdjustedMinutes, new DateTime(day.getTime()), DateTimeZone.UTC);
    }
    public BreakData(String groupName, String groupAddress, Integer driverId, String driverName, String employeeID,
                     Date day, HOSStatus status, int totalAdjustedMinutes, DateTimeZone driverTimeZone) {
        this(null, groupName, groupAddress, driverId, driverName, employeeID, day, status, totalAdjustedMinutes, new DateTime(day.getTime()), driverTimeZone);
    }
    public BreakData(Integer groupID, String groupName, String groupAddress, Integer driverId, String driverName, String employeeID,
                     Date day, HOSStatus status, int totalAdjustedMinutes, DateTime dateTime, DateTimeZone driverTimeZone) {
        super();
        this.groupID = groupID;
        this.groupName = groupName;
        this.groupAddress = groupAddress;
        this.driverId = driverId;
        this.driverName = driverName;
        this.employeeID = employeeID;
        this.day = day;
        this.status = status;
        this.totalAdjustedMinutes = totalAdjustedMinutes;
        this.dateTime = dateTime;
        this.driverTimeZone = driverTimeZone;
    }

    public void addTotalAdjustedMinutes(int minutesToAdd) {
        totalAdjustedMinutes += minutesToAdd;
    }

    public void dump()
    {
        System.out.println("new BreakData(" +
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

    public DateTime getDateTime() {
        return dateTime;
    }
    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }
    public String getDayStr() {
        return dayStr;
    }
    public void setDayStr(String dayStr) {
        this.dayStr = dayStr;
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

    public Integer getTwoHoursBreak() {
        return twoHoursBreak;
    }

    public void setTwoHoursBreak(Integer twoHoursBreak) {
        this.twoHoursBreak = twoHoursBreak;
    }

    public Integer getBreakTime() {
        return breakTime;
    }

    public void setBreakTime(Integer breakTime) {
        this.breakTime = breakTime;
    }
}
