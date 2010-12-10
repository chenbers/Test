package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum AlertMessageType implements BaseEnum
{
    ALERT_TYPE_SPEEDING(1),
    ALERT_TYPE_AGGRESSIVE_DRIVING(2),//Not used any more
    ALERT_TYPE_SEATBELT(3),
    ALERT_TYPE_ENTER_ZONE(4),
    ALERT_TYPE_EXIT_ZONE(5),
    ALERT_TYPE_LOW_BATTERY(6),
    ALERT_TYPE_UNKNOWN(7),
    ALERT_TYPE_TAMPERING(8),
    ALERT_TYPE_CRASH(9),
    ALERT_TYPE_HARD_BRAKE(10),
    ALERT_TYPE_HARD_ACCEL(11),
    ALERT_TYPE_HARD_TURN(12),
    ALERT_TYPE_HARD_BUMP(13),
    ALERT_TYPE_NO_DRIVER(14),
    ALERT_TYPE_OFF_HOURS(15),
    ALERT_TYPE_TEXT_MESSAGE_RECEIVED(16),
    
    ALERT_TYPE_PARKING_BRAKE(17),
    ALERT_TYPE_PANIC(18),   
    ALERT_TYPE_MAN_DOWN(19),   
    ALERT_TYPE_MAN_DOWN_OK(20),   
    ALERT_TYPE_IGNITION_ON(21),   
//    ALERT_TYPE_LOW_BATTERY_POTENTIAL_TAMPERING(22),   
    ALERT_TYPE_HOS_DOT_STOPPED(23),
    ALERT_TYPE_HOS_NO_HOURS_REMAINING(24),
    ALERT_TYPE_WIRELINE_ALARM(25),
    ALERT_TYPE_DSS_MICROSLEEP(26),
    ALERT_TYPE_INSTALL(27),
    ALERT_TYPE_FIRMWARE_CURRENT(28),
    ALERT_TYPE_LOCATION_DEBUG(29),
    ALERT_TYPE_QSI_UPDATED(30),
    ALERT_TYPE_WITNESS_UPDATED(31),
    ALERT_TYPE_ZONES_CURRENT(32),
    ALERT_TYPE_NO_INTERNAL_THUMB_DRIVE(33),
    ALERT_TYPE_WITNESS_HEARTBEAT_VIOLATION(34); 
    
    private int code;

    private AlertMessageType(int code)
    {
        this.code = code;
    }

    private static final Map<Integer, AlertMessageType> lookup = new HashMap<Integer, AlertMessageType>();
    static
    {
        
        for (AlertMessageType p : EnumSet.allOf(AlertMessageType.class))
        {
            lookup.put(p.code, p);
        }
    }

    private static final Map<Long, AlertMessageType> powerOfTwoLookup = new HashMap<Long, AlertMessageType>();
    static
    {
        
        for (AlertMessageType p : EnumSet.allOf(AlertMessageType.class))
        {
            powerOfTwoLookup.put(p.getBitMask(), p);
        }
    }
    public Integer getCode()
    {
        return this.code;
    }
    public Long getBitMask(){
        return new Double(Math.pow( 2, code-1)).longValue();
        
//        return  new Long(1 << (code-1));
    }
    public static AlertMessageType valueOf(Integer code)
    {
        return lookup.get(code);
    }
    public static int getMax(){
        return EnumSet.allOf(AlertMessageType.class).size();
    }
    public static Set<AlertMessageType> getAlertMessageTypes(Long alertTypeMask){
        
        Set<AlertMessageType> alertMessageTypes = EnumSet.noneOf(AlertMessageType.class);
        if (alertTypeMask == null) return alertMessageTypes;
        for(AlertMessageType amt : EnumSet.allOf(AlertMessageType.class)){
            
            long bitValue = amt.getBitMask();
            if((alertTypeMask.longValue() & bitValue) == bitValue){
                alertMessageTypes.add(amt); 
            }
        }
        return alertMessageTypes;
    }
    public static  Long convertTypes(Set<AlertMessageType> alertMessageTypes){
        Long alertMessageTypesMask = new Long(0);
        if(alertMessageTypes != null){
            for(AlertMessageType amt : alertMessageTypes){
                
                long bitValue = amt.getBitMask();
                alertMessageTypesMask = alertMessageTypesMask.longValue()  | bitValue;
            }
        }
        return alertMessageTypesMask;
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
