package com.inthinc.pro.dao.util;

import java.lang.Math;

public class NumberUtil 
{
	
	public static final double roundDouble(double d, int places) {
		return Math.round(d * Math.pow(10, (double) places)) / Math.pow(10, (double) places);
	}
	
	/**
	 * No one likes to use exceptions for flow control. For now we'll isolate this case to this method, so that it's not spread
	 * throughout our application.
	 * 
	 * @param str 
	 * @return if the passed in value is an integer.
	 */
	public static final boolean isInteger(String str)
	{
	    try
	    {
	        Integer.parseInt(str);
	        return true;
	    }
	    catch(NumberFormatException exception)
	    {
	        return false;
	    }
	}
	/**
	 * No one likes to use exceptions for flow control. For now we'll isolate this case to this method, so that it's not spread
	 * throughout our application.
	 * 
	 * @param str 
	 * Convert string to integer.  If it isn't an integer just return 0
	 */
    public static final Integer convertString(String integerValue) {
        
        if (integerValue == null) return 0;
        
        try {
            
            return Integer.parseInt(integerValue);
        }
        catch(NumberFormatException nfe){
            
            return 0;
        }
   }
    /**
     * No one likes to use exceptions for flow control. For now we'll isolate this case to this method, so that it's not spread
     * throughout our application.
     * 
     * @param str 
     * Convert string to double.  If it isn't an integer just return 0
     */
    public static final Double convertStringToDouble(String doubleValue) {
        
        if (doubleValue == null) return 0.0;
        
        try {
            
            return Double.parseDouble(doubleValue);
        }
        catch(NumberFormatException nfe){
            
            return 0.0;
        }
   }
}
