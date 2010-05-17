package com.inthinc.pro.model;

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

}
