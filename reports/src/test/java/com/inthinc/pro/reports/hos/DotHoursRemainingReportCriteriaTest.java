package com.inthinc.pro.reports.hos;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.joda.time.DateTime;
import org.junit.Test;

import com.inthinc.hos.model.HOSStatus;
import com.inthinc.hos.model.RuleSetType;
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
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,212l,563l,"08/06/10",new Date(1281074400000l),HOSStatus.DRIVING,510l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,212l,563l,"08/06/10",new Date(1281074400000l),HOSStatus.ON_DUTY,15l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,212l,563l,"08/05/10",new Date(1280988000000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,212l,563l,"08/05/10",new Date(1280988000000l),HOSStatus.ON_DUTY,645l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,212l,563l,"08/04/10",new Date(1280901600000l),HOSStatus.DRIVING,255l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,212l,563l,"08/04/10",new Date(1280901600000l),HOSStatus.ON_DUTY,840l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,212l,563l,"08/03/10",new Date(1280815200000l),HOSStatus.DRIVING,390l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,212l,563l,"08/03/10",new Date(1280815200000l),HOSStatus.ON_DUTY,150l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,212l,563l,"08/02/10",new Date(1280728800000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,212l,563l,"08/02/10",new Date(1280728800000l),HOSStatus.ON_DUTY,825l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,212l,563l,"08/01/10",new Date(1280642400000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,212l,563l,"08/01/10",new Date(1280642400000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,212l,563l,"07/31/10",new Date(1280556000000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,212l,563l,"07/31/10",new Date(1280556000000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,212l,563l,"07/30/10",new Date(1280469600000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,212l,563l,"07/30/10",new Date(1280469600000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,212l,563l,"07/29/10",new Date(1280383200000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,212l,563l,"07/29/10",new Date(1280383200000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,212l,563l,"07/28/10",new Date(1280296800000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,212l,563l,"07/28/10",new Date(1280296800000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,212l,563l,"07/27/10",new Date(1280210400000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,212l,563l,"07/27/10",new Date(1280210400000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,212l,563l,"07/26/10",new Date(1280124000000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,212l,563l,"07/26/10",new Date(1280124000000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,212l,563l,"07/25/10",new Date(1280037600000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,212l,563l,"07/25/10",new Date(1280037600000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,212l,563l,"07/24/10",new Date(1279951200000l),HOSStatus.DRIVING,15l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,212l,563l,"07/24/10",new Date(1279951200000l),HOSStatus.ON_DUTY,555l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,212l,563l,"07/23/10",new Date(1279864800000l),HOSStatus.DRIVING,645l),
        new DotHoursRemaining("Open Hole",0,"Davis,  Rod",RuleSetType.US_OIL,212l,563l,"07/23/10",new Date(1279864800000l),HOSStatus.ON_DUTY,255l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,408l,3768l,"08/06/10",new Date(1281074400000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,408l,3768l,"08/06/10",new Date(1281074400000l),HOSStatus.ON_DUTY,420l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,408l,3768l,"08/05/10",new Date(1280988000000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,408l,3768l,"08/05/10",new Date(1280988000000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,408l,3768l,"08/04/10",new Date(1280901600000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,408l,3768l,"08/04/10",new Date(1280901600000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,408l,3768l,"08/03/10",new Date(1280815200000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,408l,3768l,"08/03/10",new Date(1280815200000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,408l,3768l,"08/02/10",new Date(1280728800000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,408l,3768l,"08/02/10",new Date(1280728800000l),HOSStatus.ON_DUTY,540l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,408l,3768l,"08/01/10",new Date(1280642400000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,408l,3768l,"08/01/10",new Date(1280642400000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,408l,3768l,"07/31/10",new Date(1280556000000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,408l,3768l,"07/31/10",new Date(1280556000000l),HOSStatus.ON_DUTY,285l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,408l,3768l,"07/30/10",new Date(1280469600000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,408l,3768l,"07/30/10",new Date(1280469600000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,408l,3768l,"07/29/10",new Date(1280383200000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,408l,3768l,"07/29/10",new Date(1280383200000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,408l,3768l,"07/28/10",new Date(1280296800000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,408l,3768l,"07/28/10",new Date(1280296800000l),HOSStatus.ON_DUTY,780l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,408l,3768l,"07/27/10",new Date(1280210400000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,408l,3768l,"07/27/10",new Date(1280210400000l),HOSStatus.ON_DUTY,570l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,408l,3768l,"07/26/10",new Date(1280124000000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,408l,3768l,"07/26/10",new Date(1280124000000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,408l,3768l,"07/25/10",new Date(1280037600000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,408l,3768l,"07/25/10",new Date(1280037600000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,408l,3768l,"07/24/10",new Date(1279951200000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,408l,3768l,"07/24/10",new Date(1279951200000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,408l,3768l,"07/23/10",new Date(1279864800000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Ehlers,  Neal",RuleSetType.US_OIL,408l,3768l,"07/23/10",new Date(1279864800000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,295l,2685l,"08/06/10",new Date(1281074400000l),HOSStatus.DRIVING,120l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,295l,2685l,"08/06/10",new Date(1281074400000l),HOSStatus.ON_DUTY,435l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,295l,2685l,"08/05/10",new Date(1280988000000l),HOSStatus.DRIVING,315l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,295l,2685l,"08/05/10",new Date(1280988000000l),HOSStatus.ON_DUTY,465l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,295l,2685l,"08/04/10",new Date(1280901600000l),HOSStatus.DRIVING,195l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,295l,2685l,"08/04/10",new Date(1280901600000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,295l,2685l,"08/03/10",new Date(1280815200000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,295l,2685l,"08/03/10",new Date(1280815200000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,295l,2685l,"08/02/10",new Date(1280728800000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,295l,2685l,"08/02/10",new Date(1280728800000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,295l,2685l,"08/01/10",new Date(1280642400000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,295l,2685l,"08/01/10",new Date(1280642400000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,295l,2685l,"07/31/10",new Date(1280556000000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,295l,2685l,"07/31/10",new Date(1280556000000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,295l,2685l,"07/30/10",new Date(1280469600000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,295l,2685l,"07/30/10",new Date(1280469600000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,295l,2685l,"07/29/10",new Date(1280383200000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,295l,2685l,"07/29/10",new Date(1280383200000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,295l,2685l,"07/28/10",new Date(1280296800000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,295l,2685l,"07/28/10",new Date(1280296800000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,295l,2685l,"07/27/10",new Date(1280210400000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,295l,2685l,"07/27/10",new Date(1280210400000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,295l,2685l,"07/26/10",new Date(1280124000000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,295l,2685l,"07/26/10",new Date(1280124000000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,295l,2685l,"07/25/10",new Date(1280037600000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,295l,2685l,"07/25/10",new Date(1280037600000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,295l,2685l,"07/24/10",new Date(1279951200000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,295l,2685l,"07/24/10",new Date(1279951200000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,295l,2685l,"07/23/10",new Date(1279864800000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Gilbert,  Justin T",RuleSetType.US_OIL,295l,2685l,"07/23/10",new Date(1279864800000l),HOSStatus.ON_DUTY,0l),
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
        new DotHoursRemaining("Open Hole",0,"Wilkerson,  Jason M",RuleSetType.US_OIL,0l,1940l,"08/03/10",new Date(1280815200000l),HOSStatus.DRIVING,615l),
        new DotHoursRemaining("Open Hole",0,"Wilkerson,  Jason M",RuleSetType.US_OIL,0l,1940l,"08/03/10",new Date(1280815200000l),HOSStatus.ON_DUTY,75l),
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
        new DotHoursRemaining("Open Hole",0,"Wilkerson,  Jason M",RuleSetType.US_OIL,0l,1940l,"07/26/10",new Date(1280124000000l),HOSStatus.DRIVING,75l),
        new DotHoursRemaining("Open Hole",0,"Wilkerson,  Jason M",RuleSetType.US_OIL,0l,1940l,"07/26/10",new Date(1280124000000l),HOSStatus.ON_DUTY,270l),
        new DotHoursRemaining("Open Hole",0,"Wilkerson,  Jason M",RuleSetType.US_OIL,0l,1940l,"07/25/10",new Date(1280037600000l),HOSStatus.DRIVING,0l),
        new DotHoursRemaining("Open Hole",0,"Wilkerson,  Jason M",RuleSetType.US_OIL,0l,1940l,"07/25/10",new Date(1280037600000l),HOSStatus.ON_DUTY,0l),
        new DotHoursRemaining("Open Hole",0,"Wilkerson,  Jason M",RuleSetType.US_OIL,0l,1940l,"07/24/10",new Date(1279951200000l),HOSStatus.DRIVING,90l),
        new DotHoursRemaining("Open Hole",0,"Wilkerson,  Jason M",RuleSetType.US_OIL,0l,1940l,"07/24/10",new Date(1279951200000l),HOSStatus.ON_DUTY,120l),
        new DotHoursRemaining("Open Hole",0,"Wilkerson,  Jason M",RuleSetType.US_OIL,0l,1940l,"07/23/10",new Date(1279864800000l),HOSStatus.DRIVING,60l),
        new DotHoursRemaining("Open Hole",0,"Wilkerson,  Jason M",RuleSetType.US_OIL,0l,1940l,"07/23/10",new Date(1279864800000l),HOSStatus.ON_DUTY,420l),
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
            dotHoursRemainingReportCriteria.initDataSet(testData.getGroupHierarchy(), testData.driverHOSRecordMap, new DateTime(testCaseCurrentDate[testCaseCnt].getTime()));
            
            List<DotHoursRemaining> dataList= dotHoursRemainingReportCriteria.getMainDataset();
//            System.out.println("{");
//            for (DotHoursRemaining data : dataList) {
//                data.dump();
//            }
//            System.out.println("},");
            
            int ecnt = 0;
            for (DotHoursRemaining data : dataList) {
                DotHoursRemaining expectedData = dotHoursRemainingExpectedData[testCaseCnt][ecnt];
                assertEquals(testCaseName[testCaseCnt] + " " + ecnt + " day", expectedData.getDay(), data.getDay());
                assertEquals(testCaseName[testCaseCnt] + " " + ecnt + " driverName", expectedData.getDriverName(), data.getDriverName());
                assertEquals(testCaseName[testCaseCnt] + " " + ecnt + " dotType", expectedData.getDotType(), data.getDotType());
                assertEquals(testCaseName[testCaseCnt] + " " + ecnt + " minutesRemaining", expectedData.getMinutesRemaining(), data.getMinutesRemaining());
                assertEquals(testCaseName[testCaseCnt] + " " + ecnt + " totalAdjustedMinutes", expectedData.getTotalAdjustedMinutes(), data.getTotalAdjustedMinutes());
                assertEquals(testCaseName[testCaseCnt] + " " + ecnt + " status", expectedData.getStatus(), data.getStatus());
                ecnt++;
            }
            dump("dotHoursRemainingTest", testCaseCnt, dotHoursRemainingReportCriteria, FormatType.PDF);
            dump("dotHoursRemainingTest", testCaseCnt, dotHoursRemainingReportCriteria, FormatType.EXCEL);

        }
    }

}
