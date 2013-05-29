package com.inthinc.pro.model;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.inthinc.pro.dao.hessian.mapper.RedFlagAlertMapper;

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
        RedFlagAlertMapper rfam = new RedFlagAlertMapper();
        RedFlagAlert redFlagAlert = new RedFlagAlert();
        Long value = 1l;
        rfam.alertTypeMaskToModel(redFlagAlert, value);
        
        assertEquals(redFlagAlert.getTypes().get(0),AlertMessageType.ALERT_TYPE_SPEEDING); 
        value = 4l;
        rfam.alertTypeMaskToModel(redFlagAlert, value);
        assertEquals(redFlagAlert.getTypes().get(0),AlertMessageType.ALERT_TYPE_SEATBELT); 
        value = 1024l;
        rfam.alertTypeMaskToModel(redFlagAlert, value);
        assertEquals(redFlagAlert.getTypes().get(0),AlertMessageType.ALERT_TYPE_HARD_ACCEL); 

        
        value = AlertMessageType.ALERT_TYPE_WITNESS_UPDATED.getBitMask() +
        AlertMessageType.ALERT_TYPE_ZONES_CURRENT.getBitMask() + 
        AlertMessageType.ALERT_TYPE_WITNESS_HEARTBEAT_VIOLATION.getBitMask();
        rfam.alertTypeMaskToModel(redFlagAlert, value);
        assertEquals(redFlagAlert.getTypes().get(0),AlertMessageType.ALERT_TYPE_WITNESS_UPDATED); 
        assertEquals(redFlagAlert.getTypes().get(1),AlertMessageType.ALERT_TYPE_ZONES_CURRENT); 
        assertEquals(redFlagAlert.getTypes().get(2),AlertMessageType.ALERT_TYPE_WITNESS_HEARTBEAT_VIOLATION); 

        value = 0l;
        Map<String, Object> propertyMap = new HashMap<String,Object>();
        rfam.alertTypeMaskToColumn(redFlagAlert, propertyMap);
        assertEquals(((Long)propertyMap.get("alertTypeMask")).longValue(),AlertMessageType.ALERT_TYPE_WITNESS_UPDATED.getBitMask() +
                AlertMessageType.ALERT_TYPE_ZONES_CURRENT.getBitMask() + 
                AlertMessageType.ALERT_TYPE_WITNESS_HEARTBEAT_VIOLATION.getBitMask());
        
        value = 0l;
        rfam.alertTypeMaskToModel(redFlagAlert, value);
        assertEquals(redFlagAlert.getTypes(),Collections.emptyList()); 
     }

    @Test
    public void testAlertMessageTypeMapping2() {
        Long mask = 0l;
        mask |= AlertMessageType.ALERT_TYPE_ENTER_ZONE.getBitMask();
//        System.out.println(AlertMessageType.ALERT_TYPE_ENTER_ZONE + " mask " + Long.toHexString(mask) +" bitmask " + Long.toHexString(AlertMessageType.ALERT_TYPE_ENTER_ZONE.getBitMask()));
        mask |= AlertMessageType.ALERT_TYPE_EXIT_ZONE.getBitMask();
//        System.out.println(AlertMessageType.ALERT_TYPE_EXIT_ZONE + " mask " + Long.toHexString(mask) +" bitmask " + Long.toHexString(AlertMessageType.ALERT_TYPE_EXIT_ZONE.getBitMask()));
        
        
        mask = 0l;
        for (AlertMessageType type : AlertMessageType.values()) {
            mask |= type.getBitMask();
            System.out.println(type + " mask " + Long.toHexString(mask) +" bitmask " + Long.toHexString(type.getBitMask()));
        }
        
        String tmp = Long.toHexString(mask);
        assertEquals("fffffdfffff", Long.toHexString(mask));
        
        List<AlertMessageType> allTypes = AlertMessageType.getAlertMessageTypes(mask);
//        for (AlertMessageType type : allTypes)
//            System.out.println(type.toString());

        assertEquals(allTypes.size(), AlertMessageType.values().length);
        
    }
    

}
