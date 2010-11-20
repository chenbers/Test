package com.inthinc.pro.model.event;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import com.inthinc.pro.model.AggressiveDrivingEventType;
import com.inthinc.pro.model.BaseEnum;
import com.inthinc.pro.model.pagination.EventCategoryFilter;

@XmlRootElement
public enum EventType implements BaseEnum {
    TAMPERING(1),
    SEATBELT(2),
    SPEEDING(3),
    HARD_ACCEL(4),
    HARD_BRAKE(5),
    HARD_TURN(6),
    HARD_VERT(8),
    ZONES_ARRIVAL(9),
    ZONES_DEPARTURE(10),
    LOW_BATTERY(11),
    DEVICE_LOW_BATTERY(12),
    IDLING(13),
    CRASH(14),
    ROLLOVER(15),
    UNKNOWN(16),
    NO_DRIVER(17),
    PARKING_BRAKE(18),
    IGNITION_ON(19),
    MICRO_SLEEP(20),
    POTENTIAL_TAMPERING(21),
    WIRELINE_ALARM(22),
    INSTALL(23),
    FIRMWARE_CURRENT(24),
    LOCATION_DEBUG(25),
    QSI_UPDATED(26),
    WITNESS_UPDATED(27),
    ZONES_CURRENT(28),
    NO_INTERNAL_THUMB_DRIVE(29),
    WITNESS_HEARTBEAT_VIOLATION(30),
    PANIC(31),
    MAN_DOWN(32),
    MAN_DOWN_CANCELED(33),
    DOT_STOPPED(34),
    HOS_NO_HOURS(35);
    
    private int code;
    
    private EventType(int code) {
        this.code = code;
    }

    private static final Map<Integer, EventType> lookup = new HashMap<Integer, EventType>();
    static {
        for (EventType p : EnumSet.allOf(EventType.class)) {
            lookup.put(p.code, p);
        }
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    public static EventType getEventType(Integer code) {
        return lookup.get(code);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
        sb.append(".");
        sb.append(this.name());
        return sb.toString();
    }
    
    public EventCategoryFilter getEventCategoryFilter() {
        if (this == HARD_ACCEL)
            return new EventCategoryFilter(this, new NoteType[] {NoteType.NOTEEVENT}, new Integer[] {AggressiveDrivingEventType.HARD_ACCEL.getCode()});
        if (this == HARD_BRAKE)
            return  new EventCategoryFilter(this, new NoteType[] {NoteType.NOTEEVENT}, new Integer[] {AggressiveDrivingEventType.HARD_BRAKE.getCode()});
        if (this == HARD_TURN)
            return new EventCategoryFilter(this, new NoteType[] {NoteType.NOTEEVENT}, new Integer[] {AggressiveDrivingEventType.HARD_TURN.getCode()});
        if (this == HARD_VERT)
            return new EventCategoryFilter(this, new NoteType[] {NoteType.NOTEEVENT}, new Integer[] {AggressiveDrivingEventType.HARD_VERT.getCode()});
        
        List<NoteType> noteTypeList = new ArrayList<NoteType>();
        for (NoteType noteType : NoteType.values()) {
            if (noteType.getEventType() == this) {
                noteTypeList.add(noteType);
            }
        }
        if (noteTypeList.size() == 0)
            System.out.println("NO NOTES TYPES FOUND FOR EVENTTYPE " + this);
        return new EventCategoryFilter(this, noteTypeList.toArray(new NoteType[0]), null);
        
    }
}
