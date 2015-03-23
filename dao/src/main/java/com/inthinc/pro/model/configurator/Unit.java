package com.inthinc.pro.model.configurator;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.inthinc.pro.model.BaseEnum;

public enum Unit implements BaseEnum
{
    NO_UNITS(0,""),SECONDS(1,"Seconds"),MINUTES(2,"Minutes"),HOURS(3,"Hours"),
    MILES(4,"Miles"),FEET(5,"Feet"),MPH(6,"Mph"),KPH(7,"Kph"),
    TOGGLE(8,"off/on"),RPM(9,"RPM"),VOLTS(10,"Volts"),
    G(11,"G"),DAYS(12,"Days"),DEGREES(13,"Degrees"),DB(14,"dB"), FAHRENHEIT(15,"Fahrenheit"),
    KPA(16,"kiloPa"),LPM(17,"LitrePerMinute"),CELSIUS(18,"Celsius"),KILOMETRES(19,"Kilometres");
    
    private int code;
    private String name;
    
    private static final Map<Integer, Unit> lookup = new HashMap<Integer, Unit>();
    static
    {
        for (Unit p : EnumSet.allOf(Unit.class))
        {
            lookup.put(p.code, p);
        }
    }
    public static Unit valueOf(Integer code)
    {
        return lookup.get(code);
    }
   
    Unit(int code, String name){
        
        this.code = code;
        this.name = name;
    }
    public Integer getCode(){
        
        return code;
    }
    public String getName(){
        
        return name;
    }
  
}
