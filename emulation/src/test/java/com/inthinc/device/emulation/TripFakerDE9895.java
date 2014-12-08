package com.inthinc.device.emulation;

import com.inthinc.device.devices.WaysmartDevice;
import com.inthinc.device.devices.WaysmartDevice.Direction;
import com.inthinc.device.emulation.utils.DeviceState;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.device.objects.AutomationDeviceEvents;
import com.inthinc.pro.automation.enums.AutoSilos;
import com.inthinc.pro.automation.enums.ProductType;
import com.inthinc.pro.automation.objects.AutomationCalendar;

import java.util.Date;

public class TripFakerDE9895 {


    public static void main(String args[]) {
        AutoSilos connect = AutoSilos.QA;
        WaysmartDevice tiwi = new WaysmartDevice("300034012746360", "MCM070785", connect, Direction.gprs, ProductType.WAYSMART);
        tiwi.dump_settings();
        DeviceState state = tiwi.getState();
        AutomationCalendar initialTime = new AutomationCalendar();
        tiwi.setVehicleID("59124");
        tiwi.set_time(initialTime.setDate(new Date().getTime()));
        tiwi.getState().setWMP(17116);
        tiwi.getState().setLowIdle(300).setHighIdle(300);
        tiwi.getState().setSpeedLimit(5);
        tiwi.firstLocation(new GeoPoint(33.0104, -117.111));
        tiwi.power_on_device();
        AutomationDeviceEvents.backingEvent(tiwi);
        tiwi.turn_key_on(15);
        tiwi.update_location(new GeoPoint(0, 0), 18);
        tiwi.turn_key_off(30);
        tiwi.power_off_device(900);
        tiwi.flushNotes();
    }
}