package com.inthinc.pro.reports.hos;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import com.inthinc.pro.model.Group;
import com.inthinc.pro.reports.FormatType;
import com.inthinc.pro.reports.hos.model.HosGroupUnitMileage;
import com.inthinc.pro.reports.hos.model.HosZeroMiles;
import com.inthinc.pro.reports.util.DateTimeUtil;

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
            TestData violationsTestData = new TestData(DATA_PATH, testCaseName[testCaseCnt]);
            HosZeroMilesReportCriteria criteria = new HosZeroMilesReportCriteria(Locale.US);
            criteria.initDataSet(violationsTestData.interval, violationsTestData.topGroup, violationsTestData.groupList, violationsTestData.groupUnitNoDriverMileageList);
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

    class TestData {
        Group topGroup;
        List<Group> groupList = new ArrayList<Group>();
        Map<String, Integer> groupIDMap = new HashMap<String, Integer>();
        List<HosGroupUnitMileage> groupUnitNoDriverMileageList;
        Interval interval;
        int numDays;
        // 2010-01-29 17:11:08.0
        public SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        public TestData(String basePath, String baseFilename) {
            String values[] = baseFilename.split("_");
            readInGroupHierarchy(basePath + baseFilename + "_groups.csv", values[1]);
            groupUnitNoDriverMileageList = readInNoDriverMileage(basePath + baseFilename + "_mileageZero.csv");
            // "vtest_00_07012010_07072010",
            interval = DateTimeUtil.getStartEndInterval(values[2], values[3], "MMddyyyy");
            numDays = interval.toPeriod().toStandardDays().getDays();
        }

        private void readInGroupHierarchy(String filename, String topGroupID) {
            BufferedReader in;
            try {
                InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
                if (stream != null) {
                    in = new BufferedReader(new InputStreamReader(stream));
                    String str;
                    while ((str = in.readLine()) != null) {
                        String values[] = str.split(",(?=(?:[^\"]*\"[^\"]*\")*(?![^\"]*\"))");
                        for (int i = 0; i < values.length; i++)
                            if (values[i].startsWith("\"") && values[i].endsWith("\"")) {
                                values[i] = values[i].substring(1, values[i].length() - 1);
                            }
                        String groupId = values[0];
                        String groupName = values[1];
                        if (groupId.equals(topGroupID)) {
                            topGroup = new Group();
                            topGroup.setGroupID(calcGroupID(groupIDMap, topGroupID));
                            topGroup.setParentID(-1);
                            topGroup.setName(groupName);
                        } else {
                            Group group = new Group();
                            group.setGroupID(calcGroupID(groupIDMap, groupId));
                            group.setName(groupName);
                            String parentGroupID = groupId.substring(0, groupId.length() - 1);
                            group.setParentID(calcGroupID(groupIDMap, parentGroupID));
                            groupList.add(group);
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        private List<HosGroupUnitMileage> readInNoDriverMileage(String filename) {
            List<HosGroupUnitMileage> mileageList = new ArrayList<HosGroupUnitMileage>();
            BufferedReader in;
            try {
                InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
                if (stream != null) {
                    in = new BufferedReader(new InputStreamReader(stream));
                    String str;
                    while ((str = in.readLine()) != null) {
                        String values[] = str.split(",(?=(?:[^\"]*\"[^\"]*\")*(?![^\"]*\"))");
                        for (int i = 0; i < values.length; i++)
                            if (values[i].startsWith("\"") && values[i].endsWith("\"")) {
                                values[i] = values[i].substring(1, values[i].length() - 1);
                            }
                        String groupId = values[0];
                        String unitID = values[1];
                        long mileage = Long.valueOf(values[2].replace(",", "")).longValue() * 100;
                        mileageList.add(new HosGroupUnitMileage(calcGroupID(groupIDMap, groupId), unitID, mileage));
                    }
                }
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return mileageList;
        }

        private Integer calcGroupID(Map<String, Integer> groupIdMap, String gainGroupID) {
            Integer groupID = groupIDMap.get(gainGroupID);
            if (groupID == null) {
                groupID = groupIDMap.size();
                groupIDMap.put(gainGroupID, groupID);
            }
            return groupID;
        }
    }
}
