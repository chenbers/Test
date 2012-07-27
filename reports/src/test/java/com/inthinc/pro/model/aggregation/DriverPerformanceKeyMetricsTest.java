package com.inthinc.pro.model.aggregation;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
//import mockit.Mocked;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.model.TimeFrame;

public class DriverPerformanceKeyMetricsTest {
    //@Mocked DriverPerformanceKeyMetrics mockedData;
    //DriverPerformanceKeyMetrics testedData;
    
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
    private final static Integer[] validInterestingScores = {0, 1, 29, 30, 31, 39, 40, 41, 50};
    private final static Integer[] invalidScores = {null, -1, 51};
    private final static Integer[] totalMilesForTimeframeMethods = {null, 0, -1};
    private final static Interval[] intervals   = {ONE_DAY, TWO_DAY, ONE_WEEK, ONE_MONTH   , THREE_MONTH                                    , ONE_YEAR};
    private final static Integer[] intervalDays = {1      , 2      ,7        , DAYS_IN_JULY, (DAYS_IN_JULY+DAYS_IN_AUGUST+DAYS_IN_SEPTEMBER), DAYS_IN_YEAR};
    
    @Test
    public final void getIdleViolationsPerDay_customTimeFrameVariousIntevals_shouldReturnValiddouble() {
        final DriverPerformanceKeyMetrics data = new DriverPerformanceKeyMetrics();
        for(Integer violationCount: interestingViolationCounts){
            for(int i =0; i < intervals.length; i++){
                data.setTimeFrame(TimeFrame.CUSTOM_RANGE);
                data.setInterval(intervals[i]);
                data.setIdleViolationsCount(violationCount*intervalDays[i]);
                assertEquals(((Integer)(violationCount*intervalDays[i])).doubleValue()/intervalDays[i], data.getIdleViolationsPerDay(), EPSILON_DOUBLE);
            }
        }
    }

    @Test
    public final void getIdleViolationsPerDay_nullTimeFrameVariousIntevals_shouldReturnValiddouble() {
        final DriverPerformanceKeyMetrics data = new DriverPerformanceKeyMetrics();
        for(Integer violationCount: interestingViolationCounts){
            for(int i =0; i < intervals.length; i++){
                data.setTimeFrame(null);
                data.setInterval(intervals[i]);
                data.setIdleViolationsCount(violationCount*intervalDays[i]);
                assertEquals(((Integer)(violationCount*intervalDays[i])).doubleValue()/intervalDays[i], data.getIdleViolationsPerDay(), EPSILON_DOUBLE);
            }
        }
    }
    
    @Test
    public final void getIdlingColor_zeroMiles_white(){
        final DriverPerformanceKeyMetrics data = new DriverPerformanceKeyMetrics();
        for(Integer violationCount: interestingViolationCounts){
            for(int i =0; i < intervals.length; i++){
                data.setTimeFrame(null);
                data.setTotalMiles(null);//not null, greater than 0
                data.setInterval(intervals[i]);
                data.setIdleViolationsCount(violationCount);
                assertEquals("white", data.getIdlingColor());
            }
        }
    }
    
    @Test
    public final void getIdlingColor_allIntervalsPlusInterestingViolationCounts_validColorNotWhite(){
        final DriverPerformanceKeyMetrics data = new DriverPerformanceKeyMetrics();
        int count = 0;
        String[] expectedArray = { "green", "green", "green", "green", "green", "green", "red", "yellow", "yellow", "green", "green", "green", "red", "red", "yellow", "green", "green", "green",
                "red", "red", "red", "green", "green", "green", "red", "red", "red", "yellow", "green", "green", "red", "red", "red", "yellow", "green", "green" };
        for(Integer violationCount: interestingViolationCounts){
            for(int i =0; i < intervals.length; i++){
                data.setTimeFrame(null);
                data.setTotalMiles(1);//not null, greater than 0
                data.setInterval(intervals[i]);
                data.setIdleViolationsCount(violationCount);
                assertEquals(expectedArray[count], data.getIdlingColor());
                count++;
                //System.out.println((count++)+" : "+violationCount+"*"+intervalDays[i]+"="+(violationCount*intervalDays[i])+" :: "+data.getIdlingColor());
            }
        }
    }

    @Test
    public final void getOverallScoreColor_inValidScores_white() {
        final DriverPerformanceKeyMetrics data = new DriverPerformanceKeyMetrics();
        for(Integer score: invalidScores){
            data.setTotalMiles(1);//not null, greater than 0
            data.setOverallScore(score);
            assertEquals("white", data.getOverallScoreColor());
        }
        data.setTotalMiles(0);
        assertEquals("white", data.getOverallScoreColor());
    }
    
    @Test
    public final void getSeatbeltScoreColor_inValidScores_white() {
        final DriverPerformanceKeyMetrics data = new DriverPerformanceKeyMetrics();
        for(Integer score: invalidScores){
            data.setTotalMiles(1);//not null, greater than 0
            data.setSeatbeltScore(score);
            assertEquals("white", data.getSeatbeltScoreColor());
        }
        data.setTotalMiles(0);
        assertEquals("white", data.getSeatbeltScoreColor());
    }
    
    @Test
    public final void getSpeedingScoreColor_inValidScores_white() {
        final DriverPerformanceKeyMetrics data = new DriverPerformanceKeyMetrics();
        for(Integer score: invalidScores){
            data.setTotalMiles(1);//not null, greater than 0
            data.setSpeedingScore(score);
            assertEquals("white", data.getSpeedingScoreColor());
        }
        data.setTotalMiles(0);
        assertEquals("white", data.getSpeedingScoreColor());
    }
    
    @Test
    public final void getStyleScoreColor_inValidScores_white() {
        final DriverPerformanceKeyMetrics data = new DriverPerformanceKeyMetrics();
        for(Integer score: invalidScores){
            data.setTotalMiles(1);//not null, greater than 0
            data.setStyleScore(score);
            assertEquals("white", data.getStyleScoreColor());
        }
        data.setTotalMiles(0);
        assertEquals("white", data.getStyleScoreColor());
    }
    @Test
    public final void getOverallScoreColor_validInterestingScores_allValidColors() {
        final DriverPerformanceKeyMetrics data = new DriverPerformanceKeyMetrics();
        String[] expected = {"red","red","red","red","yellow","yellow","yellow","green","green"};
        int index = 0;
        for(Integer score: validInterestingScores){
            data.setTotalMiles(1);//not null, greater than 0
            data.setOverallScore(score);
            
            assertEquals(expected[index], data.getOverallScoreColor());
            index++;
        }
        data.setTotalMiles(0);
        assertEquals("white", data.getOverallScoreColor());
    }

    @Test
    public final void getSpeedingScoreColor_validInterestingScores_allValidColors() {
        final DriverPerformanceKeyMetrics data = new DriverPerformanceKeyMetrics();
        String[] expected = {"red","red","red","red","yellow","yellow","yellow","green","green"};
        int index = 0;
        for(Integer score: validInterestingScores){
            data.setTotalMiles(1);//not null, greater than 0
            data.setSpeedingScore(score);
            
            assertEquals(expected[index], data.getSpeedingScoreColor());
            index++;
        }
        data.setTotalMiles(0);
        assertEquals("white", data.getSpeedingScoreColor());
    }

    @Test
    public final void getStyleScoreColor_validInterestingScores_allValidColors() {
        final DriverPerformanceKeyMetrics data = new DriverPerformanceKeyMetrics();
        String[] expected = {"red","red","red","red","yellow","yellow","yellow","green","green"};
        int index = 0;
        for(Integer score: validInterestingScores){
            data.setTotalMiles(1);//not null, greater than 0
            data.setStyleScore(score);
            
            assertEquals(expected[index], data.getStyleScoreColor());
            index++;
        }
        data.setTotalMiles(0);
        assertEquals("white", data.getStyleScoreColor());
    }

    @Test
    public final void getSeatbeltScoreColor_validInterestingScores_allValidColors() {
        final DriverPerformanceKeyMetrics data = new DriverPerformanceKeyMetrics();
        String[] expected = {"red","red","red","red","yellow","yellow","yellow","green","green"};
        int index = 0;
        for(Integer score: validInterestingScores){
            data.setTotalMiles(1);//not null, greater than 0
            data.setSeatbeltScore(score);
            
            assertEquals(expected[index], data.getSeatbeltScoreColor());
            index++;
        }
        data.setTotalMiles(0);
        assertEquals("white", data.getSeatbeltScoreColor());
    }

    @Test
    public final void getTimeFrameBasedOverallScore_totalMilesIndicatesNoDriving_negativeOne() {
        final DriverPerformanceKeyMetrics data = new DriverPerformanceKeyMetrics();
        for(Integer score: validInterestingScores){
            for(Integer miles: totalMilesForTimeframeMethods){
                data.setTotalMiles(miles);//not null, greater than 0
                data.setOverallScore(score);
                assertEquals(new Integer(-1), data.getTimeFrameBasedOverallScore());
            }
            data.setTotalMiles(1);//not null, greater than 0
            assertEquals(score, data.getTimeFrameBasedOverallScore());
        }
    }
    @Test
    public final void getTimeFrameBasedOverallScore_totalMilesIndicatesDriving_validScore() {
        final DriverPerformanceKeyMetrics data = new DriverPerformanceKeyMetrics();
        for(Integer score: validInterestingScores){
            data.setOverallScore(score);
            data.setTotalMiles(1);//not null, greater than 0
            assertEquals(score, data.getTimeFrameBasedOverallScore());
        }
    }
    
    /**
     * This situation was not discussed in the UserStory (US5598), so I'm leaving this test ignored (and not implementing it on other ...TimeFrame... methods
     */
    @Ignore 
    @Test
    public final void getTimeFrameBasedOverallScore_totalMilesIndicatesDriving_invalidScore() {
        final DriverPerformanceKeyMetrics data = new DriverPerformanceKeyMetrics();
        Integer expected = new Integer(-1);
        for(Integer score: invalidScores){
            data.setOverallScore(score);
            data.setTotalMiles(1);//not null, greater than 0
            assertEquals(expected, data.getTimeFrameBasedOverallScore());
        }
    }

    @Test
    public final void getTimeFrameBasedSpeedingScore_totalMilesIndicatesNoDriving_negativeOne() {
        final DriverPerformanceKeyMetrics data = new DriverPerformanceKeyMetrics();
        for(Integer score: validInterestingScores){
            for(Integer miles: totalMilesForTimeframeMethods){
                data.setTotalMiles(miles);//not null, greater than 0
                data.setSpeedingScore(score);
                assertEquals(new Integer(-1), data.getTimeFrameBasedSpeedingScore());
            }
            data.setTotalMiles(1);//not null, greater than 0
            assertEquals(score, data.getTimeFrameBasedSpeedingScore());
        }
    }
    @Test
    public final void getTimeFrameBasedSpeedingScore_totalMilesIndicatesDriving_validScore() {
        final DriverPerformanceKeyMetrics data = new DriverPerformanceKeyMetrics();
        for(Integer score: validInterestingScores){
            data.setSpeedingScore(score);
            data.setTotalMiles(1);//not null, greater than 0
            assertEquals(score, data.getTimeFrameBasedSpeedingScore());
        }
    }

    @Test
    public final void getTimeFrameBasedStyleScore_totalMilesIndicatesNoDriving_negativeOne() {
        final DriverPerformanceKeyMetrics data = new DriverPerformanceKeyMetrics();
        for(Integer score: validInterestingScores){
            for(Integer miles: totalMilesForTimeframeMethods){
                data.setTotalMiles(miles);//not null, greater than 0
                data.setStyleScore(score);
                assertEquals(new Integer(-1), data.getTimeFrameBasedStyleScore());
            }
            data.setTotalMiles(1);//not null, greater than 0
            assertEquals(score, data.getTimeFrameBasedStyleScore());
        }
    }
    @Test
    public final void getTimeFrameBasedStyleScore_totalMilesIndicatesDriving_validScore() {
        final DriverPerformanceKeyMetrics data = new DriverPerformanceKeyMetrics();
        for(Integer score: validInterestingScores){
            data.setStyleScore(score);
            data.setTotalMiles(1);//not null, greater than 0
            assertEquals(score, data.getTimeFrameBasedStyleScore());
        }
    }

    @Test
    public final void getTimeFrameBasedSeatbeltScore_totalMilesIndicatesNoDriving_negativeOne() {
        final DriverPerformanceKeyMetrics data = new DriverPerformanceKeyMetrics();
        for(Integer score: validInterestingScores){
            for(Integer miles: totalMilesForTimeframeMethods){
                data.setTotalMiles(miles);//not null, greater than 0
                data.setSeatbeltScore(score);
                assertEquals(new Integer(-1), data.getTimeFrameBasedSeatbeltScore());
            }
            data.setTotalMiles(1);//not null, greater than 0
            assertEquals(score, data.getTimeFrameBasedSeatbeltScore());
        }
    }
    @Test
    public final void getTimeFrameBasedSeatbeltScore_totalMilesIndicatesDriving_validScore() {
        final DriverPerformanceKeyMetrics data = new DriverPerformanceKeyMetrics();
        for(Integer score: validInterestingScores){
            data.setSeatbeltScore(score);
            data.setTotalMiles(1);//not null, greater than 0
            assertEquals(score, data.getTimeFrameBasedSeatbeltScore());
        }
    }
}
