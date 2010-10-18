package com.inthinc.pro.reports.dao.mock;

import java.util.ArrayList;
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
    private static final String MOCK_prefix = "STUB ";

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
        return this.getDataBis(groupID);
    }

    private List<StateMileage> getData(Integer groupID) {
        List<StateMileage> list = new ArrayList<StateMileage>();
        
        list.add(newInstance(MOCK_GROUP_NAME + groupID, DISTANCE, "257547", 1684L));
        list.add(newInstance(MOCK_GROUP_NAME + groupID, DISTANCE, "217547", 1685L));
        list.add(newInstance(MOCK_GROUP_NAME + groupID, DISTANCE, "1575789", 1686L));
        
        return list;
    }
    
    private List<StateMileage> getDataBis(Integer groupID) {
        // only the first call adds data
        List<StateMileage> list = new ArrayList<StateMileage>();
        
        StateMileage bean = new StateMileage();
        bean.setGroupName(MOCK_prefix + "Rockies->Grand Junction->Grand Junction Maintenance Crews"); 
        bean.setVehicleName(MOCK_prefix + "10026217");
        bean.setStateName(MOCK_prefix + "Colorado");
        bean.setOnRoadFlag(true);
        bean.setMonth("February");
        bean.setMiles(3108L);
        list.add(bean);
        
        bean = new StateMileage();
        bean.setGroupName(MOCK_prefix + "Rockies->Grand Junction->Grand Junction Maintenance Crews"); 
        bean.setVehicleName(MOCK_prefix + "11077461");
        bean.setStateName(MOCK_prefix + "Colorado");
        bean.setOnRoadFlag(true);
        bean.setMonth("February");
        bean.setMiles(4484L);
        list.add(bean);
        
        bean = new StateMileage();
        bean.setGroupName(MOCK_prefix + "Rockies->Grand Junction->Grand Junction Maintenance Crews"); 
        bean.setVehicleName(MOCK_prefix + "11187740");
        bean.setStateName(MOCK_prefix + "Colorado");
        bean.setOnRoadFlag(true);
        bean.setMonth("February");
        bean.setMiles(1817L);
        list.add(bean);
        
        bean = new StateMileage();
        bean.setGroupName(MOCK_prefix + "Rockies->Grand Junction->Grand Junction Maintenance Crews->Grand Junction E-Tech Crew"); 
        bean.setVehicleName(MOCK_prefix + "10740909");
        bean.setStateName(MOCK_prefix + "New Mexico");
        bean.setOnRoadFlag(false);
        bean.setMonth("February");
        bean.setMiles(827L);
        list.add(bean);  
   
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
