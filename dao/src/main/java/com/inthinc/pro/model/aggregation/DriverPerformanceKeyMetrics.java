package com.inthinc.pro.model.aggregation;

import com.inthinc.pro.model.TimeFrame;



public class DriverPerformanceKeyMetrics implements Comparable<DriverPerformanceKeyMetrics> {
    
    private String groupName;
    private String teamName;
    private String driverName;
    private String driverPosition;
    private Integer loginCount;
    private TimeFrame timeFrame;
    private Integer totalMiles;
    private Integer overallScore;
    private Integer speedingScore;
    private Integer styleScore;
    private Integer seatbeltScore;
    private Integer loIdleViolationsCount;
    private Integer loIdleViolationsMinutes;
    private Integer hiIdleViolationsCount;
    private Integer hiIdleViolationsMinutes;
    
    public DriverPerformanceKeyMetrics()
    {
        
    }
    public DriverPerformanceKeyMetrics(String groupName, String teamName, String driverName, String driverPosition, Integer loginCount, TimeFrame timeFrame, Integer totalMiles,
            Integer overallScore, Integer speedingScore, Integer styleScore, Integer seatbeltScore, Integer loIdleViolationsCount, Integer loIdleViolationsMinutes, Integer hiIdleViolationsCount, Integer hiIdleViolationsMinutes) {
        super();
        this.groupName = groupName;
        this.teamName = teamName;
        this.driverName = driverName;
        this.driverPosition = driverPosition;
        this.loginCount = loginCount;
        this.timeFrame = timeFrame;
        this.totalMiles = totalMiles;
        this.overallScore = overallScore;
        this.speedingScore = speedingScore;
        this.styleScore = styleScore;
        this.seatbeltScore = seatbeltScore;
        this.loIdleViolationsCount = loIdleViolationsCount;
        this.loIdleViolationsMinutes = loIdleViolationsMinutes;
        this.hiIdleViolationsCount = hiIdleViolationsCount;
        this.hiIdleViolationsMinutes = hiIdleViolationsMinutes;
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
    public Integer getLoIdleViolationsCount() {
        return loIdleViolationsCount;
    }
    public void setLoIdleViolationsCount(Integer loIdleViolationsCount) {
        this.loIdleViolationsCount = loIdleViolationsCount;
    }
    public Integer getLoIdleViolationsMinutes() {
        return loIdleViolationsMinutes;
    }
    public void setLoIdleViolationsMinutes(Integer loIdleViolationsMinutes) {
        this.loIdleViolationsMinutes = loIdleViolationsMinutes;
    }
    public Integer getHiIdleViolationsCount() {
        return hiIdleViolationsCount;
    }
    public void setHiIdleViolationsCount(Integer hiIdleViolationsCount) {
        this.hiIdleViolationsCount = hiIdleViolationsCount;
    }
    public Integer getHiIdleViolationsMinutes() {
        return hiIdleViolationsMinutes;
    }
    public void setHiIdleViolationsMinutes(Integer hiIdleViolationsMinutes) {
        this.hiIdleViolationsMinutes = hiIdleViolationsMinutes;
    }
    public TimeFrame getTimeFrame() {
        return timeFrame;
    }
    public void setTimeFrame(TimeFrame timeFrame) {
        this.timeFrame = timeFrame;
    }
    @Override
    public int compareTo(DriverPerformanceKeyMetrics o) {
        // sort order: division, team, driver
        int cmp = groupName.compareToIgnoreCase(o.getGroupName());
        if (cmp == 0)
            cmp = teamName.compareToIgnoreCase(o.getTeamName());
        if (cmp == 0)
            cmp = driverName.compareToIgnoreCase(o.getDriverName());
        return cmp;
    }
}
