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

        kilometersperhour = MeasurementConversionUtil.fromMPHtoKPH(null);
//        logger.debug(actualmph + " mph = " +  kilometersperhour.toString() + " kph");
        Assert.assertEquals(null, kilometersperhour);
}
    
    @Test
    public void testFromKPHtoMPH()
    {
        Integer expectedmph = 43;
        Integer actualkph = 70;
        Number milesperhour = MeasurementConversionUtil.fromKPHtoMPH(actualkph);
        logger.debug(actualkph + " kph = " +  milesperhour.toString() + " mph");
        Assert.assertEquals(expectedmph, milesperhour);

        milesperhour = MeasurementConversionUtil.fromKPHtoMPH(null);
 //       logger.debug(actualkph + " kph = " +  milesperhour.toString() + " mph");
        Assert.assertEquals(null	, milesperhour);
    }
    
    @Test
    public void testFromMPGtoKPL()
    {
        Long expecedcKPL = 10L;
        Long actualMPG = 23L;
        Double kilometersPerLiter = (Double)MeasurementConversionUtil.fromMPGtoKPL(actualMPG);
        Assert.assertEquals(expecedcKPL, Long.valueOf(Math.round(kilometersPerLiter)));
        
        kilometersPerLiter = (Double)MeasurementConversionUtil.fromMPGtoKPL(null);
        Assert.assertEquals(null, kilometersPerLiter);
   }
    
    @Test
    public void testMilesToKiloMeters()
    {
        Float expectedKilometers = 27.4F;
        Double actualMiles = 17D;
        Float kilometers = MeasurementConversionUtil.fromMilesToKilometers(actualMiles).floatValue();        
        Assert.assertEquals(expectedKilometers.toString(), kilometers.toString());

        Number km = MeasurementConversionUtil.fromMilesToKilometers(null);        
        Assert.assertEquals(null, km);
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
    public void testConvertMpgToFuelEfficiencyType() 
    {
        Number result = MeasurementConversionUtil.convertMpgToFuelEfficiencyType(20, MeasurementType.ENGLISH, FuelEfficiencyType.MPG_UK);
        Assert.assertEquals(result,24.0F); 
        result = MeasurementConversionUtil.convertMpgToFuelEfficiencyType(null, null, null);
        Assert.assertEquals(result,null); 

        result = MeasurementConversionUtil.convertMpgToFuelEfficiencyType(20, null, null);
        Assert.assertEquals(result,20F); 
        result = MeasurementConversionUtil.convertMpgToFuelEfficiencyType(20, null, FuelEfficiencyType.MPG_US);
        Assert.assertEquals(result,20F); 
        result = MeasurementConversionUtil.convertMpgToFuelEfficiencyType(20,null, FuelEfficiencyType.MPG_UK);
        Assert.assertEquals(result,24.0F); 
        result = MeasurementConversionUtil.convertMpgToFuelEfficiencyType(20, null, FuelEfficiencyType.KMPL);
        Assert.assertEquals(result,8.5F); //20.0* 0.42514 
        result = MeasurementConversionUtil.convertMpgToFuelEfficiencyType(20, null, FuelEfficiencyType.LP100KM);
        Assert.assertEquals(result,11.76F); //100/(20.0* 0.42514) 
        result = MeasurementConversionUtil.convertMpgToFuelEfficiencyType(20, MeasurementType.ENGLISH, FuelEfficiencyType.MPG_US);
        Assert.assertEquals(result,20F); 
        result = MeasurementConversionUtil.convertMpgToFuelEfficiencyType(20, MeasurementType.ENGLISH, FuelEfficiencyType.KMPL);
        Assert.assertEquals(result,8.5F); //20.0* 0.42514 
        result = MeasurementConversionUtil.convertMpgToFuelEfficiencyType(20, MeasurementType.ENGLISH, FuelEfficiencyType.LP100KM);
        Assert.assertEquals(result,11.76F); //100/(20.0* 0.42514) 
        result = MeasurementConversionUtil.convertMpgToFuelEfficiencyType(20, MeasurementType.METRIC, FuelEfficiencyType.MPG_UK);
        Assert.assertEquals(result,24.0F); 
        result = MeasurementConversionUtil.convertMpgToFuelEfficiencyType(20, MeasurementType.METRIC, FuelEfficiencyType.MPG_US);
        Assert.assertEquals(result,20F); 
        result = MeasurementConversionUtil.convertMpgToFuelEfficiencyType(20, MeasurementType.METRIC, FuelEfficiencyType.KMPL);
        Assert.assertEquals(result,8.5F); //20.0* 0.42514 
        result = MeasurementConversionUtil.convertMpgToFuelEfficiencyType(20, MeasurementType.METRIC, FuelEfficiencyType.LP100KM);
        Number expected = 11.76F; // 100/(20.0* 0.42514)
        Assert.assertEquals(result,expected); 
        
        Assert.assertEquals(FuelEfficiencyType.MPG_US, FuelEfficiencyType.valueOf(24)); 
        Assert.assertEquals(FuelEfficiencyType.MPG_US, FuelEfficiencyType.valueOf(1)); 
        Assert.assertEquals(FuelEfficiencyType.MPG_UK, FuelEfficiencyType.valueOf(2)); 
        Assert.assertEquals(FuelEfficiencyType.KMPL, FuelEfficiencyType.valueOf(3)); 
        Assert.assertEquals(FuelEfficiencyType.LP100KM, FuelEfficiencyType.valueOf(4)); 
        Assert.assertEquals(FuelEfficiencyType.MPG_US, FuelEfficiencyType.valueOf(0)); 
      
    }

    @Test
    public void testRoundToNearestFive(){
    	
    	Assert.assertEquals(0, MathUtil.roundToNearestFive(0));
    	Assert.assertEquals(0, MathUtil.roundToNearestFive(1));
    	Assert.assertEquals(0, MathUtil.roundToNearestFive(2));
    	Assert.assertEquals(5, MathUtil.roundToNearestFive(3));
    	Assert.assertEquals(5, MathUtil.roundToNearestFive(5));
    	Assert.assertEquals(5, MathUtil.roundToNearestFive(6));
    	Assert.assertEquals(10, MathUtil.roundToNearestFive(9));
    	Assert.assertEquals(-10, MathUtil.roundToNearestFive(-9));
    	Assert.assertEquals(-5, MathUtil.roundToNearestFive(-6));
    	Assert.assertEquals(-5, MathUtil.roundToNearestFive(-4));
    	Assert.assertEquals(10, MathUtil.roundToNearestFive(8));
    	Assert.assertEquals(10, MathUtil.roundToNearestFive(7.5));
    	Assert.assertEquals(5, MathUtil.roundToNearestFive(7.4));
    	
  }
}
