package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum MeasurementType implements BaseEnum {
    ENGLISH(1, EnumSet.of(FuelEfficiencyType.MPG_UK,FuelEfficiencyType.MPG_US)), 
    METRIC(2,EnumSet.of(FuelEfficiencyType.KMPL,FuelEfficiencyType.LP100KM));
    
    private int code;
    private EnumSet<FuelEfficiencyType> validFuelEfficiencyTypes;

    private MeasurementType(int code, EnumSet<FuelEfficiencyType> validFuelEfficiencyTypes) {
        this.code = code;
        this.validFuelEfficiencyTypes = validFuelEfficiencyTypes;
    }

    private static final Map<Integer, MeasurementType> lookup = new HashMap<Integer, MeasurementType>();
    static {
        for (MeasurementType type : EnumSet.allOf(MeasurementType.class)) {
            lookup.put(type.code, type);
        }
    }

    public static MeasurementType valueOf(Integer code) {
        return lookup.get(code);
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
        sb.append(".");
        sb.append(this.name());
        return sb.toString();
    }
    
    public EnumSet<FuelEfficiencyType> getValidFuelEfficiencyTypes(){
    	
    	return validFuelEfficiencyTypes;
    }
}