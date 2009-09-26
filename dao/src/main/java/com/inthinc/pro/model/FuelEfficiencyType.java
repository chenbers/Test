package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum FuelEfficiencyType implements BaseEnum {
	
    MPG_US(1) 	{@Override public Number convertFromMPG(Number milesPerGallon) {return milesPerGallon;}},
    MPG_UK(2) 	{@Override public Number convertFromMPG(Number milesPerGallon) {return milesPerGallon.doubleValue()*1.2;}},
    KMPL(3) 	{@Override public Number convertFromMPG(Number milesPerGallon) {return milesPerGallon.doubleValue()*0.42514;}}, 
    LP100KM(4) 	{@Override public Number convertFromMPG(Number milesPerGallon) {return milesPerGallon.equals(0)?0.0:100.0/( milesPerGallon.doubleValue() * 0.42514);}} ;
    
    private int code;
    
    private FuelEfficiencyType(int code) {
        this.code = code;
    }

    private static final Map<Integer, FuelEfficiencyType> lookup = new HashMap<Integer, FuelEfficiencyType>();
    static {
        for (FuelEfficiencyType type : EnumSet.allOf(FuelEfficiencyType.class)) {
            lookup.put(type.code, type);
        }
    }

    public static FuelEfficiencyType valueOf(Integer code) {
        return lookup.get(code);
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getDeclaringClass().getSimpleName());
    	sb.append(".");
        sb.append(this.name());
        return sb.toString();
    }
    
    public abstract Number convertFromMPG(Number milesPerGallon);
}
