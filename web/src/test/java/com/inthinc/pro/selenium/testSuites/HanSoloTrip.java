package com.inthinc.pro.selenium.testSuites;

import com.inthinc.pro.automation.device_emulation.TiwiProDevice;
import com.inthinc.pro.automation.enums.Addresses;

public class HanSoloTrip {
    private TiwiProDevice tiwi;

    public void hanSolosFirstTrip(String IMEI, Addresses server, Integer initialTime) {
        tiwi = new TiwiProDevice(IMEI, server);
        tiwi.set_time( initialTime +60);
        tiwi.set_WMP(17116);
        tiwi.set_location(33.0104, -117.111);
        tiwi.power_on_device();
        tiwi.set_ignition(15);
        tiwi.update_location(33.0104, -117.111, 15);
        tiwi.update_location(33.0104, -117.113, 15);
        
        tiwi.add_noDriver();
        
        tiwi.add_seatBelt(50, 50, 50);
        tiwi.add_note_event(5, 105, 5);

        tiwi.update_location(33.01, -117.113, 15);
        tiwi.update_location(33.0097, -117.1153, 15);
        tiwi.update_location(33.015, -117.116, 15);

        tiwi.enter_zone(2);
        
        tiwi.update_location(33.0163, -117.1159, 15);
        tiwi.update_location(33.018, -117.1153, 15);
        tiwi.update_location(33.0188, -117.118, 15);
        tiwi.update_location(33.0192, -117.1199, 15);
        tiwi.update_location(33.021, -117.119, 15);
        tiwi.update_location(33.022, -117.114, 15);
        tiwi.update_location(33.0205, -117.111, 15);
        
        tiwi.tampering(4);
        
        tiwi.update_location(33.02, -117.109, 15);
        tiwi.update_location(33.02, -117.108, 15);
        tiwi.update_location(33.022, -117.104, 15);
        tiwi.update_location(33.0217, -117.103, 15);
        tiwi.update_location(33.0213, -117.1015, 15);
        tiwi.update_location(33.0185, -117.1019, 15);
        tiwi.update_location(33.017, -117.102, 15);
        tiwi.update_location(33.015, -117.1032, 15);
        tiwi.update_location(33.013, -117.105, 15);
        tiwi.update_location(33.011, -117.106, 15);
        tiwi.update_location(33.0108, -117.108, 15);
        tiwi.update_location(33.0108, -117.109, 15);

        tiwi.leave_zone(2);

        tiwi.update_location(33.0106, -117.11, 15);
        tiwi.last_location(33.0104, -117.111, 15);
        
        tiwi.add_stats();
        tiwi.logout_driver(null, 890, 204, 200);
        tiwi.set_ignition(30);
        tiwi.add_lowBattery();
        tiwi.power_off_device(900);
    }
    
    public static void main(String[] args){
        HanSoloTrip trip = new HanSoloTrip();
        Long currentTime = System.currentTimeMillis()/1000;
        Integer initialTime = currentTime.intValue();
        Addresses address;
        String imei;
//        imei = "444444444444444";  address=Addresses.QA; initialTime = 1304010578;// vehicleID=7293 in QA
//        imei = "999456789012345";  address=Addresses.SCHLUMBERGER; initialTime = 1304010578;// vehicleID=150994955 in QA
//        imei = "thisisajavadevice"; address=Addresses.CHEVRON; initialTime=1304113419;// vehicleID=117441441   deviceID=117441936 in QA
//        imei = "111111111111111"; address=Addresses.PROD; initialTime=1304113419;
        imei = "FAKEIMEIFORTINA"; address=Addresses.WEATHORFORD;initialTime=1306443089;// vehicleID=
        
        
        trip.hanSolosFirstTrip( imei, address, initialTime);
    }
}
