package com.inthinc.pro.reports;

import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.TimeFrame;

public enum CriteriaType
{
    DURATION(Duration.class),
    TIMEFRAME(TimeFrame.class),
    TIMEFRAME_ALT(TimeFrame.class);
    
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
