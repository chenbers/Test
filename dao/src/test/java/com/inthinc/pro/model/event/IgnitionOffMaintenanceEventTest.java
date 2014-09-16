package com.inthinc.pro.model.event;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;


public class IgnitionOffMaintenanceEventTest {

    @Test
    public void testGetEventType(){
        IgnitionOffMaintenanceEvent ignitionOffMaintenanceEventMalFunctionIndicator = new IgnitionOffMaintenanceEvent();
        ignitionOffMaintenanceEventMalFunctionIndicator.setAttribs(EventAttr.ATTR_MALFUNCTION_INDICATOR_LAMP.getCode()+"="+1);
        assertEquals(EventType.MALFUNCTION_INDICATOR_LAMP,ignitionOffMaintenanceEventMalFunctionIndicator.getEventType());

        IgnitionOffMaintenanceEvent ignitionOffMaintenanceEventRedStop = new IgnitionOffMaintenanceEvent();
        ignitionOffMaintenanceEventRedStop.setAttribs(EventAttr.ATTR_CHECK_ENGINE.getCode()+"="+1);
        assertEquals(EventType.RED_STOP,ignitionOffMaintenanceEventRedStop.getEventType());

        IgnitionOffMaintenanceEvent ignitionOffMaintenanceEventAmber = new IgnitionOffMaintenanceEvent();
        ignitionOffMaintenanceEventAmber.setAttribs(EventAttr.ATTR_CHECK_ENGINE.getCode()+"="+2);
        assertEquals(EventType.AMBER_WARNING,ignitionOffMaintenanceEventAmber.getEventType());

        IgnitionOffMaintenanceEvent ignitionOffMaintenanceProtect = new IgnitionOffMaintenanceEvent();
        ignitionOffMaintenanceProtect.setAttribs("32870=4");
        assertEquals(EventType.PROTECT,ignitionOffMaintenanceProtect.getEventType());

        IgnitionOffMaintenanceEvent ignitionOffMaintenanceRedAndGreen = new IgnitionOffMaintenanceEvent();
        ignitionOffMaintenanceRedAndGreen.setAttribs(EventAttr.ATTR_CHECK_ENGINE.getCode()+"="+5);
        assertEquals(EventType.RED_STOP,ignitionOffMaintenanceRedAndGreen.getEventType());
        
        IgnitionOffMaintenanceEvent ignitionOffMaintenanceUnknown = new IgnitionOffMaintenanceEvent();
        ignitionOffMaintenanceUnknown.setAttribs(EventAttr.ATTR_CHECK_ENGINE.getCode()+"="+14);
        assertEquals(EventType.UNKNOWN_CHECK_ENGINE_LAMP,ignitionOffMaintenanceUnknown.getEventType());
        
        IgnitionOffMaintenanceEvent ignitionOffNotMaintenance = new IgnitionOffMaintenanceEvent();
        assertEquals(EventType.UNKNOWN_MAINTENANCE,ignitionOffNotMaintenance.getEventType());
    }
    
    @Test 
    public void testGetEventType_attribWithNoValue_unknownEvent() {
        IgnitionOffMaintenanceEvent attribWithNoValueEvent = new IgnitionOffMaintenanceEvent();
        int UNKNOWN_ATTR = 47;
        attribWithNoValueEvent.setAttribs(EventAttr.ATTR_CHECK_ENGINE.getCode()+"="+4+";"+UNKNOWN_ATTR+"=");
        assertEquals(EventType.PROTECT,attribWithNoValueEvent.getEventType());
    }
}
