package com.inthinc.pro.model.event;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;


public class IgnitionOffMaintenanceEventTest {

    @Test
    public void testGetEventType(){
        IgnitionOffMaintenanceEvent ignitionOffMaintenanceEventMalFunctionIndicator = new IgnitionOffMaintenanceEvent();
        ignitionOffMaintenanceEventMalFunctionIndicator.setAttribs("32869=1");
        assertEquals(EventType.MALFUNCTION_INDICATOR_LAMP,ignitionOffMaintenanceEventMalFunctionIndicator.getEventType());

        IgnitionOffMaintenanceEvent ignitionOffMaintenanceEventRedStop = new IgnitionOffMaintenanceEvent();
        ignitionOffMaintenanceEventRedStop.setAttribs("32870=1");
        assertEquals(EventType.RED_STOP,ignitionOffMaintenanceEventRedStop.getEventType());

        IgnitionOffMaintenanceEvent ignitionOffMaintenanceEventAmber = new IgnitionOffMaintenanceEvent();
        ignitionOffMaintenanceEventAmber.setAttribs("32870=2");
        assertEquals(EventType.AMBER_WARNING,ignitionOffMaintenanceEventAmber.getEventType());

        IgnitionOffMaintenanceEvent ignitionOffMaintenanceProtect = new IgnitionOffMaintenanceEvent();
        ignitionOffMaintenanceProtect.setAttribs("32870=4");
        assertEquals(EventType.PROTECT,ignitionOffMaintenanceProtect.getEventType());

        IgnitionOffMaintenanceEvent ignitionOffNotMaintenance = new IgnitionOffMaintenanceEvent();
        assertEquals(EventType.IGNITION_OFF,ignitionOffNotMaintenance.getEventType());

    }
}
