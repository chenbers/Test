package com.inthinc.pro.automation.models;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum AccountHOSType implements BaseEnum {
    NONE(0, "NONE"), 
    HOS_SUPPORT(1, "HOS_SUPPORT"), 
    EUROPEAN_HOS_SUPPORT(2, "EUROPEAN_HOS_SUPPORT"); // this is a placeholder for now
    
    private String description;
    private int code;

    private AccountHOSType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    private static final Map<Integer, AccountHOSType> lookup = new HashMap<Integer, AccountHOSType>();
    static {
        for (AccountHOSType p : EnumSet.allOf(AccountHOSType.class)) {
            lookup.put(p.code, p);
        }
    }

    public Integer getCode() {
        return this.code;
    }

    public static AccountHOSType valueOf(Integer code) {
        return lookup.get(code);
    }

    @Override
    public String toString() {
        return description;
    }
}
