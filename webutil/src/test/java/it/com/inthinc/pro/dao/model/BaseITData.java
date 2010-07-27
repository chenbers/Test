package it.com.inthinc.pro.dao.model;

import static org.junit.Assert.assertNotNull;
import it.com.inthinc.pro.dao.Util;
import it.config.ReportTestConst;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
import com.inthinc.pro.dao.hessian.ZoneAlertHessianDAO;
import com.inthinc.pro.dao.hessian.ZoneHessianDAO;
import com.inthinc.pro.dao.hessian.exceptions.DuplicateEmailException;
import com.inthinc.pro.dao.hessian.exceptions.DuplicateEntryException;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Address;
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
import com.inthinc.pro.model.ZoneAlert;
import com.inthinc.pro.model.app.DeviceSensitivityMapping;
import com.inthinc.pro.model.app.SiteAccessPoints;
import com.inthinc.pro.model.app.States;
import com.inthinc.pro.model.security.Role;

public abstract class BaseITData {

	public Account account;
	public Address address;
	public Group fleetGroup;
	public User fleetUser;
	public Group districtGroup;
	public Integer startDateInSec;
	public int totalDays;
	public Zone zone;
	public Device noDriverDevice;
	public Vehicle noDriverVehicle;

    public static int GOOD = 0;
    public static int INTERMEDIATE = 1;
    public static int BAD = 2;
    protected static final String PASSWORD="nuN5q/jdjEpJKKA4A6jLTZufWZfIXtxqzjVjifqFjbGg6tfmQFGXbTtcXtEIg4Z7"; // password
    public static String TEAM_GROUP_NAME[] = {
    	"GOOD",
    	"INTERMEDIATE",
    	"BAD",
    };
    public static Integer TESTING_SILO = 0;    

    protected SiloService siloService;
    protected XMLEncoder xml;
    protected Date assignmentDate;

    protected abstract List<Integer> anyTeam();
    protected abstract void createGroupHierarchy(Integer acctID);
    
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
        DeviceSensitivityMapping mapping = new DeviceSensitivityMapping();
        mapping.setDeviceDAO(deviceDAO);
        mapping.init();

        
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
        person.setEmpid("Emp"+ group.getGroupID());
        Date expired = Util.genDate(2012, 9, 30);
        
        Driver driver = new Driver(0, person.getPersonID(), Status.ACTIVE, null, null, null, "l"+person.getPersonID(), 
                                        States.getStateByAbbrev("UT"), "ABCD", expired, null, null, group.getGroupID());

        Integer driverID = driverDAO.create(person.getPersonID(), driver);
        driver.setDriverID(driverID);
        
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
        Vehicle vehicle = new Vehicle(0, group.getGroupID(), 10, Status.ACTIVE, name, "Make", "Model", 2000, "Red", 
                    VehicleType.LIGHT, "VIN_" + deviceID, 1000, "UT " + group.getGroupID(), 
                    States.getStateByAbbrev("UT"));
        Integer vehicleID = vehicleDAO.create(group.getGroupID(), vehicle);
        vehicle.setVehicleID(vehicleID);
        
        DeviceHessianDAO deviceDAO = new DeviceHessianDAO();
        deviceDAO.setSiloService(siloService);
        vehicleDAO.setDeviceDAO(deviceDAO);

        vehicleDAO.setVehicleDevice(vehicleID, deviceID);
        if (driverID != null) 
        	vehicleDAO.setVehicleDriver(vehicleID, driverID, assignmentDate);

        return vehicle;
    }

    protected Device createDevice(Group group)
    {
        DeviceHessianDAO deviceDAO = new DeviceHessianDAO();
        deviceDAO.setSiloService(siloService);
        
        for (int cnt = 0; cnt < 10; cnt++)
        {
	        try
	        {
		        Device device = new Device(0, account.getAcctID(), DeviceStatus.ACTIVE, group.getName()+"Device", 
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
		        person = new Person(0, acctID, ReportTestConst.timeZone, 0, address.getAddrID(), 
		        		first + "email"+groupID+Util.randomInt(1, 99999)+"@email.com", 
		        		null, "5555555555", "5555555555", null, null, null, null, null, "emp01", 
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
        account.setAcctName("TEST " + timeStamp.substring(15));
System.out.println("acct name: " + "TEST " + timeStamp.substring(15));        

        // create
        Integer siloID = TESTING_SILO;
        Integer acctID = accountDAO.create(siloID, account);
        account.setAcctID(acctID);
    }
    protected void createRedFlagAlerts() {
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

        // no Driver INFO
        redFlagAlert = initRedFlagAlert("No Driver");
        redFlagAlert.setNoDriverLevel(RedFlagLevel.INFO);
        addRedFlagAlert(redFlagAlert, redFlagAlertDAO);

    
    }


    protected RedFlagAlert initRedFlagAlert(String typeStr) {
        List<String> emailList = new ArrayList<String>();
        emailList.add("cjennings@inthinc.com");
    	RedFlagAlert redFlagAlert = new RedFlagAlert(account.getAcctID(), 
    		fleetUser.getUserID(), typeStr + " Red Flag", typeStr + " Red Flag Description", 0,
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
            RedFlagLevel.NONE, RedFlagLevel.NONE, RedFlagLevel.NONE, RedFlagLevel.NONE, RedFlagLevel.NONE);
    	return redFlagAlert;
    }
    


    protected void addRedFlagAlert(RedFlagAlert redFlagAlert,
			RedFlagAlertHessianDAO redFlagAlertDAO) {
		
        Integer redFlagAlertID = redFlagAlertDAO.create(account.getAcctID(), redFlagAlert);
        assertNotNull(redFlagAlertID);
        redFlagAlert.setRedFlagAlertID(redFlagAlertID);
    	xml.writeObject(redFlagAlert);
	}


    protected void createZoneAlert() {
		// zone alert pref for enter/leave zone any time, any day, both teams
        ZoneAlertHessianDAO zoneAlertDAO = new ZoneAlertHessianDAO();
        zoneAlertDAO.setSiloService(siloService);
        ZoneAlert zoneAlert = new ZoneAlert(account.getAcctID(), 
        		fleetUser.getUserID(), "Zone Alert Profile", "Zone Alert Profile Description", 0, 1439, // start/end time setting to null to indicate anytime?
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

    protected <T> T getNext(XMLDecoder xmlDecoder, Class<T> expectedType) throws Exception {
        Object result = xmlDecoder.readObject();
        if (expectedType.isInstance(result)) {
            return (T) result;
        } else {
            throw new Exception("Expected " + expectedType.getName());
        }
    }

    



}
