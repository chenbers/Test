package com.inthinc.device.emulation;

import com.inthinc.device.devices.TiwiProDevice;
import com.inthinc.device.devices.WaysmartDevice;
import com.inthinc.device.devices.WaysmartDevice.Direction;
import com.inthinc.device.emulation.utils.DeviceState;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.device.objects.AutomationDeviceEvents;
import com.inthinc.pro.automation.enums.AutoSilos;
import com.inthinc.pro.automation.objects.AutomationCalendar;

/**
 * @author Mike Weiss
 * This test has been written to make sure all alerts are coming in.  You can access the test email account at inthincalertsmaster@gmail.com with the
 * password !AmTheAlertsMaster.  There are 36 alert notifications in total that should come in.  8 alerts are for WaySmart devices (For DVIR, plus one for ignition on),
 * 28 alerts are tiwi alerts.
 *
 */
public class AlertsTripsTest {
	
	
	public static void main(String args[]) {
		//ALERTS TIWI DEVICES
        TiwiProDevice tiwi01 = new TiwiProDevice("120275615159901", AutoSilos.DEV);  //ALERTSDEVICE1  Used for the Email1 slot on the Admin Users Settings Page
        TiwiProDevice tiwi02 = new TiwiProDevice("120275615159902", AutoSilos.DEV);  //ALERTSDEVICE2  Used for the Email2 slot on the Admin Users Settings Page
        TiwiProDevice tiwi03 = new TiwiProDevice("120275615159903", AutoSilos.DEV);  //ALERTSDEVICE3  Used for the Text Message 1 slot on the Admin Users Settings Page
        TiwiProDevice tiwi04 = new TiwiProDevice("120275615159904", AutoSilos.DEV);  //ALERTSDEVICE4  Used for the Text Message 2 slot on the Admin Users Settings Page
        TiwiProDevice tiwi05 = new TiwiProDevice("120275615159905", AutoSilos.DEV);  //ALERTSDEVICE5  Used for the Phone 1 slot on the Admin Users Settings Page
        TiwiProDevice tiwi06 = new TiwiProDevice("120275615159906", AutoSilos.DEV);  //ALERTSDEVICE6  Used for the Phone 2 slot on the Admin Users Settings Page

        //ALERTS WAYSMART DEVICES

        WaysmartDevice ws01 = new WaysmartDevice("ALERTSWS0001", "ALERTSWS01", AutoSilos.DEV, Direction.gprs);
        WaysmartDevice ws02 = new WaysmartDevice("ALERTSWS0002", "ALERTSWS02", AutoSilos.DEV, Direction.gprs);
        WaysmartDevice ws03 = new WaysmartDevice("ALERTSWS0003", "ALERTSWS03", AutoSilos.DEV, Direction.gprs);
        WaysmartDevice ws04 = new WaysmartDevice("ALERTSWS0004", "ALERTSWS04", AutoSilos.DEV, Direction.gprs);
        WaysmartDevice ws05 = new WaysmartDevice("ALERTSWS0005", "ALERTSWS05", AutoSilos.DEV, Direction.gprs);
        WaysmartDevice ws06 = new WaysmartDevice("ALERTSWS0006", "ALERTSWS06", AutoSilos.DEV, Direction.gprs);
        
        //WAYSMART01 TRIP FOR DVIR ALERTS
        ws01.getState().setWMP(17116);
        ws01.firstLocation(new GeoPoint(33.0104, -117.111));
        ws01.power_on_device();
        ws01.turn_key_on(15);														//ALERT_TYPE_IGNITION_ON (There will be two of these sent, one for WS and the other for Tiwi
        AutomationDeviceEvents.dVIRPreTripPass(ws01);								//ALERT_TYPE_DVIR_PRE_TRIP_PASS 
        AutomationDeviceEvents.dVIRPreTripFail(ws01);								//ALERT_TYPE_DVIR_PRE_TRIP_FAIL       
        AutomationDeviceEvents.dVIRUnsafe(ws01);									//ALERT_TYPE_DVIR_DRIVEN_UNSAFE
        AutomationDeviceEvents.dVIRNoPreInspection(ws01);							//ALERT_TYPE_DVIR_DRIVEN_NOINSPECTION
        AutomationDeviceEvents.dVIRNoPostInspection(ws01);							//ALERT_TYPE_DVIR_DRIVEN_NOPOSTINSPEC
        AutomationDeviceEvents.dVIRPostTripFail(ws01);								//ALERT_TYPE_DVIR_POST_TRIP_FAIL
        AutomationDeviceEvents.dVIRPostTripPass(ws01);								//ALERT_TYPE_DVIR_POST_TRIP_PASS    

        ws01.turn_key_off(30);
        ws01.power_off_device(900);
        
        //WAYSMART02 TRIP FOR DVIR ALERTS
        ws02.getState().setWMP(17116);
        ws02.firstLocation(new GeoPoint(33.0104, -117.111));
        ws02.power_on_device();
        ws02.turn_key_on(15);														//ALERT_TYPE_IGNITION_ON (There will be two of these sent, one for WS and the other for Tiwi
        AutomationDeviceEvents.dVIRPreTripFail(ws02);								//ALERT_TYPE_DVIR_PRE_TRIP_FAIL
        AutomationDeviceEvents.dVIRPreTripPass(ws02);								//ALERT_TYPE_DVIR_PRE_TRIP_PASS        
        AutomationDeviceEvents.dVIRUnsafe(ws02);									//ALERT_TYPE_DVIR_DRIVEN_UNSAFE
        AutomationDeviceEvents.dVIRNoPreInspection(ws02);							//ALERT_TYPE_DVIR_DRIVEN_NOINSPECTION
        AutomationDeviceEvents.dVIRNoPostInspection(ws02);							//ALERT_TYPE_DVIR_DRIVEN_NOPOSTINSPEC
        AutomationDeviceEvents.dVIRPostTripPass(ws02);								//ALERT_TYPE_DVIR_POST_TRIP_PASS    
        AutomationDeviceEvents.dVIRPostTripFail(ws02);								//ALERT_TYPE_DVIR_POST_TRIP_FAIL
        ws02.turn_key_off(30);
        ws02.power_off_device(900);
        
        //WAYSMART03 TRIP FOR DVIR ALERTS
        ws03.getState().setWMP(17116);
        ws03.firstLocation(new GeoPoint(33.0104, -117.111));
        ws03.power_on_device();
        ws03.turn_key_on(15);														//ALERT_TYPE_IGNITION_ON (There will be two of these sent, one for WS and the other for Tiwi
        AutomationDeviceEvents.dVIRPreTripFail(ws03);								//ALERT_TYPE_DVIR_PRE_TRIP_FAIL
        AutomationDeviceEvents.dVIRPreTripPass(ws03);								//ALERT_TYPE_DVIR_PRE_TRIP_PASS        
        AutomationDeviceEvents.dVIRUnsafe(ws03);									//ALERT_TYPE_DVIR_DRIVEN_UNSAFE
        AutomationDeviceEvents.dVIRNoPreInspection(ws03);							//ALERT_TYPE_DVIR_DRIVEN_NOINSPECTION
        AutomationDeviceEvents.dVIRNoPostInspection(ws03);							//ALERT_TYPE_DVIR_DRIVEN_NOPOSTINSPEC
        AutomationDeviceEvents.dVIRPostTripPass(ws03);								//ALERT_TYPE_DVIR_POST_TRIP_PASS    
        AutomationDeviceEvents.dVIRPostTripFail(ws03);								//ALERT_TYPE_DVIR_POST_TRIP_FAIL
        ws03.turn_key_off(30);
        ws03.power_off_device(900);
        
        //WAYSMART04 TRIP FOR DVIR ALERTS
        ws04.getState().setWMP(17116);
        ws04.firstLocation(new GeoPoint(33.0104, -117.111));
        ws04.power_on_device();
        ws04.turn_key_on(15);														//ALERT_TYPE_IGNITION_ON (There will be two of these sent, one for WS and the other for Tiwi
        AutomationDeviceEvents.dVIRPreTripFail(ws04);								//ALERT_TYPE_DVIR_PRE_TRIP_FAIL
        AutomationDeviceEvents.dVIRPreTripPass(ws04);								//ALERT_TYPE_DVIR_PRE_TRIP_PASS        
        AutomationDeviceEvents.dVIRUnsafe(ws04);									//ALERT_TYPE_DVIR_DRIVEN_UNSAFE
        AutomationDeviceEvents.dVIRNoPreInspection(ws04);							//ALERT_TYPE_DVIR_DRIVEN_NOINSPECTION
        AutomationDeviceEvents.dVIRNoPostInspection(ws04);							//ALERT_TYPE_DVIR_DRIVEN_NOPOSTINSPEC
        AutomationDeviceEvents.dVIRPostTripPass(ws04);								//ALERT_TYPE_DVIR_POST_TRIP_PASS    
        AutomationDeviceEvents.dVIRPostTripFail(ws04);								//ALERT_TYPE_DVIR_POST_TRIP_FAIL
        ws04.turn_key_off(30);
        ws04.power_off_device(900);
        
        //QA special 
        String imeiOnQA_WS850 = "MCMFAKE01";
        String mcmIDOnQA_WS850 = "MCMFAKE01";
        
        WaysmartDevice waySmartRepairAlertQA = new WaysmartDevice(imeiOnQA_WS850, mcmIDOnQA_WS850, AutoSilos.QA, Direction.gprs);
        waySmartRepairAlertQA.set_time(new AutomationCalendar());
        waySmartRepairAlertQA.getState().setWMP(17116);
        waySmartRepairAlertQA.firstLocation(new GeoPoint(33.0104, -117.111));
        waySmartRepairAlertQA.power_on_device();
        waySmartRepairAlertQA.turn_key_on(15);
        AutomationDeviceEvents.dVIRRepairAlertEvent(waySmartRepairAlertQA);                          //ALERT_TYPE_DVIR_REPAIR
        waySmartRepairAlertQA.turn_key_off(30);
        waySmartRepairAlertQA.power_off_device(900);
        
        //WAYSMART05 TRIP FOR DVIR ALERTS
        ws05.getState().setWMP(17116);
        ws05.firstLocation(new GeoPoint(33.0104, -117.111));
        ws05.power_on_device();
        ws05.turn_key_on(15);														//ALERT_TYPE_IGNITION_ON (There will be two of these sent, one for WS and the other for Tiwi
        AutomationDeviceEvents.dVIRPreTripFail(ws05);								//ALERT_TYPE_DVIR_PRE_TRIP_FAIL
        AutomationDeviceEvents.dVIRPreTripPass(ws05);								//ALERT_TYPE_DVIR_PRE_TRIP_PASS        
        AutomationDeviceEvents.dVIRUnsafe(ws05);									//ALERT_TYPE_DVIR_DRIVEN_UNSAFE
        AutomationDeviceEvents.dVIRNoPreInspection(ws05);							//ALERT_TYPE_DVIR_DRIVEN_NOINSPECTION
        AutomationDeviceEvents.dVIRNoPostInspection(ws05);							//ALERT_TYPE_DVIR_DRIVEN_NOPOSTINSPEC
        AutomationDeviceEvents.dVIRPostTripPass(ws05);								//ALERT_TYPE_DVIR_POST_TRIP_PASS    
        AutomationDeviceEvents.dVIRPostTripFail(ws05);								//ALERT_TYPE_DVIR_POST_TRIP_FAIL
        ws05.turn_key_off(30);
        ws05.power_off_device(900);
        
        //WAYSMART06 TRIP FOR DVIR ALERTS
        ws06.getState().setWMP(17116);
        ws06.firstLocation(new GeoPoint(33.0104, -117.111));
        ws06.power_on_device();
        ws06.turn_key_on(15);														//ALERT_TYPE_IGNITION_ON (There will be two of these sent, one for WS and the other for Tiwi
        AutomationDeviceEvents.dVIRPreTripFail(ws06);								//ALERT_TYPE_DVIR_PRE_TRIP_FAIL
        AutomationDeviceEvents.dVIRPreTripPass(ws06);								//ALERT_TYPE_DVIR_PRE_TRIP_PASS        
        AutomationDeviceEvents.dVIRUnsafe(ws06);									//ALERT_TYPE_DVIR_DRIVEN_UNSAFE
        AutomationDeviceEvents.dVIRNoPreInspection(ws06);							//ALERT_TYPE_DVIR_DRIVEN_NOINSPECTION
        AutomationDeviceEvents.dVIRNoPostInspection(ws06);							//ALERT_TYPE_DVIR_DRIVEN_NOPOSTINSPEC
        AutomationDeviceEvents.dVIRPostTripPass(ws06);								//ALERT_TYPE_DVIR_POST_TRIP_PASS    
        AutomationDeviceEvents.dVIRPostTripFail(ws06);								//ALERT_TYPE_DVIR_POST_TRIP_FAIL
        ws06.turn_key_off(30);
        ws06.power_off_device(900);
        
        
        //TIWI01 TRIP FOR ALL OTHER EMAIL ALERTS
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
        AutomationDeviceEvents.hardBrake(tiwi01, 200);								//ALERT_TYPE_HARD_BRAKE
        tiwi01.increment_time(20);
        AutomationDeviceEvents.hardBump(tiwi01, 90);								//ALERT_TYPE_HARD_BUMP
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
        AutomationDeviceEvents.manDown(tiwi01);										//ALERT_TYPE_MAN_DOWN (AKA LONE WORKER ALARM)
        AutomationDeviceEvents.manDownOK(tiwi01);								    //ALERT_TYPE_MAN_DOWN_OK (AKA LONE WORKER ALARM CANCELLED)
        AutomationDeviceEvents.HOSNoHours(tiwi01);									//ALERT_TYPE_HOS_NO_HOURS_REMAINING
        AutomationDeviceEvents.HOSDOTStopped(tiwi01);								//ALERT_TYPE_HOS_DOT_STOPPED
        AutomationDeviceEvents.wirelineAlarm(tiwi01);								//ALERT_TYPE_WIRELINE_ALARM
        AutomationDeviceEvents.witnessHeartbeat(tiwi01);							//ALERT_TYPE_WITNESS_HEARTBEAT_VIOLATION (AKA NO HEARTBEAT DETECTED FOR CRASH DETECTOR)
        AutomationDeviceEvents.witnessUpdated(tiwi01);								//ALERT_TYPE_WITNESS_UPDATED (AKA CRASH DETECTOR CURRENT)
        tiwi01.enter_zone(6405);													//ALERT_TYPE_ENTER_ZONE
        AutomationDeviceEvents.zonesCurrent(tiwi01);								//ALERT_TYPE_ZONES_CURRENT
        tiwi01.leave_zone(6405);													//ALERT_TYPE_LEAVE_ZONE        
        AutomationDeviceEvents.lowBattery(tiwi01);									//ALERT_TYPE_LOW_BATTERY
        AutomationDeviceEvents.dSSMicrosleep(tiwi01);								//ALERT_TYPE_DSS_MICROSLEEP
        AutomationDeviceEvents.txtMsgReceived(tiwi01);								//ALERT_TYPE_TEXT_MSG_RECEIVED
        AutomationDeviceEvents.noDriver(tiwi01);									//ALERT_TYPE_NO_DRIVER
        AutomationDeviceEvents.noThumbDrive(tiwi01);								//ALERT_TYPE_NO_INTERNAL_THUMB_DRIVE; (AKA EXTERNAL STORAGE CANNOT BE MOUNTED)
   	
        tiwi01.turn_key_off(30);
        tiwi01.power_off_device(900);
        
        //TIWI02 TRIP FOR ALL OTHER EMAIL ALERTS
        DeviceState state2 = tiwi02.getState();
        tiwi02.dump_settings();
        tiwi02.increment_time(60);
        tiwi02.getState().setWMP(17116);
        tiwi02.firstLocation(new GeoPoint(33.0104, -117.111));
        tiwi02.power_on_device();
        
        tiwi02.turn_key_on(15);														//ALERT_TYPE_IGNITION_ON
        AutomationDeviceEvents.install(tiwi02);										//ALERT_TYPE_INSTALL
        AutomationDeviceEvents.firmwareCurrent(tiwi02);								//ALERT_TYPE_FIRMWARE_CURRENT
        AutomationDeviceEvents.qSICurrent(tiwi02);									//ALERT_TYPE_QSI_UPDATED

        tiwi02.getState().setLowIdle(300).setHighIdle(300);							//ALERT_TYPE_IDLING
        AutomationDeviceEvents.idling(tiwi02);
        
        AutomationDeviceEvents.parkingBrake(tiwi02);								//ALERT_TYPE_PARKING_BRAKE
        
        tiwi02.increment_time(20);
        AutomationDeviceEvents.seatbelt(tiwi02);									//ALERT_TYPE_SEATBELT
        
        tiwi02.increment_time(20);
        AutomationDeviceEvents.hardBrake(tiwi02, 200);								//ALERT_TYPE_HARD_BRAKE
        tiwi02.increment_time(20);
        AutomationDeviceEvents.hardBump(tiwi02, 90);								//ALERT_TYPE_HARD_BUMP
        tiwi02.increment_time(20);
        AutomationDeviceEvents.hardAccel(tiwi02, 50);								//ALERT_TYPE_HARD_ACCEL
        tiwi02.increment_time(20);
        AutomationDeviceEvents.hardLeft(tiwi02, 105);								//ALERT_TYPE_HARD_LEFT
        tiwi02.increment_time(20);

        state2.setTopSpeed(80).setSpeedingDistanceX100(200).setAvgSpeed(75).setSpeedingSpeedLimit(40); //ALERT_TYPE_SPEEDING
        tiwi02.addEvent( AutomationDeviceEvents.speeding(state, null));
        
        tiwi02.update_location(new GeoPoint(33.0104, -117.111), 15);
        tiwi02.update_location(new GeoPoint(33.0104, -117.113), 15);

        AutomationDeviceEvents.crash(tiwi02);										//ALERT_TYPE_CRASH
        AutomationDeviceEvents.panic(tiwi02);										//ALERT_TYPE_PANIC     
        AutomationDeviceEvents.manDown(tiwi02);										//ALERT_TYPE_MAN_DOWN (AKA LONE WORKER ALARM)
        AutomationDeviceEvents.manDownOK(tiwi02);								    //ALERT_TYPE_MAN_DOWN_OK (AKA LONE WORKER ALARM CANCELLED)
        AutomationDeviceEvents.HOSNoHours(tiwi02);									//ALERT_TYPE_HOS_NO_HOURS_REMAINING
        AutomationDeviceEvents.HOSDOTStopped(tiwi02);								//ALERT_TYPE_HOS_DOT_STOPPED
        AutomationDeviceEvents.wirelineAlarm(tiwi02);								//ALERT_TYPE_WIRELINE_ALARM
        AutomationDeviceEvents.witnessHeartbeat(tiwi02);							//ALERT_TYPE_WITNESS_HEARTBEAT_VIOLATION (AKA NO HEARTBEAT DETECTED FOR CRASH DETECTOR)
        AutomationDeviceEvents.witnessUpdated(tiwi02);								//ALERT_TYPE_WITNESS_UPDATED (AKA CRASH DETECTOR CURRENT)
        tiwi02.enter_zone(6405);													//ALERT_TYPE_ENTER_ZONE
        AutomationDeviceEvents.zonesCurrent(tiwi02);								//ALERT_TYPE_ZONES_CURRENT
        tiwi02.leave_zone(6405);													//ALERT_TYPE_LEAVE_ZONE        
        AutomationDeviceEvents.lowBattery(tiwi02);									//ALERT_TYPE_LOW_BATTERY
        AutomationDeviceEvents.dSSMicrosleep(tiwi02);								//ALERT_TYPE_DSS_MICROSLEEP
        AutomationDeviceEvents.txtMsgReceived(tiwi02);								//ALERT_TYPE_TEXT_MSG_RECEIVED
        AutomationDeviceEvents.noDriver(tiwi02);									//ALERT_TYPE_NO_DRIVER
        AutomationDeviceEvents.noThumbDrive(tiwi02);								//ALERT_TYPE_NO_INTERNAL_THUMB_DRIVE; (AKA EXTERNAL STORAGE CANNOT BE MOUNTED)
   	
        tiwi02.turn_key_off(30);
        tiwi02.power_off_device(900);
        
        //TIWI03 TRIP FOR ALL OTHER EMAIL ALERTS
        DeviceState state3 = tiwi03.getState();
        tiwi03.dump_settings();
        tiwi03.increment_time(60);
        tiwi03.getState().setWMP(17116);
        tiwi03.firstLocation(new GeoPoint(33.0104, -117.111));
        tiwi03.power_on_device();
        
        tiwi03.turn_key_on(15);														//ALERT_TYPE_IGNITION_ON
        AutomationDeviceEvents.install(tiwi03);										//ALERT_TYPE_INSTALL
        AutomationDeviceEvents.firmwareCurrent(tiwi03);								//ALERT_TYPE_FIRMWARE_CURRENT
        AutomationDeviceEvents.qSICurrent(tiwi03);									//ALERT_TYPE_QSI_UPDATED

        tiwi03.getState().setLowIdle(300).setHighIdle(300);							//ALERT_TYPE_IDLING
        AutomationDeviceEvents.idling(tiwi03);
        
        AutomationDeviceEvents.parkingBrake(tiwi03);								//ALERT_TYPE_PARKING_BRAKE
        
        tiwi03.increment_time(20);
        AutomationDeviceEvents.seatbelt(tiwi03);									//ALERT_TYPE_SEATBELT
        
        tiwi03.increment_time(20);
        AutomationDeviceEvents.hardBrake(tiwi03, 200);								//ALERT_TYPE_HARD_BRAKE
        tiwi03.increment_time(20);
        AutomationDeviceEvents.hardBump(tiwi03, 90);								//ALERT_TYPE_HARD_BUMP
        tiwi03.increment_time(20);
        AutomationDeviceEvents.hardAccel(tiwi03, 50);								//ALERT_TYPE_HARD_ACCEL
        tiwi03.increment_time(20);
        AutomationDeviceEvents.hardLeft(tiwi03, 105);								//ALERT_TYPE_HARD_LEFT
        tiwi03.increment_time(20);

        state3.setTopSpeed(80).setSpeedingDistanceX100(200).setAvgSpeed(75).setSpeedingSpeedLimit(40); //ALERT_TYPE_SPEEDING
        tiwi03.addEvent( AutomationDeviceEvents.speeding(state, null));
        
        tiwi03.update_location(new GeoPoint(33.0104, -117.111), 15);
        tiwi03.update_location(new GeoPoint(33.0104, -117.113), 15);

        AutomationDeviceEvents.crash(tiwi03);										//ALERT_TYPE_CRASH
        AutomationDeviceEvents.panic(tiwi03);										//ALERT_TYPE_PANIC     
        AutomationDeviceEvents.manDown(tiwi03);										//ALERT_TYPE_MAN_DOWN (AKA LONE WORKER ALARM)
        AutomationDeviceEvents.manDownOK(tiwi03);								    //ALERT_TYPE_MAN_DOWN_OK (AKA LONE WORKER ALARM CANCELLED)
        AutomationDeviceEvents.HOSNoHours(tiwi03);									//ALERT_TYPE_HOS_NO_HOURS_REMAINING
        AutomationDeviceEvents.HOSDOTStopped(tiwi03);								//ALERT_TYPE_HOS_DOT_STOPPED
        AutomationDeviceEvents.wirelineAlarm(tiwi03);								//ALERT_TYPE_WIRELINE_ALARM
        AutomationDeviceEvents.witnessHeartbeat(tiwi03);							//ALERT_TYPE_WITNESS_HEARTBEAT_VIOLATION (AKA NO HEARTBEAT DETECTED FOR CRASH DETECTOR)
        AutomationDeviceEvents.witnessUpdated(tiwi03);								//ALERT_TYPE_WITNESS_UPDATED (AKA CRASH DETECTOR CURRENT)
        tiwi03.enter_zone(6405);													//ALERT_TYPE_ENTER_ZONE
        AutomationDeviceEvents.zonesCurrent(tiwi03);								//ALERT_TYPE_ZONES_CURRENT
        tiwi03.leave_zone(6405);													//ALERT_TYPE_LEAVE_ZONE        
        AutomationDeviceEvents.lowBattery(tiwi03);									//ALERT_TYPE_LOW_BATTERY
        AutomationDeviceEvents.dSSMicrosleep(tiwi03);								//ALERT_TYPE_DSS_MICROSLEEP
        AutomationDeviceEvents.txtMsgReceived(tiwi03);								//ALERT_TYPE_TEXT_MSG_RECEIVED
        AutomationDeviceEvents.noDriver(tiwi03);									//ALERT_TYPE_NO_DRIVER
        AutomationDeviceEvents.noThumbDrive(tiwi03);								//ALERT_TYPE_NO_INTERNAL_THUMB_DRIVE; (AKA EXTERNAL STORAGE CANNOT BE MOUNTED)
   	
        tiwi03.turn_key_off(30);
        tiwi03.power_off_device(900);
        
        //TIWI04 TRIP FOR ALL OTHER EMAIL ALERTS
        DeviceState state4 = tiwi04.getState();
        tiwi04.dump_settings();
        tiwi04.increment_time(60);
        tiwi04.getState().setWMP(17116);
        tiwi04.firstLocation(new GeoPoint(33.0104, -117.111));
        tiwi04.power_on_device();
        
        tiwi04.turn_key_on(15);														//ALERT_TYPE_IGNITION_ON
        AutomationDeviceEvents.install(tiwi04);										//ALERT_TYPE_INSTALL
        AutomationDeviceEvents.firmwareCurrent(tiwi04);								//ALERT_TYPE_FIRMWARE_CURRENT
        AutomationDeviceEvents.qSICurrent(tiwi04);									//ALERT_TYPE_QSI_UPDATED

        tiwi04.getState().setLowIdle(300).setHighIdle(300);							//ALERT_TYPE_IDLING
        AutomationDeviceEvents.idling(tiwi04);
        
        AutomationDeviceEvents.parkingBrake(tiwi04);								//ALERT_TYPE_PARKING_BRAKE
        
        tiwi04.increment_time(20);
        AutomationDeviceEvents.seatbelt(tiwi04);									//ALERT_TYPE_SEATBELT
        
        tiwi04.increment_time(20);
        AutomationDeviceEvents.hardBrake(tiwi04, 200);								//ALERT_TYPE_HARD_BRAKE
        tiwi04.increment_time(20);
        AutomationDeviceEvents.hardBump(tiwi04, 90);								//ALERT_TYPE_HARD_BUMP
        tiwi04.increment_time(20);
        AutomationDeviceEvents.hardAccel(tiwi04, 50);								//ALERT_TYPE_HARD_ACCEL
        tiwi04.increment_time(20);
        AutomationDeviceEvents.hardLeft(tiwi04, 105);								//ALERT_TYPE_HARD_LEFT
        tiwi04.increment_time(20);

        state4.setTopSpeed(80).setSpeedingDistanceX100(200).setAvgSpeed(75).setSpeedingSpeedLimit(40); //ALERT_TYPE_SPEEDING
        tiwi04.addEvent( AutomationDeviceEvents.speeding(state, null));
        
        tiwi04.update_location(new GeoPoint(33.0104, -117.111), 15);
        tiwi04.update_location(new GeoPoint(33.0104, -117.113), 15);

        AutomationDeviceEvents.crash(tiwi04);										//ALERT_TYPE_CRASH
        AutomationDeviceEvents.panic(tiwi04);										//ALERT_TYPE_PANIC     
        AutomationDeviceEvents.manDown(tiwi04);										//ALERT_TYPE_MAN_DOWN (AKA LONE WORKER ALARM)
        AutomationDeviceEvents.manDownOK(tiwi04);								    //ALERT_TYPE_MAN_DOWN_OK (AKA LONE WORKER ALARM CANCELLED)
        AutomationDeviceEvents.HOSNoHours(tiwi04);									//ALERT_TYPE_HOS_NO_HOURS_REMAINING
        AutomationDeviceEvents.HOSDOTStopped(tiwi04);								//ALERT_TYPE_HOS_DOT_STOPPED
        AutomationDeviceEvents.wirelineAlarm(tiwi04);								//ALERT_TYPE_WIRELINE_ALARM
        AutomationDeviceEvents.witnessHeartbeat(tiwi04);							//ALERT_TYPE_WITNESS_HEARTBEAT_VIOLATION (AKA NO HEARTBEAT DETECTED FOR CRASH DETECTOR)
        AutomationDeviceEvents.witnessUpdated(tiwi04);								//ALERT_TYPE_WITNESS_UPDATED (AKA CRASH DETECTOR CURRENT)
        tiwi04.enter_zone(6405);													//ALERT_TYPE_ENTER_ZONE
        AutomationDeviceEvents.zonesCurrent(tiwi04);								//ALERT_TYPE_ZONES_CURRENT
        tiwi04.leave_zone(6405);													//ALERT_TYPE_LEAVE_ZONE        
        AutomationDeviceEvents.lowBattery(tiwi04);									//ALERT_TYPE_LOW_BATTERY
        AutomationDeviceEvents.dSSMicrosleep(tiwi04);								//ALERT_TYPE_DSS_MICROSLEEP
        AutomationDeviceEvents.txtMsgReceived(tiwi04);								//ALERT_TYPE_TEXT_MSG_RECEIVED
        AutomationDeviceEvents.noDriver(tiwi04);									//ALERT_TYPE_NO_DRIVER
        AutomationDeviceEvents.noThumbDrive(tiwi04);								//ALERT_TYPE_NO_INTERNAL_THUMB_DRIVE; (AKA EXTERNAL STORAGE CANNOT BE MOUNTED)
   	
        tiwi04.turn_key_off(30);
        tiwi04.power_off_device(900);
        
        //TIWI05 TRIP FOR ALL OTHER EMAIL ALERTS
        DeviceState state5 = tiwi05.getState();
        tiwi05.dump_settings();
        tiwi05.increment_time(60);
        tiwi05.getState().setWMP(17116);
        tiwi05.firstLocation(new GeoPoint(33.0104, -117.111));
        tiwi05.power_on_device();
        
        tiwi05.turn_key_on(15);														//ALERT_TYPE_IGNITION_ON
        AutomationDeviceEvents.install(tiwi05);										//ALERT_TYPE_INSTALL
        AutomationDeviceEvents.firmwareCurrent(tiwi05);								//ALERT_TYPE_FIRMWARE_CURRENT
        AutomationDeviceEvents.qSICurrent(tiwi05);									//ALERT_TYPE_QSI_UPDATED

        tiwi05.getState().setLowIdle(300).setHighIdle(300);							//ALERT_TYPE_IDLING
        AutomationDeviceEvents.idling(tiwi05);
        
        AutomationDeviceEvents.parkingBrake(tiwi05);								//ALERT_TYPE_PARKING_BRAKE
        
        tiwi05.increment_time(20);
        AutomationDeviceEvents.seatbelt(tiwi05);									//ALERT_TYPE_SEATBELT
        
        tiwi05.increment_time(20);
        AutomationDeviceEvents.hardBrake(tiwi05, 200);								//ALERT_TYPE_HARD_BRAKE
        tiwi05.increment_time(20);
        AutomationDeviceEvents.hardBump(tiwi05, 90);								//ALERT_TYPE_HARD_BUMP
        tiwi05.increment_time(20);
        AutomationDeviceEvents.hardAccel(tiwi05, 50);								//ALERT_TYPE_HARD_ACCEL
        tiwi05.increment_time(20);
        AutomationDeviceEvents.hardLeft(tiwi05, 105);								//ALERT_TYPE_HARD_LEFT
        tiwi05.increment_time(20);

        state5.setTopSpeed(80).setSpeedingDistanceX100(200).setAvgSpeed(75).setSpeedingSpeedLimit(40); //ALERT_TYPE_SPEEDING
        tiwi05.addEvent( AutomationDeviceEvents.speeding(state, null));
        
        tiwi05.update_location(new GeoPoint(33.0104, -117.111), 15);
        tiwi05.update_location(new GeoPoint(33.0104, -117.113), 15);

        AutomationDeviceEvents.crash(tiwi05);										//ALERT_TYPE_CRASH
        AutomationDeviceEvents.panic(tiwi05);										//ALERT_TYPE_PANIC     
        AutomationDeviceEvents.manDown(tiwi05);										//ALERT_TYPE_MAN_DOWN (AKA LONE WORKER ALARM)
        AutomationDeviceEvents.manDownOK(tiwi05);								    //ALERT_TYPE_MAN_DOWN_OK (AKA LONE WORKER ALARM CANCELLED)
        AutomationDeviceEvents.HOSNoHours(tiwi05);									//ALERT_TYPE_HOS_NO_HOURS_REMAINING
        AutomationDeviceEvents.HOSDOTStopped(tiwi05);								//ALERT_TYPE_HOS_DOT_STOPPED
        AutomationDeviceEvents.wirelineAlarm(tiwi05);								//ALERT_TYPE_WIRELINE_ALARM
        AutomationDeviceEvents.witnessHeartbeat(tiwi05);							//ALERT_TYPE_WITNESS_HEARTBEAT_VIOLATION (AKA NO HEARTBEAT DETECTED FOR CRASH DETECTOR)
        AutomationDeviceEvents.witnessUpdated(tiwi05);								//ALERT_TYPE_WITNESS_UPDATED (AKA CRASH DETECTOR CURRENT)
        tiwi05.enter_zone(6405);													//ALERT_TYPE_ENTER_ZONE
        AutomationDeviceEvents.zonesCurrent(tiwi05);								//ALERT_TYPE_ZONES_CURRENT
        tiwi05.leave_zone(6405);													//ALERT_TYPE_LEAVE_ZONE        
        AutomationDeviceEvents.lowBattery(tiwi05);									//ALERT_TYPE_LOW_BATTERY
        AutomationDeviceEvents.dSSMicrosleep(tiwi05);								//ALERT_TYPE_DSS_MICROSLEEP
        AutomationDeviceEvents.txtMsgReceived(tiwi05);								//ALERT_TYPE_TEXT_MSG_RECEIVED
        AutomationDeviceEvents.noDriver(tiwi05);									//ALERT_TYPE_NO_DRIVER
        AutomationDeviceEvents.noThumbDrive(tiwi05);								//ALERT_TYPE_NO_INTERNAL_THUMB_DRIVE; (AKA EXTERNAL STORAGE CANNOT BE MOUNTED)
   	
        tiwi05.turn_key_off(30);
        tiwi05.power_off_device(900);
        
        //TIWI06 TRIP FOR ALL OTHER EMAIL ALERTS
        DeviceState state6 = tiwi06.getState();
        tiwi06.dump_settings();
        tiwi06.increment_time(60);
        tiwi06.getState().setWMP(17116);
        tiwi06.firstLocation(new GeoPoint(33.0104, -117.111));
        tiwi06.power_on_device();
        
        tiwi06.turn_key_on(15);														//ALERT_TYPE_IGNITION_ON
        AutomationDeviceEvents.install(tiwi06);										//ALERT_TYPE_INSTALL
        AutomationDeviceEvents.firmwareCurrent(tiwi06);								//ALERT_TYPE_FIRMWARE_CURRENT
        AutomationDeviceEvents.qSICurrent(tiwi06);									//ALERT_TYPE_QSI_UPDATED

        tiwi06.getState().setLowIdle(300).setHighIdle(300);							//ALERT_TYPE_IDLING
        AutomationDeviceEvents.idling(tiwi06);
        
        AutomationDeviceEvents.parkingBrake(tiwi06);								//ALERT_TYPE_PARKING_BRAKE
        
        tiwi06.increment_time(20);
        AutomationDeviceEvents.seatbelt(tiwi06);									//ALERT_TYPE_SEATBELT
        
        tiwi06.increment_time(20);
        AutomationDeviceEvents.hardBrake(tiwi06, 200);								//ALERT_TYPE_HARD_BRAKE
        tiwi06.increment_time(20);
        AutomationDeviceEvents.hardBump(tiwi06, 90);								//ALERT_TYPE_HARD_BUMP
        tiwi06.increment_time(20);
        AutomationDeviceEvents.hardAccel(tiwi06, 50);								//ALERT_TYPE_HARD_ACCEL
        tiwi06.increment_time(20);
        AutomationDeviceEvents.hardLeft(tiwi06, 105);								//ALERT_TYPE_HARD_LEFT
        tiwi06.increment_time(20);

        state6.setTopSpeed(80).setSpeedingDistanceX100(200).setAvgSpeed(75).setSpeedingSpeedLimit(40); //ALERT_TYPE_SPEEDING
        tiwi06.addEvent( AutomationDeviceEvents.speeding(state, null));
        
        tiwi06.update_location(new GeoPoint(33.0104, -117.111), 15);
        tiwi06.update_location(new GeoPoint(33.0104, -117.113), 15);

        AutomationDeviceEvents.crash(tiwi06);										//ALERT_TYPE_CRASH
        AutomationDeviceEvents.panic(tiwi06);										//ALERT_TYPE_PANIC     
        AutomationDeviceEvents.manDown(tiwi06);										//ALERT_TYPE_MAN_DOWN (AKA LONE WORKER ALARM)
        AutomationDeviceEvents.manDownOK(tiwi06);								    //ALERT_TYPE_MAN_DOWN_OK (AKA LONE WORKER ALARM CANCELLED)
        AutomationDeviceEvents.HOSNoHours(tiwi06);									//ALERT_TYPE_HOS_NO_HOURS_REMAINING
        AutomationDeviceEvents.HOSDOTStopped(tiwi06);								//ALERT_TYPE_HOS_DOT_STOPPED
        AutomationDeviceEvents.wirelineAlarm(tiwi06);								//ALERT_TYPE_WIRELINE_ALARM
        AutomationDeviceEvents.witnessHeartbeat(tiwi06);							//ALERT_TYPE_WITNESS_HEARTBEAT_VIOLATION (AKA NO HEARTBEAT DETECTED FOR CRASH DETECTOR)
        AutomationDeviceEvents.witnessUpdated(tiwi06);								//ALERT_TYPE_WITNESS_UPDATED (AKA CRASH DETECTOR CURRENT)
        tiwi06.enter_zone(6405);													//ALERT_TYPE_ENTER_ZONE
        AutomationDeviceEvents.zonesCurrent(tiwi06);								//ALERT_TYPE_ZONES_CURRENT
        tiwi06.leave_zone(6405);													//ALERT_TYPE_LEAVE_ZONE        
        AutomationDeviceEvents.lowBattery(tiwi06);									//ALERT_TYPE_LOW_BATTERY
        AutomationDeviceEvents.dSSMicrosleep(tiwi06);								//ALERT_TYPE_DSS_MICROSLEEP
        AutomationDeviceEvents.txtMsgReceived(tiwi06);								//ALERT_TYPE_TEXT_MSG_RECEIVED
        AutomationDeviceEvents.noDriver(tiwi06);									//ALERT_TYPE_NO_DRIVER
        AutomationDeviceEvents.noThumbDrive(tiwi06);								//ALERT_TYPE_NO_INTERNAL_THUMB_DRIVE; (AKA EXTERNAL STORAGE CANNOT BE MOUNTED)
   	
        tiwi06.turn_key_off(30);
        tiwi06.power_off_device(900);
        
    }
}
