package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum AlertMessageDeliveryType implements BaseEnum
{
    PHONE(1, "PHONE"),
    TEXT_MESSAGE(2, "TEXT_MESSAGE"),
    EMAIL(3, "EMAIL");

    private String description;
    private int code;

    private AlertMessageDeliveryType(int code, String description)
    {
        this.code = code;
        this.description = description;
    }

    private static final Map<Integer, AlertMessageDeliveryType> lookup = new HashMap<Integer, AlertMessageDeliveryType>();
    static
    {
        for (AlertMessageDeliveryType p : EnumSet.allOf(AlertMessageDeliveryType.class))
        {
            lookup.put(p.code, p);
        }
    }

    public Integer getCode()
    {
        return this.code;
    }

    public static AlertMessageDeliveryType valueOf(Integer code)
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

    public String getDescription()
    {
        return description;
    }


}
