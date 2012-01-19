package com.inthinc.pro.selenium.util;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.device.emulation.interfaces.SiloService;
import com.inthinc.emulation.hessian.AutomationHessianFactory;
import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.utils.StackToString;
import com.inthinc.pro.dao.hessian.ZoneHessianDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.mapper.DeviceMapper;
import com.inthinc.pro.dao.hessian.mapper.EventHessianMapper;
import com.inthinc.pro.dao.hessian.mapper.Mapper;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.Zone;
import com.inthinc.pro.model.event.Event;

public class AutomationSiloService {
	
	private final static Logger logger = Logger
			.getLogger(AutomationSiloService.class);

	private SiloService portalProxy;
	private Mapper mapper = new DeviceMapper();
	private EventHessianMapper noteMapper = new EventHessianMapper();
	private AutomationHessianFactory hessian;

	public AutomationSiloService(Addresses silo) {
		hessian = new AutomationHessianFactory();
		this.portalProxy = hessian.getPortalProxy(silo);
	}

	public Device createDevice(Device device) {
		device = mapper.convertToModelObject(portalProxy.createDevice(
				device.getAccountID(), deviceMap(device)), Device.class);
		return device;
	}

	public Driver createDriver(Driver driver) {
		return mapper.convertToModelObject(
				portalProxy.createDriver(driver.getPersonID(),
						mapper.convertToMap(driver)), Driver.class);
	}

	public Group createGroup(Group topGroup) {
		Map<String, Object> map = mapper.convertToMap(topGroup);
		return mapper.convertToModelObject(
				portalProxy.createGroup(topGroup.getAccountID(), map),
				Group.class);
	}

	public Person createPerson(Person person) {
		return mapper.convertToModelObject(
				portalProxy.createPerson(person.getAcctID(),
						mapper.convertToMap(person)), Person.class);
	}

	public User createUser(User user) {
		return mapper.convertToModelObject(
				portalProxy.createUser(user.getPersonID(),
						mapper.convertToMap(user)), User.class);
	}

	public Vehicle createVehicle(Integer groupID, Vehicle vehicle) {
		return mapper.convertToModelObject(
				portalProxy.createVehicle(groupID,
						mapper.convertToMap(vehicle)), Vehicle.class);
	}

	private Map<String, Object> deviceMap(Device device) {
		Map<String, Object> map = mapper.convertToMap(device);
		map.put("firmVer", device.getFirmwareVersion());
		map.put("witnessVer", device.getWitnessVersion());
		map.put("productVer", device.getProductVersion().getVersion());
		return map;
	}

	public List<Account> getAccounts() {
		return mapper.convertToModelObject(portalProxy.getAllAcctIDs(),
				Account.class);
	}

	public Account getAcct(Integer acctID) {
		Map<String, Object> map = portalProxy.getAcct(acctID);
		return mapper.convertToModelObject(map, Account.class);
	}

	public List<Map<String, Object>> getAdminRole(Integer acctID) {
		return portalProxy.getRolesByAcctID(acctID);
	}

	public Device getDevice(Integer deviceID) {
		return mapper.convertToModelObject(portalProxy.getDevice(deviceID),
				Device.class);
	}

	public Device getDeviceBYImei(String imei) {
		try {
			Integer id = (Integer) portalProxy.getID("imei", imei).get("id");
			return mapper.convertToModelObject(portalProxy.getDevice(id),
					Device.class);
		} catch (EmptyResultSetException error) {
			logger.debug(StackToString.toString(error));
			return null;
		} catch (Exception e) {
			logger.debug(StackToString.toString(e));
			return null;
		}
	}

	public Device getDeviceBYSerial(String serialNum) {
		try {
			Integer id = (Integer) portalProxy.getID("serialNum", serialNum)
					.get("id");
			return mapper.convertToModelObject(portalProxy.getDevice(id),
					Device.class);
		} catch (EmptyResultSetException error) {
			logger.debug(StackToString.toString(error));
			return null;
		} catch (Exception e) {
			logger.debug(StackToString.toString(e));
			return null;
		}
	}

	public List<Event> getDriverNote(Integer driverID, Long startDate,
			Long endDate) {
		Integer[] giveMeThemAll = {};
		List<Map<String, Object>> notes = portalProxy.getDriverNote(driverID,
				startDate, endDate, 1, giveMeThemAll);
		return noteMapper.convertToModelObject(notes, Event.class);
	}

	public Group getGroup(Integer groupID) {
		Map<String, Object> map = portalProxy.getGroup(groupID);
		return mapper.convertToModelObject(map, Group.class);
	}

	public List<Group> getGroups(Integer accountID) {
		return mapper.convertToModelObject(
				portalProxy.getGroupsByAcctID(accountID), Group.class);
	}

	public Event getNote(Long noteID) {
		Map<String, Object> note = portalProxy.getNote(noteID);
		return noteMapper.convertToModelObject(note, Event.class);
	}

	public Person getPerson(Integer personID) {
		return mapper.convertToModelObject(portalProxy.getPerson(personID),
				Person.class);
	}

	public Person getPerson(String priEmail) {
		Integer id = (Integer) portalProxy.getID("priEmail", priEmail)
				.get("id");
		return mapper.convertToModelObject(portalProxy.getPerson(id),
				Person.class);
	}

	public User getUser(Integer userID) {
		return mapper.convertToModelObject(portalProxy.getUser(userID),
				User.class);
	}

	public User getUser(String username) {
		try {
			Integer userID = (Integer) portalProxy.getID("username", username)
					.get("id");
			return mapper.convertToModelObject(portalProxy.getUser(userID),
					User.class);
		} catch (EmptyResultSetException e) {
			logger.debug(StackToString.toString(e));
			return null;
		} catch (Exception e) {
			logger.debug(StackToString.toString(e));
			return null;
		}
	}

	public User getUserByPerson(Integer personID) {
		return mapper.convertToModelObject(
				portalProxy.getUserByPersonID(personID), User.class);
	}

	public List<User> getUsersByGroupID(Integer groupID) {
		return mapper.convertToModelObject(
				portalProxy.getUsersByGroupID(groupID), User.class);
	}

	public Vehicle getVehicle(Integer vehicleID) {
		return mapper.convertToModelObject(portalProxy.getVehicle(vehicleID),
				Vehicle.class);
	}

	public Vehicle getVehicle(String vin) {
		Integer id = (Integer) portalProxy.getID("vin", vin).get("id");
		return mapper.convertToModelObject(portalProxy.getVehicle(id),
				Vehicle.class);
	}

	public Vehicle getVehicleByDriverID(Integer ID) {
		return mapper.convertToModelObject(
				portalProxy.getVehicleByDriverID(ID), Vehicle.class);
	}

	public List<Event> getVehicleNote(Integer vehicleID, Long startDate,
			Long endDate) {
		Integer[] giveMeThemAll = {};
		List<Map<String, Object>> notes = portalProxy.getVehicleNote(vehicleID,
				startDate, endDate, 1, giveMeThemAll);
//		for (Map<String, Object> note : notes){
//			MasterTest.print(note);
//		}
		return noteMapper.convertToModelObject(notes, Event.class);
	}

	public Device updateDevice(Device device) {
		logger.debug("Updated device: "
				+ portalProxy.updateDevice(device.getDeviceID(),
						deviceMap(device)));
		return mapper.convertToModelObject(
				portalProxy.getDevice(device.getDeviceID()), Device.class);
	}

	public Device updateDevice(Integer deviceID, Map<String, Object> updates) {
		portalProxy.updateDevice(deviceID, updates);
		return mapper.convertToModelObject(portalProxy.getDevice(deviceID),
				Device.class);
	}

	public Group updateGroup(Integer groupID, Map<String, Object> update) {
		portalProxy.updateGroup(groupID, update);
		return mapper.convertToModelObject(portalProxy.getGroup(groupID),
				Group.class);

	}

	public Person updatePerson(Person person) {
		portalProxy.updatePerson(person.getPersonID(),
				mapper.convertToMap(person));
		return mapper.convertToModelObject(
				portalProxy.getPerson(person.getPersonID()), Person.class);
	}

	public User updateUser(Integer userID, Map<String, Object> updateU) {
		portalProxy.updateUser(userID, updateU);
		return mapper.convertToModelObject(portalProxy.getUser(userID),
				User.class);
	}

	public User updateUser(User user) {
		portalProxy.updateUser(user.getUserID(), mapper.convertToMap(user));
		return mapper.convertToModelObject(
				portalProxy.getUser(user.getUserID()), User.class);
	}
	
	public Account getQAAccount(){
	    List<Account> accts = getAccounts();
	    for (Account account : accts){
	        if (account.getAcctName().equalsIgnoreCase("QA")){
	            return account;
	        }
	    }
	    return null;
	}
	
	public Group getGroupByName(String name, int acctID){
	    List<Group> groups = getGroups(acctID);
	    for (Group group : groups){
	        if (group.getName().equals(name)){
	            return group;
	        }
	    }
	    return null;
	}

    public Driver getDriverByPersonID(int personID) {
        return mapper.convertToModelObject(portalProxy.getDriverByPersonID(personID),
                Driver.class);
    }

    public Zone getZone(Integer zoneID) {
        return mapper.convertToModelObject(portalProxy.getZone(zoneID), Zone.class);
    }
    
    public List<Zone> getZonesByAccountID(Integer acctID){
        return new ZoneHessianDAO().getMapper().convertToModelObject(portalProxy.getZonesByAcctID(acctID), Zone.class);
    }

}
