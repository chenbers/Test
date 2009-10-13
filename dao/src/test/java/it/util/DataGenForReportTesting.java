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
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.inthinc.pro.dao.hessian.RoleHessianDAO;
import com.inthinc.pro.dao.hessian.StateHessianDAO;
import com.inthinc.pro.dao.hessian.UserHessianDAO;
import com.inthinc.pro.dao.hessian.VehicleHessianDAO;
import com.inthinc.pro.dao.hessian.exceptions.ProxyException;
import com.inthinc.pro.dao.hessian.extension.HessianTCPProxyFactory;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Address;
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
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleType;
import com.inthinc.pro.model.app.DeviceSensitivityMapping;
import com.inthinc.pro.model.app.Roles;
import com.inthinc.pro.model.app.States;

public class DataGenForReportTesting {
	public String xmlPath;
	public boolean isNewDataSet;
//	public Date eventGenStartDate;
	public Integer startDateInSec;
	public Integer numDays;
	
    public static SiloService siloService;
    public static XMLEncoder xml;
    private static final String PASSWORD="nuN5q/jdjEpJKKA4A6jLTZufWZfIXtxqzjVjifqFjbGg6tfmQFGXbTtcXtEIg4Z7"; // password

    public static Integer NUM_EVENT_DAYS = 31;

    public static Integer GOOD = 0;
    public static Integer INTERMEDIATE = 1;
    public static Integer BAD = 2;
    public static String TEAM_GROUP_NAME[] = {
    	"GOOD",
    	"INTERMEDIATE",
    	"BAD",
    };
    
    public static Integer TESTING_SILO = 0;    
    Account account;
    Address address;
    Group fleetGroup;
    Group districtGroup;
    class GroupData {
    	Integer driverType;
    	Group group;
    	User user;
    	Device device;
    	Vehicle vehicle;
    	Driver driver;
    }
    List<GroupData> teamGroupData;

    private void createTestData()
    {
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
        User fleetUser = createUser(account.getAcctID(), fleetGroup);
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
        
        Driver driver = new Driver(0, person.getPersonID(), Status.ACTIVE, null, "l"+person.getPersonID(), 
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
                    VehicleType.LIGHT, "VIN_" + group.getGroupID(), 1000, "UT " + group.getGroupID(), 
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

    private Device createDevice(Group group)
    {
        DeviceHessianDAO deviceDAO = new DeviceHessianDAO();
        deviceDAO.setSiloService(siloService);
        
        Device device = new Device(0, account.getAcctID(), DeviceStatus.ACTIVE, "Device", 
        		genNumericID(group.getGroupID(), 15), genNumericID(group.getGroupID(), 19), genNumericID(group.getGroupID(), 10), 
        		genNumericID(group.getGroupID(), 10), 
        		"5555559876");
        
        device.setAccel("1100 50 4");
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
        assertNotNull(personID);
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
    private boolean parseTestData(String xmlPath) {
        try {
//            InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(xmlPath);
            InputStream stream = new FileInputStream(xmlPath);
            XMLDecoder xml = new XMLDecoder(new BufferedInputStream(stream));
            account = getNext(xml, Account.class);
            Address address = getNext(xml, Address.class);
            fleetGroup = getNext(xml, Group.class);
            districtGroup = getNext(xml, Group.class);
            teamGroupData = new ArrayList<GroupData>();
            for (int i = GOOD; i <= BAD; i++)
            {
            	Group group = getNext(xml, Group.class);
            	GroupData groupData = new GroupData();
            	groupData.group = group;
            	groupData.driverType = i;
            	teamGroupData.add(groupData);
            }
            getNext(xml, User.class);
            for (int i = GOOD; i <= BAD; i++)
            {
            	GroupData groupData = teamGroupData.get(i);
            	groupData.user  = getNext(xml, User.class);
            	groupData.device  = getNext(xml, Device.class);
            	groupData.driver  = getNext(xml, Driver.class);
            	groupData.vehicle  = getNext(xml, Vehicle.class);
            }
            xml.close();
            return dataExists();
        }
        catch (Exception ex) {
            System.out.println("error reading " + xmlPath);
            ex.printStackTrace();
            return false;
        }
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


    private boolean dataExists() {
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
        if (!dataExists)
        {
        	System.out.println("TEST DATA is missing: regenerate the base test data set");
        }
        return dataExists;
    }


	private void generateDayData(MCMSimulator mcmSim, Date date, Integer driverType) throws Exception 
	{
		for (GroupData groupData : teamGroupData)
		{
			if (groupData.driverType.equals(driverType))
			{
				
				EventGenerator eventGenerator = new EventGenerator();
				switch (driverType.intValue()) {
				case 0:			// good
					eventGenerator.generateTrip(groupData.device.getImei(), mcmSim, date, new EventGeneratorData(0,0,0,0,false,30,0));
					break;
				case 1:			// intermediate
					eventGenerator.generateTrip(groupData.device.getImei(), mcmSim, date, new EventGeneratorData(1,1,1,1,false,25,50));
				break;
				case 2:			// bad
					eventGenerator.generateTrip(groupData.device.getImei(), mcmSim, date, new EventGeneratorData(5,5,5,5,true,20,100));
				break;
				
				}
			}		
		}
			
		
	}
	
    private boolean genTestEvent(MCMSimulator mcmSim, Event event, String imei) {
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
            catch (Exception e) {
                e.printStackTrace();
                errorFound = true;
            }
        }
        return !errorFound;
    }

	private void waitForIMEIs(MCMSimulator mcmSim) {
		
		for (GroupData data : teamGroupData)
		{
	        Date eventDate = DateUtil.convertTimeInSecondsToDate(DateUtil.getDaysBackDate(DateUtil.getTodaysDate(), NUM_EVENT_DAYS+2, ReportTestConst.TIMEZONE_STR));

			Event testEvent = new Event(0l, 0, EventMapper.TIWIPRO_EVENT_LOCATION,
                    eventDate, 60, 1,  33.0089, -117.1100);
			if (!genTestEvent(mcmSim, testEvent, data.device.getImei()))
			{
				System.out.println("Error: imei has not moved to central server");
				System.exit(1);
			}
		}
	}

	private void parseArguments(String[] args) {
		// Arguments:
		//		required
		//			0:		NEW  or EVENTS
		//			1:		full path to xml file for storeage/retrieval of current data set
		//		optional:
		//			2: 
		
		String usageErrorMsg = "Usage: DataGenForReportTesting <NEW|EVENTS> <xml file path> [optional if EVENTS: <start date: MM/DD/YYYY> <num days>]";
		
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
        
        if (args.length == 4)
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
        
        DataGenForReportTesting  testData = new DataGenForReportTesting();
        testData.parseArguments(args);
    	


        IntegrationConfig config = new IntegrationConfig();
        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());
        siloService = new SiloServiceCreator(host, port).getService();
        
        if (testData.isNewDataSet)
        {
	        try
	        {
	            System.out.println(" -- test data generation start -- ");
	            xml = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(testData.xmlPath)));
	            System.out.println(" saving output to " + testData.xmlPath);
	            testData.createTestData();
	            
	            // wait for imeis to hit central server
	            // generate data for today (midnight) and 30 previous days
	            HessianTCPProxyFactory factory = new HessianTCPProxyFactory();
	            MCMSimulator mcmSim = (MCMSimulator) factory.create(MCMSimulator.class, config.getProperty(IntegrationConfig.MCM_HOST), config.getIntegerProp(IntegrationConfig.MCM_PORT));
	            testData.waitForIMEIs(mcmSim);
	            
	            int numDays = NUM_EVENT_DAYS;
	            int todayInSec = DateUtil.getTodaysDate();
	            for (int teamType = GOOD; teamType <= BAD; teamType++)
	            {
	            	for (int day = numDays; day > 2; day--)
//	            	for (int day = numDays; day > 0; day--)
	            	{
	                    int dateInSec = DateUtil.getDaysBackDate(todayInSec, day, ReportTestConst.TIMEZONE_STR) + 60;
	                    // startDate should be one minute after midnight in the selected time zone (TIMEZONE_STR) 
	                    Date startDate = new Date((long)dateInSec * 1000l);
	            		testData.generateDayData(mcmSim, startDate, teamType);
	            	}
	            }
	         
	            // save date of 1st event
	            xml.writeObject(new Integer(DateUtil.getDaysBackDate(todayInSec, numDays, ReportTestConst.TIMEZONE_STR)));
	            if (xml != null)
	            {
	                xml.close();
	            }
	            
	            System.out.println(" -- test data generation complete -- ");
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	            System.exit(1);
	        }
        }
        else
        {
            try
            {
                if (!testData.parseTestData(testData.xmlPath))
                {
                	System.out.println("Parse of xml data file failed.  File: " + testData.xmlPath);
                	System.exit(1);
                }

                HessianTCPProxyFactory factory = new HessianTCPProxyFactory();
                MCMSimulator mcmSim = (MCMSimulator) factory.create(MCMSimulator.class, config.getProperty(IntegrationConfig.MCM_HOST), config.getIntegerProp(IntegrationConfig.MCM_PORT));
                for (int teamType = GOOD; teamType <= BAD; teamType++)
                {
    	        	for (int day = 0; day < testData.numDays; day++)
    	        	{
    	                int dateInSec = testData.startDateInSec + (day * DateUtil.SECONDS_IN_DAY) + 60;
    	                Date startDate = new Date((long)dateInSec * 1000l);
    	        		testData.generateDayData(mcmSim, startDate, teamType);
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


}
