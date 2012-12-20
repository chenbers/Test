package com.inthinc.pro.automation.models;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum HazardStatus implements BaseEnum {
    ACTIVE(1, "ACTIVE"),
    EXPIRED(2, "EXPIRED"),
    DELETED(3, "DELETED");

    private String description;
    private int code;

    private HazardStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    private static final Map<Integer, HazardStatus> lookup = new HashMap<Integer, HazardStatus>();
    static {
        for (HazardStatus p : EnumSet.allOf(HazardStatus.class)) {
            lookup.put(p.code, p);
        }
    }

    public Integer getCode() {
        return this.code;
    }

    public static HazardStatus valueOf(Integer code) {
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
