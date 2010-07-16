package com.inthinc.pro.reports.hos.converter;

import static org.junit.Assert.*;

import java.util.Locale;

import org.junit.Test;


public class ConverterTest {
    
    @Test
    public void gallonLiterDescription() {
        
        String convertedStr = Converter.convertRemarkDescription("Vehicle Gallons: 500 Trailer Gallons: 100", Locale.US);
        
        assertEquals("Unexpected conversion", " Vehicle Liters: 1893 Trailer Liters: 379", convertedStr);
        
        convertedStr = Converter.convertRemarkDescription("Fuel Stop: Vehicle Gallons: 16.1 Trailer Gallons: 0", Locale.US);

        assertEquals("Unexpected conversion", " Fuel Stop: Vehicle Liters: 61 Trailer Liters: 0", convertedStr);
        
    }
}
