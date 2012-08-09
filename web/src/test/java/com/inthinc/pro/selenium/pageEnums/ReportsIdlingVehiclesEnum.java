package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum ReportsIdlingVehiclesEnum implements SeleniumEnums {
    
    DEFAULT_URL(appUrl + "/reports/idlingVehicleReport"),
    
    GROUP_FILTER(null, "idlingVehicle-form:idlingVehicle:groupfsp"),
    DRIVER_FILTER(null, "idlingVehicle-form:idlingVehicle:fullNamefsp"),
    VEHICLE_SEARCH(null, "idlingVehicle-form:idlingVehicle:vehicleNamefsp"),
    
    GROUP_SORT(null, "idlingVehicle-form:idlingVehicle:groupheader:sortDiv"),
    DRIVER_SORT(null, "idlingVehicle-form:idlingVehicle:fullNameheader:sortDiv"),
    VEHICLE_SORT(null, "idlingVehicle-form:idlingVehicle:vehicleNameheader:sortDiv"),
    IDLE_SUPPORT_SORT(null, "idlingVehicle-form:idlingVehicle:idleSupportheader:sortDiv"),
    DURATION_SORT(null, "idlingVehicle-form:idlingVehicle:driveTimeheader:sortDiv"),
    LOW_IDLE_SORT(null, "idlingVehicle-form:idlingVehicle:lowHoursheader:sortDiv"),
    HIGH_IDLE_SORT(null, "idlingVehicle-form:idlingVehicle:highHoursheader:sortDiv"),
    
    GROUP_VALUE(null, "idlingVehicle-form:idlingVehicle:###:group"),
    DRIVER_VALUE(null, "idlingVehicle-form:idlingVehicle:###:fullName"),
    IDLE_SUPPORT_VALUE(null, "idlingVehicle-form:idlingVehicle:###:idleSupport"),
    DURATION_VALUE(null, "idlingVehicle-form:idlingVehicle:###:driveTime"),
    LOW_IDLE_VALUE(null, "idlingVehicle-form:idlingVehicle:###:lowHours"),
//    LOW_IDLE_PERCENT_VALUE(null, "//tbody[@id='idlingVehicle-form:idlingVehicle:tb']/tr[###]/td[contains(@"),
    HIGH_IDLE_VALUE(null, "idlingVehicle-form:idlingVehicle:###:highHours"),
//    HIGH_IDLE_PERCENT_VALUE(null, ""),
    TOTAL_IDLE_VALUE(null, "idlingVehicle-form:idlingVehicle:###:totalHours"),
//    TOTAL_IDLE_PERCENT_VALUE(null, ""),
    TRIPS_LINK(null, "idlingVehicle-form:idlingVehicle:###:idlingDriverTrips"),
    
    START_DATE(null, "idlingVehicle-form:startCalendarInputDate"),
    END_DATE(null, "idlingVehicle-form:endCalendarInputDate"),
    REFRESH(null, "idlingVehicle-form:idling_refresh"),
    
    IDLING_COUNTER("Report shows XXX out of YYY drivers that are reporting idling statistics.", "idlingVehicle-form:headerCounts"),
    ;

    private String text, url;
    private String[] IDs;
    
    private ReportsIdlingVehiclesEnum(String url){
    	this.url = url;
    }
    private ReportsIdlingVehiclesEnum(String text, String ...IDs){
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
