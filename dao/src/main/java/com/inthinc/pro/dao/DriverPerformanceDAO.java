package com.inthinc.pro.dao;

import java.util.List;

import org.joda.time.Interval;

import com.inthinc.pro.model.aggregation.DriverPerformance;

public interface DriverPerformanceDAO {
    
    public List<DriverPerformance> getDriverPerformance(Integer groupID, String groupName, List<Integer> driverID, Interval queryInterval);
    public List<DriverPerformance> getDriverPerformanceListForGroup(Integer groupID, String groupName, Interval queryInterval);

}
