package com.inthinc.pro.util;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.inthinc.pro.dao.util.MeasurementConversionUtil;




public class MetricConversionUtilTest
{
    private static final Logger logger = Logger.getLogger(MetricConversionUtilTest.class);
 
    @Test
    public void testFromMPHtoKPH()
    {
        Long expectedkph = 89L;
        Long actualmph = 55L;
        Long kilometersperhour = MeasurementConversionUtil.fromMPHtoKPH(actualmph);
        logger.debug(actualmph + " mph = " +  kilometersperhour.toString() + " kph");
        Assert.assertEquals(expectedkph, kilometersperhour);
        
        expectedkph = 56L;
        actualmph = 35L;
        kilometersperhour = MeasurementConversionUtil.fromMPHtoKPH(actualmph);
        logger.debug(actualmph + " mph = " +  kilometersperhour.toString() + " kph");
        Assert.assertEquals(expectedkph, kilometersperhour);
        
        expectedkph = 72L;
        actualmph = 45L;
        kilometersperhour = MeasurementConversionUtil.fromMPHtoKPH(actualmph);
        logger.debug(actualmph + " mph = " +  kilometersperhour.toString() + " kph");
        Assert.assertEquals(expectedkph, kilometersperhour);
    }
    
    @Test
    public void testFromKPHtoMPH()
    {
        Long expectedmph = 43L;
        Long actualkph = 70L;
        Long milesperhour = MeasurementConversionUtil.fromKPHtoMPH(actualkph);
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
        Long actualMiles = 17L;
        Float kilometers = MeasurementConversionUtil.fromMilesToKilometers(actualMiles);        
        Assert.assertEquals(expectedKilometers.toString(), kilometers.toString());
    }

}
