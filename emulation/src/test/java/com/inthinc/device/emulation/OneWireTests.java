package com.inthinc.device.emulation;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.device.devices.WaysmartDevice;
import com.inthinc.device.devices.WaysmartDevice.Direction;
import com.inthinc.device.emulation.utils.DeviceState;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.device.objects.AutomationDeviceEvents;
import com.inthinc.device.objects.TripDriver;
import com.inthinc.pro.automation.enums.AutoSilos;
import com.inthinc.pro.automation.enums.ProductType;

public class OneWireTests {
    protected static final Logger logger        = LogManager.getLogger(OneWireTests.class);
    
    @Test
    public void oneWirePairing_correct_checkFwdCmd(){
        String imei = "MCMFAKE01";
        String fobID = "MCMFAKE01";
        String empID = "FAKE1WRE01";
        WaysmartDevice ws = new WaysmartDevice(imei, imei, AutoSilos.QA, Direction.gprs, ProductType.WAYSMART_850);
        ws.firstLocation(new GeoPoint());
        ws.getState().setEmployeeID(empID);
        AutomationDeviceEvents.pairOneWire(ws, fobID);
        AutomationDeviceEvents.requestFobInfo(ws, fobID);
        ws.flushNotes();
    }

}
