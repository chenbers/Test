package it.com.inthinc.pro.dao;

// The tests in this file  can fail sporadically when the scheduler is running on the same
// server that is is hitting (usually dev).  If this becomes a problem, we can mark them as Ignore.  
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import it.config.IntegrationConfig;
import it.util.EventGenerator;
import it.util.MCMSimulator;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.dao.hessian.AccountHessianDAO;
import com.inthinc.pro.dao.hessian.AlertMessageHessianDAO;
import com.inthinc.pro.dao.hessian.DeviceHessianDAO;
import com.inthinc.pro.dao.hessian.DriverHessianDAO;
import com.inthinc.pro.dao.hessian.EventHessianDAO;
import com.inthinc.pro.dao.hessian.GroupHessianDAO;
import com.inthinc.pro.dao.hessian.PersonHessianDAO;
import com.inthinc.pro.dao.hessian.RedFlagAlertHessianDAO;
import com.inthinc.pro.dao.hessian.RedFlagHessianDAO;
import com.inthinc.pro.dao.hessian.RoleHessianDAO;
import com.inthinc.pro.dao.hessian.StateHessianDAO;
import com.inthinc.pro.dao.hessian.VehicleHessianDAO;
import com.inthinc.pro.dao.hessian.ZoneAlertHessianDAO;
import com.inthinc.pro.dao.hessian.ZoneHessianDAO;
import com.inthinc.pro.dao.hessian.exceptions.ProxyException;
import com.inthinc.pro.dao.hessian.exceptions.RemoteServerException;
import com.inthinc.pro.dao.hessian.extension.HessianTCPProxyFactory;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.mock.data.MockRoles;
import com.inthinc.pro.map.GeonamesAddressLookup;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.AlertMessageBuilder;
import com.inthinc.pro.model.AlertMessageDeliveryType;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.DeviceStatus;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventMapper;
import com.inthinc.pro.model.FuelEfficiencyType;
import com.inthinc.pro.model.Gender;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupType;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.RedFlag;
import com.inthinc.pro.model.RedFlagAlert;
import com.inthinc.pro.model.RedFlagLevel;
import com.inthinc.pro.model.SeatBeltEvent;
import com.inthinc.pro.model.State;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleType;
import com.inthinc.pro.model.Zone;
import com.inthinc.pro.model.ZoneAlert;
import com.inthinc.pro.model.ZoneArrivalEvent;
import com.inthinc.pro.model.app.DeviceSensitivityMapping;
import com.inthinc.pro.model.app.SiteAccessPoints;
import com.inthinc.pro.model.app.States;
import com.inthinc.pro.model.security.Role;
import com.inthinc.pro.model.security.Roles;

public class AlertMessagesTest {
    private static final Logger logger = Logger.getLogger(AlertMessagesTest.class);
    private static SiloService siloService;
    private static MCMSimulator mcmSim;
    private static final String XML_DATA_PATH = "./src/test/resources/ITBaseData.xml";
    private static final String XML_DATA_FILE = "ITBaseData.xml";
    private static Account account;
    private static Group fleetGroup;
    private static Group regionGroup;
    private static Group team1Group;
    private static Person person;
    private static Device device;
    private static Vehicle vehicle;
    private static Device noDriverDevice;
    private static Vehicle noDriverVehicle;
    private static Zone zone;
    private static ZoneAlert zoneAlert;
    private static RedFlagAlert redFlagAlert;
    private static String IMEI;
    private static String noDriverDeviceIMEI;
    private static Integer zoneID;
    private static String mapServerURL;
    private static final String PASSWORD = "nuN5q/jdjEpJKKA4A6jLTZufWZfIXtxqzjVjifqFjbGg6tfmQFGXbTtcXtEIg4Z7"; // password
    private static final int DRIVERS = 1;
    private static final int VEHICLES = 2;
    private static final int VEHICLE_TYPES = 3;
    private static final int GROUPS = 4;
    private static final int CONTACT_INFO = 5;
    private static final int ANY_TIME = 6;
    private static AlertMessageHessianDAO alertMessageDAO;
    private static EventHessianDAO eventDAO;
    private static DriverHessianDAO driverDAO;
    private static GroupHessianDAO groupDAO;
    private static PersonHessianDAO personDAO;
    private static VehicleHessianDAO vehicleDAO;
    private static ZoneHessianDAO zoneHessianDAO;
    private static GeonamesAddressLookup addressLookup;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        IntegrationConfig config = new IntegrationConfig();
        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());
        mapServerURL = config.get(IntegrationConfig.MAP_SERVER_URL).toString();
        siloService = new SiloServiceCreator(host, port).getService();
        HessianTCPProxyFactory factory = new HessianTCPProxyFactory();
        mcmSim = (MCMSimulator) factory.create(MCMSimulator.class, config.getProperty(IntegrationConfig.MCM_HOST), config.getIntegerProp(IntegrationConfig.MCM_PORT));
        
        initDAOs();
        // HessianDebug.debugIn = true;
        // HessianDebug.debugOut = true;
        // HessianDebug.debugRequest = true;
        initApp();
        if (!testDateExists())
        {
//            genTestData();
        	// to regenerate the test data uncomment the line above
        	// check in the file src/test/resources/ITBaseData.xml
        	fail("Test data does not exist.  Please regenerate it from a dev machine and check it into source control (ITBaseData.xml).");
        }
    }

    private static void initApp() {
        StateHessianDAO stateDAO = new StateHessianDAO();
        stateDAO.setSiloService(siloService);
        States states = new States();
        states.setStateDAO(stateDAO);
        states.init();
        
        RoleHessianDAO roleDAO = new RoleHessianDAO();
        roleDAO.setSiloService(siloService);
        Roles roles = new Roles(1);
        roles.setRoleDAO(roleDAO);
        roles.init(1);
        
        SiteAccessPoints siteAccessPoints = new SiteAccessPoints();
        siteAccessPoints.setRoleDAO(roleDAO);
        siteAccessPoints.init();
        
        DeviceHessianDAO deviceDAO = new DeviceHessianDAO();
        deviceDAO.setSiloService(siloService);
        DeviceSensitivityMapping mapping = new DeviceSensitivityMapping();
        mapping.setDeviceDAO(deviceDAO);
        mapping.init();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        modZoneAlertPref(GROUPS);
        modRedFlagAlertPref(GROUPS);
    }

    @Test
    public void zoneAlerts() {
        zoneID = zone.getZoneID();
        IMEI = device.getImei();
        boolean anyAlertsFound = false;
        // generate zone arrival/departure event
        if (!genZoneEvent(IMEI, zoneID))
            fail("Unable to generate zone arrival event");
        if (pollForMessages("Zone Alert Groups Set"))
        	anyAlertsFound = true;
        modZoneAlertPref(DRIVERS);
        if (!genZoneEvent(IMEI, zoneID))
            fail("Unable to generate zone arrival event");
        if (pollForMessages("Zone Alert Drivers Set"))
        	anyAlertsFound = true;
        modZoneAlertPref(VEHICLES);
        if (!genZoneEvent(IMEI, zoneID))
            fail("Unable to generate zone arrival event");
        if (pollForMessages("Zone Alert Vehicles Set"))
        	anyAlertsFound = true;
        modZoneAlertPref(VEHICLE_TYPES);
        if (!genZoneEvent(IMEI, zoneID))
            fail("Unable to generate zone arrival event");
        if (pollForMessages("Zone Alert Vehicle Types Set"))
        	anyAlertsFound = true;
        modZoneAlertPref(CONTACT_INFO);
        if (!genZoneEvent(IMEI, zoneID))
            fail("Unable to generate zone arrival event");
        if (pollForMessages("Zone Alert Contact Info Set"))
        	anyAlertsFound = true;
        modZoneAlertPref(ANY_TIME);
        if (!genZoneEvent(IMEI, zoneID))
            fail("Unable to generate zone arrival event");
        if (pollForMessages("Zone Alert ANY TIME (0,0) Set"))
        	anyAlertsFound = true;
        assertTrue("No Zone Alerts were generated", anyAlertsFound);
    }

    @Test
    public void alertsUnknownDriver() {
        zoneID = zone.getZoneID();
        noDriverDeviceIMEI = noDriverDevice.getImei();
        
        boolean anyAlertsFound = false;
        // generate zone arrival/departure event
        if (!genZoneEvent(noDriverDeviceIMEI, zoneID))
            fail("Unable to generate zone arrival event");
        if (pollForMessages("Zone Alert Groups Set"))
        	anyAlertsFound = true;

        if (!genSeatBeltEvent(noDriverDeviceIMEI))
            fail("Unable to generate seatbelt event");
        if (pollForMessages("Red Flag Alert Groups Set"))
        	anyAlertsFound = true;
        assertTrue("No Alerts were generated for No Driver", anyAlertsFound);
    }
    
    @Test
    public void redFlagAlerts() {
        IMEI = device.getImei();
        // generate zone arrival/departure event
        if (!genSeatBeltEvent(IMEI))
            fail("Unable to generate seatbelt event");
        pollForMessages("Red Flag Alert Groups Set");
        modRedFlagAlertPref(DRIVERS);
        if (!genSeatBeltEvent(IMEI))
            fail("Unable to generate seatbelt event");
        pollForMessages("Red Flag Alert Drivers Set");
        modRedFlagAlertPref(VEHICLES);
        if (!genSeatBeltEvent(IMEI))
            fail("Unable to generate seatbelt event");
        pollForMessages("Red Flag Alert Vehicles Set");
        modRedFlagAlertPref(VEHICLE_TYPES);
        if (!genSeatBeltEvent(IMEI))
            fail("Unable to generate seatbelt event");
        pollForMessages("Red Flag Alert Vehicle Types Set");
        modRedFlagAlertPref(ANY_TIME);
        if (!genSeatBeltEvent(IMEI))
            fail("Unable to generate seatbelt event");
        pollForMessages("Red Flag Alert Any Time Info Set");
        modRedFlagAlertPref(CONTACT_INFO);
        if (!genSeatBeltEvent(IMEI))
            fail("Unable to generate seatbelt event");
        pollForMessages("Red Flag Alert Contact Info Set");
        checkRedFlags();
    }

    private static void genTestData() {
        XMLEncoder xml = null;
        try {
            xml = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(XML_DATA_PATH)));
            genData();
            xml.writeObject(account);
            xml.writeObject(fleetGroup);
            xml.writeObject(regionGroup);
            xml.writeObject(team1Group);
            xml.writeObject(person);
            xml.writeObject(device);
            xml.writeObject(noDriverDevice);
            xml.writeObject(vehicle);
            xml.writeObject(noDriverVehicle);
            xml.writeObject(zone);
            xml.writeObject(zoneAlert);
            xml.writeObject(redFlagAlert);
            xml.close();
            System.out.println(" -- base data generation complete -- ");
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static boolean testDateExists() {
        try {
            InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(XML_DATA_FILE);
            XMLDecoder xml = new XMLDecoder(new BufferedInputStream(stream));
            account = getNext(xml, Account.class);
            fleetGroup = getNext(xml, Group.class);
            regionGroup = getNext(xml, Group.class);
            team1Group = getNext(xml, Group.class);
            person = getNext(xml, Person.class);
            device = getNext(xml, Device.class);
            noDriverDevice = getNext(xml, Device.class);
            vehicle = getNext(xml, Vehicle.class);
            noDriverVehicle = getNext(xml, Vehicle.class);
            zone = getNext(xml, Zone.class);
            zoneAlert = getNext(xml, ZoneAlert.class);
            redFlagAlert = getNext(xml, RedFlagAlert.class);
            xml.close();
            return dataExists();
        }
        catch (Exception ex) {
            logger.error("error reading " + XML_DATA_FILE, ex);
            return false;
        }
    }

    private static boolean dataExists() {
        // just spot check that account and team exist (this could be more comprehensive)
        AccountHessianDAO accountDAO = new AccountHessianDAO();
        accountDAO.setSiloService(siloService);
        Account existingAccount = accountDAO.findByID(account.getAcctID());
        boolean dataExists = (existingAccount != null);
        if (dataExists) {
            GroupHessianDAO groupDAO = new GroupHessianDAO();
            groupDAO.setSiloService(siloService);
            Group existingTeam = groupDAO.findByID(team1Group.getGroupID());
            dataExists = (existingTeam != null && existingTeam.getType().equals(GroupType.TEAM));
        }
        return dataExists;
    }

    private static <T> T getNext(XMLDecoder xml, Class<T> expectedType) throws Exception {
        Object result = xml.readObject();
        if (expectedType.isInstance(result)) {
            return (T) result;
        }
        else {
            throw new Exception("Expected " + expectedType.getName());
        }
    }

    private static void genData() {
    	
        AccountHessianDAO accountDAO = new AccountHessianDAO();
        accountDAO.setSiloService(siloService);
        account = new Account(null, null, null, Status.ACTIVE);
        account.setAcctName("AA" + Util.randomInt(0, 10000));
        // create
        Integer siloID = 0;
        Integer acctID = accountDAO.create(siloID, account);
        assertNotNull("Create Account failed", acctID);
        account.setAcctID(acctID);
        logger.debug("CREATED ACCOUNT: " + account.getAcctID());
        GroupHessianDAO groupDAO = new GroupHessianDAO();
        groupDAO.setSiloService(siloService);
        // fleet
        fleetGroup = new Group(0, acctID, "Fleet", 0, GroupType.FLEET, null, "Fleet Group", 5, new LatLng(0.0, 0.0));
        Integer groupID = groupDAO.create(acctID, fleetGroup);
        // Integer managerID, String description, Integer mapZoom, LatLng mapCenter)
        assertNotNull(groupID);
        fleetGroup.setGroupID(groupID);
        logger.debug("FLEET GROUP: " + groupID);
        // region
        regionGroup = new Group(0, acctID, "Region", fleetGroup.getGroupID(), GroupType.DIVISION, null, "Region Group", 5, new LatLng(0.0, 0.0));
        groupID = groupDAO.create(acctID, regionGroup);
        assertNotNull(groupID);
        regionGroup.setGroupID(groupID);
        logger.debug("DIVISION GROUP: " + groupID);
        // team
        team1Group = new Group(0, acctID, "Team", regionGroup.getGroupID(), GroupType.TEAM, null, "Team 1 Group", 5, new LatLng(0.0, 0.0));
        groupID = groupDAO.create(acctID, team1Group);
        assertNotNull(groupID);
        team1Group.setGroupID(groupID);
        logger.debug("TEAM GROUP: " + groupID);
        // driverVehicleSetup(Integer acctID, Integer groupID)
        PersonHessianDAO personDAO = new PersonHessianDAO();
        personDAO.setSiloService(siloService);
        Date expired = Util.genDate(2010, 8, 30);
        Address address = new Address(null, Util.randomInt(100, 999) + " Street", null, "City " + Util.randomInt(10, 99), randomState(), "12345", acctID);
        Driver driver = new Driver(0, 0, Status.ACTIVE,null, null, null, "l" + groupID, randomState(), "ABCD", expired, null, null, groupID);
   		List<Integer> adminRoles = new ArrayList<Integer>();
   		adminRoles.add(MockRoles.getAdminUser().getRoleID());
        User user = new User(0, 0, adminRoles, MockRoles.getAllAccessPoints(), Status.ACTIVE, "deepuser_" + groupID, PASSWORD, groupID);
        Date dob = Util.genDate(1959, 8, 30);
        person = new Person(0, acctID, TimeZone.getTimeZone("US/Mountain"), null, address.getAddrID(), "email" + groupID + "@email.com", "secEmail@test.com", "8015551111", "8015552222",
                "8015554444@texter.com", "8015555555@texter.com", 1, 1, 1, "emp" + groupID, null, "title" + groupID, "dept" + groupID, "first" + groupID, "m" + groupID, "last"
                        + groupID, "jr", Gender.MALE, 65, 180, dob, Status.ACTIVE, MeasurementType.ENGLISH, FuelEfficiencyType.MPG_US, Locale.getDefault());
        person.setUser(user);
        person.setDriver(driver);
        person.setAddress(address);
        Integer personID = personDAO.create(acctID, person);
        person.setPersonID(personID);
        assertNotNull(personID);
        logger.debug("PERSON ID: " + personID);
        logger.debug("DRIVER ID: " + person.getDriver().getDriverID());
        
        DeviceHessianDAO deviceDAO = new DeviceHessianDAO();
        deviceDAO.setSiloService(siloService);
        device = new Device(0, acctID, DeviceStatus.ACTIVE, "Device", genNumericID(acctID, 15), genNumericID(acctID, 19), genNumericID(account.getAcctID(), 10), "5555551234",
                "5555559876");
        device.setAccel("1100 50 4");
        Integer deviceID = deviceDAO.create(acctID, device);
        device.setDeviceID(deviceID);
        IMEI = device.getImei();
        logger.debug("DEVICE ID: " + deviceID);
        VehicleHessianDAO vehicleDAO = new VehicleHessianDAO();
        vehicleDAO.setSiloService(siloService);
        vehicle = new Vehicle(0, groupID, 10, Status.ACTIVE, "Test Vehicle", "Make", "Model", 2000, "Red", VehicleType.LIGHT, "VIN_" + groupID, 1000, "UT " + groupID, States
                .getStateByAbbrev("UT"));
        Integer vehicleID = vehicleDAO.create(groupID, vehicle);
        vehicle.setVehicleID(vehicleID);
        logger.debug("VEHICLE ID: " + vehicleID);
        vehicleDAO.setDeviceDAO(deviceDAO);
        vehicleDAO.setVehicleDevice(vehicleID, deviceID);
        vehicleDAO.setVehicleDriver(vehicleID, driver.getDriverID());
        
        deviceDAO.setSiloService(siloService);
        noDriverDevice = new Device(0, acctID, DeviceStatus.ACTIVE, "Device", genNumericID(acctID, 15, "7"), genNumericID(acctID, 19, "7"), genNumericID(account.getAcctID(), 10, "7"), "5555551235",
                "5555559877");
        noDriverDevice.setAccel("1100 50 4");
        deviceID = deviceDAO.create(acctID, noDriverDevice);
        noDriverDevice.setDeviceID(deviceID);
        noDriverDeviceIMEI = noDriverDevice.getImei();
        logger.debug("DEVICE ID: " + deviceID);
        vehicleDAO.setSiloService(siloService);
        noDriverVehicle = new Vehicle(0, groupID, 10, Status.ACTIVE, "Test Vehicle", "Make", "Model", 2000, "Red", VehicleType.LIGHT, "VINa_" + groupID, 1000, "UT " + groupID, States
                .getStateByAbbrev("UT"));
        vehicleID = vehicleDAO.create(groupID, noDriverVehicle);
        noDriverVehicle.setVehicleID(vehicleID);
        logger.debug("VEHICLE ID: " + vehicleID);
        vehicleDAO.setDeviceDAO(deviceDAO);
        vehicleDAO.setVehicleDevice(vehicleID, deviceID);
        
        zoneAlert(acctID, team1Group.getGroupID(), personID);
        redFlagAlert(acctID, team1Group.getGroupID());
    }

    private static String genNumericID(Integer acctID, Integer len) {
        String id = "999" + acctID.toString();
        for (int i = id.length(); i < len; i++) {
            id += "9";
        }
        return id;
    }
    private static String genNumericID(Integer acctID, Integer len, String ch) {
        String id = "999" + acctID.toString();
        for (int i = id.length(); i < len; i++) {
            id += ch;
        }
        return id;
    }

    private static void zoneAlert(Integer acctID, Integer groupID, Integer... notifyPersonIDs) {
        ZoneHessianDAO zoneDAO = new ZoneHessianDAO();
        zoneDAO.setSiloService(siloService);
        // create a zone to use
        zone = new Zone(0, acctID, Status.ACTIVE, "Zone With Alerts", "123 Street, Salt Lake City, UT 84107", groupID);
        List<LatLng> points = new ArrayList<LatLng>();
        points.add(new LatLng(40.723871753812f, -111.92932452647742f));
        points.add(new LatLng(40.704246f, -111.948613f));
        points.add(new LatLng(40.70f, -111.95f));
        points.add(new LatLng(40.723871753812f, -111.92932452647742f));
        zone.setPoints(points);
        zoneID = zoneDAO.create(acctID, zone);
        assertNotNull(zoneID);
        zone.setZoneID(zoneID);
        logger.debug("ZONE_ID:" + zoneID);
        ZoneAlertHessianDAO zoneAlertDAO = new ZoneAlertHessianDAO();
        zoneAlertDAO.setSiloService(siloService);
        List<Boolean> dayOfWeek = new ArrayList<Boolean>();
        for (int i = 0; i < 7; i++)
            dayOfWeek.add(true);
        List<Integer> groupIDList = new ArrayList<Integer>();
        groupIDList.add(team1Group.getGroupID());
        List<String> emailList = new ArrayList<String>();
        emailList.add("cjennings@inthinc.com");
        List<Integer> notifyPersonIDList = new ArrayList<Integer>();
        notifyPersonIDList.add(notifyPersonIDs[0]);
        zoneAlert = new ZoneAlert(acctID, "Zone Alert Profile", "Zone Alert Profile Description", 0, 1439, // start/end time setting to null to indicate anytime?
                dayOfWeek, groupIDList, null, // driverIDs
                null, // vehicleIDs
                null, // vehicleTypeIDs
                notifyPersonIDList, // notifyPersonIDs
                emailList, // emailTo
                0, zoneID, true, true);
        zoneAlert.setNotifyUserIDs(notifyPersonIDList);
        Integer zoneAlertID = zoneAlertDAO.create(acctID, zoneAlert);
        assertNotNull(zoneAlertID);
        zoneAlert.setZoneAlertID(zoneAlertID);
        logger.debug("ZONE_ALERT_ID:" + zoneAlertID);
    }

    private static void modZoneAlertPref(int type) {
        List<Integer> driverIDs = new ArrayList<Integer>();
        driverIDs.add(person.getDriver().getDriverID());
        List<Integer> vehicleIDs = new ArrayList<Integer>();
        vehicleIDs.add(vehicle.getVehicleID());
        List<VehicleType> vehicleTypes = new ArrayList<VehicleType>();
        vehicleTypes.add(vehicle.getVtype());
        List<Integer> groupIDList = new ArrayList<Integer>();
        groupIDList.add(team1Group.getGroupID());
        List<Integer> notifyPersonIDs = new ArrayList<Integer>();
        notifyPersonIDs.add(person.getPersonID());
        List<String> emailList = new ArrayList<String>();
        emailList.add("cjennings@inthinc.com");
        zoneAlert.setStartTOD(0);
        zoneAlert.setStopTOD(1439);
        List<Integer> emptyList = new ArrayList<Integer>();
        List<VehicleType> emptyVTList = new ArrayList<VehicleType>();
        switch (type) {
            case DRIVERS:
                zoneAlert.setDriverIDs(driverIDs);
                zoneAlert.setGroupIDs(emptyList);
                zoneAlert.setVehicleIDs(emptyList);
                zoneAlert.setVehicleTypes(emptyVTList);
                zoneAlert.setNotifyPersonIDs(notifyPersonIDs);
                zoneAlert.setEmailTo(emailList);
                break;
            case VEHICLES:
                zoneAlert.setDriverIDs(emptyList);
                zoneAlert.setGroupIDs(emptyList);
                zoneAlert.setVehicleIDs(vehicleIDs);
                zoneAlert.setVehicleTypes(emptyVTList);
                zoneAlert.setNotifyPersonIDs(notifyPersonIDs);
                zoneAlert.setEmailTo(emailList);
                break;
            case VEHICLE_TYPES:
                zoneAlert.setDriverIDs(emptyList);
                zoneAlert.setGroupIDs(emptyList);
                zoneAlert.setVehicleIDs(emptyList);
                zoneAlert.setVehicleTypes(vehicleTypes);
                zoneAlert.setNotifyPersonIDs(notifyPersonIDs);
                zoneAlert.setEmailTo(emailList);
                break;
            case GROUPS:
                zoneAlert.setDriverIDs(emptyList);
                zoneAlert.setGroupIDs(groupIDList);
                zoneAlert.setVehicleIDs(emptyList);
                zoneAlert.setVehicleTypes(emptyVTList);
                zoneAlert.setNotifyPersonIDs(notifyPersonIDs);
                zoneAlert.setEmailTo(emailList);
                break;
            case CONTACT_INFO:
                zoneAlert.setDriverIDs(emptyList);
                zoneAlert.setGroupIDs(groupIDList);
                zoneAlert.setVehicleIDs(emptyList);
                zoneAlert.setVehicleTypes(emptyVTList);
                zoneAlert.setNotifyPersonIDs(notifyPersonIDs);
                zoneAlert.setEmailTo(new ArrayList<String>());
                break;
            case ANY_TIME:
                zoneAlert.setDriverIDs(null);
                zoneAlert.setGroupIDs(groupIDList);
                zoneAlert.setVehicleIDs(null);
                zoneAlert.setVehicleTypes(null);
                zoneAlert.setNotifyPersonIDs(notifyPersonIDs);
                zoneAlert.setStartTOD(0);
                zoneAlert.setStopTOD(0);
                zoneAlert.setEmailTo(emailList);
                break;
        }
        ZoneAlertHessianDAO zoneAlertDAO = new ZoneAlertHessianDAO();
        zoneAlertDAO.setSiloService(siloService);
        zoneAlertDAO.update(zoneAlert);
    }

    private static void modRedFlagAlertPref(int type) {
        List<Integer> driverIDs = new ArrayList<Integer>();
        driverIDs.add(person.getDriver().getDriverID());
        List<Integer> vehicleIDs = new ArrayList<Integer>();
        vehicleIDs.add(vehicle.getVehicleID());
        List<VehicleType> vehicleTypes = new ArrayList<VehicleType>();
        vehicleTypes.add(vehicle.getVtype());
        List<Integer> groupIDList = new ArrayList<Integer>();
        groupIDList.add(team1Group.getGroupID());
        List<Integer> notifyPersonIDs = new ArrayList<Integer>();
        notifyPersonIDs.add(person.getPersonID());
        List<Integer> emptyList = new ArrayList<Integer>();
        List<VehicleType> emptyVTList = new ArrayList<VehicleType>();
        List<String> emailList = new ArrayList<String>();
        emailList.add("cjennings@inthinc.com");
        redFlagAlert.setStartTOD(0);
        redFlagAlert.setStopTOD(1439);
        switch (type) {
            case DRIVERS:
                redFlagAlert.setDriverIDs(driverIDs);
                redFlagAlert.setGroupIDs(emptyList);
                redFlagAlert.setVehicleIDs(emptyList);
                redFlagAlert.setVehicleTypes(emptyVTList);
                redFlagAlert.setNotifyPersonIDs(notifyPersonIDs);
                redFlagAlert.setEmailTo(emailList);
                break;
            case VEHICLES:
                redFlagAlert.setDriverIDs(emptyList);
                redFlagAlert.setGroupIDs(emptyList);
                redFlagAlert.setVehicleIDs(vehicleIDs);
                redFlagAlert.setVehicleTypes(emptyVTList);
                redFlagAlert.setNotifyPersonIDs(notifyPersonIDs);
                redFlagAlert.setEmailTo(emailList);
                break;
            case VEHICLE_TYPES:
                redFlagAlert.setDriverIDs(emptyList);
                redFlagAlert.setGroupIDs(emptyList);
                redFlagAlert.setVehicleIDs(emptyList);
                redFlagAlert.setVehicleTypes(vehicleTypes);
                redFlagAlert.setNotifyPersonIDs(notifyPersonIDs);
                redFlagAlert.setEmailTo(emailList);
                break;
            case GROUPS:
                redFlagAlert.setDriverIDs(emptyList);
                redFlagAlert.setGroupIDs(groupIDList);
                redFlagAlert.setVehicleIDs(emptyList);
                redFlagAlert.setVehicleTypes(emptyVTList);
                redFlagAlert.setNotifyPersonIDs(notifyPersonIDs);
                redFlagAlert.setEmailTo(emailList);
                break;
            case CONTACT_INFO:
                redFlagAlert.setDriverIDs(emptyList);
                redFlagAlert.setGroupIDs(groupIDList);
                redFlagAlert.setVehicleIDs(emptyList);
                redFlagAlert.setVehicleTypes(emptyVTList);
                redFlagAlert.setNotifyPersonIDs(notifyPersonIDs);
                redFlagAlert.setEmailTo(new ArrayList<String>());
                break;
            case ANY_TIME:
                redFlagAlert.setDriverIDs(emptyList);
                redFlagAlert.setGroupIDs(groupIDList);
                redFlagAlert.setVehicleIDs(emptyList);
                redFlagAlert.setVehicleTypes(emptyVTList);
                redFlagAlert.setNotifyPersonIDs(notifyPersonIDs);
                redFlagAlert.setEmailTo(emailList);
                redFlagAlert.setStartTOD(0);
                redFlagAlert.setStopTOD(1439);
                break;
        }
        RedFlagAlertHessianDAO redFlagAlertDAO = new RedFlagAlertHessianDAO();
        redFlagAlertDAO.setSiloService(siloService);
        redFlagAlertDAO.update(redFlagAlert);
    }

    private static void redFlagAlert(Integer acctID, Integer groupID) {
        RedFlagAlertHessianDAO redFlagAlertDAO = new RedFlagAlertHessianDAO();
        redFlagAlertDAO.setSiloService(siloService);
        List<Boolean> dayOfWeek = new ArrayList<Boolean>();
        for (int i = 0; i < 7; i++)
            dayOfWeek.add(true);
        List<Integer> groupIDList = new ArrayList<Integer>();
        groupIDList.add(team1Group.getGroupID());
        Integer[] speedSettings = new Integer[15];
        RedFlagLevel[] speedLevels = new RedFlagLevel[15];
        for (int i = 0; i < 15; i++) {
            speedSettings[i] = 5;
            speedLevels[i] = RedFlagLevel.CRITICAL;
        }
        List<String> emailList = new ArrayList<String>();
        emailList.add("cjennings@inthinc.com");
        // speeding alert
        redFlagAlert = new RedFlagAlert(acctID, "Red Flag Alert Profile", "Red Flag Alert Profile Description", 0,
                1439, // start/end time
                dayOfWeek, groupIDList,
                null, // driverIDs
                null, // vehicleIDs
                null, // vehicleTypeIDs
                null,
                emailList, // emailTo
                null, null, null, null, null, null,
                RedFlagLevel.NONE, RedFlagLevel.NONE, RedFlagLevel.NONE, RedFlagLevel.NONE, 
                RedFlagLevel.CRITICAL, RedFlagLevel.NONE, RedFlagLevel.NONE, RedFlagLevel.NONE);
        
        Integer redFlagAlertID = redFlagAlertDAO.create(acctID, redFlagAlert);
        assertNotNull(redFlagAlertID);
        redFlagAlert.setRedFlagAlertID(redFlagAlertID);
        logger.debug("RED FLAG ALERT PREF ID: " + redFlagAlertID);
    }

    private boolean genZoneEvent(String imei, Integer zoneID) {
        System.out.println("IMEI: " + imei);
        System.out.println("ZoneID: " + zoneID);
        ZoneArrivalEvent event = new ZoneArrivalEvent(0l, 0, EventMapper.TIWIPRO_EVENT_WSZONES_ARRIVAL_EX, new Date(), 60, 1000, new Double(40.704246f), new Double(-111.948613f),
                zoneID);
        return genEvent(event, imei);
    }

    private boolean genSeatBeltEvent(String imei) {
        SeatBeltEvent event = new SeatBeltEvent(0l, 0, EventMapper.TIWIPRO_EVENT_SEATBELT, new Date(), 60, 1000, 
        		new Double(40.704246f), new Double(-111.948613f), 80, 100, 20);
        return genEvent(event, imei);
    }

    private boolean genEvent(Event event, String imei) {
        List<byte[]> noteList = new ArrayList<byte[]>();

        byte[] eventBytes = EventGenerator.createDataBytesFromEvent(event);
        noteList.add(eventBytes);
        boolean errorFound = false;
        int retryCnt = 0;
        while (!errorFound) {
            try {
                mcmSim.note(imei, noteList);
                break;
            }
            catch (ProxyException ex) {
//                logger.error(ex.getMessage());
                if (ex.getErrorCode() != 414) {
                    System.out.print("RETRY EVENT GEN because: " + ex.getErrorCode() + "");
                    errorFound = true;
                }
                else {
                    if (retryCnt == 300) {
                        System.out.println("Retries failed after 5 min.");
                        errorFound = true;
                    }
                    else {
                    	if (retryCnt == 0)
                    		System.out.println("waiting for IMEI to show up in central server");
                        try {
                            Thread.sleep(1000l);
                            retryCnt++;
                        }
                        catch (InterruptedException e) {
                            errorFound = true;
                            e.printStackTrace();
                        }
                        System.out.print(".");
                        if (retryCnt % 25 == 0)
                            System.out.println();
                    }
                }
            }
            catch (RemoteServerException re) {
                if (re.getErrorCode() != 302 ) {
                    errorFound = true;
                }
            	
            }
            catch (Exception e) {
                e.printStackTrace();
                errorFound = true;
            }
        }
        return !errorFound;
    }

    private static void initDAOs()
    {
        alertMessageDAO = new AlertMessageHessianDAO();
        alertMessageDAO.setSiloService(siloService);
        eventDAO = new EventHessianDAO();
        driverDAO = new DriverHessianDAO();
        groupDAO = new GroupHessianDAO();
        personDAO = new PersonHessianDAO();
        vehicleDAO = new VehicleHessianDAO();
        zoneHessianDAO = new ZoneHessianDAO();
        addressLookup = new GeonamesAddressLookup();
        addressLookup.setMapServerURLString(mapServerURL);
        driverDAO.setSiloService(siloService);
        groupDAO.setSiloService(siloService);
        eventDAO.setSiloService(siloService);
        personDAO.setSiloService(siloService);
        vehicleDAO.setSiloService(siloService);
        zoneHessianDAO.setSiloService(siloService);
        alertMessageDAO.setDriverDAO(driverDAO);
        alertMessageDAO.setVehicleDAO(vehicleDAO);
        alertMessageDAO.setEventDAO(eventDAO);
        alertMessageDAO.setGroupDAO(groupDAO);
        alertMessageDAO.setPersonDAO(personDAO);
        alertMessageDAO.setZoneDAO(zoneHessianDAO);
        alertMessageDAO.setAddressLookup(addressLookup);
    }
    
    private boolean pollForMessages(String description) {
        System.out.print("Poll for message: " + description);
        int secondsToWait = 30;
        for (int i = 0; i < secondsToWait; i++) {
            List<AlertMessageBuilder> msgList = alertMessageDAO.getMessageBuilders(AlertMessageDeliveryType.EMAIL);
            if (msgList.size() == 0) {
                if (i == (secondsToWait-1)) {
                	System.out.println();
                	logger.error(description + " getMessages failed even after waiting " + secondsToWait + " sec -- most likely the scheduler picked them up");
                }
                try {
                    Thread.sleep(1000l);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
                System.out.print(".");
            }
            else {
                System.out.println(".");
                // check it
                AlertMessageBuilder msg = msgList.get(0);
                assertNotNull(description, msg);
                assertNotNull(description, msg.getAddress());
                assertNotNull(description, msg.getParamterList());
                System.out.println(description + "address: " + msg.getAddress() + " msg: " + msg.getParamterList());
                // logger.debug(description + "address: " + msg.getAddress() + " msg: " + msg.getParamterList());
                return true;
            }
        }
        
        return false;
    }

    private void checkRedFlags() {
        RedFlagHessianDAO redFlagDAO = new RedFlagHessianDAO();
        redFlagDAO.setSiloService(siloService);
        List<RedFlag> redFlagList = redFlagDAO.getRedFlags(team1Group.getGroupID(), 1, 0);
        assertNotNull(redFlagList);
        assertTrue("Red flags should be found", (redFlagList.size() > 0));
        for (RedFlag redflag : redFlagList) {
            logger.debug("redFlag: level" + redflag.getLevel() + " event type: " + redflag.getEvent().getEventType());
        }
    }

    private static State randomState() {
        int idx = Util.randomInt(0, States.getStates().size() - 1);
        int cnt = 0;
        for (State state : States.getStates().values()) {
            if (cnt++ == idx)
                return state;
        }
        return null;
    }

    private static Role adminRole() {
    	
    	return Roles.getRoleMapById().get(2);
//        for (Role role : Roles.getRoleMap().values()) {
//            if (role.getRoleID().intValue() == 5)	// superuser
//                return role;
//        }
//        return null;
    }
}
