package it.com.inthinc.pro.dao.model;

import it.config.ReportTestConst;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.inthinc.pro.dao.hessian.AccountHessianDAO;
import com.inthinc.pro.dao.hessian.GroupHessianDAO;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupType;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.Vehicle;

public class ITDataExt extends BaseITData {
	public List<GroupListData> teamGroupListData;

	
    public void createTestDataExt(SiloService siloService, XMLEncoder xml, Date assignmentDate, int driverVehicleDeviceCount)
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
        createGroupHierarchy(account.getAcctID(), false);
        writeObject(fleetGroup);
        writeObject(districtGroup);
        for (GroupListData team : teamGroupListData)
        	writeObject(team.group);

        // User at fleet level
        System.out.println("Fleet Level");
        fleetUser = createUser(account.getAcctID(), fleetGroup);
        writeObject(fleetUser);

        // User at team level
        System.out.println("Team Level");
        for (GroupListData team : teamGroupListData)
        {
        	team.user = createUser(account.getAcctID(), team.group);
        	writeObject(team.user);
        	
        	team.deviceList = new ArrayList<Device>();
        	team.driverList = new ArrayList<Driver>();
        	team.vehicleList = new ArrayList<Vehicle>();

        	for (int i = 0; i < driverVehicleDeviceCount; i++) {
	        	Device device = createDevice(team.group, assignmentDate);
	            Driver driver = createDriver(team.group, Integer.valueOf(i));
	            Vehicle vehicle = createVehicle(team.group, device.getDeviceID(), driver.getDriverID(), Integer.valueOf(i));
	            writeObject(device);
	            writeObject(driver);
	            writeObject(vehicle);
	            team.deviceList.add(device);
	            team.driverList.add(driver);
	            team.vehicleList.add(vehicle);
        	}
            
        }
        // no Driver device/vehicle
        Group noDriverGroup = teamGroupListData.get(0).group;
        noDriverDevice = createDevice(noDriverGroup, assignmentDate);
        writeObject(noDriverDevice);
        noDriverVehicle = createVehicle(noDriverGroup, noDriverDevice.getDeviceID(), null);
        writeObject(noDriverVehicle);
        
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
        for (int i = GOOD; i <= BAD; i++)
        {
        	GroupListData data = new GroupListData();
        	data.driverType = i;
        	data.group = new Group(0, acctID, TEAM_GROUP_NAME[i], districtGroup.getGroupID(), GroupType.TEAM,  null, TEAM_GROUP_NAME[i], 5, new LatLng(0.0, 0.0));
        	groupID = groupDAO.create(acctID, data.group);
        	data.group.setGroupID(groupID);
        	teamGroupListData.add(data);
        }
        
    }
    

    @Override
    protected List<Integer> anyTeam() {
		List<Integer> groupIDList = new ArrayList<Integer>();
        for (GroupListData groupData : teamGroupListData) {
        	groupIDList.add(groupData.group.getGroupID());
        }
		return groupIDList;
	}


	public boolean parseTestDataExt(InputStream stream, SiloService siloService, int driverVehicleDeviceCount) {
        try {
            XMLDecoder xmlDecoder = new XMLDecoder(new BufferedInputStream(stream));
            account = getNext(xmlDecoder, Account.class);
            getNext(xmlDecoder, Address.class);
            fleetGroup = getNext(xmlDecoder, Group.class);
            districtGroup = getNext(xmlDecoder, Group.class);
            teamGroupListData = new ArrayList<GroupListData>();
            for (int i = GOOD; i <= BAD; i++) {
                Group group = getNext(xmlDecoder, Group.class);
                GroupListData groupData = new GroupListData();
                groupData.group = group;
                groupData.driverType = i;
                teamGroupListData.add(groupData);
            }
            fleetUser = getNext(xmlDecoder, User.class);
            for (int i = GOOD; i <= BAD; i++) {
                GroupListData groupData = teamGroupListData.get(i);
                groupData.user = getNext(xmlDecoder, User.class);
            	groupData.deviceList = new ArrayList<Device>();
            	groupData.driverList = new ArrayList<Driver>();
            	groupData.vehicleList = new ArrayList<Vehicle>();
                for (int cnt = 0; cnt < driverVehicleDeviceCount; cnt++) {
                	groupData.deviceList.add(getNext(xmlDecoder, Device.class));
                	groupData.driverList.add(getNext(xmlDecoder, Driver.class));
                	groupData.vehicleList.add(getNext(xmlDecoder, Vehicle.class));
                }
            }
            noDriverDevice = getNext(xmlDecoder, Device.class);
            noDriverVehicle = getNext(xmlDecoder, Vehicle.class);
        	startDateInSec = getNext(xmlDecoder, Integer.class);
        	Integer todayInSec = DateUtil.getDaysBackDate(DateUtil.getTodaysDate(), 0, ReportTestConst.TIMEZONE_STR);
        
        	totalDays = (todayInSec - startDateInSec) / DateUtil.SECONDS_IN_DAY;
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
        Account existingAccount = accountDAO.findByID(account.getAcctID());
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
