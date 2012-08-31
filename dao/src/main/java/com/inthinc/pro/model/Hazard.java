package com.inthinc.pro.model;

import java.util.Date;

public class Hazard extends BaseEntity implements HasAccountId {
    private Integer hazardID;
    private Integer acctID;
    private Date startTime;
    private Date endTime;
    private Integer radiusMeters;
    private MeasurementLengthType radiusUnits;
    private HazardType type;
    private String description = "";
    private Integer driverID;
    private Integer userID;
    private Integer vehicleID;
    private Integer deviceID;
    private Double latitude;
    private Double longitude;
    private Integer stateID; 
    private String location = "";
    private HazardStatus status;
    
    private Driver view_driver;
    private User view_user;
    
    public Hazard() {
        super();
    }
    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("hazardID="+this.hazardID);
        buffer.append(", location="+this.location);
        buffer.append(", status="+this.status);
        buffer.append(", type="+this.type);
        buffer.append(", description="+this.description);
        return buffer.toString();
    }
    public Driver getDriver() {
        return view_driver;
    }
    public void setDriver(Driver driver){
        this.view_driver = driver;
    }
    public User getUser() {
        return view_user;
    }
    public void setUser(User user){
        this.view_user = user;
    }
    public Date getStartTime() {
        if(startTime == null)
            return new Date();
        return startTime;
    }
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    public Date getEndTime() {
        return endTime;
    }
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    public Integer getRadiusMeters() {
        if(radiusMeters!=null){
            return (int) Math.ceil(this.getRadiusUnits().convertToMeters(radiusMeters).doubleValue());
        } else if(getType()==null){
            return radiusMeters;
        }

        return ((Double)getType().getRadius()).intValue();
    }
    public HazardType getType() {
        return type;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getDriverID() {
        return driverID;
    }
    public Integer getVehicleID() {
        return vehicleID;
    }
    public Integer getDeviceID() {
        return deviceID;
    }
    public Double getLat(){
        return getLatitude();
    }
    public Double getLatitude() {
        return latitude;
    }
    public Double getLng() {
        return getLongitude();
    }
    public Double getLongitude() {
        return longitude;
    }
    public Integer getStateID() {
        return stateID;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public Integer getAccountID() {
        return getAcctID();
    }
    public void setAccountID(Integer acctID) {
        this.setAcctID(acctID);
    }
    public HazardStatus getStatus() {
        return status;
    }
    public void setStatus(HazardStatus status) {
        this.status = status;
    }
    public Integer getHazardID() {
        return hazardID;
    }
    public void setHazardID(Integer hazardID) {
        this.hazardID = hazardID;
    }
    public Integer getAcctID() {
        return acctID;
    }
    public void setAcctID(Integer acctID) {
        this.acctID = acctID;
    }
    public Integer getUserID() {
        return userID;
    }
    public void setUserID(Integer userID) {
        this.userID = userID;
    }
    public void setRadiusMeters(Double radius) {
        //we are storing meters as an int in the DB, always round UP to err on the side of caution
        this.radiusMeters = (int)Math.ceil(radius);
    }
    public void setRadiusMeters(Integer radius) {
        setRadiusMeters(radius.doubleValue());
    }
    public void setType(HazardType type) {
        this.type = type;
    }
    public void setType(Integer type){
        this.type = HazardType.valueOf(type);
    }
    public void setDriverID(Integer driverID) {
        this.driverID = driverID;
    }
    public void setVehicleID(Integer vehicleID) {
        this.vehicleID = vehicleID;
    }
    public void setDeviceID(Integer deviceID) {
        this.deviceID = deviceID;
    }
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    public void setStateID(Integer stateID) {
        this.stateID = stateID;
    }
    public MeasurementLengthType getRadiusUnits() {
        return radiusUnits!=null?radiusUnits:MeasurementLengthType.METRIC_METERS;
    }
    public void setRadiusUnits(MeasurementLengthType radiusUnits) {
        this.radiusUnits = radiusUnits;
    }
}
