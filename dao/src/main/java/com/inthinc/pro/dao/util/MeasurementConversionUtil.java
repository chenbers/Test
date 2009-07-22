package com.inthinc.pro.dao.util;

import java.math.BigDecimal;

import com.inthinc.pro.model.MeasurementType;

/**
 * Utility for converty units of measurment from metric to english and eglish to metric MPH = Miles Per Hour KPH = Kilometers Per Hour MPS = Meters Per Second MPG = Miles Per
 * Gallon KPL = Kilometers Per Liter
 * 
 * 
 * @author mstrong
 * 
 */

public class MeasurementConversionUtil
{
    /**
     * 
     * @param miles
     * @return kilometers rounded to the nearest tenth
     */
    public static Number fromMilesToKilometers(Number miles)
    {
        Number kilometers = miles.doubleValue() * 1.609344;
        BigDecimal bigDecimal = new BigDecimal(Math.round(kilometers.doubleValue() * 100));
        return bigDecimal.movePointLeft(2);
    }

    /**
     * 
     * @param kilometers
     * @return miles rounded to the nearest tenth
     */
    public static Number fromKilometersToMiles(Number kilometers)
    {
        Number miles = kilometers.doubleValue() * 0.62137;
        BigDecimal bigDecimal = new BigDecimal(Math.round(miles.doubleValue() * 100));
        return bigDecimal.movePointLeft(2).floatValue();
    }

    public static Number convertMilesToKilometers(Number distance, MeasurementType convertToMeasurementType)
    {
        if (distance != null && convertToMeasurementType == MeasurementType.METRIC)
            return fromMilesToKilometers(distance);
        else
            return distance;
    }

    public static Number fromMPHtoKPH(Number milesPerHour)
    {
        Double kilometersPerHour = milesPerHour.doubleValue() * 1.609344;
        
        return Long.valueOf(Math.round(kilometersPerHour)).intValue();
    }

    public static Number fromKPHtoMPH(Number kilometersPerHour)
    {
        Double milesPerHour = kilometersPerHour.doubleValue() * 0.62137;
        return Long.valueOf(Math.round(milesPerHour)).intValue();
    }

    public static Number fromMPGtoKPL(Number milesPerGallon)
    {
         return milesPerGallon.doubleValue() * 0.42514;
    }

    public static Number convertMpgToKpl(Number mileage, MeasurementType convertToMeasurmentType)
    {
        if (mileage != null && convertToMeasurmentType == MeasurementType.METRIC)
            return fromMPGtoKPL(mileage);
        else
            return mileage;
    }

    public static Number convertSpeed(Number speed,  MeasurementType convertToMeasurmentType)
    {
        if (convertToMeasurmentType == MeasurementType.METRIC)
            return fromMPHtoKPH(speed);
        else
            return fromKPHtoMPH(speed);
    }
    
    //Convert Pounds
    public static Long fromPoundsToKg(Long pounds)
    {
        return Math.round(pounds * .45359237);
    }
    
    public static Long fromKgToPounds(Long kg)
    {
        return Math.round(kg / .45359237);
    }
    
    public static Long convertWeight(Long weight, MeasurementType convertToMeasurementType)
    {
        if(convertToMeasurementType == MeasurementType.METRIC)
            return fromPoundsToKg(weight);
        else
            return fromKgToPounds(weight);
    }
    
    public static Long fromFeetInchToCentimeters(Integer feet,Integer inches)
    {
        
        inches = (feet * 12) + inches;
        return fromInchesToCentimeters(inches);
        
    }
    
    public static Long fromInchesToCentimeters(Integer inches)
    {
        
        return Math.round(inches * 2.54);
    }
    
    public static Long fromCentimetersToInches(Integer cm)
    {
        return Math.round(cm / 2.54);
    }
    
    

}
