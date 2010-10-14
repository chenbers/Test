package com.inthinc.pro.backing;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.model.configurator.VehicleSetting;
@Ignore
public class VehicleSettingTest {

    VehicleSetting vehicleSetting;
    
    @Before
    public void setUp() throws Exception {
        
        vehicleSetting = new VehicleSetting();
    }
    @Test
    public final void testCombineSettings() {
        Map<Integer, String> actualMap = new HashMap<Integer,String>();
        actualMap.put(3, "1");
        actualMap.put(6, "www.tiwipro.com");
        actualMap.put(7, "safety.t-mobile.com");
        actualMap.put(8, "15");
        actualMap.put(9, "0");
        actualMap.put(10, "1");
        actualMap.put(11, "1");
        actualMap.put(12, "1");
        actualMap.put(13, "1");
        actualMap.put(14, "78.0");
        actualMap.put(21, "15");
        actualMap.put(22, "1");
        actualMap.put(23, "1");
        actualMap.put(24, "5000");
        actualMap.put(25, "1");
        actualMap.put(26, "1");
        actualMap.put(27, "10");
        actualMap.put(28, "10.0");
        actualMap.put(29, "1");
        actualMap.put(30, "1");
        actualMap.put(31, "1");
        actualMap.put(32, "0");
        actualMap.put(36, "900");
        actualMap.put(41, "8090");
        actualMap.put(42, "4");
        actualMap.put(47, "0");
        actualMap.put(48, "http://www.iwiglobal.com");
        actualMap.put(49, "asip");
        actualMap.put(54, "11000");
        actualMap.put(58, "1");
        actualMap.put(59, "0");
        actualMap.put(60, "8014134361");
        actualMap.put(61, "0");
        actualMap.put(70, "0");
        actualMap.put(76, "-1000");
        actualMap.put(77, "1b890ee09ed078dc01f3e54a26e32176");
        actualMap.put(83, "15");
        actualMap.put(85, "5 10 15 20 25 30 35 40 45 50 55 60 65 70 75");
        actualMap.put(98, "1");
        actualMap.put(100, "0");
        actualMap.put(101, "1");
        actualMap.put(102, "0");
        actualMap.put(103, "1");
        actualMap.put(105, "1");
        actualMap.put(106, "60");
        actualMap.put(109, "1");
        actualMap.put(110, "0");
        actualMap.put(113, "25");
        actualMap.put(114, "250");
        actualMap.put(117, "-500");
        actualMap.put(118, "None");
        actualMap.put(119, "None");
        actualMap.put(120, "None");
        actualMap.put(121, "tiwiftp.tiwi.com");
        actualMap.put(122, "tiwibox");
        actualMap.put(123, "Phohthed8sheighoh");
        actualMap.put(124, "1");
        actualMap.put(126, "0");
        actualMap.put(128, "0");
        actualMap.put(132, "1");
        actualMap.put(133, "1");
        actualMap.put(135, "7");
        actualMap.put(136, "20");
        actualMap.put(137, "10");
        actualMap.put(138, "28000");
        actualMap.put(139, "300");
        actualMap.put(140, "60");
        actualMap.put(141, "75");
        actualMap.put(142, "1");
        actualMap.put(143, "37433");
        actualMap.put(144, "symphony-usa.eride.com");
        actualMap.put(145, "10800");
        actualMap.put(146, "2");
        actualMap.put(147, "3");
        actualMap.put(148, "0");
        actualMap.put(149, "60");
        actualMap.put(150, "2");
        actualMap.put(152, "5");
        actualMap.put(154, "110");
        actualMap.put(155, "1");
        actualMap.put(156, "0");
        actualMap.put(157, "0 0 99");
        actualMap.put(158, "0 0 99");
        actualMap.put(159, "0 0 99");
        actualMap.put(160, "0 0 99 250");
        actualMap.put(161, "0");
        actualMap.put(162, "0");
        actualMap.put(163, "0");
        actualMap.put(164, "48");
        actualMap.put(165, "1");
        actualMap.put(166, "0");
        actualMap.put(167, "0");
        actualMap.put(168, "0");
        actualMap.put(169, "1");
        actualMap.put(170, "0");
        actualMap.put(171, "0");
        actualMap.put(172, "0");
        actualMap.put(174, "8901260760004373325");
        actualMap.put(175, "0");
        actualMap.put(176, "0");
        Map<Integer, String> desiredMap = new HashMap<Integer,String>();
        vehicleSetting.setActual(actualMap);
        vehicleSetting.setDesired(desiredMap);
        
        vehicleSetting.combineSettings();
        vehicleSetting.clearCombinedSettings();
        
        vehicleSetting.setDesired(null);
        vehicleSetting.combineSettings();
    }

}
