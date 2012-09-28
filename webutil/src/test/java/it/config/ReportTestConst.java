package it.config;

import java.util.TimeZone;

public interface ReportTestConst {
    public static final long ELAPSED_TIME_PER_EVENT = 15000;	// ms units
    public static final int MILES_PER_EVENT = 100;		// 1 mile per event (units are 1/100 mi)
    public static final int EVENTS_PER_DAY = 95;
    
    public static final int LO_IDLE_TIME = 4;			// seconds units
    public static final int HI_IDLE_TIME = 8;

    public static final int TAMPER_EVENT_IDX = 0;
    public static final int TAMPER2_EVENT_IDX = 1;
    public static final int LOW_BATTERY_EVENT_IDX = 2;
    public static final int ZONE_ENTER_EVENT_IDX = 77;
    public static final int ZONE_EXIT_EVENT_IDX = 85;
    public static final int extraEventIndexes[] = {TAMPER_EVENT_IDX, TAMPER2_EVENT_IDX,   LOW_BATTERY_EVENT_IDX,  ZONE_ENTER_EVENT_IDX,  ZONE_EXIT_EVENT_IDX}; 
    public static final int CRASH_EVENT_IDX = 90;			// always put crash event at this index so we can determine mileage
    
    public static String TIMEZONE_STR = "US/Mountain";
    public static TimeZone timeZone = TimeZone.getTimeZone(TIMEZONE_STR);


}
