package com.inthinc.pro.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TrailerReportItem extends BaseEntity implements Comparable<TrailerReportItem> {
    
    private static final long serialVersionUID = 2285846054728010424L;
    
    private String groupName;
    private Integer groupID;
    private String driverName;
    private Integer driverID;
    private String vehicleName;
    private Integer vehicleID;
    private String trailerName;
    private Integer trailerID;
    private TrailerEntryMethod entryMethod;
    private Status status;

    public TrailerEntryMethod getEntryMethod() {
        return entryMethod;
    }
    
    public void setEntryMethod(Date detected, Date entered){
        if(detected == null){
            if(entered == null){
                //both are null
                this.entryMethod = null;
            } else {
                this.entryMethod = TrailerEntryMethod.ENTERED;
            }
        } else {
            if(entered == null){
                this.entryMethod = TrailerEntryMethod.DETECTED;
            } else {
                //neither is null... must check to see which is more recent
                this.entryMethod = (detected.after(entered))?TrailerEntryMethod.DETECTED:TrailerEntryMethod.ENTERED;
            }
        }
    }

    public void setEntryMethod(TrailerEntryMethod entryMethod) {
        this.entryMethod = entryMethod;
    }
    public boolean getAssignedStatus(){
       return driverID!=null ||vehicleID!=null;
    }
    
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getGroupID() {
        return groupID;
    }

    public void setGroupID(Integer groupID) {
        this.groupID = groupID;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public Integer getDriverID() {
        return driverID;
    }

    public void setDriverID(Integer driverID) {
        this.driverID = driverID;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public Integer getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(Integer vehicleID) {
        this.vehicleID = vehicleID;
    }

    public String getTrailerName() {
        return trailerName;
    }

    public void setTrailerName(String trailerName) {
        this.trailerName = trailerName;
    }

    public Integer getTrailerID() {
        return trailerID;
    }

    public void setTrailerID(Integer trailerID) {
        this.trailerID = trailerID;
    }

    @Override
    public int compareTo(TrailerReportItem item) {
        if (trailerName != null && item.getTrailerName() != null)
            return trailerName.toLowerCase().compareTo(item.getTrailerName().toLowerCase());
        return 0;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    
}
