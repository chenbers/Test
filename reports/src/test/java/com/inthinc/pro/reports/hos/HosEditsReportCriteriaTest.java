package com.inthinc.pro.reports.hos;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.junit.Test;

import com.inthinc.hos.model.HOSStatus;
import com.inthinc.pro.reports.BaseUnitTest;
import com.inthinc.pro.reports.FormatType;
import com.inthinc.pro.reports.hos.model.HosEdit;
import com.inthinc.pro.reports.hos.testData.HosRecordDataSet;


public class HosEditsReportCriteriaTest extends BaseUnitTest {
    
    public static final String DATA_PATH = "hos/";

    private static final String testCaseName[] = { 
        "vtest_000_07172010_08062010", 
    };
    
    HosEdit hosEditExpectedData[][] = {
            {
                new HosEdit("Open Hole","Davis,  Rod","292029",HOSStatus.OFF_DUTY,"2010-07-24 16:00","2010-07-24 16:00","182A7B77-D97D-4B4E-9484-E582E2159537",new Date(1280008800000l),"2010-07-26 15:33:32.730","2010-07-26 15:33:32.730")
            },
    };

    
    
    @Test
    public void gainTestCasesForHosEdits() {

      for (int testCaseCnt = 0; testCaseCnt < testCaseName.length; testCaseCnt++) {
            HosRecordDataSet testData = new HosRecordDataSet(DATA_PATH, testCaseName[testCaseCnt], false);
            HosEditsReportCriteria hosEditsReportCriteria = new HosEditsReportCriteria(Locale.US);
            hosEditsReportCriteria.initDataSet(testData.getGroupHierarchy(), testData.interval, testData.driverHOSRecordMap);
            
            List<HosEdit> dataList= hosEditsReportCriteria.getMainDataset();
//            System.out.println("{");
//            for (HosEdit data : dataList) {
//                data.dump();
//            }
//            System.out.println("},");
            
            int ecnt = 0;
            for (HosEdit data : dataList) {
                HosEdit expectedData = hosEditExpectedData[testCaseCnt][ecnt];
                assertEquals(testCaseName[testCaseCnt] + " " + ecnt + " groupName", expectedData.getGroupName(), data.getGroupName());
                assertEquals(testCaseName[testCaseCnt] + " " + ecnt + " driverName", expectedData.getDriverName(), data.getDriverName());
                assertEquals(testCaseName[testCaseCnt] + " " + ecnt + " employeeID", expectedData.getEmployeeID(), data.getEmployeeID());
                assertEquals(testCaseName[testCaseCnt] + " " + ecnt + " logTime", expectedData.getLogTime(), data.getLogTime());
                assertEquals(testCaseName[testCaseCnt] + " " + ecnt + " adjustedTime", expectedData.getAdjustedTime(), data.getAdjustedTime());
                assertEquals(testCaseName[testCaseCnt] + " " + ecnt + " editUserName", expectedData.getEditUserName(), data.getEditUserName());
                assertEquals(testCaseName[testCaseCnt] + " " + ecnt + " logDate", expectedData.getLogDate(), data.getLogDate());
                assertEquals(testCaseName[testCaseCnt] + " " + ecnt + " status", expectedData.getStatus(), data.getStatus());
                assertEquals(testCaseName[testCaseCnt] + " " + ecnt + " dateAdded", expectedData.getDateAdded(), data.getDateAdded());
                assertEquals(testCaseName[testCaseCnt] + " " + ecnt + " dateLastUpdated", expectedData.getDateLastUpdated(), data.getDateLastUpdated());
                ecnt++;
            }
            dump("hosEditsTest", testCaseCnt, hosEditsReportCriteria, FormatType.PDF);
            dump("hosEditsTest", testCaseCnt, hosEditsReportCriteria, FormatType.EXCEL);

        }
    }
}
