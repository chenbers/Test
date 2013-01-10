package com.inthinc.pro.automation.enums;

import com.inthinc.pro.automation.interfaces.TextEnum;

public enum WebDateFormat implements TextEnum {
    DATE_RANGE_FIELDS("MMM d, yyyy"),
    DURATION("HH:mm:ss"),
    FUEL_STOPS("MMM d, yyyy hh:mm:ss a (z)"),
    HOS_REPORTS("yyyy-MM-dd HH:mm"),
    LAST_NOTE_CONFIGURATOR("yyyy-MM-dd hh:mm:ss z"),
    
    NOTE_DATE_TIME("MMM d, yyyy hh:mm a (z)"),
    
    NOTE_PRECISE_TIME("yyyy-MM-dd HH:mm:ss,SSS"),

    RALLY_DATE_FORMAT("yyyy-MM-dd'T'hh:mm:ssZ"),
    
    TIME("HH:mm a (z)"),
    
    FILE_NAME("yyyy-MM-dd"), 
    
    MONTH_YEAR("MMM, yyyy"), 
    WEB_SERVICE("yyyyMMdd"),
    
    STANDARD_DATE("MM/dd/yyyy"),
    
    CASSANDRA_MONTHS("yyyy-MM"),
    CASSANDRA_DAYS("yyyy-MM-dd"),
    
    ANDROID_LOGCAT("MM-dd HH:mm:ss.SSS"),
    
    ;

    private String format;

    private WebDateFormat(String format) {
        this.format = format;
    }

    public String getText() {
        return format;
    }

    @Override
    public String toString() {
        return format;
    }

}