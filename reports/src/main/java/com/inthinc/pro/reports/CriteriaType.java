package com.inthinc.pro.reports;

import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.TimeFrame;

public enum CriteriaType
{
    DURATION(Duration.class),
    TIMEFRAME(TimeFrame.class),
    /* 
     * TIMEFRAME_STRICT This represents a time frame with strict requirements. No date range should be available
     * for the user to select.
     */
    TIMEFRAME_STRICT(TimeFrame.class), 
    TIMEFRAME_ALT(TimeFrame.class),
    TIMEFRAME_ALT_DAYS(TimeFrame.class), 
    TIMEFRAME_ALT_PLUS_CUSTOM_RANGE(TimeFrame.class),
    
    INACTIVE_DRIVERS(null),
    ZERO_MILES_DRIVERS(null),
    HOS_DRIVERS_ONLY(null),
    DONT_INCLUDE_UNASSIGNED_DEVICE(null);

    private Class clazz;

    private CriteriaType(Class clazz){
        
        this.setClazz(clazz);
        
    }

    public void setClazz(Class clazz)
    {
        this.clazz = clazz;
    }

    public Class getClazz()
    {
        return clazz;
    };
}
