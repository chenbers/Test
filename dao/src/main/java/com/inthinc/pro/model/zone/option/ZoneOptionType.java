package com.inthinc.pro.model.zone.option;

import com.inthinc.pro.model.zone.option.type.OffOn;
import com.inthinc.pro.model.zone.option.type.OffOnDevice;
import com.inthinc.pro.model.zone.option.type.ZoneVehicleType;

public enum ZoneOptionType {
    
    SPEED(Integer.class),
    OFF_ON(OffOn.class),
    OFF_ON_DEVICE(OffOnDevice.class),
    VEHICLE_TYPE(ZoneVehicleType.class);
    
    Class clazz;
    private ZoneOptionType(Class clazz)
    {
        this.clazz = clazz;
    }
    
//    public OptionValue getOptionValue(Integer value)
//    {
//        
//    }
}
