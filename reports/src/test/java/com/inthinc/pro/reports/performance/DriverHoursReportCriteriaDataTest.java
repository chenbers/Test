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
import com.inthinc.pro.reports.performance.model.DriverHours;

public class DriverHoursReportCriteriaDataTest extends BaseDriveTimeUnitTest {
    

    protected static final String DAY_FORMAT = "MM/dd/yy";

    
    @SuppressWarnings("unchecked")
    @Test
    public void gainDetailsTestCases() {
        Interval interval = initInterval();
        DateTimeFormatter dayFormatter = DateTimeFormat.forPattern(DAY_FORMAT);

        DriverHoursReportCriteria criteria = new DriverHoursReportCriteria(Locale.US);
        criteria.setDriveTimeDAO(new MockDriveTimeDAO(interval, 1));
        criteria.setDriverDAO(new MockDriverDAO());
        criteria.init(getMockGroupHierarchy(), GROUP_ID, interval);
        
        List<DriverHours> dataList = criteria.getMainDataset();
        
        assertEquals("data size", 4, dataList.size());
        DecimalFormat hoursFormatter = new DecimalFormat("###0.00"); 
        int eCnt = 1;
        for (DriverHours data : dataList) {
            data.dump();
            assertEquals("groupName " + eCnt, GROUP_NAME, data.getGroupName());
            assertEquals("driverName " + eCnt, "F"+eCnt+" L"+eCnt, data.getDriverName());
            assertEquals("day " + eCnt, dayFormatter.print(interval.getStart()), data.getDate());
            assertEquals("hours " + eCnt, hoursFormatter.format(1.0d), hoursFormatter.format(data.getHours()));
            eCnt++;
        }
        
        dump("driverHoursTest", 1, criteria, FormatType.PDF);
        dump("driverHoursTest", 1, criteria, FormatType.EXCEL);

    }
    


    
}