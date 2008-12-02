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
import com.inthinc.pro.dao.hessian.StateHessianDAO;
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
import com.inthinc.pro.model.Gender;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Role;
import com.inthinc.pro.model.State;
import com.inthinc.pro.model.StateObj;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.UserStatus;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleStatus;
import com.inthinc.pro.model.VehicleType;

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
    private User user;
    private Driver driver;
    
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
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }
    
    @Test
    @Ignore
    public void states()
    {
        // TODO:
        // this just tests if our State enum matches the state list from the database
        // this seems like a bad idea we need some sort of mapper
        StateHessianDAO stateDAO = new StateHessianDAO();
        stateDAO.setSiloService(siloService);
        try
        {
        List<StateObj> statesList = stateDAO.getStates();

        for (StateObj stateObj : statesList)
        {
            State state = State.valueOf(stateObj.getStateID());
            
            assertEquals(stateObj.getName(), state.getName());
            assertEquals(stateObj.getAbbrev(), state.getAbbrev());
        }
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }
    
    @Test
    public void siloService()
    {
        // test all create, find, update and any other methods (not delete yet though)
        account();
        Integer acctID = account.getAcctID();
//        Integer acctID = 8;
        groupHierarchy(acctID);
        
        // devices
        devices(acctID);
        
        // vehicles
        vehicles(team1Group.getGroupID());
        vehicles(team2Group.getGroupID());
        regionVehicles(regionGroup.getGroupID());
/*        
        // person
        persons(team1Group.getGroupID());
        persons(team2Group.getGroupID());
        
        // user
        users(acctID);
        
        // driver
        drivers(acctID);
*/        
    }

    private void account()
    {
        AccountHessianDAO accountDAO = new AccountHessianDAO();
        accountDAO.setSiloService(siloService);
        
        account = new Account(null, null, null, AccountStatus.ACCOUNT_ACTIVE);
        
        // create
        Integer siloID = 0;
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
        address = new Address(null, Util.randomInt(100, 999) + " Street", null, "City " + Util.randomInt(10,99), State.valueOf(Util.randomInt(1, State.values().length - 1)), "12345");
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
        
//TODO: UPDATE ALL FIELDS
        
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

    private void vehicles(Integer groupID)
    {
        VehicleHessianDAO vehicleDAO = new VehicleHessianDAO();
        vehicleDAO.setSiloService(siloService);
        
        // create
        for (int i = 0; i < VEHICLE_COUNT; i++)
        {
            Vehicle vehicle = new Vehicle(0, groupID, 10, VehicleStatus.DISABLED, "Vehicle " + i, "Make " + i, "Model " + i, 2000 + i, "COLOR " + i, 
                    VehicleType.valueOf(Util.randomInt(0, VehicleType.values().length-1)), "VIN_" + groupID + "_"+ i, 1000, "License " + i, State.valueOf(Util.randomInt(1, State.values().length - 1)));
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
      //TODO: UPDATE ALL FIELDS
        
        
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
                    VehicleType.valueOf(Util.randomInt(0, VehicleType.values().length-1)), "VIN_" + groupID + "_" + i, 1000, "License " + i, State.valueOf(Util.randomInt(1, State.values().length - 1)));
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
        PersonHessianDAO personDAO = new PersonHessianDAO();
        personDAO.setSiloService(siloService);
        
        // create
        for (int i = 0; i < PERSON_COUNT; i++)
        {
            Person person = new Person(0, groupID, TimeZone.getDefault(), 10, address.getAddrID(), "555555555"+i, "555555555"+i, 
                            "email_"+groupID+"_"+i+"@yahoo.com",
                            "emp"+i, null, "title"+i, "dept" + i, "first"+i, "m"+i, "last"+i, "", Gender.MALE, 65, 180, null);
            Integer personID = personDAO.create(groupID, person);
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
                Integer changedCount = personDAO.update(person);
                assertEquals("Person update count " + person.getPersonID(), Integer.valueOf(1), changedCount);
            }
        }
      //TODO: UPDATE ALL FIELDS
        
        for (Person person : personList)
        {
            if (person.getGroupID().equals(groupID))
            {
                Person returnedPerson = personDAO.findByID(person.getPersonID());
                Util.compareObjects(person, returnedPerson);
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
                    Util.compareObjects(person, groupPerson);
                    found = true;
                    break;
                }
                assertTrue("Person " + person.getPersonID(), found);
            }
        }
    }



    private void users(Integer acctID)
    {
        UserHessianDAO userDAO = new UserHessianDAO();
        userDAO.setSiloService(siloService);

        // make the 1st person in the list a user
        Person person = personList.get(0);
        
        user = new User(0, person.getPersonID(), Role.ROLE_CUSTOM_USER, UserStatus.ACTIVE, "user_"+acctID, PASSWORD);

        // create
        Integer userID = userDAO.create(acctID, user);
        assertNotNull("user", userID);
        user.setUserID(userID);

        Person person2 = personList.get(1);
        User editUser = new User(0, person2.getPersonID(), Role.ROLE_CUSTOM_USER, UserStatus.ACTIVE, "edituser_"+acctID, PASSWORD);
        userID = userDAO.create(acctID, editUser);
        assertNotNull("user", userID);
        editUser.setUserID(userID);

        // update
        editUser.setStatus(UserStatus.DISABLED);
        editUser.setRole(Role.ROLE_READONLY);
        Integer changedCount = userDAO.update(editUser);
        assertEquals("User update count " + editUser.getUserID(), Integer.valueOf(1), changedCount);
        
        // find User by ID
        User returnedUser = userDAO.findByID(editUser.getUserID());
        Util.compareObjects(editUser, returnedUser);
        
        // find User by user name
        returnedUser = userDAO.findByUserName("user_"+acctID);
        Util.compareObjects(editUser, returnedUser);
        
        // get all for group
        List<User> userList = userDAO.getUsersInGroupHierarchy(person.getGroupID());
        assertEquals("number of users in group: " + person.getGroupID(), 2, userList.size());
        
        // delete 
        changedCount = userDAO.deleteByID(editUser.getUserID());
        assertEquals("User delete count " + editUser.getUserID(), Integer.valueOf(1), changedCount);
        
        // user list should have decreased by 1
        userList = userDAO.getUsersInGroupHierarchy(person.getGroupID());
        assertEquals("number of users in group: " + person.getGroupID(), 1, userList.size());
        
        
    }

    private void drivers(Integer acctID)
    {
        DriverHessianDAO driverDAO = new DriverHessianDAO();
        driverDAO.setSiloService(siloService);

        // make the last person in the list a driver
        int idx = personList.size()-1;
        Person person = personList.get(idx);
        
        driver = new Driver();
/*
        // create
        Integer userID = userDAO.create(acctID, user);
        assertNotNull("user", userID);
        user.setUserID(userID);

        Person person2 = personList.get(idx-1);
        User editUser = new User(0, person2.getPersonID(), Role.ROLE_CUSTOM_USER, UserStatus.ACTIVE, "edituser_"+acctID, PASSWORD);
        userID = userDAO.create(acctID, editUser);
        assertNotNull("user", userID);
        editUser.setUserID(userID);

        // update
        editUser.setStatus(UserStatus.DISABLED);
        editUser.setRole(Role.ROLE_READONLY);
        Integer changedCount = userDAO.update(editUser);
        assertEquals("User update count " + editUser.getUserID(), Integer.valueOf(1), changedCount);
        
        // find User by ID
        User returnedUser = userDAO.findByID(editUser.getUserID());
        Util.compareObjects(editUser, returnedUser);
        
        // find User by user name
        returnedUser = userDAO.findByUserName("user_"+acctID);
        Util.compareObjects(editUser, returnedUser);
        
        // get all for group
        List<User> userList = userDAO.getUsersInGroupHierarchy(person.getGroupID());
        assertEquals("number of users in group: " + person.getGroupID(), 2, userList.size());
        
        // delete 
        changedCount = userDAO.deleteByID(editUser.getUserID());
        assertEquals("User delete count " + editUser.getUserID(), Integer.valueOf(1), changedCount);
        
        // user list should have decreased by 1
        userList = userDAO.getUsersInGroupHierarchy(person.getGroupID());
        assertEquals("number of users in group: " + person.getGroupID(), 1, userList.size());
*/       
        
    }

}
