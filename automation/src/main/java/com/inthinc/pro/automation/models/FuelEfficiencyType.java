package com.inthinc.pro.automation.models;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum FuelEfficiencyType implements BaseEnum {
	
    MPG_US(1) 	{@Override public Number convertFromMPG(Number milesPerGallon) {return milesPerGallon==null?null:MathUtil.round(milesPerGallon,1);}},
    MPG_UK(2) 	{@Override public Number convertFromMPG(Number milesPerGallon) {return milesPerGallon==null?null:MathUtil.round(milesPerGallon.doubleValue()*MPGTOMPGUK,1);}},
    KMPL(3) 	{@Override public Number convertFromMPG(Number milesPerGallon) {return milesPerGallon==null?null:MathUtil.round(milesPerGallon.doubleValue()*MPGTOKPL,1);}}, 
    LP100KM(4) 	{@Override public Number convertFromMPG(Number milesPerGallon) {return milesPerGallon==null?null:(milesPerGallon.doubleValue()==0)?0.0f:MathUtil.round(100.0f/( milesPerGallon.doubleValue() * MPGTOKPL),1);}} ;
    
	private static final Float MPGTOKPL = 0.42514f;
	private static final Float MPGTOMPGUK = 1.2f;

    private int code;
    
    private FuelEfficiencyType(int code) {
        this.code = code;
    }

    private static final Map<Integer, FuelEfficiencyType> lookup = new DefaultedMap<Integer,FuelEfficiencyType>(new HashMap<Integer, FuelEfficiencyType>(),
    																				FuelEfficiencyType.MPG_US);
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
