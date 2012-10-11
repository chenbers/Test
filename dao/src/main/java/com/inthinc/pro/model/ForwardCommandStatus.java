package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum ForwardCommandStatus implements BaseEnum
{
    STATUS_ALL(0, "STATUS_ALL"),
    STATUS_QUEUED(1, "STATUS_QUEUED"),
    STATUS_SENT(2, "STATUS_SENT"),
    STATUS_RECEIVED(3, "STATUS_RECEIVED"),
    STATUS_ACKNOWLEDGED(4, "STATUS_ACKNOWLEDGED"),
    STATUS_UNSUPPORTED(5, "STATUS_UNSUPPORTED"),
    STATUS_BAD_DATA(6, "STATUS_BAD_DATA"),
    STATUS_REMOVED(7, "STATUS_REMOVED");


    private String description;
    private int code;

    private ForwardCommandStatus(int code, String description)
    {
        this.code = code;
        this.description = description;
    }

    private static final Map<Integer, ForwardCommandStatus> lookup = new HashMap<Integer, ForwardCommandStatus>();
    static
    {
        for (ForwardCommandStatus p : EnumSet.allOf(ForwardCommandStatus.class))
        {
            lookup.put(p.code, p);
        }
    }

    public Integer getCode()
    {
        return this.code;
    }

    public static ForwardCommandStatus valueOf(Integer code)
    {
        return lookup.get(code);
    }

    @Override
    public String toString()
    {
        return this.description;
    }

}

