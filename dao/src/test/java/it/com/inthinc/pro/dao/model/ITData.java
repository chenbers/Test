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
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.RedFlagAlert;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.Zone;

public class ITData extends BaseITData{

	public List<GroupData> teamGroupData;
	public RedFlagAlert zoneAlert;
	public List<RedFlagAlert> redFlagAlertList;

    
    public void createTestData(SiloService siloService, XMLEncoder xml, Date assignmentDate, boolean includeUnknown, boolean includeZonesAndAlerts)
    {
        createTestData(siloService, xml, assignmentDate, includeUnknown, includeZonesAndAlerts, false);
    }
    public void createTestData(SiloService siloService, XMLEncoder xml, Date assignmentDate, boolean includeUnknown, boolean includeZonesAndAlerts, boolean includeWSGroup)
    {
    	this.siloService = siloService;
    	this.xml = xml;
    	this.assignmentDate = assignmentDate;
    	
        init();

        // Account
        createAccount(includeWSGroup);
        writeObject(account);

        // Address
        createAddress(account.getAccountID());
        writeObject(address);
        
        // Group Hierarchy
        createGroupHierarchy(account.getAccountID(), includeWSGroup);
        writeObject(fleetGroup);
        writeObject(districtGroup);
        for (GroupData team : teamGroupData)
        	writeObject(team.group);

        // User at fleet level
        System.out.println("Fleet Level");
        fleetUser = createUser(account.getAccountID(), fleetGroup);
        writeObject(fleetUser);

        districtUser = createUser(account.getAccountID(), districtGroup);
        writeObject(districtUser);

        // User at team level
        System.out.println("Team Level");
        for (GroupData team : teamGroupData)
        {
        	team.user = createUser(account.getAccountID(), team.group);
            team.device = createDevice(team.group, assignmentDate);
            team.driver = createDriver(team.group);
//            team.vehicle = createVehicle(team.group, null, team.driver.getDriverID());
            team.vehicle = createVehicle(team.group, team.device.getDeviceID(), team.driver.getDriverID());
        	writeObject(team.user);
            writeObject(team.device);
            writeObject(team.driver);
            writeObject(team.driver.getPerson());
            writeObject(team.vehicle);
            
        }
        
        if (includeWSGroup) {
            
        }
        if (includeUnknown) {
	        // no Driver device/vehicle
	        Group noDriverGroup = teamGroupData.get(0).group;
	        noDriverDevice = createDevice(noDriverGroup, assignmentDate);
	        writeObject(noDriverDevice);
	        noDriverVehicle = createVehicle(noDriverGroup, noDriverDevice.getDeviceID(), null);
	        writeObject(noDriverVehicle);
        }
        
        if (includeZonesAndAlerts) {
        	// zone
        	createZone();
        	
        	// zone alert preferences
        	createZoneAlert();
        	
        	// red flag alert preferences
        	createRedFlagAlerts();
        }
        
    }

    @Override
    protected void createGroupHierarchy(Integer acctID, boolean includeWSGroup)
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
        int endIdx = (includeWSGroup ? WS_GROUP : BAD);
        
        for (int i = GOOD; i <= endIdx; i++)
        {
        	GroupData data = new GroupData();
        	data.driverType = i;
        	data.group = new Group(0, acctID, TEAM_GROUP_NAME[i], districtGroup.getGroupID(), GroupType.TEAM,  null, TEAM_GROUP_NAME[i], 5, new LatLng(0.0, 0.0));
        	groupID = groupDAO.create(acctID, data.group);
        	data.group.setGroupID(groupID);
        	teamGroupData.add(data);
        }
        
    }
    
    @Override
    protected List<Integer> anyTeam() {
		List<Integer> groupIDList = new ArrayList<Integer>();
        for (GroupData groupData : teamGroupData) {
        	groupIDList.add(groupData.group.getGroupID());
        }
		return groupIDList;
	}


	public boolean parseTestData(InputStream stream, SiloService siloService, boolean includeUnknown, boolean includeZonesAndAlerts) {
	    return parseTestData(stream, siloService, includeUnknown, includeZonesAndAlerts, false);
	}
	public boolean parseTestData(InputStream stream, SiloService siloService, boolean includeUnknown, boolean includeZonesAndAlerts, boolean includeWaysmartData) {
        try {
            XMLDecoder xmlDecoder = new XMLDecoder(new BufferedInputStream(stream));
            account = getNext(xmlDecoder, Account.class);
            address = getNext(xmlDecoder, Address.class);
            fleetGroup = getNext(xmlDecoder, Group.class);
            districtGroup = getNext(xmlDecoder, Group.class);
            teamGroupData = new ArrayList<GroupData>();
            int endIdx = (includeWaysmartData) ? WS_GROUP : BAD;
            for (int i = GOOD; i <= endIdx; i++) {
                Group group = getNext(xmlDecoder, Group.class);
                GroupData groupData = new GroupData();
                groupData.group = group;
                groupData.driverType = i;
                teamGroupData.add(groupData);
            }
            fleetUser = getNext(xmlDecoder, User.class);
            districtUser = getNext(xmlDecoder, User.class);
            for (int i = GOOD; i <= endIdx; i++) {
                GroupData groupData = teamGroupData.get(i);
                groupData.user = getNext(xmlDecoder, User.class);
                groupData.device = getNext(xmlDecoder, Device.class);
                groupData.driver = getNext(xmlDecoder, Driver.class);
                groupData.driver.setPerson(getNext(xmlDecoder, Person.class));
                groupData.vehicle = getNext(xmlDecoder, Vehicle.class);
            }
            if (includeUnknown) {
	            noDriverDevice = getNext(xmlDecoder, Device.class);
	            noDriverVehicle = getNext(xmlDecoder, Vehicle.class);
            }
            if (includeZonesAndAlerts) {
            	zone = getNext(xmlDecoder, Zone.class);
            	zoneAlert = getNext(xmlDecoder, RedFlagAlert.class);
            	redFlagAlertList = new ArrayList<RedFlagAlert>();
            	for (int i = 0; i < 9; i++) {
            		redFlagAlertList.add(getNext(xmlDecoder, RedFlagAlert.class));
            	}
            }
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
        Account existingAccount = accountDAO.findByID(account.getAccountID());
        boolean dataExists = (existingAccount != null);
        if (dataExists) {
            GroupHessianDAO groupDAO = new GroupHessianDAO();
            groupDAO.setSiloService(siloService);
            Group existingTeam = groupDAO.findByID(teamGroupData.get(0).group.getGroupID());
            dataExists = (existingTeam != null && existingTeam.getType().equals(GroupType.TEAM));
        }
        if (!dataExists) {
            System.out.println("TEST DATA is missing: regenerate the base test data set");
        }
        return dataExists;
    }


}
