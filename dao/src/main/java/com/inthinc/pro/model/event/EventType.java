package com.inthinc.pro.model.event;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.inthinc.pro.model.*;
import com.inthinc.pro.model.pagination.EventCategoryFilter;

@XmlRootElement
public enum EventType implements BaseEnum {
    TAMPERING(1),
    SEATBELT(2),
    SPEEDING(3),
    HARD_ACCEL(4, AggressiveDrivingEventType.HARD_ACCEL),
    HARD_BRAKE(5, AggressiveDrivingEventType.HARD_BRAKE),
    HARD_TURN(6, AggressiveDrivingEventType.HARD_TURN),
    HARD_VERT(8, AggressiveDrivingEventType.HARD_VERT),
    ZONES_ARRIVAL(9),
    ZONES_DEPARTURE(10),
    LOW_BATTERY(11),
    DEVICE_LOW_BATTERY(12),
    IDLING(13),
    CRASH(14),
    // ROLLOVER(15),
    UNKNOWN(16),
    NO_DRIVER(17),
    PARKING_BRAKE(18, EnumSet.of(EventAccountFilter.WAYSMART)),
    IGNITION_ON(19),
    MICRO_SLEEP(20, EnumSet.of(EventAccountFilter.WAYSMART)),
    POTENTIAL_TAMPERING(21),
    WIRELINE_ALARM(22, EnumSet.of(EventAccountFilter.WAYSMART)),
    INSTALL(23, EnumSet.of(EventAccountFilter.WAYSMART)),
    FIRMWARE_CURRENT(24, EnumSet.of(EventAccountFilter.WAYSMART)),
    LOCATION_DEBUG(25, EnumSet.of(EventAccountFilter.WAYSMART)),
    QSI_UPDATED(26, EnumSet.of(EventAccountFilter.WAYSMART)),
    WITNESS_UPDATED(27, EnumSet.of(EventAccountFilter.WAYSMART)),
    ZONES_CURRENT(28, EnumSet.of(EventAccountFilter.WAYSMART)),
    NO_INTERNAL_THUMB_DRIVE(29, EnumSet.of(EventAccountFilter.WAYSMART)),
    WITNESS_HEARTBEAT_VIOLATION(30, EnumSet.of(EventAccountFilter.WAYSMART)),
    PANIC(31, EnumSet.of(EventAccountFilter.WAYSMART)),
    MAN_DOWN(32, EnumSet.of(EventAccountFilter.WAYSMART)),
    MAN_DOWN_CANCELED(33, EnumSet.of(EventAccountFilter.WAYSMART)),
    DOT_STOPPED(34, EnumSet.of(EventAccountFilter.HOS)),
    HOS_NO_HOURS(35, EnumSet.of(EventAccountFilter.HOS)),
    TEXT_MESSAGE(36, EnumSet.of(EventAccountFilter.WAYSMART)),
    OFF_HOURS(37, EnumSet.of(EventAccountFilter.WAYSMART)),
    IGNITION_OFF(38, IgnitionOffEventType.IGNITION_OFF),
    TRAILER_DATA(39),
    NEW_DRIVER(40, EnumSet.of(EventAccountFilter.WAYSMART)),
    NEW_OCCUPANT(41, EnumSet.of(EventAccountFilter.WAYSMART)),
    INVALID_DRIVER(42, EnumSet.of(EventAccountFilter.WAYSMART)),
    INVALID_OCCUPANT(43, EnumSet.of(EventAccountFilter.WAYSMART)),
    RF_SWITCH(44),
    DVIR(45),
    POWER_INTERRUPTED(46),
    DVIR_DRIVEN_UNSAFE(47),
    DVIR_DRIVEN_NOPREINSPEC(48),
    DVIR_DRIVEN_NOPOSTINSPEC(49),
    DVIR_REPAIR(50),
    BACKING(51, BackingEventType.BACKING),
    TRAILER_PROGRAMMED(52),
    ATTR_RF_OFF_DISTANCE(53),
    GENERAL_BACKING(54, BackingEventType.BACKING),
    FIRST_MOVE_FORWARD(55, BackingEventType.FIRST_MOVE_FORWARD),
    BATTERY_VOLTAGE(56, MaintenanceEventType.BATTERY_VOLTAGE),
    ENGINE_TEMP(57,MaintenanceEventType.ENGINE_TEMP),
    TRANSMISSION_TEMP(58,MaintenanceEventType.TRANSMISSION_TEMP),
    DPF_FLOW_RATE(59,MaintenanceEventType.DPF_FLOW_RATE),
    OIL_PRESSURE(60,MaintenanceEventType.OIL_PRESSURE),
    MALFUNCTION_INDICATOR_LAMP(61,IgnitionOffEventType.MALFUNCTION_INDICATOR_LAMP),
    CHECK_ENGINE(62),
    ATTR_MAINTENANCE_CAPABILITIES(63),
    ATTR_ENGINE_HOURS(64,MaintenanceEventType.ATTR_ENGINE_HOURS),
    RED_STOP(65,IgnitionOffEventType.RED_STOP),
    AMBER_WARNING(66,IgnitionOffEventType.AMBER_WARNING),
    PROTECT(67,IgnitionOffEventType.PROTECT),
    CHECK_ENGINE_LIGHT(68),
    STOP_ENGINE_LIGHT(69),
    ODOMETER(70,MaintenanceEventType.ODOMETER),
    UNKNOWN_CHECK_ENGINE_LAMP(71, IgnitionOffEventType.UNKNOWN_CHECK_ENGINE_LAMP),
    UNKNOWN_MAINTENANCE(72, MaintenanceEventType.UNKNOWN_MAINTENANCE),
    PREVENTATIVE_MESSAGE_ODOMETER(73, MaintenanceEventType.ODOMETER);




    private int code;
    private AggressiveDrivingEventType noteSubType;
    private MaintenanceEventType maintenanceEventType;
    private IgnitionOffEventType ignitionOffEventType;
    private Set<EventAccountFilter> eventAccountFilters;
    private BackingEventType backingEventType;

    private EventType(int code) {
        this.code = code;
    }

    private EventType(int code, AggressiveDrivingEventType noteSubType) {
        this.code = code;
        this.noteSubType = noteSubType;
    }

    private EventType(int code, MaintenanceEventType maintenanceEventType) {
        this.code = code;
        this.maintenanceEventType = maintenanceEventType;
    }
    private EventType(int code, IgnitionOffEventType ignitionOffEventType) {
        this.code = code;
        this.ignitionOffEventType = ignitionOffEventType;
    }
    private EventType(int code, BackingEventType backingEventType) {
        this.code = code;
        this.backingEventType = backingEventType;
    }

    private EventType(int code, Set<EventAccountFilter> eventAccountFilters) {
        this.code = code;
        this.eventAccountFilters = eventAccountFilters;
    }

    private static final Map<Integer, EventType> lookup = new HashMap<Integer, EventType>();
    private static final Map<Integer, EventType> subTypeLookup = new HashMap<Integer, EventType>();
    static {
        for (EventType p : EnumSet.allOf(EventType.class)) {
            lookup.put(p.code, p);
            if (p.noteSubType != null) {
                subTypeLookup.put(p.noteSubType.getCode(), p);
            }
            if (p.maintenanceEventType != null) {
                subTypeLookup.put(p.maintenanceEventType.getCode(), p);
            }
            if (p.ignitionOffEventType != null) {
                subTypeLookup.put(p.ignitionOffEventType.getCode(), p);
            }
            if (p.backingEventType != null) {
                subTypeLookup.put(p.backingEventType.getCode(), p);
            }
        }

    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    public static EventType getEventType(Integer code) {
        return lookup.get(code);
    }

    public static EventType getEventTypeFromSubTypeCode(Integer code) {
        return subTypeLookup.get(code);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
        sb.append(".");
        sb.append(this.name());
        return sb.toString();
    }

    public EventCategoryFilter getEventCategoryFilter() {
        List<Integer> subTypeList = null;
        if (noteSubType != null) {
            subTypeList = new ArrayList<Integer>();
            subTypeList.add(noteSubType.getCode());
        }
        if (maintenanceEventType != null) {
            subTypeList = new ArrayList<Integer>();
            subTypeList.add(maintenanceEventType.getCode());
        }
        if (ignitionOffEventType != null) {
            subTypeList = new ArrayList<Integer>();
            subTypeList.add(ignitionOffEventType.getCode());
        }
        if (backingEventType != null) {
            subTypeList = new ArrayList<Integer>();
            subTypeList.add(backingEventType.getCode());
        }

        return new EventCategoryFilter(this, getNoteTypeList(), subTypeList);

    }

    public List<NoteType> getNoteTypeList() {
        List<NoteType> noteTypeList = new ArrayList<NoteType>();
        for (NoteType noteType : NoteType.values()) {
            if (noteType.getEventTypes() != null) {
                for (EventType et : noteType.getEventTypes()) {
                    if (et == this) {
                        noteTypeList.add(noteType);
                        break;
                    }
                }
            } else {
                EventType eventType = noteType.getEventType();
                if (noteType.getEventType() == this) {
                    noteTypeList.add(noteType);
                }
            }
        }
        if (noteTypeList.size() == 0)
            System.out.println("NO NOTES TYPES FOUND FOR EVENTTYPE " + this);
        return noteTypeList;

    }

    public Boolean isWaysmartOnlyEvent() {
        return eventAccountFilters != null && eventAccountFilters.contains(EventAccountFilter.WAYSMART);
    }

    public Boolean isHOSOnlyEvent() {
        return eventAccountFilters != null && eventAccountFilters.contains(EventAccountFilter.HOS);
    }
}
