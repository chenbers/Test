package com.inthinc.pro.dao.hessian.report;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.time.Interval;

import com.inthinc.pro.dao.DriveTimeDAO;
import com.inthinc.pro.dao.DriverPerformanceDAO;
import com.inthinc.pro.dao.report.GroupReportDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.aggregation.DriverPerformance;
import com.inthinc.pro.model.aggregation.DriverPerformanceKeyMetrics;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;
import com.inthinc.pro.model.aggregation.Score;

public class DriverPerformanceReportHessianDAO implements DriverPerformanceDAO {

    GroupReportDAO groupReportDAO;
    DriveTimeDAO driveTimeDAO;
    

    @Override
    public List<DriverPerformance> getDriverPerformance(Integer groupID, String groupName, List<Integer> driverIDList, Interval interval) {
        return getFilteredDriverPerformanceListForGroup(groupID, groupName, driverIDList, interval);
    }

    @Override
    public List<DriverPerformance> getDriverPerformanceListForGroup(Integer groupID, String groupName, Interval interval) {
        return getFilteredDriverPerformanceListForGroup(groupID, groupName, null, interval);
    }

    private List<DriverPerformance> getFilteredDriverPerformanceListForGroup(Integer groupID, String groupName, List<Integer> driverIDList, Interval interval) {
        List<DriverVehicleScoreWrapper> scoreList = groupReportDAO.getDriverScores(groupID, interval);
        
        List<DriverPerformance> driverPerformanceList = new ArrayList<DriverPerformance>();
        if (scoreList == null || scoreList.isEmpty())
            return driverPerformanceList;
        
        for (DriverVehicleScoreWrapper score : scoreList) {
            if (!includeDriver(score, driverIDList, (driverIDList != null)))
                continue;
            DriverPerformance dp = new DriverPerformance();
            dp.setDriverID(score.getDriver().getDriverID());
            dp.setDriverName(score.getDriver().getPerson().getFullName());
            dp.setEmployeeID(score.getDriver().getPerson().getEmpid());
            dp.setGroupName(groupName);
            Score s = score.getScore();
            dp.setHardAccelCount(s.getAggressiveAccelEvents() == null ? 0 : s.getAggressiveAccelEvents().intValue());
            dp.setHardBrakeCount(s.getAggressiveBrakeEvents() == null ? 0 : s.getAggressiveBrakeEvents().intValue());
            dp.setHardTurnCount((s.getAggressiveLeftEvents() == null ? 0 : s.getAggressiveLeftEvents().intValue()) + 
                                (s.getAggressiveRightEvents() == null ? 0 : s.getAggressiveRightEvents().intValue()));
            dp.setHardVerticalCount(s.getAggressiveBumpEvents() == null ? 0 : s.getAggressiveBumpEvents().intValue());
            dp.setScore(s.getOverall()==null ? -1 : s.getOverall().intValue());
            dp.setSeatbeltCount(s.getSeatbeltEvents() == null ? 0 : s.getSeatbeltEvents().intValue());
            dp.setTotalMiles(s.getEndingOdometer() == null || s.getStartingOdometer() == null ? 0 : s.getEndingOdometer().intValue() - s.getStartingOdometer().intValue());
            dp.setSpeedCount0to7Over(s.getSpeedEvents1To7MphOver() == null ? 0 : s.getSpeedEvents1To7MphOver().intValue());
            dp.setSpeedCount8to14Over(s.getSpeedEvents8To14MphOver() == null ? 0 : s.getSpeedEvents8To14MphOver().intValue());
            dp.setSpeedCount15Over(s.getSpeedEvents15PlusMphOver() == null ? 0 : s.getSpeedEvents15PlusMphOver().intValue());
            driverPerformanceList.add(dp);
        }
        
        return driverPerformanceList;
    }

    public GroupReportDAO getGroupReportDAO() {
        return groupReportDAO;
    }

    public void setGroupReportDAO(GroupReportDAO groupReportDAO) {
        this.groupReportDAO = groupReportDAO;
    }

    private boolean includeDriver(DriverVehicleScoreWrapper scoreWrapper, List<Integer> driverIDList, boolean individualDrivers) 
    {
        if (scoreWrapper.getScore() == null) {
            return false;
        }
        Driver driver = scoreWrapper.getDriver();
        if (driver == null)
            return false;

        // do not include drivers with no score or no miles driven when getting for individual drivers
        if (individualDrivers) {
            Score s = scoreWrapper.getScore();
            Integer totalMiles = s.getEndingOdometer() == null || s.getStartingOdometer() == null ? 0 : s.getEndingOdometer().intValue() - s.getStartingOdometer().intValue();
            if (totalMiles.intValue() == 0)
                return false;
            for (Integer driverID : driverIDList) 
                if (driver.getDriverID().equals(driverID))
                    return true;
        }
        else {
            return true;
        }
        
        
        return false;
    }

    @Override
    public List<DriverPerformanceKeyMetrics> getDriverPerformanceKeyMetricsListForGroup(Integer groupID, String divisionName, String teamName, TimeFrame timeFrame) {
        List<DriverPerformanceKeyMetrics> driverPerformanceList = new ArrayList<DriverPerformanceKeyMetrics>();
        Interval interval = timeFrame.getInterval();
        List<DriverVehicleScoreWrapper> scoreList = groupReportDAO.getDriverScores(groupID, interval);
        
        if (scoreList == null || scoreList.isEmpty())
            return driverPerformanceList;
        
        Map<Integer, Integer> driverLoginCountMap = driveTimeDAO.getDriverLoginCountsForGroup(groupID, interval);
        for (DriverVehicleScoreWrapper score : scoreList) {
            DriverPerformanceKeyMetrics dp = new DriverPerformanceKeyMetrics();
            dp.setDriverName(score.getDriver().getPerson().getFullName());
            dp.setDriverPosition(score.getDriver().getPerson().getTitle());
            dp.setGroupName(divisionName);
            dp.setTeamName(teamName);
            dp.setTimeFrame(timeFrame);
            Score s = score.getScore();
            Integer loginCount = driverLoginCountMap.get(score.getDriver().getDriverID());
            dp.setLoginCount(loginCount == null ? 0 : loginCount);
            dp.setTotalMiles(s.getEndingOdometer() == null || s.getStartingOdometer() == null ? 0 : s.getEndingOdometer().intValue() - s.getStartingOdometer().intValue());
            dp.setOverallScore(s.getOverall()==null ? -1 : s.getOverall().intValue());
            dp.setSpeedingScore(s.getSpeeding()==null ? -1 : s.getSpeeding().intValue());
            dp.setStyleScore(s.getDrivingStyle()==null ? -1 : s.getDrivingStyle().intValue());
            dp.setSeatbeltScore(s.getSeatbelt()==null ? -1 : s.getSeatbelt().intValue());
            dp.setLoIdleViolationsCount(s.getIdleLoEvents() == null ? 0 : s.getIdleLoEvents().intValue());
            dp.setHiIdleViolationsCount(s.getIdleHiEvents() == null ? 0 : s.getIdleHiEvents().intValue()); 
            dp.setLoIdleViolationsMinutes(s.getIdleLo() == null ? 0 : s.getIdleLo().intValue());
            dp.setHiIdleViolationsMinutes(s.getIdleHi() == null ? 0 : s.getIdleHi().intValue());
            driverPerformanceList.add(dp);
        }
        return driverPerformanceList;
    }


    public DriveTimeDAO getDriveTimeDAO() {
        return driveTimeDAO;
    }

    public void setDriveTimeDAO(DriveTimeDAO driveTimeDAO) {
        this.driveTimeDAO = driveTimeDAO;
    }


}
