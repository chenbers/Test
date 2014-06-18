package com.inthinc.pro.dao.jdbc;
/*
import java.util.Map;

import mockit.Mock;
import mockit.*;

import org.junit.Test;

import com.inthinc.pro.model.hos.HOSRecord;
import static org.junit.Assert.*;
*/
public class HOSJDBCDAOMockedTest {
/*
    HOSJDBCDAO hosDAO = new HOSJDBCDAO();
    
    @Test
    public void testPopulateVehicle_nullNoteFlag_noNPEAndIsHOS() {
        HOSRecord hosRecordWithNullNoteFlag = new HOSRecord();
        hosRecordWithNullNoteFlag.setVehicleID(1);
        final Integer vehicleIsDOTFlag = 1;
        new MockUp<HOSJDBCDAO>() {
            @Mock
             <T> T queryForNullableObject(String sql, Class<T> requiredType, Map<String, Object> params) {
                if(requiredType.equals(String.class)) {
                    return (T)"license";
                } else if(requiredType.equals(Integer.class)) {
                    return (T) vehicleIsDOTFlag ;
                } else {
                    return null;
                }
            }
        };
        
        Deencapsulation.invoke(hosDAO, "populateVehicle", hosRecordWithNullNoteFlag);
        assertTrue("Vehicle should be  DOT ", hosRecordWithNullNoteFlag.getVehicleIsDOT());
    }
    
    @Test
    public void testPopulateVehicle_nullNoteFlag_noNPEAndNotHOS() {
        HOSRecord hosRecordWithNullNoteFlag = new HOSRecord();
        hosRecordWithNullNoteFlag.setVehicleID(1);
        final Integer vehicleIsDOTFlag = 0;
        new MockUp<HOSJDBCDAO>() {
            @Mock
             <T> T queryForNullableObject(String sql, Class<T> requiredType, Map<String, Object> params) {
                if(requiredType.equals(String.class)) {
                    return (T)"license";
                } else if(requiredType.equals(Integer.class)) {
                    return (T) vehicleIsDOTFlag ;
                } else {
                    return null;
                }
            }
        };
        
        Deencapsulation.invoke(hosDAO, "populateVehicle", hosRecordWithNullNoteFlag);
        assertFalse("Vehicle should not be DOT ", hosRecordWithNullNoteFlag.getVehicleIsDOT());
    }
*/
}
