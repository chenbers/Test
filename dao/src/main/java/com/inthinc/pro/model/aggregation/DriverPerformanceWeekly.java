package com.inthinc.pro.model.aggregation;

import java.util.Date;



public class DriverPerformanceWeekly implements Comparable<DriverPerformanceWeekly> {
    
    private String groupName;
    private String teamName;
    private String driverName;
    private String driverPosition;
    private Integer loginCount;
    private Date weekEndDate;
    private Integer totalMiles;
    private Integer overallScore;
    private Integer speedingScore;
    private Integer styleScore;
    private Integer seatbeltScore;
    private Integer idleViolationsCount;
    private Integer idleViolationsMinutes;
    
    public DriverPerformanceWeekly()
    {
        
    }
    public DriverPerformanceWeekly(String groupName, String teamName, String driverName, String driverPosition, Integer loginCount, Date weekEndDate, Integer totalMiles,
            Integer overallScore, Integer speedingScore, Integer styleScore, Integer seatbeltScore, Integer idleViolationsCount, Integer idleViolationsMinutes) {
        super();
        this.groupName = groupName;
        this.teamName = teamName;
        this.driverName = driverName;
        this.driverPosition = driverPosition;
        this.loginCount = loginCount;
        this.weekEndDate = weekEndDate;
        this.totalMiles = totalMiles;
        this.overallScore = overallScore;
        this.speedingScore = speedingScore;
        this.styleScore = styleScore;
        this.seatbeltScore = seatbeltScore;
        this.idleViolationsCount = idleViolationsCount;
        this.idleViolationsMinutes = idleViolationsMinutes;
    }
    
    public String getGroupName() {
        return groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    public String getTeamName() {
        return teamName;
    }
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
    public String getDriverName() {
        return driverName;
    }
    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
    public String getDriverPosition() {
        return driverPosition;
    }
    public void setDriverPosition(String driverPosition) {
        this.driverPosition = driverPosition;
    }
    public Integer getLoginCount() {
        return loginCount;
    }
    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }
    public Integer getTotalMiles() {
        return totalMiles;
    }
    public void setTotalMiles(Integer totalMiles) {
        this.totalMiles = totalMiles;
    }
    public Integer getOverallScore() {
        return overallScore;
    }
    public void setOverallScore(Integer overallScore) {
        this.overallScore = overallScore;
    }
    public Integer getSpeedingScore() {
        return speedingScore;
    }
    public void setSpeedingScore(Integer speedingScore) {
        this.speedingScore = speedingScore;
    }
    public Integer getStyleScore() {
        return styleScore;
    }
    public void setStyleScore(Integer styleScore) {
        this.styleScore = styleScore;
    }
    public Integer getSeatbeltScore() {
        return seatbeltScore;
    }
    public void setSeatbeltScore(Integer seatbeltScore) {
        this.seatbeltScore = seatbeltScore;
    }
    public Integer getIdleViolationsCount() {
        return idleViolationsCount;
    }
    public void setIdleViolationsCount(Integer idleViolationsCount) {
        this.idleViolationsCount = idleViolationsCount;
    }
    public Integer getIdleViolationsMinutes() {
        return idleViolationsMinutes;
    }
    public void setIdleViolationsMinutes(Integer idleViolationsMinutes) {
        this.idleViolationsMinutes = idleViolationsMinutes;
    }
    public Date getWeekEndDate() {
        return weekEndDate;
    }
    public void setWeekEndDate(Date weekEndDate) {
        this.weekEndDate = weekEndDate;
    }
    @Override
    public int compareTo(DriverPerformanceWeekly o) {
        // sort order: division, team, driver, date ascending
        int cmp = groupName.compareToIgnoreCase(o.getGroupName());
        if (cmp == 0)
            cmp = teamName.compareToIgnoreCase(o.getTeamName());
        if (cmp == 0)
            cmp = driverName.compareToIgnoreCase(o.getDriverName());
        if (cmp == 0)
            cmp = weekEndDate.compareTo(o.getWeekEndDate());
        return cmp;
    }
}
