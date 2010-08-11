package com.inthinc.pro.reports.hos;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;

import org.joda.time.Interval;
import org.junit.Test;

import com.inthinc.hos.model.HOSStatus;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.hos.HOSRecord;

import com.inthinc.pro.reports.FormatType;
import com.inthinc.pro.reports.hos.model.DriverDOTLog;
import com.inthinc.pro.reports.hos.model.PayrollData;


public class PayrollCriteriaTest extends BaseHosRecordUnitTest {

    public static final String DATA_PATH = "violations/";
    public static final String testCaseName[] = { 
        "vtest_01H1_07012010_07072010", 
    };
    
    PayrollData payrollDataExpectedData[][] = {
    {
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",0,"Elias,  Calvin","01790856",new Date(1277964000000l),HOSStatus.OFF_DUTY,1440),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",0,"Elias,  Calvin","01790856",new Date(1278050400000l),HOSStatus.OFF_DUTY,1440),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",0,"Elias,  Calvin","01790856",new Date(1278136800000l),HOSStatus.OFF_DUTY,1440),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",0,"Elias,  Calvin","01790856",new Date(1278223200000l),HOSStatus.OFF_DUTY,1440),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",0,"Elias,  Calvin","01790856",new Date(1278309600000l),HOSStatus.OFF_DUTY,1440),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",0,"Elias,  Calvin","01790856",new Date(1278396000000l),HOSStatus.OFF_DUTY,1440),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",0,"Elias,  Calvin","01790856",new Date(1278482400000l),HOSStatus.OFF_DUTY,1440),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1277964000000l),HOSStatus.OFF_DUTY,510),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1277964000000l),HOSStatus.DRIVING,30),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1277964000000l),HOSStatus.ON_DUTY,405),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1277964000000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1277964000000l),HOSStatus.OFF_DUTY,480),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278050400000l),HOSStatus.OFF_DUTY,690),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278050400000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278050400000l),HOSStatus.ON_DUTY,75),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278050400000l),HOSStatus.OFF_DUTY,135),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278050400000l),HOSStatus.OFF_DUTY,165),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278050400000l),HOSStatus.OFF_DUTY,360),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278136800000l),HOSStatus.OFF_DUTY,1440),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278223200000l),HOSStatus.OFF_DUTY,525),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278223200000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278223200000l),HOSStatus.ON_DUTY,90),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278223200000l),HOSStatus.DRIVING,75),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278223200000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278223200000l),HOSStatus.ON_DUTY,90),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278223200000l),HOSStatus.DRIVING,30),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278223200000l),HOSStatus.ON_DUTY,60),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278223200000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278223200000l),HOSStatus.ON_DUTY,30),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278223200000l),HOSStatus.ON_DUTY,30),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278223200000l),HOSStatus.DRIVING,30),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278223200000l),HOSStatus.ON_DUTY,225),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278223200000l),HOSStatus.OFF_DUTY,210),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278309600000l),HOSStatus.OFF_DUTY,420),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278309600000l),HOSStatus.ON_DUTY,60),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278309600000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278309600000l),HOSStatus.ON_DUTY,45),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278309600000l),HOSStatus.DRIVING,60),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278309600000l),HOSStatus.ON_DUTY,90),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278309600000l),HOSStatus.DRIVING,30),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278309600000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278309600000l),HOSStatus.ON_DUTY,45),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278309600000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278309600000l),HOSStatus.ON_DUTY,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278309600000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278309600000l),HOSStatus.ON_DUTY,30),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278309600000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278309600000l),HOSStatus.ON_DUTY,60),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278309600000l),HOSStatus.DRIVING,45),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278309600000l),HOSStatus.ON_DUTY,30),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278309600000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278309600000l),HOSStatus.OFF_DUTY,420),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278396000000l),HOSStatus.OFF_DUTY,420),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278396000000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278396000000l),HOSStatus.ON_DUTY,225),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278396000000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278396000000l),HOSStatus.ON_DUTY,45),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278396000000l),HOSStatus.ON_DUTY,60),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278396000000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278396000000l),HOSStatus.ON_DUTY,60),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278396000000l),HOSStatus.ON_DUTY,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278396000000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278396000000l),HOSStatus.ON_DUTY,150),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278396000000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278396000000l),HOSStatus.OFF_DUTY,390),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278482400000l),HOSStatus.OFF_DUTY,540),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278482400000l),HOSStatus.ON_DUTY,45),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278482400000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278482400000l),HOSStatus.ON_DUTY,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278482400000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278482400000l),HOSStatus.ON_DUTY,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278482400000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278482400000l),HOSStatus.ON_DUTY,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278482400000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278482400000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278482400000l),HOSStatus.OFF_DUTY,735),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1277964000000l),HOSStatus.OFF_DUTY,495),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1277964000000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1277964000000l),HOSStatus.DRIVING,105),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1277964000000l),HOSStatus.ON_DUTY,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1277964000000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1277964000000l),HOSStatus.ON_DUTY,75),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1277964000000l),HOSStatus.ON_DUTY,30),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1277964000000l),HOSStatus.DRIVING,30),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1277964000000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1277964000000l),HOSStatus.ON_DUTY,45),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1277964000000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1277964000000l),HOSStatus.ON_DUTY,30),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1277964000000l),HOSStatus.ON_DUTY,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1277964000000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1277964000000l),HOSStatus.ON_DUTY,75),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1277964000000l),HOSStatus.OFF_DUTY,450),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278050400000l),HOSStatus.OFF_DUTY,435),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278050400000l),HOSStatus.ON_DUTY,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278050400000l),HOSStatus.ON_DUTY,240),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278050400000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278050400000l),HOSStatus.ON_DUTY,30),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278050400000l),HOSStatus.ON_DUTY,270),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278050400000l),HOSStatus.OFF_DUTY,435),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278136800000l),HOSStatus.OFF_DUTY,525),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278136800000l),HOSStatus.ON_DUTY,30),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278136800000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278136800000l),HOSStatus.ON_DUTY,135),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278136800000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278136800000l),HOSStatus.ON_DUTY,30),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278136800000l),HOSStatus.ON_DUTY,105),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278136800000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278136800000l),HOSStatus.ON_DUTY,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278136800000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278136800000l),HOSStatus.ON_DUTY,45),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278136800000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278136800000l),HOSStatus.OFF_DUTY,480),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278223200000l),HOSStatus.OFF_DUTY,525),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278223200000l),HOSStatus.ON_DUTY,60),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278223200000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278223200000l),HOSStatus.ON_DUTY,30),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278223200000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278223200000l),HOSStatus.ON_DUTY,30),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278223200000l),HOSStatus.ON_DUTY,555),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278223200000l),HOSStatus.OFF_DUTY,210),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278309600000l),HOSStatus.OFF_DUTY,435),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278309600000l),HOSStatus.ON_DUTY,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278309600000l),HOSStatus.ON_DUTY,195),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278309600000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278309600000l),HOSStatus.ON_DUTY,375),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278309600000l),HOSStatus.OFF_DUTY,405),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278396000000l),HOSStatus.OFF_DUTY,420),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278396000000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278396000000l),HOSStatus.ON_DUTY,225),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278396000000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278396000000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278396000000l),HOSStatus.ON_DUTY,30),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278396000000l),HOSStatus.ON_DUTY,60),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278396000000l),HOSStatus.ON_DUTY,45),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278396000000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278396000000l),HOSStatus.ON_DUTY,225),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278396000000l),HOSStatus.OFF_DUTY,105),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278396000000l),HOSStatus.ON_DUTY,30),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278396000000l),HOSStatus.OFF_DUTY,240),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278482400000l),HOSStatus.OFF_DUTY,450),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278482400000l),HOSStatus.ON_DUTY,120),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278482400000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278482400000l),HOSStatus.ON_DUTY,90),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278482400000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278482400000l),HOSStatus.ON_DUTY,150),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278482400000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278482400000l),HOSStatus.ON_DUTY,240),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278482400000l),HOSStatus.OFF_DUTY,345),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1277964000000l),HOSStatus.OFF_DUTY_OCCUPANT,465),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1277964000000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1277964000000l),HOSStatus.ON_DUTY,30),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1277964000000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1277964000000l),HOSStatus.ON_DUTY,30),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1277964000000l),HOSStatus.ON_DUTY,390),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1277964000000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1277964000000l),HOSStatus.ON_DUTY,45),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1277964000000l),HOSStatus.OFF_DUTY,435),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1278050400000l),HOSStatus.OFF_DUTY,1440),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1278136800000l),HOSStatus.OFF_DUTY,1440),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1278223200000l),HOSStatus.OFF_DUTY,1440),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1278309600000l),HOSStatus.OFF_DUTY,1005),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1278309600000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1278309600000l),HOSStatus.OFF_DUTY,420),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1278396000000l),HOSStatus.OFF_DUTY,480),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1278396000000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1278396000000l),HOSStatus.ON_DUTY,225),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1278396000000l),HOSStatus.ON_DUTY,60),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1278396000000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1278396000000l),HOSStatus.ON_DUTY,270),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1278396000000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1278396000000l),HOSStatus.ON_DUTY,30),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1278396000000l),HOSStatus.ON_DUTY,330),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1278482400000l),HOSStatus.ON_DUTY,480),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1278482400000l),HOSStatus.OFF_DUTY,465),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1278482400000l),HOSStatus.OFF_DUTY_OCCUPANT,495),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",4,"Johnson,  Jack ","02341923",new Date(1277964000000l),HOSStatus.OFF_DUTY,1440),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",4,"Johnson,  Jack ","02341923",new Date(1278050400000l),HOSStatus.OFF_DUTY,1440),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",4,"Johnson,  Jack ","02341923",new Date(1278136800000l),HOSStatus.OFF_DUTY,705),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",4,"Johnson,  Jack ","02341923",new Date(1278136800000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",4,"Johnson,  Jack ","02341923",new Date(1278136800000l),HOSStatus.ON_DUTY,30),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",4,"Johnson,  Jack ","02341923",new Date(1278136800000l),HOSStatus.OFF_DUTY,690),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",4,"Johnson,  Jack ","02341923",new Date(1278223200000l),HOSStatus.OFF_DUTY,1440),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",4,"Johnson,  Jack ","02341923",new Date(1278309600000l),HOSStatus.OFF_DUTY,1440),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",4,"Johnson,  Jack ","02341923",new Date(1278396000000l),HOSStatus.OFF_DUTY,1440),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",4,"Johnson,  Jack ","02341923",new Date(1278482400000l),HOSStatus.OFF_DUTY,1440),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",5,"Pierrot,  Collin","03190857",new Date(1277964000000l),HOSStatus.OFF_DUTY,1440),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",5,"Pierrot,  Collin","03190857",new Date(1278050400000l),HOSStatus.OFF_DUTY,1440),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",5,"Pierrot,  Collin","03190857",new Date(1278136800000l),HOSStatus.OFF_DUTY,1440),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",5,"Pierrot,  Collin","03190857",new Date(1278223200000l),HOSStatus.OFF_DUTY,1440),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",5,"Pierrot,  Collin","03190857",new Date(1278309600000l),HOSStatus.OFF_DUTY,1440),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",5,"Pierrot,  Collin","03190857",new Date(1278396000000l),HOSStatus.OFF_DUTY,1440),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",5,"Pierrot,  Collin","03190857",new Date(1278482400000l),HOSStatus.OFF_DUTY,1440),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1277964000000l),HOSStatus.OFF_DUTY,495),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1277964000000l),HOSStatus.ON_DUTY,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1277964000000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1277964000000l),HOSStatus.ON_DUTY,420),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1277964000000l),HOSStatus.DRIVING,30),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1277964000000l),HOSStatus.ON_DUTY,30),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1277964000000l),HOSStatus.OFF_DUTY,435),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278050400000l),HOSStatus.OFF_DUTY,420),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278050400000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278050400000l),HOSStatus.ON_DUTY,255),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278050400000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278050400000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278050400000l),HOSStatus.ON_DUTY,30),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278050400000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278050400000l),HOSStatus.ON_DUTY,240),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278050400000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278050400000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278050400000l),HOSStatus.OFF_DUTY,405),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278136800000l),HOSStatus.OFF_DUTY,495),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278136800000l),HOSStatus.ON_DUTY,105),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278136800000l),HOSStatus.ON_DUTY,45),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278136800000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278136800000l),HOSStatus.ON_DUTY,270),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278136800000l),HOSStatus.ON_DUTY,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278136800000l),HOSStatus.OFF_DUTY,495),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278223200000l),HOSStatus.OFF_DUTY,1440),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278309600000l),HOSStatus.OFF_DUTY,1440),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278396000000l),HOSStatus.OFF_DUTY,855),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278396000000l),HOSStatus.ON_DUTY,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278396000000l),HOSStatus.ON_DUTY,570),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278482400000l),HOSStatus.ON_DUTY,435),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278482400000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278482400000l),HOSStatus.ON_DUTY,75),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278482400000l),HOSStatus.ON_DUTY,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278482400000l),HOSStatus.OFF_DUTY,345),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278482400000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278482400000l),HOSStatus.ON_DUTY,180),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278482400000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278482400000l),HOSStatus.DRIVING,15),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278482400000l),HOSStatus.OFF_DUTY,330),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",7,"Venkataraman,  Ramanathan","02707354",new Date(1277964000000l),HOSStatus.OFF_DUTY,1440),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",7,"Venkataraman,  Ramanathan","02707354",new Date(1278050400000l),HOSStatus.OFF_DUTY,1440),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",7,"Venkataraman,  Ramanathan","02707354",new Date(1278136800000l),HOSStatus.OFF_DUTY,1440),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",7,"Venkataraman,  Ramanathan","02707354",new Date(1278223200000l),HOSStatus.OFF_DUTY,1440),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",7,"Venkataraman,  Ramanathan","02707354",new Date(1278309600000l),HOSStatus.OFF_DUTY,1440),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",7,"Venkataraman,  Ramanathan","02707354",new Date(1278396000000l),HOSStatus.OFF_DUTY,1440),
        new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",7,"Venkataraman,  Ramanathan","02707354",new Date(1278482400000l),HOSStatus.OFF_DUTY,1440),                
    }
    };

    
    

    @Test
    public void gainDetailsTestCases() {
        for (int testCaseCnt = 0; testCaseCnt < testCaseName.length; testCaseCnt++) {
            TestData testData = new TestData(DATA_PATH, testCaseName[testCaseCnt], false);
            
            PayrollDetailReportCriteria criteria = new PayrollDetailReportCriteria(Locale.US);
            criteria.initDataSet(testData.interval, testData.account, testData.topGroup, testData.groupList, testData.driverHOSRecordMap);
            List<PayrollData> dataList = criteria.getMainDataset();
            int eCnt = 0;
            for (PayrollData data : dataList) {
                PayrollData expected = payrollDataExpectedData[testCaseCnt][eCnt++];
                assertEquals(testCaseName[testCaseCnt] + "groupName " + eCnt, expected.getGroupName(), data.getGroupName());
                assertEquals(testCaseName[testCaseCnt] + "groupAddress " + eCnt, expected.getGroupAddress(), data.getGroupAddress());
                assertEquals(testCaseName[testCaseCnt] + "driverName " + eCnt, expected.getDriverName(), data.getDriverName());
                assertEquals(testCaseName[testCaseCnt] + "day " + eCnt, expected.getDay(), data.getDay());
                assertEquals(testCaseName[testCaseCnt] + "employeeID " + eCnt, expected.getEmployeeID(), data.getEmployeeID());
            }
            
            dump("payrollDetailTest", testCaseCnt + 1, criteria, FormatType.PDF);
            dump("payrollDetailTest", testCaseCnt + 1, criteria, FormatType.EXCEL);

        }
    }

    @Test
    public void gainSignoffTestCases() {
        for (int testCaseCnt = 0; testCaseCnt < testCaseName.length; testCaseCnt++) {
            TestData testData = new TestData(DATA_PATH, testCaseName[testCaseCnt], false);
            
            PayrollSignoffReportCriteria criteria = new PayrollSignoffReportCriteria(Locale.US);
            
            for (Entry<Driver, List<HOSRecord>> entry : testData.driverHOSRecordMap.entrySet()) {
                
                Driver driver = entry.getKey();
                criteria.initDataSet(testData.interval, testData.account, testData.topGroup, testData.groupList, driver, entry.getValue());

                if (driver.getPerson().getLast().startsWith("Giem")) {
                    int eCnt = 0;
                    for (;eCnt < payrollDataExpectedData[testCaseCnt].length; eCnt++) {
                        PayrollData expected = payrollDataExpectedData[testCaseCnt][eCnt];
                        if (expected.getDriverName().startsWith("Giem"))
                            break;
                    }
                    List<PayrollData> dataList = criteria.getMainDataset();
                    for (PayrollData data : dataList) {
                        PayrollData expected = payrollDataExpectedData[testCaseCnt][eCnt++];
                        assertEquals(testCaseName[testCaseCnt] + "groupName " + eCnt, expected.getGroupName(), data.getGroupName());
                        assertEquals(testCaseName[testCaseCnt] + "groupAddress " + eCnt, expected.getGroupAddress(), data.getGroupAddress());
                        assertEquals(testCaseName[testCaseCnt] + "driverName " + eCnt, expected.getDriverName(), data.getDriverName());
                        assertEquals(testCaseName[testCaseCnt] + "day " + eCnt, expected.getDay(), data.getDay());
                        assertEquals(testCaseName[testCaseCnt] + "employeeID " + eCnt, expected.getEmployeeID(), data.getEmployeeID());
                    }
                }
                String prefix = "payrollSignoffTest "+entry.getKey().getPerson().getLast() + "_"; 
                dump(prefix, testCaseCnt + 1, criteria, FormatType.PDF);
                dump(prefix, testCaseCnt + 1, criteria, FormatType.EXCEL);
                

            }
        }
    }

}
