package com.inthinc.pro.reports.performance.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import com.inthinc.hos.model.DayData;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.GroupHierarchy;

public class BreakDataSummary implements Comparable<BreakDataSummary> {
    
    private Driver driver;
    private GroupHierarchy groupHierarchy;
    private Interval interval;
    
    private Integer onDutyMinutes;
    private Integer offDutyMinutes;
    private Integer breakCount;
    private Integer breakMinutes;
    private Integer sleeperBerthMinutes;
    
    private Integer roundingPlaces = 2;

    public BreakDataSummary(Interval interval, GroupHierarchy groupHierarchy, Driver driver, List<DayData> dayData) {
        this.interval = interval;
        this.groupHierarchy = groupHierarchy;
        this.driver = driver;
        
        this.onDutyMinutes = 0;
        this.offDutyMinutes = 0;
        this.breakCount = 0;
        this.breakMinutes = 0;
        this.sleeperBerthMinutes=0;

        for (DayData day : dayData) {
            addOnDutyMinutes(day.getOnDutyMinutes());
            addOffDutyMinutes(day.getOffDutyMinutes());
            addBreakCount(day.getNumberOfBreaks());
            addBreakMinutes(day.getBreakMinutes());
            addSleeperBerthMinutes(day.getTwoHourSleeperBerthBreakMinutes());
        }
    }
    
    @Override
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Class: ");
        stringBuffer.append(getClass().getName() + "@" + Integer.toHexString(hashCode()));
        stringBuffer.append(", Driver Name: ");
        stringBuffer.append(this.getDriverName());
        stringBuffer.append(", Employee ID: ");
        stringBuffer.append(this.getEmployeeID());
        stringBuffer.append(", Group: ");
        stringBuffer.append(this.getGroupName());
        stringBuffer.append(", Group ID: ");
        stringBuffer.append(this.getGroupID());
        stringBuffer.append(", On-Duty Hours: ");
        stringBuffer.append(this.getOnDutyDecimalHours());
        stringBuffer.append(", Off-Duty Hours: ");
        stringBuffer.append(this.getOffDutyDecimalHours());
        stringBuffer.append(", Sleeper berth > 2 Hours: ");
        stringBuffer.append(this.getSleeperBerthDecimalHours());
        stringBuffer.append(", Number of Breaks: ");
        stringBuffer.append(this.getBreakCount());
        stringBuffer.append(", Break Time: ");
        stringBuffer.append(this.getBreakDecimalHours());
        
        return stringBuffer.toString();
    }

    @Override
    public int compareTo(BreakDataSummary breakData) {
        int groupNameComparison = this.getGroupName().compareToIgnoreCase(breakData.getGroupName());
        if (groupNameComparison != 0) 
            return groupNameComparison;
        return this.getDriverName().compareToIgnoreCase(breakData.getDriverName());
    }
    
    private void addOnDutyMinutes(Integer newMinutes) {
        this.onDutyMinutes += newMinutes;
    }
    
    private void addOffDutyMinutes(Integer newMinutes) {
        this.offDutyMinutes += newMinutes;
    }
    
    private void addBreakCount(Integer newBreaks) {
        this.breakCount += newBreaks;
    }
    
    private void addBreakMinutes(Integer newMinutes) {
        this.breakMinutes += newMinutes;
    }

    private void addSleeperBerthMinutes(Integer newMinutes) {
        if (newMinutes != null)
            this.sleeperBerthMinutes += newMinutes;
    }
    
    public DateTime getStartDate() {
        return interval.getStart();
    }
    
    public DateTime getEndDate() {
        return interval.getEnd();
    }
    
    public String getDriverName() {
        String name = driver.getPerson().getFullNameLastFirst();
        return name;
    }
    
    public String getEmployeeID() {
        String id = driver.getPerson().getEmpid();
        return id;
    }
    
    public String getGroupName() {
        String name = groupHierarchy.getFullGroupName(driver.getGroupID());
        return name;
    }
    
    public Integer getGroupID() {
        Integer id = driver.getGroupID();
        return id;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public GroupHierarchy getGroupHierarchy() {
        return groupHierarchy;
    }

    public void setGroupHierarchy(GroupHierarchy groupHierarchy) {
        this.groupHierarchy = groupHierarchy;
    }

    public Interval getInterval() {
        return interval;
    }

    public void setInterval(Interval interval) {
        this.interval = interval;
    }

    public Integer getBreakCount() {
        return breakCount;
    }

    public void setBreakCount(Integer breakCount) {
        this.breakCount = breakCount;
    }

    public Integer getOnDutyMinutes() {
        return onDutyMinutes;
    }
    
    public Double getOnDutyDecimalHours() {
        Double hours = (double) getOnDutyMinutes() / 60;
        hours = round(hours, 2);
        return hours;
    }

    public Double getSleeperBerthDecimalHours() {
        Double hours = (double) getSleeperBerthMinutes() / 60;
        hours = round(hours, 2);
        return hours;
    }

    public void setOnDutyMinutes(Integer onDutyMinutes) {
        this.onDutyMinutes = onDutyMinutes;
    }

    public Integer getOffDutyMinutes() {
        return offDutyMinutes;
    }
    
    public Double getOffDutyDecimalHours() {
        Double hours = (double) getOffDutyMinutes() / 60;
        hours = round(hours, 2);
        return hours;
    }

    public void setOffDutyMinutes(Integer offDutyMinutes) {
        this.offDutyMinutes = offDutyMinutes;
    }

    public Integer getBreakMinutes() {
        return breakMinutes;
    }
    
    public Double getBreakDecimalHours() {
        return this.convertToDecimalHours(this.getBreakMinutes());
    }

    public void setBreakMinutes(Integer breakMinutes) {
        this.breakMinutes = breakMinutes;
    }

    public Integer getSleeperBerthMinutes() {
        return sleeperBerthMinutes;
    }

    public void setSleeperBerthMinutes(Integer sleeperBerthMinutes) {
        this.sleeperBerthMinutes = sleeperBerthMinutes;
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    
    private double convertToDecimalHours(Integer minutes) {
        Double hours = (double) minutes / 60;
        hours = round(hours, this.roundingPlaces);
        return hours;
    }

    public Integer getRoundingPlaces() {
        return this.roundingPlaces;
    }

    public void setRoundingPlaces(Integer roundingPlaces) {
        this.roundingPlaces = roundingPlaces;
    }
    
}
