package com.inthinc.pro.model;

import javax.xml.bind.annotation.XmlRootElement;

import org.joda.time.DateTime;

import com.inthinc.pro.model.configurator.SettingType;
import com.inthinc.pro.model.event.EventType;

@XmlRootElement
public class MaintenanceReportItem extends BaseEntity implements Comparable<MaintenanceReportItem> {
    
    private String groupName;
    private Integer vehicleID;
    private Integer groupID;
    private String vehicleName;
    private Integer vehicleOdometer;
    private Integer vehicleEngineHours;
    private String ymmString;
    private EventType eventType;
    private Double eventValue;
    private Integer thresholdOdo;
    private Integer thresholdHours;
    private Integer thresholdBase;
    private SettingType settingType;
    private DateTime eventTime;
    private Long eventOdometer;
    private Integer eventEngineHours;

    @Override
    public int compareTo(MaintenanceReportItem item) {
        if (vehicleName != null && item.getVehicleName() != null)
            return vehicleName.toLowerCase().compareTo(item.getVehicleName().toLowerCase());
        return 0;
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

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getYmmString() {
        return ymmString;
    }

    public void setYmmString(String ymmString) {
        this.ymmString = ymmString;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public Double getEventValue() {
        return eventValue;
    }

    public void setEventValue(Double eventValue) {
        this.eventValue = eventValue;
    }

    public SettingType getSettingType() {
        return settingType;
    }

    public void setSettingType(SettingType settingType) {
        this.settingType = settingType;
    }

    public Integer getVehicleOdometer() {
        return vehicleOdometer;
    }

    public void setVehicleOdometer(Integer vehicleOdometer) {
        this.vehicleOdometer = vehicleOdometer;
    }

    public DateTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(DateTime eventTime) {
        this.eventTime = eventTime;
    }

    public Long getEventOdometer() {
        return eventOdometer;
    }

    public void setEventOdometer(Long eventOdometer) {
        this.eventOdometer = eventOdometer;
    }

    public Integer getVehicleEngineHours() {
        return vehicleEngineHours;
    }

    public void setVehicleEngineHours(Integer vehicleEngineHours) {
        this.vehicleEngineHours = vehicleEngineHours;
    }

    public Integer getEventEngineHours() {
        return eventEngineHours;
    }

    public void setEventEngineHours(Integer eventEngineHours) {
        this.eventEngineHours = eventEngineHours;
    }

    public Integer getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(Integer vehicleID) {
        this.vehicleID = vehicleID;
    }

    public Integer getThresholdBase() {
        return thresholdBase;
    }

    public void setThresholdBase(Integer thresholdBase) {
        this.thresholdBase = thresholdBase;
    }

    public Integer getThresholdOdo() {
        return thresholdOdo;
    }

    public void setThresholdOdo(Integer thresholdOdo) {
        this.thresholdOdo = thresholdOdo;
    }

    public Integer getThresholdHours() {
        return thresholdHours;
    }

    public void setThresholdHours(Integer thresholdHours) {
        this.thresholdHours = thresholdHours;
    }
}
