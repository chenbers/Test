package com.inthinc.pro.reports.converter;

import java.text.NumberFormat;
import java.util.Locale;

import com.inthinc.pro.reports.util.MessageUtil;


public class ScoreConverter {
    
    public static String convertScore(Integer score, Locale locale) {
        
        if (score == null || score.intValue() < 0) {
            return MessageUtil.getMessageString("notApplicable", locale);
        }
        
            
        NumberFormat format = NumberFormat.getNumberInstance(locale);
        format.setMaximumFractionDigits(1);
        format.setMinimumFractionDigits(1);
    
    
        return format.format(score.doubleValue()/10.0d);
    }

}
