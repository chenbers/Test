package com.inthinc.pro.util;

/**
 * Utility for converty units of measurment from metric to english and eglish to metric MPH = Miles Per Hour KPH = Kilometers Per Hour MPS = Meters Per Second
 * MPG = Miles Per Gallon KPL = Kilometers Per Liter
 * 
 * 
 * @author mstrong
 * 
 */

public class MetricConversionUtil
{
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

}
