package com.inthinc.pro.model.zone.option.type;

public enum ZoneVehicleType implements OptionValue {
    ALL(0, "ALL"),
    LIGHT(1, "LIGHT"),
    HEAVY(2, "HEAVY");
    
    private int code;
    private String name;
    private ZoneVehicleType(int code, String name)
    {
        this.code = code;
        this.name = name;
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    @Override
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public Integer getValue() {
        return getCode();
    }
    @Override
    public String toString() {
        return name;
    }
}
