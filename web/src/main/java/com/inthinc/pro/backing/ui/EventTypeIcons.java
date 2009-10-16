package com.inthinc.pro.backing.ui;

import java.util.HashMap;
import java.util.Map;

import com.inthinc.pro.backing.BaseBean;
import com.inthinc.pro.dao.util.DefaultedMap;
import com.inthinc.pro.model.EventType;

public class EventTypeIcons extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3700211845579716989L;
	
	private Map<EventType,String> iconMap;
	
	
    public void init() {
    	
    	iconMap = new DefaultedMap<EventType, String>(new HashMap<EventType, String>(), getExternalContext().getRequestContextPath()+"/images/ico_violation.png"); 
    	iconMap.put(EventType.IDLING,  getExternalContext().getRequestContextPath()+"/images/ico_idle.png");
    	iconMap.put(EventType.TAMPERING, getExternalContext().getRequestContextPath()+"/images/ico_tampering.png");
    }

    public Map<EventType,String> getIconMap(){
    	
    	return iconMap;
    }
}
