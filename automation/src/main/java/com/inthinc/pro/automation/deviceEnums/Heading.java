package com.inthinc.pro.automation.deviceEnums;

import java.util.EnumSet;

import com.inthinc.pro.automation.device_emulation.Distance_Calc;
import com.inthinc.pro.automation.models.GeoPoint;

public enum Heading {
    NORTH(0),
    NORTH_EAST(45),
    EAST(90),
    SOUTH_EAST(135),
    SOUTH(180),
    SOUTH_WEST(225),
    WEST(270),
    NORTH_WEST(315),
    ;
    
    private Integer min, max;
    private final Integer heading;
    
    private Heading(int heading){
        this.heading = heading/45;
        min = heading - 22;
        max = heading + 23;
        if (min < 0){
            min = 0;
        }
    }
    
    public Integer getHeading(){
        return heading;
    }

    public static Heading getHeading(GeoPoint start, GeoPoint stop){
        Integer direction = Distance_Calc.get_heading(start, stop);
        for (Heading heading : EnumSet.allOf(Heading.class)){
            if (direction >= heading.min && direction < heading.max){
                return heading;
            }
        }
        return Heading.NORTH;
    }
    
    @Override
    public String toString(){
        return name() + "(" + heading + ")";
    }

}
