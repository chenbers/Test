package com.inthinc.pro.model;

import java.util.Date;
import java.util.List;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ID;
import com.inthinc.pro.dao.annotations.SimpleName;
import com.inthinc.pro.model.event.FullEvent;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@SimpleName(simpleName = "Crash")
public class CrashReport extends BaseEntity {
    /**
     * 
     */
    @Column(updateable = false)
    private static final long serialVersionUID = -2232308206099636851L;
    @ID
    @Column(name = "crashID")
    private Integer crashReportID;
    @Column(name = "acctID")
    private Integer accountID;
    @Column(name = "status")
    private CrashReportStatus crashReportStatus;
    @Column(name = "time")
    private Date date;
    private Double lat;
    private Double lng;
    private Integer vehicleID;
    private Integer driverID;
    @Column(updateable = false)
    private Vehicle vehicle;
    @Column(updateable = false)
    private Driver driver;
    private String weather;
    @Column(name = "desc")
    private String description;
    private Long noteID;
    private Integer occupantCount;
    @Column(updateable = false)
    private FullEvent fullEvent;
    @Column(updateable = false, name = "dataPts")
    private List<CrashDataPoint> crashDataPoints; // Detailed Crash Data
    private byte[] trace;

    public CrashReport() {
    }

    public CrashReport(Integer accountID, CrashReportStatus crashReportStatus, Vehicle vehicle, Driver driver, String weather, Integer occupantCount) {
        super();
        this.accountID = accountID;
        this.setCrashReportStatus(crashReportStatus);
        this.vehicleID = vehicle != null ? vehicle.getVehicleID() : null;
        this.vehicle = vehicle;
        this.weather = weather;
        this.driverID = driver != null ? driver.getDriverID() : null;
        this.driver = driver;
        this.occupantCount = occupantCount;
    }

    public Integer getCrashReportID() {
        return crashReportID;
    }

    public void setCrashReportID(Integer crashReportID) {
        this.crashReportID = crashReportID;
    }

    public Integer getAccountID() {
        return accountID;
    }

    public void setAccountID(Integer accountID) {
        this.accountID = accountID;
    }

    public CrashReportStatus getCrashReportStatus() {
        return crashReportStatus;
    }

    public void setCrashReportStatus(CrashReportStatus crashReportStatus) {
        this.crashReportStatus = crashReportStatus;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(Integer vehicleID) {
        this.vehicleID = vehicleID;
    }

    public Integer getDriverID() {
        return driverID;
    }

    public void setDriverID(Integer driverID) {
        this.driverID = driverID;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getNoteID() {
        return noteID;
    }

    public void setNoteID(Long noteID) {
        this.noteID = noteID;
    }

    public FullEvent getFullEvent() {
        return fullEvent;
    }

    public void setFullEvent(FullEvent fullEvent) {
        this.fullEvent = fullEvent;
    }

    public List<CrashDataPoint> getCrashDataPoints() {
        return crashDataPoints;
    }

    public void setCrashDataPoints(List<CrashDataPoint> crashDataPoints) {
        this.crashDataPoints = crashDataPoints;
    }

    public void setOccupantCount(Integer occupantCount) {
        this.occupantCount = occupantCount;
    }

    public Integer getOccupantCount() {
        return occupantCount;
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

    public LatLng getLatLng() {
        if (lat != null && lng != null) {
            return new LatLng(lat, lng);
        }
        return null;
    }

    public void setLatLng(LatLng latLng) {
        if (latLng != null) {
            lat = latLng.getLat();
            lng = latLng.getLng();
        }
    }

    @Override
    public String toString() {
        return "CrashReport [accountID=" + accountID + ", crashDataPoints=" + crashDataPoints + ", crashReportID=" + crashReportID + ", crashReportStatus=" + crashReportStatus
                + ", date=" + date + ", description=" + description + ", driver=" + driver + ", driverID=" + driverID + ", fullEvent=" + fullEvent + ", lat=" + lat + ", lng="
                + lng + ", noteID=" + noteID + ", occupantCount=" + occupantCount + ", vehicle=" + vehicle + ", vehicleID=" + vehicleID + ", weather=" + weather + "]";
    }

    public byte[] getTrace() {
        return trace;
    }

    public void setTrace(byte[] trace) {
        this.trace = trace;
    }
}
