package com.inthinc.device.emulation.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inthinc.device.devices.TiwiProDevice;
import com.inthinc.device.devices.WaysmartDevice;
import com.inthinc.device.devices.WaysmartDevice.Direction;
import com.inthinc.device.objects.TripDriver;
import com.inthinc.pro.automation.enums.AutoSilos;
import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.objects.AutomationCalendar;
import com.inthinc.pro.automation.resources.FileRW;
import com.inthinc.pro.automation.utils.AutomationThread;

public class SimpleStress {
    
    private final List<String> imeis;
    private final Map<String, String> ways;
    
    private static final AutoSilos address = AutoSilos.TP_INTHINC;
    
    private static final String start = "40.710459,-111.993245";
    private static final String stop = "35.446687,-112.003148";
    private final AutomationCalendar startTime;
    
    public SimpleStress(Integer startTime){
        this.startTime = new AutomationCalendar(startTime.longValue() * 1000);
        imeis = new ArrayList<String>();
        ways = new HashMap<String, String>();
        ways.put("30023FKEWS00001", "FKE00001");
        ways.put("30023FKEWS00002", "FKE00002");
        ways.put("30023FKEWS00003", "FKE00003");
        ways.put("30023FKEWS00004", "FKE00004");
        ways.put("30023FKEWS00005", "FKE00005");
        ways.put("30023FKEWS00006", "FKE00006");
        ways.put("30023FKEWS00007", "FKE00007");
        driveWaysmarts();
    }
      

    public SimpleStress(Long startTime){
        this.startTime = new AutomationCalendar(startTime * 1000);
        FileRW rw = new FileRW();
        imeis = rw.read("imeis-acct9.txt");
        driveTiwis();
        ways = new HashMap<String, String>();
    }
    
    private void driveTiwis(){
        GoogleTrips trip = new GoogleTrips();
        List<GeoPoint> points = trip.getTrip(start, stop);
        for (String imei : imeis){
            TiwiProDevice tiwi = new TiwiProDevice(imei, address);
            tiwi.set_server(address);
//            TiwiProDevice tiwi = new TiwiProDevice("DEVICEDOESNTEXIST", address);
            tiwi.getState().getTime().setDate(startTime);
            tiwi.getTripTracker().setPoints(points);
            TripDriver driver = new TripDriver(tiwi);
            driver.start();
            while (Thread.activeCount() > 20){
                AutomationThread.pause(500l);
            }
        }
    }
    
    

    private void driveWaysmarts() {
        GoogleTrips trip = new GoogleTrips();
        List<GeoPoint> points = trip.getTrip(start, stop);
        for (Map.Entry<String, String> entry : ways.entrySet()){
            WaysmartDevice ways = new WaysmartDevice(entry.getKey(), entry.getValue(), Direction.sat);
            ways.set_server(address);
            ways.getState().getTime().setDate(startTime);
            ways.getTripTracker().setPoints(points);
            new TripDriver(ways).start();

            while (Thread.activeCount() > 20){
                AutomationThread.pause(500l);
            }
        }
    }
    
    
    
    public static void main(String[] args){
//        if (args.length != 1){
//            throw new IllegalArgumentException("Please give us an epoch time stamp to start the test");
//        }
//        SimpleStress stress = new SimpleStress(Long.parseLong(args[0]));
        SimpleStress stress = new SimpleStress(1334534400);
//        SimpleStress stress = new SimpleStress(1132328400);
        Log.info(stress);
    }

}
