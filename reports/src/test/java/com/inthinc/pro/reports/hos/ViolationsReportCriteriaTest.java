package com.inthinc.pro.reports.hos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;
import org.junit.Test;

import com.inthinc.hos.model.HOSOrigin;
import com.inthinc.hos.model.HOSStatus;
import com.inthinc.hos.model.RuleSetType;
import com.inthinc.hos.model.RuleViolationTypes;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.reports.BaseUnitTest;
import com.inthinc.pro.reports.FormatType;
import com.inthinc.pro.reports.hos.model.DrivingTimeViolationsSummary;
import com.inthinc.pro.reports.hos.model.HosViolationsSummary;
import com.inthinc.pro.reports.hos.model.NonDOTViolationsSummary;
import com.inthinc.pro.reports.hos.model.Violation;
import com.inthinc.pro.reports.hos.model.ViolationsDetail;
import com.inthinc.pro.reports.hos.testData.HosRecordDataSet;
import com.inthinc.pro.model.hos.HOSRecord;

public class ViolationsReportCriteriaTest extends BaseUnitTest {

    public static final String DATA_PATH = "violations/";
    public static final String testCaseName[] = { 
        "vtest_00_07012010_07072010", 
        "vtest_01H1_07012010_07072010", 
    };
    HosViolationsSummary hosViolationsExpectedData[][] = {
            { new HosViolationsSummary("HOS", 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 32, 306500.0d, 0.0d),
                    new HosViolationsSummary("HOS->Open Hole", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 531200.0d, 5300.0d),
                    new HosViolationsSummary("HOS->Cased Hole", 0, 0, 0, 7, 3, 1, 3, 1, 1, 0, 0, 0, 54, 1296500.0d, 21000.0d), // Note: this is off by 1 mile on the zero miles from what gain reports, but it matches the hos zero miles report
                    new HosViolationsSummary("HOS->Slickline", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 133900.0d, 0.0d),
                    new HosViolationsSummary("HOS->Tech", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0.0d, 0.0d),
                    new HosViolationsSummary("HOS->Gun Loader", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 8, 0.0d, 0.0d),
                    new HosViolationsSummary("HOS->Temporary Drivers", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 0.0d, 0.0d), },
            { new HosViolationsSummary("Norman Wells->Norman Wells - WS", 0, 0, 0, 4, 1, 0, 0, 0, 0, 3, 1, 1, 7, 26700.0d, 100.0d),
                    new HosViolationsSummary("Norman Wells->REW", 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 15200.0d, 0.0d), } };
    NonDOTViolationsSummary nonDOTViolationsExpectedData[][] = {
            { new NonDOTViolationsSummary("HOS", 51, 2), new NonDOTViolationsSummary("HOS->Open Hole", 7, 0), new NonDOTViolationsSummary("HOS->Cased Hole", 55, 0),
                    new NonDOTViolationsSummary("HOS->Slickline", 6, 0), new NonDOTViolationsSummary("HOS->Tech", 5, 0), new NonDOTViolationsSummary("HOS->Gun Loader", 8, 0),
                    new NonDOTViolationsSummary("HOS->Temporary Drivers", 14, 0), }, { new NonDOTViolationsSummary("Norman Wells->Norman Wells - WS", 8, 0), } };
    DrivingTimeViolationsSummary drivingTimeViolationsExpectedData[][] = {
            { new DrivingTimeViolationsSummary("HOS", 1, 0, 2, 51), new DrivingTimeViolationsSummary("HOS->Open Hole", 5, 0, 0, 7),
                    new DrivingTimeViolationsSummary("HOS->Cased Hole", 10, 4, 0, 55), new DrivingTimeViolationsSummary("HOS->Slickline", 0, 0, 0, 6),
                    new DrivingTimeViolationsSummary("HOS->Tech", 1, 0, 0, 5), new DrivingTimeViolationsSummary("HOS->Gun Loader", 0, 0, 0, 8),
                    new DrivingTimeViolationsSummary("HOS->Temporary Drivers", 2, 0, 0, 14), },
            { new DrivingTimeViolationsSummary("Norman Wells->Norman Wells - WS", 0, 6, 0, 8), } };
    
    ViolationsDetail hosViolationsDetailExpectedData[][] = {
            {
                new ViolationsDetail("HOS->Cased Hole",new Date(1277981363000l),"461353","Coombs,  Eric","10965839",RuleSetType.US_OIL,Arrays.asList(
                        new Violation(RuleSetType.US_OIL,RuleViolationTypes.CUMMULATIVE_HOURS,14l)
                    )),
                    new ViolationsDetail("HOS->Cased Hole",new Date(1277982212000l),"461353","Coombs,  Eric","10965839",RuleSetType.US_OIL,Arrays.asList(
                        new Violation(RuleSetType.US_OIL,RuleViolationTypes.CUMMULATIVE_HOURS,11l)
                    )),
                    new ViolationsDetail("HOS->Cased Hole",new Date(1277982907000l),"461353","Coombs,  Eric","10965839",RuleSetType.US_OIL,Arrays.asList(
                        new Violation(RuleSetType.US_OIL,RuleViolationTypes.CUMMULATIVE_HOURS,14l)
                    )),
                    new ViolationsDetail("HOS->Cased Hole",new Date(1277983787000l),"461353","Coombs,  Eric",null,RuleSetType.US_OIL,Arrays.asList(
                        new Violation(RuleSetType.US_OIL,RuleViolationTypes.CUMMULATIVE_HOURS,19l)
                    )),
                    new ViolationsDetail("HOS->Cased Hole",new Date(1277985145000l),"461353","Coombs,  Eric","10965839",RuleSetType.US_OIL,Arrays.asList(
                        new Violation(RuleSetType.US_OIL,RuleViolationTypes.CUMMULATIVE_HOURS,41l)
                    )),
                    new ViolationsDetail("HOS->Cased Hole",new Date(1278482069000l),"469874","Dewitt,  Jason","10965838",RuleSetType.US_OIL,Arrays.asList(
                        new Violation(RuleSetType.US_OIL,RuleViolationTypes.ON_DUTY_HOUR,24l)
                    )),
                    new ViolationsDetail("HOS->Cased Hole",new Date(1278484060000l),"469874","Dewitt,  Jason","10965838",RuleSetType.US_OIL,Arrays.asList(
                        new Violation(RuleSetType.US_OIL,RuleViolationTypes.ON_DUTY_HOUR,15l)
                    )),
                    new ViolationsDetail("HOS->Cased Hole",new Date(1278239817000l),"450702","Freeman,  Justin Thomas","10830717",RuleSetType.US_OIL,Arrays.asList(
                        new Violation(RuleSetType.US_OIL,RuleViolationTypes.ON_DUTY_HOUR,1l)
                    )),
                    new ViolationsDetail("HOS->Cased Hole",new Date(1278446400000l),"475707","Grover,  Peter", null,RuleSetType.US_OIL,Arrays.asList(
                        new Violation(RuleSetType.US_OIL,RuleViolationTypes.ON_DUTY_HOUR,60l)
                    )),
                    new ViolationsDetail("HOS->Cased Hole",new Date(1278423124000l),"409241","Lee,  Jeremy Joseph","11242145",RuleSetType.US_OIL,Arrays.asList(
                        new Violation(RuleSetType.US_OIL,RuleViolationTypes.ON_DUTY_HOUR,5l)
                    )),
                    new ViolationsDetail("HOS->Cased Hole",new Date(1278118747000l),"408554","Monroe,  Eddie (ED)","10965841",RuleSetType.US_OIL,Arrays.asList(
                        new Violation(RuleSetType.US_OIL,RuleViolationTypes.ON_DUTY_HOUR,2l)
                    )),
                    new ViolationsDetail("HOS",new Date(1278524792000l),"473468","Penfield,  Daniel","11134018",RuleSetType.US_OIL,Arrays.asList(
                        new Violation(RuleSetType.US_OIL,RuleViolationTypes.ON_DUTY_HOUR,5l)
                    )),
                    new ViolationsDetail("HOS",new Date(1278525340000l),"473468","Penfield,  Daniel","11134018",RuleSetType.US_OIL,Arrays.asList(
                        new Violation(RuleSetType.US_OIL,RuleViolationTypes.ON_DUTY_HOUR,5l)
                    )),
                    new ViolationsDetail("HOS->Cased Hole",new Date(1278240070000l),"370532","Retzlaff,  John E","10830717",RuleSetType.US_OIL,Arrays.asList(
                        new Violation(RuleSetType.US_OIL,RuleViolationTypes.ON_DUTY_HOUR,25l)
                    )),
                    new ViolationsDetail("HOS->Cased Hole",new Date(1278241650000l),"370532","Retzlaff,  John E","10830717",RuleSetType.US_OIL,Arrays.asList(
                        new Violation(RuleSetType.US_OIL,RuleViolationTypes.ON_DUTY_HOUR,12l)
                    )),
                    new ViolationsDetail("HOS->Cased Hole",new Date(1278154891000l),"426338","Shannon,  Dustin","10965839",RuleSetType.US_OIL,Arrays.asList(
                        new Violation(RuleSetType.US_OIL,RuleViolationTypes.ON_DUTY_HOUR,10l)
                    )),
                    new ViolationsDetail("HOS->Cased Hole",new Date(1278155970000l),"426338","Shannon,  Dustin","10965839",RuleSetType.US_OIL,Arrays.asList(
                        new Violation(RuleSetType.US_OIL,RuleViolationTypes.ON_DUTY_HOUR,7l)
                    )),
                    new ViolationsDetail("HOS->Cased Hole",new Date(1278242430000l),"451337","Smith,  Matthew Mark","10830717",RuleSetType.US_OIL,Arrays.asList(
                        new Violation(RuleSetType.US_OIL,RuleViolationTypes.ON_DUTY_HOUR,2l)
                    )),
            },
            {
                new ViolationsDetail("Norman Wells->Norman Wells - WS",new Date(1278511164000l),"01041730","Harris,  Eugene","2467",RuleSetType.CANADA_2007_60_DEGREES_OIL,Arrays.asList(
                        new Violation(RuleSetType.CANADA_2007_60_DEGREES_OIL,RuleViolationTypes.ON_DUTY_HOUR,6l),
                        new Violation(RuleSetType.CANADA_2007_60_DEGREES_OIL,RuleViolationTypes.OFF_DUTY_HOUR,6l)
                    )),
                    new ViolationsDetail("Norman Wells->Norman Wells - WS",new Date(1278482400000l),"01466044","Szpuniarski,  James",null,RuleSetType.CANADA_2007_60_DEGREES_OIL,Arrays.asList(
                        new Violation(RuleSetType.CANADA_2007_60_DEGREES_OIL,RuleViolationTypes.DAILY_OFF_DUTY,142l)
                    )),
                    new ViolationsDetail("Norman Wells->Norman Wells - WS",new Date(1278514452000l),"01466044","Szpuniarski,  James","2TRA02584",RuleSetType.CANADA_2007_60_DEGREES_OIL,Arrays.asList(
                        new Violation(RuleSetType.CANADA_2007_60_DEGREES_OIL,RuleViolationTypes.ON_DUTY_HOUR,12l)
                    )),
                    new ViolationsDetail("Norman Wells->Norman Wells - WS",new Date(1278535495000l),"01466044","Szpuniarski,  James","2371",RuleSetType.CANADA_2007_60_DEGREES_OIL,Arrays.asList(
                        new Violation(RuleSetType.CANADA_2007_60_DEGREES_OIL,RuleViolationTypes.ON_DUTY_HOUR,15l),
                        new Violation(RuleSetType.CANADA_2007_60_DEGREES_OIL,RuleViolationTypes.OFF_DUTY_HOUR,15l)
                    )),
                    new ViolationsDetail("Norman Wells->Norman Wells - WS",new Date(1278547639000l),"01466044","Szpuniarski,  James","2371",RuleSetType.CANADA_2007_60_DEGREES_OIL,Arrays.asList(
                        new Violation(RuleSetType.CANADA_2007_60_DEGREES_OIL,RuleViolationTypes.ON_DUTY_HOUR,10l),
                        new Violation(RuleSetType.CANADA_2007_60_DEGREES_OIL,RuleViolationTypes.OFF_DUTY_HOUR,10l)
                    )),
                    new ViolationsDetail("Norman Wells->Norman Wells - WS",new Date(1278548506000l),"01466044","Szpuniarski,  James","2371",RuleSetType.CANADA_2007_60_DEGREES_OIL,Arrays.asList(
                        new Violation(RuleSetType.CANADA_2007_60_DEGREES_OIL,RuleViolationTypes.ON_DUTY_HOUR,12l),
                        new Violation(RuleSetType.CANADA_2007_60_DEGREES_OIL,RuleViolationTypes.OFF_DUTY_HOUR,12l)
                    )),
       
            }
            
    };
    ViolationsDetail nonDOTViolationsDetailExpectedData[][] = {
            {
            new ViolationsDetail("HOS",new Date(1278516750000l),"421378","Rosendo,  Cesar","10025801",RuleSetType.NON_DOT,Arrays.asList(
                    new Violation(RuleSetType.NON_DOT,RuleViolationTypes.NON_DOT_DRIVER,128l)
                )),
                new ViolationsDetail("HOS",new Date(1278526846000l),"421378","Rosendo,  Cesar","10025801",RuleSetType.NON_DOT,Arrays.asList(
                    new Violation(RuleSetType.NON_DOT,RuleViolationTypes.NON_DOT_DRIVER,281l)
                )),
            },
            {
                
            }
    };
    ViolationsDetail drivingTimeViolationsDetailExpectedData[][] = {
            {
            new ViolationsDetail("HOS->Cased Hole",new Date(1278482069000l),"469874","Dewitt,  Jason","10965838",RuleSetType.US_OIL,Arrays.asList(
                    new Violation(RuleSetType.US_OIL,RuleViolationTypes.DRIVING_HOUR_BAN,24l),
                    new Violation(RuleSetType.US_OIL,RuleViolationTypes.DRIVING_TIME_GENERAL,24l)
                )),
                new ViolationsDetail("HOS->Cased Hole",new Date(1278484060000l),"469874","Dewitt,  Jason","10965838",RuleSetType.US_OIL,Arrays.asList(
                    new Violation(RuleSetType.US_OIL,RuleViolationTypes.DRIVING_TIME_GENERAL,15l)
                )),
                new ViolationsDetail("HOS->Cased Hole",new Date(1278220176000l),"371369","Edvenson,  Jared","10717528",RuleSetType.US_OIL,Arrays.asList(
                    new Violation(RuleSetType.US_OIL,RuleViolationTypes.DRIVING_HOUR_BAN,5l)
                )),
                new ViolationsDetail("HOS->Cased Hole",new Date(1278047583000l),"9371369","Edvenson,  Personal Jared","10717528",RuleSetType.US_OIL,Arrays.asList(
                    new Violation(RuleSetType.US_OIL,RuleViolationTypes.DRIVING_HOUR_BAN,60l)
                )),
                new ViolationsDetail("HOS->Cased Hole",new Date(1278220523000l),"9371369","Edvenson,  Personal Jared","10717528",RuleSetType.US_OIL,Arrays.asList(
                    new Violation(RuleSetType.US_OIL,RuleViolationTypes.DRIVING_HOUR_BAN,128l)
                )),
                new ViolationsDetail("HOS->Open Hole",new Date(1278558152000l),"441900","Ehlers,  Neal","11014853",RuleSetType.US_OIL,Arrays.asList(
                    new Violation(RuleSetType.US_OIL,RuleViolationTypes.DRIVING_HOUR_BAN,66l)
                )),
                new ViolationsDetail("HOS->Tech",new Date(1278034177000l),"287776","Ethridge,  Michael Eugene","10830714",RuleSetType.US_OIL,Arrays.asList(
                    new Violation(RuleSetType.US_OIL,RuleViolationTypes.DRIVING_HOUR_BAN,42l)
                )),
                new ViolationsDetail("HOS->Cased Hole",new Date(1278239817000l),"450702","Freeman,  Justin Thomas","10830717",RuleSetType.US_OIL,Arrays.asList(
                    new Violation(RuleSetType.US_OIL,RuleViolationTypes.DRIVING_TIME_GENERAL,1l)
                )),
                new ViolationsDetail("HOS",new Date(1278217514000l),"473478","Hall,  William ","11242141",RuleSetType.US_OIL,Arrays.asList(
                    new Violation(RuleSetType.US_OIL,RuleViolationTypes.DRIVING_HOUR_BAN,42l)
                )),
                new ViolationsDetail("HOS->Temporary Drivers",new Date(1278131426000l),"423540","Lewis,  Andrew","11014853",RuleSetType.US_OIL,Arrays.asList(
                    new Violation(RuleSetType.US_OIL,RuleViolationTypes.DRIVING_HOUR_BAN,13l)
                )),
                new ViolationsDetail("HOS->Temporary Drivers",new Date(1278134293000l),"423540","Lewis,  Andrew","11014853",RuleSetType.US_OIL,Arrays.asList(
                    new Violation(RuleSetType.US_OIL,RuleViolationTypes.DRIVING_HOUR_BAN,67l)
                )),
                new ViolationsDetail("HOS->Cased Hole",new Date(1278212298000l),"408554","Monroe,  Eddie (ED)","10965841",RuleSetType.US_OIL,Arrays.asList(
                    new Violation(RuleSetType.US_OIL,RuleViolationTypes.DRIVING_HOUR_BAN,23l)
                )),
                new ViolationsDetail("HOS->Cased Hole",new Date(1278221287000l),"408554","Monroe,  Eddie (ED)","10965841",RuleSetType.US_OIL,Arrays.asList(
                    new Violation(RuleSetType.US_OIL,RuleViolationTypes.DRIVING_HOUR_BAN,35l)
                )),
                new ViolationsDetail("HOS",new Date(1278516750000l),"421378","Rosendo,  Cesar","10025801",RuleSetType.NON_DOT,Arrays.asList(
                    new Violation(RuleSetType.NON_DOT,RuleViolationTypes.NON_DOT_DRIVER,128l)
                )),
                new ViolationsDetail("HOS",new Date(1278526846000l),"421378","Rosendo,  Cesar","10025801",RuleSetType.NON_DOT,Arrays.asList(
                    new Violation(RuleSetType.NON_DOT,RuleViolationTypes.NON_DOT_DRIVER,281l)
                )),
                new ViolationsDetail("HOS->Cased Hole",new Date(1278242430000l),"451337","Smith,  Matthew Mark","10830717",RuleSetType.US_OIL,Arrays.asList(
                    new Violation(RuleSetType.US_OIL,RuleViolationTypes.DRIVING_TIME_GENERAL,2l)
                )),
                new ViolationsDetail("HOS->Cased Hole",new Date(1278560243000l),"420256","VanAlstyne,  Troy","11134018",RuleSetType.US_OIL,Arrays.asList(
                    new Violation(RuleSetType.US_OIL,RuleViolationTypes.DRIVING_HOUR_BAN,54l)
                )),
                new ViolationsDetail("HOS->Cased Hole",new Date(1278568648000l),"420256","VanAlstyne,  Troy","11134018",RuleSetType.US_OIL,Arrays.asList(
                    new Violation(RuleSetType.US_OIL,RuleViolationTypes.DRIVING_HOUR_BAN,49l)
                )),
                new ViolationsDetail("HOS->Open Hole",new Date(1278040310000l),"430279","Wilkerson,  Jason M","11014853",RuleSetType.US_OIL,Arrays.asList(
                    new Violation(RuleSetType.US_OIL,RuleViolationTypes.DRIVING_HOUR_BAN,55l)
                )),
                new ViolationsDetail("HOS->Open Hole",new Date(1278050387000l),"430279","Wilkerson,  Jason M","11014853",RuleSetType.US_OIL,Arrays.asList(
                    new Violation(RuleSetType.US_OIL,RuleViolationTypes.DRIVING_HOUR_BAN,181l)
                )),
                new ViolationsDetail("HOS->Open Hole",new Date(1278219422000l),"430279","Wilkerson,  Jason M","11014853",RuleSetType.US_OIL,Arrays.asList(
                    new Violation(RuleSetType.US_OIL,RuleViolationTypes.DRIVING_HOUR_BAN,2l)
                )),
                new ViolationsDetail("HOS->Open Hole",new Date(1278219946000l),"430279","Wilkerson,  Jason M","11014853",RuleSetType.US_OIL,Arrays.asList(
                    new Violation(RuleSetType.US_OIL,RuleViolationTypes.DRIVING_HOUR_BAN,3l)
                )),
                new ViolationsDetail("HOS->Cased Hole",new Date(1278307049000l),"448565","York,  Richard Gene","10988010",RuleSetType.US_OIL,Arrays.asList(
                    new Violation(RuleSetType.US_OIL,RuleViolationTypes.DRIVING_HOUR_BAN,78l)
                )),
                new ViolationsDetail("HOS->Cased Hole",new Date(1278387237000l),"448565","York,  Richard Gene","10988010",RuleSetType.US_OIL,Arrays.asList(
                    new Violation(RuleSetType.US_OIL,RuleViolationTypes.DRIVING_HOUR_BAN,131l)
                )),
            },
            {
                new ViolationsDetail("Norman Wells->Norman Wells - WS",new Date(1278511164000l),"01041730","Harris,  Eugene","2467",RuleSetType.CANADA_2007_60_DEGREES_OIL,Arrays.asList(
                        new Violation(RuleSetType.CANADA_2007_60_DEGREES_OIL,RuleViolationTypes.DRIVING_TIME_GENERAL,6l)
                    )),
                    new ViolationsDetail("Norman Wells->Norman Wells - WS",new Date(1278508608000l),"01466044","Szpuniarski,  James","2371",RuleSetType.CANADA_2007_60_DEGREES_OIL,Arrays.asList(
                        new Violation(RuleSetType.CANADA_2007_60_DEGREES_OIL,RuleViolationTypes.DRIVING_TIME_GENERAL,11l)
                    )),
                    new ViolationsDetail("Norman Wells->Norman Wells - WS",new Date(1278514452000l),"01466044","Szpuniarski,  James","2TRA02584",RuleSetType.CANADA_2007_60_DEGREES_OIL,Arrays.asList(
                        new Violation(RuleSetType.CANADA_2007_60_DEGREES_OIL,RuleViolationTypes.DRIVING_TIME_GENERAL,12l)
                    )),
                    new ViolationsDetail("Norman Wells->Norman Wells - WS",new Date(1278535495000l),"01466044","Szpuniarski,  James","2371",RuleSetType.CANADA_2007_60_DEGREES_OIL,Arrays.asList(
                        new Violation(RuleSetType.CANADA_2007_60_DEGREES_OIL,RuleViolationTypes.DRIVING_TIME_GENERAL,15l)
                    )),
                    new ViolationsDetail("Norman Wells->Norman Wells - WS",new Date(1278547639000l),"01466044","Szpuniarski,  James","2371",RuleSetType.CANADA_2007_60_DEGREES_OIL,Arrays.asList(
                        new Violation(RuleSetType.CANADA_2007_60_DEGREES_OIL,RuleViolationTypes.DRIVING_TIME_GENERAL,10l)
                    )),
                    new ViolationsDetail("Norman Wells->Norman Wells - WS",new Date(1278548506000l),"01466044","Szpuniarski,  James","2371",RuleSetType.CANADA_2007_60_DEGREES_OIL,Arrays.asList(
                        new Violation(RuleSetType.CANADA_2007_60_DEGREES_OIL,RuleViolationTypes.DRIVING_TIME_GENERAL,12l)
                    )),
                
            }
    };

    static Map<RuleSetType, Integer> expectedHosDetailCountMap = new HashMap<RuleSetType, Integer>();
    static {
        expectedHosDetailCountMap.put(RuleSetType.US, Integer.valueOf(3));
        expectedHosDetailCountMap.put(RuleSetType.US_OIL, Integer.valueOf(3)); 
        expectedHosDetailCountMap.put(RuleSetType.CANADA, Integer.valueOf(3)); 
        expectedHosDetailCountMap.put(RuleSetType.CANADA_60_DEGREES, Integer.valueOf(3)); 
        expectedHosDetailCountMap.put(RuleSetType.US_HOME_OFFICE, Integer.valueOf(3)); 
        expectedHosDetailCountMap.put(RuleSetType.CANADA_HOME_OFFICE, Integer.valueOf(3)); 
        expectedHosDetailCountMap.put(RuleSetType.TEXAS, Integer.valueOf(3)); 
        expectedHosDetailCountMap.put(RuleSetType.CANADA_ALBERTA, Integer.valueOf(2));
        expectedHosDetailCountMap.put(RuleSetType.CANADA_2007_CYCLE_1, Integer.valueOf(8)); 
        expectedHosDetailCountMap.put(RuleSetType.CANADA_2007_CYCLE_2, Integer.valueOf(8)); 
        expectedHosDetailCountMap.put(RuleSetType.CANADA_2007_60_DEGREES_CYCLE_1, Integer.valueOf(6)); 
        expectedHosDetailCountMap.put(RuleSetType.CANADA_2007_60_DEGREES_CYCLE_2, Integer.valueOf(6)); 
        expectedHosDetailCountMap.put(RuleSetType.CANADA_2007_OIL, Integer.valueOf(7)); 
        expectedHosDetailCountMap.put(RuleSetType.CANADA_2007_60_DEGREES_OIL, Integer.valueOf(5)); 
        expectedHosDetailCountMap.put(RuleSetType.US_7DAY, Integer.valueOf(3)); 
        expectedHosDetailCountMap.put(RuleSetType.US_OIL_7DAY, Integer.valueOf(3)); 
    }
    @Test
    public void gainSummmaryTestCases() {
        for (int testCaseCnt = 0; testCaseCnt < testCaseName.length; testCaseCnt++) {
            HosRecordDataSet violationsTestData = new HosRecordDataSet(DATA_PATH, testCaseName[testCaseCnt], true);
            HosViolationsSummaryReportCriteria criteria = new HosViolationsSummaryReportCriteria(Locale.US);
            criteria.initDataSet(violationsTestData.interval, violationsTestData.getGroupHierarchy(),
                    violationsTestData.getGroupHierarchy().getGroupList(), violationsTestData.driverHOSRecordMap,
                    violationsTestData.groupMileageList, violationsTestData.groupNoDriverMileageList);
            List<HosViolationsSummary> dataList = criteria.getMainDataset();
            assertEquals(testCaseName[testCaseCnt] + " number of records", hosViolationsExpectedData[testCaseCnt].length, dataList.size());
            int eCnt = 0;
            for (HosViolationsSummary s : dataList) {
                HosViolationsSummary expected = hosViolationsExpectedData[testCaseCnt][eCnt++];
                assertEquals(testCaseName[testCaseCnt] + " driving_1 " + eCnt, expected.getDriving_1(), s.getDriving_1());
                assertEquals(testCaseName[testCaseCnt] + " driving_2 " + eCnt, expected.getDriving_2(), s.getDriving_2());
                assertEquals(testCaseName[testCaseCnt] + " driving_2 " + eCnt, expected.getDriving_3(), s.getDriving_3());
                assertEquals(testCaseName[testCaseCnt] + " OnDuty_1 " + eCnt, expected.getOnDuty_1(), s.getOnDuty_1());
                assertEquals(testCaseName[testCaseCnt] + " OnDuty_2 " + eCnt, expected.getOnDuty_2(), s.getOnDuty_2());
                assertEquals(testCaseName[testCaseCnt] + " OnDuty_3 " + eCnt, expected.getOnDuty_3(), s.getOnDuty_3());
                assertEquals(testCaseName[testCaseCnt] + " Cumulative_1 " + eCnt, expected.getCumulative_1(), s.getCumulative_1());
                assertEquals(testCaseName[testCaseCnt] + " Cumulative_2 " + eCnt, expected.getCumulative_2(), s.getCumulative_2());
                assertEquals(testCaseName[testCaseCnt] + " Cumulative_3 " + eCnt, expected.getCumulative_3(), s.getCumulative_3());
                assertEquals(testCaseName[testCaseCnt] + " OffDuty_1 " + eCnt, expected.getOffDuty_1(), s.getOffDuty_1());
                assertEquals(testCaseName[testCaseCnt] + " OffDuty_2 " + eCnt, expected.getOffDuty_2(), s.getOffDuty_2());
                assertEquals(testCaseName[testCaseCnt] + " OffDuty_3 " + eCnt, expected.getOffDuty_3(), s.getOffDuty_3());
                assertEquals(testCaseName[testCaseCnt] + " DriverCnt " + eCnt, expected.getDriverCnt(), s.getDriverCnt());
                assertEquals(testCaseName[testCaseCnt] + " TotalMiles " + eCnt, expected.getTotalMiles(), s.getTotalMiles());
                assertEquals(testCaseName[testCaseCnt] + " TotalMilesNoDriver " + eCnt, expected.getTotalMilesNoDriver(), s.getTotalMilesNoDriver());
            }
            dump("hosViolationsSummaryTest", testCaseCnt + 1, criteria, FormatType.PDF);
            dump("hosViolationsSummaryTest", testCaseCnt + 1, criteria, FormatType.EXCEL);
            // NON-DOT VIOLATIONS
            NonDOTViolationsSummaryReportCriteria nonDOTCriteria = new NonDOTViolationsSummaryReportCriteria(Locale.US);
            nonDOTCriteria.initDataSet(violationsTestData.interval, violationsTestData.getGroupHierarchy(),
                    violationsTestData.getGroupHierarchy().getGroupList(), violationsTestData.driverHOSRecordMap);
            dump("nonDOTViolationsTest", testCaseCnt + 1, nonDOTCriteria, FormatType.PDF);
            dump("notDOTViolationsTest", testCaseCnt + 1, nonDOTCriteria, FormatType.EXCEL);
            List<NonDOTViolationsSummary> nonDOTDataList = nonDOTCriteria.getMainDataset();
            assertEquals(testCaseName[testCaseCnt] + " number of records", hosViolationsExpectedData[testCaseCnt].length, dataList.size());
            eCnt = 0;
            for (NonDOTViolationsSummary s : nonDOTDataList) {
                NonDOTViolationsSummary expected = nonDOTViolationsExpectedData[testCaseCnt][eCnt++];
                assertEquals(testCaseName[testCaseCnt] + " driverCnt " + eCnt, expected.getDriverCnt(), s.getDriverCnt());
                assertEquals(testCaseName[testCaseCnt] + " NonDOTDriverCount " + eCnt, expected.getNonDOTDriverCount(), s.getNonDOTDriverCount());
            }
            // DrivingTime VIOLATIONS
            DrivingTimeViolationsSummaryReportCriteria drivingTimeCriteria = new DrivingTimeViolationsSummaryReportCriteria(Locale.US);
            drivingTimeCriteria.initDataSet(violationsTestData.interval, violationsTestData.getGroupHierarchy(),
                    violationsTestData.getGroupHierarchy().getGroupList(),  violationsTestData.driverHOSRecordMap);
            dump("drivingViolationsTest", testCaseCnt + 1, drivingTimeCriteria, FormatType.PDF);
            dump("drivingViolationsTest", testCaseCnt + 1, drivingTimeCriteria, FormatType.EXCEL);
            List<DrivingTimeViolationsSummary> drivingTimeDataList = drivingTimeCriteria.getMainDataset();
            assertEquals(testCaseName[testCaseCnt] + " number of records", hosViolationsExpectedData[testCaseCnt].length, dataList.size());
            eCnt = 0;
            for (DrivingTimeViolationsSummary s : drivingTimeDataList) {
                DrivingTimeViolationsSummary expected = drivingTimeViolationsExpectedData[testCaseCnt][eCnt++];
                assertEquals(testCaseName[testCaseCnt] + " DriverCnt " + eCnt, expected.getDriverCnt(), s.getDriverCnt());
                assertEquals(testCaseName[testCaseCnt] + " DrivingHourBanCount " + eCnt, expected.getDrivingHourBanCount(), s.getDrivingHourBanCount());
                assertEquals(testCaseName[testCaseCnt] + " NonDOTDriverCount " + eCnt, expected.getNonDOTDriverCount(), s.getNonDOTDriverCount());
                assertEquals(testCaseName[testCaseCnt] + " OnDuty16HrCount " + eCnt, expected.getOnDuty16HrCount(), s.getOnDuty16HrCount());
            }
        }
    }

    @Test
    public void gainDetailsTestCases() {
        for (int testCaseCnt = 0; testCaseCnt < testCaseName.length; testCaseCnt++) {
            HosRecordDataSet violationsTestData = new HosRecordDataSet(DATA_PATH, testCaseName[testCaseCnt], true);
            // HOS VIOLATIONS
            HosViolationsDetailReportCriteria criteria = new HosViolationsDetailReportCriteria(Locale.US);
            criteria.initDataSet(violationsTestData.interval, violationsTestData.getGroupHierarchy(), violationsTestData.driverHOSRecordMap);
            List<ViolationsDetail> dataList = criteria.getMainDataset();
//System.out.println("testcasecnt: " + testCaseCnt);            
//             for (ViolationsDetail data : dataList)
//                 data.dump();
            assertEquals(testCaseName[testCaseCnt] + " number of records", hosViolationsDetailExpectedData[testCaseCnt].length, dataList.size());
            int eCnt = 0;
            for (ViolationsDetail s : dataList) {
                compareViolationDetails(testCaseCnt, hosViolationsDetailExpectedData[testCaseCnt][eCnt++], s);
            }
            dump("hosViolationsDetailTest", testCaseCnt + 1, criteria, FormatType.PDF);
            dump("hosViolationsDetailTest", testCaseCnt + 1, criteria, FormatType.HTML);
            // NON-DOT VIOLATIONS
            NonDOTViolationsDetailReportCriteria nonDOTCriteria = new NonDOTViolationsDetailReportCriteria(Locale.US);
            nonDOTCriteria.initDataSet(violationsTestData.interval, violationsTestData.getGroupHierarchy(), violationsTestData.driverHOSRecordMap);
            dataList = nonDOTCriteria.getMainDataset();
//            for (ViolationsDetail data : dataList)
//             data.dump();
            eCnt = 0;
            for (ViolationsDetail s : dataList) {
                compareViolationDetails(testCaseCnt, nonDOTViolationsDetailExpectedData[testCaseCnt][eCnt++], s);
            }
            dump("nonDOTViolationsDetailTest", testCaseCnt + 1, nonDOTCriteria, FormatType.PDF);
            dump("nonDOTViolationsDetailTest", testCaseCnt + 1, nonDOTCriteria, FormatType.EXCEL);
            // Driving Time VIOLATIONS
            DrivingTimeViolationsDetailReportCriteria drivingTimeCriteria = new DrivingTimeViolationsDetailReportCriteria(Locale.US);
            drivingTimeCriteria.initDataSet(violationsTestData.interval, violationsTestData.getGroupHierarchy(), violationsTestData.driverHOSRecordMap);
            dataList = drivingTimeCriteria.getMainDataset();
//          for (ViolationsDetail data : dataList)
//          data.dump();
            eCnt = 0;
            for (ViolationsDetail s : dataList) {
                compareViolationDetails(testCaseCnt, drivingTimeViolationsDetailExpectedData[testCaseCnt][eCnt++], s);
            }
            dump("drivingTimeViolationsDetailTest", testCaseCnt + 1, drivingTimeCriteria, FormatType.PDF);
            dump("drivingTimeViolationsDetailTest", testCaseCnt + 1, drivingTimeCriteria, FormatType.EXCEL);
        }
    }
    
    @Test
    public void hosDetails()
    {
        for (RuleSetType ruleSetType : RuleSetType.values()) {
            if (ruleSetType == RuleSetType.SLB_INTERNAL)
                continue;
            HosRecordDataSet violationsTestData = createHosDetailsDataSet(ruleSetType);
            HosViolationsDetailReportCriteria criteria = new HosViolationsDetailReportCriteria(Locale.US);
            criteria.initDataSet(violationsTestData.interval, violationsTestData.getGroupHierarchy(), violationsTestData.driverHOSRecordMap);
            List<ViolationsDetail> dataList = criteria.getMainDataset();
            Integer violationCount = 0;
//            System.out.println("expectedHosDetailMap.put(RuleSetType." + ruleSetType + " , ");
            for (ViolationsDetail detail : dataList) {
//                detail.dump();
                violationCount += detail.getViolationsList().size();
            }
            Integer expectedViolationCount = expectedHosDetailCountMap.get(ruleSetType);
            if (expectedViolationCount == null) 
                expectedViolationCount = 0;
            assertEquals("Unexpected Details Violation Count for ruleset type: " + ruleSetType, expectedViolationCount, violationCount);
            
            dump("hosDetails_"+ruleSetType.getName(), 0, criteria, FormatType.PDF);
            dump("hosDetails_"+ruleSetType.getName(), 0, criteria, FormatType.EXCEL);

        
            HosViolationsSummaryReportCriteria sumCriteria = new HosViolationsSummaryReportCriteria(Locale.US);
            sumCriteria.initDataSet(violationsTestData.interval, violationsTestData.getGroupHierarchy(),
                    violationsTestData.getGroupHierarchy().getGroupList(), violationsTestData.driverHOSRecordMap,
                    violationsTestData.groupMileageList, violationsTestData.groupNoDriverMileageList);
            
            List<HosViolationsSummary> sumDataList = sumCriteria.getMainDataset();
            
            Integer sumViolationCount = 0;
            for (HosViolationsSummary sum : sumDataList) {
                sumViolationCount += sum.getCumulative_1() + sum.getCumulative_2() + sum.getCumulative_3() +
                                     sum.getDriving_1() + sum.getDriving_2() + sum.getDriving_3() +
                                     sum.getOffDuty_1() + sum.getOffDuty_2() + sum.getOffDuty_3() +
                                     sum.getOnDuty_1() + sum.getOnDuty_2() + sum.getOnDuty_3();
            }
            assertEquals("Unexpected Summary Violation Count for ruleset type: " + ruleSetType, expectedViolationCount, sumViolationCount);
            
            dump("hosSummary_"+ruleSetType.getName(), 0, sumCriteria, FormatType.PDF);
            dump("hosSummary_"+ruleSetType.getName(), 0, sumCriteria, FormatType.EXCEL);

        }
    }
    
    private HosRecordDataSet createHosDetailsDataSet(RuleSetType ruleSetType) {
        
        DateTimeZone dateTimeZone = DateTimeZone.forTimeZone(TimeZone.getTimeZone("MST"));
        DateTime dateTime = new DateMidnight(new DateTime(), dateTimeZone).minusDays(1).toDateTime();

        HosRecordDataSet violationsTestData = new HosRecordDataSet(new Interval(dateTime, dateTime.plusDays(1)));
        Person person = mockPerson(violationsTestData.account.getAccountID(), dateTimeZone);
        Driver driver = mockDriver(person, violationsTestData.topGroup.getGroupID(), ruleSetType);
        
        List<HOSRecord> recordList = new ArrayList<HOSRecord>();
        
        // start driving at 1:00 am yesterday and stop at midnight)
        DateTime logTime = dateTime.plusHours(24);
        HOSRecord record = new HOSRecord(1, driver.getDriverID(),
                ruleSetType, null, null, Boolean.FALSE,
                0l, logTime.toDate(), logTime.toDate(), person.getTimeZone(), HOSStatus.OFF_DUTY, HOSOrigin.PORTAL, "SLC, UT", 
                0.0f, 0.0f, 0l,
                null, null, Boolean.TRUE, Boolean.TRUE, "TEST USER", Boolean.FALSE);
        recordList.add(record);
        logTime = dateTime.plusHours(1);
        record = new HOSRecord(1, driver.getDriverID(),
                ruleSetType, null, null, Boolean.FALSE,
                0l, logTime.toDate(), logTime.toDate(), person.getTimeZone(), HOSStatus.DRIVING, HOSOrigin.PORTAL, "SLC, UT", 
                0.0f, 0.0f, 0l,
                null, null, Boolean.TRUE, Boolean.TRUE, "TEST USER", Boolean.FALSE);
        recordList.add(record);
        // for 24 days prior last 2 hours of the day off duty and previous 22 on duty
        for (int i = 0; i < 24; i++) {
            logTime = dateTime.minusHours(2);
            record = new HOSRecord(1, driver.getDriverID(),
                    ruleSetType, null, null, Boolean.FALSE,
                    0l, logTime.toDate(), logTime.toDate(), person.getTimeZone(), HOSStatus.OFF_DUTY, HOSOrigin.PORTAL, "SLC, UT", 
                    0.0f, 0.0f, 0l,
                    null, null, Boolean.TRUE, Boolean.TRUE, "TEST USER", Boolean.FALSE);
            recordList.add(record);
            logTime = dateTime.minusHours(24);
            record = new HOSRecord(1, driver.getDriverID(),
                    ruleSetType, null, null, Boolean.FALSE,
                    0l, logTime.toDate(), logTime.toDate(), person.getTimeZone(), HOSStatus.ON_DUTY, HOSOrigin.PORTAL, "SLC, UT", 
                    0.0f, 0.0f, 0l,
                    null, null, Boolean.TRUE, Boolean.TRUE, "TEST USER", Boolean.FALSE);
            recordList.add(record);
            
            dateTime = dateTime.minusDays(1);
        }
        
        
        violationsTestData.driverHOSRecordMap.put(driver, recordList);
        return violationsTestData;
        
    }
    
    private Driver mockDriver(Person person, Integer groupID, RuleSetType ruleSetType) {
        Driver driver = new Driver();
        driver.setDriverID(person.getPersonID());
        driver.setPersonID(person.getPersonID());
        driver.setPerson(person);
        driver.setGroupID(groupID);
        driver.setDot(ruleSetType);
        person.setDriver(driver);
        
        return driver;
    }

    private Person mockPerson(Integer accountID, DateTimeZone dateTimeZone)
    {
        Person person = new Person();
        person.setAcctID(accountID);
        person.setPersonID(1);
        person.setEmpid("mock01");
        person.setPriEmail("mock@email.com");
        person.setFirst("First");
        person.setLast("Last");
        person.setTimeZone(dateTimeZone.toTimeZone());
        person.setMeasurementType(MeasurementType.ENGLISH);
        return person;
    }

    private void compareViolationDetails(int testCaseCnt, ViolationsDetail expected, ViolationsDetail s) {
        assertEquals(testCaseName[testCaseCnt] + " driverName ", expected.getDriverName(), s.getDriverName());
        assertEquals(testCaseName[testCaseCnt] + " EmployeeId ", expected.getEmployeeId(), s.getEmployeeId());
        assertEquals(testCaseName[testCaseCnt] + " NotificationTime ", expected.getNotificationTime(), s.getNotificationTime());
        assertEquals(testCaseName[testCaseCnt] + " ruleType ", expected.getRuleType(), s.getRuleType());
        if (expected.getVehicleId() == null)
            assertNull(testCaseName[testCaseCnt] + " vehicleID ", s.getVehicleId());
        else
            assertEquals(testCaseName[testCaseCnt] + " vehicleID ", expected.getVehicleId(), s.getVehicleId());
        assertEquals(testCaseName[testCaseCnt] + " violationList size ", expected.getViolationsList().size(), s.getViolationsList().size());
    }

}
