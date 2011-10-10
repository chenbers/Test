package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.SortHeader;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.selenium.pageEnums.DriverDOTLogReportTableEnum;

public class HOSReportsDriverTODLogReportTable {

    public class DriverDOTLogReportTableTexts {
        public TextTable entryDateTime() {
            return new TextTable(DriverDOTLogReportTableEnum.DATE_TIME);
        }

        public TextTable entryDriver() {
            return new TextTable(DriverDOTLogReportTableEnum.DRIVER);
        }

        public TextTable entryVehicle() {
            return new TextTable(DriverDOTLogReportTableEnum.VEHICLE);
        }

        public TextTable entryTrailer() {
            return new TextTable(DriverDOTLogReportTableEnum.TRAILER);
        }

        public TextTable entryService() {
            return new TextTable(DriverDOTLogReportTableEnum.SERVICE);
        }

        public TextTable entryLocation() {
            return new TextTable(DriverDOTLogReportTableEnum.LOCATION);
        }

        public TextTable entryDetail() {
            return new TextTable(DriverDOTLogReportTableEnum.DETAIL);
        }

        public TextTable entryAddedBy() {
            return new TextTable(DriverDOTLogReportTableEnum.ADDED_BY);
        }
    }

    public class DriverDOTLogReportTableLinks {
        public SortHeader sortDateTime() {
            return new SortHeader(DriverDOTLogReportTableEnum.DATE_TIME);
        }

        public SortHeader sortDriver() {
            return new SortHeader(DriverDOTLogReportTableEnum.DRIVER);
        }

        public SortHeader sortVehicle() {
            return new SortHeader(DriverDOTLogReportTableEnum.VEHICLE);
        }

        public SortHeader sortTrailer() {
            return new SortHeader(DriverDOTLogReportTableEnum.TRAILER);
        }

        public SortHeader sortService() {
            return new SortHeader(DriverDOTLogReportTableEnum.SERVICE);
        }

        public SortHeader sortLocation() {
            return new SortHeader(DriverDOTLogReportTableEnum.LOCATION);
        }

        public SortHeader sortDetail() {
            return new SortHeader(DriverDOTLogReportTableEnum.DETAIL);
        }

        public SortHeader sortAddedBy() {
            return new SortHeader(DriverDOTLogReportTableEnum.ADDED_BY);
        }
    }

}
