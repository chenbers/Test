package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum EntityType implements BaseEnum
{

    ENTITY_GROUP(1, "ENTITY_GROUP", true),
    ENTITY_DRIVER(2, "ENTITY_DRIVER", false),
    ENTITY_VEHICLE(3, "ENTITY_VEHICLE", false),
    ENTITY_GROUP_LIST(4, "ENTITY_GROUP_LIST", true),
    ENTITY_GROUP_LIST_OR_DRIVER(5, "ENTITY_GROUP_LIST_OR_DRIVER", true),
    ENTITY_GROUP_OR_DRIVER(6, "ENTITY_GROUP_OR_DRIVER", true), 
    ENTITY_GROUP_LIST_AND_IFTA(7, "ENTITY_GROUP_LIST_AND_IFTA", true),
    ENTITY_GROUP_AND_EXPIRED(8, "ENTITY_GROUP_AND_EXPIRED", true),
    ENTITY_DEVICE(9, "ENTITY_DEVICE", false),
    ENTITY_INDIVIDUAL_DRIVER(10, "ENTITY_INDIVIDUAL_DRIVER", true);

    private String description;
    private int code;
    private boolean groupType;

    private EntityType(int code, String description, boolean groupType)
    {
        this.code = code;
        this.description = description;
        this.groupType = groupType;
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
    public boolean isGroupType() {
        return groupType;
    }

    public void setGroupType(boolean groupType) {
        this.groupType = groupType;
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

