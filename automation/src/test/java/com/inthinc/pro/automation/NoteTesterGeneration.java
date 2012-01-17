package com.inthinc.pro.automation;

import com.inthinc.device.devices.TiwiProDevice;
import com.inthinc.device.emulation.enums.Addresses;
import com.inthinc.device.emulation.utils.AutomationCalendar;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.device.emulation.utils.GeoPoint.Heading;
import com.inthinc.device.objects.AutomationDeviceEvents;
import com.inthinc.pro.automation.selenium.AutomationProperties;

public class NoteTesterGeneration extends Thread{
    private TiwiProDevice tiwi1;
    private TiwiProDevice tiwi2;
    private TiwiProDevice tiwi3;
    private TiwiProDevice tiwi4;
    
    private String IMEI1;
    private String IMEI2;
    private String IMEI3;
    private String IMEI4;
    private Addresses server;
    private AutomationCalendar initialTime;
    
    


    public void start(String IMEI1, String IMEI2, String IMEI3, String IMEI4, Addresses server, AutomationCalendar initialTime) {
        this.IMEI1=IMEI1;
        this.IMEI2=IMEI2;
        this.IMEI3=IMEI3;
        this.IMEI4=IMEI4;
        this.server=server;
        this.initialTime = initialTime;
        super.start();
        
    }
    
    public void noteTesterFirstGeneration(String IMEI1, String IMEI2, String IMEI3, String IMEI4, Addresses server, AutomationCalendar initialTime) {
        this.IMEI1=IMEI1;
        this.IMEI2=IMEI2;
        this.IMEI3=IMEI3;
        this.IMEI4=IMEI4;
        this.server=server;
        this.initialTime = initialTime;
        noteTesterFirstGeneration();
    }

    public void noteTesterFirstGeneration() {
        tiwi1 = new TiwiProDevice(IMEI1, server);
        tiwi2 = new TiwiProDevice(IMEI2, server);
        tiwi3 = new TiwiProDevice(IMEI3, server);
        tiwi4 = new TiwiProDevice(IMEI4, server);
        
        
        TiwiProDevice[] tiwiArray = new TiwiProDevice[4];
        
        tiwiArray[0] = tiwi1;
        tiwiArray[1] = tiwi2;
        tiwiArray[2] = tiwi3;
        tiwiArray[3] = tiwi4;
        
        for(int i=0; i<4; i++){
            tiwiArray[i].set_time( initialTime.addToSeconds(60));
            tiwiArray[i].getTripTracker().addLocation(new GeoPoint(0.0, -10.0));
            tiwiArray[i].power_on_device();
            tiwiArray[i].turn_key_on(15);
        }
        
        tiwi1.nonTripNote( initialTime.addToSeconds(60), 1, Heading.NORTH_EAST, new GeoPoint(10.0, 0.0), 60, 100);
        tiwi2.nonTripNote( initialTime.addToSeconds(60), 1, Heading.NORTH_EAST, new GeoPoint(0.0, 10.0), 60, 100);
        tiwi3.nonTripNote( initialTime.addToSeconds(60), 1, Heading.NORTH_EAST, new GeoPoint(-10.0, 0.0), 60, 100);
        tiwi4.nonTripNote( initialTime.addToSeconds(60), 1, Heading.NORTH_EAST, new GeoPoint(0.0, -10.0), 60, 100);

        
        //Driving Style notes.
        AutomationDeviceEvents.hardLeft(tiwi1, 100);
        AutomationDeviceEvents.hardBrake(tiwi2, 37);
        AutomationDeviceEvents.hardBump(tiwi3, 1000);
        AutomationDeviceEvents.hardAccel(tiwi4, 750);
        
        //Speeding notes.
        tiwi1.getState().setTopSpeed(70).setSpeedingDistanceX100(100).setSpeedingSpeedLimit(65);
        AutomationDeviceEvents.speeding(tiwi1);
        tiwi1.getState().setTopSpeed(100).setSpeedingDistanceX100(6000).setSpeedingSpeedLimit(65);
        AutomationDeviceEvents.speeding(tiwi1);
        
        //Seat Belt notes.
        tiwi1.getState().setTopSpeed(60).setAvgSpeed(40).setSeatbeltDistanceX100(100);
        AutomationDeviceEvents.seatbelt(tiwi1);
        tiwi3.getState().setTopSpeed(50).setAvgSpeed(45).setSeatbeltDistanceX100(50);
        AutomationDeviceEvents.seatbelt(tiwi3);
        
        //TODO Send Crash notes (USER STORY'D!).
        
        //Tampering notes.
        tiwi4.tampering(15);
        tiwi2.tampering(15);
        tiwi2.getState().setLowIdle(10).setHighIdle(20);
        AutomationDeviceEvents.idling(tiwi2);
        
        tiwi3.getState().setLowIdle(23).setHighIdle(19);
        AutomationDeviceEvents.idling(tiwi3);

        //Zone notes.
        //Pac Man is 915, Kazakhstan is 916, Tasmania is 917.
        tiwi2.enter_zone(915);
        tiwi3.enter_zone(916);
        tiwi4.enter_zone(917);
        tiwi2.leave_zone(917);
        tiwi3.leave_zone(915);
        tiwi4.leave_zone(916);
        
        tiwi1.update_location(new GeoPoint(10, 0), 15);
        tiwi2.update_location(new GeoPoint(0, 10), 15);
        tiwi3.update_location(new GeoPoint(-10, 0), 15);
        tiwi4.update_location(new GeoPoint(0, -10), 15);
        
        for(int i=0; i<4; i++){
        	AutomationDeviceEvents.statistics(tiwiArray[i]);
            tiwiArray[i].turn_key_off(30);
            tiwiArray[i].power_off_device(900);
        }
    }
    
    public static void main(String[] args){
        NoteTesterGeneration trip = new NoteTesterGeneration();
        AutomationCalendar initialTime = new AutomationCalendar();
        Addresses address;
        String imei1; String imei2; String imei3; String imei4;
        imei1 = "999999000109741";
        imei2 = "999999000109742";
        imei3 = "999999000109743";
        imei4 = "999999000109744";
        address = Addresses.getSilo(AutomationProperties.getPropertyBean().getSilo()); 
        System.out.println("NoteTesterGeneration ran on address:"+address+"; address.webAddress:"+address.getWebAddress());
        
        trip.noteTesterFirstGeneration( imei1, imei2, imei3, imei4, address, initialTime);
    }


}
