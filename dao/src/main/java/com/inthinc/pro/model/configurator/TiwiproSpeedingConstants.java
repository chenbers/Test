package com.inthinc.pro.model.configurator;

public enum TiwiproSpeedingConstants {
    INSTANCE;
    
    public final int NUM_SPEEDS = 15;
    public final Integer DEFAULT_SPEED_SETTING = 0;    
    public final String DEFAULT_SPEED_SET = "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0";
    public final Integer[] SPEED_LIMITS = {5,10,15,20,25,30,35,40,45,50,55,60,65,70,75};
}
