package com.inthinc.device.emulation;

import com.inthinc.device.devices.WaysmartDevice;
import com.inthinc.device.devices.WaysmartDevice.Direction;
import com.inthinc.device.objects.AutomationDeviceEvents;
import com.inthinc.pro.automation.enums.AutoSilos;

public class DeviceInstaller {

	public static void main(String[] args) {
		
		WaysmartDevice device = new WaysmartDevice("928020209999999", "979020201", AutoSilos.WEATHERFORD, Direction.sat);
		
		device.getTripTracker().getTrip("898 Canyon Ridge Way, Midvale, UT 84047", "4225 Lake Park Blvd, West Valley City, UT 84120");
		
		device.getState().setVehicleID("INSTALL").setAccountID(184549378);
		
		AutomationDeviceEvents.install(device);
		device.flushNotes();

	}

}
