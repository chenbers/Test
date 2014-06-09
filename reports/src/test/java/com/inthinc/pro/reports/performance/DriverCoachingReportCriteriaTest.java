package com.inthinc.pro.reports.performance;

import static junit.framework.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import mockit.Mocked;
import mockit.NonStrictExpectations;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.junit.Before;
import org.junit.Test;

import com.inthinc.hos.model.HOSStatus;
import com.inthinc.hos.model.RuleSetType;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.HOSDAO;
import com.inthinc.pro.dao.report.DriverPerformanceDAO;
import com.inthinc.pro.dao.report.GroupReportDAO;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.aggregation.DriverPerformance;
import com.inthinc.pro.model.aggregation.DriverVehicleScoreWrapper;
import com.inthinc.pro.model.aggregation.Score;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.reports.BaseUnitTest;
import com.inthinc.pro.reports.FormatType;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.performance.DriverCoachingReportCriteria.DriverCoachingReportViolationSummary;


public class DriverCoachingReportCriteriaTest extends BaseUnitTest {


    @Mocked
    private DriverPerformanceDAO driverPerformanceDAO;

    @Mocked
    private GroupReportDAO groupReportDAO;
    
    @Mocked
    private HOSDAO hosDAO;

    @Mocked
    private DriverDAO driverDAO;

    private List<DriverVehicleScoreWrapper> driverVehicleScoreWrappers;

    private List<DriverPerformance> driverPerformances;
    
    private Driver driver;
    
    private List<HOSRecord> hosRecords;

    private static final TimeZone DRIVER_TIME_ZONE = TimeZone.getTimeZone("UTC");
    private static final RuleSetType DRIVER_DOT_TYPE = RuleSetType.CANADA_2007_CYCLE_1;
    
    @Before
    public void setupTests() {
        driverPerformances = new ArrayList<DriverPerformance>();
        
        /* Setup the mock Driver Performance Bean */
        DriverPerformance driverPerformance = new DriverPerformance();
        driverPerformance.setDriverName("Test Driver");
        driverPerformance.setDriverID(1);
        driverPerformance.setIdleLo(65);
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
        driver = new Driver();
        driver.setDriverID(1);
        driver.setDot(DRIVER_DOT_TYPE);
        Person person = new Person();
        person.setDriver(driver);
        person.setTimeZone(DRIVER_TIME_ZONE);
        
        driver.setPerson(person);
        
        driverVehicleScoreWrapper1.setDriver(driver);
        Score score = new Score();
        score.setOverall(30);
        driverVehicleScoreWrapper1.setScore(score);
        driverVehicleScoreWrappers.add(driverVehicleScoreWrapper1);
        
        
        hosRecords = new ArrayList<HOSRecord>();
        DateTime currentTime = new DateTime();
        hosRecords.add(new HOSRecord(1, currentTime.minusDays(7).toDate(), DRIVER_TIME_ZONE, HOSStatus.OFF_DUTY));
        hosRecords.add(new HOSRecord(1, currentTime.minusDays(2).toDate(), DRIVER_TIME_ZONE, HOSStatus.DRIVING));  // SHIFT and DAILY violation (24 hours of driving)
        hosRecords.add(new HOSRecord(1, currentTime.minusDays(1).toDate(), DRIVER_TIME_ZONE, HOSStatus.OFF_DUTY));
        for (HOSRecord hosRecord : hosRecords) {
            hosRecord.setDriverDotType(DRIVER_DOT_TYPE);
        }
    
    }


    @SuppressWarnings("unchecked")
    @Test
    public void testBuildDriverCoachingReportCriteria() {
        new NonStrictExpectations() {{
            driverPerformanceDAO.getDriverPerformanceListForGroup(anyInt, anyString, (Interval) any, true, true);
            returns(driverPerformances);
        }};

        new NonStrictExpectations() {{
            groupReportDAO.getDriverScores(anyInt, (Interval) any, null);
            returns(driverVehicleScoreWrappers);
        }};

        new NonStrictExpectations() {{
            driverDAO.findByID(anyInt);
            returns(driver);
        }};

        new NonStrictExpectations() {{
            hosDAO.getHOSRecords(anyInt, (Interval) any, false);
            returns(hosRecords);
        }};


        DriverCoachingReportCriteria.Builder reportCriteriaBuilder = new DriverCoachingReportCriteria.Builder(groupReportDAO,driverPerformanceDAO,1,TimeFrame.PAST_SEVEN_DAYS.getInterval(), true, true);
        reportCriteriaBuilder.setLocale(Locale.US);
        reportCriteriaBuilder.setDateTimeZone(DateTimeZone.UTC);
        reportCriteriaBuilder.setAccountHOSEnabled(true);
        reportCriteriaBuilder.setHosDAO(hosDAO);
        reportCriteriaBuilder.setDriverDAO(driverDAO);
        
        List<ReportCriteria> reportCriterias = reportCriteriaBuilder.build();
        assertEquals("Expeced 1 report criteria", 1, reportCriterias.size());
        DriverCoachingReportCriteria reportCriteria = (DriverCoachingReportCriteria)reportCriterias.get(0);

        List<DriverCoachingReportViolationSummary> violations = (List<DriverCoachingReportViolationSummary>)reportCriteria.getMainDataset();
        assertEquals("Expeced 5 items", 5, violations.size());
        assertEquals("Expected Speeding Violations", "6", violations.get(0).getSummary());
        assertEquals("Expected Seatbelt Violations", "3", violations.get(1).getSummary());
        assertEquals("Expected Aggressive Violations", "12", violations.get(2).getSummary());
        assertEquals("Expected Lo Idle Time", "00:01:05", violations.get(3).getSummary());
        assertEquals("Expected HOS Violations", "2", violations.get(4).getSummary());
        
        dump("DriverCoaching", 1, reportCriterias, FormatType.PDF);
        
        
        // non hos account
        reportCriteriaBuilder.setAccountHOSEnabled(false);
        reportCriterias = reportCriteriaBuilder.build();
        assertEquals("Expeced 1 report criteria", 1, reportCriterias.size());
        reportCriteria = (DriverCoachingReportCriteria)reportCriterias.get(0);

        violations = (List<DriverCoachingReportViolationSummary>)reportCriteria.getMainDataset();
        assertEquals("Expeced 4 items", 4, violations.size());
        
        dump("DriverCoaching", 2, reportCriterias, FormatType.PDF);
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
        testMap.put(builder.getCustomInterval(TimeFrame.THREE_MONTHS, baseInterval, DateTimeZone.UTC), new CustomExpectation("THREE_MONTHS", baseInterval.getEnd().minusMonths(3).minusDays(1),  baseInterval.getEnd()));
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
