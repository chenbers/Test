package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum TimeFormatType implements BaseEnum {

    DECIMAL_FORMAT(0, "DECIMAL_FORMAT"),
    TOTAL_MINUTES(1, "TOTAL_MINUTES"),
    HOURS(2, "HOURS");

    private Integer code;
    private String description;


    private TimeFormatType(Integer code, String description)
    {
        this.code = code;
        this.description = description;
    }

    private static final Map<Integer, TimeFormatType> lookup = new HashMap<Integer, TimeFormatType>();
    static
    {
        for (TimeFormatType p : EnumSet.allOf(TimeFormatType.class))
        {
            lookup.put(p.code, p);
        }
    }

    public Integer getCode()
    {
        return this.code;
    }

    public static TimeFormatType valueOf(Integer code)
    {
        return lookup.get(code);
    }
    
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public String toString()
    {
        return description;
    }


}
