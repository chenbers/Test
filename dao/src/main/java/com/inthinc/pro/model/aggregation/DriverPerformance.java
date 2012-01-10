package com.inthinc.pro.model.aggregation;

import java.util.List;

public class DriverPerformance implements Comparable<DriverPerformance> {
    
    private String groupName;
    private Integer driverID;
    private String driverName;
    private String employeeID;
    private Integer score;
    private Integer totalMiles;
    private Integer hardAccelCount;
    private Integer hardBrakeCount;
    private Integer hardTurnCount;
    private Integer hardVerticalCount;
    private Integer seatbeltCount;
    private Integer speedCount0to7Over;
    private Integer speedCount8to14Over;
    private Integer speedCount15Over;
    private Boolean ryg;
    
    List<VehiclePerformance> vehiclePerformanceBreakdown;
    
    public DriverPerformance()
    {
        
    }
    public DriverPerformance(String groupName, Integer driverID, String driverName, String employeeID, Integer score, Integer totalMiles, Integer hardAccelCount,
            Integer hardBrakeCount, Integer hardTurnCount, Integer hardVerticalCount, Integer seatbeltCount) {
        super();
        this.groupName = groupName;
        this.driverID = driverID;
        this.driverName = driverName;
        this.employeeID = employeeID;
        this.score = score;
        this.totalMiles = totalMiles;
        this.hardAccelCount = hardAccelCount;
        this.hardBrakeCount = hardBrakeCount;
        this.hardTurnCount = hardTurnCount;
        this.hardVerticalCount = hardVerticalCount;
        this.seatbeltCount = seatbeltCount;
    }
    
    public Integer getSeatbeltCount() {
        return seatbeltCount;
    }

    public void setSeatbeltCount(Integer seatbeltCount) {
        this.seatbeltCount = seatbeltCount;
    }

    public String getGroupName() {
        return groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    public Integer getDriverID() {
        return driverID;
    }
    public void setDriverID(Integer driverID) {
        this.driverID = driverID;
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
    public Integer getScore() {
        return score;
    }
    public void setScore(Integer score) {
        this.score = score;
    }
    public Integer getTotalMiles() {
        return totalMiles;
    }
    public void setTotalMiles(Integer totalMiles) {
        this.totalMiles = totalMiles;
    }
    public Integer getHardAccelCount() {
        return hardAccelCount;
    }
    public void setHardAccelCount(Integer hardAccelCount) {
        this.hardAccelCount = hardAccelCount;
    }
    public Integer getHardBrakeCount() {
        return hardBrakeCount;
    }
    public void setHardBrakeCount(Integer hardBrakeCount) {
        this.hardBrakeCount = hardBrakeCount;
    }
    public Integer getHardTurnCount() {
        return hardTurnCount;
    }
    public void setHardTurnCount(Integer hardTurnCount) {
        this.hardTurnCount = hardTurnCount;
    }
    public Integer getHardVerticalCount() {
        return hardVerticalCount;
    }
    public void setHardVerticalCount(Integer hardVerticalCount) {
        this.hardVerticalCount = hardVerticalCount;
    }
    public Integer getSpeedCount0to7Over() {
        return speedCount0to7Over;
    }
    public void setSpeedCount0to7Over(Integer speedCount0to7Over) {
        this.speedCount0to7Over = speedCount0to7Over;
    }
    public Integer getSpeedCount8to14Over() {
        return speedCount8to14Over;
    }
    public void setSpeedCount8to14Over(Integer speedCount8to14Over) {
        this.speedCount8to14Over = speedCount8to14Over;
    }
    public Integer getSpeedCount15Over() {
        return speedCount15Over;
    }
    public void setSpeedCount15Over(Integer speedCount15Over) {
        this.speedCount15Over = speedCount15Over;
    }
    public Boolean getRyg() {
        if (ryg == null)
            return false;
        return ryg;
    }
    public void setRyg(Boolean ryg) {
        this.ryg = ryg;
    }
/*
 *  
    G - Green 4.1 - 5.0
    B - Blue 3.1 - 4.0
    Y - Yellow 2.1 - 3.0
    O - Orange 1.1 - 3.0
    R - Red 0.0 - 1.0
    
    if RYG
    G - Green 4.1 - 5.0
    Y - Yellow 2.1 - 4.0
    R - Red 0.0 - 2.0
    
 */
    public String getColor() {
        if (score == null || score < 0)
            return " ";
        if (getRyg()) {
            if (score < 21)
                return "R";
            if (score < 41)
                return "Y";
        }
        else {
            if (score < 11)
                return "R";
            if (score < 21)
                return "O";
            if (score < 31)
                return "Y";
            if (score < 41)
                return "B";
        }
        return "G";
        
    }
    @Override
    public int compareTo(DriverPerformance o) {
        int cmp = 0;
        if (naScore(score) && !naScore(o.getScore()))
            return 1;
        if (!naScore(score) && naScore(o.getScore()))
            return -1;
        if (!naScore(score) && !naScore(o.getScore()))
            cmp = score.compareTo(o.getScore());
        if (cmp == 0) 
            cmp = driverName.compareTo(o.getDriverName());
        return cmp;
    }
    
    private boolean naScore(Integer score) {
        return score == null || score.intValue() == -1;
    }

    public List<VehiclePerformance> getVehiclePerformanceBreakdown() {
        return vehiclePerformanceBreakdown;
    }
    public void setVehiclePerformanceBreakdown(List<VehiclePerformance> vehiclePerformanceBreakdown) {
        this.vehiclePerformanceBreakdown = vehiclePerformanceBreakdown;
    }
    
    public String dump() {
        
        return "new DriverPerformance(" +
               "\"" + groupName + "\"," +
                      driverID + "," +
                      "\"" + driverName + "\"," +
                      "\"" + employeeID + "\"," +
                      score + "," +
                      totalMiles + "," +
                      hardAccelCount + "," +
                      hardBrakeCount + "," +
                      hardTurnCount + "," +
                      hardVerticalCount + "," +
                      seatbeltCount + ", " +
        speedCount0to7Over + "," +
        speedCount8to14Over + "," +
        speedCount15Over + ");";
        

    }
}
