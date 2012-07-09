package com.inthinc.pro.selenium.testSuites;

import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.device.devices.TiwiProDevice;
import com.inthinc.device.emulation.utils.DeviceState;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.device.emulation.utils.GeoPoint.Heading;
import com.inthinc.device.objects.AutomationDeviceEvents;
import com.inthinc.device.objects.TripDriver;
import com.inthinc.pro.automation.enums.ProductType;
import com.inthinc.pro.automation.objects.AutomationCalendar;
import com.inthinc.pro.automation.utils.AutoServers;

@Ignore
public class NoteTesterGeneration extends Thread{
    private TiwiProDevice tiwi1;
    private TiwiProDevice tiwi2;
    private TiwiProDevice tiwi3;
    private TiwiProDevice tiwi4;
    
    private String IMEI1;
    private String IMEI2;
    private String IMEI3;
    private String IMEI4;
    private AutomationCalendar initialTime;
    
    


    public void start(String IMEI1, String IMEI2, String IMEI3, String IMEI4, AutomationCalendar initialTime) {
        this.IMEI1=IMEI1;
        this.IMEI2=IMEI2;
        this.IMEI3=IMEI3;
        this.IMEI4=IMEI4;
        this.initialTime = initialTime;
        super.start();
        
    }
    
    public void noteTesterFirstGeneration(String IMEI1, String IMEI2, String IMEI3, String IMEI4, AutomationCalendar initialTime) {
        this.IMEI1=IMEI1;
        this.IMEI2=IMEI2;
        this.IMEI3=IMEI3;
        this.IMEI4=IMEI4;
        this.initialTime = initialTime;
        noteTesterFirstGeneration();
    }

    public void noteTesterFirstGeneration() {
        tiwi1 = new TiwiProDevice(IMEI1);
        tiwi2 = new TiwiProDevice(IMEI2);
        tiwi3 = new TiwiProDevice(IMEI3);
        tiwi4 = new TiwiProDevice(IMEI4);
        
        
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
        String imei1; String imei2; String imei3; String imei4;
        imei1 = "999999000109741";
        imei2 = "999999000109742";
        imei3 = "999999000109743";
        imei4 = "999999000109744";
        AutoServers server = new AutoServers();
        System.out.println("NoteTesterGeneration ran on address:"+server.getMcmUrl()+"; address.webAddress:"+server.getWebAddress());
        
        trip.noteTesterFirstGeneration( imei1, imei2, imei3, imei4, initialTime);
    }
    

    @Test
    public void idlingTestTrip() {
    	TiwiProDevice tiwi;
    	//String imei = "FAKEIMEIDEVICE2";
    	String imei = "FAKEIMEIDEVICE";
        tiwi = new TiwiProDevice(imei);
        tiwi.set_time(new AutomationCalendar());
        
        String start = "980 N 1050 E, Pleasant Grove, UT 84062";
        //String start = "950 Laird Ave, Salt Lake City, UT 84105";
        String mid = "815 N 1020 E, Pleasant Grove, UT 84062";
        String mid2 = "1003 E 1000 N, Pleasant Grove, UT 84062";
        String stop = "1175 North 730 East, Pleasant Grove, UT 84062";
       
        TripDriver driver = new TripDriver(tiwi);
        
        driver.addToTrip(start, mid);
        driver.addToTrip(mid, mid2);
        driver.addToTrip(mid2, stop);

        DeviceState state = new DeviceState(null, ProductType.TIWIPRO_R74);

//        state.setTopSpeed(80).setSpeedingDistanceX100(200).setAvgSpeed(75).setSpeedingSpeedLimit(40);
//        driver.addEvent(29, AutomationDeviceEvents.speeding(state, null));
        
        tiwi.getState().setLowIdle(100).setHighIdle(200);
        AutomationDeviceEvents.idling(tiwi);
        driver.addEvent(1, AutomationDeviceEvents.idling(state, null));
        
        tiwi.getState().setLowIdle(330).setHighIdle(130);
        driver.addEvent(10, AutomationDeviceEvents.idling(state, new GeoPoint()));
        
        tiwi.getState().setLowIdle(44).setHighIdle(14);
        driver.addEvent(20, AutomationDeviceEvents.idling(state, null));
        
        tiwi.getState().setLowIdle(55).setHighIdle(15);
        driver.addEvent(33, AutomationDeviceEvents.idling(state, null));
        
        tiwi.getState().setLowIdle(100).setHighIdle(200);
        AutomationDeviceEvents.idling(tiwi);
        driver.addEvent(44, AutomationDeviceEvents.idling(state, null));
        
        tiwi.getState().setLowIdle(330).setHighIdle(130);
        driver.addEvent(55, AutomationDeviceEvents.idling(state, new GeoPoint()));
        
        tiwi.getState().setLowIdle(44).setHighIdle(14);
        driver.addEvent(66, AutomationDeviceEvents.idling(state, null));
        
        tiwi.getState().setLowIdle(55).setHighIdle(15);
        driver.addEvent(77, AutomationDeviceEvents.idling(state, null));
        
        tiwi.getState().setLowIdle(100).setHighIdle(200);
        AutomationDeviceEvents.idling(tiwi);
        driver.addEvent(88, AutomationDeviceEvents.idling(state, null));
        
        tiwi.getState().setLowIdle(330).setHighIdle(130);
        driver.addEvent(99, AutomationDeviceEvents.idling(state, new GeoPoint()));

        driver.run();
        

    }

}
