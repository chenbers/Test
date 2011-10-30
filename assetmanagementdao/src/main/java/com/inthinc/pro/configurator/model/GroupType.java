package com.inthinc.pro.configurator.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum GroupType
{
    FLEET(1), 
    DIVISION(2), 
    TEAM(3);

    private Integer code;

    private GroupType(Integer code)
    {
        this.code = code;
    }

    private static final Map<Integer, GroupType> lookup = new HashMap<Integer, GroupType>();
    static
    {
        for (GroupType p : EnumSet.allOf(GroupType.class))
        {
            lookup.put(p.code, p);
        }
    }

    public Integer getCode()
    {
        return this.code;
    }

    public static GroupType valueOf(Integer code)
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
