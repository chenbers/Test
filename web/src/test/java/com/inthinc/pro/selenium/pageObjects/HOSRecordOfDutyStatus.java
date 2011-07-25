package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.selenium.pageEnums.HOSRecordOfDutyStatusEnum;

public class HOSRecordOfDutyStatus {
    public Text timeStamp(Integer reportPage) {
        return new Text(HOSRecordOfDutyStatusEnum.TIME_STAMP, reportPage);
    }

    public Text title(Integer reportPage) {
        return new Text(HOSRecordOfDutyStatusEnum.TITLE, reportPage);
    }

    public Text subTitle(Integer reportPage) {
        return new Text(HOSRecordOfDutyStatusEnum.SUB_TITLE, reportPage);
    }

    public Text labelDate(Integer reportPage) {
        return new Text(HOSRecordOfDutyStatusEnum.LABEL_DATE, reportPage);
    }

    public Text valueDate(Integer reportPage) {
        return new Text(HOSRecordOfDutyStatusEnum.VALUE_DATE, reportPage);
    }

    public Text recapTitle(Integer reportPage) {
        return new Text(HOSRecordOfDutyStatusEnum.RECAP_RECAP, reportPage);
    }

    public Text recapValueDayNumber(Integer reportPage) {
        return new Text(HOSRecordOfDutyStatusEnum.RECAP_VALUE_DAY_NO,
                reportPage);
    }

    public Text recapLabelDayNumber(Integer reportPage) {
        return new Text(HOSRecordOfDutyStatusEnum.RECAP_LABEL_DAY_NO,
                reportPage);
    }

    public Text recapRuleSet(Integer reportPage) {
        return new Text(HOSRecordOfDutyStatusEnum.RECAP_DRIVER_TYPE, reportPage);
    }

    public Text recapValueHrsAvailabelToday(Integer reportPage) {
        return new Text(HOSRecordOfDutyStatusEnum.RECAP_VALUE_HRS_AVAIL_TODAY,
                reportPage);
    }

    public Text recapLabelHrsAvailableToday(Integer reportPage) {
        return new Text(HOSRecordOfDutyStatusEnum.RECAP_LABEL_HRS_AVAIL_TODAY,
                reportPage);
    }

    public Text recapValueHrsWorkedToday(Integer reportPage) {
        return new Text(HOSRecordOfDutyStatusEnum.RECAP_VALUE_HRS_WORKED_TODAY,
                reportPage);
    }

    public Text recapLabelHrsWorkedToday(Integer reportPage) {
        return new Text(HOSRecordOfDutyStatusEnum.RECAP_LABEL_HRS_WORKED_TODAY,
                reportPage);
    }

    public Text recapValueTotalHrs7Days(Integer reportPage) {
        return new Text(HOSRecordOfDutyStatusEnum.RECAP_VALUE_TOTAL_HRS_7DAYS,
                reportPage);
    }

    public Text recapLabelTotalHrs7Days(Integer reportPage) {
        return new Text(HOSRecordOfDutyStatusEnum.RECAP_LABEL_TOTAL_HRS_7DAYS,
                reportPage);
    }

    public Text recapValueTotalHrs8Days(Integer reportPage) {
        return new Text(HOSRecordOfDutyStatusEnum.RECAP_VALUE_TOTAL_HRS_8DAYS,
                reportPage);
    }

    public Text recapLabelTotalHrs8Days(Integer reportPage) {
        return new Text(HOSRecordOfDutyStatusEnum.RECAP_LABEL_TOTAL_HRS_8DAYS,
                reportPage);
    }

    public Text recapValueHrsAvailableTomorrow(Integer reportPage) {
        return new Text(
                HOSRecordOfDutyStatusEnum.RECAP_VALUE_HRS_AVAIL_TOMORROW,
                reportPage);
    }

    public Text recapLabelHrsAvailableTomorrow(Integer reportPage) {
        return new Text(
                HOSRecordOfDutyStatusEnum.RECAP_LABEL_HRS_AVAIL_TOMORROW,
                reportPage);
    }

    public Text valueStartOdometer(Integer reportPage) {
        return new Text(HOSRecordOfDutyStatusEnum.VALUE_START_ODOMETER,
                reportPage);
    }

    public Text valueDistanceDrivenToday(Integer reportPage) {
        return new Text(
                HOSRecordOfDutyStatusEnum.VALUE_VEHICLES_DISTANCE_DRIVEN_TODAY,
                reportPage);
    }

    public Text valueRuleSet(Integer reportPage) {
        return new Text(HOSRecordOfDutyStatusEnum.VALUE_RULESET, reportPage);
    }

    public Text labelStartOdometer(Integer reportPage) {
        return new Text(HOSRecordOfDutyStatusEnum.LABEL_START_ODOMETER,
                reportPage);
    }

    public Text labelVehiclesDistanceDrivenToday(Integer reportPage) {
        return new Text(
                HOSRecordOfDutyStatusEnum.LABEL_VEHICLES_DISTANCE_DRIVEN_TODAY,
                reportPage);
    }

    public Text labelRuleset(Integer reportPage) {
        return new Text(HOSRecordOfDutyStatusEnum.LABEL_RULESET, reportPage);
    }

    public Text valueDriverName(Integer reportPage) {
        return new Text(HOSRecordOfDutyStatusEnum.VALUE_DRIVER_NAME, reportPage);
    }

    public Text valueMilesDrivenToday(Integer reportPage) {
        return new Text(HOSRecordOfDutyStatusEnum.VALUE_MILES_DRIVEN_TODAY,
                reportPage);
    }

    public Text labelDriverName(Integer reportPage) {
        return new Text(HOSRecordOfDutyStatusEnum.LABEL_DRIVER_NAME, reportPage);
    }

    public Text labelMilesDrivenToday(Integer reportPage) {
        return new Text(HOSRecordOfDutyStatusEnum.LABEL_MILES_DRIVEN_TODAY,
                reportPage);
    }

    public Text valueCarrrier(Integer reportPage) {
        return new Text(HOSRecordOfDutyStatusEnum.VALUE_CARRIER, reportPage);
    }

    public Text valueNameOfCoDriver(Integer reportPage) {
        return new Text(HOSRecordOfDutyStatusEnum.VALUE_NAME_OF_CO_DRIVER,
                reportPage);
    }

    public Text labelCarrier(Integer reportPage) {
        return new Text(HOSRecordOfDutyStatusEnum.LABEL_CARRIER, reportPage);
    }

    public Text labelNameOfCoDriver(Integer reportPage) {
        return new Text(HOSRecordOfDutyStatusEnum.LABEL_NAME_OF_CO_DRIVER,
                reportPage);
    }

    public Text valueMainOffice(Integer reportPage) {
        return new Text(HOSRecordOfDutyStatusEnum.VALUE_MAIN_OFFICE, reportPage);
    }

    public Text valueHomeTerminal(Integer reportPage) {
        return new Text(HOSRecordOfDutyStatusEnum.VALUE_HOME_TERMINAL,
                reportPage);
    }

    public Text labelMainOffice(Integer reportPage) {
        return new Text(HOSRecordOfDutyStatusEnum.LABEL_MAIN_OFFICE, reportPage);
    }

    public Text labelHomeTerminal(Integer reportPage) {
        return new Text(HOSRecordOfDutyStatusEnum.LABEL_HOME_TERMINAL,
                reportPage);
    }

    public Text driverSignature(Integer reportPage) {
        return new Text(HOSRecordOfDutyStatusEnum.DRIVER_SIGNATURE, reportPage);
    }

    public Text driverSignatureNewPage(Integer reportPage) {
        return new Text(HOSRecordOfDutyStatusEnum.DRIVER_SIGNATURE_NEW_PAGE,
                reportPage);
    }

    public Text labelRemarks(Integer reportPage) {
        return new Text(HOSRecordOfDutyStatusEnum.LABEL_REMARKS, reportPage);
    }

    public Text labelRemarksNewPage(Integer reportPage) {
        return new Text(HOSRecordOfDutyStatusEnum.LABEL_REMARKS_NEW_PAGE,
                reportPage);
    }

    public Text remarksDateTimeNewPage(Integer reportPage) {
        return new Text(HOSRecordOfDutyStatusEnum.REMARKS_DATE_TIME_NEW_PAGE,
                reportPage);
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
