package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum AlertSentStatus implements BaseEnum
{
    NONE(0),
    SENT(1),
    PENDING(2),
    CANCELED(3);

    private int code;

    private AlertSentStatus(int code)
    {
        this.code = code;
    }

    private static final Map<Integer, AlertSentStatus> lookup = new HashMap<Integer, AlertSentStatus>();
    static
    {
        for (AlertSentStatus p : EnumSet.allOf(AlertSentStatus.class))
        {
            lookup.put(p.code, p);
        }
    }

    public Integer getCode()
    {
        return this.code;
    }

    public static AlertSentStatus valueOf(Integer code)
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
