package com.inthinc.device.emulation;

import com.inthinc.device.devices.TiwiProDevice;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.device.objects.AutomationDeviceEvents;
import com.inthinc.pro.automation.enums.AutoSilos;
import com.inthinc.pro.automation.objects.AutomationCalendar;

public class CaptainNemoTrip extends Thread{
    private TiwiProDevice tiwi;
    
    private String IMEI;
    private AutoSilos server;
    private AutomationCalendar initialTime;
    
    


    public void start(String IMEI, AutoSilos server, AutomationCalendar initialTime) {
        this.IMEI=IMEI;
        this.server=server;
        this.initialTime = initialTime;
        super.start();
        
    }
    
    public void captainNemosFirstTrip(String IMEI, AutoSilos server, AutomationCalendar initialTime) {
        this.IMEI=IMEI;
        this.server=server;
        this.initialTime = initialTime;
        captainNemosFirstTrip();
    }

    public void captainNemosFirstTrip() {
        tiwi = new TiwiProDevice(IMEI, server);

        tiwi.set_time( initialTime.addToSeconds(60));
        tiwi.getState().setWMP(17116);
        tiwi.firstLocation(new GeoPoint(0, 0));
        tiwi.power_on_device();
        tiwi.turn_key_on(15);
        tiwi.update_location(new GeoPoint(0, 0), 180);
        tiwi.update_location(new GeoPoint(0, 0.05), 180);
        tiwi.update_location(new GeoPoint(0, 0.1), 180);
        tiwi.update_location(new GeoPoint(0.05, 0.1), 180);
        tiwi.update_location(new GeoPoint(0.1, 0.1), 180);
        tiwi.update_location(new GeoPoint(0.1, 0.05), 180);
        tiwi.update_location(new GeoPoint(0.1, 0), 180);
        tiwi.update_location(new GeoPoint(0.1, -0.05), 180);
        tiwi.update_location(new GeoPoint(0.1, -0.1), 180);
        tiwi.update_location(new GeoPoint(0.05, -0.1), 180);
        tiwi.update_location(new GeoPoint(0, -0.1), 180);
        tiwi.update_location(new GeoPoint(-0.05, -0.1), 180);
        tiwi.update_location(new GeoPoint(-0.1, -0.1), 180);
        tiwi.update_location(new GeoPoint(-0.1, -0.05), 180);
        tiwi.update_location(new GeoPoint(-0.1, 0), 180);
        tiwi.update_location(new GeoPoint(-0.1, 0.05), 180);
        tiwi.update_location(new GeoPoint(-0.1, 0.1), 180);
        tiwi.update_location(new GeoPoint(-0.05, 0.1), 180);
        tiwi.update_location(new GeoPoint(0, 0.1), 180);
        tiwi.update_location(new GeoPoint(0, 0.05), 180);
        tiwi.update_location(new GeoPoint(0, 0), 180);
        tiwi.last_location(new GeoPoint(0, 0), 180);
        
        AutomationDeviceEvents.statistics(tiwi);
        tiwi.turn_key_off(30);
        tiwi.power_off_device(900);
    }
    

    public void run() {
        captainNemosFirstTrip();
        
    }
    
    public static void main(String[] args){
        CaptainNemoTrip trip = new CaptainNemoTrip();
        AutomationCalendar initialTime = new AutomationCalendar();
        AutoSilos address;
        String imei;
        imei = "999999000109751"; address=AutoSilos.QA;//         initialTime = initialTime; // NO VEHICLE            NO DEVICE
//        imei = "javadeviceindavidsaccount"; address=AutoSilos.QA;   initialTime = 1307479805;  // vehicleID=37706       deviceID=34506
//        imei = "444444444444444";   address=AutoSilos.QA;           initialTime = 1307309503;  // vehicleID=7293        deviceID=3753
//        imei = "111111111111111";   address=AutoSilos.PROD;         initialTime = 1307310972;  // vehicleID=1           deviceID=1
//        imei = "thisisajavadevice"; address=AutoSilos.CHEVRON;      initialTime = 1307491773;  // vehicleID=117441441   deviceID=117441936 
//        imei = "999456789012345";   address=AutoSilos.SCHLUMBERGER; initialTime = 1307310972;  // vehicleID=150994955   deviceID=150994955
//        imei = "FAKEIMEIFORTINA";   address=AutoSilos.WEATHORFORD;  initialTime = 1307315379;  // vehicleID=184549575   deviceID=184549735
//        imei = "011596000100366";     address=AutoSilos.TEEN_PROD;
        
        
        trip.captainNemosFirstTrip( imei, address, initialTime);
    }


}
