package com.inthinc.pro.reports.dao.mock;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.Interval;

import com.inthinc.pro.dao.StateMileageDAO;
import com.inthinc.pro.model.StateMileage;

/**
 * Mock for StateMileageDAO.
 */
public class MockStateMileageDAO implements StateMileageDAO {
    private static final String DISTANCE = "Distance";
    private static final String STATE_1 = "Florida";
    private static final String STATE_2 = "Ohio";
    private static final String MOCK_GROUP_NAME = "Stub Group Name ";
    private static final String MOCK_PREFIX = "STUB ";
	private static final String MOCK_MONTH_AUG = "Aug, 2010";
	private static final String MOCK_MONTH_SEP = "Sep, 2010";

    /**
     * Default constructor.
     */
    public MockStateMileageDAO() {
    }

    /**
     * @{inherit-doc}
     * @see com.inthinc.pro.dao.StateMileageDAO#getFuelStateMileageByVehicle(java.lang.Integer, org.joda.time.Interval, java.lang.Boolean)
     */
    @Override
    public List<StateMileage> getFuelStateMileageByVehicle(Integer groupID, Interval interval, Boolean dotOnly) {
        return this.getFuelStateMileageByVehicleData(groupID, interval, dotOnly);
    }

    /**
     * @{inherit-doc}
     * @see com.inthinc.pro.dao.StateMileageDAO#getMileageByVehicle(java.lang.Integer, org.joda.time.Interval, java.lang.Boolean)
     */
    @Override
    public List<StateMileage> getMileageByVehicle(Integer groupID, Interval interval, Boolean dotOnly) {
        return this.getData(groupID);
    }

    /**
     * @{inherit-doc}
     * @see com.inthinc.pro.dao.StateMileageDAO#getStateMileageByGroup(java.lang.Integer, org.joda.time.Interval, java.lang.Boolean)
     */
    @Override
    public List<StateMileage> getStateMileageByGroup(Integer groupID, Interval interval, Boolean dotOnly) {
        return this.getData(groupID);
    }

    /**
     * @{inherit-doc}
     * @see com.inthinc.pro.dao.StateMileageDAO#getStateMileageByGroupAndMonth(java.lang.Integer, org.joda.time.Interval, java.lang.Boolean)
     */
    @Override
    public List<StateMileage> getStateMileageByGroupAndMonth(Integer groupID, Interval interval, Boolean dotOnly) {
        List<StateMileage> list = new ArrayList<StateMileage>();
        StateMileage bean = newInstance(MOCK_GROUP_NAME + groupID, STATE_1, "257547", 1500L);
        bean.setMonth("October");
        list.add(bean);
        bean = newInstance(MOCK_GROUP_NAME + groupID, STATE_1, "257547", 1540L);
        bean.setMonth("September");
        list.add(bean);
        bean = newInstance(MOCK_GROUP_NAME + groupID, STATE_1, "224547", 1360L);
        bean.setMonth("October");
        list.add(bean);
        return list;
    }

    /**
     * @{inherit-doc}
     * @see com.inthinc.pro.dao.StateMileageDAO#getStateMileageByVehicle(java.lang.Integer, org.joda.time.Interval, java.lang.Boolean)
     */
    @Override
    public List<StateMileage> getStateMileageByVehicle(Integer groupID, Interval interval, Boolean dotOnly) {
        List<StateMileage> list = new ArrayList<StateMileage>();        
        list.add(newInstance(MOCK_GROUP_NAME + groupID, STATE_1, "257547", 1500L));
        list.add(newInstance(MOCK_GROUP_NAME + groupID, STATE_2, "217547", 1600L));
        list.add(newInstance(MOCK_GROUP_NAME + groupID, "STATE_2", "217547", 1600L));
        list.add(newInstance(MOCK_GROUP_NAME + groupID, "STATE_3", "213547", 1602L));
        list.add(newInstance(MOCK_GROUP_NAME + groupID, "STATE_4", "217547", 1600L));
        list.add(newInstance(MOCK_GROUP_NAME + groupID, "VERY LONG STATE NAME", "21547", 1640L));
        list.add(newInstance(MOCK_GROUP_NAME + groupID, "STATE_6", "217547", 1600L));
        list.add(newInstance(MOCK_GROUP_NAME + groupID, "STATE_7", "217227", 1605L));
        list.add(newInstance(MOCK_GROUP_NAME + groupID, "STATE_8", "217547", 1200L));
        list.add(newInstance(MOCK_GROUP_NAME + groupID, "STATE_9", "237547", 1600L));
        list.add(newInstance(MOCK_GROUP_NAME + groupID, "STATE_10", "217547", 1100L));
        list.add(newInstance(MOCK_GROUP_NAME + groupID, "STATE_12", "217567", 1640L));
        list.add(newInstance(MOCK_GROUP_NAME + groupID, "STATE_11", "217347", 1260L));
        list.add(newInstance(MOCK_GROUP_NAME + groupID, "STATE_13", "217546", 1507L));
        
        return list;
    }

    /**
     * @{inherit-doc}
     * @see com.inthinc.pro.dao.StateMileageDAO#getStateMileageByVehicleRoad(java.lang.Integer, org.joda.time.Interval, java.lang.Boolean)
     */
    @Override
    public List<StateMileage> getStateMileageByVehicleRoad(Integer groupID, Interval interval, Boolean dotOnly) {
        return this.getDataBis(groupID, interval, dotOnly);
    }

    /* ^
     * |  Mocked methods
     * --------------------------------------------------------------------------------------
     * |  Helper Methods
     * v
     */
    
    private List<StateMileage> getData(Integer groupID) {
        List<StateMileage> list = new ArrayList<StateMileage>();
        
        list.add(newInstance(MOCK_GROUP_NAME + groupID, DISTANCE, "257547", 1684L));
        list.add(newInstance(MOCK_GROUP_NAME + groupID, DISTANCE, "217547", 1685L));
        list.add(newInstance(MOCK_GROUP_NAME + groupID, DISTANCE, "1575789", 1686L));
        
        return list;
    }
    
    private List<StateMileage> getDataBis(Integer groupID, Interval interval, Boolean isDotIfta) {
        // only the first call adds data
        List<StateMileage> list = new ArrayList<StateMileage>();
        
        StateMileage bean = new StateMileage();
        
        Date date = new Date();
        Calendar calendar = Calendar.getInstance(); 
        calendar.set(2010,9,10);
        date = calendar.getTime();

        if(interval.contains(date.getTime()) && 1504 == groupID ){ 
            bean.setGroupName(MOCK_PREFIX + "FLEET"); 
            bean.setVehicleName(MOCK_PREFIX + "10026217");
            bean.setStateName(MOCK_PREFIX + "Colorado");
            bean.setOnRoadFlag(true);
            bean.setMonth("February");
            bean.setMiles(3108L);
            list.add(bean);
        }
        
        calendar.set(2010,9,11);
        date = calendar.getTime();
        
        if(interval.contains(date.getTime()) && 1504 == groupID ){ 
            bean = new StateMileage();
            bean.setGroupName(MOCK_PREFIX + "FLEET"); 
            bean.setVehicleName(MOCK_PREFIX + "11077461");
            bean.setStateName(MOCK_PREFIX + "Colorado");
            bean.setOnRoadFlag(true);
            bean.setMonth("February");
            bean.setMiles(4484L);
            list.add(bean);
        }
        
        calendar.set(2010,9,12);
        date = calendar.getTime();
        
        if(interval.contains(date.getTime()) && 1504 == groupID ){ 
            bean = new StateMileage();   
            bean.setGroupName(MOCK_PREFIX + "FLEET"); 
            bean.setVehicleName(MOCK_PREFIX + "11187740");
            bean.setStateName(MOCK_PREFIX + "Colorado");
            bean.setOnRoadFlag(true);
            bean.setMonth("February");
            bean.setMiles(1817L);
            list.add(bean);
        }
        
        calendar.set(2010,9,13);
        date = calendar.getTime();
        
        if(interval.contains(date.getTime()) && 1505 == groupID  ){ 
            bean = new StateMileage();
            bean.setGroupName(MOCK_PREFIX + "FLEET - DIVISION"); 
            bean.setVehicleName(MOCK_PREFIX + "10740909");
            bean.setStateName(MOCK_PREFIX + "New Mexico");
            bean.setOnRoadFlag(false);
            bean.setMonth("February");
            bean.setMiles(827L);
            list.add(bean);  
        }
        
        calendar.set(2010,9,14);
        date = calendar.getTime();
        
        if(interval.contains(date.getTime()) && !isDotIfta && 1505 == groupID){ 
            bean = new StateMileage();
            bean.setGroupName(MOCK_PREFIX + "FLEET - DIVISION"); 
            bean.setVehicleName(MOCK_PREFIX + "12345678");
            bean.setStateName(MOCK_PREFIX + "South Dakota");
            bean.setOnRoadFlag(false);
            bean.setMonth("February");
            bean.setMiles(827L);
            list.add(bean);  
        }
        
        calendar.set(2010,9,15);
        date = calendar.getTime();
        
        if(interval.contains(date.getTime()) && !isDotIfta && 1505 == groupID){ 
            bean = new StateMileage();
            bean.setGroupName(MOCK_PREFIX + "FLEET - DIVISION"); 
            bean.setVehicleName(MOCK_PREFIX + "87654321");
            bean.setStateName(MOCK_PREFIX + "UTAH");
            bean.setOnRoadFlag(false);
            bean.setMonth("February");
            bean.setMiles(927L);
            list.add(bean);  
        }
        
        calendar.set(2010,9,15);
        date = calendar.getTime();
        
        if(interval.contains(date.getTime()) && !isDotIfta && 1506 == groupID){ 
            bean = new StateMileage();
            bean.setGroupName(MOCK_PREFIX + "FLEET - DIVISION - GOOD"); 
            bean.setVehicleName(MOCK_PREFIX + "87654320");
            bean.setStateName(MOCK_PREFIX + "UTAH");
            bean.setOnRoadFlag(true);
            bean.setMonth("February");
            bean.setMiles(1027L);
            list.add(bean);  
        }
   
        return list;
    }
    
    /* creates a new instance of StateMileage */
    private StateMileage newInstance(String groupName, String state, String vehicle, Long miles) {
        StateMileage bean = new StateMileage();
        bean.setGroupName(groupName); 
        bean.setStateName(state);
        bean.setVehicleName(vehicle); 
        bean.setMiles(miles);
        return bean;
    }

    private List<StateMileage> getFuelStateMileageByVehicleData(Integer groupID, Interval interval, Boolean dotOnly) {

        List<StateMileage> list = new ArrayList<StateMileage>();
    	
        Date date = new Date();
        Calendar calendar = Calendar.getInstance(); 
        calendar.set(2010,9,19);
        date = calendar.getTime();

        if(interval.contains(date.getTime())){ 
        	list.addAll(getFuelList(MOCK_MONTH_AUG));
        	list.addAll(getFuelList(MOCK_MONTH_SEP));
        }
		return list;
	}
    
    
    List<StateMileage> getFuelList(String month){
        List<StateMileage> list = new ArrayList<StateMileage>();
        
        String[] states = {"CO", "MT", "ND", "NM", "SD", "TX", "UT", "WY"};
        Map<String, Map<String, Long>> milesByStateMonth = getFuelMilesMap();  
        
        // For each state add a bean with the specified miles for that state and months
        for(String state : states){
        	list.add(getFuelBean(state, milesByStateMonth.get(month).get(state), month));
        }	    
	    return list;
    }
    
    private Map<String, Map<String, Long>> getFuelMilesMap() {
    	//Map<Month, Map<state, Long>>
    	Map<String, Map<String, Long>> milesByStateMonth = new HashMap<String,Map<String, Long>>();
    	Map<String, Long> mapAug = new HashMap<String, Long>();
    	Map<String, Long> mapSep = new HashMap<String, Long>();
    	
    	milesByStateMonth.put(MOCK_MONTH_AUG, mapAug);
    	milesByStateMonth.put(MOCK_MONTH_SEP, mapSep);
    	
    	mapAug.put("CO", 328L);
    	mapAug.put("NM", 664L);
    	mapSep.put("CO", 2L);
    	mapSep.put("NM", 326L);

    	return milesByStateMonth;
	}

	private StateMileage getFuelBean(String stateName, Long miles, String month){
    	StateMileage bean = new StateMileage();
        bean.setGroupName(MOCK_PREFIX + "Rockies->Grand Junction->Grand Junction PE Crews"); 
        bean.setVehicleName("10001794");
        bean.setMonth(month);
        bean.setStateName(stateName);
        bean.setMiles(miles == null ? 0L : miles);
        bean.setTruckGallons(0F);
        bean.setTrailerGallons(0F);  
        
        return bean;
    }
    
    
}
