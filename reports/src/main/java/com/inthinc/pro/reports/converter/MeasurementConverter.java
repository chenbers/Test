package com.inthinc.pro.reports.converter;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.util.MathUtil;
import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.FuelEfficiencyType;
import com.inthinc.pro.model.MeasurementType;

public class MeasurementConverter {
    private static final Logger logger = Logger.getLogger(MeasurementConverter.class);

    public static Integer convertSpeed(Integer speed, Boolean convertToMetric) {
        if (convertToMetric)
            return MeasurementConversionUtil.convertSpeed(speed, MeasurementType.METRIC).intValue();
        else
            return speed;
    }

    public static Number convertMileage(Number mileage, Boolean convertToMetric) {
        logger.debug("Mileage: " + mileage + " Convert To Metric: " + convertToMetric);
        if (convertToMetric != null && convertToMetric)
            return MeasurementConversionUtil.convertMpgToKpl(mileage, MeasurementType.METRIC);
        else
            return mileage;
    }

    public static Number convertDistance(Number distance, Boolean convertToMetric) {
        logger.debug("Distance: " + distance + " Convert To Metric: " + convertToMetric);
        if (convertToMetric)
            return MeasurementConversionUtil.convertMilesToKilometers(distance, MeasurementType.METRIC);
        else {
            return MathUtil.round(distance, 2);
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
    public static Number convertDistanceAndMovePoint(Number distance, Boolean convertToMetric, Integer n) {
        BigDecimal bd = BigDecimal.valueOf(distance.doubleValue());
        bd = bd.movePointLeft(2);
        return convertDistance(Double.valueOf(bd.floatValue()), convertToMetric);
    }
}
