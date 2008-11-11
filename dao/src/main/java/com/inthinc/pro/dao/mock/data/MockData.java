package com.inthinc.pro.dao.mock.data;


import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.hessian.GenericHessianDAO;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.AccountStatus;
import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.AggressiveDrivingEvent;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.DeviceStatus;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.EventMapper;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.RedFlag;
import com.inthinc.pro.model.RedFlagLevel;
import com.inthinc.pro.model.Role;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.SeatBeltEvent;
import com.inthinc.pro.model.SpeedingEvent;
import com.inthinc.pro.model.State;
import com.inthinc.pro.model.TamperingEvent;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.Vehicle;

public class MockData
{
    private static final Logger logger = Logger.getLogger(MockData.class);
    
    // used for unit testing
    public static final Integer TOP_GROUP_ID = 101; 
    public static final Integer REGION_GROUP_ID = 102;
    public static final Integer EMPTY_GROUP_ID = 1;
    
    // if using the unit test group id in unit test, these counters can be used
    public static UnitTestStats unitTestStats = new UnitTestStats();
    
    
    public static final int NUM_ACCOUNTS = 1;
    static final int MAX_GROUPS = 100;
    static final int MAX_DRIVERS_IN_GROUP = 10;
    static final int MAX_VEHICLES_IN_GROUP = 10;
    static final int MAX_USERS_IN_GROUP = 10;
    static final int MAX_TRIPS = 75;
    static final int MAX_ADDRESS = 3;
    static final int MIN_EVENTS = 0;
    static final int MAX_EVENTS = 10;
    static final int MAX_DEVICES = 200;
    
    
    

    static long timeNow = new Date().getTime();
    static int baseTimeSec = DateUtil.convertMillisecondsToSeconds(new Date().getTime());

    static String MST_TZ = "US/Mountain";
    static final String PASSWORD="nuN5q/jdjEpJKKA4A6jLTZufWZfIXtxqzjVjifqFjbGg6tfmQFGXbTtcXtEIg4Z7"; // password

    static Address address[] = { new Address(1, "2200 S 1345 W", "", "Salt Lake City", State.UT, "84119"),
        new Address(2, "3100 W 2249 S", "", "West Valley City", State.UT, "84119"),
        new Address(3, "3601 S 2700 W", "", "West Valley City", State.UT, "84119"), };
    static String addressStr[] = { "2200 S 1345 W, Salt Lake City, UT 84119",
        "3100 W 2249 S West Valley City, UT 84119", "3601 S 2700 W West Valley City, UT 84119", };
    static double lat[] = { 40.723871753812f, 40.704246f, 40.69416956554945f};
    static double lng[] = { -111.92932452647742f, -111.948613f, -111.95694072816069f};
    static String tzName[] =
    {
        "US/Mountain",
        "US/Central",
        "US/Eastern",
        "US/Hawaii",
        "US/Pacific"
    };
    
    private Map<Class<?>, List<Object>> dataMap = new HashMap<Class<?>, List<Object>>();

    //  base all dates in the mock data after this, so that it is easier to unit test
    public Integer dateNow;    
    private static MockData mockData;
    private MockData()
    {
        dateNow = DateUtil.getTodaysDate();
        logger.debug("MockData current time:" + dateNow);
        initializeStaticData();
    }
    
    public static MockData getInstance()
    {
        if (mockData == null)
        {
            mockData = new MockData();
        }
        
        return mockData;
    }

    private void initializeStaticData()
    {
        for (int i = 0; i < NUM_ACCOUNTS; i++)
        {
            Integer accountID = i+1;
            System.out.println("ACCOUNT: " + accountID);
            
            // account
            addAccountData(accountID);
// Un-comment this block if you want to see the data that is generated.            
//          dumpData(companyID);
//
//            logger.debug("-------------------------------------------------------");
//            Group topGroup = lookupObject(Group.class, "groupID", TOP_GROUP_ID);
//            dumpGroupHierarchy(topGroup, "");
//            logger.debug("-------------------------------------------------------");
        }
        
        
        // create an empty group (just for testing)
        Group emptyGroup = new Group(1, NUM_ACCOUNTS, "EMPTY GROUP", 0);
        storeObject(emptyGroup);


    }
    
    //------------ DATA GENERATION METHODS ----------------
    
    private void addAccountData(Integer accountID)
    {
        Account account = new Account(accountID, 0, 0, AccountStatus.ACCOUNT_ACTIVE);
        storeObject(account);
        addGroupData(accountID);
        addDevices(accountID, randomInt(MAX_DEVICES / 2, MAX_DEVICES));
    }
    
    private void addGroupData(Integer accountID)
    {
        Integer idOffset = accountID * MAX_GROUPS;
        // groups in company
        // structure:
        //                                                  United States 
        //              Western                                 Eastern                             Miscellaneous
        //  Montana          Utah        Colorado        New York    New Jersey                     D1 D2 D3
        // T1   T2  T3     D1 D2 D3       D1 D2          T1     T2      D1 D2
        // D1   D1  D1 D2                               D1 D2   D1 D2               
        Group[] groups =
        {
                new Group(idOffset+1, accountID, "United States", 0),       // top level group (executive)
                
                new Group(idOffset+2, accountID, "Western", idOffset+1),    // region level
                new Group(idOffset+3, accountID, "Eastern", idOffset+1),
                new Group(idOffset+4, accountID, "Miscellaneous", idOffset+1),     
                
                new Group(idOffset+5, accountID, "Montana", idOffset+2),    // parent Western
                new Group(idOffset+6, accountID, "Utah", idOffset+2),
                new Group(idOffset+7, accountID, "Colorado", idOffset+2),
                
                new Group(idOffset+8, accountID, "New York", idOffset+3),   // parent Eastern
                new Group(idOffset+9, accountID, "Maine", idOffset+3),
                
                new Group(idOffset+10, accountID, "MT Team 1", idOffset+5), // parent Western/Montana
                new Group(idOffset+11, accountID, "MT Team 2", idOffset+5),
                new Group(idOffset+12, accountID, "MT Team 3", idOffset+5),

                new Group(idOffset+13, accountID, "NY Team 1", idOffset+8), // parent Eastern/New York
                new Group(idOffset+14, accountID, "NY Team 2", idOffset+8),

        };
        
        for (int cnt = 0; cnt < groups.length; cnt++)
        {
            storeObject(groups[cnt]);
            
            // users are people who can log in with various roles
            addUsersToGroup(accountID, groups[cnt].getGroupID());
            
            addScores(groups[cnt].getGroupID(), EntityType.ENTITY_GROUP, groups[cnt].getName());
            
            if (!groupIsParent(groups, groups[cnt].getGroupID()))
            {
                List<Driver> driversInGroup = addDriversToGroup(accountID, groups[cnt].getGroupID(),5);                
//                List<Driver> driversInGroup = addDriversToGroup(accountID, groups[cnt].getGroupID(), randomInt(1, MAX_DRIVERS_IN_GROUP));
                List<Vehicle> vehiclesInGroup = addVehiclesToGroup(accountID, groups[cnt].getGroupID(), randomInt(1, MAX_VEHICLES_IN_GROUP));

                addTripsAndEvents(driversInGroup, vehiclesInGroup, idOffset);

            }
        }
        
        
    }
    


    private void addUsersToGroup(Integer accountID, Integer groupID)
    {
        Integer idOffset = accountID * MAX_GROUPS + groupID * MAX_USERS_IN_GROUP;
        
        User[] users = 
                {
                    createUser(idOffset+1, accountID, groupID, "expired"+groupID, PASSWORD, randomPhone(), randomPhone(), "expired"+groupID+"@email.com", Role.ROLE_NORMAL_USER, Boolean.FALSE),
                    createUser(idOffset+2, accountID, groupID, "custom"+groupID, PASSWORD, randomPhone(), randomPhone(), "custom"+groupID+"@email.com", Role.ROLE_CUSTOM_USER, Boolean.TRUE),
                    createUser(idOffset+3, accountID, groupID, "normal"+groupID, PASSWORD, randomPhone(), randomPhone(), "normal"+groupID+"@email.com", Role.ROLE_NORMAL_USER, Boolean.TRUE),
                    createUser(idOffset+4, accountID, groupID, "readonly"+groupID, PASSWORD, randomPhone(), randomPhone(), "readonly"+groupID+"@email.com", Role.ROLE_READONLY, Boolean.TRUE),
                    createUser(idOffset+5, accountID, groupID, "superuser"+groupID, PASSWORD, randomPhone(), randomPhone(), "superuser"+groupID+"@email.com", Role.ROLE_SUPER_USER, Boolean.TRUE),
                    createUser(idOffset+6, accountID, groupID, "supervisor"+groupID, PASSWORD, randomPhone(), randomPhone(), "supervisor"+groupID+"@email.com", Role.ROLE_SUPERVISOR, Boolean.TRUE)
        };

        for (int userCnt = 0; userCnt < users.length; userCnt++)
        {
            storeObject(users[userCnt]);
            storeObject(users[userCnt].getPerson());
        }
    }

    private User createUser(Integer id, Integer accountID, Integer groupID, String username, String password, String homePhone, String workPhone, String email, Role role,
            Boolean active)
    {
        User user = new User();
        user.setUserID(id);
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        user.setActive(active);
        user.setPerson(new Person());
        user.getPerson().setPersonID(id);
        user.getPerson().setEmpid(String.valueOf(id));
        user.getPerson().setAccountID(accountID);
        user.getPerson().setGroupID(groupID);
        user.getPerson().setHomePhone(homePhone);
        user.getPerson().setWorkPhone(workPhone);
        user.getPerson().setEmail(email);
        user.getPerson().setFirst(username.substring(0, username.length() / 2));
        user.getPerson().setLast(username.substring(username.length() / 2));
        user.getPerson().setUser(user);
        return user;
    }

    private void addScores(Integer entityID, EntityType entityType, String entityName)
    {

        // create in reverse order -- this is a kludge so date range matches correctly

        int[] monthsBack = {
                12, 6, 3, 0
        };
        
        for (int monthsBackCnt = 0; monthsBackCnt < monthsBack.length; monthsBackCnt++)
        {
            for (ScoreType scoreType : EnumSet.allOf(ScoreType.class))
            {
                if (scoreType == ScoreType.SCORE_OVERALL_TIME ) 
                {
                    createOverallScoreOverTime(
                            entityID, 
                            entityType, 
                            entityName, 
                            scoreType, 
                            monthsBack[monthsBackCnt]);
                }
            }
        }
        
        // create a score entity of each score type 1 per month
        for (int month = 0; month < 14; month++)
        {
            for (ScoreType scoreType : EnumSet.allOf(ScoreType.class))
            {
                if (scoreType != ScoreType.SCORE_OVERALL_TIME ) 
                {
                  storeObject(
                      new ScoreableEntity(
                              entityID, 
                              entityType, 
                              entityName, 
                              randomInt(0,50), 
                              DateUtil.getDaysBackDate(dateNow, 30 * month),
                              scoreType));
                }
            }
        }
        
        
    }
    
    private void createOverallScoreOverTime(Integer entityID, 
            EntityType entityType, String entityName, ScoreType scoreType, int monthsBack) {
      //Create dates per range
        for ( int i = 0; i < 30; i++ ) {
            storeObject(
                new ScoreableEntity(entityID, 
                    entityType, 
                    entityName, 
                    randomInt(0,50), 
                    DateUtil.getDaysBackDate(dateNow, 
                            (30 * monthsBack) - i * DateUtil.SECONDS_IN_DAY),
                    scoreType));
        }
    }
    
    
    private List<Driver> addDriversToGroup(Integer accountID, Integer groupID, int numDriversInGroup)
    {
        List<Driver> driverList = new ArrayList<Driver>();
        Integer idOffset = accountID * MAX_GROUPS + groupID * MAX_DRIVERS_IN_GROUP;
        
        for (int i = 0; i < numDriversInGroup; i++)
        {
            int id = idOffset+i+1;
            Driver driver = createDriver(id, accountID, groupID, "John", "Driver"+id);
            storeObject(driver);
            Person person = retrieveObject(Person.class, "personID", id);
            addScores(driver.getDriverID(), EntityType.ENTITY_DRIVER, person.getFirst() + person.getLast());
            driverList.add(driver);
        }
        
        return driverList;
    }

    private void addTripsAndEvents(List<Driver> driverList, List<Vehicle> vehicleList, int idOffset)
    {
        for (Driver driver : driverList)
        {
            int numTrips = randomInt(30, MAX_TRIPS);
            
            int day = 30;
            int minute = 0;
            Integer startDate = hourInDaysBack(day, minute);
            minute+=15;
            Integer endDate = hourInDaysBack(day, minute);
            int dayBreak = (numTrips/day);
            for (int tripCnt = 0; tripCnt < numTrips; tripCnt++)
            {
                // randomly select a vehicle for the trip
                Vehicle vehicle = vehicleList.get(randomInt(0, vehicleList.size()-1));
                int id = idOffset+driver.getDriverID() * MAX_TRIPS + tripCnt;
                int eventIdOffset = id;
    //            Integer vehicleID = idOffset+randomInt(1, numVehicles);
                Integer vehicleID = 0;
                int startAddressIdx = randomInt(0, MAX_ADDRESS - 1);
                int endAddressIdx = randomInt(0, MAX_ADDRESS - 1);
                
                List <LatLng> route = null;
                if (tripCnt == (numTrips-1))
                {
                    if (day != 1)
                    {
                        day = 1;
                        minute = 0;
                    }
                    route = getHardCodedRoute();
                    startDate = hourInDaysBack(day, minute);
                    minute+=15;
                    endDate = hourInDaysBack(day, minute);
                }
                else
                {
                    route = new ArrayList<LatLng>(2);
                    route.add(new LatLng(lat[startAddressIdx], lng[startAddressIdx]));
                    route.add(new LatLng(lat[endAddressIdx], lng[endAddressIdx]));
                }
                Trip trip = new Trip(id, vehicleID, 
                        startDate, endDate, 
                        randomInt(500, 10000), route, addressStr[startAddressIdx], addressStr[endAddressIdx]);
                if (trip.getEndTime() > baseTimeSec)
                        System.out.println("ERROR:: end time excedes base time");
                trip.setDriverID(driver.getDriverID());
                storeObject(trip);
                
                int eventCnt = addEventsAndRedFlagsForTrip(driver, vehicle, trip, eventIdOffset);
    //            addZoneEvent(xml, driverID, vehicleID, trip.getEndLoc());
                eventIdOffset += eventCnt;
                if (tripCnt == (numTrips-1))
                {
                    addWarnings(driver, vehicle, trip.getEndLoc(), eventIdOffset);
                }
                
                
                if (((tripCnt+1) % dayBreak) == 0)
                {
                    day--;
                    if (day < 2) 
                        day = 2;
                    else minute = 0;
                }
                minute+=15;
                startDate = hourInDaysBack(day, minute);
                minute+=15;
                if (minute > 1440)
                    System.out.println("ERROR: minute: " + minute);            
                endDate = hourInDaysBack(day, minute);
            }
        }
    }
    
    private void addWarnings(Driver driver, Vehicle vehicle, LatLng loc, int idOffset)
    {
        Date date = DateUtil.convertTimeInSecondsToDate(baseTimeSec - randomInt(1, 2880));
        Event event =  new Event((long)idOffset+1, vehicle.getVehicleID(), EventMapper.TIWIPRO_EVENT_LOW_BATTERY,
                date,
                randomInt(15, 70), randomInt(10, 50), loc.getLat(), loc.getLng());
        event.setDriverID(driver.getDriverID());
        event.setDriver(driver);
        event.setVehicle(vehicle);
        storeObject(event, Event.class);

        event =  new Event((long)idOffset+2, vehicle.getVehicleID(), EventMapper.TIWIPRO_EVENT_LOW_TIWI_BATTERY,
                date,
                randomInt(15, 70), randomInt(10, 50), loc.getLat(), loc.getLng());
        event.setDriverID(driver.getDriverID());
        event.setDriver(driver);
        event.setVehicle(vehicle);
        storeObject(event, Event.class);

        event =  new TamperingEvent((long)idOffset+3, vehicle.getVehicleID(), EventMapper.TIWIPRO_EVENT_UNPLUGGED,
                date,
                randomInt(15, 70), randomInt(10, 50), loc.getLat(), loc.getLng());
        event.setDriverID(driver.getDriverID());
        event.setDriver(driver);
        event.setVehicle(vehicle);
        storeObject(event, Event.class);

    }
    
    
    private int addEventsAndRedFlagsForTrip(Driver driver, Vehicle vehicle, Trip trip, int idOffset)
    {
        int numEvents = randomInt(MIN_EVENTS, MAX_EVENTS);

        for (int eventCnt = 0; eventCnt < numEvents; eventCnt++)
        {
            int eventCategory = randomInt(1, 3);
            Event event = null;
            Long id = new Long(idOffset+trip.getTripID() * MAX_EVENTS + eventCnt);
            int dateInSeconds = randomInt(trip.getStartTime(), trip.getEndTime());
            Date date = DateUtil.convertTimeInSecondsToDate(dateInSeconds);
            int addressIdx  = randomInt(0, trip.getRoute().size()-1);
            double lat = trip.getRoute().get(addressIdx).getLat();
            double lng = trip.getRoute().get(addressIdx).getLng();
       
            switch (eventCategory) {
            case 1:
                event =  new SeatBeltEvent(id, vehicle.getVehicleID(), EventMapper.TIWIPRO_EVENT_SEATBELT, 
                        date,
                        randomInt(15, 70), randomInt(10, 50), lat, lng, randomInt(50, 70),
                        randomInt(70, 90), randomInt(5, 20));
                break;
            case 2:
                Integer deltaVx = 0;
                Integer deltaVy = 0;
                Integer deltaVz = 0;
                int aggressType = randomInt(1, 4);

                if (aggressType == 1)
                { // HARD_VERT
                    deltaVx = 11;
                    deltaVy = -22;
                    deltaVz = -33;
                }
                else if (aggressType == 2)
                { // HARD_ACCEL
                    deltaVx = 23;
                    deltaVy = -20;
                    deltaVz = 0;
                }
                else if (aggressType == 3)
                { // HARD_BRAKE
                    deltaVx = -25;
                    deltaVy = 22;
                    deltaVz = -13;
                }
                else
                { // HARD_TURN
                    deltaVx = 24;
                    deltaVy = -22;
                    deltaVz = -21;
                }
                
                int severity = randomInt(0, 100);
                event = new AggressiveDrivingEvent(id, vehicle.getVehicleID(), EventMapper.TIWIPRO_EVENT_NOTEEVENT,
                            date,
                        randomInt(15, 70), randomInt(10, 50), lat, lng,
                        randomInt(50, 70), deltaVx, deltaVy, deltaVz, severity);
                break;
            case 3:
                int speedLimit = randomInt(35, 75);
                int avgSpeed = randomInt(speedLimit, 80);
                int topSpeed = randomInt(avgSpeed, 100);
                event = new SpeedingEvent(id, vehicle.getVehicleID(), EventMapper.TIWIPRO_EVENT_SPEEDING_EX3, 
                            date,
                            randomInt(15, 70), randomInt(10, 50), lat, lng, topSpeed, avgSpeed,
                            speedLimit, randomInt(5, 70), randomInt(10, 50));
                break;
            }
            event.setDriverID(driver.getDriverID());
            event.setDriver(driver);
            event.setVehicle(vehicle);
            storeObject(event, Event.class);
            Integer redFlagID = new Integer(idOffset+trip.getTripID() * MAX_EVENTS + eventCnt);
            RedFlag redFlag = new RedFlag(redFlagID, RedFlagLevel.valueOf(randomInt(1,3)), randomInt(0, 1) == 1, false, event);
            
            storeObject(redFlag);
            
            if (driver.getGroupID().equals(UnitTestStats.UNIT_TEST_GROUP_ID))
            {
                unitTestStats.totalRedFlags++;
            }
        }
                
        return numEvents;
    }


    private static List <LatLng>  getHardCodedRoute()
    {

        /* Trip from David Story's house, to Dave Harry's house */
        List <LatLng> route = new ArrayList<LatLng>();
        route.add(new LatLng(33.0089f, -117.1100f)); route.add(new LatLng(33.0095f, -117.1103f));
        route.add(new LatLng(33.0103f, -117.1105f)); route.add(new LatLng(33.0105f, -117.1105f));
        route.add(new LatLng(33.0103f, -117.1113f)); route.add(new LatLng(33.0109f, -117.1117f));
        route.add(new LatLng(33.0117f, -117.1120f)); route.add(new LatLng(33.0127f, -117.1122f));
        route.add(new LatLng(33.0139f, -117.1122f)); route.add(new LatLng(33.0152f, -117.1119f));
        route.add(new LatLng(33.0164f, -117.1115f)); route.add(new LatLng(33.0172f, -117.1124f));
        route.add(new LatLng(33.0183f, -117.1151f)); route.add(new LatLng(33.0187f, -117.1177f));
        route.add(new LatLng(33.0192f, -117.1199f)); route.add(new LatLng(33.0201f, -117.1215f));
        route.add(new LatLng(33.0212f, -117.1235f)); route.add(new LatLng(33.0218f, -117.1252f));
        route.add(new LatLng(33.0219f, -117.1271f)); route.add(new LatLng(33.0219f, -117.1285f));
        route.add(new LatLng(33.0219f, -117.1310f)); route.add(new LatLng(33.0219f, -117.1332f));
        route.add(new LatLng(33.0219f, -117.1356f)); route.add(new LatLng(33.0218f, -117.1380f));
        route.add(new LatLng(33.0218f, -117.1403f)); route.add(new LatLng(33.0217f, -117.1426f));
        route.add(new LatLng(33.0212f, -117.1446f)); route.add(new LatLng(33.0205f, -117.1461f));
        route.add(new LatLng(33.0195f, -117.1475f)); route.add(new LatLng(33.0187f, -117.1481f));
        route.add(new LatLng(33.0175f, -117.1486f)); route.add(new LatLng(33.0159f, -117.1487f));
        route.add(new LatLng(33.0147f, -117.1482f)); route.add(new LatLng(33.0132f, -117.1474f));
        route.add(new LatLng(33.0112f, -117.1466f)); route.add(new LatLng(33.0095f, -117.1458f));
        route.add(new LatLng(33.0079f, -117.1451f)); route.add(new LatLng(33.0066f, -117.1451f));
        route.add(new LatLng(33.0054f, -117.1456f)); route.add(new LatLng(33.0042f, -117.1468f));
        route.add(new LatLng(33.0025f, -117.1486f)); route.add(new LatLng(33.0012f, -117.1506f));
        route.add(new LatLng(33.0004f, -117.1522f)); route.add(new LatLng(32.9990f, -117.1545f));
        route.add(new LatLng(32.9975f, -117.1565f)); route.add(new LatLng(32.9961f, -117.1576f));
        route.add(new LatLng(32.9940f, -117.1583f)); route.add(new LatLng(32.9919f, -117.1590f));
        route.add(new LatLng(32.9903f, -117.1595f)); route.add(new LatLng(32.9882f, -117.1602f));
        route.add(new LatLng(32.9866f, -117.1607f)); route.add(new LatLng(32.9846f, -117.1608f));
        route.add(new LatLng(32.9833f, -117.1604f)); route.add(new LatLng(32.9817f, -117.1593f));
        route.add(new LatLng(32.9806f, -117.1579f)); route.add(new LatLng(32.9799f, -117.1552f));
        route.add(new LatLng(32.9798f, -117.1525f)); route.add(new LatLng(32.9790f, -117.1499f));
        route.add(new LatLng(32.9777f, -117.1482f)); route.add(new LatLng(32.9763f, -117.1466f));
        route.add(new LatLng(32.9755f, -117.1475f)); route.add(new LatLng(32.9743f, -117.1489f));
        route.add(new LatLng(32.9728f, -117.1508f)); route.add(new LatLng(32.9717f, -117.1526f));
        route.add(new LatLng(32.9710f, -117.1562f)); route.add(new LatLng(32.9709f, -117.1595f));
        route.add(new LatLng(32.9707f, -117.1620f)); route.add(new LatLng(32.9700f, -117.1649f));
        route.add(new LatLng(32.9701f, -117.1674f)); route.add(new LatLng(32.9703f, -117.1699f));
        route.add(new LatLng(32.9703f, -117.1721f)); route.add(new LatLng(32.9702f, -117.1750f));
        route.add(new LatLng(32.9691f, -117.1769f)); route.add(new LatLng(32.9682f, -117.1783f));
        route.add(new LatLng(32.9672f, -117.1810f)); route.add(new LatLng(32.9663f, -117.1832f));
        route.add(new LatLng(32.9652f, -117.1862f)); route.add(new LatLng(32.9643f, -117.1891f));
        route.add(new LatLng(32.9628f, -117.1910f)); route.add(new LatLng(32.9611f, -117.1923f));
        route.add(new LatLng(32.9615f, -117.1940f)); route.add(new LatLng(32.9617f, -117.1957f));
        route.add(new LatLng(32.9613f, -117.1981f)); route.add(new LatLng(32.9615f, -117.2009f));
        route.add(new LatLng(32.9621f, -117.2037f)); route.add(new LatLng(32.9619f, -117.2062f));
        route.add(new LatLng(32.9608f, -117.2082f)); route.add(new LatLng(32.9593f, -117.2095f));
        route.add(new LatLng(32.9595f, -117.2101f)); route.add(new LatLng(32.9598f, -117.2108f));
        route.add(new LatLng(32.9601f, -117.2103f)); route.add(new LatLng(32.9603f, -117.2102f)); 
        return route;
    }
    

    private Driver createDriver(Integer id, Integer accountID, Integer groupID, String first, String last)
    {
        Person person = retrieveObject(Person.class, "personID", id);
        if (person == null)
        {
            person = new Person();
            storeObject(person);
        }
        person.setEmpid(String.valueOf(id));
        person.setPersonID(id);
        person.setAccountID(accountID);
        person.setGroupID(groupID);
        person.setFirst(first);
        person.setLast(last);
        person.setHomePhone(randomPhone());
        person.setWorkPhone(randomPhone());
        person.setEmail(first.toLowerCase() + '.' + last.toLowerCase() + "@email.com");
        person.setTimeZone(getRandomTimezone());
        Driver driver = new Driver();
        driver.setDriverID(id);
        driver.setGroupID(groupID);
        driver.setPersonID(person.getPersonID());
        driver.setPerson(person);
        person.setDriver(driver);
        return driver;
    }

    private TimeZone getRandomTimezone()
    {
        int tz = randomInt(0, 4);
        return TimeZone.getTimeZone(tzName[tz]);
    }

    private List<Vehicle> addVehiclesToGroup(Integer accountID, Integer groupID, int numVehiclesInGroup)
    {
        List<Vehicle> vehicleList = new ArrayList<Vehicle>();
        Integer idOffset = accountID * MAX_GROUPS + groupID * MAX_VEHICLES_IN_GROUP;
        
        for (int i = 0; i < numVehiclesInGroup; i++)
        {
            int id = idOffset+i+1;
            Vehicle vehicle = createVehicle(id, accountID, groupID, "Ford", "F" + (randomInt(1, 15) * 1000), "Red", randomInt(5, 50) * 1000, "00000000000000000", "ABC-123", State
                    .values()[randomInt(0, State.values().length - 1)], randomInt(0, 10) < 8);
            storeObject(vehicle);
            addScores(vehicle.getVehicleID(), EntityType.ENTITY_VEHICLE, vehicle.getName());
            vehicleList.add(vehicle);
        }
        return vehicleList;
    }

    private Vehicle createVehicle(int id, Integer accountID, Integer groupID, String make, String model, String color, int weight, String VIN, String license, State state, boolean active)
    {
        final Vehicle vehicle = new Vehicle();
        vehicle.setVehicleID(id);
        vehicle.setAccountID(accountID);
        vehicle.setGroupID(groupID);
        vehicle.setName(String.valueOf(id));
        vehicle.setYear(String.valueOf(randomInt(1970, 2009)));
        vehicle.setMake(make);
        vehicle.setModel(model);
        vehicle.setColor(color);
        vehicle.setWeight(weight);
        vehicle.setVIN(VIN);
        vehicle.setLicense(license);
        vehicle.setState(state);
        vehicle.setActive(active);
        return vehicle;
    }

    private boolean groupIsParent(Group[] groups, Integer groupID)
    {
     
        for (Group group : groups)
        {
            if (group.getParentID().equals(groupID))
                return true;
        }
        return false;
    }

    private List<Device> addDevices(Integer accountID, int numDevices)
    {
        List<Device> deviceList = new ArrayList<Device>();
        Integer idOffset = accountID * MAX_DEVICES;
        
        for (int i = 0; i < numDevices; i++)
        {
            int id = idOffset+i+1;
            Device device = createDevice(id, accountID, randomInt(0, Integer.MAX_VALUE), DeviceStatus.values()[randomInt(0, 2)], "DEVICE" + id, String.valueOf(randomInt(10000,
                    Integer.MAX_VALUE)), String.valueOf(randomInt(10000, Integer.MAX_VALUE)), randomPhone(), randomPhone(), new Date());
            storeObject(device);
            deviceList.add(device);
        }
        return deviceList;
    }

    private Device createDevice(Integer id, Integer accountID, Integer baselineID, DeviceStatus status, String name, String mcmid, String sim, String phone, String ephone,
            Date activated)
    {
        final Device device = new Device();
        device.setDeviceID(id);
        device.setAccountID(accountID);
        device.setBaselineID(baselineID);
        device.setStatus(status);
        device.setName(name);
        device.setMcmid(mcmid);
        device.setSim(sim);
        device.setPhone(phone);
        device.setEphone(ephone);
        device.setActivated(activated);
        return device;
    }


    //------------ UTILITY METHODS ----------------
    static String randomScore()
    {
        int score = randomInt(0, 4);
        int decimal = randomInt(0, 9);
        
        return (score + "." + decimal);
    }
    static int randomInt(int min, int max)
    {
        return (int) (Math.random() * ((max - min) + 1)) + min;
    }

    private static float randomLng()
    {
        // longitude in range -117 to -101
        float base = -101.0f;

        return base + ((float) randomInt(0, 16000) / 1000.0f * -1.0f);
    }

    private static float randomLat()
    {
        // latitude in range 36 to 49
        float base = 36.0f;

        return base + ((float) randomInt(0, 15000) / 1000.0f);
    }

    private static String randomPhone()
    {
        return randomInt(100, 999) + "-" + randomInt(100, 999) + "-" + randomInt(1000, 9999);
    }

    private static Integer hourInDaysBack(int day, int minute)
    {
        int daysBackDate = DateUtil.getDaysBackDate(baseTimeSec, day, MST_TZ);
        return daysBackDate + (minute * DateUtil.SECONDS_IN_MINUTE);
    }

    
    //------------ LOOKUP METHODS ----------------
    public Map<String, Object> lookup(Class clas, String primaryKey, Object searchValue)
    {
        Object obj = retrieveObject(clas, primaryKey, searchValue);
        if (obj != null)
        {
            return GenericHessianDAO.createMapFromObject(obj, true);
        }
        return null;
    }
    public <T> T lookupObject(Class clas, String primaryKey, Object searchValue)
    {
       return (T)retrieveObject(clas, primaryKey, searchValue);
        
    }

    // get full list of a class
    public <T> List<T> lookupObjectList(Class clas, T object)
    {
        return (List<T>)dataMap.get(clas);
    }

    public <T> List<T> lookupObjectList(Class clas, T object, String key, Object value)
    {
        List<T> objList = (List<T>)dataMap.get(clas);
        if (objList != null && objList.size() > 0)
        {
            List<T> returnList = new ArrayList<T>();
            for (Object obj : objList)
            {
                Object fieldValue = getFieldValue(obj, key);
                if (fieldValue.equals(value))
                {
                    returnList.add((T)obj);
                }
            }
            
            return returnList;
        }
        return null;
    }

    public List<Map<String, Object>> lookupList(Class clas)
    {
        List<Object> objList = dataMap.get(clas);
        if (objList != null && objList.size() > 0)
        {
            List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
            for (Object obj : objList)
            {
                returnList.add(GenericHessianDAO.createMapFromObject(obj, true));
            }
            
            return returnList;
        }
        return null;
    }

    // lookup using multiple key/value pairs ANDed together
    public List<Map<String, Object>> lookupList(Class clas, SearchCriteria searchCriteria)
    {
        List<Object> objList = retrieveObjectList(clas, searchCriteria);
        if (objList != null && objList.size() > 0)
        {
            List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
            for (Object obj : objList)
            {
                returnList.add(GenericHessianDAO.createMapFromObject(obj, true));
            }
            
            return returnList;
        }
        return null;
    }


    public Map<String, Object> lookup(Class clas, SearchCriteria searchCriteria)
    {
        Object obj = retrieveObject(clas, searchCriteria);
        if (obj != null)
        {
            return GenericHessianDAO.createMapFromObject(obj, true);
        }
        return null;
    }

    
    
    public <T> void storeObject(T obj)
    {
        storeObject(obj, obj.getClass());

    }
    
    public <T> void storeObject(T obj, Class clazz)
    {
        List<Object> objList = dataMap.get(clazz);
        if (objList == null)
        {
            objList = new ArrayList<Object>();

            dataMap.put(clazz, objList);
        }
        objList.add(obj);

    }

    public <T> T retrieveObject(Class<T> clas, String key, Object value)
    {
        List<Object> objList = dataMap.get(clas);
        if (objList == null)
        {
            return null;
        }

        for (Object obj : objList)
        {
            Object fieldValue = getFieldValue(obj, key);
            if (fieldValue.equals(value))
            {
                return (T) obj;
            }
        }

        return null;
    }

    public <T> T retrieveObject(Class<T> clas, SearchCriteria searchCriteria)
    {
        
        Map<String, Object> searchMap = searchCriteria.getCriteriaMap();
        List<Object> objList = dataMap.get(clas);
        if (objList == null)
        {
            return null;
        }
        for (Object obj : objList)
        {
            boolean isMatch = true;
            for (Map.Entry<String,Object> searchItem : searchMap.entrySet())
            {
                Object fieldValue = getFieldValue(obj, searchItem.getKey().toString());
                
                if (!searchItemMatch(searchItem, fieldValue))
                {
                    isMatch = false;
                    break;
                }
                
            }
            
            if (isMatch)
            {
                return (T)obj;
            }
        }


        return null;
    }
    private boolean searchItemMatch(Entry<String, Object> searchItem, Object fieldValue)
    {
        if (searchItem.getValue() instanceof ValueRange)
        {
            ValueRange valueRange = (ValueRange)searchItem.getValue();
            if (valueRange.low.compareTo(fieldValue) > 0 ||
                    valueRange.high.compareTo(fieldValue) < 0)
            {
                return false;
            }
                    
        }
        else if (searchItem.getValue() instanceof ValueList)
        {
            // or relationship, if any in list match return true
            ValueList valueList = (ValueList)searchItem.getValue();
            for (Object obj : valueList.getObjList())
            {
                if (obj.equals(fieldValue))
                {
                    return true;
                }
            }
            return false;
        }
        else if (!searchItem.getValue().equals(fieldValue))
        {
            return false;
        }
        return true;
    }

    public <T> List<T> retrieveObjectList(Class<T> clas, SearchCriteria searchCriteria)
    {
        List<T> returnObjList = new ArrayList<T>();
        
        Map<String, Object> searchMap = searchCriteria.getCriteriaMap();
        List<Object> objList = dataMap.get(clas);
        if (objList == null)
        {
            return null;
        }
        for (Object obj : objList)
        {
            boolean isMatch = true;
            for (Map.Entry<String,Object> searchItem : searchMap.entrySet())
            {
                
                
                Object fieldValue = null;
                if (searchItem.getKey().indexOf(":") != -1)
                {
                    fieldValue = getFieldValue(obj, searchItem.getKey().split(":"));
                }
                else
                {
                    fieldValue = getFieldValue(obj, searchItem.getKey());
                }
                if (!searchItemMatch(searchItem, fieldValue))
                {
                    isMatch = false;
                    break;
                }
            }
            
            if (isMatch)
            {
                returnObjList.add((T)obj);
            }
        }


        return returnObjList;
    }

    public <T> Object getFieldValue(T object, String field[])
    {
        for (int i = 0; i < field.length; i++)
        {
            Object obj = getFieldValue(object, field[i]);
            if (obj != null)
            {
                object = (T)obj;
            }
        }
        return object;
    }
    public <T> Object getFieldValue(T object, String field)
    {
        Object value = null;

        Class cls = object.getClass();
        BeanInfo beanInfo;
        try
        {
            beanInfo = Introspector.getBeanInfo(object.getClass());
        }
        catch (IntrospectionException e)
        {
            e.printStackTrace();
            return value;
        }

        PropertyDescriptor propertyDescriptors[] = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++)
        {
            String key = propertyDescriptors[i].getName();
            if (!key.equals(field))
                continue;

            Method getMethod = propertyDescriptors[i].getReadMethod();
            try
            {
                value = getMethod.invoke(object);
            }
            catch (IllegalAccessException e)
            {
                System.out.println("IllegalAccessException occured while trying to invoke the method "
                        + getMethod.getName());
                e.printStackTrace();
            }
            catch (InvocationTargetException e)
            {
                System.out.println("InvocationTargetExcpetion occured while trying to invoke the method "
                        + getMethod.getName());
                e.printStackTrace();
            }

            break;
        }

        return value;
    }

    //------------ DEBUG METHODS ----------------
    private void dumpGroupHierarchy(Group topGroup, String indent)
    {
        logger.debug(indent + topGroup.getName() + " (ID: " + topGroup.getGroupID() + ")");
        
        List<User> userList = lookupObjectList(User.class, new User(), "groupID", topGroup.getGroupID());
        for (User user : userList)
        {
            logger.debug(indent + " " + user.getUsername() + " (ID: " + user.getUserID() + ", ROLE: " + user.getRole().toString()+ ")");
        }
        
        for (Group group : lookupObjectList(Group.class, new Group()))
        {
            if (group.getParentID().equals(topGroup.getGroupID()))
            {
                //logger.debug(group.getName() + " (ID: " + group.getGroupID() + ")");
                indent += "    ";
                dumpGroupHierarchy(group, indent);
            }
        }
    }


    private void dumpData(Integer accountID)
    {
        Account account = retrieveObject(Account.class, "accountID", accountID);
        dumpObject(account,"");
        
        // get all groups
        List<Group> groups = lookupObjectList(Group.class, new Group());
        for (Group group : groups)
        {
            dumpObject(group, "  ");
            // get all users in group
            SearchCriteria searchCriteria = new SearchCriteria();
            searchCriteria.addKeyValue("groupID", group.getGroupID());

            List<User> users = retrieveObjectList(User.class, searchCriteria);
            for (User user : users)
            {
                dumpObject(user, "    ");
            }
            List<Driver> drivers = retrieveObjectList(Driver.class, searchCriteria);
            for (Driver driver : drivers)
            {
                dumpObject(driver, "    ");
            }
        }
    }

    private <T> void dumpObject(T obj, String indent)
    {
        Map<String, Object> objMap = GenericHessianDAO.createMapFromObject(obj, true);
        logger.debug(indent + obj.getClass().getName());
        StringBuffer buffer = new StringBuffer(indent);
        for (Map.Entry<String, Object> item : objMap.entrySet())
        {
            buffer.append(item.getKey());
            buffer.append("[");
            buffer.append(item.getValue().toString());
            buffer.append("] ");
        }
            
        logger.debug(buffer);
        
    }

}
