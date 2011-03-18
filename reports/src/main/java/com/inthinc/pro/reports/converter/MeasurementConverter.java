package com.inthinc.pro.reports.converter;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.util.MathUtil;
import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.FuelEfficiencyType;
import com.inthinc.pro.model.MeasurementType;

public class MeasurementConverter {
    private static final Logger logger = Logger.getLogger(MeasurementConverter.class);

    public static String convertSpeed(Integer speed, Boolean convertToMetric, Locale locale) {
        if (speed == null)
            return "";
        NumberFormat nf = NumberFormat.getNumberInstance(locale);
        nf.setMaximumFractionDigits(1);
        nf.setMinimumFractionDigits(1);
        if (convertToMetric)
            return nf.format(MeasurementConversionUtil.convertSpeed(speed, MeasurementType.METRIC).intValue());
        else
            return nf.format(speed);
    }

    public static String convertSpeedLimit(Integer speed, Boolean convertToMetric, Locale locale) {
        NumberFormat nf = NumberFormat.getNumberInstance(locale);
        nf.setMaximumFractionDigits(1);
        nf.setMinimumFractionDigits(1);
        if (convertToMetric)
            return nf.format(MeasurementConversionUtil.fromMPHtoKPHSpeedLimit(Number.class.cast(speed)).intValue());
        else
            return nf.format(speed);
    }

    public static Number convertMileage(Number mileage, Boolean convertToMetric, Locale locale) {
        if (convertToMetric != null && convertToMetric)
            return MeasurementConversionUtil.convertMpgToKpl(mileage, MeasurementType.METRIC);
        else
            return mileage;
    }

    public static String convertDistance(Number distance, Boolean convertToMetric, Locale locale) {
        NumberFormat nf = NumberFormat.getNumberInstance(locale);
        nf.setMaximumFractionDigits(1);
        nf.setMinimumFractionDigits(1);
        if (convertToMetric)
            return nf.format(MeasurementConversionUtil.convertMilesToKilometers(distance, MeasurementType.METRIC));
        else {
            return nf.format( MathUtil.round(distance, 2));
        }
    }
    public static String convertDistance100th(Number distance, Boolean convertToMetric, Locale locale) {
    	if (distance == null)
    		distance = 0;
    	return convertDistance(distance.doubleValue()/100d, convertToMetric, locale);
    }

    /**
     * 
     * @param distance
     * @param convertToMetric
     * @param n
     *            how many decimal places to move the point to the left.
     * @return
     */
    public static String convertDistanceAndMovePoint(Number distance, Boolean convertToMetric, Integer n, Locale locale) {
        BigDecimal bd = BigDecimal.valueOf(distance.doubleValue());
//        bd = bd.movePointLeft(2);
        bd = bd.movePointLeft(n);        
        return convertDistance(Double.valueOf(bd.floatValue()), convertToMetric, locale);
    }


    public static String convertFuelEfficiency(Number mpg, MeasurementType measurementType, FuelEfficiencyType fuelEfficiencyType, Locale locale) {
        NumberFormat nf = NumberFormat.getNumberInstance(locale);
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
    	return nf.format(MeasurementConversionUtil.convertMpgToFuelEfficiencyType(mpg, measurementType, fuelEfficiencyType));
    }


}
