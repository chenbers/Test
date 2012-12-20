package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum ReportManagerDeliveryType implements BaseEnum
{
    ALL(0), EXCLUDE_TEAMS(1), EXCLUDE_DIVISIONS(2);

    private int    code;

    private ReportManagerDeliveryType(int code)
    {
        this.code = code;
    }

    private static final Map<Integer, ReportManagerDeliveryType> lookup = new HashMap<Integer, ReportManagerDeliveryType>();
    static
    {
        for (ReportManagerDeliveryType p : EnumSet.allOf(ReportManagerDeliveryType.class))
        {
            lookup.put(p.code, p);
        }
    }

    public Integer getCode()
    {
        return this.code;
    }

    public static ReportManagerDeliveryType valueOf(Integer code)
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

