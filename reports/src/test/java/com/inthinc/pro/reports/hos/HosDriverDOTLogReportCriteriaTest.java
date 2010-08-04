package com.inthinc.pro.reports.hos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.joda.time.Interval;
import org.junit.Test;

import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.reports.FormatType;



public class HosDriverDOTLogReportCriteriaTest extends BaseLogUnitTest {
    
    @Test
    public void gainTestCasesForDriverLog() {

      for (int testCaseCnt = 0; testCaseCnt < testCaseName.length; testCaseCnt++) {
//      for (int testCaseCnt = 4; testCaseCnt < 7; testCaseCnt++) {
            DDLTestData ddlTestData = new DDLTestData(testCaseName[testCaseCnt]);
            HosDriverDOTLogReportCriteria hosDriverDOTLogReportCriteria = new HosDriverDOTLogReportCriteria(Locale.US);
            Map<Driver, List<HOSRecord>> dataMap = new HashMap<Driver, List<HOSRecord>>();
            dataMap.put(ddlTestData.driver, filterByInterval(ddlTestData.interval, ddlTestData.hosRecordList));
            Interval interval = new Interval(ddlTestData.interval.getStart(), ddlTestData.interval.getEnd().minusSeconds(1));
            hosDriverDOTLogReportCriteria.initDataSet(interval, dataMap);
            dump("hosDriverDOTTest", testCaseCnt, hosDriverDOTLogReportCriteria, FormatType.PDF);
            dump("hosDriverDOTTest", testCaseCnt, hosDriverDOTLogReportCriteria, FormatType.EXCEL);

        }
    }

    private List<HOSRecord> filterByInterval(Interval interval, List<HOSRecord> hosRecordList) {
        List<HOSRecord> filteredHosRecordList = new ArrayList<HOSRecord>();
        for (HOSRecord hosRecord : hosRecordList) {
            if (hosRecord.getLogTime().after(interval.getStart().toDate()) && hosRecord.getLogTime().before(interval.getEnd().toDate())) {
                filteredHosRecordList.add(hosRecord);
            }
            
        }
        return filteredHosRecordList;
    }
}
