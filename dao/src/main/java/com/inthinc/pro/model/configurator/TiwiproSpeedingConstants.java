package com.inthinc.pro.model.configurator;

public enum TiwiproSpeedingConstants {
    INSTANCE;
    
    public final int NUM_SPEEDS = 15;
    public final Integer[] DEFAULT_SPEED_SETTING = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};    
//    public final Integer[] DEFAULT_SPEED_SETTING = {5, 10, 15, 20, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25};    
    public final String DEFAULT_SPEED_SET = "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0";
    public final Integer[] SPEED_LIMITS = {5,10,15,20,25,30,35,40,45,50,55,60,65,70,75};
    public final String[] SPEED_FIELDS = {"speed0","speed1","speed2","speed3","speed4","speed5","speed6","speed7","speed8","speed9","speed10","speed11","speed12","speed13","speed14"};
    public final double DEFAULT_MAX_SPEED_LIMIT = 78.0;

}
