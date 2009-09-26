package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum Occurrence implements BaseEnum
{
    DAILY(0,"Daily",Status.ACTIVE),
    DAILY_CUSTOM(1,"Daily - Customizable",Status.INACTIVE),
    WEEKLY(2,"Weekly",Status.ACTIVE),
    MONTHLY(3,"Monthly",Status.ACTIVE);
    
    private Integer code;
    private String description;
    private Status status;
    
    private Occurrence(Integer code,String description,Status status)
    {
        this.code = code;
        this.description = description;
        this.status = status;
    }
    
    private static final Map<Integer, Occurrence> lookup = new HashMap<Integer, Occurrence>();
    static
    {
        for (Occurrence p : EnumSet.allOf(Occurrence.class))
        {
            lookup.put(p.code, p);
        }
    }

    public Integer getCode()
    {
        return this.code;
    }

    public static Occurrence valueOf(Integer code)
    {
        return lookup.get(code);
    }

    public String getDescription()
    {
        return description;
    }
    
    public Status getStatus()
    {
        return this.status;
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
