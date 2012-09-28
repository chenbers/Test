package com.inthinc.pro.model;


public class DriverStops extends BaseEntity {

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
    private String vehicleName;
    private String driverName;

    public DriverStops()
    {
        
    }
    public DriverStops(Integer driverID, Integer vehicleID, String vehicleName, Double lat, Double lng, Long driveTime, Long arriveTime, Long departTime, Integer idleLo, Integer idleHi,
            String zoneName) {
        super();
        this.driverID = driverID;
        this.vehicleID = vehicleID;
        this.vehicleName = vehicleName;
        this.lat = lat;
        this.lng = lng;
        this.driveTime = driveTime;
        this.arriveTime = arriveTime;
        this.departTime = departTime;
        this.idleLo = idleLo;
        this.idleHi = idleHi;
        this.zoneName = zoneName;
    }

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
        if (arriveTime == null)
            return null;
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
        if (arriveTime == null || departTime == null)
            return 0;
        return idleLo;
    }
    public void setIdleLo(Integer idleLo) {
        this.idleLo = idleLo;
    }
    public Integer getIdleHi() {
        if (arriveTime == null || departTime == null)
            return 0;
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
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    
    public Integer getWaitTime() {
        
        if (departTime == null || arriveTime == null)
            return null;
        
        Integer waitTime = (int)(departTime - arriveTime) - idleHi - idleLo;
        
        if (waitTime < 0)
            return 0;
        
        return waitTime;
    }

    public Integer getTotalTime() {
        
        if (departTime == null || arriveTime == null)
            return null;
        return idleHi + idleLo + getWaitTime();
    }
    
    public String getVehicleName() {
        return vehicleName;
    }
    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }
    public String getDriverName() {
        return driverName;
    }
    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
    public void dump() {
        
        System.out.println("new DriverStops("+
                driverID + ", " +
                vehicleID + ", " +
                vehicleName + ", " +
                lat + ", " +
                lng + ", " +
                driveTime + ", " +
                arriveTime + ", " +
                departTime + ", " +
                idleLo + ", " +
                idleHi + ", " +
                (zoneName == null ? "null" : zoneName) + ");");
    }
}
