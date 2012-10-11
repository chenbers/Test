package com.inthinc.pro.automation.models;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum DeviceStatus implements BaseEnum
{
    NEW(0, "NEW"), ACTIVE(1, "ACTIVE"), INACTIVE(2, "INACTIVE"), DELETED(3, "DELETED");

    private int    code;
    private String description;

    private DeviceStatus(int code, String description)
    {
        this.code = code;
        this.description = description;
    }

    @Override
    public Integer getCode()
    {
        return code;
    }

    public String getDescription()
    {
        return description;
    }

    private static final Map<Integer, DeviceStatus> lookup = new HashMap<Integer, DeviceStatus>();
    static
    {
        for (DeviceStatus p : EnumSet.allOf(DeviceStatus.class))
        {
            lookup.put(p.code, p);
        }
    }

    public static DeviceStatus valueOf(Integer code)
    {
        return lookup.get(code);
    }
    @Override 
    public String toString(){
    	return this.getClass().getSimpleName()+"."+name();
    }
}
