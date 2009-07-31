package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
public enum FuelEfficiencyType implements BaseEnum {
    
    MPG_US(1,   new FuelEfficiencyConverter(){
        
                    public Double convert(Number mpg){
                    
                        return mpg.doubleValue();
                    }
                }
    ), 
    
    MPG_UK(2,   new FuelEfficiencyConverter(){
        
                    public Double convert(Number mpg){
                    
                        return mpg.doubleValue()*1.2;
                    }
                }
    ), 
    KMPL(3,     new FuelEfficiencyConverter(){
        
                    public Double convert(Number mpg){
                    
                        return mpg.doubleValue() * 0.42514;
                    }
                }
    ), 
    LP100KM(4,  new FuelEfficiencyConverter(){
        
                    public Double convert(Number mpg){
                    
                        if (mpg.doubleValue() == 0) return new Double(0);
                        
                        return 100.0/( mpg.doubleValue() * 0.42514);
                    }
                }
    );
    
    private int code;

    private FuelEfficiencyConverter fuelEfficiencyConverter;
    
    private FuelEfficiencyType(int code, FuelEfficiencyConverter fuelEfficiencyConverter) {
        this.code = code;
        this.fuelEfficiencyConverter = fuelEfficiencyConverter;
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
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName());
        sb.append(".");
        sb.append(this.name());
        return sb.toString();
    }
    
    public Number convertFromMPG(Number milesPerGallon){
        
        return this.fuelEfficiencyConverter.convert(milesPerGallon);
    }

}
