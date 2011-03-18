package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum PreferenceLevelOption implements BaseEnum {
    //currently used for password strength options...
    NONE(0),
    WARN(1),
    REQUIRE(2);

    private int code;

    private PreferenceLevelOption(int code) {
        this.code = code;
    }

    private static final Map<Integer, PreferenceLevelOption> lookup = new HashMap<Integer, PreferenceLevelOption>();
    static {
        for (PreferenceLevelOption p : EnumSet.allOf(PreferenceLevelOption.class)) {
            lookup.put(p.code, p);
        }
    }

    public Integer getCode() {
        return this.code;
    }

    public static PreferenceLevelOption valueOf(Integer code) {
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
