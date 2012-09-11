package com.inthinc.pro.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import com.inthinc.pro.dao.util.DefaultedMap;
import com.inthinc.pro.dao.util.MathUtil;
import com.inthinc.pro.dao.util.MeasurementConversionUtil;

@XmlRootElement
public enum MeasurementLengthType implements BaseEnum {
	
    ENGLISH_FEET(1, MeasurementConversionUtil.METERS_IN_FOOT),
    ENGLISH_MILES(2, MeasurementConversionUtil.METERS_IN_MILE),
    METRIC_FEET(3, MeasurementConversionUtil.METERS_IN_METER),
    METRIC_MILES(4, MeasurementConversionUtil.METERS_IN_KILOMETER);
    
    private int code;
    private double metersPer;
    
    private MeasurementLengthType(int code, Double metersPer) {
        this.code = code;
        this.metersPer = metersPer;
    }

    public Number convertFromMeters(Number meters){
        return meters==null?null:MathUtil.round(meters.doubleValue()/metersPer, 1);
    }
    public Number convertToMeters(Number lengthIn){
        return lengthIn==null?null:(lengthIn.doubleValue()*metersPer);
    }
    public Number convertToUnit(Number lengthIn, MeasurementLengthType unitToOutput){
        Number result;
        if(unitToOutput==this){
            result = lengthIn;
        } else {
            Double meters = lengthIn.doubleValue() * metersPer;
            result = meters/unitToOutput.metersPer;
        }
        return result;
    }
    private static final Map<Integer, MeasurementLengthType> lookup = new DefaultedMap<Integer,MeasurementLengthType>(new HashMap<Integer, MeasurementLengthType>(),
            MeasurementLengthType.ENGLISH_MILES);
    static {
        for (MeasurementLengthType type : EnumSet.allOf(MeasurementLengthType.class)) {
            lookup.put(type.code, type);
        }
    }

    public static MeasurementLengthType valueOf(Integer code) {
        return lookup.get(code);
    }

    @Override
    public Integer getCode() {
        return this.code;
    }
}
