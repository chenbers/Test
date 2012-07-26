package com.inthinc.pro.model.aggregation;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.Test;

import com.inthinc.pro.model.TimeFrame;

import mockit.Expectations;
import mockit.Mocked;

public class DriverPerformanceKeyMetricsTest {
    //@Mocked 
    DriverPerformanceKeyMetrics mockData ;
    
    private final static DateTime start = new DateTime(2012, 7, 1, 12, 0,0,0);
    private final static Interval ONE_DAY = new Interval(start.getMillis(), start.plusDays(1).getMillis());
    private final static Interval TWO_DAY = new Interval(start.getMillis(), start.plusDays(2).getMillis());
    private final static Interval ONE_WEEK = new Interval(start.getMillis(), start.plusDays(7).getMillis());
    private final static Interval ONE_MONTH = new Interval(start.getMillis(), start.plusMonths(1).getMillis());
    private final static Interval THREE_MONTH = new Interval(start.getMillis(), start.plusMonths(3).getMillis());
    private final static Interval ONE_YEAR = new Interval(start.getMillis(), start.plusYears(1).getMillis());
    
    private final static Double EPSILON_DOUBLE = (double) (1/9999);
    private final static Integer DAYS_IN_JULY = 31;
    private final static Integer DAYS_IN_AUGUST = 31;
    private final static Integer DAYS_IN_SEPTEMBER = 30;
    private final static Integer DAYS_IN_YEAR = 365;
    private final static Integer[] interestingViolationCounts = {0,1,2,4,5,7};
    private final static Interval[] intervals   = {ONE_DAY, TWO_DAY, ONE_WEEK, ONE_MONTH   , THREE_MONTH                                    , ONE_YEAR};
    private final static Integer[] intervalDays = {1      , 2      ,7        , DAYS_IN_JULY, (DAYS_IN_JULY+DAYS_IN_AUGUST+DAYS_IN_SEPTEMBER), DAYS_IN_YEAR};
    //private final static Integer[] violationCountsToTest = new Integer[interestingViolationCounts.length*daySets.length];
    private final static List<Integer> violationCountsToTest = new ArrayList<Integer>();
//    static{
//        for(Integer edge: interestingViolationCounts){
//            for(Integer days: daySets){
//                violationCountsToTest.add(days*edge);
//            }
//        }
//    }
    
    @Test
    public final void getIdleViolationsPerDay_customTimeFrameVariousIntevals_shouldReturnValiddouble() {
        final DriverPerformanceKeyMetrics data = new DriverPerformanceKeyMetrics();
        int tests = 0;
        for(Integer violationCount: interestingViolationCounts){
            for(int i =0; i < intervals.length; i++){
                System.out.println("violationCount: "+violationCount);
                System.out.println("interval: "+intervals[i]);
                System.out.println("violationCount*interval: "+violationCount*intervalDays[i]);
                
                data.setTimeFrame(TimeFrame.CUSTOM_RANGE);
                data.setInterval(intervals[i]);
                data.setIdleViolationsCount(violationCount*intervalDays[i]);
                assertEquals(((Integer)(violationCount*intervalDays[i])).doubleValue()/intervalDays[i], data.getIdleViolationsPerDay(), EPSILON_DOUBLE);
                System.out.println("tests: "+tests++);
                
            }
//            System.out.println("violationCount: "+violationCount);
//            
//            data.setTimeFrame(TimeFrame.CUSTOM_RANGE);
//            data.setInterval(ONE_DAY);
//            data.setIdleViolationsCount(violationCount);
//            assertEquals(violationCount.doubleValue()/1, data.getIdleViolationsPerDay(), EPSILON_DOUBLE);
//            
//            data.setTimeFrame(TimeFrame.CUSTOM_RANGE);
//            data.setInterval(TWO_DAY);
//            data.setIdleViolationsCount(violationCount);
//            assertEquals((violationCount.doubleValue()/2), data.getIdleViolationsPerDay(), EPSILON_DOUBLE);
//            
//            data.setTimeFrame(TimeFrame.CUSTOM_RANGE);
//            data.setInterval(ONE_WEEK);
//            data.setIdleViolationsCount(violationCount);
//            System.out.println((double)(violationCount.doubleValue()/7));
//            assertEquals((violationCount.doubleValue()/7), data.getIdleViolationsPerDay(), EPSILON_DOUBLE);
//            
//            data.setTimeFrame(TimeFrame.CUSTOM_RANGE);
//            data.setInterval(ONE_MONTH);
//            data.setIdleViolationsCount(violationCount);
//            assertEquals((violationCount.doubleValue()/31), data.getIdleViolationsPerDay(), EPSILON_DOUBLE);
//            
//            data.setTimeFrame(TimeFrame.CUSTOM_RANGE);
//            data.setInterval(THREE_MONTH);
//            data.setIdleViolationsCount(violationCount);
//            assertEquals((violationCount.doubleValue()/(DAYS_IN_JULY+DAYS_IN_AUGUST+DAYS_IN_SEPTEMBER)), data.getIdleViolationsPerDay(), EPSILON_DOUBLE);
//            
//            data.setTimeFrame(TimeFrame.CUSTOM_RANGE);
//            data.setInterval(ONE_YEAR);
//            data.setIdleViolationsCount(violationCount);
//            System.out.println("violationCount.doubleValue(): "+violationCount.doubleValue());
//            assertEquals((violationCount.doubleValue()/(DAYS_IN_YEAR)), data.getIdleViolationsPerDay(), EPSILON_DOUBLE);
        }
    }

    @Test
    public final void getIdlingColor_cond_result() {
        mockData.getIdlingColor();
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void getOverallScoreColor_cond_result() {
        mockData.getOverallScoreColor();
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void getSpeedingScoreColor_cond_result() {
        mockData.getSpeedingScoreColor();
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void getStyleScoreColor_cond_result() {
        mockData.getStyleScoreColor();
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void getSeatbeltScoreColor_cond_result() {
        mockData.getSeatbeltScoreColor();
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void getTimeFrameBasedOverallScore_cond_result() {
        mockData.getTimeFrameBasedOverallScore();
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void getTimeFrameBasedSpeedingScore_cond_result() {
        mockData.getTimeFrameBasedSpeedingScore();
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void getTimeFrameBasedStyleScore_cond_result() {
        mockData.getTimeFrameBasedStyleScore();
        fail("Not yet implemented"); // TODO
    }

    @Test
    public final void getTimeFrameBasedSeatbeltScore_cond_result() {
        mockData.getTimeFrameBasedSeatbeltScore();
        fail("Not yet implemented"); // TODO
    }
}
