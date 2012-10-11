package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.selenium.pageEnums.NonDOTViolationsSummaryReportHTMLEnum;

public class HOSReportsNonDOTViolationsSummaryHTML {
    
    public NonDOTViolationsSummaryReportHTMLTexts _text(){
        return new NonDOTViolationsSummaryReportHTMLTexts();
    }

    public class NonDOTViolationsSummaryReportHTMLTexts {
        public Text title(Integer pageNumber) {
            return new Text(NonDOTViolationsSummaryReportHTMLEnum.TITLE);
        }

        public Text timeframe(Integer pageNumber) {
            return new Text(NonDOTViolationsSummaryReportHTMLEnum.TIME_FRAME);
        }

        public Text valuelabellocation(Integer pageNumber, int rowNumber) {
            return new Text(
                    NonDOTViolationsSummaryReportHTMLEnum.VALUE_LABEL_LOCATION,
                    9 + 5 * rowNumber);
        }

        public Text valueactuallocation(Integer pageNumber, int rowNumber) {
            return new Text(
                    NonDOTViolationsSummaryReportHTMLEnum.VALUE_ACTUAL_LOCATION,
                    9 + 5 * rowNumber);
        }

        public Text labelnondotdriverindotvehicle(Integer pageNumber) {
            return new Text(
                    NonDOTViolationsSummaryReportHTMLEnum.LABEL_NON_DOT_DRIVER_IN_DOT_VEHICLE);
        }

        public Text labeldrivers(Integer pageNumber) {
            return new Text(NonDOTViolationsSummaryReportHTMLEnum.LABEL_DRIVERS);
        }

        public Text labelviolationspercent(Integer pageNumber, int rowNumber) {
            return new Text(
                    NonDOTViolationsSummaryReportHTMLEnum.LABEL_VIOLATIONS_PERCENT,
                    9 + 5 * rowNumber);
        }

        public Text valuenondotdriverindotvehicle(Integer pageNumber,
                int rowNumber) {
            return new Text(
                    NonDOTViolationsSummaryReportHTMLEnum.VALUE_NON_DOT_DRIVER_IN_DOT_VEHICLE,
                    9 + 5 * rowNumber);
        }

        public Text valuedrivers(Integer pageNumber, int rowNumber) {
            return new Text(
                    NonDOTViolationsSummaryReportHTMLEnum.VALUE_DRIVERS,
                    9 + 5 * rowNumber);
        }

        public Text valueviolationspercent(Integer pageNumber, int rowNumber) {
            return new Text(
                    NonDOTViolationsSummaryReportHTMLEnum.VALUE_VIOLATIONS_PERCENT,
                    9 + 5 * rowNumber);
        }
    }

}
