package com.inthinc.pro.reports.performance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.Test;

import com.inthinc.hos.model.HOSStatus;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.reports.BaseUnitTest;
import com.inthinc.pro.reports.FormatType;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.hos.testData.HosRecordDataSet;
import com.inthinc.pro.reports.performance.model.PayrollData;
import com.inthinc.pro.reports.tabular.Result;

public class PayrollCriteriaTest extends BaseUnitTest {

    public static final String DATA_PATH = "violations/";
    public static final String testCaseName = "vtest_01H1_07012010_07072010"; 

    static PayrollData payrollDataExpectedData[] = {
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",0,"Elias,  Calvin","01790856",new Date(1277964000000l),HOSStatus.OFF_DUTY,1440),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",0,"Elias,  Calvin","01790856",new Date(1278050400000l),HOSStatus.OFF_DUTY,1440),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",0,"Elias,  Calvin","01790856",new Date(1278136800000l),HOSStatus.OFF_DUTY,1440),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",0,"Elias,  Calvin","01790856",new Date(1278223200000l),HOSStatus.OFF_DUTY,1440),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",0,"Elias,  Calvin","01790856",new Date(1278309600000l),HOSStatus.OFF_DUTY,1440),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",0,"Elias,  Calvin","01790856",new Date(1278396000000l),HOSStatus.OFF_DUTY,1440),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",0,"Elias,  Calvin","01790856",new Date(1278482400000l),HOSStatus.OFF_DUTY,1440),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1277964000000l),HOSStatus.OFF_DUTY,510),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1277964000000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1277964000000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1277964000000l),HOSStatus.ON_DUTY,390),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1277964000000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1277964000000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1277964000000l),HOSStatus.OFF_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1277964000000l),HOSStatus.OFF_DUTY,465),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278050400000l),HOSStatus.OFF_DUTY,690),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278050400000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278050400000l),HOSStatus.ON_DUTY,60),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278050400000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278050400000l),HOSStatus.OFF_DUTY,120),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278050400000l),HOSStatus.OFF_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278050400000l),HOSStatus.OFF_DUTY,150),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278050400000l),HOSStatus.OFF_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278050400000l),HOSStatus.OFF_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278050400000l),HOSStatus.OFF_DUTY,345),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278136800000l),HOSStatus.OFF_DUTY,1440),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278223200000l),HOSStatus.OFF_DUTY,525),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278223200000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278223200000l),HOSStatus.ON_DUTY,75),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278223200000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278223200000l),HOSStatus.DRIVING,75),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278223200000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278223200000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278223200000l),HOSStatus.ON_DUTY,75),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278223200000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278223200000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278223200000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278223200000l),HOSStatus.ON_DUTY,45),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278223200000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278223200000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278223200000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278223200000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278223200000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278223200000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278223200000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278223200000l),HOSStatus.ON_DUTY,210),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278223200000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278223200000l),HOSStatus.OFF_DUTY,210),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278309600000l),HOSStatus.OFF_DUTY,405),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278309600000l),HOSStatus.OFF_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278309600000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278309600000l),HOSStatus.ON_DUTY,30),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278309600000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278309600000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278309600000l),HOSStatus.ON_DUTY,45),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278309600000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278309600000l),HOSStatus.DRIVING,30),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278309600000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278309600000l),HOSStatus.ON_DUTY,75),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278309600000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278309600000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278309600000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278309600000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278309600000l),HOSStatus.ON_DUTY,45),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278309600000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278309600000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278309600000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278309600000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278309600000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278309600000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278309600000l),HOSStatus.ON_DUTY,60),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278309600000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278309600000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278309600000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278309600000l),HOSStatus.ON_DUTY,30),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278309600000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278309600000l),HOSStatus.OFF_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278309600000l),HOSStatus.OFF_DUTY,405),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278396000000l),HOSStatus.OFF_DUTY,420),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278396000000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278396000000l),HOSStatus.ON_DUTY,225),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278396000000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278396000000l),HOSStatus.ON_DUTY,30),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278396000000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278396000000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278396000000l),HOSStatus.ON_DUTY,45),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278396000000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278396000000l),HOSStatus.ON_DUTY,45),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278396000000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278396000000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278396000000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278396000000l),HOSStatus.ON_DUTY,150),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278396000000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278396000000l),HOSStatus.OFF_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278396000000l),HOSStatus.OFF_DUTY,375),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278482400000l),HOSStatus.OFF_DUTY,525),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278482400000l),HOSStatus.OFF_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278482400000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278482400000l),HOSStatus.ON_DUTY,30),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278482400000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278482400000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278482400000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278482400000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278482400000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278482400000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278482400000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278482400000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",1,"Francey,  David ","02289734",new Date(1278482400000l),HOSStatus.OFF_DUTY,735),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1277964000000l),HOSStatus.OFF_DUTY,495),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1277964000000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1277964000000l),HOSStatus.DRIVING,90),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1277964000000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1277964000000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1277964000000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1277964000000l),HOSStatus.ON_DUTY,60),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1277964000000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1277964000000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1277964000000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1277964000000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1277964000000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1277964000000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1277964000000l),HOSStatus.ON_DUTY,30),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1277964000000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1277964000000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1277964000000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1277964000000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1277964000000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1277964000000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1277964000000l),HOSStatus.ON_DUTY,60),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1277964000000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1277964000000l),HOSStatus.OFF_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1277964000000l),HOSStatus.OFF_DUTY,435),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278050400000l),HOSStatus.OFF_DUTY,435),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278050400000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278050400000l),HOSStatus.ON_DUTY,240),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278050400000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278050400000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278050400000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278050400000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278050400000l),HOSStatus.ON_DUTY,255),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278050400000l),HOSStatus.OFF_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278050400000l),HOSStatus.OFF_DUTY,420),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278136800000l),HOSStatus.OFF_DUTY,510),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278136800000l),HOSStatus.OFF_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278136800000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278136800000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278136800000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278136800000l),HOSStatus.ON_DUTY,135),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278136800000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278136800000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278136800000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278136800000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278136800000l),HOSStatus.ON_DUTY,75),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278136800000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278136800000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278136800000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278136800000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278136800000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278136800000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278136800000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278136800000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278136800000l),HOSStatus.OFF_DUTY,480),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278223200000l),HOSStatus.OFF_DUTY,510),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278223200000l),HOSStatus.OFF_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278223200000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278223200000l),HOSStatus.ON_DUTY,30),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278223200000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278223200000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278223200000l),HOSStatus.ON_DUTY,30),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278223200000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278223200000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278223200000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278223200000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278223200000l),HOSStatus.ON_DUTY,525),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278223200000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278223200000l),HOSStatus.OFF_DUTY,210),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278309600000l),HOSStatus.OFF_DUTY,435),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278309600000l),HOSStatus.OFF_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278309600000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278309600000l),HOSStatus.ON_DUTY,180),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278309600000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278309600000l),HOSStatus.ON_DUTY,360),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278309600000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278309600000l),HOSStatus.OFF_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278309600000l),HOSStatus.OFF_DUTY,390),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278396000000l),HOSStatus.OFF_DUTY,420),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278396000000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278396000000l),HOSStatus.ON_DUTY,225),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278396000000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278396000000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278396000000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278396000000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278396000000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278396000000l),HOSStatus.ON_DUTY,30),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278396000000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278396000000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278396000000l),HOSStatus.ON_DUTY,30),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278396000000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278396000000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278396000000l),HOSStatus.ON_DUTY,195),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278396000000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278396000000l),HOSStatus.OFF_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278396000000l),HOSStatus.OFF_DUTY,75),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278396000000l),HOSStatus.OFF_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278396000000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278396000000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278396000000l),HOSStatus.OFF_DUTY,240),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278482400000l),HOSStatus.OFF_DUTY,435),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278482400000l),HOSStatus.OFF_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278482400000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278482400000l),HOSStatus.ON_DUTY,90),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278482400000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278482400000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278482400000l),HOSStatus.ON_DUTY,75),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278482400000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278482400000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278482400000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278482400000l),HOSStatus.ON_DUTY,120),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278482400000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278482400000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278482400000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278482400000l),HOSStatus.ON_DUTY,210),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278482400000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",2,"Giem,  Scott","00317263",new Date(1278482400000l),HOSStatus.OFF_DUTY,345),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1277964000000l),HOSStatus.OFF_DUTY_OCCUPANT,465),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1277964000000l),HOSStatus.OFF_DUTY_OCCUPANT,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1277964000000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1277964000000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1277964000000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1277964000000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1277964000000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1277964000000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1277964000000l),HOSStatus.ON_DUTY,375),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1277964000000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1277964000000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1277964000000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1277964000000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1277964000000l),HOSStatus.OFF_DUTY,435),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1278050400000l),HOSStatus.OFF_DUTY,1440),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1278136800000l),HOSStatus.OFF_DUTY,1440),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1278223200000l),HOSStatus.OFF_DUTY,1440),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1278309600000l),HOSStatus.OFF_DUTY,1005),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1278309600000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1278309600000l),HOSStatus.OFF_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1278309600000l),HOSStatus.OFF_DUTY,405),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1278396000000l),HOSStatus.OFF_DUTY,480),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1278396000000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1278396000000l),HOSStatus.ON_DUTY,210),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1278396000000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1278396000000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1278396000000l),HOSStatus.ON_DUTY,30),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1278396000000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1278396000000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1278396000000l),HOSStatus.ON_DUTY,255),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1278396000000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1278396000000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1278396000000l),HOSStatus.ON_DUTY,30),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1278396000000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1278396000000l),HOSStatus.ON_DUTY,315),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1278482400000l),HOSStatus.ON_DUTY,465),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1278482400000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1278482400000l),HOSStatus.OFF_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1278482400000l),HOSStatus.OFF_DUTY,450),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1278482400000l),HOSStatus.OFF_DUTY_OCCUPANT,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",3,"Harris,  Eugene","01041730",new Date(1278482400000l),HOSStatus.OFF_DUTY_OCCUPANT,480),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",4,"Johnson,  Jack ","02341923",new Date(1277964000000l),HOSStatus.OFF_DUTY,1440),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",4,"Johnson,  Jack ","02341923",new Date(1278050400000l),HOSStatus.OFF_DUTY,1440),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",4,"Johnson,  Jack ","02341923",new Date(1278136800000l),HOSStatus.OFF_DUTY,705),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",4,"Johnson,  Jack ","02341923",new Date(1278136800000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",4,"Johnson,  Jack ","02341923",new Date(1278136800000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",4,"Johnson,  Jack ","02341923",new Date(1278136800000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",4,"Johnson,  Jack ","02341923",new Date(1278136800000l),HOSStatus.OFF_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",4,"Johnson,  Jack ","02341923",new Date(1278136800000l),HOSStatus.OFF_DUTY,675),
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
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1277964000000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1277964000000l),HOSStatus.ON_DUTY,390),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1277964000000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1277964000000l),HOSStatus.DRIVING,30),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1277964000000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1277964000000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1277964000000l),HOSStatus.OFF_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1277964000000l),HOSStatus.OFF_DUTY,420),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278050400000l),HOSStatus.OFF_DUTY,420),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278050400000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278050400000l),HOSStatus.ON_DUTY,240),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278050400000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278050400000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278050400000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278050400000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278050400000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278050400000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278050400000l),HOSStatus.ON_DUTY,240),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278050400000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278050400000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278050400000l),HOSStatus.OFF_DUTY,405),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278136800000l),HOSStatus.OFF_DUTY,480),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278136800000l),HOSStatus.OFF_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278136800000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278136800000l),HOSStatus.ON_DUTY,75),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278136800000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278136800000l),HOSStatus.ON_DUTY,45),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278136800000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278136800000l),HOSStatus.ON_DUTY,255),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278136800000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278136800000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278136800000l),HOSStatus.OFF_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278136800000l),HOSStatus.OFF_DUTY,480),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278223200000l),HOSStatus.OFF_DUTY,1440),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278309600000l),HOSStatus.OFF_DUTY,1440),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278396000000l),HOSStatus.OFF_DUTY,840),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278396000000l),HOSStatus.OFF_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278396000000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278396000000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278396000000l),HOSStatus.ON_DUTY,555),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278482400000l),HOSStatus.ON_DUTY,435),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278482400000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278482400000l),HOSStatus.ON_DUTY,75),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278482400000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278482400000l),HOSStatus.OFF_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278482400000l),HOSStatus.OFF_DUTY,315),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278482400000l),HOSStatus.OFF_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278482400000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278482400000l),HOSStatus.ON_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278482400000l),HOSStatus.ON_DUTY,165),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278482400000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278482400000l),HOSStatus.DRIVING,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278482400000l),HOSStatus.OFF_DUTY,15),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",6,"Szpuniarski,  James","01466044",new Date(1278482400000l),HOSStatus.OFF_DUTY,315),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",7,"Venkataraman,  Ramanathan","02707354",new Date(1277964000000l),HOSStatus.OFF_DUTY,1440),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",7,"Venkataraman,  Ramanathan","02707354",new Date(1278050400000l),HOSStatus.OFF_DUTY,1440),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",7,"Venkataraman,  Ramanathan","02707354",new Date(1278136800000l),HOSStatus.OFF_DUTY,1440),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",7,"Venkataraman,  Ramanathan","02707354",new Date(1278223200000l),HOSStatus.OFF_DUTY,1440),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",7,"Venkataraman,  Ramanathan","02707354",new Date(1278309600000l),HOSStatus.OFF_DUTY,1440),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",7,"Venkataraman,  Ramanathan","02707354",new Date(1278396000000l),HOSStatus.OFF_DUTY,1440),
            new PayrollData("Norman Wells->Norman Wells - WS","123 Norman Wells - WS, City, UT, 12345",7,"Venkataraman,  Ramanathan","02707354",new Date(1278482400000l),HOSStatus.OFF_DUTY,1440),
            //TODO:JWIMMER: add a row to check a total time%15 != 0
    };
    static TimeZone timeZone = TimeZone.getTimeZone("US/Mountain");
    public static HOSRecord[] dstRecs = {
                                                                                                            // daylight savings ends on 11/06/2011 at 2am so extra hour that day
            new HOSRecord(10, getUTCDate("11/05/2011 23:00:00", timeZone), timeZone, HOSStatus.DRIVING),   // 1 hr driving on 11/5 and 6 hrs on 11/6 
            new HOSRecord(10, getUTCDate("11/06/2011 04:00:00", timeZone), timeZone, HOSStatus.OFF_DUTY),  
            new HOSRecord(10, getUTCDate("11/06/2011 23:00:00", timeZone), timeZone, HOSStatus.DRIVING),  
            new HOSRecord(10, getUTCDate("11/07/2011 01:00:00", timeZone), timeZone, HOSStatus.OFF_DUTY),  
    };
    public static Map<HOSStatus, Integer> dstExpectedMin = new HashMap<HOSStatus, Integer> ();
    static {
            dstExpectedMin.put(HOSStatus.OFF_DUTY, 1140);
            dstExpectedMin.put(HOSStatus.DRIVING, 360);
    };
    public static Map<HOSStatus, Integer> dstExpectedCompMin = new HashMap<HOSStatus, Integer> ();
    static {
        dstExpectedCompMin.put(HOSStatus.ON_DUTY, 360);
    };
    public String dstExpectedTabularData[] = {
            "","11/06/2011","19:00","00:00","00:00","06:00","00:00","06:00"
    };
    public String dstExpectedSummTabularData[] = {
            "","19:00","00:00","00:00","06:00","00:00","06:00"
    };
    
    public static HOSRecord[] allStatusRecs = {
            new HOSRecord(10, getUTCDate("11/05/2011 23:00:00", timeZone), timeZone, HOSStatus.OFF_DUTY),  
            new HOSRecord(10, getUTCDate("11/06/2011 04:00:00", timeZone), timeZone, HOSStatus.DRIVING),  
            new HOSRecord(10, getUTCDate("11/06/2011 04:05:00", timeZone), timeZone, HOSStatus.OFF_DUTY),  
            new HOSRecord(10, getUTCDate("11/06/2011 05:00:00", timeZone), timeZone, HOSStatus.ON_DUTY),  
            new HOSRecord(10, getUTCDate("11/06/2011 05:05:00", timeZone), timeZone, HOSStatus.OFF_DUTY),  
            new HOSRecord(10, getUTCDate("11/06/2011 06:00:00", timeZone), timeZone, HOSStatus.ON_DUTY_OCCUPANT),  
            new HOSRecord(10, getUTCDate("11/06/2011 06:05:00", timeZone), timeZone, HOSStatus.OFF_DUTY),  
            new HOSRecord(10, getUTCDate("11/06/2011 07:00:00", timeZone), timeZone, HOSStatus.OFF_DUTY_OCCUPANT),  
            new HOSRecord(10, getUTCDate("11/06/2011 07:05:00", timeZone), timeZone, HOSStatus.OFF_DUTY),  
            new HOSRecord(10, getUTCDate("11/06/2011 08:00:00", timeZone), timeZone, HOSStatus.TRAVELTIME_OCCUPANT),  
            new HOSRecord(10, getUTCDate("11/06/2011 08:05:00", timeZone), timeZone, HOSStatus.OFF_DUTY),  
            new HOSRecord(10, getUTCDate("11/06/2011 09:00:00", timeZone), timeZone, HOSStatus.SLEEPER),  
            new HOSRecord(10, getUTCDate("11/06/2011 09:05:00", timeZone), timeZone, HOSStatus.OFF_DUTY),  
            new HOSRecord(10, getUTCDate("11/06/2011 10:00:00", timeZone), timeZone, HOSStatus.OFF_DUTY_AT_WELL),  
            new HOSRecord(10, getUTCDate("11/06/2011 10:05:00", timeZone), timeZone, HOSStatus.OFF_DUTY),  
            new HOSRecord(10, getUTCDate("11/06/2011 11:00:00", timeZone), timeZone, HOSStatus.HOS_PERSONALTIME),  
            new HOSRecord(10, getUTCDate("11/06/2011 11:05:00", timeZone), timeZone, HOSStatus.OFF_DUTY),  
    };
    public static Map<HOSStatus, Integer> allStatusExpectedMin = new HashMap<HOSStatus, Integer> ();
    static {
        allStatusExpectedMin.put(HOSStatus.OFF_DUTY, 1470);
        allStatusExpectedMin.put(HOSStatus.DRIVING, 5);
        allStatusExpectedMin.put(HOSStatus.SLEEPER, 5);
        allStatusExpectedMin.put(HOSStatus.OFF_DUTY_AT_WELL, 5);
        allStatusExpectedMin.put(HOSStatus.ON_DUTY, 15);
    };
    public static Map<HOSStatus, Integer> allStatusExpectedCompMin = new HashMap<HOSStatus, Integer> ();
    static {
        allStatusExpectedCompMin.put(HOSStatus.ON_DUTY, 30);
    };
    public String allStatusExpectedTabularData[] = {
            "","11/06/2011","24:30","00:05","00:05","00:05","00:15","00:30"
    };
    public String allStatusExpectedSummTabularData[] = {
            "","24:30","00:05","00:05","00:05","00:15","00:30"
    };

    public static HOSRecord[] roundRecs = {
        new HOSRecord(10, getUTCDate("11/05/2011 23:00:00", timeZone), timeZone, HOSStatus.OFF_DUTY),   // 1 hr driving on 11/5 and 6 hrs on 11/6 
        new HOSRecord(10, getUTCDate("11/06/2011 04:00:00", timeZone), timeZone, HOSStatus.DRIVING),  
        new HOSRecord(10, getUTCDate("11/06/2011 04:29:29", timeZone), timeZone, HOSStatus.OFF_DUTY),   // 29 driving
        new HOSRecord(10, getUTCDate("11/06/2011 05:00:00", timeZone), timeZone, HOSStatus.ON_DUTY),  
        new HOSRecord(10, getUTCDate("11/06/2011 05:30:01", timeZone), timeZone, HOSStatus.OFF_DUTY),   // 30 on_duty, but 60 total compensated so add extra to driving because it is closest to minute  
        new HOSRecord(10, getUTCDate("11/06/2011 23:00:00", timeZone), timeZone, HOSStatus.OFF_DUTY),  
        new HOSRecord(10, getUTCDate("11/07/2011 01:00:00", timeZone), timeZone, HOSStatus.OFF_DUTY),  
    };
    public static Map<HOSStatus, Integer> roundExpectedMin = new HashMap<HOSStatus, Integer> ();
    static {
        roundExpectedMin.put(HOSStatus.OFF_DUTY, 1440);
        roundExpectedMin.put(HOSStatus.DRIVING, 30);
        roundExpectedMin.put(HOSStatus.ON_DUTY, 30);
    };
    public static Map<HOSStatus, Integer> roundExpectedCompMin = new HashMap<HOSStatus, Integer> ();
    static {
        roundExpectedCompMin.put(HOSStatus.ON_DUTY, 60);
    };
    public String roundExpectedTabularData[] = {
            "","11/06/2011","24:00","00:00","00:00","00:30","00:30","01:00"
    };
    public String roundExpectedSummTabularData[] = {
            "","24:00","00:00","00:00","00:30","00:30","01:00"
    };

    public static HOSRecord[] roundRecs2 = {
        new HOSRecord(10, getUTCDate("11/05/2011 23:00:00", timeZone), timeZone, HOSStatus.OFF_DUTY),   // 1 hr driving on 11/5 and 6 hrs on 11/6 
        new HOSRecord(10, getUTCDate("11/06/2011 04:00:00", timeZone), timeZone, HOSStatus.DRIVING),  
        new HOSRecord(10, getUTCDate("11/06/2011 04:29:31", timeZone), timeZone, HOSStatus.OFF_DUTY),   // 30 driving
        new HOSRecord(10, getUTCDate("11/06/2011 05:00:00", timeZone), timeZone, HOSStatus.ON_DUTY),  
        new HOSRecord(10, getUTCDate("11/06/2011 05:29:32", timeZone), timeZone, HOSStatus.OFF_DUTY),   // 30 on_duty, but 59 total compensated so delete 1 from driving because it has smallest # of secs into minute  
        new HOSRecord(10, getUTCDate("11/06/2011 23:00:00", timeZone), timeZone, HOSStatus.OFF_DUTY),  
        new HOSRecord(10, getUTCDate("11/07/2011 01:00:00", timeZone), timeZone, HOSStatus.OFF_DUTY),  
    };
    public static Map<HOSStatus, Integer> roundExpectedMin2 = new HashMap<HOSStatus, Integer> ();
    static {
        roundExpectedMin2.put(HOSStatus.OFF_DUTY, 1441);
        roundExpectedMin2.put(HOSStatus.DRIVING, 29);
        roundExpectedMin2.put(HOSStatus.ON_DUTY, 30);
    };
    public static Map<HOSStatus, Integer> roundExpectedCompMin2 = new HashMap<HOSStatus, Integer> ();
    static {
        roundExpectedCompMin2.put(HOSStatus.ON_DUTY, 59);
    };
    
    public String roundExpectedTabularData2[] = {
            "","11/06/2011","24:01","00:00","00:00","00:29","00:30","00:59"
    };
    public String roundExpectedSummTabularData2[] = {
            "","24:01","00:00","00:00","00:29","00:30","00:59"
    };

    @Test 
    public void daylightSavings() {
        runTest("dst", dstRecs, dstExpectedMin, dstExpectedCompMin, dstExpectedTabularData, dstExpectedSummTabularData);
    }
    @Test 
    public void allStatuses() {
        runTest("allStatus", allStatusRecs, allStatusExpectedMin, allStatusExpectedCompMin, allStatusExpectedTabularData, allStatusExpectedSummTabularData);
    }
    @Test 
    public void rounding() {
        runTest("round", roundRecs, roundExpectedMin, roundExpectedCompMin, roundExpectedTabularData, roundExpectedSummTabularData);
    }
    @Test 
    public void rounding2() {
        runTest("round2", roundRecs2, roundExpectedMin2, roundExpectedCompMin2, roundExpectedTabularData2, roundExpectedSummTabularData2);
    }

    private void runTest(String prefix, HOSRecord[] recList, Map<HOSStatus, Integer> expectedMin, Map<HOSStatus, Integer> expectedCompMin, 
            String[] expectedTabularData, String[] expectedSummTabularData) {
        PayrollDetailReportCriteria detailCriteria = new PayrollDetailReportCriteria(Locale.US);
        HosRecordDataSet testData = initTestData(10, recList, "11/06/2011 00:00:00", "11/06/2011 23:59:59");
        detailCriteria.initDataSet(testData.interval, testData.account, testData.getGroupHierarchy(), testData.driverHOSRecordMap);
        checkData(prefix+"_detail", detailCriteria, detailCriteria.getMainDataset(), expectedMin, detailCriteria.getTableRows(), expectedTabularData);
                
        PayrollSummaryReportCriteria  summCriteria = new PayrollSummaryReportCriteria (Locale.US);
        summCriteria.initDataSet(testData.interval, testData.account, testData.getGroupHierarchy(), testData.driverHOSRecordMap);
        checkData(prefix+"_summ", summCriteria, summCriteria.getMainDataset(), expectedMin, summCriteria.getTableRows(), expectedSummTabularData);

        PayrollSignoffReportCriteria  signoffCriteria = new PayrollSignoffReportCriteria (Locale.US);
        signoffCriteria.initDataSet(testData.interval, testData.account, testData.getGroupHierarchy(), testData.driverHOSRecordMap);
        checkData(prefix+"_signoff", signoffCriteria, signoffCriteria.getMainDataset(), expectedMin, signoffCriteria.getTableRows(), expectedTabularData);

        PayrollReportCompensatedHoursCriteria compCriteria = new PayrollReportCompensatedHoursCriteria(Locale.US);
        compCriteria.initDataSet(testData.interval, testData.account, testData.getGroupHierarchy(), testData.driverHOSRecordMap);
        checkData(prefix+"_comp", compCriteria, compCriteria.getMainDataset(), expectedCompMin, compCriteria.getTableRows(), null);
    }

    private void checkData(String desc, ReportCriteria criteria, List<PayrollData> dataList, Map<HOSStatus, Integer> expectedMinutes, List<List<Result>> tabularData, String[] expectedTabularData) {
//        System.out.println("--- check --- " + desc);
        dump(desc, 1, criteria, FormatType.PDF);
        dump(desc, 1, criteria, FormatType.EXCEL);
        assertEquals("expected number of data items", expectedMinutes.size(), dataList.size());
        for (PayrollData payrollData : dataList) {
//            System.out.println(payrollData.getStatus() + " " + payrollData.getTotalAdjustedMinutes());
            Integer expected = expectedMinutes.get(payrollData.getStatus());
            assertNotNull(desc + " should have been an entry for " + payrollData.getStatus(), expected);
            assertEquals(desc + " minutes match", expected.intValue(), payrollData.getTotalAdjustedMinutes());
        }

    
        // tabular
        if (expectedTabularData == null)
            return;
        
        assertEquals("tabular Data expected row count", 1, tabularData.size());
        for (List<Result> row : tabularData) {
            int colCnt = 0;
            for (Result result : row) {
                if (colCnt != 0) {
//                    if (!expectedTabularData[colCnt].equals(result.getDisplay()))
//                        System.out.println("(col): (" + colCnt + "): " + expectedTabularData[colCnt] +  " " + result.getDisplay());
                    assertEquals("(col): (" + colCnt + "): ", expectedTabularData[colCnt], result.getDisplay());
                }
                colCnt++;
            }
        }

    }
    

    private HosRecordDataSet initTestData(int driverID, HOSRecord[] recArray, String startDate, String endDate) {
        HosRecordDataSet testData = new HosRecordDataSet(DATA_PATH, testCaseName, false);
        testData.interval = new Interval(getUTCDateTime(startDate,timeZone), getUTCDateTime(endDate,timeZone));
        Map<Driver, List<HOSRecord>> driverHOSRecordMap = new HashMap<Driver, List<HOSRecord>>();
        Driver driver = testData.driverHOSRecordMap.keySet().iterator().next();
        driver.setDriverID(driverID);
        List<HOSRecord> recList = new ArrayList<HOSRecord>();
        for (HOSRecord rec : recArray) {
            recList.add(rec);
        }
        Collections.reverse(recList);
        driverHOSRecordMap.put(driver, recList);
        testData.driverHOSRecordMap = driverHOSRecordMap;
        
        return testData;
    }

    private static DateTime getUTCDateTime(String dateString, TimeZone timeZone) {
        
        return new DateTime(getUTCDate(dateString, timeZone));
    }
    private static Date getUTCDate(String dateString, TimeZone timeZone) {
        
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        dateFormat.setTimeZone(timeZone);
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }



    @Test
    public void getColumnHeaders_testForMissingColNames(){
        PayrollSummaryReportCriteria criteria = new PayrollSummaryReportCriteria(Locale.US);
        //NOTE: not all ReportTypes can be tested this way since some piggyback on their super Class' column headers
        ReportType[] reportTypes = {ReportType.PAYROLL_COMPENSATED_HOURS, ReportType.PAYROLL_DETAIL};
        int[] numbersOfCols = {5, 8};
        int index = 0;
        for(ReportType reportType: reportTypes) {
            List<String> results = criteria.getColumnHeaders(reportType, numbersOfCols[index]);
            for(String colHeader: results){
                assertTrue("missing column name for "+reportType+" "+colHeader, !(colHeader.contains("column.")));
            }
            index++;
        }
    }
    
    public static PayrollData[] getPayrollDataExpectedData(){
        return payrollDataExpectedData;
    }
    
  @Test
  public void testPayrollReportCompensatedHours_GetCompensatedRecords() {
      PayrollReportCompensatedHoursCriteria criteria = new PayrollReportCompensatedHoursCriteria(Locale.US);
      List<HOSStatus> compensated = Arrays.asList(HOSStatus.DRIVING ,HOSStatus.ON_DUTY ,HOSStatus.ON_DUTY_OCCUPANT ,HOSStatus.DRIVING_NONDOT  , HOSStatus.TRAVELTIME_OCCUPANT);
      List<PayrollData> results = criteria.getCompensatedRecords(Arrays.asList(PayrollCriteriaTest.getPayrollDataExpectedData()));
      for(PayrollData rec: results){
          assertTrue("total adjusted minutes should never be negative", rec.getTotalAdjustedMinutes() >= 0);
          assertTrue(rec.getStatus()+" is not a compensated HOSStatus", compensated.contains(rec.getStatus()));
      }
      int NUM_COMP_RECORDS_EXPECTED = 56;
      assertTrue("There should be as many compensated records as in getPayrollDataExpectedData (or more if that source has been added to)", results.size() >= NUM_COMP_RECORDS_EXPECTED);
  }
    
}
