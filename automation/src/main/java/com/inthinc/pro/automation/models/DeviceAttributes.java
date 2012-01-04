package com.inthinc.pro.automation.models;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.inthinc.pro.automation.interfaces.IndexEnum;
import com.inthinc.pro.model.event.EventAttr;

public class DeviceAttributes implements Iterable<EventAttr>{

    private Map<EventAttr, Object> attrs;

    
    public DeviceAttributes(){
        attrs = new HashMap<EventAttr, Object>();
    }
    
    public DeviceAttributes addAttribute(EventAttr key, Object value){
        if (value instanceof IndexEnum){
            return addAttribute(key, ((IndexEnum)value).getIndex());
        }
        attrs.put(key, value);
        return this;
    }
    
    @Override
    public Iterator<EventAttr> iterator(){
        return attrs.keySet().iterator();
    }
    
    public Object getValue(EventAttr key){
        return attrs.get(key);
    }

    @Override
    public String toString(){
        return attrs.toString();
    }
    
}
