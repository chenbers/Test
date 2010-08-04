package com.inthinc.pro.reports.hos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.hos.model.HOSOrigin;
import com.inthinc.hos.model.HOSRecAdjusted;
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
import com.inthinc.pro.reports.FormatType;
import com.inthinc.pro.reports.Report;
import com.inthinc.pro.reports.ReportCreator;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.hos.model.HosDailyDriverLog;
import com.inthinc.pro.reports.hos.model.Recap;
import com.inthinc.pro.reports.hos.model.RecapCanada;
import com.inthinc.pro.reports.hos.model.RecapCanada2007;
import com.inthinc.pro.reports.hos.model.RecapType;
import com.inthinc.pro.reports.hos.model.RecapUS;
import com.inthinc.pro.reports.jasper.JasperReport;
import com.inthinc.pro.reports.jasper.JasperReportCreator;

public class HosDriverDailyLogReportCriteriaTest extends BaseLogUnitTest{

    
    public static final int testDay[] = {
        0,
        0,
        0,
        1,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0
    };
    
    public HOSRecAdjusted expectedAdjustedList[][]  = {
            {     // test0_03302010_04012010_rule2
                new HOSRecAdjusted("generated",HOSStatus.ON_DUTY,new Date(1269928800000l),TimeZone.getTimeZone("UTC"),new Date(1269928800000l),495l,0,33,false,"","",488l,RuleSetType.US_OIL),
                new HOSRecAdjusted("1",HOSStatus.OFF_DUTY,new Date(1269958500000l),TimeZone.getTimeZone("UTC"),new Date(1269958500000l),90l,33,6,false,"","",103l,RuleSetType.US_OIL),
                new HOSRecAdjusted("0",HOSStatus.OFF_DUTY,new Date(1269963900000l),TimeZone.getTimeZone("UTC"),new Date(1269963900000l),-1l,39,57,false,"","",3728l,RuleSetType.US_OIL),
            },
            {     // test1_01242010_01312010_rule2
                new HOSRecAdjusted("generated",HOSStatus.OFF_DUTY,new Date(1264316400000l),TimeZone.getTimeZone("UTC"),new Date(1264316400000l),1440l,0,96,false,"","",1440l,RuleSetType.US_OIL),
            },
            {     // test2_03012010_03052010_rule2
                new HOSRecAdjusted("generated",HOSStatus.OFF_DUTY,new Date(1267426800000l),TimeZone.getTimeZone("UTC"),new Date(1267426800000l),1440l,0,96,false,"","",1440l,RuleSetType.US_OIL),
            },
            {     // test3_06202010_06302010_rule13
                new HOSRecAdjusted("generated",HOSStatus.OFF_DUTY,new Date(1277103600000l),TimeZone.getTimeZone("UTC"),new Date(1277103600000l),360l,0,24,false,"","",357l,RuleSetType.CANADA_2007_OIL),
                new HOSRecAdjusted("19",HOSStatus.ON_DUTY,new Date(1277125200000l),TimeZone.getTimeZone("UTC"),new Date(1277125200000l),15l,24,1,false,"","",17l,RuleSetType.CANADA_2007_OIL),
                new HOSRecAdjusted("17",HOSStatus.ON_DUTY,new Date(1277126100000l),TimeZone.getTimeZone("UTC"),new Date(1277126100000l),15l,25,1,false,"380039","",12l,RuleSetType.CANADA_2007_OIL),
                new HOSRecAdjusted("16",HOSStatus.DRIVING,new Date(1277127000000l),TimeZone.getTimeZone("UTC"),new Date(1277127000000l),0l,26,0,false,"380039","",6l,RuleSetType.CANADA_2007_OIL),
                new HOSRecAdjusted("15",HOSStatus.ON_DUTY,new Date(1277127000000l),TimeZone.getTimeZone("UTC"),new Date(1277127000000l),45l,26,3,false,"380039","",34l,RuleSetType.CANADA_2007_OIL),
                new HOSRecAdjusted("14",HOSStatus.DRIVING,new Date(1277129700000l),TimeZone.getTimeZone("UTC"),new Date(1277129700000l),45l,29,3,false,"380039","",57l,RuleSetType.CANADA_2007_OIL),
                new HOSRecAdjusted("12",HOSStatus.ON_DUTY,new Date(1277132400000l),TimeZone.getTimeZone("UTC"),new Date(1277132400000l),345l,32,23,false,"380039","",337l,RuleSetType.CANADA_2007_OIL),
                new HOSRecAdjusted("11",HOSStatus.DRIVING,new Date(1277153100000l),TimeZone.getTimeZone("UTC"),new Date(1277153100000l),60l,55,4,false,"380039","",56l,RuleSetType.CANADA_2007_OIL),
                new HOSRecAdjusted("9",HOSStatus.ON_DUTY,new Date(1277156700000l),TimeZone.getTimeZone("UTC"),new Date(1277156700000l),0l,59,0,false,"380039","",0l,RuleSetType.CANADA_2007_OIL),
                new HOSRecAdjusted("8",HOSStatus.DRIVING,new Date(1277156700000l),TimeZone.getTimeZone("UTC"),new Date(1277156700000l),0l,59,0,false,"380039","",11l,RuleSetType.CANADA_2007_OIL),
                new HOSRecAdjusted("7",HOSStatus.ON_DUTY,new Date(1277156700000l),TimeZone.getTimeZone("UTC"),new Date(1277156700000l),15l,59,1,false,"380039","",8l,RuleSetType.CANADA_2007_OIL),
                new HOSRecAdjusted("6",HOSStatus.DRIVING,new Date(1277157600000l),TimeZone.getTimeZone("UTC"),new Date(1277157600000l),0l,60,0,false,"380039","",1l,RuleSetType.CANADA_2007_OIL),
                new HOSRecAdjusted("4",HOSStatus.ON_DUTY,new Date(1277157600000l),TimeZone.getTimeZone("UTC"),new Date(1277157600000l),30l,60,2,false,"380039","",35l,RuleSetType.CANADA_2007_OIL),
                new HOSRecAdjusted("3",HOSStatus.OFF_DUTY,new Date(1277159400000l),TimeZone.getTimeZone("UTC"),new Date(1277159400000l),12555l,62,34,false,"","",12552l,RuleSetType.CANADA_2007_OIL),
            },
            {     // test4_06202010_06302010_rule7
                new HOSRecAdjusted("generated",HOSStatus.OFF_DUTY,new Date(1277010000000l),TimeZone.getTimeZone("UTC"),new Date(1277010000000l),1440l,0,96,false,"","",1440l,RuleSetType.TEXAS),
            },
            {     // test5_06202010_06302010_rule1
                new HOSRecAdjusted("generated",HOSStatus.OFF_DUTY,new Date(1277010000000l),TimeZone.getTimeZone("UTC"),new Date(1277010000000l),1440l,0,96,false,"","",1440l,RuleSetType.US),
            },
            {     // test6_04182010_04202010_rule10
                new HOSRecAdjusted("generated",HOSStatus.OFF_DUTY,new Date(1271570400000l),TimeZone.getTimeZone("UTC"),new Date(1271570400000l),1440l,0,96,false,"","",1440l,RuleSetType.NON_DOT),
            },
            {     // test7_06202010_06302010_rule14
                new HOSRecAdjusted("generated",HOSStatus.OFF_DUTY,new Date(1277013600000l),TimeZone.getTimeZone("UTC"),new Date(1277013600000l),660l,0,44,false,"","",656l,RuleSetType.CANADA_2007_60_DEGREES_OIL),
                new HOSRecAdjusted("46",HOSStatus.ON_DUTY,new Date(1277053200000l),TimeZone.getTimeZone("UTC"),new Date(1277053200000l),0l,44,0,false,"","",0l,RuleSetType.CANADA_2007_60_DEGREES_OIL),
                new HOSRecAdjusted("45",HOSStatus.DRIVING,new Date(1277053200000l),TimeZone.getTimeZone("UTC"),new Date(1277053200000l),0l,44,0,false,"","",5l,RuleSetType.CANADA_2007_60_DEGREES_OIL),
                new HOSRecAdjusted("44",HOSStatus.ON_DUTY,new Date(1277053200000l),TimeZone.getTimeZone("UTC"),new Date(1277053200000l),0l,44,0,false,"","",1l,RuleSetType.CANADA_2007_60_DEGREES_OIL),
                new HOSRecAdjusted("42",HOSStatus.DRIVING,new Date(1277053200000l),TimeZone.getTimeZone("UTC"),new Date(1277053200000l),15l,44,1,false,"","",5l,RuleSetType.CANADA_2007_60_DEGREES_OIL),
                new HOSRecAdjusted("41",HOSStatus.ON_DUTY,new Date(1277054100000l),TimeZone.getTimeZone("UTC"),new Date(1277054100000l),30l,45,2,false,"","",34l,RuleSetType.CANADA_2007_60_DEGREES_OIL),
                new HOSRecAdjusted("40",HOSStatus.DRIVING,new Date(1277055900000l),TimeZone.getTimeZone("UTC"),new Date(1277055900000l),15l,47,1,false,"","",10l,RuleSetType.CANADA_2007_60_DEGREES_OIL),
                new HOSRecAdjusted("39",HOSStatus.ON_DUTY,new Date(1277056800000l),TimeZone.getTimeZone("UTC"),new Date(1277056800000l),60l,48,4,false,"","",61l,RuleSetType.CANADA_2007_60_DEGREES_OIL),
                new HOSRecAdjusted("38",HOSStatus.DRIVING,new Date(1277060400000l),TimeZone.getTimeZone("UTC"),new Date(1277060400000l),0l,52,0,false,"","",4l,RuleSetType.CANADA_2007_60_DEGREES_OIL),
                new HOSRecAdjusted("37",HOSStatus.OFF_DUTY,new Date(1277060400000l),TimeZone.getTimeZone("UTC"),new Date(1277060400000l),1170l,52,44,false,"","",1164l,RuleSetType.CANADA_2007_60_DEGREES_OIL),
            },
            {     // test8_06202010_06302010_rule6
                new HOSRecAdjusted("generated",HOSStatus.OFF_DUTY,new Date(1277006400000l),TimeZone.getTimeZone("UTC"),new Date(1277006400000l),495l,0,33,false,"","",496l,RuleSetType.CANADA_HOME_OFFICE),
                new HOSRecAdjusted("50",HOSStatus.ON_DUTY_OCCUPANT,new Date(1277036100000l),TimeZone.getTimeZone("UTC"),new Date(1277036100000l),30l,33,2,false,"0","",21l,RuleSetType.CANADA_HOME_OFFICE),
                new HOSRecAdjusted("49",HOSStatus.OFF_DUTY,new Date(1277037900000l),TimeZone.getTimeZone("UTC"),new Date(1277037900000l),225l,35,15,false,"","",232l,RuleSetType.CANADA_HOME_OFFICE),
                new HOSRecAdjusted("48",HOSStatus.ON_DUTY_OCCUPANT,new Date(1277051400000l),TimeZone.getTimeZone("UTC"),new Date(1277051400000l),60l,50,4,false,"","",61l,RuleSetType.CANADA_HOME_OFFICE),
                new HOSRecAdjusted("47",HOSStatus.OFF_DUTY,new Date(1277055000000l),TimeZone.getTimeZone("UTC"),new Date(1277055000000l),1260l,54,42,false,"","",1254l,RuleSetType.CANADA_HOME_OFFICE),
            },
            {     // test9_06202010_06302010_rule8
                new HOSRecAdjusted("generated",HOSStatus.OFF_DUTY,new Date(1277013600000l),TimeZone.getTimeZone("UTC"),new Date(1277013600000l),1440l,0,96,false,"","",1440l,RuleSetType.CANADA_ALBERTA),
            },
            {     // test10_06052010_06152010_rule9
                new HOSRecAdjusted("generated",HOSStatus.OFF_DUTY,new Date(1275710400000l),TimeZone.getTimeZone("UTC"),new Date(1275710400000l),540l,0,36,false,"","",541l,RuleSetType.CANADA_2007_CYCLE_1),
                new HOSRecAdjusted("35",HOSStatus.ON_DUTY,new Date(1275742800000l),TimeZone.getTimeZone("UTC"),new Date(1275742800000l),0l,36,0,false,"1","",0l,RuleSetType.CANADA_2007_CYCLE_1),
                new HOSRecAdjusted("34",HOSStatus.DRIVING,new Date(1275742800000l),TimeZone.getTimeZone("UTC"),new Date(1275742800000l),15l,36,1,false,"1","",6l,RuleSetType.CANADA_2007_CYCLE_1),
                new HOSRecAdjusted("33",HOSStatus.ON_DUTY,new Date(1275743700000l),TimeZone.getTimeZone("UTC"),new Date(1275743700000l),0l,37,0,false,"1","",0l,RuleSetType.CANADA_2007_CYCLE_1),
                new HOSRecAdjusted("32",HOSStatus.DRIVING,new Date(1275743700000l),TimeZone.getTimeZone("UTC"),new Date(1275743700000l),30l,37,2,false,"1","",39l,RuleSetType.CANADA_2007_CYCLE_1),
                new HOSRecAdjusted("30",HOSStatus.OFF_DUTY,new Date(1275745500000l),TimeZone.getTimeZone("UTC"),new Date(1275745500000l),480l,39,32,false,"","",470l,RuleSetType.CANADA_2007_CYCLE_1),
                new HOSRecAdjusted("28",HOSStatus.ON_DUTY,new Date(1275774300000l),TimeZone.getTimeZone("UTC"),new Date(1275774300000l),0l,71,0,false,"1","",0l,RuleSetType.CANADA_2007_CYCLE_1),
                new HOSRecAdjusted("27",HOSStatus.DRIVING,new Date(1275774300000l),TimeZone.getTimeZone("UTC"),new Date(1275774300000l),30l,71,2,false,"1","",41l,RuleSetType.CANADA_2007_CYCLE_1),
                new HOSRecAdjusted("26",HOSStatus.OFF_DUTY,new Date(1275776100000l),TimeZone.getTimeZone("UTC"),new Date(1275776100000l),705l,73,23,false,"","",695l,RuleSetType.CANADA_2007_CYCLE_1),
            },
            {     // test11_01192010_01212010_rule11
                new HOSRecAdjusted("generated",HOSStatus.OFF_DUTY,new Date(1263873600000l),TimeZone.getTimeZone("UTC"),new Date(1263873600000l),1440l,0,96,false,"","",1440l,RuleSetType.CANADA_2007_60_DEGREES_CYCLE_1),
            },
            {     // test12_07132010_07172010_personalTime
                new HOSRecAdjusted("generated",HOSStatus.OFF_DUTY,new Date(1279000800000l),TimeZone.getTimeZone("UTC"),new Date(1279000800000l),405l,0,27,false,"","",412l,RuleSetType.CANADA_2007_CYCLE_1),
                new HOSRecAdjusted("17",HOSStatus.ON_DUTY_OCCUPANT,new Date(1279025100000l),TimeZone.getTimeZone("UTC"),new Date(1279025100000l),705l,27,47,false,"","",701l,RuleSetType.CANADA_2007_CYCLE_1),
                new HOSRecAdjusted("15",HOSStatus.OFF_DUTY,new Date(1279067400000l),TimeZone.getTimeZone("UTC"),new Date(1279067400000l),690l,74,22,false,"","",691l,RuleSetType.CANADA_2007_CYCLE_1),
            },
            {     // test13_06062010_06102010_travelTimeOccupant
                new HOSRecAdjusted("generated",HOSStatus.OFF_DUTY,new Date(1275804000000l),TimeZone.getTimeZone("UTC"),new Date(1275804000000l),1020l,0,68,true,"","",1021l,RuleSetType.US_OIL),
                new HOSRecAdjusted("55",HOSStatus.ON_DUTY,new Date(1275865200000l),TimeZone.getTimeZone("UTC"),new Date(1275865200000l),0l,68,0,true,"","",1l,RuleSetType.US_OIL),
                new HOSRecAdjusted("54",HOSStatus.TRAVELTIME_OCCUPANT,new Date(1275865200000l),TimeZone.getTimeZone("UTC"),new Date(1275865200000l),180l,68,12,true,"","",174l,RuleSetType.US_OIL),
                new HOSRecAdjusted("53",HOSStatus.OFF_DUTY,new Date(1275876000000l),TimeZone.getTimeZone("UTC"),new Date(1275876000000l),615l,80,16,false,"","",624l,RuleSetType.US_OIL),
            },
    };
    
    public HOSRecAdjusted expectedOriginalList[][]  = {
            {     // test0_03302010_04012010_rule2
            },
            {     // test1_01242010_01312010_rule2
            },
            {     // test2_03012010_03052010_rule2
            },
            {     // test3_06202010_06302010_rule13
            },
            {     // test4_06202010_06302010_rule7
            },
            {     // test5_06202010_06302010_rule1
            },
            {     // test6_04182010_04202010_rule10
            },
            {     // test7_06202010_06302010_rule14
            },
            {     // test8_06202010_06302010_rule6
            },
            {     // test9_06202010_06302010_rule8
            },
            {     // test10_06052010_06152010_rule9
            },
            {     // test11_01192010_01212010_rule11
            },
            {     // test12_07132010_07172010_personalTime
            },
            {     // test13_06062010_06102010_travelTimeOccupant
                new HOSRecAdjusted("generated",HOSStatus.ON_DUTY,new Date(1275804000000l),TimeZone.getTimeZone("UTC"),new Date(1275804000000l),1200l,0,80,false,"","",1196l,RuleSetType.US_OIL),
                new HOSRecAdjusted("53",HOSStatus.OFF_DUTY,new Date(1275876000000l),TimeZone.getTimeZone("UTC"),new Date(1275876000000l),615l,80,16,false,"","",624l,RuleSetType.US_OIL),
            },
    };
    public Boolean expectedEdited[] = {
            Boolean.FALSE,
            Boolean.FALSE,
            Boolean.FALSE,
            Boolean.FALSE,
            Boolean.FALSE,
            Boolean.FALSE,
            Boolean.FALSE,
            Boolean.FALSE,
            Boolean.FALSE,
            Boolean.FALSE,
            Boolean.FALSE,
            Boolean.FALSE,
            Boolean.FALSE,
            Boolean.TRUE,
    };
    
    ExpectedRecap expectedRecap[] = {
            //0 test0_03302010_04012010_rule2
        new ExpectedRecap(RecapType.US,RuleSetType.US_OIL,3,"08.25","00.00","-","177.25","00.00","","",0),
            // 1 test1_01242010_01312010_rule2
        new ExpectedRecap(RecapType.US,RuleSetType.US_OIL,1,"00.00","70.00","-","00.00","70.00","","",0),
            //2 test2_03012010_03052010_rule2
        new ExpectedRecap(RecapType.US,RuleSetType.US_OIL,2,"00.00","70.00","-","00.00","70.00","","",0),
            //3 test3_06202010_06302010_rule13
        null, 
            //4 test4_06202010_06302010_rule7
        new ExpectedRecap(RecapType.US,RuleSetType.TEXAS,1,"00.00","70.00","-","00.00","70.00","","",0),
            //5 test5_06202010_06302010_rule1
        new ExpectedRecap(RecapType.US,RuleSetType.US,1,"00.00","70.00","-","00.00","70.00","","",0),
            //6 test6_04182010_04202010_rule10
        null, 
            //7 test7_06202010_06302010_rule14
        null, 
            //8 test8_06202010_06302010_rule6
        new ExpectedRecap(RecapType.CANADA,RuleSetType.CANADA_HOME_OFFICE,14,"01.50","119.25","01.75","01.75","117.75","02.25","",0),
            //9 test9_06202010_06302010_rule8
        null, 
            //10 test10_06052010_06152010_rule9
        new ExpectedRecap(RecapType.CANADA_2007,RuleSetType.CANADA_2007_CYCLE_1,5,"01.25","44.75","","","68.75","","26.50",1),
            //11 test11_01192010_01212010_rule11
        new ExpectedRecap(RecapType.CANADA_2007,RuleSetType.CANADA_2007_60_DEGREES_CYCLE_1,1,"00.00","80.00","","","80.00","","00.00",1),
            //12 test12_07132010_07172010_personalTime
        new ExpectedRecap(RecapType.CANADA_2007,RuleSetType.CANADA_2007_CYCLE_1,2,"11.75","59.75","","","48.00","","22.00",1),
            //13 test13_06062010_06102010_travelTimeOccupant
        new ExpectedRecap(RecapType.US,RuleSetType.US_OIL,1,"00.00","70.00","-","00.00","70.00","","",0),
    };
    
    @Ignore
    @Test
    public void gainTestCases() {

        for (int testCaseCnt = 0; testCaseCnt < testCaseName.length; testCaseCnt++) {
            DDLTestData ddlTestData = new DDLTestData(testCaseName[testCaseCnt]);
            HosDailyDriverLogReportCriteria hosDailyDriverLogReportCriteria = new HosDailyDriverLogReportCriteria(Locale.US, Boolean.FALSE);
            hosDailyDriverLogReportCriteria.initCriteriaList(ddlTestData.interval, ddlTestData.hosRecordList, ddlTestData.hosVehicleDayDataList,
                ddlTestData.hosOccupantLogList, ddlTestData.driver, ddlTestData.account, ddlTestData.group);
            
            // check the data
            List<ReportCriteria> criteriaList = hosDailyDriverLogReportCriteria.getCriteriaList();
            assertEquals("expected one ReportCriteria item for each day", ddlTestData.numDays, criteriaList.size());
            
            int dayIdx = testDay[testCaseCnt];
            HosDailyDriverLog hosDailyDriverLog = (HosDailyDriverLog)criteriaList.get(dayIdx).getMainDataset().get(0);
            
            List<HOSRecAdjusted> correctedList =  hosDailyDriverLog.getCorrectedGraphList();
            assertEquals("corrected list size ", expectedAdjustedList[testCaseCnt].length, correctedList.size());
            int recCnt = 0;
            for (HOSRecAdjusted adjustedRec : correctedList) {
                
                compareHOSRecAdjusted(expectedAdjustedList[testCaseCnt][recCnt], adjustedRec, recCnt, testCaseCnt);
                recCnt++;
            }
            
            assertEquals("day edited testcase " + testCaseCnt + " " +testCaseName[testCaseCnt], expectedEdited[testCaseCnt], hosDailyDriverLog.getEdited());
            
            List<HOSRecAdjusted> originalList = hosDailyDriverLog.getOriginalGraphList();
            if (hosDailyDriverLog.getEdited()) {
                recCnt = 0;
                for (HOSRecAdjusted originalRec : originalList) {
                    compareHOSRecAdjusted(expectedOriginalList[testCaseCnt][recCnt], originalRec, recCnt, testCaseCnt);
                    recCnt++;
                }
            }
            else {
                assertNull("original list should be null for " + testCaseCnt + " " +testCaseName[testCaseCnt], originalList);
            }
            
            
            List<Recap> recapList = hosDailyDriverLog.getRecap();
            if (recapList == null) {
                assertEquals("expected null recap for " + testCaseCnt + " " +testCaseName[testCaseCnt], expectedRecap[testCaseCnt], null);
            }
            else {
                Recap baseRecap = recapList.get(0);
                assertEquals("expected recap type for " + testCaseCnt + " " +testCaseName[testCaseCnt], expectedRecap[testCaseCnt].recapType, baseRecap.getRecapType());
                assertEquals("expected ruleset recap type for " + testCaseCnt + " " +testCaseName[testCaseCnt], expectedRecap[testCaseCnt].ruleSetType, baseRecap.getRuleSetType());
                if (baseRecap.getRecapType() == RecapType.US) {
                    RecapUS recap = (RecapUS)baseRecap;
                    assertEquals("expected recap day for " + testCaseCnt + " " +testCaseName[testCaseCnt], expectedRecap[testCaseCnt].day, recap.getDay());
                    assertEquals("expected recap hours worked for " + testCaseCnt + " " +testCaseName[testCaseCnt], expectedRecap[testCaseCnt].hoursWorkedToday, recap.getHoursWorkedToday());
                    assertEquals("expected recap hours avail for " + testCaseCnt + " " +testCaseName[testCaseCnt], expectedRecap[testCaseCnt].hoursAvailToday, recap.getHoursAvailToday());
                    assertEquals("expected recap hours worked 7 days for " + testCaseCnt + " " +testCaseName[testCaseCnt], expectedRecap[testCaseCnt].hoursWorked7Days, recap.getHoursWorked7Days());
                    assertEquals("expected recap hours worked 8 days for " + testCaseCnt + " " +testCaseName[testCaseCnt], expectedRecap[testCaseCnt].hoursWorked8Days, recap.getHoursWorked8Days());
                    assertEquals("expected recap hours avail tomorrow for " + testCaseCnt + " " +testCaseName[testCaseCnt], expectedRecap[testCaseCnt].hoursAvailTomorrow, recap.getHoursAvailTomorrow());
                }
                else if (baseRecap.getRecapType() == RecapType.CANADA) {
                    RecapCanada recap = (RecapCanada)baseRecap;
                    assertEquals("expected recap day for " + testCaseCnt + " " +testCaseName[testCaseCnt], expectedRecap[testCaseCnt].day, recap.getDay());
                    assertEquals("expected recap hours worked for " + testCaseCnt + " " +testCaseName[testCaseCnt], expectedRecap[testCaseCnt].hoursWorkedToday, recap.getHoursWorkedToday());
                    assertEquals("expected recap hours avail for " + testCaseCnt + " " +testCaseName[testCaseCnt], expectedRecap[testCaseCnt].hoursAvailToday, recap.getHoursAvailToday());
                    assertEquals("expected recap hours worked 7 days for " + testCaseCnt + " " +testCaseName[testCaseCnt], expectedRecap[testCaseCnt].hoursWorked7Days, recap.getHoursWorked7Days());
                    assertEquals("expected recap hours worked 8 days for " + testCaseCnt + " " +testCaseName[testCaseCnt], expectedRecap[testCaseCnt].hoursWorked8Days, recap.getHoursWorked8Days());
                    assertEquals("expected recap hours worked 14 days for " + testCaseCnt + " " +testCaseName[testCaseCnt], expectedRecap[testCaseCnt].hoursWorked14Days, recap.getHoursWorked14Days());
                    assertEquals("expected recap hours avail tomorrow for " + testCaseCnt + " " +testCaseName[testCaseCnt], expectedRecap[testCaseCnt].hoursAvailTomorrow, recap.getHoursAvailTomorrow());
                }
                else if (baseRecap.getRecapType() == RecapType.CANADA_2007) {
                    RecapCanada2007 recap = (RecapCanada2007)baseRecap;
                    assertEquals("expected recap day for " + testCaseCnt + " " +testCaseName[testCaseCnt], expectedRecap[testCaseCnt].day, recap.getDay());
                    assertEquals("expected recap hours worked for " + testCaseCnt + " " +testCaseName[testCaseCnt], expectedRecap[testCaseCnt].hoursWorkedToday, recap.getHoursWorkedToday());
                    assertEquals("expected recap hours avail for " + testCaseCnt + " " +testCaseName[testCaseCnt], expectedRecap[testCaseCnt].hoursAvailToday, recap.getHoursAvailToday());
                    assertEquals("expected recap hours avail tomorrow for " + testCaseCnt + " " +testCaseName[testCaseCnt], expectedRecap[testCaseCnt].hoursAvailTomorrow, recap.getHoursAvailTomorrow());
                    assertEquals("expected recap hours worked cycle for " + testCaseCnt + " " +testCaseName[testCaseCnt], expectedRecap[testCaseCnt].hoursWorkedCycle, recap.getHoursWorkedCycle());
                    assertEquals("expected recap hours worked cycle for " + testCaseCnt + " " +testCaseName[testCaseCnt], expectedRecap[testCaseCnt].cycle, recap.getCycle());
                    
                }
            }
  
            dump("DDL", testCaseCnt, hosDailyDriverLogReportCriteria.getCriteriaList(), FormatType.PDF);
        }
    }


    

    private void compareHOSRecAdjusted(HOSRecAdjusted expectedRec, HOSRecAdjusted rec, int recCnt, int testCaseCnt) {
        String prefix = "testcase: " + testCaseCnt + " " + testCaseName[testCaseCnt] + " adjustedRec [" +recCnt + "] field: "; 
        assertEquals(prefix+"ID ", expectedRec.getId(), rec.getId()); 
        assertEquals(prefix+"Status ", expectedRec.getStatus(), rec.getStatus()); 
        assertEquals(prefix+"LogTimeDate ", expectedRec.getLogTimeDate(), rec.getLogTimeDate()); 
        assertEquals(prefix+"LogTimeZone ", expectedRec.getLogTimeZone(),  rec.getLogTimeZone());
        assertEquals(prefix+"AdjustedTime ", expectedRec.getAdjustedTime(), rec.getAdjustedTime()); 
        assertEquals(prefix+"TotalAdjustedMinutes ", expectedRec.getTotalAdjustedMinutes(), rec.getTotalAdjustedMinutes()); 
        assertEquals(prefix+"StartIncrement ", expectedRec.getStartIncrement(), rec.getStartIncrement()); 
        assertEquals(prefix+"TotalIncrements ", expectedRec.getTotalIncrements(), rec.getTotalIncrements()); 
        assertEquals(prefix+"Edited ", expectedRec.isEdited(), rec.isEdited()); 
        assertEquals(prefix+"ServiceID ", expectedRec.getServiceID(), rec.getServiceID()); 
        assertEquals(prefix+"TrailerID ", expectedRec.getTrailerID(), rec.getTrailerID()); 
        assertEquals(prefix+"TotalRealMinutes ", expectedRec.getTotalRealMinutes(), rec.getTotalRealMinutes()); 
        assertEquals(prefix+"RuleType ", expectedRec.getRuleType(), rec.getRuleType()); 
    }





    class ExpectedRecap {
        
        // all
        RecapType recapType;
        RuleSetType ruleSetType;
        Integer day;
        String hoursWorkedToday;
        
        // US, CANADA
        String hoursAvailToday;
        String hoursWorked7Days;
        String hoursWorked8Days;
        String hoursAvailTomorrow;

        // CANADA
        String hoursWorked14Days;
        
        // CANADA2007
        String hoursWorkedCycle;
        int cycle;

        public ExpectedRecap(RecapType recapType,RuleSetType ruleSetType,
                Integer day, String hoursWorkedToday, String hoursAvailToday, String hoursWorked7Days, String hoursWorked8Days, String hoursAvailTomorrow,
                String hoursWorked14Days, String hoursWorkedCycle, int cycle) {
            this.recapType = recapType;
            this.ruleSetType = ruleSetType;
            this.day = day;
            this.hoursWorkedToday = hoursWorkedToday;
            this.hoursAvailToday = hoursAvailToday;
            this.hoursWorked7Days = hoursWorked7Days;
            this.hoursWorked8Days = hoursWorked8Days;
            this.hoursAvailTomorrow = hoursAvailTomorrow;
            this.hoursWorked14Days = hoursWorked14Days;
            this.hoursWorkedCycle = hoursWorkedCycle;
            this.cycle = cycle;
        }

        public void dump() {
            
            if (recapType == RecapType.US) {
                System.out.println("        new ExpectedRecap(RecapType.US," + 
                        "RuleSetType."+ruleSetType.getName()+"," +
                        day + "," +
                        "\"" + hoursWorkedToday + "\"," +
                        "\"" + hoursAvailToday + "\"," +
                        "\"" + hoursWorked7Days + "\"," +
                        "\"" + hoursWorked8Days + "\"," +
                        "\"" + hoursAvailTomorrow + "\"," +
                        "\"\"," +
                        "\"\"," +
                        "0),");
            }
            else if (recapType ==  RecapType.CANADA) {
              System.out.println("        new ExpectedRecap(RecapType.CANADA," + 
                      "RuleSetType."+ruleSetType.getName()+"," +
                      day + "," +
                      "\"" + hoursWorkedToday + "\"," +
                      "\"" + hoursAvailToday + "\"," +
                      "\"" + hoursWorked7Days + "\"," +
                      "\"" + hoursWorked8Days + "\"," +
                      "\"" + hoursAvailTomorrow + "\"," +
                      "\"" + hoursWorked14Days + "\"," +
                      "\"\"," +
                      "0),");
            }
            else if (recapType == RecapType.CANADA_2007) {
              System.out.println("        new ExpectedRecap(RecapType.CANADA_2007," + 
                      "RuleSetType."+ruleSetType.getName()+"," +
                      day + "," +
                      "\"" + hoursWorkedToday + "\"," +
                      "\"" + hoursAvailToday + "\"," +
                      "\"\"," +
                      "\"\"," +
                      "\"" + hoursAvailTomorrow + "\"," +
                      "\"\"," +
                      "\"" + hoursWorkedCycle + "\"," +
                      cycle+"),");
            }
            
        }
    }
    
    
    
    
    
//  public HOSRecAdjusted expectedAdjustedList[][]  = {
//  System.out.println("    {     // " + testCaseName[i]); 
//  for (HOSRecAdjusted rec : correctedList) {
//      System.out.println("        new HOSRecAdjusted(" + 
//                  "\"" + rec.getId() + "\"," + 
//                  "HOSStatus." + rec.getStatus().getName() + "," + 
//                  "new Date(" + rec.getLogTimeDate().getTime() + "l)," +
//                  "TimeZone.getTimeZone(\""+rec.getLogTimeZone().getID() + "\")," +
//                  "new Date(" + rec.getAdjustedTime().getTime() + "l)," + 
//                  "" + rec.getTotalAdjustedMinutes()+ "l," + 
//                  "" + rec.getStartIncrement()+ "," + 
//                  "" + rec.getTotalIncrements()+ "," + 
//                  "" + rec.isEdited()+ "," + 
//                  "\"" + rec.getServiceID() + "\"," + 
//                  "\"" + rec.getTrailerID() + "\"," + 
//                  "" + rec.getTotalRealMinutes()+ "l," + 
//                  "RuleSetType." + rec.getRuleType().getName() + 
//              "),");
//  }
//  System.out.println("    },"); 
//  
}
