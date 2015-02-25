package com.inthinc.pro.model;

import java.sql.Date;
import java.sql.Time;

import javax.xml.bind.annotation.XmlRootElement;

import org.joda.time.DateTime;

import com.inthinc.pro.model.configurator.SettingType;

@XmlRootElement
public class MaintenanceReportItem extends BaseEntity implements Comparable<MaintenanceReportItem> {
    
    private String groupName;
    private Integer vehicleID;
    private Long noteID;
    private Integer groupID;
    private String vehicleName;
    private Integer vehicleOdometer;
    private Integer vehicleEngineHours;
    private String ymmString;
    // private EventType eventType;
    private MaintenanceEventType maintenanceEventType;
    
    // private Double eventValue;
    private Double eventVoltage;
    private Double eventEngineTemp;
    private Double eventTransmissionTemp;
    private Double eventDpfFlowRate;
    private Double eventOilPressure;
    
    private Integer thresholdOdo;
    private Integer thresholdHours;
    private Integer thresholdBase;
    private Double thresholdVoltage;
    private Double thresholdEngineTemp;
    private Double thresholdTransmissionTemp;
    private Double thresholdDpfFlowRate;
    private Double thresholdOilPressure;
    
    private SettingType settingType;
    private DateTime eventTime;
    private Integer eventOdometer;
    private Integer eventEngineHours;
    
    @Override
    public String toString() {
        StringBuilder toString = new StringBuilder();
        toString.append("MaintenanceReportItem: [");
        toString.append("noteID=" + noteID + ", ");
        toString.append("groupName=" + groupName + ", ");
        toString.append("vehicleID=" + vehicleID + ", ");
        toString.append("groupID=" + groupID + ", ");
        toString.append("vehicleName=" + vehicleName + ", ");
        toString.append("vehicleOdometer=" + vehicleOdometer + ", ");
        toString.append("vehicleEngineHours=" + vehicleEngineHours + ", ");
        toString.append("ymmString=" + ymmString + ", ");
        toString.append("maintenanceEventType=" + maintenanceEventType + ", ");
        
        toString.append("eventVoltage=" + eventVoltage + ", ");
        toString.append("eventEngineTemp=" + eventEngineTemp + ", ");
        toString.append("eventTransmissionTemp=" + eventTransmissionTemp + ", ");
        toString.append("eventDpfFlowRate=" + eventDpfFlowRate + ", ");
        toString.append("eventOilPressure=" + eventOilPressure + ", ");
        
        toString.append("thresholdOdo=" + thresholdOdo + ", ");
        toString.append("thresholdHours=" + thresholdHours + ", ");
        toString.append("thresholdBase=" + thresholdBase + ", ");
        toString.append("thresholdVoltage=" + thresholdVoltage + ", ");
        toString.append("thresholdEngineTemp=" + thresholdEngineTemp + ", ");
        toString.append("thresholdTransmissionTemp=" + thresholdTransmissionTemp + ", ");
        toString.append("thresholdDpfFlowRate=" + thresholdDpfFlowRate + ", ");
        toString.append("thresholdOilPressure=" + thresholdOilPressure + ", ");
        
        toString.append("settingType=" + settingType + ", ");
        toString.append("eventTime=" + eventTime + ", ");
        toString.append("eventOdometer=" + eventOdometer + ", ");
        toString.append("eventEngineHours=" + eventEngineHours + ", ");
        toString.append("]");
        
        return toString.toString();
    }
    
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
    
    public SettingType getSettingType() {
        return settingType;
    }
    
    public void setSettingType(SettingType settingType) {
        this.settingType = settingType;
    }
    
    public Integer getVehicleOdometer() {
        return (vehicleOdometer!=null)?vehicleOdometer:0;
    }
    
    public void setVehicleOdometer(Integer vehicleOdometer) {
        this.vehicleOdometer = vehicleOdometer;
    }
    
    public DateTime getEventTime() {
        return eventTime;
    }
    
    public void setEventTime(Date time) {
        this.eventTime = new DateTime(time);
    }
    
    public void setEventTime(DateTime eventTime) {
        this.eventTime = eventTime;
    }
    
    public Integer getEventOdometer() {
        return (eventOdometer!=null)?eventOdometer:0;
    }
    
    public void setEventOdometer(Integer eventOdometer) {
        this.eventOdometer = eventOdometer;
    }
    
    public Integer getVehicleEngineHours() {
        return (vehicleEngineHours!=null)?vehicleEngineHours:0;
    }
    
    public void setVehicleEngineHours(Integer vehicleEngineHours) {
        this.vehicleEngineHours = vehicleEngineHours;
    }
    
    public Integer getEventEngineHours() {
        return (eventEngineHours!=null)?eventEngineHours:0;
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
    
    public Double getEventVoltage() {
        return eventVoltage;
    }
    
    public void setEventVoltage(Double eventVoltage) {
        this.eventVoltage = eventVoltage;
    }
    
    public Double getEventEngineTemp() {
        return eventEngineTemp;
    }
    
    public void setEventEngineTemp(Double eventEngineTemp) {
        this.eventEngineTemp = eventEngineTemp;
    }
    
    public Double getEventTransmissionTemp() {
        return eventTransmissionTemp;
    }
    
    public void setEventTransmissionTemp(Double eventTransmissionTemp) {
        this.eventTransmissionTemp = eventTransmissionTemp;
    }
    
    public Double getEventDpfFlowRate() {
        return eventDpfFlowRate;
    }
    
    public void setEventDpfFlowRate(Double eventDpfFlowRate) {
        this.eventDpfFlowRate = eventDpfFlowRate;
    }
    
    public Double getEventOilPressure() {
        return eventOilPressure;
    }
    
    public void setEventOilPressure(Double eventOilPressure) {
        this.eventOilPressure = eventOilPressure;
    }
    
    public MaintenanceEventType getMaintenanceEventType() {
        return maintenanceEventType;
    }
    
    public void setMaintenanceEventType(MaintenanceEventType maintenanceEventType) {
        this.maintenanceEventType = maintenanceEventType;
    }
    
    public Double getThresholdVoltage() {
        return thresholdVoltage;
    }
    
    public void setThresholdVoltage(Double thresholdVoltage) {
        this.thresholdVoltage = thresholdVoltage;
    }
    
    public Double getThresholdEngineTemp() {
        return thresholdEngineTemp;
    }
    
    public void setThresholdEngineTemp(Double thresholdEngineTemp) {
        this.thresholdEngineTemp = thresholdEngineTemp;
    }
    
    public Double getThresholdTransmissionTemp() {
        return thresholdTransmissionTemp;
    }
    
    public void setThresholdTransmissionTemp(Double thresholdTransmissionTemp) {
        this.thresholdTransmissionTemp = thresholdTransmissionTemp;
    }
    
    public Double getThresholdDpfFlowRate() {
        return thresholdDpfFlowRate;
    }
    
    public void setThresholdDpfFlowRate(Double thresholdDpfFlowRate) {
        this.thresholdDpfFlowRate = thresholdDpfFlowRate;
    }
    
    public Double getThresholdOilPressure() {
        return thresholdOilPressure;
    }
    
    public void setThresholdOilPressure(Double thresholdOilPressure) {
        this.thresholdOilPressure = thresholdOilPressure;
    }

    public Long getNoteID() {
        return noteID;
    }

    public void setNoteID(Long noteID) {
        this.noteID = noteID;
    }
}
