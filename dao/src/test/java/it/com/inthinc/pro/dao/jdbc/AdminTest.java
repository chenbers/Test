package it.com.inthinc.pro.dao.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import it.com.inthinc.pro.dao.Util;
import it.com.inthinc.pro.dao.model.ITData;
import it.config.ITDataSource;
import it.config.IntegrationConfig;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.dao.hessian.DeviceHessianDAO;
import com.inthinc.pro.dao.hessian.GroupHessianDAO;
import com.inthinc.pro.dao.hessian.PersonHessianDAO;
import com.inthinc.pro.dao.hessian.RoleHessianDAO;
import com.inthinc.pro.dao.hessian.StateHessianDAO;
import com.inthinc.pro.dao.hessian.TimeZoneHessianDAO;
import com.inthinc.pro.dao.hessian.VehicleHessianDAO;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.jdbc.AdminDeviceJDBCDAO;
import com.inthinc.pro.dao.jdbc.AdminHazardJDBCDAO;
import com.inthinc.pro.dao.jdbc.AdminPersonJDBCDAO;
import com.inthinc.pro.dao.jdbc.AdminVehicleJDBCDAO;
import com.inthinc.pro.model.BoundingBox;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.Hazard;
import com.inthinc.pro.model.HazardStatus;
import com.inthinc.pro.model.HazardType;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.PersonIdentifiers;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.app.States;
import com.inthinc.pro.model.app.SupportedTimeZones;
import com.inthinc.pro.model.pagination.PageParams;
import com.inthinc.pro.model.pagination.TableFilterField;

public class AdminTest extends BaseJDBCTest {
    private static SiloService siloService;
    private static final String XML_DATA_FILE = "ReportTest.xml";
    private static ITData itData;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        IntegrationConfig config = new IntegrationConfig();
        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());
        siloService = new SiloServiceCreator(host, port).getService();
        initApp();
        itData = new ITData();
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(XML_DATA_FILE);
        if (!itData.parseTestData(stream, siloService, false, false)) {
            throw new Exception("Error parsing Test data xml file");
        }
    }

    private static void initApp() throws Exception {
        StateHessianDAO stateDAO = new StateHessianDAO();
        stateDAO.setSiloService(siloService);
        States states = new States();
        states.setStateDAO(stateDAO);
        states.init();
        RoleHessianDAO roleDAO = new RoleHessianDAO();
        roleDAO.setSiloService(siloService);
        DeviceHessianDAO deviceDAO = new DeviceHessianDAO();
        deviceDAO.setSiloService(siloService);
        TimeZoneHessianDAO timeZoneDAO = new TimeZoneHessianDAO();
        timeZoneDAO.setSiloService(siloService);
        SupportedTimeZones supportedTimeZones = new SupportedTimeZones();
        supportedTimeZones.setTimeZoneDAO(timeZoneDAO);
        supportedTimeZones.init();
    }

    @Test
    public void comparePersonTest() {
        Integer groupID = itData.fleetGroup.getGroupID();
        Integer acctID = itData.account.getAccountID();
        
        GroupHessianDAO groupHessianDAO = new GroupHessianDAO();
        groupHessianDAO.setSiloService(siloService);
        GroupHierarchy groupHierarchy = new GroupHierarchy(groupHessianDAO.getGroupsByAcctID(acctID));
        List<Integer> groupIDList = groupHierarchy.getSubGroupIDList(groupID);

        PersonHessianDAO personHessianDAO = new PersonHessianDAO();
        personHessianDAO.setSiloService(siloService);
        List<Person> hessianPersonList = personHessianDAO.getPeopleInGroupHierarchy(groupID, groupID);
        
        AdminPersonJDBCDAO adminPersonJDBCDAO = new AdminPersonJDBCDAO();
        adminPersonJDBCDAO.setDataSource(new ITDataSource().getRealDataSource());
        List<TableFilterField> filters = new ArrayList<TableFilterField>();
        Integer cnt = adminPersonJDBCDAO.getCount(acctID, groupIDList, filters);
        assertEquals("hessian vs jdbc person cnt", hessianPersonList.size(), cnt.intValue());
        
        PageParams pageParams = new PageParams();
        pageParams.setFilterList(filters);
        pageParams.setStartRow(0);
        pageParams.setEndRow(cnt-1);
        List<Person> jdbcPersonList = adminPersonJDBCDAO.getPeople(acctID, groupIDList, pageParams);
        assertEquals("hessian vs jdbc person cnt", hessianPersonList.size(), jdbcPersonList.size());

        String personIgnoreFields[] = {
        // person ok to ignore, unused fields
                "addressID", "dept", "height", "weight", "modified", "driver", "user", "driverID", "userID"};
        String userIgnoreFields[] = { "person", "modified", "selectedMapLayerIDs", "mapType",
        // TODO: talk to Matt about these.
                "lastLogin", "passwordDT"};
        String driverIgnoreFields[] = { "person", "modified", };
        for (Person hessianPerson : hessianPersonList) {
            boolean found = false;
            for (Person jdbcPerson : jdbcPersonList) {
                if (jdbcPerson.getPersonID().equals(hessianPerson.getPersonID())) {
                    found = true;
                    Util.compareObjects(hessianPerson, jdbcPerson, personIgnoreFields);
                    if (hessianPerson.getUser() != null) {
                        assertTrue("user should not be null " + hessianPerson.getPersonID(), jdbcPerson.getUser() != null);
                        Util.compareObjects(hessianPerson.getUser(), jdbcPerson.getUser(), userIgnoreFields);
                    } else {
                        assertTrue("user should be null " + hessianPerson.getPersonID(), jdbcPerson.getUser() == null);
                    }
                    if (hessianPerson.getDriver() != null) {
                        assertTrue("driver should not be null " + hessianPerson.getFullName(), jdbcPerson.getDriver() != null);
                        Util.compareObjects(hessianPerson.getDriver(), jdbcPerson.getDriver(), driverIgnoreFields);
                    } else {
                        assertTrue("driver should be null " + hessianPerson.getPersonID(), jdbcPerson.getDriver() == null);
                    }
                    break;
                }
            }
            assertTrue("Unable to find match for person " + hessianPerson.getPersonID(), found);
        }
    }

    @Test
    @Ignore
    public void comparePersonDevonTest() {
        // need to point to devon
        Integer groupID = 528;
        Integer acctID = 70;
        
        
        GroupHessianDAO groupHessianDAO = new GroupHessianDAO();
        groupHessianDAO.setSiloService(siloService);
        GroupHierarchy groupHierarchy = new GroupHierarchy(groupHessianDAO.getGroupsByAcctID(acctID));
        List<Integer> groupIDList = groupHierarchy.getSubGroupIDList(groupID);

        PersonHessianDAO personHessianDAO = new PersonHessianDAO();
        personHessianDAO.setSiloService(siloService);
        List<Person> hessianPersonList = personHessianDAO.getPeopleInGroupHierarchy(groupID, groupID);
        
        AdminPersonJDBCDAO adminPersonJDBCDAO = new AdminPersonJDBCDAO();
        adminPersonJDBCDAO.setDataSource(new ITDataSource().getRealDataSource());
        List<TableFilterField> filters = new ArrayList<TableFilterField>();
        int cnt = adminPersonJDBCDAO.getCount(acctID, groupIDList, filters);
        List<PersonIdentifiers> jdbcList = adminPersonJDBCDAO.getFilteredPersonIDs(acctID, groupIDList, filters);
        assertEquals(cnt, jdbcList.size());
        
        PageParams pageParams = new PageParams();
        pageParams.setFilterList(filters);
        pageParams.setStartRow(0);
        pageParams.setEndRow(cnt-1);
        List<Person> jdbcPersonList = adminPersonJDBCDAO.getPeople(acctID, groupIDList, pageParams);

        for (Person jdbcPerson : jdbcPersonList) {
        	boolean found = false;
        	for (Person person : hessianPersonList) {
        		if (person.getPersonID().equals(jdbcPerson.getPersonID())) {
        			found = true;
        			break;
        		}
        	}
        	if (!found) {
        		System.out.println(jdbcPerson.toString());
        	}
        }

        String personIgnoreFields[] = {
        // person ok to ignore, unused fields
                "addressID", "dept", "height", "weight", "modified", "driver", "user", "driverID", "userID", "gender"};
        String userIgnoreFields[] = { "person", "modified", "selectedMapLayerIDs",
        // TODO: talk to Matt about these.
                "lastLogin", "passwordDT", "mapType"};
        String driverIgnoreFields[] = { "person", "modified", "dot"};
        for (Person hessianPerson : hessianPersonList) {
            boolean found = false;
            for (Person jdbcPerson : jdbcPersonList) {
                if (jdbcPerson.getPersonID().equals(hessianPerson.getPersonID())) {
                    found = true;
                    Util.compareObjects(hessianPerson, jdbcPerson, personIgnoreFields);
                    if (hessianPerson.getUser() != null) {
                        assertTrue("user should not be null " + hessianPerson.getPersonID(), jdbcPerson.getUser() != null);
                        Util.compareObjects(hessianPerson.getUser(), jdbcPerson.getUser(), userIgnoreFields);
                    } else {
                        assertTrue("user should be null " + hessianPerson.getPersonID(), jdbcPerson.getUser() == null);
                    }
                    if (hessianPerson.getDriver() != null) {
                        assertTrue("driver should not be null " + hessianPerson.getFullName(), jdbcPerson.getDriver() != null);
                        Util.compareObjects(hessianPerson.getDriver(), jdbcPerson.getDriver(), driverIgnoreFields);
                    } else {
                        assertTrue("driver should be null " + hessianPerson.getPersonID(), jdbcPerson.getDriver() == null);
                    }
                    break;
                }
            }
//            assertTrue("Unable to find match for person " + hessianPerson.getPersonID(), found);
            if (!found)
            	System.out.println("Unable to find match for hessian person " + hessianPerson.toString());
        }
    }




    @Test
    public void compareVehicleTest() {
        
        Integer groupID = itData.fleetGroup.getGroupID();
        Integer acctID = itData.account.getAccountID();
        // need to point to devon
//        Integer groupID = 528;
//        Integer acctID = 70;
//groupID = 1;
//acctID = 1;

        GroupHessianDAO groupHessianDAO = new GroupHessianDAO();
        groupHessianDAO.setSiloService(siloService);
        GroupHierarchy groupHierarchy = new GroupHierarchy(groupHessianDAO.getGroupsByAcctID(acctID));
        List<Integer> groupIDList = groupHierarchy.getSubGroupIDList(groupID);
        
        VehicleHessianDAO vehicleHessianDAO = new VehicleHessianDAO();
        vehicleHessianDAO.setSiloService(siloService);
        DeviceHessianDAO deviceHessianDAO = new DeviceHessianDAO();
        deviceHessianDAO.setSiloService(siloService);
        List<Vehicle> hessianVehicleList = vehicleHessianDAO.getVehiclesInGroupHierarchy(groupID);
        for (Vehicle hessianVehicle : hessianVehicleList) {
            if (hessianVehicle.getDeviceID() != null)
                hessianVehicle.setDevice(deviceHessianDAO.findByID(hessianVehicle.getDeviceID()));
        }
        
        
        AdminVehicleJDBCDAO adminVehicleJDBCDAO = new AdminVehicleJDBCDAO();
        adminVehicleJDBCDAO.setDataSource(new ITDataSource().getRealDataSource());
        List<TableFilterField> filters = new ArrayList<TableFilterField>();
        Integer cnt = adminVehicleJDBCDAO.getCount(groupIDList, filters);
        assertEquals("hessian vs jdbc Vehicle cnt", hessianVehicleList.size(), cnt.intValue());

        PageParams pageParams = new PageParams();
        pageParams.setFilterList(filters);
        pageParams.setStartRow(0);
        pageParams.setEndRow(cnt-1);
        List<Vehicle> jdbcVehicleList = adminVehicleJDBCDAO.getVehicles(groupIDList, pageParams);
        assertEquals("hessian vs jdbc Vehicle cnt", hessianVehicleList.size(), jdbcVehicleList.size());
        String vehicleIgnoreFields[] = { "modified", "activated" 
        };
        for (Vehicle hessianVehicle : hessianVehicleList) {
            boolean found = false;
            for (Vehicle jdbcVehicle : jdbcVehicleList) {
                if (jdbcVehicle.getVehicleID().equals(hessianVehicle.getVehicleID())) {
                    found = true;
                    Util.compareObjects(hessianVehicle, jdbcVehicle, vehicleIgnoreFields);
                    break;
                }
            }
            assertTrue("Unable to find match for Vehicle " + hessianVehicle.getVehicleID(), found);
        }
    }

    @Test
    @Ignore
    public void compareDeviceTest() {
        Integer accountID = 1;
        DeviceHessianDAO deviceHessianDAO = new DeviceHessianDAO();
        deviceHessianDAO.setSiloService(siloService);
        List<Device> hessianDeviceList = deviceHessianDAO.getDevicesByAcctID(accountID);

        AdminDeviceJDBCDAO adminDeviceJDBCDAO = new AdminDeviceJDBCDAO();
        adminDeviceJDBCDAO.setDataSource(new ITDataSource().getRealDataSource());
        List<TableFilterField> filters = new ArrayList<TableFilterField>();
        Integer cnt = adminDeviceJDBCDAO.getCount(accountID, filters);
        assertEquals("hessian vs jdbc device cnt", hessianDeviceList.size(), cnt.intValue());

        PageParams pageParams = new PageParams();
        pageParams.setFilterList(filters);
        pageParams.setStartRow(0);
        pageParams.setEndRow(cnt-1);
        List<Device> jdbcDeviceList = adminDeviceJDBCDAO.getDevices(accountID, pageParams);
        assertEquals("hessian vs jdbc device cnt", hessianDeviceList.size(), jdbcDeviceList.size());
        String deviceIgnoreFields[] = {
//                "addressID", "dept", "height", "weight", "modified", "driver", "user", "driverID", "userID", 
        };
        for (Device hessianDevice: hessianDeviceList) {
            boolean found = false;
            for (Device jdbcDevice: jdbcDeviceList) {
                if (jdbcDevice.getDeviceID().equals(hessianDevice.getDeviceID())) {
                    found = true;
                    Util.compareObjects(hessianDevice, jdbcDevice, deviceIgnoreFields);
                    break;
                }
            }
            assertTrue("Unable to find match for device " + hessianDevice.getDeviceID(), found);
        }
    }
}
