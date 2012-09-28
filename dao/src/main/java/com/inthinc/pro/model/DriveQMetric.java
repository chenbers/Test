package com.inthinc.pro.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public interface DriveQMetric
{
    public static final int DRIVEQMETRIC_STARTING_ODOMETER = 0;
    public static final int DRIVEQMETRIC_ENDING_ODOMETER = 1;
    public static final int DRIVEQMETRIC_ODOMETER = 2;
    public static final int DRIVEQMETRIC_OVERALL = 3;
    public static final int DRIVEQMETRIC_SPEEDING = 4;
    public static final int DRIVEQMETRIC_SPEEDING1 = 5;
    public static final int DRIVEQMETRIC_SPEEDING2 = 6;
    public static final int DRIVEQMETRIC_SPEEDING3 = 7;
    public static final int DRIVEQMETRIC_SPEEDING4 = 8;
    public static final int DRIVEQMETRIC_SPEEDING5 = 9;
    public static final int DRIVEQMETRIC_DRIVING_STYLE = 10;
    public static final int DRIVEQMETRIC_AGGRESSIVE_BRAKE = 11;
    public static final int DRIVEQMETRIC_AGGRESSIVE_ACCEL = 12;
    public static final int DRIVEQMETRIC_AGGRESSIVE_TURN = 13;
    public static final int DRIVEQMETRIC_AGGRESSIVE_LEFT = 14;
    public static final int DRIVEQMETRIC_AGGRESSIVE_RIGHT = 15;
    public static final int DRIVEQMETRIC_AGGRESSIVE_BUMP = 16;
    public static final int DRIVEQMETRIC_SEATBELT = 17;
    public static final int DRIVEQMETRIC_COACHING = 18;
    public static final int DRIVEQMETRIC_MPG_LIGHT = 19;
    public static final int DRIVEQMETRIC_MPG_MEDIUM = 20;
    public static final int DRIVEQMETRIC_MPG_HEAVY = 21;
    public static final int DRIVEQMETRIC_IDLE_LO = 22;
    public static final int DRIVEQMETRIC_IDLE_HI = 23;
    public static final int DRIVEQMETRIC_DRIVE_TIME = 24;
    
    public static final int DRIVEQMETRIC_MIN = 0;
    public static final int DRIVEQMETRIC_MAX = 24;
}
