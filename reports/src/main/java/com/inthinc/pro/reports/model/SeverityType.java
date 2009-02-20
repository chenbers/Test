package com.inthinc.pro.reports.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import com.inthinc.pro.model.BaseEnum;

public enum SeverityType implements BaseEnum
{
    LOW     ("Low", 0, 1, 2),
    MEDIUM  ("Medium", 3, 4),
    EXTREME ("Extreme", 5);

    private Integer[] codes;
    private String description;
    private static final Map<Integer, SeverityType> lookup = new HashMap<Integer, SeverityType>();
    
    private SeverityType(String description, Integer... codes)
    {
        this.codes = codes;
        this.description = description;
    }
    
    public static SeverityType valueOf(Integer code)
    {
        return lookup.get(code);
    }
    
    static
    {
        for (SeverityType severity : EnumSet.allOf(SeverityType.class))
        {
            for(Integer i: severity.codes)
            {
                lookup.put(i, severity);
            }
        }
    }

    public String getDescription()
    {
        return this.description;
    }
    
    @Override
    public Integer getCode()
    {
        return null;
    }
    
    @Override
    public String toString()
    {
        return this.description;
    }
}


