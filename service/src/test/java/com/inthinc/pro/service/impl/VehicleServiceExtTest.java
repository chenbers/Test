package com.inthinc.pro.service.impl;

import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.model.*;
import com.inthinc.pro.service.VehicleServiceExt;
import com.inthinc.pro.service.adapters.VehicleDAOAdapter;
import mockit.Expectations;
import mockit.Mocked;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import java.util.*;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class VehicleServiceExtTest {
    static final String NAME_MODIFIER = "3";

    @Mocked
    public VehicleDAO mockVehicleDAO;

    private VehicleServiceExt vehicleServiceExt;

    private VehicleDAOAdapter vehicleDAOAdapter;
    private VehicleDAOAdapter mockVehicleDAOAdapter;
    // test data
    private Map<Integer, Vehicle> testVehicles;
    private Vehicle vehicle1;
    private Vehicle vehicle2;
    private Vehicle vehicle3;
    private Group group;
    private LastLocation mockLastLocation;
    private Trip mockLastTrip;
    private Trip mockTrip1;
    private Trip mockTrip2;
    private Trip mockTrip3;
    private List<Trip> mockTripList;

    @Before
    public void createTestData() {
        vehicleServiceExt = new VehicleServiceExtImpl();

        mockTrip1 = new Trip();
        mockTrip1.setEndAddressStr("aok");

        mockTrip2 = new Trip();
        mockTrip2.setEndAddressStr("aok");

        mockTrip3 = new Trip();
        mockTrip3.setEndAddressStr("aok");

        mockTripList = Arrays.asList(mockTrip1, mockTrip2, mockTrip3);

        mockLastLocation = new LastLocation();
        mockLastLocation.setVehicleStatus("aok");


        mockLastTrip = new Trip();
        mockLastTrip.setEndAddressStr("aok");

        mockVehicleDAOAdapter = new VehicleDAOAdapter();
        mockVehicleDAOAdapter.setVehicleDAO(mockVehicleDAO);
        VehicleServiceExtImpl vehicleServiceExtImpl = (VehicleServiceExtImpl) vehicleServiceExt;
        vehicleServiceExtImpl.setDao(mockVehicleDAOAdapter);

        testVehicles = new HashMap<Integer, Vehicle>();
        vehicle1 = new Vehicle();
        vehicle2 = new Vehicle();
        vehicle3 = new Vehicle();

        group = new Group();
        group.setGroupID(1);

        vehicle1.setDriverID(1);
        vehicle1.setVehicleID(1);
        vehicle1.setStatus(Status.ACTIVE);
        vehicle1.setName("name_1" + NAME_MODIFIER);
        vehicle1.setVtype(VehicleType.HEAVY);
        vehicle1.setColor("red");
        vehicle1.setIfta(true);
        vehicle1.setLicense("Test Rest");
        vehicle1.setVIN(NAME_MODIFIER + "90000000901");
        vehicle1.setModel("tes1");
        vehicle1.setMake("tes1");
        vehicle1.setModified(new Date());
        vehicle1.setWeight(1000);
        vehicle1.setYear(1991);
        vehicle1.setGroupID(group.getGroupID());

        vehicle2.setDriverID(2);
        vehicle2.setVehicleID(2);
        vehicle2.setStatus(Status.ACTIVE);
        vehicle2.setName("name_2" + NAME_MODIFIER);
        vehicle2.setVtype(VehicleType.LIGHT);
        vehicle2.setColor("yellow");
        vehicle2.setIfta(true);
        vehicle2.setLicense("Test Rest2");
        vehicle2.setVIN(NAME_MODIFIER + "90000000902");
        vehicle2.setModel("tes2");
        vehicle2.setMake("tes2");
        vehicle2.setModified(new Date());
        vehicle2.setWeight(1001);
        vehicle2.setYear(1992);
        vehicle2.setGroupID(group.getGroupID());

        vehicle3.setDriverID(3);
        vehicle3.setVehicleID(3);
        vehicle3.setStatus(Status.ACTIVE);
        vehicle3.setName("name_3" + NAME_MODIFIER);
        vehicle3.setVtype(VehicleType.MEDIUM);
        vehicle3.setColor("blue");
        vehicle3.setIfta(true);
        vehicle3.setLicense("Test Rest3");
        vehicle3.setVIN(NAME_MODIFIER + "90000000903");
        vehicle3.setModel("tes3");
        vehicle3.setMake("tes3");
        vehicle3.setModified(new Date());
        vehicle3.setWeight(1003);
        vehicle3.setYear(1993);
        vehicle3.setGroupID(group.getGroupID());

        // add vehicles to list
        testVehicles.put(1, vehicle1);
        testVehicles.put(2, vehicle2);
        testVehicles.put(3, vehicle3);
    }

    @After
    public void deleteTestData() {

    }

    @Test
    public void getLocationTest() {

        new Expectations() {{
            mockVehicleDAO.findByName(vehicle1.getName());
            result = vehicle1;
            mockVehicleDAO.getLastLocation(vehicle1.getVehicleID());
            result = mockLastLocation;
            mockVehicleDAO.findByName(vehicle2.getName());
            result = vehicle2;
            mockVehicleDAO.getLastLocation(vehicle2.getVehicleID());
            result = mockLastLocation;
            mockVehicleDAO.findByName(vehicle3.getName());
            result = vehicle3;
            mockVehicleDAO.getLastLocation(vehicle3.getVehicleID());
            result = mockLastLocation;
        }};


        for (int i = 1; i <= 3; i++) {
            Response response = vehicleServiceExt.getLastLocation("name_" + i + "" + NAME_MODIFIER);
            assertNotNull(response.getEntity());
            LastLocation lastLocation = (LastLocation) response.getEntity();
            assertNotNull(lastLocation);
            assertEquals(lastLocation.getVehicleStatus(), "aok");
        }
    }

    @Test
    public void getTripTest() {

        new Expectations() {{

            mockVehicleDAO.findByName(vehicle1.getName());
            result = vehicle1;
            mockVehicleDAO.getLastTrip(vehicle1.getDriverID());
            result = mockLastTrip;
            mockVehicleDAO.findByName(vehicle2.getName());
            result = vehicle2;
            mockVehicleDAO.getLastTrip(vehicle2.getDriverID());
            result = mockLastTrip;
            mockVehicleDAO.findByName(vehicle3.getName());
            result = vehicle3;
            mockVehicleDAO.getLastTrip(vehicle3.getDriverID());
            result = mockLastTrip;
        }};

        for (int i = 1; i <= 3; i++) {
            Response response = vehicleServiceExt.getLastTrip("name_" + i + "" + NAME_MODIFIER);
            assertNotNull(response.getEntity());
            GenericEntity genericEntity = (GenericEntity)response.getEntity();
            Trip lastTrip = (Trip) genericEntity.getEntity();
            assertNotNull(lastTrip);
            assertEquals(lastTrip.getEndAddressStr(), "aok");
        }
    }

//    @Test
//    public void getTripsTest(){
//        VehicleServiceExtImpl vehicleServiceExtImpl = (VehicleServiceExtImpl) vehicleServiceExt;
//        try {
//            vehicleServiceExtImpl.setDao(mockVehicleDAOAdapter);
//
//            new NonStrictExpectations() {{
//
//                mockVehicleDAO.findByName(vehicle1.getName()); result = vehicle1;
//                mockVehicleDAO.getTrips(vehicle1.getVehicleID(), (Date)anyObject(), (Date)anyObject()); result = mockTripList;
//                mockVehicleDAO.findByName(vehicle2.getName()); result = vehicle2;
//                mockVehicleDAO.getTrips(vehicle2.getVehicleID(), (Date)anyObject(), (Date)anyObject()); result = mockTripList;
//                mockVehicleDAO.findByName(vehicle3.getName()); result = vehicle3;
//                mockVehicleDAO.getTrips(vehicle3.getVehicleID(),(Date)anyObject(), (Date)anyObject()); result = mockTripList;
//            }};
//
//            for (int i = 1; i <= 3; i++) {
//                Response response = vehicleServiceExt.getTrips("dname_" + i);
//                assertNotNull(response.getEntity());
//                List<Trip> tripList = (List<Trip>) response.getEntity();
//                assertNotNull(tripList);
//
//                for (Trip trip: tripList){
//                    assertEquals(trip.getEndAddressStr(), "aok");
//                }
//            }
//        }finally {
//            vehicleServiceExtImpl.setDao(vehicleDAOAdapter);
//        }
//    }

    public VehicleServiceExt getVehicleServiceExt() {
        return vehicleServiceExt;
    }

    public void setVehicleServiceExt(VehicleServiceExt vehicleServiceExt) {
        this.vehicleServiceExt = vehicleServiceExt;
    }

    public VehicleDAO getMockVehicleDAO() {
        return mockVehicleDAO;
    }

    public void setMockVehicleDAO(VehicleDAO mockVehicleDAO) {
        this.mockVehicleDAO = mockVehicleDAO;
    }

    public VehicleDAOAdapter getVehicleDAOAdapter() {
        return vehicleDAOAdapter;
    }

    public void setVehicleDAOAdapter(VehicleDAOAdapter vehicleDAOAdapter) {
        this.vehicleDAOAdapter = vehicleDAOAdapter;
    }

    public VehicleDAOAdapter getMockVehicleDAOAdapter() {
        return mockVehicleDAOAdapter;
    }

    public void setMockVehicleDAOAdapter(VehicleDAOAdapter mockVehicleDAOAdapter) {
        this.mockVehicleDAOAdapter = mockVehicleDAOAdapter;
    }

    public Map<Integer, Vehicle> getTestVehicles() {
        return testVehicles;
    }

    public void setTestVehicles(Map<Integer, Vehicle> testVehicles) {
        this.testVehicles = testVehicles;
    }

    public Vehicle getVehicle1() {
        return vehicle1;
    }

    public void setVehicle1(Vehicle vehicle1) {
        this.vehicle1 = vehicle1;
    }

    public Vehicle getVehicle2() {
        return vehicle2;
    }

    public void setVehicle2(Vehicle vehicle2) {
        this.vehicle2 = vehicle2;
    }

    public Vehicle getVehicle3() {
        return vehicle3;
    }

    public void setVehicle3(Vehicle vehicle3) {
        this.vehicle3 = vehicle3;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public LastLocation getMockLastLocation() {
        return mockLastLocation;
    }

    public void setMockLastLocation(LastLocation mockLastLocation) {
        this.mockLastLocation = mockLastLocation;
    }
}
