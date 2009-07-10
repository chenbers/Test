package com.inthinc.pro.service.note;

import java.util.EnumSet;

public enum Heading{
    NORTH(0,0,30),
    NORTH_EAST(1,31,70),
    EAST(2,71,120),
    SOUTH_EAST(3,121,160),
    SOUTH(4,161,200),
    SOUTH_WEST(5,201,250),
    WEST(6,251,290),
    NORTH_WEST(7,291,360);
    
    private Integer code;
    private int minDegrees;
    private int maxDegrees;
    
    private Heading(int code,int minDegrees,int maxDegrees){
        this.code = code;
        this.minDegrees = minDegrees;
        this.maxDegrees = maxDegrees;
    }
    
    public static Heading valueOf(int degrees){
        for (Heading p : EnumSet.allOf(Heading.class))
        {
            if(degrees >= p.minDegrees && degrees <= p.maxDegrees){
                return p;
            }
        }
        
        return null;
    }
    
    public int getCode(){
        return code;
    }
    
    
}
