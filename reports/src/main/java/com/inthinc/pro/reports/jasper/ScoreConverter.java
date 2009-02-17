package com.inthinc.pro.reports.jasper;

import java.math.BigDecimal;

public class ScoreConverter
{
    
    public static String toString(Integer score)
    {
        String returnString;
        if(score < 0)
        {
            returnString = "N/A";
        }else
        {
            returnString = String.valueOf(toFloat(score));
        }
        
        return returnString;
    }
    
    public static Float toFloat(Integer integer)
    {
        BigDecimal bd = new BigDecimal(integer);
        bd.movePointLeft(1);
        return bd.floatValue();
    }
    
}
