package com.inthinc.device.models;


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
