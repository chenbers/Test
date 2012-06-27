package com.inthinc.pro.reports.converter;

import java.text.NumberFormat;
import java.util.Locale;

public class PercentConverter {
    
    /**
     * Converts a double to a string representation of percent using a locale
     * 
     * Example: 0.125 = 12.50%
     * 
     * @param percent
     * @param locale
     * @return
     */
    public static String doubleToString(Double percent,Locale locale){
        NumberFormat numberFormat = NumberFormat.getPercentInstance(locale);
        numberFormat.setMaximumFractionDigits(1);
        return numberFormat.format(percent);
    }

}
