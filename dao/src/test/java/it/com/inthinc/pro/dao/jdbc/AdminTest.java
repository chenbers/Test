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
    @Ignore
    public void compareVehicleTest() {
        Integer groupID = 1;
        GroupHessianDAO groupHessianDAO = new GroupHessianDAO();
        groupHessianDAO.setSiloService(siloService);
        GroupHierarchy groupHierarchy = new GroupHierarchy(groupHessianDAO.getGroupsByAcctID(groupID));
        List<Integer> groupIDList = groupHierarchy.getSubGroupIDList(groupID);
        
        VehicleHessianDAO vehicleHessianDAO = new VehicleHessianDAO();
        vehicleHessianDAO.setSiloService(siloService);
        List<Vehicle> hessianVehicleList = vehicleHessianDAO.getVehiclesInGroupHierarchy(groupID);
        
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
        String vehicleIgnoreFields[] = {
        // Vehicle ok to ignore, unused fields
        //        "addressID", "dept", "height", "weight", "modified", "driver", "user", "driverID", "userID", 
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
    
    //TODO: refactor AdminHazardJDBCDAO tests into own class
    static Map<String, LatLng> locations = new HashMap<String, LatLng>();
    static{
        locations.put("inthinc", new LatLng(40.7106, -111.9945));
        locations.put("bangerter_21st", new LatLng(40.7257, -111.9863));
        locations.put("spagetti", new LatLng(40.721, -111.9046));
        locations.put("summitPark", new LatLng(40.7525, -111.613));
    }
    @Test
    public void createHazard_validAllHazardLocations_eachShouldInsert(){
        boolean returnsHazardID = false;
        for(String locationName: locations.keySet()){
            AdminHazardJDBCDAO hazardJDBCDAO = new AdminHazardJDBCDAO();
            hazardJDBCDAO.setDataSource(new ITDataSource().getRealDataSource());
            Hazard hazard = new Hazard();
            hazard.setAcctID(1);
            hazard.setCreated(new Date());
            hazard.setDescription("IT created: "+locationName);
            hazard.setDeviceID(5780);//jwimmerTiwipro
            hazard.setDriverID(2262);//jwimmer
            hazard.setEndTime(new DateTime().plus(Period.hours(50)).toDate());
            hazard.setLatitude(locations.get(locationName).getLat());
            hazard.setLongitude(locations.get(locationName).getLng());
            hazard.setLocation("IT created for "+locationName+" at "+new DateTime().toString("yyyyMMddkkmmss"));
            System.out.println("radius: "+HazardType.ROADRESTRICTIONS_BAN_CLOSURE.getRadius());
            hazard.setRadiusMeters(HazardType.ROADRESTRICTIONS_BAN_CLOSURE.getRadius());
            hazard.setStartTime(new Date());
            hazard.setStateID(45);//UTah
            hazard.setStatus(HazardStatus.ACTIVE);
            hazard.setType(HazardType.ROADRESTRICTIONS_BAN_CLOSURE);
            hazard.setUserID(1655);//jwimmer user
            hazard.setVehicleID(6956);//ATAT aka jwimmer truck
            
            Integer hazardID = hazardJDBCDAO.create(hazard);
            returnsHazardID = (hazardID != null);
            assertTrue(returnsHazardID);
            System.out.println("inserted "+locationName);
        }
    }

    public void findHazardsByUserAcct_validBoundingBoxAcrossNeg180_validResults(){
        //create a hazard to ensure test will have results
        AdminHazardJDBCDAO hazardJDBCDAO = new AdminHazardJDBCDAO();
        boolean returnsHazardID = false;
        hazardJDBCDAO.setDataSource(new ITDataSource().getRealDataSource());
        Hazard hazardToInsert = new Hazard();
        hazardToInsert.setAcctID(2);
        hazardToInsert.setCreated(new Date());
        hazardToInsert.setDescription("IT created: mid-ocean");
        hazardToInsert.setDeviceID(5780);//jwimmerTiwipro
        hazardToInsert.setDriverID(2262);//jwimmer
        hazardToInsert.setEndTime(new DateTime().plus(Period.hours(50)).toDate());
        hazardToInsert.setLatitude(0.0);
        hazardToInsert.setLongitude(0.0);
        hazardToInsert.setLocation("IT created for 0,0 at "+new DateTime());
        System.out.println("radius: "+HazardType.ROADRESTRICTIONS_BAN_CLOSURE.getRadius());
        hazardToInsert.setRadiusMeters(HazardType.ROADRESTRICTIONS_BAN_CLOSURE.getRadius());
        hazardToInsert.setStartTime(new Date());
        hazardToInsert.setStateID(45);//UTah
        hazardToInsert.setStatus(HazardStatus.ACTIVE);
        hazardToInsert.setType(HazardType.ROADRESTRICTIONS_BAN_CLOSURE);
        hazardToInsert.setUserID(1655);//jwimmer user
        hazardToInsert.setVehicleID(6956);//ATAT aka jwimmer truck
        
        Integer hazardID = hazardJDBCDAO.create(hazardToInsert);
        returnsHazardID = (hazardID != null);
        assertTrue(returnsHazardID);
        
        
        hazardJDBCDAO.setDataSource(new ITDataSource().getRealDataSource());
        User fakeUser = new User(){
            @Override
            public String toString(){
                return "User [fakeUser]";
            }
            @Override
            public Person getPerson(){
                return new Person(){
                  @Override 
                  public Integer getAcctID(){
                      return 2;
                  }
                };
            }
        };
        BoundingBox latBiggerThanLng_world = new BoundingBox(180, -90, 179, 90);
        List<Hazard> theHazards = hazardJDBCDAO.findHazardsByUserAcct(fakeUser, latBiggerThanLng_world);
        for(Hazard hazard: theHazards){
            System.out.println("hazard: "+hazard);
        }
      //initial test just that there is SOME data returned
        assertTrue(theHazards.size() > 0);
    }
    @Test
    public void findHazardsByUserAcct_validBoundingBoxes_validResults(){
        //create a hazard to ensure test will have results
        AdminHazardJDBCDAO hazardJDBCDAO = new AdminHazardJDBCDAO();
        boolean returnsHazardID = false;
        hazardJDBCDAO.setDataSource(new ITDataSource().getRealDataSource());
        Hazard hazardToInsert = new Hazard();
        hazardToInsert.setAcctID(1);
        hazardToInsert.setCreated(new Date());
        hazardToInsert.setDescription("IT created: mid-ocean");
        hazardToInsert.setDeviceID(5780);//jwimmerTiwipro
        hazardToInsert.setDriverID(2262);//jwimmer
        hazardToInsert.setEndTime(new DateTime().plus(Period.hours(50)).toDate());
        hazardToInsert.setLatitude(0.0);
        hazardToInsert.setLongitude(0.0);
        hazardToInsert.setLocation("IT created for 0,0 at "+new DateTime());
        System.out.println("radius: "+HazardType.ROADRESTRICTIONS_BAN_CLOSURE.getRadius());
        hazardToInsert.setRadiusMeters(HazardType.ROADRESTRICTIONS_BAN_CLOSURE.getRadius());
        hazardToInsert.setStartTime(new Date());
        hazardToInsert.setStateID(45);//UTah
        hazardToInsert.setStatus(HazardStatus.ACTIVE);
        hazardToInsert.setType(HazardType.ROADRESTRICTIONS_BAN_CLOSURE);
        hazardToInsert.setUserID(1655);//jwimmer user
        hazardToInsert.setVehicleID(6956);//ATAT aka jwimmer truck
        
        Integer hazardID = hazardJDBCDAO.create(hazardToInsert);
        returnsHazardID = (hazardID != null);
        assertTrue(returnsHazardID);
        
        hazardJDBCDAO.setDataSource(new ITDataSource().getRealDataSource());
        User fakeUser = new User(){
            @Override
            public String toString(){
                return "User [fakeUser]";
            }
            @Override
            public Person getPerson(){
                return new Person(){
                  @Override 
                  public Integer getAcctID(){
                      return 1;
                  }
                };
            }
        };
        BoundingBox world = new BoundingBox(-90, -180, 90, 180);
        List<Hazard> theHazards = hazardJDBCDAO.findHazardsByUserAcct(fakeUser, world);
        for(Hazard hazard: theHazards){
            System.out.println("hazard: "+hazard);
        }
        //initial test just that there is SOME data returned
        assertTrue(theHazards.size() > 0);
    }
    @Test
    public void findHazardsByUserAcct_invalidBoundingBoxes_noResults(){
        AdminHazardJDBCDAO hazardJDBCDAO = new AdminHazardJDBCDAO();
        hazardJDBCDAO.setDataSource(new ITDataSource().getRealDataSource());
        User fakeUser = new User(){
            @Override
            public String toString(){
                return "User [fakeUser]";
            }
            @Override
            public Person getPerson(){
                return new Person(){
                  @Override 
                  public Integer getAccountID(){
                      return 1;
                  }
                };
            }
        };
        BoundingBox invalidBecause_lat1BiggerThanLat2 = new BoundingBox(-90, -180, 90, -180);
        List<Hazard> theHazards = hazardJDBCDAO.findHazardsByUserAcct(fakeUser, invalidBecause_lat1BiggerThanLat2);
        for(Hazard hazard: theHazards){
            System.out.println("hazard: "+hazard);
        }
        assertTrue(theHazards.size() == 0);
    }
    @Test
    public void findVehiclesByAccountWithinDistance_validAccountAndDist_anyVehiclesInRange(){
        
        
        AdminVehicleJDBCDAO vehicleJDBCDAO = new AdminVehicleJDBCDAO();
        vehicleJDBCDAO.setDataSource(new ITDataSource().getRealDataSource());
        List<Vehicle> vehicles = vehicleJDBCDAO.findVehiclesByAccountWithinDistance(new Integer(1), new Long(200), locations.get("inthinc"));
//        for(Vehicle vehicle: vehicles){
//            System.out.println("vehicle: "+vehicle);
//        }
        //assertTrue(vehicles.size() > 0);
        if(vehicles.size() == 0)
            System.out.println("WARNING:");
        System.out.println("findVehiclesByAccountWithinDistance found "+vehicles.size()+" vehicles.");
    }
}
