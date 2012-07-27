package com.inthinc.device.emulation;

import com.inthinc.device.devices.WaysmartDevice;
import com.inthinc.device.devices.WaysmartDevice.Direction;
import com.inthinc.device.objects.AutomationDeviceEvents;
import com.inthinc.pro.automation.enums.AutoSilos;

public class DeviceInstaller {

	public static void main(String[] args) {
		
		WaysmartDevice device = new WaysmartDevice("300235555777777", "MCM013795", AutoSilos.MY, Direction.sat);
		
		device.getTripTracker().getTrip("898 Canyon Ridge Way, Midvale, UT 84047", "4225 Lake Park Blvd, West Valley City, UT 84120");
		
		device.getState().setVehicleID("RELEASETEST").setAccountID(1);
		
		AutomationDeviceEvents.install(device);
		device.flushNotes();

	}

}
