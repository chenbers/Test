package com.inthinc.pro.model;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;

public class CrashDataPointTest {

    @Test
    public void testSorting() {
        DateTime currentTime = new DateTime();
        List<CrashDataPoint> crashDataPoints = new ArrayList<CrashDataPoint>();
        crashDataPoints.add(createCrashDataPoint(currentTime));
        crashDataPoints.add(createCrashDataPoint(currentTime.minusHours(6)));
        crashDataPoints.add(createCrashDataPoint(currentTime.minusHours(8)));
        crashDataPoints.add(createCrashDataPoint(currentTime.minusHours(1)));
        crashDataPoints.add(createCrashDataPoint(currentTime.minusHours(3)));
        Collections.sort(crashDataPoints);
        CrashDataPoint lastCrashDataPoint = null;
        for (CrashDataPoint crashDataPoint : crashDataPoints) {
            if (lastCrashDataPoint == null) {
                lastCrashDataPoint = crashDataPoint;
            }
            else {
                assertTrue("list is in ascending order", crashDataPoint.getTime().after(lastCrashDataPoint.getTime()));
                lastCrashDataPoint = crashDataPoint;
            }
        }
    }
    
    private CrashDataPoint createCrashDataPoint(DateTime dateTime) {
        CrashDataPoint crashDataPoint = new CrashDataPoint();
        crashDataPoint.setTime(dateTime.toDate());
        return crashDataPoint;
    }

}
