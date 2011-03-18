package com.inthinc.pro.tripviewer.model;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import com.inthinc.pro.model.event.Event;

public class EventAndAttr extends Event {
    private Map<Object,Object> decodedAttrMap;
    
    public EventAndAttr() {
        super();
    }
    
    public EventAndAttr(int sats,int speed,int speedLimit, double latitude, double longitude, Date time, Map<Object,Object> attrMap, Date created) {
        setSats(sats);
        setSpeed(speed);
        setSpeedLimit(speedLimit);
        setLongitude(longitude);
        setLatitude(latitude);
        setTime(time);
        setAttrMap(attrMap);
        setCreated(created);
    }

    public String getDecodedAttrMap() {
        if ( decodedAttrMap == null ) {
            return "";
        }
        
        StringBuffer sb = new StringBuffer();
        sb.append("");
        
        Iterator it = decodedAttrMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();
            sb.append(pairs.getKey() + " = " + pairs.getValue() + " , ");
        }        
        
        return sb.toString();
    }

    public void setDecodedAttrMap(Map<Object,Object> decodedAttrMap) {
        this.decodedAttrMap = decodedAttrMap;
    }

}
