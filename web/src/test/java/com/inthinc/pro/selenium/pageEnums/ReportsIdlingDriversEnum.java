package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum ReportsIdlingDriversEnum implements SeleniumEnums {
    
    DEFAULT_URL(appUrl + "/reports/idlingReport"),
    
    GROUP_FILTER(null, "idling-form:idling:groupfsp"),
    DRIVER_FILTER(null, "idling-form:idling:fullNamefsp"),
    
    GROUP_SORT(null, "idling-form:idling:groupheader:sortDiv"),
    DRIVER_SORT(null, "idling-form:idling:fullNameheader:sortDiv"),
    IDLE_SUPPORT_SORT(null, "idling-form:idling:idleSupportheader:sortDiv"),
    DURATION_SORT(null, "idling-form:idling:driveTimeheader:sortDiv"),
    LOW_IDLE_SORT(null, "idling-form:idling:lowHoursheader:sortDiv"),
    HIGH_IDLE_SORT(null, "idling-form:idling:highHoursheader:sortDiv"),
    
    GROUP_VALUE(null, "idling-form:idling:###:group"),
    DRIVER_VALUE(null, "idling-form:idling:###:fullName"),
    IDLE_SUPPORT_VALUE(null, "idling-form:idling:###:idleSupport"),
    DURATION_VALUE(null, "idling-form:idling:###:driveTime"),
    LOW_IDLE_VALUE(null, "idling-form:idling:###:lowHours"),
//    LOW_IDLE_PERCENT_VALUE(null, "//tbody[@id='idling-form:idling:tb']/tr[###]/td[contains(@"),
    HIGH_IDLE_VALUE(null, "idling-form:idling:###:highHours"),
//    HIGH_IDLE_PERCENT_VALUE(null, ""),
    TOTAL_IDLE_VALUE(null, "idling-form:idling:###:totalHours"),
//    TOTAL_IDLE_PERCENT_VALUE(null, ""),
    TRIPS_LINK(null, "idling-form:idling:###:idlingDriverTrips"),
    
    START_DATE(null, "idling-form:startCalendarInputDate"),
    END_DATE(null, "idling-form:endCalendarInputDate"),
    REFRESH(null, "idling-form:idling_refresh"),
    
    IDLING_COUNTER("Report shows XXX out of YYY drivers that are reporting idling statistics.", "idling-form:headerCounts"),
    ;

    private String text, url;
    private String[] IDs;
    
    private ReportsIdlingDriversEnum(String url){
    	this.url = url;
    }
    private ReportsIdlingDriversEnum(String text, String ...IDs){
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
