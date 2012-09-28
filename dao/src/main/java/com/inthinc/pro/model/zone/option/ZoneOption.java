package com.inthinc.pro.model.zone.option;

import java.io.Serializable;

import com.inthinc.pro.model.zone.option.type.OptionValue;

public class ZoneOption implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private ZoneAvailableOption option;
    private OptionValue value;
    
    public ZoneOption()
    {
    }
    public ZoneOption(ZoneAvailableOption option, OptionValue value) {
        super();
        this.option = option;
        this.value = value;
    }
    public ZoneAvailableOption getOption() {
        return option;
    }
    public void setOption(ZoneAvailableOption option) {
        this.option = option;
    }
    public OptionValue getValue() {
        return value;
    }
    public void setValue(OptionValue value) {
        this.value = value;
    }
    
}
