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
}
