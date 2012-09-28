package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum AutoLogoff implements BaseEnum
{
    OFF(0, "AutoLogoff.DISABLE", 0),
    ON(1, "AutoLogoff.ENABLE", 600);
    
    Integer code;
    String description;
    Integer forwardCommandSetting;
    private AutoLogoff(Integer code, String description, Integer forwardCommandSetting)
    {
    
    	this.code = code;
    	this.description = description;
    	this.forwardCommandSetting = forwardCommandSetting;
    }
    
    private static final Map<Integer, AutoLogoff> lookup = new HashMap<Integer, AutoLogoff>();
    static
    {
        for (AutoLogoff p : EnumSet.allOf(AutoLogoff.class))
        {
            lookup.put(p.code, p);
        }
    }

    public Integer getCode()
    {
        return this.code;
    }
    
    public static AutoLogoff valueOf(Integer code)
    {
        return lookup.get(code);
    }
    
    public Integer getForwardCommandSetting()
    {
    	return this.forwardCommandSetting;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public String toString()
    {
    	return description;
    }

}
