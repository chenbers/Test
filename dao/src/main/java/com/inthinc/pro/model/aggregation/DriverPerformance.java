package com.inthinc.pro.model.aggregation;

public class DriverPerformance {
    
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
/*
 * G - Green 4.1 - 5.0
    B - Blue 3.1 - 4.0
    Y - Yellow 2.1 - 3.0
    O - Orange 1.1 - 3.0
    R - Red 0.0 - 1.0
 */
    public String getColor() {
        if (score == null || score < 0)
            return " ";
        if (score < 11)
            return "R";
        if (score < 21)
            return "O";
        if (score < 31)
            return "Y";
        if (score < 41)
            return "B";
        return "G";
    }
    
}
