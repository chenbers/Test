package com.inthinc.pro.model;

import static org.junit.Assert.assertEquals;

import java.util.Collections;

import org.junit.Test;

public class AlertMessageTypeTest {
    
    @Test
    public void testName(){
        assertEquals(AlertMessageType.ALERT_TYPE_SPEEDING.name(),"ALERT_TYPE_SPEEDING");
    }

    @Test
    public void testCode(){
        assertEquals(AlertMessageType.ALERT_TYPE_SPEEDING.ordinal(),0);
    }
    @Test
    public void testAlertMessageTypeMapping(){
        
        RedFlagAlert rfa = new RedFlagAlert();
        Long value = 2l;
        rfa.setAlertTypeMask(value);
        assertEquals(rfa.getTypes().get(0),AlertMessageType.ALERT_TYPE_SPEEDING); 
        value = 8l;
        rfa.setAlertTypeMask(value);
        assertEquals(rfa.getTypes().get(0),AlertMessageType.ALERT_TYPE_SEATBELT); 
        value = AlertMessageType.ALERT_TYPE_WITNESS_UPDATED.getBitMask() +
        AlertMessageType.ALERT_TYPE_ZONES_CURRENT.getBitMask() + 
        AlertMessageType.ALERT_TYPE_WITNESS_HEARTBEAT_VIOLATION.getBitMask();
        rfa.setAlertTypeMask(value);
        assertEquals(rfa.getTypes().get(0),AlertMessageType.ALERT_TYPE_WITNESS_UPDATED); 
        assertEquals(rfa.getTypes().get(1),AlertMessageType.ALERT_TYPE_ZONES_CURRENT); 
        assertEquals(rfa.getTypes().get(2),AlertMessageType.ALERT_TYPE_WITNESS_HEARTBEAT_VIOLATION); 

        value = 0l;
        value = rfa.getAlertTypeMask();
        assertEquals(value.longValue(),AlertMessageType.ALERT_TYPE_WITNESS_UPDATED.getBitMask() +
                AlertMessageType.ALERT_TYPE_ZONES_CURRENT.getBitMask() + 
                AlertMessageType.ALERT_TYPE_WITNESS_HEARTBEAT_VIOLATION.getBitMask());
        
        rfa.setAlertTypeMask(0L);
        assertEquals(rfa.getTypes(),Collections.emptyList()); 
     }
}
