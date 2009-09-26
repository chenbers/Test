package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum VehicleType implements BaseEnum
{
    LIGHT(0, "Light"), MEDIUM(1, "Medium"), HEAVY(2, "Heavy");

    private int    code;
    private String description;

    private VehicleType(int code, String description)
    {
        this.code = code;
        this.description = description;
    }

    @Override
    public Integer getCode()
    {
        return this.code;
    }

    public String getDescription()
    {
        return this.description;
    }

    private static final HashMap<Integer, VehicleType> lookup = new HashMap<Integer, VehicleType>();
    static
    {
        for (VehicleType p : EnumSet.allOf(VehicleType.class))
        {
            lookup.put(p.code, p);
        }
    }

    public static VehicleType valueOf(Integer code)
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
