package com.inthinc.pro.model.event;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.inthinc.pro.model.BaseEnum;

@XmlRootElement
public enum EventSubCategory implements BaseEnum
{
    DRIVING_STYLE(1, EnumSet.of(EventType.HARD_ACCEL,EventType.HARD_BRAKE,EventType.HARD_TURN,EventType.HARD_VERT)),
    SPEED(2, EnumSet.of(EventType.SPEEDING)),
    COMPLIANCE(3, EnumSet.of(EventType.SEATBELT, EventType.NO_DRIVER, EventType.PARKING_BRAKE)),
    FATIGUE(4, EnumSet.of(EventType.MICRO_SLEEP)),
    VEHICLE(5, EnumSet.of(EventType.LOW_BATTERY, EventType.DEVICE_LOW_BATTERY, EventType.TAMPERING, EventType.IDLING, EventType.IGNITION_ON, EventType.POTENTIAL_TAMPERING)), 
    WIRELINE(6, EnumSet.of(EventType.WIRELINE_ALARM)), 
    INSTALLATION(7, EnumSet.of(EventType.INSTALL, EventType.FIRMWARE_CURRENT, EventType.LOCATION_DEBUG, EventType.QSI_UPDATED, EventType.WITNESS_UPDATED, EventType.ZONES_CURRENT, EventType.NO_INTERNAL_THUMB_DRIVE, EventType.WITNESS_HEARTBEAT_VIOLATION )),
    EMERGENCY(8, EnumSet.of(EventType.CRASH, EventType.PANIC, EventType.MAN_DOWN, EventType.MAN_DOWN_CANCELED)),
    ZONES(9, EnumSet.of(EventType.ZONES_ARRIVAL, EventType.ZONES_DEPARTURE)),
    HOS(10, EnumSet.of(EventType.DOT_STOPPED, EventType.HOS_NO_HOURS));

    private int code;
    
    private Set<EventType> eventTypeSet;


    private EventSubCategory(int code)
    {
        this.code = code;
    }
    private EventSubCategory(int code, Set<EventType> eventTypeSet)
    {
        this.code = code;
        this.eventTypeSet = eventTypeSet;
    }

    private static final Map<Integer, EventSubCategory> lookup = new HashMap<Integer, EventSubCategory>();
    static
    {
        for (EventSubCategory p : EnumSet.allOf(EventSubCategory.class))
        {
            lookup.put(p.code, p);
        }
    }

    public Integer getCode()
    {
        return this.code;
    }

    public static EventSubCategory valueOf(Integer code)
    {
        return lookup.get(code);
    }
    public Set<EventType> getEventTypeSet() {
        return eventTypeSet;
    }
    public void setEventTypeSet(Set<EventType> eventTypeSet) {
        this.eventTypeSet = eventTypeSet;
    }
    public List<NoteType> getNoteTypesInSubCategory()
    {
        List<NoteType> noteTypeList = new ArrayList<NoteType>();
        
        for (EventType eventType : getEventTypeSet()) {
            for (NoteType noteType : eventType.getNoteTypeList()) {
                    if (!noteTypeList.contains(noteType))
                        noteTypeList.add(noteType);
            }
        }
        return noteTypeList;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
        sb.append(".");
        sb.append(this.name());
        return sb.toString();

    }
    
}
