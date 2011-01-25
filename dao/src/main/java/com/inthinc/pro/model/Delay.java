package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum Delay implements BaseEnum
{
    MINUTES05(5*60,  "MINUTES05"),
    MINUTES10(10*60,  "MINUTES10"),
    MINUTES15(15*60, "MINUTES15"),
    MINUTES30(30*60, "MINUTES30");

    private String description;
    private int code;

    private Delay(int code, String description)
    {
        this.code = code;
        this.description = description;
    }

    private static final Map<Integer, Delay> lookup = new HashMap<Integer, Delay>();
    static
    {
        for (Delay p : EnumSet.allOf(Delay.class))
        {
            lookup.put(p.code, p);
        }
    }

    public Integer getCode()
    {
        return this.code;
    }

    public static Delay valueOf(Integer code)
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
