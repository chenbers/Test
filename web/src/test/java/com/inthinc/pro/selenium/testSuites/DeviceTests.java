package com.inthinc.pro.selenium.testSuites;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.inthinc.pro.automation.device_emulation.TiwiProDevice;
import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.utils.CreateHessian;
import com.inthinc.pro.dao.hessian.proserver.SiloService;


public class DeviceTests extends WebRallyTest {
    
    @Test
    public void forwardCommandLimit(){
        setBuildNumber("");
        String imei = "483548625738283";   
        int vehicleID=5096;
        int deviceID=364;
        Addresses server = Addresses.QA;
        try{
            for (int i=0; i<20; i++) {
                Map<String, Object> fwdMap = new HashMap<String, Object>();
                fwdMap.put("cmd", 2011);
                fwdMap.put("data", 900+i);
                fwdMap.put("status", 1);
                fwdMap.put("cmd", System.currentTimeMillis()/1000);
                SiloService portalProxy = new CreateHessian().getPortalProxy(server);
                portalProxy.queueFwdCmd(deviceID, fwdMap);
            }
            TiwiProDevice tiwi = new TiwiProDevice(imei, server);
            tiwi.nonTripNote(System.currentTimeMillis()/1000, 9, 8, 55.0, 55.0, 5, 5);
            tiwi.add_location();
            tiwi.flushNotes();
        } catch (Exception e){
            
        }
    }

}
