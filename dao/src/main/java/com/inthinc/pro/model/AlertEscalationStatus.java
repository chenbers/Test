package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum AlertEscalationStatus implements BaseEnum
{
    
    //1 new
    //2 sent
    //3 escalated awaiting acknowledge
    //4 escalated acknowledged
    //5 canceled 
    //6 in progress
    
    NONE(0),
    NEW(1),
    SENT(2),
    ESCALATED_AWAITING_ACKNOWLEDGE(3),
    ESCALATED_ACKNOWLEDGED(4),
    CANCELED(5);
    
    private int code;

    private AlertEscalationStatus(int code)
    {
        this.code = code;
    }

    private static final Map<Integer, AlertEscalationStatus> lookup = new HashMap<Integer, AlertEscalationStatus>();
    static
    {
        for (AlertEscalationStatus p : EnumSet.allOf(AlertEscalationStatus.class))
        {
            lookup.put(p.code, p);
        }
    }

    public Integer getCode()
    {
        return this.code;
    }

    public static AlertEscalationStatus valueOf(Integer code)
    {
        return lookup.get(code);
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
