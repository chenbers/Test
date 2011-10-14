package com.inthinc.pro.automation.deviceTrips;

import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.objects.TiwiProDevice;

public class MannyBikeRide extends Thread{
    private TiwiProDevice tiwi;
    
    private String IMEI;
    private Addresses server;
    private Integer initialTime;
    
    


    public void start(String IMEI, Addresses server, Integer initialTime) {
        this.IMEI=IMEI;
        this.server=server;
        this.initialTime = initialTime;
        super.start();
        
    }
    
    public void mannysFirstBikeRide(String IMEI, Addresses server, Integer initialTime) {
        this.IMEI=IMEI;
        this.server=server;
        this.initialTime = initialTime;
        mannysFirstBikeRide();
    }

    public void mannysFirstBikeRide() {
        tiwi = new TiwiProDevice(IMEI, server);

        tiwi.set_time( initialTime +60);
        tiwi.set_WMP(17116);
        tiwi.set_location(40.7097, -111.9925);
        tiwi.power_on_device();
        tiwi.turn_key_on(15);
        tiwi.update_location(40.7097, -111.9925, 15);
        tiwi.update_location(40.7097, -111.9927, 15);
        tiwi.update_location(40.7097, -111.9929, 15);
        tiwi.update_location(40.7097, -111.9931, 15);
        tiwi.update_location(40.7099, -111.9931, 15);
        tiwi.update_location(40.7099, -111.9929, 15);
        tiwi.update_location(40.7099, -111.9927, 15);
        tiwi.update_location(40.7099, -111.9925, 15);
        tiwi.update_location(40.7097, -111.9925, 15);
        tiwi.last_location(40.7097, -111.9925, 15);
        
        tiwi.add_stats();
        tiwi.turn_key_off(30);
        tiwi.power_off_device(900);
    }
    

    public void run() {
        mannysFirstBikeRide();
        
    }
    
    public static void main(String[] args){
        MannyBikeRide trip = new MannyBikeRide();
        Long currentTime = System.currentTimeMillis()/1000;
        Integer initialTime = currentTime.intValue();
        Addresses address;
        String imei;
        imei = "999999000109750"; address=Addresses.QA;//         initialTime = initialTime; // NO VEHICLE            NO DEVICE
//        imei = "javadeviceindavidsaccount"; address=Addresses.QA;   initialTime = 1307479805;  // vehicleID=37706       deviceID=34506
//        imei = "444444444444444";   address=Addresses.QA;           initialTime = 1307309503;  // vehicleID=7293        deviceID=3753
//        imei = "111111111111111";   address=Addresses.PROD;         initialTime = 1307310972;  // vehicleID=1           deviceID=1
//        imei = "thisisajavadevice"; address=Addresses.CHEVRON;      initialTime = 1307491773;  // vehicleID=117441441   deviceID=117441936 
//        imei = "999456789012345";   address=Addresses.SCHLUMBERGER; initialTime = 1307310972;  // vehicleID=150994955   deviceID=150994955
//        imei = "FAKEIMEIFORTINA";   address=Addresses.WEATHORFORD;  initialTime = 1307315379;  // vehicleID=184549575   deviceID=184549735
//        imei = "011596000100366";     address=Addresses.TEEN_PROD;
        
        
        trip.mannysFirstBikeRide( imei, address, initialTime);
    }


}
