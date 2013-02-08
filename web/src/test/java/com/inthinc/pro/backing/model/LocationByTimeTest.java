package com.inthinc.pro.backing.model;

import static org.junit.Assert.assertFalse;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.dao.HOSDAO;
import com.inthinc.pro.dao.mock.MockHOSDAO;
import com.inthinc.pro.map.GoogleAddressLookup;

public class LocationByTimeTest {

    HOSDAO mockHOSDAO;
    LocateVehicleByTime locateVehicleByTime;
    GoogleAddressLookup googleAddressLookupBean;
    
    @Before
    public void before(){
        mockHOSDAO = new MockHOSDAO();
        locateVehicleByTime = new LocateVehicleByTime();
        googleAddressLookupBean = new GoogleAddressLookup();
        locateVehicleByTime.setHosDAO(mockHOSDAO);
        locateVehicleByTime.setGoogleAddressLookupBean(googleAddressLookupBean);
    }
    
    @Test
    public void testAddress(){
        String address = locateVehicleByTime.getNearestCity(130, new Date(1307380014000l));
        assertFalse(address.isEmpty());
    }
    @Test
    public void learningDurationTest(){
        DateTime now = new DateTime();
        DateTime yesterday = new DateTime().minusDays(1);
        Duration backwards = new Duration(yesterday, now);
        Duration forwards = new Duration(now, yesterday);
        assertFalse(backwards.isEqual(forwards));
        
    }
}
