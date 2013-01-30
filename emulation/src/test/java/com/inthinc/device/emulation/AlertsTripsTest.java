package com.inthinc.device.emulation;

import com.inthinc.device.devices.TiwiProDevice;
import com.inthinc.device.devices.WaysmartDevice;
import com.inthinc.device.devices.WaysmartDevice.Direction;
import com.inthinc.device.emulation.utils.DeviceState;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.device.objects.AutomationDeviceEvents;
import com.inthinc.pro.automation.enums.AutoSilos;

public class AlertsTripsTest {
	
	
	public static void main(String args[]) {

        TiwiProDevice tiwi01 = new TiwiProDevice("120275615159901", AutoSilos.DEV);  //ALERTSDEVICE1
//        TiwiProDevice tiwi02 = new TiwiProDevice("120275615159902", AutoSilos.DEV);  //ALERTSDEVICE2
//        TiwiProDevice tiwi03 = new TiwiProDevice("120275615159903", AutoSilos.DEV);  //ALERTSDEVICE3
//        TiwiProDevice tiwi04 = new TiwiProDevice("120275615159904", AutoSilos.DEV);  //ALERTSDEVICE4
//        TiwiProDevice tiwi05 = new TiwiProDevice("120275615159905", AutoSilos.DEV);  //ALERTSDEVICE5
//        TiwiProDevice tiwi06 = new TiwiProDevice("120275615159906", AutoSilos.DEV);  //ALERTSDEVICE6
//        TiwiProDevice tiwi07 = new TiwiProDevice("120275615159907", AutoSilos.DEV);  //ALERTSDEVICE7
//        TiwiProDevice tiwi08 = new TiwiProDevice("120275615159908", AutoSilos.DEV);  //ALERTSDEVICE8
//        TiwiProDevice tiwi09 = new TiwiProDevice("120275615159909", AutoSilos.DEV);  //ALERTSDEVICE9
//        TiwiProDevice tiwi10 = new TiwiProDevice("120275615159910", AutoSilos.DEV);  //ALERTSDEVICE10
//        TiwiProDevice tiwi11 = new TiwiProDevice("120275615159911", AutoSilos.DEV);  //ALERTSDEVICE11
//        TiwiProDevice tiwi12 = new TiwiProDevice("120275615159912", AutoSilos.DEV);  //ALERTSDEVICE12
        WaysmartDevice ws01 = new WaysmartDevice("ALERTSWS0001", "ALERTSWS01", AutoSilos.DEV, Direction.gprs);

        //WAYSMART TRIP FOR DVIR ALERTS
        ws01.getState().setWMP(17116);
        ws01.firstLocation(new GeoPoint(33.0104, -117.111));
        ws01.power_on_device();
        ws01.turn_key_on(15);	
        AutomationDeviceEvents.dVIRPreTripFail(ws01);								//ALERT_TYPE_DVIR_PRE_TRIP_FAIL
        AutomationDeviceEvents.dVIRPreTripPass(ws01);								//ALERT_TYPE_DVIR_PRE_TRIP_PASS
        
        AutomationDeviceEvents.dVIRUnsafe(ws01);									//ALERT_TYPE_DVIR_DRIVEN_UNSAFE
        AutomationDeviceEvents.dVIRNoPreInspection(ws01);							//ALERT_TYPE_DVIR_DRIVEN_NOINSPECTION
        AutomationDeviceEvents.dVIRPostTripFail(ws01);								//ALERT_TYPE_DVIR_POST_TRIP_FAIL
        AutomationDeviceEvents.dVIRNoPostInspection(ws01);							//ALERT_TYPE_DVIR_DRIVEN_NOPOSTINSPEC
        AutomationDeviceEvents.dVIRPostTripPass(ws01);								//ALERT_TYPE_DVIR_POST_TRIP_PASS        
        ws01.turn_key_off(30);
        ws01.power_off_device(900);
        
        
        //TIWI TRIP FOR ALL OTHER ALERTS
        DeviceState state = tiwi01.getState();
        tiwi01.dump_settings();
        tiwi01.increment_time(60);
        tiwi01.getState().setWMP(17116);
        tiwi01.firstLocation(new GeoPoint(33.0104, -117.111));
        tiwi01.power_on_device();
        
        tiwi01.turn_key_on(15);														//ALERT_TYPE_IGNITION_ON
        AutomationDeviceEvents.install(tiwi01);										//ALERT_TYPE_INSTALL
        AutomationDeviceEvents.firmwareCurrent(tiwi01);								//ALERT_TYPE_FIRMWARE_CURRENT
        AutomationDeviceEvents.qSICurrent(tiwi01);									//ALERT_TYPE_QSI_UPDATED

        tiwi01.getState().setLowIdle(300).setHighIdle(300);							//ALERT_TYPE_IDLING
        AutomationDeviceEvents.idling(tiwi01);
        
        AutomationDeviceEvents.parkingBrake(tiwi01);								//ALERT_TYPE_PARKING_BRAKE
        
        tiwi01.increment_time(20);
        AutomationDeviceEvents.seatbelt(tiwi01);									//ALERT_TYPE_SEATBELT
        
        tiwi01.increment_time(20);
        AutomationDeviceEvents.hardBump(tiwi01, 90);								//ALERT_TYPE_HARD_BUMP
        tiwi01.increment_time(20);
        AutomationDeviceEvents.hardBrake(tiwi01, 200);								//ALERT_TYPE_HARD_BRAKE
        tiwi01.increment_time(20);
        AutomationDeviceEvents.hardAccel(tiwi01, 50);								//ALERT_TYPE_HARD_ACCEL
        tiwi01.increment_time(20);
        AutomationDeviceEvents.hardLeft(tiwi01, 105);								//ALERT_TYPE_HARD_LEFT
        tiwi01.increment_time(20);

        state.setTopSpeed(80).setSpeedingDistanceX100(200).setAvgSpeed(75).setSpeedingSpeedLimit(40); //ALERT_TYPE_SPEEDING
        tiwi01.addEvent( AutomationDeviceEvents.speeding(state, null));
        
        tiwi01.update_location(new GeoPoint(33.0104, -117.111), 15);
        tiwi01.update_location(new GeoPoint(33.0104, -117.113), 15);

        AutomationDeviceEvents.crash(tiwi01);										//ALERT_TYPE_CRASH
        AutomationDeviceEvents.panic(tiwi01);										//ALERT_TYPE_PANIC     
        AutomationDeviceEvents.manDown(tiwi01);										//ALERT_TYPE_MAN_DOWN
        AutomationDeviceEvents.manDownOK(tiwi01);								    //ALERT_TYPE_MAN_DOWN_OK
        AutomationDeviceEvents.HOSNoHours(tiwi01);									//ALERT_TYPE_HOS_NO_HOURS_REMAINING
        AutomationDeviceEvents.HOSDOTStopped(tiwi01);								//ALERT_TYPE_HOS_DOT_STOPPED
        AutomationDeviceEvents.wirelineAlarm(tiwi01);								//ALERT_TYPE_WIRELINE_ALARM
        AutomationDeviceEvents.witnessHeartbeat(tiwi01);							//ALERT_TYPE_WITNESS_HEARTBEAT_VIOLATION
        AutomationDeviceEvents.witnessUpdated(tiwi01);								//ALERT_TYPE_WITNESS_UPDATED
        tiwi01.enter_zone(6405);													//ALERT_TYPE_ENTER_ZONE
        AutomationDeviceEvents.zonesCurrent(tiwi01);								//ALERT_TYPE_ZONES_CURRENT
        tiwi01.leave_zone(6405);													//ALERT_TYPE_LEAVE_ZONE        
        AutomationDeviceEvents.lowBattery(tiwi01);									//ALERT_TYPE_LOW_BATTERY
        AutomationDeviceEvents.dSSMicrosleep(tiwi01);								//ALERT_TYPE_DSS_MICROSLEEP
        AutomationDeviceEvents.txtMsgReceived(tiwi01);								//ALERT_TYPE_TEXT_MSG_RECEIVED
        AutomationDeviceEvents.noDriver(tiwi01);									//ALERT_TYPE_NO_DRIVER
        AutomationDeviceEvents.noThumbDrive(tiwi01);								//ALERT_TYPE_NO_INTERNAL_THUMB_DRIVE;																			//ALERT_TYPE_NO_INTERNAL_THUMB_DRIVE
        
    	
        tiwi01.turn_key_off(30);
        tiwi01.power_off_device(900);
        

    }
}
