package com.inthinc.pro.backing.ui;

import java.util.HashMap;
import java.util.Map;

import com.inthinc.pro.backing.BaseBean;
import com.inthinc.pro.dao.util.DefaultedMap;
import com.inthinc.pro.model.event.EventType;

public class EventTypeIcons extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3700211845579716989L;
	
	private Map<EventType,String> iconMap;
    private Map<EventType,String> iconAbsMap;
	
    public void init() {
    	
    	iconMap = new DefaultedMap<EventType, String>(new HashMap<EventType, String>(), getExternalContext().getRequestContextPath()+"/images/ico_violation.png"); 
    	iconMap.put(EventType.IDLING,  getExternalContext().getRequestContextPath()+"/images/ico_idle.png");
    	iconMap.put(EventType.TAMPERING, getExternalContext().getRequestContextPath()+"/images/ico_tampering.png");
        iconAbsMap = new DefaultedMap<EventType, String>(new HashMap<EventType, String>(), "/images/ico_violation.png"); 
        iconAbsMap.put(EventType.IDLING,  "/images/ico_idle.png");
        iconAbsMap.put(EventType.TAMPERING, "/images/ico_tampering.png");
    }

    public Map<EventType,String> getIconMap(){
    	
    	return iconMap;
    }
    public Map<EventType,String> getIconAbsMap(){
        
        return iconAbsMap;
    }
}
