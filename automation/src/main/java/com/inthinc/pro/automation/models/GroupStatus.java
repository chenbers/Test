package com.inthinc.pro.automation.models;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum GroupStatus implements BaseEnum
{
    GROUP_NEW(0, "GROUP_NEW"),
    GROUP_ACTIVE(1, "GROUP_ACTIVE"),
    GROUP_DISABLED(2, "GROUP_DISABLED"),
    GROUP_DELETED(3, "GROUP_DELETED");

    private String description;
    private int code;

    private GroupStatus(int code, String description)
    {
        this.code = code;
        this.description = description;
    }

    private static final Map<Integer, GroupStatus> lookup = new HashMap<Integer, GroupStatus>();
    static
    {
        for (GroupStatus p : EnumSet.allOf(GroupStatus.class))
        {
            lookup.put(p.code, p);
        }
    }

    public Integer getCode()
    {
        return this.code;
    }

    public static GroupStatus valueOf(Integer code)
    {
        return lookup.get(code);
    }

    @Override
    public String toString()
    {
        return this.description;
    }

}

