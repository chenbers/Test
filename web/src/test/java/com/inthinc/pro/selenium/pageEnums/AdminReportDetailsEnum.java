package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum AdminReportDetailsEnum implements SeleniumEnums {
	
	NAME_STATUS_TIME(null, "id('reportScheduleForm')/x:div/x:div[2]/x:div/x:table/x:tbody/x:tr/x:td[1]/div[2]"),
	NAME(null,             "id('reportScheduleForm')/x:div/x:div[2]/x:div/x:table/x:tbody/x:tr/x:td[1]/x:table[1]/x:tbody/x:tr[1]/x:td[2]"),
	STATUS(null,           "id('reportScheduleForm')/x:div/x:div[2]/x:div/x:table/x:tbody/x:tr/x:td[1]/x:table[1]/x:tbody/x:tr[2]/x:td[2]"),
	TIME_OF_DAY(null,      "id('reportScheduleForm')/x:div/x:div[2]/x:div/x:table/x:tbody/x:tr/x:td[1]/x:table[1]/x:tbody/x:tr[3]/x:td[2]"),
    
	REPORT_OCCURANCE(null, "id('reportScheduleForm')/x:div/x:div[2]/x:div/x:table/x:tbody/x:tr/x:td[1]/x:div[4]"),
	OCCURANCE(null,    "id('reportScheduleForm')/x:div/x:div[2]/x:div/x:table/x:tbody/x:tr/x:td[1]/x:table[2]/x:tbody/x:tr[1]/x:td[2]"),
	DAY_OF_WEEK(null,  "id('reportScheduleForm')/x:div/x:div[2]/x:div/x:table/x:tbody/x:tr/x:td[1]/x:table[2]/x:tbody/x:tr[2]/x:td[2]"),
	DAY_OF_MONTH(null, "id('reportScheduleForm')/x:div/x:div[2]/x:div/x:table/x:tbody/x:tr/x:td[1]/x:table[2]/x:tbody/x:tr[2]/x:td[2]"),
	
	
	REPORT_SETTINGS(null, "id('reportScheduleForm')/x:div/x:div[2]/x:div/x:table/x:tbody/x:tr/x:td[3]/x:div[2]"),
	REPORT(null,   "id('reportScheduleForm:reportConfigTable')/x:table/x:tbody/x:tr[1]/x:td[2]"),
	DAYS_REPORTED(null, "reportSchedule_daysDuration", "reportSchedule_daysTimeFrame"),
	GROUP(null,    "reportSchedule_group"),
	LIST(null, "reportSchedule_list"),
	DRIVER(null,    "reportSchedule_driverName", "reportSchedule_vehicleName"),
	IFTA(null, "reportSchedule_iftaOnly"),
	OWNER(null, "reportSchedule_ownerName"),


	   
	EMAIL_SECTION(null, "id('reportScheduleForm')/x:div/x:div[2]/x:div/x:table/x:tbody/x:tr/x:td[3]/x:div[4]"),
	EMAIL(null, "id('reportScheduleForm')/x:div/x:div[2]/x:div/x:table/x:tbody/x:tr/x:td[3]/x:table/x:tbody/x:tr/x:td"),
	;

    private String text, url;
    private String[] IDs;
    
    private AdminReportDetailsEnum(String url){
    	this.url = url;
    }
    private AdminReportDetailsEnum(String text, String ...IDs){
        this.text=text;
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
