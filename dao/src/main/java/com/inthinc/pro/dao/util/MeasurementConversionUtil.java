package com.inthinc.pro.dao.util;

import com.inthinc.pro.model.FuelEfficiencyType;
import com.inthinc.pro.model.MeasurementType;

/**
 * Utility for converting units of measurement from metric to English and English to metric MPH = Miles Per Hour KPH = Kilometers Per Hour MPS = Meters Per Second MPG = Miles Per
 * Gallon KPL = Kilometers Per Liter
 * 
 * 
 * @author mstrong
 * 
 */

public class MeasurementConversionUtil
{
	private static final Double MILESTOKM = 1.609344;
	private static final Double KMTOMILES = 0.62137;
	private static final Double MPGTOKPL = 0.42514;
	private static final Double MPGTOMPGUK = 1.2;
	private static final Double LBTOKG = .45359237;
	private static final Double INCHESTOCM = 2.54;
    private static final Double LITERS_PER_GALLON = 3.7854;

	/**
     * 
     * @param miles
     * @return kilometers rounded to the nearest tenth
     */
    public static Number fromMilesToKilometers(Number miles)
    {
        return miles==null?null:MathUtil.round(miles.doubleValue() * MILESTOKM, 1);
        
    }

    /**
     * 
     * @param kilometers
     * @return miles rounded to the nearest tenth
     */
    public static Number fromKilometersToMiles(Number kilometers)
    {
        return kilometers==null?null:MathUtil.round(kilometers.doubleValue() * KMTOMILES, 1);
    }

    public static Number convertMilesToKilometers(Number distance, MeasurementType convertToMeasurementType)
    {
        if (convertToMeasurementType == MeasurementType.METRIC)
            return fromMilesToKilometers(distance);
        else
            return distance;
    }

    public static Number fromMPHtoKPH(Number milesPerHour)
    {
        
        return milesPerHour==null?null:Long.valueOf(Math.round(milesPerHour.doubleValue() * MILESTOKM)).intValue();
    }

    public static Integer fromMPHtoKPHSpeedLimit(Number milesPerHour)
    {
        //Converts to KPH and then rounds to the nearest 5kph - used for converting speed limits only
        
        if (milesPerHour == null) return null;
        
        Double addSome= new Double(MeasurementConversionUtil.fromMPHtoKPH(Number.class.cast(milesPerHour)).doubleValue())+2.5d;
        int nearestFive = addSome.intValue()/5 * 5;
        
        return nearestFive;
    }

    public static Number fromKPHtoMPH(Number kilometersPerHour)
    {
        return kilometersPerHour==null?null:Long.valueOf(Math.round(kilometersPerHour.doubleValue() * KMTOMILES)).intValue();
    }

    public static Number fromPerMillionMilesToPerMillionKm(Number perMillionMiles){
    	
        return perMillionMiles==null?null:perMillionMiles.doubleValue() * KMTOMILES;
    }
    public static Number fromMPGtoKPL(Number milesPerGallon)
    {
         return milesPerGallon==null?null:milesPerGallon.doubleValue() * MPGTOKPL;
    }

    public static Number fromMPGtoLP100KM(Number milesPerGallon)
    {
        return  (milesPerGallon==null || milesPerGallon.doubleValue()== 0D)?null:new Double(100)/fromMPGtoKPL(milesPerGallon).doubleValue();
    }
    
    public static Number fromMPGtoMPGUK(Number milesPerGallon)
    {
        return  milesPerGallon==null?null:new Double(MPGTOMPGUK)*milesPerGallon.doubleValue();
    }
    
    public static Number convertMpgToKpl(Number mileage, MeasurementType convertToMeasurmentType)
    {
        if (convertToMeasurmentType == MeasurementType.METRIC)
            return fromMPGtoKPL(mileage);
        else
            return mileage;
    }

    public static Number convertMpgToFuelEfficiencyType(Number mileage, MeasurementType convertToMeasurementType, FuelEfficiencyType convertToFuelEficiencyType)
    {
        if (convertToFuelEficiencyType == null) {
            
            if (convertToMeasurementType == null || convertToMeasurementType == MeasurementType.ENGLISH) return FuelEfficiencyType.MPG_US.convertFromMPG(mileage);
            
            if (convertToMeasurementType == MeasurementType.METRIC) return FuelEfficiencyType.KMPL.convertFromMPG(mileage);
        }
        return convertToFuelEficiencyType.convertFromMPG(mileage);
    }
    
    public static Number convertSpeed(Number speed,  MeasurementType convertToMeasurmentType)
    {
        if (convertToMeasurmentType == MeasurementType.METRIC)
            return fromMPHtoKPH(speed);
        else
            return speed;
    }
    
    //Convert Pounds
    public static Long fromPoundsToKg(Long pounds)
    {
        return pounds==null?null:Math.round(pounds * LBTOKG);
    }
    
    public static Long fromKgToPounds(Long kg)
    {
        return kg==null?null:Math.round(kg / LBTOKG);
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
        if (feet == null) return fromInchesToCentimeters(inches);
        return inches==null?fromInchesToCentimeters(feet*12):fromInchesToCentimeters(feet*12+inches);
    }
    
    public static Long fromInchesToCentimeters(Integer inches)
    {
        
        return inches==null?null:Math.round(inches * INCHESTOCM);
    }
    
    public static Long fromCentimetersToInches(Integer cm)
    {
        return cm==null?null:Math.round(cm / INCHESTOCM);
    }
    
    public static Number fromGallonsToLiters(Number gallons) {
        return gallons == null ? null : Math.round(gallons.doubleValue() * LITERS_PER_GALLON);
    }
    public static Number fromLitersToGallons(Number liters) {
        return liters == null ? null : Math.round(liters.doubleValue() / LITERS_PER_GALLON);
    }
}
