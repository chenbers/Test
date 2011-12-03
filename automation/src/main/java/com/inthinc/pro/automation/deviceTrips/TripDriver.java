package com.inthinc.pro.automation.deviceTrips;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.inthinc.pro.automation.deviceEnums.DeviceAttrs;
import com.inthinc.pro.automation.deviceEnums.DeviceNoteTypes;
import com.inthinc.pro.automation.device_emulation.DeviceBase;
import com.inthinc.pro.automation.models.GeoPoint;
import com.inthinc.pro.automation.objects.TripTracker;

public class TripDriver extends Thread {
    
    private DeviceBase device;
    private TripTracker tripTracker;
    private boolean interrupt = false;
    private ArrayList<Map<DeviceAttrs, Integer>> events;

    public TripDriver(DeviceBase device) {
        this.device = device;
        tripTracker = device.getTripTracker();
        events = new ArrayList<Map<DeviceAttrs, Integer>>();
    }
    
    @Override
    public void start(){
        super.start();
    }
    
    public boolean addToTrip(String origin, String destination){
        tripTracker.getTrip(origin, destination);
        return true;
    }
    
    @Override
    public void run(){
        Iterator<GeoPoint> itr = tripTracker.iterator();
        if (!interrupt){
            device.power_on_device();
            device.turn_key_on(60);
        }
        int totalNotes = tripTracker.size()*100;
        while (itr.hasNext() && !interrupt){
            int currentPercent = totalNotes / tripTracker.currentCount();
            if (events.get(currentPercent)!=null){
                executeEvent(events.get(currentPercent));
            }
            device.goToNextLocation(device.getState().getSpeed_limit().intValue(), false);
            
        }
        if (!interrupt){
            device.turn_key_off(60);
            device.power_off_device(60);   
        }
    }
    
    private void executeEvent(Map<DeviceAttrs, Integer> map) {
        
    }

    @Override
    public void interrupt(){
        interrupt = true;
        device = null;
        throw new NullPointerException("Ending the trip???");
    }

    public void testTrip() {
        tripTracker.addLocation(new GeoPoint(33.0104, -117.111));
        tripTracker.addLocation(new GeoPoint(33.0104, -117.113));
        tripTracker.addLocation(new GeoPoint(33.01, -117.113));
        tripTracker.addLocation(new GeoPoint(33.0097, -117.1153));
        tripTracker.addLocation(new GeoPoint(33.015, -117.116));
        tripTracker.addLocation(new GeoPoint(33.0163, -117.1159));
        tripTracker.addLocation(new GeoPoint(33.018, -117.1153));
        tripTracker.addLocation(new GeoPoint(33.0188, -117.118));
        tripTracker.addLocation(new GeoPoint(33.0192, -117.1199));
        tripTracker.addLocation(new GeoPoint(33.021, -117.119));
        tripTracker.addLocation(new GeoPoint(33.022, -117.114));
        tripTracker.addLocation(new GeoPoint(33.0205, -117.111));
        tripTracker.addLocation(new GeoPoint(33.02, -117.109));
        tripTracker.addLocation(new GeoPoint(33.02, -117.108));
        tripTracker.addLocation(new GeoPoint(33.022, -117.104));
        tripTracker.addLocation(new GeoPoint(33.0217, -117.103));
        tripTracker.addLocation(new GeoPoint(33.0213, -117.1015));
        tripTracker.addLocation(new GeoPoint(33.0185, -117.1019));
        tripTracker.addLocation(new GeoPoint(33.017, -117.102));
        tripTracker.addLocation(new GeoPoint(33.015, -117.1032));
        tripTracker.addLocation(new GeoPoint(33.013, -117.105));
        tripTracker.addLocation(new GeoPoint(33.011, -117.106));
        tripTracker.addLocation(new GeoPoint(33.0108, -117.108));
        tripTracker.addLocation(new GeoPoint(33.0108, -117.109));
        tripTracker.addLocation(new GeoPoint(33.0106, -117.11));
        tripTracker.addLocation(new GeoPoint(33.0104, -117.111));
    }
    
    //addSpeedingNote(Integer distance, Integer topSpeed, Integer avgSpeed)
    //SPEEDING_EX3(93, DeviceAttrs.TOP_SPEED, DeviceAttrs.DISTANCE, DeviceAttrs.MAX_RPM, DeviceAttrs.SPEED_LIMIT, DeviceAttrs.AVG_SPEED, DeviceAttrs.AVG_RPM),
    public void addSpeedingSection(int percentTimeIn, int duration, int speedOverLimit, int speedLimit, int maxRpm, int avgRpm){
        Map<DeviceAttrs, Integer> map = new HashMap<DeviceAttrs, Integer>();
        map.put(DeviceAttrs.DURATION, duration);
        map.put(DeviceAttrs.DWNLD_TYPE, DeviceNoteTypes.SPEEDING_EX3.getCode());
        map.put(DeviceAttrs.MAX_SPEED_LIMIT, speedOverLimit);
        map.put(DeviceAttrs.SPEED_LIMIT, speedLimit);
        map.put(DeviceAttrs.MAX_RPM, maxRpm);
        map.put(DeviceAttrs.AVG_RPM, avgRpm);
        events.add(percentTimeIn, map);
    }
    
    
    //SEATBELT(3, DeviceAttrs.TOP_SPEED, DeviceAttrs.DISTANCE, DeviceAttrs.MAX_RPM)
    //add_seatBelt(Integer topSpeed, Integer avgSpeed, Integer distance)
    public void addSeatBeltViolation(int percentTimeIn, int duration, int speed){
        Map<DeviceAttrs, Integer> map = new HashMap<DeviceAttrs, Integer>();
        map.put(DeviceAttrs.DURATION, duration);
        map.put(DeviceAttrs.DWNLD_TYPE, DeviceNoteTypes.SEATBELT.getCode());
        map.put(DeviceAttrs.SPEED_ID, speed);
        events.add(percentTimeIn, map);
    }
    
    
    //add_note_event(Integer deltaX, Integer deltaY, Integer deltaZ)
    //NOTE_EVENT(2, DeviceAttrs.DELTA_VS),
    public void addEvent(int percentTimeIn, int speed, int deltaX, int deltaY, int deltaZ){
        Map<DeviceAttrs, Integer> map = new HashMap<DeviceAttrs, Integer>();
        map.put(DeviceAttrs.DWNLD_TYPE, DeviceNoteTypes.NOTE_EVENT.getCode());
        map.put(DeviceAttrs.DELTAV_X, deltaX);
        map.put(DeviceAttrs.DELTAV_Y, deltaY);
        map.put(DeviceAttrs.DELTAV_Z, deltaZ);
        events.add(percentTimeIn, map);
    }
    
    
}
