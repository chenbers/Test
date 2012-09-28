package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ReportParamType implements BaseEnum {
    
    NONE(0, "NONE"), 
    DRIVER(1, "DRIVER"), 
    GROUPS(2, "GROUPS");

    private Integer code;
    private String description;


    private ReportParamType(Integer code, String description)
    {
        this.code = code;
        this.description = description;
    }

    private static final Map<Integer, ReportParamType> lookup = new HashMap<Integer, ReportParamType>();
    static
    {
        for (ReportParamType p : EnumSet.allOf(ReportParamType.class))
        {
            lookup.put(p.code, p);
        }
    }

    public Integer getCode()
    {
        return this.code;
    }

    public static ReportParamType valueOf(Integer code)
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
