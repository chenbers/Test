package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum EntityType implements BaseEnum
{

    ENTITY_GROUP(1, "ENTITY_GROUP"),
    ENTITY_DRIVER(2, "ENTITY_DRIVER"),
    ENTITY_VEHICLE(3, "ENTITY_VEHICLE");

    private String description;
    private int code;

    private EntityType(int code, String description)
    {
        this.code = code;
        this.description = description;
    }

    private static final Map<Integer, EntityType> lookup = new HashMap<Integer, EntityType>();
    static
    {
        for (EntityType p : EnumSet.allOf(EntityType.class))
        {
            lookup.put(p.code, p);
        }
    }

    public Integer getCode()
    {
        return this.code;
    }

    public static EntityType valueOf(Integer code)
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

