package com.inthinc.pro.model;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Date;

import com.inthinc.pro.notegen.PackageNote;

public class Hazard extends BaseEntity implements HasAccountId {
    private Integer hazardID;                       // unique id from the portal
    private Integer acctID;
    //private final int reported;                     // unix time when it was reported   //TODO: deterimine if they still want to store REPORTED separate from START
    private Date startTime;
    private Date endTime;
    //private final int shelflife;                  // active for this many seconds since reported     //probably not necessary because of endTime
    private Integer radiusMeters;                   // distance in meters
    private Integer radiusInUnits;
    private MeasurementLengthType radiusUnits;
    private HazardType type;                        // type of road hazard
    private String description = "";                // details of the hazard (max 60 chars)
    private Integer driverID;
    private Integer userID;
    private Integer vehicleID;
    private Integer deviceID;
    private Double latitude;                        // latitude   (degrees * 1e6) ...   int on device
    private Double longitude;                       // longitude  (degrees * 1e6) ...   int on device
    private Integer stateID; 
    private String location = "";
    private HazardStatus status;
    //private final boolean urgent;                   // true if urgent                   //TODO: determine if I need to worry about Urgent?  I thought PRD stated ALL are urgent???
    //private final String group;                     // group name (max 40 chars)        //TODO: determine if group is necessary???

    
    private Driver view_driver;
    private User view_user;
    private State view_state;
    
    public Hazard() {
        super();
    }
    /**
     * 
     * Device expecting the following order of parameters when sent over hessian:
     * packet size - short (2 byte)
     * rh id - integer (4 byte)
     * type - byte
     * location - compressed lat/long (6 byte)
     * radius - unsigned integer (4 byte)  [meters]
     * start time - time_t (4 byte)
     * end time - time_t (4 byte)
     * details - string (60 char)
     * 
     * @return
     */
    public ByteArrayOutputStream toByteArrayOutputStream(){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream stream = new DataOutputStream(baos);
        try {
            stream.writeInt(this.hazardID);
            stream.write((byte)this.type.getCode());
            stream.flush();
            PackageNote.longToByte(baos, PackageNote.encodeLat(this.getLatitude()), 3);
            PackageNote.longToByte(baos, PackageNote.encodeLng(this.getLongitude()), 3);
            stream.writeInt(this.getRadiusMeters());
            int startTime = (int) (this.getStartTime().getTime()/1000);
            stream.writeInt(startTime);
            int endTime = (int) (this.getEndTime().getTime()/1000);
            stream.writeInt(endTime);
            stream.writeChars(this.getDescription());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return baos;
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
    public State getState() {
        return view_state;
    }
    public void setState(State state){
        this.view_state = state;
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
            return radiusMeters;
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
        Integer radiusInt = (radius != null)?(int)Math.ceil(radius):null;
        this.radiusMeters = radiusInt;
    }
    public void setRadiusMeters(Integer radius) {
        Double radiusDouble = (radius != null)?radius.doubleValue():null;
        setRadiusMeters(radiusDouble);
    }
    public void setType(HazardType type) {
        this.type = type;
    }
    public void setType(Integer type){
        this.type = HazardType.valueOf(type);
    }
    public void setType(String type){
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
        return radiusUnits!=null?radiusUnits:MeasurementLengthType.ENGLISH_MILES;
    }
    public void setRadiusUnits(MeasurementLengthType radiusUnits) {
        this.radiusUnits = radiusUnits;
    }
    public void setRadiusUnits(Integer code){
        this.radiusUnits = MeasurementLengthType.valueOf(code);
    }
    public Integer getRadiusInUnits() {
        return radiusInUnits;
    }
    public void setRadiusInUnits(Integer radiusInUnits) {
        this.radiusInUnits = radiusInUnits;
    }
}
