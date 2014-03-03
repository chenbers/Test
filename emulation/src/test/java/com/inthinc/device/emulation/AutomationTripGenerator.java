package com.inthinc.device.emulation;

import com.inthinc.device.devices.TiwiProDevice;
import com.inthinc.device.devices.WaysmartDevice;
import com.inthinc.device.devices.WaysmartDevice.Direction;
import com.inthinc.device.emulation.enums.DeviceEnums.HOSState;
import com.inthinc.device.emulation.utils.DeviceState;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.device.objects.AutomationDeviceEvents;
import com.inthinc.pro.automation.enums.AutoSilos;
import com.inthinc.pro.automation.enums.ProductType;
import com.inthinc.pro.automation.objects.AutomationCalendar;

public class AutomationTripGenerator {
    
    
    public static void main(String args[]) {

        //NewNoteTest noteTest = new NewNoteTest(AutoSilos.QA);
        //noteTest.testDVIRNote("", "");
        //TiwiProDevice ws850 = new TiwiProDevice("970399012109740", ProductType.TIWIPRO_R747);
        WaysmartDevice ws850 = new WaysmartDevice("AUTOMATION850", "AUTOMATION850", AutoSilos.QA, Direction.gprs);
        ws850.dump_settings();
        DeviceState state = ws850.getState();
        AutomationCalendar initialTime = new AutomationCalendar();
        ws850.set_time(initialTime.setDate(1393764474));  //head to http://www.epochconverter.com/ and you can enter the date you need here
        ws850.getState().setWMP(17116);
        ws850.firstLocation(new GeoPoint(40.7103, -111.9920));
        ws850.setEmployeeID("AUTOWS850"); 
        ws850.power_on_device();
        ws850.increment_time(1);
        ws850.turn_key_on(15);
        ws850.increment_time(1);
        ws850.getState().setLowIdle(300).setHighIdle(300);
        ws850.increment_time(1);
        AutomationDeviceEvents.idling(ws850);
        ws850.increment_time(1);
        ws850.getState().setSpeedLimit(5);
        AutomationDeviceEvents.seatbelt(ws850);
        ws850.increment_time(1);
        ws850.enter_zone(1062);
        ws850.update_location(new GeoPoint(40.711, -111.9921), 15);
        AutomationDeviceEvents.panic(ws850);                                 //NECESSARY FOR AUTOMATION TESTS, WS ONLY
        
        state.setSeatbeltViolationDistanceX100(500);
        
        ws850.increment_time(5);
        AutomationDeviceEvents.hardLeft(ws850, 105);

        ws850.update_location(new GeoPoint(40.7124, -111.9885), 15);
        ws850.leave_zone(1062);
        ws850.increment_time(5);
        ws850.getState().setSpeedLimit(40);
        ws850.getState().setAvgSpeed(60);
        
        ws850.update_location(new GeoPoint(40.7115, -111.9816), 15);
        
        AutomationDeviceEvents.rfKill(ws850);
        ws850.boundaryChange();
        
        ws850.increment_time(5);
        //ws850.tampering(4);
        
        AutomationDeviceEvents.powerInterruption(ws850);
        
        ws850.last_location(new GeoPoint(40.7115, -111.9817), 15);
        
        AutomationDeviceEvents.statistics(ws850);
        AutomationDeviceEvents.requestSettings(ws850);
        
        state.setHosState(HOSState.OCCUPANT_ON_DUTY);                            //HOS DRIVER ON DUTY AS AN OCCUPANT IN A 
        AutomationDeviceEvents.hosChangeNoGPSLock(ws850, "SALT LAKE CITY, UT");  //VEHICLE
        //note.addAttr(EventAttr.SEATBELT_TOP_SPEED, state.getSeatbeltTopSpeed());
        //note.addAttr(EventAttr.SEATBELT_OUT_DISTANCE, state.getSeatbeltDistanceX100());
        
        //ws850.getState().setSeatBeltTopSpeed(75).setSeatbeltDistanceX100(58);
        
        ws850.turn_key_off(30);
        AutomationDeviceEvents.lowBattery(ws850);
        ws850.power_off_device(900);
    }
}
