package com.inthinc.pro.automation.deviceTrips;

import java.util.Iterator;

import com.inthinc.pro.automation.device_emulation.DeviceBase;
import com.inthinc.pro.automation.models.GeoPoint;
import com.inthinc.pro.automation.objects.TripTracker;

public class TripDriver extends Thread {
    
    private DeviceBase device;
    private TripTracker tripTracker;

    public TripDriver(DeviceBase device) {
        this.device = device;
        tripTracker = device.getTripTracker();
    }
    
    public boolean start(String origin, String destination){
        tripTracker.getTrip(origin, destination);
        super.start();
        return true;
    }
    
    @Override
    public void run(){
        Iterator<GeoPoint> itr = tripTracker.iterator();
        device.power_on_device();
        device.turn_key_on(60);
        while (itr.hasNext()){
            device.goToNextLocation(device.getState().getSpeed_limit().intValue(), false);
        }
        device.turn_key_off(60);
        device.power_off_device(60);
    }
    
    

}
