package com.inthinc.pro.backing.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.dao.HOSDAO;
import com.inthinc.pro.dao.mock.MockHOSDAO;
import com.inthinc.pro.map.GoogleAddressLookup;
import com.inthinc.pro.model.LatLng;

public class LocationByTimeTest {

    HOSDAO mockHOSDAO;
    LocateVehicleByTime locateVehicleByTime;
    GoogleAddressLookup googleAddressLookupBean;
    
    @Before
    public void before(){
        mockHOSDAO = new MockHOSDAO();
        locateVehicleByTime = new LocateVehicleByTime();
        googleAddressLookupBean = new GoogleAddressLookup();
        googleAddressLookupBean.setGoogleMapGeoUrl("https://maps-api-ssl.google.com/maps/geo?client=gme-inthinc&sensor=false&q=");
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
