package com.inthinc.pro.reports.hos;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Locale;

import org.junit.Test;

import com.inthinc.pro.reports.BaseUnitTest;
import com.inthinc.pro.reports.FormatType;
import com.inthinc.pro.reports.hos.model.HosZeroMiles;
import com.inthinc.pro.reports.hos.testData.ZeroMilesDataSet;

public class HosZeroMilesReportCriteriaTest extends BaseUnitTest {
    public static final String DATA_PATH = "violations/";
    public static final String testCaseName[] = { 
        "vtest_00_07012010_07072010", 
        "vtest_01H1_07012010_07072010", 
    };
    HosZeroMiles hosZeroMilesExpectedData[][] = {
            { 
                new HosZeroMiles("HOS", "10637375", 0.0), 
                new HosZeroMiles("HOS->Cased Hole", "11242145", 700.0), 
                new HosZeroMiles("HOS->Cased Hole", "11242141", 10200.0),
                new HosZeroMiles("HOS->Cased Hole", "11134018", 7000.0), 
                new HosZeroMiles("HOS->Cased Hole", "10988010", 1300.0),
                new HosZeroMiles("HOS->Cased Hole", "10971659", 1500.0), 
                new HosZeroMiles("HOS->Cased Hole", "10965841", 0.0),
                new HosZeroMiles("HOS->Cased Hole", "10965839", 100.0), 
                new HosZeroMiles("HOS->Cased Hole", "10636331", 200.0),
                new HosZeroMiles("HOS->Open Hole", "10997413", 200.0), 
                new HosZeroMiles("HOS->Open Hole", "10830714", 5100.0), 
            },
            { 
                new HosZeroMiles("Norman Wells->Norman Wells - WS", "2371", 100.0), 
            } 
    };

    @Test
    public void gainZeroMilesTestCases() {
        for (int testCaseCnt = 0; testCaseCnt < testCaseName.length; testCaseCnt++) {
            ZeroMilesDataSet violationsTestData = new ZeroMilesDataSet(DATA_PATH, testCaseName[testCaseCnt]);
            HosZeroMilesReportCriteria criteria = new HosZeroMilesReportCriteria(Locale.US);
            criteria.initDataSet(violationsTestData.interval, violationsTestData.getGroupHierarchy(), violationsTestData.groupUnitNoDriverMileageList);
            List<HosZeroMiles> dataList = criteria.getMainDataset();
            int eCnt = 0;
            for (HosZeroMiles hosZeroMiles : dataList) {
                HosZeroMiles expected = hosZeroMilesExpectedData[testCaseCnt][eCnt++];
                assertEquals(testCaseName[testCaseCnt] + " Group " + eCnt, expected.getGroupName(), hosZeroMiles.getGroupName());
                assertEquals(testCaseName[testCaseCnt] + " Unit ID " + eCnt, expected.getUnitID(), hosZeroMiles.getUnitID());
                assertEquals(testCaseName[testCaseCnt] + " TotalMilesNoDriver " + eCnt, expected.getTotalMilesNoDriver(), hosZeroMiles.getTotalMilesNoDriver());
            }
            dump("hosZeroMilesTest", testCaseCnt + 1, criteria, FormatType.PDF);
            dump("hosZeroMilesTest", testCaseCnt + 1, criteria, FormatType.EXCEL);
        }
    }

}
