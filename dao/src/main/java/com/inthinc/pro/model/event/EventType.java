package com.inthinc.pro.model.event;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import com.inthinc.pro.model.BaseEnum;

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
    NO_DRIVER(17);
    
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
}
