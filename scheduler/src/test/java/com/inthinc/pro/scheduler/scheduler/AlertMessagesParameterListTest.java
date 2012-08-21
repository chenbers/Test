package com.inthinc.pro.scheduler.scheduler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import mockit.Cascading;
import mockit.Expectations;
import mockit.Mocked;

import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.ZoneDAO;
import com.inthinc.pro.dao.jdbc.AlertMessageJDBCDAO;
import com.inthinc.pro.dao.jdbc.AlertMessageJDBCDAO.ParameterList;
import com.inthinc.pro.map.AddressLookup;
import com.inthinc.pro.model.AlertMessage;
import com.inthinc.pro.model.AlertMessageType;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.Zone;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.IdleEvent;
import com.inthinc.pro.model.event.SpeedingEvent;
import com.inthinc.pro.model.event.VersionEvent;
import com.inthinc.pro.model.event.VersionState;

public class AlertMessagesParameterListTest {
    
    private Event event; 
    @Cascading private Person person;
    private Vehicle vehicle;
    private AlertMessage alertMessage;
    @Cascading private Driver driver;
    private Zone zone;
    
    @Mocked private EventDAO eventDAO;
    @Mocked private PersonDAO personDAO;
    @Mocked private VehicleDAO vehicleDAO;
    @Mocked private DriverDAO driverDAO;
    @Mocked private ZoneDAO zoneDAO;
    @Mocked private AddressLookup addressLookup;
    private AlertMessageJDBCDAO alertMessageJDBCDAO;
    
    @Before
    public void setup(){
        alertMessageJDBCDAO = new AlertMessageJDBCDAO();
        alertMessageJDBCDAO.setDriverDAO(driverDAO);
        alertMessageJDBCDAO.setEventDAO(eventDAO);
        alertMessageJDBCDAO.setPersonDAO(personDAO);
        alertMessageJDBCDAO.setVehicleDAO(vehicleDAO);
        alertMessageJDBCDAO.setZoneDAO(zoneDAO);
        alertMessageJDBCDAO.setAddressLookup(addressLookup);
        alertMessageJDBCDAO.setFormSubmissionsURL("http://dev-tiwipro.com:8080/forms/submissions");

        event = new Event();
        event.setDriverID(1);
        event.setTime(new Date(1345234248691L));
        event.setVehicleID(1);
        event.setLatitude(0D);
        event.setLongitude(0D);
        
        driver = new Driver();
        person = new Person();
        vehicle = new Vehicle();
        vehicle.setVehicleID(1);
        vehicle.setName("vehicle1");
        
        zone = new Zone();
        zone.setName("zone1");
    }
   
    @Test
    public void testPreTripFailParameterList(){
        ParameterList parameterList = alertMessageJDBCDAO.new ParameterList();
        assertNotNull(parameterList);
        
        List<String> nullParameterList = parameterList.getParameterList(null, null, null, null, null);
        assertNull(nullParameterList);
        
        
        new Expectations(){{
            
            driverDAO.findByID(1);result = driver;
            driver.getPerson();result=person;
            driver.getPerson().getTimeZone(); result =TimeZone.getTimeZone("GMT");
            driver.getPerson();result=person;
            driver.getPerson().getFullName(); result="Jacquie Howard";
            vehicleDAO.findByID(1);result=vehicle;
            addressLookup.getAddressOrLatLng((LatLng) any);result="Salt Lake City, Utah";
        }};
        
        
        List<String> alertParameterList = parameterList.getParameterList(event, MeasurementType.ENGLISH , AlertMessageType.ALERT_TYPE_DVIR_PRE_TRIP_FAIL, Locale.getDefault(), 0);
        assertTrue(alertParameterList.size()==5);
        assertEquals("date should be first element and should be Aug 17, 2012 8:10 PM (GMT)", alertParameterList.get(0),"Aug 17, 2012 8:10 PM (GMT)");
        assertEquals("driver name should be second element and should be Jacquie Howard", alertParameterList.get(1),"Jacquie Howard");
        assertEquals("vehicle name should be third element and should be vehicle1", alertParameterList.get(2),"vehicle1");
        assertEquals("address should be fourth element and should be Salt Lake City, Utah", alertParameterList.get(3),"Salt Lake City, Utah");
        assertEquals("link should be fifth element and should be http://dev-tiwipro.com:8080/forms/submissions", alertParameterList.get(4),"http://dev-tiwipro.com:8080/forms/submissions");
    }
    @Test
    public void testPreTripPassParameterList(){
        ParameterList parameterList = alertMessageJDBCDAO.new ParameterList();
        assertNotNull(parameterList);
        
        new Expectations(){{
            
            driverDAO.findByID(1);result = driver;
            driver.getPerson();result=person;
            driver.getPerson().getTimeZone(); result =TimeZone.getTimeZone("GMT");
            driver.getPerson();result=person;
            driver.getPerson().getFullName(); result="Jacquie Howard";
            vehicleDAO.findByID(1);result=vehicle;
            addressLookup.getAddressOrLatLng((LatLng) any);result="Salt Lake City, Utah";
        }};
        
        
        List<String> alertParameterList = parameterList.getParameterList(event, MeasurementType.ENGLISH , AlertMessageType.ALERT_TYPE_DVIR_PRE_TRIP_PASS, Locale.getDefault(), 0);
        assertTrue(alertParameterList.size()==5);
        assertEquals("date should be first element and should be Aug 17, 2012 8:10 PM (GMT)", alertParameterList.get(0),"Aug 17, 2012 8:10 PM (GMT)");
        assertEquals("driver name should be second element and should be Jacquie Howard", alertParameterList.get(1),"Jacquie Howard");
        assertEquals("vehicle name should be third element and should be vehicle1", alertParameterList.get(2),"vehicle1");
        assertEquals("address should be fourth element and should be Salt Lake City, Utah", alertParameterList.get(3),"Salt Lake City, Utah");
        assertEquals("link should be fifth element and should be http://dev-tiwipro.com:8080/forms/submissions", alertParameterList.get(4),"http://dev-tiwipro.com:8080/forms/submissions");
    }
    @Test
    public void testPostTripPassParameterList(){
        ParameterList parameterList = alertMessageJDBCDAO.new ParameterList();
        assertNotNull(parameterList);
        
        new Expectations(){{
            
            driverDAO.findByID(1);result = driver;
            driver.getPerson();result=person;
            driver.getPerson().getTimeZone(); result =TimeZone.getTimeZone("GMT");
            driver.getPerson();result=person;
            driver.getPerson().getFullName(); result="Jacquie Howard";
            vehicleDAO.findByID(1);result=vehicle;
            addressLookup.getAddressOrLatLng((LatLng) any);result="Salt Lake City, Utah";
        }};
        
        
        List<String> alertParameterList = parameterList.getParameterList(event, MeasurementType.ENGLISH , AlertMessageType.ALERT_TYPE_DVIR_POST_TRIP_PASS, Locale.getDefault(), 0);
        assertTrue(alertParameterList.size()==5);
        assertEquals("date should be first element and should be Aug 17, 2012 8:10 PM (GMT)", alertParameterList.get(0),"Aug 17, 2012 8:10 PM (GMT)");
        assertEquals("driver name should be second element and should be Jacquie Howard", alertParameterList.get(1),"Jacquie Howard");
        assertEquals("vehicle name should be third element and should be vehicle1", alertParameterList.get(2),"vehicle1");
        assertEquals("address should be fourth element and should be Salt Lake City, Utah", alertParameterList.get(3),"Salt Lake City, Utah");
        assertEquals("link should be fifth element and should be http://dev-tiwipro.com:8080/forms/submissions", alertParameterList.get(4),"http://dev-tiwipro.com:8080/forms/submissions");
    }
    @Test
    public void testPostTripFailParameterList(){
        ParameterList parameterList = alertMessageJDBCDAO.new ParameterList();
        assertNotNull(parameterList);
        
        new Expectations(){{
            
            driverDAO.findByID(1);result = driver;
            driver.getPerson();result=person;
            driver.getPerson().getTimeZone(); result =TimeZone.getTimeZone("GMT");
            driver.getPerson();result=person;
            driver.getPerson().getFullName(); result="Jacquie Howard";
            vehicleDAO.findByID(1);result=vehicle;
            addressLookup.getAddressOrLatLng((LatLng) any);result="Salt Lake City, Utah";
        }};
        
        
        List<String> alertParameterList = parameterList.getParameterList(event, MeasurementType.ENGLISH , AlertMessageType.ALERT_TYPE_DVIR_POST_TRIP_FAIL, Locale.getDefault(), 0);
        assertTrue(alertParameterList.size()==5);
        assertEquals("date should be first element and should be Aug 17, 2012 8:10 PM (GMT)", alertParameterList.get(0),"Aug 17, 2012 8:10 PM (GMT)");
        assertEquals("driver name should be second element and should be Jacquie Howard", alertParameterList.get(1),"Jacquie Howard");
        assertEquals("vehicle name should be third element and should be vehicle1", alertParameterList.get(2),"vehicle1");
        assertEquals("address should be fourth element and should be Salt Lake City, Utah", alertParameterList.get(3),"Salt Lake City, Utah");
        assertEquals("link should be fifth element and should be http://dev-tiwipro.com:8080/forms/submissions", alertParameterList.get(4),"http://dev-tiwipro.com:8080/forms/submissions");
    }
    @Test
    public void testZoneParameterList(){
        ParameterList parameterList = alertMessageJDBCDAO.new ParameterList();
        assertNotNull(parameterList);
        
        new Expectations(){{
            
            driverDAO.findByID(1);result = driver;
            driver.getPerson();result=person;
            driver.getPerson().getTimeZone(); result =TimeZone.getTimeZone("GMT");
            driver.getPerson();result=person;
            driver.getPerson().getFullName(); result="Jacquie Howard";
            vehicleDAO.findByID(1);result=vehicle;
            zoneDAO.findByID((Integer) any);result=zone;
        }};
        
        
        List<String> alertParameterList = parameterList.getParameterList(event, MeasurementType.ENGLISH , AlertMessageType.ALERT_TYPE_ENTER_ZONE, Locale.getDefault(), 2);
        assertTrue(alertParameterList.size()==4);
        assertEquals("date should be first element and should be Aug 17, 2012 8:10 PM (GMT)", alertParameterList.get(0),"Aug 17, 2012 8:10 PM (GMT)");
        assertEquals("driver name should be second element and should be Jacquie Howard", alertParameterList.get(1),"Jacquie Howard");
        assertEquals("vehicle name should be third element and should be vehicle1", alertParameterList.get(2),"vehicle1");
        assertEquals("zone should be fourth element and should be zone1", alertParameterList.get(3),"zone1");
    }
    @Test
    public void testSpeedingParameterList(){
        ParameterList parameterList = alertMessageJDBCDAO.new ParameterList();
        assertNotNull(parameterList);
        event = new SpeedingEvent();
        ((SpeedingEvent)event).setTopSpeed(70);
        ((SpeedingEvent)event).setSpeedLimit(50);
        event.setDriverID(1);
        event.setTime(new Date(1345234248691L));
        event.setVehicleID(1);
        event.setLatitude(0D);
        event.setLongitude(0D);
     
        new Expectations(){{
            
            driverDAO.findByID(1);result = driver;
            driver.getPerson();result=person;
            driver.getPerson().getTimeZone(); result =TimeZone.getTimeZone("GMT");
            driver.getPerson();result=person;
            driver.getPerson().getFullName(); result="Jacquie Howard";
            vehicleDAO.findByID(1);result=vehicle;            
            addressLookup.getAddressOrLatLng((LatLng) any);result="Salt Lake City, Utah";
            
        }};
        
        
        List<String> alertParameterList = parameterList.getParameterList(event, MeasurementType.ENGLISH , AlertMessageType.ALERT_TYPE_SPEEDING, Locale.getDefault(), 2);
        assertTrue(alertParameterList.size()==6);
        assertEquals("date should be first element and should be Aug 17, 2012 8:10 PM (GMT)", alertParameterList.get(0),"Aug 17, 2012 8:10 PM (GMT)");
        assertEquals("driver name should be second element and should be Jacquie Howard", alertParameterList.get(1),"Jacquie Howard");
        assertEquals("vehicle name should be third element and should be vehicle1", alertParameterList.get(2),"vehicle1");
        assertEquals("top speed should be fourth element and should be 70", alertParameterList.get(3),"70");
        assertEquals("speedlimit should be fifth element and should be 50", alertParameterList.get(4),"50");
        assertEquals("address should be sixth element and should be Salt Lake City, Utah", alertParameterList.get(5),"Salt Lake City, Utah");
    }
    @Test
    public void testSpeedingNoEventParameterList(){
        ParameterList parameterList = alertMessageJDBCDAO.new ParameterList();
        assertNotNull(parameterList);
     
        new Expectations(){{
            
            driverDAO.findByID(1);result = driver;
            driver.getPerson();result=person;
            driver.getPerson().getTimeZone(); result =TimeZone.getTimeZone("GMT");
            driver.getPerson();result=person;
            driver.getPerson().getFullName(); result="Jacquie Howard";
            vehicleDAO.findByID(1);result=vehicle;            
//            addressLookup.getAddressOrLatLng((LatLng) any);result="Salt Lake City, Utah";
            
        }};
        
        
        List<String> alertParameterList = parameterList.getParameterList(event, MeasurementType.ENGLISH , AlertMessageType.ALERT_TYPE_SPEEDING, Locale.getDefault(), 2);
        assertTrue(alertParameterList.size()==3);
        assertEquals("date should be first element and should be Aug 17, 2012 8:10 PM (GMT)", alertParameterList.get(0),"Aug 17, 2012 8:10 PM (GMT)");
        assertEquals("driver name should be second element and should be Jacquie Howard", alertParameterList.get(1),"Jacquie Howard");
        assertEquals("vehicle name should be third element and should be vehicle1", alertParameterList.get(2),"vehicle1");
//        assertEquals("top speed should be fourth element and should be 70", alertParameterList.get(3),"70");
//        assertEquals("speedlimit should be fifth element and should be 50", alertParameterList.get(4),"50");
//        assertEquals("address should be sixth element and should be Salt Lake City, Utah", alertParameterList.get(5),"Salt Lake City, Utah");
    }
    @Test
    public void testTamperingParameterList(){
        ParameterList parameterList = alertMessageJDBCDAO.new ParameterList();
        assertNotNull(parameterList);
     
        new Expectations(){{
            
            driverDAO.findByID(1);result = driver;
            driver.getPerson();result=person;
            driver.getPerson().getTimeZone(); result =TimeZone.getTimeZone("GMT");
            driver.getPerson();result=person;
            driver.getPerson().getFullName(); result="Jacquie Howard";
            vehicleDAO.findByID(1);result=vehicle;            
            
        }};
        
        
        List<String> alertParameterList = parameterList.getParameterList(event, MeasurementType.ENGLISH , AlertMessageType.ALERT_TYPE_TAMPERING, Locale.getDefault(), 2);
        assertTrue(alertParameterList.size()==3);
        assertEquals("date should be first element and should be Aug 17, 2012 8:10 PM (GMT)", alertParameterList.get(0),"Aug 17, 2012 8:10 PM (GMT)");
        assertEquals("driver name should be second element and should be Jacquie Howard", alertParameterList.get(1),"Jacquie Howard");
        assertEquals("vehicle name should be third element and should be vehicle1", alertParameterList.get(2),"vehicle1");
    }
    @Test
    public void testLowBatteryParameterList(){
        ParameterList parameterList = alertMessageJDBCDAO.new ParameterList();
        assertNotNull(parameterList);
     
        new Expectations(){{
            
            driverDAO.findByID(1);result = driver;
            driver.getPerson();result=person;
            driver.getPerson().getTimeZone(); result =TimeZone.getTimeZone("GMT");
            driver.getPerson();result=person;
            driver.getPerson().getFullName(); result="Jacquie Howard";
            vehicleDAO.findByID(1);result=vehicle;            
            
        }};
        
        
        List<String> alertParameterList = parameterList.getParameterList(event, MeasurementType.ENGLISH , AlertMessageType.ALERT_TYPE_LOW_BATTERY, Locale.getDefault(), 2);
        assertTrue(alertParameterList.size()==3);
        assertEquals("date should be first element and should be Aug 17, 2012 8:10 PM (GMT)", alertParameterList.get(0),"Aug 17, 2012 8:10 PM (GMT)");
        assertEquals("driver name should be second element and should be Jacquie Howard", alertParameterList.get(1),"Jacquie Howard");
        assertEquals("vehicle name should be third element and should be vehicle1", alertParameterList.get(2),"vehicle1");
    }
    @Test
    public void testIdlingParameterList(){
        ParameterList parameterList = alertMessageJDBCDAO.new ParameterList();
        assertNotNull(parameterList);

        event = new IdleEvent();
        ((IdleEvent)event).setLowIdle(63);
        ((IdleEvent)event).setHighIdle(57);
        event.setDriverID(1);
        event.setTime(new Date(1345234248691L));
        event.setVehicleID(1);
        event.setLatitude(0D);
        event.setLongitude(0D);

        new Expectations(){{
            
            driverDAO.findByID(1);result = driver;
            driver.getPerson();result=person;
            driver.getPerson().getTimeZone(); result =TimeZone.getTimeZone("GMT");
            driver.getPerson();result=person;
            driver.getPerson().getFullName(); result="Jacquie Howard";
            vehicleDAO.findByID(1);result=vehicle;            
            addressLookup.getAddressOrLatLng((LatLng) any);result="Salt Lake City, Utah";
            
        }};
        
        
        List<String> alertParameterList = parameterList.getParameterList(event, MeasurementType.ENGLISH , AlertMessageType.ALERT_TYPE_IDLING, Locale.getDefault(), 2);
        assertTrue(alertParameterList.size()==5);
        assertEquals("date should be first element and should be Aug 17, 2012 8:10 PM (GMT)", alertParameterList.get(0),"Aug 17, 2012 8:10 PM (GMT)");
        assertEquals("driver name should be second element and should be Jacquie Howard", alertParameterList.get(1),"Jacquie Howard");
        assertEquals("vehicle name should be third element and should be vehicle1", alertParameterList.get(2),"vehicle1");
        assertEquals("total idle should be fourth element and should be 2", alertParameterList.get(3),"2");
        assertEquals("address should be fifth element and should be Salt Lake City, Utah", alertParameterList.get(4),"Salt Lake City, Utah");
            }
    @Test
    public void testVersionParameterList(){
        ParameterList parameterList = alertMessageJDBCDAO.new ParameterList();
        assertNotNull(parameterList);

        event = new VersionEvent();
        ((VersionEvent)event).setStatus(VersionState.NOT_UPDATED);
        event.setDriverID(1);
        event.setTime(new Date(1345234248691L));
        event.setVehicleID(1);
        event.setLatitude(0D);
        event.setLongitude(0D);

        new Expectations(){{
            
            driverDAO.findByID(1);result = driver;
            driver.getPerson();result=person;
            driver.getPerson().getTimeZone(); result =TimeZone.getTimeZone("GMT");
            driver.getPerson();result=person;
            driver.getPerson().getFullName(); result="Jacquie Howard";
            vehicleDAO.findByID(1);result=vehicle;            
            addressLookup.getAddressOrLatLng((LatLng) any);result="Salt Lake City, Utah";
        }};
        
        
        List<String> alertParameterList = parameterList.getParameterList(event, MeasurementType.ENGLISH , AlertMessageType.ALERT_TYPE_WITNESS_UPDATED, Locale.getDefault(), 2);
        assertTrue(alertParameterList.size()==5);
        assertEquals("date should be first element and should be Aug 17, 2012 8:10 PM (GMT)", alertParameterList.get(0),"Aug 17, 2012 8:10 PM (GMT)");
        assertEquals("driver name should be second element and should be Jacquie Howard", alertParameterList.get(1),"Jacquie Howard");
        assertEquals("vehicle name should be third element and should be vehicle1", alertParameterList.get(2),"vehicle1");
        assertEquals("address should be fourth element and should be Salt Lake City, Utah", alertParameterList.get(3),"Salt Lake City, Utah");
        assertEquals("status should be fifth element and should be VersionState.NOT_UPDATED", alertParameterList.get(4),"VersionState.NOT_UPDATED");
            }
}
