package com.inthinc.pro.util;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.model.Zone;
import com.inthinc.pro.model.ZoneAlert;

/**
 * @author mstrong
 * 
 * This is to assist with the BeanUtil class. This enum provides type mappings to indicate which object within model objects should be treated as
 * simple types. By defining a type as a simple type, the BeanUtil deepCopy, copyAndInit,
 * 
 *
 */

public enum SimpleType
{
    ZONE_ALERT_TYPE(ZoneAlert.class,Zone.class);
    
    @SuppressWarnings("unchecked")
    private List<Class> simpleTypes;
    
    @SuppressWarnings("unchecked")
    private Class parentType;
    
    @SuppressWarnings("unchecked")
    private SimpleType(Class parentType,Class... simpleTypes)
    {
        this.simpleTypes = Arrays.asList(simpleTypes);
        this.parentType = parentType;
    }
    
    @SuppressWarnings("unchecked")
    public static SimpleType valueOf(Class clazz)
    {
        for (SimpleType p : EnumSet.allOf(SimpleType.class))
        {
            if(p.getParentType().isAssignableFrom(clazz))
            {
                return p;
            }
        }
        return null;
    }
    
    @SuppressWarnings("unchecked")
    public List<Class> getSimpleTypes()
    {
        return simpleTypes;
    }
    
    @SuppressWarnings("unchecked")
    public Class getParentType()
    {
        return parentType;
    }
    
    
}
