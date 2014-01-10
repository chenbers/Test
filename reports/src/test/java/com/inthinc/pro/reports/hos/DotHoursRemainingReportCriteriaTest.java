package com.inthinc.pro.reports.hos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

import com.inthinc.hos.model.HOSStatus;
import com.inthinc.hos.model.RuleSetType;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.hos.HOSRecord;
import com.inthinc.pro.reports.BaseUnitTest;
import com.inthinc.pro.reports.FormatType;
import com.inthinc.pro.reports.hos.model.DotHoursRemaining;
import com.inthinc.pro.reports.hos.testData.HosRecordDataSet;


public class DotHoursRemainingReportCriteriaTest extends BaseUnitTest {
    
    public static final String DATA_PATH = "hos/";

    private static final String testCaseName[] = { 
        "vtest_000_07172010_08062010", 
    };
    private static final Date testCaseCurrentDate[] = { // date captured when expected report is run so that we can match up the data
        new Date(1281128840041l),
    };
    
    DotHoursRemaining dotHoursRemainingExpectedData[][] = {
    {
        
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,203l,563l,"08/06/10",new Date(1281074400000l),HOSStatus.DRIVING,495l),//510l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,203l,563l,"08/06/10",new Date(1281074400000l),HOSStatus.ON_DUTY,30l), //15l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,203l,563l,"08/05/10",new Date(1280988000000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,203l,563l,"08/05/10",new Date(1280988000000l),HOSStatus.ON_DUTY,645l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,203l,563l,"08/04/10",new Date(1280901600000l),HOSStatus.DRIVING,255l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,203l,563l,"08/04/10",new Date(1280901600000l),HOSStatus.ON_DUTY,840l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,203l,563l,"08/03/10",new Date(1280815200000l),HOSStatus.DRIVING,390l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,203l,563l,"08/03/10",new Date(1280815200000l),HOSStatus.ON_DUTY,150l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,203l,563l,"08/02/10",new Date(1280728800000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,203l,563l,"08/02/10",new Date(1280728800000l),HOSStatus.ON_DUTY,825l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,203l,563l,"08/01/10",new Date(1280642400000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,203l,563l,"08/01/10",new Date(1280642400000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,203l,563l,"07/31/10",new Date(1280556000000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,203l,563l,"07/31/10",new Date(1280556000000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,203l,563l,"07/30/10",new Date(1280469600000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,203l,563l,"07/30/10",new Date(1280469600000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,203l,563l,"07/29/10",new Date(1280383200000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,203l,563l,"07/29/10",new Date(1280383200000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,203l,563l,"07/28/10",new Date(1280296800000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,203l,563l,"07/28/10",new Date(1280296800000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,203l,563l,"07/27/10",new Date(1280210400000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,203l,563l,"07/27/10",new Date(1280210400000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,203l,563l,"07/26/10",new Date(1280124000000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,203l,563l,"07/26/10",new Date(1280124000000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,203l,563l,"07/25/10",new Date(1280037600000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,203l,563l,"07/25/10",new Date(1280037600000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,203l,563l,"07/24/10",new Date(1279951200000l),HOSStatus.DRIVING,15l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,203l,563l,"07/24/10",new Date(1279951200000l),HOSStatus.ON_DUTY,555l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,203l,563l,"07/23/10",new Date(1279864800000l),HOSStatus.DRIVING,660l), //645l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,203l,563l,"07/23/10",new Date(1279864800000l),HOSStatus.ON_DUTY,240l), //255l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,407l,3768l,"08/06/10",new Date(1281074400000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,407l,3768l,"08/06/10",new Date(1281074400000l),HOSStatus.ON_DUTY,420l),//435l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,407l,3768l,"08/05/10",new Date(1280988000000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,407l,3768l,"08/05/10",new Date(1280988000000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,407l,3768l,"08/04/10",new Date(1280901600000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,407l,3768l,"08/04/10",new Date(1280901600000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,407l,3768l,"08/03/10",new Date(1280815200000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,407l,3768l,"08/03/10",new Date(1280815200000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,407l,3768l,"08/02/10",new Date(1280728800000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,407l,3768l,"08/02/10",new Date(1280728800000l),HOSStatus.ON_DUTY,540l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,407l,3768l,"08/01/10",new Date(1280642400000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,407l,3768l,"08/01/10",new Date(1280642400000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,407l,3768l,"07/31/10",new Date(1280556000000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,407l,3768l,"07/31/10",new Date(1280556000000l),HOSStatus.ON_DUTY,300l), //285l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,407l,3768l,"07/30/10",new Date(1280469600000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,407l,3768l,"07/30/10",new Date(1280469600000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,407l,3768l,"07/29/10",new Date(1280383200000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,407l,3768l,"07/29/10",new Date(1280383200000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,407l,3768l,"07/28/10",new Date(1280296800000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,407l,3768l,"07/28/10",new Date(1280296800000l),HOSStatus.ON_DUTY,780l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,407l,3768l,"07/27/10",new Date(1280210400000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,407l,3768l,"07/27/10",new Date(1280210400000l),HOSStatus.ON_DUTY,570l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,407l,3768l,"07/26/10",new Date(1280124000000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,407l,3768l,"07/26/10",new Date(1280124000000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,407l,3768l,"07/25/10",new Date(1280037600000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,407l,3768l,"07/25/10",new Date(1280037600000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,407l,3768l,"07/24/10",new Date(1279951200000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,407l,3768l,"07/24/10",new Date(1279951200000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,407l,3768l,"07/23/10",new Date(1279864800000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,407l,3768l,"07/23/10",new Date(1279864800000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,289l,2685l,"08/06/10",new Date(1281074400000l),HOSStatus.DRIVING,120l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,289l,2685l,"08/06/10",new Date(1281074400000l),HOSStatus.ON_DUTY,420l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,289l,2685l,"08/05/10",new Date(1280988000000l),HOSStatus.DRIVING,345l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,289l,2685l,"08/05/10",new Date(1280988000000l),HOSStatus.ON_DUTY,435l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,289l,2685l,"08/04/10",new Date(1280901600000l),HOSStatus.DRIVING,195l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,289l,2685l,"08/04/10",new Date(1280901600000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,289l,2685l,"08/03/10",new Date(1280815200000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,289l,2685l,"08/03/10",new Date(1280815200000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,289l,2685l,"08/02/10",new Date(1280728800000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,289l,2685l,"08/02/10",new Date(1280728800000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,289l,2685l,"08/01/10",new Date(1280642400000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,289l,2685l,"08/01/10",new Date(1280642400000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,289l,2685l,"07/31/10",new Date(1280556000000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,289l,2685l,"07/31/10",new Date(1280556000000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,289l,2685l,"07/30/10",new Date(1280469600000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,289l,2685l,"07/30/10",new Date(1280469600000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,289l,2685l,"07/29/10",new Date(1280383200000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,289l,2685l,"07/29/10",new Date(1280383200000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,289l,2685l,"07/28/10",new Date(1280296800000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,289l,2685l,"07/28/10",new Date(1280296800000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,289l,2685l,"07/27/10",new Date(1280210400000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,289l,2685l,"07/27/10",new Date(1280210400000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,289l,2685l,"07/26/10",new Date(1280124000000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,289l,2685l,"07/26/10",new Date(1280124000000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,289l,2685l,"07/25/10",new Date(1280037600000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,289l,2685l,"07/25/10",new Date(1280037600000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,289l,2685l,"07/24/10",new Date(1279951200000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,289l,2685l,"07/24/10",new Date(1279951200000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,289l,2685l,"07/23/10",new Date(1279864800000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,289l,2685l,"07/23/10",new Date(1279864800000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Grainger,  Nicholas",RuleSetType.US_OIL,840l,4200l,"08/06/10",new Date(1281074400000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Grainger,  Nicholas",RuleSetType.US_OIL,840l,4200l,"08/06/10",new Date(1281074400000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Grainger,  Nicholas",RuleSetType.US_OIL,840l,4200l,"08/05/10",new Date(1280988000000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Grainger,  Nicholas",RuleSetType.US_OIL,840l,4200l,"08/05/10",new Date(1280988000000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Grainger,  Nicholas",RuleSetType.US_OIL,840l,4200l,"08/04/10",new Date(1280901600000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Grainger,  Nicholas",RuleSetType.US_OIL,840l,4200l,"08/04/10",new Date(1280901600000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Grainger,  Nicholas",RuleSetType.US_OIL,840l,4200l,"08/03/10",new Date(1280815200000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Grainger,  Nicholas",RuleSetType.US_OIL,840l,4200l,"08/03/10",new Date(1280815200000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Grainger,  Nicholas",RuleSetType.US_OIL,840l,4200l,"08/02/10",new Date(1280728800000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Grainger,  Nicholas",RuleSetType.US_OIL,840l,4200l,"08/02/10",new Date(1280728800000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Grainger,  Nicholas",RuleSetType.US_OIL,840l,4200l,"08/01/10",new Date(1280642400000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Grainger,  Nicholas",RuleSetType.US_OIL,840l,4200l,"08/01/10",new Date(1280642400000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Grainger,  Nicholas",RuleSetType.US_OIL,840l,4200l,"07/31/10",new Date(1280556000000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Grainger,  Nicholas",RuleSetType.US_OIL,840l,4200l,"07/31/10",new Date(1280556000000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Grainger,  Nicholas",RuleSetType.US_OIL,840l,4200l,"07/30/10",new Date(1280469600000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Grainger,  Nicholas",RuleSetType.US_OIL,840l,4200l,"07/30/10",new Date(1280469600000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Grainger,  Nicholas",RuleSetType.US_OIL,840l,4200l,"07/29/10",new Date(1280383200000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Grainger,  Nicholas",RuleSetType.US_OIL,840l,4200l,"07/29/10",new Date(1280383200000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Grainger,  Nicholas",RuleSetType.US_OIL,840l,4200l,"07/28/10",new Date(1280296800000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Grainger,  Nicholas",RuleSetType.US_OIL,840l,4200l,"07/28/10",new Date(1280296800000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Grainger,  Nicholas",RuleSetType.US_OIL,840l,4200l,"07/27/10",new Date(1280210400000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Grainger,  Nicholas",RuleSetType.US_OIL,840l,4200l,"07/27/10",new Date(1280210400000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Grainger,  Nicholas",RuleSetType.US_OIL,840l,4200l,"07/26/10",new Date(1280124000000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Grainger,  Nicholas",RuleSetType.US_OIL,840l,4200l,"07/26/10",new Date(1280124000000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Grainger,  Nicholas",RuleSetType.US_OIL,840l,4200l,"07/25/10",new Date(1280037600000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Grainger,  Nicholas",RuleSetType.US_OIL,840l,4200l,"07/25/10",new Date(1280037600000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Grainger,  Nicholas",RuleSetType.US_OIL,840l,4200l,"07/24/10",new Date(1279951200000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Grainger,  Nicholas",RuleSetType.US_OIL,840l,4200l,"07/24/10",new Date(1279951200000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Grainger,  Nicholas",RuleSetType.US_OIL,840l,4200l,"07/23/10",new Date(1279864800000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Grainger,  Nicholas",RuleSetType.US_OIL,840l,4200l,"07/23/10",new Date(1279864800000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Hunt,  Martyn",RuleSetType.US_OIL,840l,4200l,"08/06/10",new Date(1281074400000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Hunt,  Martyn",RuleSetType.US_OIL,840l,4200l,"08/06/10",new Date(1281074400000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Hunt,  Martyn",RuleSetType.US_OIL,840l,4200l,"08/05/10",new Date(1280988000000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Hunt,  Martyn",RuleSetType.US_OIL,840l,4200l,"08/05/10",new Date(1280988000000l),HOSStatus.ON_DUTY,615l),
        new DotHoursRemaining("Open Hole",0,"Hunt,  Martyn",RuleSetType.US_OIL,840l,4200l,"08/04/10",new Date(1280901600000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Hunt,  Martyn",RuleSetType.US_OIL,840l,4200l,"08/04/10",new Date(1280901600000l),HOSStatus.ON_DUTY,1440l),
        new DotHoursRemaining("Open Hole",0,"Hunt,  Martyn",RuleSetType.US_OIL,840l,4200l,"08/03/10",new Date(1280815200000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Hunt,  Martyn",RuleSetType.US_OIL,840l,4200l,"08/03/10",new Date(1280815200000l),HOSStatus.ON_DUTY,1440l),
        new DotHoursRemaining("Open Hole",0,"Hunt,  Martyn",RuleSetType.US_OIL,840l,4200l,"08/02/10",new Date(1280728800000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Hunt,  Martyn",RuleSetType.US_OIL,840l,4200l,"08/02/10",new Date(1280728800000l),HOSStatus.ON_DUTY,735l),
        new DotHoursRemaining("Open Hole",0,"Hunt,  Martyn",RuleSetType.US_OIL,840l,4200l,"08/01/10",new Date(1280642400000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Hunt,  Martyn",RuleSetType.US_OIL,840l,4200l,"08/01/10",new Date(1280642400000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Hunt,  Martyn",RuleSetType.US_OIL,840l,4200l,"07/31/10",new Date(1280556000000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Hunt,  Martyn",RuleSetType.US_OIL,840l,4200l,"07/31/10",new Date(1280556000000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Hunt,  Martyn",RuleSetType.US_OIL,840l,4200l,"07/30/10",new Date(1280469600000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Hunt,  Martyn",RuleSetType.US_OIL,840l,4200l,"07/30/10",new Date(1280469600000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Hunt,  Martyn",RuleSetType.US_OIL,840l,4200l,"07/29/10",new Date(1280383200000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Hunt,  Martyn",RuleSetType.US_OIL,840l,4200l,"07/29/10",new Date(1280383200000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Hunt,  Martyn",RuleSetType.US_OIL,840l,4200l,"07/28/10",new Date(1280296800000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Hunt,  Martyn",RuleSetType.US_OIL,840l,4200l,"07/28/10",new Date(1280296800000l),HOSStatus.ON_DUTY,675l),
        new DotHoursRemaining("Open Hole",0,"Hunt,  Martyn",RuleSetType.US_OIL,840l,4200l,"07/27/10",new Date(1280210400000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Hunt,  Martyn",RuleSetType.US_OIL,840l,4200l,"07/27/10",new Date(1280210400000l),HOSStatus.ON_DUTY,255l),
        new DotHoursRemaining("Open Hole",0,"Hunt,  Martyn",RuleSetType.US_OIL,840l,4200l,"07/26/10",new Date(1280124000000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Hunt,  Martyn",RuleSetType.US_OIL,840l,4200l,"07/26/10",new Date(1280124000000l),HOSStatus.ON_DUTY,1290l),
        new DotHoursRemaining("Open Hole",0,"Hunt,  Martyn",RuleSetType.US_OIL,840l,4200l,"07/25/10",new Date(1280037600000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Hunt,  Martyn",RuleSetType.US_OIL,840l,4200l,"07/25/10",new Date(1280037600000l),HOSStatus.ON_DUTY,570l),
        new DotHoursRemaining("Open Hole",0,"Hunt,  Martyn",RuleSetType.US_OIL,840l,4200l,"07/24/10",new Date(1279951200000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Hunt,  Martyn",RuleSetType.US_OIL,840l,4200l,"07/24/10",new Date(1279951200000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Hunt,  Martyn",RuleSetType.US_OIL,840l,4200l,"07/23/10",new Date(1279864800000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Hunt,  Martyn",RuleSetType.US_OIL,840l,4200l,"07/23/10",new Date(1279864800000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Wilkerson,  Jason M",RuleSetType.US_OIL,0l,1940l,"08/06/10",new Date(1281074400000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Wilkerson,  Jason M",RuleSetType.US_OIL,0l,1940l,"08/06/10",new Date(1281074400000l),HOSStatus.ON_DUTY,900l),
        new DotHoursRemaining("Open Hole",0,"Wilkerson,  Jason M",RuleSetType.US_OIL,0l,1940l,"08/05/10",new Date(1280988000000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Wilkerson,  Jason M",RuleSetType.US_OIL,0l,1940l,"08/05/10",new Date(1280988000000l),HOSStatus.ON_DUTY,1350l),
        new DotHoursRemaining("Open Hole",0,"Wilkerson,  Jason M",RuleSetType.US_OIL,0l,1940l,"08/04/10",new Date(1280901600000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Wilkerson,  Jason M",RuleSetType.US_OIL,0l,1940l,"08/04/10",new Date(1280901600000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Wilkerson,  Jason M",RuleSetType.US_OIL,0l,1940l,"08/03/10",new Date(1280815200000l),HOSStatus.DRIVING,645l),
        new DotHoursRemaining("Open Hole",0,"Wilkerson,  Jason M",RuleSetType.US_OIL,0l,1940l,"08/03/10",new Date(1280815200000l),HOSStatus.ON_DUTY,45l),
        new DotHoursRemaining("Open Hole",0,"Wilkerson,  Jason M",RuleSetType.US_OIL,0l,1940l,"08/02/10",new Date(1280728800000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Wilkerson,  Jason M",RuleSetType.US_OIL,0l,1940l,"08/02/10",new Date(1280728800000l),HOSStatus.ON_DUTY,990l),
        new DotHoursRemaining("Open Hole",0,"Wilkerson,  Jason M",RuleSetType.US_OIL,0l,1940l,"08/01/10",new Date(1280642400000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Wilkerson,  Jason M",RuleSetType.US_OIL,0l,1940l,"08/01/10",new Date(1280642400000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Wilkerson,  Jason M",RuleSetType.US_OIL,0l,1940l,"07/31/10",new Date(1280556000000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Wilkerson,  Jason M",RuleSetType.US_OIL,0l,1940l,"07/31/10",new Date(1280556000000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Wilkerson,  Jason M",RuleSetType.US_OIL,0l,1940l,"07/30/10",new Date(1280469600000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Wilkerson,  Jason M",RuleSetType.US_OIL,0l,1940l,"07/30/10",new Date(1280469600000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Wilkerson,  Jason M",RuleSetType.US_OIL,0l,1940l,"07/29/10",new Date(1280383200000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Wilkerson,  Jason M",RuleSetType.US_OIL,0l,1940l,"07/29/10",new Date(1280383200000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Wilkerson,  Jason M",RuleSetType.US_OIL,0l,1940l,"07/28/10",new Date(1280296800000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Wilkerson,  Jason M",RuleSetType.US_OIL,0l,1940l,"07/28/10",new Date(1280296800000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Wilkerson,  Jason M",RuleSetType.US_OIL,0l,1940l,"07/27/10",new Date(1280210400000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Wilkerson,  Jason M",RuleSetType.US_OIL,0l,1940l,"07/27/10",new Date(1280210400000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Wilkerson,  Jason M",RuleSetType.US_OIL,0l,1940l,"07/26/10",new Date(1280124000000l),HOSStatus.DRIVING,90l),
        new DotHoursRemaining("Open Hole",0,"Wilkerson,  Jason M",RuleSetType.US_OIL,0l,1940l,"07/26/10",new Date(1280124000000l),HOSStatus.ON_DUTY,255l),
        new DotHoursRemaining("Open Hole",0,"Wilkerson,  Jason M",RuleSetType.US_OIL,0l,1940l,"07/25/10",new Date(1280037600000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Wilkerson,  Jason M",RuleSetType.US_OIL,0l,1940l,"07/25/10",new Date(1280037600000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Wilkerson,  Jason M",RuleSetType.US_OIL,0l,1940l,"07/24/10",new Date(1279951200000l),HOSStatus.DRIVING,90l),
        new DotHoursRemaining("Open Hole",0,"Wilkerson,  Jason M",RuleSetType.US_OIL,0l,1940l,"07/24/10",new Date(1279951200000l),HOSStatus.ON_DUTY,120l),
        new DotHoursRemaining("Open Hole",0,"Wilkerson,  Jason M",RuleSetType.US_OIL,0l,1940l,"07/23/10",new Date(1279864800000l),HOSStatus.DRIVING,60l), //75l),
        new DotHoursRemaining("Open Hole",0,"Wilkerson,  Jason M",RuleSetType.US_OIL,0l,1940l,"07/23/10",new Date(1279864800000l),HOSStatus.ON_DUTY,420l), //405l),
        new DotHoursRemaining("Open Hole",0,"Wood,  Klinton",RuleSetType.US_OIL,840l,4200l,"08/06/10",new Date(1281074400000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Wood,  Klinton",RuleSetType.US_OIL,840l,4200l,"08/06/10",new Date(1281074400000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Wood,  Klinton",RuleSetType.US_OIL,840l,4200l,"08/05/10",new Date(1280988000000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Wood,  Klinton",RuleSetType.US_OIL,840l,4200l,"08/05/10",new Date(1280988000000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Wood,  Klinton",RuleSetType.US_OIL,840l,4200l,"08/04/10",new Date(1280901600000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Wood,  Klinton",RuleSetType.US_OIL,840l,4200l,"08/04/10",new Date(1280901600000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Wood,  Klinton",RuleSetType.US_OIL,840l,4200l,"08/03/10",new Date(1280815200000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Wood,  Klinton",RuleSetType.US_OIL,840l,4200l,"08/03/10",new Date(1280815200000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Wood,  Klinton",RuleSetType.US_OIL,840l,4200l,"08/02/10",new Date(1280728800000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Wood,  Klinton",RuleSetType.US_OIL,840l,4200l,"08/02/10",new Date(1280728800000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Wood,  Klinton",RuleSetType.US_OIL,840l,4200l,"08/01/10",new Date(1280642400000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Wood,  Klinton",RuleSetType.US_OIL,840l,4200l,"08/01/10",new Date(1280642400000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Wood,  Klinton",RuleSetType.US_OIL,840l,4200l,"07/31/10",new Date(1280556000000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Wood,  Klinton",RuleSetType.US_OIL,840l,4200l,"07/31/10",new Date(1280556000000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Wood,  Klinton",RuleSetType.US_OIL,840l,4200l,"07/30/10",new Date(1280469600000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Wood,  Klinton",RuleSetType.US_OIL,840l,4200l,"07/30/10",new Date(1280469600000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Wood,  Klinton",RuleSetType.US_OIL,840l,4200l,"07/29/10",new Date(1280383200000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Wood,  Klinton",RuleSetType.US_OIL,840l,4200l,"07/29/10",new Date(1280383200000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Wood,  Klinton",RuleSetType.US_OIL,840l,4200l,"07/28/10",new Date(1280296800000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Wood,  Klinton",RuleSetType.US_OIL,840l,4200l,"07/28/10",new Date(1280296800000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Wood,  Klinton",RuleSetType.US_OIL,840l,4200l,"07/27/10",new Date(1280210400000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Wood,  Klinton",RuleSetType.US_OIL,840l,4200l,"07/27/10",new Date(1280210400000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Wood,  Klinton",RuleSetType.US_OIL,840l,4200l,"07/26/10",new Date(1280124000000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Wood,  Klinton",RuleSetType.US_OIL,840l,4200l,"07/26/10",new Date(1280124000000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Wood,  Klinton",RuleSetType.US_OIL,840l,4200l,"07/25/10",new Date(1280037600000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Wood,  Klinton",RuleSetType.US_OIL,840l,4200l,"07/25/10",new Date(1280037600000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Wood,  Klinton",RuleSetType.US_OIL,840l,4200l,"07/24/10",new Date(1279951200000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Wood,  Klinton",RuleSetType.US_OIL,840l,4200l,"07/24/10",new Date(1279951200000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Wood,  Klinton",RuleSetType.US_OIL,840l,4200l,"07/23/10",new Date(1279864800000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Wood,  Klinton",RuleSetType.US_OIL,840l,4200l,"07/23/10",new Date(1279864800000l),HOSStatus.ON_DUTY,0l),
      },
    };
    
    @Test
    public void gainTestCasesForHoursRemaining() {

      for (int testCaseCnt = 0; testCaseCnt < testCaseName.length; testCaseCnt++) {
            HosRecordDataSet testData = new HosRecordDataSet(DATA_PATH, testCaseName[testCaseCnt], false);
            DotHoursRemainingReportCriteria dotHoursRemainingReportCriteria = new DotHoursRemainingReportCriteria(Locale.US);
            dotHoursRemainingReportCriteria.setReportDate(new Date(), TimeZone.getTimeZone("UTC"));
            dotHoursRemainingReportCriteria.initDataSet(testData.getGroupHierarchy(), testData.driverHOSRecordMap, new DateTime(testCaseCurrentDate[testCaseCnt].getTime()));
            
            List<DotHoursRemaining> dataList= dotHoursRemainingReportCriteria.getMainDataset();
//            System.out.println("{");
//            for (DotHoursRemaining data : dataList) {
//                data.dump();
//            }
//            System.out.println("},");
            System.out.println("dataList size " + dataList.size());
            
            int ecnt = 0;
            for (DotHoursRemaining data : dataList) {
                DotHoursRemaining expectedData = dotHoursRemainingExpectedData[testCaseCnt][ecnt];
                assertEquals(testCaseName[testCaseCnt] + " " + ecnt + " day", expectedData.getDay(), data.getDay());
                assertEquals(testCaseName[testCaseCnt] + " " + ecnt + " driverName", expectedData.getDriverName(), data.getDriverName());
                assertEquals(testCaseName[testCaseCnt] + " " + ecnt + " dotType", expectedData.getDotType(), data.getDotType());
//                if (!expectedData.getMinutesRemaining().equals(data.getMinutesRemaining()))
//                System.out.println(testCaseName[testCaseCnt] + " " + ecnt + " minutesRemaining " +  expectedData.getMinutesRemaining() + " " + data.getMinutesRemaining());                
                assertEquals(testCaseName[testCaseCnt] + " " + ecnt + " minutesRemaining", expectedData.getMinutesRemaining(), data.getMinutesRemaining());
                assertEquals(testCaseName[testCaseCnt] + " " + ecnt + " totalAdjustedMinutes", expectedData.getTotalAdjustedMinutes(), data.getTotalAdjustedMinutes());
//                if (!expectedData.getTotalAdjustedMinutes().equals( data.getTotalAdjustedMinutes()))
//                System.out.println(testCaseName[testCaseCnt] + " " + ecnt + " totalAdjustedMinutes " + expectedData.getTotalAdjustedMinutes() + " " +  data.getTotalAdjustedMinutes());
                assertEquals(testCaseName[testCaseCnt] + " " + ecnt + " status", expectedData.getStatus(), data.getStatus());
                ecnt++;
            }
            dump("dotHoursRemainingTest", testCaseCnt, dotHoursRemainingReportCriteria, FormatType.PDF);
            dump("dotHoursRemainingTest", testCaseCnt, dotHoursRemainingReportCriteria, FormatType.EXCEL);

        }
    }

    @Test
    public void gainDuplicateDrivers() {
        // for defect DE7656
      for (int testCaseCnt = 0; testCaseCnt < testCaseName.length; testCaseCnt++) {
            HosRecordDataSet testData = new HosRecordDataSet(DATA_PATH, testCaseName[testCaseCnt], false);
            String first = null;
            String last = null;
            for (Driver driver : testData.driverHOSRecordMap.keySet()) {
                if (first == null && last == null) {
                    first = driver.getPerson().getFirst();
                    last = driver.getPerson().getLast();
                }
                else {
                    driver.getPerson().setFirst(first);
                    driver.getPerson().setLast(last);
                    break;
                }
            }

            
            DotHoursRemainingReportCriteria dotHoursRemainingReportCriteria = new DotHoursRemainingReportCriteria(Locale.US);
            dotHoursRemainingReportCriteria.setReportDate(new Date(), TimeZone.getTimeZone("UTC"));
            dotHoursRemainingReportCriteria.initDataSet(testData.getGroupHierarchy(), testData.driverHOSRecordMap, new DateTime(testCaseCurrentDate[testCaseCnt].getTime()));
            
            List<DotHoursRemaining> dataList= dotHoursRemainingReportCriteria.getMainDataset();
            dump("dotHoursRemainingDupTest", testCaseCnt, dotHoursRemainingReportCriteria, FormatType.PDF);
            dump("dotHoursRemainingDupTest", testCaseCnt, dotHoursRemainingReportCriteria, FormatType.EXCEL);
            System.out.println("dataList size " + dataList.size());


            String lastId = null;
            String id = null;
            int ecnt = 0;
            for (DotHoursRemaining data : dataList) {
                DotHoursRemaining expectedData = dotHoursRemainingExpectedData[testCaseCnt][ecnt];
                id = data.getDriverName() +""+data.getDriverId();
                if (lastId != null) {
                    assertTrue("Sort order is not correct " + lastId + " should be before " + id, lastId.compareTo(id) <= 0);
                    
                }
                lastId = id;
                ecnt++;
            }

        }
    }




    private static final String DISPLAY_DATE_FORMAT = "MM/dd/yyyy";
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(DISPLAY_DATE_FORMAT).withZone(DateTimeZone.forID("US/Mountain"));

    @Test
    public void newYearIssue() {
        // for defect DE8281
      DateTime newYearDate = dateTimeFormatter.parseDateTime("01/06/2014").plusHours(1);
      DateTime oldYearDate = dateTimeFormatter.parseDateTime("08/06/2013").plusHours(1);
      for (int testCaseCnt = 0; testCaseCnt < testCaseName.length; testCaseCnt++) {
            HosRecordDataSet testDataNewYear = new HosRecordDataSet(DATA_PATH, testCaseName[testCaseCnt], false);
            offsetDatesToNewYear(testDataNewYear.driverHOSRecordMap, newYearDate);
            
            DotHoursRemainingReportCriteria dotHoursRemainingReportCriteriaNewYear = new DotHoursRemainingReportCriteria(Locale.US);
            dotHoursRemainingReportCriteriaNewYear.setReportDate(new Date(), TimeZone.getTimeZone("UTC"));
            dotHoursRemainingReportCriteriaNewYear.initDataSet(testDataNewYear.getGroupHierarchy(), testDataNewYear.driverHOSRecordMap, newYearDate);
            
            HosRecordDataSet testDataOldYear = new HosRecordDataSet(DATA_PATH, testCaseName[testCaseCnt], false);
            offsetDatesToNewYear(testDataOldYear.driverHOSRecordMap, oldYearDate);
            
            DotHoursRemainingReportCriteria dotHoursRemainingReportCriteriaOldYear = new DotHoursRemainingReportCriteria(Locale.US);
            dotHoursRemainingReportCriteriaOldYear.setReportDate(new Date(), TimeZone.getTimeZone("UTC"));
            dotHoursRemainingReportCriteriaOldYear.initDataSet(testDataNewYear.getGroupHierarchy(), testDataOldYear.driverHOSRecordMap, oldYearDate);
            
            
            String newYearReport = genReportToString(dotHoursRemainingReportCriteriaNewYear, FormatType.HTML);
            String oldYearReport = genReportToString(dotHoursRemainingReportCriteriaOldYear, FormatType.HTML);
            
            //dump("dotHoursRemainingNewYear", testCaseCnt, dotHoursRemainingReportCriteriaNewYear, FormatType.PDF);
            //dump("dotHoursRemainingOldYear", testCaseCnt, dotHoursRemainingReportCriteriaOldYear, FormatType.PDF);
            
            String oldYearReportWithDatesReplaced = replaceDates(oldYearReport);
            assertEquals("data identical except for date", newYearReport, oldYearReportWithDatesReplaced);
            
            
            
            
        }
    }
    

    
    private String replaceDates(String oldYearReport) {
        return oldYearReport.replace("08/06/13", "01/06/14")
                .replace("08/05/13", "01/05/14")
                .replace("08/04/13", "01/04/14")
                .replace("08/03/13", "01/03/14")
                .replace("08/02/13", "01/02/14")
                .replace("08/01/13", "01/01/14")
                .replace("07/31/13", "12/31/13")
                .replace("07/30/13", "12/30/13")
                .replace("07/29/13", "12/29/13")
                .replace("07/28/13", "12/28/13")
                .replace("07/27/13", "12/27/13")
                .replace("07/26/13", "12/26/13")
                .replace("07/25/13", "12/25/13")
                .replace("07/24/13", "12/24/13")
                .replace("07/23/13", "12/23/13")
                .replace("07/22/13", "12/22/13");
    }

    private Map<Driver, List<HOSRecord>> offsetDatesToNewYear(Map<Driver, List<HOSRecord>> driverHOSRecordMap, DateTime newYearDate) {
        for (Driver driver : driverHOSRecordMap.keySet()) {
            List<HOSRecord> recList = driverHOSRecordMap.get(driver);
            long timeOffset = newYearDate.toDate().getTime() - recList.get(0).getLogTime().getTime();
            
            for (HOSRecord rec : recList) {
                rec.setLogTime(new Date(rec.getLogTime().getTime()+timeOffset));
            }
        }
        return null;
    }
}
