package com.inthinc.pro.reports.performance;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import mockit.Mocked;
import mockit.NonStrictExpectations;

import org.joda.time.Interval;
import org.junit.Before;
import org.junit.Test;

import com.inthinc.pro.dao.report.DriverPerformanceDAO;
import com.inthinc.pro.dao.report.GroupReportDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.aggregation.DriverPerformance;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;
import com.inthinc.pro.model.aggregation.Score;
import com.inthinc.pro.reports.BaseUnitTest;
import com.inthinc.pro.reports.FormatType;
import com.inthinc.pro.reports.ReportCriteria;


public class DriverCoachingReportCriteriaTest extends BaseUnitTest {
  
    
    @Mocked
    private DriverPerformanceDAO driverPerformanceDAO;
    
    @Mocked
    private GroupReportDAO groupReportDAO;
    
    private List<DriverVehicleScoreWrapper> driverVehicleScoreWrappers;
    
    private List<DriverPerformance> driverPerformances;
    
    @Before
    public void setupTests(){
        driverPerformances = new ArrayList<DriverPerformance>();
        
        /* Setup the mock Driver Performance Bean */
        DriverPerformance driverPerformance = new DriverPerformance();
        driverPerformance.setDriverName("Test Driver");
        driverPerformance.setDriverID(1);
        driverPerformance.setTotalIdleTime(123123);
        driverPerformance.setHardAccelCount(3);
        driverPerformance.setHardBrakeCount(3);
        driverPerformance.setHardTurnCount(3);
        driverPerformance.setHardVerticalCount(3);
        driverPerformance.setSpeedCount0to7Over(2);
        driverPerformance.setSpeedCount15Over(2);
        driverPerformance.setSpeedCount8to14Over(2);
        driverPerformance.setSeatbeltCount(3);
        driverPerformance.setScore(3);
        driverPerformances.add(driverPerformance);
        
        
        driverVehicleScoreWrappers = new ArrayList<DriverVehicleScoreWrapper>();
        
        /* Setup the mock Driver Score Bean */
        DriverVehicleScoreWrapper driverVehicleScoreWrapper1 = new DriverVehicleScoreWrapper();
        Driver driver = new Driver();
        driver.setDriverID(1);
        driverVehicleScoreWrapper1.setDriver(driver);
        Score score = new Score();
        score.setOverall(30);
        driverVehicleScoreWrapper1.setScore(score);
        driverVehicleScoreWrappers.add(driverVehicleScoreWrapper1);
    }
    
    
    @Test
    public void testBuildDriverCoachingReportCriteria(){
        new NonStrictExpectations() {{
            driverPerformanceDAO.getDriverPerformanceListForGroup(anyInt,anyString,(Interval)any);
            returns(driverPerformances);
        }};
        
        new NonStrictExpectations() {{
            groupReportDAO.getDriverScores(anyInt, (Interval)any);
            returns(driverVehicleScoreWrappers);
        }};
        
//        DriverCoachingReportCriteria.Builder reportCriteriaBuilder = new DriverCoachingReportCriteria.Builder(groupReportDAO,driverPerformanceDAO,1,TimeFrame.PAST_SEVEN_DAYS.getInterval());
//        reportCriteriaBuilder.setLocale(Locale.US);
//        List<ReportCriteria> reportCriterias = reportCriteriaBuilder.build();
//        dump("DriverCoaching", 2, reportCriterias, FormatType.PDF);
    }

}
