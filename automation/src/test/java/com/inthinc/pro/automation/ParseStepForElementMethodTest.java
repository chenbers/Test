package com.inthinc.pro.automation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.inthinc.pro.automation.selenium.AutomationProperties;
import com.inthinc.pro.automation.utils.MasterTest;

public class ParseStepForElementMethodTest {
    
    private MasterTest test;
    
    public ParseStepForElementMethodTest(){
        test = new MasterTest(){};
    }
    
    /**
     * Test that we can get the default user and password from a step string.
     */
    @Test
    public void testDefaultCreds(){
        AutomationPropertiesBean apb = AutomationProperties.getPropertyBean();
        test.setupUser();
        assertEquals("Unable to get the default user from the string", 
                apb.getUsers().get(0), MasterTest.getComparator("When I type the defaultUser in the Login field"));
        assertEquals("Unable to get the default user from the string", 
                apb.getPassword(), MasterTest.getComparator("When I type the defaultPassword in the Login field"));
    }
    
    /**
     * Test that we can get a string from a step with a quoted value
     */
    @Test
    public void testQuotedValue(){
        String value = "This is my test value 123 *(&";
        String stepAsString = "I type \"" + value +"\" into the Username field";
        assertEquals("Was unable to get the value from the step", value, MasterTest.getComparator(stepAsString));
    }
    
    /**
     * Test that we can save any variable and get it again
     */
    @Test
    public void testSavingVariable(){
        String value = "This is my test value 321 !@#$";
        String variableName = "my stinking variable name";
        String saveStep = "Then I save the Login button as " + variableName;
        MasterTest.setComparator(saveStep, value);
        String useStep = "I type " + variableName + " into the Login field";
        assertEquals("Was unable to get the value from the step", value, MasterTest.getComparator(useStep));
    }
    
    /**
     * Test that we can save any variable and get it again
     */
    @Test
    public void testGettingSavedVariable(){
        String value = "This is my test value 321 !@#$";
        String variableName = "my stinking variable name";
        String saveStep = "Then I save the Login button as " + variableName;
        MasterTest.setComparator(saveStep, value);
        String useStep = "I validate the Login button is " + variableName;
        assertEquals("Was unable to get the value from the step", value, MasterTest.getComparator(useStep));
    }
    
    /**
     * Test that we can use a quoted string as a comparator
     */
    @Test
    public void testComparingToQuoted(){
        String value = "This is my test value 321 !@#$";
        String useStep = "I validate the Login button is \"" + value + "\"";
        assertEquals("Was unable to get the value from the step", value, MasterTest.getComparator(useStep));
    }
}
