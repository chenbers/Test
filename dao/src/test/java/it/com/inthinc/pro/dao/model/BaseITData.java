package it.com.inthinc.pro.dao.model;

import static org.junit.Assert.assertNotNull;
import it.com.inthinc.pro.dao.Util;
import it.config.ReportTestConst;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import com.inthinc.hos.model.RuleSetType;
import com.inthinc.pro.dao.hessian.AccountHessianDAO;
import com.inthinc.pro.dao.hessian.AddressHessianDAO;
import com.inthinc.pro.dao.hessian.DeviceHessianDAO;
import com.inthinc.pro.dao.hessian.DriverHessianDAO;
import com.inthinc.pro.dao.hessian.PersonHessianDAO;
import com.inthinc.pro.dao.hessian.RedFlagAlertHessianDAO;
import com.inthinc.pro.dao.hessian.RoleHessianDAO;
import com.inthinc.pro.dao.hessian.StateHessianDAO;
import com.inthinc.pro.dao.hessian.UserHessianDAO;
import com.inthinc.pro.dao.hessian.VehicleHessianDAO;
import com.inthinc.pro.dao.hessian.ZoneHessianDAO;
import com.inthinc.pro.dao.hessian.exceptions.DuplicateEmailException;
import com.inthinc.pro.dao.hessian.exceptions.DuplicateEntryException;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.AccountHOSType;
import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.AlertEscalationItem;
import com.inthinc.pro.model.AlertMessageType;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.DeviceStatus;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.FuelEfficiencyType;
import com.inthinc.pro.model.Gender;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.RedFlagAlert;
import com.inthinc.pro.model.RedFlagLevel;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleType;
import com.inthinc.pro.model.Zone;
import com.inthinc.pro.model.app.SiteAccessPoints;
import com.inthinc.pro.model.app.States;
import com.inthinc.pro.model.configurator.ProductType;
import com.inthinc.pro.model.security.Role;

public abstract class BaseITData {

    public Account account;
    public Address address;
    public Group fleetGroup;
    public User fleetUser;
    public Group districtGroup;
    public User districtUser;
    public Integer startDateInSec;
    public int totalDays;
    public Zone zone;
    public Device noDriverDevice;
    public Vehicle noDriverVehicle;

    public static int GOOD = 0;
    public static int INTERMEDIATE = 1;
    public static int BAD = 2;
    public static int WS_GROUP = 3;
    protected static final String PASSWORD="nuN5q/jdjEpJKKA4A6jLTZufWZfIXtxqzjVjifqFjbGg6tfmQFGXbTtcXtEIg4Z7"; // password
    public static String TEAM_GROUP_NAME[] = {
        "GOOD",
        "INTERMEDIATE",
        "BAD",
        "WS"
    };
    public static Integer TESTING_SILO = 0;    

    protected SiloService siloService;
    protected XMLEncoder xml;
    protected Date assignmentDate;

    protected abstract List<Integer> anyTeam();
    protected abstract void createGroupHierarchy(Integer acctID, boolean includeWSGroup);
    
    protected void createAddress(Integer acctID)
    {
        AddressHessianDAO addressDAO = new AddressHessianDAO();
        addressDAO.setSiloService(siloService);
        address = new Address(null, Util.randomInt(100, 999) + " Street", null, "City " + Util.randomInt(10,99),
                            States.getStateByAbbrev("UT"), "12345", acctID);
        Integer addrID = addressDAO.create(acctID, address);
        address.setAddrID(addrID);

    }

    protected void writeObject(Object obj)
    {
        if (xml != null)
            xml.writeObject(obj);
    }
    protected void init()
    {
        StateHessianDAO stateDAO = new StateHessianDAO();
        stateDAO.setSiloService(siloService);
        
        States states = new States();
        states.setStateDAO(stateDAO);
        states.init();

        RoleHessianDAO roleDAO = new RoleHessianDAO();
        roleDAO.setSiloService(siloService);

        SiteAccessPoints siteAccessPoints = new SiteAccessPoints();
        siteAccessPoints.setRoleDAO(roleDAO);
        siteAccessPoints.init();
        
        DeviceHessianDAO deviceDAO = new DeviceHessianDAO();
        deviceDAO.setSiloService(siloService);
//        DeviceSensitivityMapping mapping = new DeviceSensitivityMapping();
//        mapping.setDeviceDAO(deviceDAO);
//        mapping.init();

        
    }
    
    protected Driver createDriver(Group group)
    {
        return createDriver(group, null);
    }
    
    protected Driver createDriver(Group group, Integer idx)
    {
        DriverHessianDAO driverDAO = new DriverHessianDAO();
        driverDAO.setSiloService(siloService);
        
        Person person = createPerson(group.getAccountID(), group.getGroupID(), ((idx == null) ? "" : idx) + "Driver"+group.getName(), "Last"+group.getGroupID());
        Date expired = Util.genDate(2012, 9, 30);
        
        Driver driver = new Driver(0, person.getPersonID(), Status.ACTIVE, null, null, null, "l"+person.getPersonID(), 
                                        States.getStateByAbbrev("UT"), "ABCD", expired, null, RuleSetType.US_OIL.getCode(), group.getGroupID());

        Integer driverID = driverDAO.create(person.getPersonID(), driver);
        driver.setDriverID(driverID);
        driver.setPerson(person);
        
        System.out.println("driverId: " + driverID);
        return driver;
        
    }

    protected Vehicle createVehicle(Group group, Integer deviceID, Integer driverID)
    {
        return createVehicle(group, deviceID, driverID, null);
    }
    
    protected Vehicle createVehicle(Group group, Integer deviceID, Integer driverID, Integer idx)
    {
        VehicleHessianDAO vehicleDAO = new VehicleHessianDAO();
        vehicleDAO.setSiloService(siloService);

        String name = ((idx == null) ? "" : idx) + "Vehicle" + (driverID == null ? "NO_DRIVER" : group.getName());
        Vehicle vehicle = new Vehicle(0, group.getGroupID(), Status.ACTIVE, name, "Make", "Model", 2000, "Red", 
                    VehicleType.LIGHT, "VIN_" + (deviceID==null? Util.randomInt(1000, 30000) : deviceID), 1000, "UT " + group.getGroupID(), 
                    States.getStateByAbbrev("UT"));
        Integer vehicleID = vehicleDAO.create(group.getGroupID(), vehicle);
        vehicle.setVehicleID(vehicleID);

        if (deviceID != null) {
            DeviceHessianDAO deviceDAO = new DeviceHessianDAO();
            deviceDAO.setSiloService(siloService);
            vehicleDAO.setDeviceDAO(deviceDAO);
            vehicleDAO.setVehicleDevice(vehicleID, deviceID, assignmentDate);
            if (driverID != null) 
                vehicleDAO.setVehicleDriver(vehicleID, driverID, assignmentDate);
        }


        return vehicle;
    }

    protected Device createDevice(Group group, Date assignmentDate)
    {
        DeviceHessianDAO deviceDAO = new DeviceHessianDAO();
        deviceDAO.setSiloService(siloService);
        
        for (int cnt = 0; cnt < 10; cnt++)
        {
            try
            {
                Device device = new Device(0, account.getAcctID(), DeviceStatus.ACTIVE, group.getName()+"Device", 
                        genNumericID(group.getGroupID(), 15), genNumericID(group.getGroupID(), 19), genNumericID(group.getGroupID(), 10), 
                        genNumericID(group.getGroupID(), 10));
                device.setActivated(assignmentDate);
                device.setEmuMd5("696d6acbc199d607a5704642c67f4d86");
                if (group.getName().startsWith("WS")) 
                    device.setProductVersion(ProductType.WAYSMART);
                else device.setProductVersion(ProductType.TIWIPRO_R74);
                System.out.println("device imei " + device.getImei() + " activated date: " + assignmentDate);
                Integer deviceID = deviceDAO.create(account.getAcctID(), device);
                device.setDeviceID(deviceID);
                
                return device;
            }
            catch (DuplicateEntryException ex)
            {
                throw ex;
            }
        }
        
        return null;
    }

    protected String genNumericID(Integer acctID, Integer len)
    {
        String id = "9" + acctID.toString();
        
        for (int i = id.length(); i < len; i++)
        {
            id += "" + Util.randomInt(0, 9);
        }
        
        return id;
    }


    protected User createUser(Integer acctID, Group team)
    {
        UserHessianDAO userDAO = new UserHessianDAO();
        userDAO.setSiloService(siloService);
        
        
        List<Integer> roleIDs =getAccountDefaultRoles(acctID);

        // create a person
        Person person = createPerson(acctID, team.getGroupID(), "Person"+team.getName(), "Last"+team.getGroupID()); 
        String username = "TEST_"+person.getPersonID();
        User user = new User(0, person.getPersonID(), roleIDs, Status.ACTIVE, username, PASSWORD, team.getGroupID());
        Integer userID = userDAO.create(person.getPersonID(), user);
        user.setUserID(userID);
     
        System.out.println(team.getGroupID() + " LOGIN NAME: " + username);
        return user;
        
    }
    
    protected List<Integer> getAccountDefaultRoles(Integer acctID)
    {
        RoleHessianDAO roleDAO = new RoleHessianDAO();
        roleDAO.setSiloService(siloService);
        List<Role> roles = roleDAO.getRoles(acctID);
        List<Integer> roleIDs = new ArrayList<Integer>();
        for (Role role : roles)
            roleIDs.add(role.getRoleID());
        return roleIDs;
    
    }

    protected Person createPerson(Integer acctID, Integer groupID, String first, String last)
    {
        PersonHessianDAO personDAO = new PersonHessianDAO();
        personDAO.setSiloService(siloService);

        int retryCount = 10;
        Person person = null;
        // create a person
        while (retryCount > 0)
            try {
                person = new Person(0, acctID, ReportTestConst.timeZone, address.getAddrID(), 
                        first + "email"+groupID+Util.randomInt(1, 99999)+"@email.com", 
                        null, "5555555555", "5555555555", null, null, null, null, null, "emp_"+groupID+"_"+acctID+"_"+Util.randomInt(1, 99999), 
                        null, "title", "dept", first, "m", last, "jr", Gender.MALE, 65, 180, new Date(), Status.ACTIVE, 
                        MeasurementType.ENGLISH, FuelEfficiencyType.MPG_US, Locale.getDefault());
                person.setCrit(1);
                person.setWarn(1);
                person.setInfo(1);
        
                Integer personID = personDAO.create(acctID, person);
                person.setPersonID(personID);
                retryCount = 0;
            }
            catch (DuplicateEmailException ex) {
                retryCount--;
            }

        return person;
    }

    protected void createAccount()
    {
        AccountHessianDAO accountDAO = new AccountHessianDAO();
        accountDAO.setSiloService(siloService);
        
        account = new Account(null, Status.ACTIVE);
        String timeStamp = Calendar.getInstance().getTime().toString();
System.out.println(timeStamp);        
        account.setAcctName("TEST " + timeStamp.substring(11));
        account.setHos(AccountHOSType.HOS_SUPPORT);
System.out.println("acct name: " + "TEST " + timeStamp.substring(11));        

        // create
        Integer siloID = TESTING_SILO;
        Integer acctID = accountDAO.create(siloID, account);
        account.setAcctID(acctID);
    }
    protected void createRedFlagAlerts() {
        RedFlagAlertHessianDAO redFlagAlertDAO = new RedFlagAlertHessianDAO();
        redFlagAlertDAO.setSiloService(siloService);

        // speeding alert (5 mph over any speed, WARNING level)
        RedFlagAlert redFlagAlert = initRedFlagAlert(AlertMessageType.ALERT_TYPE_SPEEDING);
        redFlagAlert.setSpeedSettings(new Integer[] {5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,});
        redFlagAlert.setSeverityLevel(RedFlagLevel.WARNING);
        addRedFlagAlert(redFlagAlert, redFlagAlertDAO);

        // aggressive driving all  (level 1)  CRITICAL
        redFlagAlert = initRedFlagAlert(AlertMessageType.ALERT_TYPE_HARD_BUMP);
        redFlagAlert.setHardAcceleration(Integer.valueOf(1));
        redFlagAlert.setSeverityLevel(RedFlagLevel.CRITICAL);
        redFlagAlert.setHardBrake(Integer.valueOf(1));
        redFlagAlert.setHardTurn(Integer.valueOf(1));
        redFlagAlert.setHardVertical(Integer.valueOf(1));
        addRedFlagAlert(redFlagAlert, redFlagAlertDAO);

        // seat belt INFO
        redFlagAlert = initRedFlagAlert(AlertMessageType.ALERT_TYPE_SEATBELT);
        redFlagAlert.setSeverityLevel(RedFlagLevel.INFO);
        addRedFlagAlert(redFlagAlert, redFlagAlertDAO);

        // crash CRITICAL
        redFlagAlert = initRedFlagAlert(AlertMessageType.ALERT_TYPE_CRASH);
        redFlagAlert.setSeverityLevel(RedFlagLevel.CRITICAL);
        addRedFlagAlert(redFlagAlert, redFlagAlertDAO);
        
        // tampering INFO
        redFlagAlert = initRedFlagAlert(AlertMessageType.ALERT_TYPE_TAMPERING);
        redFlagAlert.setSeverityLevel(RedFlagLevel.INFO);
        addRedFlagAlert(redFlagAlert, redFlagAlertDAO);
        
        // low battery INFO
        redFlagAlert = initRedFlagAlert(AlertMessageType.ALERT_TYPE_LOW_BATTERY);
        redFlagAlert.setSeverityLevel(RedFlagLevel.INFO);
        addRedFlagAlert(redFlagAlert, redFlagAlertDAO);

        // no Driver INFO
        redFlagAlert = initRedFlagAlert(AlertMessageType.ALERT_TYPE_NO_DRIVER);
        redFlagAlert.setSeverityLevel(RedFlagLevel.INFO);
        addRedFlagAlert(redFlagAlert, redFlagAlertDAO);

    
    }


    protected RedFlagAlert initRedFlagAlert(AlertMessageType type) {
        List<String> emailList = new ArrayList<String>();
        emailList.add("cjennings@inthinc.com");
        List<AlertMessageType> list = new ArrayList<AlertMessageType>();
        list.add(type);
        RedFlagAlert redFlagAlert = new RedFlagAlert(list, account.getAcctID(),
            fleetUser.getUserID(),
            type + " Red Flag", type + " Red Flag Description", 0,
            1439, // start/end time
            anyDay(), 
            anyTeam(),
            null, // driverIDs
            null, // vehicleIDs
            null, // vehicleTypeIDs
            notifyPersonList(),
            null, // emailTo
            null,
            null, null, null, null,
            RedFlagLevel.WARNING,null,
            escalationList(),5, null,5);
        return redFlagAlert;
    }
    
    protected RedFlagAlert initRedFlagAlertMultiple(List<AlertMessageType> types) {
        List<String> emailList = new ArrayList<String>();
        emailList.add("cjennings@inthinc.com");
        StringBuilder typeBuilder = new StringBuilder();
        StringBuilder descriptionBuilder = new StringBuilder();
        
        for(AlertMessageType amt:types){
            typeBuilder.append(amt + " Red Flag");
            descriptionBuilder.append(amt + " Red Flag Description\n");
        }
        RedFlagAlert redFlagAlert = new RedFlagAlert(types, account.getAcctID(),
            fleetUser.getUserID(),
            typeBuilder.toString(), descriptionBuilder.toString(), 0,
            1439, // start/end time
            anyDay(), 
            anyTeam(),
            null, // driverIDs
            null, // vehicleIDs
            null, // vehicleTypeIDs
            notifyPersonList(),
            null, // emailTo
            null,
            null, null, null, null,
            RedFlagLevel.WARNING,null,
            escalationList(),5, null,5);
        return redFlagAlert;
    }


    protected void addRedFlagAlert(RedFlagAlert redFlagAlert,
            RedFlagAlertHessianDAO redFlagAlertDAO) {
        
        Integer redFlagAlertID = redFlagAlertDAO.create(account.getAcctID(), redFlagAlert);
        assertNotNull(redFlagAlertID);
        redFlagAlert.setAlertID(redFlagAlertID);
        xml.writeObject(redFlagAlert);
    }


    protected void createZoneAlert() {
        // zone alert pref for enter/leave zone any time, any day, both teams
        RedFlagAlertHessianDAO zoneAlertDAO = new RedFlagAlertHessianDAO();
        zoneAlertDAO.setSiloService(siloService);
        List<AlertMessageType>list = new ArrayList<AlertMessageType>(EnumSet.of(AlertMessageType.ALERT_TYPE_ENTER_ZONE,AlertMessageType.ALERT_TYPE_EXIT_ZONE));
        RedFlagAlert zoneAlert = new RedFlagAlert(list,account.getAcctID(), this.fleetUser.getUserID(), 
                "Zone Alert Profile", "Zone Alert Profile Description", 0, 1439, // start/end time setting to null to indicate anytime?
                anyDay(), anyTeam(), null, // driverIDs
                null, // vehicleIDs
                null, // vehicleTypeIDs
                notifyPersonList(), // notifyPersonIDs
                null, // emailTo
                null,//speed settings
                null,null,null,null,//aggressive driving settings
                RedFlagLevel.INFO, zone.getZoneID(),
                escalationList(),5, null,5);
        Integer zoneAlertID = zoneAlertDAO.create(account.getAcctID(), zoneAlert);
        assertNotNull(zoneAlertID);
        zoneAlert.setAlertID(zoneAlertID);
        xml.writeObject(zoneAlert);

    }



    protected void createZone() {
        ZoneHessianDAO zoneDAO = new ZoneHessianDAO();
        zoneDAO.setSiloService(siloService);

        // create a zone to use
        zone = new Zone(0, account.getAcctID(), Status.ACTIVE, "Zone With Alerts", "123 Street, Salt Lake City, UT 84107", fleetGroup.getGroupID());
        List<LatLng> points = new ArrayList<LatLng>();
        zone.setName("Zone " + account.getAcctID());
        points.add(new LatLng(32.96453094482422f, -117.12944793701172f ));
        points.add(new LatLng(32.96453094482422f, -117.12352752685547f));
        points.add(new LatLng(32.96186447143555f, -117.12352752685547f));
        points.add(new LatLng(32.96186447143555f, -117.12944793701172f));
        points.add(new LatLng(32.96453094482422f, -117.12944793701172f));
        zone.setPoints(points);
        Integer zoneID = zoneDAO.create(account.getAcctID(), zone);
        zone.setZoneID(zoneID);
        xml.writeObject(zone);

    }

    protected List<Boolean> anyDay() {
        List<Boolean> dayOfWeek = new ArrayList<Boolean>();
        for (int i = 0; i < 7; i++)
            dayOfWeek.add(true);
        return dayOfWeek;
    }

    protected List<Integer> notifyPersonList() {
        List<Integer> notifyPersonIDList = new ArrayList<Integer>();
        notifyPersonIDList.add(fleetUser.getPersonID());
        return notifyPersonIDList;
    }
    protected List<AlertEscalationItem>escalationList(){
        List<AlertEscalationItem> escalationPersonIDList = new ArrayList<AlertEscalationItem>();
        escalationPersonIDList.add(new AlertEscalationItem(fleetUser.getPersonID(), 1));
        escalationPersonIDList.add(new AlertEscalationItem(districtUser.getPersonID(),0));
        return escalationPersonIDList;
        
    }
    protected <T> T getNext(XMLDecoder xmlDecoder, Class<T> expectedType) throws Exception {
        Object result = xmlDecoder.readObject();
        if (expectedType.isInstance(result)) {
            return (T) result;
        } else {
            throw new Exception("Expected " + expectedType.getName());
        }
    }

    



}
