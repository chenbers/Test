package com.inthinc.pro.charts;


import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class PieTest
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
    public void chartItem()
    {
        Pie pie = new Pie();
        
        String result = pie.getChartItem(new Object[] {Integer.valueOf(44), "", "123456"});
        
        assertEquals("<set value='44' label='' color='123456'/>", result);
    }

}
