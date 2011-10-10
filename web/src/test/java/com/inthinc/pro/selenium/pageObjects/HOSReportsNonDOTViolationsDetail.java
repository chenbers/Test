package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.selenium.pageEnums.NonDOTViolationsDetailReportEnum;

public class HOSReportsNonDOTViolationsDetail {
    
    public NonDOTViolationsDetailReportText _text(){
        return new NonDOTViolationsDetailReportText();
    }

    public class NonDOTViolationsDetailReportText {
        public Text title(Integer pageNumber) {
            return new Text(NonDOTViolationsDetailReportEnum.TITLE,
                    pageNumber.toString());
        }

        public Text timeFrame(Integer pageNumber) {
            return new Text(NonDOTViolationsDetailReportEnum.TIME_FRAME,
                    pageNumber.toString());
        }

        public Text headerLocation(Integer pageNumber) {
            return new Text(NonDOTViolationsDetailReportEnum.HEADER_LOCATION,
                    pageNumber.toString());
        }

        public Text headerDateTime(Integer pageNumber) {
            return new Text(NonDOTViolationsDetailReportEnum.HEADER_DATE_TIME,
                    pageNumber.toString());
        }

        public Text headerEmployee(Integer pageNumber) {
            return new Text(NonDOTViolationsDetailReportEnum.HEADER_EMPLOYEE,
                    pageNumber.toString());
        }

        public Text headerDriver(Integer pageNumber) {
            return new Text(NonDOTViolationsDetailReportEnum.HEADER_DRIVER,
                    pageNumber.toString());
        }

        public Text headerVehicle(Integer pageNumber) {
            return new Text(NonDOTViolationsDetailReportEnum.HEADER_VEHICLE,
                    pageNumber.toString());
        }

        public Text headerViolation(Integer pageNumber) {
            return new Text(NonDOTViolationsDetailReportEnum.HEADER_VIOLATION,
                    pageNumber.toString());
        }

        public Text headerLength(Integer pageNumber) {
            return new Text(NonDOTViolationsDetailReportEnum.HEADER_LENGTH,
                    pageNumber.toString());
        }

        public Text headerRule(Integer pageNumber) {
            return new Text(NonDOTViolationsDetailReportEnum.HEADER_RULE,
                    pageNumber.toString());
        }

        public Text valueLocation(Integer pageNumber, int rowNumber) {
            return new Text(NonDOTViolationsDetailReportEnum.VALUE_LOCATION,
                    pageNumber.toString(), 13 + 3 * rowNumber);
        }

        public Text valueDateTime(Integer pageNumber, int rowNumber) {
            return new Text(NonDOTViolationsDetailReportEnum.VALUE_DATE_TIME,
                    pageNumber.toString(), 13 + 3 * rowNumber);
        }

        public Text valueEmployee(Integer pageNumber, int rowNumber) {
            return new Text(NonDOTViolationsDetailReportEnum.VALUE_EMPLOYEE,
                    pageNumber.toString(), 13 + 3 * rowNumber);
        }

        public Text valueDriver(Integer pageNumber, int rowNumber) {
            return new Text(NonDOTViolationsDetailReportEnum.VALUE_DRIVER,
                    pageNumber.toString(), 13 + 3 * rowNumber);
        }

        public Text valueVehicle(Integer pageNumber, int rowNumber) {
            return new Text(NonDOTViolationsDetailReportEnum.VALUE_VEHICLE,
                    pageNumber.toString(), 13 + 3 * rowNumber);
        }

        public Text valueViolation(Integer pageNumber, int rowNumber) {
            return new Text(NonDOTViolationsDetailReportEnum.VALUE_VIOLATION,
                    pageNumber.toString(), 13 + 3 * rowNumber);
        }

        public Text valueLength(Integer pageNumber, int rowNumber) {
            return new Text(NonDOTViolationsDetailReportEnum.VALUE_LENGTH,
                    pageNumber.toString(), 13 + 3 * rowNumber);
        }

        public Text valueRule(Integer pageNumber, int rowNumber) {
            return new Text(NonDOTViolationsDetailReportEnum.VALUE_RULE,
                    pageNumber.toString(), 13 + 3 * rowNumber);
        }
    }
}
