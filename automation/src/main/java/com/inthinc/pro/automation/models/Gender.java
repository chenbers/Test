package com.inthinc.pro.automation.models;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum Gender implements BaseEnum {
    NOT_SPECIFIED(0, ""),
    MALE(1, "MALE"),
    FEMALE(2, "FEMALE");

    private int code;
    private String description;

    private Gender(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    private static final Map<Integer, Gender> lookup = new HashMap<Integer, Gender>();
    static {
        for (Gender p : EnumSet.allOf(Gender.class)) {
            lookup.put(p.code, p);
        }
    }

    public static Gender valueOf(Integer code) {
        return lookup.get(code);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
        sb.append(".");
        sb.append(this.name());
        return sb.toString();
    }
}
