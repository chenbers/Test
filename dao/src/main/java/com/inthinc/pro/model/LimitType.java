package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum LimitType implements BaseEnum {
    COUNT(1, "Count"),
    TIME(2, "Time");
    
    private String description;
    private int code;
    private static final Map<Integer, LimitType> lookup = new HashMap<Integer, LimitType>();
    static
    {
        for (LimitType p : EnumSet.allOf(LimitType.class))
        {
            lookup.put(p.code, p);
        }
    }
    private LimitType(int code, String description)
    {
        this.code = code;
        this.description = description;
    }
    public static LimitType valueOf(Integer code)
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
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
