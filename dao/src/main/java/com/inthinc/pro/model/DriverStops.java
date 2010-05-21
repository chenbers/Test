package com.inthinc.pro.model;

import java.util.List;

import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;

public class DriverStops extends BaseEntity {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Integer driverID;
    private Integer vehicleID;
    private Double lat;
    private Double lng;  
    private Long driveTime;
    private Long arriveTime;
    private Long departTime;
    private Integer idleLo;
    private Integer idleHi;
    private String zoneName;  
    private String address;
    
    private Boolean summary;

    public Integer getDriverID() {
        return driverID;
    }
    public void setDriverID(Integer driverID) {
        this.driverID = driverID;
    }
    public Integer getVehicleID() {
        return vehicleID;
    }
    public void setVehicleID(Integer vehicleID) {
        this.vehicleID = vehicleID;
    }    
    public Double getLat() {
        return lat;
    }
    public void setLat(Double lat) {
        this.lat = lat;
    }
    public Double getLng() {
        return lng;
    }
    public void setLng(Double lng) {
        this.lng = lng;
    }
    public Long getDriveTime() {
        return driveTime;
    }
    public void setDriveTime(Long driveTime) {
        this.driveTime = driveTime;
    }
    public Long getArriveTime() {
        return arriveTime;
    }
    public void setArriveTime(Long arriveTime) {
        this.arriveTime = arriveTime;
    }
    public Long getDepartTime() {
        return departTime;
    }
    public void setDepartTime(Long departTime) {
        this.departTime = departTime;
    }
    public Integer getIdleLo() {
        return idleLo;
    }
    public void setIdleLo(Integer idleLo) {
        this.idleLo = idleLo;
    }
    public Integer getIdleHi() {
        return idleHi;
    }
    public void setIdleHi(Integer idleHi) {
        this.idleHi = idleHi;
    }
    public String getZoneName() {
        return zoneName;
    }
    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }    
    public Boolean getSummary() {
        return summary;
    }
    public void setSummary(Boolean summary) {
        this.summary = summary;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public static DriverStops summarize(List<DriverStops> driverStops) {
        
        DriverStops d = new DriverStops();
        
        int roundTrip = 0;
        long arriveTime = 0;
        long departTime = 0;
        int lowIdle = 0;
        int highIdle = 0;
        long driveTime = 0;
        
        // Sum up over all trips by the selected driver
        for ( DriverStops dsri: driverStops ) {
//            if ( dsri.getRoundTrip() != null ) {
//                roundTrip++;
//            }
            if ( dsri.getArriveTime() != null ) {
                arriveTime += dsri.getArriveTime().intValue();
            }
            if ( dsri.getDepartTime() != null ) {
                departTime += dsri.getDepartTime().intValue();
            }
            if ( dsri.getIdleLo() != null ) {
                lowIdle += dsri.getIdleLo().intValue();
            }
            if ( dsri.getIdleHi() != null ) {
                highIdle += dsri.getIdleHi().intValue();
            }
            if ( dsri.getDriveTime() != null ) {
                driveTime += dsri.getDriveTime();
            }
        }
        
        // Set the summary row
//        d.setRoundTrip(roundTrip);
        d.setArriveTime(arriveTime);
        d.setDepartTime(departTime);
        d.setIdleLo(lowIdle);
        d.setIdleHi(highIdle);
        d.setDriveTime(driveTime);
        
        return d;   
    }
}
