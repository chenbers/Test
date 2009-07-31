package it.util;

import static org.junit.Assert.assertNotNull;
import it.com.inthinc.pro.dao.Util;
import it.config.IntegrationConfig;

import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.Date;
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
import com.inthinc.pro.dao.hessian.exceptions.ProxyException;
import com.inthinc.pro.dao.hessian.extension.HessianTCPProxyFactory;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
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
import com.inthinc.pro.model.app.Roles;
import com.inthinc.pro.model.app.States;

// class to generate test data
//  
public class DataGenerator
{
    public static SiloService siloService;
    public static XMLEncoder xml;
    public static Integer TESTING_SILO = 0;
    private static final String PASSWORD="nuN5q/jdjEpJKKA4A6jLTZufWZfIXtxqzjVjifqFjbGg6tfmQFGXbTtcXtEIg4Z7"; // password
    private static final long MS_IN_DAY = 24*60*60*1000;
    
    Account account;
    Address address;
    Group fleetGroup;
    Group districtGroup;
    Group teamGroup;
    User user;
    Device device;
    Vehicle vehicle;
    Driver driver;
    
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
        writeObject(teamGroup);

        // User at fleet level
        System.out.println("Fleet Level");
        createUser(account.getAcctID(), fleetGroup.getGroupID());
        writeObject(user);

        // User at team level
        System.out.println("Team Level");
        createUser(account.getAcctID(), teamGroup.getGroupID());
        writeObject(user);

        createDevice();
        createDriver();
        createVehicle(device.getDeviceID(), driver.getDriverID());
        writeObject(device);
        writeObject(driver);
        writeObject(vehicle);
        
        System.out.println("generate Events for " +device.getImei());            
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
        
    }
    
    private void createDriver()
    {
        DriverHessianDAO driverDAO = new DriverHessianDAO();
        driverDAO.setSiloService(siloService);
        
        Person person = createPerson(teamGroup.getAccountID(), teamGroup.getGroupID(), "Driver", "Last"+teamGroup.getGroupID());
        Date expired = Util.genDate(2010, 9, 30);
        
        driver = new Driver(0, person.getPersonID(), Status.ACTIVE, null, "l"+person.getPersonID(), 
                                        States.getStateByAbbrev("UT"), "ABCD", expired, null, null, teamGroup.getGroupID());

        Integer driverID = driverDAO.create(person.getPersonID(), driver);
        driver.setDriverID(driverID);
        System.out.println("driverId: " + driverID);
        
    }

    private void createVehicle(Integer deviceID, Integer driverID)
    {
        VehicleHessianDAO vehicleDAO = new VehicleHessianDAO();
        vehicleDAO.setSiloService(siloService);

        vehicle = new Vehicle(0, teamGroup.getGroupID(), 10, Status.ACTIVE, "Test Vehicle", "Make", "Model", 2000, "Red", 
                    VehicleType.LIGHT, "VIN_" + teamGroup.getGroupID(), 1000, "UT " + teamGroup.getGroupID(), 
                    States.getStateByAbbrev("UT"));
        Integer vehicleID = vehicleDAO.create(teamGroup.getGroupID(), vehicle);
        vehicle.setVehicleID(vehicleID);
        
        vehicleDAO.setVehicleDevice(vehicleID, deviceID);
        vehicleDAO.setVehicleDriver(vehicleID, driverID);
        
    }

    private void createDevice()
    {
        DeviceHessianDAO deviceDAO = new DeviceHessianDAO();
        deviceDAO.setSiloService(siloService);
        
        device = new Device(0, account.getAcctID(), DeviceStatus.ACTIVE, "Device", genNumericID(account.getAcctID(), 15), genNumericID(account.getAcctID(), 19), genNumericID(account.getAcctID(), 10), "5555551234", "5555559876");
        
        device.setAccel("1100 50 4");
        Integer deviceID = deviceDAO.create(account.getAcctID(), device);
        device.setDeviceID(deviceID);
        
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



    private void createUser(Integer acctID, Integer groupID)
    {
        UserHessianDAO userDAO = new UserHessianDAO();
        userDAO.setSiloService(siloService);

        // create a person
        Person person = createPerson(acctID, groupID, "Person", "Last"+groupID); 


        String username = "user_"+person.getPersonID();
        user = new User(0, person.getPersonID(), Roles.getRoleByName("superUser"), Status.ACTIVE, username, PASSWORD, groupID, Locale.getDefault());
        Integer userID = userDAO.create(person.getPersonID(), user);
        user.setUserID(userID);
        
        System.out.println(groupID + " LOGIN NAME: " + username);
        
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
        
        // team
        teamGroup = new Group(0, acctID, "Team", districtGroup.getGroupID(), GroupType.TEAM,  null, "Team  Group", 5, new LatLng(0.0, 0.0));
        groupID = groupDAO.create(acctID, teamGroup);
        teamGroup.setGroupID(groupID);
        
    }
    
    private Person createPerson(Integer acctID, Integer groupID, String first, String last)
    {
        PersonHessianDAO personDAO = new PersonHessianDAO();
        personDAO.setSiloService(siloService);

        // create a person
        Person person = new Person(0, acctID, TimeZone.getDefault(), 0, address.getAddrID(), first + "email"+groupID+"@email.com", null, "5555555555", "5555555555", null, null, null, null, null, "emp01", 
                null, "title", "dept", first, "m", last, "jr", Gender.MALE, 65, 180, new Date(), Status.ACTIVE, MeasurementType.ENGLISH, FuelEfficiencyType.MPG_US);

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
        
        // create
//        Integer siloID = TESTING_SILO;
//        Integer acctID = accountDAO.create(siloID, account);
        Integer acctID = accountDAO.create(account);
        account.setAcctID(acctID);
        
    }

    private void generateEvents(MCMSimulator mcmSim)
    {
/*        
        DeviceHessianDAO deviceDAO = new DeviceHessianDAO();
        deviceDAO.setSiloService(siloService);
        System.out.print("Wait for device to propagate to central ");
        for (int i = 0; i < 300; i++)
        {
            if (i == 299)
            {
                Device d = deviceDAO.findByKey(device.getImei());
                if (d == null)
                {
                    System.out.println("Event Generation Failed");
                    System.out.println("Device not found in central server even after 5 min.");
                    return;
                }
            }
            try
            {
                
                Thread.sleep(1000l);
                System.out.print(".");
            }
            catch (InterruptedException e)
            {
                System.out.println("Event Generation Failed");
                System.out.println("Wait Thread interupted");
                return;
            }
        }
        System.out.println("");
*/
        
        System.out.println("*** Generate Events for " +device.getImei() + " warning: this can take up to 5 min");            
        boolean errorFound = false;
        int retryCnt = 0;
        while (!errorFound)
        {
            Date startTime = new Date(new Date().getTime()-MS_IN_DAY);
            EventGenerator gen = new EventGenerator();
            try
            {
                gen.generateEvents(device.getImei(), mcmSim, startTime);
                break;
    //System.out.println("*** Generate Events for IMEI_280");            
    //            gen.generateEvents("IMEI_280", mcmSim, startTime);
            }
            catch (ProxyException ex)
            {
                if (ex.getErrorCode() != 414)
                {
                    System.out.print(ex.getErrorCode()+"");
                    errorFound = true;
                }
                else
                {
                    if (retryCnt == 300)
                    {
                        System.out.println("Retries failed after 5 min.");
                        errorFound = true;
                    }
                    else
                    {
                        try
                        {
                            Thread.sleep(1000l);
                            retryCnt++;
                        }
                        catch (InterruptedException e)
                        {
                            errorFound = true;
                            e.printStackTrace();
                        }
                        System.out.print(".");
                        if (retryCnt % 25 == 0)
                            System.out.println();
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
                errorFound = true;
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

        
        IntegrationConfig config = new IntegrationConfig();

        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());
        
        siloService = new SiloServiceCreator(host, port).getService();
        
        
        DataGenerator  testData = new DataGenerator ();
        try
        {
            System.out.println(" -- test data generation start -- ");
            if (xmlPath != null)
            {
                String xmlFileName = xmlPath + "/TestData.xml";
                xml = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(xmlFileName)));
                System.out.println(" saving output to " + xmlFileName);
            }
            testData.createTestData();
            
            HessianTCPProxyFactory factory = new HessianTCPProxyFactory();
            MCMSimulator mcmSim = (MCMSimulator) factory.create(MCMSimulator.class, config.getProperty(IntegrationConfig.MCM_HOST), config.getIntegerProp(IntegrationConfig.MCM_PORT));
            testData.generateEvents(mcmSim);
         
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
