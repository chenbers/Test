package it.com.inthinc.pro.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import it.config.IntegrationConfig;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.FindByKey;
import com.inthinc.pro.dao.hessian.AccountHessianDAO;
import com.inthinc.pro.dao.hessian.AddressHessianDAO;
import com.inthinc.pro.dao.hessian.DeviceHessianDAO;
import com.inthinc.pro.dao.hessian.DriverHessianDAO;
import com.inthinc.pro.dao.hessian.EventHessianDAO;
import com.inthinc.pro.dao.hessian.GroupHessianDAO;
import com.inthinc.pro.dao.hessian.PersonHessianDAO;
import com.inthinc.pro.dao.hessian.RedFlagAlertHessianDAO;
import com.inthinc.pro.dao.hessian.ReportScheduleHessianDAO;
import com.inthinc.pro.dao.hessian.RoleHessianDAO;
import com.inthinc.pro.dao.hessian.StateHessianDAO;
import com.inthinc.pro.dao.hessian.TablePreferenceHessianDAO;
import com.inthinc.pro.dao.hessian.TimeZoneHessianDAO;
import com.inthinc.pro.dao.hessian.UserHessianDAO;
import com.inthinc.pro.dao.hessian.VehicleHessianDAO;
import com.inthinc.pro.dao.hessian.ZoneAlertHessianDAO;
import com.inthinc.pro.dao.hessian.ZoneHessianDAO;
import com.inthinc.pro.dao.hessian.exceptions.DuplicateEntryException;
import com.inthinc.pro.dao.hessian.exceptions.DuplicateIMEIException;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.AutoLogoff;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.DeviceStatus;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.DriverLocation;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventCategory;
import com.inthinc.pro.model.EventMapper;
import com.inthinc.pro.model.ForwardCommand;
import com.inthinc.pro.model.ForwardCommandID;
import com.inthinc.pro.model.ForwardCommandStatus;
import com.inthinc.pro.model.FuelEfficiencyType;
import com.inthinc.pro.model.Gender;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupType;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Occurrence;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.RedFlagAlert;
import com.inthinc.pro.model.RedFlagLevel;
import com.inthinc.pro.model.ReportSchedule;
import com.inthinc.pro.model.Role;
import com.inthinc.pro.model.SensitivityForwardCommandMapping;
import com.inthinc.pro.model.SensitivityType;
import com.inthinc.pro.model.State;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.TablePreference;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.TripStatus;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleType;
import com.inthinc.pro.model.Zone;
import com.inthinc.pro.model.ZoneAlert;
import com.inthinc.pro.model.app.DeviceSensitivityMapping;
import com.inthinc.pro.model.app.Roles;
import com.inthinc.pro.model.app.States;
import com.inthinc.pro.model.app.SupportedTimeZones;

public class SiloServiceTest {
    private static final Logger logger = Logger.getLogger(SiloServiceTest.class);
    private static SiloService siloService;
    private Account account;
    private Address address;
    private Group fleetGroup;
    private Group regionGroup;
    private Group team1Group;
    private Group team2Group;
    private List<Device> deviceList = new ArrayList<Device>();
    private List<Vehicle> vehicleList = new ArrayList<Vehicle>();
    private List<Person> personList = new ArrayList<Person>();
    private List<User> userList = new ArrayList<User>();
    private List<Driver> driverList = new ArrayList<Driver>();
    private static final Integer TESTING_SILO = 0; // this silo can be wiped out/restored at anytime
    private static final Integer DEVICE_COUNT = 5;
    private static final Integer VEHICLE_COUNT = 3;
    private static final Integer PERSON_COUNT = 3;
    private static final String PASSWORD = "nuN5q/jdjEpJKKA4A6jLTZufWZfIXtxqzjVjifqFjbGg6tfmQFGXbTtcXtEIg4Z7"; // password
    private static final String SPEEDRACER = "speedracer";
    private static Integer TESTING_DRIVER_ID; // speedracer
    private static Integer TESTING_VEHICLE_ID = 1; // speedracer
    private static Integer TESTING_GROUP_ID; // speedracer

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        IntegrationConfig config = new IntegrationConfig();
        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());
        siloService = new SiloServiceCreator(host, port).getService();
        // HessianDebug.debugIn = true;
        // HessianDebug.debugOut = true;
        // HessianDebug.debugRequest = true;
        DeviceHessianDAO deviceDAO = new DeviceHessianDAO();
        deviceDAO.setSiloService(siloService);
        DeviceSensitivityMapping mapping = new DeviceSensitivityMapping();
        mapping.setDeviceDAO(deviceDAO);
        mapping.init();
        
        //Setup Speedracer
        UserHessianDAO userDAO = new UserHessianDAO();
        userDAO.setSiloService(siloService);
        User user = userDAO.findByUserName(SPEEDRACER);
        assertNotNull("Error retrieving the User 'speedracer'", user);
        TESTING_GROUP_ID = user.getGroupID();
        DriverHessianDAO driverDAO = new DriverHessianDAO();
        driverDAO.setSiloService(siloService);
        Driver driver = driverDAO.findByPersonID(user.getPerson().getPersonID());
        assertNotNull("Error retrieving the Driver associated with 'speedracer'", driver);
        TESTING_DRIVER_ID = driver.getDriverID();
        VehicleHessianDAO vehicleDAO = new VehicleHessianDAO();
        vehicleDAO.setSiloService(siloService);
        Vehicle vehicle = vehicleDAO.findByDriverID(TESTING_DRIVER_ID);
        assertNotNull("Error retrieving the Vehicle associated with 'speedracer'", vehicle);
        TESTING_VEHICLE_ID = vehicle.getVehicleID();
    }
    
    @Test
    public void testDeviceSensitivityMapping() {
        for (SensitivityType type : SensitivityType.values()) {
            assertNotNull(DeviceSensitivityMapping.getForwardCommand(type, 1));
        }
    }


    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Test
    public void states() {
        StateHessianDAO stateDAO = new StateHessianDAO();
        stateDAO.setSiloService(siloService);
        States states = new States();
        states.setStateDAO(stateDAO);
        states.init();
        assertTrue(States.getStates().size() >= 50);
    }

    @Test
    public void roles() {
        RoleHessianDAO roleDAO = new RoleHessianDAO();
        roleDAO.setSiloService(siloService);
        Roles roles = new Roles();
        roles.setRoleDAO(roleDAO);
        roles.init();
        assertTrue(Roles.getRoleMap().size() > 0);
    }

    @Test
    public void supportedTimeZones() {
        TimeZoneHessianDAO timeZoneDAO = new TimeZoneHessianDAO();
        timeZoneDAO.setSiloService(siloService);
        SupportedTimeZones supportedTimeZones = new SupportedTimeZones();
        supportedTimeZones.setTimeZoneDAO(timeZoneDAO);
        supportedTimeZones.init();
        assertTrue(SupportedTimeZones.getSupportedTimeZones().size() > 0);
    }

    @Test
    public void sensitivityForwardCommandMapping() {
        DeviceHessianDAO deviceDAO = new DeviceHessianDAO();
        deviceDAO.setSiloService(siloService);
        Map<SensitivityType, SensitivityForwardCommandMapping> fcList = deviceDAO.getSensitivityForwardCommandMapping();
        assertEquals("The sensitivity forward command mapping list should contain 5 items.", 5, fcList.size());
    }

    @Test
    public void lastLocationDriver() {
        DriverHessianDAO driverDAO = new DriverHessianDAO();
        driverDAO.setSiloService(siloService);
        LastLocation loc = driverDAO.getLastLocation(TESTING_DRIVER_ID);
        assertNotNull(loc);
        assertEquals(TESTING_DRIVER_ID, loc.getDriverID());
        assertNotNull(loc.getTime());
        assertNotNull(loc.getLoc().getLat());
        assertNotNull(loc.getLoc().getLng());
        assertNotNull(loc.getLoc().getLng());
    }

    @Test
    public void driversNearLoc() {
        // DriverHessianDAO driverDAO = new DriverHessianDAO();
        // driverDAO.setSiloService(siloService);
        VehicleHessianDAO vehicleDAO = new VehicleHessianDAO();
        vehicleDAO.setSiloService(siloService);
        Integer numOf = 10;
        Double lat = (double) 33.010;
        Double lng = (double) -117.111;
        List<DriverLocation> result = vehicleDAO.getVehiclesNearLoc(TESTING_GROUP_ID, numOf, lat, lng);
        assertNotNull(result);
        if (result != null) {
            for (DriverLocation d : result) {
                System.out.println("found " + d.getDriver().getGroupID() + " " + d.getDriver().getDriverID() + " " + d.getLoc().getLat() + " " + d.getLoc().getLng());
            }
        } else {
            System.out.println("NO DRIVERS FOUND NEAR");
        }
    }

    @Test
    public void driversEvents() {
        EventHessianDAO eventDAO = new EventHessianDAO();
        eventDAO.setSiloService(siloService);
        // year time frame from today back
        Date endDate = new Date();
        Date startDate = DateUtil.getDaysBackDate(endDate, 365);
        List<Event> result = eventDAO.getEventsForDriver(TESTING_DRIVER_ID, startDate, endDate, EventMapper.getEventTypesInCategory(EventCategory.VIOLATION),
                EventDAO.EXCLUDE_FORGIVEN);
        assertNotNull(result);
        if (result != null) {
            for (Event r : result) {
                System.out.println("driver id " + r.getDriverID() + " speed " + r.getSpeed() + " lat " + r.getLatitude() + " lng " + r.getLongitude());
            }
            int size = result.size();
            if (size > 0) {
                Event e = result.get(0);
                eventDAO.forgive(e.getDriverID(), e.getNoteID());
                List<Event> newResult = eventDAO.getEventsForDriver(TESTING_DRIVER_ID, startDate, endDate, EventMapper.getEventTypesInCategory(EventCategory.VIOLATION),
                        EventDAO.EXCLUDE_FORGIVEN);
                assertEquals("list size should be 1 less after forgive", (size - 1), newResult.size());
                eventDAO.unforgive(e.getDriverID(), e.getNoteID());
                newResult = eventDAO.getEventsForDriver(TESTING_DRIVER_ID, startDate, endDate, EventMapper.getEventTypesInCategory(EventCategory.VIOLATION),
                        EventDAO.EXCLUDE_FORGIVEN);
                assertEquals("list size should be same after forgive/unforgive", size, newResult.size());
            }
        } else {
            System.out.println("NO VEHICLES FOUND");
        }
    }

    @Test
    public void vehiclesEvents() {
        EventHessianDAO eventDAO = new EventHessianDAO();
        eventDAO.setSiloService(siloService);
        // year time frame from today back
        Date endDate = new Date();
        Date startDate = DateUtil.getDaysBackDate(endDate, 365);
        List<Integer> type = new ArrayList<Integer>();
        type.add(EventMapper.TIWIPRO_EVENT_SPEEDING_EX3);
        List<Event> result = eventDAO.getEventsForVehicle(TESTING_VEHICLE_ID, startDate, endDate, type, EventDAO.EXCLUDE_FORGIVEN);
        assertNotNull(result);
        if (result != null) {
            for (Event r : result) {
                System.out.println("vehicle id " + r.getVehicleID() + " speed " + r.getSpeed() + " lat " + r.getLatitude() + " lng " + r.getLongitude());
            }
        } else {
            System.out.println("NO VEHICLES FOUND");
        }
    }

    @Test
    public void lastLocationVehicle() {
        VehicleHessianDAO vehicleDAO = new VehicleHessianDAO();
        vehicleDAO.setSiloService(siloService);
        LastLocation vloc = vehicleDAO.getLastLocation(TESTING_VEHICLE_ID);
        assertNotNull(vloc);
        assertEquals(TESTING_VEHICLE_ID, vloc.getVehicleID());
        assertNotNull(vloc.getTime());
        assertNotNull(vloc.getLoc().getLat());
        assertNotNull(vloc.getLoc().getLng());
    }

    /*
     * @Test public void tmpevents() { EventHessianDAO eventDAO = new EventHessianDAO(); eventDAO.setSiloService(siloService);
     * 
     * List<Event> recentEventsList = eventDAO.getMostRecentEvents(1105, 5); assertEquals(5, recentEventsList.size());
     * validateEvents(EventMapper.getEventTypesInCategory(EventCategory.VIOLATION), recentEventsList);
     * 
     * 
     * List<Event> recentWarningsList = eventDAO.getMostRecentWarnings(1105, 5); assertEquals(3, recentWarningsList.size());
     * validateEvents(EventMapper.getEventTypesInCategory(EventCategory.WARNING), recentWarningsList);
     * 
     * 
     * }
     */
    @Test
    public void events() {
        EventHessianDAO eventDAO = new EventHessianDAO();
        eventDAO.setSiloService(siloService);
        // year time frame from today back
        Date endDate = new Date();
        Date startDate = DateUtil.getDaysBackDate(endDate, 365);
        List<Event> violationEventsList = eventDAO.getViolationEventsForDriver(TESTING_DRIVER_ID, startDate, endDate, EventDAO.EXCLUDE_FORGIVEN);
        assertTrue("expected some events to be returned", violationEventsList.size() > 0);
        validateEvents(EventMapper.getEventTypesInCategory(EventCategory.VIOLATION), violationEventsList, startDate, endDate);
        List<Event> warningEventsList = eventDAO.getWarningEventsForDriver(TESTING_DRIVER_ID, startDate, endDate, EventDAO.EXCLUDE_FORGIVEN);
        // TODO: ask David to generate some of these types
        // assertTrue("expected some events to be returned", warningEventsList.size() > 0);
        validateEvents(EventMapper.getEventTypesInCategory(EventCategory.WARNING), warningEventsList, startDate, endDate);
        List<Event> recentEventsList = eventDAO.getMostRecentEvents(TESTING_GROUP_ID, 5);
        // assertTrue("expected some events to be returned", (recentEventsList.size() >= 0 && recentEventsList.size() < 6));
        validateEvents(EventMapper.getEventTypesInCategory(EventCategory.VIOLATION), recentEventsList);
        int listSize = recentEventsList.size();
        List<Event> recentWarningsList = eventDAO.getMostRecentWarnings(TESTING_GROUP_ID, 5);
        // TODO: ask David to generate some of these types
        // assertTrue("expected some events to be returned", (recentWarningsList.size() > 0 && recentWarningsList.size() < 6));
        validateEvents(EventMapper.getEventTypesInCategory(EventCategory.WARNING), recentWarningsList);
        recentEventsList = eventDAO.getMostRecentEvents(TESTING_GROUP_ID, 5);
        assertEquals(listSize, recentEventsList.size());
    }

    private void validateEvents(List<Integer> expectedTypes, List<Event> eventList, Date startDate, Date endDate) {
        for (Event violation : eventList) {
            assertTrue(expectedTypes.contains(violation.getType()));
            assertTrue(startDate.before(violation.getTime()));
            assertTrue(endDate.after(violation.getTime()));
        }
    }

    private void validateEvents(List<Integer> expectedTypes, List<Event> eventList) {
        for (Event violation : eventList) {
            assertTrue(expectedTypes.contains(violation.getType()));
        }
    }

    @Test
    public void trips() {
        DriverHessianDAO driverDAO = new DriverHessianDAO();
        driverDAO.setSiloService(siloService);
        VehicleHessianDAO vehicleDAO = new VehicleHessianDAO();
        vehicleDAO.setSiloService(siloService);
        // year time frame from today back
        Date endDate = new Date();
        Date startDate = DateUtil.getDaysBackDate(endDate, 365);
        List<Trip> tripList = driverDAO.getTrips(TESTING_DRIVER_ID, startDate, endDate);
        assertNotNull(tripList);
        System.out.println("num trips: " + tripList.size());
        assertTrue(tripList.size() > 0);
        for (Trip trip : tripList) {
            // TODO: at the moment, the first trip in the collection has a vehicleID of 0, the rest are 1. I have asked David Story to look at it.
            // Until he fixes it, I will leave the following line commented
            // assertEquals(TESTING_VEHICLE_ID, trip.getVehicleID());
            assertTrue(startDate.before(trip.getStartTime()));
            assertTrue(endDate.after(trip.getEndTime()));
            assertTrue(trip.getMileage() > 0);
            assertTrue(trip.getStatus().equals(TripStatus.TRIP_COMPLETED));
        }
        Trip trip = driverDAO.getLastTrip(TESTING_DRIVER_ID);
        assertNotNull(trip);
        tripList = vehicleDAO.getTrips(TESTING_VEHICLE_ID, startDate, endDate);
        assertNotNull(tripList);
        System.out.println("num trips: " + tripList.size());
        assertTrue(tripList.size() > 0);
        for (Trip t : tripList) {
            // It is possible that a trip is associated to the Unknown Driver
            // assertEquals(TESTING_DRIVER_ID, t.getDriverID());
            assertTrue(startDate.before(t.getStartTime()));
            assertTrue(endDate.after(t.getEndTime()));
            assertTrue(t.getMileage() > 0);
        }
        trip = vehicleDAO.getLastTrip(TESTING_VEHICLE_ID);
        assertNotNull(trip);
    }

    @Test
    public void admin() {
        // test all create, find, update and any other methods (not delete yet though)
        account();
        Integer acctID = account.getAcctID();
        groupHierarchy(acctID);
        // zones
        
        
        zones(acctID, team1Group.getGroupID());
        
        
        // devices
        devices(acctID);
        // forward commands to devices
        forwardCommands();
        // vehicles
        vehicles(team1Group.getGroupID());
        vehicles(team2Group.getGroupID());
        regionVehicles(regionGroup.getGroupID());
        // assign devices to vehicles in team1 group
        assignDevicesToVehicles(team1Group.getGroupID());
        // person
        persons(acctID, team1Group.getGroupID());
        persons(acctID, team2Group.getGroupID());
        users(team1Group.getGroupID());
        // assign manager to group
        groupManager(team1Group.getGroupID());
        // table preferences
        tablePref(team2Group.getGroupID());
        // driver
        drivers(team1Group.getGroupID());
        // person with driver, address and user
        personDeep(acctID, team1Group.getGroupID());
        // assign drivers to vehicles
        assignDriversToVehicles(team1Group.getGroupID());
        // various find methods
        find();
        // zone alert profiles
        zoneAlertProfiles(acctID, team1Group.getGroupID());
        redFlagAlertProfiles(acctID, team1Group.getGroupID());
    }

    private void redFlagAlertProfiles(Integer acctID, Integer groupID) {
        RedFlagAlertHessianDAO redFlagAlertDAO = new RedFlagAlertHessianDAO();
        redFlagAlertDAO.setSiloService(siloService);
        List<Boolean> dayOfWeek = new ArrayList<Boolean>();
        for (int i = 0; i < 7; i++)
            dayOfWeek.add(true);
        List<Integer> groupIDList = new ArrayList<Integer>();
        groupIDList.add(team1Group.getGroupID());
        List<Integer> notifyPersonIDs = new ArrayList<Integer>();
        notifyPersonIDs.add(this.personList.get(0).getPersonID());
        notifyPersonIDs.add(this.personList.get(1).getPersonID());
        Integer[] speedSettings = { 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80 };
        RedFlagLevel[] speedLevels = new RedFlagLevel[15];
        for (int i = 0; i < 15; i++)
            if (i / 2 == 0)
                speedLevels[i] = RedFlagLevel.CRITICAL;
            else
                speedLevels[i] = RedFlagLevel.INFO;
        RedFlagAlert redFlagAlert = new RedFlagAlert(acctID, "Red Flag Alert Profile", "Red Flag Alert Profile Description", 0, 1339, dayOfWeek, groupIDList,
                null, // driverIDs
                null, // vehicleIDs
                null, // vehicleTypeIDs
                notifyPersonIDs,
                null, // emailTo
                speedSettings, speedLevels, 10, 10, 10, 10, RedFlagLevel.WARNING, RedFlagLevel.WARNING, RedFlagLevel.WARNING, RedFlagLevel.WARNING, RedFlagLevel.WARNING,
                RedFlagLevel.WARNING, RedFlagLevel.WARNING, RedFlagLevel.WARNING);
        Integer redFlagAlertID = redFlagAlertDAO.create(acctID, redFlagAlert);
        assertNotNull(redFlagAlertID);
        redFlagAlert.setRedFlagAlertID(redFlagAlertID);
        // find
        String ignoreFields[] = { "modified" };
        RedFlagAlert returnedRedFlagAlert = redFlagAlertDAO.findByID(redFlagAlertID);
        Util.compareObjects(redFlagAlert, returnedRedFlagAlert, ignoreFields);
        // update
        redFlagAlert.setHardAcceleration(20);
        redFlagAlert.setHardBrake(20);
        redFlagAlert.setHardTurn(20);
        redFlagAlert.setHardVertical(20);
        redFlagAlert.setHardAccelerationLevel(RedFlagLevel.CRITICAL);
        redFlagAlert.setHardBrakeLevel(RedFlagLevel.CRITICAL);
        redFlagAlert.setHardTurnLevel(RedFlagLevel.CRITICAL);
        redFlagAlert.setHardVerticalLevel(RedFlagLevel.CRITICAL);
        redFlagAlert.setSeatBeltLevel(RedFlagLevel.CRITICAL);
        Integer[] newSpeedSettings = new Integer[15];
        RedFlagLevel[] newSpeedLevels = new RedFlagLevel[15];
        for (int i = 0; i < 15; i++) {
            newSpeedSettings[i] = 99;
            newSpeedLevels[i] = RedFlagLevel.WARNING;
        }
        redFlagAlert.setSpeedLevels(newSpeedLevels);
        redFlagAlert.setSpeedSettings(newSpeedSettings);
        Integer changedCount = redFlagAlertDAO.update(redFlagAlert);
        assertEquals("Red Flag alert update count", Integer.valueOf(1), changedCount);
        // find after update
        returnedRedFlagAlert = redFlagAlertDAO.findByID(redFlagAlertID);
        Util.compareObjects(redFlagAlert, returnedRedFlagAlert, ignoreFields);
        // delete
        Integer deletedCount = redFlagAlertDAO.deleteByID(redFlagAlertID);
        assertEquals("Red Flag alert delete count", Integer.valueOf(1), deletedCount);
        // find after delete
        returnedRedFlagAlert = redFlagAlertDAO.findByID(redFlagAlertID);
        assertEquals("Red flag alert should have deleted status after delete", Status.DELETED, returnedRedFlagAlert.getStatus());
    }

    private void zoneAlertProfiles(Integer acctID, Integer groupID) {
        ZoneHessianDAO zoneDAO = new ZoneHessianDAO();
        zoneDAO.setSiloService(siloService);
        // create a zone to use
        Zone zone = new Zone(0, acctID, Status.ACTIVE, "Zone With Alerts", "123 Street, Salt Lake City, UT 84107", groupID);
        List<LatLng> points = new ArrayList<LatLng>();
        points.add(new LatLng(40.723871753812f, -111.92932452647742f));
        points.add(new LatLng(40.704246f, -111.948613f));
        points.add(new LatLng(40.70f, -111.95f));
        points.add(new LatLng(40.723871753812f, -111.92932452647742f));
        zone.setPoints(points);
        Integer zoneID = zoneDAO.create(acctID, zone);
        assertNotNull(zoneID);
        zone.setZoneID(zoneID);
        ZoneAlertHessianDAO zoneAlertDAO = new ZoneAlertHessianDAO();
        zoneAlertDAO.setSiloService(siloService);
        List<Boolean> dayOfWeek = new ArrayList<Boolean>();
        for (int i = 0; i < 7; i++)
            dayOfWeek.add(true);
        List<Integer> groupIDList = new ArrayList<Integer>();
        groupIDList.add(team1Group.getGroupID());
        List<Integer> notifyPersonIDs = new ArrayList<Integer>();
        notifyPersonIDs.add(this.personList.get(0).getPersonID());
        notifyPersonIDs.add(this.personList.get(1).getPersonID());
        ZoneAlert zoneAlert = new ZoneAlert(acctID, "Zone Alert Profile", "Zone Alert Profile Description", 0, 1339, dayOfWeek, groupIDList, null, // driverIDs
                null, // vehicleIDs
                null, // vehicleTypeIDs
                notifyPersonIDs, null, // emailTo
                0, zoneID, true, true);
        Integer zoneAlertID = zoneAlertDAO.create(acctID, zoneAlert);
        assertNotNull(zoneAlertID);
        zoneAlert.setZoneAlertID(zoneAlertID);
        // find
        String ignoreFields[] = { "modified" };
        ZoneAlert returnedZoneAlert = zoneAlertDAO.findByID(zoneAlertID);
        Util.compareObjects(zoneAlert, returnedZoneAlert, ignoreFields);
        // update
        zoneAlert.setName("Mod Zone Alert Profile");
        zoneAlert.setDescription("Mod Zone Alert Profile Description");
        zoneAlert.setArrival(false);
        zoneAlert.setGroupIDs(new ArrayList<Integer>());
        List<VehicleType> vehicleTypeList = new ArrayList<VehicleType>();
        vehicleTypeList.add(VehicleType.LIGHT);
        Integer changedCount = zoneAlertDAO.update(zoneAlert);
        assertEquals("Zone update count", Integer.valueOf(1), changedCount);
        // find after update
        returnedZoneAlert = zoneAlertDAO.findByID(zoneAlertID);
        Util.compareObjects(zoneAlert, returnedZoneAlert, ignoreFields);
        // get by account id
        List<ZoneAlert> zoneAlertList = zoneAlertDAO.getZoneAlerts(acctID);
        assertEquals(1, zoneAlertList.size());
        Util.compareObjects(zoneAlert, zoneAlertList.get(0), ignoreFields);
        // delete
        Integer deletedCount = zoneAlertDAO.deleteByID(zoneAlertID);
        assertEquals("Red Flag alert delete count", Integer.valueOf(1), deletedCount);
        // find after delete
        returnedZoneAlert = zoneAlertDAO.findByID(zoneAlertID);
        assertEquals("Zone alert have deleted status after delete", Status.DELETED, returnedZoneAlert.getStatus());
        // mark it back to ACTIVE
        changedCount = zoneAlertDAO.update(zoneAlert);
        assertEquals("Zone update count (restore after delete)", Integer.valueOf(1), changedCount);
        // find after un-delete
        returnedZoneAlert = zoneAlertDAO.findByID(zoneAlertID);
        assertEquals("Zone alert have deleted status after delete", Status.ACTIVE, returnedZoneAlert.getStatus());
        zoneAlertDAO.deleteByZoneID(zoneID);
        returnedZoneAlert = zoneAlertDAO.findByID(zoneAlertID);
        assertEquals("Zone alert have deleted status after deletebyzoneID", Status.DELETED, returnedZoneAlert.getStatus());
    }

    private void zones(Integer acctID, Integer groupID) {
        ZoneHessianDAO zoneDAO = new ZoneHessianDAO();
        zoneDAO.setSiloService(siloService);
        Zone zone = new Zone(0, acctID, Status.ACTIVE, "Zone 1", "123 Street, Salt Lake City, UT 84107", groupID);
        List<LatLng> points = new ArrayList<LatLng>();
        points.add(new LatLng(40.723871753812f, -111.92932452647742f));
        points.add(new LatLng(40.704246f, -111.948613f));
        points.add(new LatLng(40.70f, -111.95f));
        points.add(new LatLng(40.723871753812f, -111.92932452647742f));
        zone.setPoints(points);
        // create
        Integer zoneID = zoneDAO.create(acctID, zone);
        assertNotNull(zoneID);
        zone.setZoneID(zoneID);
        // find
        String ignoreFields[] = { "created", "modified", "pointsString", "points" };
        Zone returnedZone = zoneDAO.findByID(zoneID);
        Util.compareObjects(zone, returnedZone, ignoreFields);
        // update
        zone.setName("Mod Zone 1");
        zone.setAddress("123 Street, Salt Lake City, UT 84107");
        points = new ArrayList<LatLng>();
        points.add(new LatLng(40.723871753812f, -111.92932452647742f));
        points.add(new LatLng(40.704246f, -111.948613f));
        points.add(new LatLng(40.71f, -111.93f));
        points.add(new LatLng(40.723871753812f, -111.92932452647742f));
        zone.setPoints(points);
        Integer changedCount = zoneDAO.update(zone);
        assertEquals("Zone update count", Integer.valueOf(1), changedCount);
        // find/compare after update
        returnedZone = zoneDAO.findByID(zoneID);
        Util.compareObjects(zone, returnedZone, ignoreFields);
        List<Zone> zones = zoneDAO.getZones(acctID);
        assertNotNull(zones);
        assertEquals(1, zones.size());
        Util.compareObjects(zone, zones.get(0), ignoreFields);
        // delete
        Integer deleteCount = zoneDAO.deleteByID(zoneID);
        assertEquals("Zone delte count", Integer.valueOf(1), deleteCount);
        returnedZone = zoneDAO.findByID(zoneID);
        assertEquals(Status.DELETED, returnedZone.getStatus());
    }

    private void account() {
        AccountHessianDAO accountDAO = new AccountHessianDAO();
        accountDAO.setSiloService(siloService);
        account = new Account(null, null, null, Status.ACTIVE);
        String timeStamp = Calendar.getInstance().getTime().toString();
        account.setAcctName(timeStamp);
        // create
        Integer siloID = TESTING_SILO;
        Integer acctID = accountDAO.create(siloID, account);
        assertNotNull("Create Account failed", acctID);
        account.setAcctID(acctID);
        logger.debug("CREATED ACCOUNT: " + account.getAcctID());
        // find
        String ignoreFields[] = { "modified", "unkDriverID" };  // baseID was the previous 2nd value? found on device object but?
        Account savedAccount = accountDAO.findByID(account.getAcctID());
        Util.compareObjects(account, savedAccount, ignoreFields);
        assertNotNull("Account unknownDriverID",savedAccount.getUnkDriverID());
        Address mailAddress = address(acctID);
        account.setMailID(mailAddress.getAddrID());
        Address billAddress = address(acctID);
        account.setBillID(billAddress.getAddrID());
        // update
        Integer changedCount = accountDAO.update(account);
        assertEquals("Account update count", Integer.valueOf(1), changedCount);
        savedAccount = accountDAO.findByID(account.getAcctID());
        Util.compareObjects(account, savedAccount, ignoreFields);
        List<Account> accountList = accountDAO.getAllAcctIDs();
        assertTrue(!accountList.isEmpty());
    }

    private Address address(Integer acctID) {
        AddressHessianDAO addressDAO = new AddressHessianDAO();
        addressDAO.setSiloService(siloService);
        // create
        address = new Address(null, Util.randomInt(100, 999) + " Street", null, "City " + Util.randomInt(10, 99), randomState(), "12345", acctID);
        Integer addrID = addressDAO.create(acctID, address);
        address.setAddrID(addrID);
        // find
        Address savedAddress = addressDAO.findByID(address.getAddrID());
        Util.compareObjects(address, savedAddress);
        // update
        address.setAddr1(Util.randomInt(100, 999) + " Update Street");
        address.setCity("Update City " + Util.randomInt(10, 99));
        Integer changedCount = addressDAO.update(address);
        assertEquals("Address update count", Integer.valueOf(1), changedCount);
        savedAddress = addressDAO.findByID(address.getAddrID());
        Util.compareObjects(address, savedAddress);
        return address;
    }

    private void groupHierarchy(Integer acctID) {
        GroupHessianDAO groupDAO = new GroupHessianDAO();
        groupDAO.setSiloService(siloService);
        // fleet
        fleetGroup = new Group(0, acctID, "Fleet", 0, GroupType.FLEET, null, "Fleet Group", 5, new LatLng(0.0, 0.0));
        Integer groupID = groupDAO.create(acctID, fleetGroup);
        // Integer managerID, String description, Integer mapZoom, LatLng mapCenter)
        assertNotNull(groupID);
        fleetGroup.setGroupID(groupID);
        // region
        regionGroup = new Group(0, acctID, "Region", fleetGroup.getGroupID(), GroupType.DIVISION, null, "Region Group", 5, new LatLng(0.0, 0.0));
        groupID = groupDAO.create(acctID, regionGroup);
        assertNotNull(groupID);
        regionGroup.setGroupID(groupID);
        // team
        team1Group = new Group(0, acctID, "Team", regionGroup.getGroupID(), GroupType.TEAM, null, "Team 1 Group", 5, new LatLng(0.0, 0.0));
        groupID = groupDAO.create(acctID, team1Group);
        assertNotNull(groupID);
        team1Group.setGroupID(groupID);
        team2Group = new Group(0, acctID, "Team 2", regionGroup.getGroupID(), GroupType.TEAM, null, "Team 2 Group", 5, new LatLng(0.0, 0.0));
        groupID = groupDAO.create(acctID, team2Group);
        assertNotNull(groupID);
        team2Group.setGroupID(groupID);
        // find individual
        // TODO: Should this field be ignored?
        String ignoreFields[] = { "zoneRev" };
        Group returnedGroup = groupDAO.findByID(fleetGroup.getGroupID());
        Util.compareObjects(fleetGroup, returnedGroup, ignoreFields);
        returnedGroup = groupDAO.findByID(regionGroup.getGroupID());
        Util.compareObjects(regionGroup, returnedGroup, ignoreFields);
        returnedGroup = groupDAO.findByID(team1Group.getGroupID());
        Util.compareObjects(team1Group, returnedGroup, ignoreFields);
        // find group hierarchy
        List<Group> groupList = groupDAO.getGroupHierarchy(acctID, fleetGroup.getGroupID());
        assertEquals("group list size", 4, groupList.size());
        Util.compareObjects(fleetGroup, groupList.get(0), ignoreFields);
        Util.compareObjects(regionGroup, groupList.get(1), ignoreFields);
        Group teamGroup = groupList.get(2);
        if (teamGroup.getGroupID().equals(team1Group.getGroupID())) {
            Util.compareObjects(team1Group, teamGroup, ignoreFields);
            Util.compareObjects(team2Group, groupList.get(3), ignoreFields);
        } else {
            Util.compareObjects(team2Group, teamGroup, ignoreFields);
            Util.compareObjects(team1Group, groupList.get(3), ignoreFields);
        }
        // update
        regionGroup.setName("Updated Region");
        Integer changedCount = groupDAO.update(regionGroup);
        assertEquals("Group update count", Integer.valueOf(1), changedCount);
        returnedGroup = groupDAO.findByID(regionGroup.getGroupID());
        Util.compareObjects(regionGroup, returnedGroup, ignoreFields);
        List<Group> emptyGroupList = groupDAO.getGroupHierarchy(acctID, 0);
        assertEquals("group list size", 0, emptyGroupList.size());
    }

    private void devices(Integer acctID) {
        DeviceHessianDAO deviceDAO = new DeviceHessianDAO();
        deviceDAO.setSiloService(siloService);
        List<Device> emptyDeviceList = deviceDAO.getDevicesByAcctID(acctID);
        assertEquals("no devices yet for account", 0, emptyDeviceList.size());
        // create all as new
        for (int i = 0; i < DEVICE_COUNT; i++) {
            Device device = new Device(0, acctID, DeviceStatus.NEW, "Device " + i, "IMEI " + acctID + i, "SIM " + i, 
            		// Integer.valueOf(Util.randomInt(40, 99999)).toString(),
            		"SN" + acctID + i,
                    "555555123" + i, // phone
                    "555555987" + i); // ephone
            Integer deviceID = deviceDAO.create(acctID, device);
            assertNotNull(deviceID);
            device.setDeviceID(deviceID);
            deviceList.add(device);
        }
        // duplicate imei -- should throw exception
        boolean exceptionThrown = false;
        try {
            Device dupDevice = new Device(0, acctID, DeviceStatus.NEW, "Device " + 0, "IMEI " + acctID + 0, "SIM " + 0, "SERIALNUM" + 0, "PHONE " + 0, "EPHONE " + 0);
            deviceDAO.create(acctID, dupDevice);
        } catch (DuplicateIMEIException e) {
            exceptionThrown = true;
        } catch (DuplicateEntryException ex) {
            exceptionThrown = true;
        }
        assertTrue("excepted a DuplicateException", exceptionThrown);
        // update all to activated
        for (Device device : deviceList) {
            device.setStatus(DeviceStatus.ACTIVE);
            Integer changedCount = deviceDAO.update(device);
            assertEquals("Device update count " + device.getName(), Integer.valueOf(1), changedCount);
        }
        // find
        String ignoreFields[] = { "modified", "baseID" };  
        for (Device device : deviceList) {
            Device returnedDevice = deviceDAO.findByID(device.getDeviceID());
            Util.compareObjects(device, returnedDevice, ignoreFields);
        }
        // find all
        List<Device> returnDeviceList = deviceDAO.getDevicesByAcctID(acctID);
        assertEquals("device count for account", deviceList.size(), returnDeviceList.size());
        for (Device device : deviceList) {
            boolean found = false;
            for (Device returnedDevice : returnDeviceList) {
                if (returnedDevice.getDeviceID().equals(device.getDeviceID())) {
                    Util.compareObjects(device, returnedDevice, ignoreFields);
                    found = true;
                    break;
                }
            }
            assertTrue("Not Returned in list for account Device " + device.getName(), found);
        }
        // forward commands queuing
        for (Device device : deviceList) {
            device.setHardAcceleration(10);
            device.setHardBrake(10);
            device.setHardTurn(10);
            device.setHardVertical(15);
            device.setSpeedSettings(new Integer[] { 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10 });
            device.setEphone("5555559999");
            device.setAutoLogoff(AutoLogoff.ON);
            Integer changedCount = deviceDAO.update(device);
            assertEquals("Device update count " + device.getName(), Integer.valueOf(1), changedCount);
            List<ForwardCommand> fwdCmdQueue = deviceDAO.getForwardCommands(device.getDeviceID(), ForwardCommandStatus.STATUS_QUEUED);
            assertEquals("expected 17 forward commands to be queued for device: " + device.getDeviceID(), 17, fwdCmdQueue.size());
        }
    }

    private void forwardCommands() {
        DeviceHessianDAO deviceDAO = new DeviceHessianDAO();
        deviceDAO.setSiloService(siloService);
        Device device = deviceList.get(0);
        List<ForwardCommand> origiinalQueuedCommands = deviceDAO.getForwardCommands(device.getDeviceID(), ForwardCommandStatus.STATUS_QUEUED);
        Integer initialQueueSize = origiinalQueuedCommands.size();
        ForwardCommand stringDataCmd = new ForwardCommand(0, ForwardCommandID.SET_GPRS_APN, "555555123" + Util.randomInt(1, 9), ForwardCommandStatus.STATUS_QUEUED);
        ForwardCommand intDataCmd = new ForwardCommand(0, ForwardCommandID.SET_MSGS_PER_NOTIFICATION, Util.randomInt(1, 4), ForwardCommandStatus.STATUS_QUEUED);
        ForwardCommand noDataCmd = new ForwardCommand(0, ForwardCommandID.BUZZER_SEATBELT_DISABLE, 0, ForwardCommandStatus.STATUS_QUEUED);
        deviceDAO.queueForwardCommand(device.getDeviceID(), stringDataCmd);
        deviceDAO.queueForwardCommand(device.getDeviceID(), intDataCmd);
        deviceDAO.queueForwardCommand(device.getDeviceID(), noDataCmd);
        List<ForwardCommand> queuedCommands = deviceDAO.getForwardCommands(device.getDeviceID(), ForwardCommandStatus.STATUS_QUEUED);
        assertEquals("queued forward commands", 3 + initialQueueSize, queuedCommands.size());
        String ignoreFields[] = { "modified", "fwdID" };
        for (ForwardCommand forwardCommand : queuedCommands) {
            if (forwardCommand.getCmd().equals(ForwardCommandID.SET_GPRS_APN)) {
                Util.compareObjects(stringDataCmd, forwardCommand, ignoreFields);
            } else if (forwardCommand.getCmd().equals(ForwardCommandID.SET_MSGS_PER_NOTIFICATION)) {
                Util.compareObjects(intDataCmd, forwardCommand, ignoreFields);
            } else if (forwardCommand.getCmd().equals(ForwardCommandID.BUZZER_SEATBELT_DISABLE)) {
                Util.compareObjects(noDataCmd, forwardCommand, ignoreFields);
            }
        }
    }

    private void vehicles(Integer groupID) {
        VehicleHessianDAO vehicleDAO = new VehicleHessianDAO();
        vehicleDAO.setSiloService(siloService);
        List<Vehicle> emptyVehicleList = vehicleDAO.getVehiclesInGroupHierarchy(groupID);
        assertEquals("vehicle list should be empty", Integer.valueOf(0), new Integer(emptyVehicleList.size()));
        // create
        for (int i = 0; i < VEHICLE_COUNT; i++) {
            Vehicle vehicle = new Vehicle(0, groupID, 10, Status.INACTIVE, "Vehicle " + i, "Make " + i, "Model " + i, 2000 + i, "COLOR " + i, VehicleType.valueOf(Util.randomInt(0,
                    VehicleType.values().length - 1)), "VIN_" + groupID + "_" + i, 1000, "License " + i, randomState());
            Integer vehicleID = vehicleDAO.create(groupID, vehicle);
            assertNotNull(vehicleID);
            vehicle.setVehicleID(vehicleID);
            vehicleList.add(vehicle);
            // get last loc (should be empty);
            LastLocation loc = vehicleDAO.getLastLocation(vehicle.getVehicleID());
            assertNull("no location expected for new vehicle", loc);
        }
        // update all to activated
        for (Vehicle vehicle : vehicleList) {
            if (vehicle.getGroupID().equals(groupID)) {
                vehicle.setStatus(Status.ACTIVE);
                Integer changedCount = vehicleDAO.update(vehicle);
                assertEquals("Vehicle update count " + vehicle.getName(), Integer.valueOf(1), changedCount);
            }
        }
        // TODO: find out if we need this field (costPerHour) -- not in the backend
        String ignoreFields[] = { "costPerHour", "modified" };
        for (Vehicle vehicle : vehicleList) {
            if (vehicle.getGroupID().equals(groupID)) {
                Vehicle returnedVehicle = vehicleDAO.findByID(vehicle.getVehicleID());
                Util.compareObjects(vehicle, returnedVehicle, ignoreFields);
            }
        }
        // find all
        List<Vehicle> groupVehicleList = vehicleDAO.getVehiclesInGroupHierarchy(groupID);
        assertEquals("vehicle count for group", Integer.valueOf(VEHICLE_COUNT), new Integer(groupVehicleList.size()));
        for (Vehicle vehicle : vehicleList) {
            if (vehicle.getGroupID().equals(groupID)) {
                boolean found = false;
                for (Vehicle groupVehicle : groupVehicleList) {
                    if (vehicle.getVehicleID().equals(groupVehicle.getVehicleID())) {
                        Util.compareObjects(vehicle, groupVehicle, ignoreFields);
                        found = true;
                        break;
                    }
                }
                assertTrue("vehicle " + vehicle.getName(), found);
            }
        }
    }

    private void regionVehicles(Integer groupID) {
        VehicleHessianDAO vehicleDAO = new VehicleHessianDAO();
        vehicleDAO.setSiloService(siloService);
        // create
        for (int i = 0; i < VEHICLE_COUNT; i++) {
            Vehicle vehicle = new Vehicle(0, groupID, 10, Status.ACTIVE, "Vehicle " + i, "Make " + i, "Model " + i, 2000 + i, "COLOR " + i, VehicleType.valueOf(Util.randomInt(0,
                    VehicleType.values().length - 1)), "VIN_" + groupID + "_" + i, 1000, "License " + i, randomState());
            Integer vehicleID = vehicleDAO.create(groupID, vehicle);
            assertNotNull(vehicleID);
            vehicle.setVehicleID(vehicleID);
            vehicleList.add(vehicle);
        }
        List<Vehicle> groupVehicleList = vehicleDAO.getVehiclesInGroupHierarchy(groupID);
        assertEquals("vehicle count for region group", Integer.valueOf(VEHICLE_COUNT * 3), new Integer(groupVehicleList.size()));
    }

    private void persons(Integer acctID, Integer groupID) {
        logger.debug("Persons GroupID: " + groupID);
        PersonHessianDAO personDAO = new PersonHessianDAO();
        personDAO.setSiloService(siloService);
        UserHessianDAO userDAO = new UserHessianDAO();
        userDAO.setSiloService(siloService);
        List<Person> emptyPersonList = personDAO.getPeopleInGroupHierarchy(groupID);
        assertEquals("expected no people in group", Integer.valueOf(0), new Integer(emptyPersonList.size()));
        // create
        for (int i = 0; i < PERSON_COUNT; i++) {
            Date dob = Util.genDate(1959, 8, 30);
            String email = "email_" + groupID + "_" + i + "@yahoo.com";
            Person person = new Person(0, acctID, TimeZone.getDefault(), null, address.getAddrID(), email, null, "555555555" + i, "555555555" + i, null, null, null, null, null,
                    "emp" + i, null, "title" + i, "dept" + i, "first" + i, "m" + i, "last" + i, "jr", Gender.MALE, 65, 180, dob, Status.ACTIVE, MeasurementType.ENGLISH,
                    FuelEfficiencyType.MPG_US, Locale.getDefault());
            User user = new User(0, 0, randomRole(), Status.ACTIVE, "user" + groupID + "_" + i, PASSWORD, groupID);
            person.setUser(user);
            Integer personID = personDAO.create(acctID, person);
            assertNotNull(personID);
            person.setPersonID(personID);
            person.setAddress(address);
            personList.add(person);
            // update
            Role newRole = randomRole();
            user.setRole(newRole);
            Integer changedCount = userDAO.update(user);
            assertEquals("user update count " + user.getUserID(), Integer.valueOf(1), changedCount);
            // find user by ID
            String ignoreFields[] = { "modified", "person" };
            User returnedUser = userDAO.findByID(user.getUserID());
            Util.compareObjects(user, returnedUser, ignoreFields);
            returnedUser = userDAO.getUserByPersonID(person.getPersonID());
            // TODO this needs to be fixed
            Util.compareObjects(user, returnedUser, ignoreFields);
            userList.add(user);

            reportSchedules(acctID, person.getUser().getUserID(), groupID);

        }
        // update all to female
        for (Person person : personList) {
            person.setGender(Gender.FEMALE);
            person.setMiddle("middle");
            Integer changedCount = personDAO.update(person);
            assertEquals("Person update count " + person.getPersonID(), Integer.valueOf(1), changedCount);
        }
        String ignoreFields[] = { "modified", "costPerHour" };
        for (Person person : personList) {
            Person returnedPerson = personDAO.findByID(person.getPersonID());
            Util.compareObjects(person, returnedPerson, ignoreFields);
        }
        // find all
        List<Person> groupPersonList = personDAO.getPeopleInGroupHierarchy(groupID);
        assertEquals("people count for group", Integer.valueOf(PERSON_COUNT), new Integer(groupPersonList.size()));
        String ignoreFields2[] = { "modified", "costPerHour", "user", "driver"};
        for (Person person : personList) {
            for (Person groupPerson : groupPersonList) {
                if (groupPerson.getPersonID().equals(person.getPersonID())) {
                    Util.compareObjects(person, groupPerson, ignoreFields2);
                    break;
                }
            }
        }
    }

    private void reportSchedules(Integer acctID, Integer userID, Integer groupID) {
    	
        ReportScheduleHessianDAO reportScheduleHessianDAO = new ReportScheduleHessianDAO();

        reportScheduleHessianDAO.setSiloService(siloService);
        
        // Weekly report create
        ReportSchedule reportScheduleWeekly = new ReportSchedule();
        reportScheduleWeekly.setReportDuration(Duration.DAYS);
        reportScheduleWeekly.setStatus(Status.ACTIVE);
        reportScheduleWeekly.setReportID(0);
        reportScheduleWeekly.setName("Report Schedule");
        reportScheduleWeekly.setOccurrence(Occurrence.WEEKLY);
        reportScheduleWeekly.setUserID(userID);
        reportScheduleWeekly.setGroupID(groupID);
        reportScheduleWeekly.setReportID(1);
        reportScheduleWeekly.setAccountID(acctID);
        
        Calendar lastDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        lastDate.set(Calendar.HOUR, 0);
        lastDate.set(Calendar.MINUTE, 0);
        lastDate.set(Calendar.SECOND, 0);
        lastDate.set(Calendar.MILLISECOND, 0);
        lastDate.add(Calendar.DATE, -7);
        
        Calendar endDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        endDate.set(Calendar.HOUR, 0);
        endDate.set(Calendar.MINUTE, 0);
        endDate.set(Calendar.SECOND, 0);
        endDate.set(Calendar.MILLISECOND, 0);
        
        reportScheduleWeekly.setLastDate(lastDate.getTime());
        reportScheduleWeekly.setEndDate(endDate.getTime());
        reportScheduleWeekly.setStartDate(lastDate.getTime());
        
        List<String> emailList = new ArrayList<String>();
        emailList.add("foo@inthinc.com");
        emailList.add("bar@inthinc.com");
        emailList.add("baz@inthinc.com");
        reportScheduleWeekly.setEmailTo(emailList);
        
        List<Boolean> booleanList = new ArrayList<Boolean>();
        booleanList.add(Boolean.FALSE);
        booleanList.add(Boolean.FALSE);
        booleanList.add(Boolean.TRUE);
        booleanList.add(Boolean.FALSE);
        booleanList.add(Boolean.FALSE);
        booleanList.add(Boolean.FALSE);
        booleanList.add(Boolean.FALSE);
        reportScheduleWeekly.setDayOfWeek(booleanList);
        
        Integer weeklyId = reportScheduleHessianDAO.create(acctID, reportScheduleWeekly);
        logger.debug("Report Schedule ID: " + weeklyId);
        logger.debug("Report Schedule acctID: " + acctID);
        assertNotNull(weeklyId);
        reportScheduleWeekly.setReportScheduleID(weeklyId);
        
        // Daily report create
        ReportSchedule reportScheduleDaily = new ReportSchedule();
        reportScheduleDaily.setReportDuration(Duration.DAYS); 
        reportScheduleDaily.setStatus(Status.ACTIVE);  
        reportScheduleDaily.setName("Report Schedule 1");  
        reportScheduleDaily.setOccurrence(Occurrence.DAILY); 
        reportScheduleDaily.setUserID(userID); 
        reportScheduleDaily.setGroupID(groupID);  
        reportScheduleDaily.setReportID(10);    
        reportScheduleDaily.setAccountID(acctID);      
        
        reportScheduleDaily.setLastDate(lastDate.getTime()); 
        reportScheduleDaily.setEndDate(endDate.getTime());  
        reportScheduleDaily.setStartDate(lastDate.getTime());  
        
        reportScheduleDaily.setEmailTo(emailList);      
        
        reportScheduleDaily.setDayOfWeek(booleanList);  
        
        Integer dailyId = reportScheduleHessianDAO.create(acctID, reportScheduleDaily);
        logger.debug("Report Schedule ID: " + dailyId);
        logger.debug("Report Schedule acctID: " + acctID);        
        assertNotNull(dailyId);
        reportScheduleDaily.setReportScheduleID(dailyId);  
        
        // Monthly report create
        ReportSchedule reportScheduleMonthly = new ReportSchedule();
        reportScheduleMonthly.setReportDuration(Duration.DAYS); 
        reportScheduleMonthly.setStatus(Status.ACTIVE);  
        reportScheduleMonthly.setName("Report Schedule 2");  
        reportScheduleMonthly.setOccurrence(Occurrence.MONTHLY); 
        reportScheduleMonthly.setUserID(userID); 
        reportScheduleMonthly.setGroupID(groupID);  
        reportScheduleMonthly.setReportID(10);    
        reportScheduleMonthly.setAccountID(acctID);      
        
        reportScheduleMonthly.setLastDate(lastDate.getTime()); 
        reportScheduleMonthly.setEndDate(endDate.getTime());  
        reportScheduleMonthly.setStartDate(lastDate.getTime());  
        
        reportScheduleMonthly.setEmailTo(emailList);      
        
        reportScheduleMonthly.setDayOfWeek(booleanList);  
        
        Integer monthlyId = reportScheduleHessianDAO.create(acctID, reportScheduleMonthly);
        logger.debug("Report Schedule ID: " + monthlyId);
        logger.debug("Report Schedule acctID: " + acctID);        
        assertNotNull(monthlyId);
        reportScheduleMonthly.setReportScheduleID(monthlyId);
        
        // Retrieve all
        List<ReportSchedule> reportSchedules = reportScheduleHessianDAO.getReportSchedulesByAccountID(acctID);
        
        // Found some?
        assertTrue("Report schedule list for " + acctID, reportSchedules.size() > 0);   
        
        // One of each?
        assertTrue(checkTypes("daily",reportSchedules));
        assertTrue(checkTypes("weekly",reportSchedules));
        assertTrue(checkTypes("monthly",reportSchedules));
        
        // find 
        // TODO: Check on startDate, endDate (they are 12 hours off)
        String[] ignoreFields = { "modified", 
        		"emailToString",	// list might be in different order (could extract and test)
        		"endDate",			// not sure why this is different
        		"startDate" };		// not sure why this is different

        ReportSchedule reportSchedule = reportScheduleHessianDAO.findByID(weeklyId);
        Util.compareObjects(reportScheduleWeekly, reportSchedule, ignoreFields);

//        System.out.println("startDateDiff = " + (reportSchedule.getStartDate().getTime() - reportScheduleWeekly.getStartDate().getTime()));
//        System.out.println("endDateDiff = " + (reportSchedule.getEndDate().getTime() - reportScheduleWeekly.getEndDate().getTime()));
        // update
        reportScheduleWeekly.setName("Weekly Updated");
        reportScheduleHessianDAO.update(reportScheduleWeekly);

        reportSchedule = reportScheduleHessianDAO.findByID(weeklyId);
        Util.compareObjects(reportScheduleWeekly, reportSchedule, ignoreFields);

        // get by userID
        List<ReportSchedule> reportSchedulesByUser = reportScheduleHessianDAO.getReportSchedulesByUserID(userID);
        assertEquals("report schedules for userID: " + userID, 3, reportSchedulesByUser.size());   

        
        // delete
        reportScheduleHessianDAO.deleteByID(reportScheduleWeekly.getReportScheduleID());
        reportSchedulesByUser = reportScheduleHessianDAO.getReportSchedulesByUserID(userID);
        assertEquals("after delete report schedules for userID: " + userID, 2, reportSchedulesByUser.size());   
    }
    
    private boolean checkTypes(String type,List<ReportSchedule> reports) {
        
        for ( ReportSchedule rp: reports ) {
            if ( rp.getOccurrence().getDescription().equalsIgnoreCase(type)) {
                return true;
            }
        }
        
        return false;
    }

    private void groupManager(Integer groupID) {
        // try to assign the person as the managerID of a group
        Person manager = personList.get(0);
        GroupHessianDAO groupHessianDAO = new GroupHessianDAO();
        groupHessianDAO.setSiloService(siloService);
        Group group = groupHessianDAO.findByID(groupID);
        group.setManagerID(manager.getPersonID());
        groupHessianDAO.update(group);
        Group returnedGroup = groupHessianDAO.findByID(groupID);
        Util.compareObjects(group, returnedGroup);
    }

    private void users(Integer groupID) {
        UserHessianDAO userDAO = new UserHessianDAO();
        userDAO.setSiloService(siloService);
        PersonHessianDAO personDAO = new PersonHessianDAO();
        personDAO.setSiloService(siloService);
        List<Person> groupPersonList = personDAO.getPeopleInGroupHierarchy(groupID);
        assertEquals("people count for group", Integer.valueOf(PERSON_COUNT), new Integer(groupPersonList.size()));
        // get all for group
        List<User> groupUserList = userDAO.getUsersInGroupHierarchy(groupID);
        assertEquals("number of users in group: " + groupID, groupPersonList.size(), groupUserList.size());
        // delete all
        for (User user : groupUserList) {
            Integer changedCount = userDAO.deleteByID(user.getUserID());
            assertEquals("User delete count " + user.getUserID(), Integer.valueOf(1), changedCount);
        }
        // user list should be empty
        groupUserList = userDAO.getUsersInGroupHierarchy(groupID);
        assertEquals("number of users in group: " + groupID, 0, groupUserList.size());
        // restore all
        for (User user : userList) {
            user.setStatus(Status.ACTIVE);
            Integer changedCount = userDAO.update(user);
            assertEquals("User update count " + user.getUserID(), Integer.valueOf(1), changedCount);
        }
        // user list should be same
        groupUserList = userDAO.getUsersInGroupHierarchy(groupID);
        assertEquals("number of users in group: " + groupID, groupPersonList.size(), groupUserList.size());
    }

    private void tablePref(Integer groupID) {
        UserHessianDAO userDAO = new UserHessianDAO();
        userDAO.setSiloService(siloService);
        TablePreferenceHessianDAO tablePreferenceDAO = new TablePreferenceHessianDAO();
        tablePreferenceDAO.setSiloService(siloService);
        List<User> groupUserList = userDAO.getUsersInGroupHierarchy(groupID);
        // just use the first User
        Integer userID = groupUserList.get(0).getUserID();
        List<Boolean> visible = new ArrayList<Boolean>();
        visible.add(true);
        visible.add(true);
        visible.add(false);
        visible.add(false);
        TablePreference tablePref = new TablePreference(0, userID, TableType.RED_FLAG, visible);
        List<TablePreference> tablePrefList = tablePreferenceDAO.getTablePreferencesByUserID(userID);
        assertEquals("no table preferences should exist yet", 0, tablePrefList.size());
        // create
        Integer tablePrefID = tablePreferenceDAO.create(userID, tablePref);
        tablePref.setTablePrefID(tablePrefID);
        assertNotNull("Table Preference", tablePrefID);
        // get
        TablePreference returnedTablePref = tablePreferenceDAO.findByID(tablePrefID);
        String[] ignoreFields = { "flags" };
        Util.compareObjects(tablePref, returnedTablePref, ignoreFields);
        // update
        visible.set(2, true);
        visible.set(3, true);
        tablePref.setVisible(visible);
        Integer changedCount = tablePreferenceDAO.update(tablePref);
        assertEquals("Table Preference Update", Integer.valueOf(1), changedCount);
        returnedTablePref = tablePreferenceDAO.findByID(tablePrefID);
        Util.compareObjects(tablePref, returnedTablePref, ignoreFields);
        tablePrefList = tablePreferenceDAO.getTablePreferencesByUserID(userID);
        assertEquals("1 table preferences should exist", 1, tablePrefList.size());
        Util.compareObjects(tablePref, tablePrefList.get(0), ignoreFields);
    }

    private void drivers(Integer groupID) {
        DriverHessianDAO driverDAO = new DriverHessianDAO();
        driverDAO.setSiloService(siloService);
        PersonHessianDAO personDAO = new PersonHessianDAO();
        personDAO.setSiloService(siloService);
        // year time frame from today back
        Date endDate = new Date();
        Date startDate = DateUtil.getDaysBackDate(endDate, 365);
        List<Person> groupPersonList = personDAO.getPeopleInGroupHierarchy(groupID);
        assertEquals("people count for group", Integer.valueOf(PERSON_COUNT), new Integer(groupPersonList.size()));
        String ignoreFields[] = { "modified", "person" };
        for (Person person : groupPersonList) {
            Date expired = Util.genDate(2010, 9, 30);
            Driver driver = new Driver(0, person.getPersonID(), Status.ACTIVE, null, "l" + person.getPersonID(), randomState(), "ABCD", expired, null, null, groupID);
            // create
            Integer driverID = driverDAO.create(person.getPersonID(), driver);
            assertNotNull("driver", driverID);
            driver.setDriverID(driverID);
            // update
            long rfid = 200l + person.getPersonID().longValue();
            driver.setRFID(rfid);
            Integer changedCount = driverDAO.update(driver);
            assertEquals("Driver update count " + driver.getDriverID(), Integer.valueOf(1), changedCount);
            // find Driver by ID
            Driver returnedDriver = driverDAO.findByID(driver.getDriverID());
            Util.compareObjects(driver, returnedDriver, ignoreFields);
            // find by RFID
            Integer returnedDriverID = driverDAO.getDriverIDForRFID(rfid);
            assertEquals("getDriverForRFID returned incorrect driver", driver.getDriverID(), returnedDriverID);
            
            // find by PersonID
            returnedDriver = driverDAO.findByPersonID(person.getPersonID());
            Util.compareObjects(driver, returnedDriver, ignoreFields);
            driverList.add(driver);
            // get last loc (should be empty);
            LastLocation loc = driverDAO.getLastLocation(driver.getDriverID());
            assertNull("no location expected for new driver", loc);
            // trips list should be empty
            List<Trip> emptyTripList = driverDAO.getTrips(driver.getDriverID(), startDate, endDate);
            assertNotNull(emptyTripList);
            assertTrue(emptyTripList.size() == 0);
        }
        // get all for group (deep)
        List<Driver> groupDriverList = driverDAO.getAllDrivers(groupID);
        assertEquals("number of drivers in group: " + groupID, groupPersonList.size(), groupDriverList.size());
        // get all for group (not deep) -- should be same as deep list in this case
        List<Driver> groupOnlyDriverList = driverDAO.getDrivers(groupID);
        assertEquals("number of drivers in group only list: " + groupID, groupPersonList.size(), groupOnlyDriverList.size());
        // delete all
        for (Driver driver : groupDriverList) {
            Integer changedCount = driverDAO.deleteByID(driver.getDriverID());
            assertEquals("User delete count " + driver.getDriverID(), Integer.valueOf(1), changedCount);
        }
        // driver list should be empty
        groupDriverList = driverDAO.getAllDrivers(groupID);
        assertEquals("number of drivers in group: " + groupID, 0, groupDriverList.size());
        // restore all
        for (Driver driver : driverList) {
            driver.setStatus(Status.ACTIVE);
            Integer changedCount = driverDAO.update(driver);
            assertEquals("User update count " + driver.getDriverID(), Integer.valueOf(1), changedCount);
        }
        // driver list should be same
        groupDriverList = driverDAO.getAllDrivers(groupID);
        assertEquals("number of drivers in group: " + groupID, groupPersonList.size(), groupDriverList.size());
    }

    private void assignDevicesToVehicles(Integer groupID) {
        VehicleHessianDAO vehicleDAO = new VehicleHessianDAO();
        vehicleDAO.setSiloService(siloService);
        DeviceHessianDAO deviceDAO = new DeviceHessianDAO();
        deviceDAO.setSiloService(siloService);
        vehicleDAO.setDeviceDAO(deviceDAO);
        List<Vehicle> groupVehicles = vehicleDAO.getVehiclesInGroupHierarchy(groupID);
        assertEquals(Integer.valueOf(VEHICLE_COUNT), Integer.valueOf(groupVehicles.size()));
        int deviceIdx = 0;
        for (Vehicle vehicle : groupVehicles) {
            Device device = deviceList.get(deviceIdx++);
            Integer deviceID = device.getDeviceID();
            vehicleDAO.setVehicleDevice(vehicle.getVehicleID(), deviceID);
            Vehicle returnedVehicle = vehicleDAO.findByID(vehicle.getVehicleID());
            assertEquals("setVehicleDevice failed", deviceID, returnedVehicle.getDeviceID());
            // update the lists that are kept globally
            device.setVehicleID(vehicle.getVehicleID());
            for (Vehicle lVehicle : vehicleList) {
                if (lVehicle.getVehicleID().equals(vehicle.getVehicleID())) {
                    lVehicle.setDeviceID(deviceID);
                }
            }
            logger.debug(vehicle.getVehicleID() + " assigned to " + deviceID);
        }
    }

    private void assignDriversToVehicles(Integer groupID) {
        VehicleHessianDAO vehicleDAO = new VehicleHessianDAO();
        vehicleDAO.setSiloService(siloService);
        List<Vehicle> groupVehicles = vehicleDAO.getVehiclesInGroupHierarchy(groupID);
        assertEquals(Integer.valueOf(VEHICLE_COUNT), Integer.valueOf(groupVehicles.size()));
        int driverIdx = 0;
        for (Vehicle vehicle : groupVehicles) {
            Driver driver = driverList.get(driverIdx++);
            Integer driverID = driver.getDriverID();
            vehicleDAO.setVehicleDriver(vehicle.getVehicleID(), driverID);
            Vehicle returnedVehicle = vehicleDAO.findByID(vehicle.getVehicleID());
            assertEquals("setVehicleDriver failed", driverID, returnedVehicle.getDriverID());
            // update the lists that are kept globally
            // driver.setVehicleID(vehicle.getVehicleID());
            for (Vehicle lVehicle : vehicleList) {
                if (lVehicle.getVehicleID().equals(vehicle.getVehicleID())) {
                    lVehicle.setDriverID(driverID);
                }
            }
        }
    }

    private void personDeep(Integer acctID, Integer groupID) {
        PersonHessianDAO personDAO = new PersonHessianDAO();
        personDAO.setSiloService(siloService);
        Date expired = Util.genDate(2010, 8, 30);
        Address address = new Address(null, Util.randomInt(100, 999) + " Street", null, "City " + Util.randomInt(10, 99), randomState(), "12345", acctID);
        Driver driver = new Driver(0, 0, Status.ACTIVE, null, "l" + groupID, randomState(), "ABCD", expired, null, null, groupID);
        User user = new User(0, 0, randomRole(), Status.ACTIVE, "deepuser_" + groupID, PASSWORD, groupID);
        Date dob = Util.genDate(1959, 8, 30);
        Person person = new Person(0, acctID, TimeZone.getDefault(), null, address.getAddrID(), "priEmail" + groupID + "@test.com", "secEmail@test.com", "8015551111",
                "8015552222", "8015554444@texter.com", "8015555555@texter.com", 1, 2, 3, "emp" + groupID, null, "title" + groupID, "dept" + groupID, "first" + groupID, "m"
                        + groupID, "last" + groupID, "jr", Gender.MALE, 65, 180, dob, Status.ACTIVE, MeasurementType.ENGLISH, FuelEfficiencyType.MPG_US, Locale.getDefault());
        person.setUser(user);
        person.setDriver(driver);
        person.setAddress(address);
        Integer personID = personDAO.create(acctID, person);
        person.setPersonID(personID);
        assertNotNull(personID);
        assertNotNull("addressID not set", person.getAddressID());
        assertNotNull("userID not set", person.getUser().getUserID());
        assertNotNull("driverID not set", person.getDriver().getDriverID());
        assertNotNull("personID in user not set", person.getUser().getPersonID());
        assertNotNull("personID in driver not set", person.getDriver().getPersonID());
        Person returnPerson = personDAO.findByID(personID);
        String[] ignoreFields = { "modified", "measurementType" };
        Util.compareObjects(person, returnPerson, ignoreFields);
        // TODO: This did not work
        // personDAO.deleteByID(personID);
        // Person delPerson = personDAO.findByID(personID);
        // assertNull(delPerson);
    }

    private void find() {
        // do these last to allow back end more time to update it's cache (can take up to 5 min)
        PersonHessianDAO personDAO = new PersonHessianDAO();
        personDAO.setSiloService(siloService);
        findByKey(personDAO, personList.get(0), personList.get(0).getPriEmail(), new String[] { "modified", "address", "driver", "user", "measurementType", "driverID", "userID" });
        findByKeyExpectNoResult(personDAO, "BAD_EMAIL");
        UserHessianDAO userDAO = new UserHessianDAO();
        userDAO.setSiloService(siloService);
        findByKey(userDAO, userList.get(0), userList.get(0).getUsername(), new String[] { "modified", "person" });
        findByKeyExpectNoResult(userDAO, "BAD_USER");
        DeviceHessianDAO deviceDAO = new DeviceHessianDAO();
        deviceDAO.setSiloService(siloService);
        findByKey(deviceDAO, deviceList.get(0), deviceList.get(0).getImei(), new String[] { "modified", "baseID" });
        findByKeyExpectNoResult(deviceDAO, "BAD_DEVICE");
        VehicleHessianDAO vehicleDAO = new VehicleHessianDAO();
        vehicleDAO.setSiloService(siloService);
        findByKey(vehicleDAO, vehicleList.get(0), vehicleList.get(0).getVIN(), new String[] { "modified", "costPerHour" });
        findByKeyExpectNoResult(vehicleDAO, "BAD_VEHICLE");
    }

    private <T> void findByKeyExpectNoResult(FindByKey<T> dao, String keyField) {
        T foundObject = dao.findByKey(keyField);
        assertNull("expected a null result", foundObject);
    }

    private <T> void findByKey(FindByKey<T> dao, T objectToFind, String keyField, String[] ignoreFields) {
        T foundObject = null;
        for (int i = 0; i < 300; i++) {
            foundObject = dao.findByKey(keyField);
            if (foundObject == null) {
                if (i == 299) {
                    fail("findByKey " + keyField + " failed even after waiting 5 min");
                }
                try {
                    Thread.sleep(1000l);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            } else {
                break;
            }
        }
        Util.compareObjects(objectToFind, foundObject, ignoreFields);
    }

    private State randomState() {
        int idx = Util.randomInt(0, States.getStates().size() - 1);
        int cnt = 0;
        for (State state : States.getStates().values()) {
            if (cnt++ == idx)
                return state;
        }
        return null;
    }

    private Role randomRole() {
        int idx = Util.randomInt(0, Roles.getRoleMap().size() - 1);
        int cnt = 0;
        for (Role role : Roles.getRoleMap().values()) {
            if (cnt++ == idx)
                return role;
        }
        return null;
    }
}
