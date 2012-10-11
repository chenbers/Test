package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum NotificationsCrashReportEnum implements SeleniumEnums {

    DEFAULT_URL(appUrl + "/notifications/crashReport"),
    
    BACK("Back to Crash History List", "NEED HARD CODED ID"),
    
    CRASH_DETAILS_HEADER("Crash Details", "//td[1]/div[@class='panel_title']"),
    CRASH_ROUTE_HEADER("Crash Route", "//td[2]/div[@class='panel_title']"),
    CRASH_EVENTS_HEADER("Crash Location", "//div[@class='panel_title']"),
    
    CRASH_REPORT_STATUS("Crash Report Status", "NEED HARD CODED ID"),
    DATE_TIME("Date/Time", "NEED HARD CODED ID"),
    VEHICLE("Vehicle", "crashHistoryVehiclePerformance"),
    DRIVER("Driver", "crashReport-driverPerformance"),
    WEATHER("Weather", "NEED HARD CODED ID"),
    OCCUPANT_COUNT("Occupant Count", "NEED HARD CODED ID"),
    DESCRIPTION_MESSAGE("Description:", "NEED HARD CODED ID"),
    
    TIME_HEADER("Time", "NEED HARD CODED ID"),
    RPM_HEADER("RPM", "NEED HARD CODED ID"),
    SPEED_HEADER("Speed", "NEED HARD CODED ID"),
    OBD_HEADER("OBD", "NEED HARD CODED ID"),
    GPS_HEADER("GPS", "NEED HARD CODED ID"),
    SEAT_BELT_HEADER("Seat Belt", "NEED HARD CODED ID"),
    AVAILABLE_HEADER("Available", "NEED HARD CODED ID"),
    ON_HEADER("On", "NEED HARD CODED ID"),
    
    TIME(null, "NEED HARD CODED ID"),
    RPM(null, "NEED HARD CODED ID"),
    SPEED(null, "NEED HARD CODED ID"),
    OBD(null, "NEED HARD CODED ID"),
    GPS(null, "NEED HARD CODED ID"),
    SEAT_BELT(null, "NEED HARD CODED ID"),
    AVAILABLE(null, "NEED HARD CODED ID"),
    ON(null, "NEED HARD CODED ID"),
    
    EDIT("Edit", "crashReportEdit"),

    ;
    private String text, url;
    private String[] IDs;
    
    private NotificationsCrashReportEnum(String url){
        this.url = url;
    }
    private NotificationsCrashReportEnum(String text, String ...IDs){
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
