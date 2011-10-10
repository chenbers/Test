package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.SortHeader;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.selenium.pageEnums.ViolationsDetailReportTableEnum;

public class HOSReportsViolationsDetailTable {

    public class ViolationsDetailReportTableTexts {
        public TextTable entryLocation() {
            return new TextTable(ViolationsDetailReportTableEnum.LOCATION);
        }

        public TextTable entryDateTime() {
            return new TextTable(ViolationsDetailReportTableEnum.DATE_TIME);
        }

        public TextTable entryDriver() {
            return new TextTable(ViolationsDetailReportTableEnum.DRIVER);
        }

        public TextTable entryVehicle() {
            return new TextTable(ViolationsDetailReportTableEnum.VEHICLE);
        }

        public TextTable entryViolation() {
            return new TextTable(ViolationsDetailReportTableEnum.VIOLATION);
        }

        public TextTable entryCFR() {
            return new TextTable(ViolationsDetailReportTableEnum.CFR);
        }

        public TextTable entryLength() {
            return new TextTable(ViolationsDetailReportTableEnum.LENGTH);
        }

        public TextTable entryRule() {
            return new TextTable(ViolationsDetailReportTableEnum.RULE);
        }
    }

    public class ViolationsDetailReportTableLinks {
        public SortHeader sortByLocation() {
            return new SortHeader(ViolationsDetailReportTableEnum.LOCATION);
        }

        public SortHeader sortByDateTime() {
            return new SortHeader(ViolationsDetailReportTableEnum.DATE_TIME);
        }

        public SortHeader sortByDriver() {
            return new SortHeader(ViolationsDetailReportTableEnum.DRIVER);
        }

        public SortHeader sortByVehicle() {
            return new SortHeader(ViolationsDetailReportTableEnum.VEHICLE);
        }

        public SortHeader sortByViolation() {
            return new SortHeader(ViolationsDetailReportTableEnum.VIOLATION);
        }

        public SortHeader sortByCFR() {
            return new SortHeader(ViolationsDetailReportTableEnum.CFR);
        }

        public SortHeader sortByLength() {
            return new SortHeader(ViolationsDetailReportTableEnum.LENGTH);
        }

        public SortHeader sortByRule() {
            return new SortHeader(ViolationsDetailReportTableEnum.RULE);
        }
    }
    
    public ViolationsDetailReportTableLinks _link(){
        return new ViolationsDetailReportTableLinks();
    }
    
    public ViolationsDetailReportTableTexts _text(){
        return new ViolationsDetailReportTableTexts();
    }

}
