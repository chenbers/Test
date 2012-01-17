package com.inthinc.pro.selenium.testSuites;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.device.devices.WaysmartDevice;
import com.inthinc.device.devices.WaysmartDevice.Direction;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.device.objects.AutomationDeviceEvents;
import com.inthinc.device.objects.TripTracker;
import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.objects.AutomationCalendar;

@Ignore
public class HOSRuleSets extends WebRallyTest {
    
    @Before
    public void setupPage() {
    }

    @Test
    public void de6587_canadaWaysmartTrip() {
        logger.info("generating canadaWaysmartTrip");
        WaysmartDevice waySmart;
        String satImei = "virt_ws_de6587";
        String mcmID = "virt_MCM39731";
        String driverID = "CANADA";
        String occupantID = "TWO";
        Addresses server = Addresses.QA; 
        AutomationCalendar initialTime = new AutomationCalendar();
        String vehicleID="virtualWS"; 
        int accountID=2;
        
        waySmart = new WaysmartDevice(satImei, mcmID, server, Direction.wifi);
        waySmart.set_time(initialTime);

        TripTracker trip = waySmart.getTripTracker();
        trip.getTrip("Vancouver Canada", "Abbotsford Canada");

        waySmart.setBaseOdometer(5000);
        waySmart.setVehicleID(vehicleID);
        waySmart.setAccountID(accountID);
        AutomationDeviceEvents.install(waySmart);
        waySmart.power_on_device();
        waySmart.logInDriver(driverID);
        
        waySmart.logInOccupant(occupantID);
        waySmart.turn_key_on(15);
        

        Iterator<GeoPoint> itr = trip.iterator();
        
        while (itr.hasNext()){
        	trip.getNextLocation(65, false);
        	waySmart.addLocation();
        }

        waySmart.turn_key_off(15);
        waySmart.power_off_device(100);
    }
    
}