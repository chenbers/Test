package com.inthinc.pro.automation.utils;

import com.inthinc.pro.automation.logging.Log;


public class AutomationNumberManager {
	
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
	    Log.debug("String: " + stringWithIntegers + " to Integer: " + justTheNumbers);
	    return justTheNumbers;
	}
	
}
