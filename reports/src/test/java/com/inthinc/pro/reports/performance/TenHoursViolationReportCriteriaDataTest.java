package com.inthinc.pro.reports.performance;

import static org.junit.Assert.*;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.model.performance.TenHoursViolationRecord;
import com.inthinc.pro.reports.BaseUnitTest;
import com.inthinc.pro.reports.FormatType;
import com.inthinc.pro.reports.dao.impl.WaysmartDAOImpl;
import com.inthinc.pro.reports.hos.testData.HosRecordDataSet;
import com.inthinc.pro.reports.performance.model.TenHoursViolation;


public class TenHoursViolationReportCriteriaDataTest extends BaseUnitTest {
    
    // for gain data test
    public static final String DATA_PATH = "violations/";
    public static final String testCaseName = "vtest_01H1_07012010_07072010"; 
    
    TenHoursViolation[] expectedData = {
        new TenHoursViolation("Norman Wells"+ TenHoursViolationReportCriteria.SLASH_GROUP_SEPERATOR +"Norman Wells - WS","07/04/2010"," David  Francey",null,"02289734",3.0833333333333335),
        new TenHoursViolation("Norman Wells"+ TenHoursViolationReportCriteria.SLASH_GROUP_SEPERATOR +"Norman Wells - WS","07/05/2010"," David  Francey",null,"02289734",3.5166666666666666),
        new TenHoursViolation("Norman Wells"+ TenHoursViolationReportCriteria.SLASH_GROUP_SEPERATOR +"Norman Wells - WS","07/01/2010"," Scott Giem",null,"00317263",3.433333333333333),
    };

    // test using data extracted from GAIN database
    @Test
    public void gainDetailsTestCases() {
        HosRecordDataSet testData = new HosRecordDataSet(DATA_PATH, testCaseName, false);
        
        Map<Driver, List<TenHoursViolationRecord>> violationRecordMap = new HashMap<Driver, List<TenHoursViolationRecord>> ();
        for (Entry<Driver, List<HOSRecord>> dataEntry : testData.driverHOSRecordMap.entrySet()) {
            List<TenHoursViolationRecord> tenHoursViolationRecordList = new WaysmartDAOImpl().getTenHoursViolations(dataEntry.getKey(), testData.interval, dataEntry.getValue(), 120);
            violationRecordMap.put(dataEntry.getKey(), tenHoursViolationRecordList);
        }
        TenHoursViolationReportCriteria criteria = new TenHoursViolationReportCriteria(Locale.US);
        criteria.initDataSet(testData.getGroupHierarchy(), testData.interval, violationRecordMap);
        
        List<TenHoursViolation> dataList = criteria.getMainDataset();
        DecimalFormat hoursFormatter = new DecimalFormat("###0.00"); 
        int eCnt = 0;
        for (TenHoursViolation data : dataList) {
//            data.dump();
            TenHoursViolation expected = expectedData[eCnt++];
            assertEquals(testCaseName + "groupName " + eCnt, expected.getGroupName(), data.getGroupName());
            assertEquals(testCaseName + "driverName " + eCnt, expected.getDriverName(), data.getDriverName());
            assertEquals(testCaseName + "employeeID " + eCnt, expected.getEmployeeID(), data.getEmployeeID());
            assertEquals(testCaseName + "vehicleName " + eCnt, expected.getVehicleName(), data.getVehicleName());
            assertEquals(testCaseName + "day " + eCnt, expected.getDate(), data.getDate());
            assertEquals(testCaseName + "hours " + eCnt, hoursFormatter.format(expected.getHoursThisDay()), hoursFormatter.format(data.getHoursThisDay()));
        }
        
        dump("tenHoursViolationTest", 1, criteria, FormatType.PDF);
        dump("tenHoursViolationTest", 1, criteria, FormatType.EXCEL);
        
    }
    
}