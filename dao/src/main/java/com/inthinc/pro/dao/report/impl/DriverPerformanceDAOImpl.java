package com.inthinc.pro.dao.report.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.joda.time.DateTimeZone;
import org.joda.time.Interval;

import com.inthinc.pro.dao.EventDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.VehiclePerformanceDAO;
import com.inthinc.pro.dao.report.DriverPerformanceDAO;
import com.inthinc.pro.dao.report.GroupReportDAO;
import com.inthinc.pro.dao.util.DateUtil;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.aggregation.DriverPerformance;
import com.inthinc.pro.model.aggregation.DriverPerformanceKeyMetrics;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;
import com.inthinc.pro.model.aggregation.Score;
import com.inthinc.pro.model.aggregation.VehiclePerformance;
import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.NoteType;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;


public class DriverPerformanceDAOImpl implements DriverPerformanceDAO {

    private GroupDAO groupDAO;
    private GroupReportDAO groupReportDAO;
    private EventDAO eventDAO;
    private VehiclePerformanceDAO vehiclePerformanceDAO;
    
    private static List<NoteType> loginNoteType = new ArrayList<NoteType>();
    static {
        // tiwipro
        loginNoteType.add(NoteType.NEW_DRIVER);
        // waysmart
        loginNoteType.add(NoteType.INVALID_DRIVER);
        loginNoteType.add(NoteType.INVALID_OCCUPANT);
        loginNoteType.add(NoteType.VALID_OCCUPANT);
        loginNoteType.add(NoteType.NEWDRIVER_HOSRULE);
    }    

    @Override
    public List<DriverPerformance> getDriverPerformance(Integer groupID, String groupName, List<Integer> driverIDList, Interval interval) {
        return getFilteredDriverPerformanceListForGroup(groupID, groupName, driverIDList, interval);
    }

    @Override
    public List<DriverPerformance> getDriverPerformanceListForGroup(Integer groupID, String groupName, Interval interval) {
        return getFilteredDriverPerformanceListForGroup(groupID, groupName, null, interval);
    }

    private List<DriverPerformance> getFilteredDriverPerformanceListForGroup(Integer groupID, String groupName, List<Integer> driverIDList, Interval interval) {
	
        List<DriverVehicleScoreWrapper> scoreList = groupReportDAO.getDriverScores(groupID, interval, null);
        
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
            dp.setTotalMiles(s.getOdometer6() == null ? 0 : s.getOdometer6().intValue());
            dp.setSpeedCount0to7Over(s.getSpeedEvents1To7MphOver() == null ? 0 : s.getSpeedEvents1To7MphOver().intValue());
            dp.setSpeedCount8to14Over(s.getSpeedEvents8To14MphOver() == null ? 0 : s.getSpeedEvents8To14MphOver().intValue());
            dp.setSpeedCount15Over(s.getSpeedEvents15PlusMphOver() == null ? 0 : s.getSpeedEvents15PlusMphOver().intValue());
            dp.setTotalIdleTime(s.getIdleTotal() == null ?0:s.getIdleTotal());
            dp.setIdleLo(s.getIdleLo() == null ?0:s.getIdleLo());
            
            List<VehiclePerformance> vehiclePerformanceBreakdown = vehiclePerformanceDAO.getVehiclePerformance(score.getDriver().getDriverID(), interval);
            if (vehiclePerformanceBreakdown != null && vehiclePerformanceBreakdown.size() > 0) 
                dp.setVehiclePerformanceBreakdown(vehiclePerformanceBreakdown);
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

    public GroupDAO getGroupDAO() {
        return groupDAO;
    }

    public void setGroupDAO(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
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
            Integer totalMiles = s.getOdometer6() == null ? 0 : s.getOdometer6().intValue();
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
    public List<DriverPerformanceKeyMetrics> getDriverPerformanceKeyMetricsListForGroup(Integer groupID, String divisionName, String teamName,TimeFrame timeFrame, Interval interval) {
    	List<DriverPerformanceKeyMetrics> driverPerformanceList = new ArrayList<DriverPerformanceKeyMetrics>();
		
		Group group = groupDAO.findByID(groupID);
		GroupHierarchy gh = new GroupHierarchy(groupDAO.getGroupHierarchy(group.getAccountID(), groupID));
        List<DriverVehicleScoreWrapper> scoreList = groupReportDAO.getDriverScores(groupID, interval.getStart(), interval.getEnd().minusDays(1), gh);
        
        if (scoreList == null || scoreList.isEmpty())
            return driverPerformanceList;
        for (DriverVehicleScoreWrapper score : scoreList) {
            DriverPerformanceKeyMetrics dp = new DriverPerformanceKeyMetrics();
            dp.setDriverName(score.getDriver().getPerson().getFullName());
            dp.setDriverPosition(score.getDriver().getPerson().getTitle());
            dp.setGroupName(divisionName);
            dp.setTeamName(teamName);
            dp.setTimeFrame(timeFrame);
            dp.setInterval(interval);
            Score s = score.getScore();
            dp.setLoginCount(getDriverLoginCount(score.getDriver().getDriverID(), score.getDriver().getPerson().getTimeZone(), interval));
            dp.setTotalMiles(s.getOdometer6() == null ? 0 : s.getOdometer6().intValue());
            dp.setOverallScore(s.getOverall()==null ? -1 : s.getOverall().intValue());
            dp.setSpeedingScore(s.getSpeeding()==null ? -1 : s.getSpeeding().intValue());
            dp.setStyleScore(s.getDrivingStyle()==null ? -1 : s.getDrivingStyle().intValue());
            dp.setSeatbeltScore(s.getSeatbelt()==null ? -1 : s.getSeatbelt().intValue());
            // note: lo and hi idle event counts are the same and represent the total violations
            dp.setIdleViolationsCount(s.getIdleLoEvents() == null ? 0 : s.getIdleLoEvents().intValue());
            dp.setLoIdleViolationsMinutes(s.getIdleLo() == null ? 0 : s.getIdleLo().intValue());
            dp.setHiIdleViolationsMinutes(s.getIdleHi() == null ? 0 : s.getIdleHi().intValue());
            driverPerformanceList.add(dp);
        }
        return driverPerformanceList;
    }
    @Override
    public List<DriverPerformanceKeyMetrics> getDriverPerformanceKeyMetricsListForGroup(Integer groupID, String divisionName, String teamName, TimeFrame timeFrame) {
        return getDriverPerformanceKeyMetricsListForGroup(groupID, divisionName, teamName, timeFrame, null);
    }
    @Override
    public List<DriverPerformanceKeyMetrics> getDriverPerformanceKeyMetricsListForGroup(Integer groupID, String divisionName, String teamName, Interval interval) {
        return getDriverPerformanceKeyMetricsListForGroup(groupID, divisionName, teamName, null, interval);
    }

        
    private Integer getDriverLoginCount(Integer driverID, TimeZone timeZone, Interval interval) {
        
        Interval tzInterval = DateUtil.getIntervalInTimeZone(interval, DateTimeZone.forTimeZone(timeZone));

        List<Event> logins = eventDAO.getEventsForDriver(driverID, tzInterval.getStart().toDate(), tzInterval.getEnd().minusMillis(1).toDate(), loginNoteType, 1); 
        return logins == null ? 0 : logins.size();
    }

    public EventDAO getEventDAO() {
        return eventDAO;
    }

    public void setEventDAO(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }

    public VehiclePerformanceDAO getVehiclePerformanceDAO() {
        return vehiclePerformanceDAO;
    }

    public void setVehiclePerformanceDAO(VehiclePerformanceDAO vehiclePerformanceDAO) {
        this.vehiclePerformanceDAO = vehiclePerformanceDAO;
    }


}
