package com.inthinc.pro.dao.util;

import java.math.BigDecimal;

public class MathUtil
{
    public static Number round(Number number, double decimalPlaces)
    {
        double multiplier = Math.pow(10, decimalPlaces);
        double result = number.doubleValue() * multiplier;
        long roundedNumber = Math.round(result);
        
        BigDecimal bigDecimal = new BigDecimal(roundedNumber);
        return bigDecimal.movePointLeft(2).floatValue();
    }
}
