package com.inthinc.device.emulation.utils;

import java.util.List;

import com.inthinc.device.devices.TiwiProDevice;
import com.inthinc.device.objects.TripDriver;
import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.objects.AutomationCalendar;
import com.inthinc.pro.automation.resources.FileRW;
import com.inthinc.pro.automation.utils.AutomationThread;

public class SimpleStress {
    
    private final List<String> imeis;
    private static final Addresses address = Addresses.USER_CREATED.setUrlAndPort("tp.inthinc.com", 8, "tp.inthinc.com", 8090);
    
    private static final String start = "40.710459,-111.993245";
    private static final String stop = "35.446687,-112.003148";
    private final AutomationCalendar startTime;
      
    public SimpleStress(Long startTime){
        this.startTime = new AutomationCalendar(startTime * 1000);
        FileRW rw = new FileRW();
        imeis = rw.read("imeis-acct9.txt");
        driveTrip();
    }
    
    private void driveTrip(){
        GoogleTrips trip = new GoogleTrips();
        List<GeoPoint> points = trip.getTrip(start, stop);
        for (String imei : imeis){
//            TiwiProDevice tiwi = new TiwiProDevice(imei, address);
            TiwiProDevice tiwi = new TiwiProDevice("DEVICEDOESNTEXIST", address);
            tiwi.getState().getTime().setDate(startTime);
            tiwi.getTripTracker().setPoints(points);
            TripDriver driver = new TripDriver(tiwi);
            driver.run();
            while (Thread.activeCount() > 20){
                AutomationThread.pause(500l);
            }
        }
    }
    
    
    
    public static void main(String[] args){
        if (args.length != 1){
            throw new IllegalArgumentException("Please give us an epoch time stamp to start the test");
        }
        SimpleStress stress = new SimpleStress(Long.parseLong(args[0]));
    }

}
