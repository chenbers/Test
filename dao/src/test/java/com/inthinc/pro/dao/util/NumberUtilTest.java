package com.inthinc.pro.dao.util;

import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class NumberUtilTest {
    
    @Test
    public void testConvertStringToDoubleZero(){
        
        Double result = NumberUtil.convertStringToDouble("0");
        assertTrue(result.equals(0.0));
    }
    
    @Test
    public void testConvertStringToDoubleInteger(){
        
        Double result = NumberUtil.convertStringToDouble("1");
        assertTrue(result.equals(1.0));
    }
    @Test
    public void testConvertStringToDoubleError(){
        
        Double result = NumberUtil.convertStringToDouble("dfgsdgsdg");
        assertTrue(result.equals(0.0));
    }
    @Test
    public void testConvertStringToDoubleNull(){
        
        Double result = NumberUtil.convertStringToDouble(null);
        assertTrue(result.equals(0.0));
    }
    @Test
    public void testConvertStringToDoubleEmpty(){
        
        Double result = NumberUtil.convertStringToDouble("");
        assertTrue(result.equals(0.0));
    }
    @Test
    public void testConvertStringToDoubleDouble(){
        
        Double result = NumberUtil.convertStringToDouble("3.5656");
        assertTrue(result.equals(3.5656));
    }
    @Test
    public void testIntValueNull() {
        int result = NumberUtil.intValue(null);
        int expected = 0;
        assertTrue(expected == result);
    }
    @Test
    public void testIntValueDouble() {
        assertTrue(1 == NumberUtil.intValue(1.0d));
    }
}
