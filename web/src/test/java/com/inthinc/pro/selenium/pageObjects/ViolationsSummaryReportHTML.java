package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.selenium.pageEnums.ViolationsSummaryReportHTMLEnum;

public class ViolationsSummaryReportHTML {

    public Texts _text() {
        return new Texts();
    }

    public class Texts {
        public Text title(Integer pageNumber) {
            return new Text(ViolationsSummaryReportHTMLEnum.TITLE,
                    pageNumber.toString());
        }

        public Text timeFrame(Integer pageNumber) {
            return new Text(ViolationsSummaryReportHTMLEnum.TIME_FRAME,
                    pageNumber.toString());
        }

        public Text drivingRuleLineOne(Integer pageNumber) {
            return new Text(ViolationsSummaryReportHTMLEnum.DRIVING_LINE1,
                    pageNumber.toString());
        }

        public Text drivingRuleLineTwo(Integer pageNumber) {
            return new Text(ViolationsSummaryReportHTMLEnum.DRIVING_LINE2,
                    pageNumber.toString());
        }

        public Text onDutyRuleLineOne(Integer pageNumber) {
            return new Text(ViolationsSummaryReportHTMLEnum.ON_DUTY_RULE_LINE1,
                    pageNumber.toString());
        }

        public Text onDutyRuleLineTwo(Integer pageNumber) {
            return new Text(ViolationsSummaryReportHTMLEnum.ON_DUTY_RULE_LINE2,
                    pageNumber.toString());
        }

        public Text cumulativeRuleLineOne(Integer pageNumber) {
            return new Text(
                    ViolationsSummaryReportHTMLEnum.CUMULATIVE_RULE_LINE1,
                    pageNumber.toString());
        }

        public Text cumulativeRuleLineTwo(Integer pageNumber) {
            return new Text(
                    ViolationsSummaryReportHTMLEnum.CUMULATIVE_RULE_LINE2,
                    pageNumber.toString());
        }

        public Text offDutyRuleLineOne(Integer pageNumber) {
            return new Text(
                    ViolationsSummaryReportHTMLEnum.OFF_DUTY_RULE_LINE1,
                    pageNumber.toString());
        }

        public Text offDutyRuleLineTwo(Integer pageNumber) {
            return new Text(
                    ViolationsSummaryReportHTMLEnum.OFF_DUTY_RULE_LINE3,
                    pageNumber.toString());
        }

        public Text headerTotal(Integer pageNumber) {
            return new Text(ViolationsSummaryReportHTMLEnum.TOTAL,
                    pageNumber.toString());
        }

        public Text headerZeroMiles(Integer pageNumber) {
            return new Text(ViolationsSummaryReportHTMLEnum.ZERO_MILES,
                    pageNumber.toString());
        }

        public Text headerZeroMilesPercent(Integer pageNumber) {
            return new Text(ViolationsSummaryReportHTMLEnum.ZERO_MILES_PERCENT,
                    pageNumber.toString());
        }

        public Text headerViolationsPercent(Integer pageNumber) {
            return new Text(ViolationsSummaryReportHTMLEnum.VIOLATIONS_PERCENT,
                    pageNumber.toString());
        }

        public Text headerDriving_0_14(Integer pageNumber) {
            return new Text(
                    ViolationsSummaryReportHTMLEnum.HEADER_DRIVING_0_14,
                    pageNumber.toString());
        }

        public Text headerDriving_15_29(Integer pageNumber) {
            return new Text(
                    ViolationsSummaryReportHTMLEnum.HEADER_DRIVING_15_29,
                    pageNumber.toString());
        }

        public Text headerDriving_30(Integer pageNumber) {
            return new Text(ViolationsSummaryReportHTMLEnum.HEADER_DRIVING_30,
                    pageNumber.toString());
        }

        public Text headerOnDuty_0_14(Integer pageNumber) {
            return new Text(
                    ViolationsSummaryReportHTMLEnum.HEADER_ON_DUTY_0_14,
                    pageNumber.toString());
        }

        public Text headerOnDuty_15_29(Integer pageNumber) {
            return new Text(
                    ViolationsSummaryReportHTMLEnum.HEADER_ON_DUTY_15_29,
                    pageNumber.toString());
        }

        public Text headerOnDuty_30(Integer pageNumber) {
            return new Text(ViolationsSummaryReportHTMLEnum.HEADER_ON_DUTY_30,
                    pageNumber.toString());
        }

        public Text headerCumulative_0_14(Integer pageNumber) {
            return new Text(
                    ViolationsSummaryReportHTMLEnum.HEADER_CUMULATIVE_0_14,
                    pageNumber.toString());
        }

        public Text headerCumulative_15_29(Integer pageNumber) {
            return new Text(
                    ViolationsSummaryReportHTMLEnum.HEADER_CUMULATIVE_15_29,
                    pageNumber.toString());
        }

        public Text headerCumulative_30(Integer pageNumber) {
            return new Text(
                    ViolationsSummaryReportHTMLEnum.HEADER_CUMULATIVE_30,
                    pageNumber.toString());
        }

        public Text headerOffDuty_0_14(Integer pageNumber) {
            return new Text(
                    ViolationsSummaryReportHTMLEnum.HEADER_OFF_DUTY_0_14,
                    pageNumber.toString());
        }

        public Text headerOffDuty_15_29(Integer pageNumber) {
            return new Text(
                    ViolationsSummaryReportHTMLEnum.HEADER_OFF_DUTY_15_29,
                    pageNumber.toString());
        }

        public Text headerOffDuty_30(Integer pageNumber) {
            return new Text(ViolationsSummaryReportHTMLEnum.HEADER_OFF_DUTY_30,
                    pageNumber.toString());
        }

        public Text headerDrivers(Integer pageNumber) {
            return new Text(ViolationsSummaryReportHTMLEnum.HEADER_DRIVERS,
                    pageNumber.toString());
        }

        public Text labelLocationGroup(Integer pageNumber, int groupRowNumber) {
            return new Text(ViolationsSummaryReportHTMLEnum.LOCATION_LABEL,
                    pageNumber.toString(), 17 + 5 * groupRowNumber);
        }

        public Text entryLocationGroup(Integer pageNumber, int groupRowNumber) {
            return new Text(ViolationsSummaryReportHTMLEnum.LOCATION_VALUE,
                    pageNumber.toString(), 17 + 5 * groupRowNumber);
        }

        public Text entryDriving_0_14(Integer pageNumber, int groupRowNumber) {
            return new Text(ViolationsSummaryReportHTMLEnum.DRIVING_0_14,
                    pageNumber.toString(), 17 + 5 * groupRowNumber);
        }

        public Text entryDriving_15_29(Integer pageNumber, int groupRowNumber) {
            return new Text(ViolationsSummaryReportHTMLEnum.DRIVING_15_29,
                    pageNumber.toString(), 17 + 5 * groupRowNumber);
        }

        public Text entryDriving_30(Integer pageNumber, int groupRowNumber) {
            return new Text(ViolationsSummaryReportHTMLEnum.DRIVING_30,
                    pageNumber.toString(), 17 + 5 * groupRowNumber);
        }

        public Text entryOnDuty_0_14(Integer pageNumber, int groupRowNumber) {
            return new Text(ViolationsSummaryReportHTMLEnum.ON_DUTY_0_14,
                    pageNumber.toString(), 17 + 5 * groupRowNumber);
        }

        public Text entryOnDuty_15_29(Integer pageNumber, int groupRowNumber) {
            return new Text(ViolationsSummaryReportHTMLEnum.ON_DUTY_15_29,
                    pageNumber.toString(), 17 + 5 * groupRowNumber);
        }

        public Text entryOnDuty_30(Integer pageNumber, int groupRowNumber) {
            return new Text(ViolationsSummaryReportHTMLEnum.ON_DUTY_30,
                    pageNumber.toString(), 17 + 5 * groupRowNumber);
        }

        public Text entryCumulative_0_14(Integer pageNumber, int groupRowNumber) {
            return new Text(ViolationsSummaryReportHTMLEnum.CUMULATIVE_0_14,
                    pageNumber.toString(), 17 + 5 * groupRowNumber);
        }

        public Text entryCumulative_15_29(Integer pageNumber, int groupRowNumber) {
            return new Text(ViolationsSummaryReportHTMLEnum.CUMULATIVE_15_29,
                    pageNumber.toString(), 17 + 5 * groupRowNumber);
        }

        public Text entryCumulative_30(Integer pageNumber, int groupRowNumber) {
            return new Text(ViolationsSummaryReportHTMLEnum.CUMULATIVE_30,
                    pageNumber.toString(), 17 + 5 * groupRowNumber);
        }

        public Text entryOffDuty_0_14(Integer pageNumber, int groupRowNumber) {
            return new Text(ViolationsSummaryReportHTMLEnum.OFF_DUTY_0_14,
                    pageNumber.toString(), 17 + 5 * groupRowNumber);
        }

        public Text entryOffDuty_15_29(Integer pageNumber, int groupRowNumber) {
            return new Text(ViolationsSummaryReportHTMLEnum.OFF_DUTY_15_29,
                    pageNumber.toString(), 17 + 5 * groupRowNumber);
        }

        public Text entryOffDuty_30(Integer pageNumber, int groupRowNumber) {
            return new Text(ViolationsSummaryReportHTMLEnum.OFF_DUTY_30,
                    pageNumber.toString(), 17 + 5 * groupRowNumber);
        }

        public Text entryDrivers(Integer pageNumber, int groupRowNumber) {
            return new Text(ViolationsSummaryReportHTMLEnum.DRIVERS,
                    pageNumber.toString(), 17 + 5 * groupRowNumber);
        }

        public Text entryMiles(Integer pageNumber, int groupRowNumber) {
            return new Text(ViolationsSummaryReportHTMLEnum.MILES,
                    pageNumber.toString(), 17 + 5 * groupRowNumber);
        }

        public Text entryZeroMiles(Integer pageNumber, int groupRowNumber) {
            return new Text(ViolationsSummaryReportHTMLEnum.ENTRY_ZERO_MILES,
                    pageNumber.toString(), 17 + 5 * groupRowNumber);
        }

        public Text entryZeroMilesPercent(Integer pageNumber, int groupRowNumber) {
            return new Text(
                    ViolationsSummaryReportHTMLEnum.ENTRY_ZERO_MILES_PERCENT,
                    pageNumber.toString(), 17 + 5 * groupRowNumber);
        }

        public Text entryViolationsPercent(Integer pageNumber,
                int groupRowNumber) {
            return new Text(
                    ViolationsSummaryReportHTMLEnum.ENTRY_VIOLATIONS_PERCENT,
                    pageNumber.toString(), 17 + 5 * groupRowNumber);
        }
    }

    public Text totalsdriving_0_14_Total(Integer pageNumber) {
        return new Text(ViolationsSummaryReportHTMLEnum.DRIVING_0_14_TOTAL,
                pageNumber.toString());
    }

    public Text totalsdriving_12_29_Total(Integer pageNumber) {
        return new Text(ViolationsSummaryReportHTMLEnum.DRIVING_15_29_TOTAL,
                pageNumber.toString());
    }

    public Text totalsdriving30Total(Integer pageNumber) {
        return new Text(ViolationsSummaryReportHTMLEnum.DRIVING_30_TOTAL,
                pageNumber.toString());
    }

    public Text totalsonduty_0_14_Total(Integer pageNumber) {
        return new Text(ViolationsSummaryReportHTMLEnum.ON_DUTY_0_14_TOTAL,
                pageNumber.toString());
    }

    public Text totalsonduty_12_29_Total(Integer pageNumber) {
        return new Text(ViolationsSummaryReportHTMLEnum.ON_DUTY_15_29_TOTAL,
                pageNumber.toString());
    }

    public Text totalsonduty30Total(Integer pageNumber) {
        return new Text(ViolationsSummaryReportHTMLEnum.ON_DUTY_30_TOTAL,
                pageNumber.toString());
    }

    public Text totalscumulative_0_14_Total(Integer pageNumber) {
        return new Text(ViolationsSummaryReportHTMLEnum.CUMULATIVE_0_14_TOTAL,
                pageNumber.toString());
    }

    public Text totalscumulative_12_29_Total(Integer pageNumber) {
        return new Text(ViolationsSummaryReportHTMLEnum.CUMULATIVE_15_29_TOTAL,
                pageNumber.toString());
    }

    public Text totalscumulative30Total(Integer pageNumber) {
        return new Text(ViolationsSummaryReportHTMLEnum.CUMULATIVE_30_TOTAL,
                pageNumber.toString());
    }

    public Text totalsoffduty_0_14_Total(Integer pageNumber) {
        return new Text(ViolationsSummaryReportHTMLEnum.OFF_DUTY_0_14_TOTAL,
                pageNumber.toString());
    }

    public Text totalsoffduty_12_29_Total(Integer pageNumber) {
        return new Text(ViolationsSummaryReportHTMLEnum.OFF_DUTY_15_29_TOTAL,
                pageNumber.toString());
    }

    public Text totalsoffduty30Total(Integer pageNumber) {
        return new Text(ViolationsSummaryReportHTMLEnum.OFF_DUTY_30_TOTAL,
                pageNumber.toString());
    }

    public Text totalsdriversTotal(Integer pageNumber) {
        return new Text(ViolationsSummaryReportHTMLEnum.DRIVERS_TOTAL,
                pageNumber.toString());
    }

    public Text totalsmilesTotal(Integer pageNumber) {
        return new Text(ViolationsSummaryReportHTMLEnum.MILES_TOTAL,
                pageNumber.toString());
    }

    public Text totalsEntryZeroMilesTotal(Integer pageNumber) {
        return new Text(ViolationsSummaryReportHTMLEnum.ENTRY_ZERO_MILES_TOTAL,
                pageNumber.toString());
    }

    public Text totalsViolationsTitle(Integer pageNumber) {
        return new Text(ViolationsSummaryReportHTMLEnum.VIOLATIONS_TITLE,
                pageNumber.toString());
    }

    public Text totalsDrivingViolationTotalLabel(Integer pageNumber) {
        return new Text(
                ViolationsSummaryReportHTMLEnum.DRIVING_VIOLATION_TOTAL_LABEL,
                pageNumber.toString());
    }

    public Text totalsOnDutyViolationTotalLabel(Integer pageNumber) {
        return new Text(
                ViolationsSummaryReportHTMLEnum.ON_DUTY_VIOLATION_TOTAL_LABEL,
                pageNumber.toString());
    }

    public Text totalsCumulativeViolationTotalLabel(Integer pageNumber) {
        return new Text(
                ViolationsSummaryReportHTMLEnum.CUMULATIVE_VIOLATION_TOTAL_LABEL,
                pageNumber.toString());
    }

    public Text totalsOffDutyTotalLabel(Integer pageNumber) {
        return new Text(ViolationsSummaryReportHTMLEnum.OFF_DUTY_TOTAL_LABEL,
                pageNumber.toString());
    }

    public Text totalsDrivingViolationTotalNumber(Integer pageNumber) {
        return new Text(
                ViolationsSummaryReportHTMLEnum.DRIVING_VIOLATION_TOTAL_NUMBER,
                pageNumber.toString());
    }

    public Text totalsOnDutyViolationTotalnumber(Integer pageNumber) {
        return new Text(
                ViolationsSummaryReportHTMLEnum.ON_DUTY_VIOLATION_TOTAL_NUMBER,
                pageNumber.toString());
    }

    public Text totalsCumulativeViolationTotalnumber(Integer pageNumber) {
        return new Text(
                ViolationsSummaryReportHTMLEnum.CUMULATIVE_VIOLATION_TOTAL_NUMBER,
                pageNumber.toString());
    }

    public Text totalsOffDutyTotalnumber(Integer pageNumber) {
        return new Text(ViolationsSummaryReportHTMLEnum.OFF_DUTY_TOTAL_NUMBER,
                pageNumber.toString());
    }

    public Text totalsDrivingViolationtotalPercent(Integer pageNumber) {
        return new Text(
                ViolationsSummaryReportHTMLEnum.DRIVING_VIOLATION_TOTAL_PERCENT,
                pageNumber.toString());
    }

    public Text totalsOnDutyViolationTotalPercent(Integer pageNumber) {
        return new Text(
                ViolationsSummaryReportHTMLEnum.ON_DUTY_VIOLATION_TOTAL_PERCENT,
                pageNumber.toString());
    }

    public Text totalsCumulativeViolationTotalPercent(Integer pageNumber) {
        return new Text(
                ViolationsSummaryReportHTMLEnum.CUMULATIVE_VIOLATION_TOTAL_PERCENT,
                pageNumber.toString());
    }

    public Text totalsOffDutyTotalPercent(Integer pageNumber) {
        return new Text(ViolationsSummaryReportHTMLEnum.OFF_DUTY_TOTAL_PERCENT,
                pageNumber.toString());
    }
}
