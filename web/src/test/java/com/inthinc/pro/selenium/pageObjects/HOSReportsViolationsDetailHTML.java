package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.selenium.pageEnums.ViolationsDetailReportHTMLEnum;

public class HOSReportsViolationsDetailHTML {

    public class ViolationsDetailReportHTMLTexts {
        
        public Text headerLocation(Integer pageNumber) {
            return new Text(ViolationsDetailReportHTMLEnum.HEADER_LOCATION,
                    pageNumber.toString());
        }

        public Text headerDateTime(Integer pageNumber) {
            return new Text(ViolationsDetailReportHTMLEnum.HEADER_DATE_TIME,
                    pageNumber.toString());
        }

        public Text headerEmployeeID(Integer pageNumber) {
            return new Text(ViolationsDetailReportHTMLEnum.HEADER_EMPLOYEE_ID,
                    pageNumber.toString());
        }

        public Text headerDriver(Integer pageNumber) {
            return new Text(ViolationsDetailReportHTMLEnum.HEADER_DRIVER,
                    pageNumber.toString());
        }

        public Text headerVehicle(Integer pageNumber) {
            return new Text(ViolationsDetailReportHTMLEnum.HEADER_VEHICLE,
                    pageNumber.toString());
        }

        public Text headerViolation(Integer pageNumber) {
            return new Text(ViolationsDetailReportHTMLEnum.HEADER_VIOLATION,
                    pageNumber.toString());
        }

        public Text headerCFR(Integer pageNumber) {
            return new Text(ViolationsDetailReportHTMLEnum.HEADER_CFR,
                    pageNumber.toString());
        }

        public Text headerLength(Integer pageNumber) {
            return new Text(ViolationsDetailReportHTMLEnum.HEADER_LENGTH,
                    pageNumber.toString());
        }

        public Text headerRule(Integer pageNumber) {
            return new Text(ViolationsDetailReportHTMLEnum.HEADER_RULE,
                    pageNumber.toString());
        }

        public Text entryLocation(Integer pageNumber, int rowNumber) {
            return new Text(ViolationsDetailReportHTMLEnum.ENTRY_LOCATION,
                    pageNumber.toString(), 11 + 5 * rowNumber);
        }

        public Text entryDateTime(Integer pageNumber, int rowNumber) {
            return new Text(ViolationsDetailReportHTMLEnum.ENTRY_DATE_TIME,
                    pageNumber.toString(), 11 + 5 * rowNumber);
        }

        public Text entryEmployeeID(Integer pageNumber, int rowNumber) {
            return new Text(ViolationsDetailReportHTMLEnum.ENTRY_EMPLOYEE_ID,
                    pageNumber.toString(), 11 + 5 * rowNumber);
        }

        public Text entryDriver(Integer pageNumber, int rowNumber) {
            return new Text(ViolationsDetailReportHTMLEnum.ENTRY_DRIVER,
                    pageNumber.toString(), 11 + 5 * rowNumber);
        }

        public Text entryVehicle(Integer pageNumber, int rowNumber) {
            return new Text(ViolationsDetailReportHTMLEnum.ENTRY_VEHICLE,
                    pageNumber.toString(), 11 + 5 * rowNumber);
        }

        public Text entryViolation(Integer pageNumber, int rowNumber) {
            return new Text(ViolationsDetailReportHTMLEnum.ENTRY_VIOLATION,
                    pageNumber.toString(), 11 + 5 * rowNumber);
        }

        public Text entryCFR(Integer pageNumber, int rowNumber) {
            return new Text(ViolationsDetailReportHTMLEnum.ENTRY_CFR,
                    pageNumber.toString(), 11 + 5 * rowNumber);
        }

        public Text entryLength(Integer pageNumber, int rowNumber) {
            return new Text(ViolationsDetailReportHTMLEnum.ENTRY_LENGTH,
                    pageNumber.toString(), 11 + 5 * rowNumber);
        }

        public Text entryRule(Integer pageNumber, int rowNumber) {
            return new Text(ViolationsDetailReportHTMLEnum.ENTRY_RULE,
                    pageNumber.toString(), 11 + 5 * rowNumber);
        }

        public Text title(Integer pageNumber) {
            return new Text(ViolationsDetailReportHTMLEnum.TITLE, pageNumber.toString());
        }

        public Text dateTime(Integer pageNumber) {
            return new Text(ViolationsDetailReportHTMLEnum.DATE_TIME,
                    pageNumber.toString());
        }

    }
}
