package com.inthinc.pro.automation.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.automation.device_emulation.TiwiAttrs;
import com.inthinc.pro.automation.device_emulation.Ways_ATTRS;


public class ProcessAttrs {
	private final static Logger logger = Logger.getLogger(AutomationLogger.class);

	public static Map<Ways_ATTRS, Object> processThemW(Map<Object, Object> rogerRoger){
		Map<Ways_ATTRS, Object> jedi = new HashMap<Ways_ATTRS, Object>();
		Iterator<Object> itr = rogerRoger.keySet().iterator();
		while (itr.hasNext()) {
			String key = (String) itr.next();
			Integer realKey = IntegersOnlyPlease.getOnlyInternationals(key);
			jedi.put(Ways_ATTRS.valueOf(realKey), rogerRoger.get(key));
		}
		logger.debug("Enumerated Attributes: "+jedi);
		return jedi;
	}
	
	public static Map<TiwiAttrs, Object> processThemT(Map<Object, Object> rogerRoger){
		if (rogerRoger==null)return null;
		Map<TiwiAttrs, Object> jedi = new HashMap<TiwiAttrs, Object>();
		
		Iterator<Object> itr = null;
		itr = rogerRoger.keySet().iterator();
		while (itr.hasNext()) {
			String key = (String) itr.next();
			Integer realKey = IntegersOnlyPlease.getOnlyInternationals(key);
			jedi.put(TiwiAttrs.valueOf(realKey), rogerRoger.get(key));
		}
		logger.debug("Enumerated Attributes: "+jedi);
		return jedi;
	}
}
