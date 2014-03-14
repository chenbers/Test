package com.inthinc.pro.selenium.pageEnums;

import com.inthinc.pro.automation.interfaces.SeleniumEnums;

public enum NotificationsCrashHistoryAddEditEnum implements SeleniumEnums {

    DEFAULT_URL(appUrl + "/notifications/addCrashReport"),
    
    CRASH_SUMMARY_HEADER("Crash Summary", "//td[1]/div[@class='section_title']"),
    DESCRIPTION_HEADER("Description", "//td[2]/div[@class='section_title']"),
    CRASH_LOCATION_HEADER("Crash Location", "//div[@class='add_section_title']"),
    
    CRASH_REPORT_STATUS("Crash Report Status", "edit-form:editCrashReport-statusSelectOne"),
    DATE_TIME("Date/Time", "edit-form:editCrashReport-dateCalendarInputDate"),
    DATE_TIME_BUTTON("Date/Time", "edit-form:editCrashReport-dateCalendarPopupButton"),
    VEHICLE("Vehicle", "edit-form:editCrashReport-vehicle"),
    DRIVER("Driver", "edit-form:editCrashReport-driver"),
    WEATHER("Weather", "edit-form:editCrashReport-weather"),
    OCCUPANT_COUNT("Occupant Count", "//input[@name='edit-form:editCrashReport-occupantCount']"),
    
    //buttons and links inside the calendar
    CLEAN_LINK("Clean", "//td[2][@class='rich-calendar-toolfooter']"),    
    TIME_LINK(null, "//div[@class='rich-calendar-tool-btn rich-calendar-tool-btn-hover rich-calendar-tool-btn-press']"),
    TODAY_LINK("Today", "//td[5][@class='rich-calendar-toolfooter']"),
    HOURS_FIELD(null, "edit-form:editCrashReport-dateCalendarTimeHours"),
    HOURS_BUTTON_UP(null, "edit-form:editCrashReport-dateCalendarTimeHoursBtnUp"),
    HOURS_BUTTON_DOWN(null, "edit-form:editCrashReport-dateCalendarTimeHoursBtnDown"),
    MINUTES_FIELD(null, "edit-form:editCrashReport-dateCalendarTimeMinutes"),
    MINUTES_BUTTON_UP(null, "edit-form:editCrashReport-dateCalendarTimeMinutesBtnUp"),
    MINUTES_BUTTON_DOWN(null, "edit-form:editCrashReport-dateCalendarTimeMinutesBtnDown"),
    TIME_OF_DAY_FIELD(null, "edit-form:editCrashReport-dateCalendarTimeSign"),
    TIME_OF_DAY_UP(null, "edit-form:editCrashReport-dateCalendarTimeSignBtnUp"),
    TIME_OF_DAY_DOWN(null, "edit-form:editCrashReport-dateCalendarTimeSignBtnDown"),
    TIME_OK_BUTTON("OK", "edit-form:editCrashReport-dateCalendarTimeEditorButtonOk"),
    TIME_CANCEL_BUTTON("Cancel", "edit-form:editCrashReport-dateCalendarTimeEditorButtonCancel"),
    
    DESCRIPTION_MESSAGE("Please describe the crash event, occupants and/or witnesses and their contact information, personal injuries, and property damage.", "//span[@class='instructions']"),
    DESCRIPTION_BOX(null, "edit-form:editCrashReport-description"),
    
    LABEL_SELECT_LOCATION_BY("Select location by:", "//table[@id='edit-form:location']/../../td[1]"),
    
    SELECT_LOCATION_BY_TRIPS(null, "edit-form:location:0"),
    SELECT_LOCATION_BY_FIND_ADDRESS(null, "edit-form:location:1"),
    
    FIND_BY_DRIVER("Driver", "edit-form:editCrashReport-findTrips:0"),
    FIND_BY_VEHICLE("Vehicle", "edit-form:editCrashReport-findTrips:0"),
    
    SORT_BY_START_TIME("Start Time", "edit-form:trips:editCrashReport-startTime:sortDiv"),
    START_TIME(null, "edit-form:trips:###:editCrashReport-startTime"),
    END_TIME(null, "//td[2]/div[@class='extdt-cell-div']"), 
    END_TIME_HEADER("End Time", "//thead/tr/th[2]"),

    ADDRESS_TEXT_FIELD(null, "editCrashReport-addressTextBox"),
    LOCATE("Locate", "edit-form:editCrashReport-search"),
    
    TOP_SAVE(save, "edit-form:editCrashReportSave1"),
    BOTTOM_SAVE(save, "edit-form:editCrashReportSave2"),
    TOP_CANCEL(cancel, "edit-form:editCrashReportCancel1"),
    BOTTOM_CANCEL(cancel, "edit-form:editCrashReportCancel2"), 
    
    TITLE("ADD/EDIT Crash Report", "//span[@class='email']")

    ;
    private String text, url;
    private String[] IDs;
    
    private NotificationsCrashHistoryAddEditEnum(String url){
        this.url = url;
    }
    private NotificationsCrashHistoryAddEditEnum(String text, String ...IDs){
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
