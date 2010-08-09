package com.inthinc.pro.reports.hos;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.hos.model.HOSOrigin;
import com.inthinc.hos.model.HOSStatus;
import com.inthinc.hos.model.RuleSetType;
import com.inthinc.pro.dao.mock.data.MockStates;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.hos.HOSOccupantLog;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.model.hos.HOSVehicleDayData;
import com.inthinc.pro.reports.hos.model.HosGroupMileage;

public class BaseHosRecordUnitTest extends BaseUnitTest {
    
    class TestData {
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

        public TestData(String basePath, String baseFilename, boolean includeMileage) {
            this(basePath, baseFilename, includeMileage, true);
        }
        public TestData(String basePath, String baseFilename, boolean includeMileage, boolean filterByGraphable) {
            String values[] = baseFilename.split("_");
            readInGroupHierarchy(basePath + baseFilename + "_groups.csv", values[1]);
            readInTestDataSet(basePath + baseFilename + ".csv", values[1]);
            for (Driver driver : driverHOSRecordMap.keySet()) {
//                System.out.println("driver: " + driver.getBarcode());
                driverHOSRecordMap.put(driver, readInHOSTestDataSet(basePath + baseFilename + "_" + driver.getBarcode() + ".csv", filterByGraphable));
            }
            if (includeMileage) {
                groupMileageList = readInMileage(basePath + baseFilename + "_mileage.csv");
                groupNoDriverMileageList = readInMileage(basePath + baseFilename + "_mileageZero.csv");
            }
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
            return mileageList;
        }

        private List<HOSRecord> readInHOSTestDataSet(String filename, boolean filterGraphable) {
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
                        for (int i = 0; i < values.length; i++) {
                            if (values[i].startsWith("\"") && values[i].endsWith("\"")) {
                                values[i] = values[i].substring(1, values[i].length() - 1);
                            } else if (values[i].equals("null"))
                                values[i] = null;
                        }
                        String id = values[0];
                        HOSStatus status = HOSStatus.valueOf(Integer.valueOf(values[1]));
                        if (filterGraphable && !status.isGraphable()) {
                            continue;
                        }
                        RuleSetType ruleType = RuleSetType.valueOf(Integer.valueOf(values[2]));
                        Date logTime = new Date(Long.valueOf(values[3]).longValue());
                        TimeZone timeZone = TimeZone.getTimeZone(values[4]);
                        String vehicleName = values[5];
                        Boolean singleDriver = Boolean.valueOf(values[6]);
                        Boolean vehicleIsDOT = Boolean.valueOf(values[7]);
                        Boolean isDeleted = Boolean.valueOf(values[8]);
                        Boolean isEdited = Boolean.valueOf(values[9]);
                        Integer origin = Integer.valueOf(values[10]); 
                        Date dateAdded = new Date(Long.valueOf(values[11]).longValue());
                        String trailerID = values[12];
                        String serviceID = values[13];
                        String userID = values[14];
                        String location = values[15];
                        String originalLocation = values[16];


                        HOSRecord hosRecord = new HOSRecord();
                        hosRecord.setHosLogID(hosLogID++);
                        hosRecord.setDriverDotType(ruleType);
                        hosRecord.setLogTime(logTime);
                        hosRecord.setStatus(status);
                        hosRecord.setTimeZone(timeZone);
                        hosRecord.setVehicleName(vehicleName);
                        hosRecord.setSingleDriver(singleDriver);
                        hosRecord.setDeleted(isDeleted);
                        hosRecord.setVehicleIsDOT(vehicleIsDOT);
                        hosRecord.setEdited(isEdited);
                        hosRecord.setOrigin(HOSOrigin.valueOf(origin));
                        hosRecord.setAddedTime(dateAdded);
                        hosRecord.setTrailerID(trailerID);
                        hosRecord.setServiceID(serviceID);
                        hosRecord.setLocation(location);
                        hosRecord.setEditUserName(userID);
                        hosRecord.setOriginalLocation(originalLocation);
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
                            System.out.println("ERROR: groupID MISSING " + groupId);
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
