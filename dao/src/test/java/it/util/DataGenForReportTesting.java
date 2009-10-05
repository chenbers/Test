package it.util;

import static org.junit.Assert.assertNotNull;
import it.com.inthinc.pro.dao.Util;
import it.config.IntegrationConfig;
import it.util.DayDataGenForReportTesting.GroupData;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

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
import com.inthinc.pro.dao.hessian.extension.HessianTCPProxyFactory;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
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
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleType;
import com.inthinc.pro.model.app.DeviceSensitivityMapping;
import com.inthinc.pro.model.app.Roles;
import com.inthinc.pro.model.app.States;

public class DataGenForReportTesting {

    public static SiloService siloService;
    public static XMLEncoder xml;
    private static final String PASSWORD="nuN5q/jdjEpJKKA4A6jLTZufWZfIXtxqzjVjifqFjbGg6tfmQFGXbTtcXtEIg4Z7"; // password

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
                            States.getStateByAbbrev("UT"), "12345");
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
        Date expired = Util.genDate(2010, 9, 30);
        
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
        vehicleDAO.setVehicleDriver(vehicleID, driverID);

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
        Person person = new Person(0, acctID, TimeZone.getDefault(), 0, address.getAddrID(), first + "email"+groupID+"@email.com", null, "5555555555", "5555555555", null, null, null, null, null, "emp01", 
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

	private void generateDayData(MCMSimulator mcmSim, Date date, Integer driverType) throws Exception 
	{
		for (GroupData groupData : teamGroupData)
		{
			if (groupData.driverType.equals(driverType))
			{
				
				EventGenerator eventGenerator = new EventGenerator();
				switch (driverType.intValue()) {
				case 0:			// good
					eventGenerator.generateTrip(groupData.device.getImei(), mcmSim, date, new EventGeneratorData(0,0,0,0,30));
					break;
				case 1:			// intermediate
					eventGenerator.generateTrip(groupData.device.getImei(), mcmSim, date, new EventGeneratorData(1,1,1,1,25));
				break;
				case 2:			// bad
					eventGenerator.generateTrip(groupData.device.getImei(), mcmSim, date, new EventGeneratorData(5,5,5,5,20));
				break;
				
				}
			}
		}
			
		
	}
	private void waitForIMEIs() {
		
		System.out.print("Waiting for IMEIs to propagate to central");
		for (int attempt = 0; attempt < 40; attempt++)
		{
			System.out.print(".");
			try {
				Thread.sleep(10000l);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
    
    public static void main(String[] args)
    {
        String xmlPath = null;
        if (args.length == 1)
        {
            xmlPath = args[0];        
        }
        else
        {
        	System.out.println("Please specify the full path to the xml file for storing data generated.");
        	System.exit(0);
        }

        IntegrationConfig config = new IntegrationConfig();

        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());
        
        siloService = new SiloServiceCreator(host, port).getService();
        
        
        DataGenForReportTesting  testData = new DataGenForReportTesting();
        try
        {
            System.out.println(" -- test data generation start -- ");
            xml = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(xmlPath)));
            System.out.println(" saving output to " + xmlPath);
            testData.createTestData();
            
            // wait for imeis to hit central server
            testData.waitForIMEIs();
            
            // generate data for today (midnight) and 30 previous days
            HessianTCPProxyFactory factory = new HessianTCPProxyFactory();
            MCMSimulator mcmSim = (MCMSimulator) factory.create(MCMSimulator.class, config.getProperty(IntegrationConfig.MCM_HOST), config.getIntegerProp(IntegrationConfig.MCM_PORT));
            int numDays = 30;
            for (int teamType = GOOD; teamType <= BAD; teamType++)
            {
            	for (int day = numDays; day > 0; day--)
            	{
                    int dateInSec = DateUtil.getDaysBackDate(DateUtil.getTodaysDate(), day, TimeZone.getDefault().getDisplayName());
                    Date startDate = new Date((long)dateInSec * 1000l);
            		testData.generateDayData(mcmSim, startDate, teamType);
            	}
            }
         
            // save date of 1st event
            xml.writeObject(new Integer(DateUtil.getDaysBackDate(DateUtil.getTodaysDate(), numDays, TimeZone.getDefault().getDisplayName())));
            if (xml != null)
            {
                xml.close();
            }
            
            System.out.println(" -- test data generation complete -- ");
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.exit(0);
    }

}
