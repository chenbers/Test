package com.inthinc.device.emulation;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.device.devices.WaysmartDevice;
import com.inthinc.device.devices.WaysmartDevice.Direction;
import com.inthinc.device.emulation.enums.DeviceEnums.HosRuleSet;
import com.inthinc.device.emulation.utils.DeviceState;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.device.objects.AutomationDeviceEvents;
import com.inthinc.pro.automation.enums.AutoSilos;
import com.inthinc.pro.automation.objects.AutomationCalendar;

public class HOSTest {
    
    // uses hostest.xml ws device, etc.
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd hh:mm:ss").withZone(DateTimeZone.UTC);

    public static void main(String args[]) {
        
        
//        DateTime testDateTime = new DateTime(itData.startDateInSec*1000l, DateTimeZone.UTC);
        DateTime testDateTime = new DateTime(1389337200000l, DateTimeZone.UTC);

//        WaysmartDevice tiwi = new WaysmartDevice("953259629433625", "MCM9532525075", AutoSilos.DEV, Direction.gprs);
        WaysmartDevice tiwi = new WaysmartDevice("353259629433625", "MCM9532525075", AutoSilos.DEV, Direction.sat);
//        tiwi.dump_settings();
        DeviceState state = tiwi.getState();
        AutomationCalendar initialTime = new AutomationCalendar();
        tiwi.set_time(initialTime.setDate(1389578400));

        //        tiwi.increment_time(20);
        tiwi.getState().setWMP(17116);
        tiwi.firstLocation(new GeoPoint(33.0104, -117.111));
        tiwi.power_on_device();
        tiwi.turn_key_on(15);
        
        state.setEmployeeID("E532576098");
        state.setHosRuleSet(HosRuleSet.US_8DAY);
//        AutomationDeviceEvents.newDriverHosRule(tiwi);
//        AutomationDeviceEvents.newDriverHosRuleEx(tiwi, 1389765400);      // no changes
//        AutomationDeviceEvents.newDriverHosRuleEx(tiwi, 1389366000);        // 1 change  (deleted record)
        
        int startTime = (int)(testDateTime.toDate().getTime()/1000);
        int endTime = (int)(testDateTime.plusHours(1).toDate().getTime()/1000);
        System.out.println("start: " + startTime + " end: " + endTime);
//        AutomationDeviceEvents.newDriverHosRuleEx(tiwi, 1389366000, startTime, endTime, 4, 33);        // bad checksum
        AutomationDeviceEvents.newDriverHosRuleEx(tiwi, 1389366000, startTime, endTime, 4, -40);        // good checksum
        
        
        tiwi.turn_key_off(30);
        tiwi.power_off_device(900);
    }
    
}
