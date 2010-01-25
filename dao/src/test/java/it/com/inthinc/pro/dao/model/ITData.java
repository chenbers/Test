package it.com.inthinc.pro.dao.model;

import static org.junit.Assert.assertNotNull;
import it.com.inthinc.pro.dao.Util;
import it.config.ReportTestConst;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.inthinc.pro.dao.hessian.AccountHessianDAO;
import com.inthinc.pro.dao.hessian.AddressHessianDAO;
import com.inthinc.pro.dao.hessian.DeviceHessianDAO;
import com.inthinc.pro.dao.hessian.DriverHessianDAO;
import com.inthinc.pro.dao.hessian.GroupHessianDAO;
import com.inthinc.pro.dao.hessian.PersonHessianDAO;
import com.inthinc.pro.dao.hessian.RedFlagAlertHessianDAO;
import com.inthinc.pro.dao.hessian.RoleHessianDAO;
import com.inthinc.pro.dao.hessian.StateHessianDAO;
import com.inthinc.pro.dao.hessian.UserHessianDAO;
import com.inthinc.pro.dao.hessian.VehicleHessianDAO;
import com.inthinc.pro.dao.hessian.ZoneAlertHessianDAO;
import com.inthinc.pro.dao.hessian.ZoneHessianDAO;
import com.inthinc.pro.dao.hessian.exceptions.DuplicateIMEIException;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.DeviceStatus;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.FuelEfficiencyType;
import com.inthinc.pro.model.Gender;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupType;
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
import com.inthinc.pro.model.ZoneAlert;
import com.inthinc.pro.model.app.DeviceSensitivityMapping;
import com.inthinc.pro.model.app.Roles;
import com.inthinc.pro.model.app.States;

public class ITData {

	public Account account;
	public Address address;
	public Group fleetGroup;
	public User fleetUser;
	public Group districtGroup;
	public List<GroupData> teamGroupData;
	public Integer startDateInSec;
	public int totalDays;
	public Zone zone;

    public static int GOOD = 0;
    public static int INTERMEDIATE = 1;
    public static int BAD = 2;
    private static final String PASSWORD="nuN5q/jdjEpJKKA4A6jLTZufWZfIXtxqzjVjifqFjbGg6tfmQFGXbTtcXtEIg4Z7"; // password
    public static String TEAM_GROUP_NAME[] = {
    	"GOOD",
    	"INTERMEDIATE",
    	"BAD",
    };
    public static Integer TESTING_SILO = 0;    

    private SiloService siloService;
	private XMLEncoder xml;
	private Date assignmentDate;

    
    public void createTestData(SiloService siloService, XMLEncoder xml, Date assignmentDate, boolean includeZonesAndAlerts)
    {
    	this.siloService = siloService;
    	this.xml = xml;
    	this.assignmentDate = assignmentDate;
    	
        init();

        // Account
        createAccount();
        writeObject(account);

        // Address
        createAddress(account.getAcctID());
        writeObject(address);
        
        // Group Hierarchy
        createGroupHierarchy(account.getAcctID());
        writeObject(fleetGroup);
        writeObject(districtGroup);
        for (GroupData team : teamGroupData)
        	writeObject(team.group);

        // User at fleet level
        System.out.println("Fleet Level");
        fleetUser = createUser(account.getAcctID(), fleetGroup);
        writeObject(fleetUser);

        // User at team level
        System.out.println("Team Level");
        for (GroupData team : teamGroupData)
        {
        	team.user = createUser(account.getAcctID(), team.group);
            team.device = createDevice(team.group);
            team.driver = createDriver(team.group);
            team.vehicle = createVehicle(team.group, team.device.getDeviceID(), team.driver.getDriverID());
        	writeObject(team.user);
            writeObject(team.device);
            writeObject(team.driver);
            writeObject(team.vehicle);
            
        }
        if (includeZonesAndAlerts) {
        	// zone
        	createZone();
        	
        	// zone alert preferences
        	createZoneAlert();
        	
        	// red flag alert preferences
        	createRedFlagAlerts();
        }
        
    }
    
    private void createAddress(Integer acctID)
    {
        AddressHessianDAO addressDAO = new AddressHessianDAO();
        addressDAO.setSiloService(siloService);
        address = new Address(null, Util.randomInt(100, 999) + " Street", null, "City " + Util.randomInt(10,99),
                            States.getStateByAbbrev("UT"), "12345", acctID);
        Integer addrID = addressDAO.create(acctID, address);
        address.setAddrID(addrID);

    }

    private void writeObject(Object obj)
    {
        if (xml != null)
            xml.writeObject(obj);
    }
    private void init()
    {
        StateHessianDAO stateDAO = new StateHessianDAO();
        stateDAO.setSiloService(siloService);
        
        States states = new States();
        states.setStateDAO(stateDAO);
        states.init();

        RoleHessianDAO roleDAO = new RoleHessianDAO();
        roleDAO.setSiloService(siloService);

        Roles roles = new Roles();
        roles.setRoleDAO(roleDAO);
        roles.init();
        
        DeviceHessianDAO deviceDAO = new DeviceHessianDAO();
        deviceDAO.setSiloService(siloService);
        DeviceSensitivityMapping mapping = new DeviceSensitivityMapping();
        mapping.setDeviceDAO(deviceDAO);
        mapping.init();

        
    }
    
    private Driver createDriver(Group group)
    {
        DriverHessianDAO driverDAO = new DriverHessianDAO();
        driverDAO.setSiloService(siloService);
        
        Person person = createPerson(group.getAccountID(), group.getGroupID(), "Driver"+group.getName(), "Last"+group.getGroupID());
        Date expired = Util.genDate(2012, 9, 30);
        
        Driver driver = new Driver(0, person.getPersonID(), Status.ACTIVE, null, null, null, "l"+person.getPersonID(), 
                                        States.getStateByAbbrev("UT"), "ABCD", expired, null, null, group.getGroupID());

        Integer driverID = driverDAO.create(person.getPersonID(), driver);
        driver.setDriverID(driverID);
        
        System.out.println("driverId: " + driverID);
        return driver;
        
    }

    private Vehicle createVehicle(Group group, Integer deviceID, Integer driverID)
    {
        VehicleHessianDAO vehicleDAO = new VehicleHessianDAO();
        vehicleDAO.setSiloService(siloService);

        Vehicle vehicle = new Vehicle(0, group.getGroupID(), 10, Status.ACTIVE, "Vehicle" + group.getName(), "Make", "Model", 2000, "Red", 
                    VehicleType.LIGHT, "VIN_" + deviceID, 1000, "UT " + group.getGroupID(), 
                    States.getStateByAbbrev("UT"));
        Integer vehicleID = vehicleDAO.create(group.getGroupID(), vehicle);
        vehicle.setVehicleID(vehicleID);
        
        DeviceHessianDAO deviceDAO = new DeviceHessianDAO();
        deviceDAO.setSiloService(siloService);
        vehicleDAO.setDeviceDAO(deviceDAO);

        vehicleDAO.setVehicleDevice(vehicleID, deviceID);
        vehicleDAO.setVehicleDriver(vehicleID, driverID, assignmentDate);

        return vehicle;
    }

    private Device createDevice(Group group)
    {
        DeviceHessianDAO deviceDAO = new DeviceHessianDAO();
        deviceDAO.setSiloService(siloService);
        
        for (int cnt = 0; cnt < 10; cnt++)
        {
	        try
	        {
		        Device device = new Device(0, account.getAcctID(), DeviceStatus.ACTIVE, "Device", 
		        		genNumericID(group.getGroupID(), 15), genNumericID(group.getGroupID(), 19), genNumericID(group.getGroupID(), 10), 
		        		genNumericID(group.getGroupID(), 10), 
		        		"5555559876");
		        
		        device.setAccel("1100 50 4");
		        device.setEmuMd5("696d6acbc199d607a5704642c67f4d86");
		        System.out.println("device imei " + device.getImei());
		        Integer deviceID = deviceDAO.create(account.getAcctID(), device);
		        device.setDeviceID(deviceID);
		        
		        return device;
	        }
	        catch (DuplicateIMEIException ex)
	        {
	        	throw ex;
	        }
        }
        
        return null;
    }

    private String genNumericID(Integer acctID, Integer len)
    {
        String id = "999" + acctID.toString();
        
        for (int i = id.length(); i < len; i++)
        {
            id += "" + Util.randomInt(0, 9);
        }
        
        return id;
    }



    private User createUser(Integer acctID, Group team)
    {
        UserHessianDAO userDAO = new UserHessianDAO();
        userDAO.setSiloService(siloService);

        // create a person
        Person person = createPerson(acctID, team.getGroupID(), "Person"+team.getName(), "Last"+team.getGroupID()); 


        String username = "TEST_"+person.getPersonID();
        User user = new User(0, person.getPersonID(), Roles.getRoleByName("superUser"), Status.ACTIVE, username, PASSWORD, team.getGroupID());
        Integer userID = userDAO.create(person.getPersonID(), user);
        user.setUserID(userID);
     
        System.out.println(team.getGroupID() + " LOGIN NAME: " + username);
        return user;
        
    }

    private void createGroupHierarchy(Integer acctID)
    {
        GroupHessianDAO groupDAO = new GroupHessianDAO();
        groupDAO.setSiloService(siloService);
    
        // fleet
        fleetGroup = new Group(0, acctID, "Fleet", 0, GroupType.FLEET,  null, "Fleet Group", 5, new LatLng(0.0, 0.0));
        Integer groupID = groupDAO.create(acctID, fleetGroup);
        fleetGroup.setGroupID(groupID);
        
        // district
        districtGroup = new Group(0, acctID, "Division", fleetGroup.getGroupID(), GroupType.DIVISION,  null, "Division Group", 5, new LatLng(0.0, 0.0));
        groupID = groupDAO.create(acctID, districtGroup);
        districtGroup.setGroupID(groupID);
        
        teamGroupData = new ArrayList<GroupData>();
        
        // team
        for (int i = GOOD; i <= BAD; i++)
        {
        	GroupData data = new GroupData();
        	data.driverType = i;
        	data.group = new Group(0, acctID, TEAM_GROUP_NAME[i], districtGroup.getGroupID(), GroupType.TEAM,  null, TEAM_GROUP_NAME[i], 5, new LatLng(0.0, 0.0));
        	groupID = groupDAO.create(acctID, data.group);
        	data.group.setGroupID(groupID);
        	teamGroupData.add(data);
        }
        
    }
    
    private Person createPerson(Integer acctID, Integer groupID, String first, String last)
    {
        PersonHessianDAO personDAO = new PersonHessianDAO();
        personDAO.setSiloService(siloService);

        // create a person
        Person person = new Person(0, acctID, ReportTestConst.timeZone, 0, address.getAddrID(), first + "email"+groupID+"@email.com", null, "5555555555", "5555555555", null, null, null, null, null, "emp01", 
                null, "title", "dept", first, "m", last, "jr", Gender.MALE, 65, 180, new Date(), Status.ACTIVE, MeasurementType.ENGLISH, FuelEfficiencyType.MPG_US, Locale.getDefault());

        Integer personID = personDAO.create(acctID, person);
        person.setPersonID(personID);

        return person;
    }

    private void createAccount()
    {
        AccountHessianDAO accountDAO = new AccountHessianDAO();
        accountDAO.setSiloService(siloService);
        
        account = new Account(null, null, null, Status.ACTIVE);
        String timeStamp = Calendar.getInstance().getTime().toString();
        account.setAcctName("TEST " + timeStamp.substring(15));

        // create
        Integer siloID = TESTING_SILO;
        Integer acctID = accountDAO.create(siloID, account);
        account.setAcctID(acctID);
    }
    private void createRedFlagAlerts() {
        RedFlagAlertHessianDAO redFlagAlertDAO = new RedFlagAlertHessianDAO();
        redFlagAlertDAO.setSiloService(siloService);

        // speeding alert (5 mph over any speed, WARNING level)
        RedFlagAlert redFlagAlert = initRedFlagAlert("Speeding");
        redFlagAlert.setSpeedSettings(new Integer[] {5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,});
        redFlagAlert.setSpeedLevels(new RedFlagLevel[] {RedFlagLevel.WARNING,RedFlagLevel.WARNING,RedFlagLevel.WARNING,RedFlagLevel.WARNING,RedFlagLevel.WARNING,
        							RedFlagLevel.WARNING,RedFlagLevel.WARNING,RedFlagLevel.WARNING,RedFlagLevel.WARNING,RedFlagLevel.WARNING,
        							RedFlagLevel.WARNING,RedFlagLevel.WARNING,RedFlagLevel.WARNING,RedFlagLevel.WARNING,RedFlagLevel.WARNING,}); 
        addRedFlagAlert(redFlagAlert, redFlagAlertDAO);

        // aggressive driving all  (level 1)  CRITICAL
        redFlagAlert = initRedFlagAlert("Aggressive Driving");
        redFlagAlert.setHardAcceleration(Integer.valueOf(1));
        redFlagAlert.setHardAccelerationLevel(RedFlagLevel.CRITICAL);
        redFlagAlert.setHardBrake(Integer.valueOf(1));
        redFlagAlert.setHardBrakeLevel(RedFlagLevel.CRITICAL);
        redFlagAlert.setHardTurn(Integer.valueOf(1));
        redFlagAlert.setHardTurnLevel(RedFlagLevel.CRITICAL);
        redFlagAlert.setHardVertical(Integer.valueOf(1));
        redFlagAlert.setHardVerticalLevel(RedFlagLevel.CRITICAL);
        addRedFlagAlert(redFlagAlert, redFlagAlertDAO);

        // seat belt INFO
        redFlagAlert = initRedFlagAlert("Seat belt");
        redFlagAlert.setSeatBeltLevel(RedFlagLevel.INFO);
        addRedFlagAlert(redFlagAlert, redFlagAlertDAO);

        // crash CRITICAL
        redFlagAlert = initRedFlagAlert("Crash");
        redFlagAlert.setCrashLevel(RedFlagLevel.CRITICAL);
        addRedFlagAlert(redFlagAlert, redFlagAlertDAO);
        
        // tampering INFO
        redFlagAlert = initRedFlagAlert("Tampering");
        redFlagAlert.setTamperingLevel(RedFlagLevel.INFO);
        addRedFlagAlert(redFlagAlert, redFlagAlertDAO);
        
        // low battery INFO
        redFlagAlert = initRedFlagAlert("Low Battery");
        redFlagAlert.setLowBatteryLevel(RedFlagLevel.INFO);
        addRedFlagAlert(redFlagAlert, redFlagAlertDAO);
    }


    private RedFlagAlert initRedFlagAlert(String typeStr) {
        List<String> emailList = new ArrayList<String>();
        emailList.add("cjennings@inthinc.com");
    	RedFlagAlert redFlagAlert = new RedFlagAlert(account.getAcctID(), typeStr + " Red Flag", typeStr + " Red Flag Description", 0,
            1439, // start/end time
            anyDay(), 
            anyTeam(),
            null, // driverIDs
            null, // vehicleIDs
            null, // vehicleTypeIDs
            notifyPersonList(),
            null, // emailTo
            null, null,
            null, null, null, null,
            RedFlagLevel.NONE, RedFlagLevel.NONE, RedFlagLevel.NONE, RedFlagLevel.NONE, 
            RedFlagLevel.NONE, RedFlagLevel.NONE, RedFlagLevel.NONE, RedFlagLevel.NONE);
    	return redFlagAlert;
    }
	private void addRedFlagAlert(RedFlagAlert redFlagAlert,
			RedFlagAlertHessianDAO redFlagAlertDAO) {
		
        Integer redFlagAlertID = redFlagAlertDAO.create(account.getAcctID(), redFlagAlert);
        assertNotNull(redFlagAlertID);
        redFlagAlert.setRedFlagAlertID(redFlagAlertID);
    	xml.writeObject(redFlagAlert);
	}


	private void createZoneAlert() {
		// zone alert pref for enter/leave zone any time, any day, both teams
        ZoneAlertHessianDAO zoneAlertDAO = new ZoneAlertHessianDAO();
        zoneAlertDAO.setSiloService(siloService);
        ZoneAlert zoneAlert = new ZoneAlert(account.getAcctID(), "Zone Alert Profile", "Zone Alert Profile Description", 0, 1439, // start/end time setting to null to indicate anytime?
                anyDay(), anyTeam(), null, // driverIDs
                null, // vehicleIDs
                null, // vehicleTypeIDs
                notifyPersonList(), // notifyPersonIDs
                null, // emailTo
                0, zone.getZoneID(), true, true);
        Integer zoneAlertID = zoneAlertDAO.create(account.getAcctID(), zoneAlert);
        assertNotNull(zoneAlertID);
        zoneAlert.setZoneAlertID(zoneAlertID);
    	xml.writeObject(zoneAlert);

	}



	private void createZone() {
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

	private List<Integer> anyTeam() {
		List<Integer> groupIDList = new ArrayList<Integer>();
        for (GroupData groupData : teamGroupData) {
        	groupIDList.add(groupData.group.getGroupID());
        }
		return groupIDList;
	}


	private List<Boolean> anyDay() {
		List<Boolean> dayOfWeek = new ArrayList<Boolean>();
        for (int i = 0; i < 7; i++)
            dayOfWeek.add(true);
		return dayOfWeek;
	}

	private List<Integer> notifyPersonList() {
		List<Integer> notifyPersonIDList = new ArrayList<Integer>();
        notifyPersonIDList.add(fleetUser.getPersonID());
		return notifyPersonIDList;
	}

	public boolean parseTestData(String xmlPath, SiloService siloService, boolean includeZonesAndAlerts) {
        try {
            InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(xmlPath);
            // InputStream stream = new FileInputStream(xmlPath);
            XMLDecoder xml = new XMLDecoder(new BufferedInputStream(stream));
            account = getNext(xml, Account.class);
            getNext(xml, Address.class);
            fleetGroup = getNext(xml, Group.class);
            districtGroup = getNext(xml, Group.class);
            teamGroupData = new ArrayList<GroupData>();
            for (int i = GOOD; i <= BAD; i++) {
                Group group = getNext(xml, Group.class);
                GroupData groupData = new GroupData();
                groupData.group = group;
                groupData.driverType = i;
                teamGroupData.add(groupData);
            }
            getNext(xml, User.class);
            for (int i = GOOD; i <= BAD; i++) {
                GroupData groupData = teamGroupData.get(i);
                groupData.user = getNext(xml, User.class);
                groupData.device = getNext(xml, Device.class);
                groupData.driver = getNext(xml, Driver.class);
                groupData.vehicle = getNext(xml, Vehicle.class);
            }
            
            if (includeZonesAndAlerts) {
            	zone = getNext(xml, Zone.class);
            	getNext(xml, ZoneAlert.class);
            	for (int i = 0; i < 6; i++) {
            		getNext(xml, RedFlagAlert.class);
            	}
            }
        	startDateInSec = getNext(xml, Integer.class);
        	Integer todayInSec = DateUtil.getDaysBackDate(DateUtil.getTodaysDate(), 0, ReportTestConst.TIMEZONE_STR);
        
        	totalDays = (todayInSec - startDateInSec) / DateUtil.SECONDS_IN_DAY;
            xml.close();
            return dataExists(siloService);
        } catch (Exception ex) {
            System.out.println("error reading " + xmlPath);
            ex.printStackTrace();
            return false;
        }
    }

	private Object getNext(XMLDecoder xml) {
		return xml.readObject();
	}

    private <T> T getNext(XMLDecoder xml, Class<T> expectedType) throws Exception {
        Object result = xml.readObject();
        if (expectedType.isInstance(result)) {
            return (T) result;
        } else {
            throw new Exception("Expected " + expectedType.getName());
        }
    }

    private boolean dataExists(SiloService siloService) {
        // just spot check that account and team exist (this could be more comprehensive)
        AccountHessianDAO accountDAO = new AccountHessianDAO();
        accountDAO.setSiloService(siloService);
        Account existingAccount = accountDAO.findByID(account.getAcctID());
        boolean dataExists = (existingAccount != null);
        if (dataExists) {
            GroupHessianDAO groupDAO = new GroupHessianDAO();
            groupDAO.setSiloService(siloService);
            Group existingTeam = groupDAO.findByID(teamGroupData.get(0).group.getGroupID());
            dataExists = (existingTeam != null && existingTeam.getType().equals(GroupType.TEAM));
        }
        if (!dataExists) {
            System.out.println("TEST DATA is missing: regenerate the base test data set");
        }
        return dataExists;
    }


}
