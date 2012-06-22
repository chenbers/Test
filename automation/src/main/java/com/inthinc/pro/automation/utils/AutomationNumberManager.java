package com.inthinc.pro.automation.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.inthinc.pro.automation.logging.Log;


public class AutomationNumberManager {
	
	public static Integer extract(String string) {
		
	    
	    if (string == null) {
	        throw new IllegalArgumentException("String cannot be null");
	    }

	    StringBuffer strBuff = new StringBuffer();
	    char c;
	    
	    for (int i = 0; i < string.length() ; i++) {
	        c = string.charAt(i);
	        
	        if (Character.isDigit(c)) {
	            strBuff.append(c);
	        }
	    }
	    Integer allNumbers = Integer.parseInt(strBuff.toString());
	    Log.debug("String: " + string + " to Integer: " + allNumbers);
	    return allNumbers;
	}
	
	public static Integer extractXNumber(String string, int x){
	    if (string == null) {
            throw new IllegalArgumentException("String cannot be null");
        } else if (x==0){
            x = 1;
        }

	    int num = 0;
	    StringBuffer strBuff = new StringBuffer();
	    char c;
	    boolean inNumber = false;
	    int length = string.length();
	    
	    for (int i=0;i<length;i++){
	        c = string.charAt(i);
	        if (Character.isDigit(c)){
	            if (!inNumber){
	                inNumber = true;
	                num++;
	            }
	            if (num==x){
	                strBuff.append(c);
	            }
	        } else {
	            inNumber = false;
	        }
	    }
	    String asString = strBuff.toString();
	    if (!asString.isEmpty()){
	        return Integer.parseInt(asString);
	    }else {
	        return 1;
	    }
	}
	
}
