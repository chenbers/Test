package com.inthinc.pro.dao.util;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.inthinc.pro.model.FuelEfficiencyType;
import com.inthinc.pro.model.MeasurementType;




public class MetricConversionUtilTest
{
    private static final Logger logger = Logger.getLogger(MetricConversionUtilTest.class);
 
    @Test
    public void testFromMPHtoKPH()
    {
        Integer expectedkph = 89;
        Integer actualmph = 55;
        Number kilometersperhour = MeasurementConversionUtil.fromMPHtoKPH(actualmph);
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
        Number milesperhour = MeasurementConversionUtil.fromKPHtoMPH(actualkph);
        logger.debug(actualkph + " kph = " +  milesperhour.toString() + " mph");
        Assert.assertEquals(expectedmph, milesperhour);
    }
    
    @Test
    public void testFromMPGtoKPL()
    {
        Long expecedcKPL = 10L;
        Long actualMPG = 23L;
        Double kilometersPerLiter = (Double)MeasurementConversionUtil.fromMPGtoKPL(actualMPG);
        Assert.assertEquals(expecedcKPL, Long.valueOf(Math.round(kilometersPerLiter)));
    }
    
    @Test
    public void testMilesToKiloMeters()
    {
        Float expectedKilometers = 27.36F;
        Double actualMiles = 17D;
        Float kilometers = MeasurementConversionUtil.fromMilesToKilometers(actualMiles).floatValue();        
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
    /**
     * Test method for {@link com.inthinc.pro.dao.util.MeasurementConversionUtil#convertMpgToFuelEfficiencyType(java.lang.Number, com.inthinc.pro.model.MeasurementType, com.inthinc.pro.model.FuelEfficiencyType)}.
     */
    @Test
    public void testConvertMpgToFuelEfficiencyTpe() 
    {
        Number result = MeasurementConversionUtil.convertMpgToFuelEfficiencyType(20, MeasurementType.ENGLISH, FuelEfficiencyType.MPG_UK);
        Assert.assertEquals(result,24.0); 
        
        result = MeasurementConversionUtil.convertMpgToFuelEfficiencyType(20, MeasurementType.ENGLISH, FuelEfficiencyType.MPG_US);
        Assert.assertEquals(result,20.0); 
        result = MeasurementConversionUtil.convertMpgToFuelEfficiencyType(20, MeasurementType.ENGLISH, FuelEfficiencyType.KMPL);
        Assert.assertEquals(result,20.0* 0.42514); 
        result = MeasurementConversionUtil.convertMpgToFuelEfficiencyType(20, MeasurementType.ENGLISH, FuelEfficiencyType.LP100KM);
        Assert.assertEquals(result,100/(20.0* 0.42514)); 
        result = MeasurementConversionUtil.convertMpgToFuelEfficiencyType(20, MeasurementType.METRIC, FuelEfficiencyType.MPG_UK);
        Assert.assertEquals(result,24.0); 
        result = MeasurementConversionUtil.convertMpgToFuelEfficiencyType(20, MeasurementType.METRIC, FuelEfficiencyType.MPG_US);
        Assert.assertEquals(result,20.0); 
        result = MeasurementConversionUtil.convertMpgToFuelEfficiencyType(20, MeasurementType.METRIC, FuelEfficiencyType.KMPL);
        Assert.assertEquals(result,20.0* 0.42514); 
        result = MeasurementConversionUtil.convertMpgToFuelEfficiencyType(20, MeasurementType.METRIC, FuelEfficiencyType.LP100KM);
        Number expected = 100/(20.0* 0.42514);
        Assert.assertEquals(result,expected); 
      
    }

}
