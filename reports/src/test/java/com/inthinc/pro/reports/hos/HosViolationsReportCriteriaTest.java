package com.inthinc.pro.reports.hos;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.junit.Test;

import com.inthinc.hos.model.HOSStatus;
import com.inthinc.hos.model.RuleSetType;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.reports.FormatType;
import com.inthinc.pro.reports.Report;
import com.inthinc.pro.reports.ReportCreator;
import com.inthinc.pro.reports.hos.model.HosGroupMileage;
import com.inthinc.pro.reports.jasper.JasperReport;
import com.inthinc.pro.reports.jasper.JasperReportCreator;

public class HosViolationsReportCriteriaTest {
    public static final String testCaseName[] = { "vtest_00_07012010_07072010", };

    @Test
    public void gainTestCases() {
        for (int testCaseCnt = 0; testCaseCnt < testCaseName.length; testCaseCnt++) {
            ViolationsTestData violationsTestData = new ViolationsTestData(testCaseName[testCaseCnt]);
            /*            
            for (Group group : violationsTestData.groupList)
                System.out.println("Group: " + group);
            System.out.println("TopGroup:" + violationsTestData.topGroup);
            for (Group group : violationsTestData.groupList)
                System.out.println("Group: " + group);
            for (Driver driver : violationsTestData.driverHOSRecordMap.keySet()) {
                System.out.println("Driver: " + driver);
                System.out.println("Record count: " + violationsTestData.driverHOSRecordMap.get(driver).size());
            }
*/            
            
            HosViolationsSummaryReportCriteria criteria = new HosViolationsSummaryReportCriteria(Locale.US);
            criteria.initDataSet(violationsTestData.interval, violationsTestData.topGroup, violationsTestData.groupList, violationsTestData.driverHOSRecordMap,
                                    violationsTestData.groupMileageList, violationsTestData.groupNoDriverMileageList);
//            dumpToPDF(1000, criteria);
        }
    }

    private void dumpToPDF(int testCaseCnt, HosViolationsSummaryReportCriteria hosViolationsSummaryReportCriteria) {
        ReportCreator<JasperReport> reportCreator = new JasperReportCreator(null);
        Report report = reportCreator.getReport(hosViolationsSummaryReportCriteria);
        OutputStream out = null;
        try {
            out = new FileOutputStream("c:\\reportTest" + testCaseCnt + ".pdf");
            report.exportReportToStream(FormatType.PDF, out);
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
        // criteria.initDataSet(Interval interval, Group topGroup, List<Group> groupList, Map<Driver, List<HOSRecord>> driverHOSRecordMap);
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
//                System.out.println("filename: " + filename);
                if (stream != null) {
//                    System.out.println("stream exists");
                    in = new BufferedReader(new InputStreamReader(stream));
                    String str;
                    while ((str = in.readLine()) != null) {
                        String values[] = str.split(",(?=(?:[^\"]*\"[^\"]*\")*(?![^\"]*\"))");
                        for (int i = 0; i < values.length; i++)
                            if (values[i].startsWith("\"") && values[i].endsWith("\"")) {
                                values[i] = values[i].substring(1, values[i].length() - 1);
                            }
                        
    //                    6AC094CF-96AC-46BC-BE81-C6F5DC039341,8,2,1276743078000,US/Mountain,10724356,true,false
    //                    AC43A0FD-F258-4932-8070-B8C6AE7ACDE3,7,2,1276726937000,US/Mountain,10724356,true,false
    //                    csv.println(id + "," + status + "," + ruleType + "," + ts.getTime() + "," + timezoneID + "," + vehicleID + "," + singleDriver + "," + nonDOTDriverDrivingDOTVehicle);
    
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
                        if (topGroup == null) {
                            topGroup = new Group();
                            topGroup.setGroupID(calcGroupID(groupIDMap, groupId));
                            topGroup.setParentID(calcGroupID(groupIDMap, groupId.substring(0, groupId.length() - 1)));
                            topGroup.setName(groupName);
                        }
                        driverGroup = topGroup;
                    } else {
                        for (Group group : groupList)
                            if (group.getName().equals(groupName)) {
                                driverGroup = group;
                                break;
                            }
                        if (driverGroup == null) {
                            driverGroup = new Group();
                            driverGroup.setGroupID(calcGroupID(groupIDMap, groupId));
                            driverGroup.setName(groupName);
                            String parentGroupID = groupId.substring(0, groupId.length() - 1);
                            if (groupIDMap.get(parentGroupID) == null) {
                                Group parentGroup = new Group();
                                parentGroup.setGroupID(calcGroupID(groupIDMap, parentGroupID));
                                parentGroup.setName("Generated " + parentGroupID);
                                groupList.add(parentGroup);
                                
                                
                            }
                            driverGroup.setParentID(calcGroupID(groupIDMap, parentGroupID));
//System.out.println("groupID " + driverGroup.getGroupID());                            
                            groupList.add(driverGroup);
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
