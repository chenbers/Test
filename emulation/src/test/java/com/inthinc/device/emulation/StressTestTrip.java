package com.inthinc.device.emulation;

import com.inthinc.device.devices.TiwiProDevice;
import com.inthinc.device.emulation.utils.DeviceState;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.device.objects.AutomationDeviceEvents;
import com.inthinc.pro.automation.enums.AutoSilos;

public class StressTestTrip {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
        TiwiProDevice tiwi = new TiwiProDevice("000000000000001", AutoSilos.DEV);
        TiwiProDevice tiwi2 = new TiwiProDevice("000000000000114", AutoSilos.DEV);
        TiwiProDevice tiwi3 = new TiwiProDevice("000000000000124", AutoSilos.DEV);
		
		//WaysmartDevice tiwi = new WaysmartDevice("300235555777777", "MCM013795", AutoSilos.PROD, Direction.gprs);
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
    	
    	AutomationDeviceEvents.noDriver(tiwi);
    	
        tiwi.turn_key_off(30);
        AutomationDeviceEvents.lowBattery(tiwi);
        tiwi.power_off_device(900);
        
        /* TIWI2 trip */
    
		//WaysmartDevice tiwi = new WaysmartDevice("300235555777777", "MCM013795", AutoSilos.PROD, Direction.gprs);
        DeviceState state2 = tiwi2.getState();
        tiwi2.increment_time(60);
        tiwi2.getState().setWMP(17116);
        tiwi2.firstLocation(new GeoPoint(33.0104, -117.111));
        tiwi2.power_on_device();
        tiwi2.turn_key_on(15);
        tiwi2.getState().setLowIdle(300).setHighIdle(300);
        //AutomationDeviceEvents.idling(tiwi2);
        tiwi2.getState().setSpeedLimit(5);
        
        //tiwi2.increment_time(20);
        //AutomationDeviceEvents.seatbelt(tiwi2);
        tiwi2.update_location(new GeoPoint(33.0104, -117.111), 15);
        tiwi2.update_location(new GeoPoint(33.0104, -117.113), 15);
        
        state2.setSeatbeltViolationDistanceX100(500);
        
        tiwi2.increment_time(20);
        //AutomationDeviceEvents.seatbelt(tiwi2);
        AutomationDeviceEvents.hardLeft(tiwi2, 105);

        tiwi2.update_location(new GeoPoint(33.01, -117.113), 15);
        tiwi2.update_location(new GeoPoint(33.0097, -117.1153), 15);
        tiwi2.update_location(new GeoPoint(33.015, -117.116), 15);
        
        tiwi2.increment_time(20);
        //AutomationDeviceEvents.seatbelt(tiwi2);
        tiwi2.getState().setSpeedLimit(40);
        //tiwi2.enter_zone(2);
        
        tiwi2.update_location(new GeoPoint(33.0163, -117.1159), 15);
        tiwi2.update_location(new GeoPoint(33.018, -117.1153), 15);
        tiwi2.update_location(new GeoPoint(33.0188, -117.118), 15);
        tiwi2.update_location(new GeoPoint(33.0192, -117.1199), 15);
        
        AutomationDeviceEvents.rfKill(tiwi2);
        
        tiwi2.update_location(new GeoPoint(33.021, -117.119), 15);
        tiwi2.update_location(new GeoPoint(33.022, -117.114), 15);
        tiwi2.update_location(new GeoPoint(33.0205, -117.111), 15);
        
        tiwi2.increment_time(20);
        //AutomationDeviceEvents.seatbelt(tiwi2);
        //tiwi2.tampering(4);
        
        tiwi2.update_location(new GeoPoint(33.02, -117.109), 15);
        tiwi2.update_location(new GeoPoint(33.02, -117.108), 15);
        tiwi2.update_location(new GeoPoint(33.022, -117.104), 15);
        tiwi2.update_location(new GeoPoint(33.0217, -117.103), 15);
        tiwi2.update_location(new GeoPoint(33.0213, -117.1015), 15);
        tiwi2.update_location(new GeoPoint(33.0185, -117.1019), 15);
        tiwi2.update_location(new GeoPoint(33.017, -117.102), 15);
        tiwi2.update_location(new GeoPoint(33.015, -117.1032), 15);
        tiwi2.update_location(new GeoPoint(33.013, -117.105), 15);
        tiwi2.update_location(new GeoPoint(33.011, -117.106), 15);
        tiwi2.update_location(new GeoPoint(33.0108, -117.108), 15);
        tiwi2.update_location(new GeoPoint(33.0108, -117.109), 15);

        //tiwi2.leave_zone(2);

        tiwi2.increment_time(20);
        //AutomationDeviceEvents.seatbelt(tiwi2);
        tiwi2.update_location(new GeoPoint(33.0106, -117.11), 15);
        tiwi2.last_location(new GeoPoint(33.0104, -117.111), 15);
        
        AutomationDeviceEvents.statistics(tiwi2);
        //tiwi2.logout_driver(null, 890, 204, 200);
        
        
        
        //note.addAttr(EventAttr.SEATBELT_TOP_SPEED, state.getSeatbeltTopSpeed());
    	//note.addAttr(EventAttr.SEATBELT_OUT_DISTANCE, state.getSeatbeltDistanceX100());
        
    	tiwi2.getState().setSeatBeltTopSpeed(75).setSeatbeltDistanceX100(58);
    	
    	AutomationDeviceEvents.noDriver(tiwi2);
    	
        tiwi2.turn_key_off(30);
        AutomationDeviceEvents.lowBattery(tiwi2);
        tiwi2.power_off_device(900);
        
        /* TIWI3 trip */
        
		//WaysmartDevice tiwi = new WaysmartDevice("300235555777777", "MCM013795", AutoSilos.PROD, Direction.gprs);
        DeviceState state3 = tiwi3.getState();
        tiwi3.increment_time(60);
        tiwi3.getState().setWMP(17116);
        tiwi3.firstLocation(new GeoPoint(33.0104, -117.111));
        tiwi3.power_on_device();
        tiwi3.turn_key_on(15);
        tiwi3.getState().setLowIdle(300).setHighIdle(300);
        //AutomationDeviceEvents.idling(tiwi3);
        tiwi3.getState().setSpeedLimit(5);
        
        //tiwi3.increment_time(20);
        //AutomationDeviceEvents.seatbelt(tiwi3);
        tiwi3.update_location(new GeoPoint(33.0104, -117.111), 15);
        tiwi3.update_location(new GeoPoint(33.0104, -117.113), 15);
        
        state3.setSeatbeltViolationDistanceX100(500);
        
        tiwi3.increment_time(20);
        //AutomationDeviceEvents.seatbelt(tiwi3);
        AutomationDeviceEvents.hardLeft(tiwi3, 105);

        tiwi3.update_location(new GeoPoint(33.01, -117.113), 15);
        tiwi3.update_location(new GeoPoint(33.0097, -117.1153), 15);
        tiwi3.update_location(new GeoPoint(33.015, -117.116), 15);
        
        tiwi3.increment_time(20);
        //AutomationDeviceEvents.seatbelt(tiwi3);
        tiwi3.getState().setSpeedLimit(40);
        //tiwi3.enter_zone(2);
        
        tiwi3.update_location(new GeoPoint(33.0163, -117.1159), 15);
        tiwi3.update_location(new GeoPoint(33.018, -117.1153), 15);
        tiwi3.update_location(new GeoPoint(33.0188, -117.118), 15);
        tiwi3.update_location(new GeoPoint(33.0192, -117.1199), 15);
        
        AutomationDeviceEvents.rfKill(tiwi3);
        
        tiwi3.update_location(new GeoPoint(33.021, -117.119), 15);
        tiwi3.update_location(new GeoPoint(33.022, -117.114), 15);
        tiwi3.update_location(new GeoPoint(33.0205, -117.111), 15);
        
        tiwi3.increment_time(20);
        //AutomationDeviceEvents.seatbelt(tiwi3);
        //tiwi3.tampering(4);
        
        tiwi3.update_location(new GeoPoint(33.02, -117.109), 15);
        tiwi3.update_location(new GeoPoint(33.02, -117.108), 15);
        tiwi3.update_location(new GeoPoint(33.022, -117.104), 15);
        tiwi3.update_location(new GeoPoint(33.0217, -117.103), 15);
        tiwi3.update_location(new GeoPoint(33.0213, -117.1015), 15);
        tiwi3.update_location(new GeoPoint(33.0185, -117.1019), 15);
        tiwi3.update_location(new GeoPoint(33.017, -117.102), 15);
        tiwi3.update_location(new GeoPoint(33.015, -117.1032), 15);
        tiwi3.update_location(new GeoPoint(33.013, -117.105), 15);
        tiwi3.update_location(new GeoPoint(33.011, -117.106), 15);
        tiwi3.update_location(new GeoPoint(33.0108, -117.108), 15);
        tiwi3.update_location(new GeoPoint(33.0108, -117.109), 15);

        //tiwi3.leave_zone(2);

        tiwi3.increment_time(20);
        //AutomationDeviceEvents.seatbelt(tiwi3);
        tiwi3.update_location(new GeoPoint(33.0106, -117.11), 15);
        tiwi3.last_location(new GeoPoint(33.0104, -117.111), 15);
        
        AutomationDeviceEvents.statistics(tiwi3);
        //tiwi3.logout_driver(null, 890, 204, 200);
        
        
        
        //note.addAttr(EventAttr.SEATBELT_TOP_SPEED, state.getSeatbeltTopSpeed());
    	//note.addAttr(EventAttr.SEATBELT_OUT_DISTANCE, state.getSeatbeltDistanceX100());
        
    	tiwi3.getState().setSeatBeltTopSpeed(75).setSeatbeltDistanceX100(58);
    	
    	AutomationDeviceEvents.noDriver(tiwi3);
    	
        tiwi3.turn_key_off(30);
        AutomationDeviceEvents.lowBattery(tiwi3);
        tiwi3.power_off_device(900);

	}

}
