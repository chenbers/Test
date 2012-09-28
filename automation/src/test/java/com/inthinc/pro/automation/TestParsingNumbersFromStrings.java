package com.inthinc.pro.automation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.inthinc.pro.automation.utils.AutomationNumberManager;

public class TestParsingNumbersFromStrings {
    

    @Test
    public void testParser(){
        String step = "And I select the option containing \"None\" from the Information dropdown";
        int parsed = AutomationNumberManager.extractXNumber(step, 0);
        assertEquals("No number provided", 1, parsed);
        
        step = "And I select the option containing the 21st instance of \"None\" from the Information dropdown";
        parsed = AutomationNumberManager.extractXNumber(step, 0);
        assertEquals("Specific number", 21, parsed);
        
        Integer[] numbers = {13, 44, 2984, 333, 8899, 0, -1, Integer.MAX_VALUE-1, Integer.MIN_VALUE+1, -99999};
        int length = numbers.length;
        String format = "";
        for (int i=0; i<length; i++){
            format += "%d ";
        }
        
        step = String.format(format, (Object[])numbers);
        
        for (int i=0; i<length;i++){
            parsed = AutomationNumberManager.extractXNumber(step, i+1);
            assertEquals("Grabbed by group", parsed, Math.abs(numbers[i]));
        }
    }

}
