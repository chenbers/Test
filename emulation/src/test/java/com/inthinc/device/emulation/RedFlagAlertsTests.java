package com.inthinc.device.emulation;

import com.inthinc.device.devices.WaysmartDevice;
import com.inthinc.device.devices.WaysmartDevice.Direction;
import com.inthinc.device.emulation.utils.DeviceState;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.device.objects.AutomationDeviceEvents;
import com.inthinc.device.objects.TripDriver;
import com.inthinc.pro.automation.enums.AutoSilos;
import com.inthinc.pro.automation.enums.ProductType;

public class RedFlagAlertsTests {
    
    public static void main(String[] args) {
        
        // Set the device up...
        String imei = "356938035643809";
        String mcmid = "mcmid";
        
        WaysmartDevice wsd = new WaysmartDevice(imei, mcmid, AutoSilos.DEV, Direction.gprs);
        
        // Set route waypoints
        String addressSuffix = ", Taylorsville, UT 84129";
        String home = "2840 West Allred Circle" + addressSuffix;
        String loc1 = "5288 S 3200 W" + addressSuffix;
        String loc2 = "5382 S 3200 W" + addressSuffix;
        String loc3 = "5390 Appian Way" + addressSuffix;
        String loc4 = "5276 Appian Way" + addressSuffix;
        
        // Instantiate the driver
        TripDriver driver = new TripDriver(wsd);
        
        // Add legs to the trip
        driver.addToTrip(home, loc1);
        driver.addToTrip(loc1, loc2);
        driver.addToTrip(loc2, loc3);
        driver.addToTrip(loc3, loc4);
        driver.addToTrip(loc4, home);
        
        // Create a device state with a speeding violation
        DeviceState deviceState = new DeviceState(imei, ProductType.WAYSMART_850);
        
        deviceState.setTopSpeed(103);
        deviceState.setSpeedingDistanceX100(200);
        deviceState.setSpeedingSpeedLimit(25);
        
        // Add a speeding violation to the trip
        driver.addEvent(50, AutomationDeviceEvents.speeding(deviceState, new GeoPoint(40.653, -111.965)));
        
        // Run the trip
        driver.start();
        
    }
    
}
