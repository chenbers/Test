package com.inthinc.pro.automation.utils;

import org.apache.log4j.Logger;

public class AutomationNumberManager {
	private final static Logger logger = Logger.getLogger(AutomationNumberManager.class);
	
	public static Integer extract(String stringWithIntegers) {
		
	    
	    if (stringWithIntegers == null) {
	        return null;
	    }

	    StringBuffer strBuff = new StringBuffer();
	    char c;
	    
	    for (int i = 0; i < stringWithIntegers.length() ; i++) {
	        c = stringWithIntegers.charAt(i);
	        
	        if (Character.isDigit(c)) {
	            strBuff.append(c);
	        }
	    }
	    Integer justTheNumbers = Integer.parseInt(strBuff.toString());
	    logger.debug("String: " + stringWithIntegers + " to Integer: " + justTheNumbers);
	    return justTheNumbers;
	}
	
}
