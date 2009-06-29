package com.inthinc.pro.dao.util;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.inthinc.pro.dao.util.MeasurementConversionUtil;
import com.inthinc.pro.model.MeasurementType;




public class MetricConversionUtilTest
{
    private static final Logger logger = Logger.getLogger(MetricConversionUtilTest.class);
 
    @Test
    public void testFromMPHtoKPH()
    {
        Integer expectedkph = 89;
        Integer actualmph = 55;
        Integer kilometersperhour = MeasurementConversionUtil.fromMPHtoKPH(actualmph);
        logger.debug(actualmph + " mph = " +  kilometersperhour.toString() + " kph");
        Assert.assertEquals(expectedkph, kilometersperhour);
        
        expectedkph = 56;
        actualmph = 35;
        kilometersperhour = MeasurementConversionUtil.fromMPHtoKPH(actualmph);
        logger.debug(actualmph + " mph = " +  kilometersperhour.toString() + " kph");
        Assert.assertEquals(expectedkph, kilometersperhour);
        
        expectedkph = 72;
        actualmph = 45;
        kilometersperhour = MeasurementConversionUtil.fromMPHtoKPH(actualmph);
        logger.debug(actualmph + " mph = " +  kilometersperhour.toString() + " kph");
        Assert.assertEquals(expectedkph, kilometersperhour);
    }
    
    @Test
    public void testFromKPHtoMPH()
    {
        Integer expectedmph = 43;
        Integer actualkph = 70;
        Integer milesperhour = MeasurementConversionUtil.fromKPHtoMPH(actualkph);
        logger.debug(actualkph + " kph = " +  milesperhour.toString() + " mph");
        Assert.assertEquals(expectedmph, milesperhour);
    }
    
    @Test
    public void testFromMPGtoKPL()
    {
        Long expecedcKPL = 10L;
        Long actualMPG = 23L;
        Long kilometersPerLiter = MeasurementConversionUtil.fromMPGtoKPL(actualMPG);
        Assert.assertEquals(expecedcKPL, kilometersPerLiter);
    }
    
    @Test
    public void testMilesToKiloMeters()
    {
        Float expectedKilometers = 27.4F;
        Double actualMiles = 17D;
        Float kilometers = MeasurementConversionUtil.fromMilesToKilometers(actualMiles);        
        Assert.assertEquals(expectedKilometers.toString(), kilometers.toString());
    }
    
    @Test
    public void testPoundConversion()
    {
        Long kg = 73L;
        Long pounds = 160L;
        Long actualKg = MeasurementConversionUtil.convertWeight(pounds,MeasurementType.METRIC);
        Assert.assertEquals(kg, actualKg);
        
        pounds=161L;
        Long actualPounds = MeasurementConversionUtil.convertWeight(kg, MeasurementType.ENGLISH);
        Assert.assertEquals(pounds, actualPounds);
    }
    
    @Test
    public void testHeightConversion()
    {
        Long inches = 67L;
        Long centimeters = 170L;
        Long actualCm = MeasurementConversionUtil.fromInchesToCentimeters(inches.intValue());
        Assert.assertEquals(centimeters, actualCm);
        
        actualCm = MeasurementConversionUtil.fromFeetInchToCentimeters(5, 7);
        Assert.assertEquals(centimeters,actualCm);
    }

}
