package com.inthinc.pro.selenium.testSuites;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.automation.deviceTrips.HanSoloTrip;
import com.inthinc.pro.automation.device_emulation.WaysmartDevice;
import com.inthinc.pro.automation.enums.Addresses;

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
        Long currentTime = System.currentTimeMillis()/1000;
        Integer initialTime = currentTime.intValue();
        String vehicleID="virtualWS"; 
        int accountID=2;
        
        waySmart = new WaysmartDevice(satImei, mcmID, server);
        waySmart.set_time(initialTime);
        waySmart.set_location(33.0104, -117.111);
        waySmart.setBaseOdometer(5000);
        waySmart.addInstallEvent(vehicleID, accountID);
        waySmart.power_on_device();
        waySmart.logInDriver(driverID);
        
        waySmart.logInOccupant(occupantID);
        waySmart.turn_key_on(15);

        HanSoloTrip.generateTrip("Vancouver Canada", "Abbotsford Canada", waySmart);
        
        waySmart.turn_key_off(15);
        waySmart.power_off_device(100);
    }
    
}