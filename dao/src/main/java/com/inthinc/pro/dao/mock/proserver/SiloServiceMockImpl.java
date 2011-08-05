package com.inthinc.pro.dao.mock.proserver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.ProDAOException;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.mapper.AbstractMapper;
import com.inthinc.pro.dao.hessian.mapper.SimpleMapper;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.mock.data.MockData;
import com.inthinc.pro.dao.mock.data.MockRoles;
import com.inthinc.pro.dao.mock.data.MockStates;
import com.inthinc.pro.dao.mock.data.MockTimeZones;
import com.inthinc.pro.dao.mock.data.SearchCriteria;
import com.inthinc.pro.dao.mock.data.TempConversionUtil;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.AlertMessage;
import com.inthinc.pro.model.AlertMessageDeliveryType;
import com.inthinc.pro.model.Cellblock;
import com.inthinc.pro.model.CrashReport;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.DriverLocation;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.LastLocation;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.RedFlagAlert;
import com.inthinc.pro.model.ReportSchedule;
import com.inthinc.pro.model.TablePreference;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.Zone;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.model.phone.CellProviderType;
import com.inthinc.pro.model.phone.CellStatusType;

public class SiloServiceMockImpl extends AbstractServiceMockImpl implements SiloService {


	private static final long serialVersionUID = 2995830460382195043L;
    private static final Logger logger = Logger.getLogger(SiloServiceMockImpl.class);

    // helper method
    private Map<String, Object> doMockLookup(Class<?> clazz, String key, Object searchValue, String emptyResultSetMsg, String methodName) {
        Map<String, Object> returnMap = MockData.getInstance().lookup(clazz, key, searchValue);

        if (returnMap == null) {
            throw new EmptyResultSetException(emptyResultSetMsg, methodName, 0);
        }
        return returnMap;
    }

    private Map<String, Object> createReturnValue(String key, Integer value) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put(key, value);
        return returnMap;
    }

    @Override
    public Map<String, Object> deleteUser(Integer userID) throws ProDAOException {
        return createReturnValue("count", 0);
    }

    @Override
    public Map<String, Object> createUser(Integer acctID, Map<String, Object> userMap) throws ProDAOException {
        // TODO: actually store the object to the mock data
        return createReturnValue("userID", (int) (Math.random() * Integer.MAX_VALUE));
    }

    @Override
    public Map<String, Object> getUser(Integer userID) throws ProDAOException {
        return doMockLookup(User.class, "userID", userID, "No user for ID: " + userID, "getUser");
    }

    @Override
    public Map<String, Object> updateUser(Integer userID, Map<String, Object> userMap) throws ProDAOException {
        return createReturnValue("count", 1);
    }

    @Override
    public Map<String, Object> getID(String name, String value) throws ProDAOException {
        Map<String, Object> returnMap = null;
        if (name.equals("priEmail")) {
            Person person = MockData.getInstance().lookupObject(Person.class, name, value);
            if (person != null) {
                returnMap = new HashMap<String, Object>();
                returnMap.put("id", person.getPersonID());
            }
        } else if (name.equals("username")) {
            User user = MockData.getInstance().lookupObject(User.class, name, value);
            if (user != null) {
                returnMap = new HashMap<String, Object>();
                returnMap.put("id", user.getUserID());
            }
        } else if (name.equals("vin")) {
            Vehicle vehicle = MockData.getInstance().lookupObject(Vehicle.class, "VIN", value);
            if (vehicle != null) {
                returnMap = new HashMap<String, Object>();
                returnMap.put("id", vehicle.getVehicleID());
            }
        } else if (name.equals("imei")) {
            Device device = MockData.getInstance().lookupObject(Device.class, name, value);
            if (device != null) {
                returnMap = new HashMap<String, Object>();
                returnMap.put("id", device.getDeviceID());
            }
        }

        if (returnMap == null) {
            throw new EmptyResultSetException("getID() returned no value for name=" + name + " value=" + value, "getID", 0);
        }

        return returnMap;
    }

    @Override
    public Map<String, Object> deletePerson(Integer personID) throws ProDAOException {
        MockData.getInstance().deleteObject(Person.class, "personID", personID);
        return createReturnValue("count", 0);
    }

    @Override
    public Map<String, Object> createPerson(Integer acctID, Map<String, Object> personMap) throws ProDAOException {
        AbstractMapper mapper = new AbstractMapper() {};
        Person person = mapper.convertToModelObject(personMap, Person.class);
        person.setPersonID((int) (Math.random() * Integer.MAX_VALUE));
        MockData.getInstance().storeObject(person);
        return createReturnValue("personID", person.getPersonID());
    }

    @Override
    public Map<String, Object> getPerson(Integer personID) throws ProDAOException {
        return doMockLookup(Person.class, "personID", personID, "No person for ID: " + personID, "getPerson");

    }

    @Override
    public Map<String, Object> updatePerson(Integer personID, Map<String, Object> personMap) throws ProDAOException {
        return createReturnValue("count", 1);
    }

    @Override
    public List<Map<String, Object>> getVehiclesByGroupID(Integer groupID) {
        final List<Map<String, Object>> vehicles = new LinkedList<Map<String, Object>>();
        Group topGroup = MockData.getInstance().lookupObject(Group.class, "groupID", groupID);

        List<Group> groupHierarchy = getGroupHierarchy(topGroup);

        for (Group group : groupHierarchy) {
            final Integer id = group.getGroupID();
            if (id != null) {
                final SearchCriteria criteria = new SearchCriteria();
                criteria.addKeyValue("groupID", id);
                final List<Map<String, Object>> matches = MockData.getInstance().lookupList(Vehicle.class, criteria);
                if (matches != null)
                    vehicles.addAll(matches);
            }
        }

        return vehicles;
    }

    @Override
    public Map<String, Object> getVehicleByDriverID(Integer driverID) throws ProDAOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> deleteVehicle(Integer vehicleID) throws ProDAOException {
        return createReturnValue("count", 0);
    }

    @Override
    public Map<String, Object> createVehicle(Integer acctID, Map<String, Object> vehicleMap) throws ProDAOException {
        // TODO: actually store the object to the mock data
        return createReturnValue("vehicleID", (int) (Math.random() * Integer.MAX_VALUE));
    }

    @Override
    public Map<String, Object> getVehicle(Integer vehicleID) throws ProDAOException {
        return doMockLookup(Vehicle.class, "vehicleID", vehicleID, "No vehicle for ID: " + vehicleID, "getVehicle");

    }

    @Override
    public Map<String, Object> updateVehicle(Integer vehicleID, Map<String, Object> vehicleMap) throws ProDAOException {
        return createReturnValue("count", 1);
    }

    @Override
    public Map<String, Object> createGroup(Integer acctID, Map<String, Object> groupMap) throws ProDAOException {
        AbstractMapper mapper = new AbstractMapper() {};
        Group updatedGroup = mapper.convertToModelObject(groupMap, Group.class);
        updatedGroup.setGroupID((int) (Math.random() * Integer.MAX_VALUE));
        MockData.getInstance().storeObject(updatedGroup);
        logger.debug("Group Added: " + updatedGroup.getName());
        return createReturnValue("groupID", updatedGroup.getGroupID());
    }

    @Override
    public Map<String, Object> deleteGroup(Integer groupID) throws ProDAOException {
        int newSize = MockData.getInstance().deleteObject(Group.class, "groupID", groupID);
        return createReturnValue("count", newSize);
    }

    @Override
    public Map<String, Object> getGroup(Integer groupID) throws ProDAOException {

        return doMockLookup(Group.class, "groupID", groupID, "No group for ID: " + groupID, "getGroup");
    }

    @Override
    public Map<String, Object> updateGroup(Integer groupID, Map<String, Object> groupMap) throws ProDAOException {
        Group group = (Group) MockData.getInstance().lookupObject(Group.class, "groupID", groupID);

        AbstractMapper mapper = new AbstractMapper() {};
        Group updatedGroup = mapper.convertToModelObject(groupMap, Group.class);

        group.setDescription(updatedGroup.getDescription());
        group.setName(updatedGroup.getName());
        group.setMapCenter(updatedGroup.getMapCenter());
        group.setMapZoom(updatedGroup.getMapZoom());
        group.setParentID(updatedGroup.getParentID());
        group.setType(updatedGroup.getType());
        group.setManagerID(updatedGroup.getManagerID());

        logger.debug("Group Updated: " + group.getName());
        return createReturnValue("count", 1);
    }

    @Override
    public List<Map<String, Object>> getTrips(Integer id, Integer reqType, Long startDate, Long endDate) throws ProDAOException {
        SearchCriteria criteria = new SearchCriteria();
        if (reqType.intValue() == 1)
            criteria.addKeyValue("driverID", id);
        else
            criteria.addKeyValue("vehicleID", id);

        criteria.addKeyValueRange("startTime", DateUtil.convertTimeInSecondsToDate(startDate), DateUtil.convertTimeInSecondsToDate(endDate));

        List<Map<String, Object>> matches = MockData.getInstance().lookupList(Trip.class, criteria);
        return matches;

    }

    @Override
    public Map<String, Object> getLastTrip(Integer id, Integer reqType) {
        SearchCriteria criteria = new SearchCriteria();
        if (reqType.intValue() == 1)
            criteria.addKeyValue("driverID", id);
        else
            criteria.addKeyValue("vehicleID", id);
        Map<String, Object> matches = MockData.getInstance().lookup(Trip.class, criteria);
        return matches;

    }

    @Override
    public List<Map<String, Object>> getStops(Integer driverID, Long startDate, Long endDate) {
        List<Map<String, Object>> dumdList = new ArrayList<Map<String, Object>>();
        return dumdList;
    }

    @Override
    public List<Map<String, Object>> getRecentNotes(Integer groupID, Integer eventCnt, Integer[] types) throws ProDAOException {
        Group group = MockData.getInstance().lookupObject(Group.class, "groupID", groupID);

        List<Driver> drivers = getAllDriversInGroup(group);

        List<Event> allEventsForGroup = new ArrayList<Event>();

        List<Object> typeList = new ArrayList<Object>();
        for (Integer type : types)
            typeList.add(NoteType.valueOf(type));
        Collections.addAll(typeList, types);
        for (Driver driver : drivers) {
            SearchCriteria searchCriteria = new SearchCriteria();
            searchCriteria.addKeyValue("driverID", driver.getDriverID());
            searchCriteria.addKeyValueInList("type", typeList);
            List<Event> eventList = MockData.getInstance().retrieveObjectList(Event.class, searchCriteria);
            allEventsForGroup.addAll(eventList);
        }

        if (allEventsForGroup.size() == 0) {
            throw new EmptyResultSetException("no events for group", "getMostRecentEvents", 0);
        }

        Collections.sort(allEventsForGroup); // Make sure events are in ascending order
        Collections.reverse(allEventsForGroup); // descending order (i.e. most recent first)

        // to do: first sort my date
        int cnt = 0;
        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        for (Event event : allEventsForGroup) {
            returnList.add(TempConversionUtil.createMapFromObject(event, true));
            cnt++;
            if (cnt == eventCnt.intValue())
                break;
        }

        return returnList;
    }

    // ----------- HELPER METHODS ---------------------

    // private Map<String, Object> getAverageScore(Integer startDate, List<ScoreableEntity> allScores)
    // {
    // int total = 0;
    // ScoreableEntity firstEntity = allScores.get(0);
    // ScoreableEntity returnEntity = new ScoreableEntity(firstEntity.getEntityID(), EntityType.ENTITY_GROUP, firstEntity.getIdentifier(), 0, startDate, firstEntity
    // .getScoreType());
    // for (ScoreableEntity entity : allScores)
    // {
    // total += entity.getScore();
    // }
    // returnEntity.setScore(total / allScores.size());
    //
    // return TempConversionUtil.createMapFromObject(returnEntity);
    // }

    @Override
    public List<Map<String, Object>> getDevicesByAcctID(Integer acctID) throws ProDAOException {
        final SearchCriteria criteria = new SearchCriteria();
        criteria.addKeyValue("accountID", acctID);
        return MockData.getInstance().lookupList(Device.class, criteria);
    }

    @Override
    public Map<String, Object> deleteDevice(Integer deviceID) throws ProDAOException {
        return createReturnValue("count", 0);
    }

    @Override
    public Map<String, Object> createDevice(Integer acctID, Map<String, Object> deviceMap) throws ProDAOException {
        // TODO: actually store the object to the mock data
        return createReturnValue("deviceID", (int) (Math.random() * Integer.MAX_VALUE));
    }

    @Override
    public Map<String, Object> getDevice(Integer deviceID) throws ProDAOException {
        return doMockLookup(Device.class, "deviceID", deviceID, "No device for ID: " + deviceID, "getDevice");

    }

    @Override
    public Map<String, Object> updateDevice(Integer deviceID, Map<String, Object> deviceMap) throws ProDAOException {
        return createReturnValue("count", 1);
    }

    @Override
    public List<Map<String, Object>> getDriversByGroupID(Integer groupID) {
        logger.debug("mock IMPL getAllDrivers groupID = " + groupID);
        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        Group group = MockData.getInstance().lookupObject(Group.class, "groupID", groupID);
        if (group == null) {
            return returnList;
        }

        List<Driver> drivers = getAllDriversInGroup(group);

        for (Driver driver : drivers) {
            returnList.add(TempConversionUtil.createMapFromObject(driver, true));
        }

        return returnList;
    }

    @Override
    public List<Map<String, Object>> getVehiclesNearLoc(Integer groupID, Integer numof, Double lat, Double lng) {
        Group group = MockData.getInstance().lookupObject(Group.class, "groupID", groupID);
        List<Driver> drivers = getAllDriversInGroup(group);

        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();

        int count = 0;
        for (Driver driver : drivers) {
            if (count == numof)
                break;

            DriverLocation dl = new DriverLocation();

            dl.setDriver(driver);

            dl.setTime(new Date());
            // dl.setDriverID(driver.getDriverID());
            // dl.setGroupID(driver.getGroupID());
            // dl.setPriPhone(driver.getPerson().getPriPhone());
            // dl.setSecPhone(driver.getPerson().getSecPhone());
            // dl.setName(driver.getPerson().getFirst() + " " + driver.getPerson().getLast());
            // dl.setVehicleType(VehicleType.MEDIUM);

            SearchCriteria searchCriteria = new SearchCriteria();
            searchCriteria.addKeyValue("driverID", driver.getDriverID());

            LastLocation l = MockData.getInstance().retrieveObject(LastLocation.class, searchCriteria);
            dl.setLoc(l.getLoc());

            returnList.add(TempConversionUtil.createMapFromObject(dl, true));
            count++;
        }

        return returnList;
    }

    @Override
    public Map<String, Object> deleteDriver(Integer driverID) throws ProDAOException {
        return createReturnValue("count", 0);
    }

    @Override
    public Map<String, Object> createDriver(Integer acctID, Map<String, Object> driverMap) throws ProDAOException {
        // TODO: actually store the object to the mock data
        return createReturnValue("driverID", (int) (Math.random() * Integer.MAX_VALUE));
    }

    @Override
    public Map<String, Object> getDriver(Integer driverID) throws ProDAOException {
        return doMockLookup(Driver.class, "driverID", driverID, "No driver for ID: " + driverID, "getDriver");
    }

    @Override
    public Map<String, Object> updateDriver(Integer driverID, Map<String, Object> driverMap) throws ProDAOException {
        return createReturnValue("count", 1);
    }

    @Override
    public Map<String, Object> setVehicleDriver(Integer vehicleID, Integer driverID) throws ProDAOException {
        final Vehicle vehicle = MockData.getInstance().lookupObject(Vehicle.class, "vehicleID", vehicleID);
        if (vehicle != null)
            vehicle.setDriverID(driverID);

        return createReturnValue("count", 1);
    }

    @Override
    public Map<String, Object> setVehicleDevice(Integer vehicleID, Integer deviceID) throws ProDAOException {
        final Vehicle vehicle = MockData.getInstance().lookupObject(Vehicle.class, "vehicleID", vehicleID);
        if (vehicle != null)
            vehicle.setDeviceID(deviceID);

        if (deviceID != null) {
            final Device device = MockData.getInstance().lookupObject(Device.class, "deviceID", deviceID);
            if (device != null)
                device.setVehicleID(vehicleID);
        }

        return createReturnValue("count", 1);
    }

    @Override
    public Map<String, Object> clrVehicleDevice(Integer vehicleID, Integer deviceID) throws ProDAOException {
        return createReturnValue("count", 0);
    }

    /*
     * @Override public List<Map<String, Object>> getRedFlags(Integer groupID, Long startDate, Long endDate, Integer includeForgiven) throws ProDAOException { Group group =
     * MockData.getInstance().lookupObject(Group.class, "groupID", groupID);
     * 
     * List<Driver> drivers = getAllDriversInGroup(group); List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
     * 
     * for (Driver driver : drivers) { SearchCriteria searchCriteria = new SearchCriteria(); searchCriteria.addKeyValue("event:driverID", driver.getDriverID());
     * returnList.addAll(MockData.getInstance().lookupList(RedFlag.class, searchCriteria)); }
     * 
     * return returnList; }
     */
    @Override
    public List<Map<String, Object>> getTablePrefsByUserID(Integer userID) throws ProDAOException {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.addKeyValue("userID", userID);
        List<Map<String, Object>> returnList = MockData.getInstance().lookupList(TablePreference.class, searchCriteria);

        if (returnList == null) {
            throw new EmptyResultSetException("No table preferences for userID: " + userID, "getTablePreferencesByUserID", 0);
        }

        return returnList;
    }

    @Override
    public Map<String, Object> deleteTablePref(Integer tablePrefID) throws ProDAOException {
        return createReturnValue("count", 0);
    }

    @Override
    public Map<String, Object> createTablePref(Integer userID, Map<String, Object> tablePreferenceMap) throws ProDAOException {
        // TODO: actually store the object to the mock data
        return createReturnValue("tablePrefID", (int) (Math.random() * Integer.MAX_VALUE));
    }

    @Override
    public Map<String, Object> getTablePref(Integer tablePrefID) throws ProDAOException {
        return doMockLookup(TablePreference.class, "tablePrefID", tablePrefID, "No tablePreference for ID: " + tablePrefID, "getTablePreference");
    }

    @Override
    public Map<String, Object> updateTablePref(Integer tablePrefID, Map<String, Object> tablePreferenceMap) throws ProDAOException {
        return createReturnValue("count", 1);
    }

    @Override
    public List<Map<String, Object>> getZonesByAcctID(Integer accountID) {
        final SearchCriteria criteria = new SearchCriteria();
        criteria.addKeyValue("accountID", accountID);
        return MockData.getInstance().lookupList(Zone.class, criteria);
    }

    @Override
    public Map<String, Object> deleteZone(Integer zoneID) throws ProDAOException {
        return createReturnValue("count", 0);
    }

    @Override
    public Map<String, Object> createZone(Integer acctID, Map<String, Object> zoneMap) throws ProDAOException {
        // TODO: actually store the object to the mock data
        return createReturnValue("zoneID", (int) (Math.random() * Integer.MAX_VALUE));
    }

    @Override
    public Map<String, Object> getZone(Integer zoneID) throws ProDAOException {
        return doMockLookup(Zone.class, "zoneID", zoneID, "No zone for ID: " + zoneID, "getZone");

    }

    @Override
    public Map<String, Object> updateZone(Integer zoneID, Map<String, Object> zoneMap) throws ProDAOException {
        return createReturnValue("count", 1);
    }

//    @Override
//    public List<Map<String, Object>> getZoneAlertsByAcctID(Integer accountID) {
//        final SearchCriteria criteria = new SearchCriteria();
//        criteria.addKeyValue("accountID", accountID);
//        return MockData.getInstance().lookupList(ZoneAlert.class, criteria);
//    }
//
//    @Override
//    public Map<String, Object> deleteZoneAlert(Integer zoneAlertID) throws ProDAOException {
//        return createReturnValue("count", 0);
//    }
//
//    @Override
//    public Map<String, Object> deleteZoneAlertsByZoneID(Integer zoneID) {
//        return createReturnValue("count", 0);
//    }
//
//    @Override
//    public Map<String, Object> createZoneAlert(Integer acctID, Map<String, Object> zoneAlertMap) throws ProDAOException {
//        // TODO: actually store the object to the mock data
//        return createReturnValue("zoneAlertID", (int) (Math.random() * Integer.MAX_VALUE));
//    }

//    @Override
//    public Map<String, Object> getZoneAlert(Integer zoneAlertID) throws ProDAOException {
//        return doMockLookup(ZoneAlert.class, "zoneAlertID", zoneAlertID, "No zoneAlert for ID: " + zoneAlertID, "getZoneAlert");
//
//    }
//
//    @Override
//    public Map<String, Object> updateZoneAlert(Integer zoneAlertID, Map<String, Object> zoneAlertMap) throws ProDAOException {
//        return createReturnValue("count", 1);
//    }

    @Override
    public List<Map<String, Object>> getAlertsByAcctID(Integer accountID) {
        final SearchCriteria criteria = new SearchCriteria();
        criteria.addKeyValue("accountID", accountID);
        return MockData.getInstance().lookupList(RedFlagAlert.class, criteria);
    }

    @Override
    public Map<String, Object> deleteAlert(Integer redFlagAlertID) throws ProDAOException {
        return createReturnValue("count", 0);
    }

    @Override
    public Map<String, Object> createAlert(Integer acctID, Map<String, Object> redFlagAlertMap) throws ProDAOException {
        // TODO: actually store the object to the mock data
        return createReturnValue("redFlagAlertID", (int) (Math.random() * Integer.MAX_VALUE));
    }

    @Override
    public Map<String, Object> getAlert(Integer redFlagAlertID) throws ProDAOException {
        return doMockLookup(RedFlagAlert.class, "redFlagAlertID", redFlagAlertID, "No redFlagAlert for ID: " + redFlagAlertID, "getRedFlagAlert");

    }

    @Override
    public Map<String, Object> updateAlert(Integer redFlagAlertID, Map<String, Object> redFlagAlertMap) throws ProDAOException {
        return createReturnValue("count", 1);
    }
    
//    @Override
//    public List<Map<String, Object>> getTextMsgAlertsByAcctID(Integer accountID) {
//        return null;
//    }    

    @Override
    public Map<String, Object> createAcct(Integer siloID, Map<String, Object> acctMap) throws ProDAOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> createAddr(Integer acctID, Map<String, Object> addrMap) throws ProDAOException {
        // TODO Add the value
        return createReturnValue("addrID", (int) (Math.random() * Integer.MAX_VALUE));
    }

    @Override
    public Map<String, Object> getAcct(Integer acctID) throws ProDAOException {
        return doMockLookup(Account.class, "accountID", acctID, "No Account for ID: " + acctID, "getAccount");
    }

    @Override
    public List<Map<String, Object>> getAccts(Integer siloID) throws ProDAOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> getAddr(Integer addrID) throws ProDAOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getStates() {
        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        for (Object obj : MockStates.getAll()) {
            returnList.add(TempConversionUtil.createMapFromObject(obj, true));
        }

        return returnList;
    }

    @Override
    public Map<String, Object> updateAcct(Integer acctID, Map<String, Object> acctMap) throws ProDAOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> updateAddr(Integer addrID, Map<String, Object> addrMap) throws ProDAOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getGroupsByAcctID(Integer acctID) throws ProDAOException {
        return MockData.getInstance().lookupList(Group.class);
    }

    @Override
    public Map<String, Object> deleteAcct(Integer acctID) throws ProDAOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getUsersByGroupID(Integer groupID) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getUsersByGroupIDDeep(Integer groupID) {
        final List<Map<String, Object>> userIDs = new LinkedList<Map<String, Object>>();

        Group topGroup = MockData.getInstance().lookupObject(Group.class, "groupID", groupID);
        if (topGroup == null)
            return userIDs;

        List<Group> groupHierarchy = getGroupHierarchy(topGroup);

        for (Group group : groupHierarchy) {
            final Integer id = group.getGroupID();
            if (id != null) {
                final SearchCriteria criteria = new SearchCriteria();
                criteria.addKeyValue("groupID", id);
                final List<Map<String, Object>> matches = MockData.getInstance().lookupList(User.class, criteria);
                if (matches != null)
                    userIDs.addAll(matches);
            }
        }

        return userIDs;
    }

    @Override
    public List<Map<String, Object>> getFwdCmds(Integer deviceID, Integer status) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> queueFwdCmd(Integer deviceID, Map<String, Object> fwdMap) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> updateFwdCmd(Integer fwdID, Integer status) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getTimezones() {
        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        for (Object obj : MockTimeZones.getAll()) {
            returnList.add(TempConversionUtil.createMapFromObject(obj, true));
        }

        return returnList;
    }

    @Override
    public List<Map<String, Object>> getAllAcctIDs() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> getNextSilo() {
        // TODO Auto-generated method stub
        return null;
    }

    // @Override
    // public List<Map<String, Object>> getRoles()
    // {
    // List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
    // for (Object obj : MockRoles.getAll())
    // {
    // returnList.add(TempConversionUtil.createMapFromObject(obj, true));
    // }
    //        
    // return returnList;
    // }

    @Override
    public List<Map<String, Object>> getSensitivityMaps() {
        Map<String, Object> sensitivityMapping1 = new HashMap<String, Object>();
        sensitivityMapping1.put("setting", 1);
        sensitivityMapping1.put("fwdCmd", 101);

        Map<String, Object> sensitivityMapping2 = new HashMap<String, Object>();
        sensitivityMapping2.put("setting", 2);
        sensitivityMapping2.put("fwdCmd", 101);

        Map<String, Object> sensitivityMapping3 = new HashMap<String, Object>();
        sensitivityMapping3.put("setting", 3);
        sensitivityMapping3.put("fwdCmd", 101);

        Map<String, Object> sensitivityMapping4 = new HashMap<String, Object>();
        sensitivityMapping4.put("setting", 4);
        sensitivityMapping4.put("fwdCmd", 101);

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        list.add(sensitivityMapping1);
        list.add(sensitivityMapping2);
        list.add(sensitivityMapping3);
        list.add(sensitivityMapping4);

        return list;
    }

    @Override
    public Map<String, Object> getLastLoc(Integer id, Integer reqType) {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.addKeyValue("driverID", id);

        return MockData.getInstance().lookup(LastLocation.class, searchCriteria);
    }

    @Override
    public List<Map<String, Object>> getDriverNote(Integer driverID, Long startDate, Long endDate, Integer includeForgiven, Integer[] types) {
        // List<Event> driverEvents = new ArrayList<Event>();

        List<Object> typeList = new ArrayList<Object>();
        Collections.addAll(typeList, types);
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.addKeyValue("driverID", driverID);
        searchCriteria.addKeyValueInList("type", typeList);
        searchCriteria.addKeyValueRange("time", DateUtil.convertTimeInSecondsToDate(startDate), DateUtil.convertTimeInSecondsToDate(endDate));

        List<Event> eventList = MockData.getInstance().retrieveObjectList(Event.class, searchCriteria);
        Collections.sort(eventList); // Make sure events are in ascending order
        Collections.reverse(eventList); // descending order (i.e. most recent first)

        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        for (Event event : eventList) {
            returnList.add(TempConversionUtil.createMapFromObject(event, true));
        }

        return returnList;
    }

    @Override
    public List<Map<String, Object>> getVehicleNote(Integer vehicleID, Long startDate, Long endDate, Integer includeForgiven, Integer[] types) {
        // List<Event> vehicleEvents = new ArrayList<Event>();

        List<Object> typeList = new ArrayList<Object>();
        Collections.addAll(typeList, types);
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.addKeyValue("vehicleID", vehicleID);
        searchCriteria.addKeyValueInList("type", typeList);
        searchCriteria.addKeyValueRange("time", DateUtil.convertTimeInSecondsToDate(startDate), DateUtil.convertTimeInSecondsToDate(endDate));

        List<Event> eventList = MockData.getInstance().retrieveObjectList(Event.class, searchCriteria);
        Collections.sort(eventList); // Make sure events are in ascending order
        Collections.reverse(eventList); // descending order (i.e. most recent first)

        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        for (Event event : eventList) {
            returnList.add(TempConversionUtil.createMapFromObject(event, true));
        }

        return returnList;
    }

    @Override
    public Map<String, Object> getNoteNearLoc(Integer driverID, Double lat, Double lng, Long startDate, Long endDate) {
        return getDriverNote(driverID, startDate, endDate, 0, new Integer[] { NoteType.SPEEDING_EX3.getCode() }).size() > 0 ? getDriverNote(driverID, startDate, endDate, 0,
                new Integer[] { NoteType.SPEEDING_EX3.getCode() }).get(0) : null;
    }

    @Override
    public Map<String, Object> getDriverByPersonID(Integer personID) throws ProDAOException {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.addKeyValue("personID", personID);
        Map<String, Object> returnMap = MockData.getInstance().lookup(Driver.class, searchCriteria);
        if (returnMap == null) {
            throw new EmptyResultSetException("getDriverByPersonID() returned no value for personID=" + personID, "getDriverByPersonID()", 0);
        }
        return returnMap;
    }

    @Override
    public Map<String, Object> getUserByPersonID(Integer personID) throws ProDAOException {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.addKeyValue("personID", personID);
        Map<String, Object> returnMap = MockData.getInstance().lookup(User.class, searchCriteria);
        if (returnMap == null) {
            throw new EmptyResultSetException("getUserByPersonID() returned no value for personID=" + personID, "getUserByPersonID()", 0);
        }
        return returnMap;
    }

    @Override
    public List<Map<String, Object>> getDriversByGroupIDDeep(Integer groupID) throws ProDAOException {
        return getDriversByGroupID(groupID);
    }

    @Override
    public List<Map<String, Object>> getGroupsByGroupIDDeep(Integer groupID) throws ProDAOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getVehiclesByGroupIDDeep(Integer groupID) throws ProDAOException {
        return getVehiclesByGroupID(groupID);
    }

    @Override
    public List<Map<String, Object>> getMessages(Integer siloID, Integer alertMessageType) {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.addKeyValue("alertMessageDeliveryType", AlertMessageDeliveryType.valueOf(alertMessageType));
        List<Map<String, Object>> returnList = MockData.getInstance().lookupList(AlertMessage.class, searchCriteria);
        if (returnList == null) {
            throw new EmptyResultSetException("getMessages() returned no value for alertMessageType=" + alertMessageType, "getMessages()", 0);
        }
        return returnList;
    }

    @Override
    public Map<String, Object> getNote(Long noteID) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> forgive(Integer driverID, Long noteID) throws ProDAOException {
        Integer temp = 1;
        return TempConversionUtil.createMapFromObject(temp, true);
    }

    @Override
    public Map<String, Object> unforgive(Integer driverID, Long noteID) throws ProDAOException {
        Integer temp = 1;
        return TempConversionUtil.createMapFromObject(temp, true);
    }

    @Override
    public Map<String, Object> createReportPref(Integer acctID, Map<String, Object> reportPrefMap) throws ProDAOException {
        AbstractMapper mapper = new SimpleMapper();
        ReportSchedule reportSchedule = mapper.convertToModelObject(reportPrefMap, ReportSchedule.class);
        reportSchedule.setReportScheduleID((int) (Math.random() * Integer.MAX_VALUE));
        MockData.getInstance().storeObject(reportSchedule);
        logger.debug("Report Schedule Added: " + reportSchedule.getName());
        return createReturnValue("reportScheduleID", reportSchedule.getReportScheduleID());
    }

    @Override
    public Map<String, Object> deleteReportPref(Integer reportPrefID) throws ProDAOException {
        int newSize = MockData.getInstance().deleteObject(ReportSchedule.class, "reportScheduleID", reportPrefID);
        return createReturnValue("count", newSize);
    }

    @Override
    public Map<String, Object> getReportPref(Integer reportPrefID) throws ProDAOException {
        return doMockLookup(ReportSchedule.class, "reportScheduleID", reportPrefID, "No Report Schedule for ID: " + reportPrefID, "getReportSchedule");
    }

    @Override
    public Map<String, Object> updateReportPref(Integer reportPrefID, Map<String, Object> reportPrefMap) throws ProDAOException {

        return null;
    }

    @Override
    public List<Map<String, Object>> getReportPrefsByAcctID(Integer acctID) throws ProDAOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getReportPrefsByUserID(Integer userID) throws ProDAOException {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.addKeyValue("userID", userID);
        List<Map<String, Object>> returnList = MockData.getInstance().lookupList(ReportSchedule.class, searchCriteria);
        return returnList;
    }

    @Override
    public Map<String, Object> createCrash(Integer acctID, Map<String, Object> crashReportMap) throws ProDAOException {
        AbstractMapper mapper = new SimpleMapper();
        CrashReport crashReport = mapper.convertToModelObject(crashReportMap, CrashReport.class);
        crashReport.setCrashReportID((int) (Math.random() * Integer.MAX_VALUE));
        MockData.getInstance().storeObject(crashReport);
        logger.debug("CrashReport Added: " + crashReport.getCrashReportID());
        return createReturnValue("crashReportID", crashReport.getCrashReportID());
    }

    @Override
    public Map<String, Object> deleteCrash(Integer crashReportID) throws ProDAOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> getCrash(Integer crashReportID) throws ProDAOException {
        return MockData.getInstance().lookup(CrashReport.class, "crashReportID", crashReportID);
    }

    @Override
    public List<Map<String, Object>> getCrashes(Integer groupID, Long startDt, Long stopDT, Integer incForgiven) throws ProDAOException {
        SearchCriteria searchCriteria = new SearchCriteria();
        AbstractMapper mapper = new SimpleMapper();
        searchCriteria.addKeyValue("groupID", groupID);
        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> driverList = this.getDriversByGroupIDDeep(groupID);
        List<Driver> drivers = mapper.convertToModelObject(driverList, Driver.class);
        for (Driver driver : drivers) {
            Map<String, Object> map = MockData.getInstance().lookup(CrashReport.class, "driverID", driver.getDriverID());
            if (map != null)
                returnList.add(map);
        }

        return returnList;
    }

    @Override
    public Map<String, Object> updateCrash(Integer reportPrefID, Map<String, Object> crashReportMap) throws ProDAOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> forgiveCrash(Integer groupID) throws ProDAOException {
        Integer temp = 1;
        return TempConversionUtil.createMapFromObject(temp, true);
    }

    @Override
    public Map<String, Object> unforgiveCrash(Integer groupID) throws ProDAOException {
        Integer temp = 1;
        return TempConversionUtil.createMapFromObject(temp, true);
    }

    @Override
    public Map<String, Object> setVehicleDriver(Integer vehicleID, Integer driverID, Long assignTime) throws ProDAOException {
        // TODO Auto-generated method stub
        return null;
    }

    private Integer getIntValueFromFilter(String key, Map<String, String> filter) {

        String value = filter.get(key);
        if (value == null)
            return 0;

        try {
            Integer intValue = Integer.valueOf(value);
            return intValue;
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    List<Map<String, Object>> cacheRedFlagList = null;
    Integer cacheRFGroupID;
    Long cacheRFStartDate;
    Long cacheRFEndDate;
    Integer cacheRFIncludeForgiven;

    /*
     * @Override public Map<String, Object> getRedFlagsCount(Integer groupID, Long startDate, Long endDate, Integer includeForgiven, Map<String, String> filter) { if
     * (cacheRedFlagList != null) { if (cacheRFGroupID.equals(groupID) && cacheRFStartDate.equals(startDate) && cacheRFEndDate.equals(endDate) &&
     * cacheRFIncludeForgiven.equals(includeForgiven)) { return createReturnValue("count", cacheRedFlagList.size()); } } cacheRedFlagList = getRedFlags(groupID, startDate, endDate,
     * includeForgiven); cacheRFGroupID = groupID; cacheRFStartDate = startDate; cacheRFEndDate = endDate; cacheRFIncludeForgiven = includeForgiven; return
     * createReturnValue("count", cacheRedFlagList.size()); }
     * 
     * @Override public List<Map<String, Object>> getRedFlagsPage(Integer groupID, Long startDate, Long endDate, Integer includeForgiven, Map<String, Object> pageParams) { if
     * (cacheRedFlagList != null) { if (cacheRFGroupID.equals(groupID) && cacheRFStartDate.equals(startDate) && cacheRFEndDate.equals(endDate) &&
     * cacheRFIncludeForgiven.equals(includeForgiven)) { } } else { cacheRedFlagList = getRedFlags(groupID, startDate, endDate, includeForgiven); cacheRFGroupID = groupID;
     * cacheRFStartDate = startDate; cacheRFEndDate = endDate; cacheRFIncludeForgiven = includeForgiven; } List<Map<String, Object>> returnList = cacheRedFlagList; int startRow =
     * Integer.valueOf(pageParams.get("startRow").toString()); int endRow = Integer.valueOf(pageParams.get("endRow").toString()); logger.info("return subList " + startRow + " - " +
     * endRow); return returnList.subList(startRow, endRow); }
     */

    @Override
    public List<Long> getRfidsForBarcode(String barcode) throws ProDAOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getDVLByGroupIDDeep(Integer groupID) throws ProDAOException {
        Group group = MockData.getInstance().lookupObject(Group.class, "groupID", groupID);
        List<Driver> drivers = getAllDriversInGroup(group);

        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();

        int count = 0;
        for (Driver driver : drivers) {
            DriverLocation dl = new DriverLocation();

            dl.setDriver(driver);

            dl.setTime(new Date());
            // dl.setDriverID(driver.getDriverID());
            // dl.setGroupID(driver.getGroupID());
            // dl.setPriPhone(driver.getPerson().getPriPhone());
            // dl.setSecPhone(driver.getPerson().getSecPhone());
            // dl.setName(driver.getPerson().getFirst() + " " + driver.getPerson().getLast());
            // dl.setVehicleType(VehicleType.MEDIUM);

            SearchCriteria searchCriteria = new SearchCriteria();
            searchCriteria.addKeyValue("driverID", driver.getDriverID());

            LastLocation l = MockData.getInstance().retrieveObject(LastLocation.class, searchCriteria);
            dl.setLoc(l.getLoc());

            returnList.add(TempConversionUtil.createMapFromObject(dl, true));
            count++;
        }

        return returnList;
    }

    @Override
    public List<Map<String, Object>> getSiteAccessPts() {

        List<Map<String, Object>> accessPoints = new ArrayList<Map<String, Object>>();

        Map<String, Object> accessPoint = new HashMap<String, Object>();
        accessPoint.put("accessPtID", new Integer(1));
        accessPoint.put("msgKey", "rolesAccess");
        accessPoints.add(accessPoint);

        accessPoint = new HashMap<String, Object>();
        accessPoint.put("accessPtID", new Integer(2));
        accessPoint.put("msgKey", "usersAccess");
        accessPoints.add(accessPoint);

        accessPoint = new HashMap<String, Object>();
        accessPoint.put("accessPtID", new Integer(3));
        accessPoint.put("msgKey", "vehiclesAccess");
        accessPoints.add(accessPoint);

        accessPoint = new HashMap<String, Object>();
        accessPoint.put("accessPtID", new Integer(4));
        accessPoint.put("msgKey", "devicesAccess");
        accessPoints.add(accessPoint);

        accessPoint = new HashMap<String, Object>();
        accessPoint.put("accessPtID", new Integer(5));
        accessPoint.put("msgKey", "zonesAccess");
        accessPoints.add(accessPoint);

        accessPoint = new HashMap<String, Object>();
        accessPoint.put("accessPtID", new Integer(6));
        accessPoint.put("msgKey", "zoneAlertsAccess");
        accessPoints.add(accessPoint);

        accessPoint = new HashMap<String, Object>();
        accessPoint.put("accessPtID", new Integer(7));
        accessPoint.put("msgKey", "redFlagsAccess");
        accessPoints.add(accessPoint);

        accessPoint = new HashMap<String, Object>();
        accessPoint.put("accessPtID", new Integer(8));
        accessPoint.put("msgKey", "reportsAccess");
        accessPoints.add(accessPoint);

        accessPoint = new HashMap<String, Object>();
        accessPoint.put("accessPtID", new Integer(9));
        accessPoint.put("msgKey", "organizationAccess");
        accessPoints.add(accessPoint);

        accessPoint = new HashMap<String, Object>();
        accessPoint.put("accessPtID", new Integer(10));
        accessPoint.put("msgKey", "speedByStreetAccess");
        accessPoints.add(accessPoint);

        return accessPoints;
    }

    @Override
    public List<Map<String, Object>> getRolesByAcctID(Integer acctID) {

        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        for (Object obj : MockRoles.getAll()) {
            returnList.add(TempConversionUtil.createMapFromObject(obj, true));
        }

        return returnList;
    }

    @Override
    public Map<String, Object> createRole(Integer acctID, Map<String, Object> roleMap) throws ProDAOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> deleteRole(Integer roleID) throws ProDAOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> getRole(Integer roleID) throws ProDAOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> updateRole(Integer roleID, Map<String, Object> roleMap) throws ProDAOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getDriverEventPage(Integer groupID, Long startDate, Long endDate, Integer includeForgiven, Map<String, Object> pageParams, Integer[] types) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> getDriverEventCount(Integer groupID, Long startDate, Long endDate, Integer includeForgiven, List<Map<String, Object>> filterList, Integer[] types) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getRedFlagsPage(Integer groupID, Long startDate, Long endDate, Integer includeForgiven, Map<String, Object> pageParams) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> getRedFlagsCount(Integer groupID, Long startDate, Long endDate, Integer includeForgiven, List<Map<String, Object>> filterList) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getUsersAccessPts(Integer userID) {

        List<Map<String, Object>> accessPoints = new ArrayList<Map<String, Object>>();

        Map<String, Object> accessPoint = new HashMap<String, Object>();
        accessPoint.put("siteAccessPtID", new Integer(1));
        accessPoint.put("mode", new Integer(15));
        accessPoints.add(accessPoint);

        accessPoint = new HashMap<String, Object>();
        accessPoint.put("siteAccessPtID", new Integer(2));
        accessPoint.put("mode", new Integer(15));
        accessPoints.add(accessPoint);

        accessPoint = new HashMap<String, Object>();
        accessPoint.put("siteAccessPtID", new Integer(3));
        accessPoint.put("mode", new Integer(15));
        accessPoints.add(accessPoint);

        accessPoint = new HashMap<String, Object>();
        accessPoint.put("siteAccessPtID", new Integer(4));
        accessPoint.put("mode", new Integer(15));
        accessPoints.add(accessPoint);

        return accessPoints;
    }

    @Override
    public Map<String, Object> getDeviceReportCount(Integer groupID, List<Map<String, Object>> filterList) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getDeviceReportPage(Integer groupID, Map<String, Object> pageParams) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> getDriverReportCount(Integer groupID, List<Map<String, Object>> filterList) {
        List<Map<String, Object>> driverList = getDriversByGroupID(groupID);
        
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("count", driverList == null ? 0 : driverList.size());
        
        return returnMap;
    }

    @Override
    public List<Map<String, Object>> getDriverReportPage(Integer groupID, Map<String, Object> pageParams) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> getIdlingReportCount(Integer groupID, Long startDate, Long endDate, List<Map<String, Object>> filterList) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getIdlingReportPage(Integer groupID, Long startDate, Long endDate, Map<String, Object> pageParams) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> getVehicleReportCount(Integer groupID, List<Map<String, Object>> filterList) {
        List<Map<String, Object>> vehicleList = getVehiclesByGroupID(groupID);
        
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("count", vehicleList == null ? 0 : vehicleList.size());
        
        return returnMap;
    }

    @Override
    public List<Map<String, Object>> getVehicleReportPage(Integer groupID, Map<String, Object> pageParams) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> clearSuperuser(Integer userID) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> isSuperuser(Integer userID) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> setSuperuser(Integer userID) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> createFwdCmdDef(Map<String, Object> fwdCmdDefMap) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> deleteFwdCmdDef(Integer fwdCmd) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getFwdCmdDefs() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> updateFwdCmdDef(Map<String, Object> fwdCmdDefMap) {
        // TODO Auto-generated method stub
        return null;
    }
	@Override
	public List<Map<String, Object>> getAlertsByUserIDDeep(Integer userID) {
        User user= (User) MockData.getInstance().lookupObject(User.class, "userID", userID);
        Person person = (Person) MockData.getInstance().lookupObject(Person.class, "personID", user.getPersonID());
		return getAlertsByAcctID(person.getAcctID());
	}

	@Override
	public List<Map<String, Object>> getAlertsByUserID(Integer userID) {
        User user= (User) MockData.getInstance().lookupObject(User.class, "userID", userID);
		return getAlertsByAcctID(user.getPerson().getAcctID());
	}

	@Override
	public List<Map<String, Object>> getReportPrefsByUserIDDeep(Integer userID)
			throws ProDAOException {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.addKeyValue("userID", userID);
        List<Map<String, Object>> returnList =  MockData.getInstance().lookupList(ReportSchedule.class,searchCriteria);
        return returnList;
	}

    @Override
    public List<Map<String, Object>> getSettingDefs() {

        return null;
    }

    @Override
    public Map<String, Object> getVehicleSettings(Integer vehicleID) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getVehicleSettingsByGroupIDDeep(Integer groupID) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getVehicleSettingsHistory(Integer vehicleID, Long startTime, Long endTime) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> setVehicleSettings(Integer vehicleID, Map<Integer, String> setMap, Integer userID, String reason) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> updateVehicleSettings(Integer vehicleID, Map<Integer, String> setMap, Integer userID, String reason) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> setVehicleDevice(Integer vehicleID, Integer deviceID, Long assignTime) throws ProDAOException {
        // TODO Auto-generated method stub
        return null;
    }
    
//    @Override
//    public Map<String,Object> createTextMsgAlert(Integer acctID, Map<String,Object> textMsgAlertMap) throws ProDAOException {
//        return null;
//    }
//    
    @Override
	public List<Map<String, Object>> getSensitivitySliderValues() {
		// TODO Auto-generated method stub
		return null;
	}
    @Override
    public Map<String, Object> getTextMsgCount(Integer groupID, Long startDate, Long endDate, List<Map<String, Object>> filterList) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getTextMsgPage(Integer groupID, Long startDate, Long endDate, List<Map<String, Object>> filterList, Map<String, Object> pageParams) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getSentTextMsgsByGroupID(Integer groupID, Long startDate, Long endDate) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> publishZones(Integer accountID) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> deleteAlertsByZoneID(Integer zoneID) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getAlertsByTeamGroupID(Integer groupID) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> createCellblock(Integer driverID, Map<String, Object> cellblockMap) throws ProDAOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> deleteCellblock(Integer driverID) throws ProDAOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> getCellblock(Integer driverID) throws ProDAOException {
        Map<String,Object> cellblockMap = new HashMap<String,Object>();
        cellblockMap.put("driverID", driverID);
        cellblockMap.put("acctID", 1);
        cellblockMap.put("cellPhone", "8017127234");
        cellblockMap.put("cellStatus", CellStatusType.DISABLED);
        cellblockMap.put("provider", CellProviderType.CELL_CONTROL);
        cellblockMap.put("providerUser", "8017127234");
        cellblockMap.put("provicerPass", "password");
        return cellblockMap;
//        return doMockLookup(Cellblock.class, "driverID", driverID, "No cellblock for ID: " + driverID, "getCellblock");
    }

    @Override
    public Map<String, Object> updateCellblock(Integer driverID, Map<String, Object> cellblockMap) throws ProDAOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> findByPhoneNumber(String phoneID) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getCellblocksByAcctID(Integer acctID) {
        final SearchCriteria criteria = new SearchCriteria();
        criteria.addKeyValue("acctID", acctID);
        return MockData.getInstance().lookupList(Cellblock.class, criteria);
    }

    @Override
    public List<Map<String, Object>> getDriversWithDisabledPhones(Integer siloID) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getDriverNamesByGroupIDDeep(Integer groupID) throws ProDAOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getVehicleNamesByGroupIDDeep(Integer groupID) throws ProDAOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> getPersonsByAcctID(Integer acctID) throws ProDAOException {
        // TODO Auto-generated method stub
        return null;
    }

	@Override
	public Map<String, Object> getPersonByEmpid(String empID)
			throws ProDAOException {
		// TODO Auto-generated method stub
		return null;
	}

}
