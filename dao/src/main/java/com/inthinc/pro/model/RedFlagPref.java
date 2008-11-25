package com.inthinc.pro.model;

import java.util.List;

public class RedFlagPref extends BaseEntity
{
    public static final int MIN_TOD                       = 0;
    public static final int MAX_TOD                       = 1439;
    public static final int ALERT_TYPE_SPEEDING           = 1;
    public static final int ALERT_TYPE_AGGRESSIVE_DRIVING = 2;
    public static final int ALERT_TYPE_SEATBELT           = 3;
    public static final int ALERT_TYPE_ENTER_ZONE         = 4;
    public static final int ALERT_TYPE_EXIT_ZONE          = 5;
    public static final int ALERT_TYPE_LOW_BATTERY        = 6;
    public static final int ALERT_TYPE_UNKNOWN            = 7;
    public static final int ALERT_TYPE_TAMPERING          = 8;
    public static final int ALERT_TYPE_MIN                = 1;
    public static final int ALERT_TYPE_MAX                = 8;

    private String          name;
    private Integer         userID;
    private Integer         driverID;
    private List            alertType;
    private Integer         zoneID;
    private List            deliveryMethod;
    private Integer         startTOD;
    private Integer         stopTOD;
    private List            dayOfWeek;
    private Integer         speedingSeverity;
}
