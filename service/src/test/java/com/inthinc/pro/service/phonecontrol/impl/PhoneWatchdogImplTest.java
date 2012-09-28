package com.inthinc.pro.service.phonecontrol.impl;

import mockit.Deencapsulation;
import mockit.Mocked;
import mockit.Mockit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.service.phonecontrol.dao.DriverPhoneDAO;

/**
 * Unit test for PhoneWatchdogImpl
 * 
 * @author dcueva
 * 
 */
public class PhoneWatchdogImplTest {

    private static final Integer DRIVER_ID[] = { new Integer(6286), new Integer(1689) };
    private static final Integer SECONDS_AGO = 100;

    @Mocked
    private DriverDAO driverDAO;

    @Mocked
    private DriverPhoneDAO driverPhoneDAO;

    @Mocked(methods = "<clinit>", inverse = true)
    private PhoneControlMovementEventHandler phoneControl;
    // Exclude static initializations from being mocked
    // https://code.google.com/p/jmockit/issues/detail?id=74

    private PhoneWatchdogImpl watchdogSUT = new PhoneWatchdogImpl();

    @Before
    public void setUp() {
        Deencapsulation.setField(watchdogSUT, "driverPhoneDAO", driverPhoneDAO);
        Deencapsulation.setField(watchdogSUT, "phoneControl", phoneControl);
        watchdogSUT.setSecondsAgoEvents(SECONDS_AGO);
    }

    @After
    public void tearDown() {
        Mockit.tearDownMocks();
    }

    /**
     * Happy path: One driver with disabled cell phone and No events. Result: cell phone enabled.
     */
    @Test
    public void testEnablePhonesWhenLostCommNoEvents() {}

    /**
     * One driver with disabled cell phone and One event. Result: cell phone NOT enabled.
     */
    @Test
    public void testEnablePhonesWhenLostCommOneEvent() {}

    /**
     * One driver with disabled cell phone and One event. A second driver with disabled cell phone and No events. Result: cell phone NOT enabled for first, but enabled for second.
     */
    @Test
    public void testEnablePhonesWhenLostCommZeroAndOneEvents() {}
}
