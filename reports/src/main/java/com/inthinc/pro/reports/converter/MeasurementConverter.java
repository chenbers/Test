package com.inthinc.pro.reports.converter;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.MeasurementType;

public class MeasurementConverter
{
    private static final Logger logger = Logger.getLogger(MeasurementConverter.class);
    
    public static Integer convertSpeed(Integer speed,Boolean convertToMetric){
        if(convertToMetric)
            return MeasurementConversionUtil.convertSpeed(speed, MeasurementType.METRIC).intValue();
        else
            return speed;
    }
    
    public static Integer convertMileage(Integer mileage,Boolean convertToMetric)
    {
        logger.debug("Mileage: " + mileage + " Convert To Metric: " + convertToMetric);
        if(convertToMetric != null && convertToMetric)
            return MeasurementConversionUtil.convertMileage(mileage, MeasurementType.METRIC).intValue();
        else
            return mileage;
        
    }
    
    public static Float convertDistance(Integer distance,Boolean convertToMetric){
        logger.debug("Distance: " + distance + " Convert To Metric: " + convertToMetric);
        if(convertToMetric)
            return MeasurementConversionUtil.convertDistance(distance, MeasurementType.METRIC);
        else
            return distance.floatValue();
    }
}
