package com.inthinc.pro.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class IgnitionOffEventTypeTest {

    @Test
    public void testGetCode() {
        String expectationRedStop = "RED_STOP";
        IgnitionOffEventType testIngRedStop = IgnitionOffEventType.getEventType(1);
        assertEquals(testIngRedStop.toString(), expectationRedStop);
        assertEquals(testIngRedStop.getCode(), Integer.valueOf(1));

        String expectationAmber = "AMBER_WARNING";
        IgnitionOffEventType testIngAmber = IgnitionOffEventType.getEventType(2);
        assertEquals(testIngAmber.toString(), expectationAmber);
        assertEquals(testIngAmber.getCode(), Integer.valueOf(2));


        String expectationProtect = "PROTECT";
        IgnitionOffEventType testIngProtect = IgnitionOffEventType.getEventType(3);
        assertEquals(testIngProtect.toString(), expectationProtect);
        assertEquals(testIngProtect.getCode(), Integer.valueOf(3));

        String expectationIndicatorLamp = "MALFUNCTION_INDICATOR_LAMP";
        IgnitionOffEventType testIngIndicatorLamp = IgnitionOffEventType.getEventType(9);
        assertEquals(testIngIndicatorLamp.toString(), expectationIndicatorLamp);
        assertEquals(testIngIndicatorLamp.getCode(), Integer.valueOf(9));


        String expectationIgnitionOff = "IGNITION_OFF";
        IgnitionOffEventType testIgnitionOff = IgnitionOffEventType.getEventType(20);
        assertEquals(testIgnitionOff.toString(), expectationIgnitionOff);
        assertEquals(testIgnitionOff.getCode(), Integer.valueOf(20));
    }
}
