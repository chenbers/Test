package it.com.inthinc.pro.dao;


import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import it.config.IntegrationConfig;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.dao.hessian.AccountHessianDAO;
import com.inthinc.pro.dao.hessian.AddressHessianDAO;
import com.inthinc.pro.dao.hessian.DeviceHessianDAO;
import com.inthinc.pro.dao.hessian.DriverHessianDAO;
import com.inthinc.pro.dao.hessian.GroupHessianDAO;
import com.inthinc.pro.dao.hessian.PersonHessianDAO;
import com.inthinc.pro.dao.hessian.RoleHessianDAO;
import com.inthinc.pro.dao.hessian.StateHessianDAO;
import com.inthinc.pro.dao.hessian.TimeZoneHessianDAO;
import com.inthinc.pro.dao.hessian.UserHessianDAO;
import com.inthinc.pro.dao.hessian.VehicleHessianDAO;
import com.inthinc.pro.dao.hessian.extension.HessianDebug;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.AccountStatus;
import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.DeviceStatus;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.DriverStatus;
import com.inthinc.pro.model.ForwardCommand;
import com.inthinc.pro.model.ForwardCommandID;
import com.inthinc.pro.model.ForwardCommandStatus;
import com.inthinc.pro.model.Gender;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Role;
import com.inthinc.pro.model.State;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.UserStatus;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleStatus;
import com.inthinc.pro.model.VehicleType;
import com.inthinc.pro.model.app.Roles;
import com.inthinc.pro.model.app.States;
import com.inthinc.pro.model.app.SupportedTimeZones;

public class SiloServiceTest
{
    private static final Logger logger = Logger.getLogger(SiloServiceTest.class);
    
    private static SiloService siloService;
    
    private Account account;
    private Address address;
    private Group fleetGroup;
    private Group regionGroup;
    private Group team1Group;
    private Group team2Group;
    private List<Device> deviceList = new ArrayList<Device>();
    private List<Vehicle> vehicleList = new ArrayList<Vehicle>();
    private List<Person> personList = new ArrayList<Person>();
    private List<User> userList = new ArrayList<User>();
    private List<Driver> driverList = new ArrayList<Driver>();
    
    private static final Integer TESTING_SILO = 0;  // this silo can be wiped out/restored at anytime
    private static final Integer DEVICE_COUNT = 5;
    private static final Integer VEHICLE_COUNT = 3;
    private static final Integer PERSON_COUNT = 3;
    
    static final String PASSWORD="nuN5q/jdjEpJKKA4A6jLTZufWZfIXtxqzjVjifqFjbGg6tfmQFGXbTtcXtEIg4Z7"; // password

    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
        IntegrationConfig config = new IntegrationConfig(new File("./src/test/resources"));

        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());
        
        siloService = new SiloServiceCreator(host, port).getService();
//        HessianDebug.debugIn = true;
//        HessianDebug.debugOut = true;
        HessianDebug.debugRequest = true;
        
        initApp();
        
    }

    private static void initApp()
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
        
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }
    
    @Test
    public void states()
    {
        StateHessianDAO stateDAO = new StateHessianDAO();
        stateDAO.setSiloService(siloService);
        
        States states = new States();
        states.setStateDAO(stateDAO);
        states.init();
        
        assertTrue(States.getStates().size() >= 50);
    }
    
    @Test
    public void roles()
    {
        RoleHessianDAO roleDAO = new RoleHessianDAO();
        roleDAO.setSiloService(siloService);

        Roles roles = new Roles();
        roles.setRoleDAO(roleDAO);
        roles.init();
        
        assertTrue(Roles.getRoles().size() > 0);
    }
    
    @Test
    public void supportedTimeZones()
    {
        TimeZoneHessianDAO timeZoneDAO = new TimeZoneHessianDAO();
        timeZoneDAO.setSiloService(siloService);
        
        SupportedTimeZones supportedTimeZones = new SupportedTimeZones();
        supportedTimeZones.setTimeZoneDAO(timeZoneDAO);
        supportedTimeZones.init();
        
        assertTrue(SupportedTimeZones.getSupportedTimeZones().size() > 0);
        
    }
    
    @Test
    public void siloService()
    {
// TODO: add all of the empty result set cases
        
        // test all create, find, update and any other methods (not delete yet though)
        account();
        Integer acctID = account.getAcctID();
        groupHierarchy(acctID);
        
        // devices
        devices(acctID);
        
        // forward commands to devices
        forwardCommands();
        
        // vehicles
        vehicles(team1Group.getGroupID());
        vehicles(team2Group.getGroupID());
        regionVehicles(regionGroup.getGroupID());

        // assign devices to vehicles in team1 group 
        assignDevicesToVehicles(team1Group.getGroupID());

        // person
        persons(team1Group.getGroupID());
        persons(team2Group.getGroupID());
        
        // user
        users(team2Group.getGroupID());
        
        // driver
        drivers(team1Group.getGroupID());
        
        // assign drivers to vehicles
        assignDriversToVehicles(team1Group.getGroupID());
    }


    private void account()
    {
        AccountHessianDAO accountDAO = new AccountHessianDAO();
        accountDAO.setSiloService(siloService);
        
        account = new Account(null, null, null, AccountStatus.ACCOUNT_ACTIVE);
        
        // create
        Integer siloID = TESTING_SILO;
        Integer acctID = accountDAO.create(siloID, account);
        assertNotNull("Create Account failed", acctID);
        account.setAcctID(acctID);
        logger.debug("CREATED ACCOUNT: " + account.getAcctID());        
        
        // find
        Account savedAccount = accountDAO.findByID(account.getAcctID());
        Util.compareObjects(account, savedAccount);

        Address mailAddress = address(acctID);
        account.setMailID(mailAddress.getAddrID());
        Address billAddress = address(acctID);
        account.setBillID(billAddress.getAddrID());

        // update
        Integer changedCount = accountDAO.update(account);
        assertEquals("Account update count", Integer.valueOf(1), changedCount);
        savedAccount = accountDAO.findByID(account.getAcctID());
        Util.compareObjects(account, savedAccount);

    }

    private Address address(Integer acctID)
    {
        AddressHessianDAO addressDAO = new AddressHessianDAO();
        addressDAO.setSiloService(siloService);
        
        // create
        address = new Address(null, Util.randomInt(100, 999) + " Street", null, "City " + Util.randomInt(10,99),
                            randomState(), "12345");
        Integer addrID = addressDAO.create(acctID, address);
        address.setAddrID(addrID);

        // find
        Address savedAddress= addressDAO.findByID(address.getAddrID());
        Util.compareObjects(address, savedAddress);
                
        // update
        address.setAddr1(Util.randomInt(100, 999) + " Update Street");
        address.setCity("Update City " + Util.randomInt(10,99));
        Integer changedCount = addressDAO.update(address);
        assertEquals("Address update count", Integer.valueOf(1), changedCount);
        savedAddress= addressDAO.findByID(address.getAddrID());
        Util.compareObjects(address, savedAddress);
        return address;
    }
    
    private void groupHierarchy(Integer acctID)
    {
        GroupHessianDAO groupDAO = new GroupHessianDAO();
        groupDAO.setSiloService(siloService);
    
        // fleet
        fleetGroup = new Group(0, acctID, "Fleet", 0);
        Integer groupID = groupDAO.create(acctID, fleetGroup);
        assertNotNull(groupID);
        fleetGroup.setGroupID(groupID);
logger.debug("FLEET: " + groupID);        
        
        // region
        regionGroup = new Group(0, acctID, "Region", fleetGroup.getGroupID());
        groupID = groupDAO.create(acctID, regionGroup);
        assertNotNull(groupID);
        regionGroup.setGroupID(groupID);
logger.debug("REGION: " + groupID);        
        
        // team
        team1Group = new Group(0, acctID, "Team", regionGroup.getGroupID());
        groupID = groupDAO.create(acctID, team1Group);
        assertNotNull(groupID);
        team1Group.setGroupID(groupID);
logger.debug("TEAM 1: " + groupID);        

        team2Group = new Group(0, acctID, "Team 2", regionGroup.getGroupID());
        groupID = groupDAO.create(acctID, team2Group);
        assertNotNull(groupID);
        team2Group.setGroupID(groupID);
logger.debug("TEAM 2: " + groupID);        
        
        // find individual
        Group returnedGroup = groupDAO.findByID(fleetGroup.getGroupID());
        Util.compareObjects(fleetGroup, returnedGroup);
        returnedGroup = groupDAO.findByID(regionGroup.getGroupID());
        Util.compareObjects(regionGroup, returnedGroup);
        returnedGroup = groupDAO.findByID(team1Group.getGroupID());
        Util.compareObjects(team1Group, returnedGroup);
        
        // find group hierarchy
        List<Group> groupList = groupDAO.getGroupHierarchy(acctID, fleetGroup.getGroupID());
        assertEquals("group list size", 4, groupList.size());
        Util.compareObjects(fleetGroup, groupList.get(0));
        Util.compareObjects(regionGroup, groupList.get(1));
        Group teamGroup = groupList.get(2);
        
        if (teamGroup.getGroupID().equals(team1Group.getGroupID()))
        {
            Util.compareObjects(team1Group, teamGroup);
            Util.compareObjects(team2Group, groupList.get(3));
        }
        else 
        {
            Util.compareObjects(team2Group, teamGroup);
            Util.compareObjects(team1Group, groupList.get(3));
        }
        
        // update
        regionGroup.setName("Updated Region");
        Integer changedCount = groupDAO.update(regionGroup);
        assertEquals("Group update count", Integer.valueOf(1), changedCount);
        returnedGroup = groupDAO.findByID(regionGroup.getGroupID());
        Util.compareObjects(regionGroup, returnedGroup);

        // TODO: try changing group status
    }

    private void devices(Integer acctID)
    {
        DeviceHessianDAO deviceDAO = new DeviceHessianDAO();
        deviceDAO.setSiloService(siloService);
        

        // create all as new
        for (int i = 0; i < DEVICE_COUNT; i++)
        {
            Device device = new Device(0, acctID, 1, DeviceStatus.NEW, "Device " + i, "IMEI " + acctID + i, "SIM " + i, "PHONE " + i, "EPHONE " + i);
            Integer deviceID = deviceDAO.create(acctID, device);
            assertNotNull(deviceID);
            device.setDeviceID(deviceID);
            deviceList.add(device);
        }

//TODO: TEST DUPLICATE imei -- should throw exception
        
        // update all to activated
        for (Device device : deviceList)
        {
            device.setStatus(DeviceStatus.ACTIVE);
            Integer changedCount = deviceDAO.update(device);
            assertEquals("Device update count " + device.getName(), Integer.valueOf(1), changedCount);
        }
        
       
        // find
        String ignoreFields[] = {"modified"};
        for (Device device : deviceList)
        {
            Device returnedDevice = deviceDAO.findByID(device.getDeviceID());
            Util.compareObjects(device, returnedDevice, ignoreFields);
        }

        // find all
        List<Device> returnDeviceList = deviceDAO.getDevicesByAcctID(acctID);
        assertEquals("device count for account", deviceList.size(), returnDeviceList.size());

        
        for (Device device : deviceList)
        {
            boolean found = false;
            for (Device returnedDevice : returnDeviceList)
            {
                if (returnedDevice.getDeviceID().equals(device.getDeviceID()))
                {
                    Util.compareObjects(device, returnedDevice, ignoreFields);
                    found = true;
                    break;
                }
            }
            assertTrue("Not Returned in list for account Device " + device.getName(), found);
        }
        
        
    }
    private void forwardCommands()
    {
        DeviceHessianDAO deviceDAO = new DeviceHessianDAO();
        deviceDAO.setSiloService(siloService);
        
        Device device = deviceList.get(0);
        ForwardCommand stringDataCmd = new ForwardCommand(0, ForwardCommandID.ADD_VALID_CALLER, "1 555555123"+Util.randomInt(1,9), ForwardCommandStatus.STATUS_QUEUED);
        ForwardCommand intDataCmd = new ForwardCommand(0, ForwardCommandID.SET_MSGS_PER_NOTIFICATION, Util.randomInt(1, 4), ForwardCommandStatus.STATUS_QUEUED);
        ForwardCommand noDataCmd = new ForwardCommand(0, ForwardCommandID.BUZZER_SEATBELT_DISABLE, 0, ForwardCommandStatus.STATUS_QUEUED);

        deviceDAO.queueForwardCommand(device.getDeviceID(), stringDataCmd);
        deviceDAO.queueForwardCommand(device.getDeviceID(), intDataCmd);
        deviceDAO.queueForwardCommand(device.getDeviceID(), noDataCmd);
        
        List<ForwardCommand> queuedCommands = deviceDAO.getForwardCommands(device.getDeviceID(), ForwardCommandStatus.STATUS_QUEUED);
        assertEquals("queued forward commands", 3, queuedCommands.size());

        String ignoreFields[] = {"modified", "fwdID"};
        for (ForwardCommand forwardCommand : queuedCommands)
        {
            if (forwardCommand.getCmd().equals(ForwardCommandID.ADD_VALID_CALLER))
            {
                Util.compareObjects(stringDataCmd, forwardCommand, ignoreFields);
            }
            else if (forwardCommand.getCmd().equals(ForwardCommandID.SET_MSGS_PER_NOTIFICATION))
            {
                Util.compareObjects(intDataCmd, forwardCommand, ignoreFields);
                
            } 
            else if (forwardCommand.getCmd().equals(ForwardCommandID.BUZZER_SEATBELT_DISABLE))
            {
                Util.compareObjects(noDataCmd, forwardCommand, ignoreFields);
                
            }
            else
            {
                fail("Unexpected queued forward command" + forwardCommand.getFwdID());
            }
        }
    }

    private void vehicles(Integer groupID)
    {
        VehicleHessianDAO vehicleDAO = new VehicleHessianDAO();
        vehicleDAO.setSiloService(siloService);
        
        // create
        for (int i = 0; i < VEHICLE_COUNT; i++)
        {
            Vehicle vehicle = new Vehicle(0, groupID, 10, VehicleStatus.DISABLED, "Vehicle " + i, "Make " + i, "Model " + i, 2000 + i, "COLOR " + i, 
                    VehicleType.valueOf(Util.randomInt(0, VehicleType.values().length-1)), "VIN_" + groupID + "_"+ i, 1000, "License " + i, 
                    randomState());
            Integer vehicleID = vehicleDAO.create(groupID, vehicle);
            assertNotNull(vehicleID);
            vehicle.setVehicleID(vehicleID);
            vehicleList.add(vehicle);
        }
        
        
        // update all to activated
        for (Vehicle vehicle : vehicleList)
        {
            if (vehicle.getGroupID().equals(groupID))
            {
                vehicle.setStatus(VehicleStatus.ACTIVE);
                Integer changedCount = vehicleDAO.update(vehicle);
                assertEquals("Vehicle update count " + vehicle.getName(), Integer.valueOf(1), changedCount);
            }
        }
        
        // TODO: find out if we need this field (costPerHour) -- not in the backend
        String ignoreFields[] = {"costPerHour", "modified"};
        for (Vehicle vehicle : vehicleList)
        {
            if (vehicle.getGroupID().equals(groupID))
            {
                Vehicle returnedVehicle = vehicleDAO.findByID(vehicle.getVehicleID());
                Util.compareObjects(vehicle, returnedVehicle, ignoreFields);
            }
        }
        
        
        // find all
        List<Vehicle> groupVehicleList = vehicleDAO.getVehiclesInGroupHierarchy(groupID);
        assertEquals("vehicle count for group", Integer.valueOf(VEHICLE_COUNT), new Integer(groupVehicleList.size()));

        for (Vehicle vehicle : vehicleList)
        {
            if (vehicle.getGroupID().equals(groupID))
            {
                boolean found = false;
                for (Vehicle groupVehicle : groupVehicleList)
                {
                    if (vehicle.getVehicleID().equals(groupVehicle.getVehicleID()))
                    {
                        Util.compareObjects(vehicle, groupVehicle, ignoreFields);
                        found = true;
                        break;
                    }
                }
                assertTrue("vehicle " + vehicle.getName(), found);
            }
        }
    }
    
    private void regionVehicles(Integer groupID)
    {
        VehicleHessianDAO vehicleDAO = new VehicleHessianDAO();
        vehicleDAO.setSiloService(siloService);

        // create
        for (int i = 0; i < VEHICLE_COUNT; i++)
        {
            Vehicle vehicle = new Vehicle(0, groupID, 10, VehicleStatus.ACTIVE, "Vehicle " + i, "Make " + i, "Model " + i, 2000 + i, "COLOR " + i, 
                    VehicleType.valueOf(Util.randomInt(0, VehicleType.values().length-1)), "VIN_" + groupID + "_" + i, 1000, "License " + i, 
                    randomState());
            Integer vehicleID = vehicleDAO.create(groupID, vehicle);
            assertNotNull(vehicleID);
            vehicle.setVehicleID(vehicleID);
            vehicleList.add(vehicle);
        }
        
        List<Vehicle> groupVehicleList = vehicleDAO.getVehiclesInGroupHierarchy(groupID);
        
// TODO: asked for deep version of this so that we can get all vehicles down the hierarchy        
        assertEquals("vehicle count for region group", Integer.valueOf(VEHICLE_COUNT), new Integer(groupVehicleList.size()));
        
    }


    private void persons(Integer groupID)
    {
logger.debug("Persons GroupID: " + groupID);                
        PersonHessianDAO personDAO = new PersonHessianDAO();
        personDAO.setSiloService(siloService);
        
        // create
        for (int i = 0; i < PERSON_COUNT; i++)
        {
            Date dob = Util.genDate(1959, 8, 30);
            Person person = new Person(0, groupID, TimeZone.getDefault(), null, address.getAddrID(), "555555555"+i, "555555555"+i, 
                            "email_"+groupID+"_"+i+"@yahoo.com",
                            "emp"+i, null, "title"+i, "dept" + i, "first"+i, "m"+i, "last"+i, "jr", Gender.MALE, 65, 180, dob);
            Integer personID = personDAO.create(groupID, person);
logger.debug("Create Person: " + personID);                
            assertNotNull(personID);
            person.setPersonID(personID);
            personList.add(person);
        }

        
        // update all to female
        for (Person person : personList)
        {
            if (person.getGroupID().equals(groupID))
            {
                person.setGender(Gender.FEMALE);
                person.setMiddle("middle");
logger.debug("Update Person: " + person.getPersonID());                
                Integer changedCount = personDAO.update(person);
                assertEquals("Person update count " + person.getPersonID(), Integer.valueOf(1), changedCount);
            }
        }

        String ignoreFields[] = {"costPerHour"};
        for (Person person : personList)
        {
            if (person.getGroupID().equals(groupID))
            {
logger.debug("Find Person: " + person.getPersonID());                
                Person returnedPerson = personDAO.findByID(person.getPersonID());
                Util.compareObjects(person, returnedPerson, ignoreFields);
            }
        }
        
        
        // find all
        List<Person> groupPersonList = personDAO.getPeopleInGroupHierarchy(groupID);
        assertEquals("people count for group", Integer.valueOf(PERSON_COUNT), new Integer(groupPersonList.size()));

        for (Person person : personList)
        {
            if (person.getGroupID().equals(groupID))
            {
                boolean found = false;
                for (Person groupPerson : groupPersonList)
                {
                    if (groupPerson.getPersonID().equals(person.getPersonID()))
                    {
                        Util.compareObjects(person, groupPerson);
                        found = true;
                        break;
                    }
                }
                assertTrue("Person " + person.getPersonID(), found);
            }
        }
    }



    private void users(Integer groupID)
    {
        UserHessianDAO userDAO = new UserHessianDAO();
        userDAO.setSiloService(siloService);

        PersonHessianDAO personDAO = new PersonHessianDAO();
        personDAO.setSiloService(siloService);
        
        List<Person> groupPersonList = personDAO.getPeopleInGroupHierarchy(groupID);
        assertEquals("people count for group", Integer.valueOf(PERSON_COUNT), new Integer(groupPersonList.size()));
        
        for (Person person : groupPersonList)
        {

            User user = new User(0, person.getPersonID(), randomRole(), UserStatus.ACTIVE, "user_"+person.getPersonID(), PASSWORD);
            // create
            Integer userID = userDAO.create(person.getPersonID(), user);
            assertNotNull("user", userID);
            user.setUserID(userID);

            // update
            user.setRole(randomRole());
            Integer changedCount = userDAO.update(user);
            assertEquals("user update count " + user.getUserID(), Integer.valueOf(1), changedCount);
            
            // find user by ID
            String ignoreFields[] = {"modified", "person"};

            User returnedUser = userDAO.findByID(user.getUserID());
            Util.compareObjects(user, returnedUser, ignoreFields);

            userList.add(user);

        }

        
        // get all for group
        List<User> groupUserList = userDAO.getUsersInGroupHierarchy(groupID);
        assertEquals("number of users in group: " + groupID, groupPersonList.size(), groupUserList.size());
        
        // delete all 
        for (User user : groupUserList)
        {
            Integer changedCount = userDAO.deleteByID(user.getUserID());
            assertEquals("User delete count " + user.getUserID(), Integer.valueOf(1), changedCount);
        }
        
        // user list should be empty
        groupUserList = userDAO.getUsersInGroupHierarchy(groupID);
        assertEquals("number of users in group: " + groupID, 0, groupUserList.size());
        
        // restore all 
        for (User user : userList)
        {
            user.setStatus(UserStatus.ACTIVE);
            Integer changedCount = userDAO.update(user);
            assertEquals("User update count " + user.getUserID(), Integer.valueOf(1), changedCount);
        }
        
        // user  list should be same
        groupUserList = userDAO.getUsersInGroupHierarchy(groupID);
        assertEquals("number of users in group: " + groupID, groupPersonList.size(), groupUserList.size());

    }

    private void drivers(Integer groupID)
    {
        DriverHessianDAO driverDAO = new DriverHessianDAO();
        driverDAO.setSiloService(siloService);
        
        PersonHessianDAO personDAO = new PersonHessianDAO();
        personDAO.setSiloService(siloService);
        
        List<Person> groupPersonList = personDAO.getPeopleInGroupHierarchy(groupID);
        assertEquals("people count for group", Integer.valueOf(PERSON_COUNT), new Integer(groupPersonList.size()));
        
        String ignoreFields[] = {"modified", "person"};
        for (Person person : groupPersonList)
        {
            Date expired = Util.genDate(2010, 9, 30);
        
            Driver driver = new Driver(0, person.getPersonID(), DriverStatus.ACTIVE, 100+person.getPersonID(), "l"+person.getPersonID(), 
                                        randomState(), "ABCD", expired);

            // create
            Integer driverID = driverDAO.create(person.getPersonID(), driver);
            assertNotNull("driver", driverID);
            driver.setDriverID(driverID);
            

            // update
            driver.setRFID(200+person.getPersonID());
            Integer changedCount = driverDAO.update(driver);
            assertEquals("Driver update count " + driver.getDriverID(), Integer.valueOf(1), changedCount);
            
            // find Driver by ID
            Driver returnedDriver = driverDAO.findByID(driver.getDriverID());
            Util.compareObjects(driver, returnedDriver, ignoreFields);

            driverList.add(driver);

        }

        
        // get all for group
        List<Driver> groupDriverList = driverDAO.getAllDrivers(groupID);
        assertEquals("number of drivers in group: " + groupID, groupPersonList.size(), groupDriverList.size());
        
        // delete all 
        for (Driver driver : groupDriverList)
        {
            Integer changedCount = driverDAO.deleteByID(driver.getDriverID());
            assertEquals("User delete count " + driver.getDriverID(), Integer.valueOf(1), changedCount);
        }
        
        // driver list should be empty
        groupDriverList = driverDAO.getAllDrivers(groupID);
        assertEquals("number of drivers in group: " + groupID, 0, groupDriverList.size());
        
        // restore all 
        for (Driver driver : driverList)
        {
            driver.setStatus(DriverStatus.ACTIVE);
            Integer changedCount = driverDAO.update(driver);
            assertEquals("User update count " + driver.getDriverID(), Integer.valueOf(1), changedCount);
        }
        
        // driver  list should be same
        groupDriverList = driverDAO.getAllDrivers(groupID);
        assertEquals("number of drivers in group: " + groupID, groupPersonList.size(), groupDriverList.size());
    }
    private void assignDevicesToVehicles(Integer groupID)
    {
        VehicleHessianDAO vehicleDAO = new VehicleHessianDAO();
        vehicleDAO.setSiloService(siloService);

        List<Vehicle> groupVehicles = vehicleDAO.getVehiclesInGroupHierarchy(groupID);
        assertEquals(Integer.valueOf(VEHICLE_COUNT), Integer.valueOf(groupVehicles.size()));
        
        int deviceIdx = 0;
        for (Vehicle vehicle : groupVehicles)
        {
            Integer deviceID = deviceList.get(deviceIdx++).getDeviceID();
            vehicleDAO.setVehicleDevice(vehicle.getVehicleID(), deviceID);
            
            Vehicle returnedVehicle = vehicleDAO.findByID(vehicle.getVehicleID());
            
            assertEquals("setVehicleDevice failed", deviceID, returnedVehicle.getDeviceID());
            
        }
    }

    private void assignDriversToVehicles(Integer groupID)
    {
        VehicleHessianDAO vehicleDAO = new VehicleHessianDAO();
        vehicleDAO.setSiloService(siloService);

        List<Vehicle> groupVehicles = vehicleDAO.getVehiclesInGroupHierarchy(groupID);
        assertEquals(Integer.valueOf(VEHICLE_COUNT), Integer.valueOf(groupVehicles.size()));
        
        int driverIdx = 0;
        for (Vehicle vehicle : groupVehicles)
        {
            Integer driverID = driverList.get(driverIdx++).getDriverID();
            vehicleDAO.setVehicleDriver(vehicle.getVehicleID(), driverID);
            
            Vehicle returnedVehicle = vehicleDAO.findByID(vehicle.getVehicleID());
            
            assertEquals("setVehicleDriver failed", driverID, returnedVehicle.getDriverID());
            
        }
    }
    
    private State randomState()
    {
        int idx = Util.randomInt(0, States.getStates().size()-1);
        int cnt = 0;
        for (State state : States.getStates().values())
        {
            if (cnt++ == idx)
                return state;
        }
        
        return null;
    }

    private Role randomRole()
    {
        int idx = Util.randomInt(0, Roles.getRoles().size()-1);
        int cnt = 0;
        for (Role role : Roles.getRoles().values())
        {
            if (cnt++ == idx)
                return role;
        }
        
        return null;
    }
}
