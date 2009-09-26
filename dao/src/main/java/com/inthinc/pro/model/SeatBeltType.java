package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum SeatBeltType implements BaseEnum{
    OFF(0), ON(1), UNKOWN(2);

    int code;

    private SeatBeltType(int code) {
        this.code = code;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    private static final Map<Integer, SeatBeltType> lookup = new HashMap<Integer, SeatBeltType>();
    static {
        for (SeatBeltType p : EnumSet.allOf(SeatBeltType.class)) {
            lookup.put(p.code, p);
        }
    }

    public static SeatBeltType valueOf(Integer code) {
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
