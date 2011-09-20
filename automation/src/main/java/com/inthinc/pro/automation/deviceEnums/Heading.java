package com.inthinc.pro.automation.deviceEnums;

import java.util.EnumSet;

import com.inthinc.pro.automation.device_emulation.Distance_Calc;

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
    
    private Integer direction;
    private Integer min, max;
    
    private Heading(int direction){
        this.direction = direction;
        min = direction - 15;
        max = direction + 15;
        if (min < 0){
            min = 360 - 15;
        }
    }
    
    public Integer getHeading(){
        return direction;
    }

    public Heading getHeading(Double last_lat, Double last_lng, Double latitude, Double longitude){
        Distance_Calc calculator = new Distance_Calc();
        Integer direction = calculator.get_heading(last_lat, last_lng, latitude, longitude);
        for (Heading heading : EnumSet.allOf(Heading.class)){
            if (direction > heading.min && direction < heading.max){
                return heading;
            }
        }
        return Heading.NORTH;
    }

}
