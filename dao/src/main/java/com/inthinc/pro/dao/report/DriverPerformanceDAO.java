package com.inthinc.pro.dao.report;

import java.util.List;

import org.joda.time.Interval;

import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.aggregation.DriverPerformance;
import com.inthinc.pro.model.aggregation.DriverPerformanceKeyMetrics;

public interface DriverPerformanceDAO {
    
    public List<DriverPerformance> getDriverPerformance(Integer groupID, String groupName, List<Integer> driverID, Interval queryInterval);
    public List<DriverPerformance> getDriverPerformanceListForGroup(Integer groupID, String groupName, Interval queryInterval);
    public List<DriverPerformanceKeyMetrics> getDriverPerformanceKeyMetricsListForGroup(Integer groupID, String divisionName, String teamName, TimeFrame timeFrame);
    public List<DriverPerformanceKeyMetrics> getDriverPerformanceKeyMetricsListForGroup(Integer groupID, String divisionName, String teamName, Interval interval);
    public List<DriverPerformanceKeyMetrics> getDriverPerformanceKeyMetricsListForGroup(Integer groupID, String divisionName, String teamName, TimeFrame timeFrame, Interval interval);
    public List<DriverPerformanceKeyMetrics> getDriverPerformanceKeyMetricsListForGroup(Integer groupID, String divisionName, String teamName, TimeFrame timeFrame, boolean includeInactiveDrivers, boolean includeZeroMilesDrivers);
    public List<DriverPerformanceKeyMetrics> getDriverPerformanceKeyMetricsListForGroup(Integer groupID, String divisionName, String teamName, Interval interval, boolean includeInactiveDrivers, boolean includeZeroMilesDrivers);
    public List<DriverPerformanceKeyMetrics> getDriverPerformanceKeyMetricsListForGroup(Integer groupID, String divisionName, String teamName, TimeFrame timeFrame, Interval interval, boolean includeInactiveDrivers, boolean includeZeroMilesDrivers);
}
