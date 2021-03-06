package com.inthinc.device.objects;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.inthinc.device.emulation.enums.EventAttr;
import com.inthinc.pro.automation.interfaces.IndexEnum;
import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.objects.AutomationCalendar;

public class DeviceAttributes implements Iterable<EventAttr>{

    protected Map<EventAttr, Object> attrs;
    

    
    public DeviceAttributes(){
        attrs = new HashMap<EventAttr, Object>();
    }
	
	public void removeAttribute(EventAttr key){
		attrs.remove(key);
    }
    
    public DeviceAttributes addAttribute(EventAttr key, Object value){
        if (value instanceof IndexEnum){
        	attrs.put(key, ((IndexEnum)value).getIndex());
        } else if (value instanceof Number || value instanceof String){
        	attrs.put(key, value);
        } else if (value instanceof Boolean){
        	attrs.put(key, (Boolean) value ? 1:0);
        } else if (value instanceof Calendar){
        	attrs.put(key, ((Calendar)value).getTimeInMillis()/1000);
        } else if (value instanceof AutomationCalendar){
        	attrs.put(key, ((AutomationCalendar)value).toInt());
        } else if (value == null){
        	attrs.put(key, 0);
        } else {
            throw new IllegalArgumentException("Cannot add value of type: " + value.getClass());
        }
        return this;
    }
    
    @Override
    public Iterator<EventAttr> iterator(){
        return attrs.keySet().iterator();
    }
    
    public Object getValue(EventAttr key){
        return attrs.get(key);
    }
    
    public int getInt(EventAttr key){
        Object value = attrs.get(key);
        try {
            return (Integer) value;
        } catch (ClassCastException e){
            Log.error("Cannot cast " + key + " to an Integer, Actual class is : " + attrs.get(key).getClass().getSimpleName());
        }
        return 0;
    }
    
    public String getString(EventAttr key){
        Object value = attrs.get(key);
        try {
            return (String) value;
        } catch (ClassCastException e){
            Log.error("Cannot cast " + key + " to a String, Actual class is : " + attrs.get(key).getClass().getSimpleName());
        }
        return "";
    }
    
    public double getDouble(EventAttr key){
        Object value = attrs.get(key);
        try {
            return (Double) value;
        } catch (ClassCastException e){
            Log.error("Cannot cast " + key + " to a Double, Actual class is : " + attrs.get(key).getClass().getSimpleName());
        }
        return 0.0;
    }

    @Override
    public String toString(){
        return attrs.toString();
    }
}
