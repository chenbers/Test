package it.util;

import static org.junit.Assert.assertNotNull;
import it.com.inthinc.pro.dao.Util;
import it.config.IntegrationConfig;
import it.config.ReportTestConst;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

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
import com.inthinc.pro.dao.hessian.ZoneHessianDAO;
import com.inthinc.pro.dao.hessian.exceptions.DuplicateEmailException;
import com.inthinc.pro.dao.hessian.exceptions.DuplicateEmpIDException;
import com.inthinc.pro.dao.hessian.exceptions.DuplicateIMEIException;
import com.inthinc.pro.dao.hessian.exceptions.ProxyException;
import com.inthinc.pro.dao.hessian.exceptions.RemoteServerException;
import com.inthinc.pro.dao.hessian.extension.HessianTCPProxyFactory;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.AlertMessageType;
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
import com.inthinc.pro.model.app.States;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.model.security.Role;

public class DataGenForStressTesting {
    public static SiloService siloService;
    private static final String PASSWORD="nuN5q/jdjEpJKKA4A6jLTZufWZfIXtxqzjVjifqFjbGg6tfmQFGXbTtcXtEIg4Z7"; // password
    private static final String alphabet="ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private String xmlPath;
    private static String configFile;
    
    public static Integer NUM_EVENT_DAYS = 8;

    public static Integer EAST = 0;
    public static Integer WEST = 1;
    public static Integer CENTRAL = 2;
    public static String NAME[] = {
    	"East",
    	"West",
    	"Central",
    };
//    public static Integer TEAM_CNT = 5;
    
    public static Integer TESTING_SILO = 0;    

    Account account;
    Address address;
    Group fleetGroup;
    class GroupData {
    	Group group;
    	List<User> userList = new ArrayList<User>();
    	List<Device> deviceList = new ArrayList<Device>();
    	List<Driver> driverList = new ArrayList<Driver>();
    	List<Vehicle> vehicleList = new ArrayList<Vehicle>();
    }
    List<GroupData> teamGroupData;
    Zone zone;
    Integer zoneID;
    static User fleetUser;
    
    String names[][] = {  // first, last
    		{"James","Miller"},
    		{"John","Smith"},
    		{"Robert","Jones"},
    		{"Michael","White"},
    		{"William","Green"},
    		{"Christopher","House"},
    		{"Daniel","Butler"},
    		{"Paul","Johnson"},
    		{"Mark","Peterson"},
    		{"Donald","Pearson"},
    		{"Ronald","Atkins"},
    		{"Anthony","Jensen"},
    		{"Kevin","Jackson"},
    		{"Jeff","French"},
    		{"Mary","Mitchell"},
    		{"Patricia","Nelson"},
    		{"Linda","Stone"},
    		{"Barbara","Tanner"},
    		{"Elizabeth","O'Malley"},
    		{"Lisa","Richardson"},
    		{"Nancy","Adams"},
    		{"Helen","Washington"},
    		{"Betty","Canfield"},
    		{"Karen","Irish"},
    		{"Michelle","Moore"},
    		{"Laura","Sinclair"},
    		{"Sarah","Preston"},
    		{"Deborah","Williams"},
    		{"David","Brown"},
    		{"Richard","Davis"},
    		{"Charles","Moore"},
    		{"Joseph","Taylor"},
    		{"Thomas","Anderson"},
    		{"George","Thomas"},
    		{"Kenneth","Jackson"},
    		{"Steven","Harris"},
    		{"Brian","Martin"},
    		{"Edward","Thompson"},
    		{"Jennifer","Garcia"},
    		{"Maria","Martinez"},
    		{"Susan","Robinson"},
    		{"Alexandru", "Munteanu"},
   			{"Alin", "Caba"},
   			{"Anca", "Filip"},
    		{"Brigita", "Bara"},
    		{"Camelia", "Casian"},
    		{"Catalena", "Ursu"},
    		{"Catalina", "Lupu"},
    		{"Catarino", "Moldoveanu"},
    		{"Cici", "Deleanu"},
    		{"Claudiu", "Sava"},
    		{"Crina", "Teodorescu"},
    		{"Dragos", "Blaga"},
    		{"Ecaterina", "Antonescu"},
    		{"Elisabeta", "Grigoroiu"},
    		{"Eugen ", "Tudose"},
    		{"Luminita", "Medeleanu"},
    		{"Octavian", "Ionescu"},
    		{"Tavian", "Georgescu"},
    		{"Tullia", "Popa"},
    		{"Virgiliu", "Popescu"}
    };
    	
    String makes[] = { 
    		"Ford",
    		"Volkswagen ",
    		"Chevrolet",
    		"Hyundai",
    		"Honda",
    		"Toyota"
    };
    String models[][] = {
    		{"F-150", "F-250", "Taurus"},
    		{"Jetta", "Rabbit", "Beetle"},
    		{"Blazer"},
    		{"Accent", "Elantra"},
    		{"Accord", "Civic", "CRV"},
    		{"Tundra", "Rav-4"}
    };

    String fleetUserName;
    
    boolean isNewDataSet;
    int numDays;
    int startDateInSec;
    List<String> imeiList;
    
    private void createTestData()
    {
        init();

        // Account
        createAccount();
        System.out.println("account ID: " + account.getAccountID());

        // Address
        createAddress(account.getAccountID());
        
        // Group Hierarchy
        createGroupHierarchy(account.getAccountID());

        // User at fleet level
        fleetUser = createUser(account.getAccountID(), fleetGroup);
        System.out.println("Fleet Level User " + fleetUser.getUsername());
        fleetUserName = fleetUser.getUsername();

        // User at team level
        for (GroupData team : teamGroupData)
        {
        	int num = Util.randomInt(10, 25);
        	for (int i = 0; i < num; i++)
        	{
        		User user = createUser(account.getAccountID(), team.group);
        		Device device = createDevice(team.group, user.getUserID());
        		Driver driver = createDriver(team.group);
        		Vehicle vehicle = createVehicle(team.group, device.getDeviceID(), driver.getDriverID());
        		device.setVehicleID(vehicle.getVehicleID());
                zoneAlert(account.getAccountID(), team.group.getGroupID(), driver.getPerson().getPersonID());
                team.userList.add(user);
                team.deviceList.add(device);
                team.vehicleList.add(vehicle);
                team.driverList.add(driver);
        	}
            redFlagAlert(account.getAccountID(), team.group.getGroupID());
        }
        
    	// zone
    	createZone();
    	
    	// zone alert preferences
    	createZoneAlert();
    	
    	// red flag alert preferences
    	createRedFlagAlerts();

    }
    
    private void createRedFlagAlerts() {
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
        redFlagAlert.setHardBrake(Integer.valueOf(1));
        redFlagAlert.setHardTurn(Integer.valueOf(1));
        redFlagAlert.setHardVertical(Integer.valueOf(1));
        redFlagAlert.setSeverityLevel(RedFlagLevel.CRITICAL);
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
    }


    private RedFlagAlert initRedFlagAlert(AlertMessageType type) {
        List<String> emailList = new ArrayList<String>();
        emailList.add("cjennings@inthinc.com");
        List<AlertMessageType> list = new ArrayList<AlertMessageType>();
        list.add(type);
        RedFlagAlert redFlagAlert = new RedFlagAlert(list, account.getAccountID(), 
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
            null, null,
            null, null, null,
            RedFlagLevel.NONE,null,null,
            null,5,null, 5);
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
        RedFlagAlert redFlagAlert = new RedFlagAlert(types, account.getAccountID(),
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
            RedFlagLevel.WARNING,null,null,
            null,5, null,5);
        return redFlagAlert;
    }
	private void addRedFlagAlert(RedFlagAlert redFlagAlert,
			RedFlagAlertHessianDAO redFlagAlertDAO) {
		
        Integer redFlagAlertID = redFlagAlertDAO.create(account.getAccountID(), redFlagAlert);
        assertNotNull(redFlagAlertID);
        redFlagAlert.setAlertID(redFlagAlertID);
	}


	private void createZoneAlert() {
		// zone alert pref for enter/leave zone any time, any day, both teams
        RedFlagAlertHessianDAO zoneAlertDAO = new RedFlagAlertHessianDAO();
        zoneAlertDAO.setSiloService(siloService);
        List<AlertMessageType>list = new ArrayList<AlertMessageType>(EnumSet.of(AlertMessageType.ALERT_TYPE_ENTER_ZONE,AlertMessageType.ALERT_TYPE_EXIT_ZONE));
        RedFlagAlert zoneAlert = new RedFlagAlert(list,account.getAccountID(), 
        		fleetUser.getUserID(),
        		"Zone Alert Profile", "Zone Alert Profile Description", 0, 1439, // start/end time setting to null to indicate anytime?
                anyDay(), anyTeam(), null, // driverIDs
                null, // vehicleIDs
                null, // vehicleTypeIDs
                notifyPersonList(), // notifyPersonIDs
                null, // emailTo
                null,//speed
                null,null,null,null,//aggressive
                RedFlagLevel.NONE,
                zone.getZoneID(),null,
                null,5,null, 5);
        Integer zoneAlertID = zoneAlertDAO.create(account.getAccountID(), zoneAlert);
        assertNotNull(zoneAlertID);
        zoneAlert.setAlertID(zoneAlertID);

	}



	private void createZone() {
        ZoneHessianDAO zoneDAO = new ZoneHessianDAO();
        zoneDAO.setSiloService(siloService);

        // create a zone to use
        zone = new Zone(0, account.getAccountID(), Status.ACTIVE, "Zone With Alerts", "123 Street, Salt Lake City, UT 84107", fleetGroup.getGroupID());
        List<LatLng> points = new ArrayList<LatLng>();
        zone.setName("Zone " + account.getAccountID());
        points.add(new LatLng(32.96453094482422f, -117.12944793701172f ));
        points.add(new LatLng(32.96453094482422f, -117.12352752685547f));
        points.add(new LatLng(32.96186447143555f, -117.12352752685547f));
        points.add(new LatLng(32.96186447143555f, -117.12944793701172f));
        points.add(new LatLng(32.96453094482422f, -117.12944793701172f));
        zone.setPoints(points);
        zoneID = zoneDAO.create(account.getAccountID(), zone);
        zone.setZoneID(zoneID);

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

    
	private void writeTestDataToXML() throws FileNotFoundException {
		XMLEncoder xml = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(xmlPath)));
		
		xml.writeObject(fleetUserName);
		xml.writeObject(zoneID);
		
        for (GroupData team : teamGroupData) {
        	
        	for (Device device : team.deviceList) {
        		xml.writeObject(device.getImei());
        	}
        		
        }
        xml.close();		
		
	}

    
    private static void zoneAlert(Integer acctID, Integer groupID, Integer... notifyPersonIDs) {
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
        zone.setZoneID(zoneID);

        RedFlagAlertHessianDAO zoneAlertDAO = new RedFlagAlertHessianDAO();
        zoneAlertDAO.setSiloService(siloService);
        List<Boolean> dayOfWeek = new ArrayList<Boolean>();
        for (int i = 0; i < 7; i++)
            dayOfWeek.add(true);
        List<Integer> groupIDList = new ArrayList<Integer>();
        groupIDList.add(groupID);
        List<String> emailList = new ArrayList<String>();
        emailList.add("test@email.com");
        List<Integer> notifyPersonIDList = new ArrayList<Integer>();
        notifyPersonIDList.add(notifyPersonIDs[0]);
        List<AlertMessageType>list = new ArrayList<AlertMessageType>(EnumSet.of(AlertMessageType.ALERT_TYPE_ENTER_ZONE,AlertMessageType.ALERT_TYPE_EXIT_ZONE));
        RedFlagAlert zoneAlert = new RedFlagAlert(list,acctID,
        		fleetUser.getUserID(),
        		"Zone Alert Profile", "Zone Alert Profile Description", 0, 1439, // start/end time setting to null to indicate anytime?
                dayOfWeek, groupIDList, null, // driverIDs
                null, // vehicleIDs
                null, // vehicleTypeIDs
                notifyPersonIDList, // notifyPersonIDs
                emailList, // emailTo
                null,//speed
                null,null,null,null,//aggressive
                RedFlagLevel.NONE,
                zone.getZoneID(),null,
                null,5,null, 5);
        zoneAlert.setNotifyPersonIDs(notifyPersonIDList);
        Integer zoneAlertID = zoneAlertDAO.create(acctID, zoneAlert);
        zoneAlert.setAlertID(zoneAlertID);
    }

    private static void redFlagAlert(Integer acctID, Integer groupID) {
        RedFlagAlertHessianDAO redFlagAlertDAO = new RedFlagAlertHessianDAO();
        redFlagAlertDAO.setSiloService(siloService);
        List<Boolean> dayOfWeek = new ArrayList<Boolean>();
        for (int i = 0; i < 7; i++)
            dayOfWeek.add(true);
        List<Integer> groupIDList = new ArrayList<Integer>();
        groupIDList.add(groupID);
        Integer[] speedSettings = new Integer[15];
        RedFlagLevel[] speedLevels = new RedFlagLevel[15];
        for (int i = 0; i < 15; i++) {
            speedSettings[i] = 5;
            speedLevels[i] = RedFlagLevel.CRITICAL;
        }
        List<String> emailList = new ArrayList<String>();
        emailList.add("test@email.com");
        // speeding alert
        List<AlertMessageType>list = new ArrayList<AlertMessageType>(EnumSet.of(AlertMessageType.ALERT_TYPE_SEATBELT));
        RedFlagAlert redFlagAlert = new RedFlagAlert(list, acctID, 
        		fleetUser.getUserID(),
        		"Red Flag Alert Profile", "Red Flag Alert Profile Description", 0,
                1439, // start/end time
                dayOfWeek, groupIDList,
                null, // driverIDs
                null, // vehicleIDs
                null, // vehicleTypeIDs
                null,
                emailList, // emailTo
                null, null, null, null, null,
                RedFlagLevel.CRITICAL, null,null,
                null,5,null, 5);
        
        Integer redFlagAlertID = redFlagAlertDAO.create(acctID, redFlagAlert);
        redFlagAlert.setAlertID(redFlagAlertID);
    }

    private void createAddress(Integer acctID)
    {
        AddressHessianDAO addressDAO = new AddressHessianDAO();
        addressDAO.setSiloService(siloService);
        address = new Address(null, Util.randomInt(100, 999) + " State Street", null, "Salt Lake City",
                            States.getStateByAbbrev("UT"), "84120", acctID);
        Integer addrID = addressDAO.create(acctID, address);
        address.setAddrID(addrID);

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

//        Roles roles = new Roles();
//        roles.setRoleDAO(roleDAO);
//        roles.init();
        
        DeviceHessianDAO deviceDAO = new DeviceHessianDAO();
        deviceDAO.setSiloService(siloService);
//        DeviceSensitivityMapping mapping = new DeviceSensitivityMapping();
//        mapping.setDeviceDAO(deviceDAO);
//        mapping.init();

        
    }
    
    private Driver createDriver(Group group)
    {
        DriverHessianDAO driverDAO = new DriverHessianDAO();
        driverDAO.setSiloService(siloService);
        
        String first = getFirstName();
        String last= getLastName();
        String mi= getMI();
        
        // create a person
        Person person = createPerson(group.getAccountID(), group.getGroupID(), first, last, mi); 
        
        Date expired = Util.genDate(2012, 9, 30);
        
        Driver driver = new Driver(0, person.getPersonID(), Status.ACTIVE, null, null,null, "l"+person.getPersonID(), 
                                        States.getStateByAbbrev("UT"), "ABCD", expired, null, null, group.getGroupID());

        Integer driverID = driverDAO.create(person.getPersonID(), driver);
        driver.setDriverID(driverID);
        driver.setPerson(person);
        
        System.out.println("driverId: " + driverID);
        return driver;
        
    }

    private Vehicle createVehicle(Group group, Integer deviceID, Integer driverID)
    {
        VehicleHessianDAO vehicleDAO = new VehicleHessianDAO();
        vehicleDAO.setSiloService(siloService);

        int idx = Util.randomInt(0, makes.length-1);
        String make = makes[idx];
        String model = models[idx][Util.randomInt(0, models[idx].length-1)];
        VehicleType vtype = VehicleType.valueOf(Util.randomInt(0, 2));
        Vehicle vehicle = new Vehicle(0, group.getGroupID(), Status.ACTIVE, make + "_" + model+"_"+deviceID , make, model, Util.randomInt(1995, 2009), "Red", 
                    vtype, "VIN_" + deviceID, 1000, "UT " +deviceID, 
                    States.getStateByAbbrev("UT"));
        Integer vehicleID = vehicleDAO.create(group.getGroupID(), vehicle);
        vehicle.setVehicleID(vehicleID);
        
        DeviceHessianDAO deviceDAO = new DeviceHessianDAO();
        deviceDAO.setSiloService(siloService);
        vehicleDAO.setDeviceDAO(deviceDAO);
        
System.out.println("vehicleID: " + vehicleID);
System.out.println("deviceID: " + deviceID);

        vehicleDAO.setVehicleDevice(vehicleID, deviceID);
        Date assignmentDate = DateUtil.convertTimeInSecondsToDate(DateUtil.getDaysBackDate(DateUtil.getTodaysDate(), NUM_EVENT_DAYS+2, ReportTestConst.TIMEZONE_STR));
        vehicleDAO.setVehicleDriver(vehicleID, driverID, assignmentDate);

        return vehicle;
    }

    private Device createDevice(Group group, Integer uniqueID)
    {
        DeviceHessianDAO deviceDAO = new DeviceHessianDAO();
        deviceDAO.setSiloService(siloService);
        
        for (int retryCnt = 0; retryCnt < 100; retryCnt++) {
System.out.println("retryCnt: " + retryCnt);        	
	        try{
		        Device device = new Device(0, account.getAccountID(), DeviceStatus.ACTIVE, "Device_" + uniqueID, 
		        		genNumericID(uniqueID, 15), genNumericID(uniqueID, 19), genNumericID(uniqueID, 10), 
		        		genNumericID(uniqueID, 10));
//		        , 
//		        		"5555559876");
		        
//		        device.setAccel("1100 50 4");
		        Integer deviceID = deviceDAO.create(account.getAccountID(), device);
		        device.setDeviceID(deviceID);
		        
		        
		        return device;
	        }
	        catch (DuplicateIMEIException ex) {
	        	if (retryCnt == 99) {
	        		System.out.println("dupicate after 99 attempts");
	        		throw ex;
	        	}
	        }
        }
	    return null;
    }

    private String genNumericID(Integer acctID, Integer len)
    {
        String id = "999" + acctID.toString();
        
        for (int i = id.length(); i < len; i++)
        {
            id += ("" + Util.randomInt(0, 9));
        }
        
        return id;
    }



    private User createUser(Integer acctID, Group team)
    {
        UserHessianDAO userDAO = new UserHessianDAO();
        userDAO.setSiloService(siloService);

        String first = getFirstName();
        String last= getLastName();
        String mi = getMI();
        
        // create a person
        Person person = createPerson(acctID, team.getGroupID(), first, last, mi); 


        String username = new String(first.substring(0,1) + mi + last + Util.randomInt(0, 1000)).toLowerCase();
        if (team.getGroupID().equals(fleetGroup.getGroupID()))
        	username = "stress" + Util.randomInt(0, 10000);
        
        User user = new User(0, person.getPersonID(), getAccountDefaultRoles(acctID), Status.ACTIVE, username, PASSWORD, team.getGroupID());
        Integer userID = userDAO.create(person.getPersonID(), user);
        user.setUserID(userID);
     
        System.out.println(team.getGroupID() + " LOGIN NAME: " + username);
        
        return user;
        
    }
    
    private List<Integer> getAccountDefaultRoles(Integer acctID)
    {
		RoleHessianDAO roleDAO = new RoleHessianDAO();
		roleDAO.setSiloService(siloService);
		List<Role> roles = roleDAO.getRoles(acctID);
		List<Integer> roleIDs = new ArrayList<Integer>();
		for (Role role : roles)
			roleIDs.add(role.getRoleID());
		return roleIDs;
	
    }

    private String getLastName() {
		int rnd = Util.randomInt(0, names.length-1);
		return names[rnd][1];
	}

	private String getFirstName() {
		int rnd = Util.randomInt(0, names.length-1);
		return names[rnd][0];
	}
	private String getMI() {
		int rnd = Util.randomInt(0, 25);
		return alphabet.substring(rnd, rnd+1);
	}

	private void createGroupHierarchy(Integer acctID)
    {
        GroupHessianDAO groupDAO = new GroupHessianDAO();
        groupDAO.setSiloService(siloService);
    
        // fleet
        LatLng UTLatLng = new LatLng(40.7672, -111.906);
		fleetGroup = new Group(0, acctID, "Fleet", 0, GroupType.FLEET,  null, "Fleet Group", 5, UTLatLng);
        Integer groupID = groupDAO.create(acctID, fleetGroup);
        fleetGroup.setGroupID(groupID);
        
        teamGroupData = new ArrayList<GroupData>();
        
        // division
        for (int i = EAST; i <= CENTRAL; i++)
        {
        	String name = NAME[i] + " Division";

        	Group districtGroup = new Group(0, acctID, name , fleetGroup.getGroupID(), GroupType.DIVISION,  null, name, 5, UTLatLng);
	        groupID = groupDAO.create(acctID, districtGroup);
	        districtGroup.setGroupID(groupID);
	    
	        // team
	        int teamCnt = Util.randomInt(5, 10);
	        for (int j = 0; j < teamCnt; j++)
	        {
	        	GroupData data = new GroupData();
	        	String teamName = NAME[i]+ " Team " + (j+1);
	        	data.group = new Group(0, acctID, teamName, districtGroup.getGroupID(), GroupType.TEAM,  null, teamName, 5, UTLatLng);
	        	groupID = groupDAO.create(acctID, data.group);
	        	data.group.setGroupID(groupID);
	        	teamGroupData.add(data);
	        }
        }
    }
    
    private Person createPerson(Integer acctID, Integer groupID, String first, String last, String mi)
    {
        PersonHessianDAO personDAO = new PersonHessianDAO();
        personDAO.setSiloService(siloService);

        // create a person
        Person person = new Person(0, acctID, ReportTestConst.timeZone, address.getAddrID(), 
        		first + mi + last + groupID+"@email" + Util.randomInt(0, 20) + ".com", null, "5555555555", "5555555555", 
        		null, null, null, null, null, acctID+"emp" + Util.randomInt(0, 99999), 
                null, "title", "dept", first, mi, last, "", Util.randomInt(0, 1) == 0 ? Gender.MALE : Gender.FEMALE, 
                Util.randomInt(60, 75), Util.randomInt(150, 220), DateUtil.getDaysBackDate(new Date(), Util.randomInt(18*365, 50*365)), Status.ACTIVE, 
                MeasurementType.ENGLISH, 
                FuelEfficiencyType.MPG_US, Locale.ENGLISH);

        for (int i = 0; i < 100; i++) {
            try
            {
            	Integer personID = personDAO.create(acctID, person);
            	person.setPersonID(personID);
                return person;
            }
            catch (DuplicateEmailException ex)
            {
            	person.setPriEmail(first+mi+last+groupID+  "@email" + Util.randomInt(0, 20) + i + ".com");
            }
            catch (DuplicateEmpIDException ex)
            {
                person.setEmpid(acctID+"emp" +Util.randomInt(0, 99999)+i);
            }
        }


        return null;
    }

    private void createAccount()
    {
        AccountHessianDAO accountDAO = new AccountHessianDAO();
        accountDAO.setSiloService(siloService);
        
        account = new Account(null, Status.ACTIVE);
        String timeStamp = Calendar.getInstance().getTime().toString();
        account.setAcctName("inthinc " + Util.randomInt(0, 1000));

        // create
        Integer siloID = TESTING_SILO;
        Integer acctID = accountDAO.create(siloID, account);
        account.setAccountID(acctID);
        
    }
	
    private boolean genTestEvent(MCMSimulator mcmSim, Event event, String imei) {
        List<byte[]> noteList = new ArrayList<byte[]>();
System.out.println("Waiting for imei: " + imei);
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
                if (ex.getErrorCode() != 414) {
                    errorFound = true;
                }
                else {
                    if (retryCnt == 300) {
                        System.out.println("Retries failed after 5 min.");
                        errorFound = true;
                    }
                    else {
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

	private void waitForIMEIs(MCMSimulator mcmSim, int eventDateSec) {

		for (GroupData data : this.teamGroupData)
		{

			for (Device device : data.deviceList)
			{
				Event testEvent = new Event(0l, 0, NoteType.STRIPPED_ACKNOWLEDGE_ID_WITH_DATA,//NoteType.LOCATION,
	                    new Date(eventDateSec * 1000l), 60, 0,  33.0089, -117.1100);
				if (!genTestEvent(mcmSim, testEvent, device.getImei()))
				{
					System.out.println("Error: imei has not moved to central server");
					System.exit(1);
				}
			}
		}
	}
	private void generateDayData(MCMSimulator mcmSim, Date date, String imei, Integer zoneID) throws Exception 
	{
		EventGenerator eventGenerator = new EventGenerator();
		EventGeneratorData data = getEventGeneratorData();
		eventGenerator.generateTripExt(imei, mcmSim, date, data, zoneID);

	}
    private EventGeneratorData getEventGeneratorData() {
    	EventGeneratorData data = null;
		boolean includeCrash = (Util.randomInt(0, 100) < 10);
		int cnt = Util.randomInt(0, 100);

		
	    if (cnt < 10)
	    {
	    	// good  (no violations)
	    	data = new EventGeneratorData(0,0,0,0,includeCrash,Util.randomInt(30, 40),0);
	    }
	    else if (cnt < 70)
	    {
	    	// intermediate  ( a few violations)
	    	data = new EventGeneratorData(Util.randomInt(0, 5),Util.randomInt(0, 5),Util.randomInt(0, 5),Util.randomInt(0, 5),includeCrash,Util.randomInt(20, 30),0);
	    }
	    else
	    {
	    	//bad  (lots of violations)
			if (Util.randomInt(0, 1000) > 950)
				includeCrash = true;
	    	data = new EventGeneratorData(Util.randomInt(5, 10),Util.randomInt(5, 10),Util.randomInt(5, 10),Util.randomInt(5, 10),includeCrash,Util.randomInt(10, 20),0);
	    }
		
		return data;
	}

	private void parseArguments(String[] args) {
		// Arguments:
		//		required
		//			0:		NEW  or EVENTS
		//			1:		full path to xml file for storeage/retrieval of current data set
		//		optional:
		//			2: 
		
		String usageErrorMsg = "Usage: DataGenForStressTesting <NEW|EVENTS> <xml file path> [optional if EVENTS: <start date: MM/DD/YYYY> <num days> <config file> ]";
		
        if (args.length < 2)
        {
        	System.out.println(usageErrorMsg);
        	System.exit(1);
        }
        
        if (args[0].equalsIgnoreCase("NEW"))
        {
        	isNewDataSet = true;
        }
        else if (args[0].equalsIgnoreCase("EVENTS"))
        {
        	isNewDataSet = false;
        }
        else
        {
        	System.out.println(usageErrorMsg);
        	System.exit(1);
        }
        
        xmlPath = args[1];
        if (args.length == 3)
        {
        	configFile = args[2];
        	// start of day today (i.e. midnight today)
        	startDateInSec = DateUtil.getDaysBackDate(DateUtil.getTodaysDate(), 1, ReportTestConst.TIMEZONE_STR);
        	numDays = 1;
        }
        else if (args.length >= 4)
        {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    		dateFormat.setTimeZone(ReportTestConst.timeZone);
            String dateStr = args[2];
            String numDaysStr = args[3];
    		try {
    			Date eventGenStartDate = dateFormat.parse(dateStr);
    			startDateInSec = DateUtil.getDaysBackDate((int)DateUtil.convertDateToSeconds(eventGenStartDate), 1, ReportTestConst.TIMEZONE_STR);
    			
    		} catch (ParseException e1) {
            	System.out.println(usageErrorMsg);
            	System.out.println("Command Line Args: expected day format mm/dd/yyyy");
            	System.exit(1);
    		}

    		try
    		{
    			numDays = Integer.valueOf(numDaysStr);
    			if (numDays < 1)
    				throw new NumberFormatException("");
    		}
    		catch (NumberFormatException ne)
    		{
            	System.out.println(usageErrorMsg);
            	System.out.println("Command Line Args: expected numDays greater than 0");
            	System.exit(1);
    		}
            if (args.length == 5) {
            	configFile = args[4];
            }
        	
        }
        else
        {
        	// start of day today (i.e. midnight today)
        	startDateInSec = DateUtil.getDaysBackDate(DateUtil.getTodaysDate(), 1, ReportTestConst.TIMEZONE_STR);
        	numDays = 1;
        }
        
	}


	public static void main(String[] args)
    {
        
        DataGenForStressTesting  testData = new DataGenForStressTesting();
        testData.parseArguments(args);

        IntegrationConfig config = new IntegrationConfig(configFile);
        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());
        siloService = new SiloServiceCreator(host, port).getService();
        
        if (testData.isNewDataSet) {
        
	        try
	        {
	            System.out.println(" -- test data generation start -- ");
	            testData.createTestData();
	            testData.writeTestDataToXML();
	            // wait for imeis to hit central server
	            // generate data for today (midnight) and 30 previous days
	            HessianTCPProxyFactory factory = new HessianTCPProxyFactory();
	            MCMSimulator mcmSim = (MCMSimulator) factory.create(MCMSimulator.class, config.getProperty(IntegrationConfig.MCM_HOST), config.getIntegerProp(IntegrationConfig.MCM_PORT));

	            int todayInSec = DateUtil.getTodaysDate();
	            testData.waitForIMEIs(mcmSim, DateUtil.getDaysBackDate(todayInSec, 1, ReportTestConst.TIMEZONE_STR) + 60);
	            
	            int numDays = NUM_EVENT_DAYS;
	    		for (GroupData data : testData.teamGroupData)
	    		{

	    			for (Device device : data.deviceList)
		            {
		            	for (int day = numDays; day > 0; day--)
		            	{
		                    int dateInSec = DateUtil.getDaysBackDate(todayInSec, day, ReportTestConst.TIMEZONE_STR) + 60;
		                    // startDate should be one minute after midnight in the selected time zone (TIMEZONE_STR) 
		                    Date startDate = new Date((long)dateInSec * 1000l);
		            		testData.generateDayData(mcmSim, startDate, device.getImei(), testData.zoneID);
		            	}
		            }
	    		}
	            System.out.println(" -- test data generation complete -- ");
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	            System.exit(1);
	        }
        }
        else {
            try
            {
                if (!testData.parseTestData())
                {
                	System.out.println("Parse of xml data file failed.  File: " + testData.xmlPath);
                	System.exit(1);
                }

                HessianTCPProxyFactory factory = new HessianTCPProxyFactory();
                MCMSimulator mcmSim = (MCMSimulator) factory.create(MCMSimulator.class, config.getProperty(IntegrationConfig.MCM_HOST), config.getIntegerProp(IntegrationConfig.MCM_PORT));
                for (String imei : testData.imeiList) {
    	        	for (int day = 0; day < testData.numDays; day++)
    	        	{
    	                int dateInSec = testData.startDateInSec + (day * DateUtil.SECONDS_IN_DAY) + 60;
//    	                Date startDate = new Date((long)dateInSec * 1000l);
    	                Date startDate = new Date((long)dateInSec * 1000l + DateUtil.MILLISECONDS_IN_MINUTE*120);
    	        		testData.generateDayData(mcmSim, startDate, imei, testData.zoneID);
    	        	}
                }
             
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        	
        }
        	
        System.exit(0);
    }

	private boolean parseTestData() {
		
		imeiList = new ArrayList<String>();
        try {
//          InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(xmlPath);
          InputStream stream = new FileInputStream(xmlPath);
          XMLDecoder xml = new XMLDecoder(new BufferedInputStream(stream));
          String fleetUserName = getNext(xml, String.class);
          zoneID = getNext(xml, Integer.class);
          String imei = getNext(xml, String.class);
          while (imei != null) {
        	  imeiList.add(imei);
        	  imei = getNext(xml, String.class);
          }
          xml.close();
          return true;
      }
      catch (Exception ex) {
          System.out.println("error reading " + xmlPath);
          ex.printStackTrace();
          return false;
      }

	}


    private static <T> T getNext(XMLDecoder xml, Class<T> expectedType) throws Exception {
    	try {
	        Object result = xml.readObject();
	        if (expectedType.isInstance(result)) {
	            return (T) result;
	        }
	        else {
	            throw new Exception("Expected " + expectedType.getName());
	        }
    	} catch (ArrayIndexOutOfBoundsException ex) {
    		// throws this at end of file
    		return null;
    	}
    }



}
