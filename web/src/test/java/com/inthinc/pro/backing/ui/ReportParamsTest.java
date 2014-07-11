package com.inthinc.pro.backing.ui;

import static org.junit.Assert.*;

import java.util.Locale;

import org.junit.Test;

import com.inthinc.pro.model.ReportParamType;

import mockit.Mocked;
import mockit.NonStrictExpectations;

public class ReportParamsTest {
    
    @Mocked private Locale mockLocale;
    
    private ReportParams reportParamsCUT = new ReportParams(mockLocale);
    
    @Test
    /**
     * Tests whether isZeroMilesDriversControlDisabled returns false
     * when paramType is null
     */
    public void testIsZeroMilesDriversControlDisabledWithNullParamType() {
        
        assertFalse(reportParamsCUT.isZeroMilesDriversControlDisabled());
    }
    
    @Test
    /**
     * Tests whether isZeroMilesDriversControlDisabled returns true
     * when paramType is DRIVER
     */
    public void testIsZeroMilesDriversControlDisabledWithDriverParamType() {
        
        // Override the parameterType with the mocked version
        reportParamsCUT.setParamType(ReportParamType.DRIVER);
        
        assertTrue(reportParamsCUT.isZeroMilesDriversControlDisabled());
    }
    
    @Test
    /**
     * Tests whether isZeroMilesDriversControlDisabled returns false
     * when paramType is GROUPS
     */
    public void testIsZeroMilesDriversControlDisabledWithGroupParamType() {
        
        // Override the parameterType with the mocked version
        reportParamsCUT.setParamType(ReportParamType.GROUPS);
        
        assertFalse(reportParamsCUT.isZeroMilesDriversControlDisabled());
    }
    
    @Test
    /**
     * Tests whether isIncludeZeroMilesDrivers is overridden
     * when paramType is null (it should NOT be overridden)
     */
    public void testIsIncludeZeroMilesDriversWithNullParamType() {
        
        reportParamsCUT.setIncludeZeroMilesDrivers(false);
        
        assertFalse(reportParamsCUT.isIncludeZeroMilesDrivers());
    }
    
    @Test
    /**
     * Tests whether isIncludeZeroMilesDrivers is overridden
     * when paramType is DRIVER (it SHOULD be overridden)
     */
    public void testIsIncludeZeroMilesDriversWithDriverParamType() {
        
        reportParamsCUT.setIncludeZeroMilesDrivers(false);
        reportParamsCUT.setParamType(ReportParamType.DRIVER);
        
        assertTrue(reportParamsCUT.isIncludeZeroMilesDrivers());
    }
    
}
