package com.inthinc.pro.model.hos;

import java.util.Date;
import java.util.TimeZone;

import com.inthinc.hos.model.HOSOrigin;
import com.inthinc.pro.model.BaseEntity;

public class FuelStopRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;
    private Long fuelStopID;
    private Integer driverID;
    private Integer noteID;
    private Integer vehicleID;
    private String vehicleName;
    private Boolean vehicleIsDOT;
    private Long odometerBefore;
    private Long odometerAfter;
    private Date dateTime;
    private TimeZone timeZone;
    private String location;
    private Float lat;
    private Float lng;
    private String trailerID;
    private Boolean edited;
    private Integer editUserID;
    private String editUserName;
    private Boolean deleted;
    private Integer changedCount;
    private Float truckGallons;
    private Float trailerGallons;
    private HOSOrigin origin;
    private Date dateLastUpdated;
    
    public FuelStopRecord() {
        super();
    }
    public FuelStopRecord(Long fuelStopID, Integer driverID, Integer noteID, Integer vehicleID, String vehicleName, Boolean vehicleIsDOT, Long odometerBefore, Long odometerAfter, Date dateTime, TimeZone timeZone, String location,
            Float lat, Float lng, String trailerID, Boolean edited, Integer editUserID, String editUserName, Boolean deleted, Integer changedCnt, Float truckGallons, Float trailerGallons,
            Date dateLastUpdated) {
        super();
        this.fuelStopID = fuelStopID;
        this.driverID = driverID;
        this.noteID = noteID;
        this.vehicleID = vehicleID;
        this.vehicleName = vehicleName;
        this.vehicleIsDOT = vehicleIsDOT;
        this.odometerBefore = odometerBefore;
        this.odometerAfter = odometerAfter;
        this.dateTime = dateTime;
        this.timeZone = timeZone;
        this.location = location;
        this.lat = lat;
        this.lng = lng;
        this.trailerID = trailerID;
        this.edited = edited;
        this.editUserID = editUserID;
        this.editUserName = editUserName;
        this.deleted = deleted;
        this.changedCount = changedCnt;
        this.truckGallons = truckGallons;
        this.trailerGallons = trailerGallons;
        this.dateLastUpdated = dateLastUpdated;
    }
    public Integer getDriverID() {
        return driverID;
    }
    public void setDriverID(Integer driverID) {
        this.driverID = driverID;
    }
    public Integer getNoteID() {
        return noteID;
    }
    public void setNoteID(Integer noteID) {
        this.noteID = noteID;
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
    public Date getDateTime() {
        return dateTime;
    }
    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
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
    public String getTrailerID() {
        return trailerID;
    }
    public void setTrailerID(String trailerID) {
        this.trailerID = trailerID;
    }
    public Boolean getEdited() {
        return edited;
    }
    public void setEdited(Boolean edited) {
        this.edited = edited;
    }
    public Integer getEditUserID() {
        return editUserID;
    }
    public void setEditUserID(Integer editUserID) {
        this.editUserID = editUserID;
    }
    public String getEditUserName() {
        return editUserName;
    }
    public void setEditUserName(String editUserName) {
        this.editUserName = editUserName;
    }
    public Boolean getDeleted() {
        return deleted;
    }
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
    public Integer getChangedCount() {
        return changedCount;
    }
    public void setChangedCount(Integer changedCount) {
        this.changedCount = changedCount;
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
    public Date getDateLastUpdated() {
        return dateLastUpdated;
    }
    public void setDateLastUpdated(Date dateLastUpdated) {
        this.dateLastUpdated = dateLastUpdated;
    }
    public Long getFuelStopID() {
        return fuelStopID;
    }
    public void setFuelStopID(Long fuelStopID) {
        this.fuelStopID = fuelStopID;
    }
    public Long getOdometerBefore() {
        return odometerBefore;
    }
    public void setOdometerBefore(Long odometerBefore) {
        this.odometerBefore = odometerBefore;
    }
    public Long getOdometerAfter() {
        return odometerAfter;
    }
    public void setOdometerAfter(Long odometerAfter) {
        this.odometerAfter = odometerAfter;
    }
    public HOSOrigin getOrigin() {
        return origin;
    }
    public void setOrigin(HOSOrigin origin) {
        this.origin = origin;
    }
}
