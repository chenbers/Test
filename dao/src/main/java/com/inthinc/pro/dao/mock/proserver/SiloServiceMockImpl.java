package com.inthinc.pro.dao.mock.proserver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.ProDAOException;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.mock.data.MockData;
import com.inthinc.pro.dao.mock.data.SearchCriteria;
import com.inthinc.pro.dao.mock.data.TempConversionUtil;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Device;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.Event;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.RedFlag;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.model.TablePreference;
import com.inthinc.pro.model.Trip;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.Vehicle;
import com.inthinc.pro.model.Zone;

public class SiloServiceMockImpl implements SiloService
{

    private static final Logger logger = Logger.getLogger(SiloServiceMockImpl.class);

    // helper method
    private Map<String, Object> doMockLookup(Class<?> clazz, String key, Object searchValue, String emptyResultSetMsg, String methodName)
    {
        Map<String, Object> returnMap = MockData.getInstance().lookup(clazz, key, searchValue);

        if (returnMap == null)
        {
            throw new EmptyResultSetException(emptyResultSetMsg, methodName, 0);
        }
        return returnMap;

    }

    private Map<String, Object> createReturnValue(String key, Integer value)
    {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put(key, value);
        return returnMap;
    }

    @Override
    public Map<String, Object> deleteUser(Integer userID) throws ProDAOException
    {
        return createReturnValue("count", 0);
    }

    @Override
    public Map<String, Object> createUser(Integer acctID, Map<String, Object> userMap) throws ProDAOException
    {
        // TODO: actually store the object to the mock data
        return createReturnValue("userID", (int) (Math.random() * Integer.MAX_VALUE));
    }

    @Override
    public Map<String, Object> getUser(Integer userID) throws ProDAOException
    {
        return doMockLookup(User.class, "userID", userID, "No user for ID: " + userID, "getUser");
    }

    @Override
    public Map<String, Object> updateUser(Integer userID, Map<String, Object> userMap) throws ProDAOException
    {
        return createReturnValue("count", 1);
    }

    @Override
    public Map<String, Object> getUserByAccountID(Integer accountID) throws ProDAOException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> getUserIDByEmail(String email) throws ProDAOException
    {
        Map<String, Object> returnMap = null;
        Person person = MockData.getInstance().retrieveObject(Person.class, "email", email);
        if ((person != null) && (person.getUser() != null))
        {
            returnMap = TempConversionUtil.createMapFromObject(person.getUser());
        }

        if (returnMap == null)
        {
            throw new EmptyResultSetException("No user for email: " + email, "getUserIDByEmail", 0);
        }
        return returnMap;
    }

    @Override
    public Map<String, Object> getUserIDByName(String username) throws ProDAOException
    {
        Map<String, Object> returnMap = MockData.getInstance().lookup(User.class, "username", username);

        if (returnMap == null)
        {
            throw new EmptyResultSetException("No user for username: " + username, "getUserIDByUsername", 0);
        }
        return returnMap;
    }

    @Override
    public Map<String, Object> deletePerson(Integer personID) throws ProDAOException
    {
        return createReturnValue("count", 0);
    }

    @Override
    public Map<String, Object> createPerson(Integer acctID, Map<String, Object> personMap) throws ProDAOException
    {
        // TODO: actually store the object to the mock data
        return createReturnValue("personID", (int) (Math.random() * Integer.MAX_VALUE));
    }

    @Override
    public Map<String, Object> getPerson(Integer personID) throws ProDAOException
    {
        return doMockLookup(Person.class, "personID", personID, "No person for ID: " + personID, "getPerson");

    }

    @Override
    public Map<String, Object> updatePerson(Integer personID, Map<String, Object> personMap) throws ProDAOException
    {
        return createReturnValue("count", 1);
    }

    @Override
    public List<Map<String, Object>> getPersonIDsInGroupHierarchy(Integer groupID)
    {
        final List<Map<String, Object>> personIDs = new LinkedList<Map<String, Object>>();

        final List<Map<String, Object>> hierarchy = new SiloServiceCreator().getService().getGroupHierarchy(groupID);
        for (final Map<String, Object> map : hierarchy)
        {
            final Integer id = (Integer) map.get("groupID");
            if (id != null)
            {
                final SearchCriteria criteria = new SearchCriteria();
                criteria.addKeyValue("groupID", id);
                final List<Map<String, Object>> matches = MockData.getInstance().lookupList(Person.class, criteria);
                if (matches != null)
                    personIDs.addAll(matches);
            }
        }

        return personIDs;
    }

    @Override
    public Map<String, Object> getAverageScoreByType(Integer groupID, Integer startDate, Integer endDate, ScoreType st) throws ProDAOException
    {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.addKeyValue("entityID", groupID);
        searchCriteria.addKeyValue("scoreType", st);
        searchCriteria.addKeyValueRange("date", startDate, endDate);

        // get all scores of the time period and average them
        List<ScoreableEntity> allScores = MockData.getInstance().retrieveObjectList(ScoreableEntity.class, searchCriteria);
        if (allScores.size() == 0)
        {
            throw new EmptyResultSetException("No overall score for: " + groupID, "getOverallScore", 0);
        }

        return getAverageScore(startDate, allScores);

    }
    
    @Override
    public Map<String, Object> getAverageScoreByTypeAndMiles(Integer driverID, Integer milesBack, ScoreType st) throws ProDAOException
    {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.addKeyValue("entityID", driverID);
        searchCriteria.addKeyValue("scoreType", st);
        

        // get all scores of the time period and average them
        List<ScoreableEntity> allScores = MockData.getInstance().retrieveObjectList(ScoreableEntity.class, searchCriteria);
        if (allScores.size() == 0)
        {
            throw new EmptyResultSetException("No overall score for: " + driverID, "getOverallScore", 0);
        }

        return getAverageScore(milesBack, allScores);

    }

    @Override
    public List<Map<String, Object>> getScores(Integer groupID, Integer startDate, Integer endDate, Integer scoreType) throws ProDAOException
    {
        logger.debug("mock IMPL getOverallScores groupID = " + groupID);
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.addKeyValue("parentID", groupID);

        // get list of groups that have the specified groupID as the parent
        List<Map<String, Object>> entityList = MockData.getInstance().lookupList(Group.class, searchCriteria);

        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        if (entityList != null)
        {
            for (Map<String, Object> groupMap : entityList)
            {
                searchCriteria = new SearchCriteria();
                searchCriteria.addKeyValue("entityID", groupMap.get("groupID"));
                searchCriteria.addKeyValue("scoreType", ScoreType.valueOf(scoreType));
                // searchCriteria.addKeyValue("scoreValueType", ScoreValueType.SCORE_SCALE_0_50);
                searchCriteria.addKeyValueRange("date", startDate, endDate);

                List<ScoreableEntity> allScores = MockData.getInstance().retrieveObjectList(ScoreableEntity.class, searchCriteria);
                if (allScores.size() > 0)
                {
                    returnList.add(getAverageScore(startDate, allScores));
                }
                else
                {
                    logger.error("score missing for groupID " + groupMap.get("groupID"));
                }
            }
        }
        else
        {
            searchCriteria = new SearchCriteria();
            searchCriteria.addKeyValue("groupID", groupID);

            // get list of drivers that are in the specified group
            entityList = MockData.getInstance().lookupList(Driver.class, searchCriteria);
            if (entityList == null)
            {
                return returnList;
            }
            for (Map<String, Object> driverMap : entityList)
            {
                searchCriteria = new SearchCriteria();
                searchCriteria.addKeyValue("entityID", driverMap.get("driverID"));
                searchCriteria.addKeyValue("scoreType", ScoreType.valueOf(scoreType));
                searchCriteria.addKeyValueRange("date", startDate, endDate);

                List<ScoreableEntity> allScores = MockData.getInstance().retrieveObjectList(ScoreableEntity.class, searchCriteria);
                if (allScores.size() > 0)
                {
                    returnList.add(getAverageScore(startDate, allScores));
                }
                else
                {
                    logger.error("score missing for driverID " + driverMap.get("driverID"));
                }
            }

        }
        return returnList;
    }

    @Override
    public List<Map<String, Object>> getScoreBreakdown(Integer groupID, Integer startDate, Integer endDate, Integer scoreType) throws ProDAOException
    {

        Group topGroup = MockData.getInstance().lookupObject(Group.class, "groupID", groupID);

        List<Driver> allDriversInGroup = getAllDriversInGroup(topGroup);

        int totals[] = new int[5];
        for (Driver driver : allDriversInGroup)
        {
            SearchCriteria searchCriteria = new SearchCriteria();
            searchCriteria.addKeyValue("entityID", driver.getDriverID());
            searchCriteria.addKeyValue("scoreType", ScoreType.valueOf(scoreType));
            searchCriteria.addKeyValueRange("date", startDate, endDate);
            List<ScoreableEntity> allScores = MockData.getInstance().retrieveObjectList(ScoreableEntity.class, searchCriteria);
            Map<String, Object> scoreMap = getAverageScore(startDate, allScores);

            Integer score = (Integer) scoreMap.get("score");
            int idx = (score - 1) / 10;
            totals[idx]++;
        }
        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        int totalDrivers = allDriversInGroup.size();
        int percentTotal = 0;
        for (int i = 0; i < 5; i++)
        {
            int percent = 0;
            if (i < 4)
            {
                percent = Math.round((float) ((float) totals[i] * 100f) / (float) totalDrivers);
                percentTotal += percent;
            }
            else
            {
                percent = 100 - percentTotal;
            }
            returnList.add(TempConversionUtil.createMapFromObject(new ScoreableEntity(groupID, EntityType.ENTITY_GROUP, (i + 1) + "", // name will be 1 to 5 for the 5 different
                                                                                                                                        // score breakdowns
                    new Integer(percent), startDate, ScoreType.valueOf(scoreType))));
        }

        return returnList;
    }
 
    @Override
    public List<Map<String, Object>> getBottomFiveScores(Integer groupID)
    {
        Integer endDate = DateUtil.getTodaysDate();
        Integer startDate = DateUtil.getDaysBackDate(endDate, 30);
        try {
	    	List<Map<String, Object>> returnList =  getScores(groupID, startDate, endDate, ScoreType.SCORE_OVERALL.getCode());
	    	//TODO  return top 5.   
	    	if (returnList.size() > 5){
	    		for (int i=0;returnList.size() > 5;){
	    			
	    			returnList.remove(i);
	    		}
	    	}
	        return returnList;
        }
        catch(ProDAOException pdaoe){
        	
        	return null;
        }
    }
    @Override
    public List<Map<String, Object>> getTopFiveScores(Integer groupID)
    {
        Integer endDate = DateUtil.getTodaysDate();
        Integer startDate = DateUtil.getDaysBackDate(endDate, 30);
    	List<Map<String, Object>> returnList =  getScores(groupID, startDate, endDate, ScoreType.SCORE_OVERALL.getCode());
    	//TODO  return top 5.   
    	if (returnList.size() > 5){
    		for (int i=5;returnList.size() > 5;){
    			
    			returnList.remove(i);
    		}
    	}
        return returnList;
    }
    
    @Override
    public List<Map<String, Object>> getDriverScoreHistoryByMiles(Integer driverID, Integer milesBack, Integer scoreType) throws ProDAOException
    {
        Integer currentOdometer = 9923;
        Integer numScoreRecords = 10;
        
        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        
        for (int i = 0; i < numScoreRecords; i++)
        {
            ScoreableEntity se = new ScoreableEntity();
            
            if(i==numScoreRecords) //No room for label on last data point.  Place it next to last.
                se.setIdentifier(currentOdometer.toString() + "mi"); //Mileage at time the score was calculated.
     
            if(i==0)
            {
                Integer temp = milesBack > currentOdometer ? 0 : currentOdometer - milesBack; 
                se.setIdentifier(temp.toString() + "mi");
            }
            else
                se.setIdentifier("");
            
            se.setScore((int) (Math.random() * ((50 - 0) + 1)) + 0);
            
            returnList.add(TempConversionUtil.createMapFromObject(se));          
        }
        
        return returnList;
    }
    
    
    @Override
    public List<Map<String, Object>> getVehiclesByAcctID(Integer acctID) throws ProDAOException
    {
        final SearchCriteria criteria = new SearchCriteria();
        criteria.addKeyValue("accountID", acctID);
        return MockData.getInstance().lookupList(Vehicle.class, criteria);
    }

    @Override
    public List<Map<String, Object>> getVehiclesInGroupHierarchy(Integer groupID)
    {
        final List<Map<String, Object>> vehicles = new LinkedList<Map<String, Object>>();

        final List<Map<String, Object>> hierarchy = new SiloServiceCreator().getService().getGroupHierarchy(groupID);
        for (final Map<String, Object> map : hierarchy)
        {
            final Integer id = (Integer) map.get("groupID");
            if (id != null)
            {
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
    public Map<String, Object> deleteVehicle(Integer vehicleID) throws ProDAOException
    {
        return createReturnValue("count", 0);
    }

    @Override
    public Map<String, Object> createVehicle(Integer acctID, Map<String, Object> vehicleMap) throws ProDAOException
    {
        // TODO: actually store the object to the mock data
        return createReturnValue("vehicleID", (int) (Math.random() * Integer.MAX_VALUE));
    }

    @Override
    public Map<String, Object> getVehicle(Integer vehicleID) throws ProDAOException
    {
        return doMockLookup(Vehicle.class, "vehicleID", vehicleID, "No vehicle for ID: " + vehicleID, "getVehicle");

    }

    @Override
    public Map<String, Object> updateVehicle(Integer vehicleID, Map<String, Object> vehicleMap) throws ProDAOException
    {
        return createReturnValue("count", 1);
    }

    @Override
    public Map<String, Object> createGroup(Integer acctID, Map<String, Object> groupMap) throws ProDAOException
    {
        // TODO: actually store the object to the mock data
        return createReturnValue("groupID", (int) (Math.random() * Integer.MAX_VALUE));
    }

    @Override
    public Map<String, Object> deleteGroup(Integer groupID) throws ProDAOException
    {
        return createReturnValue("count", 0);
    }

    @Override
    public Map<String, Object> getGroup(Integer groupID) throws ProDAOException
    {
        return doMockLookup(Group.class, "groupID", groupID, "No group for ID: " + groupID, "getGroup");
    }

    @Override
    public Map<String, Object> updateGroup(Integer groupID, Map<String, Object> groupMap) throws ProDAOException
    {
        return createReturnValue("count", 1);
    }

    @Override
    public List<Map<String, Object>> getGroupHierarchy(Integer groupID) throws ProDAOException
    {
        Group topGroup = MockData.getInstance().lookupObject(Group.class, "groupID", groupID);
        if (topGroup == null)
            return new ArrayList<Map<String, Object>>();

        List<Group> hierarchyGroups = new ArrayList<Group>();
        hierarchyGroups.add(topGroup);

        // filter out just the ones in the hierarchy
        List<Group> allGroups = MockData.getInstance().lookupObjectList(Group.class, new Group());
        addChildren(hierarchyGroups, allGroups, groupID);

        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        for (Group group : hierarchyGroups)
        {
            returnList.add(TempConversionUtil.createMapFromObject(group));
        }

        return returnList;
    }

    @Override
    public List<Map<String, Object>> getTrips(Integer driverID, Integer startDate, Integer endDate) throws ProDAOException
    {
        SearchCriteria criteria = new SearchCriteria();
        criteria.addKeyValue("driverID", driverID);
        List<Map<String, Object>> matches = MockData.getInstance().lookupList(Trip.class, criteria);
        return matches;

    }
    
    @Override
    public Map<String, Object> getLastTrip(Integer driverID)
    {
        SearchCriteria criteria = new SearchCriteria();
        criteria.addKeyValue("driverID", driverID);
        Map<String, Object> matches = MockData.getInstance().lookup(Trip.class, criteria);
        return matches;
        
    }

    @Override
    public List<Map<String, Object>> getMostRecentEvents(Integer groupID, Integer eventCnt, Integer[] types) throws ProDAOException
    {
        Group group = MockData.getInstance().lookupObject(Group.class, "groupID", groupID);

        List<Driver> drivers = getAllDriversInGroup(group);

        List<Event> allEventsForGroup = new ArrayList<Event>();

        List<Object> typeList = new ArrayList<Object>();
        Collections.addAll(typeList, types);
        for (Driver driver : drivers)
        {
            SearchCriteria searchCriteria = new SearchCriteria();
            searchCriteria.addKeyValue("driverID", driver.getDriverID());
            searchCriteria.addKeyValueInList("type", typeList);
            List<Event> eventList = MockData.getInstance().retrieveObjectList(Event.class, searchCriteria);
            allEventsForGroup.addAll(eventList);
        }

        if (allEventsForGroup.size() == 0)
        {
            throw new EmptyResultSetException("no events for group", "getMostRecentEvents", 0);
        }

        Collections.sort(allEventsForGroup); // Make sure events are in ascending order
        Collections.reverse(allEventsForGroup); // descending order (i.e. most recent first)

        // to do: first sort my date
        int cnt = 0;
        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
        for (Event event : allEventsForGroup)
        {
            returnList.add(TempConversionUtil.createMapFromObject(event, true));
            cnt++;
            if (cnt == eventCnt.intValue())
                break;
        }

        return returnList;
    }

    // ----------- HELPER METHODS ---------------------

    private Map<String, Object> getAverageScore(Integer startDate, List<ScoreableEntity> allScores)
    {
        int total = 0;
        ScoreableEntity firstEntity = allScores.get(0);
        ScoreableEntity returnEntity = new ScoreableEntity(firstEntity.getEntityID(), EntityType.ENTITY_GROUP, firstEntity.getIdentifier(), 0, startDate, firstEntity
                .getScoreType());
        for (ScoreableEntity entity : allScores)
        {
            total += entity.getScore();
        }
        returnEntity.setScore(total / allScores.size());

        return TempConversionUtil.createMapFromObject(returnEntity);
    }

    private void addChildren(List<Group> hierarchyGroups, List<Group> allGroups, Integer groupID)
    {
        for (Group group : allGroups)
        {
            if (group.getParentID().equals(groupID))
            {
                hierarchyGroups.add(group);
                addChildren(hierarchyGroups, allGroups, group.getGroupID());
            }
        }

    }

    // get all drivers that are under the specified group -- children, grandchildren, etc.
    private List<Driver> getAllDriversInGroup(Group topGroup)
    {
        // TODO Auto-generated method stub
        List<Group> hierarchyGroups = new ArrayList<Group>();
        hierarchyGroups.add(topGroup);

        // filter out just the ones in the hierarchy
        List<Group> allGroups = MockData.getInstance().lookupObjectList(Group.class, new Group());
        addChildren(hierarchyGroups, allGroups, topGroup.getGroupID());

        List<Driver> returnDriverList = new ArrayList<Driver>();
        for (Group group : hierarchyGroups)
        {
            SearchCriteria searchCriteria = new SearchCriteria();
            searchCriteria.addKeyValue("groupID", group.getGroupID());

            List<Driver> driverList = MockData.getInstance().retrieveObjectList(Driver.class, searchCriteria);
            returnDriverList.addAll(driverList);

        }

        return returnDriverList;
    }

    @Override
    public List<Map<String, Object>> getDevicesByAcctID(Integer acctID) throws ProDAOException
    {
        final SearchCriteria criteria = new SearchCriteria();
        criteria.addKeyValue("accountID", acctID);
        return MockData.getInstance().lookupList(Device.class, criteria);
    }

    @Override
    public Map<String, Object> deleteDevice(Integer deviceID) throws ProDAOException
    {
        return createReturnValue("count", 0);
    }

    @Override
    public Map<String, Object> createDevice(Integer acctID, Map<String, Object> deviceMap) throws ProDAOException
    {
        // TODO: actually store the object to the mock data
        return createReturnValue("deviceID", (int) (Math.random() * Integer.MAX_VALUE));
    }

    @Override
    public Map<String, Object> getDevice(Integer deviceID) throws ProDAOException
    {
        return doMockLookup(Device.class, "deviceID", deviceID, "No device for ID: " + deviceID, "getDevice");

    }

    @Override
    public Map<String, Object> updateDevice(Integer deviceID, Map<String, Object> deviceMap) throws ProDAOException
    {
        return createReturnValue("count", 1);
    }

    @Override
    public List<Map<String, Object>> getAllDrivers(Integer groupID)
    {
        logger.debug("mock IMPL getAllDrivers groupID = " + groupID);
        Group group = MockData.getInstance().lookupObject(Group.class, "groupID", groupID);

        List<Driver> drivers = getAllDriversInGroup(group);
        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();

        for (Driver driver : drivers)
        {
            returnList.add(TempConversionUtil.createMapFromObject(driver, true));
        }

        return returnList;
    }

    @Override
    public Map<String, Object> deleteDriver(Integer driverID) throws ProDAOException
    {
        return createReturnValue("count", 0);
    }

    @Override
    public Map<String, Object> createDriver(Integer acctID, Map<String, Object> driverMap) throws ProDAOException
    {
        // TODO: actually store the object to the mock data
        return createReturnValue("driverID", (int) (Math.random() * Integer.MAX_VALUE));
    }

    @Override
    public Map<String, Object> getDriver(Integer driverID) throws ProDAOException
    {
        return doMockLookup(Driver.class, "driverID", driverID, "No driver for ID: " + driverID, "getDriver");
    }

    @Override
    public Map<String, Object> updateDriver(Integer driverID, Map<String, Object> driverMap) throws ProDAOException
    {
        return createReturnValue("count", 1);
    }

    @Override
    public Map<String, Object> setVehicleDriver(Integer vehicleID, Integer driverID) throws ProDAOException
    {
        final Vehicle vehicle = MockData.getInstance().lookupObject(Vehicle.class, "vehicleID", vehicleID);
        if (vehicle != null)
            vehicle.setDriverID(driverID);

        return createReturnValue("count", 1);
    }

    @Override
    public Map<String, Object> setVehicleDevice(Integer vehicleID, Integer deviceID) throws ProDAOException
    {
        final Vehicle vehicle = MockData.getInstance().lookupObject(Vehicle.class, "vehicleID", vehicleID);
        if (vehicle != null)
            vehicle.setDeviceID(deviceID);

        if (deviceID != null)
        {
            final Device device = MockData.getInstance().lookupObject(Device.class, "deviceID", deviceID);
            if (device != null)
                device.setVehicleID(vehicleID);
        }

        return createReturnValue("count", 1);
    }

    @Override
    public List<Map<String, Object>> getRedFlags(Integer groupID) throws ProDAOException
    {
        Group group = MockData.getInstance().lookupObject(Group.class, "groupID", groupID);

        List<Driver> drivers = getAllDriversInGroup(group);
        List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();

        for (Driver driver : drivers)
        {
            SearchCriteria searchCriteria = new SearchCriteria();
            searchCriteria.addKeyValue("event:driverID", driver.getDriverID());
            returnList.addAll(MockData.getInstance().lookupList(RedFlag.class, searchCriteria));
        }

        return returnList;
    }

    @Override
    public Map<String, Object> getGroupByID(Integer groupID) throws ProDAOException
    {
        Map<String, Object> returnMap = MockData.getInstance().lookup(Group.class, "groupID", groupID);

        if (returnMap == null)
        {
            throw new EmptyResultSetException("No group for groupID: " + groupID, "getGroupByID", 0);
        }
        return returnMap;
    }    
    
    @Override
    public Map<String, Object> getVehicleByID(Integer vehicleID) throws ProDAOException
    {
        Map<String, Object> returnMap = MockData.getInstance().lookup(Vehicle.class, "vehicleID", vehicleID);

        if (returnMap == null)
        {
            throw new EmptyResultSetException("No vehicle for vehicleID: " + vehicleID, "getVehicleByID", 0);
        }
        return returnMap;        
    }
    
    
    @Override
    public Map<String, Object> getDriverByID(Integer driverID) throws ProDAOException
    {
        Map<String, Object> returnMap = MockData.getInstance().lookup(Driver.class, "driverID", driverID);

        if (returnMap == null)
        {
            throw new EmptyResultSetException("No driver for driverID: " + driverID, "getDriverByID", 0);
        }
        return returnMap;        
    }
    
    
    @Override
    public List<Map<String, Object>> getTablePreferencesByUserID(Integer userID) throws ProDAOException
    {
        SearchCriteria searchCriteria = new SearchCriteria();
        searchCriteria.addKeyValue("userID", userID);
        List<Map<String, Object>> returnList= MockData.getInstance().lookupList(TablePreference.class, searchCriteria);

        if (returnList == null)
        {
            throw new EmptyResultSetException("No table preferences for userID: " + userID, "getTablePreferencesByUserID", 0);
        }

        return returnList;
    }

    @Override
    public Map<String, Object> deleteTablePreference(Integer tablePrefID) throws ProDAOException
    {
        return createReturnValue("count", 0);
    }

    @Override
    public Map<String, Object> createTablePreference(Integer acctID, Map<String, Object> tablePreferenceMap) throws ProDAOException
    {
        // TODO: actually store the object to the mock data
        return createReturnValue("tablePrefID", (int) (Math.random() * Integer.MAX_VALUE));
    }

    @Override
    public Map<String, Object> getTablePreference(Integer tablePrefID) throws ProDAOException
    {
        return doMockLookup(TablePreference.class, "tablePrefID", tablePrefID, "No tablePreference for ID: " + tablePrefID, "getTablePreference");
    }

    @Override
    public Map<String, Object> updateTablePreference(Integer tablePrefID, Map<String, Object> tablePreferenceMap) throws ProDAOException
    {
        return createReturnValue("count", 1);
    }

    @Override
    public List<Map<String, Object>> getZoneIDsInGroupHierarchy(Integer groupID)
    {
        final List<Map<String, Object>> zoneIDs = new LinkedList<Map<String, Object>>();
    
        final List<Map<String, Object>> hierarchy = new SiloServiceCreator().getService().getGroupHierarchy(groupID);
        for (final Map<String, Object> map : hierarchy)
        {
            final Integer id = (Integer) map.get("groupID");
            if (id != null)
            {
                final SearchCriteria criteria = new SearchCriteria();
                criteria.addKeyValue("groupID", id);
                final List<Map<String, Object>> matches = MockData.getInstance().lookupList(Zone.class, criteria);
                if (matches != null)
                    zoneIDs.addAll(matches);
            }
        }
    
        return zoneIDs;
    }

    @Override
    public Map<String, Object> deleteZone(Integer zoneID) throws ProDAOException
    {
        return createReturnValue("count", 0);
    }

    @Override
    public Map<String, Object> createZone(Integer acctID, Map<String, Object> zoneMap) throws ProDAOException
    {
        // TODO: actually store the object to the mock data
        return createReturnValue("zoneID", (int) (Math.random() * Integer.MAX_VALUE));
    }

    @Override
    public Map<String, Object> getZone(Integer zoneID) throws ProDAOException
    {
        return doMockLookup(Zone.class, "zoneID", zoneID, "No zone for ID: " + zoneID, "getZone");

    }

    @Override
    public Map<String, Object> updateZone(Integer zoneID, Map<String, Object> zoneMap) throws ProDAOException
    {
        return createReturnValue("count", 1);
    }
}
