/**
 * 
 */
package com.inthinc.pro.dao.jdbc;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.Mocked;
import mockit.NonStrictExpectations;

import org.junit.Test;

import com.inthinc.pro.map.AddressLookup;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.dao.DeviceDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.ZoneDAO;
import com.inthinc.pro.model.AlertMessage;
import com.inthinc.pro.model.AlertMessageType;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.RedFlagLevel;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.Zone;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventAttr;
import com.inthinc.pro.model.event.IdleEvent;
import com.inthinc.pro.model.event.SpeedingEvent;

/**
 * @author anelson
 *
 */
public class AlertMessageJDBCDAOTest {
    
    // Mocks
    @Mocked Driver mockDriver;
    @Mocked Person mockPerson;
    @Mocked GroupDAO mockGroupDAO;
    @Mocked List<Group> mockList;
    @Mocked GroupHierarchy mockGroupHierarchy;
    @Mocked Event mockEvent;
    @Mocked SpeedingEvent mockEventSpeed;
    @Mocked IdleEvent mockEventIdle;
    @Mocked Vehicle mockVehicle;
    @Mocked VehicleDAO mockVehicleDAO;
    @Mocked Device mockDevice;
    @Mocked DeviceDAO mockDeviceDAO;
    @Mocked AlertMessage mockAlertMessage;
    @Mocked AddressLookup mockAddressLookup;
    @Mocked Zone mockZone;
    @Mocked ZoneDAO mockZoneDAO;
    //@Mocked Locale mockLocale;
    
    @Test
    public final void testGetDriverOrgStructureWithValidDriver() {
        
        // Methods that may be called in the execution of the method we're testing
        new NonStrictExpectations() {{
            mockDriver.getPerson(); result = mockPerson;
            mockPerson.getAcctID(); result = 1;
            mockGroupDAO.getGroupsByAcctID(1); result = mockList;
            new GroupHierarchy();
            mockGroupHierarchy.getFullGroupName(anyInt, " > "); result = "Group Name";
        }};

        // Instantiate our parent class (necessary before instantiating nested class)
        AlertMessageJDBCDAO alertMessageJDBCDAO = new AlertMessageJDBCDAO();
        
        // Set the groupDAO to a mocked version
        alertMessageJDBCDAO.setGroupDAO(mockGroupDAO);
        
        // Instantiate the class we're testing
        AlertMessageJDBCDAO.ParameterList parameterList = alertMessageJDBCDAO.new ParameterList();
        
        // Run the method
        String result = Deencapsulation.invoke(parameterList, "getDriverOrgStructure", mockDriver);
        
        // Test the result
        assertEquals(result, "Group Name");
    }
    
    @Test
    public final void testGetDriverOrgStructureWithNullPerson() {
        
        // Return null when getPerson() is called to make sure we're returning
        // and empty string when the first condition fails
        new NonStrictExpectations() {{
             mockDriver.getPerson(); result = null;
        }};

        // Instantiate our parent class (necessary before instantiating nested class)
        AlertMessageJDBCDAO alertMessageJDBCDAO = new AlertMessageJDBCDAO();
        
        // Set the groupDAO to a mocked version
        alertMessageJDBCDAO.setGroupDAO(mockGroupDAO);
        
        // Instantiate the class we're testing
        AlertMessageJDBCDAO.ParameterList parameterList = alertMessageJDBCDAO.new ParameterList();
        
        // Run the method
        String result = Deencapsulation.invoke(parameterList, "getDriverOrgStructure", mockDriver);
        
        // Test the result
        assertEquals(result, "");
        
    }

    private Expectations  expectExCrm(final AlertMessageType alertType){
       return expectExCrm(alertType, MeasurementType.ENGLISH, 75);
    }

    private Expectations expectExCrm(final AlertMessageType alertType, final MeasurementType measurementType, final Integer speed) {
        return new NonStrictExpectations() {{
            mockAlertMessage.getAlertMessageType(); result = alertType;
            mockAlertMessage.getName(); result = "my Red Flag Alert";
            mockAlertMessage.getLevel(); result = RedFlagLevel.CRITICAL;
            mockAlertMessage.getZoneID(); result = 1;
    
            mockEventSpeed.getDriverID(); result = 1;
            mockEventSpeed.getTime(); result = new Date();
            mockEventSpeed.getVehicleID(); result = 1;
            mockEventSpeed.getDeviceID(); result = 1;
            mockEventSpeed.getOdometer(); result = 54000;
            mockEventSpeed.getSpeed(); result = speed;
            mockEventSpeed.getLatitude(); result = 41.234;
            mockEventSpeed.getLongitude(); result = -111.8902;
            mockEventSpeed.getTopSpeed(); result = 75;
            mockEventSpeed.getSpeedLimit(); result = 65;

            mockEvent.getDriverID(); result = 1;
            mockEvent.getTime(); result = new Date();
            mockEvent.getVehicleID(); result = 1;
            mockEvent.getDeviceID(); result = 1;
            mockEvent.getOdometer(); result = 54000;
            mockEvent.getSpeed(); result = speed;
            mockEvent.getLatitude(); result = 41.234;
            mockEvent.getLongitude(); result = -111.8902;
            mockEvent.getAttrByType(EventAttr.UP_TO_DATE_STATUS); result = "2";
            
            mockEventIdle.getDriverID(); result = 1;
            mockEventIdle.getTime(); result = new Date();
            mockEventIdle.getVehicleID(); result = 1;
            mockEventIdle.getDeviceID(); result = 1;
            mockEventIdle.getOdometer(); result = 54000;
            mockEventIdle.getSpeed(); result = speed;
            mockEventIdle.getLatitude(); result = 41.234;
            mockEventIdle.getLongitude(); result = -111.8902;
            mockEventIdle.getTotalIdling(); result = 3400;
            
            mockDriver.getPerson(); result = mockPerson;
            mockDriver.getDriverID(); result = 1234;
            
            mockPerson.getAcctID(); result = 1;
            mockPerson.getEmpid(); result = "4242";
            mockPerson.getMeasurementType(); result = measurementType;
            mockPerson.getTimeZone(); result = TimeZone.getDefault();
            mockPerson.getFullName(); result= "Jonathan Wood";
            
            mockAddressLookup.getAddressOrLatLng((LatLng) any); result = "Salt Lake City, Utah";
    
            mockGroupDAO.getGroupsByAcctID(1); result = mockList;
            new GroupHierarchy();
            mockGroupHierarchy.getFullGroupName(anyInt, " > "); result = "Group Name";
            
            mockVehicleDAO.findByID(1); result = mockVehicle;
            mockVehicle.getName(); result = "my Big Truck";
            mockVehicle.getYear(); result = 2010;
            mockVehicle.getMake(); result = "Ford";
            mockVehicle.getModel(); result = "F350";
            
            mockZoneDAO.findByID(1); result = mockZone;
            mockZone.getName(); result = "my Mock Zone";
            
            mockDeviceDAO.findByID(1); result = mockDevice;
            mockDevice.getFirmwareVersion(); result = 5150;
            mockDevice.getWitnessVersion(); result = 101;
        }};
    }
    
    @Test
    public final void testGetEzCrmParameterListALERT_TYPE_SPEEDING() {
        
        // Methods that may be called in the execution of the method we're testing
        expectExCrm(AlertMessageType.ALERT_TYPE_SPEEDING);

        // Instantiate our parent class (necessary before instantiating nested class)
        AlertMessageJDBCDAO alertMessageJDBCDAO = new AlertMessageJDBCDAO();
        
        // Set the mocked version objects
        alertMessageJDBCDAO.setGroupDAO(mockGroupDAO);
        alertMessageJDBCDAO.setVehicleDAO(mockVehicleDAO);
        alertMessageJDBCDAO.setAddressLookup(mockAddressLookup);
        
        // Instantiate the class we're testing
        AlertMessageJDBCDAO.EzCrmParameterList parameterList = alertMessageJDBCDAO.new EzCrmParameterList();
        
        parameterList.setLocal(Locale.getDefault());
        parameterList.setMeasurementType(MeasurementType.ENGLISH);
        parameterList.setEvent(mockEventSpeed);
        parameterList.setAlertMessage(mockAlertMessage);
        parameterList.setPerson(mockPerson);
        parameterList.setDriver(mockDriver);
        
        // Run the method  //Deencapsulation.invoke(parameterList, "getDriverOrgStructure", mockDriver);
        List<String> result = parameterList.getParameterListTest();
        
        // Test the result
        int i = 0;
        for (String s : result) {
            System.out.println("["+i+"] "+s);
            ++i;
        }
        assertEquals(result.size(), 20);
    }

    @Test
    public final void testGetEzCrmParameterListALERT_TYPE_SEATBELT() {
        
        // Methods that may be called in the execution of the method we're testing
        expectExCrm(AlertMessageType.ALERT_TYPE_SEATBELT);

        // Instantiate our parent class (necessary before instantiating nested class)
        AlertMessageJDBCDAO alertMessageJDBCDAO = new AlertMessageJDBCDAO();
        
        // Set the mocked version objects
        alertMessageJDBCDAO.setGroupDAO(mockGroupDAO);
        alertMessageJDBCDAO.setVehicleDAO(mockVehicleDAO);
        alertMessageJDBCDAO.setAddressLookup(mockAddressLookup);
        
        // Instantiate the class we're testing
        AlertMessageJDBCDAO.EzCrmParameterList parameterList = alertMessageJDBCDAO.new EzCrmParameterList();
        
        parameterList.setLocal(Locale.getDefault());
        parameterList.setMeasurementType(MeasurementType.ENGLISH);
        parameterList.setEvent(mockEventSpeed);
        parameterList.setAlertMessage(mockAlertMessage);
        parameterList.setPerson(mockPerson);
        parameterList.setDriver(mockDriver);
        
        // Run the method  //Deencapsulation.invoke(parameterList, "getDriverOrgStructure", mockDriver);
        List<String> result = parameterList.getParameterListTest();
        
        // Test the result
        int i = 0;
        for (String s : result) {
            System.out.println("["+i+"] "+s);
            ++i;
        }
        assertEquals(result.size(), 18);
    }

    @Test
    public final void testGetEzCrmParameterListALERT_TYPE_ENTER_ZONE() {
        
        // Methods that may be called in the execution of the method we're testing
        expectExCrm(AlertMessageType.ALERT_TYPE_ENTER_ZONE);

        // Instantiate our parent class (necessary before instantiating nested class)
        AlertMessageJDBCDAO alertMessageJDBCDAO = new AlertMessageJDBCDAO();
        
        // Set the mocked version objects
        alertMessageJDBCDAO.setGroupDAO(mockGroupDAO);
        alertMessageJDBCDAO.setVehicleDAO(mockVehicleDAO);
        alertMessageJDBCDAO.setAddressLookup(mockAddressLookup);
        alertMessageJDBCDAO.setZoneDAO(mockZoneDAO);
        
        // Instantiate the class we're testing
        AlertMessageJDBCDAO.EzCrmParameterList parameterList = alertMessageJDBCDAO.new EzCrmParameterList();
        
        parameterList.setLocal(Locale.getDefault());
        parameterList.setMeasurementType(MeasurementType.ENGLISH);
        parameterList.setEvent(mockEventSpeed);
        parameterList.setAlertMessage(mockAlertMessage);
        parameterList.setPerson(mockPerson);
        parameterList.setDriver(mockDriver);
        
        // Run the method  //Deencapsulation.invoke(parameterList, "getDriverOrgStructure", mockDriver);
        List<String> result = parameterList.getParameterListTest();
        
        // Test the result
        int i = 0;
        for (String s : result) {
            System.out.println("["+i+"] "+s);
            ++i;
        }
        assertEquals(result.size(), 20);
    }

    @Test
    public final void testGetEzCrmParameterListALERT_TYPE_EXIT_ZONE() {
        
        // Methods that may be called in the execution of the method we're testing
        expectExCrm(AlertMessageType.ALERT_TYPE_EXIT_ZONE);

        // Instantiate our parent class (necessary before instantiating nested class)
        AlertMessageJDBCDAO alertMessageJDBCDAO = new AlertMessageJDBCDAO();
        
        // Set the mocked version objects
        alertMessageJDBCDAO.setGroupDAO(mockGroupDAO);
        alertMessageJDBCDAO.setVehicleDAO(mockVehicleDAO);
        alertMessageJDBCDAO.setAddressLookup(mockAddressLookup);
        alertMessageJDBCDAO.setZoneDAO(mockZoneDAO);
        
        // Instantiate the class we're testing
        AlertMessageJDBCDAO.EzCrmParameterList parameterList = alertMessageJDBCDAO.new EzCrmParameterList();
        
        parameterList.setLocal(Locale.getDefault());
        parameterList.setMeasurementType(MeasurementType.ENGLISH);
        parameterList.setEvent(mockEventSpeed);
        parameterList.setAlertMessage(mockAlertMessage);
        parameterList.setPerson(mockPerson);
        parameterList.setDriver(mockDriver);
        
        // Run the method  //Deencapsulation.invoke(parameterList, "getDriverOrgStructure", mockDriver);
        List<String> result = parameterList.getParameterListTest();
        
        // Test the result
        int i = 0;
        for (String s : result) {
            System.out.println("["+i+"] "+s);
            ++i;
        }
        assertEquals(result.size(), 20);
    }

    @Test
    public final void testGetEzCrmParameterListALERT_TYPE_LOW_BATTERY() {
        
        // Methods that may be called in the execution of the method we're testing
        expectExCrm(AlertMessageType.ALERT_TYPE_LOW_BATTERY);

        // Instantiate our parent class (necessary before instantiating nested class)
        AlertMessageJDBCDAO alertMessageJDBCDAO = new AlertMessageJDBCDAO();
        
        // Set the mocked version objects
        alertMessageJDBCDAO.setGroupDAO(mockGroupDAO);
        alertMessageJDBCDAO.setVehicleDAO(mockVehicleDAO);
        alertMessageJDBCDAO.setAddressLookup(mockAddressLookup);
        alertMessageJDBCDAO.setZoneDAO(mockZoneDAO);
        
        // Instantiate the class we're testing
        AlertMessageJDBCDAO.EzCrmParameterList parameterList = alertMessageJDBCDAO.new EzCrmParameterList();
        
        parameterList.setLocal(Locale.getDefault());
        parameterList.setMeasurementType(MeasurementType.ENGLISH);
        parameterList.setEvent(mockEventSpeed);
        parameterList.setAlertMessage(mockAlertMessage);
        parameterList.setPerson(mockPerson);
        parameterList.setDriver(mockDriver);
        
        // Run the method  //Deencapsulation.invoke(parameterList, "getDriverOrgStructure", mockDriver);
        List<String> result = parameterList.getParameterListTest();
        
        // Test the result
        int i = 0;
        for (String s : result) {
            System.out.println("["+i+"] "+s);
            ++i;
        }
        assertEquals(result.size(), 18);
    }

    @Test
    public final void testGetEzCrmParameterListALERT_TYPE_TAMPERING() {
        
        // Methods that may be called in the execution of the method we're testing
        expectExCrm(AlertMessageType.ALERT_TYPE_TAMPERING);

        // Instantiate our parent class (necessary before instantiating nested class)
        AlertMessageJDBCDAO alertMessageJDBCDAO = new AlertMessageJDBCDAO();
        
        // Set the mocked version objects
        alertMessageJDBCDAO.setGroupDAO(mockGroupDAO);
        alertMessageJDBCDAO.setVehicleDAO(mockVehicleDAO);
        alertMessageJDBCDAO.setAddressLookup(mockAddressLookup);
        alertMessageJDBCDAO.setZoneDAO(mockZoneDAO);
        
        // Instantiate the class we're testing
        AlertMessageJDBCDAO.EzCrmParameterList parameterList = alertMessageJDBCDAO.new EzCrmParameterList();
        
        parameterList.setLocal(Locale.getDefault());
        parameterList.setMeasurementType(MeasurementType.ENGLISH);
        parameterList.setEvent(mockEventSpeed);
        parameterList.setAlertMessage(mockAlertMessage);
        parameterList.setPerson(mockPerson);
        parameterList.setDriver(mockDriver);
        
        // Run the method  //Deencapsulation.invoke(parameterList, "getDriverOrgStructure", mockDriver);
        List<String> result = parameterList.getParameterListTest();
        
        // Test the result
        int i = 0;
        for (String s : result) {
            System.out.println("["+i+"] "+s);
            ++i;
        }
        assertEquals(result.size(), 18);
    }

    @Test
    public final void testGetEzCrmParameterListALERT_TYPE_CRASH() {
        
        // Methods that may be called in the execution of the method we're testing
        expectExCrm(AlertMessageType.ALERT_TYPE_CRASH);

        // Instantiate our parent class (necessary before instantiating nested class)
        AlertMessageJDBCDAO alertMessageJDBCDAO = new AlertMessageJDBCDAO();
        
        // Set the mocked version objects
        alertMessageJDBCDAO.setGroupDAO(mockGroupDAO);
        alertMessageJDBCDAO.setVehicleDAO(mockVehicleDAO);
        alertMessageJDBCDAO.setAddressLookup(mockAddressLookup);
        alertMessageJDBCDAO.setZoneDAO(mockZoneDAO);
        
        // Instantiate the class we're testing
        AlertMessageJDBCDAO.EzCrmParameterList parameterList = alertMessageJDBCDAO.new EzCrmParameterList();
        
        parameterList.setLocal(Locale.getDefault());
        parameterList.setMeasurementType(MeasurementType.ENGLISH);
        parameterList.setEvent(mockEventSpeed);
        parameterList.setAlertMessage(mockAlertMessage);
        parameterList.setPerson(mockPerson);
        parameterList.setDriver(mockDriver);
        
        // Run the method  //Deencapsulation.invoke(parameterList, "getDriverOrgStructure", mockDriver);
        List<String> result = parameterList.getParameterListTest();
        
        // Test the result
        int i = 0;
        for (String s : result) {
            System.out.println("["+i+"] "+s);
            ++i;
        }
        assertEquals(result.size(), 18);
    }

    @Test
    public final void testGetEzCrmParameterListALERT_TYPE_HARD_BRAKE() {
        
        // Methods that may be called in the execution of the method we're testing
        expectExCrm(AlertMessageType.ALERT_TYPE_HARD_BRAKE);

        // Instantiate our parent class (necessary before instantiating nested class)
        AlertMessageJDBCDAO alertMessageJDBCDAO = new AlertMessageJDBCDAO();
        
        // Set the mocked version objects
        alertMessageJDBCDAO.setGroupDAO(mockGroupDAO);
        alertMessageJDBCDAO.setVehicleDAO(mockVehicleDAO);
        alertMessageJDBCDAO.setAddressLookup(mockAddressLookup);
        alertMessageJDBCDAO.setZoneDAO(mockZoneDAO);
        
        // Instantiate the class we're testing
        AlertMessageJDBCDAO.EzCrmParameterList parameterList = alertMessageJDBCDAO.new EzCrmParameterList();
        
        parameterList.setLocal(Locale.getDefault());
        parameterList.setMeasurementType(MeasurementType.ENGLISH);
        parameterList.setEvent(mockEventSpeed);
        parameterList.setAlertMessage(mockAlertMessage);
        parameterList.setPerson(mockPerson);
        parameterList.setDriver(mockDriver);
        
        // Run the method  //Deencapsulation.invoke(parameterList, "getDriverOrgStructure", mockDriver);
        List<String> result = parameterList.getParameterListTest();
        
        // Test the result
        int i = 0;
        for (String s : result) {
            System.out.println("["+i+"] "+s);
            ++i;
        }
        assertEquals(result.size(), 18);
    }

    @Test
    public final void testGetEzCrmParameterListALERT_TYPE_HARD_ACCEL() {
        
        // Methods that may be called in the execution of the method we're testing
        expectExCrm(AlertMessageType.ALERT_TYPE_HARD_ACCEL);

        // Instantiate our parent class (necessary before instantiating nested class)
        AlertMessageJDBCDAO alertMessageJDBCDAO = new AlertMessageJDBCDAO();
        
        // Set the mocked version objects
        alertMessageJDBCDAO.setGroupDAO(mockGroupDAO);
        alertMessageJDBCDAO.setVehicleDAO(mockVehicleDAO);
        alertMessageJDBCDAO.setAddressLookup(mockAddressLookup);
        alertMessageJDBCDAO.setZoneDAO(mockZoneDAO);
        
        // Instantiate the class we're testing
        AlertMessageJDBCDAO.EzCrmParameterList parameterList = alertMessageJDBCDAO.new EzCrmParameterList();
        
        parameterList.setLocal(Locale.getDefault());
        parameterList.setMeasurementType(MeasurementType.ENGLISH);
        parameterList.setEvent(mockEventSpeed);
        parameterList.setAlertMessage(mockAlertMessage);
        parameterList.setPerson(mockPerson);
        parameterList.setDriver(mockDriver);
        
        // Run the method  //Deencapsulation.invoke(parameterList, "getDriverOrgStructure", mockDriver);
        List<String> result = parameterList.getParameterListTest();
        
        // Test the result
        int i = 0;
        for (String s : result) {
            System.out.println("["+i+"] "+s);
            ++i;
        }
        assertEquals(result.size(), 18);
    }

    @Test
    public final void testGetEzCrmParameterListALERT_TYPE_HARD_TURN() {
        
        // Methods that may be called in the execution of the method we're testing
        expectExCrm(AlertMessageType.ALERT_TYPE_HARD_TURN);

        // Instantiate our parent class (necessary before instantiating nested class)
        AlertMessageJDBCDAO alertMessageJDBCDAO = new AlertMessageJDBCDAO();
        
        // Set the mocked version objects
        alertMessageJDBCDAO.setGroupDAO(mockGroupDAO);
        alertMessageJDBCDAO.setVehicleDAO(mockVehicleDAO);
        alertMessageJDBCDAO.setAddressLookup(mockAddressLookup);
        alertMessageJDBCDAO.setZoneDAO(mockZoneDAO);
        
        // Instantiate the class we're testing
        AlertMessageJDBCDAO.EzCrmParameterList parameterList = alertMessageJDBCDAO.new EzCrmParameterList();
        
        parameterList.setLocal(Locale.getDefault());
        parameterList.setMeasurementType(MeasurementType.ENGLISH);
        parameterList.setEvent(mockEventSpeed);
        parameterList.setAlertMessage(mockAlertMessage);
        parameterList.setPerson(mockPerson);
        parameterList.setDriver(mockDriver);
        
        // Run the method  //Deencapsulation.invoke(parameterList, "getDriverOrgStructure", mockDriver);
        List<String> result = parameterList.getParameterListTest();
        
        // Test the result
        int i = 0;
        for (String s : result) {
            System.out.println("["+i+"] "+s);
            ++i;
        }
        assertEquals(result.size(), 18);
    }

    @Test
    public final void testGetEzCrmParameterListALERT_TYPE_HARD_BUMP() {
        
        // Methods that may be called in the execution of the method we're testing
        expectExCrm(AlertMessageType.ALERT_TYPE_HARD_BUMP);

        // Instantiate our parent class (necessary before instantiating nested class)
        AlertMessageJDBCDAO alertMessageJDBCDAO = new AlertMessageJDBCDAO();
        
        // Set the mocked version objects
        alertMessageJDBCDAO.setGroupDAO(mockGroupDAO);
        alertMessageJDBCDAO.setVehicleDAO(mockVehicleDAO);
        alertMessageJDBCDAO.setAddressLookup(mockAddressLookup);
        alertMessageJDBCDAO.setZoneDAO(mockZoneDAO);
        
        // Instantiate the class we're testing
        AlertMessageJDBCDAO.EzCrmParameterList parameterList = alertMessageJDBCDAO.new EzCrmParameterList();
        
        parameterList.setLocal(Locale.getDefault());
        parameterList.setMeasurementType(MeasurementType.ENGLISH);
        parameterList.setEvent(mockEventSpeed);
        parameterList.setAlertMessage(mockAlertMessage);
        parameterList.setPerson(mockPerson);
        parameterList.setDriver(mockDriver);
        
        // Run the method  //Deencapsulation.invoke(parameterList, "getDriverOrgStructure", mockDriver);
        List<String> result = parameterList.getParameterListTest();
        
        // Test the result
        int i = 0;
        for (String s : result) {
            System.out.println("["+i+"] "+s);
            ++i;
        }
        assertEquals(result.size(), 18);
    }

    @Test
    public final void testGetEzCrmParameterListALERT_TYPE_NO_DRIVER() {
        
        // Methods that may be called in the execution of the method we're testing
        expectExCrm(AlertMessageType.ALERT_TYPE_NO_DRIVER);

        // Instantiate our parent class (necessary before instantiating nested class)
        AlertMessageJDBCDAO alertMessageJDBCDAO = new AlertMessageJDBCDAO();
        
        // Set the mocked version objects
        alertMessageJDBCDAO.setGroupDAO(mockGroupDAO);
        alertMessageJDBCDAO.setVehicleDAO(mockVehicleDAO);
        alertMessageJDBCDAO.setAddressLookup(mockAddressLookup);
        alertMessageJDBCDAO.setZoneDAO(mockZoneDAO);
        
        // Instantiate the class we're testing
        AlertMessageJDBCDAO.EzCrmParameterList parameterList = alertMessageJDBCDAO.new EzCrmParameterList();
        
        parameterList.setLocal(Locale.getDefault());
        parameterList.setMeasurementType(MeasurementType.ENGLISH);
        parameterList.setEvent(mockEventSpeed);
        parameterList.setAlertMessage(mockAlertMessage);
        parameterList.setPerson(mockPerson);
        parameterList.setDriver(mockDriver);
        
        // Run the method  //Deencapsulation.invoke(parameterList, "getDriverOrgStructure", mockDriver);
        List<String> result = parameterList.getParameterListTest();
        
        // Test the result
        int i = 0;
        for (String s : result) {
            System.out.println("["+i+"] "+s);
            ++i;
        }
        assertEquals(result.size(), 18);
    }

    @Test
    public final void testGetEzCrmParameterListALERT_TYPE_OFF_HOURS() {
        
        // Methods that may be called in the execution of the method we're testing
        expectExCrm(AlertMessageType.ALERT_TYPE_OFF_HOURS);

        // Instantiate our parent class (necessary before instantiating nested class)
        AlertMessageJDBCDAO alertMessageJDBCDAO = new AlertMessageJDBCDAO();
        
        // Set the mocked version objects
        alertMessageJDBCDAO.setGroupDAO(mockGroupDAO);
        alertMessageJDBCDAO.setVehicleDAO(mockVehicleDAO);
        alertMessageJDBCDAO.setAddressLookup(mockAddressLookup);
        alertMessageJDBCDAO.setZoneDAO(mockZoneDAO);
        
        // Instantiate the class we're testing
        AlertMessageJDBCDAO.EzCrmParameterList parameterList = alertMessageJDBCDAO.new EzCrmParameterList();
        
        parameterList.setLocal(Locale.getDefault());
        parameterList.setMeasurementType(MeasurementType.ENGLISH);
        parameterList.setEvent(mockEventSpeed);
        parameterList.setAlertMessage(mockAlertMessage);
        parameterList.setPerson(mockPerson);
        parameterList.setDriver(mockDriver);
        
        // Run the method  //Deencapsulation.invoke(parameterList, "getDriverOrgStructure", mockDriver);
        List<String> result = parameterList.getParameterListTest();
        
        // Test the result
        int i = 0;
        for (String s : result) {
            System.out.println("["+i+"] "+s);
            ++i;
        }
        assertEquals(result.size(), 18);
    }

    @Test
    public final void testGetEzCrmParameterListALERT_TYPE_TEXT_MESSAGE_RECEIVED() {
        
        // Methods that may be called in the execution of the method we're testing
        expectExCrm(AlertMessageType.ALERT_TYPE_TEXT_MESSAGE_RECEIVED);

        // Instantiate our parent class (necessary before instantiating nested class)
        AlertMessageJDBCDAO alertMessageJDBCDAO = new AlertMessageJDBCDAO();
        
        // Set the mocked version objects
        alertMessageJDBCDAO.setGroupDAO(mockGroupDAO);
        alertMessageJDBCDAO.setVehicleDAO(mockVehicleDAO);
        alertMessageJDBCDAO.setAddressLookup(mockAddressLookup);
        alertMessageJDBCDAO.setZoneDAO(mockZoneDAO);
        
        // Instantiate the class we're testing
        AlertMessageJDBCDAO.EzCrmParameterList parameterList = alertMessageJDBCDAO.new EzCrmParameterList();
        
        parameterList.setLocal(Locale.getDefault());
        parameterList.setMeasurementType(MeasurementType.ENGLISH);
        parameterList.setEvent(mockEventSpeed);
        parameterList.setAlertMessage(mockAlertMessage);
        parameterList.setPerson(mockPerson);
        parameterList.setDriver(mockDriver);
        
        // Run the method  //Deencapsulation.invoke(parameterList, "getDriverOrgStructure", mockDriver);
        List<String> result = parameterList.getParameterListTest();
        
        // Test the result
        int i = 0;
        for (String s : result) {
            System.out.println("["+i+"] "+s);
            ++i;
        }
        assertEquals(result.size(), 18);
    }

    @Test
    public final void testGetEzCrmParameterListALERT_TYPE_PARKING_BRAKE() {
        
        // Methods that may be called in the execution of the method we're testing
        expectExCrm(AlertMessageType.ALERT_TYPE_PARKING_BRAKE);

        // Instantiate our parent class (necessary before instantiating nested class)
        AlertMessageJDBCDAO alertMessageJDBCDAO = new AlertMessageJDBCDAO();
        
        // Set the mocked version objects
        alertMessageJDBCDAO.setGroupDAO(mockGroupDAO);
        alertMessageJDBCDAO.setVehicleDAO(mockVehicleDAO);
        alertMessageJDBCDAO.setAddressLookup(mockAddressLookup);
        alertMessageJDBCDAO.setZoneDAO(mockZoneDAO);
        
        // Instantiate the class we're testing
        AlertMessageJDBCDAO.EzCrmParameterList parameterList = alertMessageJDBCDAO.new EzCrmParameterList();
        
        parameterList.setLocal(Locale.getDefault());
        parameterList.setMeasurementType(MeasurementType.ENGLISH);
        parameterList.setEvent(mockEventSpeed);
        parameterList.setAlertMessage(mockAlertMessage);
        parameterList.setPerson(mockPerson);
        parameterList.setDriver(mockDriver);
        
        // Run the method  //Deencapsulation.invoke(parameterList, "getDriverOrgStructure", mockDriver);
        List<String> result = parameterList.getParameterListTest();
        
        // Test the result
        int i = 0;
        for (String s : result) {
            System.out.println("["+i+"] "+s);
            ++i;
        }
        assertEquals(result.size(), 18);
    }

    @Test
    public final void testGetEzCrmParameterListALERT_TYPE_PANIC() {
        
        // Methods that may be called in the execution of the method we're testing
        expectExCrm(AlertMessageType.ALERT_TYPE_PANIC);

        // Instantiate our parent class (necessary before instantiating nested class)
        AlertMessageJDBCDAO alertMessageJDBCDAO = new AlertMessageJDBCDAO();
        
        // Set the mocked version objects
        alertMessageJDBCDAO.setGroupDAO(mockGroupDAO);
        alertMessageJDBCDAO.setVehicleDAO(mockVehicleDAO);
        alertMessageJDBCDAO.setAddressLookup(mockAddressLookup);
        alertMessageJDBCDAO.setZoneDAO(mockZoneDAO);
        
        // Instantiate the class we're testing
        AlertMessageJDBCDAO.EzCrmParameterList parameterList = alertMessageJDBCDAO.new EzCrmParameterList();
        
        parameterList.setLocal(Locale.getDefault());
        parameterList.setMeasurementType(MeasurementType.ENGLISH);
        parameterList.setEvent(mockEventSpeed);
        parameterList.setAlertMessage(mockAlertMessage);
        parameterList.setPerson(mockPerson);
        parameterList.setDriver(mockDriver);
        
        // Run the method  //Deencapsulation.invoke(parameterList, "getDriverOrgStructure", mockDriver);
        List<String> result = parameterList.getParameterListTest();
        
        // Test the result
        int i = 0;
        for (String s : result) {
            System.out.println("["+i+"] "+s);
            ++i;
        }
        assertEquals(result.size(), 18);
    }

    @Test
    public final void testGetEzCrmParameterListALERT_TYPE_MAN_DOWN() {
        
        // Methods that may be called in the execution of the method we're testing
        expectExCrm(AlertMessageType.ALERT_TYPE_MAN_DOWN);

        // Instantiate our parent class (necessary before instantiating nested class)
        AlertMessageJDBCDAO alertMessageJDBCDAO = new AlertMessageJDBCDAO();
        
        // Set the mocked version objects
        alertMessageJDBCDAO.setGroupDAO(mockGroupDAO);
        alertMessageJDBCDAO.setVehicleDAO(mockVehicleDAO);
        alertMessageJDBCDAO.setAddressLookup(mockAddressLookup);
        alertMessageJDBCDAO.setZoneDAO(mockZoneDAO);
        
        // Instantiate the class we're testing
        AlertMessageJDBCDAO.EzCrmParameterList parameterList = alertMessageJDBCDAO.new EzCrmParameterList();
        
        parameterList.setLocal(Locale.getDefault());
        parameterList.setMeasurementType(MeasurementType.ENGLISH);
        parameterList.setEvent(mockEventSpeed);
        parameterList.setAlertMessage(mockAlertMessage);
        parameterList.setPerson(mockPerson);
        parameterList.setDriver(mockDriver);
        
        // Run the method  //Deencapsulation.invoke(parameterList, "getDriverOrgStructure", mockDriver);
        List<String> result = parameterList.getParameterListTest();
        
        // Test the result
        int i = 0;
        for (String s : result) {
            System.out.println("["+i+"] "+s);
            ++i;
        }
        assertEquals(result.size(), 18);
    }

    @Test
    public final void testGetEzCrmParameterListALERT_TYPE_MAN_DOWN_OK() {
        
        // Methods that may be called in the execution of the method we're testing
        expectExCrm(AlertMessageType.ALERT_TYPE_MAN_DOWN_OK);

        // Instantiate our parent class (necessary before instantiating nested class)
        AlertMessageJDBCDAO alertMessageJDBCDAO = new AlertMessageJDBCDAO();
        
        // Set the mocked version objects
        alertMessageJDBCDAO.setGroupDAO(mockGroupDAO);
        alertMessageJDBCDAO.setVehicleDAO(mockVehicleDAO);
        alertMessageJDBCDAO.setAddressLookup(mockAddressLookup);
        alertMessageJDBCDAO.setZoneDAO(mockZoneDAO);
        
        // Instantiate the class we're testing
        AlertMessageJDBCDAO.EzCrmParameterList parameterList = alertMessageJDBCDAO.new EzCrmParameterList();
        
        parameterList.setLocal(Locale.getDefault());
        parameterList.setMeasurementType(MeasurementType.ENGLISH);
        parameterList.setEvent(mockEventSpeed);
        parameterList.setAlertMessage(mockAlertMessage);
        parameterList.setPerson(mockPerson);
        parameterList.setDriver(mockDriver);
        
        // Run the method  //Deencapsulation.invoke(parameterList, "getDriverOrgStructure", mockDriver);
        List<String> result = parameterList.getParameterListTest();
        
        // Test the result
        int i = 0;
        for (String s : result) {
            System.out.println("["+i+"] "+s);
            ++i;
        }
        assertEquals(result.size(), 18);
    }

    @Test
    public final void testGetEzCrmParameterListALERT_TYPE_IGNITION_ON() {
        
        // Methods that may be called in the execution of the method we're testing
        expectExCrm(AlertMessageType.ALERT_TYPE_IGNITION_ON);

        // Instantiate our parent class (necessary before instantiating nested class)
        AlertMessageJDBCDAO alertMessageJDBCDAO = new AlertMessageJDBCDAO();
        
        // Set the mocked version objects
        alertMessageJDBCDAO.setGroupDAO(mockGroupDAO);
        alertMessageJDBCDAO.setVehicleDAO(mockVehicleDAO);
        alertMessageJDBCDAO.setAddressLookup(mockAddressLookup);
        alertMessageJDBCDAO.setZoneDAO(mockZoneDAO);
        
        // Instantiate the class we're testing
        AlertMessageJDBCDAO.EzCrmParameterList parameterList = alertMessageJDBCDAO.new EzCrmParameterList();
        
        parameterList.setLocal(Locale.getDefault());
        parameterList.setMeasurementType(MeasurementType.ENGLISH);
        parameterList.setEvent(mockEventSpeed);
        parameterList.setAlertMessage(mockAlertMessage);
        parameterList.setPerson(mockPerson);
        parameterList.setDriver(mockDriver);
        
        // Run the method  //Deencapsulation.invoke(parameterList, "getDriverOrgStructure", mockDriver);
        List<String> result = parameterList.getParameterListTest();
        
        // Test the result
        int i = 0;
        for (String s : result) {
            System.out.println("["+i+"] "+s);
            ++i;
        }
        assertEquals(result.size(), 18);
    }

    @Test
    public final void testGetEzCrmParameterListALERT_TYPE_HOS_DOT_STOPPED() {
        
        // Methods that may be called in the execution of the method we're testing
        expectExCrm(AlertMessageType.ALERT_TYPE_HOS_DOT_STOPPED);

        // Instantiate our parent class (necessary before instantiating nested class)
        AlertMessageJDBCDAO alertMessageJDBCDAO = new AlertMessageJDBCDAO();
        
        // Set the mocked version objects
        alertMessageJDBCDAO.setGroupDAO(mockGroupDAO);
        alertMessageJDBCDAO.setVehicleDAO(mockVehicleDAO);
        alertMessageJDBCDAO.setAddressLookup(mockAddressLookup);
        alertMessageJDBCDAO.setZoneDAO(mockZoneDAO);
        
        // Instantiate the class we're testing
        AlertMessageJDBCDAO.EzCrmParameterList parameterList = alertMessageJDBCDAO.new EzCrmParameterList();
        
        parameterList.setLocal(Locale.getDefault());
        parameterList.setMeasurementType(MeasurementType.ENGLISH);
        parameterList.setEvent(mockEventSpeed);
        parameterList.setAlertMessage(mockAlertMessage);
        parameterList.setPerson(mockPerson);
        parameterList.setDriver(mockDriver);
        
        // Run the method  //Deencapsulation.invoke(parameterList, "getDriverOrgStructure", mockDriver);
        List<String> result = parameterList.getParameterListTest();
        
        // Test the result
        int i = 0;
        for (String s : result) {
            System.out.println("["+i+"] "+s);
            ++i;
        }
        assertEquals(result.size(), 18);
    }

    @Test
    public final void testGetEzCrmParameterListALERT_TYPE_HOS_NO_HOURS_REMAINING() {
        
        // Methods that may be called in the execution of the method we're testing
        expectExCrm(AlertMessageType.ALERT_TYPE_HOS_NO_HOURS_REMAINING);

        // Instantiate our parent class (necessary before instantiating nested class)
        AlertMessageJDBCDAO alertMessageJDBCDAO = new AlertMessageJDBCDAO();
        
        // Set the mocked version objects
        alertMessageJDBCDAO.setGroupDAO(mockGroupDAO);
        alertMessageJDBCDAO.setVehicleDAO(mockVehicleDAO);
        alertMessageJDBCDAO.setAddressLookup(mockAddressLookup);
        alertMessageJDBCDAO.setZoneDAO(mockZoneDAO);
        
        // Instantiate the class we're testing
        AlertMessageJDBCDAO.EzCrmParameterList parameterList = alertMessageJDBCDAO.new EzCrmParameterList();
        
        parameterList.setLocal(Locale.getDefault());
        parameterList.setMeasurementType(MeasurementType.ENGLISH);
        parameterList.setEvent(mockEventSpeed);
        parameterList.setAlertMessage(mockAlertMessage);
        parameterList.setPerson(mockPerson);
        parameterList.setDriver(mockDriver);
        
        // Run the method  //Deencapsulation.invoke(parameterList, "getDriverOrgStructure", mockDriver);
        List<String> result = parameterList.getParameterListTest();
        
        // Test the result
        int i = 0;
        for (String s : result) {
            System.out.println("["+i+"] "+s);
            ++i;
        }
        assertEquals(result.size(), 18);
    }

    @Test
    public final void testGetEzCrmParameterListALERT_TYPE_WIRELINE_ALARM() {
        
        // Methods that may be called in the execution of the method we're testing
        expectExCrm(AlertMessageType.ALERT_TYPE_WIRELINE_ALARM);

        // Instantiate our parent class (necessary before instantiating nested class)
        AlertMessageJDBCDAO alertMessageJDBCDAO = new AlertMessageJDBCDAO();
        
        // Set the mocked version objects
        alertMessageJDBCDAO.setGroupDAO(mockGroupDAO);
        alertMessageJDBCDAO.setVehicleDAO(mockVehicleDAO);
        alertMessageJDBCDAO.setAddressLookup(mockAddressLookup);
        alertMessageJDBCDAO.setZoneDAO(mockZoneDAO);
        
        // Instantiate the class we're testing
        AlertMessageJDBCDAO.EzCrmParameterList parameterList = alertMessageJDBCDAO.new EzCrmParameterList();
        
        parameterList.setLocal(Locale.getDefault());
        parameterList.setMeasurementType(MeasurementType.ENGLISH);
        parameterList.setEvent(mockEventSpeed);
        parameterList.setAlertMessage(mockAlertMessage);
        parameterList.setPerson(mockPerson);
        parameterList.setDriver(mockDriver);
        
        // Run the method  //Deencapsulation.invoke(parameterList, "getDriverOrgStructure", mockDriver);
        List<String> result = parameterList.getParameterListTest();
        
        // Test the result
        int i = 0;
        for (String s : result) {
            System.out.println("["+i+"] "+s);
            ++i;
        }
        assertEquals(result.size(), 18);
    }

    @Test
    public final void testGetEzCrmParameterListALERT_TYPE_DSS_MICROSLEEP() {
        
        // Methods that may be called in the execution of the method we're testing
        expectExCrm(AlertMessageType.ALERT_TYPE_DSS_MICROSLEEP);

        // Instantiate our parent class (necessary before instantiating nested class)
        AlertMessageJDBCDAO alertMessageJDBCDAO = new AlertMessageJDBCDAO();
        
        // Set the mocked version objects
        alertMessageJDBCDAO.setGroupDAO(mockGroupDAO);
        alertMessageJDBCDAO.setVehicleDAO(mockVehicleDAO);
        alertMessageJDBCDAO.setAddressLookup(mockAddressLookup);
        alertMessageJDBCDAO.setZoneDAO(mockZoneDAO);
        
        // Instantiate the class we're testing
        AlertMessageJDBCDAO.EzCrmParameterList parameterList = alertMessageJDBCDAO.new EzCrmParameterList();
        
        parameterList.setLocal(Locale.getDefault());
        parameterList.setMeasurementType(MeasurementType.ENGLISH);
        parameterList.setEvent(mockEventSpeed);
        parameterList.setAlertMessage(mockAlertMessage);
        parameterList.setPerson(mockPerson);
        parameterList.setDriver(mockDriver);
        
        // Run the method  //Deencapsulation.invoke(parameterList, "getDriverOrgStructure", mockDriver);
        List<String> result = parameterList.getParameterListTest();
        
        // Test the result
        int i = 0;
        for (String s : result) {
            System.out.println("["+i+"] "+s);
            ++i;
        }
        assertEquals(result.size(), 18);
    }

    @Test
    public final void testGetEzCrmParameterListALERT_TYPE_INSTALL() {
        
        // Methods that may be called in the execution of the method we're testing
        expectExCrm(AlertMessageType.ALERT_TYPE_INSTALL);

        // Instantiate our parent class (necessary before instantiating nested class)
        AlertMessageJDBCDAO alertMessageJDBCDAO = new AlertMessageJDBCDAO();
        
        // Set the mocked version objects
        alertMessageJDBCDAO.setGroupDAO(mockGroupDAO);
        alertMessageJDBCDAO.setVehicleDAO(mockVehicleDAO);
        alertMessageJDBCDAO.setAddressLookup(mockAddressLookup);
        alertMessageJDBCDAO.setZoneDAO(mockZoneDAO);
        
        // Instantiate the class we're testing
        AlertMessageJDBCDAO.EzCrmParameterList parameterList = alertMessageJDBCDAO.new EzCrmParameterList();
        
        parameterList.setLocal(Locale.getDefault());
        parameterList.setMeasurementType(MeasurementType.ENGLISH);
        parameterList.setEvent(mockEventSpeed);
        parameterList.setAlertMessage(mockAlertMessage);
        parameterList.setPerson(mockPerson);
        parameterList.setDriver(mockDriver);
        
        // Run the method  //Deencapsulation.invoke(parameterList, "getDriverOrgStructure", mockDriver);
        List<String> result = parameterList.getParameterListTest();
        
        // Test the result
        int i = 0;
        for (String s : result) {
            System.out.println("["+i+"] "+s);
            ++i;
        }
        assertEquals(result.size(), 18);
    }

    @Test
    public final void testGetEzCrmParameterListALERT_TYPE_FIRMWARE_CURRENT() {
        
        // Methods that may be called in the execution of the method we're testing
        expectExCrm(AlertMessageType.ALERT_TYPE_FIRMWARE_CURRENT);

        // Instantiate our parent class (necessary before instantiating nested class)
        AlertMessageJDBCDAO alertMessageJDBCDAO = new AlertMessageJDBCDAO();
        
        // Set the mocked version objects
        alertMessageJDBCDAO.setGroupDAO(mockGroupDAO);
        alertMessageJDBCDAO.setVehicleDAO(mockVehicleDAO);
        alertMessageJDBCDAO.setAddressLookup(mockAddressLookup);
        alertMessageJDBCDAO.setZoneDAO(mockZoneDAO);
        alertMessageJDBCDAO.setDeviceDAO(mockDeviceDAO);
        
        // Instantiate the class we're testing
        AlertMessageJDBCDAO.EzCrmParameterList parameterList = alertMessageJDBCDAO.new EzCrmParameterList();
        
        parameterList.setLocal(Locale.getDefault());
        parameterList.setMeasurementType(MeasurementType.ENGLISH);
        parameterList.setEvent(mockEventSpeed);
        parameterList.setAlertMessage(mockAlertMessage);
        parameterList.setPerson(mockPerson);
        parameterList.setDriver(mockDriver);
        
        // Run the method  //Deencapsulation.invoke(parameterList, "getDriverOrgStructure", mockDriver);
        List<String> result = parameterList.getParameterListTest();
        
        // Test the result
        int i = 0;
        for (String s : result) {
            System.out.println("["+i+"] "+s);
            ++i;
        }
        assertEquals(result.size(), 19);
    }

    @Test
    public final void testGetEzCrmParameterListALERT_TYPE_LOCATION_DEBUG() {
        
        // Methods that may be called in the execution of the method we're testing
        expectExCrm(AlertMessageType.ALERT_TYPE_LOCATION_DEBUG);

        // Instantiate our parent class (necessary before instantiating nested class)
        AlertMessageJDBCDAO alertMessageJDBCDAO = new AlertMessageJDBCDAO();
        
        // Set the mocked version objects
        alertMessageJDBCDAO.setGroupDAO(mockGroupDAO);
        alertMessageJDBCDAO.setVehicleDAO(mockVehicleDAO);
        alertMessageJDBCDAO.setAddressLookup(mockAddressLookup);
        alertMessageJDBCDAO.setZoneDAO(mockZoneDAO);
        
        // Instantiate the class we're testing
        AlertMessageJDBCDAO.EzCrmParameterList parameterList = alertMessageJDBCDAO.new EzCrmParameterList();
        
        parameterList.setLocal(Locale.getDefault());
        parameterList.setMeasurementType(MeasurementType.ENGLISH);
        parameterList.setEvent(mockEventSpeed);
        parameterList.setAlertMessage(mockAlertMessage);
        parameterList.setPerson(mockPerson);
        parameterList.setDriver(mockDriver);
        
        // Run the method  //Deencapsulation.invoke(parameterList, "getDriverOrgStructure", mockDriver);
        List<String> result = parameterList.getParameterListTest();
        
        // Test the result
        int i = 0;
        for (String s : result) {
            System.out.println("["+i+"] "+s);
            ++i;
        }
        assertEquals(result.size(), 18);
    }

    @Test
    public final void testGetEzCrmParameterListALERT_TYPE_QSI_UPDATED() {
        
        // Methods that may be called in the execution of the method we're testing
        expectExCrm(AlertMessageType.ALERT_TYPE_QSI_UPDATED);

        // Instantiate our parent class (necessary before instantiating nested class)
        AlertMessageJDBCDAO alertMessageJDBCDAO = new AlertMessageJDBCDAO();
        
        // Set the mocked version objects
        alertMessageJDBCDAO.setGroupDAO(mockGroupDAO);
        alertMessageJDBCDAO.setVehicleDAO(mockVehicleDAO);
        alertMessageJDBCDAO.setAddressLookup(mockAddressLookup);
        alertMessageJDBCDAO.setZoneDAO(mockZoneDAO);
        
        // Instantiate the class we're testing
        AlertMessageJDBCDAO.EzCrmParameterList parameterList = alertMessageJDBCDAO.new EzCrmParameterList();
        
        parameterList.setLocal(Locale.getDefault());
        parameterList.setMeasurementType(MeasurementType.ENGLISH);
        parameterList.setEvent(mockEvent);
        parameterList.setAlertMessage(mockAlertMessage);
        parameterList.setPerson(mockPerson);
        parameterList.setDriver(mockDriver);
        
        // Run the method  //Deencapsulation.invoke(parameterList, "getDriverOrgStructure", mockDriver);
        List<String> result = parameterList.getParameterListTest();
        
        // Test the result
        int i = 0;
        for (String s : result) {
            System.out.println("["+i+"] "+s);
            ++i;
        }
        assertEquals(result.size(), 19);
    }

    @Test
    public final void testGetEzCrmParameterListALERT_TYPE_WITNESS_UPDATED() {
        
        // Methods that may be called in the execution of the method we're testing
        expectExCrm(AlertMessageType.ALERT_TYPE_WITNESS_UPDATED);

        // Instantiate our parent class (necessary before instantiating nested class)
        AlertMessageJDBCDAO alertMessageJDBCDAO = new AlertMessageJDBCDAO();
        
        // Set the mocked version objects
        alertMessageJDBCDAO.setGroupDAO(mockGroupDAO);
        alertMessageJDBCDAO.setVehicleDAO(mockVehicleDAO);
        alertMessageJDBCDAO.setAddressLookup(mockAddressLookup);
        alertMessageJDBCDAO.setZoneDAO(mockZoneDAO);
        alertMessageJDBCDAO.setDeviceDAO(mockDeviceDAO);
        
        // Instantiate the class we're testing
        AlertMessageJDBCDAO.EzCrmParameterList parameterList = alertMessageJDBCDAO.new EzCrmParameterList();
        
        parameterList.setLocal(Locale.getDefault());
        parameterList.setMeasurementType(MeasurementType.ENGLISH);
        parameterList.setEvent(mockEvent);
        parameterList.setAlertMessage(mockAlertMessage);
        parameterList.setPerson(mockPerson);
        parameterList.setDriver(mockDriver);
        
        // Run the method  //Deencapsulation.invoke(parameterList, "getDriverOrgStructure", mockDriver);
        List<String> result = parameterList.getParameterListTest();
        
        // Test the result
        int i = 0;
        for (String s : result) {
            System.out.println("["+i+"] "+s);
            ++i;
        }
        assertEquals(result.size(), 19);
        assertEquals(result.get(2), "0");
        assertEquals(result.get(7), "my Big Truck");
        assertEquals(result.get(10), "Ford");
        assertEquals(result.get(14), "Salt Lake City, Utah");
        assertEquals(result.get(17), "0");
    }

    @Test
    public final void testGetEzCrmParameterListALERT_TYPE_ZONES_CURRENT() {
        
        // Methods that may be called in the execution of the method we're testing
        expectExCrm(AlertMessageType.ALERT_TYPE_ZONES_CURRENT);

        // Instantiate our parent class (necessary before instantiating nested class)
        AlertMessageJDBCDAO alertMessageJDBCDAO = new AlertMessageJDBCDAO();
        
        // Set the mocked version objects
        alertMessageJDBCDAO.setGroupDAO(mockGroupDAO);
        alertMessageJDBCDAO.setVehicleDAO(mockVehicleDAO);
        alertMessageJDBCDAO.setAddressLookup(mockAddressLookup);
        alertMessageJDBCDAO.setZoneDAO(mockZoneDAO);
        
        // Instantiate the class we're testing
        AlertMessageJDBCDAO.EzCrmParameterList parameterList = alertMessageJDBCDAO.new EzCrmParameterList();
        
        parameterList.setLocal(Locale.getDefault());
        parameterList.setMeasurementType(MeasurementType.ENGLISH);
        parameterList.setEvent(mockEventSpeed);
        parameterList.setAlertMessage(mockAlertMessage);
        parameterList.setPerson(mockPerson);
        parameterList.setDriver(mockDriver);
        
        // Run the method  //Deencapsulation.invoke(parameterList, "getDriverOrgStructure", mockDriver);
        List<String> result = parameterList.getParameterListTest();
        
        // Test the result
        int i = 0;
        for (String s : result) {
            System.out.println("["+i+"] "+s);
            ++i;
        }
        assertEquals(result.size(), 19);
        assertEquals(result.get(2), "0");
        assertEquals(result.get(7), "my Big Truck");
        assertEquals(result.get(10), "Ford");
        assertEquals(result.get(14), "Salt Lake City, Utah");
        assertEquals(result.get(17), "0");
    }

    @Test
    public final void testGetEzCrmParameterListALERT_TYPE_NO_INTERNAL_THUMB_DRIVE() {
        
        // Methods that may be called in the execution of the method we're testing
        expectExCrm(AlertMessageType.ALERT_TYPE_NO_INTERNAL_THUMB_DRIVE);

        // Instantiate our parent class (necessary before instantiating nested class)
        AlertMessageJDBCDAO alertMessageJDBCDAO = new AlertMessageJDBCDAO();
        
        // Set the mocked version objects
        alertMessageJDBCDAO.setGroupDAO(mockGroupDAO);
        alertMessageJDBCDAO.setVehicleDAO(mockVehicleDAO);
        alertMessageJDBCDAO.setAddressLookup(mockAddressLookup);
        alertMessageJDBCDAO.setZoneDAO(mockZoneDAO);
        
        // Instantiate the class we're testing
        AlertMessageJDBCDAO.EzCrmParameterList parameterList = alertMessageJDBCDAO.new EzCrmParameterList();
        
        parameterList.setLocal(Locale.getDefault());
        parameterList.setMeasurementType(MeasurementType.ENGLISH);
        parameterList.setEvent(mockEventSpeed);
        parameterList.setAlertMessage(mockAlertMessage);
        parameterList.setPerson(mockPerson);
        parameterList.setDriver(mockDriver);
        
        // Run the method  //Deencapsulation.invoke(parameterList, "getDriverOrgStructure", mockDriver);
        List<String> result = parameterList.getParameterListTest();
        
        // Test the result
        int i = 0;
        for (String s : result) {
            System.out.println("["+i+"] "+s);
            ++i;
        }
        assertEquals(result.size(), 18);
        assertEquals(result.get(2), "0");
        assertEquals(result.get(7), "my Big Truck");
        assertEquals(result.get(10), "Ford");
        assertEquals(result.get(14), "Salt Lake City, Utah");
        assertEquals(result.get(17), "0");
    }

    @Test
    public final void testGetEzCrmParameterListALERT_TYPE_WITNESS_HEARTBEAT_VIOLATION() {
        
        // Methods that may be called in the execution of the method we're testing
        expectExCrm(AlertMessageType.ALERT_TYPE_WITNESS_HEARTBEAT_VIOLATION);

        // Instantiate our parent class (necessary before instantiating nested class)
        AlertMessageJDBCDAO alertMessageJDBCDAO = new AlertMessageJDBCDAO();
        
        // Set the mocked version objects
        alertMessageJDBCDAO.setGroupDAO(mockGroupDAO);
        alertMessageJDBCDAO.setVehicleDAO(mockVehicleDAO);
        alertMessageJDBCDAO.setAddressLookup(mockAddressLookup);
        alertMessageJDBCDAO.setZoneDAO(mockZoneDAO);
        
        // Instantiate the class we're testing
        AlertMessageJDBCDAO.EzCrmParameterList parameterList = alertMessageJDBCDAO.new EzCrmParameterList();
        
        parameterList.setLocal(Locale.getDefault());
        parameterList.setMeasurementType(MeasurementType.ENGLISH);
        parameterList.setEvent(mockEventSpeed);
        parameterList.setAlertMessage(mockAlertMessage);
        parameterList.setPerson(mockPerson);
        parameterList.setDriver(mockDriver);
        
        // Run the method  //Deencapsulation.invoke(parameterList, "getDriverOrgStructure", mockDriver);
        List<String> result = parameterList.getParameterListTest();
        
        // Test the result
        int i = 0;
        for (String s : result) {
            System.out.println("["+i+"] "+s);
            ++i;
        }
        assertEquals(result.size(), 18);
        assertEquals(result.get(2), "0");
        assertEquals(result.get(7), "my Big Truck");
        assertEquals(result.get(10), "Ford");
        assertEquals(result.get(14), "Salt Lake City, Utah");
        assertEquals(result.get(17), "0");
    }

    @Test
    public final void testGetEzCrmParameterListALERT_TYPE_IDLING() {

        // Methods that may be called in the execution of the method we're testing
        expectExCrm(AlertMessageType.ALERT_TYPE_IDLING);

        // Instantiate our parent class (necessary before instantiating nested class)
        AlertMessageJDBCDAO alertMessageJDBCDAO = new AlertMessageJDBCDAO();
        
        // Set the mocked version objects
        alertMessageJDBCDAO.setGroupDAO(mockGroupDAO);
        alertMessageJDBCDAO.setVehicleDAO(mockVehicleDAO);
        alertMessageJDBCDAO.setAddressLookup(mockAddressLookup);
        alertMessageJDBCDAO.setZoneDAO(mockZoneDAO);
        
        // Instantiate the class we're testing
        AlertMessageJDBCDAO.EzCrmParameterList parameterList = alertMessageJDBCDAO.new EzCrmParameterList();
        
        parameterList.setLocal(Locale.getDefault());
        parameterList.setMeasurementType(MeasurementType.ENGLISH);
        parameterList.setEvent(mockEventIdle);
        parameterList.setAlertMessage(mockAlertMessage);
        parameterList.setPerson(mockPerson);
        parameterList.setDriver(mockDriver);
        
        // Run the method  //Deencapsulation.invoke(parameterList, "getDriverOrgStructure", mockDriver);
        List<String> result = parameterList.getParameterListTest();
        
        // Test the result
        int i = 0;
        for (String s : result) {
            System.out.println("["+i+"] "+s);
            ++i;
        }
        assertEquals(result.size(), 19);
        assertEquals(result.get(2), "0");
        assertEquals(result.get(7), "my Big Truck");
        assertEquals(result.get(10), "Ford");
        assertEquals(result.get(14), "Salt Lake City, Utah");
        assertEquals(result.get(17), "0");
        assertEquals("56", result.get(18));
    }


    @Test
    public final void testNoSpeedConversion() {
        expectExCrm(AlertMessageType.ALERT_TYPE_SPEEDING, MeasurementType.ENGLISH, 100);

        // Instantiate our parent class (necessary before instantiating nested class)
        AlertMessageJDBCDAO alertMessageJDBCDAO = new AlertMessageJDBCDAO();

        // Set the mocked version objects
        alertMessageJDBCDAO.setGroupDAO(mockGroupDAO);
        alertMessageJDBCDAO.setVehicleDAO(mockVehicleDAO);
        alertMessageJDBCDAO.setAddressLookup(mockAddressLookup);
        alertMessageJDBCDAO.setZoneDAO(mockZoneDAO);

        // Instantiate the class we're testing
        AlertMessageJDBCDAO.EzCrmParameterList parameterList = alertMessageJDBCDAO.new EzCrmParameterList();

        parameterList.setLocal(Locale.getDefault());
        parameterList.setMeasurementType(MeasurementType.ENGLISH);
        parameterList.setEvent(mockEventSpeed);
        parameterList.setAlertMessage(mockAlertMessage);
        parameterList.setPerson(mockPerson);
        parameterList.setDriver(mockDriver);

        // Run the method  //Deencapsulation.invoke(parameterList, "getDriverOrgStructure", mockDriver);
        List<String> result = parameterList.getParameterListTest();

        // Test the result
        int i = 0;
        for (String s : result) {
            System.out.println("["+i+"] "+s);
            ++i;
        }
        assertEquals("100", result.get(16));
    }

    @Test
    public final void testKMSpeedConversion() {
        expectExCrm(AlertMessageType.ALERT_TYPE_SPEEDING, MeasurementType.METRIC, 100);

        // Instantiate our parent class (necessary before instantiating nested class)
        AlertMessageJDBCDAO alertMessageJDBCDAO = new AlertMessageJDBCDAO();

        // Set the mocked version objects
        alertMessageJDBCDAO.setGroupDAO(mockGroupDAO);
        alertMessageJDBCDAO.setVehicleDAO(mockVehicleDAO);
        alertMessageJDBCDAO.setAddressLookup(mockAddressLookup);
        alertMessageJDBCDAO.setZoneDAO(mockZoneDAO);

        // Instantiate the class we're testing
        AlertMessageJDBCDAO.EzCrmParameterList parameterList = alertMessageJDBCDAO.new EzCrmParameterList();

        parameterList.setLocal(Locale.getDefault());
        parameterList.setMeasurementType(MeasurementType.ENGLISH);
        parameterList.setEvent(mockEventSpeed);
        parameterList.setAlertMessage(mockAlertMessage);
        parameterList.setPerson(mockPerson);
        parameterList.setDriver(mockDriver);

        // Run the method  //Deencapsulation.invoke(parameterList, "getDriverOrgStructure", mockDriver);
        List<String> result = parameterList.getParameterListTest();

        // Test the result
        int i = 0;
        for (String s : result) {
            System.out.println("["+i+"] "+s);
            ++i;
        }
        assertEquals("160", result.get(16));
    }
}
