package com.inthinc.pro.model.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.inthinc.pro.model.AlertMessage;
import com.inthinc.pro.model.AlertMessageType;
import com.inthinc.pro.model.BaseEnum;

@XmlRootElement
public enum EventSubCategory implements BaseEnum
{
    DRIVING_STYLE(1, 
            EnumSet.of(EventType.HARD_ACCEL,EventType.HARD_BRAKE,EventType.HARD_TURN,EventType.HARD_VERT,EventType.ATTR_RF_OFF_DISTANCE),
            EnumSet.of(AlertMessageType.ALERT_TYPE_HARD_ACCEL,AlertMessageType.ALERT_TYPE_HARD_BRAKE,AlertMessageType.ALERT_TYPE_HARD_TURN,AlertMessageType.ALERT_TYPE_HARD_BUMP, AlertMessageType.ALERT_TYPE_SATELLITE)),
                        
    SPEED(2, 
            EnumSet.of(EventType.SPEEDING),
            EnumSet.of(AlertMessageType.ALERT_TYPE_SPEEDING)),
    
    COMPLIANCE(3, 
            EnumSet.of(EventType.SEATBELT, EventType.NO_DRIVER, EventType.PARKING_BRAKE),
            EnumSet.of(AlertMessageType.ALERT_TYPE_SEATBELT, AlertMessageType.ALERT_TYPE_NO_DRIVER, AlertMessageType.ALERT_TYPE_PARKING_BRAKE)),
            
    FATIGUE(4, 
            EnumSet.of(EventType.MICRO_SLEEP),
            EnumSet.of(AlertMessageType.ALERT_TYPE_DSS_MICROSLEEP)),
            
    VEHICLE(5, 
            EnumSet.of(EventType.LOW_BATTERY, EventType.DEVICE_LOW_BATTERY, EventType.TAMPERING, EventType.IDLING, EventType.IGNITION_ON, EventType.IGNITION_OFF, EventType.POTENTIAL_TAMPERING, EventType.RF_SWITCH, EventType.POWER_INTERRUPTED, EventType.BACKING, EventType.FIRST_MOVE_FORWARD),
            EnumSet.of(AlertMessageType.ALERT_TYPE_LOW_BATTERY, AlertMessageType.ALERT_TYPE_TAMPERING,AlertMessageType.ALERT_TYPE_IGNITION_ON,AlertMessageType.ALERT_TYPE_IDLING, AlertMessageType.ALERT_TYPE_VEHICLE_MOVE_BACKWARDS )),
            
    WIRELINE(6, 
            EnumSet.of(EventType.WIRELINE_ALARM),
            EnumSet.of(AlertMessageType.ALERT_TYPE_WIRELINE_ALARM)), 
            
    INSTALLATION(7, 
            EnumSet.of(EventType.INSTALL, EventType.FIRMWARE_CURRENT, EventType.LOCATION_DEBUG, EventType.QSI_UPDATED, EventType.WITNESS_UPDATED, EventType.ZONES_CURRENT, EventType.NO_INTERNAL_THUMB_DRIVE, EventType.WITNESS_HEARTBEAT_VIOLATION ),
            EnumSet.of(AlertMessageType.ALERT_TYPE_INSTALL, AlertMessageType.ALERT_TYPE_FIRMWARE_CURRENT, AlertMessageType.ALERT_TYPE_LOCATION_DEBUG, AlertMessageType.ALERT_TYPE_QSI_UPDATED, 
                    AlertMessageType.ALERT_TYPE_WITNESS_UPDATED, AlertMessageType.ALERT_TYPE_ZONES_CURRENT, AlertMessageType.ALERT_TYPE_NO_INTERNAL_THUMB_DRIVE, AlertMessageType.ALERT_TYPE_WITNESS_HEARTBEAT_VIOLATION )),
    EMERGENCY(8, 
            EnumSet.of(EventType.CRASH, EventType.PANIC, EventType.MAN_DOWN, EventType.MAN_DOWN_CANCELED),
            EnumSet.of(AlertMessageType.ALERT_TYPE_CRASH, AlertMessageType.ALERT_TYPE_PANIC,AlertMessageType.ALERT_TYPE_MAN_DOWN, AlertMessageType.ALERT_TYPE_MAN_DOWN_OK)),
            
    ZONES(9, 
            EnumSet.of(EventType.ZONES_ARRIVAL, EventType.ZONES_DEPARTURE),
            EnumSet.of(AlertMessageType.ALERT_TYPE_ENTER_ZONE,AlertMessageType.ALERT_TYPE_EXIT_ZONE)),
            
    HOS(10, 
            EnumSet.of(EventType.DOT_STOPPED, EventType.HOS_NO_HOURS),
            EnumSet.of(AlertMessageType.ALERT_TYPE_HOS_DOT_STOPPED, AlertMessageType.ALERT_TYPE_HOS_NO_HOURS_REMAINING)),
            
    TEXTMESSAGE(11,
            EnumSet.of(EventType.TEXT_MESSAGE),
            EnumSet.of(AlertMessageType.ALERT_TYPE_TEXT_MESSAGE_RECEIVED)),
    DRIVER(12,
    		EnumSet.of(EventType.NEW_DRIVER, EventType.NEW_OCCUPANT, EventType.INVALID_DRIVER, EventType.INVALID_OCCUPANT),
    		null),
    RF_SWITCH(13,EnumSet.of(EventType.RF_SWITCH),EnumSet.of(AlertMessageType.ALERT_TYPE_RF_SWITCH)),
    DVIR(14, EnumSet.of(EventType.DVIR, EventType.DVIR_DRIVEN_UNSAFE, EventType.DVIR_DRIVEN_NOPREINSPEC, EventType.DVIR_DRIVEN_NOPOSTINSPEC, EventType.DVIR_REPAIR), 
            EnumSet.of(AlertMessageType.ALERT_TYPE_DVIR_PRE_TRIP_FAIL,AlertMessageType.ALERT_TYPE_DVIR_PRE_TRIP_PASS,
                    AlertMessageType.ALERT_TYPE_DVIR_POST_TRIP_FAIL, AlertMessageType.ALERT_TYPE_DVIR_POST_TRIP_PASS,
                    AlertMessageType.ALERT_TYPE_DVIR_DRIVEN_INSPECTED_UNSAFE,AlertMessageType.ALERT_TYPE_DVIR_DRIVEN_WITHOUT_INSPECTION,
                    AlertMessageType.ALERT_TYPE_DVIR_NO_POST_TRIP_INSPECTION, AlertMessageType.ALERT_TYPE_DVIR_REPAIR)),
    REVERSE(15,EnumSet.of(EventType.BACKING), null),
    TRAILER(16, EnumSet.of(EventType.TRAILER_DATA, EventType.TRAILER_PROGRAMMED), null),
    CONDITIONAL(17,
                EnumSet.of(EventType.BATTERY_VOLTAGE, EventType.ENGINE_TEMP, EventType.TRANSMISSION_TEMP, EventType.DPF_FLOW_RATE,EventType.OIL_PRESSURE),
                EnumSet.of(AlertMessageType.ATTR_BATTERY_VOLTAGE,AlertMessageType.ATTR_ENGINE_TEMP,AlertMessageType.ATTR_TRANSMISSION_TEMP,AlertMessageType.ATTR_DPF_FLOW_RATE,AlertMessageType.ATTR_OIL_PRESSURE)),
    IGNITION_OFF(19,EnumSet.of(EventType.RED_STOP,EventType.AMBER_WARNING,EventType.PROTECT,EventType.MALFUNCTION_INDICATOR_LAMP), EnumSet.of(AlertMessageType.ATTR_CHECK_ENGINE,AlertMessageType.ATTR_RED_STOP,AlertMessageType.ATTR_AMBER_WARNING,AlertMessageType.ATTR_PROTECT,AlertMessageType.ATTR_MALFUNCTION_INDICATOR_LAMP)),
    PREVENTATIVE_MAINTENANCE(18,  EnumSet.of(EventType.ATTR_ENGINE_HOURS, EventType.ODOMETER), EnumSet.of(AlertMessageType.ATTR_ENGINE_HOURS, AlertMessageType.ATTR_ODOMETER)),
//    NOTIFICATION_MAINTENANCE(19,EnumSet.of(EventType.RED_STOP,EventType.AMBER_WARNING,EventType.PROTECT),
//    EnumSet.of(AlertMessageType.ATTR_RED_STOP,AlertMessageType.ATTR_AMBER_WARNING,AlertMessageType.ATTR_PROTECT));
    FIRST_MOVE_FORWARD(20, EnumSet.of(EventType.FIRST_MOVE_FORWARD), EnumSet.of(AlertMessageType.ALERT_FIRST_MOVE_FORWARD)),
    TWO_HOURS_BREAK(21,
            EnumSet.of(EventType.TWO_HOURS_BREAK),
            EnumSet.of(AlertMessageType.ALERT_TYPE_TWO_HOURS_BREAK));
    
    private int code;
    private Set<EventType> eventTypeSet;
    private Set<AlertMessageType> alertMessageTypeSet;
    
    private EventSubCategory(int code)
    {
        this.code = code;
    }
    public Set<AlertMessageType> getAlertMessageTypeSet() {
        return alertMessageTypeSet;
    }
    private EventSubCategory(int code, Set<EventType> eventTypeSet, Set<AlertMessageType> alertMessageTypeSet)
    {
        this.code = code;
        this.eventTypeSet = eventTypeSet;
        this.alertMessageTypeSet = alertMessageTypeSet;
        if(this.alertMessageTypeSet == null)
            this.alertMessageTypeSet = Collections.emptySet();
    }

    private static final Map<Integer, EventSubCategory> lookup = new HashMap<Integer, EventSubCategory>();
    static
    {
        for (EventSubCategory p : EnumSet.allOf(EventSubCategory.class))
        {
            lookup.put(p.code, p);
        }
    }

    private static final Map<AlertMessageType, EventSubCategory> lookupByAlertMessageType = new HashMap<AlertMessageType, EventSubCategory>();
    static
    {
        for (EventSubCategory p : EnumSet.allOf(EventSubCategory.class))
        {
            if (p.alertMessageTypeSet != null){
                for (AlertMessageType amt : p.alertMessageTypeSet){
                    lookupByAlertMessageType.put(amt, p);
                }
            }
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
    public static EventSubCategory valueForAlertMessageType(AlertMessageType alertMessageType){
        return lookupByAlertMessageType.get(alertMessageType);
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
