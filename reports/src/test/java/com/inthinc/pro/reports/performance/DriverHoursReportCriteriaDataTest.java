package com.inthinc.pro.reports.performance;

import static org.junit.Assert.assertEquals;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.model.performance.DriverHoursRecord;
import com.inthinc.pro.reports.BaseUnitTest;
import com.inthinc.pro.reports.FormatType;
import com.inthinc.pro.reports.dao.impl.WaysmartDAOImpl;
import com.inthinc.pro.reports.hos.testData.HosRecordDataSet;
import com.inthinc.pro.reports.performance.model.DriverHours;

public class DriverHoursReportCriteriaDataTest extends BaseUnitTest {
    

    // for gain data test
    public static final String DATA_PATH = "violations/";
    public static final String testCaseName = "vtest_01H1_07012010_07072010"; 
    DriverHours driverHoursExpectedData[]= {
            new DriverHours("Norman Wells->Norman Wells - WS","07/01/10","Elias,  Calvin",0.0),
            new DriverHours("Norman Wells->Norman Wells - WS","07/02/10","Elias,  Calvin",0.0),
            new DriverHours("Norman Wells->Norman Wells - WS","07/03/10","Elias,  Calvin",0.0),
            new DriverHours("Norman Wells->Norman Wells - WS","07/04/10","Elias,  Calvin",0.0),
            new DriverHours("Norman Wells->Norman Wells - WS","07/05/10","Elias,  Calvin",0.0),
            new DriverHours("Norman Wells->Norman Wells - WS","07/06/10","Elias,  Calvin",0.0),
            new DriverHours("Norman Wells->Norman Wells - WS","07/07/10","Elias,  Calvin",0.0),
            new DriverHours("Norman Wells->Norman Wells - WS","07/01/10","Francey,  David ",0.6),
            new DriverHours("Norman Wells->Norman Wells - WS","07/02/10","Francey,  David ",0.23333333333333334),
            new DriverHours("Norman Wells->Norman Wells - WS","07/03/10","Francey,  David ",0.0),
            new DriverHours("Norman Wells->Norman Wells - WS","07/04/10","Francey,  David ",3.0833333333333335),
            new DriverHours("Norman Wells->Norman Wells - WS","07/05/10","Francey,  David ",3.5166666666666666),
            new DriverHours("Norman Wells->Norman Wells - WS","07/06/10","Francey,  David ",1.2),
            new DriverHours("Norman Wells->Norman Wells - WS","07/07/10","Francey,  David ",1.0166666666666666),
            new DriverHours("Norman Wells->Norman Wells - WS","07/01/10","Giem,  Scott",3.433333333333333),
            new DriverHours("Norman Wells->Norman Wells - WS","07/02/10","Giem,  Scott",0.2833333333333333),
            new DriverHours("Norman Wells->Norman Wells - WS","07/03/10","Giem,  Scott",1.2833333333333334),
            new DriverHours("Norman Wells->Norman Wells - WS","07/04/10","Giem,  Scott",0.7833333333333333),
            new DriverHours("Norman Wells->Norman Wells - WS","07/05/10","Giem,  Scott",0.31666666666666665),
            new DriverHours("Norman Wells->Norman Wells - WS","07/06/10","Giem,  Scott",1.1166666666666667),
            new DriverHours("Norman Wells->Norman Wells - WS","07/07/10","Giem,  Scott",1.0333333333333334),
            new DriverHours("Norman Wells->Norman Wells - WS","07/01/10","Harris,  Eugene",0.8166666666666667),
            new DriverHours("Norman Wells->Norman Wells - WS","07/02/10","Harris,  Eugene",0.0),
            new DriverHours("Norman Wells->Norman Wells - WS","07/03/10","Harris,  Eugene",0.0),
            new DriverHours("Norman Wells->Norman Wells - WS","07/04/10","Harris,  Eugene",0.0),
            new DriverHours("Norman Wells->Norman Wells - WS","07/05/10","Harris,  Eugene",0.18333333333333332),
            new DriverHours("Norman Wells->Norman Wells - WS","07/06/10","Harris,  Eugene",0.8833333333333333),
            new DriverHours("Norman Wells->Norman Wells - WS","07/07/10","Harris,  Eugene",0.1),
            new DriverHours("Norman Wells->Norman Wells - WS","07/01/10","Johnson,  Jack ",0.0),
            new DriverHours("Norman Wells->Norman Wells - WS","07/02/10","Johnson,  Jack ",0.0),
            new DriverHours("Norman Wells->Norman Wells - WS","07/03/10","Johnson,  Jack ",0.35),
            new DriverHours("Norman Wells->Norman Wells - WS","07/04/10","Johnson,  Jack ",0.0),
            new DriverHours("Norman Wells->Norman Wells - WS","07/05/10","Johnson,  Jack ",0.0),
            new DriverHours("Norman Wells->Norman Wells - WS","07/06/10","Johnson,  Jack ",0.0),
            new DriverHours("Norman Wells->Norman Wells - WS","07/07/10","Johnson,  Jack ",0.0),
            new DriverHours("Norman Wells->Norman Wells - WS","07/01/10","Pierrot,  Collin",0.0),
            new DriverHours("Norman Wells->Norman Wells - WS","07/02/10","Pierrot,  Collin",0.0),
            new DriverHours("Norman Wells->Norman Wells - WS","07/03/10","Pierrot,  Collin",0.0),
            new DriverHours("Norman Wells->Norman Wells - WS","07/04/10","Pierrot,  Collin",0.0),
            new DriverHours("Norman Wells->Norman Wells - WS","07/05/10","Pierrot,  Collin",0.0),
            new DriverHours("Norman Wells->Norman Wells - WS","07/06/10","Pierrot,  Collin",0.0),
            new DriverHours("Norman Wells->Norman Wells - WS","07/07/10","Pierrot,  Collin",0.0),
            new DriverHours("Norman Wells->Norman Wells - WS","07/01/10","Szpuniarski,  James",1.0),
            new DriverHours("Norman Wells->Norman Wells - WS","07/02/10","Szpuniarski,  James",1.0),
            new DriverHours("Norman Wells->Norman Wells - WS","07/03/10","Szpuniarski,  James",0.65),
            new DriverHours("Norman Wells->Norman Wells - WS","07/04/10","Szpuniarski,  James",0.0),
            new DriverHours("Norman Wells->Norman Wells - WS","07/05/10","Szpuniarski,  James",0.0),
            new DriverHours("Norman Wells->Norman Wells - WS","07/06/10","Szpuniarski,  James",0.25),
            new DriverHours("Norman Wells->Norman Wells - WS","07/07/10","Szpuniarski,  James",1.0),
            new DriverHours("Norman Wells->Norman Wells - WS","07/01/10","Venkataraman,  Ramanathan",0.0),
            new DriverHours("Norman Wells->Norman Wells - WS","07/02/10","Venkataraman,  Ramanathan",0.0),
            new DriverHours("Norman Wells->Norman Wells - WS","07/03/10","Venkataraman,  Ramanathan",0.0),
            new DriverHours("Norman Wells->Norman Wells - WS","07/04/10","Venkataraman,  Ramanathan",0.0),
            new DriverHours("Norman Wells->Norman Wells - WS","07/05/10","Venkataraman,  Ramanathan",0.0),
            new DriverHours("Norman Wells->Norman Wells - WS","07/06/10","Venkataraman,  Ramanathan",0.0),
            new DriverHours("Norman Wells->Norman Wells - WS","07/07/10","Venkataraman,  Ramanathan",0.0),
            
    };
    
    
    // test using data extracted from GAIN database
    @Test
    public void gainDetailsTestCases() {
        HosRecordDataSet testData = new HosRecordDataSet(DATA_PATH, testCaseName, false);

        Map<Driver, List<DriverHoursRecord>> driverHoursRecordMap = new HashMap<Driver, List<DriverHoursRecord>>();
        for (Entry<Driver, List<HOSRecord>> dataEntry : testData.driverHOSRecordMap.entrySet()) {
            List<DriverHoursRecord> driverHoursList = new WaysmartDAOImpl().getDriverHours(dataEntry.getKey(), testData.interval, dataEntry.getValue());
            driverHoursRecordMap.put(dataEntry.getKey(), driverHoursList);
        }
        DriverHoursReportCriteria criteria = new DriverHoursReportCriteria(Locale.US);
        criteria.initDataSet(testData.getGroupHierarchy(), testData.interval, driverHoursRecordMap);
        
        List<DriverHours> dataList = criteria.getMainDataset();
        DecimalFormat hoursFormatter = new DecimalFormat("###0.00"); 
        int eCnt = 0;
        for (DriverHours data : dataList) {
            DriverHours expected = driverHoursExpectedData[eCnt++];
            assertEquals(testCaseName + "groupName " + eCnt, expected.getGroupName(), data.getGroupName());
            assertEquals(testCaseName + "driverName " + eCnt, expected.getDriverName(), data.getDriverName());
            assertEquals(testCaseName + "day " + eCnt, expected.getDate(), data.getDate());
            assertEquals(testCaseName + "hours " + eCnt, hoursFormatter.format(expected.getHours()), hoursFormatter.format(data.getHours()));
        }
        
        dump("driverHoursTest", 1, criteria, FormatType.PDF);
        dump("driverHoursTest", 1, criteria, FormatType.EXCEL);

    }
    
}