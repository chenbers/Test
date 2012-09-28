package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.selenium.pageEnums.HOSRecordOfDutyStatusEnum;

public class HOSRecordOfDutyStatus {
    
    public RecordOfDutyStatusTexts _text(){
        return new RecordOfDutyStatusTexts();
    }
    
    public class RecordOfDutyStatusTexts {
        public Text timeStamp(Integer reportPage) {
            return new Text(HOSRecordOfDutyStatusEnum.TIME_STAMP, reportPage.toString());
        }
    
        public Text title(Integer reportPage) {
            return new Text(HOSRecordOfDutyStatusEnum.TITLE, reportPage.toString());
        }
    
        public Text subTitle(Integer reportPage) {
            return new Text(HOSRecordOfDutyStatusEnum.SUB_TITLE, reportPage.toString());
        }
    
        public Text labelDate(Integer reportPage) {
            return new Text(HOSRecordOfDutyStatusEnum.LABEL_DATE, reportPage.toString());
        }
    
        public Text valueDate(Integer reportPage) {
            return new Text(HOSRecordOfDutyStatusEnum.VALUE_DATE, reportPage.toString());
        }
    
        public Text recapTitle(Integer reportPage) {
            return new Text(HOSRecordOfDutyStatusEnum.RECAP_RECAP, reportPage.toString());
        }
    
        public Text recapValueDayNumber(Integer reportPage) {
            return new Text(HOSRecordOfDutyStatusEnum.RECAP_VALUE_DAY_NO,
                    reportPage.toString());
        }
    
        public Text recapLabelDayNumber(Integer reportPage) {
            return new Text(HOSRecordOfDutyStatusEnum.RECAP_LABEL_DAY_NO,
                    reportPage.toString());
        }
    
        public Text recapRuleSet(Integer reportPage) {
            return new Text(HOSRecordOfDutyStatusEnum.RECAP_DRIVER_TYPE, reportPage.toString());
        }
    
        public Text recapValueHrsAvailabelToday(Integer reportPage) {
            return new Text(HOSRecordOfDutyStatusEnum.RECAP_VALUE_HRS_AVAIL_TODAY,
                    reportPage.toString());
        }
    
        public Text recapLabelHrsAvailableToday(Integer reportPage) {
            return new Text(HOSRecordOfDutyStatusEnum.RECAP_LABEL_HRS_AVAIL_TODAY,
                    reportPage.toString());
        }
    
        public Text recapValueHrsWorkedToday(Integer reportPage) {
            return new Text(HOSRecordOfDutyStatusEnum.RECAP_VALUE_HRS_WORKED_TODAY,
                    reportPage.toString());
        }
    
        public Text recapLabelHrsWorkedToday(Integer reportPage) {
            return new Text(HOSRecordOfDutyStatusEnum.RECAP_LABEL_HRS_WORKED_TODAY,
                    reportPage.toString());
        }
    
        public Text recapValueTotalHrs7Days(Integer reportPage) {
            return new Text(HOSRecordOfDutyStatusEnum.RECAP_VALUE_TOTAL_HRS_7DAYS,
                    reportPage.toString());
        }
    
        public Text recapLabelTotalHrs7Days(Integer reportPage) {
            return new Text(HOSRecordOfDutyStatusEnum.RECAP_LABEL_TOTAL_HRS_7DAYS,
                    reportPage.toString());
        }
    
        public Text recapValueTotalHrs8Days(Integer reportPage) {
            return new Text(HOSRecordOfDutyStatusEnum.RECAP_VALUE_TOTAL_HRS_8DAYS,
                    reportPage.toString());
        }
    
        public Text recapLabelTotalHrs8Days(Integer reportPage) {
            return new Text(HOSRecordOfDutyStatusEnum.RECAP_LABEL_TOTAL_HRS_8DAYS,
                    reportPage.toString());
        }
    
        public Text recapValueHrsAvailableTomorrow(Integer reportPage) {
            return new Text(
                    HOSRecordOfDutyStatusEnum.RECAP_VALUE_HRS_AVAIL_TOMORROW,
                    reportPage.toString());
        }
    
        public Text recapLabelHrsAvailableTomorrow(Integer reportPage) {
            return new Text(
                    HOSRecordOfDutyStatusEnum.RECAP_LABEL_HRS_AVAIL_TOMORROW,
                    reportPage.toString());
        }
    
        public Text valueStartOdometer(Integer reportPage) {
            return new Text(HOSRecordOfDutyStatusEnum.VALUE_START_ODOMETER,
                    reportPage.toString());
        }
    
        public Text valueDistanceDrivenToday(Integer reportPage) {
            return new Text(
                    HOSRecordOfDutyStatusEnum.VALUE_VEHICLES_DISTANCE_DRIVEN_TODAY,
                    reportPage.toString());
        }
    
        public Text valueRuleSet(Integer reportPage) {
            return new Text(HOSRecordOfDutyStatusEnum.VALUE_RULESET, reportPage.toString());
        }
    
        public Text labelStartOdometer(Integer reportPage) {
            return new Text(HOSRecordOfDutyStatusEnum.LABEL_START_ODOMETER,
                    reportPage.toString());
        }
    
        public Text labelVehiclesDistanceDrivenToday(Integer reportPage) {
            return new Text(
                    HOSRecordOfDutyStatusEnum.LABEL_VEHICLES_DISTANCE_DRIVEN_TODAY,
                    reportPage.toString());
        }
    
        public Text labelRuleset(Integer reportPage) {
            return new Text(HOSRecordOfDutyStatusEnum.LABEL_RULESET, reportPage.toString());
        }
    
        public Text valueDriverName(Integer reportPage) {
            return new Text(HOSRecordOfDutyStatusEnum.VALUE_DRIVER_NAME, reportPage.toString());
        }
    
        public Text valueMilesDrivenToday(Integer reportPage) {
            return new Text(HOSRecordOfDutyStatusEnum.VALUE_MILES_DRIVEN_TODAY,
                    reportPage.toString());
        }
    
        public Text labelDriverName(Integer reportPage) {
            return new Text(HOSRecordOfDutyStatusEnum.LABEL_DRIVER_NAME, reportPage.toString());
        }
    
        public Text labelMilesDrivenToday(Integer reportPage) {
            return new Text(HOSRecordOfDutyStatusEnum.LABEL_MILES_DRIVEN_TODAY,
                    reportPage.toString());
        }
    
        public Text valueCarrrier(Integer reportPage) {
            return new Text(HOSRecordOfDutyStatusEnum.VALUE_CARRIER, reportPage.toString());
        }
    
        public Text valueNameOfCoDriver(Integer reportPage) {
            return new Text(HOSRecordOfDutyStatusEnum.VALUE_NAME_OF_CO_DRIVER,
                    reportPage.toString());
        }
    
        public Text labelCarrier(Integer reportPage) {
            return new Text(HOSRecordOfDutyStatusEnum.LABEL_CARRIER, reportPage.toString());
        }
    
        public Text labelNameOfCoDriver(Integer reportPage) {
            return new Text(HOSRecordOfDutyStatusEnum.LABEL_NAME_OF_CO_DRIVER,
                    reportPage.toString());
        }
    
        public Text valueMainOffice(Integer reportPage) {
            return new Text(HOSRecordOfDutyStatusEnum.VALUE_MAIN_OFFICE, reportPage.toString());
        }
    
        public Text valueHomeTerminal(Integer reportPage) {
            return new Text(HOSRecordOfDutyStatusEnum.VALUE_HOME_TERMINAL,
                    reportPage.toString());
        }
    
        public Text labelMainOffice(Integer reportPage) {
            return new Text(HOSRecordOfDutyStatusEnum.LABEL_MAIN_OFFICE, reportPage.toString());
        }
    
        public Text labelHomeTerminal(Integer reportPage) {
            return new Text(HOSRecordOfDutyStatusEnum.LABEL_HOME_TERMINAL,
                    reportPage.toString());
        }
    
        public Text driverSignature(Integer reportPage) {
            return new Text(HOSRecordOfDutyStatusEnum.DRIVER_SIGNATURE, reportPage.toString());
        }
    
        public Text driverSignatureNewPage(Integer reportPage) {
            return new Text(HOSRecordOfDutyStatusEnum.DRIVER_SIGNATURE_NEW_PAGE,
                    reportPage.toString());
        }
    
        public Text labelRemarks(Integer reportPage) {
            return new Text(HOSRecordOfDutyStatusEnum.LABEL_REMARKS, reportPage.toString());
        }
    
        public Text labelRemarksNewPage(Integer reportPage) {
            return new Text(HOSRecordOfDutyStatusEnum.LABEL_REMARKS_NEW_PAGE,
                    reportPage.toString());
        }
    
        public Text remarksDateTimeNewPage(Integer reportPage) {
            return new Text(HOSRecordOfDutyStatusEnum.REMARKS_DATE_TIME_NEW_PAGE,
                    reportPage.toString());
        }
    
        public TextTable remarksCityNewPage(Integer reportPage) {
            return new TextTable(HOSRecordOfDutyStatusEnum.REMARKS_CITY_NEW_PAGE,
                    reportPage.toString());
        }
    
        public TextTable remarksHosStatusNewPage(Integer reportPage) {
            return new TextTable(
                    HOSRecordOfDutyStatusEnum.REMARKS_HOS_STATUS_NEW_PAGE,
                    reportPage.toString());
        }
    
        public TextTable remarksOdometerNewPage(Integer reportPage) {
            return new TextTable(
                    HOSRecordOfDutyStatusEnum.REMARKS_ODOMETER_NEW_PAGE,
                    reportPage.toString());
        }
    
        public TextTable remarksEditedByNewPage(Integer reportPage) {
            return new TextTable(
                    HOSRecordOfDutyStatusEnum.REMARKS_EDITED_BY_NEW_PAGE,
                    reportPage.toString());
        }
    
        public TextTable remarksDateTime(Integer reportPage) {
            return new TextTable(HOSRecordOfDutyStatusEnum.REMARKS_DATE_TIME,
                    reportPage.toString());
        }
    
        public TextTable remarksCity(Integer reportPage) {
            return new TextTable(HOSRecordOfDutyStatusEnum.REMARKS_CITY,
                    reportPage.toString());
        }
    
        public TextTable remarksHosStatus(Integer reportPage) {
            return new TextTable(HOSRecordOfDutyStatusEnum.REMARKS_HOS_STATUS,
                    reportPage.toString());
        }
    
        public TextTable remarksOdometer(Integer reportPage) {
            return new TextTable(HOSRecordOfDutyStatusEnum.REMARKS_ODOMETER,
                    reportPage.toString());
        }
    
        public TextTable remarksEditedBy(Integer reportPage) {
            return new TextTable(HOSRecordOfDutyStatusEnum.REMARKS_EDITED_BY,
                    reportPage.toString());
        }
    }
}
