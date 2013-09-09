package com.inthinc.pro.backing;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

import com.inthinc.pro.model.TimeFrame;
import com.inthinc.pro.model.aggregation.Trend;

public class TeamCommonBeanTest {
    @Test
    public void testPopulateDateGaps() {
        TeamCommonBean teamCommonBean = new TeamCommonBean();
        teamCommonBean.setTimeFrame(TimeFrame.TODAY);
        
        DateTime trendEndingDate = new DateTime();
        trendEndingDate = trendEndingDate.minusDays(30);
        trendEndingDate = trendEndingDate.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
        trendEndingDate = trendEndingDate.withZone(DateTimeZone.UTC);
        
        List<Trend> trendList = new ArrayList<Trend>();
        Trend trend = new Trend();
        trend.setEndingDate(trendEndingDate.toDate());
        
        trendList.add(trend);
        
        teamCommonBean.populateDateGaps(trendList);
        
//        for(Trend t : trendList) {
//            System.out.println(t.getEndingDate());
//        }
        
        assertEquals(31, trendList.size());
        
        teamCommonBean.setTimeFrame(TimeFrame.MONTH);
        
        trendEndingDate = new DateTime();
        trendEndingDate = trendEndingDate.minusMonths(3);
        trendEndingDate = trendEndingDate.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
        trendEndingDate = trendEndingDate.dayOfMonth().withMinimumValue();
        trendEndingDate = trendEndingDate.withZone(DateTimeZone.UTC);
        
        trendList = new ArrayList<Trend>();
        trend = new Trend();
        trend.setEndingDate(trendEndingDate.toDate());
        trendList.add(trend);
        
        teamCommonBean.populateDateGaps(trendList);
        
//        for(Trend t : trendList) {
//            System.out.println(t.getEndingDate());
//        }
        
        assertEquals(4, trendList.size());
    }
}
