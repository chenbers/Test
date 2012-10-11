package com.inthinc.device.models;


public class SpeedValue implements OptionValue {

    private Integer value;
    
    public SpeedValue(Integer value) {
        this.value = value;
    }
    
    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }
    
    @Override
    public String toString() {
        return (value == null) ? null : value.toString();

    }

    @Override
    public String getName() {
        return null;
    }

    static public OptionValue valueOf(Integer value) {
        return new SpeedValue(value);
    }
    @Override
    public Boolean getBooleanValue() {
        return null;
    }

}
