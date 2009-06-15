package com.inthinc.pro.dao.util;

import java.math.BigDecimal;

/**
 * Utility for converty units of measurment from metric to english and eglish to metric MPH = Miles Per Hour KPH = Kilometers Per Hour MPS = Meters Per Second
 * MPG = Miles Per Gallon KPL = Kilometers Per Liter
 * 
 * 
 * @author mstrong
 * 
 */

public class MeasurementConversionUtil
{
    public static Float fromMilesToKilometers(Long miles)
    {
        Double kilometers = miles * 1.609344;
        BigDecimal bigDecimal =  new BigDecimal(Math.round(kilometers * 100));
        return bigDecimal.movePointLeft(2).floatValue();
    }
    
    public static Float fromKilometersToMiles(Long kilometers)
    {
        Double miles = kilometers * 0.62137;
        BigDecimal bigDecimal =  new BigDecimal(Math.round(miles * 100));
        return bigDecimal.movePointLeft(2).floatValue();
    }
    
    public static Float convertDistance(Long distance,boolean convertToMetric)
    {
        if(convertToMetric){
            return fromMilesToKilometers(distance);
        }else
            return fromKilometersToMiles(distance);
    }
    
    public static Long fromMPHtoKPH(Long milesPerHour)
    {
        Double kilometersPerHour = milesPerHour * 1.609344;
        return Math.round(kilometersPerHour);
    }

    public static Long fromKPHtoMPH(Long kilometersPerHour)
    {
        Double milesPerHour = kilometersPerHour * 0.62137;
        return Math.round(milesPerHour);
    }

    public static Long fromMPGtoKPL(Long milesPerGallon)
    {
        Double milesPerLiter = milesPerGallon * 0.42514;
        return Math.round(milesPerLiter);
    }
    
    public static Long convertSpeed(Long speed,boolean convertToMetric){
        if(convertToMetric)
            return fromMPHtoKPH(speed);
        else
            return fromKPHtoMPH(speed);
    }

}
