package com.inthinc.pro.automation.device_emulation;

import java.util.EnumSet;
import java.util.HashMap;

public enum TiwiNoteTypes {
	/* Note Types */

    NOTE_TYPE_FULLEVENT(1),
    NOTE_TYPE_NOTE_EVENT(2),
    NOTE_TYPE_SEATBELT(3),
    NOTE_TYPE_SPEEDING(4),
    NOTE_TYPE_LOCATION(6),
    NOTE_TYPE_NEWDRIVER(7),
    NOTE_TYPE_RPM(14),
    NOTE_TYPE_IGNITION_ON(19),
    NOTE_TYPE_IGNITION_OFF(20),
    NOTE_TYPE_LOW_BATTERY(22),
    NOTE_TYPE_VERSION(27),
    NOTE_TYPE_NO_DRIVER(46),
    NOTE_TYPE_STATS(51),
    NOTE_TYPE_LOW_POWER_MODE(52),
    NOTE_TYPE_CLEAR_DRIVER(66),
    NOTE_TYPE_FIRMWARE_UP_TO_DATE(74),
    NOTE_TYPE_MAPS_UP_TO_DATE(75),
    NOTE_TYPE_ZONES_UP_TO_DATE(76),
    NOTE_TYPE_BOOT_LOADER_UP_TO_DATE(77),
    NOTE_TYPE_WSZONES_ARRIVAL(78),
    NOTE_TYPE_WSZONES_DEPARTURE(87),
    NOTE_TYPE_SPEEDING_EX3(93),
    NOTE_TYPE_DMM_STATUS(95),
    NOTE_TYPE_A_AND_D_SPACE(113),
    NOTE_TYPE_WSZONES_ARRIVAL_EX(117),
    NOTE_TYPE_WSZONES_DEPARTURE_EX(118),
    NOTE_TYPE_WITNESSII_STATUS(120),
    NOTE_TYPE_WITNESS_UP_TO_DATE(123),
    NOTE_TYPE_SMTOOLS_EMULATION_UP_TO_DATE(144),
    NOTE_TYPE_SMTOOLS_FIRMWARE_UP_TO_DATE(145),
    NOTE_TYPE_SMTOOLS_SILICON_ID(146),
    NOTE_TYPE_SMTOOLS_DEVICE_STATUS(148),
    NOTE_TYPE_OBD_PARAMS_STATUS(149),
    NOTE_TYPE_POWER_ON(150),
    NOTE_TYPE_SMTOOLS_ERROR(151),
    NOTE_TYPE_MPG_VALUE(160),
    NOTE_TYPE_DIAGNOSTICS_REPORT(200),
    NOTE_TYPE_SPEEDING_START(201),
    NOTE_TYPE_UNPLUGGED(202),
    NOTE_TYPE_SEATBELT_START(203),
    NOTE_TYPE_DMM_MONITOR(204),
    NOTE_TYPE_INCOMING_CALL(205),
    NOTE_TYPE_OUTGOING_CALL(206),
    NOTE_TYPE_LOW_BACKUP_BATTERY(207),
    NOTE_TYPE_IDLING(208),
    NOTE_TYPE_ROLLOVER(209),
    NOTE_TYPE_COACH_SPEEDING(210),
    NOTE_TYPE_COACH_SEATBELT(211),
    NOTE_TYPE_SUB_THRESHOLD_CRASH(212),
    NOTE_TYPE_UNPLUGGED_WHILE_ASLEEP(213),
    NOTE_TYPE_STRIPPED_ACKNOWLEDGE_ID_WITH_DATA(246),
    NOTE_TYPE_ZONE_ALERTED(247),
    NOTE_TYPE_ZONE_EXIT_ALERTED(248),
    NOTE_TYPE_STRIPPED_ACKNOWLEDGE(254);
    
    private int code;

    private TiwiNoteTypes(int c) {
    	code = c;
    }
    public int getCode() {
    	return code;
    } 
    
    private static HashMap<Integer, TiwiNoteTypes> lookupByCode = new HashMap<Integer, TiwiNoteTypes>();
    
    static {
        for (TiwiNoteTypes p : EnumSet.allOf(TiwiNoteTypes.class))
        {
            lookupByCode.put(p.getCode(), p);
        }
    }
    
    public static TiwiNoteTypes valueOf(Integer code){
    	return lookupByCode.get(code);
    }

}
