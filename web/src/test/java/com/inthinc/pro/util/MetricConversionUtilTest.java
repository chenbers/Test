package com.inthinc.pro.util;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;


public class MetricConversionUtilTest
{
    private static final Logger logger = Logger.getLogger(MetricConversionUtilTest.class);
 
    @Test
    public void testFromMPHtoKPH()
    {
        Long expectedkph = 89L;
        Long actualmph = 55L;
        Long kilometersperhour = MetricConversionUtil.fromMPHtoKPH(actualmph);
        logger.debug(actualmph + " mph = " +  kilometersperhour.toString() + " kph");
        Assert.assertEquals(expectedkph, kilometersperhour);
        
        expectedkph = 56L;
        actualmph = 35L;
        kilometersperhour = MetricConversionUtil.fromMPHtoKPH(actualmph);
        logger.debug(actualmph + " mph = " +  kilometersperhour.toString() + " kph");
        Assert.assertEquals(expectedkph, kilometersperhour);
        
        expectedkph = 72L;
        actualmph = 45L;
        kilometersperhour = MetricConversionUtil.fromMPHtoKPH(actualmph);
        logger.debug(actualmph + " mph = " +  kilometersperhour.toString() + " kph");
        Assert.assertEquals(expectedkph, kilometersperhour);
    }
    
    @Test
    public void testFromKPHtoMPH()
    {
        Long expectedmph = 43L;
        Long actualkph = 70L;
        Long milesperhour = MetricConversionUtil.fromKPHtoMPH(actualkph);
        logger.debug(actualkph + " kph = " +  milesperhour.toString() + " mph");
        Assert.assertEquals(expectedmph, milesperhour);
    }
    
    @Test
    public void testFromMPGtoKPL()
    {
        Long expecedcKPL = 10L;
        Long actualMPG = 23L;
        Long kilometersPerLiter = MetricConversionUtil.fromMPGtoKPL(actualMPG);
        Assert.assertEquals(expecedcKPL, kilometersPerLiter);
    }

}
