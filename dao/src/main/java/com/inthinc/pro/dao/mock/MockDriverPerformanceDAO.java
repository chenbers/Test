package com.inthinc.pro.dao.mock;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.Interval;

import com.inthinc.pro.dao.report.DriverPerformanceDAO;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.aggregation.DriverPerformance;
import com.inthinc.pro.model.aggregation.DriverPerformanceKeyMetrics;

public class MockDriverPerformanceDAO implements DriverPerformanceDAO {

    @Override
    public List<DriverPerformance> getDriverPerformance(Integer groupID, String groupName, List<Integer> driverID, Interval queryInterval) {

        return getDriverPerformanceListForGroup(groupID, groupName, queryInterval);
    }
    @Override
    public List<DriverPerformance> getDriverPerformance(Integer groupID, String groupName, List<Integer> driverIDList, Interval interval, boolean includeInactiveDrivers,
            boolean includeZeroMilesDrivers) {
        // TODO Auto-generated method stub
        return getDriverPerformanceListForGroup(groupID, groupName, interval);
    }

    public List<DriverPerformance> getDriverPerformanceListForGroup(Integer groupID, String groupName, Interval queryInterval, boolean includeInactiveDrivers, boolean includeZeroMilesDrivers) {
     // TODO Auto-generated method stub
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

    @Override
    public List<DriverPerformanceKeyMetrics> getDriverPerformanceKeyMetricsListForGroup(Integer groupID, String divisionName, String teamName, TimeFrame timeFrame) {
        List<DriverPerformanceKeyMetrics> list = new ArrayList<DriverPerformanceKeyMetrics>();

        Integer hiIdleViolationsMinutes = 0;
        Integer totalMiles = 0;
        Integer loginCount = 0;
        Integer overallScore = 0;
        Integer loIdleViolationsMinutes = 0;
        Integer seatbeltScore = 0;
        Integer speedingScore = 0;
        String driverPosition = "Position NA";
        Integer styleScore = 0;
        Integer idleViolationsCount = 1;
        String driverName = "Driver NA";
        String groupName = "Group NA";
        list.add(new DriverPerformanceKeyMetrics(groupName , divisionName, driverName, driverPosition, loginCount, timeFrame, totalMiles, overallScore, speedingScore, styleScore, seatbeltScore, idleViolationsCount, loIdleViolationsMinutes, hiIdleViolationsMinutes));
        for (int i = 0; i < 5; i++) {
            list.add(new DriverPerformanceKeyMetrics(groupName , divisionName, driverName, driverPosition, loginCount++, timeFrame, totalMiles++, overallScore++, speedingScore++, styleScore, seatbeltScore++, idleViolationsCount++, loIdleViolationsMinutes++, hiIdleViolationsMinutes++));
        }
        return list;  
    }

    @Override
    public List<DriverPerformanceKeyMetrics> getDriverPerformanceKeyMetricsListForGroup(Integer groupID, String divisionName, String teamName, Interval interval) {
        List<DriverPerformanceKeyMetrics> list = new ArrayList<DriverPerformanceKeyMetrics>();

        Integer hiIdleViolationsMinutes = 0;
        Integer totalMiles = 0;
        Integer loginCount = 0;
        Integer overallScore = 0;
        Integer loIdleViolationsMinutes = 0;
        Integer seatbeltScore = 0;
        Integer speedingScore = 0;
        String driverPosition = "Position NA";
        Integer styleScore = 0;
        Integer idleViolationsCount = 1;
        String driverName = "Driver NA";
        String groupName = "Group NA";
        ArrayList<String> colors = new ArrayList<String>();
        colors.add("R");
        colors.add("O");
        colors.add("Y");
        colors.add("B");
        colors.add("G");
        list.add(new DriverPerformanceKeyMetrics(groupName , divisionName, driverName, driverPosition, loginCount, interval, totalMiles, overallScore, speedingScore, styleScore, seatbeltScore, idleViolationsCount, loIdleViolationsMinutes, hiIdleViolationsMinutes, " "));
        for (String color: colors){
            list.add(new DriverPerformanceKeyMetrics(groupName , divisionName, driverName, driverPosition, loginCount++, interval, totalMiles++, overallScore++, speedingScore++, styleScore, seatbeltScore++, idleViolationsCount++, loIdleViolationsMinutes++, hiIdleViolationsMinutes++, color));
        }
        return list;
    }

    @Override
    public List<DriverPerformanceKeyMetrics> getDriverPerformanceKeyMetricsListForGroup(Integer groupID, String divisionName, String teamName, TimeFrame timeFrame, Interval interval) {
        if(interval != null)
            return getDriverPerformanceKeyMetricsListForGroup(groupID, divisionName, teamName, interval);
        else
            return getDriverPerformanceKeyMetricsListForGroup(groupID, divisionName, teamName, timeFrame);
    }

    @Override
    public List<DriverPerformanceKeyMetrics> getDriverPerformanceKeyMetricsListForGroup(Integer groupID, String divisionName, String teamName, TimeFrame timeFrame, boolean includeInactiveDrivers,
            boolean includeZeroMilesDrivers) {
        return getDriverPerformanceKeyMetricsListForGroup(groupID, divisionName, teamName, timeFrame);
    }

    @Override
    public List<DriverPerformanceKeyMetrics> getDriverPerformanceKeyMetricsListForGroup(Integer groupID, String divisionName, String teamName, Interval interval, boolean includeInactiveDrivers,
            boolean includeZeroMilesDrivers) {
        return getDriverPerformanceKeyMetricsListForGroup(groupID, divisionName, teamName, interval);
    }

    @Override
    public List<DriverPerformanceKeyMetrics> getDriverPerformanceKeyMetricsListForGroup(Integer groupID, String divisionName, String teamName, TimeFrame timeFrame, Interval interval,
            boolean includeInactiveDrivers, boolean includeZeroMilesDrivers) {
        return getDriverPerformanceKeyMetricsListForGroup(groupID, divisionName, teamName, timeFrame, interval);
    }
}
