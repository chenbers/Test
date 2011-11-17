package com.inthinc.pro.automation.deviceTrips;

import java.util.Iterator;

import com.inthinc.pro.automation.device_emulation.DeviceBase;
import com.inthinc.pro.automation.models.GeoPoint;
import com.inthinc.pro.automation.objects.TripTracker;

public class TripDriver extends Thread {
    
    private DeviceBase device;
    private TripTracker tripTracker;
    private boolean interrupt = false;

    public TripDriver(DeviceBase device) {
        this.device = device;
        tripTracker = device.getTripTracker();
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
        while (itr.hasNext() && !interrupt){
            device.goToNextLocation(device.getState().getSpeed_limit().intValue(), false);
        }
        if (!interrupt){
            device.turn_key_off(60);
            device.power_off_device(60);   
        }
    }
    
    @Override
    public void interrupt(){
        interrupt = true;
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
}
