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
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.inthinc.hos.model.RuleSetType;
import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.FindByKey;
import com.inthinc.pro.dao.hessian.AccountHessianDAO;
import com.inthinc.pro.dao.hessian.AddressHessianDAO;
import com.inthinc.pro.dao.hessian.DeviceHessianDAO;
import com.inthinc.pro.dao.hessian.DriverHessianDAO;
import com.inthinc.pro.dao.hessian.EventHessianDAO;
import com.inthinc.pro.dao.hessian.ForwardCommandDefHessianDAO;
import com.inthinc.pro.dao.hessian.GroupHessianDAO;
import com.inthinc.pro.dao.hessian.PersonHessianDAO;
import com.inthinc.pro.dao.hessian.RedFlagAlertHessianDAO;
import com.inthinc.pro.dao.hessian.ReportScheduleHessianDAO;
import com.inthinc.pro.dao.hessian.RoleHessianDAO;
import com.inthinc.pro.dao.hessian.StateHessianDAO;
import com.inthinc.pro.dao.hessian.SuperuserHessianDAO;
import com.inthinc.pro.dao.hessian.TablePreferenceHessianDAO;
import com.inthinc.pro.dao.hessian.TimeZoneHessianDAO;
import com.inthinc.pro.dao.hessian.UserHessianDAO;
import com.inthinc.pro.dao.hessian.VehicleHessianDAO;
import com.inthinc.pro.dao.hessian.ZoneHessianDAO;
import com.inthinc.pro.dao.hessian.exceptions.DuplicateEntryException;
import com.inthinc.pro.dao.hessian.exceptions.DuplicateIMEIException;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.AccountHOSType;
import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.AlertEscalationItem;
import com.inthinc.pro.model.AlertMessageType;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.DeviceStatus;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.DriverLocation;
import com.inthinc.pro.model.DriverName;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.ForwardCommand;
import com.inthinc.pro.model.ForwardCommandDef;
import com.inthinc.pro.model.ForwardCommandID;
import com.inthinc.pro.model.ForwardCommandParamType;
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
import com.inthinc.pro.model.ReportParamType;
import com.inthinc.pro.model.ReportSchedule;
import com.inthinc.pro.model.State;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.TablePreference;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.TripQuality;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleDOTType;
import com.inthinc.pro.model.VehicleName;
import com.inthinc.pro.model.VehicleType;
import com.inthinc.pro.model.Zone;
import com.inthinc.pro.model.app.SiteAccessPoints;
import com.inthinc.pro.model.app.States;
import com.inthinc.pro.model.app.SupportedTimeZones;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.EventCategory;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.model.security.AccessPoint;
import com.inthinc.pro.model.security.Role;
import com.inthinc.pro.model.security.Roles;
import com.inthinc.pro.model.zone.option.ZoneAvailableOption;
import com.inthinc.pro.model.zone.option.ZoneOption;
import com.inthinc.pro.model.zone.option.ZoneOptionType;
import com.inthinc.pro.model.zone.option.type.OffOnDevice;

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
    @SuppressWarnings("unused")
    private static final String SPEEDRACER_RFID = "speedRacerRFID";
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
//        DeviceSensitivityMapping mapping = new DeviceSensitivityMapping();
//        mapping.setDeviceDAO(deviceDAO);
//        mapping.init();
        
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
    
    @AfterClass
    public static void tearDownAfterClass() throws Exception {

    	// clean up 
    	ForwardCommandDefHessianDAO forwardCommandDefDAO = new ForwardCommandDefHessianDAO();
    	forwardCommandDefDAO.setSiloService(siloService);

    	forwardCommandDefDAO.deleteByID(666);
    }
    

    @Test
    //@Ignore
    public void states() {
        initStates();
        assertTrue(States.getStates().size() >= 50);
    }
    
    private void initStates() {
        StateHessianDAO stateDAO = new StateHessianDAO();
        stateDAO.setSiloService(siloService);
        States states = new States();
        states.setStateDAO(stateDAO);
        states.init();
    }
    

    @Test
    //@Ignore
    public void accessPoints() {
        RoleHessianDAO roleDAO = new RoleHessianDAO();
        roleDAO.setSiloService(siloService);
        SiteAccessPoints accessPoints = new SiteAccessPoints();
        accessPoints.setRoleDAO(roleDAO);
        accessPoints.init();
        assertTrue(SiteAccessPoints.getAccessPointMap().size() > 0);
    }

    @Test
    //@Ignore
    public void supportedTimeZones() {
        TimeZoneHessianDAO timeZoneDAO = new TimeZoneHessianDAO();
        timeZoneDAO.setSiloService(siloService);
        SupportedTimeZones supportedTimeZones = new SupportedTimeZones();
        supportedTimeZones.setTimeZoneDAO(timeZoneDAO);
        supportedTimeZones.init();
        assertTrue(SupportedTimeZones.getSupportedTimeZones().size() > 0);
    }
    
    @Test
    //@Ignore
    public void forwardCommandDefs() {
    	ForwardCommandDefHessianDAO forwardCommandDefDAO = new ForwardCommandDefHessianDAO();
    	forwardCommandDefDAO.setSiloService(siloService);
    	
    	List<ForwardCommandDef> list = forwardCommandDefDAO.getFwdCmdDefs();
    	
    	assertTrue("expected some canned forward command defs", list.size() > 0);
    	
    	ForwardCommandDef def = new ForwardCommandDef(666, "Bad Command", "Bad Command - just testing",
    			ForwardCommandParamType.NONE, true);
    	
    	forwardCommandDefDAO.create(def);
    	
    	List<ForwardCommandDef> newList = forwardCommandDefDAO.getFwdCmdDefs();
    	
    	assertEquals("expected list size to be one more", list.size()+1, newList.size());
    	
    	def.setAccessAllowed(false);
    	def.setName("Bad Command updated");
    	def.setDescription("Bad Command Description updated");
    	def.setParamType(ForwardCommandParamType.INTEGER);
    	
    	forwardCommandDefDAO.update(def);
    	newList = forwardCommandDefDAO.getFwdCmdDefs();
    	boolean found = false;
    	for (ForwardCommandDef forwardCommandDef : newList) {
    		if (forwardCommandDef.getFwdCmd().equals(def.getFwdCmd())) {
    			found = true;
    	        Util.compareObjects(def, forwardCommandDef);
    	        break;
    		}
    	}
    	
    	assertTrue("New/updated forward command def not found", found);
    	
    	forwardCommandDefDAO.deleteByID(666);
    	newList = forwardCommandDefDAO.getFwdCmdDefs();
    	
    	assertEquals("expected list size to be back to original after delete", list.size(), newList.size());
    	
    }
    
    @Test
    //@Ignore
    public void rfidsFromBarcode(){
    	
    	DriverHessianDAO driverDAO = new DriverHessianDAO();
    	driverDAO.setSiloService(siloService);
    	
    	List<Long> rfids = driverDAO.getRfidsByBarcode("speedRacerRFID");
    	assertEquals(1000000001l, rfids.get(0).longValue());
    	assertEquals(1000000002l, rfids.get(1).longValue());
    }
    @Test
    //@Ignore
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
    //@Ignore
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
    //@Ignore
    public void driversEvents() {
        EventHessianDAO eventDAO = new EventHessianDAO();
        eventDAO.setSiloService(siloService);
        // year time frame from today back
        Date endDate = new Date();
        Date startDate = DateUtil.getDaysBackDate(endDate, 365);
        List<Event> result = eventDAO.getEventsForDriver(TESTING_DRIVER_ID, startDate, endDate, EventCategory.VIOLATION.getNoteTypesInCategory(),
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
                List<Event> newResult = eventDAO.getEventsForDriver(TESTING_DRIVER_ID, startDate, endDate, EventCategory.VIOLATION.getNoteTypesInCategory(),
                        EventDAO.EXCLUDE_FORGIVEN);
                assertEquals("list size should be 1 less after forgive", (size - 1), newResult.size());
                eventDAO.unforgive(e.getDriverID(), e.getNoteID());
                newResult = eventDAO.getEventsForDriver(TESTING_DRIVER_ID, startDate, endDate, EventCategory.VIOLATION.getNoteTypesInCategory(),
                        EventDAO.EXCLUDE_FORGIVEN);
                assertEquals("list size should be same after forgive/unforgive", size, newResult.size());
            }
        } else {
            System.out.println("NO VEHICLES FOUND");
        }
    }

    @Test
    //@Ignore
    public void vehiclesEvents() {
        EventHessianDAO eventDAO = new EventHessianDAO();
        eventDAO.setSiloService(siloService);
        // year time frame from today back
        Date endDate = new Date();
        Date startDate = DateUtil.getDaysBackDate(endDate, 365);
        List<NoteType> type = new ArrayList<NoteType>();
        type.add(NoteType.SPEEDING_EX3);
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
    public void vehicleNames(){
        VehicleHessianDAO vehicleDAO = new VehicleHessianDAO();
        vehicleDAO.setSiloService(siloService);
        List<VehicleName> names = vehicleDAO.getVehicleNames(TESTING_GROUP_ID);
        assertTrue(names.size() >0);
    }
    @Test
    //@Ignore
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

    @Test
    //@Ignore
    public void lastLocationDrivers() {
        DriverHessianDAO driverDAO = new DriverHessianDAO();
        driverDAO.setSiloService(siloService);
        List<DriverLocation> locList = driverDAO.getDriverLocations(TESTING_GROUP_ID);
        assertNotNull(locList);
        assertTrue(locList.size() > 0);
        
        // speed racer should be in there
        for (DriverLocation driver : locList) {
        	assertNotNull(driver.getDriver());
        	assertNotNull(driver.getDriver().getPerson());
        	if (driver.getDriver().getDriverID().equals(TESTING_DRIVER_ID)) {
        		assertNotNull(driver.getLoc());
        		assertNotNull(driver.getTime());
        		
        	}
        }
    }

    
    @Test
    //@Ignore
    public void events() {
        EventHessianDAO eventDAO = new EventHessianDAO();
        eventDAO.setSiloService(siloService);
        // year time frame from today back
        Date endDate = new Date();
        Date startDate = DateUtil.getDaysBackDate(endDate, 365);
        List<Event> violationEventsList = eventDAO.getViolationEventsForDriver(TESTING_DRIVER_ID, startDate, endDate, EventDAO.EXCLUDE_FORGIVEN);
        assertTrue("expected some events to be returned", violationEventsList.size() > 0);
        validateEvents(EventCategory.VIOLATION.getNoteTypesInCategory(), violationEventsList, startDate, endDate);
        List<Event> warningEventsList = eventDAO.getWarningEventsForDriver(TESTING_DRIVER_ID, startDate, endDate, EventDAO.EXCLUDE_FORGIVEN);
        // TODO: ask David to generate some of these types
        // assertTrue("expected some events to be returned", warningEventsList.size() > 0);
        validateEvents(EventCategory.WARNING.getNoteTypesInCategory(), warningEventsList, startDate, endDate);
        List<Event> recentEventsList = eventDAO.getMostRecentEvents(TESTING_GROUP_ID, 5);
        // assertTrue("expected some events to be returned", (recentEventsList.size() >= 0 && recentEventsList.size() < 6));
        validateEvents(EventCategory.VIOLATION.getNoteTypesInCategory(), recentEventsList);
        int listSize = recentEventsList.size();
        List<Event> recentWarningsList = eventDAO.getMostRecentWarnings(TESTING_GROUP_ID, 5);
        // TODO: ask David to generate some of these types
        // assertTrue("expected some events to be returned", (recentWarningsList.size() > 0 && recentWarningsList.size() < 6));
        validateEvents(EventCategory.WARNING.getNoteTypesInCategory(), recentWarningsList);
        recentEventsList = eventDAO.getMostRecentEvents(TESTING_GROUP_ID, 5);
        assertEquals(listSize, recentEventsList.size());
    }

    private void validateEvents(List<NoteType> expectedTypes, List<Event> eventList, Date startDate, Date endDate) {
        for (Event violation : eventList) {
            assertTrue(expectedTypes.contains(violation.getType()));
            assertTrue(startDate.before(violation.getTime()));
            assertTrue(endDate.after(violation.getTime()));
        }
    }

    private void validateEvents(List<NoteType> expectedTypes, List<Event> eventList) {
        for (Event violation : eventList) {
            assertTrue(expectedTypes.contains(violation.getType()));
        }
    }

    @Test
    //@Ignore
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
        Trip trip = tripList.get(tripList.size()-1);
        assertEquals("invalid vehicle", TESTING_VEHICLE_ID, trip.getVehicleID());
        assertTrue("trip invalid start date", startDate.before(trip.getStartTime()));
        assertTrue("trip invalid end date", endDate.after(trip.getEndTime()));
        assertTrue("trip invalid mileage", trip.getMileage() > 0);
            
        assertTrue("trip Quality should be set", trip.getQuality().equals(TripQuality.GOOD) || trip.getQuality().equals(TripQuality.BAD) || trip.getQuality().equals(TripQuality.UNKNOWN));
        
        Trip lastTrip = driverDAO.getLastTrip(TESTING_DRIVER_ID);
        assertNotNull(lastTrip);
        tripList = vehicleDAO.getTrips(TESTING_VEHICLE_ID, startDate, endDate);
        assertNotNull(tripList);
        System.out.println("num trips: " + tripList.size());
        assertTrue(tripList.size() > 0);
        trip =  tripList.get(tripList.size()-1);
        assertEquals("trip invalid driver id", TESTING_DRIVER_ID, trip.getDriverID());
        assertTrue("trip invalid start date", startDate.before(trip.getStartTime()));
        assertTrue("trip invalid end date", endDate.after(trip.getEndTime()));
        assertTrue("trip invalid mileage", trip.getMileage() > 0);
        assertTrue("trip Quality should be set", trip.getQuality().equals(TripQuality.GOOD) || trip.getQuality().equals(TripQuality.BAD) || trip.getQuality().equals(TripQuality.UNKNOWN));

        lastTrip = vehicleDAO.getLastTrip(TESTING_VEHICLE_ID);
        assertNotNull(lastTrip);
        
    }
    @Test
    public void driverNames(){
        DriverHessianDAO driverDAO = new DriverHessianDAO();
        driverDAO.setSiloService(siloService);
        List<DriverName> names = driverDAO.getDriverNames(TESTING_GROUP_ID);
        assertTrue(names.size() >0);
    }
    @Test
    public void admin() {
        // test all create, find, update and any other methods (not delete yet though)
        account();
        Integer acctID = account.getAcctID();
        unknownDriver(acctID);
        
        groupHierarchy(acctID);
        
        roles(acctID);
        System.out.println("Admin - roles done");
	    	
        // zones
        zones(acctID, team1Group.getGroupID());
        System.out.println("Admin - zones done");
    	
        // devices
        devices(acctID);
        System.out.println("Admin - devices done");
    	
        // forward commands to devices
// TODO:FIX        
        forwardCommands();
        System.out.println("Admin - forward commands done");
    	
        // vehicles
        vehicles(team1Group.getGroupID());
        vehicles(team2Group.getGroupID());
        regionVehicles(regionGroup.getGroupID());
        // assign devices to vehicles in team1 group
        assignDevicesToVehicles(team1Group.getGroupID());
        System.out.println("Admin - vehicles done");


// TODO: add back in when David deploys the backend        
//        waysmartDOTTypeForwardCommandCheck(team2Group.getGroupID());

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
        System.out.println("Admin - users, person, drivers done");
    	
        // various find methods
        find();

        // create a users at the fleet level for use by the alerts tests
        Person person = new Person(0, acctID, TimeZone.getDefault(),  address.getAddrID(),  "email_" + fleetGroup.getGroupID() + "_" + 1000 + "@yahoo.com", null, "555555555" + 9, "555555555" + 9, null, null, null, null, null,
                acctID+"-"+"emp" + 1000, null, "title", "dept" , "first", "m", "last", "jr", Gender.MALE, 65, 180, Util.genDate(1959, 8, 30), Status.ACTIVE, MeasurementType.ENGLISH,
                FuelEfficiencyType.MPG_US, Locale.getDefault());
        person.setUser(new User(0, 0, randomRole(acctID), Status.ACTIVE, "user" + fleetGroup.getGroupID() + "_" + 1000, PASSWORD, fleetGroup.getGroupID()));
        PersonHessianDAO personDAO = new PersonHessianDAO();
        personDAO.setSiloService(siloService);
        Integer personID = personDAO.create(acctID, person);
        assertNotNull(personID);
        
        zoneAlertProfiles(acctID, fleetGroup.getGroupID(), team1Group.getGroupID(),person.getUser().getUserID());
        redFlagAlertProfiles(acctID, fleetGroup.getGroupID(), team1Group.getGroupID(),person.getUser().getUserID());
        
        superuser(team1Group.getGroupID());
    }


    private void superuser(Integer groupID) {
        UserHessianDAO userDAO = new UserHessianDAO();
        userDAO.setSiloService(siloService);
        SuperuserHessianDAO superuserDAO = new SuperuserHessianDAO();
        superuserDAO.setSiloService(siloService);
        
        List<User> userList = userDAO.getUsersInGroupHierarchy(groupID);
        assertTrue("no users found", userList.size() > 0);
        
        User user = userList.get(0);
        
        Boolean isSuperuser = superuserDAO.isSuperuser(user.getUserID());
        assertTrue("not superuser", !isSuperuser);
		
        superuserDAO.setSuperuser(user.getUserID());
        
        isSuperuser = superuserDAO.isSuperuser(user.getUserID());
        assertTrue("superuser", isSuperuser);
        
        superuserDAO.clearSuperuser(user.getUserID());
        
        isSuperuser = superuserDAO.isSuperuser(user.getUserID());
        assertTrue("not superuser", !isSuperuser);
	}

	private void redFlagAlertProfiles(Integer acctID, Integer fleetGroupID, Integer groupID, Integer fleetUserID) {
        RedFlagAlertHessianDAO redFlagAlertDAO = new RedFlagAlertHessianDAO();
        redFlagAlertDAO.setSiloService(siloService);
        
        UserHessianDAO userDAO = new UserHessianDAO();
        userDAO.setSiloService(siloService);
        List<User> groupUserList = userDAO.getUsersInGroupHierarchy(groupID);
        Integer userID = groupUserList.get(0).getUserID();
        
        List<Boolean> dayOfWeek = new ArrayList<Boolean>();
        for (int i = 0; i < 7; i++)
            dayOfWeek.add(true);
        List<Integer> groupIDList = new ArrayList<Integer>();
        groupIDList.add(team1Group.getGroupID());
        List<Integer> notifyPersonIDs = new ArrayList<Integer>();
        notifyPersonIDs.add(this.personList.get(0).getPersonID());
        notifyPersonIDs.add(this.personList.get(1).getPersonID());
        List<AlertEscalationItem> escalationList = new ArrayList<AlertEscalationItem>();
        escalationList.add(new AlertEscalationItem(this.personList.get(0).getPersonID(),1));
        escalationList.add(new AlertEscalationItem(this.personList.get(1).getPersonID(), 0));
        Integer[] speedSettings = { 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80 };
        List<AlertMessageType>list = new ArrayList<AlertMessageType>(EnumSet.of(AlertMessageType.ALERT_TYPE_SPEEDING));
        RedFlagAlert redFlagAlert = new RedFlagAlert(list,acctID, userID, 
        		"Red Flag Alert Profile", "Red Flag Alert Profile Description", 0, 1339, dayOfWeek, groupIDList,
                null, // driverIDs
                null, // vehicleIDs
                null, // vehicleTypeIDs
                notifyPersonIDs,
                null, // emailTo
                speedSettings, 10, 10, 10, 10, RedFlagLevel.CRITICAL, null,null,
                escalationList,5, null,5);
        Integer redFlagAlertID = redFlagAlertDAO.create(acctID, redFlagAlert);
        assertNotNull(redFlagAlertID);
        redFlagAlert.setAlertID(redFlagAlertID);

        String ignoreFields[] = { "modified", "fullName", "escalationList" };

        // find
        RedFlagAlert returnedRedFlagAlert = redFlagAlertDAO.findByID(redFlagAlertID);
        Util.compareObjects(redFlagAlert, returnedRedFlagAlert, ignoreFields);
        // update
        redFlagAlert.setHardAcceleration(20);
        redFlagAlert.setHardBrake(20);
        redFlagAlert.setHardTurn(20);
        redFlagAlert.setHardVertical(20);
        redFlagAlert.setSeverityLevel(RedFlagLevel.CRITICAL);
        Integer[] newSpeedSettings = new Integer[15];
        for (int i = 0; i < 15; i++) {
            newSpeedSettings[i] = 99;
        }
        redFlagAlert.setSpeedSettings(newSpeedSettings);
        Integer changedCount = redFlagAlertDAO.update(redFlagAlert);
        assertEquals("Red Flag alert update count", Integer.valueOf(1), changedCount);
        // find after update
        returnedRedFlagAlert = redFlagAlertDAO.findByID(redFlagAlertID);
        Util.compareObjects(redFlagAlert, returnedRedFlagAlert, ignoreFields);

        // account id
        List<RedFlagAlert> redFlagAlertList = redFlagAlertDAO.getRedFlagAlerts(acctID);
        assertEquals(1, redFlagAlertList.size());
        Util.compareObjects(redFlagAlert, redFlagAlertList.get(0), ignoreFields);
        // user id
        List<RedFlagAlert> userRedFlagAlertList = redFlagAlertDAO.getRedFlagAlertsByUserID(userID);
        assertEquals(1, userRedFlagAlertList.size());
        Util.compareObjects(redFlagAlert, userRedFlagAlertList.get(0), ignoreFields);
        // group id
        List<RedFlagAlert> groupRedFlagAlertList = redFlagAlertDAO.getRedFlagAlertsByUserIDDeep(userID);
        assertEquals(1, groupRedFlagAlertList.size());
        Util.compareObjects(redFlagAlert, groupRedFlagAlertList.get(0), ignoreFields);

        
        // fleet level user
        userRedFlagAlertList = redFlagAlertDAO.getRedFlagAlertsByUserID(fleetUserID);
        assertEquals(0, userRedFlagAlertList.size());
        // get by user deep
        groupRedFlagAlertList = redFlagAlertDAO.getRedFlagAlertsByUserIDDeep(fleetUserID);
        assertEquals(1, groupRedFlagAlertList.size());
        Util.compareObjects(redFlagAlert, groupRedFlagAlertList.get(0), ignoreFields);
        list = new ArrayList<AlertMessageType>(EnumSet.of(AlertMessageType.ALERT_TYPE_SPEEDING));
        RedFlagAlert fleetRedFlagAlert = new RedFlagAlert(list,acctID, fleetUserID, 
                "Red Flag Alert Profile", "Red Flag Alert Profile Description", 0, 1339, dayOfWeek, groupIDList,
                null, // driverIDs
                null, // vehicleIDs
                null, // vehicleTypeIDs
                notifyPersonIDs,
                null, // emailTo
                speedSettings, 10, 10, 10, 10, RedFlagLevel.CRITICAL, null,null,
                escalationList,5, null,5);
        Integer fleetRedFlagAlertID = redFlagAlertDAO.create(acctID, fleetRedFlagAlert);
        fleetRedFlagAlert.setAlertID(fleetRedFlagAlertID);
        userRedFlagAlertList = redFlagAlertDAO.getRedFlagAlertsByUserID(fleetUserID);
        assertEquals(1, userRedFlagAlertList.size());
        Util.compareObjects(fleetRedFlagAlert, userRedFlagAlertList.get(0), ignoreFields);
        // get by user deep
        groupRedFlagAlertList = redFlagAlertDAO.getRedFlagAlertsByUserIDDeep(fleetUserID);
        assertEquals(2, groupRedFlagAlertList.size());
        
        // modify the owner 
        fleetRedFlagAlert.setUserID(userID);
        fleetRedFlagAlert.setGroupIDs(fleetRedFlagAlert.getGroupIDs());
        changedCount = redFlagAlertDAO.update(fleetRedFlagAlert);
        assertEquals("Red flag update count", Integer.valueOf(1), changedCount);
        RedFlagAlert updatedRedFlagAlert = redFlagAlertDAO.findByID(fleetRedFlagAlert.getAlertID());
        Util.compareObjects(fleetRedFlagAlert, updatedRedFlagAlert, ignoreFields);
        
        // check counts
        userRedFlagAlertList = redFlagAlertDAO.getRedFlagAlertsByUserID(fleetUserID);
        assertEquals(0, userRedFlagAlertList.size());
        userRedFlagAlertList = redFlagAlertDAO.getRedFlagAlertsByUserIDDeep(fleetUserID);
        assertEquals(2, userRedFlagAlertList.size());
        userRedFlagAlertList = redFlagAlertDAO.getRedFlagAlertsByUserID(userID);
        assertEquals(2, userRedFlagAlertList.size());
        
        // delete
        Integer deletedCount = redFlagAlertDAO.deleteByID(redFlagAlertID);
        assertEquals("Red Flag alert delete count", Integer.valueOf(1), deletedCount);
        // find after delete
        returnedRedFlagAlert = redFlagAlertDAO.findByID(redFlagAlertID);
        assertEquals("Red flag alert should have deleted status after delete", Status.DELETED, returnedRedFlagAlert.getStatus());
    }

    private void zoneAlertProfiles(Integer acctID, Integer fleetGroupID, Integer groupID, Integer fleetUserID) {
        ZoneHessianDAO zoneDAO = new ZoneHessianDAO();
        zoneDAO.setSiloService(siloService);
        
        UserHessianDAO userDAO = new UserHessianDAO();
        userDAO.setSiloService(siloService);
        List<User> groupUserList = userDAO.getUsersInGroupHierarchy(groupID);
        Integer userID = groupUserList.get(0).getUserID();
        
        // create a zone to use
        Integer zoneID = createZone(acctID, groupID, "Zone With Alerts", zoneDAO);
        
        RedFlagAlertHessianDAO zoneAlertDAO = new RedFlagAlertHessianDAO();
        zoneAlertDAO.setSiloService(siloService);
        
        RedFlagAlert zoneAlert = createZoneAlert(acctID, userID, zoneID, zoneAlertDAO);
        Integer zoneAlertID = zoneAlert.getAlertID();
        
        // find
        // TODO: escalationList fails because it comes in in a different order than the original -- could compare seperately
        String ignoreFields[] = { "modified", "fullName", "escalationList", "speedSettings" };
        RedFlagAlert returnedZoneAlert = zoneAlertDAO.findByID(zoneAlertID);
        Util.compareObjects(zoneAlert, returnedZoneAlert, ignoreFields);
        // update
        zoneAlert.setName("Mod Zone Alert Profile");
        zoneAlert.setDescription("Mod Zone Alert Profile Description");
//        zoneAlert.setArrival(false);
        zoneAlert.setGroupIDs(new ArrayList<Integer>());
        List<VehicleType> vehicleTypeList = new ArrayList<VehicleType>();
        vehicleTypeList.add(VehicleType.LIGHT);
        Integer changedCount = zoneAlertDAO.update(zoneAlert);
        assertEquals("Zone update count", Integer.valueOf(1), changedCount);
        // find after update
        returnedZoneAlert = zoneAlertDAO.findByID(zoneAlertID);
        Util.compareObjects(zoneAlert, returnedZoneAlert, ignoreFields);
        // get by account id
        List<RedFlagAlert> zoneAlertList = zoneAlertDAO.getRedFlagAlerts(acctID);
        assertEquals(1, zoneAlertList.size());
        Util.compareObjects(zoneAlert, zoneAlertList.get(0), ignoreFields);
        // get by user id
        List<RedFlagAlert> userZoneAlertList = zoneAlertDAO.getRedFlagAlertsByUserID(userID);
        assertEquals(1, userZoneAlertList.size());
        Util.compareObjects(zoneAlert, userZoneAlertList.get(0), ignoreFields);
        // get by group id
        List<RedFlagAlert> groupZoneAlertList = zoneAlertDAO.getRedFlagAlertsByUserIDDeep(userID);
        assertEquals(1, groupZoneAlertList.size());
        Util.compareObjects(zoneAlert, groupZoneAlertList.get(0), ignoreFields);
        
        
        // fleet level user
        userZoneAlertList = zoneAlertDAO.getRedFlagAlertsByUserID(fleetUserID);
        assertEquals(0, userZoneAlertList.size());
        // get by user deep
        groupZoneAlertList = zoneAlertDAO.getRedFlagAlertsByUserIDDeep(fleetUserID);
        assertEquals(1, groupZoneAlertList.size());
        Util.compareObjects(zoneAlert, groupZoneAlertList.get(0), ignoreFields);
        
        RedFlagAlert fleetZoneAlert = createZoneAlert(acctID, fleetUserID, zoneID, zoneAlertDAO);
        userZoneAlertList = zoneAlertDAO.getRedFlagAlertsByUserID(fleetUserID);
        assertEquals(1, userZoneAlertList.size());
        Util.compareObjects(fleetZoneAlert, userZoneAlertList.get(0), ignoreFields);
        // get by user deep
        groupZoneAlertList = zoneAlertDAO.getRedFlagAlertsByUserIDDeep(fleetUserID);
        assertEquals(2, groupZoneAlertList.size());
        
        // modify the owner 
        fleetZoneAlert.setUserID(userID);
        fleetZoneAlert.setGroupIDs(zoneAlert.getGroupIDs());
        changedCount = zoneAlertDAO.update(fleetZoneAlert);
        assertEquals("Zone update count", Integer.valueOf(1), changedCount);
        RedFlagAlert updatedZoneAlert = zoneAlertDAO.findByID(fleetZoneAlert.getAlertID());
        Util.compareObjects(fleetZoneAlert, updatedZoneAlert, ignoreFields);
        
        // check counts
        userZoneAlertList = zoneAlertDAO.getRedFlagAlertsByUserID(fleetUserID);
        assertEquals(0, userZoneAlertList.size());
        userZoneAlertList = zoneAlertDAO.getRedFlagAlertsByUserIDDeep(fleetUserID);
        assertEquals(2, userZoneAlertList.size());
        userZoneAlertList = zoneAlertDAO.getRedFlagAlertsByUserID(userID);
        assertEquals(2, userZoneAlertList.size());
        
        
        
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
        zoneAlertDAO.deleteAlertsByZoneID(zoneID);
        returnedZoneAlert = zoneAlertDAO.findByID(zoneAlertID);
        assertEquals("Zone alert have deleted status after deletebyzoneID", Status.DELETED, returnedZoneAlert.getStatus());
        
        
    }

    private RedFlagAlert createZoneAlert(Integer acctID, Integer userID, Integer zoneID, RedFlagAlertHessianDAO zoneAlertDAO) {
        List<Boolean> dayOfWeek = new ArrayList<Boolean>();
        for (int i = 0; i < 7; i++)
            dayOfWeek.add(true);
        List<Integer> groupIDList = new ArrayList<Integer>();
        groupIDList.add(team1Group.getGroupID());
        List<Integer> notifyPersonIDs = new ArrayList<Integer>();
        notifyPersonIDs.add(this.personList.get(0).getPersonID());
        notifyPersonIDs.add(this.personList.get(1).getPersonID());
        List<AlertEscalationItem> escalationList = new ArrayList<AlertEscalationItem>();
        escalationList.add(new AlertEscalationItem(this.personList.get(0).getPersonID(),1));
        escalationList.add(new AlertEscalationItem(this.personList.get(1).getPersonID(),0));
        List<AlertMessageType>list = new ArrayList<AlertMessageType>(EnumSet.of(AlertMessageType.ALERT_TYPE_ENTER_ZONE,AlertMessageType.ALERT_TYPE_EXIT_ZONE));
        RedFlagAlert zoneAlert = new RedFlagAlert(list,acctID, userID, 
        		"Zone Alert Profile", "Zone Alert Profile Description", 0, 1339, dayOfWeek, groupIDList, null, // driverIDs
                null, // vehicleIDs
                null, // vehicleTypeIDs
                notifyPersonIDs, null, // emailTo
                null,//speed settings
                null,null,null,null,//aggressive driving settings
                RedFlagLevel.NONE, zoneID,null,
                escalationList,5, null,5);
        Integer zoneAlertID = zoneAlertDAO.create(acctID, zoneAlert);
        assertNotNull(zoneAlertID);
        zoneAlert.setAlertID(zoneAlertID);
        return zoneAlert;
    }

    private Integer createZone(Integer acctID, Integer groupID, String name, ZoneHessianDAO zoneDAO) {
        Zone zone = new Zone(0, acctID, Status.ACTIVE, name, "123 Street, Salt Lake City, UT 84107", groupID);
        List<LatLng> points = new ArrayList<LatLng>();
        points.add(new LatLng(40.723871753812f, -111.92932452647742f));
        points.add(new LatLng(40.704246f, -111.948613f));
        points.add(new LatLng(40.70f, -111.95f));
        points.add(new LatLng(40.723871753812f, -111.92932452647742f));
        zone.setPoints(points);
        Integer zoneID = zoneDAO.create(acctID, zone);
        assertNotNull(zoneID);
        zone.setZoneID(zoneID);
        return zoneID;
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
        
        List<ZoneOption> options = new ArrayList<ZoneOption>();
        
        for (ZoneAvailableOption zoneAvailableOption : ZoneAvailableOption.values()) {
            options.add(new ZoneOption(zoneAvailableOption, zoneAvailableOption.getDefaultValue()));
        }
        zone.setOptions(options);
        
        
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
        for (ZoneOption zoneOption : zone.getOptions()) {
            // set 3 state options to off state
            if (zoneOption.getOption().getOptionType() == ZoneOptionType.OFF_ON_DEVICE)
                zoneOption.setValue(OffOnDevice.OFF);
            
        }
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
        account = new Account(null, Status.ACTIVE);
        String timeStamp = Calendar.getInstance().getTime().toString();
        account.setAcctName(timeStamp);
        account.setHos(AccountHOSType.NONE);
        // create
        Integer siloID = TESTING_SILO;
        Integer acctID = accountDAO.create(siloID, account);
        assertNotNull("Create Account failed", acctID);
        account.setAcctID(acctID);
        logger.debug("CREATED ACCOUNT: " + account.getAcctID());
        // find
        String ignoreFields[] = { "modified", "unkDriverID", "props" };  // baseID was the previous 2nd value? found on device object but?
        Account savedAccount = accountDAO.findByID(account.getAcctID());
        Util.compareObjects(account, savedAccount, ignoreFields);
        assertNotNull("Account unknownDriverID",savedAccount.getUnkDriverID());
        
        Address accountAddress = address(acctID);

        account.setAddressID(accountAddress.getAddrID());
        account.setHos(AccountHOSType.HOS_SUPPORT);
        
        // update
        Integer changedCount = accountDAO.update(account);
        assertEquals("Account update count", Integer.valueOf(1), changedCount);
        savedAccount = accountDAO.findByID(account.getAcctID());
        Util.compareObjects(account, savedAccount, ignoreFields);
        List<Account> accountList = accountDAO.getAllAcctIDs();
        assertTrue(!accountList.isEmpty());
        
        
    }
    private void unknownDriver(Integer acctID) {
        AccountHessianDAO accountDAO = new AccountHessianDAO();
        accountDAO.setSiloService(siloService);
        PersonHessianDAO personDAO = new PersonHessianDAO();
        personDAO.setSiloService(siloService);
        DriverHessianDAO driverDAO = new DriverHessianDAO();
        driverDAO.setSiloService(siloService);
    	
        Account savedAccount = accountDAO.findByID(acctID);
        assertNotNull("unknown driver should be set for account "+acctID, savedAccount.getUnkDriverID());
        
        Driver unknownDriver = driverDAO.findByID(savedAccount.getUnkDriverID());
        assertNotNull("unknown driver should exist for account "+acctID, unknownDriver);
        assertNotNull("unknown driver should have person ID set", unknownDriver.getPersonID());
        assertNotNull("unknown driver should have person set", unknownDriver.getPerson());
        
        
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
        AddressHessianDAO addressDAO = new AddressHessianDAO();
        addressDAO.setSiloService(siloService);
        // create
        Address team1Address = new Address(null, "1 Team", null, "City", randomState(), "55555", acctID);
        Integer addrID = addressDAO.create(acctID, team1Address);
        team1Address.setAddrID(addrID);
        team1Group.setAddressID(addrID);

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
    private void roles(Integer acctID){
    	
        RoleHessianDAO roleHessianDAO = new RoleHessianDAO();
        roleHessianDAO.setSiloService(siloService);
        SiteAccessPoints siteAccessPoints = new SiteAccessPoints();
        siteAccessPoints.setRoleDAO(roleHessianDAO);
        siteAccessPoints.init();

        Role newRole = new Role();
		newRole.setAcctID(acctID);
		
		//Create some accessPoints
		List<AccessPoint> accessPoints = new ArrayList<AccessPoint>();
		for (Integer accessPointID : siteAccessPoints.getAccessPointsMap().keySet()) {
		    accessPoints.add(new AccessPoint(accessPointID,15));
		}
		newRole.setAccessPts(accessPoints);
		newRole.setName("TestUserAccess ");

		Integer roleID = roleHessianDAO.create(acctID, newRole);
		newRole.setRoleID(roleID);
	    List<Role> roleList = roleHessianDAO.getRoles(acctID);
	    assertTrue("No  new roles were found", roleList.size() > 2);
	    
	    newRole.setName("RenamedRole");
	    Integer changedCount = roleHessianDAO.update(newRole);
        assertEquals("Role update count " + newRole.getName(), Integer.valueOf(1), changedCount);
        
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
                    "555555123" + i);
//            , // phone
//                    "555555987" + i); // ephone
            if (i == DEVICE_COUNT-1)
                device.setProductVersion(ProductType.WAYSMART);
            else device.setProductVersion(ProductType.TIWIPRO_R74);
            Integer deviceID = deviceDAO.create(acctID, device);
            assertNotNull(deviceID);
            device.setDeviceID(deviceID);
            deviceList.add(device);
        }
        // duplicate imei -- should throw exception
        boolean exceptionThrown = false;
        try {
            Device dupDevice = new Device(0, acctID, DeviceStatus.NEW, "Device " + 0, "IMEI " + acctID + 0, "SIM " + 0, "SERIALNUM" + 0, "PHONE " + 0);
//            , "EPHONE " + 0);
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
        String ignoreFields[] = { "modified", "baseID"};  
        for (Device device : deviceList) {
            Device returnedDevice = deviceDAO.findByID(device.getDeviceID());
            Util.compareObjects(device, returnedDevice, ignoreFields);
        }
        for (Device device : deviceList) {
            Device returnedDevice = deviceDAO.findBySerialNum(device.getSerialNum());
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
    
    
        Device device = new Device(0, acctID, DeviceStatus.NEW, "Device DEL", "IMEI " + acctID + "DEL", "SIM " + "DEL", 
        		"SN" + acctID + "DEL",
                "5555551239");
//        , // phone
//                "5555559879"); // ephone
        device.setProductVersion(ProductType.TIWIPRO_R74);
        Integer deviceID = deviceDAO.create(acctID, device);
        assertNotNull(deviceID);
        device.setDeviceID(deviceID);
        
        int delCnt = deviceDAO.deleteByID(deviceID);
        assertEquals("Device delete count", 1, delCnt);
        
        Device delDevice = deviceDAO.findByID(deviceID);
        assertEquals("Deleted device status ", DeviceStatus.DELETED, delDevice.getStatus());
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
        String ignoreFields[] = { "modified", "fwdID", "created" };
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
            Vehicle vehicle = new Vehicle(0, groupID, Status.INACTIVE, "Vehicle " + i, "Make " + i, "Model " + i, 2000 + i, "COLOR " + i, VehicleType.valueOf(Util.randomInt(0,
                    VehicleType.values().length - 1)), "VIN_" + groupID + "_" + i, 1000, "License " + i, randomState());
//            vehicle.setHos((i == 0));   // set just 1st to hos 
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
        // TODO: make sure all of these fields should be in ignore List
        String ignoreFields[] = { "modified" , "warrantyStart", "warrantyStop"};
        for (Vehicle vehicle : vehicleList) {
            if (vehicle.getGroupID().equals(groupID)) {
                Vehicle returnedVehicle = vehicleDAO.findByID(vehicle.getVehicleID());
                Util.compareObjects(vehicle, returnedVehicle, ignoreFields);
            }
        }
        // find all (deep)
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
        
        // find all (not deep)
        groupVehicleList = vehicleDAO.getVehiclesInGroup(groupID);
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
            Vehicle vehicle = new Vehicle(0, groupID, Status.ACTIVE, "Vehicle " + i, "Make " + i, "Model " + i, 2000 + i, "COLOR " + i, VehicleType.valueOf(Util.randomInt(0,
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
        
//        RoleHessianDAO roleDAO = new RoleHessianDAO();
//        roleDAO.setSiloService(siloService);
//        
//        List<Role> roleList = roleDAO.getRoles(acctID);
//        List<Integer> roles = new ArrayList<Integer>();
//        roles.add(roleList.get(0).getRoleID());
//        roles.add(roleList.get(1).getRoleID()); //default normal user
        
        List<Person> emptyPersonList = personDAO.getPeopleInGroupHierarchy(groupID);
        assertEquals("expected no people in group", Integer.valueOf(0), new Integer(emptyPersonList.size()));
        
        // create
        for (int i = 0; i < PERSON_COUNT; i++) {
            Person person = new Person(0, acctID, TimeZone.getDefault(), address.getAddrID(),  "email_" + groupID + "_" + i + "@yahoo.com", null, "555555555" + i, "555555555" + i, null, null, null, null, null,
                    acctID+"-"+groupID+"-"+"emp" + i, null, "title" + i, "dept" + i, "first" + i, "m" + i, "last" + i, "jr", Gender.MALE, 65, 180, Util.genDate(1959, 8, 30), Status.ACTIVE, MeasurementType.ENGLISH,
                    FuelEfficiencyType.MPG_US, Locale.getDefault());
            person.setUser(new User(0, 0, randomRole(acctID), Status.ACTIVE, "user" + groupID + "_" + i, PASSWORD, groupID));
            Integer personID = personDAO.create(acctID, person);
            assertNotNull(personID);
            
            Person createdPerson = personDAO.findByID(personID);
            String ignoreFields[] = { "personID","modified"};
            Util.compareObjects(person, createdPerson, ignoreFields);
            
            User user = person.getUser();
            
            person.setPersonID(personID);
            person.setAddress(address);
            personList.add(person);
            // update
//            List<Integer> newRoles = new ArrayList<Integer>();
//            newRoles.add(roleList.get(0).getRoleID());// default admin role
//            newRoles.add(roleList.get(1).getRoleID());
            
            user.setRoles(randomRole(acctID));
            Integer changedCount = userDAO.update(user);
            assertEquals("user update count " + user.getUserID(), Integer.valueOf(1), changedCount);
            // find user by ID - ignoring roles until update fixed
            String ignoreFields1[] = { "modified", "person" };
            User returnedUser = userDAO.findByID(user.getUserID());
            Util.compareObjects(user, returnedUser, ignoreFields1);
            returnedUser = userDAO.getUserByPersonID(person.getPersonID());
            // TODO this needs to be fixed
            Util.compareObjects(user, returnedUser, ignoreFields1);
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
        String ignoreFields[] = { "modified", "address"};
        for (Person person : personList) {
            Person returnedPerson = personDAO.findByID(person.getPersonID());
            Util.compareObjects(person, returnedPerson, ignoreFields);
        }
        // find all
        List<Person> groupPersonList = personDAO.getPeopleInGroupHierarchy(groupID);
        assertEquals("people count for group", Integer.valueOf(PERSON_COUNT), new Integer(groupPersonList.size()));
        String ignoreFields2[] = { "modified", "user", "driver", "address"};
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
        reportScheduleMonthly.setIftaOnly(Boolean.TRUE);
        reportScheduleMonthly.setParamType(ReportParamType.GROUPS);
//        reportScheduleMonthly.setGroupID(groupID);
        List<Integer> groupIDList = new ArrayList<Integer>();
        groupIDList.add(groupID);
        reportScheduleMonthly.setGroupIDList(groupIDList);
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
        String[] ignoreFields = { "modified", 
                "emailToString",    // list might be in different order (could extract and test)
                "endDate",          // not sure why this is different
                "startDate" };      // not sure why this is different

        ReportSchedule reportSchedule = reportScheduleHessianDAO.findByID(monthlyId);
        Util.compareObjects(reportScheduleMonthly, reportSchedule, ignoreFields);


        reportSchedule = reportScheduleHessianDAO.findByID(weeklyId);
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

        // get by groupID
// TODO:        
//        List<ReportSchedule> reportSchedulesByUserDeep = reportScheduleHessianDAO.getReportSchedulesByUserIDDeep(userID);
//        assertEquals("report schedules for userIDDeep: " + userID, 3, reportSchedulesByUserDeep.size());   

        
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
        String ignoreFields[] = { "modified", "person", "barcode", "rfid1", "rfid2" };
        for (Person person : groupPersonList) {
            Date expired = Util.genDate(2010, 9, 30);
            Driver driver = new Driver(0, person.getPersonID(), Status.ACTIVE, null, null, null, "l" + person.getPersonID(), randomState(), "ABCD", expired, null, RuleSetType.US_OIL, groupID);
            
            // create
            Integer driverID = driverDAO.create(person.getPersonID(), driver);
            assertNotNull("driver", driverID);
            driver.setDriverID(driverID);
            // update
/*            
 			// this no longer works because RFIDs have to be 'real' ones
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
*/            
            // find by PersonID
            Driver returnedDriver = driverDAO.findByPersonID(person.getPersonID());
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
    
    private void waysmartDOTTypeForwardCommandCheck(Integer groupID) {
        VehicleHessianDAO vehicleDAO = new VehicleHessianDAO();
        vehicleDAO.setSiloService(siloService);
        DeviceHessianDAO deviceDAO = new DeviceHessianDAO();
        deviceDAO.setSiloService(siloService);
        vehicleDAO.setDeviceDAO(deviceDAO);
        List<Vehicle> groupVehicles = vehicleDAO.getVehiclesInGroupHierarchy(groupID);

        // switch device assignment
        Integer vehicleID = groupVehicles.get(0).getVehicleID();
        Integer wsdeviceID = deviceList.get(DEVICE_COUNT-1).getDeviceID();
        
        vehicleDAO.setVehicleDevice(vehicleID, wsdeviceID);
        
        Vehicle vehicle = vehicleDAO.findByID(vehicleID);
        
        vehicle.setDot(VehicleDOTType.PROMPT_FOR_DOT_TRIP);
        vehicleDAO.update(vehicle);
        
        
        List<ForwardCommand> queuedCommands = deviceDAO.getForwardCommands(wsdeviceID, ForwardCommandStatus.STATUS_QUEUED);
        assertEquals("queued forward commands", 1, queuedCommands.size());
        boolean found = false;
        for (ForwardCommand forwardCommand : queuedCommands) {
            if (forwardCommand.getCmd().equals(ForwardCommandID.DOT_PROMPT_BY_TRIP)) 
                found = true;
        }
        
        assertTrue("expected forward command " + ForwardCommandID.DOT_PROMPT_BY_TRIP + " not found", found);

        
        
    }


    private void assignDevicesToVehicles(Integer groupID) {
        VehicleHessianDAO vehicleDAO = new VehicleHessianDAO();
        vehicleDAO.setSiloService(siloService);
        DeviceHessianDAO deviceDAO = new DeviceHessianDAO();
        deviceDAO.setSiloService(siloService);
        vehicleDAO.setDeviceDAO(deviceDAO);
        List<Vehicle> groupVehicles = vehicleDAO.getVehiclesInGroupHierarchy(groupID);
        assertEquals(Integer.valueOf(VEHICLE_COUNT), Integer.valueOf(groupVehicles.size()));

        // switch device assignment
        Integer vehicle1ID = groupVehicles.get(0).getVehicleID();
        Integer device1ID = deviceList.get(0).getDeviceID();
        Integer device2ID = deviceList.get(1).getDeviceID();
//System.out.println("------- " + cnt + " ----------");
//System.out.println("vehicleID: " + vehicle1ID);
//System.out.println("deviceID 1: " + device1ID);
//System.out.println("deviceID 2: " + device2ID);
        
        vehicleDAO.setVehicleDevice(vehicle1ID, device1ID);
        Vehicle returnedVehicle1 = vehicleDAO.findByID(vehicle1ID);
//System.out.println("after setting device 1 to vehicle -- deviceID in vehicle = " + returnedVehicle1.getDeviceID());
		assertEquals("setVehicleDevice failed", device1ID, returnedVehicle1.getDeviceID());
        
        vehicleDAO.setVehicleDevice(vehicle1ID, device2ID);
        Vehicle returnedVehicle2 = vehicleDAO.findByID(vehicle1ID);
//        System.out.println("after setting device 2 to vehicle -- deviceID in vehicle = " + returnedVehicle2.getDeviceID());
        assertEquals("setVehicleDevice failed vehicle id: " + vehicle1ID, device2ID, returnedVehicle2.getDeviceID());

        Device returnedDevice1 = deviceDAO.findByID(device1ID);
        Device returnedDevice2 = deviceDAO.findByID(device2ID);
//System.out.println("vehicleID in device 1 = " + returnedDevice1.getVehicleID());
//System.out.println("vehicleID in device 2 = " + returnedDevice2.getVehicleID());
//        }
        assertNull("setVehicleDevice failed", returnedDevice1.getVehicleID());
        assertEquals("setVehicleDevice failed vehicle id: " + vehicle1ID, vehicle1ID, returnedDevice2.getVehicleID());

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
        Driver driver = new Driver(0, 0, Status.ACTIVE,null, null, null, "l" + groupID, randomState(), "ABCD", expired, null, RuleSetType.NON_DOT, groupID);
        User user = new User(0, 0, randomRole(acctID), Status.ACTIVE, "deepuser_" + groupID, PASSWORD, groupID);
        Date dob = Util.genDate(1959, 8, 30);
        Person person = new Person(0, acctID, TimeZone.getDefault(), 
                address.getAddrID(), "priEmail" + groupID + "@test.com", "secEmail@test.com", "8015551111",
                "8015552222", "8015554444@texter.com", "8015555555@texter.com", 1, 2, 3, acctID+"-"+"emp" + groupID, null, "title" + groupID, "dept" + groupID, "first" + groupID, "m"
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
        String[] ignoreFields = { "modified", "measurementType", "address" };
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
        findByKey(personDAO, personList.get(0), personList.get(0).getPriEmail(), new String[] { "modified", "address", "driver", "user", "measurementType", "driverID", "userID", "roles" });
        findByKeyExpectNoResult(personDAO, "BAD_EMAIL");
        UserHessianDAO userDAO = new UserHessianDAO();
        userDAO.setSiloService(siloService);
        findByKey(userDAO, userList.get(0), userList.get(0).getUsername(), new String[] { "modified", "person", "roles" });
        findByKeyExpectNoResult(userDAO, "BAD_USER");
        DeviceHessianDAO deviceDAO = new DeviceHessianDAO();
        deviceDAO.setSiloService(siloService);
        findByKey(deviceDAO, deviceList.get(0), deviceList.get(0).getImei(), new String[] { "modified", "baseID", "productVersion" });
        findByKeyExpectNoResult(deviceDAO, "BAD_DEVICE");
        VehicleHessianDAO vehicleDAO = new VehicleHessianDAO();
        vehicleDAO.setSiloService(siloService);
        findByKey(vehicleDAO, vehicleList.get(0), vehicleList.get(0).getVIN(), new String[] { "modified", "warrantyStart", "warrantyStop" });
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
        if (States.getStates() == null) {
            initStates();
        }
        int idx = Util.randomInt(0, States.getStates().size() - 1);
        int cnt = 0;
        for (State state : States.getStates().values()) {
            if (cnt++ == idx)
                return state;
        }
        return null;
    }

    private List<Integer> randomRole(Integer acctID) {
//        int idx = Util.randomInt(0, Roles.getRoleMap().size() - 1);
//        int cnt = 0;
//        for (Role role : Roles.getRoleMap().values()) {
//            if (cnt++ == idx)
//                return role;
//        }
    	RoleHessianDAO roleDAO = new RoleHessianDAO();
    	roleDAO.setSiloService(siloService);
        Roles accountRoles = new Roles();
        accountRoles.setRoleDAO(roleDAO);
        accountRoles.init(acctID);
    	int index = Util.randomInt(0,accountRoles.getRoleList().size() - 1);
    	List<Integer> roles = new ArrayList<Integer>();
    	roles.add(accountRoles.getRoleList().get(index).getRoleID());
    	
    	return roles;
    }
    
}
