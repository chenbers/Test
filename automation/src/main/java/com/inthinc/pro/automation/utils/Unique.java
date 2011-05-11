package com.inthinc.pro.automation.utils;

import org.apache.log4j.Logger;

import com.inthinc.pro.automation.enums.UniqueValues;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.automation.utils.RandomValues;

public class Unique {
	private final static Logger logger = Logger.getLogger(AutomationLogger.class);
	
	private SiloService portalProxy;
	private RandomValues random = new RandomValues();
	private boolean start;
	private String startString;

	public Unique(SiloService portalProxy){
		this.portalProxy = portalProxy;
	}
	
	public String getUniqueValue(Integer length, UniqueValues type){
		Boolean unique = false;
		String value = "";
		while (!unique){
			if (this.start){
				value = this.startString;
			}else value = "";
			
			if (type.isString() && type != UniqueValues.PERSONID_EMAIL){
				value += random.randomMixedString(length);
			}else if (type == UniqueValues.PERSONID_EMAIL){
				value += random.randomEmail();
			}else{
				value += random.getNumberString(length);
			}
			unique = checkUnique(type, value);
		}
		start = false; 
		startString = "";
		logger.debug("Random " + type.toString()+": "+value);
		return value;
	}
	
	public String getUniqueValue(String start, Integer length, UniqueValues type){
		this.start = true;
		this.startString = start;
		return getUniqueValue(length, type);
	}
	
	private Boolean checkUnique(UniqueValues value, String text){
		try{
			portalProxy.getID(value.getName(), text);
		}catch(EmptyResultSetException error){
			return true;
		}
		return false;
	}
}
