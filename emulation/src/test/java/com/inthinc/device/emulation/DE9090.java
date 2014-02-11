package com.inthinc.device.emulation;

import com.inthinc.device.devices.WaysmartDevice;
import com.inthinc.device.devices.WaysmartDevice.Direction;
import com.inthinc.device.emulation.utils.DeviceState;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.device.objects.AutomationDeviceEvents;
import com.inthinc.pro.automation.enums.AutoSilos;
import com.inthinc.pro.automation.objects.AutomationCalendar;

/**
 * Emulates some things for DE9090.
 */
public class DE9090 {
	
 	public static void main(String args[]) {
        WaysmartDevice tiwi = new WaysmartDevice("555550209999999", "555550201", AutoSilos.DEV, Direction.gprs);
        tiwi.dump_settings();
        DeviceState state = tiwi.getState();
        AutomationCalendar initialTime = new AutomationCalendar();
        tiwi.set_time(initialTime.setDate(1367445641));
        tiwi.getState().setWMP(17116);
        tiwi.firstLocation(new GeoPoint(33.0104, -117.111));
        tiwi.power_on_device();
        tiwi.turn_key_on(15);
        tiwi.getState().setLowIdle(300).setHighIdle(300);
        tiwi.getState().setSpeedLimit(5);
        tiwi.update_location(new GeoPoint(33.0104, -117.111), 15);
        tiwi.update_location(new GeoPoint(33.0104, -117.113), 15);
        AutomationDeviceEvents.panic(tiwi);
        tiwi.flushNotes();
        tiwi.increment_time(20);
        AutomationDeviceEvents.seatbelt(tiwi);
        tiwi.flushNotes();
        AutomationDeviceEvents.hardLeft(tiwi, 105);
        tiwi.flushNotes();
        tiwi.turn_key_off(30);
        AutomationDeviceEvents.lowBattery(tiwi);
        tiwi.power_off_device(900);
    }
}
