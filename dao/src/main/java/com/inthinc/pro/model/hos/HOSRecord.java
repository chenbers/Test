package com.inthinc.pro.model.hos;

import java.util.Date;
import java.util.TimeZone;

import javax.xml.bind.annotation.XmlRootElement;

import com.inthinc.hos.model.HOSOrigin;
import com.inthinc.hos.model.HOSStatus;
import com.inthinc.hos.model.RuleSetType;
import com.inthinc.pro.model.BaseEntity;

@XmlRootElement
public class HOSRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;
    
    private Integer hosLogID;
    private Integer driverID;
    private Integer noteID;
    private RuleSetType driverDotType;
    private Integer vehicleID;
    private String vehicleName;
    private Boolean vehicleIsDOT;
    private Number vehicleOdometer;
    private Date originalLogTime;
    private Date logTime;
    private Date addedTime;
    private TimeZone timeZone;
    private HOSStatus status;
    private HOSOrigin origin;
    private String location;
    private Float lat;
    private Float lng;
    private Long distance;
    private String trailerID;
    private String serviceID;
    private Boolean singleDriver;
    private Boolean edited;
    private Integer editUserID;
    private String editUserName;
    private String originalLocation;
    private Boolean deleted;
    private Integer changedCnt;
    private Float truckGallons;
    private Float trailerGallons;
    private String employeeID;
    private String vehicleLicense;
    private Boolean tripReportFlag;
    private Boolean tripInspectionFlag;
    private Date dateLastUpdated;
    
    public HOSRecord()
    {
        
    }
    public HOSRecord(Integer hosLogID, Integer driverID,// String driverName,
            RuleSetType driverDotType, Integer vehicleID, String vehicleName, Boolean vehicleIsDOT,
            Number vehicleOdometer, Date logTime, Date addedTime, TimeZone timeZone, HOSStatus status, HOSOrigin origin, String location, Float lat, Float lng, Long distance,
            String trailerID, String serviceID, Boolean singleDriver, Boolean edited, String editUserName, Boolean deleted) {
        super();
        this.hosLogID = hosLogID;
        this.driverID = driverID;
        this.driverDotType = driverDotType;
        this.vehicleID = vehicleID;
        this.vehicleName = vehicleName;
        this.vehicleIsDOT = vehicleIsDOT;
        this.vehicleOdometer = vehicleOdometer;
        this.logTime = logTime;
        this.addedTime = addedTime;
        this.timeZone = timeZone;
        this.status = status;
        this.origin = origin;
        this.location = location;
        this.lat = lat;
        this.lng = lng;
        this.distance = distance;
        this.trailerID = trailerID;
        this.serviceID = serviceID;
        this.singleDriver = singleDriver;
        this.edited = edited;
        this.editUserName = editUserName;
        this.deleted = deleted;
    }
    
    public Integer getChangedCnt() {
        return changedCnt;
    }
    public void setChangedCnt(Integer changedCnt) {
        this.changedCnt = changedCnt;
    }
    public Boolean getDeleted() {
        return deleted;
    }
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
//    public String getNotificationData() {
//        String data = status.toString(); 
//
//        if (status == HOSStatus.FUEL_STOP)
//            data += "Vehicle Gallons: " + getTruckGallons() + " Trailer Gallons: " + getTrailerGallons();
//        
//        return data;
//        
//    }
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
    public String getVehicleName() {
        return vehicleName;
    }
    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }
    public Boolean getVehicleIsDOT() {
        return vehicleIsDOT;
    }
    public void setVehicleIsDOT(Boolean vehicleIsDOT) {
        this.vehicleIsDOT = vehicleIsDOT;
    }
    public Date getLogTime() {
        return logTime;
    }
    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }
    public Date getOriginalLogTime() {
        return originalLogTime;
    }
    public void setOriginalLogTime(Date originalLogTime) {
        this.originalLogTime = originalLogTime;
    }
    public Date getAddedTime() {
        return addedTime;
    }
    public void setAddedTime(Date addedTime) {
        this.addedTime = addedTime;
    }
    public TimeZone getTimeZone() {
        return timeZone;
    }
    public void setTimeZone(TimeZone timeZone) {
        this.timeZone = timeZone;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public Float getLat() {
        return lat;
    }
    public void setLat(Float lat) {
        this.lat = lat;
    }
    public Float getLng() {
        return lng;
    }
    public void setLng(Float lng) {
        this.lng = lng;
    }
    public Long getDistance() {
        return distance;
    }
    public void setDistance(Long distance) {
        this.distance = distance;
    }
    public String getTrailerID() {
        return trailerID;
    }
    public void setTrailerID(String trailerID) {
        this.trailerID = trailerID;
    }
    public String getServiceID() {
        return serviceID;
    }
    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }
    public Boolean getSingleDriver() {
        return singleDriver;
    }
    public void setSingleDriver(Boolean singleDriver) {
        this.singleDriver = singleDriver;
    }
    public Boolean getEdited() {
        return edited;
    }
    public void setEdited(Boolean edited) {
        this.edited = edited;
    }
    public String getEditUserName() {
        return editUserName;
    }
    public void setEditUserName(String editUserName) {
        this.editUserName = editUserName;
    }
    public String getOriginalLocation() {
        return originalLocation;
    }
    public void setOriginalLocation(String originalLocation) {
        this.originalLocation = originalLocation;
    }
    
    public RuleSetType getDriverDotType() {
        return driverDotType;
    }
    public void setDriverDotType(RuleSetType driverDotType) {
        this.driverDotType = driverDotType;
    }
    public HOSStatus getStatus() {
        return status;
    }
    public void setStatus(HOSStatus status) {
        this.status = status;
    }
    public HOSOrigin getOrigin() {
        return origin;
    }
    public void setOrigin(HOSOrigin origin) {
        this.origin = origin;
    }
    public Integer getHosLogID() {
        return hosLogID;
    }
    public void setHosLogID(Integer hosLogID) {
        this.hosLogID = hosLogID;
    }
    public Number getVehicleOdometer() {
        return vehicleOdometer;
    }
    public void setVehicleOdometer(Number vehicleOdometer) {
        this.vehicleOdometer = vehicleOdometer;
    }

    public Float getTruckGallons() {
        return truckGallons;
    }
    
    public void setTruckGallons(Float truckGallons) {
        this.truckGallons = truckGallons;
    }
    
    public Float getTrailerGallons() {
        return trailerGallons;
    }

    public void setTrailerGallons(Float trailerGallons) {
        this.trailerGallons = trailerGallons;
    }

    public Integer getNoteID() {
        return noteID;
    }
    public void setNoteID(Integer noteID) {
        this.noteID = noteID;
    }
    
    public String getEmployeeID() {
        return employeeID;
    }
    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getVehicleLicense() {
        return vehicleLicense;
    }
  
    public void setVehicleLicense(String vehicleLicense) {
        this.vehicleLicense = vehicleLicense;
    }
    
  
    public Integer getEditUserID() {
        return editUserID;
    }
    public void setEditUserID(Integer editUserID) {
        this.editUserID = editUserID;
    }

    public Boolean getTripReportFlag() {
        return tripReportFlag;
    }
    public void setTripReportFlag(Boolean tripReportFlag) {
        this.tripReportFlag = tripReportFlag;
    }
    public Boolean getTripInspectionFlag() {
        return tripInspectionFlag;
    }
    public void setTripInspectionFlag(Boolean tripInspectionFlag) {
        this.tripInspectionFlag = tripInspectionFlag;
    }
    public Date getDateLastUpdated() {
        return dateLastUpdated;
    }
    public void setDateLastUpdated(Date dateLastUpdated) {
        this.dateLastUpdated = dateLastUpdated;
    }
    public void dump () {
        System.out.println("new HOSRecord(" +
        hosLogID + "," + 
        driverID + "," +
        "RuleSetType." + driverDotType.getName() + "," +
        vehicleID + "," +
        "\"" + vehicleName + "\"," +
        vehicleIsDOT + "," +
         vehicleOdometer + "," +
        "new Date(" + logTime.getTime() + "l)," +
        "new Date(" + addedTime.getTime() + "l)," +
        "TimeZone.getTimeZone(\"" + timeZone.getID() + "\")," +
        "HOSStatus." + status.getName() + "," +
        "HOSOrigin." + origin.getName() + "," +
        "\"" + location + "\"," +
        lat + "," +
        lng + "," +
        distance+ "," +
        "\"" + trailerID+ "\"," +
        "\"" + serviceID + "\"," +
        singleDriver + "," +
        edited + "," +
        "\"" + editUserName + "\"," +
        deleted + "),");
    }

}
