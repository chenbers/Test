package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum TrailerEntryMethod implements BaseEnum {
    DETECTED(1, "DETECTED"),
    ENTERED(2, "ENTERED");
    
    private String description;
    private int code;
    
    private TrailerEntryMethod(int code, String description) {
        this.code = code;
        this.description = description;
    }
    
    private static final Map<Integer, TrailerEntryMethod> lookup = new HashMap<Integer, TrailerEntryMethod>();
    static {
        for (TrailerEntryMethod tem : EnumSet.allOf(TrailerEntryMethod.class)) {
            lookup.put(tem.code, tem);
        }
    }
    
    public Integer getCode() {
        return this.code;
    }
    
    public static TrailerEntryMethod valueOf(Integer code) {
        return lookup.get(code);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
        sb.append(".");
        sb.append(this.name());
        return sb.toString();
    }
    
    public String getDescription() {
        return description;
    }
    
}
