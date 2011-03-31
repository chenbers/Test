package com.inthinc.pro.reports.performance;

import static org.junit.Assert.assertEquals;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import com.inthinc.pro.reports.FormatType;
import com.inthinc.pro.reports.performance.model.TenHoursViolation;


public class TenHoursViolationReportCriteriaDataTest extends BaseDriveTimeUnitTest {
    
    protected static final String DAY_FORMAT = "MM/dd/yyyy";

    @SuppressWarnings("unchecked")
    @Test
    public void dataTestCases() {
        Interval interval = initInterval();
        DateTimeFormatter dayFormatter = DateTimeFormat.forPattern(DAY_FORMAT);

        TenHoursViolationReportCriteria criteria = new TenHoursViolationReportCriteria(Locale.US);
        criteria.setDriveTimeDAO(new MockDriveTimeDAO(interval, 11));
        criteria.setDriverDAO(new MockDriverDAO());
        criteria.init(getMockGroupHierarchy(), GROUP_ID, interval);
        
        List<TenHoursViolation> dataList = criteria.getMainDataset();
        
        assertEquals("data size", 4, dataList.size());
        DecimalFormat hoursFormatter = new DecimalFormat("###0.00"); 
        int eCnt = 1;
        for (TenHoursViolation data : dataList) {
            data.dump();
            assertEquals("groupName " + eCnt, GROUP_NAME, data.getGroupName());
            assertEquals("driverName " + eCnt, "F"+eCnt+" L"+eCnt, data.getDriverName());
            assertEquals("day " + eCnt, dayFormatter.print(interval.getStart()), data.getDate());
            assertEquals("hours " + eCnt, hoursFormatter.format(11.0d), hoursFormatter.format(data.getHoursThisDay()));
            eCnt++;
        }
        
        dump("tenHoursViolationTest", 1, criteria, FormatType.PDF);
        dump("tenHoursViolationTest", 1, criteria, FormatType.EXCEL);

    }
}
