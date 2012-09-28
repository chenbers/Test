package com.inthinc.pro.charts;


import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


public class Bar3DTest
{

    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }
    
    @Test
    public void chart()
    {
        Bar3D bar3D = new Bar3D();
        
        String result = bar3D.getChartItem(new Object[] {Integer.valueOf(44)});
        
        assertEquals("<set value='44'/>", result);
        
        result = bar3D.getChartDataSet("title", "red", new Object[] {Integer.valueOf(33), Integer.valueOf(44)});
        String expectedResult = "<dataset seriesName='title' color='red' showValues='0'><set value='33'/><set value='44'/></dataset>";
        assertEquals(expectedResult, result);

    }


}
