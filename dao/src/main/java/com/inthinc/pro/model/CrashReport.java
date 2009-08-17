package com.inthinc.pro.model;

import java.util.Date;
import java.util.List;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ID;

public class CrashReport extends BaseEntity {

    /**
     * 
     */
    @Column(updateable = false)
    private static final long serialVersionUID = -2232308206099636851L;
    @ID
    private Integer crashReportID;
    @Column(name = "acctID")
    private Integer accountID;
    private CrashReportStatus crashReportStatus;
    private Date date;
    private LatLng latLng;
    private String location;
    private DamageType damageType;
    private Integer vehicleID;
    @Column(updateable = false)
    private Vehicle vehicle;
    private Integer groupID;
    private Group group;
    private String weather;
    private String description;
    @Column(updateable = false)
    private List<VehicleOccupant> vehicleOccupants;
    @Column(updateable = false)
    private List<CrashDataPoint> crashDataPoints; //Detailed Crash Data
    
    public CrashReport(){
        
    }

    public CrashReport(Integer accountID, CrashReportStatus crashReportStatus, DamageType damageType, Vehicle vehicle, Group group, String weather) {
        super();
        this.accountID = accountID;
        this.setCrashReportStatus(crashReportStatus);
        this.damageType = damageType;
        this.vehicleID = vehicle != null ? vehicle.getVehicleID():null;
        this.vehicle = vehicle;
        this.groupID = group != null? group.getGroupID():null;
        this.group = group;
        this.weather = weather;
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

    

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public DamageType getDamageType() {
        return damageType;
    }

    public void setDamageType(DamageType damageType) {
        this.damageType = damageType;
    }

    public Integer getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(Integer vehicleID) {
        this.vehicleID = vehicleID;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
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

    public List<VehicleOccupant> getVehicleOccupants() {
        return vehicleOccupants;
    }

    public void setVehicleOccupants(List<VehicleOccupant> vehicleOccupants) {
        this.vehicleOccupants = vehicleOccupants;
    }

    

    public void setGroupID(Integer groupID) {
        this.groupID = groupID;
    }

    public Integer getGroupID() {
        return groupID;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Group getGroup() {
        return group;
    }
    
    

    
    public List<CrashDataPoint> getCrashDataPoints() {
        return crashDataPoints;
    }

    
    public void setCrashDataPoints(List<CrashDataPoint> crashDataPoints) {
        this.crashDataPoints = crashDataPoints;
    }
    
    

    
    


    @Override
    public String toString() {
        return "CrashReport [accountID=" + accountID + ", crashDataPoints=" + crashDataPoints + ", crashReportID=" + crashReportID + ", crashReportStatus=" + crashReportStatus
                + ", damageType=" + damageType + ", date=" + date + ", description=" + description + ", group=" + group + ", groupID=" + groupID + ", latLng=" + latLng
                + ", location=" + location + ", vehicle=" + vehicle + ", vehicleID=" + vehicleID + ", vehicleOccupants=" + vehicleOccupants + ", weather=" + weather + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((crashReportID == null) ? 0 : crashReportID.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof CrashReport)) {
            return false;
        }
        CrashReport other = (CrashReport) obj;
        if (crashReportID == null) {
            if (other.crashReportID != null) {
                return false;
            }
        }
        else if (!crashReportID.equals(other.crashReportID)) {
            return false;
        }
        return true;
    }

    public void setCrashReportStatus(CrashReportStatus crashReportStatus) {
        this.crashReportStatus = crashReportStatus;
    }

    public CrashReportStatus getCrashReportStatus() {
        return crashReportStatus;
    }


    
}
