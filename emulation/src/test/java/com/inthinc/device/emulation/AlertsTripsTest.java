package com.inthinc.device.emulation;

import com.inthinc.device.devices.TiwiProDevice;
import com.inthinc.device.emulation.utils.DeviceState;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.device.objects.AutomationDeviceEvents;
import com.inthinc.pro.automation.enums.AutoSilos;

public class AlertsTripsTest {
	
	
	public static void main(String args[]) {

        TiwiProDevice tiwi01 = new TiwiProDevice("120275615159901", AutoSilos.DEV);  //ALERTSDEVICE1
        TiwiProDevice tiwi02 = new TiwiProDevice("120275615159902", AutoSilos.DEV);  //ALERTSDEVICE2
        TiwiProDevice tiwi03 = new TiwiProDevice("120275615159903", AutoSilos.DEV);  //ALERTSDEVICE3
        TiwiProDevice tiwi04 = new TiwiProDevice("120275615159904", AutoSilos.DEV);  //ALERTSDEVICE4
        TiwiProDevice tiwi05 = new TiwiProDevice("120275615159905", AutoSilos.DEV);  //ALERTSDEVICE5
        TiwiProDevice tiwi06 = new TiwiProDevice("120275615159906", AutoSilos.DEV);  //ALERTSDEVICE6
        TiwiProDevice tiwi07 = new TiwiProDevice("120275615159907", AutoSilos.DEV);  //ALERTSDEVICE7
        TiwiProDevice tiwi08 = new TiwiProDevice("120275615159908", AutoSilos.DEV);  //ALERTSDEVICE8
        TiwiProDevice tiwi09 = new TiwiProDevice("120275615159909", AutoSilos.DEV);  //ALERTSDEVICE9
        TiwiProDevice tiwi10 = new TiwiProDevice("120275615159910", AutoSilos.DEV);  //ALERTSDEVICE10
        TiwiProDevice tiwi11 = new TiwiProDevice("120275615159911", AutoSilos.DEV);  //ALERTSDEVICE11
        TiwiProDevice tiwi12 = new TiwiProDevice("120275615159912", AutoSilos.DEV);  //ALERTSDEVICE12
        
        NewNoteTest noteTest = new NewNoteTest(AutoSilos.DEV);
        DeviceState state = tiwi01.getState();
        
        tiwi01.increment_time(-60);
        tiwi01.getState().setWMP(17116);
        tiwi01.firstLocation(new GeoPoint(33.0104, -117.111));
        tiwi01.power_on_device();
        AutomationDeviceEvents.install(tiwi01);
        tiwi01.turn_key_on(15);
        ;
        
        
//        tiwi01.getState().setLowIdle(300).setHighIdle(300);
        //AutomationDeviceEvents.idling(tiwi01);
//        tiwi01.getState().setSpeedLimit(5);
        
        //tiwi01.increment_time(20);
        //AutomationDeviceEvents.seatbelt(tiwi01);
//        tiwi01.update_location(new GeoPoint(33.0104, -117.111), 15);
//        tiwi01.update_location(new GeoPoint(33.0104, -117.113), 15);
//        AutomationDeviceEvents.panic(tiwi01);
        
        //state.setSeatbeltViolationDistanceX100(500);
        
//        tiwi01.increment_time(-20);
//        AutomationDeviceEvents.seatbelt(tiwi01);
//        AutomationDeviceEvents.hardLeft(tiwi01, 105);
//
//        tiwi01.update_location(new GeoPoint(33.01, -117.113), 15);
//        tiwi01.update_location(new GeoPoint(33.0097, -117.1153), 15);
//        tiwi01.update_location(new GeoPoint(33.015, -117.116), 15);
//        
//        tiwi01.increment_time(-20);
        //AutomationDeviceEvents.seatbelt(tiwi01);
//        tiwi01.getState().setSpeedLimit(40);
//        tiwi01.enter_zone(1062);
//        
//        tiwi01.update_location(new GeoPoint(33.0163, -117.1159), 15);
//        tiwi01.update_location(new GeoPoint(33.018, -117.1153), 15);
//        tiwi01.update_location(new GeoPoint(33.0188, -117.118), 15);
//        tiwi01.update_location(new GeoPoint(33.0192, -117.1199), 15);
        
        //AutomationDeviceEvents.rfKill(tiwi01);
        
//        tiwi01.update_location(new GeoPoint(33.021, -117.119), 15);
//        tiwi01.update_location(new GeoPoint(33.022, -117.114), 15);
//        tiwi01.update_location(new GeoPoint(33.0205, -117.111), 15);
//        
//        tiwi01.increment_time(-20);
        //AutomationDeviceEvents.seatbelt(tiwi01);
        //tiwi01.tampering(4);
        
        //AutomationDeviceEvents.powerInterruption(tiwi01);
        
        //noteTest.testSeatbeltClicks("", "999999000582802");

        //AutomationDeviceEvents.seatbeltClick(tiwi01);
        
//        tiwi01.update_location(new GeoPoint(33.02, -117.109), 15);
//        tiwi01.update_location(new GeoPoint(33.02, -117.108), 15);
//        tiwi01.update_location(new GeoPoint(33.022, -117.104), 15);
//        tiwi01.update_location(new GeoPoint(33.0217, -117.103), 15);
//        tiwi01.update_location(new GeoPoint(33.0213, -117.1015), 15);
//        tiwi01.update_location(new GeoPoint(33.0185, -117.1019), 15);
//        tiwi01.update_location(new GeoPoint(33.017, -117.102), 15);
//        tiwi01.update_location(new GeoPoint(33.015, -117.1032), 15);
//        tiwi01.update_location(new GeoPoint(33.013, -117.105), 15);
//        tiwi01.update_location(new GeoPoint(33.011, -117.106), 15);
//        tiwi01.update_location(new GeoPoint(33.0108, -117.108), 15);
//        tiwi01.update_location(new GeoPoint(33.0108, -117.109), 15);
//
//        tiwi01.leave_zone(1062);
//
//        tiwi01.increment_time(-20);
        
        //AutomationDeviceEvents.seatbelt(tiwi01);
//        tiwi01.update_location(new GeoPoint(33.0106, -117.11), 15);
//        tiwi01.last_location(new GeoPoint(33.0104, -117.111), 15);
        
//        AutomationDeviceEvents.statistics(tiwi01);
        //tiwi01.logout_driver(null, 890, 204, 200);
        
        //tiwi01.setEmployeeID("DASTARDLY1");										 //THESE THREE LINES ARE FOR SENDING IN HOS
        //state.setHosState(HOSState.OCCUPANT_ON_DUTY);							 //DRIVER ON DUTY AS AN OCCUPANT IN A 
        //AutomationDeviceEvents.hosChangeNoGPSLock(tiwi01, "SALT LAKE CITY, UT");	 //VEHICLE
        //note.addAttr(EventAttr.SEATBELT_TOP_SPEED, state.getSeatbeltTopSpeed());
    	//note.addAttr(EventAttr.SEATBELT_OUT_DISTANCE, state.getSeatbeltDistanceX100());
        
    	//tiwi01.getState().setSeatBeltTopSpeed(75).setSeatbeltDistanceX100(58);
    	
        tiwi01.turn_key_off(30);
        //AutomationDeviceEvents.lowBattery(tiwi01);
        tiwi01.power_off_device(900);
    }
}
