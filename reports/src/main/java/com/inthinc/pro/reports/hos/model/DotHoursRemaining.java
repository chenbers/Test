package com.inthinc.pro.reports.hos.model;

import java.util.Date;

import com.inthinc.hos.model.HOSStatus;
import com.inthinc.hos.model.RuleSetType;

public class DotHoursRemaining implements Comparable<DotHoursRemaining> {
    private String groupName;
    private Integer driverId; 
    private String driverName; 
    private RuleSetType dotType;
    private Long minutesRemaining;
    private String day;
    private Date dayDate;
    private HOSStatus status;
    private Long totalAdjustedMinutes;

    public void dump() {
        System.out.println("new DotHoursRemaining(" +
                "\"" + groupName + "\"," +
                "0," +
                "\"" + driverName + "\"," +
                "RuleSetType." + dotType.getName() + "," +
                minutesRemaining + "l," +
                "\"" + day + "\"," +
                "new Date(" + dayDate.getTime() + "l)," +
                "HOSStatus." + status.getName() + "," +
                totalAdjustedMinutes + "l" +
                "),"
                );
    }
    
    public DotHoursRemaining(String groupName, Integer driverId, String driverName, RuleSetType dotType)
    {
        super();
        this.groupName = groupName;
        this.driverId = driverId;
        this.driverName = driverName;
        this.dotType = dotType;
    }
    public DotHoursRemaining(String groupName, Integer driverId, String driverName, RuleSetType dotType,
            Long minutesRemaining, String day, Date dayDate, HOSStatus status, Long totalAdjustedMinutes)
    {
        super();
        this.groupName = groupName;
        this.driverId = driverId;
        this.driverName = driverName;
        this.dotType = dotType;
        this.minutesRemaining = minutesRemaining;
        this.day = day;
        this.dayDate = dayDate;
        this.status = status;
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
    public RuleSetType getDotType()
    {
        return dotType;
    }
    public void setDotType(RuleSetType dotType)
    {
        this.dotType = dotType;
    }
    public Long getMinutesRemaining()
    {
        return minutesRemaining;
    }
    public void setMinutesRemaining(Long minutesRemaining)
    {
        this.minutesRemaining = minutesRemaining;
    }

    public String getDay() {
        return day;
    }
    public void setDay(String day) {
        this.day = day;
    }
    public HOSStatus getStatus() {
        return status;
    }
    public void setStatus(HOSStatus status) {
        this.status = status;
    }
    public Long getTotalAdjustedMinutes() {
        return totalAdjustedMinutes;
    }
    public void setTotalAdjustedMinutes(Long totalAdjustedMinutes) {
        this.totalAdjustedMinutes = totalAdjustedMinutes;
    }
    public Date getDayDate() {
        return dayDate;
    }

    public void setDayDate(Date dayDate) {
        this.dayDate = dayDate;
    }


    @Override
    public int compareTo(DotHoursRemaining o) {
        // first by driver name and next by day and then by status
        int cmp = driverName.compareTo(o.getDriverName());
        if (cmp == 0) {
            cmp = dayDate.compareTo(o.getDayDate()) * -1;
            if (cmp == 0) 
                cmp = status.getCode().compareTo(o.getStatus().getCode());
        }
        
        return cmp;
    }
}
