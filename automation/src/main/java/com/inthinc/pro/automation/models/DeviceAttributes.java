package com.inthinc.pro.automation.models;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.automation.device_emulation.NoteManager.AttrTypes;
import com.inthinc.pro.automation.interfaces.DeviceTypes;

public class DeviceAttributes implements Iterable<DeviceTypes>{

    private final static Logger logger = Logger.getLogger(DeviceAttributes.class);
    
    private Map<DeviceTypes, Object> attrs;
    private Map<DeviceTypes, Integer> size;

    
    public DeviceAttributes(){
        attrs = new HashMap<DeviceTypes, Object>();
        size = new HashMap<DeviceTypes, Integer>();
    }
    
    public DeviceAttributes addAttribute(DeviceTypes key, Object value, Integer byteSize){
        attrs.put(key, value);
        this.size.put(key, byteSize);
        logger.debug(attrs);
        logger.debug(this.size);
        return this;
    }
    
    public DeviceAttributes addAttribute(DeviceTypes key, Integer value){
        return addAttribute(key, value, AttrTypes.getType(key).getSize());
    }
    
    @Override
    public Iterator<DeviceTypes> iterator(){
        return attrs.keySet().iterator();
    }
    
    public Object getValue(DeviceTypes key){
        return attrs.get(key);
    }
    
    public Integer getSize(DeviceTypes key){
        return size.get(key);
    }
    
    @Override
    public String toString(){
        return attrs.toString();
    }
    
}
