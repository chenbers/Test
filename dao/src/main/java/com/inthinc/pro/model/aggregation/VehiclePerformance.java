package com.inthinc.pro.model.aggregation;

public class VehiclePerformance {
    
    private String vehicleName;
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
    
    
    public VehiclePerformance() {
        super();
    }
    public VehiclePerformance(String vehicleName, Integer score, Integer totalMiles, Integer hardAccelCount, Integer hardBrakeCount, Integer hardTurnCount,
            Integer hardVerticalCount, Integer seatbeltCount, Integer speedCount0to7Over, Integer speedCount8to14Over, Integer speedCount15Over) {
        super();
        this.vehicleName = vehicleName;
        this.score = score;
        this.totalMiles = totalMiles;
        this.hardAccelCount = hardAccelCount;
        this.hardBrakeCount = hardBrakeCount;
        this.hardTurnCount = hardTurnCount;
        this.hardVerticalCount = hardVerticalCount;
        this.seatbeltCount = seatbeltCount;
        this.speedCount0to7Over = speedCount0to7Over;
        this.speedCount8to14Over = speedCount8to14Over;
        this.speedCount15Over = speedCount15Over;
    }
    public String getVehicleName() {
        return vehicleName;
    }
    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
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
    public Integer getSeatbeltCount() {
        return seatbeltCount;
    }
    public void setSeatbeltCount(Integer seatbeltCount) {
        this.seatbeltCount = seatbeltCount;
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

    public String dump() {
        return "new VehiclePerformance(" +
            "\"" + vehicleName + "\"," +
            score+ "," +
            totalMiles+ "," +
            hardAccelCount+ "," +
            hardBrakeCount+ "," +
            hardTurnCount+ "," +
            hardVerticalCount+ "," +
            seatbeltCount+ "," +
            speedCount0to7Over+ "," +
            speedCount8to14Over+ "," +
            speedCount15Over+ ");";

    }
}
