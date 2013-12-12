package it.com.inthinc.pro.dao.model;

import it.com.inthinc.pro.dao.Util;
import it.config.ReportTestConst;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.inthinc.hos.model.RuleSetType;
import com.inthinc.pro.dao.hessian.AccountHessianDAO;
import com.inthinc.pro.dao.hessian.DeviceHessianDAO;
import com.inthinc.pro.dao.hessian.DriverHessianDAO;
import com.inthinc.pro.dao.hessian.GroupHessianDAO;
import com.inthinc.pro.dao.hessian.PersonHessianDAO;
import com.inthinc.pro.dao.hessian.VehicleHessianDAO;
import com.inthinc.pro.dao.hessian.exceptions.DuplicateEmailException;
import com.inthinc.pro.dao.hessian.exceptions.DuplicateEmpIDException;
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
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.VehicleType;
import com.inthinc.pro.model.app.States;
import com.inthinc.pro.model.configurator.ProductType;

public class ITDataOneTeamExt extends BaseITData {
	public List<GroupListData> teamGroupListData;

	
    public void createTestDataExt(SiloService siloService, XMLEncoder xml, Date assignmentDate)
    {
    	this.siloService = siloService;
    	this.xml = xml;
    	this.assignmentDate = assignmentDate;
    	
        init();

        // Account
        createAccount(false);
        writeObject(account);

        // Address
        createAddress(account.getAccountID());
        writeObject(address);
        
        // Group Hierarchy
        createGroupHierarchy(account.getAccountID(), false);
        writeObject(fleetGroup);
        writeObject(districtGroup);
        for (GroupListData team : teamGroupListData)
        	writeObject(team.group);

        // User at fleet level
        System.out.println("Fleet Level");
//        fleetUser = createUser(account.getAccountID(), fleetGroup);
//        writeObject(fleetUser);

        // User at team level
        System.out.println("Team Level");
        for (GroupListData team : teamGroupListData)
        {
//        	team.user = createUser(account.getAccountID(), team.group);
//        	writeObject(team.user);
        	
        	team.deviceList = new ArrayList<Device>();
        	team.driverList = new ArrayList<Driver>();
        	team.vehicleList = new ArrayList<Vehicle>();

        	Device device = createDevice(team.group, assignmentDate, "Waysmart820 2144", "MCMWS8201", "30003123456AAAA", 2, 1375374693);
            Driver driver = createDriver(team.group, "TestDriver", "empid123");
            Vehicle vehicle = createVehicle(team.group, device.getDeviceID(), driver.getDriverID(), "Waysmart820 2144");
            writeObject(device);
            writeObject(vehicle);
            writeObject(driver);
            team.deviceList.add(device);
            team.vehicleList.add(vehicle);
            team.driverList.add(driver);

            device = createDevice(team.group, assignmentDate, "Waysmart820 339", "MCMWS8202", "30003123456BBBB", 2, 1375370000);
            vehicle = createVehicle(team.group, device.getDeviceID(), account.getUnkDriverID(), "Waysmart820 339");
            writeObject(device);
            writeObject(vehicle);
            team.deviceList.add(device);
            team.vehicleList.add(vehicle);
            
            device = createDevice(team.group, assignmentDate, "Waysmart850", "MCMWS850P", "30003123456CCCC", 12, 1375370000);
            vehicle = createVehicle(team.group, device.getDeviceID(), account.getUnkDriverID(), "Waysmart850");
            writeObject(device);
            writeObject(vehicle);
            team.deviceList.add(device);
            team.vehicleList.add(vehicle);

            device = createDevice(team.group, assignmentDate, "Tiwipro", "123456789876543", "123456789876543", 3, 1375370000);
            vehicle = createVehicle(team.group, device.getDeviceID(), account.getUnkDriverID(), "Tiwipro");
            writeObject(device);
            writeObject(vehicle);
            team.deviceList.add(device);
            team.vehicleList.add(vehicle);
        }
    }

    @Override
    protected void createGroupHierarchy(Integer acctID, boolean notUsed)
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
        
        teamGroupListData = new ArrayList<GroupListData>();
        
        // team
    	GroupListData data = new GroupListData();
//    	data.driverType = i;
    	data.group = new Group(0, acctID, "team", districtGroup.getGroupID(), GroupType.TEAM,  null, "team", 5, new LatLng(0.0, 0.0));
    	groupID = groupDAO.create(acctID, data.group);
    	data.group.setGroupID(groupID);
    	teamGroupListData.add(data);
        
    }
    

    @Override
    protected List<Integer> anyTeam() {
		List<Integer> groupIDList = new ArrayList<Integer>();
        for (GroupListData groupData : teamGroupListData) {
        	groupIDList.add(groupData.group.getGroupID());
        }
		return groupIDList;
	}

    protected Device createDevice(Group group, Date assignmentDate, String name, String mcmID, String imei, int productVersion, Integer firmwareVersion)
    {
        Device device = null;
        DeviceHessianDAO deviceDAO = new DeviceHessianDAO();
        deviceDAO.setSiloService(siloService);
        VehicleHessianDAO vehicleDAO = new VehicleHessianDAO();
        vehicleDAO.setSiloService(siloService);
        deviceDAO.setVehicleDAO(vehicleDAO);
        
        Device existingDevice = deviceDAO.findByIMEI(imei);
        if (existingDevice != null)
            deviceDAO.deleteByID(existingDevice.getDeviceID());
        
        try
        {
            device = new Device(0, account.getAccountID(), DeviceStatus.ACTIVE, name, 
                    genNumericID(group.getGroupID(), 15), genNumericID(group.getGroupID(), 19), genNumericID(group.getGroupID(), 10), 
                    genNumericID(group.getGroupID(), 10));
            device.setActivated(assignmentDate);
            device.setEmuMd5("696d6acbc199d607a5704642c67f4d86");
            device.setProductVer(productVersion);
            device.setMcmid(mcmID);
            device.setImei(imei);
            device.setFirmwareVersion(firmwareVersion);
            System.out.println("device imei " + device.getImei() + " activated date: " + assignmentDate);
            Integer deviceID = deviceDAO.create(account.getAccountID(), device);
            device.setDeviceID(deviceID);
            
            return device;
        }
        catch (Exception ex)
        {
            System.out.println("Exception: " + ex);
        }
        return device;
    }

    
    protected Driver createDriver(Group group, String firstName, String empID)
    {
        DriverHessianDAO driverDAO = new DriverHessianDAO();
        driverDAO.setSiloService(siloService);
        
        Person person = createPerson(group.getAccountID(), group.getGroupID(), empID, firstName, "Driver");
        Date expired = Util.genDate(2012, 9, 30);
        
        Driver driver = new Driver(0, person.getPersonID(), Status.ACTIVE, null, null, null, null, "l"+person.getPersonID(), 
                                        States.getStateByAbbrev("UT"), "ABCD", expired, null, RuleSetType.US_OIL, group.getGroupID());

        Integer driverID = driverDAO.create(person.getPersonID(), driver);
        driver.setDriverID(driverID);
        driver.setPerson(person);
        
        System.out.println("driverId: " + driverID);
        return driver;
        
    }
    
    public Person createPerson(Integer acctID, Integer groupID, String empID, String first, String last)
    {
        PersonHessianDAO personDAO = new PersonHessianDAO();
        personDAO.setSiloService(siloService);

        Person person = null;
        // create a person
        try {
            person = new Person(0, acctID, ReportTestConst.timeZone, address.getAddrID(), 
                    first + "email"+groupID+Util.randomInt(1, 99999)+"@example.com", 
                    null, "5555555555", "5555555555", null, null, null, null, null, empID, 
                    null, "title", "dept", first, "m", last, "jr", Gender.MALE, 65, 180, new Date(), Status.ACTIVE, 
                    MeasurementType.ENGLISH, FuelEfficiencyType.MPG_US, Locale.getDefault());
            person.setCrit(1);
            person.setWarn(1);
            person.setInfo(1);
    
            Integer personID = personDAO.create(acctID, person);
            person.setPersonID(personID);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return person;
    }


    protected Vehicle createVehicle(Group group, Integer deviceID, Integer driverID, String name)
    {
        VehicleHessianDAO vehicleDAO = new VehicleHessianDAO();
        vehicleDAO.setSiloService(siloService);

        
        Vehicle vehicle = new Vehicle(0, group.getGroupID(), Status.ACTIVE, name, "Make", "Model", 2000, "Red", 
                    VehicleType.LIGHT, String.valueOf(new Date().getTime()), 1000, "UT " + group.getGroupID(), 
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


	public boolean parseTestDataExt(InputStream stream, SiloService siloService) {
        try {
            XMLDecoder xmlDecoder = new XMLDecoder(new BufferedInputStream(stream));
            account = getNext(xmlDecoder, Account.class);
            getNext(xmlDecoder, Address.class);
            fleetGroup = getNext(xmlDecoder, Group.class);
            districtGroup = getNext(xmlDecoder, Group.class);
            teamGroupListData = new ArrayList<GroupListData>();
            Group group = getNext(xmlDecoder, Group.class);
            GroupListData groupData = new GroupListData();
            groupData.group = group;
//            groupData.driverType = i;
            teamGroupListData.add(groupData);
//            fleetUser = getNext(xmlDecoder, User.class);
            
            //Load team
            groupData = teamGroupListData.get(0);
//            groupData.user = getNext(xmlDecoder, User.class);
        	groupData.deviceList = new ArrayList<Device>();
        	groupData.driverList = new ArrayList<Driver>();
        	groupData.vehicleList = new ArrayList<Vehicle>();
            for (int cnt = 0; cnt < 4; cnt++) {
            	groupData.deviceList.add(getNext(xmlDecoder, Device.class));
            	groupData.vehicleList.add(getNext(xmlDecoder, Vehicle.class));
                if (cnt == 0)
                    groupData.driverList.add(getNext(xmlDecoder, Driver.class));
            }
/*  
            noDriverDevice = getNext(xmlDecoder, Device.class);
            noDriverVehicle = getNext(xmlDecoder, Vehicle.class);
        	startDateInSec = getNext(xmlDecoder, Integer.class);
        	Integer todayInSec = DateUtil.getDaysBackDate(DateUtil.getTodaysDate(), 0, ReportTestConst.TIMEZONE_STR);
        
        	totalDays = (todayInSec - startDateInSec) / DateUtil.SECONDS_IN_DAY;
 */
            xmlDecoder.close();
            return dataExists(siloService);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private boolean dataExists(SiloService siloService) {
        // just spot check that account and team exist (this could be more comprehensive)
        AccountHessianDAO accountDAO = new AccountHessianDAO();
        accountDAO.setSiloService(siloService);
        Account existingAccount = accountDAO.findByID(account.getAccountID());
        boolean dataExists = (existingAccount != null);
        if (dataExists) {
            GroupHessianDAO groupDAO = new GroupHessianDAO();
            groupDAO.setSiloService(siloService);
            Group existingTeam = groupDAO.findByID(teamGroupListData.get(0).group.getGroupID());
            dataExists = (existingTeam != null && existingTeam.getType().equals(GroupType.TEAM));
        }
        if (!dataExists) {
            System.out.println("TEST DATA is missing: regenerate the base test data set");
        }
        return dataExists;
    }
  

}
