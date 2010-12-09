package it.util;

import it.com.inthinc.pro.dao.Util;
import it.config.IntegrationConfig;
import it.config.ReportTestConst;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumSet;
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
import com.inthinc.pro.dao.hessian.ZoneHessianDAO;
import com.inthinc.pro.dao.hessian.exceptions.DuplicateEmailException;
import com.inthinc.pro.dao.hessian.exceptions.ProxyException;
import com.inthinc.pro.dao.hessian.exceptions.RemoteServerException;
import com.inthinc.pro.dao.hessian.extension.HessianTCPProxyFactory;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.AlertEscalationItem;
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

public class DataGenForHelpScreenShots {
    public static SiloService siloService;
    private static final String PASSWORD="nuN5q/jdjEpJKKA4A6jLTZufWZfIXtxqzjVjifqFjbGg6tfmQFGXbTtcXtEIg4Z7"; // password

    public static Integer NUM_EVENT_DAYS = 31;

    public static Integer EAST = 0;
    public static Integer WEST = 1;
    public static Integer CENTRAL = 2;
    public static String NAME[] = {
    	"East",
    	"West",
    	"Central",
    };
    public static String NAME_ro[] = {
    	"De Est",
    	"Occidentul",
    	"De Nord",
    };
    public static Integer TEAM_CNT = 5;
    
    public static Integer TESTING_SILO = 0;    
    Account account;
    Address address;
    Group fleetGroup;
    static User fleetUser;
    class GroupData {
    	Group group;
    	List<User> userList = new ArrayList<User>();
    	List<Device> deviceList = new ArrayList<Device>();
    	List<Driver> driverList = new ArrayList<Driver>();
    	List<Vehicle> vehicleList = new ArrayList<Vehicle>();
    }
    List<GroupData> teamGroupData;
    
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
    };
    String names_ro[][] = {  // first, last
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
    
    String localeStr = "";
    Locale locale = Locale.getDefault();    
    private void createTestData()
    {
        init();

        // Account
        createAccount();
        System.out.println("account ID: " + account.getAcctID());

        // Address
        createAddress(account.getAcctID());
        
        // Group Hierarchy
        createGroupHierarchy(account.getAcctID());

        // User at fleet level
        fleetUser = createUser(account.getAcctID(), fleetGroup);
        System.out.println("Fleet Level User " + fleetUser.getUsername());

        // User at team level
        System.out.println("Team Level");
        for (GroupData team : teamGroupData)
        {
        	int num = Util.randomInt(3, 7);
        	for (int i = 0; i < num; i++)
        	{
        		User user = createUser(account.getAcctID(), team.group);
        		Device device = createDevice(team.group, user.getUserID());
        		Driver driver = createDriver(team.group);
        		Vehicle vehicle = createVehicle(team.group, device.getDeviceID(), driver.getDriverID());
        		device.setVehicleID(vehicle.getVehicleID());
                zoneAlert(account.getAcctID(), team.group.getGroupID(), driver.getPerson().getPersonID());
                team.userList.add(user);
                team.deviceList.add(device);
                team.vehicleList.add(vehicle);
                team.driverList.add(driver);
        	}
            redFlagAlert(account.getAcctID(), team.group.getGroupID());
        }
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
        List<AlertEscalationItem> escalationList = new ArrayList<AlertEscalationItem>();
        escalationList.add(new AlertEscalationItem(notifyPersonIDs[0],1));
        escalationList.add(new AlertEscalationItem(notifyPersonIDs[1], -1));
        RedFlagAlert zoneAlert = new RedFlagAlert(EnumSet.of(AlertMessageType.ALERT_TYPE_ENTER_ZONE,AlertMessageType.ALERT_TYPE_ENTER_ZONE),acctID, 
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
                zoneID,
                escalationList,5,5, 5,0);
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
        RedFlagAlert redFlagAlert = new RedFlagAlert(EnumSet.of(AlertMessageType.ALERT_TYPE_SEATBELT), acctID, 
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
                RedFlagLevel.CRITICAL, null,
                null, null, null, null, null);
        
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
//        
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
        
        // create a person
        Person person = createPerson(group.getAccountID(), group.getGroupID(), first, last); 
        
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

        vehicleDAO.setVehicleDevice(vehicleID, deviceID);
        Date assignmentDate = DateUtil.convertTimeInSecondsToDate(DateUtil.getDaysBackDate(DateUtil.getTodaysDate(), NUM_EVENT_DAYS+2, ReportTestConst.TIMEZONE_STR));
        vehicleDAO.setVehicleDriver(vehicleID, driverID, assignmentDate);

        return vehicle;
    }
	private Integer assignDriverToVehicle(Device device, GroupData data,
			Date assignmentDate) {

		VehicleHessianDAO vehicleDAO = new VehicleHessianDAO();
        vehicleDAO.setSiloService(siloService);
        Integer driverID = data.driverList.get(Util.randomInt(0, data.driverList.size()-1)).getDriverID();
        vehicleDAO.setVehicleDriver(device.getVehicleID(), driverID, assignmentDate);
        return driverID;
		
	}

    private Device createDevice(Group group, Integer uniqueID)
    {
        DeviceHessianDAO deviceDAO = new DeviceHessianDAO();
        deviceDAO.setSiloService(siloService);
        
        Device device = new Device(0, account.getAcctID(), DeviceStatus.ACTIVE, "Device_" + uniqueID, 
        		genNumericID(uniqueID, 15), genNumericID(uniqueID, 19), genNumericID(uniqueID, 10), 
        		genNumericID(uniqueID, 10));
//        , 
//        		"5555559876");
        
//        device.setAccel("1100 50 4");
        Integer deviceID = deviceDAO.create(account.getAcctID(), device);
        device.setDeviceID(deviceID);
        
        
        return device;
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



    private User createUser(Integer acctID, Group team)
    {
        UserHessianDAO userDAO = new UserHessianDAO();
        userDAO.setSiloService(siloService);

        String first = getFirstName();
        String last= getLastName();
        
        // create a person
        Person person = createPerson(acctID, team.getGroupID(), first, last); 


        String username = new String(first.substring(0,1) + last + Util.randomInt(0, 1000)).toLowerCase();
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
    	if (localeStr.equals("ro"))
    	{
    		int rnd = Util.randomInt(0, names_ro.length-1);
    		return names_ro[rnd][1];
    	}
    	else
    	{
    		int rnd = Util.randomInt(0, names.length-1);
    		return names[rnd][1];
    	}
	}

	private String getFirstName() {
    	if (localeStr.equals("ro"))
    	{
    		int rnd = Util.randomInt(0, names_ro.length-1);
    		return names_ro[rnd][0];
    	}
    	else
    	{
    		int rnd = Util.randomInt(0, names.length-1);
    		return names[rnd][0];
    	}
	}

	private void createGroupHierarchy(Integer acctID)
    {
        GroupHessianDAO groupDAO = new GroupHessianDAO();
        groupDAO.setSiloService(siloService);
    
        // fleet
        LatLng UTLatLng = null;
    	if (localeStr.equals("ro"))
    	{
            UTLatLng = new LatLng(44.263298, 23.838501);
    		fleetGroup = new Group(0, acctID, "Flota", 0, GroupType.FLEET,  null, "Flota", 5, UTLatLng);
    	}
    	else
    	{
            UTLatLng = new LatLng(40.7672, -111.906);
    		fleetGroup = new Group(0, acctID, "Fleet", 0, GroupType.FLEET,  null, "Fleet Group", 5, UTLatLng);
    	}
        Integer groupID = groupDAO.create(acctID, fleetGroup);
        fleetGroup.setGroupID(groupID);
        
        teamGroupData = new ArrayList<GroupData>();
        
        // division
        for (int i = EAST; i <= CENTRAL; i++)
        {
        	String name = NAME[i] + " Division";
        	if (localeStr.equals("ro"))
        	{
            	name = NAME_ro[i] + " Regiune";
        	}
	        Group districtGroup = new Group(0, acctID, name , fleetGroup.getGroupID(), GroupType.DIVISION,  null, name, 5, UTLatLng);
	        groupID = groupDAO.create(acctID, districtGroup);
	        districtGroup.setGroupID(groupID);
	    
	        // team
	        for (int j = 0; j < TEAM_CNT; j++)
	        {
	        	GroupData data = new GroupData();
	        	String teamName = NAME[i]+ " Team " + (j+1);
	        	if (localeStr.equals("ro"))
	        	{
	            	teamName = NAME_ro[i] + " Echipa " + (j+1);
	        	}
	        	data.group = new Group(0, acctID, teamName, districtGroup.getGroupID(), GroupType.TEAM,  null, teamName, 5, UTLatLng);
	        	groupID = groupDAO.create(acctID, data.group);
	        	data.group.setGroupID(groupID);
	        	teamGroupData.add(data);
	        }
        }
    }
    
    private Person createPerson(Integer acctID, Integer groupID, String first, String last)
    {
        PersonHessianDAO personDAO = new PersonHessianDAO();
        personDAO.setSiloService(siloService);

        // create a person
        Person person = new Person(0, acctID, ReportTestConst.timeZone, address.getAddrID(), first + last +groupID+"@email" + Util.randomInt(0, 20) + ".com", null, "5555555555", "5555555555", 
        		null, null, null, null, null, "emp" + Util.randomInt(0, 9999), 
                null, "title", "dept", first, "", last, "", Util.randomInt(0, 1) == 0 ? Gender.MALE : Gender.FEMALE, 
                Util.randomInt(60, 75), Util.randomInt(150, 220), DateUtil.getDaysBackDate(new Date(), Util.randomInt(18*365, 50*365)), Status.ACTIVE, 
                localeStr.equals("ro") ? MeasurementType.METRIC : MeasurementType.ENGLISH, 
                localeStr.equals("ro") ? FuelEfficiencyType.KMPL : FuelEfficiencyType.MPG_US, locale);

        try
        {
        	Integer personID = personDAO.create(acctID, person);
        	person.setPersonID(personID);
        }
        catch (DuplicateEmailException ex)
        {
        	person.setPriEmail(first+last+groupID+  "@email" + Util.randomInt(0, 20)+ ".com");
        }

        return person;
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
        account.setAcctID(acctID);
        
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
	private void generateDayData(MCMSimulator mcmSim, Date date, String imei, Integer driverID) throws Exception 
	{
		EventGenerator eventGenerator = new EventGenerator();
		EventGeneratorData data = getEventGeneratorData(driverID);
		eventGenerator.generateTrip(imei, mcmSim, date, data);
	}
    private EventGeneratorData getEventGeneratorData(Integer driverID) {
    	EventGeneratorData data = null;
		boolean includeCrash = false;
		int cnt = 0;
		
		boolean found = false;
	    for (GroupData groupData :  teamGroupData)
	    {
	    	for (Driver driver : groupData.driverList)
	    	{
	    		if (driver.getDriverID().equals(driverID))
	    		{
	    			found = true;
	    			break;
	    		}
	    		cnt++;
	    	}
	    	if (found)
	    		break;
	    }
	    
	    if (cnt % 3 == 0)
	    {
	    	// good  (no violations)
	    	data = new EventGeneratorData(0,0,0,0,includeCrash,Util.randomInt(30, 40),0);
	    }
	    else if (cnt %3 == 1)
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


	public static void main(String[] args)
    {
        
        DataGenForHelpScreenShots  testData = new DataGenForHelpScreenShots();

        testData.localeStr = "ro";
        Locale available[] = Locale.getAvailableLocales();
        for (int i = 0; i  < available.length; i++)
        {
       		if (available[i].getLanguage().equals(new Locale(testData.localeStr, "", "").getLanguage()))
       		{
       			testData.locale = available[i];
       			break;
       		}
        }
        IntegrationConfig config = new IntegrationConfig();
        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());
        siloService = new SiloServiceCreator(host, port).getService();
        
	        try
	        {
	            System.out.println(" -- test data generation start -- ");
	            testData.createTestData();
	            
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
		                    Integer driverID = testData.assignDriverToVehicle(device, data, startDate);
		            		testData.generateDayData(mcmSim, startDate, device.getImei(), driverID);
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
        System.exit(0);
    }



}
