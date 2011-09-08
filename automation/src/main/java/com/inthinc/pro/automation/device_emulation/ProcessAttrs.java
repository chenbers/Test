package com.inthinc.pro.automation.device_emulation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.automation.deviceEnums.TiwiAttrs;
import com.inthinc.pro.automation.deviceEnums.Ways_ATTRS;
import com.inthinc.pro.automation.utils.ExtractIntegers;


public class ProcessAttrs {
	private final static Logger logger = Logger.getLogger(ProcessAttrs.class);

	public static Map<Ways_ATTRS, Object> waysmart(Map<Object, Object> raw){
	    if (raw==null)return null;
		Map<Ways_ATTRS, Object> processed = new HashMap<Ways_ATTRS, Object>();
		Iterator<Object> itr = raw.keySet().iterator();
		while (itr.hasNext()) {
			String strKey = (String) itr.next();
			Integer enumKey = ExtractIntegers.extract(strKey);
			processed.put(Ways_ATTRS.STATIC.valueOf(enumKey), raw.get(strKey));
		}
		logger.debug("Enumerated Attributes: "+processed);
		return processed;
	}
	
	public static Map<TiwiAttrs, Object> tiwiPro(Map<Object, Object> raw){
		if (raw==null)return null;
		Map<TiwiAttrs, Object> processed = new HashMap<TiwiAttrs, Object>();
		
		Iterator<Object> itr = null;
		itr = raw.keySet().iterator();
		while (itr.hasNext()) {
			String strKey = (String) itr.next();
			Integer enumKey = ExtractIntegers.extract(strKey);
			processed.put(TiwiAttrs.STATIC.valueOf(enumKey), raw.get(strKey));
		}
		logger.debug("Enumerated Attributes: "+processed);
		return processed;
	}
}
