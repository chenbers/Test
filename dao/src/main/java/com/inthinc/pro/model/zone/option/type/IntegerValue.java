package com.inthinc.pro.model.zone.option.type;


public class IntegerValue implements OptionValue {

    private Integer value;
    
    public IntegerValue(Integer value) {
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
        return new IntegerValue(value);
    }

}
