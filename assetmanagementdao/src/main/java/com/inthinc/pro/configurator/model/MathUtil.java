package com.inthinc.pro.configurator.model;

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
    
    public static Number percent(Number n, Number total)
    {
    	if (total == null || total.floatValue() == 0f)
    		return 0f;
    	
		Float percent = ((n.floatValue() * 100f) / total.floatValue() + .0049f);
		
		if (percent > 100)
			percent = 100f;
		
		return percent;

    }
    public static Number roundToNearestFive(Number value){
    	
    	Double pushUp = value.doubleValue() + (2.5 * Math.signum(value.doubleValue()));
     	return pushUp.intValue()-(pushUp.intValue() % 5);
    }

}
