package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum AdminReportAddEditEnum implements SeleniumEnums {
    DEFAULT_URL(appUrl + "/admin/editReport"),

    DROP_DOWNS(null, "edit-form:editReportSchedule-***"),

    GROUP_DHX(null, "edit_form:editReportSchedule-group"),
    TEXT_FIELDS(null, "edit-form:editReportSchedule-***"),

    NAME(null,          "edit_form:scheduleName"),
    STATUS(null,        "edit_form:editReportScheduleStatus"),
    TIME_OF_DAY(null,   "editReportScheduletimeOfDay-timeOfDayTime"),
    TIME_AM_PM(null,    "editReportScheduletimeOfDay-timeOfDayAfternoon"),
    
    OCCURANCE(null,    "id('reportScheduleForm')/x:div/x:div[2]/x:div/x:table/x:tbody/x:tr/x:td[1]/x:table[2]/x:tbody/x:tr[1]/x:td[2]"),
    DAY_OF_WEEK(null,  "id('reportScheduleForm')/x:div/x:div[2]/x:div/x:table/x:tbody/x:tr/x:td[1]/x:table[2]/x:tbody/x:tr[2]/x:td[2]"),
    DAY_OF_MONTH(null, "id('reportScheduleForm')/x:div/x:div[2]/x:div/x:table/x:tbody/x:tr/x:td[1]/x:table[2]/x:tbody/x:tr[2]/x:td[2]"),
    
    REPORT(null,   "id('reportScheduleForm:reportConfigTable')/x:table/x:tbody/x:tr[1]/x:td[2]"),
    DAYS_REPORTED(null, "reportSchedule_daysDuration", "reportSchedule_daysTimeFrame"),
    GROUP(null,    "reportSchedule_group"),
    LIST(null, "reportSchedule_list"),
    DRIVER(null,    "reportSchedule_driverName", "reportSchedule_vehicleName"),
    IFTA(null, "reportSchedule_iftaOnly"),
    OWNER(null, "reportSchedule_ownerName"),   
    EMAIL(null, "id('reportScheduleForm')/x:div/x:div[2]/x:div/x:table/x:tbody/x:tr/x:td[3]/x:table/x:tbody/x:tr/x:td"),
    
    CANCEL("Cancel", "edit-form:editReportScheduleCancel***"),
    SAVE(save, "edit-form:editReportScheduleSave***"),    
    ;

    private String text, url;
    private String[] IDs;

    private AdminReportAddEditEnum(String url) {
	this.url = url;
    }

    private AdminReportAddEditEnum(String text, String... IDs) {
	this.text = text;
	this.IDs = IDs;
    }

    @Override
    public String[] getIDs() {
	return IDs;
    }

    @Override
    public String getText() {
	return text;
    }

    @Override
    public String getURL() {
	return url;
    }
}
