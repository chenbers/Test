package com.inthinc.pro.reports.dao.mock;

import java.util.ArrayList;
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

    private Map<Integer, List<StateMileage>> mileageByVehicle;

    /**
     * Default constructor.
     */
    public MockStateMileageDAO() {
    // TODO Auto-generated constructor stub
    }

    /**
     * @{inherit-doc}
     * @see com.inthinc.pro.dao.StateMileageDAO#getFuelStateMileageByVehicle(java.lang.Integer, org.joda.time.Interval, java.lang.Boolean)
     */
    @Override
    public List<StateMileage> getFuelStateMileageByVehicle(Integer groupID, Interval interval, Boolean dotOnly) {
        // TODO Add method body
        return null;
    }

    /**
     * @{inherit-doc}
     * @see com.inthinc.pro.dao.StateMileageDAO#getMileageByVehicle(java.lang.Integer, org.joda.time.Interval, java.lang.Boolean)
     */
    @Override
    public List<StateMileage> getMileageByVehicle(Integer groupID, Interval interval, Boolean dotOnly) {
        return this.getMileageByVehicleData(groupID);
    }

    /**
     * @{inherit-doc}
     * @see com.inthinc.pro.dao.StateMileageDAO#getStateMileageByGroup(java.lang.Integer, org.joda.time.Interval, java.lang.Boolean)
     */
    @Override
    public List<StateMileage> getStateMileageByGroup(Integer groupID, Interval interval, Boolean dotOnly) {
        // TODO Add method body
        return null;
    }

    /**
     * @{inherit-doc}
     * @see com.inthinc.pro.dao.StateMileageDAO#getStateMileageByGroupAndMonth(java.lang.Integer, org.joda.time.Interval, java.lang.Boolean)
     */
    @Override
    public List<StateMileage> getStateMileageByGroupAndMonth(Integer groupID, Interval interval, Boolean dotOnly) {
        // TODO Add method body
        return null;
    }

    /**
     * @{inherit-doc}
     * @see com.inthinc.pro.dao.StateMileageDAO#getStateMileageByVehicle(java.lang.Integer, org.joda.time.Interval, java.lang.Boolean)
     */
    @Override
    public List<StateMileage> getStateMileageByVehicle(Integer groupID, Interval interval, Boolean dotOnly) {
        // TODO Add method body
        return null;
    }

    /**
     * @{inherit-doc}
     * @see com.inthinc.pro.dao.StateMileageDAO#getStateMileageByVehicleRoad(java.lang.Integer, org.joda.time.Interval, java.lang.Boolean)
     */
    @Override
    public List<StateMileage> getStateMileageByVehicleRoad(Integer groupID, Interval interval, Boolean dotOnly) {
        // TODO Add method body
        return null;
    }

    private List<StateMileage> getMileageByVehicleData(Integer groupID) {
        if (this.mileageByVehicle == null) {
            // only the first call adds data
            mileageByVehicle = new HashMap<Integer, List<StateMileage>>();
            List<StateMileage> list = new ArrayList<StateMileage>();
            
            StateMileage bean = new StateMileage();
            bean.setGroupName(groupID.toString());
            bean.setVehicleName("257547"); bean.setMiles(1684L);
            list.add(bean);
            
            bean = new StateMileage();
            bean.setGroupName(groupID.toString());
            bean.setVehicleName("217547"); bean.setMiles(1685L);
            list.add(bean);
            
            bean = new StateMileage();
            bean.setGroupName(groupID.toString());
            bean.setVehicleName("1575789"); bean.setMiles(1686L);
            list.add(bean);
            
            mileageByVehicle.put(groupID, list);
        }
        return this.mileageByVehicle.get(groupID);
    }
}
