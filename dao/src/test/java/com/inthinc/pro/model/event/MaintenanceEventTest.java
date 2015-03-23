package com.inthinc.pro.model.event;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class MaintenanceEventTest {

    @Test
    public void testGetEventType(){
        MaintenanceEvent maintenanceEventBatteryVoltage = new MaintenanceEvent();
        maintenanceEventBatteryVoltage.setAttribs("8303=1");
        assertEquals(EventType.BATTERY_VOLTAGE,maintenanceEventBatteryVoltage.getEventType());

        MaintenanceEvent maintenanceEventEngineTemp = new MaintenanceEvent();
        maintenanceEventEngineTemp.setAttribs("16471=1");
        assertEquals(EventType.ENGINE_TEMP,maintenanceEventEngineTemp.getEventType());

        MaintenanceEvent maintenanceEventTransmissionTemp = new MaintenanceEvent();
        maintenanceEventTransmissionTemp.setAttribs("16472=1");
        assertEquals(EventType.TRANSMISSION_TEMP,maintenanceEventTransmissionTemp.getEventType());

        MaintenanceEvent maintenanceEventFlowRate = new MaintenanceEvent();
        maintenanceEventFlowRate.setAttribs("16473=1");
        assertEquals(EventType.DPF_FLOW_RATE,maintenanceEventFlowRate.getEventType());

        MaintenanceEvent maintenanceEventOilPresure = new MaintenanceEvent();
        maintenanceEventOilPresure.setAttribs("16474=1");
        assertEquals(EventType.OIL_PRESSURE,maintenanceEventOilPresure.getEventType());

        MaintenanceEvent maintenanceEventEngineHoursX100 = new MaintenanceEvent();
        maintenanceEventEngineHoursX100.setAttribs("240=1");
        assertEquals(EventType.ATTR_ENGINE_HOURS,maintenanceEventEngineHoursX100.getEventType());

        MaintenanceEvent maintenanceEventOdometer = new MaintenanceEvent();
        maintenanceEventOdometer.setAttribs("32801=1");
        assertEquals(EventType.ODOMETER,maintenanceEventOdometer.getEventType());

    }
}
