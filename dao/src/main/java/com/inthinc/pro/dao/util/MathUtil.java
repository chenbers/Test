package com.inthinc.pro.dao.util;

import java.math.BigDecimal;

public class MathUtil
{
    public static Number round(Number number, int decimalPlaces)
    {
        double multiplier = Math.pow(10, decimalPlaces);
        double result = number.doubleValue() * multiplier;
        long roundedNumber = Math.round(result);
        
        BigDecimal bigDecimal = new BigDecimal(roundedNumber);
        return bigDecimal.movePointLeft(decimalPlaces).floatValue();
    }
    
    public static Integer percent(Long n, Long total)
    {
    	if (total == null || total == 0l)
    		return 0;
    	
		Integer percent = (int)(((float)n * 100f) / (float)total + .005f);
		
		if (percent > 100)
			percent = 100;
		
		return percent;

    }
    public static Number roundToNearestFive(Number value){
    	
    	Double pushUp = value.doubleValue() + (2.5 * Math.signum(value.doubleValue()));
     	return pushUp.intValue()-(pushUp.intValue() % 5);
    }

}
