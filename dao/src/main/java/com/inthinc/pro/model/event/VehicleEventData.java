package com.inthinc.pro.model.event;

import com.inthinc.pro.model.Vehicle;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Holds vehicle event data.
 */
public class VehicleEventData {
    private Map<Integer, Vehicle> vehicles = new HashMap<Integer, Vehicle>();
    private Map<Integer, Integer> noteCodes = new HashMap<Integer, Integer>();
    private Map<Integer, Integer> eventCodes = new HashMap<Integer, Integer>();
    private Map<Integer, Date> dates = new HashMap<Integer, Date>();
    private Map<Integer, Date> prevEventDates = new HashMap<Integer, Date>();
    private Map<Integer, Integer> deviceIDs = new HashMap<Integer, Integer>();
    private Map<Integer, String> driveOdometers = new HashMap<Integer, String>();
    private Map<Integer, String> odometersLastEvent = new HashMap<Integer, String>();
    private Map<Integer, String> engineHoursAtDates = new HashMap<Integer, String>();
    private Map<Integer, String> engineHoursAtLastDates = new HashMap<Integer, String>();
    private Map<Integer, String> vehicleYmms = new HashMap<Integer, String>();
    private Map<Integer, String> maintenanceEvents = new HashMap<Integer, String>();
    private Map<Integer, String> values = new HashMap<Integer, String>();
    private Map<Integer, String> actuals = new HashMap<Integer, String>();
    private Map<Integer, String> groupNames = new HashMap<Integer, String>();

    public void putGroupName(Integer vehicleID, String groupName){
        groupNames.put(vehicleID, groupName);
    }

    public void putValue(Integer vehicleID, String value) {
        values.put(vehicleID, value);
    }

    public void putActual(Integer vehicleID, String actual) {
        actuals.put(vehicleID, actual);
    }

    public void putVehicleYmm(Integer vehicleID, String vehicleYmm) {
        vehicleYmms.put(vehicleID, vehicleYmm);
    }

    public void putMaintenanceEvent(Integer vehicleID, String maintenanceEvent) {
        maintenanceEvents.put(vehicleID, maintenanceEvent);
    }

    public void putVehicle(Integer vehicleID, Vehicle vehicle) {
        vehicles.put(vehicleID, vehicle);
    }

    public void putNoteCode(Integer vehicleID, Integer noteCode) {
        noteCodes.put(vehicleID, noteCode);
    }

    public void putEventCode(Integer vehicleID, Integer eventCode) {
        eventCodes.put(vehicleID, eventCode);
    }

    public void putDate(Integer vehicleID, Date date) {
        dates.put(vehicleID, date);
    }

    public void putDeviceID(Integer vehicleID, Integer deviceID){
        deviceIDs.put(vehicleID, deviceID);
    }

    public Map<Integer, Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(Map<Integer, Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public Map<Integer, Integer> getNoteCodes() {
        return noteCodes;
    }

    public void setNoteCodes(Map<Integer, Integer> noteCodes) {
        this.noteCodes = noteCodes;
    }

    public Map<Integer, Integer> getEventCodes() {
        return eventCodes;
    }

    public void setEventCodes(Map<Integer, Integer> eventCodes) {
        this.eventCodes = eventCodes;
    }

    public Map<Integer, Date> getDates() {
        return dates;
    }

    public void setDates(Map<Integer, Date> dates) {
        this.dates = dates;
    }

    public Map<Integer, Integer> getDeviceIDs() {
        return deviceIDs;
    }

    public void setDeviceIDs(Map<Integer, Integer> deviceIDs) {
        this.deviceIDs = deviceIDs;
    }

    public Map<Integer, Date> getPrevEventDates() {
        return prevEventDates;
    }

    public void setPrevEventDates(Map<Integer, Date> prevEventDates) {
        this.prevEventDates = prevEventDates;
    }

    public Map<Integer, String> getDriveOdometers() {
        return driveOdometers;
    }

    public void setDriveOdometers(Map<Integer, String> driveOdometers) {
        this.driveOdometers = driveOdometers;
    }

    public Map<Integer, String> getOdometersLastEvent() {
        return odometersLastEvent;
    }

    public void setOdometersLastEvent(Map<Integer, String> odometersLastEvent) {
        this.odometersLastEvent = odometersLastEvent;
    }

    public Map<Integer, String> getEngineHoursAtDates() {
        return engineHoursAtDates;
    }

    public void setEngineHoursAtDates(Map<Integer, String> engineHoursAtDates) {
        this.engineHoursAtDates = engineHoursAtDates;
    }

    public Map<Integer, String> getEngineHoursAtLastDates() {
        return engineHoursAtLastDates;
    }

    public void setEngineHoursAtLastDates(Map<Integer, String> engineHoursAtLastDates) {
        this.engineHoursAtLastDates = engineHoursAtLastDates;
    }

    public Map<Integer, String> getVehicleYmms() {
        return vehicleYmms;
    }

    public void setVehicleYmms(Map<Integer, String> vehicleYmms) {
        this.vehicleYmms = vehicleYmms;
    }

    public Map<Integer, String> getMaintenanceEvents() {
        return maintenanceEvents;
    }

    public void setMaintenanceEvents(Map<Integer, String> maintenanceEvents) {
        this.maintenanceEvents = maintenanceEvents;
    }

    public Map<Integer, String> getValues() {
        return values;
    }

    public void setValues(Map<Integer, String> values) {
        this.values = values;
    }

    public Map<Integer, String> getActuals() {
        return actuals;
    }

    public void setActuals(Map<Integer, String> actuals) {
        this.actuals = actuals;
    }

    public Map<Integer, String> getGroupNames() {
        return groupNames;
    }

    public void setGroupNames(Map<Integer, String> groupNames) {
        this.groupNames = groupNames;
    }
}
