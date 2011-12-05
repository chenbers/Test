package com.inthinc.pro.reports.performance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.Test;

import com.inthinc.pro.dao.DriverPerformanceDAO;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.aggregation.DriverPerformance;
import com.inthinc.pro.model.aggregation.DriverPerformanceKeyMetrics;
import com.inthinc.pro.reports.FormatType;
import com.inthinc.pro.reports.ReportType;


public class DriverPerformanceReportCriteriaDataTest extends BasePerformanceUnitTest {
    
    @Test
    public void individualDriverTestrgy() {
        
        DriverPerformanceReportCriteria criteria = new DriverPerformanceReportCriteria(ReportType.DRIVER_PERFORMANCE_INDIVIDUAL, Locale.US);
        Interval interval = initInterval();

        criteria.setDriverPerformanceDAO(new MockDriverPerformanceDAO(interval));
        List<Integer> idList = new ArrayList<Integer>();
        idList.add(Integer.valueOf(4));
        criteria.init(getMockGroupHierarchy(), GROUP_ID, idList, initInterval(),true);
        
        dump("IndividualDriverPerformance_ryg", 1, criteria.getCriteriaList().get(0), FormatType.PDF);
        dump("IndividualDriverPerformance_ryg", 1, criteria.getCriteriaList().get(0), FormatType.HTML);

    }
    @Test
    public void teamDriverTestryg() {
        
        DriverPerformanceReportCriteria criteria = new DriverPerformanceReportCriteria(ReportType.DRIVER_PERFORMANCE_TEAM, Locale.US);
        Interval interval = initInterval();

        criteria.setDriverPerformanceDAO(new MockDriverPerformanceDAO(interval));
        criteria.init(getMockGroupHierarchy(), GROUP_ID, initInterval(), true);
        
        dump("TeamDriverPerformance_ryg", 1, criteria, FormatType.PDF);
        dump("TeamDriverPerformance_ryg", 1, criteria, FormatType.HTML);

    }
    @Test
    public void individualDriverTest() {
        
        DriverPerformanceReportCriteria criteria = new DriverPerformanceReportCriteria(ReportType.DRIVER_PERFORMANCE_INDIVIDUAL, Locale.US);
        Interval interval = initInterval();

        criteria.setDriverPerformanceDAO(new MockDriverPerformanceDAO(interval));
        List<Integer> idList = new ArrayList<Integer>();
        idList.add(Integer.valueOf(4));
        criteria.init(getMockGroupHierarchy(), GROUP_ID, idList, initInterval(),false);
        
        dump("IndividualDriverPerformance", 1, criteria.getCriteriaList().get(0), FormatType.PDF);
        dump("IndividualDriverPerformance", 1, criteria.getCriteriaList().get(0), FormatType.HTML);

    }
    @Test
    public void teamDriverTest() {
        
        DriverPerformanceReportCriteria criteria = new DriverPerformanceReportCriteria(ReportType.DRIVER_PERFORMANCE_TEAM, Locale.US);
        Interval interval = initInterval();

        criteria.setDriverPerformanceDAO(new MockDriverPerformanceDAO(interval));
        criteria.init(getMockGroupHierarchy(), GROUP_ID, initInterval(), false);
        
        dump("TeamDriverPerformance", 1, criteria, FormatType.PDF);
        dump("TeamDriverPerformance", 1, criteria, FormatType.HTML);

    }

    @Test
    public void keyMetricsPerformanceTest() {
        
        DriverPerformanceKeyMetricsReportCriteria criteria = new DriverPerformanceKeyMetricsReportCriteria(ReportType.DRIVER_PERFORMANCE_KEY_METRICS, Locale.US);
        Interval interval = initInterval();

        criteria.setDriverPerformanceDAO(new MockDriverPerformanceDAO(interval));
        List<Integer> groupIDList = new ArrayList<Integer>();
        groupIDList.add(GROUP_ID);
        criteria.init(getMockGroupHierarchy(), groupIDList, TimeFrame.PAST_MONTH, MeasurementType.ENGLISH);
        
        dump("DriverPerformanceKeyMetrics_mi", 1, criteria, FormatType.EXCEL);

        criteria.init(getMockGroupHierarchy(), groupIDList, TimeFrame.WEEK, MeasurementType.METRIC);
        dump("DriverPerformanceKeyMetrics_km", 1, criteria, FormatType.EXCEL);

    }
    
    class MockDriverPerformanceDAO implements DriverPerformanceDAO{

        Interval interval;
        
        public MockDriverPerformanceDAO(Interval interval)
        {
            this.interval = interval;
        }
        @Override
        public List<DriverPerformance> getDriverPerformance(Integer groupID, String groupName, List<Integer> driverID, Interval queryInterval) {
//                String groupName, Integer driverID, String driverName, String employeeID, Integer score, Integer totalMiles, Integer hardAccelCount,
//                Integer hardBrakeCount, Integer hardTurnCount, Integer hardVerticalCount
                return getDriverPerformanceListForGroup(groupID, groupName, queryInterval);
        }

        @Override
        public List<DriverPerformance> getDriverPerformanceListForGroup(Integer groupID, String groupName, Interval queryInterval) {
            List<DriverPerformance> list = new ArrayList<DriverPerformance>();

            list.add(new DriverPerformance("Group", 100, "Driver NA", "Emp NA", -1, 0, 0,0,0,0,0));
            for (int i = 0; i < 5; i++) {
                list.add(new DriverPerformance("Group", i, "Driver " + i, "Emp " + i, i*10+1, i*1000, i,i,i,i,i));
            }
            return list;
        }
        @Override
        public List<DriverPerformanceKeyMetrics> getDriverPerformanceKeyMetricsListForGroup(Integer groupID, String divisionName, String teamName, TimeFrame timeFrame) {
            List<DriverPerformanceKeyMetrics> list = new ArrayList<DriverPerformanceKeyMetrics>();

            DateTime weekEndDateTime = new DateMidnight(new DateTime().minusDays(1)).toDateTime();
            
            list.add(new DriverPerformanceKeyMetrics("Division", "Team", "Driver NA", "Position", 0, timeFrame, 0, 0, 0,0,0,0,0, 0, 0));
            for (int i = 0; i < 5; i++) {
                list.add(new DriverPerformanceKeyMetrics("Division", "Team", "Driver " + i, "Position", i, timeFrame, i*1000, i*10,i*10,i*10,i*10,i,i*30,i,i*60));
            }
            Collections.sort(list);
            return list;
        }
    }

}
