package com.inthinc.pro.dao.mock.data;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.dao.util.MiscUtil;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.AccountAttributes;
import com.inthinc.pro.model.AccountHOSType;
import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.AlertMessage;
import com.inthinc.pro.model.AlertMessageDeliveryType;
import com.inthinc.pro.model.AlertMessageType;

import com.inthinc.pro.model.AlertSentStatus;
import com.inthinc.pro.model.BaseAlert;
import com.inthinc.pro.model.CrashDataPoint;
import com.inthinc.pro.model.CrashReport;
import com.inthinc.pro.model.CrashReportStatus;
import com.inthinc.pro.model.DVQMap;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.DeviceStatus;
import com.inthinc.pro.model.DriveQMap;
import com.inthinc.pro.model.DriveQMetric;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.GQMap;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupType;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.MpgEntity;
import com.inthinc.pro.model.Occurrence;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.QuintileMap;
import com.inthinc.pro.model.RedFlag;
import com.inthinc.pro.model.RedFlagAlert;
import com.inthinc.pro.model.RedFlagLevel;
import com.inthinc.pro.model.ReportSchedule;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.State;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleType;
import com.inthinc.pro.model.Zone;
import com.inthinc.pro.model.configurator.TiwiproSpeedingConstants;
import com.inthinc.pro.model.event.AggressiveDrivingEvent;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.IdleEvent;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.model.event.SeatBeltEvent;
import com.inthinc.pro.model.event.SpeedingEvent;

public class MockData {

    private static final Logger logger = Logger.getLogger(MockData.class);
    // used for unit testing
    public static final Integer TOP_ACCOUNT_ID = 1;
    public static final Integer TOP_GROUP_ID = 101;
    public static final Integer DIVISION_GROUP_ID = 102;
    public static final Integer EMPTY_GROUP_ID = 1;
    // if using the unit test group id in unit test, these counters can be used
    public static UnitTestStats unitTestStats = new UnitTestStats();
    public static final int NUM_ACCOUNTS = 1;
    static final int MAX_GROUPS = 100;
    static final int MAX_REPORT_SCHEDULES = 50;
    static final int MAX_CRASH_REPORTS = 1; //This is called per driver
    static final int MAX_DRIVERS_IN_GROUP = 9;
    static final int MAX_VEHICLES_IN_GROUP = 9;
    static final int MAX_USERS_IN_GROUP = 9;
    static final int MAX_ZONES = 100;
    static final int MAX_ZONE_ALERTS_PER_ZONE = 5;
    static final int MAX_RED_FLAG_PREFS = 100;
    static final int MAX_TRIPS = 75;
    static final int MAX_ADDRESS = 3;
    static final int MIN_EVENTS = 0;
    static final int MAX_EVENTS = 10;
    static final int MAX_DEVICES = 200;
    public static long timeNow = new Date().getTime();
    static int baseTimeSec = DateUtil.convertMillisecondsToSeconds(new Date().getTime());
    static String MST_TZ = "US/Mountain";
    static final String PASSWORD = "nuN5q/jdjEpJKKA4A6jLTZufWZfIXtxqzjVjifqFjbGg6tfmQFGXbTtcXtEIg4Z7"; // password
    static final State UT = MockStates.getByAbbrev("UT");
    static Address address[] = { new Address(1, "2200 S 1345 W", "", "Salt Lake City", UT, "84119", 1), new Address(2, "3100 W 2249 S", "", "West Valley City", UT, "84119", TOP_ACCOUNT_ID),
            new Address(3, "3601 S 2700 W", "", "West Valley City", UT, "84119", TOP_ACCOUNT_ID), };
    static String addressStr[] = { "2200 S 1345 W, Salt Lake City, UT 84119", "3100 W 2249 S West Valley City, UT 84119", "3601 S 2700 W West Valley City, UT 84119"};
    static double lat[] = { 40.723871753812f, 40.704246f, 40.69416956554945f };
    static double lng[] = { -111.92932452647742f, -111.948613f, -111.95694072816069f };
    // static String tzName[] =
    // {
    // "US/Mountain",
    // "US/Central",
    // "US/Eastern",
    // "US/Hawaii",
    // "US/Pacific"
    // };
    private Map<Class<?>, List<Object>> dataMap = new HashMap<Class<?>, List<Object>>();
    // base all dates in the mock data after this, so that it is easier to unit test
    public Date dateNow;
    private static MockData mockData;

    private MockData() {
        dateNow = new Date();
        logger.debug("MockData current time:" + dateNow);
        initializeStaticData();
    }

    public static MockData getInstance() {
        if (mockData == null) {
            mockData = new MockData();
        }
        return mockData;
    }

    private void initializeStaticData() {
        for (int i = 0; i < NUM_ACCOUNTS; i++) {
            Integer accountID = i + 1;
            System.out.println("ACCOUNT: " + accountID);
            // account
            addAccountData(accountID);
            // Un-comment this block if you want to see the data that is generated.
            // dumpData(companyID);
            //
            // logger.debug("-------------------------------------------------------");
            // Group topGroup = lookupObject(Group.class, "groupID", TOP_GROUP_ID);
            // dumpGroupHierarchy(topGroup, "");
            // logger.debug("-------------------------------------------------------");
        }
        // create an empty group (just for testing)
        Group emptyGroup = new Group(1, NUM_ACCOUNTS, "EMPTY GROUP", 0);
        storeObject(emptyGroup);
        // add some messages for testing alert messages
        // un-comment only if testing alert messages
        // addMessages();
    }

    // ------------ DATA GENERATION METHODS ----------------
    private void addMessages() {
        AlertMessage msg = new AlertMessage(1, AlertMessageDeliveryType.EMAIL, AlertMessageType.ALERT_TYPE_HARD_BUMP, "cjennings@inthinc.com",
                "this is a test email message");
        storeObject(msg);
        msg = new AlertMessage(2, AlertMessageDeliveryType.TEXT_MESSAGE, AlertMessageType.ALERT_TYPE_LOW_BATTERY, "8017185958@vtext.com", "this is a test SMS message");
        storeObject(msg);
        msg = new AlertMessage(3, AlertMessageDeliveryType.PHONE, AlertMessageType.ALERT_TYPE_ENTER_ZONE, "8017185958", "this is a test phone message");
        storeObject(msg);
    }

    private void addAccountData(Integer accountID) {
        Account account = new Account(accountID, Status.ACTIVE);
        account.setAcctName("United States Fleet");
        account.setHos(AccountHOSType.HOS_SUPPORT);
        AccountAttributes attribs = new AccountAttributes();
        attribs.setSupportContact1(UnitTestStats.ACCOUNT_CONTACT1);
        attribs.setSupportContact2(UnitTestStats.ACCOUNT_CONTACT2);
        account.setProps(attribs);
        storeObject(account);
        addGroupData(accountID);
        addDevices(accountID, randomInt(MAX_DEVICES / 2, MAX_DEVICES));
        addZones(accountID, randomInt(MAX_ZONES / 2, MAX_ZONES));
        addRedFlagAlerts(accountID, randomInt(MAX_RED_FLAG_PREFS / 2, MAX_RED_FLAG_PREFS));
    }

    private void addGroupData(Integer accountID) {
        Integer idOffset = accountID * MAX_GROUPS;
        // groups in company
        // structure:
        // United States
        // Western Eastern Miscellaneous
        // Montana Utah Colorado New York New Jersey D1 D2 D3
        // T1 T2 T3 D1 D2 D3 D1 D2 T1 T2 D1 D2
        // D1 D1 D1 D2 D1 D2 D1 D2
        Group[] groups = {
                new Group(idOffset + 1, accountID, "United States", 0, GroupType.FLEET, null, "Root Node", 3, new LatLng(44, -111)), // top level group (executive)
                new Group(idOffset + 2, accountID, "Western", idOffset + 1, GroupType.DIVISION, null, "Western", 3, new LatLng(44, -111)), // region level
                new Group(idOffset + 3, accountID, "Eastern", idOffset + 1, GroupType.DIVISION, null, "Eastern Division", 3, new LatLng(44, -111)),
                new Group(idOffset + 4, accountID, "Miscellaneous", idOffset + 1, GroupType.TEAM, null, "Misc", 3, new LatLng(44, -111)),
                new Group(idOffset + 5, accountID, "Montana", idOffset + 2, GroupType.DIVISION, null, "Montana", 3, new LatLng(44, -111)), // parent Western
                new Group(idOffset + 6, accountID, "Utah", idOffset + 2, GroupType.TEAM, null, "Utah", 3, new LatLng(44, -111)),
                new Group(idOffset + 7, accountID, "Colorado", idOffset + 2, GroupType.TEAM, null, "Colorado State", 3, new LatLng(44, -111)),
                new Group(idOffset + 8, accountID, "New York", idOffset + 3, GroupType.DIVISION, null, "New York State", 3, new LatLng(44, -111)), // parent Eastern
                new Group(idOffset + 9, accountID, "Maine", idOffset + 3, GroupType.TEAM, null, "Main State", 3, new LatLng(44, -111)),
                new Group(idOffset + 10, accountID, "MT Team 1", idOffset + 5, GroupType.TEAM, null, "MT Team", 3, new LatLng(44, -111)), // parent Western/Montana
                new Group(idOffset + 11, accountID, "MT Team 2", idOffset + 5, GroupType.TEAM, null, "MT Team", 3, new LatLng(44, -111)),
                new Group(idOffset + 12, accountID, "MT Team 3", idOffset + 5, GroupType.TEAM, null, "MT Team", 3, new LatLng(44, -111)),
                new Group(idOffset + 13, accountID, "NY Team 1", idOffset + 8, GroupType.TEAM, null, "NY Team", 3, new LatLng(44, -111)), // parent Eastern/New York
                new Group(idOffset + 14, accountID, "NY Team 2", idOffset + 8, GroupType.TEAM, null, "NY Team", 3, new LatLng(44, -111)), };
        for (int cnt = 0; cnt < groups.length; cnt++) {
            storeObject(groups[cnt]);
            // users are people who can log in with various roles
            addUsersToGroup(accountID, groups[cnt].getGroupID());
            addScores(groups[cnt].getGroupID(), EntityType.ENTITY_GROUP, groups[cnt].getName());
            addMpg(groups[cnt].getGroupID(), groups[cnt].getName(), groups[cnt].getGroupID());
            addGroupDriveQMap(groups[cnt]);
            addGroupQuintileMaps(groups[cnt]);
            if (!groupIsParent(groups, groups[cnt].getGroupID())) {
                List<Driver> driversInGroup = addDriversToGroup(accountID, groups[cnt].getGroupID(), randomInt(5, MAX_DRIVERS_IN_GROUP));
                if (groups[cnt].getGroupID().equals(UnitTestStats.UNIT_TEST_GROUP_ID)) {
                    unitTestStats.totalDrivers = driversInGroup.size();
                }
                List<Vehicle> vehiclesInGroup = addVehiclesToGroup(accountID, groups[cnt].getGroupID(), randomInt(1, MAX_VEHICLES_IN_GROUP));
                addTripsAndEvents(driversInGroup, vehiclesInGroup, idOffset);
                // newer mock scores
                addDriveQMaps(driversInGroup, vehiclesInGroup);
            }
        }
    }

    private void addGroupQuintileMaps(Group group) {
        QuintileMap qMap = new QuintileMap();
        for (int i = DriveQMetric.DRIVEQMETRIC_MIN; i <= DriveQMetric.DRIVEQMETRIC_MAX; i++) {
            for (Duration duration : EnumSet.allOf(Duration.class)) {
                MockQuintileMap mockQuintileMap = new MockQuintileMap(randomQuintileMap(), group.getGroupID(), i, duration.getCode());
                storeObject(mockQuintileMap);
            }
        }
    }

    private QuintileMap randomQuintileMap() {
        QuintileMap qMap = new QuintileMap();
        qMap.setPercent1(randomInt(0, 25));
        qMap.setPercent2(randomInt(0, 25));
        qMap.setPercent3(randomInt(0, 25));
        qMap.setPercent4(randomInt(0, 25));
        qMap.setPercent5(100 - (qMap.getPercent1() + qMap.getPercent2() + qMap.getPercent3() + qMap.getPercent4()));
        return qMap;
    }

    private void addGroupDriveQMap(Group group) {
        GQMap gqMap = new GQMap();
        Long odometer = randomLong(500, 5000);
        gqMap.setDriveQ(createDriveQ(odometer));
        gqMap.setGroup(group);
        storeObject(gqMap);
    }

    private void addDriveQMaps(List<Driver> driversInGroup, List<Vehicle> vehiclesInGroup) {
        logger.debug("addDriveQMaps");
        for (Driver driver : driversInGroup) {
            Long odometer = randomLong(500, 5000);
            for (int i = 0; i < 30; i++) {
                DVQMap dvqMap = new DVQMap();
                dvqMap.setDriver(driver);
                dvqMap.setVehicle(vehiclesInGroup.get(randomInt(0, vehiclesInGroup.size() - 1)));
                dvqMap.setDriveQ(createDriveQ(odometer));
                odometer += 100;
                storeObject(dvqMap);
            }
        }
    }

    private DriveQMap createDriveQ(Long odometer) {
        DriveQMap driveQMap = new DriveQMap();
        driveQMap.setCreated(new Date()); // NEED TO CREATE OLD DATES
        driveQMap.setAggressiveAccel(randomInt(0, 50));
        driveQMap.setAggressiveBrake(randomInt(0, 50));
        driveQMap.setAggressiveBump(randomInt(0, 50));
        driveQMap.setAggressiveLeft(randomInt(0, 50));
        driveQMap.setAggressiveRight(randomInt(0, 50));
        driveQMap.setAggressiveTurn((driveQMap.getAggressiveLeft() + driveQMap.getAggressiveRight()) / 2);
        driveQMap.setDrivingStyle((driveQMap.getAggressiveAccel() + driveQMap.getAggressiveBrake() + driveQMap.getAggressiveBump() + driveQMap.getAggressiveTurn()) / 4);
        driveQMap.setCoaching(randomInt(0, 50));
        driveQMap.setDriveTime(randomLong(1, 5000));
        driveQMap.setIdleHi(randomLong(0, 50));
        driveQMap.setIdleLo(randomLong(0, 50));
        driveQMap.setStartingOdometer(odometer);
        driveQMap.setEndingOdometer(odometer + 100);
        driveQMap.setOdometer(odometer);
        driveQMap.setMpgLight(randomInt(20, 30));
        driveQMap.setMpgMedium(randomInt(15, 25));
        driveQMap.setMpgHeavy(randomInt(10, 20));
        driveQMap.setSeatbelt(randomInt(0, 50));
        driveQMap.setSpeeding1(randomInt(0, 50));
        driveQMap.setSpeeding2(randomInt(0, 50));
        driveQMap.setSpeeding3(randomInt(0, 50));
        driveQMap.setSpeeding4(randomInt(0, 50));
        driveQMap.setSpeeding5(randomInt(0, 50));
        driveQMap.setSpeeding((driveQMap.getSpeeding1() + driveQMap.getSpeeding2() + driveQMap.getSpeeding3() + driveQMap.getSpeeding4() + driveQMap.getSpeeding5()) / 5);
        driveQMap.setOverall((driveQMap.getDrivingStyle() + driveQMap.getSeatbelt() + driveQMap.getSpeeding()) / 3);
        driveQMap.setCrashEvents(randomInt(0,10));
        driveQMap.setCrashTotal(randomInt(10,50));
        driveQMap.setCrashOdometer(odometer/2);
        driveQMap.setCrashDays(randomInt(0, 10));
        driveQMap.setSpeedOdometer1(randomLong(0, 100));
        driveQMap.setSpeedOdometer2(randomLong(0, 100));
        driveQMap.setSpeedOdometer3(randomLong(0, 100));
        driveQMap.setSpeedOdometer4(randomLong(0, 100));
        driveQMap.setSpeedOdometer5(randomLong(0, 100));
        driveQMap.setSpeedOdometer(	driveQMap.getSpeedOdometer1().longValue() + driveQMap.getSpeedOdometer2().longValue() + 
        							driveQMap.getSpeedOdometer3().longValue() + driveQMap.getSpeedOdometer4().longValue() + 
        							driveQMap.getSpeedOdometer5().longValue());
        return driveQMap;
    }

    private void addUsersToGroup(Integer accountID, Integer groupID) {
        Integer idOffset = accountID * MAX_GROUPS + groupID * MAX_USERS_IN_GROUP;
   		List<Integer> normalRoles = new ArrayList<Integer>();
   		normalRoles.add(MockRoles.getNormalUser().getRoleID());
   		List<Integer> adminRoles = new ArrayList<Integer>();
   		adminRoles.add(MockRoles.getAdminUser().getRoleID());
        User[] users = {
                /*
                 * TODO: roles stuff isn't complete -- deal with it when we add permissions allRoles.put(key, new Role(key++, "readOnly")); allRoles.put(key, new Role(key++,
                 * "normalUser")); allRoles.put(key, new Role(key++, "supervisor")); allRoles.put(key, new Role(key++, "customUser")); allRoles.put(key, new Role(key++,
                 * "superUser"));
                 */
                 createUser(idOffset + 1, accountID, groupID, "expired" + groupID, PASSWORD, randomPhone(), randomPhone(), "expired" + groupID + "@email.com", normalRoles,
                         Boolean.FALSE),
                createUser(idOffset + 2, accountID, groupID, "custom" + groupID, PASSWORD, randomPhone(), randomPhone(), "custom" + groupID + "@email.com", adminRoles,
                         Boolean.TRUE),
                createUser(idOffset + 3, accountID, groupID, "normal" + groupID, PASSWORD, randomPhone(), randomPhone(), "normal" + groupID + "@email.com", normalRoles,
                         Boolean.TRUE),
                createUser(idOffset + 4, accountID, groupID, "readonly" + groupID, PASSWORD, randomPhone(), randomPhone(), "readonly" + groupID + "@email.com", normalRoles,
                         Boolean.TRUE),
                createUser(idOffset + 5, accountID, groupID, "superuser" + groupID, PASSWORD, randomPhone(), randomPhone(), "superuser" + groupID + "@email.com", adminRoles,
                         Boolean.TRUE),
                createUser(idOffset + 6, accountID, groupID, "supervisor" + groupID, PASSWORD, randomPhone(), randomPhone(), "supervisor" + groupID + "@email.com", adminRoles,
                         Boolean.TRUE), 
                createUser(idOffset + 7, accountID, groupID, "normal7" + groupID, PASSWORD, randomPhone(), randomPhone(), "normal7" + groupID + "@email.com", normalRoles,
                                 Boolean.TRUE),
                createUser(idOffset + 8, accountID, groupID, "normal8" + groupID, PASSWORD, randomPhone(), randomPhone(), "normal8" + groupID + "@email.com", normalRoles,
                                         Boolean.TRUE),
                createUser(idOffset + 9, accountID, groupID, "normal9" + groupID, PASSWORD, randomPhone(), randomPhone(), "normal9" + groupID + "@email.com", normalRoles,
                                                 Boolean.TRUE),
                };
        for (int userCnt = 0; userCnt < users.length; userCnt++) {
            storeObject(users[userCnt]);
            storeObject(users[userCnt].getPerson());
            addReportSchedules(accountID, users[userCnt]);
        }
    }

    private User createUser(Integer id, Integer accountID, Integer groupID, String username, String password, String priPhone, String secPhone, String email, List<Integer> roles,
            Boolean active) {
        User user = new User();
        user.setUserID(id);
        user.setGroupID(groupID);
        user.setUsername(username);
        user.setPassword(password);
        user.setRoles(roles);
        user.setStatus(Status.ACTIVE);
        user.setPerson(new Person());
        user.getPerson().setAcctID(accountID);
        user.getPerson().setPersonID(id);
        user.getPerson().setEmpid(String.valueOf(id));
        user.getPerson().setPriPhone(priPhone);
        user.getPerson().setSecPhone(secPhone);
        user.getPerson().setPriEmail(email);
        user.getPerson().setFirst(username.substring(0, username.length() / 2));
        user.getPerson().setLast(username.substring(username.length() / 2));
        user.getPerson().setTimeZone(TimeZone.getTimeZone("MST"));
        user.getPerson().setMeasurementType(MeasurementType.ENGLISH);
        user.getPerson().setUser(user);
        return user;
    }

    private void addReportSchedules(Integer acctID, User user) {
        Integer idOffset = acctID * MAX_REPORT_SCHEDULES + user.getUserID() * MAX_REPORT_SCHEDULES;
        for (int i = 0; i < MAX_REPORT_SCHEDULES; i++) {
            String name = "Report Schedule " + Integer.valueOf(i);
            ReportSchedule reportSchedule = createReportSchedule(idOffset++, acctID, user.getUserID(), 1, user.getUsername() + "@inthinc.com", 5, name, Calendar.getInstance()
                    .getTime(), Calendar.getInstance().getTime());
            storeObject(reportSchedule);
        }
    }

    private ReportSchedule createReportSchedule(Integer reportScheduleID, Integer acctID, Integer userID, Integer reportID, String emailTo, Integer durationID, String name,
            Date startDate, Date endDate) {
        ReportSchedule reportSchedule = new ReportSchedule();
        reportSchedule.setAccountID(acctID);
        reportSchedule.setReportScheduleID(reportScheduleID);
        reportSchedule.setUserID(userID);
        reportSchedule.setReportID(reportID);
        List<String> emailToList = new ArrayList<String>();
        emailToList.add(emailTo);
        reportSchedule.setEmailTo(emailToList);
        reportSchedule.setEndDate(endDate);
        reportSchedule.setStartDate(startDate);
        reportSchedule.setReportDuration(Duration.DAYS);
        reportSchedule.setOccurrence(Occurrence.WEEKLY);
        reportSchedule.setName(name);
        List<Boolean> booleanList = new ArrayList<Boolean>();
        booleanList.add(Boolean.TRUE);
        booleanList.add(Boolean.FALSE);
        booleanList.add(Boolean.TRUE);
        booleanList.add(Boolean.FALSE);
        booleanList.add(Boolean.TRUE);
        booleanList.add(Boolean.FALSE);
        booleanList.add(Boolean.TRUE);
        reportSchedule.setDayOfWeek(booleanList);
        reportSchedule.setTimeOfDay(360);
        return reportSchedule;
    }

    
    private void addScores(Integer entityID, EntityType entityType, String entityName) {
        addScores(entityID, entityType, entityName, 0);
    }

    private void addScores(Integer entityID, EntityType entityType, String entityName, Integer groupID) {
        // create in reverse order -- this is a kludge so date range matches correctly
        int[] monthsBack = { 12, 6, 3, 0 };
        // create a score entity of each score type 1 per month
        for (int month = 0; month < 14; month++) {
            for (ScoreType scoreType : EnumSet.allOf(ScoreType.class)) {
                // if (scoreType != ScoreType.SCORE_OVERALL_TIME )
                {
                    Date daysBack = DateUtil.getDaysBackDate(dateNow, 30 * month);
                    daysBack.setTime(daysBack.getTime() - 1000);
                    ScoreableEntity scoreableEntity = new ScoreableEntity(entityID, entityType, entityName, randomInt(0, 50), daysBack, scoreType);
                    // if (scoreType.equals(ScoreType.SCORE_DRIVING_STYLE_HARD_ACCEL) && month == 0)
                    // {
                    // System.out.println(DateUtil.getDaysBackDate(dateNow, 30 * month));
                    // }
                    storeObject(scoreableEntity);
                    if (groupID.equals(UnitTestStats.UNIT_TEST_GROUP_ID) && month == 0 && scoreType.getSubTypes() == null) {
                        unitTestStats.addToTotalScore(scoreType, scoreableEntity.getScore());
                    }
                }
            }
        }
    }

    private void createOverallScoreOverTime(Integer entityID, EntityType entityType, String entityName, ScoreType scoreType, int monthsBack) {
        // Create dates per range
        for (int i = 0; i < 30; i++) {
            Date daysBack = DateUtil.getDaysBackDate(dateNow, (30 * monthsBack) - i * DateUtil.SECONDS_IN_DAY);
            storeObject(new ScoreableEntity(entityID, entityType, entityName, randomInt(0, 50), daysBack, scoreType));
        }
    }

    private void addMpg(Integer entityID, String entityName, Integer groupID) {
        int[] monthsBack = { 12, 6, 3, 0 };
        for (int monthsBackCnt = 0; monthsBackCnt < monthsBack.length; monthsBackCnt++) {
            Date daysBack = DateUtil.getDaysBackDate(dateNow, monthsBack[monthsBackCnt] * 30);
            daysBack.setTime(daysBack.getTime() - 1000);
            MpgEntity mapEntity = new MpgEntity(entityID, entityName, groupID, randomInt(0, 50), randomInt(0, 50), randomInt(0, 50), daysBack);
            storeObject(mapEntity);
        }
    }

    private List<Driver> addDriversToGroup(Integer accountID, Integer groupID, int numDriversInGroup) {
        List<Driver> driverList = new ArrayList<Driver>();
        Integer idOffset = accountID * MAX_GROUPS + groupID * MAX_DRIVERS_IN_GROUP;
        for (int i = 0; i < numDriversInGroup; i++) {
            int id = idOffset + i + 1;
            Driver driver = createDriver(id, accountID, groupID, "John", "Driver" + id);
            storeObject(driver);
            Person person = retrieveObject(Person.class, "personID", id);
            addScores(driver.getDriverID(), EntityType.ENTITY_DRIVER, person.getFirst() + person.getLast(), groupID);
            addMpg(driver.getDriverID(), person.getFirst() + person.getLast(), groupID);
            driverList.add(driver);
        }
        if (groupID.equals(UnitTestStats.UNIT_TEST_GROUP_ID)) {
            unitTestStats.fixupTotalScores();
        }
        return driverList;
    }

    private void addTripsAndEvents(List<Driver> driverList, List<Vehicle> vehicleList, int idOffset) {
        for (Driver driver : driverList) {
            int numTrips = randomInt(30, MAX_TRIPS);
            int day = 30;
            int minute = 0;
            Date startDate = DateUtil.convertTimeInSecondsToDate(hourInDaysBack(day, minute));
            minute += 15;
            Date endDate = DateUtil.convertTimeInSecondsToDate(hourInDaysBack(day, minute));
            int dayBreak = (numTrips / day);
            if (driver.getDriverID().equals(UnitTestStats.UNIT_TEST_DRIVER_ID)) {
                unitTestStats.totalTripsForDriver = numTrips;
            }
            for (int tripCnt = 0; tripCnt < numTrips; tripCnt++) {
                // randomly select a vehicle for the trip
                Vehicle vehicle = vehicleList.get(randomInt(0, vehicleList.size() - 1));
                int id = idOffset + driver.getDriverID() * MAX_TRIPS + tripCnt;
                int eventIdOffset = id;
                // Integer vehicleID = idOffset+randomInt(1, numVehicles);
                // Integer vehicleID = 0;
                int startAddressIdx = randomInt(0, MAX_ADDRESS - 1);
                int endAddressIdx = randomInt(0, MAX_ADDRESS - 1);
                List<LatLng> route = null;
                if (tripCnt == (numTrips - 1)) {
                    if (day != 1) {
                        day = 1;
                        minute = 0;
                    }
                    route = getHardCodedRoute();
                    startDate = DateUtil.convertTimeInSecondsToDate(hourInDaysBack(day, minute));
                    minute += 15;
                    endDate = DateUtil.convertTimeInSecondsToDate(hourInDaysBack(day, minute));
                }
                else {
                    int routeLen = 3;
                    route = new ArrayList<LatLng>(2);
                    for (int r = 0; r < routeLen; r++) {
                        route.add(new LatLng(randomLat(), randomLng()));
                    }
                }
                Trip trip = new Trip(Long.valueOf(id), vehicle.getVehicleID(), startDate, endDate, randomInt(500, 10000), route, addressStr[startAddressIdx], addressStr[endAddressIdx]);
                trip.setDriverID(driver.getDriverID());
                int eventCnt = addEventsAndRedFlagsForTrip(driver, vehicle, trip, eventIdOffset);
                if (tripCnt == numTrips - 2) {
                    LastLocation lastLoc = new LastLocation();
                    int routeCount = trip.getRoute().size();
                    lastLoc.setLoc(trip.getRoute().get(routeCount - 1));
                    lastLoc.setTime(trip.getEndTime());
                    lastLoc.setDriverID(trip.getDriverID());
                    lastLoc.setVehicleID(trip.getVehicleID());
                    storeObject(lastLoc);
                }
                storeObject(trip);
                // addZoneEvent(xml, driverID, vehicleID, trip.getEndLoc());
                eventIdOffset += eventCnt;
                if (tripCnt == (numTrips - 1)) {
                    int warningCnt = addWarnings(driver, vehicle, trip.getEndLoc(), eventIdOffset);
                    addCrashReports(driver.getPerson().getAcctID(), driver.getPerson(), retrieveObject(Group.class,"groupID",driver.getGroupID()), vehicle);
                    if (driver.getDriverID().equals(UnitTestStats.UNIT_TEST_DRIVER_ID)) {
                        unitTestStats.totalEventsInLastTripForDriver = eventCnt;
                    }
                }
                if (((tripCnt + 1) % dayBreak) == 0) {
                    day--;
                    if (day < 2)
                        day = 2;
                    else
                        minute = 0;
                }
                minute += 15;
                startDate = DateUtil.convertTimeInSecondsToDate(hourInDaysBack(day, minute));
                minute += 15;
                if (minute > 1440)
                    System.out.println("ERROR: minute: " + minute);
                endDate = DateUtil.convertTimeInSecondsToDate(hourInDaysBack(day, minute));
            }
        }
    }
    
    private void addCrashReports(Integer acctID, Person person, Group group, Vehicle vehicle) {
        if(acctID == null || person == null)
            return;
        Integer idOffset = acctID * MAX_CRASH_REPORTS + person.getPersonID() * MAX_CRASH_REPORTS;
        for (int i = 0; i < MAX_CRASH_REPORTS; i++) {
            String name = "Report Schedule " + Integer.valueOf(i);
            CrashReport crashReport = createCrashReport(idOffset, acctID, group, person, new Date(), new LatLng(40.7109991d,-111.9928979d), vehicle);
            storeObject(crashReport);
        }
    }

    private CrashReport createCrashReport(Integer crashReportID, Integer accountID, Group group, Person person, Date date, LatLng latLng, Vehicle vehicle) {
        CrashReport crashReport = new CrashReport(accountID, CrashReportStatus.NEW, vehicle, person.getDriver(), "Sunny",4);
        crashReport.setCrashReportID(crashReportID);
        crashReport.setDate(date);
        crashReport.setLatLng(latLng);
        crashReport.setDescription("Description Place Holder: This is where a user would write any further description of the crash that is not captured by the data we collect");
        
        List<CrashDataPoint> crashDataPointList = new ArrayList<CrashDataPoint>();
        for(int i = 10; i > 0; i--) {
            CrashDataPoint point = new CrashDataPoint();
            point.setTime(new Date());
            point.setGpsSpeed(76);
            point.setObdSpeed(70);
            point.setSeatBeltAvailable(false);
            point.setSeatBeltState(false);
            point.setRpm(5800);
            point.setLatLng(new LatLng((40.7109991d + (i * 0.75)),(-111.9928979d) + (i * 0.75)));
            crashDataPointList.add(point);
        }
        crashReport.setCrashDataPoints(crashDataPointList);
        
        return crashReport;
    }


    private int addWarnings(Driver driver, Vehicle vehicle, LatLng loc, int idOffset) {
        Date date = DateUtil.convertTimeInSecondsToDate(baseTimeSec - randomInt(1, 2880));
        Event event = new Event((long) idOffset + 1, vehicle.getVehicleID(), NoteType.LOW_BATTERY, date, randomInt(15, 70), randomInt(10, 50), loc
                .getLat(), loc.getLng());
        event.setDriverID(driver.getDriverID());
        event.setDriver(driver);
        event.setVehicle(vehicle);
        event.setAddressStr(addressStr[randomInt(0, 2)]);
        event.setGroupID(UnitTestStats.UNIT_TEST_GROUP_ID);
        storeObject(event, Event.class);
        RedFlag redFlag = new RedFlag(idOffset + 1, RedFlagLevel.valueOf(randomInt(1, 3)), AlertSentStatus.valueOf(randomInt(0, 2)), event, driver.getPerson().getTimeZone());
        storeObject(redFlag);
        if (driver.getGroupID().equals(UnitTestStats.UNIT_TEST_GROUP_ID)) {
            unitTestStats.totalRedFlags++;
            unitTestStats.totalWarningRedFlags++;
        }
        event = new Event((long) idOffset + 2, vehicle.getVehicleID(), NoteType.LOW_TIWI_BATTERY, date, randomInt(15, 70), randomInt(10, 50), loc
                .getLat(), loc.getLng());
        event.setDriverID(driver.getDriverID());
        event.setDriver(driver);
        event.setVehicle(vehicle);
        event.setGroupID(UnitTestStats.UNIT_TEST_GROUP_ID);
        event.setAddressStr(addressStr[randomInt(0, 2)]);
        storeObject(event, Event.class);
        redFlag = new RedFlag(idOffset + 2, RedFlagLevel.valueOf(randomInt(1, 3)), AlertSentStatus.valueOf(randomInt(0, 2)), event, driver.getPerson().getTimeZone());
        storeObject(redFlag);
        if (driver.getGroupID().equals(UnitTestStats.UNIT_TEST_GROUP_ID)) {
            unitTestStats.totalRedFlags++;
            unitTestStats.totalWarningRedFlags++;
        }
        event = new Event((long) idOffset + 3, vehicle.getVehicleID(), NoteType.UNPLUGGED, date, randomInt(15, 70), randomInt(10, 50), loc.getLat(), loc
                .getLng());
        event.setDriverID(driver.getDriverID());
        event.setDriver(driver);
        event.setVehicle(vehicle);
        event.setAddressStr(addressStr[randomInt(0, 2)]);
        event.setGroupID(UnitTestStats.UNIT_TEST_GROUP_ID);
        storeObject(event, Event.class);
        redFlag = new RedFlag(idOffset + 3, RedFlagLevel.valueOf(randomInt(1, 3)), AlertSentStatus.valueOf(randomInt(0, 2)), event, driver.getPerson().getTimeZone());
        storeObject(redFlag);
        if (driver.getGroupID().equals(UnitTestStats.UNIT_TEST_GROUP_ID)) {
            unitTestStats.totalRedFlags++;
            unitTestStats.totalWarningRedFlags++;
        }
        return 3;
    }

    private int addEventsAndRedFlagsForTrip(Driver driver, Vehicle vehicle, Trip trip, int idOffset) {
        int numEvents = randomInt(MIN_EVENTS, MAX_EVENTS);
        boolean firstSpeeding = true;
        trip.setEvents(new ArrayList<Event>());
        int eventCategory;
        for (int eventCnt = 0; eventCnt < numEvents; eventCnt++) {
        	
        	if((eventCnt == (numEvents -1)) && firstSpeeding){
        		
        		eventCategory = 3;
        	}
        	else {
        		
        		eventCategory= randomInt(1, 4);
        	}
            Event event = null;
            Long id = new Long(idOffset + trip.getTripID() * MAX_EVENTS + eventCnt);
            int dateInSeconds = randomInt((int) DateUtil.convertDateToSeconds(trip.getStartTime()), (int) DateUtil.convertDateToSeconds(trip.getEndTime()));
            Date date = DateUtil.convertTimeInSecondsToDate(dateInSeconds);
            int addressIdx = randomInt(0, trip.getRoute().size() - 1);
            double lat = trip.getRoute().get(addressIdx).getLat();
            double lng = trip.getRoute().get(addressIdx).getLng();
            switch (eventCategory) {
                case 1:
                    event = new SeatBeltEvent(id, vehicle.getVehicleID(), NoteType.SEATBELT, date, randomInt(15, 70), randomInt(10, 50), lat, lng, randomInt(50,
                            70), randomInt(70, 90), randomInt(5, 20));
                    event.setAddressStr(addressStr[randomInt(0, 2)]);
                    event.setGroupID(UnitTestStats.UNIT_TEST_GROUP_ID);
                    break;
                case 2:
                    Integer deltaVx = 0;
                    Integer deltaVy = 0;
                    Integer deltaVz = 0;
                    int aggressType = randomInt(1, 4);
                    if (aggressType == 1) { // HARD_VERT
                        deltaVx = 11;
                        deltaVy = -22;
                        deltaVz = -33;
                    }
                    else if (aggressType == 2) { // HARD_ACCEL
                        deltaVx = 23;
                        deltaVy = -20;
                        deltaVz = 0;
                    }
                    else if (aggressType == 3) { // HARD_BRAKE
                        deltaVx = -25;
                        deltaVy = 22;
                        deltaVz = -13;
                    }
                    else { // HARD_TURN
                        deltaVx = 24;
                        deltaVy = -22;
                        deltaVz = -21;
                    }
                    int severity = randomInt(1, 5);
                    event = new AggressiveDrivingEvent(id, vehicle.getVehicleID(), NoteType.NOTEEVENT, date, randomInt(15, 70), randomInt(10, 50), lat, lng,
                            randomInt(50, 70), deltaVx, deltaVy, deltaVz, severity);
                    event.setAddressStr(addressStr[randomInt(0, 2)]);
                    break;
                case 3:
                	int speedLimit,avgSpeed,topSpeed;
                	if (firstSpeeding){
                		speedLimit = 0;
                		avgSpeed = 0;
                		topSpeed = 0;
                		firstSpeeding = false;
                	}
                	else {
                    	speedLimit = randomInt(35, 75);
                    	avgSpeed = randomInt(speedLimit, 80);
                    	topSpeed = randomInt(avgSpeed, 100);
                	}
                    event = new SpeedingEvent(id, vehicle.getVehicleID(), NoteType.SPEEDING_EX3, date, randomInt(15, 70), randomInt(10, 50), lat, lng, topSpeed,
                            avgSpeed, speedLimit, randomInt(5, 70), randomInt(10, 50));
                    event.setAddressStr(addressStr[randomInt(0, 2)]);
                    break;
                case 4:
                    int lowIdle = randomInt(55, 500);
                    int highIdle = randomInt(55, 200);
                    event = new IdleEvent(id, vehicle.getVehicleID(), NoteType.IDLE, date, randomInt(15, 70), randomInt(10, 50), lat, lng, lowIdle, highIdle);
                    event.setAddressStr(addressStr[randomInt(0, 2)]);
                    break;
            }
            event.setDriverID(driver.getDriverID());
            event.setDriver(driver);
            event.setVehicle(vehicle);
            event.setGroupID(UnitTestStats.UNIT_TEST_GROUP_ID);
            storeObject(event, Event.class);
            Integer redFlagID = new Integer(idOffset * MAX_EVENTS + eventCnt);
            RedFlag redFlag = new RedFlag(redFlagID, RedFlagLevel.valueOf(randomInt(1, 3)), AlertSentStatus.valueOf(randomInt(0, 2)), event, driver.getPerson().getTimeZone());
            storeObject(redFlag);
            if (driver.getGroupID().equals(UnitTestStats.UNIT_TEST_GROUP_ID)) {
                unitTestStats.totalRedFlags++;
            }
            trip.getEvents().add(event);
        }
        return numEvents;
    }

    private List<Zone> addZones(Integer accountID, int numZones) {
        List<Zone> zoneList = new ArrayList<Zone>();
        Integer idOffset = accountID * MAX_ZONES;
        for (int i = 0; i < numZones; i++) {
            int id = idOffset + i + 1;
            Zone zone = new Zone();
            zone.setAccountID(accountID);
            zone.setZoneID(id);
            zone.setCreated(new Date());
            zone.setName("Zone" + id);
            zone.setAddress("4225 West Lake Park Blvd. Suite 100 West Valley City UT 84120");
            addPoints(zone, new LatLng(40.711435, -111.991518));
            storeObject(zone);
            zoneList.add(zone);
            addZoneAlertsToZone(id, accountID, randomInt(1, MAX_ZONE_ALERTS_PER_ZONE));
        }
        return zoneList;
    }

    private void addPoints(Zone zone, LatLng center) {
        final ArrayList<LatLng> points = new ArrayList<LatLng>();
        for (int i = 0; i < randomInt(3, 9); i++)
            points.add(new LatLng(randomLat(), randomLng()));
        points.add(new LatLng(points.get(0).getLat(), points.get(0).getLng()));
        zone.setPoints(points);
    }

    private List<RedFlagAlert> addZoneAlertsToZone(Integer zoneID, Integer accountID, int numZoneAlerts) {
        final List<RedFlagAlert> alerts = new ArrayList<RedFlagAlert>();
        final Integer idOffset = accountID * MAX_ZONES + zoneID * MAX_ZONE_ALERTS_PER_ZONE;
        for (int i = 0; i < numZoneAlerts; i++) {
            int id = idOffset + i + 1;
            final RedFlagAlert alert = new RedFlagAlert();
            alert.setAccountID(accountID);
            alert.setZoneID(zoneID);
            alert.setAlertID(id);
            alert.setCreated(new Date());
            alert.setName("Zone Alert " + id);
            alert.setDescription("Toolin' around the zone");
            final ArrayList<Boolean> dayOfWeek = new ArrayList<Boolean>();
            boolean pickedOne = false;
            for (int j = 0; j < 7; j++) {
                final boolean day = randomInt(0, 1) == 1 ? true : false;
                dayOfWeek.add(day);
                pickedOne |= day;
            }
            if (!pickedOne)
                dayOfWeek.set(0, true);
            alert.setDayOfWeek(dayOfWeek);
            if (randomInt(0, 1) == 1) {
                alert.setStartTOD(randomInt(RedFlagAlert.MIN_TOD, RedFlagAlert.MAX_TOD));
                alert.setStopTOD(randomInt(RedFlagAlert.MIN_TOD, RedFlagAlert.MAX_TOD));
            }
            Set<AlertMessageType> types = EnumSet.noneOf(AlertMessageType.class);
            if ( randomInt(0, 1)==1) types.add(AlertMessageType.ALERT_TYPE_ENTER_ZONE);
            if ( randomInt(0, 1)==1) types.add(AlertMessageType.ALERT_TYPE_EXIT_ZONE);
            if (types.isEmpty()) types.add(AlertMessageType.ALERT_TYPE_EXIT_ZONE);
            alert.setTypes(new ArrayList(types));
            // groups
            final ArrayList<Integer> groupIDs = new ArrayList<Integer>();
            final List<Object> groups = dataMap.get(Group.class);
            for (final Object group : groups)
                if (randomInt(0, 5) == 0)
                    groupIDs.add(((Group) group).getGroupID());
            alert.setGroupIDs(groupIDs);
            // recipients
            final ArrayList<Integer> personIDs = new ArrayList<Integer>();
            final List<Object> people = dataMap.get(Person.class);
            for (final Object person : people)
                if (randomInt(0, 5) == 0)
                    personIDs.add(((Person) person).getPersonID());
            alert.setNotifyPersonIDs(personIDs);
            // e-mail to
            if (randomInt(0, 1) == 1) {
                final ArrayList<String> emailTo = new ArrayList<String>();
                for (final Object person : people)
                    if (randomInt(0, 5) == 0)
                        emailTo.add(((Person) person).getPriEmail());
//                alert.setEmailTo(emailTo);
            }
            storeObject(alert);
            alerts.add(alert);
        }
        return alerts;
    }

    private List<RedFlagAlert> addRedFlagAlerts(Integer accountID, int numRedFlagAlerts) {
        List<RedFlagAlert> flags = new ArrayList<RedFlagAlert>();
        Integer idOffset = accountID * MAX_RED_FLAG_PREFS;
        for (int i = 0; i < numRedFlagAlerts; i++) {
            int id = idOffset + i + 1;
            RedFlagAlert flag = new RedFlagAlert();
            flag.setAccountID(accountID);
            flag.setAlertID(id);
            flag.setCreated(new Date());
            flag.setName("RedFlagAlert" + id);
            flag.setDescription("Don't step on my blue suede shoes!");
            final ArrayList<Boolean> dayOfWeek = new ArrayList<Boolean>();
            boolean pickedOne = false;
            for (int j = 0; j < 7; j++) {
                final boolean day = randomInt(0, 1) == 1 ? true : false;
                dayOfWeek.add(day);
                pickedOne |= day;
            }
            if (!pickedOne)
                dayOfWeek.set(0, true);
            flag.setDayOfWeek(dayOfWeek);
            if (randomInt(0, 1) == 1) {
                flag.setStartTOD(randomInt(RedFlagAlert.MIN_TOD, RedFlagAlert.MAX_TOD));
                flag.setStopTOD(randomInt(RedFlagAlert.MIN_TOD, RedFlagAlert.MAX_TOD));
            }
            final int type = randomInt(0, 2);
            if (type == 0) {
                flag.setTypes(new ArrayList(AlertMessageType.getAggressiveDrivingTypes()));
                flag.setSeverityLevel(RedFlagLevel.values()[randomInt(1, RedFlagLevel.values().length - 1)]);
                flag.setHardBrake(randomInt(0, 2));
                flag.setHardAcceleration(randomInt(0, 2));
                flag.setHardTurn(randomInt(0, 2));
                flag.setHardVertical(randomInt(0, 2));
            }
            else if (type == 1) {
                flag.setTypes(new ArrayList(EnumSet.of(AlertMessageType.ALERT_TYPE_SPEEDING)));
                flag.setSeverityLevel(RedFlagLevel.values()[randomInt(1, RedFlagLevel.values().length - 1)]);
                final Integer[] speedSettings = new Integer[TiwiproSpeedingConstants.INSTANCE.NUM_SPEEDS];
                for (int j = 0; j < speedSettings.length; j++)
                    speedSettings[j] = randomInt(0, 5) * 5;
                flag.setSpeedSettings(speedSettings);
            }
            else {
                flag.setTypes(new ArrayList(EnumSet.of(AlertMessageType.ALERT_TYPE_SEATBELT)));
                flag.setSeverityLevel(RedFlagLevel.values()[randomInt(1, RedFlagLevel.values().length - 1)]);
            }
            // groups
            if (randomInt(0, 1) == 1) {
                final ArrayList<Integer> groupIDs = new ArrayList<Integer>();
                final List<Object> groups = dataMap.get(Group.class);
                for (final Object group : groups)
                    if (randomInt(0, 5) == 0)
                        groupIDs.add(((Group) group).getGroupID());
                flag.setGroupIDs(groupIDs);
            }
            // vehicle types
            if (randomInt(0, 1) == 1) {
                final ArrayList<VehicleType> vehicleTypes = new ArrayList<VehicleType>();
                for (final VehicleType vehicleType : VehicleType.values())
                    if (randomInt(0, 1) == 0)
                        vehicleTypes.add(vehicleType);
                flag.setVehicleTypes(vehicleTypes);
            }
            // recipients
            final ArrayList<Integer> personIDs = new ArrayList<Integer>();
            final List<Object> people = dataMap.get(Person.class);
            for (final Object person : people)
                if (randomInt(0, 5) == 0)
                    personIDs.add(((Person) person).getPersonID());
            flag.setNotifyPersonIDs(personIDs);
            // e-mail to
            if (randomInt(0, 1) == 1) {
                final ArrayList<String> emailTo = new ArrayList<String>();
                for (final Object person : people)
                    if (randomInt(0, 5) == 0)
                        emailTo.add(((Person) person).getPriEmail());
//                flag.setEmailTo(emailTo);
            }
            storeObject(flag);
            flags.add(flag);
        }
        return flags;
    }

    private static List<LatLng> getHardCodedRoute() {
        /* Trip from David Story's house, to Dave Harry's house */
        List<LatLng> route = new ArrayList<LatLng>();
        route.add(new LatLng(33.0089f, -117.1100f));
        route.add(new LatLng(33.0095f, -117.1103f));
        route.add(new LatLng(33.0103f, -117.1105f));
        route.add(new LatLng(33.0105f, -117.1105f));
        route.add(new LatLng(33.0103f, -117.1113f));
        route.add(new LatLng(33.0109f, -117.1117f));
        route.add(new LatLng(33.0117f, -117.1120f));
        route.add(new LatLng(33.0127f, -117.1122f));
        route.add(new LatLng(33.0139f, -117.1122f));
        route.add(new LatLng(33.0152f, -117.1119f));
        route.add(new LatLng(33.0164f, -117.1115f));
        route.add(new LatLng(33.0172f, -117.1124f));
        route.add(new LatLng(33.0183f, -117.1151f));
        route.add(new LatLng(33.0187f, -117.1177f));
        route.add(new LatLng(33.0192f, -117.1199f));
        route.add(new LatLng(33.0201f, -117.1215f));
        route.add(new LatLng(33.0212f, -117.1235f));
        route.add(new LatLng(33.0218f, -117.1252f));
        route.add(new LatLng(33.0219f, -117.1271f));
        route.add(new LatLng(33.0219f, -117.1285f));
        route.add(new LatLng(33.0219f, -117.1310f));
        route.add(new LatLng(33.0219f, -117.1332f));
        route.add(new LatLng(33.0219f, -117.1356f));
        route.add(new LatLng(33.0218f, -117.1380f));
        route.add(new LatLng(33.0218f, -117.1403f));
        route.add(new LatLng(33.0217f, -117.1426f));
        route.add(new LatLng(33.0212f, -117.1446f));
        route.add(new LatLng(33.0205f, -117.1461f));
        route.add(new LatLng(33.0195f, -117.1475f));
        route.add(new LatLng(33.0187f, -117.1481f));
        route.add(new LatLng(33.0175f, -117.1486f));
        route.add(new LatLng(33.0159f, -117.1487f));
        route.add(new LatLng(33.0147f, -117.1482f));
        route.add(new LatLng(33.0132f, -117.1474f));
        route.add(new LatLng(33.0112f, -117.1466f));
        route.add(new LatLng(33.0095f, -117.1458f));
        route.add(new LatLng(33.0079f, -117.1451f));
        route.add(new LatLng(33.0066f, -117.1451f));
        route.add(new LatLng(33.0054f, -117.1456f));
        route.add(new LatLng(33.0042f, -117.1468f));
        route.add(new LatLng(33.0025f, -117.1486f));
        route.add(new LatLng(33.0012f, -117.1506f));
        route.add(new LatLng(33.0004f, -117.1522f));
        route.add(new LatLng(32.9990f, -117.1545f));
        route.add(new LatLng(32.9975f, -117.1565f));
        route.add(new LatLng(32.9961f, -117.1576f));
        route.add(new LatLng(32.9940f, -117.1583f));
        route.add(new LatLng(32.9919f, -117.1590f));
        route.add(new LatLng(32.9903f, -117.1595f));
        route.add(new LatLng(32.9882f, -117.1602f));
        route.add(new LatLng(32.9866f, -117.1607f));
        route.add(new LatLng(32.9846f, -117.1608f));
        route.add(new LatLng(32.9833f, -117.1604f));
        route.add(new LatLng(32.9817f, -117.1593f));
        route.add(new LatLng(32.9806f, -117.1579f));
        route.add(new LatLng(32.9799f, -117.1552f));
        route.add(new LatLng(32.9798f, -117.1525f));
        route.add(new LatLng(32.9790f, -117.1499f));
        route.add(new LatLng(32.9777f, -117.1482f));
        route.add(new LatLng(32.9763f, -117.1466f));
        route.add(new LatLng(32.9755f, -117.1475f));
        route.add(new LatLng(32.9743f, -117.1489f));
        route.add(new LatLng(32.9728f, -117.1508f));
        route.add(new LatLng(32.9717f, -117.1526f));
        route.add(new LatLng(32.9710f, -117.1562f));
        route.add(new LatLng(32.9709f, -117.1595f));
        route.add(new LatLng(32.9707f, -117.1620f));
        route.add(new LatLng(32.9700f, -117.1649f));
        route.add(new LatLng(32.9701f, -117.1674f));
        route.add(new LatLng(32.9703f, -117.1699f));
        route.add(new LatLng(32.9703f, -117.1721f));
        route.add(new LatLng(32.9702f, -117.1750f));
        route.add(new LatLng(32.9691f, -117.1769f));
        route.add(new LatLng(32.9682f, -117.1783f));
        route.add(new LatLng(32.9672f, -117.1810f));
        route.add(new LatLng(32.9663f, -117.1832f));
        route.add(new LatLng(32.9652f, -117.1862f));
        route.add(new LatLng(32.9643f, -117.1891f));
        route.add(new LatLng(32.9628f, -117.1910f));
        route.add(new LatLng(32.9611f, -117.1923f));
        route.add(new LatLng(32.9615f, -117.1940f));
        route.add(new LatLng(32.9617f, -117.1957f));
        route.add(new LatLng(32.9613f, -117.1981f));
        route.add(new LatLng(32.9615f, -117.2009f));
        route.add(new LatLng(32.9621f, -117.2037f));
        route.add(new LatLng(32.9619f, -117.2062f));
        route.add(new LatLng(32.9608f, -117.2082f));
        route.add(new LatLng(32.9593f, -117.2095f));
        route.add(new LatLng(32.9595f, -117.2101f));
        route.add(new LatLng(32.9598f, -117.2108f));
        route.add(new LatLng(32.9601f, -117.2103f));
        route.add(new LatLng(32.9603f, -117.2102f));
        return route;
    }

    private Driver createDriver(Integer id, Integer accountID, Integer groupID, String first, String last) {
        Person person = retrieveObject(Person.class, "personID", id);
        if (person == null) {
            person = new Person();
            storeObject(person);
        }
        person.setEmpid(String.valueOf(id));
        person.setPersonID(id);
        person.setFirst(first);
        person.setLast(last);
        person.setPriPhone(randomPhone());
        person.setSecPhone(randomPhone());
        person.setPriEmail(first.toLowerCase() + '.' + last.toLowerCase() + "@email.com");
        person.setTimeZone(MockTimeZones.getRandomTimezone());
        Driver driver = new Driver();
        driver.setDriverID(id);
        driver.setPersonID(person.getPersonID());
        driver.setPerson(person);
        driver.setGroupID(groupID);
        person.setDriver(driver);
        return driver;
    }

    private List<Vehicle> addVehiclesToGroup(Integer accountID, Integer groupID, int numVehiclesInGroup) {
        List<Vehicle> vehicleList = new ArrayList<Vehicle>();
        Integer idOffset = accountID * MAX_GROUPS + groupID * MAX_VEHICLES_IN_GROUP;
        for (int i = 0; i < numVehiclesInGroup; i++) {
            int id = idOffset + i + 1;
            String VIN = String.valueOf(randomInt(10000000, 99999999)) + String.valueOf(randomInt(100000000, 999999999));
            Vehicle vehicle = createVehicle(id, accountID, groupID, "Ford", "F" + (randomInt(1, 15) * 1000), "Red", randomInt(5, 50) * 1000, VIN, "ABC-123", MockStates
                    .randomState(), randomInt(0, 10) < 8);
            storeObject(vehicle);
            addScores(vehicle.getVehicleID(), EntityType.ENTITY_VEHICLE, vehicle.getName());
            vehicleList.add(vehicle);
        }
        return vehicleList;
    }

    private Vehicle createVehicle(int id, Integer accountID, Integer groupID, String make, String model, String color, int weight, String VIN, String license, State state,
            boolean active) {
        final Vehicle vehicle = new Vehicle();
        vehicle.setVehicleID(id);
        vehicle.setGroupID(groupID);
        vehicle.setName(String.valueOf(id));
        vehicle.setYear(randomInt(1970, 2009));
        vehicle.setMake(make);
        vehicle.setModel(model);
        vehicle.setColor(color);
        vehicle.setWeight(weight);
        vehicle.setVIN(VIN);
        vehicle.setLicense(license);
        vehicle.setState(state);
        vehicle.setStatus(Status.ACTIVE);
        vehicle.setVtype(VehicleType.values()[randomInt(0, VehicleType.values().length - 1)]);
        return vehicle;
    }

    private boolean groupIsParent(Group[] groups, Integer groupID) {
        for (Group group : groups) {
            if (group.getParentID().equals(groupID))
                return true;
        }
        return false;
    }

    private List<Device> addDevices(Integer accountID, int numDevices) {
        List<Device> deviceList = new ArrayList<Device>();
        Integer idOffset = accountID * MAX_DEVICES;
        for (int i = 0; i < numDevices; i++) {
            int id = idOffset + i + 1;
            Device device = createDevice(id, accountID, DeviceStatus.values()[randomInt(0, 2)], "DEVICE" + id, 
            				genNumericID(accountID, 15), genNumericID(accountID, 19), 
            				randomPhone(), randomPhone(), new Date());
            storeObject(device);
            deviceList.add(device);
        }
        return deviceList;
    }
    
    private String genNumericID(Integer acctID, Integer len)
    {
        String id = "999" + acctID.toString();
        
        for (int i = id.length(); i < len; i++)
        {
            id += "9";
        }
        
        return id;
    }


    private Device createDevice(Integer id, Integer accountID, DeviceStatus status, String name, String imei, String sim, String phone, String ephone, Date activated) {
        final Device device = new Device();
        device.setDeviceID(id);
        device.setAccountID(accountID);
        device.setStatus(status);
        device.setName(name);
        device.setImei(imei);
        device.setSim(sim);
        device.setPhone(phone);
//        device.setEphone(ephone);
        device.setActivated(activated);
//        device.setHardBrake(randomInt(0, 2));
//        device.setHardAcceleration(randomInt(0, 2));
//        device.setHardTurn(randomInt(0, 2));
//        device.setHardVertical(randomInt(0, 2));
//        final Integer[] speedSettings = new Integer[Vehicle.NUM_SPEEDS];
//        for (int i = 0; i < speedSettings.length; i++)
//            speedSettings[i] = randomInt(0, 5) * 5;
//        device.setSpeedSettings(speedSettings);
        return device;
    }

    // ------------ UTILITY METHODS ----------------
    static String randomScore() {
        int score = randomInt(0, 4);
        int decimal = randomInt(0, 9);
        return (score + "." + decimal);
    }

    static long randomLong(long min, long max) {
        return (long) (Math.random() * ((max - min) + 1)) + min;
    }
    static int randomInt(int min, int max) {
        return (int) (Math.random() * ((max - min) + 1)) + min;
    }

    private static float randomLng() {
        // longitude in range -117 to -101
        float base = -101.0f;
        return base + ((float) randomInt(0, 16000) / 1000.0f * -1.0f);
    }

    private static float randomLat() {
        // latitude in range 36 to 49
        float base = 36.0f;
        return base + ((float) randomInt(0, 15000) / 1000.0f);
    }

    private static String randomPhone() {
        return randomInt(100, 999) + "-" + randomInt(100, 999) + "-" + randomInt(1000, 9999);
    }

    private static Integer hourInDaysBack(int day, int minute) {
        int daysBackDate = DateUtil.getDaysBackDate(baseTimeSec, day, MST_TZ);
        return daysBackDate + (minute * DateUtil.SECONDS_IN_MINUTE);
    }

    // ------------ DELETE METHODS ----------------
    public int deleteObject(Class clas, String key, Object value) {
        List<Object> objList = dataMap.get(clas);
        for (Object object : objList) {
            Object fieldValue = getFieldValue(object, key);
            if (fieldValue.equals(value)) {
                objList.remove(object);
            }
        }
        return objList.size();
    }

    // ------------ LOOKUP METHODS ----------------
    public Map<String, Object> lookup(Class clas, String primaryKey, Object searchValue) {
        Object obj = retrieveObject(clas, primaryKey, searchValue);
        if (obj != null) {
            return TempConversionUtil.createMapFromObject(obj, true);
        }
        return null;
    }

    public <T> T lookupObject(Class clas, String primaryKey, Object searchValue) {
        return (T) retrieveObject(clas, primaryKey, searchValue);
    }

    // get full list of a class
    public <T> List<T> lookupObjectList(Class clas, T object) {
        return (List<T>) dataMap.get(clas);
    }

    public <T> List<T> lookupObjectList(Class clas, T object, String key, Object value) {
        List<T> objList = (List<T>) dataMap.get(clas);
        if (objList != null && objList.size() > 0) {
            List<T> returnList = new ArrayList<T>();
            for (Object obj : objList) {
                Object fieldValue = getFieldValue(obj, key);
                if (fieldValue.equals(value)) {
                    returnList.add((T) obj);
                }
            }
            return returnList;
        }
        return null;
    }

    public List<Map<String, Object>> lookupList(Class clas) {
        List<Object> objList = dataMap.get(clas);
        if (objList != null && objList.size() > 0) {
            List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
            for (Object obj : objList) {
                returnList.add(TempConversionUtil.createMapFromObject(obj, true));
            }
            return returnList;
        }
        return null;
    }

    // lookup using multiple key/value pairs ANDed together
    public List<Map<String, Object>> lookupList(Class clas, SearchCriteria searchCriteria) {
        List<Object> objList = retrieveObjectList(clas, searchCriteria);
        if (objList != null && objList.size() > 0) {
            List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
            for (Object obj : objList) {
                returnList.add(TempConversionUtil.createMapFromObject(obj, true));
            }
            return returnList;
        }
        return null;
    }

    public Map<String, Object> lookup(Class clas, SearchCriteria searchCriteria) {
        Object obj = retrieveObject(clas, searchCriteria);
        if (obj != null) {
            return TempConversionUtil.createMapFromObject(obj, true);
        }
        return null;
    }

    public <T> void storeObject(T obj) {
        storeObject(obj, obj.getClass());
    }

    public <T> void storeObject(T obj, Class clazz) {
        List<Object> objList = dataMap.get(clazz);
        if (objList == null) {
            objList = new ArrayList<Object>();
            dataMap.put(clazz, objList);
        }
        objList.add(obj);
    }

    public <T> T retrieveObject(Class<T> clas, String key, Object value) {
        List<Object> objList = dataMap.get(clas);
        if (objList == null) {
            return null;
        }
        for (Object obj : objList) {
            Object fieldValue = getFieldValue(obj, key);
            if ((fieldValue != null) && fieldValue.equals(value)) {
                return (T) obj;
            }
        }
        return null;
    }

    public <T> T retrieveObject(Class<T> clas, SearchCriteria searchCriteria) {
        Map<String, Object> searchMap = searchCriteria.getCriteriaMap();
        List<Object> objList = dataMap.get(clas);
        if (objList == null) {
            return null;
        }
        for (Object obj : objList) {
            boolean isMatch = true;
            for (Map.Entry<String, Object> searchItem : searchMap.entrySet()) {
                Object fieldValue = null;
                if (searchItem.getKey().indexOf(":") != -1) {
                    fieldValue = getFieldValue(obj, searchItem.getKey().split(":"));
                }
                else {
                    fieldValue = getFieldValue(obj, searchItem.getKey());
                }
                if (!searchItemMatch(searchItem, fieldValue)) {
                    isMatch = false;
                    break;
                }
            }
            if (isMatch) {
                return (T) obj;
            }
        }
        return null;
    }

    private boolean searchItemMatch(Entry<String, Object> searchItem, Object fieldValue) {
        if (searchItem.getValue() instanceof ValueRange) {
            ValueRange valueRange = (ValueRange) searchItem.getValue();
            if (valueRange.low.compareTo(fieldValue) > 0 || valueRange.high.compareTo(fieldValue) < 0) {
                return false;
            }
        }
        else if (searchItem.getValue() instanceof ValueList) {
            // or relationship, if any in list match return true
            ValueList valueList = (ValueList) searchItem.getValue();
            for (Object obj : valueList.getObjList()) {
                if (obj.equals(fieldValue)) {
                    return true;
                }
            }
            return false;
        }
        else if (!searchItem.getValue().equals(fieldValue)) {
            return false;
        }
        return true;
    }

    public <T> List<T> retrieveObjectList(Class<T> clas, SearchCriteria searchCriteria) {
        List<T> returnObjList = new ArrayList<T>();
        Map<String, Object> searchMap = searchCriteria.getCriteriaMap();
        List<Object> objList = dataMap.get(clas);
        if (objList == null) {
            return null;
        }
        for (Object obj : objList) {
            boolean isMatch = true;
            for (Map.Entry<String, Object> searchItem : searchMap.entrySet()) {
                Object fieldValue = null;
                if (searchItem.getKey().indexOf(":") != -1) {
                    fieldValue = getFieldValue(obj, searchItem.getKey().split(":"));
                }
                else {
                    fieldValue = getFieldValue(obj, searchItem.getKey());
                }
                if (!searchItemMatch(searchItem, fieldValue)) {
                    isMatch = false;
                    break;
                }
            }
            if (isMatch) {
                returnObjList.add((T) obj);
            }
        }
        return returnObjList;
    }

    public <T> Object getFieldValue(T object, String field[]) {
        for (int i = 0; i < field.length; i++) {
            Object obj = getFieldValue(object, field[i]);
            if (obj != null) {
                object = (T) obj;
            }
        }
        return object;
    }

    public <T> Object getFieldValue(T object, String field) {
        Object value = null;
        Class cls = object.getClass();
        BeanInfo beanInfo;
        try {
            beanInfo = Introspector.getBeanInfo(object.getClass());
        }
        catch (IntrospectionException e) {
            e.printStackTrace();
            return value;
        }
        PropertyDescriptor propertyDescriptors[] = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++) {
            String key = propertyDescriptors[i].getName();
            if (!key.equals(field))
                continue;
            Method getMethod = propertyDescriptors[i].getReadMethod();
            try {
                value = getMethod.invoke(object);
            }
            catch (IllegalAccessException e) {
                System.out.println("IllegalAccessException occured while trying to invoke the method " + getMethod.getName());
                e.printStackTrace();
            }
            catch (InvocationTargetException e) {
                System.out.println("InvocationTargetExcpetion occured while trying to invoke the method " + getMethod.getName());
                e.printStackTrace();
            }
            break;
        }
        return value;
    }

    // ------------ DEBUG METHODS ----------------
    private void dumpGroupHierarchy(Group topGroup, String indent) {
        logger.debug(indent + topGroup.getName() + " (ID: " + topGroup.getGroupID() + ")");
        List<User> userList = lookupObjectList(User.class, new User(), "groupID", topGroup.getGroupID());
        for (User user : userList) {
            logger.debug(indent + " " + user.getUsername() + " (ID: " + user.getUserID() + ", ROLE: " + user.getRoles().toString() + ")");
        }
        for (Group group : lookupObjectList(Group.class, new Group())) {
            if (group.getParentID().equals(topGroup.getGroupID())) {
                // logger.debug(group.getName() + " (ID: " + group.getGroupID() + ")");
                indent += "    ";
                dumpGroupHierarchy(group, indent);
            }
        }
    }

    private void dumpData(Integer accountID) {
        Account account = retrieveObject(Account.class, "accountID", accountID);
        dumpObject(account, "");
        // get all groups
        List<Group> groups = lookupObjectList(Group.class, new Group());
        for (Group group : groups) {
            dumpObject(group, "  ");
            // get all users in group
            SearchCriteria searchCriteria = new SearchCriteria();
            searchCriteria.addKeyValue("groupID", group.getGroupID());
            List<User> users = retrieveObjectList(User.class, searchCriteria);
            for (User user : users) {
                dumpObject(user, "    ");
            }
            List<Driver> drivers = retrieveObjectList(Driver.class, searchCriteria);
            for (Driver driver : drivers) {
                dumpObject(driver, "    ");
            }
        }
    }

    private <T> void dumpObject(T obj, String indent) {
        Map<String, Object> objMap = TempConversionUtil.createMapFromObject(obj, true);
        logger.debug(indent + obj.getClass().getName());
        StringBuffer buffer = new StringBuffer(indent);
        for (Map.Entry<String, Object> item : objMap.entrySet()) {
            buffer.append(item.getKey());
            buffer.append("[");
            buffer.append(item.getValue().toString());
            buffer.append("] ");
        }
        logger.debug(buffer);
    }
}
