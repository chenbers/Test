package com.inthinc.pro.dao.hessian.report;

import static org.junit.Assert.*;

import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import com.inthinc.pro.dao.hessian.report.GroupReportHessianDAO;

public class GroupReportHessianDAOTest {

    private static final DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").withZone(DateTimeZone.UTC);
    
    
    @Test
    public void testScoringInterval() {
        GroupReportHessianDAO dao = new GroupReportHessianDAO();
        
        
        Interval interval = intervalForDates("2013-07-01 00:00:00", "2013-07-02 23:59:59", DateTimeZone.UTC);
        Interval expectedInterval = intervalForDates("2013-07-01 00:00:00", "2013-07-02 00:00:00", DateTimeZone.UTC);
        Interval scoringInterval = dao.getScoringInterval(interval);
        assertEquals("Unexpected Scoring Interval", getDisplayInterval(expectedInterval), getDisplayInterval(scoringInterval));
        
        interval = intervalForDates("2013-07-01 00:00:00", "2013-07-01 23:59:59", DateTimeZone.UTC);
        expectedInterval = intervalForDates("2013-07-01 00:00:00", "2013-07-01 00:00:00", DateTimeZone.UTC);
        scoringInterval = dao.getScoringInterval(interval);
        assertEquals("Unexpected Scoring Interval", getDisplayInterval(expectedInterval), getDisplayInterval(scoringInterval));
        
        DateTimeZone pacificTZ = DateTimeZone.forID("US/Pacific");
        interval = intervalForDates("2013-07-01 00:00:00", "2013-07-02 23:59:59", pacificTZ);
        expectedInterval = intervalForDates("2013-07-01 00:00:00", "2013-07-02 00:00:00", DateTimeZone.UTC);
        scoringInterval = dao.getScoringInterval(interval);
        assertEquals("Unexpected Scoring Interval", getDisplayInterval(expectedInterval), getDisplayInterval(scoringInterval));
        
        interval = intervalForDates("2013-07-01 00:00:00", "2013-07-01 23:59:59", pacificTZ);
        expectedInterval = intervalForDates("2013-07-01 00:00:00", "2013-07-01 00:00:00", DateTimeZone.UTC);
        scoringInterval = dao.getScoringInterval(interval);
        assertEquals("Unexpected Scoring Interval", getDisplayInterval(expectedInterval), getDisplayInterval(scoringInterval));

    }


    private String getDisplayInterval(Interval interval) {
        return formatter.print(interval.getStart()) + " " + formatter.print(interval.getEnd()); 
    }


    private Interval intervalForDates(String start, String end, DateTimeZone dateTimeZone) {
        return new Interval(formatter.withZone(dateTimeZone).parseMillis(start), formatter.withZone(dateTimeZone).parseMillis(end), DateTimeZone.UTC);
    }

}
