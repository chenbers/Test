package com.inthinc.pro.reports.converter;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.util.MathUtil;
import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.MeasurementType;

public class MeasurementConverter {
    private static final Logger logger = Logger.getLogger(MeasurementConverter.class);

    public static String convertSpeed(Integer speed, Boolean convertToMetric, Locale locale) {
        NumberFormat nf = NumberFormat.getNumberInstance(locale);
        nf.setMaximumFractionDigits(1);
        nf.setMinimumFractionDigits(1);
        if (convertToMetric)
            return nf.format(MeasurementConversionUtil.convertSpeed(speed, MeasurementType.METRIC).intValue());
        else
            return nf.format(speed);
    }

    public static Number convertMileage(Number mileage, Boolean convertToMetric, Locale locale) {
        logger.debug("Mileage: " + mileage + " Convert To Metric: " + convertToMetric);
        if (convertToMetric != null && convertToMetric)
            return MeasurementConversionUtil.convertMpgToKpl(mileage, MeasurementType.METRIC);
        else
            return mileage;
    }

    public static String convertDistance(Number distance, Boolean convertToMetric, Locale locale) {
        logger.debug("Distance: " + distance + " Convert To Metric: " + convertToMetric);
        NumberFormat nf = NumberFormat.getNumberInstance(locale);
        nf.setMaximumFractionDigits(1);
        nf.setMinimumFractionDigits(1);
        if (convertToMetric)
            return nf.format(MeasurementConversionUtil.convertMilesToKilometers(distance, MeasurementType.METRIC));
        else {
            return nf.format( MathUtil.round(distance, 2));
        }
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
}
