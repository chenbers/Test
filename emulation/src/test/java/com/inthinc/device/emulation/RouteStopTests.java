package com.inthinc.device.emulation;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.inthinc.device.devices.WaysmartDevice;
import com.inthinc.device.devices.WaysmartDevice.Direction;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.device.objects.AutomationDeviceEvents;
import com.inthinc.pro.automation.enums.AutoSilos;
import com.inthinc.pro.automation.enums.ProductType;

public class RouteStopTests {
    protected static final Logger logger        = LogManager.getLogger(OneWireTests.class);
    
    @Test
    public void routeStopArrival(){
        String imei = "MCMFAKE01";
        String actionID = "12345";
        WaysmartDevice ws = new WaysmartDevice(imei, imei, AutoSilos.QA, Direction.gprs, ProductType.WAYSMART_850);
        ws.firstLocation(new GeoPoint());
        AutomationDeviceEvents.routeStopArrival(ws, actionID);
        ws.flushNotes();
    }

    @Test
    public void routeStopDeparture(){
        String imei = "MCMFAKE01";
        String actionID = "12345";
        WaysmartDevice ws = new WaysmartDevice(imei, imei, AutoSilos.QA, Direction.gprs, ProductType.WAYSMART_850);
        ws.firstLocation(new GeoPoint());
        AutomationDeviceEvents.routeStopDeparture(ws, actionID);
        ws.flushNotes();
    }

}
