package com.inthinc.pro.automation.utils;

import org.apache.log4j.Logger;

public class IntegersOnlyPlease {
	private final static Logger logger = Logger.getLogger(AutomationLogger.class);
	
	public static Integer getOnlyInternationals(String eatMe) {
		
	    
	    if (eatMe == null) {
	        return null;
	    }

	    StringBuffer strBuff = new StringBuffer();
	    char c;
	    
	    for (int i = 0; i < eatMe.length() ; i++) {
	        c = eatMe.charAt(i);
	        
	        if (Character.isDigit(c)) {
	            strBuff.append(c);
	        }
	    }
	    Integer numbersOooh = Integer.parseInt(strBuff.toString());
	    logger.debug("String: " + eatMe + " to Integer: " + numbersOooh);
	    return numbersOooh;
	}

}
