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

    private Integer hosLogID;
    private Integer driverID;
    private String driverName;
    private RuleSetType driverDotType;
    private Integer vehicleID;
    private String vehicleName;
    private Boolean vehicleIsDOT;
    private Number vehicleOdometer;
    private Date logTime;
    private TimeZone timeZone;
    private HOSStatus status;
    private HOSOrigin origin;
    private String location;
    private Long lat;
    private Long lng;
    private Long distance;
    private String trailerID;
    private String serviceID;
    private Boolean singleDriver;
    private Boolean edited;
    private String editUserName;
    private String originalLocation;
    private Boolean deleted;
    private String notificationData;
    
    public Boolean getDeleted() {
        return deleted;
    }
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
    public String getNotificationData() {
        return notificationData;
    }
    public void setNotificationData(String notificationData) {
        this.notificationData = notificationData;
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
    public Long getLat() {
        return lat;
    }
    public void setLat(Long lat) {
        this.lat = lat;
    }
    public Long getLng() {
        return lng;
    }
    public void setLng(Long lng) {
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
}
