package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.selenium.pageEnums.DriverDOTLogReportHTMLEnum;

public class DriverDOTLogReportHTML {
    
    public DriverDOTLogReportHTMLTexts _text(){
        return new DriverDOTLogReportHTMLTexts();
    }

    public class DriverDOTLogReportHTMLTexts {
        public Text title(Integer pageNumber) {
            return new Text(DriverDOTLogReportHTMLEnum.TITLE,
                    pageNumber.toString());
        }

        public Text timeFrame(Integer pageNumber) {
            return new Text(DriverDOTLogReportHTMLEnum.TIME_FRAME,
                    pageNumber.toString());
        }

        public Text headerDateTime(Integer pageNumber) {
            return new Text(DriverDOTLogReportHTMLEnum.HEADER_DATE_TIME,
                    pageNumber.toString());
        }

        public Text headerStatus(Integer pageNumber) {
            return new Text(DriverDOTLogReportHTMLEnum.HEADER_STATUS,
                    pageNumber.toString());
        }

        public Text headerVehicle(Integer pageNumber) {
            return new Text(DriverDOTLogReportHTMLEnum.HEADER_VEHICLE,
                    pageNumber.toString());
        }

        public Text headerTrailer(Integer pageNumber) {
            return new Text(DriverDOTLogReportHTMLEnum.HEADER_TRAILER,
                    pageNumber.toString());
        }

        public Text headerService(Integer pageNumber) {
            return new Text(DriverDOTLogReportHTMLEnum.HEADER_SERVICE,
                    pageNumber.toString());
        }

        public Text headerLocation(Integer pageNumber) {
            return new Text(DriverDOTLogReportHTMLEnum.HEADER_LOCATION,
                    pageNumber.toString());
        }

        public Text headerDetail(Integer pageNumber) {
            return new Text(DriverDOTLogReportHTMLEnum.HEADER_DETAIL,
                    pageNumber.toString());
        }

        public Text headerAddedBy(Integer pageNumber) {
            return new Text(DriverDOTLogReportHTMLEnum.HEADER_ADDED_BY,
                    pageNumber.toString());
        }

        public Text labelDriver(Integer pageNumber) {
            return new Text(DriverDOTLogReportHTMLEnum.LABEL_DRIVER,
                    pageNumber.toString());
        }

        public Text valueDriver(Integer pageNumber) {
            return new Text(DriverDOTLogReportHTMLEnum.VALUE_DRIVER,
                    pageNumber.toString());
        }

        public TextTable entryDateTime(Integer pageNumber, int rowNumber) {
            return new TextTable(DriverDOTLogReportHTMLEnum.ENTRY_DATE_TIME,
                    pageNumber.toString(), 18 + 2 * rowNumber);
        }

        public TextTable entryStatus(Integer pageNumber, int rowNumber) {
            return new TextTable(DriverDOTLogReportHTMLEnum.ENTRY_STATUS,
                    pageNumber.toString(), 18 + 2 * rowNumber);
        }

        public TextTable entryVehicle(Integer pageNumber, int rowNumber) {
            return new TextTable(DriverDOTLogReportHTMLEnum.ENTRY_VEHICLE,
                    pageNumber.toString(), 18 + 2 * rowNumber);
        }

        public TextTable entryTrailer(Integer pageNumber, int rowNumber) {
            return new TextTable(DriverDOTLogReportHTMLEnum.ENTRY_TRAILER,
                    pageNumber.toString(), 18 + 2 * rowNumber);
        }

        public TextTable entryService(Integer pageNumber, int rowNumber) {
            return new TextTable(DriverDOTLogReportHTMLEnum.ENTRY_SERVICE,
                    pageNumber.toString(), 18 + 2 * rowNumber);
        }

        public TextTable entryLocation(Integer pageNumber, int rowNumber) {
            return new TextTable(DriverDOTLogReportHTMLEnum.ENTRY_LOCATION,
                    pageNumber.toString(), 18 + 2 * rowNumber);
        }

        public TextTable entryDetail(Integer pageNumber, int rowNumber) {
            return new TextTable(DriverDOTLogReportHTMLEnum.ENTRY_DETAIL,
                    pageNumber.toString(), 18 + 2 * rowNumber);
        }

        public TextTable entryAddedBy(Integer pageNumber, int rowNumber) {
            return new TextTable(DriverDOTLogReportHTMLEnum.ENTRY_ADDED_BY,
                    pageNumber.toString(), 18 + 2 * rowNumber);
        }
    }

}
