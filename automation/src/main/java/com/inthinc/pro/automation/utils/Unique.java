package com.inthinc.pro.automation.utils;

import java.io.StringWriter;

import org.apache.log4j.Logger;

import com.inthinc.device.emulation.enums.Addresses;
import com.inthinc.device.emulation.interfaces.SiloService;
import com.inthinc.emulation.hessian.AutomationHessianFactory;
import com.inthinc.emulation.hessian.GenericHessianException;
import com.inthinc.pro.automation.enums.UniqueValues;

public class Unique {
	private final static Logger logger = Logger.getLogger(Unique.class);
	
	private SiloService portalProxy;
	private RandomValues random = new RandomValues();
	private boolean start;
	private String startString;

	public Unique(SiloService portalProxy){
		this.portalProxy = portalProxy;
	}
	
	public Unique(Addresses getYourOwn){
		this.portalProxy = new AutomationHessianFactory().getPortalProxy(getYourOwn);
	}
	
	public String getUniqueValue(Integer length, UniqueValues type){
		Boolean unique = false;
		StringWriter aStringAString = new StringWriter();
		while (!unique){
			if (this.start){
				aStringAString.write(this.startString);
			}
			
			if (type == UniqueValues.VEHICLE_VIN){
				aStringAString.write(random.getCharIntString(length));
			}
			else if (type.isString() && type != UniqueValues.PERSONID_EMAIL){
				aStringAString.write(random.getCharIntString(length));
			}else if (type == UniqueValues.PERSONID_EMAIL){
				aStringAString.write(random.getEmail());
			}else{
				aStringAString.write(random.getIntString(length));
			}
			unique = checkUnique(type, aStringAString.toString());
		}
		start = false; 
		startString = "";
		logger.debug("Random " + type.toString()+": "+aStringAString.toString());
		return aStringAString.toString();
	}
	
	public String getUniqueValue(String start, Integer length, UniqueValues type){
		this.start = true;
		this.startString = start;
		return getUniqueValue(length, type);
	}
	
	private Boolean checkUnique(UniqueValues value, String text){
		try{
			portalProxy.getID(value.getName(), text);
		}catch(GenericHessianException error){
			return true;
		}
		return false;
	}
}
