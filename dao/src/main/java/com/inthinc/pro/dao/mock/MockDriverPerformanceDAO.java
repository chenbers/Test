package com.inthinc.pro.dao.mock;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.Interval;

import com.inthinc.pro.dao.DriverPerformanceDAO;
import com.inthinc.pro.model.aggregation.DriverPerformance;

public class MockDriverPerformanceDAO implements DriverPerformanceDAO {

    @Override
    public DriverPerformance getDriverPerformance(Integer driverID, Interval queryInterval) {
       return new DriverPerformance("Group", driverID, "Driver " + driverID, "Emp " + driverID, 25, 1000, 1,2,3,4,22);
    }

    @Override
    public List<DriverPerformance> getDriverPerformanceListForGroup(Integer groupID, Interval queryInterval) {
        List<DriverPerformance> list = new ArrayList<DriverPerformance>();

        list.add(new DriverPerformance("Group", 100, "Driver NA", "Emp NA", -1, 0, 0,0,0,0,0));
        for (int i = 0; i < 5; i++) {
            list.add(new DriverPerformance("Group", i, "Driver " + i, "Emp " + i, i*10+1, i*1000, i,i,i,i,i));
        }
        return list;
    }
}
