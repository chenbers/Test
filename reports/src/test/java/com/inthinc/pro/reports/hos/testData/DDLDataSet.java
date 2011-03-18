package com.inthinc.pro.reports.hos.testData;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

import com.inthinc.hos.model.HOSOrigin;
import com.inthinc.hos.model.HOSStatus;
import com.inthinc.hos.model.RuleSetType;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.hos.HOSOccupantLog;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.model.hos.HOSVehicleDayData;
import com.inthinc.pro.reports.util.DateTimeUtil;

public class DDLDataSet {
    public Account account;
    public Group group;
    public Driver driver ;
    public List<HOSRecord> hosRecordList;
    public Interval interval;
    public int numDays;
    public List<HOSVehicleDayData> hosVehicleDayDataList;
    public List<HOSOccupantLog> hosOccupantLogList;
    
    Map<String, Integer> vehicleNameIDMap;
    Driver occupant;
    
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
    public static final int originalStatusIdx = 26;
    
    //2010-01-29 17:11:08.0
    public  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    

    public DDLDataSet(String baseFilename) {
        account = MockData.createMockAccount();
        group = MockData.createMockGroup(account.getAcctID());
        driver = MockData.createMockDriver(account.getAcctID());
        occupant = MockData.createMockDriver(account.getAcctID(), 20, "Occupant", "Driver");

        vehicleNameIDMap = new HashMap<String, Integer>();

        hosRecordList = readInTestDataSet("ddl/" + baseFilename + ".csv", driver);

        String values[] = baseFilename.split("_");
        interval = DateTimeUtil.getStartEndInterval(values[1], values[2], "MMddyyyy", DateTimeZone.getDefault());
        
        numDays = interval.toPeriod().toStandardDays().getDays() + 1;
        
        hosOccupantLogList = genOccupantLogList(driver.getDriverID(), interval.getStart(), interval.getEnd());
    }

    private List<HOSRecord> readInTestDataSet(String filename, Driver driver) {
        
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        
        hosVehicleDayDataList = new ArrayList<HOSVehicleDayData>();
        List<HOSRecord> hosRecordList = new ArrayList<HOSRecord>();
        int cnt = 0;
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
                rec.setEdited(Boolean.valueOf(values[editedIdx]));
                rec.setEditUserName(""+values[userIdIdx]);
                rec.setHosLogID(cnt++);
                rec.setLocation(values[tmpLocationIdx]);
                rec.setLogTime(dateFormat.parse(values[logTimeIdx]));
                rec.setAddedTime(dateFormat.parse(values[dateAddedIdx]));
                rec.setOrigin(values[originIdx] == null || values[originIdx].isEmpty() ? HOSOrigin.UNKNOWN : HOSOrigin.valueOf(Integer.valueOf(values[originIdx])));
                rec.setOriginalLocation(values[originalLocationIdx]);
                rec.setServiceID(values[serviceIdIdx]);
                rec.setStatus(HOSStatus.valueOf(Integer.valueOf(values[statusIdx])));
                rec.setTimeZone(TimeZone.getTimeZone(values[tznameIdx]));
                rec.setTrailerID(values[trailerIdIdx]);
                rec.setVehicleName(values[unitIdIdx]);
                rec.setVehicleID(getVehicleID(values[unitIdIdx]));
                rec.setVehicleOdometer(values[startOdometerIdx] == null || values[startOdometerIdx].isEmpty() ? null : (Long.valueOf(values[startOdometerIdx])*100l));
                rec.setChangedCnt(0);
                rec.setTrailerGallons(0f);
                rec.setTruckGallons(0f);
                if (values.length > originalStatusIdx)
                    rec.setOriginalStatus(values[originalStatusIdx] == null ? null : HOSStatus.valueOf(Integer.valueOf(values[originalStatusIdx])));
                

                if (rec.getStatus().equals(HOSStatus.DRIVING)) {
                    DateTime day = new DateMidnight(new DateTime(rec.getLogTime().getTime(), DateTimeZone.forTimeZone(rec.getTimeZone()))).toDateTime();
                    HOSVehicleDayData data = vehicleInDay(day, values[unitIdIdx]);
                    if (data == null) {
                        
                        data = new HOSVehicleDayData();
                        data.setDay(day.toDate());
                        data.setVehicleID(rec.getVehicleID());
                        data.setVehicleName(values[unitIdIdx]);
                        data.setMilesDriven(1000l);
                        data.setStartOdometer(150000l);
                        
                        hosVehicleDayDataList.add(data);

                    
                        data = new HOSVehicleDayData();
                        data.setDay(day.toDate());
                        data.setVehicleID(rec.getVehicleID()*100);
                        data.setVehicleName(values[unitIdIdx]+ " TEST");
                        data.setMilesDriven(100l);
                        data.setStartOdometer(15000l);
                        
                        hosVehicleDayDataList.add(data);

                    }
                }
  
                hosRecordList.add(rec);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return hosRecordList;
        
    }

    private Integer getVehicleID(String vehicleName) {
        Integer id = vehicleNameIDMap.get(vehicleName);
        if (id == null) {
            id = vehicleNameIDMap.size() + 1;
            vehicleNameIDMap.put(vehicleName, id);
        }
        return id;
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

        // just add an occupant to the 1st driving record
        HOSRecord lastRec = null;
        for (HOSRecord rec : hosRecordList) {
            if (rec.getStatus().equals(HOSStatus.DRIVING)) {
                HOSOccupantLog hosOccupantLog = new HOSOccupantLog();
                hosOccupantLog.setDriverID(occupant.getDriverID());
                hosOccupantLog.setDriverName(occupant.getPerson().getFullNameLastFirst());
                hosOccupantLog.setVehicleID(rec.getVehicleID());
                hosOccupantLog.setStatus(HOSStatus.ON_DUTY_OCCUPANT);
                hosOccupantLog.setLogTime(rec.getLogTime());
                hosOccupantLog.setEndTime((lastRec == null) ? new Date() : lastRec.getLogTime());
                hosOccupantLog.setServiceID("Occupant ServiceID");
                hosOccupantLog.setTrailerID("Occupant TrailerID");
            
                hosOccupantLogList.add(hosOccupantLog);
                break;
            }
            lastRec = rec;
        }
        return hosOccupantLogList;
    }
}


