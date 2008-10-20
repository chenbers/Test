package com.inthinc.pro.backing;

import org.apache.log4j.Logger;

import com.inthinc.pro.util.GraphicUtil;

public class FuelBean extends BaseBean {

	private static final Logger logger = Logger.getLogger(FuelBean.class);
	
	private String barDef;	
	
	public String getBarDef() {		
		barDef = createBarDef();
		logger.debug("returned string: " + barDef);
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
		sb.append(GraphicUtil.createFakeBarData());		
		
		sb.append("</chart>");
		
		return sb.toString();
	}


}
