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
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import java.util.*;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext-dao.xml", "classpath:spring/applicationContext-beans.xml", "classpath:spring/applicationContext-security.xml"})
public class VehicleServiceExtIntegrationTest extends BaseTest {
    static final String NAME_MODIFIER = "4";
    static final String TEST_USERNAME = "inthincTechSupportQA";
    static final String TEST_PASSWORD = "welcome456";

    @Autowired
    public UserDAO userDAO;
    @Autowired
    public VehicleDAO vehicleDAO;
    @Autowired
    public GroupDAO groupDAO;
    @Autowired
    public VehicleServiceExt vehicleServiceExt;
    @Autowired
    private EventDAO eventDAO;
    @Autowired
    private VehicleReportDAO vehicleReportDAO;
    private VehicleDAOAdapter vehicleDAOAdapter;
    // test data
    private Map<Integer, Vehicle> testVehicles;
    private Vehicle vehicle1;
    private Vehicle vehicle2;
    private Vehicle vehicle3;
    private Group group;

    private User testUser;

    @Before
    public void createTestData() {
        GrantedAuthority[] grantedAuthorities = new GrantedAuthorityImpl[1];
        grantedAuthorities[0] = new GrantedAuthorityImpl("ROLE_ADMIN");
        testUser = userDAO.findByUserName(TEST_USERNAME);
        ProUser proUser = new ProUser(testUser, grantedAuthorities);
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(proUser, TEST_PASSWORD));

        vehicleDAOAdapter = new VehicleDAOAdapter();
        vehicleDAOAdapter.setVehicleDAO(vehicleDAO);
        vehicleDAOAdapter.setEventDAO(eventDAO);
        vehicleDAOAdapter.setVehicleReportDAO(vehicleReportDAO);

        testVehicles = new HashMap<Integer, Vehicle>();
        vehicle1 = new Vehicle();
        vehicle2 = new Vehicle();
        vehicle3 = new Vehicle();

        group = groupDAO.findByID(testUser.getGroupID());

        vehicle1.setStatus(Status.ACTIVE);
        vehicle1.setName("name_1" + NAME_MODIFIER);
        vehicle1.setVtype(VehicleType.HEAVY);
        vehicle1.setColor("red");
        vehicle1.setIfta(true);
        vehicle1.setLicense("Test Rest");
        vehicle1.setVIN(NAME_MODIFIER + "91000000901");
        vehicle1.setModel("tes1");
        vehicle1.setMake("tes1");
        vehicle1.setModified(new Date());
        vehicle1.setWeight(1000);
        vehicle1.setYear(1991);
        vehicle1.setGroupID(group.getGroupID());
        vehicle1.setVehicleID(vehicleDAO.create(1, vehicle1));

        vehicle2.setStatus(Status.ACTIVE);
        vehicle2.setName("name_2" + NAME_MODIFIER);
        vehicle2.setVtype(VehicleType.LIGHT);
        vehicle2.setColor("yellow");
        vehicle2.setIfta(true);
        vehicle2.setLicense("Test Rest2");
        vehicle2.setVIN(NAME_MODIFIER + "91000000902");
        vehicle2.setModel("tes2");
        vehicle2.setMake("tes2");
        vehicle2.setModified(new Date());
        vehicle2.setWeight(1001);
        vehicle2.setYear(1992);
        vehicle2.setGroupID(group.getGroupID());
        vehicle2.setVehicleID(vehicleDAO.create(1, vehicle2));

        vehicle3.setStatus(Status.ACTIVE);
        vehicle3.setName("name_3" + NAME_MODIFIER);
        vehicle3.setVtype(VehicleType.MEDIUM);
        vehicle3.setColor("blue");
        vehicle3.setIfta(true);
        vehicle3.setLicense("Test Rest3");
        vehicle3.setVIN(NAME_MODIFIER + "91000000903");
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
    }

    @Test
    public void getVehicleByNameTest() {
        for (int i = 1; i <= 3; i++) {
            Response response = vehicleServiceExt.get("name_" + i + "" + NAME_MODIFIER);
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

    @Test
    public void getAllVehiclesTest() {
        Response response = vehicleServiceExt.getAll();
        assertNotNull(response.getEntity());
        GenericEntity genericEntity = (GenericEntity) response.getEntity();
        List<Vehicle> vehicleList = (ArrayList<Vehicle>) genericEntity.getEntity();
        int found = 0;

        for (Vehicle vehicle : vehicleList) {
            for (Map.Entry<Integer, Vehicle> vehicleEntry : testVehicles.entrySet()) {
                if (vehicleEntry.getValue().getVIN().equals(vehicle.getVIN()))
                    found++;
            }
        }
        assertEquals(3, found);
    }


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

    public VehicleDAOAdapter getVehicleDAOAdapter() {
        return vehicleDAOAdapter;
    }

    public void setVehicleDAOAdapter(VehicleDAOAdapter vehicleDAOAdapter) {
        this.vehicleDAOAdapter = vehicleDAOAdapter;
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
}
