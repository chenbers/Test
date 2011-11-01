package com.inthinc.pro.reports.performance;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.hos.model.HOSStatus;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.reports.BaseUnitTest;
import com.inthinc.pro.reports.FormatType;
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

    String tabularPayrollDetailsExpectedData[][] = {
            {"Elias,  Calvin", "07/01/2010", "24:00", "00:00", "00:00", "00:00", "00:00", "00:00",   },
            {"Elias,  Calvin", "07/02/2010", "24:00", "00:00", "00:00", "00:00", "00:00", "00:00",   },
            {"Elias,  Calvin", "07/03/2010", "24:00", "00:00", "00:00", "00:00", "00:00", "00:00",   },
            {"Elias,  Calvin", "07/04/2010", "24:00", "00:00", "00:00", "00:00", "00:00", "00:00",   },
            {"Elias,  Calvin", "07/05/2010", "24:00", "00:00", "00:00", "00:00", "00:00", "00:00",   },
            {"Elias,  Calvin", "07/06/2010", "24:00", "00:00", "00:00", "00:00", "00:00", "00:00",   },
            {"Elias,  Calvin", "07/07/2010", "24:00", "00:00", "00:00", "00:00", "00:00", "00:00",   },
            {"Francey,  David ", "07/01/2010", "16:30", "00:00", "00:00", "00:45", "06:45", "07:30",   },
            {"Francey,  David ", "07/02/2010", "22:30", "00:00", "00:00", "00:15", "01:15", "01:30",   },
            {"Francey,  David ", "07/03/2010", "24:00", "00:00", "00:00", "00:00", "00:00", "00:00",   },
            {"Francey,  David ", "07/04/2010", "12:15", "00:00", "00:00", "02:45", "09:00", "11:45",   },
            {"Francey,  David ", "07/05/2010", "14:00", "00:00", "00:00", "04:00", "06:00", "10:00",   },
            {"Francey,  David ", "07/06/2010", "13:30", "00:00", "00:00", "01:15", "09:15", "10:30",   },
            {"Francey,  David ", "07/07/2010", "21:15", "00:00", "00:00", "00:45", "02:00", "02:45",   },
            {"Giem,  Scott", "07/01/2010", "15:45", "00:00", "00:00", "03:30", "04:45", "08:15",   },
            {"Giem,  Scott", "07/02/2010", "14:30", "00:00", "00:00", "00:00", "09:30", "09:30",   },
            {"Giem,  Scott", "07/03/2010", "16:45", "00:00", "00:00", "01:00", "06:15", "07:15",   },
            {"Giem,  Scott", "07/04/2010", "12:15", "00:00", "00:00", "00:30", "11:15", "11:45",   },
            {"Giem,  Scott", "07/05/2010", "14:15", "00:00", "00:00", "00:00", "09:45", "09:45",   },
            {"Giem,  Scott", "07/06/2010", "12:45", "00:00", "00:00", "00:45", "10:30", "11:15",   },
            {"Giem,  Scott", "07/07/2010", "13:15", "00:00", "00:00", "00:45", "10:00", "10:45",   },
            {"Harris,  Eugene", "07/01/2010", "15:15", "00:00", "00:00", "00:30", "08:15", "08:45",   },
            {"Harris,  Eugene", "07/02/2010", "24:00", "00:00", "00:00", "00:00", "00:00", "00:00",   },
            {"Harris,  Eugene", "07/03/2010", "24:00", "00:00", "00:00", "00:00", "00:00", "00:00",   },
            {"Harris,  Eugene", "07/04/2010", "24:00", "00:00", "00:00", "00:00", "00:00", "00:00",   },
            {"Harris,  Eugene", "07/05/2010", "23:45", "00:00", "00:00", "00:15", "00:00", "00:15",   },
            {"Harris,  Eugene", "07/06/2010", "08:00", "00:00", "00:00", "00:45", "15:15", "16:00",   },
            {"Harris,  Eugene", "07/07/2010", "16:00", "00:00", "00:00", "00:00", "08:00", "08:00",   },
            {"Johnson,  Jack ", "07/01/2010", "24:00", "00:00", "00:00", "00:00", "00:00", "00:00",   },
            {"Johnson,  Jack ", "07/02/2010", "24:00", "00:00", "00:00", "00:00", "00:00", "00:00",   },
            {"Johnson,  Jack ", "07/03/2010", "23:15", "00:00", "00:00", "00:15", "00:30", "00:45",   },
            {"Johnson,  Jack ", "07/04/2010", "24:00", "00:00", "00:00", "00:00", "00:00", "00:00",   },
            {"Johnson,  Jack ", "07/05/2010", "24:00", "00:00", "00:00", "00:00", "00:00", "00:00",   },
            {"Johnson,  Jack ", "07/06/2010", "24:00", "00:00", "00:00", "00:00", "00:00", "00:00",   },
            {"Johnson,  Jack ", "07/07/2010", "24:00", "00:00", "00:00", "00:00", "00:00", "00:00",   },
            {"Pierrot,  Collin", "07/01/2010", "24:00", "00:00", "00:00", "00:00", "00:00", "00:00",   },
            {"Pierrot,  Collin", "07/02/2010", "24:00", "00:00", "00:00", "00:00", "00:00", "00:00",   },
            {"Pierrot,  Collin", "07/03/2010", "24:00", "00:00", "00:00", "00:00", "00:00", "00:00",   },
            {"Pierrot,  Collin", "07/04/2010", "24:00", "00:00", "00:00", "00:00", "00:00", "00:00",   },
            {"Pierrot,  Collin", "07/05/2010", "24:00", "00:00", "00:00", "00:00", "00:00", "00:00",   },
            {"Pierrot,  Collin", "07/06/2010", "24:00", "00:00", "00:00", "00:00", "00:00", "00:00",   },
            {"Pierrot,  Collin", "07/07/2010", "24:00", "00:00", "00:00", "00:00", "00:00", "00:00",   },
            {"Szpuniarski,  James", "07/01/2010", "15:30", "00:00", "00:00", "00:45", "07:45", "08:30",   },
            {"Szpuniarski,  James", "07/02/2010", "13:45", "00:00", "00:00", "01:30", "08:45", "10:15",   },
            {"Szpuniarski,  James", "07/03/2010", "16:30", "00:00", "00:00", "00:15", "07:15", "07:30",   },
            {"Szpuniarski,  James", "07/04/2010", "24:00", "00:00", "00:00", "00:00", "00:00", "00:00",   },
            {"Szpuniarski,  James", "07/05/2010", "24:00", "00:00", "00:00", "00:00", "00:00", "00:00",   },
            {"Szpuniarski,  James", "07/06/2010", "14:15", "00:00", "00:00", "00:15", "09:30", "09:45",   },
            {"Szpuniarski,  James", "07/07/2010", "11:15", "00:00", "00:00", "01:00", "11:45", "12:45",   },
            {"Venkataraman,  Ramanathan", "07/01/2010", "24:00", "00:00", "00:00", "00:00", "00:00", "00:00",   },
            {"Venkataraman,  Ramanathan", "07/02/2010", "24:00", "00:00", "00:00", "00:00", "00:00", "00:00",   },
            {"Venkataraman,  Ramanathan", "07/03/2010", "24:00", "00:00", "00:00", "00:00", "00:00", "00:00",   },
            {"Venkataraman,  Ramanathan", "07/04/2010", "24:00", "00:00", "00:00", "00:00", "00:00", "00:00",   },
            {"Venkataraman,  Ramanathan", "07/05/2010", "24:00", "00:00", "00:00", "00:00", "00:00", "00:00",   },
            {"Venkataraman,  Ramanathan", "07/06/2010", "24:00", "00:00", "00:00", "00:00", "00:00", "00:00",   },
            {"Venkataraman,  Ramanathan", "07/07/2010", "24:00", "00:00", "00:00", "00:00", "00:00", "00:00",   },
    };
    String tabularPayrollSignoffExpectedData[][] = {
         
            {"Giem,  Scott", "07/01/2010", "15:45", "00:00", "00:00", "03:30", "04:45", "08:15",   },
            {"Giem,  Scott", "07/02/2010", "14:30", "00:00", "00:00", "00:00", "09:30", "09:30",   },
            {"Giem,  Scott", "07/03/2010", "16:45", "00:00", "00:00", "01:00", "06:15", "07:15",   },
            {"Giem,  Scott", "07/04/2010", "12:15", "00:00", "00:00", "00:30", "11:15", "11:45",   },
            {"Giem,  Scott", "07/05/2010", "14:15", "00:00", "00:00", "00:00", "09:45", "09:45",   },
            {"Giem,  Scott", "07/06/2010", "12:45", "00:00", "00:00", "00:45", "10:30", "11:15",   },
            {"Giem,  Scott", "07/07/2010", "13:15", "00:00", "00:00", "00:45", "10:00", "10:45",   },
            
    };    
    String tabularPayrollSummaryExpectedData[][] = {
            {"Elias,  Calvin", "168:00", "00:00", "00:00", "00:00", "00:00", "00:00",   },
            {"Francey,  David ", "124:00", "00:00", "00:00", "09:45", "34:15", "44:00",   },
            {"Giem,  Scott", "99:30", "00:00", "00:00", "06:30", "62:00", "68:30",   },
            {"Harris,  Eugene", "135:00", "00:00", "00:00", "01:30", "31:30", "33:00",   },
            {"Johnson,  Jack ", "167:15", "00:00", "00:00", "00:15", "00:30", "00:45",   },
            {"Pierrot,  Collin", "168:00", "00:00", "00:00", "00:00", "00:00", "00:00",   },
            {"Szpuniarski,  James", "119:15", "00:00", "00:00", "03:45", "45:00", "48:45",   },
            {"Venkataraman,  Ramanathan", "168:00", "00:00", "00:00", "00:00", "00:00", "00:00",   },
    };
    

    @Test
    @Ignore
    public void gainDetailsTestCases() {
        HosRecordDataSet testData = new HosRecordDataSet(DATA_PATH, testCaseName, false);

        PayrollDetailReportCriteria criteria = new PayrollDetailReportCriteria(Locale.US);
        criteria.initDataSet(testData.interval, testData.account, testData.getGroupHierarchy(), testData.driverHOSRecordMap);
        List<PayrollData> dataList = criteria.getMainDataset();
        int eCnt = 0;
        dump("payrollDetailTest", 1, criteria, FormatType.PDF);
        dump("payrollDetailTest", 1, criteria, FormatType.EXCEL);

        for (PayrollData data : dataList) {

            PayrollData expected = payrollDataExpectedData[eCnt++];
            assertEquals(testCaseName + " groupName " + eCnt, expected.getGroupName(), data.getGroupName());
            assertEquals(testCaseName + " groupAddress " + eCnt, expected.getGroupAddress(), data.getGroupAddress());
            assertEquals(testCaseName + " driverName " + eCnt, expected.getDriverName(), data.getDriverName());
            assertEquals(testCaseName + " day " + eCnt, expected.getDay(), data.getDay());
            assertEquals(testCaseName + " status " + eCnt, expected.getStatus(), data.getStatus());
            assertEquals(testCaseName + " minutes " + eCnt, expected.getTotalAdjustedMinutes(), data.getTotalAdjustedMinutes());
            assertEquals(testCaseName + " employeeID " + eCnt, expected.getEmployeeID(), data.getEmployeeID());
        }

        // tabular
        List<List<Result>> tabularData = criteria.getTableRows();
        assertEquals("tabular Data expected row count", tabularPayrollDetailsExpectedData.length, tabularData.size());
        int rowCnt = 0;
        for (List<Result> row : tabularData) {
            int colCnt = 0;
            for (Result result : row) {
                if (!tabularPayrollDetailsExpectedData[rowCnt][colCnt].equals(result.getDisplay()))
                    System.out.println("(row,col): (" + rowCnt + "," + colCnt + "): " + tabularPayrollDetailsExpectedData[rowCnt][colCnt] + result.getDisplay());
                assertEquals("(row,col): (" + rowCnt + "," + colCnt + "): ", tabularPayrollDetailsExpectedData[rowCnt][colCnt], result.getDisplay());
                colCnt++;
            }
            rowCnt++;
        }
    }

    @Test
    @Ignore
    public void gainSignoffTestCases() {
        HosRecordDataSet testData = new HosRecordDataSet(DATA_PATH, testCaseName, false);
        
        PayrollSignoffReportCriteria criteria = new PayrollSignoffReportCriteria(Locale.US);
        
        for (Entry<Driver, List<HOSRecord>> entry : testData.driverHOSRecordMap.entrySet()) {
            Driver driver = entry.getKey();
            
            Map<Driver,List<HOSRecord>> oneDriverMap = new HashMap<Driver,List<HOSRecord>>();
            oneDriverMap.put(driver, entry.getValue());
            criteria.initDataSet(testData.interval, testData.account, testData.getGroupHierarchy(), oneDriverMap);

            if (driver.getPerson().getLast().startsWith("Giem")) {
                int eCnt = 0;
                for (;eCnt < payrollDataExpectedData.length; eCnt++) {
                    PayrollData expected = payrollDataExpectedData[eCnt];
                    if (expected.getDriverName().startsWith("Giem"))
                        break;
                }
                List<PayrollData> dataList = criteria.getMainDataset();
                for (PayrollData data : dataList) {
                    PayrollData expected = payrollDataExpectedData[eCnt++];
                    assertEquals(testCaseName + " groupName " + eCnt, expected.getGroupName(), data.getGroupName());
                    assertEquals(testCaseName + " groupAddress " + eCnt, expected.getGroupAddress(), data.getGroupAddress());
                    assertEquals(testCaseName + " driverName " + eCnt, expected.getDriverName(), data.getDriverName());
                    assertEquals(testCaseName + " day " + eCnt, expected.getDay(), data.getDay());
                    assertEquals(testCaseName + " employeeID " + eCnt, expected.getEmployeeID(), data.getEmployeeID());
                }
            }
            String prefix = "payrollSignoffTest "+entry.getKey().getPerson().getLast() + "_"; 
            dump(prefix, 1, criteria, FormatType.PDF);
            dump(prefix, 1, criteria, FormatType.EXCEL);

            // tabular
            if (driver.getPerson().getLast().startsWith("Giem")) {
                List<List<Result>> tablularData = criteria.getTableRows();
                assertEquals("tabular Data expected row count", tabularPayrollSignoffExpectedData.length, tablularData.size());
                int rowCnt = 0;
                for (List<Result> row : tablularData) {
                    int colCnt = 0;
                    for (Result result : row) {
                        assertEquals("(row,col): (" + rowCnt + "," + colCnt + "): ", tabularPayrollSignoffExpectedData[rowCnt][colCnt], result.getDisplay());
                        colCnt++;
                    }
                    rowCnt++;
                }
            }
      }
    }

    @Test
    @Ignore
    public void gainSummaryTestCases() {
        HosRecordDataSet testData = new HosRecordDataSet(DATA_PATH, testCaseName, false);
        
        PayrollSummaryReportCriteria criteria = new PayrollSummaryReportCriteria(Locale.US);
        criteria.initDataSet(testData.interval, testData.account, testData.getGroupHierarchy(), testData.driverHOSRecordMap);
        List<PayrollData> dataList = criteria.getMainDataset();
        int eCnt = 0;
        for (PayrollData data : dataList) {
            PayrollData expected = payrollDataExpectedData[eCnt++];
            assertEquals(testCaseName + " groupName " + eCnt, expected.getGroupName(), data.getGroupName());
            assertEquals(testCaseName+ " groupAddress " + eCnt, expected.getGroupAddress(), data.getGroupAddress());
            assertEquals(testCaseName+ " driverName " + eCnt, expected.getDriverName(), data.getDriverName());
            assertEquals(testCaseName+ " day " + eCnt, expected.getDay(), data.getDay());
            assertEquals(testCaseName+ " employeeID " + eCnt, expected.getEmployeeID(), data.getEmployeeID());
        }
        
        dump("payrollSummaryTest", 1, criteria, FormatType.PDF);
        dump("payrollSummaryTest", 1, criteria, FormatType.EXCEL);
        
        // tabular
        List<List<Result>> tablularData = criteria.getTableRows();
        assertEquals("tabular Data expected row count", tabularPayrollSummaryExpectedData.length, tablularData.size());
        int rowCnt = 0;
        for (List<Result> row : tablularData) {
            int colCnt = 0;
            for (Result result : row) {
                assertEquals("(row,col): (" + rowCnt + "," + colCnt + "): ", tabularPayrollSummaryExpectedData[rowCnt][colCnt], result.getDisplay());
                colCnt++;
            }
            rowCnt++;
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
