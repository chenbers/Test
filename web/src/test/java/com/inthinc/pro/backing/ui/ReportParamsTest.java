package com.inthinc.pro.backing.ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Locale;

import mockit.Mocked;

import org.junit.Test;

import com.inthinc.pro.model.ReportParamType;

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
    
    @Test
    /**
     * Tests whether isInactiveDriversControlDisabled returns false
     * when paramType is null
     */
    public void testIsInactiveDriversControlDisabledWithNullParamType() {
        
        assertFalse(reportParamsCUT.isInactiveDriversControlDisabled());
    }
    
    @Test
    /**
     * Tests whether isInactiveControlDisabled returns true
     * when paramType is DRIVER
     */
    public void testIsInactiveDriversControlDisabledWithDriverParamType() {
        
        // Override the parameterType with the mocked version
        reportParamsCUT.setParamType(ReportParamType.DRIVER);
        
        assertTrue(reportParamsCUT.isInactiveDriversControlDisabled());
    }
    
    @Test
    /**
     * Tests whether isInactiveDriversControlDisabled returns false
     * when paramType is GROUPS
     */
    public void testIsInactiveDriversControlDisabledWithGroupParamType() {
        
        // Override the parameterType with the mocked version
        reportParamsCUT.setParamType(ReportParamType.GROUPS);
        
        assertFalse(reportParamsCUT.isInactiveDriversControlDisabled());
    }
    
    @Test
    /**
     * Tests whether isIncludeInactiveDrivers is overridden
     * when paramType is null (it should NOT be overridden)
     */
    public void testIsIncludeInactiveDriversWithNullParamType() {
        
        reportParamsCUT.setIncludeInactiveDrivers(false);
        
        assertFalse(reportParamsCUT.isIncludeInactiveDrivers());
    }
    
    @Test
    /**
     * Tests whether isIncludeInactiveDrivers is overridden
     * when paramType is DRIVER (it SHOULD be overridden)
     */
    public void testIsIncludeInactiveDriversWithDriverParamType() {
        
        reportParamsCUT.setIncludeInactiveDrivers(false);
        reportParamsCUT.setParamType(ReportParamType.DRIVER);
        
        assertTrue(reportParamsCUT.isIncludeInactiveDrivers());
    }
    
}
