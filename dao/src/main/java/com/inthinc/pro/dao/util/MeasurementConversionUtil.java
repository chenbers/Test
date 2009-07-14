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
    public static Float fromMilesToKilometers(Double miles)
    {
        Double kilometers = miles * 1.609344;
        BigDecimal bigDecimal = new BigDecimal(Math.round(kilometers * 100));
        return bigDecimal.movePointLeft(2).floatValue();
    }

    /**
     * 
     * @param kilometers
     * @return miles rounded to the nearest tenth
     */
    public static Float fromKilometersToMiles(Double kilometers)
    {
        Double miles = kilometers * 0.62137;
        BigDecimal bigDecimal = new BigDecimal(Math.round(miles * 100));
        return bigDecimal.movePointLeft(2).floatValue();
    }

    public static Float convertDistance(Float distance, MeasurementType convertToMeasurementType)
    {
        if (convertToMeasurementType == MeasurementType.METRIC)
        {
            return fromMilesToKilometers(distance.doubleValue());
        }
        else
            return fromKilometersToMiles(distance.doubleValue());
    }

    public static Integer fromMPHtoKPH(Integer milesPerHour)
    {
        Double kilometersPerHour = milesPerHour * 1.609344;
        
        return Long.valueOf(Math.round(kilometersPerHour)).intValue();
    }

    public static Integer fromKPHtoMPH(Integer kilometersPerHour)
    {
        Double milesPerHour = kilometersPerHour * 0.62137;
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

    public static Integer convertSpeed(Integer speed,  MeasurementType convertToMeasurmentType)
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
