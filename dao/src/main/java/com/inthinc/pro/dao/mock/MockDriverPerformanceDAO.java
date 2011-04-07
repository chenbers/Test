package com.inthinc.pro.dao.mock;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.Interval;

import com.inthinc.pro.dao.DriverPerformanceDAO;
import com.inthinc.pro.model.aggregation.DriverPerformance;

public class MockDriverPerformanceDAO implements DriverPerformanceDAO {

    @Override
    public List<DriverPerformance> getDriverPerformance(Integer groupID, String groupName, List<Integer> driverID, Interval queryInterval) {

        return getDriverPerformanceListForGroup(groupID, groupName, queryInterval);
    }

    @Override
    public List<DriverPerformance> getDriverPerformanceListForGroup(Integer groupID, String groupName, Interval queryInterval) {
        List<DriverPerformance> list = new ArrayList<DriverPerformance>();

        list.add(new DriverPerformance(groupName, 100, "Driver NA", "Emp NA", -1, 0, 0,0,0,0,0));
        for (int i = 0; i < 5; i++) {
            list.add(new DriverPerformance(groupName, i, "Driver " + i, "Emp " + i, i*10+1, i*1000, i,i,i,i,i));
        }
        return list;
    }
}
