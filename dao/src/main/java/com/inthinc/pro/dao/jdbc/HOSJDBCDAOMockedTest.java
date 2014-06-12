package com.inthinc.pro.dao.jdbc;

import java.util.Map;

import mockit.Mock;
import mockit.*;

import org.junit.Test;

import com.inthinc.pro.model.hos.HOSRecord;

public class HOSJDBCDAOMockedTest {

    HOSJDBCDAO hosDAO = new HOSJDBCDAO();
    
    @Test
    public void testPopulateVehicle_nullNoteFlag_noNPE() {
        HOSRecord hosRecordWithNullNoteFlag = new HOSRecord();
        hosRecordWithNullNoteFlag.setVehicleID(1);
        final Integer resultInteger = 123;
        new MockUp<HOSJDBCDAO>() {
            @Mock
             <T> T queryForNullableObject(String sql, Class<T> requiredType, Map<String, Object> params) {
                if(requiredType.equals(String.class)) {
                    return (T)"license";
                } else if(requiredType.equals(Integer.class)) {
                    return (T) resultInteger ;
                } else {
                    return null;
                }
            }
            
        };
        
        
        Deencapsulation.invoke(hosDAO, "populateVehicle", hosRecordWithNullNoteFlag);
    }
    
}
