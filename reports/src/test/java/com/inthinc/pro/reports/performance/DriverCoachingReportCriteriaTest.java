package com.inthinc.pro.reports.performance;

import com.inthinc.pro.dao.report.DriverPerformanceDAO;
import com.inthinc.pro.dao.report.GroupReportDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.aggregation.DriverPerformance;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;
import com.inthinc.pro.model.aggregation.Score;
import com.inthinc.pro.reports.BaseUnitTest;
import junit.framework.Assert;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.*;


public class DriverCoachingReportCriteriaTest extends BaseUnitTest {


    @Mocked
    private DriverPerformanceDAO driverPerformanceDAO;

    @Mocked
    private GroupReportDAO groupReportDAO;

    private List<DriverVehicleScoreWrapper> driverVehicleScoreWrappers;

    private List<DriverPerformance> driverPerformances;

    @Before
    public void setupTests() {
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
    public void testBuildDriverCoachingReportCriteria() {
        new NonStrictExpectations() {{
            driverPerformanceDAO.getDriverPerformanceListForGroup(anyInt, anyString, (Interval) any);
            returns(driverPerformances);
        }};

        new NonStrictExpectations() {{
            groupReportDAO.getDriverScores(anyInt, (Interval) any, null);
            returns(driverVehicleScoreWrappers);
        }};

//        DriverCoachingReportCriteria.Builder reportCriteriaBuilder = new DriverCoachingReportCriteria.Builder(groupReportDAO,driverPerformanceDAO,1,TimeFrame.PAST_SEVEN_DAYS.getInterval());
//        reportCriteriaBuilder.setLocale(Locale.US);
//        List<ReportCriteria> reportCriterias = reportCriteriaBuilder.build();
//        dump("DriverCoaching", 2, reportCriterias, FormatType.PDF);
    }


    @Test
    public void testGetCustomInterval() throws IllegalAccessException, InstantiationException {
        DateTime today = new DateTime(DateTimeZone.UTC);
        Interval baseInterval = new Interval(new DateMidnight(today.minusDays(3)).getMillis(), new DateMidnight(today.plusDays(1)).getMillis(), DateTimeZone.UTC); //delta = 4 days

        // minimal builder
        DriverCoachingReportCriteria.Builder builder = new DriverCoachingReportCriteria.Builder(null, null, 0, baseInterval);

        // test custom interval
        class CustomExpectation{
            public DateTime start;
            public DateTime end;
            public String timeFrameName;

            CustomExpectation(String timeFrameName, DateTime start, DateTime end){
                this.timeFrameName = timeFrameName;
                this.start = start;
                this.end = end;
            }
        }

        /**
         * Sine mock data for test.
         * Takes into account the plusDays(1) behaviour of some time frames.
         */
        Map<Interval, CustomExpectation> testMap = new HashMap<Interval, CustomExpectation>();
        testMap.put(builder.getCustomInterval(TimeFrame.SIX_MONTHS, baseInterval, DateTimeZone.UTC), new CustomExpectation("SIX_MONTHS", baseInterval.getEnd().minusMonths(6).minusDays(1),  baseInterval.getEnd()));
        testMap.put(builder.getCustomInterval(TimeFrame.THREE_MONTHS, baseInterval, DateTimeZone.UTC), new CustomExpectation("THREE_MONTHS", baseInterval.getEnd().minusMonths(3),  baseInterval.getEnd()));
        testMap.put(builder.getCustomInterval(TimeFrame.FIVE_DAYS_AGO, baseInterval, DateTimeZone.UTC), new CustomExpectation("FIVE_DAYS_AGO", baseInterval.getEnd().minusDays(1),  baseInterval.getEnd()));
        testMap.put(builder.getCustomInterval(TimeFrame.LAST_MONTH, baseInterval, DateTimeZone.UTC), new CustomExpectation("LAST_MONTH", baseInterval.getEnd().minusMonths(1),  baseInterval.getEnd()));
        testMap.put(builder.getCustomInterval(TimeFrame.THREE_DAYS_AGO, baseInterval, DateTimeZone.UTC), new CustomExpectation("THREE_DAYS_AGO", baseInterval.getEnd().minusDays(1),  baseInterval.getEnd()));

        // test mock data
        for (Map.Entry<Interval, CustomExpectation> entry: testMap.entrySet()){
            Interval interval = entry.getKey();
            CustomExpectation exp = entry.getValue();

            System.out.println("Testing for "+exp.timeFrameName + " " + interval);
            assertEquals("Start " + exp.timeFrameName, interval.getStart().toDateMidnight(), exp.start.toDateMidnight());
            assertEquals("End " + exp.timeFrameName, interval.getEnd().toDateMidnight(), exp.end.toDateMidnight());
        }
    }
}
