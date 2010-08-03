package com.inthinc.pro.reports.hos;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.hos.model.HOSStatus;
import com.inthinc.hos.model.RuleSetType;
import com.inthinc.hos.model.RuleViolationTypes;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.reports.FormatType;
import com.inthinc.pro.reports.Report;
import com.inthinc.pro.reports.ReportCreator;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.hos.model.DrivingTimeViolationsSummary;
import com.inthinc.pro.reports.hos.model.HosGroupMileage;
import com.inthinc.pro.reports.hos.model.HosViolationsDetail;
import com.inthinc.pro.reports.hos.model.HosViolationsSummary;
import com.inthinc.pro.reports.hos.model.NonDOTViolationsSummary;
import com.inthinc.pro.reports.hos.model.Violation;
import com.inthinc.pro.reports.jasper.JasperReport;
import com.inthinc.pro.reports.jasper.JasperReportCreator;

public class ViolationsReportCriteriaTest {
    public static final String testCaseName[] = { 
        "vtest_00_07012010_07072010", 
        "vtest_01H1_07012010_07072010",
    };
    HosViolationsSummary hosViolationsExpectedData[][] = {
            {
                new HosViolationsSummary("HOS",0,0,0,2,0,0,0,0,0,0,0,0,32,306500.0d,0.0d),
                new HosViolationsSummary("HOS->Open Hole",0,0,0,0,0,0,0,0,0,0,0,0,7,531200.0d,5300.0d),
                new HosViolationsSummary("HOS->Cased Hole",0,0,0,7,3,1,3,1,1,0,0,0,54,1296600.0d,21100.0d),
                new HosViolationsSummary("HOS->Slickline",0,0,0,0,0,0,0,0,0,0,0,0,6,133900.0d,0.0d),
                new HosViolationsSummary("HOS->Tech",0,0,0,0,0,0,0,0,0,0,0,0,5,0.0d,0.0d),
                new HosViolationsSummary("HOS->Gun Loader",0,0,0,0,0,0,0,0,0,0,0,0,8,0.0d,0.0d),
                new HosViolationsSummary("HOS->Temporary Drivers",0,0,0,0,0,0,0,0,0,0,0,0,10,0.0d,0.0d),
            },
            {
                new HosViolationsSummary("Norman Wells->Norman Wells - WS",0,0,0,4,1,0,0,0,0,3,1,1,7,26700.0d,100.0d),
                new HosViolationsSummary("Norman Wells->REW",0,0,0,0,0,0,0,0,0,0,0,0,0,15200.0d,0.0d),
            }
    };
    NonDOTViolationsSummary nonDOTViolationsExpectedData[][] = {
            {
                new NonDOTViolationsSummary("HOS",51,2),
                new NonDOTViolationsSummary("HOS->Open Hole",7,0),
                new NonDOTViolationsSummary("HOS->Cased Hole",55,0),
                new NonDOTViolationsSummary("HOS->Slickline",6,0),
                new NonDOTViolationsSummary("HOS->Tech",5,0),
                new NonDOTViolationsSummary("HOS->Gun Loader",8,0),
                new NonDOTViolationsSummary("HOS->Temporary Drivers",14,0),

            },
            {
                new NonDOTViolationsSummary("Norman Wells->Norman Wells - WS",8,0),
            }
    };
    DrivingTimeViolationsSummary drivingTimeViolationsExpectedData[][] = {
            {
                new DrivingTimeViolationsSummary("HOS",1,0,2,51),
                new DrivingTimeViolationsSummary("HOS->Open Hole",5,0,0,7),
                new DrivingTimeViolationsSummary("HOS->Cased Hole",10,4,0,55),
                new DrivingTimeViolationsSummary("HOS->Slickline",0,0,0,6),
                new DrivingTimeViolationsSummary("HOS->Tech",1,0,0,5),
                new DrivingTimeViolationsSummary("HOS->Gun Loader",0,0,0,8),
                new DrivingTimeViolationsSummary("HOS->Temporary Drivers",2,0,0,14),

            },
            {
                new DrivingTimeViolationsSummary("Norman Wells->Norman Wells - WS", 0, 6, 0, 8),
            }
    };

    
    HosViolationsDetail hosViolationsDetailExpectedData[][] = {
            {
                new HosViolationsDetail("HOS->Cased Hole",new Date(1277981363000l),"461353","Coombs,  Eric","10965839",RuleSetType.US_OIL,Arrays.asList(
                        new Violation(RuleViolationTypes.CUMMULATIVE_HOURS,14l)
                    )),
                    new HosViolationsDetail("HOS->Cased Hole",new Date(1277982212000l),"461353","Coombs,  Eric","10965839",RuleSetType.US_OIL,Arrays.asList(
                        new Violation(RuleViolationTypes.CUMMULATIVE_HOURS,11l)
                    )),
                    new HosViolationsDetail("HOS->Cased Hole",new Date(1277982907000l),"461353","Coombs,  Eric","10965839",RuleSetType.US_OIL,Arrays.asList(
                        new Violation(RuleViolationTypes.CUMMULATIVE_HOURS,14l)
                    )),
                    new HosViolationsDetail("HOS->Cased Hole",new Date(1277983787000l),"461353","Coombs,  Eric",null,RuleSetType.US_OIL,Arrays.asList(
                        new Violation(RuleViolationTypes.CUMMULATIVE_HOURS,19l)
                    )),
                    new HosViolationsDetail("HOS->Cased Hole",new Date(1277985145000l),"461353","Coombs,  Eric","10965839",RuleSetType.US_OIL,Arrays.asList(
                        new Violation(RuleViolationTypes.CUMMULATIVE_HOURS,41l)
                    )),
                    new HosViolationsDetail("HOS->Cased Hole",new Date(1278482069000l),"469874","Dewitt,  Jason","10965838",RuleSetType.US_OIL,Arrays.asList(
                        new Violation(RuleViolationTypes.ON_DUTY_HOUR,24l)
                    )),
                    new HosViolationsDetail("HOS->Cased Hole",new Date(1278484060000l),"469874","Dewitt,  Jason","10965838",RuleSetType.US_OIL,Arrays.asList(
                        new Violation(RuleViolationTypes.ON_DUTY_HOUR,15l)
                    )),
                    new HosViolationsDetail("HOS->Cased Hole",new Date(1278239817000l),"450702","Freeman,  Justin Thomas","10830717",RuleSetType.US_OIL,Arrays.asList(
                        new Violation(RuleViolationTypes.ON_DUTY_HOUR,1l)
                    )),
                    new HosViolationsDetail("HOS->Cased Hole",new Date(1278446400000l),"475707","Grover,  Peter",null,RuleSetType.US_OIL,Arrays.asList(
                        new Violation(RuleViolationTypes.ON_DUTY_HOUR,60l)
                    )),
                    new HosViolationsDetail("HOS->Cased Hole",new Date(1278423124000l),"409241","Lee,  Jeremy Joseph","11242145",RuleSetType.US_OIL,Arrays.asList(
                        new Violation(RuleViolationTypes.ON_DUTY_HOUR,5l)
                    )),
                    new HosViolationsDetail("HOS->Cased Hole",new Date(1278118747000l),"408554","Monroe,  Eddie (ED)","10965841",RuleSetType.US_OIL,Arrays.asList(
                        new Violation(RuleViolationTypes.ON_DUTY_HOUR,2l)
                    )),
                    new HosViolationsDetail("HOS",new Date(1278524792000l),"473468","Penfield,  Daniel","11134018",RuleSetType.US_OIL,Arrays.asList(
                        new Violation(RuleViolationTypes.ON_DUTY_HOUR,5l)
                    )),
                    new HosViolationsDetail("HOS",new Date(1278525340000l),"473468","Penfield,  Daniel","11134018",RuleSetType.US_OIL,Arrays.asList(
                        new Violation(RuleViolationTypes.ON_DUTY_HOUR,5l)
                    )),
                    new HosViolationsDetail("HOS->Cased Hole",new Date(1278240070000l),"370532","Retzlaff,  John E","10830717",RuleSetType.US_OIL,Arrays.asList(
                        new Violation(RuleViolationTypes.ON_DUTY_HOUR,25l)
                    )),
                    new HosViolationsDetail("HOS->Cased Hole",new Date(1278241650000l),"370532","Retzlaff,  John E","10830717",RuleSetType.US_OIL,Arrays.asList(
                        new Violation(RuleViolationTypes.ON_DUTY_HOUR,12l)
                    )),
                    new HosViolationsDetail("HOS->Cased Hole",new Date(1278154891000l),"426338","Shannon,  Dustin","10965839",RuleSetType.US_OIL,Arrays.asList(
                        new Violation(RuleViolationTypes.ON_DUTY_HOUR,10l)
                    )),
                    new HosViolationsDetail("HOS->Cased Hole",new Date(1278155970000l),"426338","Shannon,  Dustin","10965839",RuleSetType.US_OIL,Arrays.asList(
                        new Violation(RuleViolationTypes.ON_DUTY_HOUR,7l)
                    )),
                    new HosViolationsDetail("HOS->Cased Hole",new Date(1278242430000l),"451337","Smith,  Matthew Mark","10830717",RuleSetType.US_OIL,Arrays.asList(
                        new Violation(RuleViolationTypes.ON_DUTY_HOUR,2l)
                    )),
            },
            {
        new HosViolationsDetail("Norman Wells->Norman Wells - WS",new Date(1278511164000l),"01041730","Harris,  Eugene","2467",RuleSetType.CANADA_2007_60_DEGREES_OIL,Arrays.asList(
                new Violation(RuleViolationTypes.OFF_DUTY_HOUR,6l),
                new Violation(RuleViolationTypes.ON_DUTY_HOUR,6l)
            )),
            new HosViolationsDetail("Norman Wells->Norman Wells - WS",new Date(1278482400000l),"01466044","Szpuniarski,  James",null,RuleSetType.CANADA_2007_60_DEGREES_OIL,Arrays.asList(
                new Violation(RuleViolationTypes.DAILY_OFF_DUTY,142l)
            )),
            new HosViolationsDetail("Norman Wells->Norman Wells - WS",new Date(1278514452000l),"01466044","Szpuniarski,  James","2TRA02584",RuleSetType.CANADA_2007_60_DEGREES_OIL,Arrays.asList(
                new Violation(RuleViolationTypes.ON_DUTY_HOUR,12l)
            )),
            new HosViolationsDetail("Norman Wells->Norman Wells - WS",new Date(1278535495000l),"01466044","Szpuniarski,  James","2371",RuleSetType.CANADA_2007_60_DEGREES_OIL,Arrays.asList(
                new Violation(RuleViolationTypes.OFF_DUTY_HOUR,15l),
                new Violation(RuleViolationTypes.ON_DUTY_HOUR,15l)
            )),
            new HosViolationsDetail("Norman Wells->Norman Wells - WS",new Date(1278547639000l),"01466044","Szpuniarski,  James","2371",RuleSetType.CANADA_2007_60_DEGREES_OIL,Arrays.asList(
                new Violation(RuleViolationTypes.OFF_DUTY_HOUR,10l),
                new Violation(RuleViolationTypes.ON_DUTY_HOUR,10l)
            )),
            new HosViolationsDetail("Norman Wells->Norman Wells - WS",new Date(1278548506000l),"01466044","Szpuniarski,  James","2371",RuleSetType.CANADA_2007_60_DEGREES_OIL,Arrays.asList(
                new Violation(RuleViolationTypes.OFF_DUTY_HOUR,12l),
                new Violation(RuleViolationTypes.ON_DUTY_HOUR,12l)
            )),
            }
    };

    @Ignore
    @Test
    public void gainSummmaryTestCases() {
        for (int testCaseCnt = 0; testCaseCnt < testCaseName.length; testCaseCnt++) {
            ViolationsTestData violationsTestData = new ViolationsTestData(testCaseName[testCaseCnt]);
            
            // HOS VIOLATIONS
            HosViolationsSummaryReportCriteria criteria = new HosViolationsSummaryReportCriteria(Locale.US);
            criteria.initDataSet(violationsTestData.interval, violationsTestData.topGroup, violationsTestData.groupList, violationsTestData.driverHOSRecordMap,
                                    violationsTestData.groupMileageList, violationsTestData.groupNoDriverMileageList);
            List<HosViolationsSummary> dataList = criteria.getMainDataset();
            assertEquals(testCaseName[testCaseCnt] + " number of records", hosViolationsExpectedData[testCaseCnt].length, dataList.size());
            int eCnt = 0;
            for (HosViolationsSummary s : dataList)
            {
                HosViolationsSummary expected = hosViolationsExpectedData[testCaseCnt][eCnt++];
                assertEquals(testCaseName[testCaseCnt] +" driving_1 " + eCnt, expected.getDriving_1(), s.getDriving_1()); 
                assertEquals(testCaseName[testCaseCnt] +" driving_2 " + eCnt, expected.getDriving_2(), s.getDriving_2()); 
                assertEquals(testCaseName[testCaseCnt] +" driving_2 " + eCnt, expected.getDriving_3(), s.getDriving_3()); 
                assertEquals(testCaseName[testCaseCnt] +" OnDuty_1 " + eCnt, expected.getOnDuty_1(), s.getOnDuty_1()); 
                assertEquals(testCaseName[testCaseCnt] +" OnDuty_2 " + eCnt, expected.getOnDuty_2(), s.getOnDuty_2()); 
                assertEquals(testCaseName[testCaseCnt] +" OnDuty_3 " + eCnt, expected.getOnDuty_3(), s.getOnDuty_3()); 
                assertEquals(testCaseName[testCaseCnt] +" Cumulative_1 " + eCnt, expected.getCumulative_1(), s.getCumulative_1()); 
                assertEquals(testCaseName[testCaseCnt] +" Cumulative_2 " + eCnt, expected.getCumulative_2(), s.getCumulative_2()); 
                assertEquals(testCaseName[testCaseCnt] +" Cumulative_3 " + eCnt, expected.getCumulative_3(), s.getCumulative_3()); 
                assertEquals(testCaseName[testCaseCnt] +" OffDuty_1 " + eCnt, expected.getOffDuty_1(), s.getOffDuty_1()); 
                assertEquals(testCaseName[testCaseCnt] +" OffDuty_2 " + eCnt, expected.getOffDuty_2(), s.getOffDuty_2()); 
                assertEquals(testCaseName[testCaseCnt] +" OffDuty_3 " + eCnt, expected.getOffDuty_3(), s.getOffDuty_3()); 
                assertEquals(testCaseName[testCaseCnt] +" DriverCnt " + eCnt, expected.getDriverCnt(), s.getDriverCnt()); 
                assertEquals(testCaseName[testCaseCnt] +" TotalMiles " + eCnt, expected.getTotalMiles(), s.getTotalMiles()); 
                assertEquals(testCaseName[testCaseCnt] +" TotalMilesNoDriver " + eCnt, expected.getTotalMilesNoDriver(), s.getTotalMilesNoDriver()); 
            }
            
             dump("hosViolationsSummaryTest", testCaseCnt+1, criteria, FormatType.PDF);
             dump("hosViolationsSummaryTest", testCaseCnt+1, criteria, FormatType.EXCEL);

        
        
             // NON-DOT VIOLATIONS
             NonDOTViolationsSummaryReportCriteria nonDOTCriteria = new NonDOTViolationsSummaryReportCriteria(Locale.US);
             nonDOTCriteria.initDataSet(violationsTestData.interval, violationsTestData.topGroup, violationsTestData.groupList, violationsTestData.driverHOSRecordMap);
             dump("nonDOTViolationsTest", testCaseCnt+1, nonDOTCriteria, FormatType.PDF);
             dump("notDOTViolationsTest", testCaseCnt+1, nonDOTCriteria, FormatType.EXCEL);
             List<NonDOTViolationsSummary> nonDOTDataList = nonDOTCriteria.getMainDataset();
             assertEquals(testCaseName[testCaseCnt] + " number of records", hosViolationsExpectedData[testCaseCnt].length, dataList.size());
             eCnt = 0;
             for (NonDOTViolationsSummary s : nonDOTDataList)
             {
                 NonDOTViolationsSummary expected = nonDOTViolationsExpectedData[testCaseCnt][eCnt++];
                 assertEquals(testCaseName[testCaseCnt] +" driverCnt " + eCnt, expected.getDriverCnt(), s.getDriverCnt());
                 assertEquals(testCaseName[testCaseCnt] +" NonDOTDriverCount " + eCnt, expected.getNonDOTDriverCount(), s.getNonDOTDriverCount());
             }
             
             // DrivingTime VIOLATIONS
             DrivingTimeViolationsSummaryReportCriteria drivingTimeCriteria = new DrivingTimeViolationsSummaryReportCriteria(Locale.US);
             drivingTimeCriteria.initDataSet(violationsTestData.interval, violationsTestData.topGroup, violationsTestData.groupList, violationsTestData.driverHOSRecordMap);
             dump("drivingViolationsTest", testCaseCnt+1, drivingTimeCriteria, FormatType.PDF);
             dump("drivingViolationsTest", testCaseCnt+1, drivingTimeCriteria, FormatType.EXCEL);
             List<DrivingTimeViolationsSummary> drivingTimeDataList = drivingTimeCriteria.getMainDataset();
             assertEquals(testCaseName[testCaseCnt] + " number of records", hosViolationsExpectedData[testCaseCnt].length, dataList.size());
             eCnt = 0;
             for (DrivingTimeViolationsSummary s : drivingTimeDataList)
             {
                 DrivingTimeViolationsSummary expected = drivingTimeViolationsExpectedData[testCaseCnt][eCnt++];
                 assertEquals(testCaseName[testCaseCnt] +" DriverCnt " + eCnt, expected.getDriverCnt(), s.getDriverCnt());
                 assertEquals(testCaseName[testCaseCnt] +" DrivingHourBanCount " + eCnt, expected.getDrivingHourBanCount(), s.getDrivingHourBanCount());
                 assertEquals(testCaseName[testCaseCnt] +" NonDOTDriverCount " + eCnt, expected.getNonDOTDriverCount(), s.getNonDOTDriverCount());
                 assertEquals(testCaseName[testCaseCnt] +" OnDuty16HrCount " + eCnt, expected.getOnDuty16HrCount(), s.getOnDuty16HrCount());
             }
        }
    }
    @Test
    public void gainDetailsTestCases() {
        for (int testCaseCnt = 0; testCaseCnt < testCaseName.length; testCaseCnt++) {
            ViolationsTestData violationsTestData = new ViolationsTestData(testCaseName[testCaseCnt]);
            
            // HOS VIOLATIONS
            HosViolationsDetailReportCriteria criteria = new HosViolationsDetailReportCriteria(Locale.US);
            criteria.initDataSet(violationsTestData.interval, violationsTestData.topGroup, violationsTestData.groupList, violationsTestData.driverHOSRecordMap);
            List<HosViolationsDetail> dataList = criteria.getMainDataset();
//            for (HosViolationsDetail data : dataList)
//                data.dump();
            assertEquals(testCaseName[testCaseCnt] + " number of records", hosViolationsDetailExpectedData[testCaseCnt].length, dataList.size());
            int eCnt = 0;
            for (HosViolationsDetail s : dataList)
            {
                HosViolationsDetail expected = hosViolationsDetailExpectedData[testCaseCnt][eCnt++];
                assertEquals(testCaseName[testCaseCnt] +" driverName " + eCnt, expected.getDriverName(), s.getDriverName());
                assertEquals(testCaseName[testCaseCnt] +" EmployeeId " + eCnt, expected.getEmployeeId(), s.getEmployeeId());
                assertEquals(testCaseName[testCaseCnt] +" NotificationTime " + eCnt, expected.getNotificationTime(), s.getNotificationTime());
                assertEquals(testCaseName[testCaseCnt] +" ruleType " + eCnt, expected.getRuleType(), s.getRuleType());
//                assertEquals(testCaseName[testCaseCnt] +" timeStr " + eCnt, expected.getTimeStr(), s.getTimeStr());
                if (expected.getVehicleId() == null)
                    assertNull(testCaseName[testCaseCnt] +" vehicleID " + eCnt, s.getVehicleId());
                else assertEquals(testCaseName[testCaseCnt] +" vehicleID " + eCnt, expected.getVehicleId(), s.getVehicleId());
                assertEquals(testCaseName[testCaseCnt] +" violationList size " + eCnt, expected.getViolationsList().size(), s.getViolationsList().size());
                
            }
            dump("hosViolationsDetailTest", testCaseCnt+1, criteria, FormatType.PDF);
        }
    }
    
    private void dump(String prefix, int testCaseCnt, ReportCriteria reportCriteria, FormatType formatType) {
        // remove comments to get pdf or xls dump of report
        ReportCreator<JasperReport> reportCreator = new JasperReportCreator(null);
        Report report = reportCreator.getReport(reportCriteria);
        OutputStream out = null;
        try {
            out = new FileOutputStream("c:\\" + prefix + testCaseCnt + ((formatType == FormatType.PDF)? ".pdf" : ".xls"));
            report.exportReportToStream(formatType, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    class ViolationsTestData {
        Group topGroup;
        List<Group> groupList = new ArrayList<Group>();
        Map<String, Integer> groupIDMap = new HashMap<String, Integer>();
        Map<Driver, List<HOSRecord>> driverHOSRecordMap = new HashMap<Driver, List<HOSRecord>>();
        List<HosGroupMileage> groupMileageList;
        List<HosGroupMileage> groupNoDriverMileageList;
        
        DateTime startDate;
        DateTime endDate;
        Interval interval;
        int numDays;
        // 2010-01-29 17:11:08.0
        public SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        public ViolationsTestData(String baseFilename) {
            String values[] = baseFilename.split("_");
            readInGroupHierarchy("violations/" + baseFilename + "_groups.csv", values[1]);
            readInTestDataSet("violations/" + baseFilename + ".csv", values[1]);
            for (Driver driver : driverHOSRecordMap.keySet()) {
                
                driverHOSRecordMap.put(driver, readInHOSTestDataSet("violations/" + baseFilename + "_" + driver.getBarcode() + ".csv"));
            }
            groupMileageList = readInMileage("violations/" + baseFilename + "_mileage.csv");
            groupNoDriverMileageList = readInMileage("violations/" + baseFilename + "_mileageZero.csv");
            // "vtest_00_07012010_07072010",
            DateTimeFormatter fmt = DateTimeFormat.forPattern("MMddyyyy");
            startDate = new DateMidnight(fmt.parseDateTime(values[2])).toDateTime();
            endDate = new DateMidnight(fmt.parseDateTime(values[3]).plusDays(1).minusSeconds(1)).toDateTime();
            interval = new Interval(startDate, endDate);
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
                        }
                        else {
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

        private List<HosGroupMileage> readInMileage(String filename) {
            List<HosGroupMileage> mileageList = new ArrayList<HosGroupMileage>();
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
                        long mileage = Long.valueOf(values[1].replace(",", "")).longValue() * 100;
                        mileageList.add(new HosGroupMileage(calcGroupID(groupIDMap, groupId), mileage));
                    }
                }
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            return mileageList ;
        }

        private List<HOSRecord> readInHOSTestDataSet(String filename) {
            List<HOSRecord> hosRecordList = new ArrayList<HOSRecord>();
            int hosLogID = 0;
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
                            else if (values[i].equals("null"))
                                values[i] = null;
                        String id = values[0];
                        HOSStatus status = HOSStatus.valueOf(Integer.valueOf(values[1]));
                        RuleSetType ruleType = RuleSetType.valueOf(Integer.valueOf(values[2]));
                        Date logTime = new Date(Long.valueOf(values[3]).longValue());
                        TimeZone timeZone = TimeZone.getTimeZone(values[4]);
                        String vehicleName = values[5];
                        Boolean singleDriver = Boolean.valueOf(values[6]); 
                        Boolean notDOTDrivingDOT = Boolean.valueOf(values[7]);
                        
                        HOSRecord hosRecord = new HOSRecord();
                        hosRecord.setHosLogID(hosLogID++);
                        hosRecord.setDriverDotType(ruleType);
                        hosRecord.setLogTime(logTime);
                        hosRecord.setStatus(status);
                        hosRecord.setTimeZone(timeZone);
                        hosRecord.setVehicleName(vehicleName);
                        hosRecord.setSingleDriver(singleDriver);
                        hosRecord.setDeleted(false);
                        hosRecord.setVehicleIsDOT(true);
                        
                        hosRecordList.add(hosRecord);
                    }
                }
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            
            return hosRecordList;
        }

        private void readInTestDataSet(String filename, String topGroupID) {
            BufferedReader in;
            try {
                InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
                in = new BufferedReader(new InputStreamReader(stream));
                String str;
                while ((str = in.readLine()) != null) {
                    String values[] = str.split(",(?=(?:[^\"]*\"[^\"]*\")*(?![^\"]*\"))");
                    for (int i = 0; i < values.length; i++)
                        if (values[i].startsWith("\"") && values[i].endsWith("\"")) {
                            values[i] = values[i].substring(1, values[i].length() - 1);
                        }
                    String driverId = values[0];
                    String groupName = values[1];
                    String groupId = values[2];
                    String employeeId = values[3];
                    int dot = Integer.valueOf(values[4]);
                    String driverName = values[5];
                    String timeZoneID = values[6];
                    Group driverGroup = null;
                    if (groupId.equals(topGroupID)) {
                        driverGroup = topGroup;
                    } else {
                        for (Group group : groupList)
                            if (group.getName().equals(groupName)) {
                                driverGroup = group;
                                break;
                            }
                        if (driverGroup == null) {
                            System.out.println("ERROR: groupID MISSING " + groupId );                            
                        }
                    }
                    boolean found = false;
                    for (Driver driver : driverHOSRecordMap.keySet())
                        if (driver.getBarcode().equals(driverId)) {
                            found = true;
                            break;
                        }
                    if (!found) {
                        Driver driver = new Driver();
                        Person person = new Person();
                        driver.setPerson(person);
                        String[] names = driverName.split(",");
                        person.setFirst(names[1]);
                        person.setLast(names[0]);
                        person.setEmpid(employeeId);
                        person.setTimeZone(TimeZone.getTimeZone(timeZoneID));
                        driver.setGroupID(driverGroup.getGroupID());
                        driver.setDot(RuleSetType.valueOf(dot));
                        driver.setBarcode(driverId);
                        driverHOSRecordMap.put(driver, new ArrayList<HOSRecord>());
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


        private Integer calcGroupID(Map<String, Integer>groupIdMap, String gainGroupID) {
            Integer groupID = groupIDMap.get(gainGroupID);
            if (groupID == null) {
                groupID = groupIDMap.size();
                groupIDMap.put(gainGroupID, groupID);
                
            }
            return groupID;
        }
    }
}
