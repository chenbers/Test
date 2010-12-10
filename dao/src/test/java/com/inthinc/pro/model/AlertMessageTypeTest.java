package com.inthinc.pro.model;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.Set;

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
        Long value = 1l;
        rfa.setAlertTypeMask(value);
        assertEquals(rfa.getTypes().get(0),AlertMessageType.ALERT_TYPE_SPEEDING); 
        value = 4l;
        rfa.setAlertTypeMask(value);
        assertEquals(rfa.getTypes().get(0),AlertMessageType.ALERT_TYPE_SEATBELT); 
        rfa.setAlertTypeMask(2l);
        assertEquals(rfa.getTypes().get(0),AlertMessageType.ALERT_TYPE_AGGRESSIVE_DRIVING); 

        
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

    @Test
    public void testAlertMessageTypeMapping2() {
        Long mask = 0l;
        
        
        for (AlertMessageType type : AlertMessageType.values()) {
            mask |= type.getBitMask();
            System.out.println("mask " + Long.toHexString(mask) +" bitmask " + Long.toHexString(type.getBitMask()));
        }
        
        assertEquals("3ffdfffff", Long.toHexString(mask));
        
        Set<AlertMessageType> allTypes = AlertMessageType.getAlertMessageTypes(mask);
        for (AlertMessageType type : allTypes)
            System.out.println(type.toString());

        assertEquals(allTypes.size(), AlertMessageType.values().length);
        
    }
    

}
