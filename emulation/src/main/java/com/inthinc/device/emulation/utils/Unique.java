package com.inthinc.device.emulation.utils;

import java.io.StringWriter;

import com.inthinc.device.emulation.interfaces.SiloService;
import com.inthinc.device.hessian.tcp.AutomationHessianFactory;
import com.inthinc.pro.automation.enums.AutoSilos;
import com.inthinc.pro.automation.enums.UniqueValues;
import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.utils.RandomValues;

public class Unique {
	
	private SiloService portalProxy;
	private RandomValues random = new RandomValues();
	private boolean start;
	private String startString;

	public Unique(SiloService portalProxy){
		this.portalProxy = portalProxy;
	}
	
	public Unique(AutoSilos getYourOwn){
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
		Log.debug("Random " + type.toString()+": "+aStringAString.toString());
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
		}catch(Exception error){
			return true;
		}
		return false;
	}
}
