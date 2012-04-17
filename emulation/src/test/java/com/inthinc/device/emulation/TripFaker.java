package com.inthinc.device.emulation;

import com.inthinc.device.devices.TiwiProDevice;
import com.inthinc.device.devices.WaysmartDevice;
import com.inthinc.device.devices.WaysmartDevice.Direction;
import com.inthinc.device.emulation.enums.EventAttr;
import com.inthinc.device.emulation.utils.DeviceState;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.device.objects.AutomationDeviceEvents;
import com.inthinc.pro.automation.enums.Addresses;

public class TripFaker {
	
	
	public static void main(String args[]) {
		
        TiwiProDevice tiwi = new TiwiProDevice("011596000038897", Addresses.DEV);
		
		//WaysmartDevice tiwi = new WaysmartDevice("300235555777777", "MCM013795", Addresses.PROD, Direction.gprs);
        DeviceState state = tiwi.getState();
        tiwi.increment_time(60);
        tiwi.getState().setWMP(17116);
        tiwi.firstLocation(new GeoPoint(33.0104, -117.111));
        tiwi.power_on_device();
        tiwi.turn_key_on(15);
        tiwi.getState().setLowIdle(300).setHighIdle(300);
        //AutomationDeviceEvents.idling(tiwi);
        tiwi.getState().setSpeedLimit(5);
        
        //tiwi.increment_time(20);
        //AutomationDeviceEvents.seatbelt(tiwi);
        tiwi.update_location(new GeoPoint(33.0104, -117.111), 15);
        tiwi.update_location(new GeoPoint(33.0104, -117.113), 15);
        
        state.setSeatbeltViolationDistanceX100(500);
        
        tiwi.increment_time(20);
        //AutomationDeviceEvents.seatbelt(tiwi);
        AutomationDeviceEvents.hardLeft(tiwi, 105);

        tiwi.update_location(new GeoPoint(33.01, -117.113), 15);
        tiwi.update_location(new GeoPoint(33.0097, -117.1153), 15);
        tiwi.update_location(new GeoPoint(33.015, -117.116), 15);
        
        tiwi.increment_time(20);
        //AutomationDeviceEvents.seatbelt(tiwi);
        tiwi.getState().setSpeedLimit(40);
        //tiwi.enter_zone(2);
        
        tiwi.update_location(new GeoPoint(33.0163, -117.1159), 15);
        tiwi.update_location(new GeoPoint(33.018, -117.1153), 15);
        tiwi.update_location(new GeoPoint(33.0188, -117.118), 15);
        tiwi.update_location(new GeoPoint(33.0192, -117.1199), 15);
        
        AutomationDeviceEvents.rfKill(tiwi);
        
        tiwi.update_location(new GeoPoint(33.021, -117.119), 15);
        tiwi.update_location(new GeoPoint(33.022, -117.114), 15);
        tiwi.update_location(new GeoPoint(33.0205, -117.111), 15);
        
        tiwi.increment_time(20);
        //AutomationDeviceEvents.seatbelt(tiwi);
        //tiwi.tampering(4);
        
        tiwi.update_location(new GeoPoint(33.02, -117.109), 15);
        tiwi.update_location(new GeoPoint(33.02, -117.108), 15);
        tiwi.update_location(new GeoPoint(33.022, -117.104), 15);
        tiwi.update_location(new GeoPoint(33.0217, -117.103), 15);
        tiwi.update_location(new GeoPoint(33.0213, -117.1015), 15);
        tiwi.update_location(new GeoPoint(33.0185, -117.1019), 15);
        tiwi.update_location(new GeoPoint(33.017, -117.102), 15);
        tiwi.update_location(new GeoPoint(33.015, -117.1032), 15);
        tiwi.update_location(new GeoPoint(33.013, -117.105), 15);
        tiwi.update_location(new GeoPoint(33.011, -117.106), 15);
        tiwi.update_location(new GeoPoint(33.0108, -117.108), 15);
        tiwi.update_location(new GeoPoint(33.0108, -117.109), 15);

        //tiwi.leave_zone(2);

        tiwi.increment_time(20);
        //AutomationDeviceEvents.seatbelt(tiwi);
        tiwi.update_location(new GeoPoint(33.0106, -117.11), 15);
        tiwi.last_location(new GeoPoint(33.0104, -117.111), 15);
        
        AutomationDeviceEvents.statistics(tiwi);
        //tiwi.logout_driver(null, 890, 204, 200);
        
        
        
        //note.addAttr(EventAttr.SEATBELT_TOP_SPEED, state.getSeatbeltTopSpeed());
    	//note.addAttr(EventAttr.SEATBELT_OUT_DISTANCE, state.getSeatbeltDistanceX100());
        
    	tiwi.getState().setSeatBeltTopSpeed(75).setSeatbeltDistanceX100(58);
    	
        tiwi.turn_key_off(30);
        AutomationDeviceEvents.lowBattery(tiwi);
        tiwi.power_off_device(900);
    }
}
