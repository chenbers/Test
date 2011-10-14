package com.inthinc.pro.automation.objects;

import com.inthinc.pro.automation.deviceEnums.Heading;

public class TripTracker {

    private Double latitude;
    private Double longitude;
    private Double last_lat;
    private Double last_lng;
    
    private Long trip_start;
    private Long trip_stop;
    

    protected Heading heading;
    protected int speed = 0;

}
