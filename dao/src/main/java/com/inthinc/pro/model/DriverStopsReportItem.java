package com.inthinc.pro.model;

public class DriverStopsReportItem extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Integer roundTrip;
    private String stopLocation;
    private String arrive;
    private String depart;
    private Integer totalTimeAtStop;
    private Integer lowIdle;
    private Integer highIdle;
    private Integer wait;
    private Integer driveTime;
    
    private Double latitude;
    private Double longitude;
    
    public Integer getRoundTrip() {
        return roundTrip;
    }
    public void setRoundTrip(Integer roundTrip) {
        this.roundTrip = roundTrip;
    }
    public String getStopLocation() {
        return stopLocation;
    }
    public void setStopLocation(String stopLocation) {
        this.stopLocation = stopLocation;
    }
    public String getArrive() {
        return arrive;
    }
    public void setArrive(String arrive) {
        this.arrive = arrive;
    }
    public String getDepart() {
        return depart;
    }
    public void setDepart(String depart) {
        this.depart = depart;
    }
    public Integer getTotalTimeAtStop() {
        return totalTimeAtStop;
    }
    public void setTotalTimeAtStop(Integer totalTimeAtStop) {
        this.totalTimeAtStop = totalTimeAtStop;
    }
    public Integer getLowIdle() {
        return lowIdle;
    }
    public void setLowIdle(Integer lowIdle) {
        this.lowIdle = lowIdle;
    }
    public Integer getHighIdle() {
        return highIdle;
    }
    public void setHighIdle(Integer highIdle) {
        this.highIdle = highIdle;
    }
    public Integer getWait() {
        return wait;
    }
    public void setWait(Integer wait) {
        this.wait = wait;
    }
    public Integer getDriveTime() {
        return driveTime;
    }
    public void setDriveTime(Integer driveTime) {
        this.driveTime = driveTime;
    }
    public Double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public Double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
