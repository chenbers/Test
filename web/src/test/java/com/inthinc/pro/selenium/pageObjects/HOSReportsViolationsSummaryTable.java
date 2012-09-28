package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.SortHeader;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.selenium.pageEnums.ViolationsSummaryReportTableEnum;

public class HOSReportsViolationsSummaryTable {

    public class Links {
        public SortHeader sortCumulative_0_14() {
            return new SortHeader(ViolationsSummaryReportTableEnum.CUMULATIVE_0_14);
        }

        public SortHeader sortCumulative_15_29() {
            return new SortHeader(ViolationsSummaryReportTableEnum.CUMULATIVE_15_29);
        }

        public SortHeader sortCumulative_30() {
            return new SortHeader(ViolationsSummaryReportTableEnum.CUMULATIVE_30);
        }

        public SortHeader sortDrivers() {
            return new SortHeader(ViolationsSummaryReportTableEnum.DRIVERS);
        }

        public SortHeader sortDriving_0_14() {
            return new SortHeader(ViolationsSummaryReportTableEnum.DRIVING_0_14);
        }

        public SortHeader sortDriving_15_29() {
            return new SortHeader(ViolationsSummaryReportTableEnum.DRIVING_15_29);
        }

        public SortHeader sortDriving_30() {
            return new SortHeader(ViolationsSummaryReportTableEnum.DRIVING_30);
        }

        public SortHeader sortGroupName() {
            return new SortHeader(ViolationsSummaryReportTableEnum.GROUP_NAME);
        }

        public SortHeader sortMiles() {
            return new SortHeader(ViolationsSummaryReportTableEnum.MILES);
        }

        public SortHeader sortMilesNoDriver() {
            return new SortHeader(ViolationsSummaryReportTableEnum.MILES_NO_DRIVER);
        }

        public SortHeader sortOffDuty_0_14() {
            return new SortHeader(ViolationsSummaryReportTableEnum.OFF_DUTY_0_14);
        }

        public SortHeader sortOffDuty_15_29() {
            return new SortHeader(ViolationsSummaryReportTableEnum.OFF_DUTY_15_29);
        }

        public SortHeader sortOffDuty_30() {
            return new SortHeader(ViolationsSummaryReportTableEnum.OFF_DUTY_30);
        }

        public SortHeader sortOnDuty_0_14() {
            return new SortHeader(ViolationsSummaryReportTableEnum.ON_DUTY_0_14);
        }

        public SortHeader sortOnDuty_15_29() {
            return new SortHeader(ViolationsSummaryReportTableEnum.ON_DUTY_15_29);
        }

        public SortHeader sortOnDuty_30() {
            return new SortHeader(ViolationsSummaryReportTableEnum.ON_DUTY_30);
        }

    }

    public class Texts {
        public Text cumulativeRuleMinOver() {
            return new Text(ViolationsSummaryReportTableEnum.CUMULATIVE_RULE);
        }

        public Text drivingRuleMinOver() {
            return new Text(ViolationsSummaryReportTableEnum.DRIVING_RULE);
        }

        public TextTable entryCumulative_0_14() {
            return new TextTable(ViolationsSummaryReportTableEnum.CUMULATIVE_0_14);
        }

        public TextTable entryCumulative_15_29() {
            return new TextTable(ViolationsSummaryReportTableEnum.CUMULATIVE_15_29);
        }

        public TextTable entryCumulative_30() {
            return new TextTable(ViolationsSummaryReportTableEnum.CUMULATIVE_30);
        }

        public TextTable entryDrivers() {
            return new TextTable(ViolationsSummaryReportTableEnum.DRIVERS);
        }

        public TextTable entryDriving_0_14() {
            return new TextTable(ViolationsSummaryReportTableEnum.DRIVING_0_14);
        }

        public TextTable entryDriving_15_29() {
            return new TextTable(ViolationsSummaryReportTableEnum.DRIVING_15_29);
        }

        public TextTable entryDriving_30() {
            return new TextTable(ViolationsSummaryReportTableEnum.DRIVING_30);
        }

        public TextTable entryGroupName() {
            return new TextTable(ViolationsSummaryReportTableEnum.GROUP_NAME);
        }

        public TextTable entryMiles() {
            return new TextTable(ViolationsSummaryReportTableEnum.MILES);
        }

        public TextTable entryMilesNoDriver() {
            return new TextTable(ViolationsSummaryReportTableEnum.MILES_NO_DRIVER);
        }

        public TextTable entryOffDuty_0_14() {
            return new TextTable(ViolationsSummaryReportTableEnum.OFF_DUTY_0_14);
        }

        public TextTable entryOffDuty_15_29() {
            return new TextTable(ViolationsSummaryReportTableEnum.OFF_DUTY_15_29);
        }

        public TextTable entryOffDuty_30() {
            return new TextTable(ViolationsSummaryReportTableEnum.OFF_DUTY_30);
        }

        public TextTable entryOnDuty_0_14() {
            return new TextTable(ViolationsSummaryReportTableEnum.ON_DUTY_0_14);
        }

        public TextTable entryOnDuty_15_29() {
            return new TextTable(ViolationsSummaryReportTableEnum.ON_DUTY_15_29);
        }

        public TextTable entryOnDuty_30() {
            return new TextTable(ViolationsSummaryReportTableEnum.ON_DUTY_30);
        }

        public Text offDutyRuleMinOver() {
            return new Text(ViolationsSummaryReportTableEnum.OFF_DUTY_RULE);
        }

        public Text onDutyRuleMinOver() {
            return new Text(ViolationsSummaryReportTableEnum.ON_DUTY_RULE);
        }

        public Text total() {
            return new Text(ViolationsSummaryReportTableEnum.TOTAL);
        }

    }

    public Links _link() {
        return new Links();
    }

    public Texts _text() {
        return new Texts();
    }
}
