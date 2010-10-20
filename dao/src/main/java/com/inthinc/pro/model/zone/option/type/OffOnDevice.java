package com.inthinc.pro.model.zone.option.type;

public enum OffOnDevice implements OptionValue {
    OFF(0, "OFF"),
    ON(1, "ON"),
    DEVICE(2, "DEVICE");
    
    private int code;
    private String name;
    
    private OffOnDevice(int code, String name)
    {
        this.code = code;
        this.name = name;
    }
    @Override
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    @Override
    public Integer getValue() {
        return getCode();
    }
    
    @Override
    public String toString()
    {
        return getName();
    }
}
