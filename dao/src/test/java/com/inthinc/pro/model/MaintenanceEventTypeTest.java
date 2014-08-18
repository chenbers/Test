package com.inthinc.pro.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class MaintenanceEventTypeTest {

    @Test
    public void testGetCode() {
        String expectationBatteryVoltage = "BATTERY_VOLTAGE";
        MaintenanceEventType testBatteryVoltage = MaintenanceEventType.getEventType(4);
        assertEquals(testBatteryVoltage.toString(), expectationBatteryVoltage);
        assertEquals(testBatteryVoltage.getCode(), Integer.valueOf(4));

        String expectationEngineTemp = "ENGINE_TEMP";
        MaintenanceEventType testEngineTemp = MaintenanceEventType.getEventType(5);
        assertEquals(testEngineTemp.toString(), expectationEngineTemp);
        assertEquals(testEngineTemp.getCode(), Integer.valueOf(5));


        String expectationTransmissionTemp = "TRANSMISSION_TEMP";
        MaintenanceEventType testTransmissionTemp = MaintenanceEventType.getEventType(6);
        assertEquals(testTransmissionTemp.toString(), expectationTransmissionTemp);
        assertEquals(testTransmissionTemp.getCode(), Integer.valueOf(6));

        String expectationFlowRate = "DPF_FLOW_RATE";
        MaintenanceEventType testFlowRate = MaintenanceEventType.getEventType(7);
        assertEquals(testFlowRate.toString(), expectationFlowRate);
        assertEquals(testFlowRate.getCode(), Integer.valueOf(7));


        String expectationOilPressure = "OIL_PRESSURE";
        MaintenanceEventType testOilPressure = MaintenanceEventType.getEventType(8);
        assertEquals(testOilPressure.toString(), expectationOilPressure);
        assertEquals(testOilPressure.getCode(), Integer.valueOf(8));

        String expectationEngineHours = "ATTR_ENGINE_HOURS";
        MaintenanceEventType testEngineHours = MaintenanceEventType.getEventType(10);
        assertEquals(testEngineHours.toString(), expectationEngineHours);
        assertEquals(testEngineHours.getCode(), Integer.valueOf(10));

        String expectationOdometer = "ODOMETER";
        MaintenanceEventType testOdometer = MaintenanceEventType.getEventType(11);
        assertEquals(testOdometer.toString(), expectationOdometer);
        assertEquals(testOdometer.getCode(), Integer.valueOf(11));
    }
}
