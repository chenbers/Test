package com.inthinc.device.models;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import com.inthinc.pro.automation.models.BaseEnum;

@XmlRootElement
public enum TripStatus implements BaseEnum {
    TRIP_IN_PROGRESS(0, "TRIP_IN_PROGRESS"),
    TRIP_COMPLETED(1, "TRIP_COMPLETED");

    private String description;
    private int code;

    private TripStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    private static final Map<Integer, TripStatus> lookup = new HashMap<Integer, TripStatus>();
    static {
        for (TripStatus p : EnumSet.allOf(TripStatus.class)) {
            lookup.put(p.code, p);
        }
    }

    public Integer getCode() {
        return this.code;
    }

    public static TripStatus valueOf(Integer code) {
        return lookup.get(code);
    }

    @Override
    public String toString() {
        return this.description;
    }

}
