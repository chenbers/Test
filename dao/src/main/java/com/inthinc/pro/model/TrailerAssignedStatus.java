package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum TrailerAssignedStatus implements BaseEnum {
    ASSIGNED(1, "ASSIGNED"),
    NOT_ASSIGNED(0, "NOT_ASSIGNED");
    
    private String description;
    private int code;
    
    private TrailerAssignedStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }
    
    private static final Map<Integer, TrailerAssignedStatus> lookup = new HashMap<Integer, TrailerAssignedStatus>();
    static {
        for (TrailerAssignedStatus tas : EnumSet.allOf(TrailerAssignedStatus.class)) {
            lookup.put(tas.code, tas);
        }
    }
    
    public Integer getCode() {
        return this.code;
    }
    
    public static TrailerAssignedStatus valueOf(Integer code) {
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
