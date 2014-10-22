package it.com.inthinc.pro.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import it.com.inthinc.pro.dao.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.junit.Test;

import com.inthinc.hos.model.RuleSetType;
import com.inthinc.pro.backing.dao.impl.SiloServiceImpl;
import com.inthinc.pro.backing.dao.model.DaoMethod;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.AccountHOSType;
import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.AlertEscalationItem;
import com.inthinc.pro.model.AlertMessageType;
import com.inthinc.pro.model.Cellblock;
import com.inthinc.pro.model.CrashReport;
import com.inthinc.pro.model.CrashReportStatus;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.DeviceStatus;
import com.inthinc.pro.model.Driver;
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
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Occurrence;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.RedFlagAlert;
import com.inthinc.pro.model.RedFlagLevel;
import com.inthinc.pro.model.ReportSchedule;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.TablePreference;
import com.inthinc.pro.model.TableType;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleType;
import com.inthinc.pro.model.Zone;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.configurator.SettingType;
import com.inthinc.pro.model.phone.CellProviderType;
import com.inthinc.pro.model.phone.CellStatusType;
import com.inthinc.pro.model.security.AccessPoint;
import com.inthinc.pro.model.security.Role;

public class SiloServiceCRUDImplTest extends BaseServiceImpTest {

    @Test
    public void forwardCommandDefs() {
        try {
            init();
        } catch (Exception e1) {
            e1.printStackTrace();
            fail("init failed");
        }

        Map<String, DaoMethod> methodMap = initMethodMap(SiloServiceImpl.class, true);
        List<Map<String, Object>> returnList = findAllUsingDAO(methodMap, "getFwdCmdDefs", new Object[] {});
        Integer originalCount = returnList.size();

        ForwardCommandDef def = new ForwardCommandDef(666, "Bad Command", "Bad Command - just testing", ForwardCommandParamType.NONE, true);

        createUsingDAO(methodMap, "createFwdCmdDef", new Object[] { def });
        returnList = findAllUsingDAO(methodMap, "getFwdCmdDefs", new Object[] {});
        assertEquals("expected one more definition", originalCount + 1, returnList.size());

        def.setName("Big Bad");
        Integer changedCount = updateUsingDAO(methodMap, "updateFwdCmdDef", new Object[] { def });
        assertEquals("Changed count", 1, changedCount.intValue());

        deleteUsingDAO(methodMap, "deleteFwdCmdDef", new Object[] { 666 });
        returnList = findAllUsingDAO(methodMap, "getFwdCmdDefs", new Object[] {});
        assertEquals("expected one fewer definition", originalCount.intValue(), returnList.size());

    }

    @Test
    public void testSiloServiceCRUDImpl() {

        try {
            init();
        } catch (Exception e1) {
            e1.printStackTrace();
            fail("init failed");
        }

        Map<String, DaoMethod> methodMap = initMethodMap(SiloServiceImpl.class, true);
        Integer accountID = createUpdateAccount(methodMap);

        createUpdateAddress(methodMap, accountID);

        Integer roleID = createUpdateRole(methodMap, accountID);

        Integer teamGroupID = createUpdateGroups(methodMap, accountID);

        Integer zoneID = createUpdateZone(methodMap, accountID, teamGroupID);

        Integer deviceID = createUpdateDevice(methodMap, accountID);

        Integer vehicleID = createUpdateVehicle(methodMap, teamGroupID);

        Integer personID = createUpdatePerson(methodMap, accountID, teamGroupID);

        Integer driverID = createUpdateDriver(methodMap, personID, teamGroupID);

        setVehicleDeviceDriver(methodMap, deviceID, vehicleID, driverID);

        Integer userID = createUpdateUser(methodMap, personID, teamGroupID, roleID);

        Integer tablePrefID = createUpdateTablePref(methodMap, userID);

        Integer reportPrefID = createUpdateReportPref(methodMap, accountID, teamGroupID, userID);

        Integer alertID = createUpdateRedFlagAlert(methodMap, accountID, teamGroupID, personID, userID);
        createUpdateRedFlagZoneAlert(methodMap, accountID, teamGroupID, personID, userID, zoneID);

        Integer crashReportID = createUpdateCrashReport(methodMap, accountID, driverID, vehicleID);

        setUpdateVehicleSettings(methodMap, vehicleID, deviceID, userID);

        queueUpdateForwardCommand(methodMap, deviceID);

        setClearSuperuser(methodMap, userID);

        createUpdateCellblock(methodMap, accountID, driverID);

        // delete
        deleteUsingDAO(methodMap, "deleteCrash", new Object[] { crashReportID });
        deleteUsingDAO(methodMap, "deleteAlert", new Object[] { alertID });
        deleteUsingDAO(methodMap, "deleteTablePref", new Object[] { tablePrefID });
        deleteUsingDAO(methodMap, "deleteReportPref", new Object[] { reportPrefID });
        deleteUsingDAO(methodMap, "deleteUser", new Object[] { userID });
        deleteUsingDAO(methodMap, "deleteDriver", new Object[] { driverID });
        deleteUsingDAO(methodMap, "deleteVehicle", new Object[] { vehicleID });
        deleteUsingDAO(methodMap, "deleteZone", new Object[] { zoneID });
        deleteUsingDAO(methodMap, "deleteDevice", new Object[] { deviceID });
        deleteUsingDAO(methodMap, "deleteGroup", new Object[] { teamGroupID });
        deleteUsingDAO(methodMap, "deleteAcct", new Object[] { accountID });
    }

    private static final Integer TESTING_SILO = 0;

    private Integer createUpdateAccount(Map<String, DaoMethod> methodMap) {
        Account account = new Account(null, Status.ACTIVE);
        String timeStamp = Calendar.getInstance().getTime().toString();
        account.setAcctName(timeStamp);
        account.setHos(AccountHOSType.NONE);

        Integer acctID = createUsingDAO(methodMap, "createAcct", new Object[] { TESTING_SILO, account });
        account.setAccountID(acctID);

        account.setHos(AccountHOSType.HOS_SUPPORT);
        Integer changedCount = updateUsingDAO(methodMap, "updateAcct", new Object[] { acctID, account });
        assertEquals("Changed count", 1, changedCount.intValue());

        Map<String, Object> returnMap = findUsingDAO(methodMap, "getAcct", new Object[] { acctID });
        assertEquals("expected field to change", account.getHos().getCode(), returnMap.get("hos"));

        return acctID;
    }

    private Integer createUpdateRole(Map<String, DaoMethod> methodMap, Integer accountID) {
        Role role = new Role();
        role.setAcctID(accountID);
        List<AccessPoint> accessPoints = new ArrayList<AccessPoint>();
        accessPoints.add(new AccessPoint(2, 15));
        accessPoints.add(new AccessPoint(3, 15));
        role.setAccessPts(accessPoints);
        role.setName("TestRole");

        Integer roleID = createUsingDAO(methodMap, "createRole", new Object[] { accountID, role });
        role.setRoleID(roleID);

        role.setName("TestRole2");
        Integer changedCount = updateUsingDAO(methodMap, "updateRole", new Object[] { roleID, role });
        assertEquals("Changed count", 1, changedCount.intValue());

        Map<String, Object> returnMap = findUsingDAO(methodMap, "getRole", new Object[] { roleID });
        assertEquals("expected field to change", role.getName(), returnMap.get("name"));

        return roleID;
    }

    private Integer createUpdateAddress(Map<String, DaoMethod> methodMap, Integer acctID) {
        Address address = new Address(null, randomInt(100, 999) + " Street", null, "City " + randomInt(10, 99), null, "12345", acctID);
        Integer addrID = createUsingDAO(methodMap, "createAddr", new Object[] { acctID, address });
        assertNotNull("createAddr failed ", addrID);
        address.setAddrID(addrID);

        address.setZip("56789");
        Integer changedCount = updateUsingDAO(methodMap, "updateAddr", new Object[] { addrID, address });
        assertEquals("Changed count", 1, changedCount.intValue());

        Map<String, Object> returnMap = findUsingDAO(methodMap, "getAddr", new Object[] { addrID });
        assertEquals("expected field to change", address.getZip(), returnMap.get("zip"));

        return addrID;
    }

    private Integer createUpdateGroups(Map<String, DaoMethod> methodMap, Integer accountID) {
        Group fleetGroup = new Group(0, accountID, "Fleet", 0, GroupType.FLEET, null, "Fleet Group", 5, new LatLng(0.0, 0.0));
        Integer groupID = createUsingDAO(methodMap, "createGroup", new Object[] { accountID, fleetGroup });
        assertNotNull("createGroup failed ", groupID);
        fleetGroup.setGroupID(groupID);

        Group teamGroup = new Group(0, accountID, "Team", fleetGroup.getGroupID(), GroupType.TEAM, null, "Team Group", 5, new LatLng(0.0, 0.0));
        groupID = createUsingDAO(methodMap, "createGroup", new Object[] { accountID, teamGroup });
        assertNotNull("createGroup failed ", groupID);
        teamGroup.setGroupID(groupID);

        teamGroup.setDescription("Testing Team Group");
        Integer changedCount = updateUsingDAO(methodMap, "updateGroup", new Object[] { accountID, teamGroup });
        assertEquals("Changed count", 1, changedCount.intValue());

        Map<String, Object> returnMap = findUsingDAO(methodMap, "getGroup", new Object[] { groupID });
        assertEquals("expected field to change", teamGroup.getDescription(), returnMap.get("desc"));

        return groupID;
    }

    private Integer createUpdateZone(Map<String, DaoMethod> methodMap, Integer accountID, Integer groupID) {
        Zone zone = new Zone(0, accountID, Status.ACTIVE, "zone " + accountID, "123 Street, Salt Lake City, UT 84107", groupID);
        List<LatLng> points = new ArrayList<LatLng>();
        points.add(new LatLng(40.723871753812f, -111.92932452647742f));
        points.add(new LatLng(40.704246f, -111.948613f));
        points.add(new LatLng(40.70f, -111.95f));
        points.add(new LatLng(40.723871753812f, -111.92932452647742f));
        zone.setPoints(points);
        Integer zoneID = createUsingDAO(methodMap, "createZone", new Object[] { accountID, zone });
        zone.setZoneID(zoneID);

        zone.setName("Test " + accountID);
        Integer changedCount = updateUsingDAO(methodMap, "updateZone", new Object[] { accountID, zone });
        assertEquals("Changed count", 1, changedCount.intValue());

        Map<String, Object> returnMap = findUsingDAO(methodMap, "getZone", new Object[] { zoneID });
        assertEquals("expected field to change", zone.getName(), returnMap.get("name"));

        callUsingDAO(methodMap, "publishZones", new Object[] { accountID });

        return zoneID;
    }

    private Integer createUpdateDevice(Map<String, DaoMethod> methodMap, Integer accountID) {
        Device device = new Device(0, accountID, DeviceStatus.NEW, "Device " + accountID, "IMEIT" + accountID, "SIMT" + accountID, "SNT" + accountID, "5555551234");
        device.setProductVersion(ProductType.WAYSMART);
        device.setProductVer(ProductType.WAYSMART.getVersions()[0]);
        Integer deviceID = createUsingDAO(methodMap, "createDevice", new Object[] { accountID, device });
        assertNotNull("createDevice failed ", deviceID);
        device.setDeviceID(deviceID);

        device.setSim("SIM " + accountID + "_1");
        Integer changedCount = updateUsingDAO(methodMap, "updateDevice", new Object[] { accountID, device });
        assertEquals("Changed count", 1, changedCount.intValue());

        Map<String, Object> returnMap = findUsingDAO(methodMap, "getDevice", new Object[] { deviceID });
        assertEquals("expected field to change", device.getSim(), returnMap.get("sim"));

        return deviceID;
    }

    private Integer createUpdateVehicle(Map<String, DaoMethod> methodMap, Integer teamGroupID) {
        Vehicle vehicle = new Vehicle(0, teamGroupID, Status.INACTIVE, "Vehicle " + teamGroupID, "Make " + teamGroupID, "Model " + teamGroupID, 2000, "COLOR", VehicleType.LIGHT, "VIN_" + teamGroupID
                + "_0", 1000, "License ", null);
        Integer vehicleID = createUsingDAO(methodMap, "createVehicle", new Object[] { teamGroupID, vehicle });
        assertNotNull("createVehicle failed ", vehicleID);
        vehicle.setVehicleID(vehicleID);

        vehicle.setName("TestVehicle " + teamGroupID);
        Integer changedCount = updateUsingDAO(methodMap, "updateVehicle", new Object[] { teamGroupID, vehicle });
        assertEquals("Changed count", 1, changedCount.intValue());

        Map<String, Object> returnMap = findUsingDAO(methodMap, "getVehicle", new Object[] { vehicleID });
        assertEquals("expected field to change", vehicle.getName(), returnMap.get("name"));

        return vehicleID;
    }

    private Integer createUpdatePerson(Map<String, DaoMethod> methodMap, Integer accountID, Integer groupID) {

        Person person = new Person(0, accountID, TimeZone.getDefault(), null, "email_" + groupID + "@example.com", null, "5555555555", "5555555555", null, null, null, null, null, accountID + "-"
                + groupID + "-" + "EMP", null, "title", "dept", "first", "m", "last", "jr", Gender.MALE, 65, 180, new DateTime(1960, 8, 8, 0, 0, 0, 0).toDate(), Status.ACTIVE,
                MeasurementType.ENGLISH, FuelEfficiencyType.MPG_US, Locale.getDefault());
        Integer personID = createUsingDAO(methodMap, "createPerson", new Object[] { accountID, person });
        assertNotNull("createPerson failed ", personID);
        person.setPersonID(personID);

        person.setLast("TestLast");
        Integer changedCount = updateUsingDAO(methodMap, "updatePerson", new Object[] { accountID, person });
        assertEquals("Changed count", 1, changedCount.intValue());

        Map<String, Object> returnMap = findUsingDAO(methodMap, "getPerson", new Object[] { personID });
        assertEquals("expected field to change", person.getLast(), returnMap.get("last"));

        return personID;
    }

    private Integer createUpdateDriver(Map<String, DaoMethod> methodMap, Integer personID, Integer groupID) {
        Driver driver = new Driver(0, personID, Status.ACTIVE, null, null, null, null, "l" + personID, null, "ABCD", new DateTime(2020, 8, 8, 0, 0, 0, 0).toDate(), null, RuleSetType.US_OIL, groupID);

        Integer driverID = createUsingDAO(methodMap, "createDriver", new Object[] { personID, driver });
        assertNotNull("createDriver failed ", driverID);
        driver.setDriverID(driverID);

        driver.setDot(RuleSetType.CANADA_2007_CYCLE_1);
        Integer changedCount = updateUsingDAO(methodMap, "updateDriver", new Object[] { driverID, driver });
        assertEquals("Changed count", 1, changedCount.intValue());

        Map<String, Object> returnMap = findUsingDAO(methodMap, "getDriver", new Object[] { driverID });
        assertEquals("expected field to change", driver.getDot().getCode(), returnMap.get("dot"));

        return driverID;
    }

    private static final String OLD_PASSWORD = "nuN5q/jdjEpJKKA4A6jLTZufWZfI";
    private static final String PASSWORD = "nuN5q/jdjEpJKKA4A6jLTZufWZfIXtxqzjVjifqFjbGg6tfmQFGXbTtcXtEIg4Z7"; // password

    private Integer createUpdateUser(Map<String, DaoMethod> methodMap, Integer personID, Integer groupID, Integer roleID) {

        User user = new User(0, personID, Arrays.asList(roleID), Status.ACTIVE, "user_" + groupID, OLD_PASSWORD, groupID);

        Integer userID = createUsingDAO(methodMap, "createUser", new Object[] { personID, user });
        assertNotNull("createUser failed ", userID);
        user.setUserID(userID);

        user.setPassword(PASSWORD);
        Integer changedCount = updateUsingDAO(methodMap, "updateUser", new Object[] { userID, user });
        assertEquals("Changed count", 1, changedCount.intValue());

        Map<String, Object> returnMap = findUsingDAO(methodMap, "getUser", new Object[] { userID });
        assertEquals("expected field to change", user.getPassword(), returnMap.get("password"));

        return userID;
    }

    private Integer createUpdateTablePref(Map<String, DaoMethod> methodMap, Integer userID) {
        List<Boolean> visible = new ArrayList<Boolean>();
        visible.add(true);
        visible.add(true);
        visible.add(false);
        visible.add(false);
        TablePreference tablePref = new TablePreference(0, userID, TableType.RED_FLAG, visible);

        Integer tablePrefID = createUsingDAO(methodMap, "createTablePref", new Object[] { userID, tablePref });
        assertNotNull("createTablePref failed ", tablePrefID);
        tablePref.setTablePrefID(tablePrefID);

        tablePref.setTableType(TableType.DIAGNOSTICS);
        Integer changedCount = updateUsingDAO(methodMap, "updateTablePref", new Object[] { tablePrefID, tablePref });
        assertEquals("Changed count", 1, changedCount.intValue());

        Map<String, Object> returnMap = findUsingDAO(methodMap, "getTablePref", new Object[] { tablePrefID });
        assertEquals("expected field to change", tablePref.getTableType().getCode(), returnMap.get("tableType"));

        return tablePrefID;
    }

    private Integer createUpdateReportPref(Map<String, DaoMethod> methodMap, Integer accountID, Integer groupID, Integer userID) {
        ReportSchedule reportSchedule = new ReportSchedule();
        reportSchedule.setReportDuration(Duration.DAYS);
        reportSchedule.setStatus(Status.ACTIVE);
        reportSchedule.setReportID(0);
        reportSchedule.setName("Report Schedule");
        reportSchedule.setOccurrence(Occurrence.WEEKLY);
        reportSchedule.setUserID(userID);
        reportSchedule.setGroupID(groupID);
        reportSchedule.setReportID(1);
        reportSchedule.setAccountID(accountID);

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

        reportSchedule.setLastDate(lastDate.getTime());
        reportSchedule.setEndDate(endDate.getTime());
        reportSchedule.setStartDate(lastDate.getTime());
        reportSchedule.setEmailTo(Arrays.asList("foo@example.com"));
        reportSchedule.setDayOfWeek(Arrays.asList(false, false, true, false, false, false, false));

        Integer reportScheduleID = createUsingDAO(methodMap, "createReportPref", new Object[] { accountID, reportSchedule });
        assertNotNull("createReportPref failed ", reportScheduleID);
        reportSchedule.setReportScheduleID(reportScheduleID);

        reportSchedule.setName("TEST");
        Integer changedCount = updateUsingDAO(methodMap, "updateReportPref", new Object[] { reportScheduleID, reportSchedule });
        assertEquals("Changed count", 1, changedCount.intValue());

        Map<String, Object> returnMap = findUsingDAO(methodMap, "getReportPref", new Object[] { reportScheduleID });
        assertEquals("expected field to change", reportSchedule.getName(), returnMap.get("name"));

        return reportScheduleID;
    }

    private Integer createUpdateRedFlagAlert(Map<String, DaoMethod> methodMap, Integer accountID, Integer groupID, Integer personID, Integer userID) {
        List<Boolean> dayOfWeek = Arrays.asList(true, true, true, true, true, true, true);
        List<Integer> groupIDList = Arrays.asList(groupID);
        List<Integer> notifyPersonIDs = Arrays.asList(personID);
        List<AlertEscalationItem> escalationList = Arrays.asList(new AlertEscalationItem(personID, 1));
        Integer[] speedSettings = { 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80 };
        List<AlertMessageType> list = new ArrayList<AlertMessageType>(EnumSet.of(AlertMessageType.ALERT_TYPE_SPEEDING));
        RedFlagAlert redFlagAlert = new RedFlagAlert(list, accountID, userID, "Red Flag Alert Profile", "Red Flag Alert Profile Description", 0, 1339, dayOfWeek, groupIDList, null, // driverIDs
                null, // vehicleIDs
                null, // vehicleTypeIDs
                notifyPersonIDs, null, // emailTo
                speedSettings, 10, 10, 10, 10, RedFlagLevel.CRITICAL, null, null, escalationList, 5, null, 5);

        Integer redFlagAlertID = createUsingDAO(methodMap, "createAlert", new Object[] { accountID, redFlagAlert });
        assertNotNull("createReportPref failed ", redFlagAlertID);
        redFlagAlert.setAlertID(redFlagAlertID);

        redFlagAlert.setName("TEST");
        Integer changedCount = updateUsingDAO(methodMap, "updateAlert", new Object[] { redFlagAlertID, redFlagAlert });
        assertEquals("Changed count", 1, changedCount.intValue());

        Map<String, Object> returnMap = findUsingDAO(methodMap, "getAlert", new Object[] { redFlagAlertID });
        assertEquals("expected field to change", redFlagAlert.getName(), returnMap.get("name"));

        return redFlagAlertID;
    }

    private void createUpdateRedFlagZoneAlert(Map<String, DaoMethod> methodMap, Integer accountID, Integer groupID, Integer personID, Integer userID, Integer zoneID) {
        List<Boolean> dayOfWeek = Arrays.asList(true, true, true, true, true, true, true);
        List<Integer> groupIDList = Arrays.asList(groupID);
        List<Integer> notifyPersonIDs = Arrays.asList(personID);
        List<AlertEscalationItem> escalationList = Arrays.asList(new AlertEscalationItem(personID, 1));
        List<AlertMessageType> list = new ArrayList<AlertMessageType>(EnumSet.of(AlertMessageType.ALERT_TYPE_ENTER_ZONE));
        RedFlagAlert redFlagZoneAlert = new RedFlagAlert(list, accountID, userID, "Red Flag Zone Alert Profile", "Red Flag Zone Alert Profile Description", 0, 1339, dayOfWeek, groupIDList, null, // driverIDs
                null, // vehicleIDs
                null, // vehicleTypeIDs
                notifyPersonIDs, null, // emailTo
                null, null, null, null, null, RedFlagLevel.CRITICAL, zoneID, null, escalationList, 5, null, 5);

        Integer redFlagAlertID = createUsingDAO(methodMap, "createAlert", new Object[] { accountID, redFlagZoneAlert });
        assertNotNull("createReportPref failed ", redFlagAlertID);
        redFlagZoneAlert.setAlertID(redFlagAlertID);

        List<Map<String, Object>> returnList = findAllUsingDAO(methodMap, "getAlertsByAcctID", new Object[] { accountID });
        Integer countWithZoneAlerts = returnList.size();

        callUsingDAO(methodMap, "deleteAlertsByZoneID", new Object[] { zoneID });
        returnList = findAllUsingDAO(methodMap, "getAlertsByAcctID", new Object[] { accountID });
        assertEquals("Expected one fewer zone after deleteByZoneID", countWithZoneAlerts - 1, returnList.size());

    }

    private Integer createUpdateCrashReport(Map<String, DaoMethod> methodMap, Integer accountID, Integer driverID, Integer vehicleID) {

        CrashReport crashReport = new CrashReport();
        crashReport.setDriverID(driverID);
        crashReport.setVehicleID(vehicleID);
        crashReport.setOccupantCount(0);
        crashReport.setCrashReportStatus(CrashReportStatus.AGGRESSIVE_DRIVING);
        crashReport.setHasTrace(0);
        crashReport.setDate(new Date());
        crashReport.setLat(32.960110);
        crashReport.setLng(-117.210274);
        crashReport.setNoteID(1l);

        Integer crashReportID = createUsingDAO(methodMap, "createCrash", new Object[] { accountID, crashReport });
        assertNotNull("createReportPref failed ", crashReportID);
        crashReport.setCrashReportID(crashReportID);

        crashReport.setDescription("TEST");
        Integer changedCount = updateUsingDAO(methodMap, "updateCrash", new Object[] { crashReportID, crashReport });
        assertEquals("Changed count", 1, changedCount.intValue());

        Map<String, Object> returnMap = findUsingDAO(methodMap, "getCrash", new Object[] { crashReportID });
        assertEquals("expected field to change", crashReport.getDescription(), returnMap.get("desc"));

        return crashReportID;
    }

    @SuppressWarnings("unchecked")
    private void setUpdateVehicleSettings(Map<String, DaoMethod> methodMap, Integer vehicleID, Integer deviceID, Integer userID) {

        callUsingDAO(methodMap, "setVehicleDevice", new Object[] { vehicleID, deviceID });

        Map<Integer, String> settingMap = new HashMap<Integer, String>();
        settingMap.put(SettingType.HARD_VERT_SETTING.getSettingID(), "3000 80 2 300");
        callUsingDAO(methodMap, "setVehicleSettings", new Object[] { vehicleID, settingMap, userID, "Test setVehicleSettings" });

        Map<String, Object> returnMap = findUsingDAO(methodMap, "getVehicleSettings", new Object[] { vehicleID });
        Map<Integer, Object> desiredSettingsMap = (Map<Integer, Object>) returnMap.get("desired");
        assertTrue("new setting not found", desiredSettingsMap.containsKey(160));

    }

    private void queueUpdateForwardCommand(Map<String, DaoMethod> methodMap, Integer deviceID) {
        List<Map<String, Object>> returnList = findAllUsingDAO(methodMap, "getFwdCmds", new Object[] { deviceID, ForwardCommandStatus.STATUS_QUEUED.getCode() });
        Integer originalCount = returnList.size();

        ForwardCommand stringDataCmd = new ForwardCommand(0, ForwardCommandID.SET_GPRS_APN, "555555123" + Util.randomInt(1, 9), ForwardCommandStatus.STATUS_QUEUED);
        callUsingDAO(methodMap, "queueFwdCmd", new Object[] { deviceID, stringDataCmd });

        List<Map<String, Object>> newReturnList = findAllUsingDAO(methodMap, "getFwdCmds", new Object[] { deviceID, ForwardCommandStatus.STATUS_QUEUED.getCode() });
        assertEquals("expected one more forwardCommand", originalCount + 1, newReturnList.size());
        Integer fwdID = null;
        for (Map<String, Object> returnMap : newReturnList) {
            boolean existedBefore = false;
            for (Map<String, Object> oreturnMap : returnList) {
                if (oreturnMap.get("fwdID").equals(returnMap.get("fwdID"))) {
                    existedBefore = true;
                    break;
                }
            }

            if (!existedBefore) {
                fwdID = (Integer) returnMap.get("fwdID");
            }

        }
        assertNotNull("expected to find new fwd cmd", fwdID);

        returnList = findAllUsingDAO(methodMap, "getFwdCmds", new Object[] { deviceID, ForwardCommandStatus.STATUS_BAD_DATA.getCode() });

        // TODO: this didn't work on backend/hessian side
        // updateUsingDAO(methodMap, "updateFwdCmd", new Object[] {fwdID, ForwardCommandStatus.STATUS_BAD_DATA.getCode()});
        // returnList = findAllUsingDAO(methodMap, "getFwdCmds", new Object[] {deviceID, ForwardCommandStatus.STATUS_BAD_DATA.getCode()});
        // assertEquals("expected one more forwardCommand with bad data status", badDataCnt+1, newReturnList.size());

    }

    private void setClearSuperuser(Map<String, DaoMethod> methodMap, Integer userID) {

        callUsingDAO(methodMap, "setSuperuser", new Object[] { userID });

        Map<String, Object> returnMap = findUsingDAO(methodMap, "isSuperuser", new Object[] { userID });
        assertEquals("expected returnMap size after set superuser", 1, returnMap.size());
        assertEquals("expected returnMap value after set superuser ", 1, returnMap.get("count"));

        callUsingDAO(methodMap, "clearSuperuser", new Object[] { userID });
        returnMap = findUsingDAO(methodMap, "isSuperuser", new Object[] { userID });

        assertNull("expected a null return map after clearing superuser flag", returnMap);
    }

    private void setVehicleDeviceDriver(Map<String, DaoMethod> methodMap, Integer deviceID, Integer vehicleID, Integer driverID) {
        callUsingDAO(methodMap, "setVehicleDevice", new Object[] { vehicleID, deviceID });
        callUsingDAO(methodMap, "setVehicleDriver", new Object[] { vehicleID, driverID });

        Map<String, Object> returnMap = findUsingDAO(methodMap, "getVehicle", new Object[] { vehicleID });
        assertEquals("expected deviceID set for vehicle ", deviceID, returnMap.get("deviceID"));
        assertEquals("expected driverID set for vehicle ", driverID, returnMap.get("driverID"));

        callUsingDAO(methodMap, "clrVehicleDevice", new Object[] { vehicleID, deviceID });

        returnMap = findUsingDAO(methodMap, "getVehicle", new Object[] { vehicleID });
        assertNull("expected deviceID cleared for vehicle ", returnMap.get("deviceID"));
    }

    private void createUpdateCellblock(Map<String, DaoMethod> methodMap, Integer accountID, Integer driverID) {

        Cellblock cellblock = new Cellblock();
        cellblock.setDriverID(driverID);
        cellblock.setAcctID(accountID);
        cellblock.setCellStatus(CellStatusType.DISABLED);
        cellblock.setProvider(CellProviderType.CELL_CONTROL);
        cellblock.setProviderUser("luser");
        cellblock.setProviderPassword("666");
        cellblock.setCellPhone("07 noi doi");

        createUsingDAO(methodMap, "createCellblock", new Object[] { driverID, cellblock });

        cellblock.setCellPhone("5555555555");

        Integer changedCount = updateUsingDAO(methodMap, "updateCellblock", new Object[] { driverID, cellblock });
        assertEquals("Changed count", 1, changedCount.intValue());

        Map<String, Object> returnMap = findUsingDAO(methodMap, "getCellblock", new Object[] { driverID });
        assertEquals("expected field to change", cellblock.getCellPhone(), returnMap.get("cellPhone"));

        deleteUsingDAO(methodMap, "deleteCellblock", new Object[] { driverID });

        returnMap = findUsingDAO(methodMap, "getCellblock", new Object[] { driverID });
        assertNull("expected no results after delete", returnMap);

    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> findUsingDAO(Map<String, DaoMethod> methodMap, String methodName, Object[] args) {
        DaoMethod daoMethod = methodMap.get(methodName);
        assertNotNull("DAO method not defined for  " + methodName, daoMethod);
        try {
            return (Map<String, Object>) daoResult(daoMethod, args);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        fail("dao call failed for " + methodName);
        return null;
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> findAllUsingDAO(Map<String, DaoMethod> methodMap, String methodName, Object[] args) {
        DaoMethod daoMethod = methodMap.get(methodName);
        assertNotNull("DAO method not defined for  " + methodName, daoMethod);
        try {
            return (List<Map<String, Object>>) daoResult(daoMethod, args);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        fail("dao call failed for " + methodName);
        return null;
    }

    @SuppressWarnings("unchecked")
    private Integer updateUsingDAO(Map<String, DaoMethod> methodMap, String methodName, Object[] args) {
        DaoMethod daoMethod = methodMap.get(methodName);
        assertNotNull("DAO method not defined for  " + methodName, daoMethod);
        try {
            Map<String, Object> returnMap = (Map<String, Object>) daoResult(daoMethod, args);
            return returnMap == null ? null : (Integer) returnMap.get(SiloServiceImpl.COUNT_KEY);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        fail("dao call failed for " + methodName);
        return null;
    }

    private void callUsingDAO(Map<String, DaoMethod> methodMap, String methodName, Object[] args) {
        DaoMethod daoMethod = methodMap.get(methodName);
        assertNotNull("DAO method not defined for  " + methodName, daoMethod);
        try {
            daoResult(daoMethod, args);
        } catch (Throwable e) {
            e.printStackTrace();
            fail("dao call failed for " + methodName);
        }

    }

    @SuppressWarnings("unchecked")
    private Integer createUsingDAO(Map<String, DaoMethod> methodMap, String methodName, Object args[]) {
        DaoMethod daoMethod = methodMap.get(methodName);
        assertNotNull("DAO method not defined for  " + methodName, daoMethod);
        try {
            Map<String, Object> returnMap = (Map<String, Object>) daoResult(daoMethod, args);
            return returnMap == null ? null : (Integer) returnMap.get(SiloServiceImpl.ID_KEY);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        fail("dao call failed for " + methodName);
        return null;
    }

    @SuppressWarnings("unchecked")
    private Integer deleteUsingDAO(Map<String, DaoMethod> methodMap, String methodName, Object args[]) {
        DaoMethod daoMethod = methodMap.get(methodName);
        assertNotNull("DAO method not defined for  " + methodName, daoMethod);
        try {
            Map<String, Object> returnMap = (Map<String, Object>) daoResult(daoMethod, args);
            return returnMap == null ? null : (Integer) returnMap.get(SiloServiceImpl.COUNT_KEY);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        fail("dao call failed for " + methodName);
        return null;
    }

}
