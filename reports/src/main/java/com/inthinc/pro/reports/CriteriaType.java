package com.inthinc.pro.reports;

import com.inthinc.pro.model.Duration;

public enum CriteriaType
{
    DURATION(Duration.class);
    
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
