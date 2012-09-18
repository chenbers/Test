package com.inthinc.pro.reports.performance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import junit.framework.Assert;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.junit.Test;

import com.inthinc.pro.dao.report.DriverPerformanceDAO;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.aggregation.DriverPerformance;
import com.inthinc.pro.model.aggregation.DriverPerformanceKeyMetrics;
import com.inthinc.pro.model.aggregation.VehiclePerformance;
import com.inthinc.pro.reports.FormatType;
import com.inthinc.pro.reports.ReportType;

public class DriverPerformanceReportCriteriaDataTest extends BasePerformanceUnitTest {
    @Test
    public void scratchPad(){
        TimeFrame timeFrame = TimeFrame.CUSTOM_RANGE;
        Integer days_one = (int) (timeFrame.getDuration().getMillis()/DateTimeConstants.MILLIS_PER_DAY);
        System.out.println("days: "+days_one);
        
        DateTime startDate = new DateTime(2012, 1, 1, 12, 0, 0, 0);
        DateTime endDate =   new DateTime(2012, 2, 1, 12, 0, 0, 0);

        Integer days_two = (int) (timeFrame.getInterval(startDate.getMillis(), endDate.getMillis(), DateTimeZone.UTC).toDuration().getMillis()/DateTimeConstants.MILLIS_PER_DAY);
        System.out.println("days_two: "+days_two);
        
        try{
            TimeFrame timeFrameThatDoesNotSupportCustomInteval = TimeFrame.THREE_MONTHS;
            Integer days_three = (int) (timeFrameThatDoesNotSupportCustomInteval.getInterval(startDate.getMillis(), endDate.getMillis(), DateTimeZone.UTC).toDuration().getMillis()/DateTimeConstants.MILLIS_PER_DAY);
            System.out.println("days_three: "+days_three);
            Assert.assertTrue("expected IllegalArgumentException",false);
        } catch (IllegalArgumentException iae) {
            System.out.println("expected IllegalArgumentException was thrown: "+iae);
        }
        Integer days_four = timeFrame.getNumberOfDays();
        System.out.println("days_four: "+days_four);
        
        Double violations = new Integer(4).doubleValue();
        Integer days_five = 7;
        for(int i = 0; i < 70; i++){
            System.out.println("how does this compare to 1/7? "+(violations/(days_five+i)));
            System.out.println("how does this compare to "+(1.0/7)+"? "+((1.0/7)<(violations/(days_five+i))));
        }
    }
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
        dump("TeamDriverPerformance_ryg", 1, criteria, FormatType.EXCEL);

        criteria.addParameter("USE_METRIC", Boolean.TRUE);
        criteria.init(getMockGroupHierarchy(), GROUP_ID, initInterval(), true);
        
        dump("TeamDriverPerformanceMetric_ryg", 1, criteria, FormatType.PDF);
        dump("TeamDriverPerformanceMetric_ryg", 1, criteria, FormatType.HTML);
        dump("TeamDriverPerformanceMetric_ryg", 1, criteria, FormatType.EXCEL);
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
        dump("TeamDriverPerformance", 1, criteria, FormatType.EXCEL);

    }

    @Test
    public void keyMetricsPerformanceTest() {
        
        DriverPerformanceKeyMetricsReportCriteria criteria = new DriverPerformanceKeyMetricsReportCriteria(ReportType.DRIVER_PERFORMANCE_KEY_METRICS, Locale.US);
        Interval interval = initInterval();

        criteria.setDriverPerformanceDAO(new MockDriverPerformanceDAO(interval));
        List<Integer> groupIDList = new ArrayList<Integer>();
        groupIDList.add(GROUP_ID);
        criteria.init(getMockGroupHierarchy(), groupIDList, TimeFrame.LAST_MONTH, MeasurementType.ENGLISH);
        
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
                DriverPerformance driverPerformance = new DriverPerformance("Group", i, "Driver " + i, "Emp " + i, i*10+1, i*1000, i,i,i,i,i);
                List<VehiclePerformance> vehiclePerformanceBreakdown = new ArrayList<VehiclePerformance>();
                for (int j = 0; j < i+1; j++) {
                    vehiclePerformanceBreakdown.add(new VehiclePerformance("Vehicle " + i + "_" + j, i*10+1, i*1000, i,i,i,i,i, 0,0,0));
                }
                driverPerformance.setVehiclePerformanceBreakdown(vehiclePerformanceBreakdown);
                
                list.add(driverPerformance);
            }
            return list;
        }
        @Override
        public List<DriverPerformanceKeyMetrics> getDriverPerformanceKeyMetricsListForGroup(Integer groupID, String divisionName, String teamName, TimeFrame timeFrame) {
            List<DriverPerformanceKeyMetrics> list = new ArrayList<DriverPerformanceKeyMetrics>();

            DateTime weekEndDateTime = new DateMidnight(new DateTime().minusDays(1)).toDateTime();
            
            list.add(new DriverPerformanceKeyMetrics("Division", "Team", "Driver NA", "Position", 0, timeFrame, 0, 0, 0,0,0,0,0,0));
            for (int i = 0; i < 5; i++) {
                list.add(new DriverPerformanceKeyMetrics("Division", "Team", "Driver " + i, "Position", i, timeFrame, i*1000, i*10,i*10,i*10,i*10,i,i*30*60,i*60*60));
            }
            Collections.sort(list);
            return list;
        }
		@Override
		public List<DriverPerformanceKeyMetrics> getDriverPerformanceKeyMetricsListForGroup(Integer groupID, String divisionName, String teamName, Interval interval) {
            List<DriverPerformanceKeyMetrics> list = new ArrayList<DriverPerformanceKeyMetrics>();
            DateTime weekEndDateTime = new DateMidnight(new DateTime().minusDays(1)).toDateTime();
            list.add(new DriverPerformanceKeyMetrics("Division", "Team", "Driver NA", "Position", 0, interval, 0, 0, 0,0,0,0,0,0, " "));
            for (int i = 0; i < 5; i++) {
                list.add(new DriverPerformanceKeyMetrics("Division", "Team", "Driver " + i, "Position", i, interval, i*1000, i*10,i*10,i*10,i*10,i,i*30*60,i*60*60, " "));//TODO: jwimmer: FYI: color is NOT getting set correctly here
            }
            Collections.sort(list);
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

}
