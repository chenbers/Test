package com.inthinc.pro.backing.importer.validator;

import static org.junit.Assert.*;

import org.junit.Test;


public class ValidatorTest {
    
    @Test
    public void validators() {
        
        assertTrue("StringValidator valid", new StringValidator(10).isValid("hello"));
        assertFalse("StringValidator invalid", new StringValidator(5).isValid("tooLong"));
        checkInvalidMessage(new StringValidator(5));
        
        assertTrue("CountryValidator valid", new CountryValidator().isValid("US"));
        assertFalse("CountryValidator invalid", new CountryValidator().isValid("junk"));
        checkInvalidMessage(new CountryValidator());

        assertTrue("LanguageValidator valid", new LanguageValidator().isValid("en"));
        assertFalse("LanguageValidator invalid", new LanguageValidator().isValid("junk"));
        checkInvalidMessage(new LanguageValidator());

        assertTrue("TimeZoneValidator valid", new TimeZoneValidator().isValid("MST"));
        assertFalse("TimeZoneValidator invalid", new TimeZoneValidator().isValid("junk"));
        checkInvalidMessage(new TimeZoneValidator());

        assertTrue("MeasurementTypeValidator valid", new MeasurementTypeValidator().isValid("METRIC"));
        assertFalse("MeasurementTypeValidator invalid", new MeasurementTypeValidator().isValid("junk"));
        checkInvalidMessage(new MeasurementTypeValidator());

        assertTrue("FuelEfficiencyTypeValidator valid", new FuelEfficiencyTypeValidator().isValid("LP100KM"));
        assertFalse("FuelEfficiencyTypeValidator invalid", new FuelEfficiencyTypeValidator().isValid("junk"));
        checkInvalidMessage(new FuelEfficiencyTypeValidator());
    
    }
    
    private void checkInvalidMessage(Validator validator) {
        assertNotNull(validator.getInvalidMessage());
        System.out.println(validator.getInvalidMessage());
        
    }
}
