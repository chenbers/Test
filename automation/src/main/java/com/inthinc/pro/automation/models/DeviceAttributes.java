package com.inthinc.pro.automation.models;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.inthinc.pro.automation.deviceEnums.DeviceAttrs;
import com.inthinc.pro.automation.interfaces.IndexEnum;

public class DeviceAttributes implements Iterable<DeviceAttrs>{

    private Map<DeviceAttrs, Object> attrs;

    
    public DeviceAttributes(){
        attrs = new HashMap<DeviceAttrs, Object>();
    }
    
    public DeviceAttributes addAttribute(DeviceAttrs key, Object value){
        if (value instanceof IndexEnum){
            return addAttribute(key, ((IndexEnum)value).getIndex());
        }
        attrs.put(key, value);
        return this;
    }
    
    @Override
    public Iterator<DeviceAttrs> iterator(){
        return attrs.keySet().iterator();
    }
    
    public Object getValue(DeviceAttrs key){
        return attrs.get(key);
    }

    @Override
    public String toString(){
        return attrs.toString();
    }
    
}
