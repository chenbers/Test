package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum AlertOption implements BaseEnum
{
    NONE(0),
    EMAIL_1(1),
    EMAIL_2(2),
    PHONE_1(3),
    PHONE_2(4),
    TEXT_MESSAGE_1(6),
    TEXT_MESSAGE_2(7);
    
    private int code;
    
    private AlertOption(int code){
        this.code = code;
    }
    
    private static final Map<Integer, AlertOption> lookup = new HashMap<Integer, AlertOption>();
    static
    {
        for (AlertOption p : EnumSet.allOf(AlertOption.class))
        {
            lookup.put(p.code, p);
        }
    }

    public Integer getCode()
    {
        return this.code;
    }
    
    public static AlertOption valueOf(Integer code)
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
