package com.inthinc.pro.reports.hos;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
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

public class BaseLogUnitTest extends BaseUnitTest {
    // these csv files were generated by connecting to the gain db and running the stored proc 
    //   hos_fetchLogsForDriver driverID, startDate, endDate 
    // for example:
    //   hos_fetchLogsForDriver '945463F0-44A8-4B85-9BA3-2EDAD2A9B501', '03/08/2010', '03/31/2010'
    // start date should be 20 days prior to the date you want to run the ddl report for
    // the results should be exported to csv and named test#_ddlStartDate_ddlEndDate_rule<type>
    // the csv file should be copied to test/resources/ddl and added to the list below
    // to compare run the same report for the same driver/date range in gain and copy the resulting pdf
    // to test/resources/ddl/test#Expected.pdf  -- recap, graphs, and remarks should be the same
    public static final String testCaseName[] = {
        "test0_03302010_04012010_rule2",
        "test1_01242010_01312010_rule2",
        "test2_03012010_03052010_rule2",
        "test3_06202010_06302010_rule13",
        "test4_06202010_06302010_rule7",
        "test5_06202010_06302010_rule1",
        "test6_04182010_04202010_rule10",
        "test7_06202010_06302010_rule14",
        "test8_06202010_06302010_rule6",
        "test9_06202010_06302010_rule8",
        "test10_06052010_06152010_rule9",
        "test11_01192010_01212010_rule11",
        "test12_07132010_07172010_personalTime",
        "test13_06062010_06102010_travelTimeOccupant"
    };
    class DDLTestData {
        Account account = createTestAccount();
        Group group = createTestGroup(account.getAcctID());
        Driver driver = createTestDriver(account.getAcctID());
        List<HOSRecord> hosRecordList;
        DateTime startDate; 
        DateTime endDate; 
        Interval interval;
        int numDays;
        List<HOSVehicleDayData> hosVehicleDayDataList;
        List<HOSOccupantLog> hosOccupantLogList = genOccupantLogList(driver.getDriverID(), startDate, endDate);
        
        
        public static final int statusIdx = 0;
        public static final int descriptionIdx = 1;
        public static final int tmpLocationIdx = 2;
        public static final int startOdometerIdx = 3;
        public static final int idIdx = 4;
        public static final int userIdIdx = 5;
        public static final int vehicleIdIdx = 6;
        public static final int unitIdIdx = 7;
        public static final int trailerIdIdx = 8;
        public static final int serviceIdIdx = 9;
        public static final int notificationIdIdx = 10;
        public static final int dateAddedIdx = 11;
        public static final int dateLastUpdatedIdx = 12;
        public static final int notificationTimeIdx = 13;
        public static final int localTimeIdx = 14;
        public static final int adjustedTimeIdx = 15;
        public static final int unknownIdx = 16;
        public static final int realDriverNameIdx = 17;
        public static final int originalLocationIdx = 18;
        public static final int deletedIdx = 19;
        public static final int tznameIdx = 20;
        public static final int originIdx = 21;
        public static final int editedIdx = 22;
        public static final int logTimeIdx = 23;
        public static final int ruleIdIdx = 24;
        public static final int licenseIdx = 25;
        
        //2010-01-29 17:11:08.0
        public  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        

        public DDLTestData(String baseFilename) {
            hosRecordList = readInTestDataSet("ddl/" + baseFilename + ".csv", driver);

            String values[] = baseFilename.split("_");
            DateTimeFormatter fmt = DateTimeFormat.forPattern("MMddyyyy");
            startDate = new DateMidnight(fmt.parseDateTime(values[1])).toDateTime(); 
            endDate = new DateMidnight(fmt.parseDateTime(values[2]).plusDays(1)).toDateTime(); 
            
            genVehicleDayTestData(driver.getDriverID(), startDate, endDate);
            
            interval = new Interval(startDate, endDate);
            
            numDays = interval.toPeriod().toStandardDays().getDays();
//            System.out.println("numDays: " + numDays);
        }

        private List<HOSVehicleDayData> genVehicleDayTestData(Integer driverID, DateTime startDate, DateTime endDate) {
            List<HOSVehicleDayData> hosVehicleDayDataList = new ArrayList<HOSVehicleDayData>();
            for (DateTime day = startDate; day.isBefore(endDate); day = day.plusDays(1)) {

                
                HOSVehicleDayData data = new HOSVehicleDayData();
                data.setDay(day.toDate());
                data.setVehicleID(10);
                data.setVehicleName("Test Vehicle");
                data.setMilesDriven(1000l);
                data.setStartOdometer(150000l);
                
                hosVehicleDayDataList.add(data);
            }
            
            return hosVehicleDayDataList;
        }

        private List<HOSRecord> readInTestDataSet(String filename, Driver driver) {
            
//System.out.println("filename: " + filename);            
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            
            hosVehicleDayDataList = new ArrayList<HOSVehicleDayData>();
            List<HOSRecord> hosRecordList = new ArrayList<HOSRecord>();
            int cnt = 0;
            int vehicleCnt = 0;
            BufferedReader in;
            try {
                InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
                
                in = new BufferedReader(new InputStreamReader(stream));
                // skip first line of titles
                in.readLine();
                String str; 
                while ((str = in.readLine()) != null)
                {  
                    String values[] = str.split(",(?=(?:[^\"]*\"[^\"]*\")*(?![^\"]*\"))");
                    for (int i = 0; i < values.length; i++) {
                        if (values[i].startsWith("\"") && values[i].endsWith("\"")) {
                            values[i] = values[i].substring(1, values[i].length()-1);
                        }
                    }
                    HOSRecord rec = new HOSRecord();
                    rec.setDeleted(Boolean.valueOf(values[deletedIdx]));
                    rec.setDistance(0l);
                    rec.setDriverDotType(RuleSetType.valueOf(Integer.valueOf(values[ruleIdIdx])));
                    rec.setDriverID(driver.getDriverID());
                    rec.setDriverName(driver.getPerson().getFullName());
                    rec.setEdited(Boolean.valueOf(values[editedIdx]));
                    rec.setEditUserName(""+values[userIdIdx]);
                    rec.setHosLogID(cnt++);
                    rec.setLocation(values[tmpLocationIdx]);
                    rec.setLogTime(dateFormat.parse(values[logTimeIdx]));
                    rec.setAddedTime(dateFormat.parse(values[dateAddedIdx]));
                    rec.setNotificationData(values[descriptionIdx]);
                    rec.setOrigin(values[originIdx] == null || values[originIdx].isEmpty() ? HOSOrigin.UNKNOWN : HOSOrigin.valueOf(Integer.valueOf(values[originIdx])));
                    rec.setOriginalLocation(values[originalLocationIdx]);
                    rec.setServiceID(values[serviceIdIdx]);
                    rec.setStatus(HOSStatus.valueOf(Integer.valueOf(values[statusIdx])));
                    rec.setTimeZone(TimeZone.getTimeZone(values[tznameIdx]));
//System.out.println("timeZone: " + rec.getTimeZone());                    
                    rec.setTrailerID(values[trailerIdIdx]);
                    rec.setVehicleName(values[unitIdIdx]);
                    rec.setVehicleOdometer(values[startOdometerIdx] == null || values[startOdometerIdx].isEmpty() ? null : (Long.valueOf(values[startOdometerIdx])*100l));
                    rec.setChangedCnt(0);
//System.out.println("logTime: " + values[logTimeIdx] + " adjTime: " + values[adjustedTimeIdx]);                    
                    
                    
                    DateTime day = new DateMidnight(new DateTime(rec.getLogTime().getTime(), DateTimeZone.forTimeZone(rec.getTimeZone()))).toDateTime();
                    HOSVehicleDayData data = vehicleInDay(day, values[vehicleIdIdx]);
                    if (data == null) {
                        data = new HOSVehicleDayData();
                        data.setDay(day.toDate());
                        data.setVehicleID(vehicleCnt);
                        data.setVehicleName(values[vehicleIdIdx]);
                        data.setMilesDriven(1000l);
                        data.setStartOdometer(150000l);
                        
                        hosVehicleDayDataList.add(data);
                    }
                    rec.setVehicleID(vehicleCnt);
                    
                    vehicleCnt++;
                    
      
                    hosRecordList.add(rec);
                }
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return hosRecordList;
            
        }
        private Group createTestGroup(Integer acctID) {
            Integer groupID = 15;
            Group group = new Group(groupID, acctID, "Fleet Group", 0);
            group.setAddress(new Address(15, "123 Group Street", "Suite 100", "Billings", MockStates.getByAbbrev("MT"), "59801", acctID));
            return group;
        }

        private Account createTestAccount() {
            Integer accountID = 1;
            Account account = new Account();
            account.setAcctID(accountID);
            account.setAcctName("Test Account");
            account.setAddress(new Address(1, "123 Street", "Suite 100", "Salt Lake City", MockStates.getByAbbrev("UT"), "84121", accountID));
            
            return account;
        }

        private Driver createTestDriver(Integer acctID) {
            Integer driverID = 10;
            Driver driver = new Driver();
            driver.setDriverID(driverID);
            driver.setDot(RuleSetType.US);
            
            Person person = new Person();
            person.setFirst("First");
            person.setLast("Last");
            driver.setPerson(person);
            
            return driver;
        }


        private HOSVehicleDayData vehicleInDay(DateTime day, String vehicleName) {
            
            for (HOSVehicleDayData hosVehicleDayData : hosVehicleDayDataList) {
                if (hosVehicleDayData.getDay().equals(day.toDate()) ||  hosVehicleDayData.getVehicleName().equals(vehicleName))
                    return hosVehicleDayData;
            }
            
            return null;
        }



        private List<HOSOccupantLog> genOccupantLogList(Integer driverID, DateTime startDate, DateTime endDate) {
            List<HOSOccupantLog> hosOccupantLogList = new ArrayList<HOSOccupantLog>();
            
//            HOSOccupantLog hosOccupantLog = new HOSOccupantLog();
//            hosOccupantLog.setDriverID(11);
//            hosOccupantLog.setDriverName("Test Occupant");
//            hosOccupantLog.setVehicleID(10);
//            hosOccupantLog.setStatus(HOSStatus.ON_DUTY_OCCUPANT);
//            hosOccupantLog.setTime(startDate.toDate());
//            hosOccupantLog.setEndTime(startDate.plusHours(8).toDate());
//            
//            
//            hosOccupantLogList.add(hosOccupantLog);
            return hosOccupantLogList;
        }
    }

}
