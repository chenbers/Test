package it.config;

import java.util.TimeZone;

import com.inthinc.pro.model.event.Event;
import com.inthinc.pro.model.event.NoteType;

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
    public static final int extraEventIndexes[] = {TAMPER_EVENT_IDX, TAMPER2_EVENT_IDX,   LOW_BATTERY_EVENT_IDX,  
                        ZONE_ENTER_EVENT_IDX,  ZONE_EXIT_EVENT_IDX}; 
    public static final int CRASH_EVENT_IDX = 90;			// always put crash event at this index so we can determine mileage


    public static final int INSTALL_IDX = 0;
    public static final int FIRMWARE_CURRENT_IDX = 1;
    public static final int QSI_UPDATED_IDX = 2;
    public static final int WITNESS_UPDATED_IDX = 3;
    public static final int ZONES_UPDATED_IDX = 4;
    public static final int NO_DRIVER_IDX = 8;
    public static final int NOTE_EVENT_SECONDARY_IDX = 10;
    public static final int HARD_VERT_820_IDX = 12;
    public static final int HARD_VERT_820_SECONDARY_IDX = 14;
    public static final int SPEEDING_EXT_IDX = 15;
    public static final int PARKING_BRAKE_IDX = 20;
    public static final int MICRO_SLEEP_IDX = 25;
    public static final int LOW_BATTERY_POTENTIAL_IDX = 30;
    public static final int WIRELINE_ALARM_IDX = 35;
    public static final int LOCATION_DEBUG_IDX = 40;
    public static final int NO_INTERNAL_THUMB_DRIVE_IDX = 45;
    public static final int WITNESS_HEARTBEAT_VIOLATION_IDX = 50;
    public static final int ROLLOVER_IDX = 51;
    public static final int PANIC_IDX = 52;
    public static final int AUTO_MAN_DOWN_IDX = 53;
    public static final int AUTO_MAN_DOWN_CANCEL_IDX = 54;
    public static final int MAN_DOWN_IDX = 55;
    public static final int MAN_DOWN_OK_IDX = 56;
    public static final int MAN_DOWN_CANCEL_IDX = 58;
    public static final int REMOTE_AUTO_MAN_DOWN_IDX = 60;
    public static final int REMOTE_MANUAL_MAN_DOWN_IDX = 62;
    public static final int DOT_STOPPED_IDX = 70;
    public static final int HOS_NO_HOURS_REMAINING_IDX = 75;

    public static final WS waySmartEventIndexes[] = {
        new WS(NOTE_EVENT_SECONDARY_IDX, NoteType.NOTE_EVENT_SECONDARY),
        new WS(HARD_VERT_820_IDX, NoteType.VERTICAL_EVENT),
        new WS(HARD_VERT_820_SECONDARY_IDX, NoteType.VERTICAL_EVENT_SECONDARY),
        new WS(SPEEDING_EXT_IDX, NoteType.SPEEDING_EX4),
        new WS(NO_DRIVER_IDX, NoteType.NO_DRIVER),
        new WS(PARKING_BRAKE_IDX, NoteType.PARKING_BRAKE),
        new WS(MICRO_SLEEP_IDX, NoteType.DSS_MICROSLEEP),
        new WS(LOW_BATTERY_POTENTIAL_IDX, NoteType.LOW_BATTERY_POTENIAL_TAMPERING),
        new WS(WIRELINE_ALARM_IDX, NoteType.WIRELINE_ALARM),
        new WS(INSTALL_IDX, NoteType.INSTALL),
        new WS(FIRMWARE_CURRENT_IDX, NoteType.FIRMWARE_UP_TO_DATE),
        new WS(LOCATION_DEBUG_IDX, NoteType.LOCATION_DEBUG),
        new WS(QSI_UPDATED_IDX, NoteType.QSI_UP_TO_DATE),
        new WS(WITNESS_UPDATED_IDX, NoteType.WITNESS_UP_TO_DATE),
        new WS(ZONES_UPDATED_IDX, NoteType.ZONES_UP_TO_DATE),
        new WS(NO_INTERNAL_THUMB_DRIVE_IDX, NoteType.NO_INTERNAL_THUMB_DRIVE),
        new WS(WITNESS_HEARTBEAT_VIOLATION_IDX, NoteType.WITNESS_HEARTBEAT_VIOLATION),
        new WS(ROLLOVER_IDX, NoteType.ROLLOVER_WAYSMART),
        new WS(PANIC_IDX, NoteType.PANIC),
        new WS(AUTO_MAN_DOWN_IDX, NoteType.AUTO_MAN_DOWN),
        new WS(AUTO_MAN_DOWN_CANCEL_IDX, NoteType.AUTO_MAN_OK),
        new WS(MAN_DOWN_IDX, NoteType.MANDOWN),
        new WS(MAN_DOWN_OK_IDX, NoteType.MAN_OK),
        new WS(MAN_DOWN_CANCEL_IDX, NoteType.MAN_OK),
        new WS(REMOTE_AUTO_MAN_DOWN_IDX, NoteType.REMOTE_AUTO_MANDOWN),
        new WS(REMOTE_MANUAL_MAN_DOWN_IDX, NoteType.REMOTE_MAN_MANDOWN),
        new WS(DOT_STOPPED_IDX,NoteType.DOT_STOPPED),
        new WS(HOS_NO_HOURS_REMAINING_IDX, NoteType.HOS_NO_HOURS)
    };
    public static String TIMEZONE_STR = "US/Mountain";
    public static TimeZone timeZone = TimeZone.getTimeZone(TIMEZONE_STR);

    class WS {
        public int idx;
        public NoteType type;
        
        public WS(int idx, NoteType type) {
            this.idx = idx;
            this.type = type;
        }
    }
}
