package com.inthinc.device.emulation;

import org.junit.Test;

import com.inthinc.device.devices.WaysmartDevice;
import com.inthinc.device.devices.WaysmartDevice.Direction;
import com.inthinc.device.emulation.utils.DeviceState;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.device.objects.AutomationDeviceEvents;
import com.inthinc.device.objects.TripDriver;
import com.inthinc.pro.automation.enums.AutoSilos;
import com.inthinc.pro.automation.enums.ProductType;
import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.objects.AutomationCalendar;

public class OneWireTests {
    @Test
    public void oneWirePairing(){
        String satImei = "MCMFAKE01";
        String mcmID = "MCMFAKE01";
        String fakeFobID = "this is a fake fobID";
        AutoSilos server = AutoSilos.QA; 
        WaysmartDevice wsAnd = new WaysmartDevice(satImei, mcmID, server, Direction.wifi, ProductType.WAYSMART_850);
        wsAnd.set_time(new AutomationCalendar());
        GeoPoint location = new GeoPoint("980 N 1050 E, Pleasant Grove, UT 84062");
        
        String start = "980 N 1050 E, Pleasant Grove, UT 84062";
        String mid = "815 N 1020 E, Pleasant Grove, UT 84062";
        String stop = "1002 N 1020 E, Pleasant Grove, UT 84062";
        TripDriver driver = new TripDriver(wsAnd);
        
        DeviceState state = new DeviceState(satImei, ProductType.WAYSMART_850);
        
        driver.addToTrip(start, mid);
        driver.addToTrip(mid, stop);
        driver.addToTrip(stop, start);
        
        wsAnd.addEvent(AutomationDeviceEvents.pairOneWire(state, location, fakeFobID));
        driver.run();
    }
    
    @Test
    public void oneWirePairing_tooBig_shouldNotPair(){
        String satImei = "MCMFAKE02";
        String mcmID = "MCMFAKE02";
        String fortyCharFobID = "0123456789012345678901234567890123456789";
        AutoSilos server = AutoSilos.QA; 
        WaysmartDevice wsAnd = new WaysmartDevice(satImei, mcmID, server, Direction.wifi, ProductType.WAYSMART_850);
        wsAnd.set_time(new AutomationCalendar());
        GeoPoint location = new GeoPoint("980 N 1050 E, Pleasant Grove, UT 84062");
        
        String start = "980 N 1050 E, Pleasant Grove, UT 84062";
        String mid = "815 N 1020 E, Pleasant Grove, UT 84062";
        String stop = "1002 N 1020 E, Pleasant Grove, UT 84062";
        TripDriver driver = new TripDriver(wsAnd);
        
        DeviceState state = new DeviceState(satImei, ProductType.WAYSMART_850);
        
        driver.addToTrip(start, mid);
        driver.addToTrip(mid, stop);
        driver.addToTrip(stop, start);
        
        wsAnd.addEvent(AutomationDeviceEvents.pairOneWire(state, location, fortyCharFobID));
        driver.run();
    }
}
