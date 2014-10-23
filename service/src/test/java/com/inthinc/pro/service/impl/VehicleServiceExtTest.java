package com.inthinc.pro.service.impl;

import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.dao.VehicleDAO;
import com.inthinc.pro.dao.report.VehicleReportDAO;
import com.inthinc.pro.model.*;
import com.inthinc.pro.security.userdetails.ProUser;
import com.inthinc.pro.service.VehicleServiceExt;
import com.inthinc.pro.service.adapters.VehicleDAOAdapter;
import com.inthinc.pro.service.it.BaseTest;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.easymock.EasyMock.anyObject;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext-dao.xml", "classpath:spring/applicationContext-beans.xml", "classpath:spring/applicationContext-security.xml"})
public class VehicleServiceExtTest extends BaseTest {
    static final String NAME_MODIFIER = "2";
    static final String TEST_USERNAME = "inthincTechSupportQA";
    static final String TEST_PASSWORD = "welcome456";

    @Autowired
    public UserDAO userDAO;
    @Autowired
    public VehicleDAO vehicleDAO;
    @Mocked
    public VehicleDAO mockVehicleDAO;
    @Autowired
    public GroupDAO groupDAO;
    @Autowired
    public VehicleServiceExt vehicleServiceExt;
    @Autowired
    private EventDAO eventDAO;
    @Autowired
    private VehicleReportDAO vehicleReportDAO;
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
    private User testUser;

    @Before
    public void createTestData() {
        GrantedAuthority[] grantedAuthorities = new GrantedAuthorityImpl[1];
        grantedAuthorities[0] = new GrantedAuthorityImpl("ROLE_ADMIN");
        testUser = userDAO.findByUserName(TEST_USERNAME);
        ProUser proUser = new ProUser(testUser, grantedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(proUser, TEST_PASSWORD));

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

        vehicleDAOAdapter = new VehicleDAOAdapter();
        vehicleDAOAdapter.setVehicleDAO(vehicleDAO);
        vehicleDAOAdapter.setEventDAO(eventDAO);
        vehicleDAOAdapter.setVehicleReportDAO(vehicleReportDAO);

        mockVehicleDAOAdapter = new VehicleDAOAdapter();
        mockVehicleDAOAdapter.setVehicleDAO(mockVehicleDAO);
        mockVehicleDAOAdapter.setEventDAO(eventDAO);
        mockVehicleDAOAdapter.setVehicleReportDAO(vehicleReportDAO);

        testVehicles = new HashMap<Integer, Vehicle>();
        vehicle1 = new Vehicle();
        vehicle2 = new Vehicle();
        vehicle3 = new Vehicle();

        group = new Group();
        group.setName("test group");
        group.setAccountID(1);
        group.setPath("text/group");
        group.setGroupID(groupDAO.create(1, group));

        vehicle1.setStatus(Status.ACTIVE);
        vehicle1.setName(NAME_MODIFIER + "name_1");
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
        vehicle1.setVehicleID(vehicleDAO.create(1, vehicle1));

        vehicle2.setStatus(Status.ACTIVE);
        vehicle2.setName(NAME_MODIFIER + "name_2");
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
        vehicle2.setVehicleID(vehicleDAO.create(1, vehicle2));

        vehicle3.setStatus(Status.ACTIVE);
        vehicle3.setName(NAME_MODIFIER + "name_3");
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
        vehicle3.setVehicleID(vehicleDAO.create(1, vehicle3));

        // add vehicles to list
        testVehicles.put(1, vehicle1);
        testVehicles.put(2, vehicle2);
        testVehicles.put(3, vehicle3);
    }

    @After
    public void deleteTestData() {
        for (Map.Entry<Integer, Vehicle> vehicleEntry : testVehicles.entrySet()) {
            if (vehicleEntry.getValue() != null && vehicleEntry.getValue().getVehicleID() != null)
                vehicleDAO.deleteByID(vehicleEntry.getValue().getVehicleID());
        }
        if (group != null && group.getGroupID() != null)
            groupDAO.deleteByID(group.getGroupID());
    }

    @Test
    public void getVehicleByNameTest() {
        for (int i = 1; i <= 3; i++) {
            Response response = vehicleServiceExt.get(NAME_MODIFIER + "name_" + i);
            assertNotNull(response.getEntity());
            Vehicle vehicle = (Vehicle) response.getEntity();
            assertNotNull(vehicle);
            assertEquals(vehicle.getName(), testVehicles.get(i).getName());
        }
    }

    @Test
    public void getVehicleByVinTest() {
        for (int i = 1; i <= 3; i++) {
            Response response = vehicleServiceExt.findByVIN(NAME_MODIFIER + "9000000090" + i);
            assertNotNull(response.getEntity());
            Vehicle vehicle = (Vehicle) response.getEntity();
            assertNotNull(vehicle);
            assertEquals(vehicle.getName(), testVehicles.get(i).getName());
        }
    }
//
//    @Test
//    public void getAllVehiclesTest() {
//        Response response = vehicleServiceExt.getAll();
//        assertNotNull(response.getEntity());
//        List<Vehicle> vehicleList = (List<Vehicle>) response.getEntity();
//        int found = 0;
//
//        for (Vehicle vehicle: vehicleList){
//           for (Map.Entry<Integer, Vehicle> vehicleEntry: testVehicles.entrySet()){
//               if (vehicleEntry.getValue().getVehicleID().equals(vehicle.getVehicleID()))
//                   found ++;
//           }
//        }
//        assertEquals(3, found);
//    }
//
//
//    @Test
//    public void getLocationTest() {
//        VehicleServiceExtImpl vehicleServiceExtImpl = (VehicleServiceExtImpl) vehicleServiceExt;
//        try {
//            vehicleServiceExtImpl.setDao(mockVehicleDAOAdapter);
//
//            new NonStrictExpectations() {{
//
//                mockVehicleDAO.findByName(vehicle1.getName()); result = vehicle1;
//                mockVehicleDAO.getLastLocation(vehicle1.getVehicleID()); result = mockLastLocation;
//                mockVehicleDAO.findByName(vehicle2.getName()); result = vehicle1;
//                mockVehicleDAO.getLastLocation(vehicle2.getVehicleID()); result = mockLastLocation;
//                mockVehicleDAO.findByName(vehicle3.getName()); result = vehicle1;
//                mockVehicleDAO.getLastLocation(vehicle3.getVehicleID()); result = mockLastLocation;
//            }};
//
//            for (int i = 1; i <= 3; i++) {
//                Response response = vehicleServiceExt.getLastLocation("dname_" + i);
//                assertNotNull(response.getEntity());
//                LastLocation lastLocation = (LastLocation) response.getEntity();
//                assertNotNull(lastLocation);
//                assertEquals(lastLocation.getVehicleStatus(), "aok");
//            }
//        }finally {
//            vehicleServiceExtImpl.setDao(vehicleDAOAdapter);
//        }
//    }
//
//    @Test
//    public void getTripTest(){
//        VehicleServiceExtImpl vehicleServiceExtImpl = (VehicleServiceExtImpl) vehicleServiceExt;
//        try {
//            vehicleServiceExtImpl.setDao(mockVehicleDAOAdapter);
//
//            new NonStrictExpectations() {{
//
//                mockVehicleDAO.findByName(vehicle1.getName()); result = vehicle1;
//                mockVehicleDAO.getLastTrip(vehicle1.getDriverID()); result = mockLastTrip;
//                mockVehicleDAO.findByName(vehicle2.getName()); result = vehicle2;
//                mockVehicleDAO.getLastTrip(vehicle2.getDriverID()); result = mockLastTrip;
//                mockVehicleDAO.findByName(vehicle3.getName()); result = vehicle3;
//                mockVehicleDAO.getLastTrip(vehicle3.getDriverID()); result = mockLastTrip;
//            }};
//
//            for (int i = 1; i <= 3; i++) {
//                Response response = vehicleServiceExt.getLastTrip("dname_" + i);
//                assertNotNull(response.getEntity());
//                Trip lastTrip = (Trip) response.getEntity();
//                assertNotNull(lastTrip);
//                assertEquals(lastTrip.getEndAddressStr(), "aok");
//            }
//        }finally {
//            vehicleServiceExtImpl.setDao(vehicleDAOAdapter);
//        }
//    }
//
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

    public VehicleDAO getVehicleDAO() {
        return vehicleDAO;
    }

    public void setVehicleDAO(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    public GroupDAO getGroupDAO() {
        return groupDAO;
    }

    public void setGroupDAO(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    public VehicleServiceExt getVehicleServiceExt() {
        return vehicleServiceExt;
    }

    public void setVehicleServiceExt(VehicleServiceExt vehicleServiceExt) {
        this.vehicleServiceExt = vehicleServiceExt;
    }

    public EventDAO getEventDAO() {
        return eventDAO;
    }

    public void setEventDAO(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    public VehicleReportDAO getVehicleReportDAO() {
        return vehicleReportDAO;
    }

    public void setVehicleReportDAO(VehicleReportDAO vehicleReportDAO) {
        this.vehicleReportDAO = vehicleReportDAO;
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
