package com.inthinc.pro.reports.hos;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.joda.time.Interval;
import org.junit.Test;

import com.inthinc.hos.model.HOSOrigin;
import com.inthinc.hos.model.HOSStatus;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.reports.BaseUnitTest;
import com.inthinc.pro.reports.FormatType;
import com.inthinc.pro.reports.hos.model.DriverDOTLog;
import com.inthinc.pro.reports.hos.testData.HosRecordDataSet;



public class HosDriverDOTLogReportCriteriaTest extends BaseUnitTest {

        public static final String DATA_PATH = "violations/";
        public static final String testCaseName[] = { 
            "vtest_00_07012010_07072010", 
            "vtest_01H1_07012010_07072010", 
        };
        
        
        DriverDOTLog driverDOTLogExpectedData[] = {
                new DriverDOTLog("","2010-07-07 21:24:32 MDT","7/7/10 9:24 PM","clifton colorado",null,"Knutson,  Matthew",null,null,HOSStatus.OFF_DUTY,HOSOrigin.KIOSK,false,null),
                new DriverDOTLog("","2010-07-07 05:05:18 MDT","7/7/10 5:09 AM","17.1 mi W of Meeker, CO","11242141","Knutson,  Matthew","","",HOSStatus.ON_DUTY_OCCUPANT,HOSOrigin.DEVICE,false,null),
                new DriverDOTLog("","2010-07-06 04:20:17 MDT","7/6/10 4:21 AM","Clifton, CO","10844065","Knutson,  Matthew","","",HOSStatus.ON_DUTY_OCCUPANT,HOSOrigin.DEVICE,false,null),
                new DriverDOTLog("","2010-07-06 03:42:15 MDT","7/6/10 3:42 AM","clifton colorado",null,"Knutson,  Matthew",null,null,HOSStatus.ON_DUTY,HOSOrigin.KIOSK,false,null),
                new DriverDOTLog("","2010-07-04 19:55:00 MDT","7/4/10 7:54 PM","clifton colorado",null,"Knutson,  Matthew",null,null,HOSStatus.OFF_DUTY,HOSOrigin.KIOSK,false,null),
                new DriverDOTLog("","2010-07-04 05:15:48 MDT","7/4/10 5:17 AM","Clifton, CO","10965839","Knutson,  Matthew","","",HOSStatus.ON_DUTY_OCCUPANT,HOSOrigin.DEVICE,false,null),
                new DriverDOTLog("","2010-07-04 04:36:52 MDT","7/4/10 4:36 AM","clifton colorado",null,"Knutson,  Matthew",null,null,HOSStatus.ON_DUTY,HOSOrigin.KIOSK,false,null),
                new DriverDOTLog("","2010-07-03 16:57:44 MDT","7/3/10 4:57 PM","clifton colorado",null,"Knutson,  Matthew",null,null,HOSStatus.OFF_DUTY,HOSOrigin.KIOSK,false,null),
                new DriverDOTLog("","2010-07-03 07:26:07 MDT","7/3/10 7:28 AM","17 mi NW of De Beque, CO",null,"Knutson,  Matthew",null,null,HOSStatus.ON_DUTY,HOSOrigin.DEVICE,false,null),
                new DriverDOTLog("","2010-07-03 05:00:46 MDT","7/3/10 5:02 AM","Clifton, CO","10965839","Knutson,  Matthew","","",HOSStatus.ON_DUTY_OCCUPANT,HOSOrigin.DEVICE,false,null),
                new DriverDOTLog("","2010-07-03 04:37:54 MDT","7/3/10 4:37 AM","clifton colorado",null,"Knutson,  Matthew",null,null,HOSStatus.ON_DUTY,HOSOrigin.KIOSK,false,null),
                new DriverDOTLog("","2010-07-02 19:19:16 MDT","7/2/10 7:19 PM","clifton colorado",null,"Knutson,  Matthew",null,null,HOSStatus.OFF_DUTY,HOSOrigin.KIOSK,false,null),
                new DriverDOTLog("","2010-07-02 06:10:15 MDT","7/2/10 6:10 AM","clifton colorado",null,"Knutson,  Matthew",null,null,HOSStatus.ON_DUTY,HOSOrigin.KIOSK,false,null),
                new DriverDOTLog("","2010-07-01 20:05:07 MDT","7/1/10 7:56 PM","clifton colorado",null,"Knutson,  Matthew",null,null,HOSStatus.OFF_DUTY,HOSOrigin.DEVICE,false,null),
                new DriverDOTLog("","2010-07-01 05:29:58 MDT","7/1/10 5:33 AM","Palisade, CO","10965839","Knutson,  Matthew","","",HOSStatus.ON_DUTY_OCCUPANT,HOSOrigin.DEVICE,false,null),
                new DriverDOTLog("","2010-07-01 05:29:44 MDT","7/1/10 5:31 AM","Palisade, CO",null,"Knutson,  Matthew",null,null,HOSStatus.ON_DUTY,HOSOrigin.DEVICE,false,null),
                new DriverDOTLog("","2010-07-01 05:28:59 MDT","7/1/10 5:30 AM","Palisade, CO","10965839","Knutson,  Matthew","","",HOSStatus.ON_DUTY_OCCUPANT,HOSOrigin.DEVICE,false,null),
                new DriverDOTLog("","2010-07-01 04:29:17 MDT","7/1/10 4:29 AM","clifton colorado",null,"Knutson,  Matthew",null,null,HOSStatus.ON_DUTY,HOSOrigin.KIOSK,false,null),
        };

    @Test
    public void gainTestCasesForDriverLog() {
        for (int testCaseCnt = 0; testCaseCnt < testCaseName.length; testCaseCnt++) {
            HosRecordDataSet testData = new HosRecordDataSet(DATA_PATH, testCaseName[testCaseCnt], false, false);

            HosDriverDOTLogReportCriteria hosDriverDOTLogReportCriteria = new HosDriverDOTLogReportCriteria(Locale.US);
            Map<Driver, List<HOSRecord>> dataMap = new HashMap<Driver, List<HOSRecord>>();
            for (Entry<Driver, List<HOSRecord>> entry : testData.driverHOSRecordMap.entrySet())
                dataMap.put(entry.getKey(), filterByInterval(testData.interval, entry.getValue()));
            hosDriverDOTLogReportCriteria.initDataSet(testData.interval, dataMap);
            dump("hosDriverDOTTest", testCaseCnt, hosDriverDOTLogReportCriteria, FormatType.PDF);
            dump("hosDriverDOTTest", testCaseCnt, hosDriverDOTLogReportCriteria, FormatType.EXCEL);

            // check just 1 driver's records
            if (testCaseCnt == 0) {
                dataMap = new HashMap<Driver, List<HOSRecord>>();
                for (Entry<Driver, List<HOSRecord>> entry : testData.driverHOSRecordMap.entrySet()) {
                    Driver driver = entry.getKey();
//System.out.println(driver.getPerson().getFullNameLastFirst());                    
          
                    if (driver.getPerson().getFullNameLastFirst().trim().compareTo("Knutson,  Matthew") == 0) {
                        dataMap.put(entry.getKey(), filterByInterval(testData.interval, entry.getValue()));
                        break;
                    }
                }
                hosDriverDOTLogReportCriteria.initDataSet(testData.interval, dataMap);
                
                List<DriverDOTLog> driverDOTLogList = hosDriverDOTLogReportCriteria.getMainDataset();
                int ecnt = 0;
                for (DriverDOTLog log : driverDOTLogList) {
                    //log.dump();
                    DriverDOTLog expected = driverDOTLogExpectedData[ecnt++];
                    assertEquals(testCaseName[testCaseCnt] + " changedCnt " + ecnt, expected.getChangedCnt(),log.getChangedCnt());
                    assertEquals(testCaseName[testCaseCnt] + " DriverName " + ecnt, expected.getDriverName(), log.getDriverName());
                    assertEquals(testCaseName[testCaseCnt] + " EditUserName " + ecnt, expected.getEditUserName(), log.getEditUserName());
                    assertEquals(testCaseName[testCaseCnt] + " Location " + ecnt, expected.getLocation(), log.getLocation());
                    assertEquals(testCaseName[testCaseCnt] + " Origin " + ecnt, expected.getOrigin(), log.getOrigin());
                    assertEquals(testCaseName[testCaseCnt] + " Service " + ecnt, expected.getService(), log.getService());
                    assertEquals(testCaseName[testCaseCnt] + " Status " + ecnt, expected.getStatus(), log.getStatus());
                    assertEquals(testCaseName[testCaseCnt] + " TimeAddedStr " + ecnt, expected.getTimeAddedStr(), log.getTimeAddedStr());
                    assertEquals(testCaseName[testCaseCnt] + " TimeStr " + ecnt, expected.getTimeStr(), log.getTimeStr());
                    assertEquals(testCaseName[testCaseCnt] + " Trailer " + ecnt, expected.getTrailer(), log.getTrailer());
                    assertEquals(testCaseName[testCaseCnt] + " VehicleName " + ecnt, expected.getVehicleName(), log.getVehicleName());

                }
            }
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
