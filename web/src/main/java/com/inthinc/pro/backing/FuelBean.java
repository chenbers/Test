package com.inthinc.pro.backing;

import org.apache.log4j.Logger;

import com.inthinc.pro.model.Duration;
import com.inthinc.pro.util.GraphicUtil;

public class FuelBean extends BaseDurationBean {

	private static final Logger logger = Logger.getLogger(FuelBean.class);
	
	private String barDef;	
	
	public String getBarDef() {		
		barDef = createBarDef();
//		logger.debug("returned string: " + barDef);
		return barDef;
	}

	public void setBarDef(String barDef) {
		this.barDef = barDef;
	}
	
	public String createBarDef() {
		StringBuffer sb = new StringBuffer();
		
		//Control parameters
		sb.append(GraphicUtil.getBarControlParameters());
			
		//Bar parameters
		//MAKE SURE YOU LOAD REAL DATA SO, IF THERE IS FEWER OBSERVATIONS
		//THAN THE REQUESTED INTERVAL I.E. 22 DAYS WHEN YOU NEED 30, YOU 
		//PAD THE FRONT WITH ZEROES
		sb.append(GraphicUtil.createFakeBarData());		
		
		sb.append("</chart>");
		
		return sb.toString();
	}
	
}
