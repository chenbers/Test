package com.inthinc.pro.reports.dao.mock;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.joda.time.Interval;

import com.inthinc.pro.dao.StateMileageDAO;
import com.inthinc.pro.model.StateMileage;
import com.inthinc.pro.reports.ifta.model.StateMileageByVehicleRoadStatus;

/**
 * Mock for StateMileageDAO.
 */
public class MockStateMileageDAO implements StateMileageDAO {
    private static final String DISTANCE = "Distance";
    private static final String STATE_1 = "Florida";
    private static final String STATE_2 = "Ohio";
    private static final String MOCK_GROUP_NAME = "Stub Group Name ";
    private static final String MOCK_PREFIX = "STUB ";

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
        return this.getData(groupID);
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
        return this.getData(groupID);
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

        if(interval.contains(date.getTime()) && !isDotIfta){ 
            bean.setGroupName(MOCK_PREFIX + "Rockies->Grand Junction->Grand Junction Maintenance Crews" + groupID); 
            bean.setVehicleName(MOCK_PREFIX + "10026217");
            bean.setStateName(MOCK_PREFIX + "Colorado");
            bean.setOnRoadFlag(true);
            bean.setMonth("February");
            bean.setMiles(3108L);
            list.add(bean);
        }
        
        calendar.set(2010,9,11);
        date = calendar.getTime();
        
        if(interval.contains(date.getTime()) && !isDotIfta){ 
            bean = new StateMileage();
            bean.setGroupName(MOCK_PREFIX + "Rockies->Grand Junction->Grand Junction Maintenance Crews" + groupID); 
            bean.setVehicleName(MOCK_PREFIX + "11077461");
            bean.setStateName(MOCK_PREFIX + "Colorado");
            bean.setOnRoadFlag(true);
            bean.setMonth("February");
            bean.setMiles(4484L);
            list.add(bean);
        }
        
        calendar.set(2010,9,12);
        date = calendar.getTime();
        
        if(interval.contains(date.getTime()) && !isDotIfta){ 
            bean = new StateMileage();   
            bean.setGroupName(MOCK_PREFIX + "Rockies->Grand Junction->Grand Junction Maintenance Crews" + groupID); 
            bean.setVehicleName(MOCK_PREFIX + "11187740");
            bean.setStateName(MOCK_PREFIX + "Colorado");
            bean.setOnRoadFlag(true);
            bean.setMonth("February");
            bean.setMiles(1817L);
            list.add(bean);
        }
        
        calendar.set(2010,9,13);
        date = calendar.getTime();
        
        if(interval.contains(date.getTime()) && !isDotIfta){ 
            bean = new StateMileage();
            bean.setGroupName(MOCK_PREFIX + "Rockies->Grand Junction->Grand Junction Maintenance Crews->Grand Junction E-Tech Crew" + groupID); 
            bean.setVehicleName(MOCK_PREFIX + "10740909");
            bean.setStateName(MOCK_PREFIX + "New Mexico");
            bean.setOnRoadFlag(false);
            bean.setMonth("February");
            bean.setMiles(827L);
            list.add(bean);  
        }
        
        calendar.set(2010,9,14);
        date = calendar.getTime();
        
        if(interval.contains(date.getTime()) && isDotIfta){ 
            bean = new StateMileage();
            bean.setGroupName(MOCK_PREFIX + "Pro->Small Junction->Small Junction Improvememt Teams->Small Junction Z-Tech Team" + groupID); 
            bean.setVehicleName(MOCK_PREFIX + "12345678");
            bean.setStateName(MOCK_PREFIX + "South Dakota");
            bean.setOnRoadFlag(false);
            bean.setMonth("February");
            bean.setMiles(827L);
            list.add(bean);  
        }
        
        calendar.set(2010,9,15);
        date = calendar.getTime();
        
        if(interval.contains(date.getTime()) && isDotIfta){ 
            bean = new StateMileage();
            bean.setGroupName(MOCK_PREFIX + "Pro->Small Junction->Small Junction Improvememt Teams -> W-Team" + groupID); 
            bean.setVehicleName(MOCK_PREFIX + "87654321");
            bean.setStateName(MOCK_PREFIX + "UTAH");
            bean.setOnRoadFlag(false);
            bean.setMonth("February");
            bean.setMiles(827L);
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
    public List<StateMileageByVehicleRoadStatus> getStateMileageByVehicleRoadStatus(Integer groupID, Interval interval, boolean dotOnly) {
        List<StateMileageByVehicleRoadStatus> list = new ArrayList<StateMileageByVehicleRoadStatus>();
        
        StateMileageByVehicleRoadStatus bean = new StateMileageByVehicleRoadStatus();
        bean.setGroupName(groupID.toString());
        bean.setVehicle("257547"); 
        list.add(bean);
        
        return list;
    }
}
