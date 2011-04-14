package com.inthinc.pro.selenium.testSuites;

import org.junit.Test;

import com.inthinc.pro.automation.device_emulation.TiwiProDevice;

public class HanSoloTrip {
    private TiwiProDevice tiwi;
    
    @Test
    public void hanSolosFirstTrip() {
        tiwi = new TiwiProDevice("123456789012345", "QA");
        tiwi.set_time(1302448797);
        tiwi.set_WMP(17116);
        tiwi.set_location(33.0104, -117.111);
        tiwi.power_on_device();
        tiwi.set_ignition(15);
        tiwi.add_location();
        tiwi.set_location(33.0104, -117.113, 10);
        
        tiwi.add_note_event(108, 5, 5);
        tiwi.set_location(33.01, -117.113, 15);
        tiwi.add_location();
        tiwi.set_location(33.0097, -117.1153, 15);
        tiwi.add_location();
        tiwi.set_location(33.015, -117.116, 15);
        tiwi.add_location();
        tiwi.enter_zone(2);
        tiwi.set_location(33.0163, -117.1159, 15);
        tiwi.add_location();
        tiwi.set_location(33.018, -117.1153, 15);
        tiwi.add_location();
        tiwi.set_location(33.0188, -117.118, 15);
        tiwi.add_location();
        tiwi.set_location(33.0192, -117.1199, 15);
        tiwi.add_location();
        tiwi.set_location(33.021, -117.119, 15);
        tiwi.add_location();
        tiwi.set_location(33.022, -117.114, 15);
        tiwi.add_location();
        tiwi.set_location(33.0205, -117.111, 15);
        tiwi.add_location();
        tiwi.set_location(33.02, -117.109, 15);
        tiwi.add_location();
        tiwi.set_location(33.02, -117.108, 15);
        tiwi.add_location();
        tiwi.set_location(33.022, -117.104, 15);
        tiwi.add_location();
        tiwi.set_location(33.0217, -117.103, 15);
        tiwi.add_location();
        tiwi.set_location(33.0213, -117.1015, 15);
        tiwi.add_location();
        tiwi.set_location(33.0185, -117.1019, 15);
        tiwi.add_location();
        tiwi.set_location(33.017, -117.102, 15);
        tiwi.add_location();
        tiwi.set_location(33.015, -117.1032, 15);
        tiwi.add_location();
        tiwi.set_location(33.013, -117.105, 15);
        tiwi.add_location();
        tiwi.set_location(33.011, -117.106, 15);
        tiwi.add_location();
        tiwi.set_location(33.0108, -117.108, 15);
        tiwi.add_location();
        tiwi.set_location(33.0108, -117.109, 15);
        tiwi.add_location();
        tiwi.leave_zone(2);
        tiwi.set_location(33.0106, -117.11, 15);
        tiwi.add_location();
        tiwi.set_location(33.0104, -117.111, 15);
        tiwi.add_location();
        tiwi.add_stats();
        tiwi.logout_driver(null, 890, 204, 200);
        tiwi.set_ignition(30);
        tiwi.power_off_device(900);
    }
}
